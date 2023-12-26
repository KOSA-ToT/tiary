<template>
  <Header :threshold="50"></Header>
  <div class="min-h-screen">
    <div v-show="!isLoading">
      <div v-if="getArticle.length === 0" class="flex flex-col justify-center items-center h-full">
        <div class="text-center mt-4">
          <h2 class="text-3xl mt-20 mb-12 font-semibold">{{ categoryName }}</h2>
          <hr>
        </div>
        <img src="/images/loading_2.gif" class="mt-24" alt="Loading..." />
        게시물이 존재하지 않습니다 :(
      </div>
      <div v-else>
        <div class="text-center mt-4">
          <h2 class="text-3xl mt-20 mb-12 font-semibold">{{ categoryName }}</h2>
          <hr>
        </div>
        <div class="grid grid-cols-7 gap-1">
          <div class="col-start-2 col-end-5">
            <ul class="grid grid-cols-1 xl:grid-cols-1 gap-y-10 gap-x-6 items-start p-8 mt-12">
              <li v-for="item in getArticle" :key="item.id" class="relative flex flex-col sm:flex-row xl:flex-col 
      items-start
      p-4 transition-all duration-300 hover:border-orange-300 hover:shadow-md">

                <router-link :to="{ name: 'Post', params: { articleId: item.id } }" class="flex w-full">
                  <!-- 왼쪽 묶음 -->
                  <div class="flex flex-col w-full
                    justify-between">
                    <!-- 제목 -->
                    <h3 class="mb-1 text-slate-900 font-semibold dark:text-slate-200">
                      <span class="mb-1 block text-xl mb-3 leading-6 text-black">{{ item.title || 'No Title Available'
                      }}</span>
                    </h3>
                    <!-- 본문 -->
                    <div class="prose prose-slate prose-sm text-slate-600 dark:prose-dark mb-6 lines-clamp-3">
                      <p v-html="sanitizeHTML(item.content) || 'No Content Available'"></p>
                    </div>
                    <!-- 메타데이터 -->
                    <div>
                      <span class="ico_dot"></span>
                      <span class="publish_time">{{ item.createdAt ? dateFormat.formatCreatedAt(item.createdAt) :
                        'NoDateAvailable' }}</span>
                      <span class="ico_dot"></span>
                      <span class="ico_by">by</span>
                      <span class="name_txt">{{ item.createdBy }}</span>
                    </div>
                  </div>
                  <!-- 오른쪽 이미지 -->
                  <div class="ml-6">
                    <img v-if="item.imgPath && item.imgPath.length > 0" :src="getRandomImage(item.imgPath)" alt=""
                      class="shadow-md rounded-lg bg-slate-50 w-250 h-250 xl:w-[250px] xl:h-[250px] object-contain"
                      width="250" height="250" />
                  </div>
                </router-link>
                <hr class="my-4 border-gray-300" v-if="getArticle.indexOf(item) !== getArticle.length - 1">
              </li>
            </ul>
          </div>
          <div class="col-start-5 col-end-7 mt-24">
            <div class="flex justify-center gap-2 flex-wrap p-4">
              <span v-for="hashtag in getHashtag"
                class="bg-orange-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-50 hover:text-gray-0 hover:bg-orange-400">
                #{{ hashtag.hashtagName || 'No Hashtag Available' }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>

<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import { onMounted, ref } from 'vue';
import * as dateFormat from '@/utils/dateformat.js'
import { getArticleFromCategory, getArticleFromHashtag } from '@/api/common';

const { categoryName } = defineProps(['categoryName'])
const getArticle = ref([]);
const getHashtag = ref([]);

onMounted(() => {
  fetchList(0);
  fetchHashtag(0);
});

const isLoading = ref(true);

async function fetchList(e) {
  const response = getArticleFromCategory(categoryName)
  const data = (await response).data;
  getArticle.value = data.data;
  isLoading.value = false;
}

async function fetchHashtag(e) {
  const response = getArticleFromHashtag(categoryName)
  const data = (await response).data;
  getHashtag.value = data;
}

// HTML 태그 제외 함수
function sanitizeHTML(html) {
  const doc = new DOMParser().parseFromString(html, 'text/html');
  return doc.body.textContent || "";
}

// getRandomImage 함수 정의
function getRandomImage(imgPathArray) {
  // 랜덤한 이미지 선택
  const randomIndex = Math.floor(Math.random() * imgPathArray.length);
  return imgPathArray[randomIndex];
}

onMounted(() => {
  // 페이지가 전체적으로 렌더링되는 데 500ms의 대기 시간을 줌
  setTimeout(() => {
    // 이 시점에서 렌더링이 완료된 상태
    console.log('Page is fully rendered after 500ms');
  }, 1000);
});
</script>

<style scoped>
.body-size {
  max-width: 1200px;
}

.ico_dot {
  background-color: #aaa;
  display: inline-block;
  height: 2px;
  margin: 8px 6px 0;
  vertical-align: top;
  width: 2px;
}

.publish_time {
  color: #959595;
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

.name_txt {
  color: #959595;
  font-size: 12px;
  overflow: hidden;
  padding-top: 20px;
}

.thumbnails {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.lines-clamp-3 {
  display: -webkit-box;
  -webkit-box-orient: vertical;
  overflow: hidden;
  -webkit-line-clamp: 3;
}
</style>
