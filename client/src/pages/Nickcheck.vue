<template>
  <UserModal :task="task" :nickCheck="nickCheck"></UserModal>
</template>

<script setup>
import UserModal from '@/components/UserModal.vue';

const task = '회원가입';
const nickCheck = true;

</script>