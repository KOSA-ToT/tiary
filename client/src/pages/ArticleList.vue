

<template>
  <div>
    <div class="grid grid-cols-9 gap-1">
      <div class="col-start-2 col-end-7">
        <ul class="grid grid-cols-1 xl:grid-cols-1 gap-y-10 gap-x-6 items-start p-8">
          <li v-for="item in getArticle" class="relative flex flex-col sm:flex-row xl:flex-row items-start">
            <div class="order-1 sm:ml-6 xl:ml-15">
              <h3 class="mb-1 text-slate-900 font-semibold dark:text-slate-200">
                <span class="mb-1 block text-sm leading-6 text-indigo-500">Headless UI</span>{{ item.title }}
              </h3>
              <div class="prose prose-slate prose-sm text-slate-600 dark:prose-dark">
                <p>{{item.content}}</p>
              </div><a
                class="group inline-flex items-center h-9 rounded-full text-sm font-semibold whitespace-nowrap px-3 focus:outline-none focus:ring-2 bg-slate-100 text-slate-700 hover:bg-slate-200 hover:text-slate-900 focus:ring-slate-500 dark:bg-slate-700 dark:text-slate-100 dark:hover:bg-slate-600 dark:hover:text-white dark:focus:ring-slate-500 mt-6"
                href="https://headlessui.dev">Learn
                more<span class="sr-only">, Completely unstyled, fully accessible UI components</span><svg
                  class="overflow-visible ml-3 text-slate-300 group-hover:text-slate-400 dark:text-slate-500 dark:group-hover:text-slate-400"
                  width="3" height="6" viewBox="0 0 3 6" fill="none" stroke="currentColor" stroke-width="2"
                  stroke-linecap="round" stroke-linejoin="round">
                  <path d="M0 0L3 3L0 6"></path>
                </svg></a>
            </div><img src="https://tailwindcss.com/_next/static/media/headlessui@75.c1d50bc1.jpg" alt=""
              class="mb-6 shadow-md rounded-lg bg-slate-50 w-full sm:w-[17rem] sm:mb-0 xl:mb-6 xl:w-35%" width="1216"
              height="640">
          </li>
        </ul>
      </div>

      <div class="col-start-7 col-end-9">
        <div class="flex justify-center gap-2 flex-wrap p-4">
          <span v-for="hashtag in getHashtag" class="bg-gray-100 rounded-full px-3 py-1 text-sm font-semibold text-gray-600">#{{ hashtag.hashtagName }}</span>
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
import router from '@/router';


const { categoryCode } = defineProps(['categoryCode'])
const getArticle = ref([]);
const getHashtag = ref([]);


onMounted(() => {
  fetchList(0);
  fetchHashtag(0);
});

async function fetchList(e){
  const apiUrl = 'http://localhost:8088/category?categoryCode='+categoryCode
  const response = axios.get(apiUrl);
  const data = (await response).data;
  
  getArticle.value = data;
}

async function fetchHashtag(e){
  const apiUrl = 'http://localhost:8088/hashtag'

  const response = axios.get(apiUrl);
  const data = (await response).data;
  
  getHashtag.value = data;
}

</script>

<style scoped>
.body-size {
  max-width: 1200px;
}
</style>
