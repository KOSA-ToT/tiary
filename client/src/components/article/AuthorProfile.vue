<template>
  <section class="bg-gray-100 mt-10 p-4">
    <div class="w-full md:w-full md:mx-auto flex flex-col md:flex-row items-center justify-start text-center">
      <img
        class="inline-flex object-cover border-4 border-orange-600 rounded-full shadow-[5px_5px_0_0_rgba(0,0,0,1)] shadow-orange-600/100 dark:shadow-orange-700/100 bg-orange-50 h-24 w-24 !h-32 !w-32 mb-4 md:mb-0 ml-0 md:mr-5"
        :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + user.userPicture" alt="">
      <div class="flex flex-col">
        <div class="md:text-justify mb-3">
          <div class="flex flex-col mb-5">

            <div class="flex items-center">
              <p class="text-gray-900 font-bold text-xl">
                <router-link :to="'/writer-page/' + userId">
                  {{ user.nickname }}
                </router-link>
              </p>
              <button @click="subscribe(userId)"
                class="px-3 py-1  ml-3 text-sm font-bold text-orange-500 uppercase transition-all duration-150 ease-linear bg-white border border-orange-500 border-solid rounded-full outline-none bg-opacity-40 hover:bg-orange-500 hover:text-white active:bg-orange-600 focus:outline-none">
                구독
              </button>
              <button @click="donate()"
                class="px-3 py-1  ml-1 text-sm font-bold text-orange-500 uppercase transition-all duration-150 ease-linear bg-white border border-orange-500 border-solid rounded-full outline-none bg-opacity-40 hover:bg-orange-500 hover:text-white active:bg-orange-600 focus:outline-none">
                응원하기
              </button>
            </div>

            <ul class="mt-2 flex flex-row items-center justify-center md:justify-start ">
              <li class="mr-5">
                <a href="" target="_blank" aria-label="GitHub">
                  <svg class="h-6 text-gray-700 hover:text-orange-300" fill="currentColor" role="img" viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg">
                    <title>GitHub</title>
                    <path
                      d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12">
                    </path>
                  </svg>
                </a>
              </li>

              <li>
                <a href="" target="_blank" aria-label="Twitter">
                  <svg class="h-6 text-gray-700 hover:text-orange-300" fill="currentColor" role="img" viewBox="0 0 24 24"
                    xmlns="http://www.w3.org/2000/svg">
                    <title>Twitter</title>
                    <path
                      d="M23.953 4.57a10 10 0 01-2.825.775 4.958 4.958 0 002.163-2.723c-.951.555-2.005.959-3.127 1.184a4.92 4.92 0 00-8.384 4.482C7.69 8.095 4.067 6.13 1.64 3.162a4.822 4.822 0 00-.666 2.475c0 1.71.87 3.213 2.188 4.096a4.904 4.904 0 01-2.228-.616v.06a4.923 4.923 0 003.946 4.827 4.996 4.996 0 01-2.212.085 4.936 4.936 0 004.604 3.417 9.867 9.867 0 01-6.102 2.105c-.39 0-.779-.023-1.17-.067a13.995 13.995 0 007.557 2.209c9.053 0 13.998-7.496 13.998-13.985 0-.21 0-.42-.015-.63A9.935 9.935 0 0024 4.59z">
                    </path>
                  </svg>
                </a>
              </li>
            </ul>
          </div>

          <p class="text-gray-500 font-semibold text-left md:text-left">
            자기소개가 없어서 비워뒀지만 여기는 자기소개가 들어올 자리입니다 :)
          </p>
        </div>
      </div>
    </div>
    
  </section>
</template>
<script setup>
import { onMounted, ref, defineProps, defineEmits } from 'vue';
import { getUser, subscribeUser } from '@/api/common';

const { userId } = defineProps(['userId']);
const user = ref("");
const donateBtn = ref(false);
const emits = defineEmits();

onMounted(async () => {
  try {
    const response = await getUser(userId)
    user.value = response.data;
    console.log(user.value);
  } catch (error) {
    console.error("글을 불러오는 데 실패했습니다:", error);
  }
});

async function subscribe(userId) {
  try {
    const response = await subscribeUser(userId);
    if (response.data === false) {
      alert('이미 구독한 사용자입니다!');
    }
    else {
      alert('구독했습니다!');
      window.location.reload();
      console.log("구독완료");
    }
  } catch (error) {
    if (error.response && error.response.status === 409) {
      alert('자신은 구독할 수 없습니다.');
    }
    else if (error.response && error.response.status === 500) {
      alert('로그인 해주세요!');
    }
    console.log("에러 : " + error);
  }
};

// 응원하기
function donate() {
  donateBtn.value = true;
  emits('handleDonateModal', donateBtn.value, user.value.nickname);
}
</script>
<style scoped></style>
