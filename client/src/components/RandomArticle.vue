<template>
  <section id="main">
    <h2 style="text-align: center">Random Articles</h2>
    <section class="thumbnails">
      <div v-for="article in randomArticles.slice(0, 6)" :key="article.id">
        <router-link :to="{ name: 'Post', params: { articleId: article.id } }" class="article-link">
          <div class="ml-6">
            <img v-if="article.imgPath && article.imgPath.length > 0" :src="getRandomImage(article.imgPath)" alt=""
                 class="shadow-md rounded-lg bg-slate-50 w-250 h-250 xl:w-[250px] xl:h-[250px] object-contain"
                 width="250" height="250" />
          </div>
          <h3>{{ article.title }}</h3>
        </router-link>
      </div>
    </section>
  </section>
</template>

<script setup>
// 'props' 는 자동으로 설정됩니다.
const { randomArticles } = defineProps(['randomArticles']);

function getRandomImage(imgPathArray) {
  // 랜덤한 이미지 선택
  const randomIndex = Math.floor(Math.random() * imgPathArray.length);
  return imgPathArray[randomIndex];
}
</script>

<style scoped>
@import "@/assets/css/main.css";

/* 타이틀의 밑줄 제거 및 내용 2줄로 제한 */
.article-link {
  text-decoration: none;
}

.article-link h3 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>
