<template>
  <UserModal :task="task" :nickCheck="nickCheck" :email="email"></UserModal>
</template>

<script setup>
import UserModal from '@/components/UserModal.vue';
import { ref } from 'vue';
import {useRouter} from "vue-router";

const task = '회원가입';
const nickCheck = true;

const router = useRouter();
const email = ref(router.currentRoute.value.params.email);

</script>