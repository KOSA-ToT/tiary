<template>
</template>

<script setup>
import { ref, onBeforeMount } from 'vue'
import { emailAuthResultReq, loginReq } from '@/api/common'
import router from '@/router';

const task = ref('');
const key = ref('');

onBeforeMount(() => {
  const urlParams = new URLSearchParams(window.location.search);
  key.value = urlParams.get('link');
  task.value = urlParams.get('task');

  getVerified(key.value, task.value);
})

async function getVerified(key, task) {
  if(localStorage.getItem('Authorization')) {
    alert("이미 인증되었습니다.");
    router.push('/');
    return;
  }

  try { // 이메인 인증 요청
      const response = await emailAuthResultReq(key, task);
      alert(response.data.accepted)

      try { // 회원정보 있으면 로그인
        const loginDto = { email: response.data.email };
          const loginResponse = await loginReq(loginDto);
          localStorage.setItem('Authorization', loginResponse.headers.authorization)
          router.push('/');
      } catch (error) { // 회원정보 없으니 회원가입
        if(task === '회원가입') {
          router.push({ name: 'Nickcheck', params: { email: response.data.email } });
        }
        console.log(error.response.data.error)
      }

  } catch (error) {
      // 제한시간 초과
      console.log(error)
      alert(error.response.data)
      router.push('/');
  }
}

</script>