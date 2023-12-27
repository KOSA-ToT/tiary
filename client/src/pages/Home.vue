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
import Notice from '@/components/admin/Notice.vue'
import { getArticleListRequest,getCategoryList } from '@/api/common';

import { onMounted, ref } from 'vue';
import axios from 'axios';

const categories = ref([])
onMounted(async () => {
  const response = getCategoryList()
    .then((response) => {
      categories.value = response.data;
    })
}
);

const randomArticle = ref([])
onMounted(async () => {
  try {
    const response = await getArticleListRequest();
    // response.data에서 6개의 랜덤 인덱스를 뽑아서 randomArticle.value에 할당
    randomArticle.value = getRandomArticles(response.data, 6);
  } catch (error) {
    console.error('데이터를 불러오는 도중 오류가 발생했습니다:', error);
  }
});

function getRandomArticles(articles, count) {
  const result = [];
  const visitedIndices = new Set();

  while (result.length < count) {
    const randomIndex = Math.floor(Math.random() * articles.length);

    if (!visitedIndices.has(randomIndex)) {
      result.push(articles[randomIndex]);
      visitedIndices.add(randomIndex);
    }
  }
  return result;
}
</script>
<style scoped></style>
