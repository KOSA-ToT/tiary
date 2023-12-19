<template>
    <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
        <p class="pb-6">
            <!-- Container for demo purpose -->
        <div class="container mx-auto my-24 md:px-6">
            <!-- Section: Design Block -->
            <section class="mb-32 text-center md:text-left">
                <h2 class="mb-12 text-lg font-bold text-zinc-600">작성 댓글</h2>
                <!-- <button
                    class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">삭제</button>
                <button @click="selectAll"
                    class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">전체
                    선택</button> -->
                <div class="flex flex-wrap mb-6" v-for="comment in commentList" :key="comment.id">
                    <!-- <input type="checkbox" :id="'react-option-' + comment.id"> -->
                    <!-- <div class="w-full px-3 mb-6 ml-auto shrink-0 grow-0 basis-auto md:mb-0 md:w-3/12">
                        <div class="relative mb-6 overflow-hidden bg-no-repeat bg-cover rounded-lg shadow-lg dark:shadow-black/20"
                            data-te-ripple-init data-te-ripple-color="light">
                            <a href="#!">
                                <div
                                    class="absolute top-0 right-0 bottom-0 left-0 h-full w-full overflow-hidden bg-fixed opacity-0 transition duration-300 ease-in-out hover:opacity-100 bg-[hsla(0,0%,98.4%,.15)]">
                                </div>
                            </a>
                        </div>
                    </div> -->
                    <router-link :to="'/article/' + comment.articleId">
                        <div class="w-full px-3 mb-6">
                            <h5 class="mb-3 text-lg font-bold">{{ comment.content }}</h5>
                            <div
                                class="flex items-center justify-center mb-3 text-sm font-medium text-danger dark:text-danger-500 md:justify-start">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2"
                                    stroke="currentColor" class="w-5 h-5 mr-2">
                                    <path stroke-linecap="round" stroke-linejoin="round"
                                        d="M12.75 3.03v.568c0 .334.148.65.405.864l1.068.89c.442.369.535 1.01.216 1.49l-.51.766a2.25 2.25 0 01-1.161.886l-.143.048a1.107 1.107 0 00-.57 1.664c.369.555.169 1.307-.427 1.605L9 13.125l.423 1.059a.956.956 0 01-1.652.928l-.679-.906a1.125 1.125 0 00-1.906.172L4.5 15.75l-.612.153M12.75 3.031a9 9 0 00-8.862 12.872M12.75 3.031a9 9 0 016.69 14.036m0 0l-.177-.529A2.25 2.25 0 0017.128 15H16.5l-.324-.324a1.453 1.453 0 00-2.328.377l-.036.073a1.586 1.586 0 01-.982.816l-.99.282c-.55.157-.894.702-.8 1.267l.073.438c.08.474.49.821.97.821.846 0 1.598.542 1.865 1.345l.215.643m5.276-3.67a9.012 9.012 0 01-5.276 3.67m0 0a9 9 0 01-10.275-4.835M15.75 9c0 .896-.393 1.7-1.016 2.25" />
                                </svg>
                                원글 : {{ comment.title }}
                            </div>
                            <p class="mb-6 text-neutral-500 dark:text-neutral-300">
                                <small>작성일시 : {{ formatCreatedAt(comment.createdAt) }}</small>
                            </p>

                        </div>
                    </router-link>
                    <button @click="deleteSelected(comment.articleId, comment.id)" :id="'button-option-' + comment.id"
                        class="ml-auto rounded-md bg-white h-8 px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">삭제</button>

                </div>

            </section>
            <!-- Section: Design Block -->
            <section v-if="totalPages != 0"><!-- 페이징 -->
                <div class="flex items-center justify-between px-4 py-3 sm:px-6 bg-back">
                    <div class="flex justify-between flex-1 sm:hidden">
                        <a
                            class="relative inline-flex items-center px-4 py-2 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">Previous</a>
                        <a
                            class="relative inline-flex items-center px-4 py-2 ml-3 text-sm font-medium text-gray-700 bg-white border border-gray-300 rounded-md hover:bg-gray-50">Next</a>
                    </div>
                    <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-center">
                        <div>
                            <nav class="inline-flex -space-x-px rounded-md shadow-sm isolate" aria-label="Pagination">
                                <a @click="prevPage" :disabled="currentPage === 1"
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-l-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                                    id="previousPage">
                                    <span class="sr-only">Previous</span>
                                    <svg class="w-5 h-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path fill-rule="evenodd"
                                            d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </a>
                                <a v-for="page in visiblePages" :key="page" @click="clickPage(page - 1)"
                                    :aria-current="currentPage === page - 1 ? 'page' : null"
                                    :class="{ 'hover:bg-commu-pagination-hover': totalPages > page - 1 }"
                                    class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 duration-200 ease-in-out ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0 page-link">
                                    {{ page }}
                                </a><a @click="nextPage" :disabled="currentPage === totalPages"
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-r-md ring-1 ring-inset ring-gray-300 hover:bg-commu-pagination-hover focus:z-20 focus:outline-offset-0"
                                    id="nextPage">
                                    <span class="sr-only">Next</span>
                                    <svg class="w-5 h-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                        <path fill-rule="evenodd"
                                            d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
                                            clip-rule="evenodd" />
                                    </svg>
                                </a>
                            </nav>
                        </div>
                    </div>
                </div>
            </section>
            <p v-else>댓글이 없습니다.</p>
        </div>
        <!-- Container for demo purpose -->
        </p>
    </div>
