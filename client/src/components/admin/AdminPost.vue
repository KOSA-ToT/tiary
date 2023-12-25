<template>
  <div>
    <table class="min-w-full divide-y divide-gray-200 overflow-x-auto">
      <thead class="bg-gray-50">
        <tr>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            No
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            Notice
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            Actions
          </th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="(notice, index) in noticeList" :key="notice.id">
          <td class="px-6 py-4 whitespace-nowrap">{{ index + 1 }}</td>
          <td class="px-6 py-4 whitespace-nowrap">
            <router-link :to="'/article/' + notice.id">
              <div class="text-sm text-gray-900">
                {{ notice.title }}
              </div>
            </router-link>
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
            <a href="#" class="text-indigo-600 hover:text-indigo-900">Edit</a>
            <a href="#" class="ml-2 text-red-600 hover:text-red-900">Delete</a>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- 페이징 -->
    <!-- <section v-if="totalPages != 0">
      <div class="flex items-center justify-between px-4 py-3 sm:px-6 bg-back">
        <div class="flex justify-between flex-1 sm:hidden">
          <a
            @click="prevPage"
            class="relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50"
            >Previous</a
          >
          <a
            @click="nextPage"
            class="relative inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50"
            >Next</a
          >
        </div>
        <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
          <div>
            <nav
              class="inline-flex -space-x-px rounded-md shadow-sm isolate"
              aria-label="Pagination"
            >
              <a
                @click="prevPage"
                :disabled="currentPage === 1"
                class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-l-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                id="previousPage"
              >
                <span class="sr-only">Previous</span>
                <svg
                  class="w-5 h-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                >
                  <path
                    fill-rule="evenodd"
                    d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z"
                    clip-rule="evenodd"
                  />
                </svg>
              </a>
              <a
                v-for="page in visiblePages"
                :key="page"
                @click="clickPage(page - 1)"
                :aria-current="currentPage === page - 1 ? 'page' : null"
                :class="{
                  'hover:bg-commu-pagination-hover': totalPages > page - 1,
                }"
                class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 duration-200 ease-in-out ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0 page-link"
              >
                {{ page }} </a
              ><a
                @click="nextPage"
                :disabled="currentPage === totalPages"
                class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-r-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                id="nextPage"
              >
                <span class="sr-only">Next</span>
                <svg
                  class="w-5 h-5"
                  viewBox="0 0 20 20"
                  fill="currentColor"
                  aria-hidden="true"
                >
                  <path
                    fill-rule="evenodd"
                    d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
                    clip-rule="evenodd"
                  />
                </svg>
              </a>
            </nav>
          </div>
        </div>
      </div>
    </section> -->
  </div>
</template>

<script setup>
import { defineProps, onMounted, ref, watchEffect, watch, computed } from "vue";
import { listMyPost, readNoticeList, deleteArticleRequest } from "@/api/common";
import { useRoute } from "vue-router";
import { useNoticeStore } from "@/stores/noticeStore";
const noticeList = ref([]);
const allChecked = ref(false);
const totalPages = ref(0);
const currentPage = ref(0);
const visiblePages = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
const pageCalculate = ref();
const selectedPosts = ref([]);

const currentRoute = useRoute();
let adminId = currentRoute.params.id;

const noticeStore = useNoticeStore();

// function test() {
//   console.log("어드민아이디", adminId);
// }
onMounted(async () => {
  await getNoticeList;
});

const Post = {
  title: ref(),
  content: ref(),
};

async function getNoticeList() {
  try {
    pageCalculate.value = Math.floor(currentPage.value / 10);
    console.log(pageCalculate.value);
    pageCalculate.value = pageCalculate.value * 10;
    visiblePages.value = Array.from(
      { length: 10 },
      (_, index) => pageCalculate.value + index + 1
    );
    const response = await listMyPost(adminId, currentPage.value);
    noticeStore.setNotices(response.data.content);
    console.log("response", response);
    console.log("response.data.content", response.data.content);

    noticeList.value = response.data.content;
    totalPages.value = response.data.totalPages;
    console.log("noticeList.value", noticeList.value);
    // console.log("response.data : " + response.data.content[0].value);
    console.log("response.data.totalPages : " + response.data.totalPages);
  } catch (error) {
    console.log(error);
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    currentPage.value -= 1;
    getNoticeList();
  }
}
function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    currentPage.value += 1;
    getNoticeList();
  }
}
function clickPage(num) {
  if (num < totalPages.value) {
    currentPage.value = num;
    getNoticeList();
  }
}
const formatCreatedAt = (createdAt) => {
  // createdAt이 문자열인 경우 Date 객체로 변환
  const date = typeof createdAt === "string" ? new Date(createdAt) : createdAt;

  // Date 객체를 원하는 형식으로 변환
  const formattedDate =
    `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ` +
    `${date.getHours()}시 ${date.getMinutes()}분 ${date.getSeconds()}초`;

  return formattedDate;
};

// async function deleteSelected(postId) {
//     try {
//         const response = await deleteArticleRequest(postId);
//         console.log('삭제 요청이 성공했습니다.');
//         window.location.reload();
//     } catch (error) {
//         console.log("에러 : " + error);

//     }
// };

const selectAll = () => {
  console.log("선택됨");
  allChecked.value = !allChecked.value;
  // 전체 선택 상태에 따라 noticeList의 각 항목의 선택 상태를 업데이트
  noticeList.value.forEach((post) => (post.selected = allChecked.value));
};

const handleCheckboxChange = () => {
  // 개별 체크박스 상태 변경에 대한 로직 추가
  // postId를 이용하여 해당 포스트의 선택 여부를 처리
};
onMounted(() => {
  getNoticeList();
});

// watch(() => adminId.value, () => {
//     getNoticeList();
// });
</script>

<style scoped></style>
