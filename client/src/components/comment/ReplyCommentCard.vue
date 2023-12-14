<template>
  <!-- Reply Container  -->
  <div class="text-gray-300 font-bold pl-14">|</div>
  <div class="border ml-5 rounded-md">
    <div class="p-3">
      <div class="flex gap-3 items-center">
        <img
          src="https://avatars.githubusercontent.com/u/22263436?v=4"
          class="object-cover w-10 h-10 rounded-full border-2 border-emerald-400 shadow-emerald-400"
        />

        <h3 class="font-bold">
          {{ replyCommentData.createdBy }} 아이디: {{ replyCommentData.id }}
          <br />
          <span class="text-sm text-gray-400 font-normal">{{
            formatCreatedAt(replyCommentData.createdAt)
          }}</span>
        </h3>
      </div>
      <p class="text-gray-600 mt-2">
        {{ replyCommentData.content }}
      </p>
      <!-- <button class="text-left text-blue-500" @click="replyToComment">
        Reply
      </button> -->
      <div class="text-right">
        <!-- 회원이 작성한 댓글 -->
        <!-- 
        <span
          class="text-sm text-gray-400 hover:text-gray-700 font-normal cursor-pointer"
          @click="openUpdateModal"
        >
          edit&nbsp;&nbsp;
        </span>
        <span
          class="text-sm text-gray-400 hover:text-gray-700 font-normal cursor-pointer"
          @click="deleteComment"
        >
          delete</span
        > -->

        <!-- 비회원이 작성한 댓글 -->

        <span
          class="text-sm text-gray-400 hover:text-gray-700 font-normal cursor-pointer"
          @click="openModal('edit')"
        >
          edit&nbsp;&nbsp;
        </span>
        <span
          class="text-sm text-gray-400 hover:text-gray-700 font-normal cursor-pointer"
          @click="openModal('delete')"
        >
          delete</span
        >
      </div>

      <CommentPasswordModal
        v-if="isPasswordModalOpen"
        @closeModal="closeModal"
        @submitPassword="checkPassword"
      ></CommentPasswordModal>

      <CommentUpdateModal
        v-if="isUpdateModalOpen"
        @closeModal="closeModal"
        @updateContent="editComment"
        :commentContent="{
          content: replyCommentData.content,
        }"
      >
      </CommentUpdateModal>

      <ReplyInputModal
        v-if="isReplyModalOpen"
        @closeModal="closeModal"
        @createReplyComment="createReplyComment"
      ></ReplyInputModal>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { defineProps, ref } from "vue";
import CommentPasswordModal from "./CommentPasswordModal.vue";
import CommentUpdateModal from "./CommentUpdateModal.vue";
import ReplyInputModal from "./ReplyInputModal.vue";
import router from "@/router";
const { replyCommentData } = defineProps(["replyCommentData"]);
console.log(replyCommentData);

let commentRequestDTO = ref({
  content: "",
  password: "",
  parentId: "",
});

const mode = ref("");

let commentId = ref(replyCommentData.id);

let isPasswordModalOpen = ref(false);
let isUpdateModalOpen = ref(false);
let isReplyModalOpen = ref(false);

// 수정 모달창 오픈
function openUpdateModal() {
  isUpdateModalOpen.value = true;
}

// 비밀번호 확인 모달창 열기
function openModal(action) {
  // 회원
  // isUpdateModalOpen.value = true;

  // 비회원
  mode.value = action;
  isPasswordModalOpen.value = true;
}

// 모달창 닫기
function closeModal() {
  isPasswordModalOpen.value = false;
  isUpdateModalOpen.value = false;
}

// 비밀번호 검증
function checkPassword(password) {
  commentRequestDTO.value.password = password;
  console.log("비밀번호", commentRequestDTO.value.password);

  axios
    .post(
      `http://localhost:8088/comment/guest/password-confirm/${replyCommentData.id}`,
      commentRequestDTO.value
    )
    .then((response) => {
      if (mode.value === "delete") {
        deleteComment();
      } else if (mode.value === "edit") {
        closeModal();
        openUpdateModal();
      }
    })
    .catch((error) => {
      alert("비밀번호가 일치하지 않습니다.");
      closeModal();
      console.log("Error confirming password", error);
    });
}

//  댓글 수정
function editComment(content) {
  commentRequestDTO.value.content = content;
  axios
    .patch(
      `http://localhost:8088/comment/guest/2/${replyCommentData.id}`,
      commentRequestDTO.value
    )
    .then((response) => {
      console.log("Comment updated successfully", response);
      closeModal();
    })
    .catch((error) => {
      console.error("Error updating comment", error);
    });
}

// 댓글 삭제
function deleteComment() {
  let result = confirm("정말 삭제하시겠습니까?");
  if (result) {
    axios
      .delete(`http://localhost:8088/comment/guest/2/${replyCommentData.id}`)
      .then((response) => {
        alert("성공적으로 삭제되었습니다.");
        router.push("/article-test");
      })
      .catch((error) => console.error("Error deleting comment", error));
  }
}

// 댓글 포맷
function formatCreatedAt(createdAt) {
  const date = new Date(createdAt);
  return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(
    date.getDate()
  )} ${padZero(date.getHours())}:${padZero(date.getMinutes())}:${padZero(
    date.getSeconds()
  )}`;
}
function padZero(num) {
  return num.toString().padStart(2, "0");
}
</script>

<style scoped></style>