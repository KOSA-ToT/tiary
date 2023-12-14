<template>
  <div>
    <!-- 커버 이미지 영역 -->
    <div v-if="article" class="h-64 bg-cover bg-center relative"
      :style="{ 'background-image': `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.8)), url('${article.headerImage}')`, 'background-color': showHeader ? 'rgba(169, 169, 169, 0.8)' : '' }">

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
          <span v-if="authStore.isLoggedIn">
            <span class="mx-2">•</span>
            <router-link :to="{ name: 'ArticleEdit', params: { id: articleId }}">수정</router-link>
            <span class="mx-2">•</span>
            <button @click="deleteArticle">삭제</button>
          </span>
        </div>
      </div>
      <!-- 헤더 영역 -->
      <nav v-if="showHeader" class="flex items-center justify-between p-4 text-white absolute top-0 w-full sticky"
        :style="{ 'background-color': headerBackgroundVisible ? 'rgba(169, 169, 169, 0.95)' : '' }"
        style="z-index: 1000;">

        <div>
          <router-link to="/">HOME LOGO</router-link>
        </div>

        <div class="meta text-right">
          <span class="mx-2">•</span>
          <!--로그인한 유저의 프로필이 뜨게 위치만 잡아둠-->
          <span>by {{ article.createdBy }}</span>
        </div>
      </nav>
    </div>

    <!-- 글 내용 -->
    <div v-if="article" class="max-w-2xl mx-auto  p-4 bg-white shadow-lg">
      <div class="content prose prose-sm" v-html="article.content || '내용 없음'"></div>
      <!-- 해시태그 영역 -->
      <div class="max-w-2xl mx-auto mt-4 p-4">
        <div class="flex flex-wrap justify-start items-end space-x-2">
          <span v-for="hashtag in article.hashtagList" :key="hashtag.id">
            <div class="bg-blue-500 text-white p-2 rounded-full mb-2">
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
import recommendations from '@/components/article/Recommendations.vue'
import comment from '@/components/comment/Comment.vue'
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';
import * as dateFormat from '@/utils/dateformat.js';
import { useAuthStore } from '@/stores/auth';
import router from '@/router';

const { articleId } = defineProps(['articleId']);
const article = ref(null);
const showHeader = ref(true);
const headerBackgroundVisible = ref(false);

// 로그인 상태 관리
const authStore = useAuthStore();

onMounted(async () => {
  try {
    const response = await axios.get(`http://localhost:8088/article/${articleId}`);
    article.value = response.data;
    authStore.login({
      token: 'token',
      currentUserName: '테스트계정'
    });
  } catch (error) {
    console.error('글을 불러오는 데 실패했습니다:', error);
  }
});

// editArticle 및 deleteArticle 메소드 정의

const deleteArticle = () => {
  // 삭제 로직 구현
  console.log('삭제 버튼 클릭');
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

// 스크롤 이벤트 핸들러 등록
onMounted(() => {
  window.addEventListener('scroll', handleHeaderBackground);

  // 컴포넌트가 제거될 때 이벤트 핸들러 제거
  onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleHeaderBackground);
  });
});

// 헤더 배경을 처리하는 함수
const handleHeaderBackground = () => {
  const scrollY = window.scrollY;

  // 헤더의 위치를 확인하고 배경을 표시할지 여부를 업데이트
  headerBackgroundVisible.value = scrollY > 250; // 48은 커버 이미지의 높이에 따라 조절할 수 있음
};
</script>

<style scoped>
/* Sticky 헤더를 위한 스타일 */
nav.sticky {
  position: fixed;
  top: 0;
  width: 100%;
  animation: fadeInDown 0.3s;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 추가된 스타일 */
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

.ico_by {
  color: #bfbfbf;
  font-family: Georgia;
  font-size: 12px;
  font-style: italic;
  height: 15px;
  margin-right: 4px;
  width: 15px;
}

/* 기타 Tailwind CSS 클래스를 필요에 따라 추가할 수 있습니다. */
</style>
