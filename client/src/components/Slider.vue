<template>
  <div id="default-carousel" class="relative w-full mb-20 mt-20" data-carousel="slide">
    <!-- Carousel wrapper -->
    <div class="relative h-56 overflow-hidden md:h-96">
      <!-- Carousel items --> 
      <transition name="fade" mode="out-in">
        <div :key="currentSlide" class="absolute inset-0">
          <img :src="items[currentSlide].imageSrc" class="absolute inset-0 w-full h-full object-cover" alt="...">
        </div>
      </transition>
    </div>

    <!-- Slider controls -->
    <button v-if="showControls" type="button" @click="prevSlide"
      class="absolute top-1/2 -translate-y-1/2 start-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
      data-carousel-prev>
      <!-- Previous button content -->
      <span
        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
        <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4" />
        </svg>
        <span class="sr-only">Previous</span>
      </span>
    </button>
    <button v-if="showControls" type="button" @click="nextSlide"
      class="absolute top-1/2 -translate-y-1/2 end-0 z-30 flex items-center justify-center h-full px-4 cursor-pointer group focus:outline-none"
      data-carousel-next>
      <!-- Next button content -->
      <span
        class="inline-flex items-center justify-center w-10 h-10 rounded-full bg-white/30 dark:bg-gray-800/30 group-hover:bg-white/50 dark:group-hover:bg-gray-800/60 group-focus:ring-4 group-focus:ring-white dark:group-focus:ring-gray-800/70 group-focus:outline-none">
        <svg class="w-4 h-4 text-white dark:text-gray-800 rtl:rotate-180" aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4" />
        </svg>
        <span class="sr-only">Next</span>
      </span>
    </button>
<!-- Slider indicators -->
<div v-show="showIndicators && items.length > 1" class="absolute z-30 flex -translate-x-1/2 bottom-5 left-1/2 space-x-3 rtl:space-x-reverse">
  <button v-for="(item, index) in items" :key="index" type="button" class="w-3 h-3 rounded-full" :style="{ 'background-color': currentSlide === index ? 'black' : 'gray' }" :aria-current="currentSlide === index" :aria-label="`Slide ${index + 1}`" :data-carousel-slide-to="index" @click="goToSlide(index)"></button>
</div>

  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

const items = [
  { imageSrc: "/images/slider1.png" },
  { imageSrc: "/images/slider2.png" },
  { imageSrc: "/images/slider3.png" },
  { imageSrc: "/images/slider4.png" },
  { imageSrc: "/images/slider5.png" },
];

const totalSlides = items.length;
const currentSlide = ref(0);
const showControls = ref(true);
const showIndicators = ref(true);

const nextSlide = () => {
  currentSlide.value = (currentSlide.value + 1) % totalSlides;
};

const prevSlide = () => {
  currentSlide.value = (currentSlide.value - 1 + totalSlides) % totalSlides;
};

const goToSlide = (index) => {
  currentSlide.value = index;
};

onMounted(() => {
  // 자동 슬라이드를 위한 타이머 설정 (옵션)
  const interval = setInterval(nextSlide, 5000);

  // 컴포넌트가 소멸될 때 타이머 해제
  onBeforeUnmount(() => {
    clearInterval(interval);
  });
});
</script>
<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
