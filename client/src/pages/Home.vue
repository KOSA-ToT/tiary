<template>
    <button @click="openModal('로그인')">로그인 버튼</button>
    <button @click="openModal('회원가입')">회원가입 버튼</button>
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <Slider></Slider>
    <Category :categories="categories"></Category>
    <Thumbnails></Thumbnails>
    <Band></Band>
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
import {ref } from 'vue'

import Slider from '@/components/Slider.vue'
import Footer from '@/components/Footer.vue'
import Thumbnails from '@/components/Thumbnails.vue'
import Card from '@/components/Card.vue'
import Band from '@/components/Band.vue'
import UserModal from '@/components/UserModal.vue'
import Category from '@/components/Category.vue'

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
