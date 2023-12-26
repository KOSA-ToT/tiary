<template>
  <div>
    <!-- 커버 이미지 영역 -->
    <div v-if="article" class="h-64 bg-cover bg-center relative overflow-hidden">
      <img :src="getRandomDefaultImage()" class="opacity-70 blur-sm" />
      <div class="absolute bottom-0 left-0 right-0 text-white text-center p-4">
        <h1 class="text-3xl font-bold mb-5">
          {{ article.title || "제목 없음" }}
        </h1>

        <div class="mb-4">
          <span class="ico_by">At </span>
          <span class="mr-5">
            {{
              isWithinSixHours(article.createdAt)
              ? formatTimeDifference(article.createdAt)
              : dateFormat.formatCreatedAt(article.createdAt)
            }}
          </span>
          <span class="ico_by">By </span>
          <router-link :to="'/writer-page/' + article.userId">
            <span>{{ article.createdBy }}</span>
          </router-link>
          <!-- <span v-if="authStore.isLoggedIn"> -->

          <span v-if="shouldShowEditDeleteButtons">
            <span class="mx-2">•</span>
            <router-link :to="{ name: 'ArticleEdit', params: { id: articleId } }">수정</router-link>
            <span class="mx-2">•</span>
            <button @click="deleteArticle">삭제</button>
          </span>
        </div>
      </div>
      <!-- 헤더 영역 -->
      <Header :threshold="250"></Header>
    </div>

    <!-- 글 내용 -->

    <div v-if="article" class="max-w-3xl mx-auto p-4 bg-white shadow-lg">
      <!-- <div class="content prose prose-sm max-w-750 mx-auto" v-html="article.content || '내용 없음'">
        </div> -->

      <div id="content" ref="editor">
        <div v-html="testHtml"></div>
      </div>



      <!-- 해시태그 영역 -->
      <div class="max-w-2xl mx-auto mt-4 p-4">
        <div class="flex flex-wrap justify-start items-end space-x-2">
          <span v-for="hashtag in article.hashtagList" :key="hashtag.id">
            <div class="bg-amber-500 text-white p-2 rounded-full mb-2">
              #{{ hashtag.hashtagName }}
            </div>
          </span>
        </div>
      </div>

      <div class="parent-of-parent">
        <div class="flex items-center space-x-4 mb-4 justify-between">
          <!-- 좋아요 영역 -->
          <like :articleId="articleId"></like>
          <!-- 댓글 토글 버튼 -->
          <button @click="toggleComments"
            class="bg-blue-500 text-white p-2 rounded-full cursor-pointer btn btn-outline btn-orange">
            {{ showComments ? '댓글 닫기' : '댓글 보기' }}
          </button>
        </div>
        <!-- 댓글 영역 -->
        <comment v-if="showComments" class="mb-4"></comment>
      </div>
      <hr>
      <!-- 프로필 영역-->
      <AuthorProfile :userId="userId" @handleDonateModal="handleDonateModal"></AuthorProfile>
      <!-- 추천게시물 영역 -->
      <recommendations :articleId="articleId" />
    </div>
    <div v-else class="text-center mt-10">
      <p>Loading...</p>
    </div>
  </div>
  <div v-if="donateBtn" class="modal-overlay">
      <div class="max-w-2xl m-0 sm:m-10 bg-white shadow sm:rounded-lg flex justify-center flex-1">
        <div class="p-6">
          <button
            @click="closeDonationModal()"
            type="button"
            class="absolute top-3 right-3 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm ml-auto inline-flex items-center popup-close"
          >
            <svg
              aria-hidden="true"
              class="w-5 h-5"
              fill="#c6c7c7"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fill-rule="evenodd"
                d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                cliprule="evenodd"
              ></path>
            </svg>
            <span class="sr-only">Close Modal</span>
          </button>
          <div class="flex items-center justify-center h-full space-x-2">
            <!-- 로고 말고 다른거 넣어도 괜찮을듯 -->
  <img src="/images/header_logo.png" class="w-18 h-18 mx-auto" />
</div>
        </div>
        <div class="p-6">
          <h2 class="text-2xl font-bold mb-4">{{ `${author}님 응원하기` }}</h2>
          <p class="mb-4">작가님을 응원하고 싶습니다! 얼마를 후원하시겠습니까?</p>
          <!-- 여기에 버튼 등 기부 UI 추가 -->
          <TossPayment
              :userId="userId"
              :articleId="articleId"
              :supporter="supporter"
              :receiver="receiver"
              :author="author"
          ></TossPayment>
        </div>
      </div>
    </div>
    <div v-if="paymentSuccess" class="modal-overlay">
      <PaymentSuccessModal></PaymentSuccessModal>
    </div>
</template>

<script setup>
import Header from "@/components/Header.vue";
import like from "@/components/article/like/Like.vue"
import AuthorProfile from './AuthorProfile.vue';
import comment from "@/components/comment/Comment.vue";
import recommendations from "@/components/article/Recommendations.vue";
import { ref, onMounted, computed, defineProps, nextTick, onBeforeMount } from "vue";
import * as dateFormat from "@/utils/dateformat.js";
import router from "@/router";
import Editor from '@toast-ui/editor';
import { deleteArticleRequest, getArticleRequest } from "@/api/common";
import { useAuthStore } from "@/stores/auth";
import Viewer from '@toast-ui/editor/dist/toastui-editor-viewer';
import TossPayment from '@/components/TossPayment.vue';
import PaymentSuccessModal from '@/components/PaymentSuccessModal.vue'

