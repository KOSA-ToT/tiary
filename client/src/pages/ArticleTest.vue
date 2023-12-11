<template>
  <div>게시판 페이지 입니다~</div>
  Comment
  <div class="w-fullbg-white rounded-lg border p-1 md:p-3 m-10">
    <h3 class="font-semibold p-1">Comment</h3>
    <CommentCard
      v-for="comment in commentList.value"
      :key="comment.id"
      :commentData="{
        articleId: comment.articleId,
        commentType: comment.commentType,
        content: comment.content,
        createdAt: comment.createdAt,
        createdBy: comment.createdBy,
      }"
    >
    </CommentCard>
    <!-- 작성폼 -->
    <div class="w-full px-3 mb-2 mt-6">
      <textarea
        class="bg-gray-100 rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-400 focus:outline-none focus:bg-white"
        name="body"
        placeholder="Comment" id="commentForm"
        required
      ></textarea>
    </div>
    <!-- 등록버튼 -->
    <div class="w-full flex justify-end px-3 my-3" id="submitBtn">
      <input
        type="submit"
        class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 text-lg"
        value="Post Comment" 
        @click="createComment"
      />
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import CommentCard from "@/components/comment/CommentCard.vue";
import { onMounted, reactive, ref } from "vue";

let commentList = reactive([]);

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

// 댓글 등록
function createComment(){

}


</script>

<style scoped></style>
