<template>
  <section
    class="flex flex-col items-center justify-center max-w-screen h-screen p-16"
  >
    <slot></slot>
    <img :src="getRandomDefaultImage()" class="m-auto" />
  </section>
</template>
<script setup>
const imageArray = [
  "/images/loading_1.gif",
  "/images/loading_2.gif",
  "/images/loading_3.gif",
  "/images/loading_4.gif",
  "/images/loading_5.gif",
];

function getRandomDefaultImage() {
  const randomIndex = Math.floor(Math.random() * imageArray.length);
  return imageArray[randomIndex];
}

console.log(getRandomDefaultImage());
</script>
