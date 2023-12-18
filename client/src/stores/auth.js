// src/store/auth.js
import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: false,
    // currentUser: null,
  }),
  actions: {
    login() {
      this.isLoggedIn = true;
      // this.currentUser = currentUser;
    },
    logout() {
      this.isLoggedIn = false;
      this.currentUser = null;
    },
  },
});

