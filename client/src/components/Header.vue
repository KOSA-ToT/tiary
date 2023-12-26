<!-- Header.vue -->
<template>
  <div>
    <nav
      v-if="showHeader"
      class="flex items-center justify-between p-4 text-white absolute top-0 w-full sticky"
      :style="{
        'background-color': headerBackgroundVisible
          ? 'rgba(255, 255, 255, 0.8)'
          : '',
        opacity: inSlider ? '0.9' : '1',
      }"
      style="z-index: 1000; transition: background-color 0.3s ease"
    >
      <!-- 왼쪽에 사이드바와 홈 로고 위치 -->
      <div class="flex items-center">
        <div v-if="userRole === 'ADMIN' || userRole.value === 'ADMIN'">
          <AdminSidebar></AdminSidebar>
        </div>
        <div v-else>
          <Sidebar></Sidebar>
        </div>
        <router-link
          to="/"
          class="text-lg font-bold text-gray-800 dark:text-black"
        >
          <img src="/images/header_logo.png" class="h-auto max-h-full" />
        </router-link>
      </div>

      <div class="flex items-center space-x-4">
        <div
          v-if="authStore.isLoggedIn && userRole != 'ADMIN'"
          class="meta text-right space-x-4"
        >
          <span>{{ userVars.task }}</span>
          <button @click="articleCreate" class="btn btn-outline btn-orange">
            글 작성
          </button>
          <button @click="alertLogout" class="btn btn-outline btn-orange">
            로그아웃
          </button>
        </div>

        <div v-else-if="userRole != 'ADMIN'" class="space-x-4">
          <button
            @click="openModal('로그인')"
            class="btn btn-outline btn-orange"
          >
            로그인
          </button>
          <button
            @click="openModal('회원가입')"
            class="btn btn-outline btn-orange"
          >
            회원가입
          </button>
        </div>
      </div>
    </nav>
    <UserModal
      v-if="userVars.isShowModal"
      v-model:task="userVars.task"
      v-model:isShowModal="userVars.isShowModal"
    ></UserModal>
  </div>
</template>

<script setup>
import { ref, watchEffect, onMounted, onBeforeUnmount, defineProps } from "vue";
import { useAuthStore } from "@/stores/auth";
import AdminSidebar from "@/components/AdminSidebar.vue";
import Sidebar from "@/components/Sidebar.vue";
import UserModal from "@/components/UserModal.vue";
import router from "@/router/index.js";
import { userEmail } from "@/utils/jwtUtils";
import { getUserInfoReq } from "@/api/common";

const props = defineProps(["threshold"]);
const showHeader = ref(true);
const headerBackgroundVisible = ref(false);
const inSlider = ref(false);
const authStore = useAuthStore();
const userRole = ref("");

const userVars = ref({
  task: "",
  isShowModal: false,
});

const { alertLogout, login } = authStore;

async function getUserInfo() {
  try {
    const res = await getUserInfoReq();
    return res.data;
  } catch (err) {}
}

// 사이드바 관리
async function onSidebar() {
  try {
    const userInfo = await getUserInfo();
    userRole.value = userInfo.role;
  } catch (err) {
    console.log(err);
  }
}

function openModal(tasks) {
  userVars.value.task = tasks;
  userVars.value.isShowModal = true;
}
function articleCreate() {
  router.push("/article-create");
}

// 컴포넌트가 렌더링될 때마다 로그인 상태를 동적으로 감시
watchEffect(() => {
  const authorizationToken = localStorage.getItem("Authorization");
  if (authorizationToken) {
    login(userEmail(authorizationToken));
  }
});
async function ifAdmin(){
  const userInfo = await getUserInfo();
  userRole.value = userInfo.role;

}
// 스크롤 이벤트 핸들러 등록
onMounted(async () => {
  window.addEventListener("scroll", handleScroll);
  onBeforeUnmount(() => {
    window.removeEventListener("scroll", handleScroll);
  });
  await onSidebar();
});

// 스크롤 이벤트 핸들러
function handleScroll() {
  handleHeaderBackground(props.threshold);
}

// 헤더 배경을 처리하는 함수
function handleHeaderBackground(threshold) {
  const scrollY = window.scrollY;
  headerBackgroundVisible.value = scrollY > threshold;
}
</script>

<style scoped>
/* Sticky 헤더를 위한 스타일 */
nav.sticky {
  position: fixed;
  top: 0;
  width: 100%;
  animation: fadeInDown 0.3s;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }

  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 추가된 스타일 */
.btn {
  margin-right: 5px;
  padding: 5px 16px; /* padding 수정 */
  min-width: 75px; /* 최소 너비 추가 */
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