const { articleId } = defineProps(["articleId"]);
const article = ref(null);
const userId = ref("");
const author = ref("");

const editor = ref("");
const editorValid = ref("");
const testHtml = ref("");

const receiver = ref('');
const supporter = ref('');
const emits = defineEmits();

// onMounted(async () => {
//   try {
//     const response = await getArticleRequest(articleId)
//     article.value = response.data;
//     userId.value = article.value.userId;

//     testHtml.value = article.value.content;

//   } catch (error) {
//     console.error("글을 불러오는 데 실패했습니다:", error);
//   }
// });

onMounted(async () => {
  try {
    const response = await getArticleRequest(articleId);
    article.value = response.data;
    userId.value = article.value.userId;
    // testHtml 값 설정
    testHtml.value = article.value.content;
    // Editor를 생성하여 Viewer 모드로 설정
    await nextTick(() => {
      const viewer = new Viewer({
        el: editor.value, // ref="editor"로 설정한 엘리먼트를 지정
        height : 'auto',
        initialValue: testHtml.value || '', // 내용 설정
        viewer: true,
        usageStatistics: false,
      });
    });
  } catch (error) {
    console.error("글을 불러오는 데 실패했습니다:", error);
  }
});


// editArticle 및 deleteArticle 메소드 정의
const deleteArticle = () => {
  // 삭제 로직 구현
  try {
    const response = deleteArticleRequest(articleId).then((response) => {
      if (response.status == 205) {
        alert("게시물이 삭제되었습니다.");
        router.go("-1");
      }
    });
  } catch {
    alert("에러입니다.");
  }
};

// 댓글 영역 토글 상태 변수
const showComments = ref(false);

// 댓글 토글 메소드
const toggleComments = () => {
  showComments.value = !showComments.value;
};

// 시간 표시
const isWithinSixHours = (createdAt) => {
  const sixHoursInMillis = 6 * 60 * 60 * 1000;
  const currentTime = new Date().getTime();
  const articleTime = new Date(createdAt).getTime();
  const timeDifference = currentTime - articleTime;

  return timeDifference <= sixHoursInMillis;
};

const formatTimeDifference = (createdAt) => {
  const currentTime = new Date().getTime();
  const articleTime = new Date(createdAt).getTime();
  const timeDifference = currentTime - articleTime;

  const hours = Math.floor(timeDifference / (60 * 60 * 1000));
  const minutes = Math.floor((timeDifference % (60 * 60 * 1000)) / (60 * 1000));

  if (hours > 0) {
    return `${hours} 시간 전`;
  } else {
    return `${minutes} 분 전`;
  }
};

// 로그인 상태 관리
const authStore = useAuthStore();

const shouldShowEditDeleteButtons = computed(() => {
  // 사용자가 로그인했고, 현재 사용자와 게시물 작성자가 동일한 경우에만 표시
  return authStore.isLoggedIn && authStore.currentUser === article.value.email;
});

const defaultImageArray = [
  "/images/cover/travle_1.jpeg",
  "/images/cover/travle_2.jpeg",
  "/images/cover/travle_3.jpeg",
  "/images/cover/culture_1.jpg",
  "/images/cover/culture_2.jpg",
  "/images/cover/culture_3.jpg",
  "/images/cover/economy_1.jpg",
  "/images/cover/economy_1.jpg",
  "/images/cover/economy_1.jpg",
  "/images/cover/health_1.jpg",
  "/images/cover/health_2.jpg",
  "/images/cover/health_3.jpg",
  "/images/cover/tech_1.jpg",
  "/images/cover/tech_2.jpg",
  "/images/cover/tech_3.jpg",
];
function getRandomDefaultImage() {
  const randomIndex = Math.floor(Math.random() * defaultImageArray.length);
  return defaultImageArray[randomIndex];
}

const donateBtn = ref(false);

// 응원하기 전달
function handleDonateModal(btnValue, nickname) {
  donateBtn.value = btnValue;
  author.value = nickname;
  receiver.value = article.value.email;
  supporter.value = authStore.currentUser;
}

function closeDonationModal() {
  donateBtn.value = false;
}

const paymentSuccess = ref(false);

function openPaymentSuccessModal() {
  paymentSuccess.value = true;
}

</script>
<style scoped>
/* 추가된 스타일 */
.bg-cover img {
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.bg-blue-500 {
  background-color: #3490dc;
}

.text-white {
  color: white;
}

.p-2 {
  padding: 0.5rem;
}

.rounded-full {
  border-radius: 999px;
}

.mb-2 {
  margin-bottom: 0.5rem;
}

.text-stroke {
  text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000,
    1px 1px 0 #000;
}

.btn {
  margin-right: 5px;
  padding: 5px 16px;
  /* padding 수정 */
  min-width: 75px;
  /* 최소 너비 추가 */
  border: 1px solid orange;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn:hover {
  border-color: #ff9800;
}

.btn-outline {
  background-color: transparent;
  color: #ff9800;
}

.btn-outline:hover {
  background-color: #ff9800;
  color: #fff;
}

.btn-orange {
  background-color: transparent;
  color: #ff9800;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7); /* 반투명한 검은 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1002; /* 모달이 다른 요소 위에 나타나도록 설정 */
}

.max-w-2xl {
  width: 80%; /* 가로 크기를 80%로 설정 (조절 가능) */
  position: relative;
}

.popup-close {
  top: 10px; /* 모달창 상단에서 10px 떨어진 위치 */
  right: 10px; /* 모달창 오른쪽에서 10px 떨어진 위치 */
  cursor: pointer;
}
</style>
