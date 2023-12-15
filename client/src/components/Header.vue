<!-- Header.vue -->
<template>
  <nav v-if="showHeader" class="flex items-center justify-between p-4 text-white absolute top-0 w-full sticky"
    :style="{ 'background-color': headerBackgroundVisible ? 'rgba(169, 169, 169, 0.95)' : '' }" style="z-index: 1000;">
    <div>
      <router-link to="/">홈 로고</router-link>
    </div>

    <div class="flex items-center">
      <button v-if="!isLoggedIn" @click="openModal('로그인')">로그인</button>&nbsp;
      <button v-if="!isLoggedIn" @click="openModal('회원가입')">회원가입</button>&nbsp;
      <div v-if="isLoggedIn" class="meta text-right">
        <span>{{ userVars.task }}</span>
        <router-link to="/article-create">글 작성</router-link>
        <button @click="logoutHeader">로그아웃</button>
      </div>
    </div>
  </nav>
  <UserModal v-if="userVars.isShowModal" v-model:task="userVars.task" v-model:isShowModal="userVars.isShowModal">
  </UserModal>
</template>

<script setup>
import { ref, watchEffect } from 'vue';
import { useAuthStore } from '@/stores/auth';
import UserModal from '@/components/UserModal.vue';

const showHeader = ref(true);
const headerBackgroundVisible = ref(true);
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
</script>
