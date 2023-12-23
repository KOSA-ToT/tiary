<template>
<div class="w-full flex-1 mt-8">
    <div class="flex flex-col items-center mb-5">
        <!-- Google -->
        <a :href="oauthGoogle"
            class="w-full max-w-xs font-bold shadow-sm rounded-lg py-3 bg-white border text-gray-800 flex items-center justify-center transition-all duration-300 ease-in-out focus:outline-none hover:shadow focus:shadow-sm focus:shadow-outline">
            <div class="py-2 rounded-full">
                <svg class="w-4" viewBox="0 0 533.5 544.3">
                    <path
                        d="M533.5 278.4c0-18.5-1.5-37.1-4.7-55.3H272.1v104.8h147c-6.1 33.8-25.7 63.7-54.4 82.7v68h87.7c51.5-47.4 81.1-117.4 81.1-200.2z"
                        fill="#4285f4" />
                    <path
                        d="M272.1 544.3c73.4 0 135.3-24.1 180.4-65.7l-87.7-68c-24.4 16.6-55.9 26-92.6 26-71 0-131.2-47.9-152.8-112.3H28.9v70.1c46.2 91.9 140.3 149.9 243.2 149.9z"
                        fill="#34a853" />
                    <path
                        d="M119.3 324.3c-11.4-33.8-11.4-70.4 0-104.2V150H28.9c-38.6 76.9-38.6 167.5 0 244.4l90.4-70.1z"
                        fill="#fbbc04" />
                    <path
                        d="M272.1 107.7c38.8-.6 76.3 14 104.4 40.8l77.7-77.7C405 24.6 339.7-.8 272.1 0 169.2 0 75.1 58 28.9 150l90.4 70.1c21.5-64.5 81.8-112.4 152.8-112.4z"
                        fill="#ea4335" />
                </svg>
            </div>
            <span class="ml-3.5 font-bold">
                구글 {{ task }}
            </span>
        </a>
        <!-- Naver -->
        <a :href="oauthNaver"
            class="w-full max-w-xs font-bold shadow-sm rounded-lg py-3 naver text-gray-800 flex items-center justify-center transition-all duration-300 ease-in-out focus:outline-none hover:shadow focus:shadow-sm focus:shadow-outline mt-5">
            <div class="rounded-full">
                <svg version="1.1" id="naver" class="w-7" 
                xmlns="http://www.w3.org/2000/svg" 
                xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 200 200">
                    <polygon class="logo" fill="#ffffff" points="115.9,145.8 83.7,98.4 83.7,145.8 50,145.8 50,54.3 84.2,54.3 116.4,101.6 116.4,54.3    150,54.3 150,145.8 115.9,145.8"/>
                </svg>
            </div>
            <span class="ml-2 py-1.5 text-white font-bold">
                네이버 {{ task }}
            </span>
        </a>
    </div>

    <div class="mb-8 border-b text-center">
        <div
            class="leading-none px-2 inline-block text-sm text-gray-600 tracking-wide font-medium bg-white transform translate-y-1/2">
            또는 이메일로 {{ task }}
        </div>
    </div>

    <div class="mx-auto max-w-xs ">
        <form @submit.prevent="submitEmail(task)">
            <input
                @input="email = $event.target.value"
                required
                class="w-full px-8 py-4 rounded-lg font-medium bg-gray-100 border border-gray-200 placeholder-gray-400 text-sm focus:outline-none focus:border-gray-400 focus:bg-white"
                type="email" 
                placeholder="이메일을 입력해주세요" />
            <button 
                class="mt-5 tracking-wide font-semibold bg-orange-400 text-gray-100 w-full py-4 rounded-lg hover:bg-orange-500 transition-all duration-300 ease-in-out flex items-center justify-center focus:shadow-outline focus:outline-none">
                <svg class="w-6 h-6 -ml-2" fill="none" stroke="currentColor" stroke-width="2"
                    stroke-linecap="round" stroke-linejoin="round">
                    <path d="M16 21v-2a4 4 0 00-4-4H5a4 4 0 00-4 4v2" />
                    <circle cx="8.5" cy="7" r="4" />
                    <path d="M20 8v6M23 11h-6" />
                </svg>
                <span class="ml-3"> {{ task }} </span>
            </button> 
        </form>
        <p v-if="task==='회원가입'" class="mt-6 text-xs text-gray-600 text-center">
            요청시 이메일로 인증 요청이 발송되며,
        </p>
        <p v-if="task==='회원가입'" class="mt-6 text-xs text-gray-600 text-center">
            인증 후 회원가입 시
            <a href="#" class="border-b border-gray-500 border-dotted">
                개인정보 수집 
            </a>
            및
            <a href="#" class="border-b border-gray-500 border-dotted">
                이용약관
            </a>
            에 동의됩니다.
        </p>
    </div>
</div>

</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue'

import { emailAuthReq, emailDupCheckReq } from '@/api/common';

const props = defineProps(['task'])
const emits = defineEmits()

const oauthGoogle = import.meta.env.VITE_SERVER_URL + import.meta.env.VITE_OAUTH_GOOGLE;
const oauthNaver = import.meta.env.VITE_SERVER_URL + import.meta.env.VITE_OAUTH_NAVER;

const task = ref(props.task);
const email = ref('');

async function submitEmail(task){
  console.log(task);
  console.log(email.value);

  try {
    if(task === '회원가입') {
      try {
        const emailDupCheckResponse = await emailDupCheckReq(email.value);
          if(!confirm(emailDupCheckResponse.data + " 사용하시겠습니까?")) {
          return;
        }
      } catch (error) { // 이메일 중복
        alert(error.response.data)
        return;
      }
    }
    const emailDto = {
        email: email.value,
        task: props.task
    };
    emits('isLoading', true);
    const request = await emailAuthReq(emailDto);
    alert(request.data);
    emits('isLoading', false);
  } catch (error) {
    console.log("에러: ", error);
  }
  
}

</script>
<style scoped>
.naver {
  background-color:#03C75A
}
</style>
