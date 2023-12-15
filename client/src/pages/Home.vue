<template>
  <Header></Header>
  
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <Slider></Slider>
    <Category :categories="categories"></Category>
    <Thumbnails></Thumbnails>
    <Band></Band>
    <Card></Card>
    <Footer></Footer>
    
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue'
import Slider from '@/components/Slider.vue'
import Footer from '@/components/Footer.vue'
import Thumbnails from '@/components/Thumbnails.vue'
import Card from '@/components/Card.vue'
import Band from '@/components/Band.vue'

import Category from '@/components/Category.vue'

import { onMounted, ref } from 'vue';
import axios from 'axios';

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
