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
    >
    </CommentCard>

    <!-- 로그인 한 경우 입력폼 -->
    <div v-if="userId">
      <UserCommentInput></UserCommentInput>
      </div>
      <!-- 비회원인 경우 입력폼 -->
      <div v-else>
        <GuestCommentInput></GuestCommentInput>
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import CommentCard from "@/components/comment/CommentCard.vue";
import UserCommentInput from "@/components/comment/UserCommentInput.vue";
import GuestCommentInput from "@/components/comment/GuestCommentInput.vue";
import { onMounted, reactive, ref } from "vue";

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
async function getCommentList() {
  try {
    const response = await axios.get("http://localhost:8088/comment/1014");
    commentList.value = response.data;
  } catch (err) {
    console.log(err);
  }
}
</script>

<style scoped></style>
