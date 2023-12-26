<template>
  <div>
    <div class="m-3 gap-100">
      <div>
        <div class="w-full mb-3 border rounded-md">
          <div class="p-3">

            <div class="flex items-center gap-3"><router-link :to="'/writer-page/' + commentData.userId"
                v-if="commentData.userProfileImageUrl">
                <img v-if="commentData.userProfileImageUrl" :src="
                  'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' +
                  commentData.userProfileImageUrl
                "
                  class="object-cover w-10 h-10 border-2 border-orange-300 rounded-full shadow-emerald-400" /></router-link>
              <img v-else src="https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_640.png"
                class="object-cover w-10 h-10 border-2 border-orange-300 rounded-full shadow-emerald-400" />
              <h3 class="font-bold">
                {{
                  commentData.createdBy === "anonymousUser"
                  ? "Guest"
                  : commentData.createdBy
                }}
                <br />
                <span class="text-sm font-normal text-gray-400">
                  {{
                    commentData.createdAt == commentData.modifiedAt
                    ? formatCreatedAt(commentData.createdAt)
                    : formatCreatedAt(commentData.modifiedAt)
                  }}
                </span>
              </h3>
            </div>
            <p class="mt-2 text-gray-600">
              {{ commentData.content }}
            </p>
            <div class="text-sm font-normal text-gray-400" v-if="commentData.children && commentData.children.length > 0"
              @click="showReplyComment">
              <ReplyCommentCard v-for="(replyComment, index) in commentData.children"
                :replyCommentData="commentData.children[index]"></ReplyCommentCard>
            </div>
            <button class="text-left text-orange-300" @click="replyToComment">
              Reply
            </button>
            <div class="text-right">
              <!-- 회원이 작성한 댓글 -->
              <span v-if="showEditDeleteBtn" class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
                @click="openUpdateModal">
                edit &nbsp;&nbsp;
              </span>
              <span v-if="showEditDeleteBtn" class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700"
                @click="deleteComment">
                delete</span>
              <!-- 비회원이 작성한 댓글 -->
              <span v-if="commentData.createdBy === 'anonymousUser'"
                class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700" @click="openModal('edit'),editComment, deleteComment"  >
                edit&nbsp;&nbsp;
              </span>
              <span v-if="commentData.createdBy === 'anonymousUser'"
                class="text-sm font-normal text-gray-400 cursor-pointer hover:text-gray-700" @click="openModal('delete')">
                delete</span>
            </div>
          </div>
        </div>
      </div>
      <div class="flex justify-center my-0">
        <CommentPasswordModal v-if="isPasswordModalOpen" @closeModal="closeModal" @submitPassword="checkPassword">
        </CommentPasswordModal>

        <CommentUpdateModal v-if="isUpdateModalOpen" @closeModal="closeModal" @updateContent="editComment"
          :commentContent="{
            content: commentData.content,
          }">
        </CommentUpdateModal>

        <ReplyInputModal v-if="isReplyModalOpen" @closeModal="closeModal" @createReplyComment="createReplyComment">
        </ReplyInputModal>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps, ref, computed } from "vue";
import ReplyCommentCard from "../comment/ReplyCommentCard.vue";
import CommentPasswordModal from "../comment/CommentPasswordModal.vue";
import CommentUpdateModal from "../comment/CommentUpdateModal.vue";
import ReplyInputModal from "./ReplyInputModal.vue";
import { useAuthStore } from "@/stores/auth";
import {
  commentPasswordConfirm,
  editUserComment,
  editGuestComment,
  deleteUserComment,
  deleteGuestComment,
  createUserComment,
  createGuestComment,
} from "@/api/common";

const { commentData } = defineProps(["commentData"]);
const emit = defineEmits(["editComment", "deleteComment"])

const authStore = useAuthStore();
let user = localStorage.getItem("Authorization");

const mode = ref("");
let commentId = ref(commentData.id);

let commentRequestDTO = ref({
  content: "",
  password: "",
  parentId: "",
});

// 모달창
let isPasswordModalOpen = ref(false);
let isUpdateModalOpen = ref(false);
let isReplyModalOpen = ref(false);

// 로그인 확인
const showEditDeleteBtn = computed(() => {
  return authStore.isLoggedIn && authStore.currentUser === commentData.email;
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
  console.log("비밀번호", commentRequestDTO.value.password);
  try {
    const passwordConfirmResponse = await commentPasswordConfirm(
      commentRequestDTO.value,
      commentData.id
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
      commentData.articleId,
      commentData.id
    );
    console.log("Comment updated successfully", editCommentResponse);
    closeModal();
    emit("editComment")
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
    const response = await deleteComment(commentData.articleId, commentData.id);
    console.log(response);
    closeModal();
    alert("성공적으로 삭제되었습니다.");
    emit("deleteComment")
  } catch (error) {
    console.error("Error deleting comment", error);
  }
}

// 대댓글 모달창 오픈
function replyToComment() {
  isReplyModalOpen.value = true;
}

// 대댓글 등록
async function createReplyComment(replyComment) {
  let content = replyComment.content.trim();
  let password = replyComment.password.trim();

  if (content === "") {
    alert("댓글 내용을 입력하세요");
    return;
  }
  try {
    commentRequestDTO.value.content = replyComment.content;
    commentRequestDTO.value.parentId = commentData.id;
    if (user) {
      let commentResponse = await createUserComment(
        commentRequestDTO.value,
        commentData.articleId
      );
      console.log(commentResponse);
      closeModal();
    } else {
      if (password == "") {
        alert("비밀번호를 입력하세요");
        return;
      }
      commentRequestDTO.value.password = replyComment.password;
      await createGuestComment(commentRequestDTO.value, commentData.articleId);
      closeModal();
    }
    emit("createReplyComment");
  } catch (error) {
    console.log(error);
  }
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
