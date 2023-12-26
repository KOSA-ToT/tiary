<template>
  <!-- Container for demo purpose -->
  <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
    <p class="pb-6">
    <div class="container mx-auto my-12 md:px-6">
      <!-- Section: Design Block -->
      <section class="mb-12 text-center md:text-left">
        <h2 class="mb-12 ml-12 text-xl ">
          <td class="py-4 whitespace-nowrap">
  <span class="bg-gradient-to-tr font-extrabold from-pink-400 to-yellow-500 text-transparent bg-clip-text">구독한 글
    </span></td>
  </h2>
        <div class="flex justify-end">
        </div>
        <div v-if="postList" v-for="post in postList" :key="post.id"
          class="max-w-3xl mx-auto p-4 bg-white shadow-lg rounded-lg bg-gradient-to-tr from-pink-300 to-orange-200 p-0.5 mb-4">
          <router-link :to="'/article/' + post.id">
            <div class="bg-white p-7 rounded-md">
              <h1 class="text-3xl font-bold mb-5">
                {{ post.title || "제목 없음" }}
              </h1>
              <div class="flex grid-cols-2">
                <h1 class="text-xs mb-5 text-zinc-500 mr-2">
                  At {{
                    isWithinSixHours(post.createdAt)
                    ? formatTimeDifference(post.createdAt)
                    : dateFormat.formatCreatedAt(post.createdAt)
                  }}
                </h1>
                <h1 class="text-xs mb-5 text-zinc-500">
                  By {{ post.createdBy }}
                </h1>
              </div>
              <div id="content" ref="editor" class="text-base mb-2">
                <div v-html="post.content"></div>
              </div>
              <hr>
              <!-- 해시태그 영역 -->
              <div class="max-w-2xl mx-auto mt-4 p-4">
                <div class="flex flex-wrap justify-start items-end space-x-2">
                  <span v-for="hashtag in post.hashtagList" :key="hashtag.id">
                    <div class="bg-amber-500 text-white p-2 rounded-full mb-2">
                      #{{ hashtag.hashtagName }}
                    </div>
                  </span>
                </div>
              </div>
            </div>
          </router-link>
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
import * as dateFormat from "@/utils/dateformat.js";
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
const formatTimeDifference = (createdAt) => {
  const currentTime = new Date().getTime();
  const articleTime = new Date(createdAt).getTime();
  const timeDifference = currentTime - articleTime;

  const hours = Math.floor(timeDifference / (60 * 60 * 1000));
  const minutes = Math.floor((timeDifference % (60 * 60 * 1000)) / (60 * 1000));

  if (hours > 0) {
    return `${hours} 시간 전`;
  } else {
    return `${minutes} 분 전`;
  }
};
const isWithinSixHours = (createdAt) => {
  const sixHoursInMillis = 6 * 60 * 60 * 1000;
  const currentTime = new Date().getTime();
  const articleTime = new Date(createdAt).getTime();
  const timeDifference = currentTime - articleTime;

  return timeDifference <= sixHoursInMillis;
};
onMounted(() => {
  listMySubscribes();
});
watch(() => props.user.id.value, () => {
  listMySubscribes();
});
</script>
<style></style>