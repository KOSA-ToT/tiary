<template>
  <div class="w-fullbg-white rounded-lg border p-1 md:p-3">
    <h3 class="font-semibold p-1">Comment</h3>
    <CommentCard
      v-for="comment in commentList.value"
      :key="comment.id"
      :commentData="{
        id: comment.id,
        articleId: comment.articleId,
        commentType: comment.commentType,
        children: comment.children,
        content: comment.content,
        createdAt: comment.createdAt,
        createdBy: comment.createdBy,
        modifiedAt: comment.modifiedAt,
        userProfileImageUrl: comment.userProfileImageUrl,
      }"
    >
    </CommentCard>

    <CommentInput></CommentInput>
  </div>
</template>

<script setup>
import axios from "axios";
import CommentCard from "@/components/comment/CommentCard.vue";
import CommentInput from "@/components/comment/CommentInput.vue";
import { useRoute } from "vue-router";
import { onMounted, reactive, ref, watch, watchEffect } from "vue";
import { getCommentList } from "@/api/common.js";

const currentRoute = useRoute();
let articleId = currentRoute.params.articleId;

let commentList = reactive([]);

onMounted(async () => {
  await getCommentLists();
  // console.log(commentList.value);
  // console.log("articleId", articleId);
});

// // 새로고침 안하고 바로 불러오기
watch(
  () => commentList.value,
  (newVal, oldVal) => {
    if (newVal !== oldVal) {
      getCommentLists();
      // console.log("Comment list updated:", newVal);
    }
  },
  { deep: true, immediate: true }
);

// 새로고침 없이 데이터 업데이트
// watchEffect(
//   () => {
//     getCommentLists();
//   },
//   { immediate: true, deep:true }
// );

// comment 불러오기
async function getCommentLists() {
  try {
    const response = await getCommentList(articleId);
    commentList.value = response.data;
  } catch (err) {
    console.log(err);
  }
}
</script>

<style scoped>


</style>
