<template>
  <!-- 이미지를 가운데 정렬하도록 클래스 추가 -->
  <div class="text-center">
    <img style="display: block; margin: 0 auto;" src="/images/random_article.png" alt="Random Image">
  </div>
  <section>
    <section class="thumbnails">
      <div v-for="(article, index) in getRandomArticles(randomArticles, 6)" :key="article.id" class="article-item">
        <router-link :to="{ name: 'Post', params: { articleId: article.id } }" class="article-link">
          <div class="ml-6">
            <img
              v-if="article.imgPath && article.imgPath.length > 0"
              :src="getRandomImage(article.imgPath)"
              alt=""
              :class="getRandomThumbnailClass()"
              style="object-fit: cover; object-position: center center;"
            />
            <img
              v-else
              :src="getRandomDefaultImage()"
              alt=""
              :class="getRandomThumbnailClass()"
              style="object-fit: cover; object-position: center center;"
            />
          </div>
          <p class="text-2xl text-orange-900"
          style="margin-top: 10px;
          font-size: large;
          font-weight: 400;">
            {{ article.title }}</p>
          <p class="text-lg text-gray-600" 
          v-html="clampLines(stripHTML(article.content), 3)"></p>
        </router-link>
      </div>
    </section>
  </section>
</template>

<script setup>
const { randomArticles } = defineProps(['randomArticles']);

const defaultImageArray = ["/images/cat_1.jpg", "/images/cat_2.jpg", "/images/dog.jpg"]; // 여기 이미지 추가

function getRandomArticles(articles, count) {
  // 배열을 랜덤으로 섞은 후, 필요한 개수만큼 자름
  return articles.sort(() => Math.random() - 0.5).slice(0, count);
}

function getRandomImage(imgPathArray) {
  const randomIndex = Math.floor(Math.random() * imgPathArray.length);
  return imgPathArray[randomIndex];
}

function getRandomDefaultImage() {
  const randomIndex = Math.floor(Math.random() * defaultImageArray.length);
  return defaultImageArray[randomIndex];
}

function getRandomThumbnailClass() {
  const classes = [
    "shadow-md rounded-lg bg-slate-50 w-250 h-250 xl:w-[250px] xl:h-[250px] object-contain",
    "shadow-md rounded-lg bg-slate-50 w-200 h-300 xl:w-[200px] xl:h-[300px] object-contain",
    "shadow-md rounded-lg bg-slate-50 w-200 h-150 xl:w-[300px] xl:h-[200px] object-contain",
    "shadow-md rounded-lg bg-slate-50 w-250 h-300 xl:w-[300px] xl:h-[200px] object-contain",
    "shadow-md rounded-lg bg-slate-50 w-100 h-350 xl:w-[300px] xl:h-[200px] object-contain"
    // Add more classes as needed
  ];
  const randomIndex = Math.floor(Math.random() * classes.length);
  return classes[randomIndex];
}

function stripHTML(html) {
  // HTML 태그 제거 함수
  const doc = new DOMParser().parseFromString(html, 'text/html');
  return doc.body.textContent || '';
}

function clampLines(text, maxLines) {
  const lines = text.split('\n');
  if (lines.length <= maxLines) {
    return text;
  } else {
    return lines.slice(0, maxLines).join('\n');
  }
}
</script>
<style scoped>
@import "@/assets/css/main.css";

.article-link {
  text-decoration: none;
}

.article-item {
  margin-bottom: 20px;
  display: inline-block;
  width: 50%;
  box-sizing: border-box;
  padding: 0 10px;
}

.article-link h3 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
/* Additional styles to adjust spacing and layout as needed */
.thumbnails {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}

.article-link img {
  width: 100%;
  height: 100%;
}

.article-link p {
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

</style>
