<script setup>
</script>

<template>
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <router-view />
  </div>
</template>
<script setup>
import {ref , watch} from 'vue';

const isDarkMode = ref(false);
watch(
  () => window.matchMedia('(prefers-color-scheme: dark)').matches,
  (dark) => {
    isDarkMode.value = dark;
  },
  { immediate: true }
);

</script>
<style scoped>
.dark {
  background-color: #F2F2F2;
  color: #000000;
}
</style>
