<template>
  <div>
    <!-- 커버 이미지 영역 -->
    <div v-if="article" class="h-64 bg-cover bg-center relative overflow-hidden">
      <img :src="getRandomDefaultImage()" class="opacity-70 blur-sm">
      <div class="absolute bottom-0 left-0 right-0 text-white text-center p-4">
        <h1 class="text-3xl font-bold mb-5">{{ article.title || '제목 없음' }}</h1>

        <div class="mb-4">
          <span class="ico_by">At </span>
          <span class="mr-5">
            {{
              isWithinSixHours(article.createdAt)
              ? formatTimeDifference(article.createdAt)
              : dateFormat.formatCreatedAt(article.createdAt)
            }}
          </span>
          <span class="ico_by">By </span>
          <span>{{ article.createdBy }}</span>
          <!-- <span v-if="authStore.isLoggedIn"> -->

          <span v-if="shouldShowEditDeleteButtons">
            <span class="mx-2">•</span>
            <router-link :to="{ name: 'ArticleEdit', params: { id: articleId } }">수정</router-link>
            <span class="mx-2">•</span>
            <button @click="deleteArticle">삭제</button>
          </span>

        </div>
      </div>
      <!-- 헤더 영역 -->
      <Header :threshold="250"></Header>
    </div>

    <!-- 글 내용 -->

    <div v-if="article" class="max-w-2xl mx-auto p-4 bg-white shadow-lg overflow-hidden">
      <div class="content prose prose-sm max-w-700 mx-auto" v-html="article.content || '내용 없음'"></div>


      <!-- 해시태그 영역 -->
      <div class="max-w-2xl mx-auto mt-4 p-4">
        <div class="flex flex-wrap justify-start items-end space-x-2">
          <span v-for="hashtag in article.hashtagList" :key="hashtag.id">
            <div class="bg-amber-500 text-white p-2 rounded-full mb-2">
              #{{ hashtag.hashtagName }}
            </div>
          </span>
        </div>
      </div>
      <!-- 댓글 영역-->
      <comment></comment>

      <!-- 추천게시물 영역 -->
      <recommendations :articleId="articleId" />
    </div>
    <div v-else class="text-center mt-10">
      <p>Loading...</p>
    </div>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import recommendations from '@/components/article/Recommendations.vue';
import comment from '@/components/comment/Comment.vue';
import { ref, onMounted, computed } from 'vue';
import axios from 'axios';
import * as dateFormat from '@/utils/dateformat.js';
import router from '@/router';

import { deleteArticleRequest } from '@/api/common';
import { useAuthStore } from '@/stores/auth';

const { articleId } = defineProps(['articleId']);
const article = ref(null);

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:8088/article/${articleId}`);
    article.value = response.data;
    console.log(article);
  } catch (error) {
    console.error('글을 불러오는 데 실패했습니다:', error);
  }
});

// editArticle 및 deleteArticle 메소드 정의
const deleteArticle = () => {
  // 삭제 로직 구현
  try {
    const response = deleteArticleRequest(articleId)
      .then((response) => {
        if (response.status == 205) {
          alert("게시물이 삭제되었습니다.")
          router.go("/");
        }
      })
  } catch {
    alert("에러입니다.")
  }
};

// 시간 표시
const isWithinSixHours = (createdAt) => {
  const sixHoursInMillis = 6 * 60 * 60 * 1000;
  const currentTime = new Date().getTime();
  const articleTime = new Date(createdAt).getTime();
  const timeDifference = currentTime - articleTime;

  return timeDifference <= sixHoursInMillis;
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

// 로그인 상태 관리
const authStore = useAuthStore();

const shouldShowEditDeleteButtons = computed(() => {
  // 사용자가 로그인했고, 현재 사용자와 게시물 작성자가 동일한 경우에만 표시
  return authStore.isLoggedIn && authStore.currentUser === article.value.email;
});

const defaultImageArray=[
  '/images/cover/travle_1.jpeg',
  '/images/cover/travle_2.jpeg',
  '/images/cover/travle_3.jpeg'
]
function getRandomDefaultImage() {
  const randomIndex = Math.floor(Math.random() * defaultImageArray.length);
  return defaultImageArray[randomIndex];
}
</script>
<style scoped>
/* 추가된 스타일 */
.bg-cover img {
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.bg-blue-500 {
  background-color: #3490dc;
}

.text-white {
  color: white;
}

.p-2 {
  padding: 0.5rem;
}

.rounded-full {
  border-radius: 999px;
}

.mb-2 {
  margin-bottom: 0.5rem;
}

.text-stroke {
  text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000;
}
</style>
