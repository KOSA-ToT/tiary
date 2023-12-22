
<template>
  <div v-if="relatedArticles.length > 0" class="max-w-2xl mx-auto mt-4 p-4">
    <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
      <a v-for="related in relatedArticles" :key="related.id" :href="'/article/' + related.articleId" class="sm:col-span-1">
        <div class="bg-white rounded-lg overflow-hidden shadow-md">
          <div>
            <img
              :src="getThumbnailImage(related.content)"
              alt=""
              style="object-fit: cover; object-position: center center;"
            />
          </div>
          <div class="p-4">
            <div class="font-bold text-xl mb-2 lines-clamp-2">{{ related.title }}</div>
            <div class="text-gray-700 text-base lines-clamp-2"><p v-html="clampLines(stripHTML(related.content), 1)"></p></div>
          </div>
        </div>
      </a>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { getRecommendationArticleRequest } from '@/api/common';

const { articleId } = defineProps(['articleId']);
const relatedArticles = ref([]);
const defaultImageArray = ["/images/cat_1.jpg", "/images/cat_2.jpg", "/images/dog.jpg"];

onMounted(() => {
  fetchData(); // 초기 로드
});

async function fetchData() {
  try {
    const response = await getRecommendationArticleRequest(articleId);
    relatedArticles.value = response.data.responseRelatedArticleDtoList;
  } catch (error) {
    console.error('글을 불러오는 데 실패했습니다:', error);
  }
}

function getThumbnailImage(content) {
  const match = content.match(/<img[^>]+src="([^">]+)"/);
  return match ? match[1] : getRandomDefaultImage();
}

function stripHTML(html) {
  // HTML 태그 제거 함수
  const doc = new DOMParser().parseFromString(html, 'text/html');
  return doc.body.textContent || '';
}

function clampLines(text, maxLines) {
  const lines = text.split('\n');
  if (lines.length <= maxLines) {
    return text;
  } else {
    return lines.slice(0, maxLines).join('\n');
  }
}

function getRandomDefaultImage() {
  const randomIndex = Math.floor(Math.random() * defaultImageArray.length);
  return defaultImageArray[randomIndex];
}
</script>

<style scoped>
.lines-clamp-2 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 2;
}
</style>
