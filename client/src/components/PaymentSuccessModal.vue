<template>
  <div class="max-w-2xl m-0 sm:m-10 bg-white shadow sm:rounded-lg flex justify-center flex-1">
    <div class="p-6">
      <button
        @click="closePaymentSuccessModal()"
        type="button"
        class="absolute top-3 right-3 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm ml-auto inline-flex items-center popup-close"
      >
        <svg
          aria-hidden="true"
          class="w-5 h-5"
          fill="#c6c7c7"
          viewBox="0 0 20 20"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            fill-rule="evenodd"
            d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
            clip-rule="evenodd"
          ></path>
        </svg>
        <span class="sr-only">Close Modal</span>
      </button>
      <div class="flex items-center justify-center h-full space-x-2">
        <!-- 모달 내용 -->
        <div>
          <p class="text-lg font-bold mb-4">후원이 완료되었습니다!</p>
          <!-- 확인 버튼 -->
          <button @click="confirmPaymentSuccess" class="btn btn-orange">확인</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
const { emit } = defineEmits();

const confirmPaymentSuccess = () => {
  emit('update:paymentSuccess', false);
};

</script>

<style scoped>
/* 추가적인 스타일링이 필요한 경우 여기에 추가하세요. */
</style>
