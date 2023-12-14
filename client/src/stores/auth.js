// src/store/auth.js
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: false,
    token: null,
    currentUser: null,
  }),
  actions: {
    login({ token, currentUser }) {
      this.isLoggedIn = true;
      this.token = token;
      this.currentUser = currentUser;
    },
    logout() {
      this.isLoggedIn = false;
      this.token = null;
      this.currentUser = null;
    },
  },
});

// 로그인 시 로직

// <!-- Login.vue -->
// <template>
//   <div>
//     <!-- 로그인 폼 등의 컴포넌트 내용 -->

//     <button @click="login">로그인</button>
//   </div>
// </template>

// <script setup>
// import { useAuthStore } from '@/store/auth';

// const authStore = useAuthStore();

// const login = async () => {
//   try {
//     // 로그인 API 호출 및 필요한 정보 획득
//     const response = await api.login(/* 로그인에 필요한 정보 */);
//     const { token, currentUser } = response.data;

//     // Pinia store의 login 메소드를 통해 로그인 상태 업데이트
//     authStore.login({ token, currentUser });

//     // 로그인 후 다른 동작 수행
//     // ...
//   } catch (error) {
//     console.error('로그인 실패', error);
//   }
// };
// </script>
