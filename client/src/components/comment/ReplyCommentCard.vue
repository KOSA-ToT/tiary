<template>
  <!-- Reply Container  -->
  <div class="font-bold text-gray-300 pl-14">|</div>
  <div class="ml-5 border rounded-md">
    <div class="p-3">
      <div class="flex items-center gap-3">
        <router-link :to="'/writer-page/' + replyCommentData.userId" v-if="replyCommentData.userProfileImageUrl">
        <img
          v-if="replyCommentData.userProfileImageUrl"
          :src="
            'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' +
            replyCommentData.userProfileImageUrl
          "
          class="object-cover w-10 h-10 border-2 border-orange-300 rounded-full shadow-emerald-400"
        /></router-link>
        <img
          v-else
          src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
          class="object-cover w-10 h-10 border-2 border-orange-300 rounded-full shadow-emerald-400"
        />

        <h3 class="font-bold">
          {{
            replyCommentData.createdBy === "anonymousUser"
              ? "Guest"
              : replyCommentData.createdBy
          }}
          <br />
          <span class="text-sm font-normal text-gray-400">
            {{
              replyCommentData.createdAt == replyCommentData.modifiedAt
                ? formatCreatedAt(replyCommentData.createdAt)
                : formatCreatedAt(replyCommentData.modifiedAt)
            }}
          </span>
        </h3>
      </div>
      <p class="mt-2 text-gray-600">{{ replyCommentData.content }}</p>
      <!-- <button class="text-left text-blue-500" @click="replyToComment">
        Reply
      </button> -->
      <div class="text-right">
        <!-- 회원이 작성한 댓글 -->
        <span
        v-if="showEditDeleteBtn"
          class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
          @click="openUpdateModal"
        >
          edit&nbsp;&nbsp;
        </span>
        <span
        v-if="showEditDeleteBtn"
          class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
          @click="deleteComment"
        >
          delete</span
        >
        <!-- 비회원이 작성한 댓글 -->
        <span
          v-if="replyCommentData.createdBy === 'anonymousUser'"
          class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
          @click="openModal('edit')"
        >
          edit&nbsp;&nbsp;
        </span>
        <span
          v-if="replyCommentData.createdBy === 'anonymousUser'"
          class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
          @click="openModal('delete')"
        >
          delete</span
        >
      </div>
      <div class="flex justify-center my-0">
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
  </div>
</template>

<script setup>
import { defineProps, ref, computed } from "vue";
import CommentPasswordModal from "./CommentPasswordModal.vue";
import CommentUpdateModal from "./CommentUpdateModal.vue";
import ReplyInputModal from "./ReplyInputModal.vue";
import { useAuthStore } from "@/stores/auth";
import {
  commentPasswordConfirm,
  editGuestComment,
  editUserComment,
  deleteGuestComment,
  deleteUserComment,
} from "@/api/common";

const { replyCommentData } = defineProps(["replyCommentData"]);
const authStore = useAuthStore();
let user = localStorage.getItem("Authorization");

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

// 로그인 확인
const showEditDeleteBtn = computed(() => {
  return authStore.isLoggedIn && authStore.currentUser === replyCommentData.email;
});

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
  isReplyModalOpen.value = false;
}

// 비밀번호 검증
async function checkPassword(password) {
  commentRequestDTO.value.password = password;
  // console.log("비밀번호", commentRequestDTO.value.password);
  try {
    const passwordConfirmResponse = await commentPasswordConfirm(
      commentRequestDTO.value,
      replyCommentData.id
    );
    console.log(passwordConfirmResponse);
    if (mode.value === "delete") {
      deleteComment();
    } else if (mode.value === "edit") {
      closeModal();
      openUpdateModal();
    }
  } catch (error) {
    alert("비밀번호가 일치하지 않습니다.");
    closeModal();
    console.log("Error confirming password", error);
  }
}

//  댓글 수정
async function editComment(content) {
  commentRequestDTO.value.content = content;

  const editComment = user ? editUserComment : editGuestComment;
  try {
    const editCommentResponse = await editComment(
      commentRequestDTO.value,
      replyCommentData.articleId,
      replyCommentData.id
    );
    console.log("Comment updated successfully", editCommentResponse);
    closeModal();
  } catch (error) {
    console.log("failed edit comment", error);
  }
}

// 댓글 삭제
async function deleteComment() {
  const confirmDelete = confirm("정말 삭제하시겠습니까?");
  if (!confirmDelete) return;

  const deleteComment = user ? deleteUserComment : deleteGuestComment;
  try {
    const response = await deleteComment(
      replyCommentData.articleId,
      replyCommentData.id
    );
    console.log(response);
    closeModal();
    alert("성공적으로 삭제되었습니다.");
  } catch (error) {
    console.error("Error deleting comment", error);
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
