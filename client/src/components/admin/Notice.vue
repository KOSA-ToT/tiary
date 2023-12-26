<template>
 <div class="flex justify-center">
  <img  src="/images/writing-icegif-15.gif" alt="writing image">
  </div>
</template>

<script setup>
import { defineProps, onMounted, ref, watchEffect, watch, computed } from "vue";
import { listMyPost, deleteArticleRequest } from "@/api/common";
import { useRoute } from "vue-router";

import { useNoticeStore } from "@/stores/noticeStore";
const noticeList = ref([]);
const visiblePages = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
const pageCalculate = ref();
const currentPage = ref(0);
let adminId = 10;
let noticeId = ref(0);
// const randomNum = ref(0);

onMounted(async () => {
  await listMyPosts();
});

async function listMyPosts() {
  try {
    pageCalculate.value = Math.floor(currentPage.value / 10);
    pageCalculate.value = pageCalculate.value * 10;
    visiblePages.value = Array.from(
      { length: 10 },
      (_, index) => pageCalculate.value + index + 1
    );
    const response = await listMyPost(adminId, currentPage.value);
    noticeList.value = response.data.content;
  } catch (error) {
    console.log(error);

  }
}

// function getRandomNotice() {
//   if (noticeList.value.length === 0) {
//     return "공지사항이 없습니다.";
//   }
//   randomNum.value = Math.floor(Math.random() * noticeList.value.length);
//   return noticeList.value[randomNum.value].title;
// }
</script>

<style scoped></style>
