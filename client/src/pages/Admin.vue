<template>
  <div>
    <Header></Header>
    <div class="flex items-center">
      <section class="w-full pt-5 bg-center bg-cover py-15 pb-20"
        style="background-image: url('https://picsum.photos/1440/190/?blur=1?random=1​');">
        <div class="container mx-auto text-center text-white">
          <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
            <div class="flex items-center justify-end px-12">
            </div>
          </div>

        </div>
      </section>
    </div>
    <div class="">
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
