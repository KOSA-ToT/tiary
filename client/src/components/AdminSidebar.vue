<template>
  <div
    id="sideBtn"
    @click="onSidebar"
    class="text-orange-300 hover:text-orange-400"
  >
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="currentColor"
      viewBox="0 0 24 24"
      stroke-width="1.5"
      stroke="currentColor"
      class="w-8 h-8"
    >
      <path
        stroke-linecap="round"
        stroke-linejoin="round"
        d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5"
      />
    </svg>
  </div>
  <div
    id="sideBar"
    :class="{ 'translate-x-0': isSideBar }"
    class="fixed top-0 left-0 z-40 w-64 h-screen p-4 overflow-y-auto transition-transform duration-300 ease-in-out -translate-x-full bg-stone-100 dark:bg-gray-800"
    tabindex="-1"
  >
    <div>
      <div class="flex flex-col items-center mb-6">
        <a href="/">
          <!-- 망치사진으로 변경하기 -->
          <img
            :src="getUserPictureUrl()"
            alt="Profile"
            class="w-16 h-16 rounded-full mb-2"
        /></a>
        <h5 class="text-lg font-semibold text-gray-800 dark:text-gray-200">
          Admin
        </h5>
      </div>
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700" />
      <ul class="space-y-2">
        <li v-for="link in links" :key="link.text">
          <router-link
            :to="link.to"
            class="flex items-center p-3 text-gray-800 dark:text-white rounded-lg hover:bg-gray-200 dark:hover:bg-gray-700 group"
          >
            <img :src="link.iconClass" alt="icon" class="w-5 h-5 rounded-lg" />
            <span class="ms-3">{{ link.text }}</span>
          </router-link>
        </li>
      </ul>
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700" />
      <div class="flex justify-center">
        <button
          @click="authStore.alertLogout()"
          class="btn btn-orange btn-outline"
        >
          로그아웃
        </button>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, onBeforeMount, watch } from "vue";
import { useAuthStore } from "@/stores/auth";
import { getUserInfoReq } from "@/api/common";

const authStore = useAuthStore();
const isSideBar = ref(false);
const userNickname = ref("");
const userId = ref("");
const userPicture = ref(null);
const userRole = ref(null);
let links = [];

// 사이드바 오픈 && 유저 정보 출력
async function onSidebar() {
  if (authStore.isLoggedIn) {
    try {
      const userInfo = await getUserInfo();
      userNickname.value = userInfo.nickname;
      userId.value = userInfo.id;
      userPicture.value = userInfo.picture;
      userRole.value = userInfo.role;
      links = [
        {
          text: "작가 관리",
          to: `/admin/writer-management`,
          iconClass: "/images/book.svg",
        },
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
  } catch (err) {}
}

// 사용자 프로필 사진 경로를 동적으로 생성하는 계산된 속성
function getUserPictureUrl() {
  if (userPicture.value === null) {
    // 이미지가 없을 경우 로딩 이미지 경로
    return "/images/loading_1.gif";
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
