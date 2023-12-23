<template>
  <button type="button" class="flex items-center gap-2 bg-blue-500 text-white p-2 rounded-full cursor-pointer btn btn-outline btn-orange">
    <svg class="w-6 h-6" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24">
      <path d="M3 7H1a1 1 0 0 0-1 1v10a2 2 0 0 0 4 0V8a1 1 0 0 0-1-1Zm12.954 0H12l1.558-4.5a1.778 1.778 0 0 0-3.331-1.06A24.859 24.859 0 0 1 6 6.8v13.586h.114C8.223 21.969 11.015 23 13.6 23c1.4 0 1.592-.526 1.88-1.317l2.354-7A2 2 0 0 0 15.954 7Z"/>
    </svg>
    <span>좋아요</span>
  </button>
</template>
<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { getArticleLikeRequest, getArticleLikeCancleRequest } from '@/api/common';

const { articleId } = defineProps(['articleId']);
const isLiked = ref(false);

onMounted(async () =>{
  try{
    console.log(articleId);
  } catch(error){
    console.error("좋아요 상태를 가져오는 데 실패했습니다:", error);
  }
})

</script>
<style scoped>
.btn {
  margin-right: 5px;
  padding: 5px 16px; /* padding 수정 */
  min-width: 75px; /* 최소 너비 추가 */
  border: 1px solid orange;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn:hover {
  border-color: #ff9800;
}

.btn-outline {
  background-color: transparent;
  color: #ff9800;
}

.btn-outline:hover {
  background-color: #ff9800;
  color: #fff;
}
.btn-orange {
  background-color: transparent;
  color : #ff9800;
}
</style>
