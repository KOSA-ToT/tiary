<template>
   
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <Header :threshold="200"></Header>
    <Slider></Slider>
    <Category :categories="categories"></Category>
    <RandomArticle :randomArticles="randomArticle"></RandomArticle>
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
import Header from '@/components/Header.vue';
import Slider from '@/components/Slider.vue';
import Footer from '@/components/Footer.vue';
import RandomArticle from '@/components/RandomArticle.vue';
import Card from '@/components/home/Card.vue';
import UserModal from '@/components/UserModal.vue';
import Category from '@/components/Category.vue';

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

const randomArticle = ref([])
onMounted( async () => {
  const response = await axios.get('http://localhost:8088/article')
  .then((response) => {
    randomArticle.value = response.data;
    console.log(randomArticle.value);
  })
})
</script>
<style scoped></style>
