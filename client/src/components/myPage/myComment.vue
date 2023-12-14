<template>
    <!-- Container for demo purpose -->
    <div class="container mx-auto my-24 md:px-6">
        <!-- Section: Design Block -->
        <section class="mb-32 text-center md:text-left">
            <h2 class="mb-12 text-lg font-bold text-zinc-600">작성 댓글</h2>
            <button class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">삭제</button>
            <button @click="selectAll" class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">전체 선택</button>
            <div class="flex flex-wrap mb-6" v-for="comment in commentList" :key="comment.id">
                <input type="checkbox" :id="'react-option-' + comment.id">
                    <div class="w-full px-3 mb-6 ml-auto shrink-0 grow-0 basis-auto md:mb-0 md:w-3/12">
                        <div class="relative mb-6 overflow-hidden bg-no-repeat bg-cover rounded-lg shadow-lg dark:shadow-black/20"
                            data-te-ripple-init data-te-ripple-color="light">
                            <a href="#!">
                                <div
                                    class="absolute top-0 right-0 bottom-0 left-0 h-full w-full overflow-hidden bg-fixed opacity-0 transition duration-300 ease-in-out hover:opacity-100 bg-[hsla(0,0%,98.4%,.15)]">
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="w-full px-3 mb-6 mr-auto shrink-0 grow-0 basis-auto md:mb-0 md:w-9/12 xl:w-7/12">
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
            </div>

        </section>
        <!-- Section: Design Block -->
    </div>
    <!-- Container for demo purpose -->
</template>
<script setup>
import { defineProps, onMounted, ref, computed, reactive } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
const commentList = ref([]);
const userId = ref();
const Url = ref('');
const selectedComments = reactive({});
const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});

const listMyComment = async () => {
    await axios.get(`http://localhost:8088/users/1/comments`)
        .then(response => {
            // 성공 처리
            commentList.value = response.data;
            console.log("불러오기 완료" + commentList.value);
        })
        .catch(error => {
            // 에러 처리
            console.log(error);
        });

};
const selectAll = () => {
  // 전체 선택 버튼 클릭 시 모든 체크박스의 상태를 변경
  commentList.value.forEach((comment) => {
    selectedComments[comment.id] = true;
  });
};
const formatCreatedAt = (createdAt) => {
    // createdAt이 문자열인 경우 Date 객체로 변환
    const date = typeof createdAt === 'string' ? new Date(createdAt) : createdAt;

    // Date 객체를 원하는 형식으로 변환
    const formattedDate = `${date.getFullYear()}년 ${date.getMonth() + 1}월 ${date.getDate()}일 ` +
        `${date.getHours()}시 ${date.getMinutes()}분 ${date.getSeconds()}초`;

    return formattedDate;
};
onMounted(() => {
    listMyComment();
});
</script>