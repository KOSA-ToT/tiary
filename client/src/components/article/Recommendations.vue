<template>
  <div v-if="relatedArticles.length > 0" class="max-w-2xl mx-auto mt-4 p-4">
    <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
      <a v-for="related in relatedArticles" :key="related.id" :href="'/article/' + related.articleId" class="sm:col-span-1">
        <div class="bg-white rounded-lg overflow-hidden shadow-md">
          <div v-if="related.imageUrl" class="h-48 bg-cover bg-center" :style="{ 'background-image': `url('${related.imageUrl}')` }"></div>
          <div v-else class="h-48 bg-orange-500"></div>
          <div class="p-4">
            <div class="font-bold text-xl mb-2 lines-clamp-2">{{ related.title }}</div>
            <p class="text-gray-700 text-base lines-clamp-2" v-html="sanitizeHtml(related.content)"></p>
          </div>
        </div>
      </a>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const { articleId } = defineProps(['articleId']);
const relatedArticles = ref([]);

const fetchData = async () => {
  try {
    const response = await axios.get(`http://localhost:8089/batch/recommendations/${articleId}`);
    relatedArticles.value = response.data.responseRelatedArticleDtoList;
  } catch (error) {
    console.error('글을 불러오는 데 실패했습니다:', error);
  }
};

onMounted(() => {
  fetchData(); // 초기 로드
});

const sanitizeHtml = (html) => {
  const parser = new DOMParser();
  const doc = parser.parseFromString(html, 'text/html');
  
  // 텍스트 노드만 추출
  const textNodes = [...doc.body.childNodes].filter(node => node.nodeType === Node.TEXT_NODE);
  
  // 추출한 텍스트 노드들을 합쳐서 반환
  return textNodes.map(node => node.nodeValue).join(' ');
};
</script>

<style scoped>
/* CSS 파일이나 컴포넌트 내에서 사용 가능 */
.lines-clamp-2 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 2;
}
</style>
