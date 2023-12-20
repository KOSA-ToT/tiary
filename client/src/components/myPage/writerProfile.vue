<template>
  <div>
    <Header :threshold="100"></Header>
    <div class="flex items-center">
      <section class="w-full pt-5 bg-center bg-cover py-15"
        style="background-image: url('https://picsum.photos/1440/190/?blur=1?random=1');">

        <div class="container mx-auto text-center text-white">
          <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
            <div class="flex items-center justify-end px-12">
              <div class="max-w-screen-md text-lg leading-relaxed text-gray-700 lg:px-0">
                <div
                  class="flex px-6 py-3 mb-1 text-sm font-bold text-white uppercase transition-all duration-150 ease-linear bg-orange-500 bg-opacity-50 border border-orange-500 border-solid rounded-full outline-none mr-52 focus:outline-none">
                  <div class="mr-4">
                    <p class="mb-2">구독자</p>
                    <p>{{ subscriberListLength }}</p>
                  </div>
                  <div>
                    <p class="mb-2">구독 작가</p>
                    <p>{{ subscribedListLength }}</p>
                  </div>
                </div>
              </div>
              <div class="flex flex-col mb-4">
                <div class="mb-1">
                  <h1 class="mr-6 text-2xl font-bold text-white text-outline">{{ User.nickname.value }}</h1>
                </div>
                <div>
                  <button @click="subscribe(User.id.value)"
                    class="px-6 py-3 mb-1 mr-5 text-sm font-bold text-orange-500 uppercase transition-all duration-150 ease-linear bg-white border border-orange-500 border-solid rounded-full outline-none bg-opacity-40 hover:bg-orange-500 hover:text-white active:bg-orange-600 focus:outline-none">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                      stroke="currentColor" class="w-6 h-6">
                      <path stroke-linecap="round" stroke-linejoin="round"
                        d="M19 7.5v3m0 0v3m0-3h3m-3 0h-3m-2.25-4.125a3.375 3.375 0 11-6.75 0 3.375 3.375 0 016.75 0zM4 19.235v-.11a6.375 6.375 0 0112.75 0v.109A12.318 12.318 0 0110.374 21c-2.331 0-4.512-.645-6.374-1.766z" />
                    </svg>
                  </button>
                </div>
              </div>
              <img class="w-24 h-24 mb-6 rounded-full shadow-lg"
                :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + User.userPicture.value" alt="">
            </div>
          </div>
        </div>
      </section>
    </div>
    <div>
      <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
        <PostView :user="User" />
      </div>

    </div>
    <Footer></Footer>
  </div>
</template>
<script setup>
import PostView from '@/components/myPage/postList.vue'
import { onBeforeMount, onMounted, ref, watch } from 'vue';
import { getUser, listSubscribers, listsubscribedWriter, subscribeUser } from '@/api/common';
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import axios from "axios";
import { useRoute } from 'vue-router';
const id = ref(null);
const route = useRoute();

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
async function getUsers(props) {
  try {
    console.log("id.value : " + id.value);
    const response = await getUser(id.value);
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
async function subscribe(userId) {
  try {
    const response = await subscribeUser(userId);
    if (response.data === false) {
      alert('이미 구독한 사용자입니다!');
    }
    else {
      alert('구독했습니다!');
      window.location.reload();
      console.log("구독완료");
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      alert('자신은 구독할 수 없습니다.');
    }
    else if(error.response && error.response.status === 500){
      alert('로그인 해주세요!');
    }
    console.log("에러 : " + error);
  }
};
onMounted(() => {
  getUsers();
  listSubscriber();
});
watch(() => User.id.value, listSubscriber);
onBeforeMount(() => {
  id.value = route.params.id;
});
</script>
<style>
  .text-outline {
    -webkit-text-stroke: 1px rgb(189, 116, 48); /* 외곽선 색상 및 두께를 조절할 수 있습니다. */
  }
</style>