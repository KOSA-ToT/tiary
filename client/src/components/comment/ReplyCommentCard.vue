<template>
  <!-- Reply Container  -->
  <div class="text-gray-300 font-bold pl-14">|</div>
  <div class="border ml-5 rounded-md">
    <div class="p-3">
      <div class="flex gap-3 items-center">
        <img
          src="https://avatars.githubusercontent.com/u/22263436?v=4"
          class="object-cover w-10 h-10 rounded-full border-2 border-emerald-400 shadow-emerald-400"
        />
        <h3 class="font-bold">
          {{ replyCommentData.createdBy }}
          <br />
          <span class="text-sm text-gray-400 font-normal">{{
            formatCreatedAt(replyCommentData.createdAt)
          }}</span>
        </h3>
      </div>
      <p class="text-gray-600 mt-2">
        {{ replyCommentData.content }}
      </p>
      <button class="text-left text-blue-500" @click="replyToComment">
        Reply
      </button>
      <div class="text-right">
        <!-- 수정버튼 클릭 시 회원이면 수정폼 / 비회원이면 비밀번호 인증폼 -->
        <span
          class="text-sm text-gray-400 font-normal cursor-pointer"
          @click="openModal"
        >
          edit&nbsp;&nbsp;
        </span>
        <span
          class="text-sm text-gray-400 font-normal cursor-pointer"
          @click="deleteComment"
        >
          delete</span
        >
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
const { replyCommentData } = defineProps(["replyCommentData"]);
console.log(replyCommentData);

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
