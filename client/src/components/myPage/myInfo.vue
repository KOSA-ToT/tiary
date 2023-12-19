<template>
    <main>
        <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
            <p class="pb-6">
            <div class="pb-12 border-b border-gray-900/10">
                <h2 class="text-base font-semibold leading-7 text-gray-900">내 정보</h2>
                <div class="grid grid-cols-1 mt-10 gap-x-6 gap-y-8 sm:grid-cols-6">

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
                                <button type="button" @click="editNickName()"
                                    class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경</button>

                            </div>
                            <p v-if="isChangedNickname">{{ msgNickname }}</p>
                        </div>
                    </div>
                    <div class="pl-3 col-span-full">
                        <label for="photo" class="block text-sm font-medium leading-6 text-gray-900">프로필 이미지</label>
                        <div class="items-center mt-2">
                            <form enctype="multipart/form-data" id="profileImgFile">
                                <input type="file" @change="handleFileChange" style="display: none" ref="fileInput" />
                                <div @click="$refs.fileInput.click()">
                                    <svg v-if="!previewImage && user.userPicture.value == null"
                                        class="w-12 h-12 text-gray-300 cursor-pointer" viewBox="0 0 24 24"
                                        fill="currentColor" aria-hidden="true">
                                        <path fill-rule="evenodd"
                                            d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
                                            clip-rule="evenodd" />
                                    </svg>

                                    <!-- 이미지 미리보기 -->
                                    <img v-else-if="previewImage" :src="previewImage" alt="Preview"
                                        class="w-12 h-12 text-gray-300 rounded-full cursor-pointer"
                                        style="object-fit: cover;" />
                                    <img v-else-if="!previewImage && user.userPicture.value != null"
                                        :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + user.userPicture.value"
                                        alt="" class="w-12 h-12 text-gray-300 rounded-full cursor-pointer"
                                        style="object-fit: cover;" />
                                </div>
                                <button type="button" @click="changeProfileImg"
                                    class="rounded-md bg-white px-2.5 py-1.5 text-sm font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경하기</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div @click="modalOpen = true">
                <h2 class="mb-24 text-base font-semibold leading-7 text-gray-900"><u>Tiary</u>에서 탈퇴하기</h2>
            </div>
            </p>


        </div>
    </main>
    <div class="flex items-center justify-center">
        <div v-if="modalOpen" class="fixed inset-0 transition-opacity" aria-hidden="true" @click="modalOpen = false">
            <div class="absolute inset-0 bg-gray-500 opacity-75"></div>
        </div>

        <div v-if="modalOpen" class="fixed inset-0 z-10 overflow-y-auto">
            <div class="flex items-end justify-center min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
                <div class="inline-block w-full overflow-hidden text-left align-bottom transition-all transform bg-white rounded-lg shadow-xl sm:my-8 sm:align-middle sm:max-w-sm sm:w-full"
                    role="dialog" aria-modal="true" aria-labelledby="modal-headline">
                    <div class="px-4 pt-5 pb-4 bg-white sm:p-6 sm:pb-4">
                        <div class="sm:flex sm:items-start">
                            <div
                                class="flex items-center justify-center flex-shrink-0 w-12 h-12 mx-auto bg-red-100 rounded-full sm:mx-0 sm:h-10 sm:w-10">
                                <svg width="64px" height="64px" class="w-6 h-6 text-red-600" stroke="currentColor"
                                    fill="none" viewBox="0 0 24.00 24.00" xmlns="http://www.w3.org/2000/svg"
                                    stroke-width="0.45600000000000007">
                                    <!-- ... (SVG paths) ... -->
                                </svg>
                            </div>
                            <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <div class="flex">
                                    <h3 class="text-lg font-semibold leading-6 text-gray-900" id="modal-headline">정말로
                                        탈퇴하시겠습니까?</h3>
                                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
                                        stroke-width="1.5" stroke="currentColor" class="w-6 h-6 ml-1">
                                        <!-- ... (SVG paths) ... -->
                                    </svg>
                                </div>
                                <div class="mt-2">
                                    <p class="ml-3 text-sm text-gray-500">탈퇴 후에 번복할 수 없습니다.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="justify-center px-4 py-3 bg-gray-50 sm:px-6 sm:flex sm:flex-row-reverse">
                        <button @click="handleWithdrawal" type="button"
                            class="inline-flex justify-center w-full px-4 py-2 text-base font-medium text-white bg-red-500 border border-transparent rounded-md shadow-sm hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:ml-3 sm:w-auto sm:text-sm">탈퇴</button>
                        <button @click="modalOpen = false" type="button"
                            class="inline-flex justify-center w-full px-4 py-2 mt-3 mr-20 text-base font-medium text-gray-700 bg-white border border-gray-300 rounded-md shadow-sm hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-200 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">취소</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup>
import { onMounted, ref, watch } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { editNickname, handleWithDrawal, changeProfileImage } from '@/api/common';
import { useAuthStore } from '@/stores/auth';
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
const modalOpen = ref(false);
const route = useRoute();
const router = useRouter();
const previewImage = ref(null);
const token = localStorage.getItem('Authorization');
const authStore = useAuthStore();
const { currentUser } = authStore;
async function editNickName() {
    // 토큰이 없으면 요청을 보내지 않음
    if (!token) {
        alert('로그인을 해주세요');
        router.push("/");
    }
    else {
        try {
            const response = await editNickname(props.user.id.value, { nickname: props.user.nickname.value });
            isChangedNickname.value = true;
            if (response.status === 205) {
                msgNickname.value = "변경되었습니다.";
            }
        } catch (error) {
            console.log("에러 : " + error);
            if (error.response && error.response.status === 409) {
                isChangedNickname.value = true;
                msgNickname.value = "이미 있는 닉네임입니다.";
            }
        }
    }

}
async function handleWithdrawal() {
    try {
        const response = await handleWithDrawal(props.user.id.value, { nickname: props.user.nickname.value });
        modalOpen.value = false;
        authStore.logout();
        router.push("/");
    } catch (error) {
        console.log("에러 : " + error);

    }
};

async function changeProfileImg() {
    const profileImgForm = document.querySelector("#profileImgFile");
    const formData = new FormData();

    formData.append("profileImg", profileImgForm[0].files[0]);
    if (profileImgForm[0].files[0] == null) {
        alert("선택된 이미지가 없습니다.");
        return false;
    }
    try {
        const response = await changeProfileImage(props.user.id.value, formData);
        console.log("업로드 완료");
        location.reload();
    } catch (error) {
        console.log("에러 : " + error);

    }
}
// 파일 선택 이벤트 핸들러
const handleFileChange = (event) => {
    const file = event.target.files[0];

    if (file) {
        // 이미지 미리보기 업데이트
        const reader = new FileReader();
        reader.onload = () => {
            previewImage.value = reader.result;
        };
        reader.readAsDataURL(file);
    } else {
        // 파일 선택이 해제된 경우 미리보기 초기화
        previewImage.value = null;
    }
};
onMounted(() => {
	console.log("currentUser : "+currentUser);
});
</script>