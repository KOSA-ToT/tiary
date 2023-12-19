// src/store/auth.js
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: false,
    currentUser: null,
  }),
  actions: {
    login(currentUser) {
      this.isLoggedIn = true;
      this.currentUser = currentUser;
    },
    logout() {
      localStorage.removeItem('Authorization');
      this.isLoggedIn = false;
      this.currentUser = null;
    },
    alertLogout() {
      if (confirm('로그아웃 하시겠습니까?')) {
        this.logout();
        location.href = '/';
      }
    }
  },
});

