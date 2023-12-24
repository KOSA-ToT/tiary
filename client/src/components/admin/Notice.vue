<template>
  <div class="text-center">공지사항입니다~</div>
</template>

<script setup>
import { onMounted } from "vue";
import { useNoticeStore } from "@/stores/noticeStore";
const noticeStore = useNoticeStore();
onMounted(async () => {
  await test();
});
function test() {
  console.log("공지사항", noticeStore.notices);
}
</script>

<style scoped></style>
