<!-- 댓글 입력 창 -->
<template>
  <div>
    <div class="py-7 px-3 text-lg font-semibold" v-if="!user">
      Guest Comment
    </div>
    <div class="w-full px-3 mb-2 mt-6">
      <input
        v-if="!user"
        class="bg-white rounded border border-gray-400 placeholder-gray-400 py-1 px-3 focus:bg-white font-light focus:outline-none"
        placeholder="Password"
        type="password"
        v-model="commentRequestDTO.password"
      />
    </div>
    <div class="w-full px-3 mb-2 mt-6">
      <textarea
        class="bg-white rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-light placeholder-lightgray-400 focus:outline-none focus:bg-white"
        name="body"
        placeholder="댓글을 입력해주세요"
        v-model="commentRequestDTO.content"
        @keyup.enter="createComment"
      ></textarea>
    </div>

    <div class="w-full flex justify-end px-3 my-3" id="submitBtn">
      <input
        type="submit"
        class="px-2.5 py-1.5 rounded-md text-white text-sm btn text-lg btn-orange cursor-pointer"
        value="Post Comment"
        @click="createComment"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { createUserComment, createGuestComment } from "@/api/common";
import { useRoute } from "vue-router";
import router from "@/router";

const emit = defineEmits(["createComment", "submitPassword"]);

const user = localStorage.getItem("Authorization");
const currentRoute = useRoute();
let articleId = currentRoute.params.articleId;

const commentRequestDTO = ref({
  content: "",
  password: "",
  commentType: user ? "USER" : "GUEST",
});

// 댓글등록
async function createComment() {
  const content = commentRequestDTO.value.content.trim();
  const password = commentRequestDTO.value.password.trim();

  if (content === "") {
    alert("댓글 내용을 입력하세요");
    return;
  }

  try {
    if (user) {
      // 회원
      console.log("articleId", articleId);
      await createUserComment(commentRequestDTO.value, articleId);
    } else {
      // 비회원
      if (password === "") {
        alert("비밀번호를 입력하세요");
        return;
      }
      await createGuestComment(commentRequestDTO.value, articleId);
    }
    emit("createComment");
    resetInput();
  } catch (error) {
    console.error(error);
  }
}

// 입력창 리셋
function resetInput() {
  commentRequestDTO.value.content = "";
  commentRequestDTO.value.password = "";
}
</script>

<style scoped>
.btn {
  margin-right: 5px;
  padding: 5px 16px;
  min-width: 75px;
  border: 1px solid orange;
  border-radius: 30px;
  transition: all 0.3s ease;
  background-color: #ff9800;
}
.btn:hover {
  background-color: #ff9800;
  color: #fff;
}
.btn-orange {
  background-color: transparent;
  color: #ff9800;
}
</style>
