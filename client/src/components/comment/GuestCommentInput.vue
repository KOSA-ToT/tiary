<template>
  <!-- 비회원 내용 입력폼 -->
  <div class="w-full px-3 mb-2 mt-6">
    <input
      class="bg-white rounded border border-gray-400 placeholder-gray-400 py-1 px-3 focus:bg-white font-medium focus:outline-none"
      placeholder="Password"
      required
    />
  </div>
  <!-- 비밀번호 입력폼 -->
  <div class="w-full px-3 mb-2 mt-6">
    <input
      class="bg-white rounded border border-gray-400 leading-normal resize-none w-full h-20 py-2 px-3 font-medium placeholder-gray-400 focus:outline-none focus:bg-white"
      name="body"
      placeholder="Comment"
      required
    />
  </div>
  <!-- 등록버튼 -->
  <div class="w-full flex justify-end px-3 my-3" id="submitBtn">
    <input
      type="submit"
      class="px-2.5 py-1.5 rounded-md text-white text-sm bg-indigo-500 text-lg"
      value="Post Comment"
    />
  </div>
</template>

<script setup></script>

<style scoped></style>
