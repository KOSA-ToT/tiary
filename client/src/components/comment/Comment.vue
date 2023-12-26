<template>
  <div class="p-1 border rounded-lg w-fullbg-white md:p-3">
    <h3 class="p-1 font-semibold">Comment</h3>
    <CommentCard
      v-for="comment in commentList.value"
      :key="comment.id"
      :commentData="{
        id: comment.id,
        userId: comment.userId,
        articleId: comment.articleId,
        commentType: comment.commentType,
        children: comment.children,
        content: comment.content,
        createdAt: comment.createdAt,
        createdBy: comment.createdBy,
        modifiedAt: comment.modifiedAt,
        userProfileImageUrl: comment.userProfileImageUrl,
        email: comment.email,
      }"
      @editComment="handleCommentList"
      @deleteComment="handleCommentList"
      @createReplyComment="handleCommentList"
    >
    </CommentCard>

    <CommentInput @createComment="handleCommentList"></CommentInput>
  </div>
</template>

<script setup>
import CommentCard from "@/components/comment/CommentCard.vue";
import CommentInput from "@/components/comment/CommentInput.vue";
import { useRoute } from "vue-router";
import { onMounted, reactive, ref, watch, watchEffect } from "vue";
import { getCommentList } from "@/api/common.js";
import { useAuthStore } from "@/stores/auth";

const currentRoute = useRoute();
let articleId = currentRoute.params.articleId;

let commentList = reactive([]);
const authStore = useAuthStore();

onMounted(async () => {
  await getCommentLists();
});

// 새로고침 안하고 바로 불러오기

// watch(
//   () => commentList.value,
//   (newVal, oldVal) => {
//     if (newVal !== oldVal) {
//       getCommentLists();
//       // console.log("Comment list updated:", newVal);
//     }
//   },
//   { deep: true, immediate: true }
// );


// 댓글이 생성, 수정, 삭제되면 commentList 다시 불러오기
async function handleCommentList() {
  await getCommentLists();
}

// comment 불러오기
async function getCommentLists() {
  try {
    const response = await getCommentList(articleId);
    commentList.value = response.data;
    console.log(response);
  } catch (err) {
    console.log(err);
  }
}
</script>

<style scoped></style>
