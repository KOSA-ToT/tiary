<template>
    <SubscribeTab @subscribe="listSubscriber"></SubscribeTab>
    <div class="flex flex-col h-full pt-4">
        <div v-if="subscriberList"
            class="relative flex flex-col items-center w-[576px] mx-auto p-4 bg-white dark:border-[#ffffff33] dark:!bg-navy-800 dark:text-white dark:shadow-none">
            <div class="flex items-center justify-between w-full rounded-t-3xl">
            </div>
            <div v-for="subscriber in subscriberList"
                class="flex h-full w-full items-start justify-between rounded-md border-[1px] border-[transparent] dark:hover:border-white/20 bg-white px-3 py-[20px] transition-all duration-150 hover:border-gray-200 dark:!bg-navy-800 dark:hover:!bg-navy-700">
                <div class="flex items-center gap-3">
                    <router-link :to="'/writer-page/' + subscriber.userId" v-if="store.dataFromChild == '구독자'" class="flex items-center gap-3">
                        <div class="flex items-center justify-center w-16 h-16">
                            <img class="w-full h-full rounded-full" v-if="subscriber.userPicture"
                                :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + subscriber.userPicture"
                                alt="" />
                            <img class="w-full h-full rounded-full" v-else
                                src="https://horizon-tailwind-react-corporate-7s21b54hb-horizon-ui.vercel.app/static/media/Nft1.0fea34cca5aed6cad72b.png"
                                alt="" />
                        </div>
                        <div class="flex flex-col">
                            <h5 class="text-base font-bold text-navy-700 dark:text-white">
                                {{ subscriber.nickname }}
                            </h5>
                        </div>
                    </router-link>
                    <router-link :to="'/writer-page/' + subscriber.writerId" v-if="store.dataFromChild == '구독작가'" class="flex items-center gap-3">
                        <div class="flex items-center justify-center w-16 h-16">
                            <img class="w-full h-full rounded-full" v-if="subscriber.userPicture"
                                :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + subscriber.userPicture"
                                alt="" />
                            <img class="w-full h-full rounded-full" v-else
                                src="https://horizon-tailwind-react-corporate-7s21b54hb-horizon-ui.vercel.app/static/media/Nft1.0fea34cca5aed6cad72b.png"
                                alt="" />
                        </div>
                        <div class="flex flex-col">
                            <h5 class="text-base font-bold text-navy-700 dark:text-white">
                                {{ subscriber.nickname }}
                            </h5>
                        </div>
                    </router-link>
                </div>
                <div class="flex items-center justify-center mt-3 text-navy-700 dark:text-white">
                    <button @click="subscribe(subscriber.userId)" :id="'subscriber-option-' + subscriber.userId"
                        v-if="store.dataFromChild == '구독자'"
                        class="flex linear rounded-[20px] bg-lightPrimary px-4 py-2 text-base font-semibold text-brand-500 transition duration-200 hover:bg-gray-100 active:bg-gray-200 dark:bg-white/5 dark:text-white dark:hover:bg-white/10 dark:active:bg-white/20">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M19 7.5v3m0 0v3m0-3h3m-3 0h-3m-2.25-4.125a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zM4 19.235v-.11a6.375 6.375 0 0112.75 0v.109A12.318 12.318 0 0110.374 21c-2.331 0-4.512-.645-6.374-1.766z" />
                        </svg>
                    </button>
                    <!-- <button @click="subscribe(subscriber.userId)"
                        class="flex linear rounded-[20px] bg-lightPrimary px-4 py-2 text-base font-semibold text-brand-500 transition duration-200 hover:bg-gray-100 active:bg-gray-200 dark:bg-white/5 dark:text-white dark:hover:bg-white/10 dark:active:bg-white/20">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                            stroke="currentColor" class="w-6 h-6">
                            <path stroke-linecap="round" stroke-linejoin="round"
                                d="M22 10.5h-6m-2.25-4.125a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zM4 19.235v-.11a6.375 6.375 0 0112.75 0v.109A12.318 12.318 0 0110.374 21c-2.331 0-4.512-.645-6.374-1.766z" />
                        </svg>
                    </button> -->
                </div>
            </div>
            <p>{{ msg }}</p>
        </div>

    </div>
</template>
<script setup>
import SubscribeTab from '@/components/myPage/subscribeTab.vue';
import { onBeforeMount, onMounted, ref, watch } from 'vue';
import axios from "axios";
import { appStore } from '@/stores/store';
import { storeToRefs } from 'pinia'
import { listSubscribers, listsubscribedWriter, subscribeUser } from '@/api/common';
const store = appStore()
const { dataFromChild, setDataFromChild } = store
const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
const subscriberList = ref([]);
const subscriberLists = ref([]);
const subscribedLists = ref([]);
const msg = ref("");
const showButton = ref({});
const firstShowButton = ref({});
const duplicateUsers = ref([]);
async function listSubscriber() {
    try {
        if (store.dataFromChild == '구독자') {
            const response = await listSubscribers(props.user.id.value);
            subscriberList.value = response.data;
            subscriberLists.value = response.data;
            console.log(subscriberList.value);
            console.log("구독자 불러오기 완료");
            if (subscriberList.value == "") {
                msg.value = "구독자가 없습니다.";
            }
            else {
                msg.value = "";
            }
        }
        else if (store.dataFromChild == '구독작가') {
            const response = await listsubscribedWriter(props.user.id.value);
            subscriberList.value = response.data;
            subscribedLists.value = response.data;
            console.log(subscriberList.value);
            console.log("구독 작가 불러오기 완료");
            if (subscriberList.value == "") {
                msg.value = "구독 작가가 없습니다.";
            } else {
                msg.value = "";
            }
        }
        // console.log(subscriberLists.value, subscribedLists.value);
        // findDuplicates(subscriberLists.value, subscribedLists.value);
    } catch (error) {
        console.log(error);
    }
}
// function findDuplicates(subscriberList, subscribedWriterList) {
//     // const duplicateUsers = [];
//     for (const subscriber of subscriberList) {
//         for (const subscribedWriter of subscribedWriterList) {
//             if (subscriber.id === subscribedWriter.id) {
//                 duplicateUsers.value.push(subscriber.id);
//                 // 중복된 사용자에 대한 추가 조치를 수행하려면 여기에 로직을 추가하세요.
//                 // console.log("subscriber id : "+subscriber.id);
//             }
//         }
//     }
// }
// const isDuplicate = (userId) => {
//     console.log(userId+duplicateUsers.value.includes(userId));
//     return duplicateUsers.value.includes(userId);
// };
async function subscribe(userId) {
    try {
        const response = await subscribeUser(userId);
        if (response.data === false) {
            alert('이미 구독한 사용자입니다!');
        }
        else {
            alert('구독했습니다!');
            console.log("구독완료");
        }

    } catch (error) {
        console.log("에러 : " + error);
    }
};
watch(() => store.dataFromChild, listSubscriber);
onMounted(() => {
    listSubscriber();
});
onBeforeMount(()=>{
	listSubscriber();
});
</script>