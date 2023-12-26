<template>
  <div>
    <Header :threshold="100"></Header>
    <div class="flex items-center">
      <section class="w-full pt-5 bg-center bg-cover py-15"
        style="background-image: url('https://picsum.photos/1440/190/?blur=1?random=1​');">
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
              <svg height="25px" width="25px" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg"
                class="mr-2 mt-6" v-if="ifWriter == true" xmlns:xlink="http://www.w3.org/1999/xlink"
                viewBox="0 0 392.699 392.699" xml:space="preserve" fill="#000000">
                <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                <g id="SVGRepo_iconCarrier">
                  <g>
                    <path style="fill:#FFFFFF;"
                      d="M370.888,170.455c-0.388,3.556-2.069,6.529-4.848,8.598l-84.234,61.22 c-5.495,3.556-10.408,2.651-15.063-1.164L153.611,125.914c-2.133-2.133-3.297-5.042-3.426-7.887h39.499 c6.012,0,10.925-4.848,10.925-10.925c0-6.012-4.848-10.925-10.925-10.925h-26.57l16.937-23.273h41.115 c6.012,0,10.925-4.848,10.925-10.925c0-6.012-4.848-10.925-10.925-10.925H195.89l17.778-24.501 c2.263-3.038,5.624-4.784,9.438-4.784c3.103,0,6.012,1.228,8.21,3.426L367.462,161.34 C369.918,163.797,371.082,167.029,370.888,170.455z">
                    </path>
                    <path style="fill:#FFFFFF;"
                      d="M137.579,273.308c-9.956,0-18.101-8.145-18.101-18.101s8.145-18.101,18.101-18.101 s18.101,8.145,18.101,18.101C155.68,265.162,147.535,273.308,137.579,273.308z">
                    </path>
                    <path style="fill:#FFFFFF;"
                      d="M242.694,245.898l-28.638,79.321L45.199,362.908l72.921-72.921 c5.689,3.168,12.347,5.042,19.394,5.042c22.044,0,39.887-17.842,39.887-39.887s-17.842-39.887-39.887-39.887 s-39.887,17.842-39.887,39.887c0,7.046,1.875,13.705,5.042,19.394l-72.921,72.921l37.689-168.857l79.127-28.832L242.694,245.898z">
                    </path>
                  </g>
                  <path style="fill:#FFC10D;"
                    d="M217.611,251.651L141.07,175.11l-54.885,20.04L58.646,318.819l44.154-44.154 c-3.168-5.689-5.042-12.347-5.042-19.394c0-22.044,17.842-39.887,39.887-39.887s39.887,17.842,39.887,39.887 c0,22.044-17.842,39.887-39.887,39.887c-7.046,0-13.705-1.875-19.394-5.042l-44.154,44.154l123.669-27.669L217.611,251.651z">
                  </path>
                  <path style="fill:#56ACE0;"
                    d="M343.543,168.387L224.27,49.179l-49.584,67.038l101.43,101.43L343.543,168.387z"></path>
                  <path style="fill:#194F82;"
                    d="M382.783,146.019L246.638,9.873c-6.335-6.335-14.675-9.826-23.661-9.826 c-10.667,0-20.816,5.172-27.022,13.77l-61.22,84.234c-7.434,10.214-8.339,23.273-3.103,34.069l-77.188,28.121 c-3.491,1.228-6.077,4.267-6.982,7.887L0.27,379.393c-0.84,3.62,0.323,7.434,2.909,10.149c2.457,2.651,5.883,3.556,10.149,2.909 l211.265-47.192c3.62-0.776,6.594-3.426,7.887-6.853l28.121-77.188c11.636,5.042,23.014,4.461,34.069-3.103l84.234-61.22 c7.887-5.818,12.929-14.675,13.705-24.436C393.32,162.568,389.765,152.936,382.783,146.019z M214.056,325.219L45.199,362.908 l72.921-72.921c5.689,3.168,12.347,5.042,19.394,5.042c22.044,0,39.887-17.842,39.887-39.887s-17.842-39.887-39.887-39.887 s-39.887,17.842-39.887,39.887c0,7.046,1.875,13.705,5.042,19.394l-72.921,72.921l37.689-168.857l79.127-28.832l96.129,96.129 L214.056,325.219z M119.478,255.207c0-9.956,8.145-18.101,18.101-18.101s18.101,8.145,18.101,18.101 c0,9.956-8.145,18.101-18.101,18.101S119.478,265.162,119.478,255.207z M366.04,179.053l-84.234,61.22 c-5.495,3.556-10.408,2.651-15.063-1.164L153.611,125.914c-2.133-2.133-3.297-5.042-3.426-7.887h39.499 c6.012,0,10.925-4.848,10.925-10.925c0-6.012-4.848-10.925-10.925-10.925h-26.57l16.937-23.273h41.115 c6.012,0,10.925-4.848,10.925-10.925c0-6.012-4.848-10.925-10.925-10.925H195.89l17.778-24.501c2.263-3.038,5.624-4.784,9.438-4.784 c3.103,0,6.012,1.228,8.21,3.426L367.462,161.34c2.457,2.457,3.62,5.689,3.426,9.115C370.5,174.011,368.819,176.985,366.04,179.053z ">
                  </path>
                </g>
              </svg>
              <h1 class="mt-6 mr-6 text-2xl font-bold text-white text-outline">{{ User.nickname.value }}</h1>
              <img class="w-24 h-24 mb-6 rounded-full shadow-lg"
                :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + User.userPicture.value" alt="">
            </div>
          </div>

          <!-- <p class="mb-12 text-xl">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra euismod odio,
				gravida pellentesque urna varius vitae.</p> -->
          <!-- <a href="#" class="px-12 py-4 text-white bg-indigo-500 rounded-full hover:bg-indigo-600">구독</a> -->
        </div>
      </section>
    </div>
    <div>
      <router-view :user="User" />
    </div>
    <Footer></Footer>
  </div>
