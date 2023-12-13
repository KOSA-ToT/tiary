<template>
  <!-- 비회원 댓글 수정 모달 -->
  <div class="max-w-md border rounded-lg">
    <div class="flex flex-col p-5 rounded-lg shadow bg-white">
      <div class="ml-3">
        <h2 class="font-semibold text-gray-800">댓글 내용 수정</h2>
        <br />
        <textarea
          type="text"
          class="border rounded-lg w-5/6 h-20"
          v-model="content"
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
          @click="$emit('updateContent', content)"
        >
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref } from "vue";

// const content = ref(props.commentContent.content);
const content = ref("");
const props = defineProps(["closeModal", "updateContent", "commentContent"]);

defineEmits(["closeModal", "updateContent"]);
</script>
<style scoped></style>
