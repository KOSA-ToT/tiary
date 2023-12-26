<template>
    <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
        <p class="pb-6">
            <!-- Container for demo purpose -->
        <div class="container mx-auto my-16 md:px-6">
            <!-- Section: Design Block -->
            <section class="mb-24 text-center md:text-left">
                <h2 class="mb-12 ml-12 text-xl bg-gradient-to-tr font-extrabold from-pink-400 to-yellow-400 text-transparent bg-clip-text">작성 댓글</h2>
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
                            <h5 class="mb-3 text-base font-bold">{{ comment.content }}</h5>
                            <div
                                class="flex items-center justify-center mb-3 text-sm font-medium text-danger dark:text-danger-500 md:justify-start">
                                <svg height="25px" width="25px" version="1.1" id="Layer_1" class="mr-2"
                                    xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                    viewBox="0 0 503.467 503.467" xml:space="preserve" fill="#000000">
                                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                    <g id="SVGRepo_iconCarrier">
                                        <g transform="translate(5 5)">
                                            <path style="fill:#F2EDDA;"
                                                d="M417.4,33.4h8.533c17.067,0,34.133,17.067,34.133,34.133v358.4H33.4v-358.4 C33.4,50.467,50.467,33.4,67.533,33.4h25.6H417.4z M33.4,460.067h426.667v-34.133H33.4V460.067z M33.4,460.067h426.667 c0,17.067-17.067,34.133-34.133,34.133h-358.4C50.467,494.2,33.4,477.133,33.4,460.067z">
                                            </path>
                                            <path style="fill:#AAB1BA;"
                                                d="M118.733,50.467c9.387,0,17.067,7.68,17.067,17.067S128.12,84.6,118.733,84.6 s-17.067-7.68-17.067-17.067S109.347,50.467,118.733,50.467z M374.733,50.467c9.387,0,17.067,7.68,17.067,17.067 S384.12,84.6,374.733,84.6c-9.387,0-17.067-7.68-17.067-17.067S365.347,50.467,374.733,50.467z M204.067,50.467 c9.387,0,17.067,7.68,17.067,17.067S213.453,84.6,204.067,84.6S187,76.92,187,67.533S194.68,50.467,204.067,50.467z M289.4,50.467 c9.387,0,17.067,7.68,17.067,17.067S298.787,84.6,289.4,84.6s-17.067-7.68-17.067-17.067S280.013,50.467,289.4,50.467z">
                                            </path>
                                            <path style="fill:#FF7474;"
                                                d="M169.933,187v-4.267c9.387-8.533,21.333-12.8,34.133-12.8c17.92,0,33.28,9.387,42.667,23.04 c9.387-13.653,24.747-23.04,42.667-23.04c28.16,0,51.2,23.04,51.2,51.2s-34.133,59.733-34.133,59.733L246.733,340.6L187,280.867 c0,0-34.133-31.573-34.133-59.733c0-12.8,5.12-24.747,12.8-34.133H169.933z">
                                            </path>
                                        </g>
                                        <path style="fill:#51565F;"
                                            d="M430.933,503.467h-358.4c-19.627,0-38.4-18.773-38.4-38.4V72.533c0-19.627,18.773-38.4,38.4-38.4 H89.6c2.56,0,4.267,1.707,4.267,4.267s-1.707,4.267-4.267,4.267H72.533c-14.507,0-29.867,15.36-29.867,29.867v354.133H460.8V72.533 c0-14.507-15.36-29.867-29.867-29.867h-17.067c-2.56,0-4.267-1.707-4.267-4.267s1.707-4.267,4.267-4.267h17.067 c19.627,0,38.4,18.773,38.4,38.4v392.533C469.333,484.693,450.56,503.467,430.933,503.467z M42.667,469.333 c2.56,13.653,16.213,25.6,29.867,25.6h358.4c13.653,0,27.307-11.947,29.867-25.6H42.667z M42.667,460.8H460.8v-25.6H42.667V460.8z M251.733,349.867c-0.853,0-2.56,0-3.413-0.853l-59.733-59.733C186.88,288.427,153.6,256,153.6,226.133 c0-2.56,1.707-4.267,4.267-4.267c2.56,0,4.267,1.707,4.267,4.267c0,26.453,32.427,56.32,32.427,56.32l57.173,57.173l56.32-56.32 c0.853,0,33.28-30.72,33.28-56.32S320,180.053,294.4,180.053c-15.36,0-30.72,7.68-39.253,21.333c-1.707,2.56-5.12,2.56-6.827,0 c-8.533-12.8-23.04-21.333-39.253-21.333c-2.56,0-4.267-1.707-4.267-4.267s1.707-4.267,4.267-4.267 c16.213,0,32.427,7.68,42.667,19.627c10.24-12.8,26.453-19.627,42.667-19.627c30.72,0,55.467,24.747,55.467,55.467 c0,29.867-34.133,61.44-35.84,63.147l-17.92,17.92l17.92,17.92c0.853,0.853,0.853,1.707,0.853,3.413c0,1.706,0,2.56-0.853,3.413 c-1.707,1.707-4.267,1.707-5.973,0l-17.92-17.92l-34.987,34.987C254.293,349.867,252.587,349.867,251.733,349.867z M209.067,230.4 c-0.853,0-2.56,0-3.413-0.853l-33.28-33.28h-32.427c-2.56,0-4.267-1.707-4.267-4.267c0-2.56,1.707-4.267,4.267-4.267h29.867v-29.867 c0-2.56,1.707-4.267,4.267-4.267s4.267,1.707,4.267,4.267v32.427l33.28,33.28c1.707,1.707,1.707,4.267,0,5.973 C211.627,230.4,209.92,230.4,209.067,230.4z M379.733,93.867c-11.947,0-21.333-9.387-21.333-21.333 c0-10.24,7.68-18.773,17.067-20.48V4.267c0-2.56,1.707-4.267,4.267-4.267C382.293,0,384,1.707,384,4.267v47.787 c9.387,1.707,17.067,10.24,17.067,20.48C401.067,84.48,391.68,93.867,379.733,93.867z M379.733,59.733 c-6.827,0-12.8,5.973-12.8,12.8c0,6.827,5.973,12.8,12.8,12.8c6.827,0,12.8-5.973,12.8-12.8 C392.533,65.707,386.56,59.733,379.733,59.733z M294.4,93.867c-11.947,0-21.333-9.387-21.333-21.333 c0-10.24,7.68-18.773,17.067-20.48V4.267c0-2.56,1.707-4.267,4.267-4.267s4.267,1.707,4.267,4.267v47.787 c9.387,1.707,17.067,10.24,17.067,20.48C315.733,84.48,306.347,93.867,294.4,93.867z M294.4,59.733c-6.827,0-12.8,5.973-12.8,12.8 c0,6.827,5.973,12.8,12.8,12.8s12.8-5.973,12.8-12.8C307.2,65.707,301.227,59.733,294.4,59.733z M209.067,93.867 c-11.947,0-21.333-9.387-21.333-21.333c0-10.24,7.68-18.773,17.067-20.48V4.267c0-2.56,1.707-4.267,4.267-4.267 s4.267,1.707,4.267,4.267v47.787c9.387,1.707,17.067,10.24,17.067,20.48C230.4,84.48,221.013,93.867,209.067,93.867z M209.067,59.733c-6.827,0-12.8,5.973-12.8,12.8c0,6.827,5.973,12.8,12.8,12.8s12.8-5.973,12.8-12.8 C221.867,65.707,215.893,59.733,209.067,59.733z M123.733,93.867c-11.947,0-21.333-9.387-21.333-21.333 c0-10.24,7.68-18.773,17.067-20.48V4.267c0-2.56,1.707-4.267,4.267-4.267C126.293,0,128,1.707,128,4.267v47.787 c9.387,1.707,17.067,10.24,17.067,20.48C145.067,84.48,135.68,93.867,123.733,93.867z M123.733,59.733 c-6.827,0-12.8,5.973-12.8,12.8c0,6.827,5.973,12.8,12.8,12.8c6.827,0,12.8-5.973,12.8-12.8 C136.533,65.707,130.56,59.733,123.733,59.733z M345.6,42.667h-17.067c-2.56,0-4.267-1.707-4.267-4.267s1.707-4.267,4.267-4.267 H345.6c2.56,0,4.267,1.707,4.267,4.267S348.16,42.667,345.6,42.667z M260.267,42.667H243.2c-2.56,0-4.267-1.707-4.267-4.267 s1.707-4.267,4.267-4.267h17.067c2.56,0,4.267,1.707,4.267,4.267S262.827,42.667,260.267,42.667z M174.933,42.667h-17.067 c-2.56,0-4.267-1.707-4.267-4.267s1.707-4.267,4.267-4.267h17.067c2.56,0,4.267,1.707,4.267,4.267S177.493,42.667,174.933,42.667z">
                                        </path>
                                    </g>
                                </svg>
                                원글 : {{ comment.title }}
                            </div>
                            <p class="mb-6 text-sm text-neutral-500 dark:text-neutral-300">
                                <small>작성일시 : {{ formatCreatedAt(comment.createdAt) }}</small>
                            </p>

                        </div>
                    </router-link>
                    <button @click="deleteSelected(comment.articleId, comment.id)" :id="'button-option-' + comment.id"
                        class="h-8 px-6 mt-4 ml-auto mr-5 text-sm font-bold text-orange-500 uppercase transition-all duration-150 ease-linear bg-white border border-orange-500 border-solid rounded-full outline-none bg-opacity-40 hover:bg-orange-500 hover:text-white active:bg-orange-600 focus:outline-none">삭제</button>

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
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-l-md ring-1 ring-inset ring-gray-300 hover:bg-amber-100 focus:z-20 focus:outline-offset-0"
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
                                    :class="{ 'hover:bg-amber-100': totalPages > page - 1 }"
                                    class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 duration-200 ease-in-out ring-1 ring-inset ring-gray-300 focus:z-20 focus:outline-offset-0 page-link">
                                    {{ page }}
                                </a><a @click="nextPage" :disabled="currentPage === totalPages"
                                    class="relative inline-flex items-center px-2 py-2 text-gray-400 rounded-r-md ring-1 ring-inset ring-gray-300 hover:bg-amber-100 focus:z-20 focus:outline-offset-0"
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
        console.log("num : " + currentPage.value);
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