</template>
<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import MyPageMenu from '@/components/myPage/myMenu.vue'
import MyProfile from '@/components/myPage/myProfile.vue'
import { storeToRefs } from 'pinia';
import api from '@/router/api';
import { onBeforeMount, onMounted, ref } from 'vue';
import axios from "axios";
import { getUser, writerConfirm, listSubscribers, listsubscribedWriter, subscribeUser } from '@/api/common';
import { useRoute } from 'vue-router';
const id = ref(null);
const route = useRoute();
const ifWriter = ref(false);
// const serverUrl = import.meta.env.VUE_APP_API_BASE_URL;
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
async function approval() {
  
  try {
    const response = await writerConfirm(id.value);
    console.log("writerConfirm : " + response.data);
    if (response.data === 'Accepted') {
      ifWriter.value = true;
    }
  } catch (error) {
    console.log("에러 : " + error + error.response);
  }
}
async function listSubscriber() {
  try {
    const subscriberResponse = await listSubscribers(id.value);
    subscriberList.value = subscriberResponse.data;
    subscriberListLength.value = subscriberList.value.length;
    console.log("subscriberListLength.value : " + subscriberListLength.value);

    const subscribedWriterResponse = await listsubscribedWriter(id.value);
    subscribedList.value = subscribedWriterResponse.data;
    subscribedListLength.value = subscribedList.value.length;
    console.log("subscribedListLength.value : " + subscribedListLength.value);
  }
  catch (error) {
    console.log(error);
  }
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
onMounted(() => {
  getUsers();
  listSubscriber();
  approval();
});
onBeforeMount(() => {
  id.value = route.params.id;
});
</script>
<style>
.text-outline {
  -webkit-text-stroke: 0.8px rgb(189, 116, 48);
  /* 외곽선 색상 및 두께를 조절할 수 있습니다. */
}
</style>