</template>
<script setup>
import { defineProps, onMounted, ref, computed, reactive, watch } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { listMyComment, deleteUserComment } from '@/api/common';
const commentList = ref([]);
const userId = ref();
const Url = ref('');
const totalPages = ref(0);
const currentPage = ref(0);
const visiblePages = ref([1, 2, 3, 4, 5, 6, 7, 8, 9, 10]);
const pageCalculate = ref();
const selectedComments = reactive({});
const allChecked = ref(false);
const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
function prevPage() {
    if (currentPage.value > 0) {
        currentPage.value -= 1;
        listMyComments();
    }
}
function nextPage() {
    if (currentPage.value < totalPages.value - 1) {
        currentPage.value += 1;
        listMyComments();
    }
}
function clickPage(num) {
    if (num < totalPages.value) {
        currentPage.value = num;
        listMyComments();
        console.log("num : "+currentPage.value);
    }
}
async function listMyComments() {
    try {
        pageCalculate.value = Math.floor((currentPage.value) / 10);
        console.log(pageCalculate.value);
        pageCalculate.value = (pageCalculate.value) * 10;
        visiblePages.value = Array.from({ length: 10 }, (_, index) => pageCalculate.value + index + 1);
        const response = await listMyComment(props.user.id.value, currentPage.value);
        commentList.value = response.data.content;
        totalPages.value = response.data.totalPages;
        console.log("response.data : " + response.data.content);
        console.log("response.data.totalPages : " + response.data.totalPages);
        
        // commentList.value = response.data;
    } catch (error) {
        console.log(error);
    }
}
async function deleteSelected(articleId, commentId) {
    try {
        const response = await deleteUserComment(articleId, commentId);
        console.log('삭제 요청이 성공했습니다.');
        window.location.reload();
    } catch (error) {
        console.log("에러 : " + error);

    }
};
// const selectAll = () => {
//     // 전체 선택 버튼 클릭 시 모든 체크박스의 상태를 변경
//     allChecked.value = !allChecked.value;
//     commentList.value.forEach(comment => (comment.selected = allChecked.value));
// };
const formatCreatedAt = (createdAt) => {
    // createdAt이 문자열인 경우 Date 객체로 변환
    const date = typeof createdAt === 'string' ? new Date(createdAt) : createdAt;

    // Date 객체를 원하는 형식으로 변환
    const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ` +
        `${date.getHours()}시 ${date.getMinutes()}분 ${date.getSeconds()}초`;

    return formattedDate;
};
onMounted(() => {
    listMyComments();
});
watch(() => props.user.id.value, () => {
    listMyComments();
});
</script>