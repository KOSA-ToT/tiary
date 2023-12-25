<template>
  <div :class="{ dark: isDarkMode, light: !isDarkMode }">
    <div id="article" class="max-w-4xl mx-auto p-4 font-sans bg-white dark:bg-white-800">
      <div id="category" :class="{ 'text-white': isDarkMode, 'text-black': !isDarkMode }">
        <label for="category" class="block text-sm font-bold mb-2"></label>
        <select v-model="categoryCode" class="w-40 p-2 border border-gray-300 dark:bg-white-300 dark:text-black">
          <option disabled value="" selected>카테고리</option>
          <option v-for="i in categories" :key="i.categoryCode" :value="i.categoryCode"
            class="dark:bg-gray-700 dark:text-white">
            {{ i.categoryName }}
          </option>
        </select>
      </div>
      <div id="title" class="mb-4">
        <label for="title" class="block text-sm font-bold mb-2"></label>
        <input type="text" name="title" v-model="title" placeholder="제목을 입력해주세요"
          class="w-full p-2 border-0 border-gray-300 dark:bg-gray-300 dark:text-black" />
      </div>
      <div id="content" ref="editor" class="mb-8 h-full">
        <div v-html="testHtml"></div>
      </div>
      <div id="hashtag" class="mb-4">
        <label for="hashtag" class="block text-sm font-bold mb-2" placeholder="#해시태그"></label>
        <input type="text" name="hashtag" v-model="hashtag" placeholder="#해시태그"
          class="w-full p-2 border-0 border-gray-300 dark:bg-gray-300 dark:text-black" />
      </div>
    </div>
    <div class="fixed bottom-0 right-0 left-0 flex justify-end items-center p-4 bg-white dark:bg-gray-300">
      <button @click.prevent="postArticle"
        class="text-white py-2 px-4 bg-orange-400 dark:bg-orange-400 rounded-full hover:bg-orange-500 transition duration-300">
        작성하기
      </button>
      <button @click.prevent="backArticle"
        class="text-white py-2 px-4 ml-2 bg-orange-400 dark:bg-orange-400 rounded-full hover:bg-orange-500 transition duration-300">
        취소
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, onUnmounted } from "vue";
import axios from "axios";
import Editor from "@toast-ui/editor";
import "@toast-ui/editor/dist/toastui-editor.css";
import router from "@/router";
import { postArticleRequest, getCategoryList } from "@/api/common";

//컬러
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css";

// 코드 하이라이트
import "prismjs/themes/prism.css";
import "@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css";
import codeSyntaxHighlight from "@toast-ui/editor-plugin-code-syntax-highlight";

const emit = defineEmits(["update:modelValue"]);
const editor = ref("");
const editorValid = ref("");
const images = [];

//article
const title = ref("");
const hashtag = ref("");
const testHtml = ref("");

//category
const categories = ref([]);
const categoryCode = ref("");

//css
const isDarkMode = ref(false);

// 주기적으로 실행될 함수
function autoSave() {
  // 현재 작성된 내용을 가져와서 임시 저장
  const dataToSave = {
    title: title.value,
    content: editorValid.value.getHTML(),
    hashtag: hashtag.value,
    categoryCode: categoryCode.value,
    storeName: images,
  };

  // localStorage에 데이터 저장
  localStorage.setItem('autoSaveData', JSON.stringify(dataToSave));
  console.log('자동 저장 완료');
}

// 주기적으로 autoSave 함수 호출 (예: 2분마다)
const autoSaveInterval = setInterval(autoSave, 2 * 60 * 1000); // 2분

// 컴포넌트가 소멸될 때 clearInterval을 통해 인터벌 해제
onUnmounted(() => {
  clearInterval(autoSaveInterval);
});

