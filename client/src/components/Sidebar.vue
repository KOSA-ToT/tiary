<template>
   <!-- drawer init and show -->
   <div id="sideBtn" @click="onOffSidebar" class="text-orange-300 hover:text-orange-400">
      <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 24 24" stroke-width="1.5"
         stroke="currentColor" class="w-8 h-8">
         <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
      </svg>
   </div>

   <!-- drawer component -->
   <div id="sideBar" :class="{ 'translate-x-0': isSideBar }"
      class="fixed top-0 left-0 z-40 w-64 h-screen p-4 overflow-y-auto transition-transform duration-300 ease-in-out -translate-x-full bg-white dark:bg-gray-800"
      tabindex="-1">
      <!-- Profile Section -->
      <div class="flex flex-col items-center mb-6">
         <!-- Add your profile picture here -->
         <img src="/images/loading.gif" alt="Profile" class="w-16 h-16 rounded-full mb-2" />
         <h5 class="text-lg font-semibold text-gray-800 dark:text-gray-200">{{ authStore.currentUser }}</h5>
      </div>
      <!-- Write and Apply Buttons -->
      <div class="flex justify-center">
         <button class="btn btn-orange btn-outline">글쓰기</button>
         <button class="btn btn-orange btn-outline">작가 신청</button>
      </div>
      <!-- Divider -->
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700">

      <!-- Navigation Links -->
      <ul class="space-y-2">
         <li v-for="link in links" :key="link.text">
            <router-link :to="link.to"
               class="flex items-center p-3 text-gray-800 dark:text-white rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 group">
               <span
                  class="w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                  :class="link.iconClass"></span>
               <span class="ms-3">{{ link.text }}</span>
            </router-link>
         </li>
      </ul>

      <!-- Divider -->
      <hr class="my-4 border-t border-gray-300 dark:border-gray-700">

      <div class="flex justify-center">
         <button class="btn btn-orange btn-outline">마이페이지</button>
         <button class="btn btn-orange btn-outline">로그아웃</button>
      </div>
   </div>
</template>
 
<script setup>
import { ref, onMounted } from 'vue';
import { useAuthStore } from '@/stores/auth';

const isSideBar = ref(false);
const authStore = useAuthStore();

function onOffSidebar() {
   isSideBar.value = !isSideBar.value;
}

const links = [
   { text: 'Dashboard', to: '/dashboard', iconClass: 'icon-dashboard' },
   { text: 'Kanban', to: '/kanban', iconClass: 'icon-kanban' },
   { text: 'Inbox', to: '/inbox', iconClass: 'icon-inbox' },
   { text: 'Users', to: '/users', iconClass: 'icon-users' },
   { text: 'Products', to: '/products', iconClass: 'icon-products' },
   { text: 'Sign In', to: '/signin', iconClass: 'icon-signin' },
   { text: 'Sign Up', to: '/signup', iconClass: 'icon-signup' }
];

onMounted(() => {
   // 사이드바 내부에서의 클릭이면, 사이드바를 닫지 않음
   // 사이드바 외부에서의 클릭이면, 사이드바를 닫음
   const sidebar = document.getElementById('sideBar');
   const sidebtn = document.getElementById('sideBtn');
   window.addEventListener('click', closeSidebar);

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
/* Add your own styles for icons, etc. */
.icon-dashboard::before {
   content: url('/path/to/dashboard-icon.svg');
   /* Replace with your own dashboard icon */
}

.icon-kanban::before {
   content: url('/path/to/kanban-icon.svg');
   /* Replace with your own kanban icon */
}

.icon-inbox::before {
   content: url('/path/to/inbox-icon.svg');
   /* Replace with your own inbox icon */
}

.icon-users::before {
   content: url('/path/to/users-icon.svg');
   /* Replace with your own users icon */
}

.icon-products::before {
   content: url('/path/to/products-icon.svg');
   /* Replace with your own products icon */
}

.icon-signin::before {
   content: url('/path/to/signin-icon.svg');
   /* Replace with your own sign-in icon */
}

.icon-signup::before {
   content: url('/path/to/signup-icon.svg');
   /* Replace with your own sign-up icon */
}

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
 