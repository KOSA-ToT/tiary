<template>
  <button
    type="button"
    class="flex items-center gap-2 text-white p-2 rounded-full cursor-pointer btn btn-outline btn-orange"
    :class="{ 'hover-style': isLiked, 'orange-bg': isLiked }"
    @click="toggleLike"
  >
    <svg
      class="w-6 h-6"
      aria-hidden="true"
      xmlns="http://www.w3.org/2000/svg"
      fill="currentColor"
      viewBox="0 0 24 24"
    >
      <path d="M3 7H1a1 1 0 0 0-1 1v10a2 2 0 0 0 4 0V8a1 1 0 0 0-1-1Zm12.954 0H12l1.558-4.5a1.778 1.778 0 0 0-3.331-1.06A24.859 24.859 0 0 1 6 6.8v13.586h.114C8.223 21.969 11.015 23 13.6 23c1.4 0 1.592-.526 1.88-1.317l2.354-7A2 2 0 0 0 15.954 7Z" />
    </svg>
    <span>{{ isLiked ? '좋아요 취소' : '좋아요' }}</span>
  </button>
</template>

<script setup>
import { ref, defineProps, onMounted } from 'vue';
import { getLikeState, getArticleLikeRequest, getArticleLikeCancelRequest } from '@/api/common';

const { articleId } = defineProps(['articleId']);
const isLiked = ref(false);

onMounted(async () => {
  try {
    // 좋아요 상태를 가져오는 비동기 함수 호출
    const response = await getLikeState(articleId);
    // 서버에서 받아온 값으로 isLiked 상태 갱신
    console.log(response.data);
    isLiked.value = response.data;
  } catch (error) {
    console.error('좋아요 상태를 가져오는 데 실패했습니다:', error);
  }
});

// 좋아요 토글 함수
const toggleLike = async () => {
  try {
    if (isLiked.value) {
      // 이미 좋아요 상태인 경우, 좋아요 취소 요청
      await getArticleLikeCancelRequest(articleId);
    } else {
      // 좋아요 상태가 아닌 경우, 좋아요 요청
      await getArticleLikeRequest(articleId);
    }

    // 토글 후 isLiked 상태 갱신
    isLiked.value = !isLiked.value;
  } catch (error) {
    console.error('좋아요 토글에 실패했습니다:', error);
  }
};
</script>

<style scoped>
.btn {
  margin-right: 5px;
  padding: 5px 16px;
  min-width: 75px;
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
  color: #ff9800;
}

/* 좋아요 상태일 때의 스타일 */
.hover-style:hover {
  border-color: #ff9800;
  /* 다른 스타일 속성도 필요한 경우 추가 */
}

.orange-bg {
  background-color: #ff9800; /* 오렌지 배경 색 */
}
</style>
