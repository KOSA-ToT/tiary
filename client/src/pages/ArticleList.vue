<template>
  <div>
    <div class="grid grid-cols-9 gap-1">
      <div class="col-start-2 col-end-7">
        <ul class="grid grid-cols-1 xl:grid-cols-1 gap-y-10 gap-x-6 items-start p-8">
          <li v-for="item in getArticle" :key="item.id"
            class="relative flex flex-col sm:flex-row xl:flex-row items-start">
            <router-link :to="{ name: 'Post', params: { articleId: item.id } }">
              <img v-if="item.imageSrc" :src="item.imageSrc" alt=""
                class="mb-6 shadow-md rounded-lg bg-slate-50 w-full sm:w-[17rem] sm:mb-0 xl:mb-6 xl:w-35%" width="1216"
                height="640">
              <div class="order-1 sm:ml-6 xl:ml-15">
                <h3 class="mb-1 text-slate-900 font-semibold dark:text-slate-200">
                  <span class="mb-1 block text-xl mb-3 leading-6 text-black">{{ item.title || 'No Title Available'
                  }}</span>
                </h3>
                <div class="prose prose-slate prose-sm text-slate-600 dark:prose-dark">
                  <!-- 사용자 입력 HTML이 그대로 렌더링되지 않도록 v-html 디렉티브 사용 -->
                  <p class="mb-10" v-html="sanitizeHTML(item.content) || 'No Content Available'"></p>
                </div>
                <span>
                  <span class="ico_dot"></span>
                  <span class="publish_time">{{ item.createdAt ? dateFormat.formatCreatedAt(item.createdAt) : 'No Date Available' }}</span>
                  <span class="ico_dot"></span>
                  <span class="ico_by">by</span>
                  <span class="name_txt">{{ item.createdBy }}</span>
                </span>
              </div>
            </router-link>
          </li>
        </ul>
      </div>

      <div class="col-start-7 col-end-9">
        <div class="flex justify-center gap-2 flex-wrap p-4">
          <span v-for="hashtag in getHashtag"
            class="bg-gray-300 rounded-full px-3 py-1 text-sm font-semibold text-gray-600 hover:text-gray-800 hover:bg-gray-400">
            #{{ hashtag.hashtagName || 'No Hashtag Available' }}
          </span>
        </div>
      </div>
    </div>
  </div>
  <Footer />
</template>



<script setup>
import Footer from '@/components/Footer.vue';
import { onMounted, ref } from 'vue';
import axios from 'axios';
import * as dateFormat from '@/utils/dateformat.js'

import router from '@/router';

const { categoryCode } = defineProps(['categoryCode'])
const getArticle = ref([]);
const getHashtag = ref([]);

onMounted(() => {
  fetchList(0);
  fetchHashtag(0);
});

async function fetchList(e) {
  const apiUrl = 'http://localhost:8088/category?categoryCode=' + categoryCode
  const response = axios.get(apiUrl);
  const data = (await response).data;

  getArticle.value = data;
}

async function fetchHashtag(e) {
  const apiUrl = 'http://localhost:8088/hashtag?categoryCode=' + categoryCode

  const response = axios.get(apiUrl);
  const data = (await response).data;

  getHashtag.value = data;
}

// HTML 태그 제외 함수
function sanitizeHTML(html) {
  const doc = new DOMParser().parseFromString(html, 'text/html');
  return doc.body.textContent || "";
}
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
}</style>
