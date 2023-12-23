<template>
    <Loading>
      <h1 class="text-7xl font-bold mt-20">Loading...</h1>
    </Loading>
</template>
<script setup>
import Loading from '@/components/Loading.vue';
import { onMounted } from 'vue';
import { oauthTokenReq } from '@/api/common';
import router from '@/router';

onMounted (() => {
  setTimeout(() => {
    oauthProvideToken();
  }, 500);
});

async function oauthProvideToken(){
  console.log(localStorage.getItem('Authorization'));
  if (localStorage.getItem('Authorization') != null) {
      localStorage.removeItem('Authorization');
      router.push('/error');
      return;
  } 
  try {
      const oauthTokenResponse = await oauthTokenReq();
      console.log(oauthTokenResponse.headers.authorization)
      localStorage.setItem('Authorization', oauthTokenResponse.headers.authorization)
      router.push('/');
  } catch (error) {
    console.log(error)
    return;
  }
}
</script>