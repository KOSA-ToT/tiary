<template>
  <div class="py-7 px-3 text-lg font-semibold">Guest Comment</div>
  <!--비밀번호 입력폼 -->
  <div class="w-full px-3 mb-2 mt-6">
    <input
      class="bg-white rounded border border-gray-400 placeholder-gray-400 py-1 px-3 focus:bg-white font-medium focus:outline-none"
      placeholder="Password"
      type="password"
      v-model="commentRequestDTO.password"
    />
  </div>
  <!-- 내용 입력폼 -->
  <div class="w-full px-3 mb-2 mt-6">
    <input
      class="bg-gray-50 rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-400 focus:outline-none focus:bg-white"
      name="body"
      placeholder="Comment"
      v-model="commentRequestDTO.content"
    />
  </div>
  <!-- 등록버튼 -->
  <div class="w-full flex justify-end px-3 my-3" id="submitBtn">
    <input
      type="submit"
      class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 text-lg cursor-pointer"
      value="Post Comment"
      @click="createComment"
    />
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";
let commentRequestDTO = ref({
  content: "",
  password: "",
  commentType: "GUEST",
});

// 비회원 댓글 등록
function createComment() {
  axios
    .post("http://localhost:8088/comment/guest/1014", commentRequestDTO.value)
    .then((response) => {
      console.log(response);
      commentRequestDTO.value.content = "";
      commentRequestDTO.value.password = "";
    })
    .catch((err) => console.log(err));
}
</script>

<style scoped></style>
