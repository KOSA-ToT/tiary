<template>
  <div>
    <Header></Header>
    <br />
    <br />
    <br />
    <br />
    <div class="flex flex-col justify-center items-center h-full">
      <router-view />
    </div>
    <Footer></Footer>
  </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
</script>

<style scoped></style>
