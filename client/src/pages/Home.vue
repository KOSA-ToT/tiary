<template>
    <button @click="openModal('로그인')">로그인</button>&nbsp;
    <button @click="openModal('회원가입')">회원가입</button>&nbsp;
    <button @click="logout()">로그아웃</button>
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <Sidebar></Sidebar>
    <Slider></Slider>
    <Category :categories="categories"></Category>
    <Thumbnails></Thumbnails>
    <Card></Card>
    <Footer></Footer>
    <UserModal
        v-if="userVars.isShowModal"
        v-model:task="userVars.task"
        v-model:isShowModal="userVars.isShowModal"
        >
    </UserModal>
  </div>
</template>

<script setup>
import Slider from '@/components/Slider.vue'
import Footer from '@/components/Footer.vue'
import Thumbnails from '@/components/Thumbnails.vue'
import Card from '@/components/home/Card.vue'
import UserModal from '@/components/UserModal.vue'
import Category from '@/components/Category.vue'
import Sidebar from '@/components/Sidebar.vue'

import { onMounted, ref } from 'vue';
import axios from 'axios';

// 유저
const userVars = ref({
    task: '',
    isShowModal: false
})

function openModal(tasks) { 
    userVars.value.task = tasks
    userVars.value.isShowModal = true;
}

function logout() {
  if(confirm("로그아웃 하시겠습니까?")) {
    localStorage.removeItem('Authorization');
  }
}

const categories = ref([])
onMounted(async () => {
  const response = await axios.get('http://localhost:8088/category')
    .then((response) => {
      categories.value = response.data;
    })
}
);

</script>
<style scoped></style>
