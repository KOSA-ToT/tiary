<template>
  <div>
    <!-- Category 컴포넌트에서 categories를 사용 -->
    <div class="flex flex-wrap justify-center">
      <router-link
        v-for="category in categories"
        :key="category.categoryCode"
        class="w-full sm:w-1/2 md:w-1/4 lg:w-1/4 xl:w-1/4 2xl:w-1/4 mb-5 px-5 py-3 border transition duration-300 ease-in-out hover:bg-gray-200 hover:text-blue-500 hover:border-blue-500"
        :to="{ name: 'CategoryPage', query: { categoryCode: category.categoryCode } }"
        style="text-decoration: none; color: inherit; display: flex; flex-direction: column; align-items: center; justify-content: center; width: 150px; height: 150px;"
      >
        {{ category.categoryName }}
      </router-link>
    </div>
  </div>
</template>

<script setup>
const props = defineProps(['categories']);
</script>
