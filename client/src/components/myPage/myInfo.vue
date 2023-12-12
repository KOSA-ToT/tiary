<template>
    <div class="px-10 pt-5 space-y-12">
        <div class="pb-12 border-b border-gray-900/10">
            <h2 class="text-base font-semibold leading-7 text-gray-900">내 정보</h2>
            <div class="grid grid-cols-1 mt-10 gap-x-6 gap-y-8 sm:grid-cols-6">
                <div class="pl-3 sm:col-span-4">
                    <label for="email-address-icon"
                        class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">이메일</label>
                    <div class="relative">
                        <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 16">
                                <path
                                    d="m10.036 8.278 9.258-7.79A1.979 1.979 0 0 0 18 0H2A1.987 1.987 0 0 0 .641.541l9.395 7.737Z" />
                                <path
                                    d="M11.241 9.817c-.36.275-.801.425-1.255.427-.428 0-.845-.138-1.187-.395L0 2.6V14a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V2.5l-8.759 7.317Z" />
                            </svg>
                        </div>
                        <div class="flex">
                            <input type="text" id="email-address-icon"
                                class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-80 ps-10 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                                placeholder="이메일을 입력해주세요" v-model="props.user.email.value">
                            <button type="button" @click="editEmail()"
                                class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경</button>
                        </div>
                        <p v-if="isChanged">{{msgEmail}}</p>
                    </div>

                </div>
                <div class="pl-3 sm:col-span-4">
                    <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                    <div class="relative">
                        <div class="absolute inset-y-0 start-0 flex items-center ps-3.5 pointer-events-none">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                                class="w-5 h-5 text-gray-500 dark:text-gray-400">
                                <path
                                    d="M10 8a3 3 0 100-6 3 3 0 000 6zM3.465 14.493a1.23 1.23 0 00.41 1.412A9.957 9.957 0 0010 18c2.31 0 4.438-.784 6.131-2.1.43-.333.604-.903.408-1.41a7.002 7.002 0 00-13.074.003z" />
                            </svg>
                        </div>
                        <div class="flex mt-2">

                            <input type="text" name="first-name" id="first-name" autocomplete="given-name"
                                class="block py-2 pl-10 text-gray-600 border-0 rounded-md shadow-sm w-80 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                                v-model="props.user.nickname.value" />
                            <button type="button" @click="editNickname()"
                                class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경</button>

                        </div>
                        <p v-if="isChangedNickname">{{ msgNickname }}</p>
                    </div>
                </div>
                <div class="pl-3 col-span-full">
                    <label for="photo" class="block text-sm font-medium leading-6 text-gray-900">프로필 이미지</label>
                    <div class="flex items-center mt-2 gap-x-3">
                        <svg class="w-12 h-12 text-gray-300" viewBox="0 0 24 24" fill="currentColor" aria-hidden="true">
                            <path fill-rule="evenodd"
                                d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
                                clip-rule="evenodd" />
                    </svg>
                    <button type="button"
                        class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경하기</button>
                </div>
            </div>
        </div>
    </div>

    <div @click="handleWithdrawal">
        <h2 class="text-base font-semibold leading-7 text-gray-900"><u>Tiary</u>에서 탈퇴하기</h2>
    </div>
</div></template>
<script setup>
import { ref } from 'vue';
import axios from 'axios';

const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
const changedNickName = ref("");
let msgEmail = ref("");
let msgNickname = ref("");
let isChanged = ref(false);
let isChangedNickname = ref(false);
async function editEmail() {
    await axios
        .patch("http://localhost:8088/users/1/updateEmail", {
            email: props.user.email.value,
        })
        .then((response) => {
            isChanged.value=true;
            // changedNickName.value = response.data.nickname;
            // console.log(changedNickName.value);
            if(response.status === 205){
                msgEmail.value="변경되었습니다.";
            }
            console.log("status: "+response.status);
            console.log("data : "+response.data);
            console.log("isChanged: "+isChanged.value);
        })
        .catch(error => {
            // alert(error);
            if (error.response && error.response.status === 409) {
                isChanged.value = true;
                msgEmail.value = "이미 있는 이메일입니다.";
            }
        });
}
async function editNickname() {
    await axios
        .patch("http://localhost:8088/users/1/updateNickname", {
            nickname: props.user.nickname.value,
        })
        .then((response) => {
            isChangedNickname.value=true;
            // changedNickName.value = response.data.nickname;
            // console.log(changedNickName.value);
            if(response.status === 205){
                msgNickname.value="변경되었습니다.";
            }
        })
        .catch(error => {
            if (error.response && error.response.status === 409) {
                isChangedNickname.value = true;
                msgNickname.value = "이미 있는 닉네임입니다.";
            }
        });
}
async function handleWithdrawal() {
    await axios
        .patch("http://localhost:8088/users/1/inactive",{
            nickname: props.user.nickname.value,
        })
        .then((response)=>{
            console.log("inactive");
        })
        .catch(error=>{
            console.log("에러");
        }
        );
    console.log('클릭 이벤트 발생!');
};
</script>