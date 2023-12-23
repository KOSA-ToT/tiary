<template>
   <!-- drawer init and show -->
   <div id="sideBtn" @click="onSidebar" class="text-orange-300 hover:text-orange-400">
      <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" stroke-width="1.5"
         stroke="currentColor" class="w-8 h-8">
         <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
      </svg>
   </div>

   <!-- drawer component -->
   <div id="sideBar" :class="{ 'translate-x-0': isSideBar }"
      class="fixed top-0 left-0 z-40 w-64 h-screen p-4 overflow-y-auto transition-transform duration-300 ease-in-out -translate-x-full bg-white dark:bg-gray-800"
      tabindex="-1">
      <div v-if="authStore.isLoggedIn">
         <!-- Profile Section -->
         <div class="flex flex-col items-center mb-6">
            <!-- Add your profile picture here -->
            <a href="/"><img :src="getUserPictureUrl()" alt="Profile" class="w-16 h-16 rounded-full mb-2" /></a>
            <h5 class="text-lg font-semibold text-gray-800 dark:text-gray-200">{{ userNickname }}</h5>
         </div>
         <!-- Write and Apply Buttons -->
         <div class="flex justify-center">
            <a href="/article-create" class="btn btn-orange btn-outline">글쓰기</a>
            <button class="btn btn-orange btn-outline">작가 신청</button>
         </div>
         <!-- Divider -->
         <hr class="my-4 border-t border-gray-300 dark:border-gray-700">

         <!-- Navigation Links -->
         <ul class="space-y-2">
            <li v-for="link in links" :key="link.text">
               <!-- <a :href="link.to"
                  class="flex items-center p-3 text-gray-800 dark:text-white rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 group">
                  <img :src="link.iconClass" alt="icon" class="w-5 h-5 rounded-lg">
                  <span class="ms-3">{{ link.text }}</span>
               </a> -->
               <router-link :to="link.to"
                  class="flex items-center p-3 text-gray-800 dark:text-white rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 group">
                  <img :src="link.iconClass" alt="icon" class="w-5 h-5 rounded-lg">
                  <!-- <span
                     class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                     :class="link.iconClass"></span> -->
                  <span class="ms-3">{{ link.text }}</span>
               </router-link>
            </li>
         </ul>

         <!-- Divider -->
         <hr class="my-4 border-t border-gray-300 dark:border-gray-700">

         <div class="flex justify-center">
            <a :href="`/mypage/${userId}`" class="btn btn-orange btn-outline">마이페이지</a>
            <button @click="authStore.alertLogout()" class="btn btn-orange btn-outline">로그아웃</button>
         </div>
   </div>
   <div v-else class="mt-4 flex flex-col items-center">
      <img src="/images/person-who-invests.svg">
      <span class="my-2 text-black font-bold">누구나 작가가 될 수 있는 곳</span>
      <span class="mb-2 text-black font-bold text-lg">Tiary<span class="text-black">와 함께해요</span></span>
      <button type="button" class="text-white bg-gradient-to-br from-pink-500 to-orange-400 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-pink-200 dark:focus:ring-pink-800 font-medium rounded-2xl text-sm px-5 py-2.5 text-center me-2 mb-2">Tiary 시작하기 →</button>
   </div>
   </div>
</template>
<script setup>
import { ref, onMounted, onBeforeMount, watch } from 'vue';
import { useAuthStore } from '@/stores/auth';
import { getUserInfoReq } from '@/api/common';

const authStore = useAuthStore();
const isSideBar = ref(false);
const userNickname = ref('');
const userId = ref('');
const userPicture = ref(null);
let links = [];

// 사이드바 오픈 && 유저 정보 출력
async function onSidebar() {
   if (authStore.isLoggedIn) {
      try {
         const userInfo = await getUserInfo();
         userNickname.value = userInfo.nickname;
         userId.value = userInfo.id;
         userPicture.value = userInfo.picture;
         links = [
         { text: '작성한 글', to: `/mypage/post/${userId.value}`, iconClass: '/images/book.svg' },
         { text: '작성한 댓글', to: `/mypage/comment/${userId.value}`, iconClass: '/images/comment-dots.svg' },
         { text: '구독한 작가', to: `/mypage/subscriber/${userId.value}`, iconClass: '/images/heart.svg' },
      ];
      } catch (err) {
         console.log(err);
      }
   }
   isSideBar.value = !isSideBar.value;
}


// 사이드바 클릭 시, 유저 정보를 가져옴
async function getUserInfo() {
   try {
      const res = await getUserInfoReq();
      return res.data;
   } catch (err) {
   }
}

// 사용자 프로필 사진 경로를 동적으로 생성하는 계산된 속성
function getUserPictureUrl() {
   if (userPicture.value === null) {
      // 이미지가 없을 경우 로딩 이미지 경로
      return '/images/loading_1.gif';
   } else {
      // 사용자 프로필 사진이 있을 경우 경로 설정
      return import.meta.env.VITE_S3_URL + userPicture.value;
   }
}

onMounted(() => {
  // 사이드바 내부에서의 클릭이면, 사이드바를 닫지 않음
  // 사이드바 외부에서의 클릭이면, 사이드바를 닫음
  const sidebar = document.getElementById("sideBar");
  const sidebtn = document.getElementById("sideBtn");
  window.addEventListener("click", closeSidebar);

  // 사이드바 외부에서의 클릭 && 사이드바 전용 버튼이 아닌 경우(사이드바 오픈을 막지 않기 위함)
  function closeSidebar(event) {
    const isClickInsideSidebar = sidebar.contains(event.target);
    const isClickSidebtn = sidebtn.contains(event.target);
    if (!isClickInsideSidebar && !isClickSidebtn) {
      isSideBar.value = false;
    }
  }
});
</script>
<style scoped>
/* 추가된 스타일 */
.btn {
  margin-right: 5px;
  padding: 5px 16px;
  /* padding 수정 */
  min-width: 75px;
  /* 최소 너비 추가 */
  border: 1px solid orange;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn:hover {
  border-color: #ff9800;
}

.btn-outline {
  background-color: transparent;
  color: #ff9800;
}

.btn-outline:hover {
  background-color: #ff9800;
  color: #fff;
}

.btn-orange {
  background-color: transparent;
  color: #ff9800;
}
</style>
