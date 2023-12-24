import { defineStore } from 'pinia';

export const useNoticeStore = defineStore('notice', {
  state: () => ({
    notices: []
  }),
  actions: {
    setNotices(newNotices) {
      this.notices = newNotices;
    }
  }
});