<template>
  <div :class="{ 'dark': isDarkMode, 'light': !isDarkMode }">
    <div id="custom-toolbar">
      <button onclick="customBold()">Bold</button>
      <button onclick="customItalic()">Italic</button>
      <!-- 필요한 다른 사용자 지정 버튼들... -->
    </div>
    <div id="article" class="max-w-4xl mx-auto p-4 font-sans bg-white dark:bg-white-800">
      <div id="category" :class="{ 'text-white': isDarkMode, 'text-black': !isDarkMode }">
        <label for="category" class="block text-sm font-bold mb-2"></label>
        <select v-model="categoryCode" class="w-40 p-2 border border-gray-300  dark:bg-white-300 dark:text-black">
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
          class="w-full p-2 border-0 border-gray-300  dark:bg-gray-300 dark:text-black">
      </div>
      <div id="content" ref="editor" class="mb-8 h-96">
        <div v-html="testHtml.value"></div>
      </div>
      <div id="hashtag" class="mb-4">
        <label for="hashtag" class="block text-sm font-bold mb-2" placeholder="#해시태그"></label>
        <input type="text" name="hashtag" v-model="hashtag" placeholder="#해시태그"
          class="w-full p-2 border-0 border-gray-300  dark:bg-gray-300 dark:text-black">
      </div>

    </div>
    <div class="fixed bottom-0 right-0 left-0 flex justify-end items-center p-4 bg-white dark:bg-gray-300">
      <button @click.prevent="postArticle"
        class="text-white py-2 px-4 bg-green-500 dark:bg-gray-800 rounded-full hover:bg-purple-500 transition duration-300">
        작성
      </button>
      <button
        class="text-white-700 py-2 px-4 ml-2 bg-gray-300 dark:bg-gray-800 rounded-full hover:bg-purple-500 transition duration-300">
        취소
      </button>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch, onUnmounted } from 'vue';
import axios from 'axios';
import Editor from '@toast-ui/editor';
import '@toast-ui/editor/dist/toastui-editor.css';

//컬러
import colorSyntax from "@toast-ui/editor-plugin-color-syntax";
import "@toast-ui/editor-plugin-color-syntax/dist/toastui-editor-plugin-color-syntax.css";

// 코드 하이라이트
import 'prismjs/themes/prism.css';
import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';


const props = defineProps({
  modelValue: {
    type: String,
    required: false,
    default: '',
  },
});

const emit = defineEmits(['update:modelValue']);
const editor = ref("");
const editorValid = ref("");
const formData = new FormData();
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

//마운트될때 Editor 생성
onMounted(() => {
  editorValid.value = new Editor({
    el: editor.value,
    height: 'auto',
    usageStatistics: false,
    hideModeSwitch: true,
    hideToolbar : true,
    initialEditType: 'wysiwyg',
    plugins: [colorSyntax, codeSyntaxHighlight],
    hooks: {
      addImageBlobHook(blob, callback) {
        formData.append('images', blob)

        const response = axios.post('http://localhost:8088/images', formData)
          .then((response) => {
            const dataMap = new Map(Object.entries(response.data))
            dataMap.forEach((value) => {
              callback(value, "img alt attribute");
            })
            dataMap.forEach((value, key) => {
              images.push(key)
            })
          });
      }
    }
  })
});

onMounted(
  async () => {
    const response = await axios.get('http://localhost:8088/category')
      .then((response) => {
        console.log(response.data);
        categories.value = response.data;
      })
    console.log(categories);
  }
);


function postArticle() {
  const requestArticleDto = {
    title: title.value,
    content: editorValid.value.getHTML(),
    hashtag: hashtag.value,
    categoryCode: categoryCode.value, //categoryCode.value 
    storeName: images
  };
  console.log(requestArticleDto);
  if (editorValid.value.getMarkdown().length < 1) {
    alert('에디터 내용을 입력해 주세요.');
    throw new Error('editor content is required!');
  }
  const auth = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlkIjoxLCJleHAiOjE3MDIzNjMzNzIsImVtYWlsIjoidGVzdEBnbWFpbC5jb20ifQ.--PQsHLQ";

  axios.post('http://localhost:8088/article', requestArticleDto, {
    headers: {
      Authorization: auth
    }
  })
    .then((response) => {
      if (response.status == 201) {
        alert("확인")
      }
    })
}

watch(
  () => window.matchMedia('(prefers-color-scheme: dark)').matches,
  (dark) => {
    isDarkMode.value = dark;
  },
  { immediate: true }
)
//작성한 내용 불러와서 html 적용
const testValid = (e) => {
  testHtml.value = editorValid.value.getHTML()
};
</script>
<style scoped>
.dark body {
  background-color: #ffffff;
  /* 다크 모드 배경색을 적절히 수정하세요 */
  color: #fff;
  /* 다크 모드 텍스트 색상을 적절히 수정하세요 */
}

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
</style>
