<!-- Header.vue -->
<template>
  <div>
    <nav v-if="showHeader" class="flex items-center justify-between p-4 text-white absolute top-0 w-full sticky"
      :style="{ 'background-color': headerBackgroundVisible ? 'rgba(255, 255, 255, 0.9)' : '', 'opacity': inSlider ? '0.8' : '1' }"
      style="z-index: 1000; transition: background-color 0.3s ease;">
     
      <!-- 왼쪽에 사이드바와 홈 로고 위치 -->
      <div class="flex items-center">
        <Sidebar></Sidebar>
        <router-link to="/">홈 로고</router-link>
      </div>

      <div class="flex items-center">
        <div v-if="isLoggedIn" class="meta text-right">
          <span>{{ userVars.task }}</span>
          <router-link to="/article-create">글 작성</router-link>
          <button @click="logoutHeader">로그아웃</button>
        </div>

        <div v-else>
          <button @click="openModal('로그인')">로그인</button>&nbsp;
          <button @click="openModal('회원가입')">회원가입</button>
        </div>
      </div>
    </nav>
    <UserModal v-if="userVars.isShowModal" v-model:task="userVars.task" v-model:isShowModal="userVars.isShowModal">
    </UserModal>
  </div>
</template>

<script setup>
import { ref, watchEffect, onMounted, onBeforeUnmount, defineProps } from 'vue';
import { useAuthStore } from '@/stores/auth';
import Sidebar from '@/components/Sidebar.vue';
import UserModal from '@/components/UserModal.vue';

const props = defineProps(['threshold']);
const showHeader = ref(true);
const headerBackgroundVisible = ref(false);
const inSlider = ref(false);
const authStore = useAuthStore();
const userVars = ref({
  task: '',
  isShowModal: false
});

const { isLoggedIn, logout, login } = authStore;

function openModal(tasks) {
  userVars.value.task = tasks;
  userVars.value.isShowModal = true;
}

function logoutHeader() {
  if (confirm("로그아웃 하시겠습니까?")) {
    localStorage.removeItem('Authorization');
    window.location.reload();
    logout();
  }
}

// 컴포넌트가 렌더링될 때마다 로그인 상태를 동적으로 감시
watchEffect(() => {
  const authorizationToken = localStorage.getItem('Authorization');
  if (authorizationToken) {
    login();
  }
});

// 스크롤 이벤트 핸들러 등록
onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  onBeforeUnmount(() => {
    window.removeEventListener('scroll', handleScroll);
  });
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
</style>

