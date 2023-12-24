<template>
  <div>
    <Header></Header>
    <br />
    <br />
    <br />
    <br />
    <router-view />
    <Footer></Footer>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<style scoped></style>
