<template>
  <div>
    <Header :threshold="100"></Header>
    <Slider></Slider>
    <Category :categories="categories"></Category>
    <RandomArticle :randomArticles="randomArticle"></RandomArticle>
    <Card></Card>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Header from '@/components/Header.vue';
import Slider from '@/components/Slider.vue';
import Footer from '@/components/Footer.vue';
import RandomArticle from '@/components/home/RandomArticle.vue'
import Card from '@/components/home/Card.vue';
import Category from '@/components/home/Category.vue';

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
