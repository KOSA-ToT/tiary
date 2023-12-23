<template>
  <!-- 비회원 댓글 수정 모달 -->
  <div class="w-3/4 border rounded-lg">
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
          class="px-4 py-2 ml-2 btn btn-orange text-white text-sm font-medium rounded-md"
          @click="$emit('updateContent', content)"
        >
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps, ref, watch } from "vue";

const content = ref();
const props = defineProps(["closeModal", "updateContent", "commentContent"]);

defineEmits(["closeModal", "updateContent"]);

// 댓글 내용이 변경될 때마다 content 값을 초기화
watch(
  () => props.commentContent.content,
  () => {
    content.value = props.commentContent.content;
  },
  { immediate: true }
);
</script>
<style scoped>
.btn {
  min-width: 75px;
  border: 1px solid orange;
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
