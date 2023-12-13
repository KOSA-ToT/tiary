<template>
  <div>게시판 페이지 입니다~</div>
  Comment
  <div class="w-fullbg-white rounded-lg border p-1 md:p-3 m-10">
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
      }"
      @replyToComment="handleReplyToComment"
    >
    </CommentCard>

    <CommentInput></CommentInput>
    <!-- <div v-if="userId">
      <UserCommentInput></UserCommentInput>
    </div>
    <div v-else>
      <GuestCommentInput></GuestCommentInput>
    </div> -->
  </div>
</template>

<script setup>
import axios from "axios";
import CommentCard from "@/components/comment/CommentCard.vue";
import CommentInput from "@/components/comment/CommentInput.vue";
import { onMounted, reactive, ref, watch, watchSyncEffect } from "vue";

let userId = sessionStorage.getItem("user");

let commentList = reactive([]);
let commentRequestDTO = ref({
  content: "",
  password: "",
});

onMounted(async () => {
  await getCommentList();
  console.log(commentList.value);
});

// comment 불러오기
watch(async function getCommentList() {
  try {
    const response = await axios.get("http://localhost:8088/comment/1014");
    commentList.value = response.data;
  } catch (err) {
    console.log(err);
  }
});
</script>

<style scoped></style>
