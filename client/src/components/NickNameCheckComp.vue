<template>
  <div class="mx-auto max-w-xs ">
    <div class="mt-8 mb-5 font-bold">닉네임 중복확인</div>
    <form @submit.prevent="">
      <input v-model.trim="nick" required
        class="w-full px-8 py-4 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-400 text-sm focus:outline-none focus:border-gray-400 focus:bg-white"
        type="text" placeholder="닉네임을 입력해주세요" />
        <p v-if="nick.length < 2" class="text-xs mt-2">닉네임은 2글자 이상이어야 합니다.</p>
      <button @click.prevent="checkDupNick()"
        :disabled="nick.length < 2"
        class="mt-5 tracking-wide font-semibold bg-orange-400 text-gray-100 w-full py-4 rounded-lg hover:bg-orange-500 transition-all duration-300 ease-in-out flex items-center justify-center focus:shadow-outline focus:outline-none">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
          class="w-6 h-6">
          <path stroke-linecap="round" stroke-linejoin="round" d="M4.5 12.75l6 6 9-13.5" />
        </svg>
        <span class="ml-2">중복확인</span>
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { loginReq, nickDupCheckReq, signupReq } from '@/api/common';
import router from '@/router';
import { useAuthStore } from '@/stores/auth';

const nick = ref('');
const email = ref(router.currentRoute.value.params.email);
const store = useAuthStore();

async function checkDupNick() {
  console.log(nick.value);
  try {
    const response = await nickDupCheckReq(nick.value);

    alert(response.data);
    // 확인필요
    if(confirm(nick.value + "(으)로 가입하시겠습니까?")) {
      const signupDto = {
        email: email.value,
        nickname: nick.value,
      };

      try {
        const signupResponse = await signupReq(signupDto);
        alert(signupResponse.data);
        try {
          const loginDto = { email: email.value };
          const loginResponse = await loginReq(loginDto);
          localStorage.setItem('Authorization', loginResponse.headers.authorization);
          router.push('/');
        } catch (error) {
          console.log(error);
        }
      } catch (error) {
        // 이미 가입 되었을때
        alert(error.response.data.message);
      }
      
    }
  } catch (error) {
    alert(error.response.data);
  }
}

</script>