// 컴포넌트가 생성될 때 localStorage에서 데이터 불러오기
onMounted(() => {
  const savedData = localStorage.getItem('autoSaveData');
  if (savedData) {
    const confirmLoad = window.confirm('임시 저장된 데이터가 있습니다. 불러오시겠습니까?');

    if (confirmLoad) {
      const parsedData = JSON.parse(savedData);
      console.log(savedData);
     
      title.value = parsedData.title;
      content.value = parsedData.content;
     
    } else {
      localStorage.removeItem('autoSaveData');
    }
  }
  //마운트될때 Editor 생성
  editorValid.value = new Editor({
    el: editor.value,
    initialValue : content.value,
    height: "460px",
    usageStatistics: false,
    hideModeSwitch: true,
    hideToolbar: true,
    initialEditType: "wysiwyg",
    plugins: [colorSyntax, codeSyntaxHighlight],
    hooks: {
      async addImageBlobHook(blob, callback) {
        const formData = new FormData();
        formData.append("images", blob);

        try {
          const response = await axios.post(
            "http://localhost:8088/images",
            formData
          );
          // 새로운 이미지 추가
          Object.entries(response.data).forEach(([key, value]) => {
            console.log(response.data);
            callback(value, "img alt attribute");
            images.push(key);
          });
        } catch (error) {
          console.error("Error uploading image:", error);
        }
      },
    },
  });
});

onMounted(async () => {
  const response = await getCategoryList()
    .then((response) => {
      categories.value = response.data;
    });
});

function postArticle() {
  const requestArticleDto = {
    title: title.value,
    content: editorValid.value.getHTML(),
    hashtag: hashtag.value,
    categoryCode: categoryCode.value, //categoryCode.value
    storeName: images,
  };

  if (editorValid.value.getMarkdown().length < 1) {
    alert("에디터 내용을 입력해 주세요.");
    throw new Error("editor content is required!");
  }
  try {
    const response = postArticleRequest(requestArticleDto).then((response) => {
      console.log(response);
      if (response.status == 201) {
        alert("게시물이 작성되었습니다.");
        console.log(response.data);
        router.push("/article/" + response.data.id);
      }
    });
  } catch {
    alert(response.status);
    return;
  }
}

function backArticle() {
  router.push('/');
}


watch(
  () => window.matchMedia("(prefers-color-scheme: dark)").matches,
  (dark) => {
    isDarkMode.value = dark;
  },
  { immediate: true }
);
//작성한 내용 불러와서 html 적용
const testValid = (e) => {
  testHtml.value = editorValid.value.getHTML();
};
</script>
<style scoped>
/* category 입력창의 플레이스홀더 색상 설정 */
#category select::placeholder {
  color: #5d5c5c;
  /* 원하는 색상으로 변경하세요 */
}

#category select:hover {
  background-color: #e2e8f0;
  /* hover 시 배경색 설정 */
}

/* title 입력창의 플레이스홀더 색상 설정 */
#title input::placeholder {
  color: #5d5c5c;
  /* 원하는 색상으로 변경하세요 */
}

#title input {
  background-color: transparent;
  /* 배경색을 투명으로 설정 */
  border: none;
  /* 테두리 제거 */
  border-bottom: 1px solid #bab9b9;
  /* 밑줄 스타일 및 색상 설정 */
  outline: none;
  /* 포커스시 테두리 제거 */
}

/* hashtag 입력창의 플레이스홀더 색상 설정 */
#hashtag input::placeholder {
  color: #5d5c5c;
  /* 원하는 색상으로 변경하세요 */
}

#hashtag input {
  background-color: transparent;
  /* 배경색을 투명으로 설정 */
  border: none;
  /* 테두리 제거 */
  /* border-bottom: 1px solid #5c5a5a;  밑줄 스타일 및 색상 설정 */
  outline: none;
  /* 포커스시 테두리 제거 */
}

@keyframes catJump {
    0%, 100% {
      transform: translateY(0);
    }
    50% {
      transform: translateY(-10px);
    }
  }

  .cat-button {
    animation: catJump 1s infinite;
  }
</style>
