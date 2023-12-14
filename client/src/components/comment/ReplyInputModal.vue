<template>
  <!-- 대댓글 작성 모달 -->
  <div class="max-w-md border rounded-lg">
    <div class="flex flex-col p-5 rounded-lg shadow bg-white">
      <div class="ml-3">
        <h2 class="font-semibold text-gray-800">대댓글 작성</h2>
        <br />
        <!-- userId가 존재하는 경우 비밀번호 입력란 숨김 -->
        <input v-if="userId" type="hidden" />
        <!-- userId가 존재하지 않는 경우 비밀번호 입력란 표시 -->
        <div v-else>
          <input
            type="password"
            class="border rounded-lg"
            placeholder="password"
            v-model="replyComment.password"
          />
          <br /><br />
        </div>
        <textarea
          type="text"
          class="border rounded-lg w-5/6 h-20"
          v-model="replyComment.content"
        ></textarea>
      </div>

      <div class="flex justify-end items-center mt-3">
        <button
          class="px-4 py-2 bg-gray-100 hover:bg-gray-200 text-gray-800 text-sm font-medium rounded-md"
          @click="$emit('closeModal')"
        >
          Cancel
        </button>
        <button
          class="px-4 py-2 ml-2 bg-blue-500 hover:bg-blue-600 text-white text-sm font-medium rounded-md"
          @click="$emit('createReplyComment', replyComment)"
        >
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

let userId = "test";
const replyComment = ref({
  password: "",
  content: "",
});
// const props = defineProps(["closeModal", "createReplyComment"]);

defineEmits(["closeModal", "createReplyComment"]);
</script>

<style scoped></style>
