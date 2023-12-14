<template>
  <div>
    <div class="gap-100 m-3">
      <div>
        <div class="w-full border rounded-md mb-3">
          <div class="p-3">
            <div class="flex gap-3 items-center">
              <img
                src="https://avatars.githubusercontent.com/u/22263436?v=4"
                class="object-cover w-10 h-10 rounded-full border-2 border-emerald-400 shadow-emerald-400"
              />
              <h3 class="font-bold">
                id: {{ commentData.id }}/
                {{ commentData.createdBy }}
                <br />
                <span class="text-sm text-gray-400 font-normal"
                  >{{ formatCreatedAt(commentData.createdAt) }}
                </span>
              </h3>
            </div>
            <p class="text-gray-600 mt-2">{{ commentData.content }}</p>
            <div
              class="text-sm text-gray-400 font-normal"
              v-if="commentData.children && commentData.children.length > 0"
              @click="showReplyComment"
            >
              대댓글 개수: {{ commentData.children.length }}
              <ReplyCommentCard
                v-for="(replyComment, index) in commentData.children"
                :replyCommentData="commentData.children[index]"
              ></ReplyCommentCard>
            </div>
            <button class="text-left text-blue-500" @click="replyToComment">
              Reply
            </button>
            <div class="text-right">
              <!-- 회원이 작성한 댓글 -->

              <!-- <span
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
          </div>
        </div>
      </div>
      <div class="my-0">
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
            content: commentData.content,
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
  </div>
</template>
<script setup>
import axios from "axios";
import { defineProps, ref } from "vue";
import ReplyCommentCard from "../comment/ReplyCommentCard.vue";
import CommentPasswordModal from "../comment/CommentPasswordModal.vue";
import CommentUpdateModal from "../comment/CommentUpdateModal.vue";
import ReplyInputModal from "./ReplyInputModal.vue";
import router from "@/router";

const { commentData } = defineProps(["commentData"]);

let commentRequestDTO = ref({
  content: "",
  password: "",
  parentId: "",
});

const mode = ref("");

let commentId = ref(commentData.id);

let isPasswordModalOpen = ref(false);
let isUpdateModalOpen = ref(false);
let isReplyModalOpen = ref(false);

// 수정 모달창 오픈
function openUpdateModal() {
  isUpdateModalOpen.value = true;
}

// 비밀번호 확인 모달창 열기
function openModal(action) {
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
      `http://localhost:8088/comment/guest/password-confirm/${commentData.id}`,
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
      `http://localhost:8088/comment/guest/2/${commentData.id}`,
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
      .delete(`http://localhost:8088/comment/guest/2/${commentData.id}`)
      .then((response) => {
        alert("성공적으로 삭제되었습니다.");
      })
      .catch((error) => console.error("Error deleting comment", error));
  }
}

// 대댓글 모달창 오픈
function replyToComment() {
  isReplyModalOpen.value = true;
}

// 대댓글 등록
function createReplyComment(replyComment) {
  commentRequestDTO.value.content = replyComment.content;
  commentRequestDTO.value.password = replyComment.password;
  commentRequestDTO.value.parentId = commentData.id;

  // 비회원일 경우
  axios
    .post(`http://localhost:8088/comment/guest/2`, commentRequestDTO.value)
    .then((response) => {
      console.log(response);
      commentRequestDTO.value.content = "";
      commentRequestDTO.value.password = "";
    })
    .catch((err) => console.log(err));
}

// 등록 시간 포맷
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