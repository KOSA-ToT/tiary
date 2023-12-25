<template>
  <div>
    <Header :threshold="100"></Header>
    어드민 페이지
    <router-view></router-view>
    <Footer></Footer>
  </div>
</template>

<script setup>
import NoticeManagement from '@/components/admin/NoticeManagement.vue'
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
</script>

<style  scoped></style>