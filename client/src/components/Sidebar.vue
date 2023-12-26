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
    class="fixed top-0 left-0 z-40 w-64 h-screen p-4 overflow-y-auto transition-transform duration-300 ease-in-out -translate-x-full bg-stone-50 dark:bg-gray-800"
    tabindex="-1">
    <div v-if="authStore.isLoggedIn">
      <!-- Profile Section -->
      <div class="flex flex-col items-center mb-6">
        <!-- Add your profile picture here -->
        <a href="/"><img :src="getUserPictureUrl()" alt="Profile" class="w-16 h-16 rounded-full mb-2" /></a>
        <div class="flex grid-cols-2">
          <svg height="21px" width="21px" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" class="mr-1 mt-1"
            v-if="ifWriter === true" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 392.699 392.699"
            xml:space="preserve" fill="#000000">
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
          <h5 class="text-lg font-semibold text-gray-800 dark:text-gray-200">
            {{ userNickname }}
          </h5>
        </div>
      </div>
      <!-- Write and Apply Buttons -->
      <div class="flex justify-center">
        <a href="/article-create" class="btn btn-orange btn-outline">글쓰기</a>
        <button @click="writerApproval()" v-if="ifWriter == false" class="btn btn-orange btn-outline">
          {{ writerMsg }}
        </button>
      </div>
      <!-- Divider -->
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700" />

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
            <img :src="link.iconClass" alt="icon" class="w-5 h-5 rounded-lg" />
            <!-- <span
                     class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                     :class="link.iconClass"></span> -->
            <span class="ms-3">{{ link.text }}</span>
          </router-link>
        </li>
      </ul>

      <!-- Divider -->
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700" />

      <div class="flex justify-center">
        <a :href="`/mypage/${userId}`" class="btn btn-orange btn-outline">마이페이지</a>
        <button @click="authStore.alertLogout()" class="btn btn-orange btn-outline">
          로그아웃
        </button>
      </div>
    </div>
    <div v-else class="mt-4 flex flex-col items-center">
      <img src="/images/person-who-invests.svg" />
      <span class="my-2 text-black font-bold">누구나 작가가 될 수 있는 곳</span>
      <span class="mb-2 text-black font-bold text-lg">Tiary<span class="text-black">와 함께해요</span></span>
      <button type="button"
        class="text-white bg-gradient-to-br from-pink-500 to-orange-400 hover:bg-gradient-to-bl focus:ring-4 focus:outline-none focus:ring-pink-200 dark:focus:ring-pink-800 font-medium rounded-2xl text-sm px-5 py-2.5 text-center me-2 mb-2">
        Tiary 시작하기 →
      </button>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted, onBeforeMount, watch } from "vue";
import { useAuthStore } from "@/stores/auth";
import { getUserInfoReq, writerApprove, writerConfirm } from "@/api/common";

const authStore = useAuthStore();
const isSideBar = ref(false);
const userNickname = ref("");
const userId = ref("");
const userPicture = ref(null);
const userRole = ref(null);
const writerMsg = ref("작가신청");
const ifWriter = ref(false);
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
      try {
        const response = await writerConfirm(userId.value);
        console.log("writerConfirm : " + response.data);
        if (response.data === 'Approving') {
          writerMsg.value = "작가신청중";
        }
        else if (response.data === 'Accepted') {
          writerMsg.value = "작가됨";
          ifWriter.value = true;
        }
        else if (response.data === 'Rejected') {
          writerMsg.value = "작가신청";
        }
      } catch (error) {
        console.log("에러 : " + error + error.response);
      }
      links = [
        {
          text: "작성한 글",
          to: `/mypage/post/${userId.value}`,
          iconClass: "/images/book.svg",
        },
        {
          text: "작성한 댓글",
          to: `/mypage/comment/${userId.value}`,
          iconClass: "/images/comment-dots.svg",
        },
        {
          text: "구독한 작가",
          to: `/mypage/subscriber/${userId.value}`,
          iconClass: "/images/heart.svg",
        },
        {
          text: "구독한 글",
          to: `/mypage/my-subscribe/${userId.value}`,
          iconClass: "/images/star.svg",
        },
      ];
    } catch (err) {
      console.log(err);
    }
  }
  isSideBar.value = !isSideBar.value;
}
async function writerApproval() {
  try {
    const response = await writerApprove();
    writerMsg.value = "작가 신청중";
    console.log("작가신청 완료");
  } catch (error) {
    console.log("에러 : " + error);
  }
};
async function writerConfirming() {
  try {
    const response = await writerConfirm(userId.value);
    console.log("writerConfirm : " + response.data);
  } catch (error) {
    console.log("에러 : " + error + error.response);
  }
};
// 사이드바 클릭 시, 유저 정보를 가져옴
async function getUserInfo() {
  try {
    const res = await getUserInfoReq();
    return res.data;
  } catch (err) { }
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
  //   writerConfirming();
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
