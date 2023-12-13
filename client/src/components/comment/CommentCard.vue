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
              <!-- 수정버튼 클릭 시 회원이면 수정폼 / 비회원이면 비밀번호 인증폼 -->
              <span
                class="text-sm text-gray-400 font-normal cursor-pointer"
                @click="openModal"
              >
                수정&nbsp;&nbsp;
              </span>
              <span
                class="text-sm text-gray-400 font-normal cursor-pointer"
                @click="deleteComment"
              >
                삭제</span
              >
            </div>
            <!-- 대댓글 표시 -->
          </div>
        </div>
      </div>
      <div class="my-0">
        <CommentPasswordModal
          v-if="isPasswordModalOpen"
          @closeModal="closeModal"
          @submitPassword="checkPasswordAndEditComment"
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
let commentId = ref(commentData.id);

let isPasswordModalOpen = ref(false);
let isUpdateModalOpen = ref(false);
let isReplyModalOpen = ref(false);

/** 대댓글 */
function replyToComment() {
  isReplyModalOpen.value = true;
}

// 대댓글 등록
function createReplyComment(replyComment) {
  console.log(replyComment.content);
  console.log(replyComment.password);
  commentRequestDTO.value.content = replyComment.content;
  commentRequestDTO.value.password = replyComment.password;
  commentRequestDTO.value.parentId = commentData.id;

  // 비회원일 경우
  axios
    .post(`http://localhost:8088/comment/guest/1014`, commentRequestDTO.value)
    .then((response) => {
      console.log(response);
      commentRequestDTO.value.content = "";
      commentRequestDTO.value.password = "";
    })
    .catch((err) => console.log(err));
}

/** 수정 모달창 열기 */
function openModal() {
  // 회원인지 비회원인지 처리해서 회원이면 수정폼 / 비회원이면 비밀번호 인증폼
  // 회원
  isUpdateModalOpen.value = true;

  // 비회원
  // isPasswordModalOpen.value = true;
}

/** 모달창 닫기 */
function closeModal() {
  isPasswordModalOpen.value = false;
  isUpdateModalOpen.value = false;
}

/** 비밀번호확인 + 수정 */
// 익명댓글인지 회원댓글인지 구분
// 익명댓글
function checkPasswordAndEditComment(password) {
  commentRequestDTO.value.password = password;
  console.log("비밀번호", commentRequestDTO.value.password);
  axios
    .post(
      `http://localhost:8088/comment/guest/password-confirm/${commentData.id}`,
      commentRequestDTO.value
    )
    .then((response) => {
      confirmPassword.value = true;
      if (response.status == 200) {
        // 비밀번호가 일치하면 댓글을 수정할 수 있는 함수 호출
        loadComment();
        // editComment();
        closePasswordModal();
      } else {
        console.error("Incorrect password");
      }
    })
    .catch((error) => {
      console.error("Error confirming password", error);
      // 적절한 에러 처리
    });
}

/**  댓글 수정 */
function editComment(content) {
  commentRequestDTO.value.content = content;
  axios
    .patch(
      `http://localhost:8088/comment/guest/1014/${commentData.id}`,
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

/** 댓글 삭제 */
function deleteComment() {
  // 익명댓글인지 회원댓글인지 구분
  //비회원 댓글 삭제
  console.log(commentData.id);
  let result = confirm("정말 삭제하시겠습니까?");
  if (result) {
    axios
      .delete(`http://localhost:8088/comment/guest/1014/${commentData.id}`)
      .then((response) => {
        alert("성공적으로 삭제되었습니다.");
        console.log("댓글이 성공적으로 삭제되었습니다.", response);
        router.push("/article-test");
      })
      .catch((error) => console.error("Error deleting comment", error));
  }
}
/** 등록 시간 포맷 */
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

/** 댓글 내용 불러오기 */
function loadComment() {
  // 비밀번호가 일치하면 해당 댓글의 내용을 불러온다.
  axios
    .get(`http://localhost:8088/comment/${commentData.id}`)
    .then((response) => {
      const comment = response.data;
      // 불러온 내용을 commentRequestDTO에 채워준다.
      commentRequestDTO.value.content = comment.content;
      // 불러온 내용을 모달 창에 표시할 수 있도록 구현
      // (모달 창에 댓글 내용을 표시할 수 있는 프로퍼티 추가 필요)
    })
    .catch((error) => {
      console.error("Error loading comment", error);
    });
}
</script>

<style scoped></style>
