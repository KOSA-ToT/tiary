<template>
    <div class="max-w-sm p-3 bg-white divide-y divide-gray-200 rounded-lg drop-shadow-xl">
        <div aria-label="header" class="flex items-center p-4 space-x-4">
            <div aria-label="avatar" class="flex items-center mr-auto space-x-4 text-lg">
                My Page
            </div>

            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
            <path d="M8 9l4 -4l4 4"></path>
            <path d="M16 15l-4 4l-4 -4"></path>

        </div>
        <div aria-label="navigation" class="py-2">
            <nav class="grid gap-1">
                <router-link to="/mypage/1"
                    class="flex items-center w-full px-4 py-3 space-x-3 leading-6 text-gray-600 rounded-md text-md focus:outline-none hover:bg-gray-100">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round"
                            d="M17.982 18.725A7.488 7.488 0 0012 15.75a7.488 7.488 0 00-5.982 2.975m11.963 0a9 9 0 10-11.963 0m11.963 0A8.966 8.966 0 0112 21a8.966 8.966 0 01-5.982-2.275M15 9.75a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>

                    <span>내 정보</span>
                </router-link>
                <router-link to="/mypage/subscriber/1"
                    class="flex items-center w-full px-4 py-3 space-x-3 leading-6 text-gray-600 rounded-md text-md focus:outline-none hover:bg-gray-100">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round"
                            d="M17.593 3.322c1.1.128 1.907 1.077 1.907 2.185V21L12 17.25 4.5 21V5.507c0-1.108.806-2.057 1.907-2.185a48.507 48.507 0 0111.186 0z" />
                    </svg>

                    <span>구독 목록</span>
                </router-link>
                <router-link to="/mypage/post/1"
                    class="flex items-center w-full px-4 py-3 space-x-3 leading-6 text-gray-600 rounded-md text-md focus:outline-none hover:bg-gray-100">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round"
                            d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                    </svg>

                    <span>내가 쓴 글</span>
                </router-link>
                <router-link to="/mypage/comment/1"
                    class="flex items-center w-full px-4 py-3 space-x-3 leading-6 text-gray-600 rounded-md text-md focus:outline-none hover:bg-gray-100">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                        stroke="currentColor" class="w-6 h-6">
                        <path stroke-linecap="round" stroke-linejoin="round"
                            d="M8.625 9.75a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H8.25m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0H12m4.125 0a.375.375 0 11-.75 0 .375.375 0 01.75 0zm0 0h-.375m-13.5 3.01c0 1.6 1.123 2.994 2.707 3.227 1.087.16 2.185.283 3.293.369V21l4.184-4.183a1.14 1.14 0 01.778-.332 48.294 48.294 0 005.83-.498c1.585-.233 2.708-1.626 2.708-3.228V6.741c0-1.602-1.123-2.995-2.707-3.228A48.394 48.394 0 0012 3c-2.392 0-4.744.175-7.043.513C3.373 3.746 2.25 5.14 2.25 6.741v6.018z" />
                    </svg>

                <span>내가 쓴 댓글</span>
            </router-link>
        </nav>
    </div>
</div></template>
<script setup>
import { ref } from 'vue'
const isClick = ref(false);
const mypageButton = () => {
    isClick.value = true;
};
</script>
<style>
.tab[aria-current="page"] {
    background-color: #ffeefb;
    /* 현재 페이지 버튼의 배경색을 변경 */
    color: #c03ac5;
    /* 현재 페이지 버튼의 텍스트 색상을 변경 */
}</style>