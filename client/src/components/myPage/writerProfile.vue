<template>
    <div class="flex mx-64 bg-purple-100 sm:px-6 lg:px-8">
        <div class="">
            <p>구독자</p>
            <p>{{ subscriberListLength }}</p>
            <p>구독 작가</p>
            <p>{{ subscribedListLength }}</p>
        </div>
        <div class="pt-20 ml-auto">
            <h1 class="text-xl tracking-tight text-gray-900">
                {{ User.nickname.value }}
            </h1>
        </div>
        <div class="flex px-4 py-6 max-w-7xl sm:px-6 lg:px-8 lg:block md:block ">
            <img class="w-24 h-24 rounded-full shadow-lg"
                :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + User.userPicture.value" alt="">
        </div>
    </div>
    <button
        class="rounded-md bg-white px-2.5 ml-20 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">
        구독</button>
    <div class="flex mx-64 min-h-screen h-[calc(100%-1rem)] w-screen pt-10">
        <div>
            <PostView :user="User" />
        </div>
    </div>
</template>
<script setup>
import PostView from '@/components/myPage/postList.vue'
import { onMounted, ref, watch } from 'vue';
import { getUser } from '@/api/common';
import { listSubscribers, listsubscribedWriter } from '@/api/common';
const subscriberList = ref([]);
const subscribedList = ref([]);
const subscriberListLength = ref();
const subscribedListLength = ref();
const User = {
    id: ref(),
    nickname: ref(),
    email: ref(),
    role: ref(),
    userPicture: ref(),
    userStatus: ref()
}
async function getUsers() {
    try {
        const response = await getUser(2);
        const data = response.data;
        User.id.value = data.id;
        User.nickname.value = data.nickname;
        User.email.value = data.email;
        User.role.value = data.role;
        User.userPicture.value = data.userPicture;
        User.userStatus.value = data.userStatus;
        console.log(User.nickname.value);
        console.log("User.id.value : " + User.id.value);
    } catch (error) {
        console.log(error);
    }
}
async function listSubscriber() {
    try {
        const subscriberResponse = await listSubscribers(User.id.value);
        subscriberList.value = subscriberResponse.data;
        subscriberListLength.value = subscriberList.value.length;
        console.log("subscriberListLength.value : " + subscriberListLength.value);

        const subscribedWriterResponse = await listsubscribedWriter(User.id.value);
        subscribedList.value = subscribedWriterResponse.data;
        subscribedListLength.value = subscribedList.value.length;
        console.log("subscribedListLength.value : " + subscribedListLength.value);
    }
    catch (error) {
        console.log(error);
    }
}
onMounted(() => {
    getUsers();
    listSubscriber();
});
watch(() => User.id.value, listSubscriber);

</script>