<template>
    <SubscribeTab @subscribe="listSubscriber"></SubscribeTab>
    <div class="flex flex-col h-[100vh] pt-4">
        <div
            class="relative flex flex-col items-center rounded-[10px] border-[1px] border-gray-200 w-[576px] mx-auto p-4 bg-white bg-clip-border shadow-md shadow-[#F3F3F3] dark:border-[#ffffff33] dark:!bg-navy-800 dark:text-white dark:shadow-none">
            <div class="flex items-center justify-between w-full p-3 rounded-t-3xl">
            </div>
            <div v-for="subscriber in subscriberList"
                class="flex h-full w-full items-start justify-between rounded-md border-[1px] border-[transparent] dark:hover:border-white/20 bg-white px-3 py-[20px] transition-all duration-150 hover:border-gray-200 dark:!bg-navy-800 dark:hover:!bg-navy-700">
                <div class="flex items-center gap-3">
                    <div class="flex items-center justify-center w-16 h-16">
                        <img class="w-full h-full rounded-full"
                            src="https://horizon-tailwind-react-corporate-7s21b54hb-horizon-ui.vercel.app/static/media/Nft1.0fea34cca5aed6cad72b.png"
                            alt="" />
                    </div>
                    <div class="flex flex-col">
                        <h5 class="text-base font-bold text-navy-700 dark:text-white">
                            {{ subscriber.nickname }}
                        </h5>
                    </div>
                </div>
                <div class="flex items-center justify-center mt-3 text-navy-700 dark:text-white">
                    <button @click="subscribe(subscriber.userId)"
                        class="flex linear rounded-[20px] bg-lightPrimary px-4 py-2 text-base font-semibold text-brand-500 transition duration-200 hover:bg-gray-100 active:bg-gray-200 dark:bg-white/5 dark:text-white dark:hover:bg-white/10 dark:active:bg-white/20">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M19 7.5v3m0 0v3m0-3h3m-3 0h-3m-2.25-4.125a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zM4 19.235v-.11a6.375 6.375 0 0112.75 0v.109A12.318 12.318 0 0110.374 21c-2.331 0-4.512-.645-6.374-1.766z" />
                        </svg>

                    </button>

                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import SubscribeTab from '@/components/myPage/subscribeTab.vue';
import { onMounted, ref,watch } from 'vue';
import axios from "axios";
import { appStore } from '@/stores/store';
import { storeToRefs } from 'pinia'
const store = appStore()
const { dataFromChild, setDataFromChild } = store
const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
const subscriberList = ref([]);
const listSubscriber = async () => {
    if(store.dataFromChild=='구독자'){
        await axios.get('http://localhost:8088/users/1/subscriber')
        .then(response => {
            // 성공 처리
            subscriberList.value = response.data;
            console.log(subscriberList.value);
            console.log("구독자 불러오기 완료");
        })
        .catch(error => {
            // 에러 처리
            console.log(error);
        });
    }
    else if(store.dataFromChild=='구독작가'){
        await axios.get('http://localhost:8088/users/1/subscribedWriter')
        .then(response => {
            // 성공 처리
            subscriberList.value = response.data;
            console.log(subscriberList.value);
            console.log("구독 작가 불러오기 완료");
        })
        .catch(error => {
            // 에러 처리
            console.log(error);
        });
    }
    
};
const subscribe = async (userId) => {
    await axios.get(`http://localhost:8088/users/${userId}/subscribe`)
        .then(response => {
            if (response.status === 201) {
                console.log("구독완료");
            }
        })
        .catch(error => {
            // 에러 처리
            console.log(error);
        });
};
watch(() => store.dataFromChild, listSubscriber);
onMounted(() => {
	listSubscriber();
});
</script>