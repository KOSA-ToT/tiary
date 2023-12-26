<template>
  <!-- Container for demo purpose -->
  <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
    <p class="pb-6">
    <div class="container mx-auto my-12 md:px-6">
      <!-- Section: Design Block -->
      <section class="mb-12 text-center md:text-left">
        <h2 class="mb-12 ml-12 text-lg font-bold text-orange-500">구독한 글</h2>
        <div class="flex justify-end">
        </div>
        <div v-if="postList" v-for="post in postList" :key="post.id" class="max-w-3xl mx-auto p-4 bg-white shadow-lg">
          <h1 class="text-3xl font-bold mb-5">
          {{ post.title || "제목 없음" }}
        </h1>
          <div id="content" ref="editor">
            <div v-html="post.content"></div>
          </div>
          <hr>
        </div>
        <div v-else class="text-center mt-10">
          <p>Loading...</p>
        </div>
      </section>
    </div>
    </p>
  </div>
  <!-- Container for demo purpose -->
</template>
<script setup>
import { defineProps, onMounted, ref, watchEffect, watch } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { listMySubscribe } from '@/api/common';
const testHtml = ref("");
const postList = ref([]);
const props = defineProps({
  user: {
    type: Object,
    required: true,
  },
});

async function listMySubscribes() {
  try {
    const response = await listMySubscribe(props.user.id.value);
    postList.value = response.data;
    console.log(postList.value);
  } catch (error) {
    console.log(error);
  }
}
const formatCreatedAt = (createdAt) => {
  // createdAt이 문자열인 경우 Date 객체로 변환
  const date = typeof createdAt === 'string' ? new Date(createdAt) : createdAt;

  // Date 객체를 원하는 형식으로 변환
  const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ` +
    `${date.getHours()}시 ${date.getMinutes()}분 ${date.getSeconds()}초`;

  return formattedDate;
};

onMounted(() => {
  listMySubscribes();
});
watch(() => props.user.id.value, () => {
  listMySubscribes();
});
</script>
<style>

</style>