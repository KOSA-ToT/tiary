<template>
    <div class="max-w-screen-md px-4 mx-auto text-lg leading-relaxed text-gray-700 lg:px-0">
            <p class="pb-6">
    <ul
        class="flex w-full text-sm font-semibold text-center text-gray-500 bg-gray-200 border-gray-200 dark:border-gray-700 dark:text-gray-400">
        <li class="flex-1">
            <a href="#" @click="toggleButtonSubscriber" :aria-current="isActiveSubscriber ? 'page' : null"
                class="block p-4 font-bold rounded-t-lg hover:text-gray-600 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-300 tab">구독자</a>
        </li>
        <li class="flex-1">
            <a href="#" @click="toggleButtonSubscribedWriter" :aria-current="isActiveSubscribedWriter ? 'page' : null"
                class="block p-4 font-bold rounded-t-lg hover:text-gray-600 hover:bg-gray-100 dark:hover:bg-gray-800 dark:hover:text-gray-300 tab">구독
                작가</a>
        </li>
    </ul>
    </p>
    </div>
</template>
<script setup>
import { onMounted, ref } from 'vue';
import { appStore } from '@/stores/store';
import { storeToRefs } from 'pinia'

const store = appStore();
const { dataFromChild, setDataFromChild } = store;
const isActiveSubscriber = ref(true);
const isActiveSubscribedWriter = ref(false);

const toggleButtonSubscriber = () => {
    isActiveSubscriber.value = true;
    isActiveSubscribedWriter.value=false;
    store.setDataFromChild('구독자');
    // emit('subscribe','구독자');
    console.log("구독자 클릭");
    console.log("store.dataFromChild : "+store.dataFromChild);
};
const toggleButtonSubscribedWriter = () => {
    isActiveSubscriber.value = false;
    isActiveSubscribedWriter.value=true;
    store.setDataFromChild('구독작가');
    // emit('subscribe','구독작가');
    console.log("구독 작가 클릭");
    console.log("store.dataFromChild : "+store.dataFromChild);
};
</script>
<style>
.tab[aria-current="page"] {
    background-color: #ffffff;
    /* 현재 페이지 버튼의 배경색을 변경 */
    color: #ff9100;
    /* 현재 페이지 버튼의 텍스트 색상을 변경 */
}</style>