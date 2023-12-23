<template>
  <img
    src="/images/topBtn.png"
    @click="scrollToTop"
    v-show="shouldShowButton"
    class="scroll-to-top-button"
  />
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";

const shouldShowButton = ref(false);

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});

const handleScroll = () => {
  shouldShowButton.value = window.scrollY > 500;
};

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: "smooth" });
};
</script>

<style scoped>
.scroll-to-top-button {
  position: fixed;
  width: 75px;
  bottom: 30px;
  right: 30px;
  color: #fff;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  opacity: 0.8;
}

.scroll-to-top-button:hover {
  opacity: 1;
}
</style>
