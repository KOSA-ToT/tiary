<template>
  <div>
    <div class="w-full px-3 mb-2 mt-6" v-if="userId">
      <textarea
        class="bg-white rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-400 focus:outline-none focus:bg-white"
        name="body"
        placeholder="Comment"
        v-model="commentRequestDTO.content"
        @keyup.enter="createComment"
      ></textarea>
    </div>

    <div class="py-7 px-3 text-lg font-semibold" v-else>Guest Comment</div>

    <div class="w-full px-3 mb-2 mt-6">
      <input
        v-if="!userId"
        class="bg-white rounded border border-gray-400 placeholder-gray-400 py-1 px-3 focus:bg-white font-medium focus:outline-none"
        placeholder="Password"
        type="password"
        v-model="commentRequestDTO.password"
      />
    </div>

    <div class="w-full flex justify-end px-3 my-3" id="submitBtn">
      <input
        type="submit"
        class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 text-lg cursor-pointer hover:bg-indigo-700"
        value="Post Comment"
        @click="createComment"
      />
    </div>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref } from "vue";

// const userId = sessionStorage.getItem("user");
const userId = "test";
const auth =
  "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlkIjoxLCJleHAiOjE3MDI1MzIwNjQsImVtYWlsIjoidGVzdEBnbWFpbC5jb20ifQ.daZZtA-l1q8YRI4BOt-Wct7oFKCtfTtLnB8teYB2PmInbWucnZxQp2AoBk2SJf7ABlCfqHuXZiyEgT9yIcJ6kg";

const commentRequestDTO = ref({
  content: "",
  password: "",
  commentType: userId ? "USER" : "GUEST",
});

// 댓글등록
function createComment() {
  if (commentRequestDTO.value.content.trim() == "") {
    alert("댓글 내용을 입력하세요");
  } else {
    // 회원
    if (userId) {
      axios
        .post(`http://localhost:8088/comment/2`, commentRequestDTO.value, {
          headers: {
            Authorization: auth,
          },
        })
        .then((response) => {
          console.log(response);
          resetInput();
        })
        .catch((err) => console.log(err));
    }
    // 비회원
    else {
      if (commentRequestDTO.value.password.trim() == "") {
        alert("비밀번호를 입력하세요");
      } else {
        axios
          .post(
            `http://localhost:8088/comment/${userId ? "" : "guest"}/2`,
            commentRequestDTO.value
          )
          .then((response) => {
            console.log(response);
            resetInput();
          })
          .catch((err) => console.log(err));
      }
    }
  }
}
// 입력창 리셋
function resetInput() {
  commentRequestDTO.value.content = "";
  commentRequestDTO.value.password = "";
}
</script>

<style scoped></style>
