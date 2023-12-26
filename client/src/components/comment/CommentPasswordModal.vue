<template>
  <div class="w-3/4 border rounded-lg">
    <div class="flex flex-col p-5 rounded-lg shadow bg-white">
      <div class="flex">
        <div>
          <svg
            class="w-6 h-6 fill-current text-orange-300"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
          >
            <path d="M0 0h24v24H0V0z" fill="none" />
            <path
              d="M11 7h2v2h-2zm0 4h2v6h-2zm1-9C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 18c-4.41 0-8-3.59-8-8s3.59-8 8-8 8 3.59 8 8-3.59 8-8 8z"
            />
          </svg>
        </div>

        <div class="ml-3">
          <h2 class="font-semibold text-gray-800">비밀번호 확인</h2>
          <p class="mt-2 text-sm text-gray-600 leading-relaxed">
            비밀번호를 입력해주세요.
          </p>
          <input type="password" class="border rounded-lg" v-model="password" />
        </div>
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
          @click="$emit('submitPassword', password)"
        >
          Confirm
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

defineEmits(["closeModal", "submitPassword"]);

const password = ref("");
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
