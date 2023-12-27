<template>
    <main>
        <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
            <p class="pb-6">
            <div class="pb-12 ml-12 border-b border-gray-900/10">
                <h2 class="text-base font-semibold leading-7 text-gray-900">내 정보</h2>
                <div class="grid grid-cols-1 mt-10 gap-x-6 gap-y-8 sm:grid-cols-6">

                    <div class="pl-3 sm:col-span-4">
                        <label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">아이디</label>
                        <div class="relative">
                            <div class="absolute inset-y-0 start-0 items-center ps-3.5 pointer-events-none">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                                    class="w-5 h-5 mt-2.5 text-gray-500 dark:text-gray-400">
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
                            <div class="mt-2 ml-2 text-xs text-pink-700">{{ msgNickname }}</div>
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
                                    class="rounded-md bg-white px-2.5 py-1.5 text-sm mt-2 font-normal text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50">변경하기</button>
                            </form>
                        </div>
                    </div>
                </div>
                <h2 class="text-base font-semibold leading-7 text-gray-900 mt-10">작가 신청 상태</h2>
                <p class="block text-sm font-medium leading-6 text-gray-900 ml-4 mt-8">{{ writerMsg }}</p>
            </div>
            <div class="mt-4 mb-24 ml-12">
                <div @click="modalOpen = true">
                    <span class="text-base font-semibold leading-7 text-gray-900"><u>Tiary</u>에서 탈퇴하기</span>
                </div>
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
                                <svg height="64px" width="64px" version="1.1" id="Layer_1"
                                    xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"
                                    viewBox="0 0 512.001 512.001" xml:space="preserve" fill="#000000">
                                    <g id="SVGRepo_bgCarrier" stroke-width="0"></g>
                                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g>
                                    <g id="SVGRepo_iconCarrier">
                                        <circle style="fill:#F7B239;" cx="256.005" cy="256.004" r="246.855"></circle>
                                        <g>
                                            <path style="fill:#E09B2D;"
                                                d="M126.307,385.694c-88.801-88.802-95.798-228.426-20.998-325.241 c-8.286,6.401-16.258,13.399-23.858,20.999c-96.401,96.401-96.401,252.698,0,349.099s252.698,96.401,349.099,0 c7.599-7.599,14.597-15.573,20.999-23.858C354.733,481.492,215.109,474.495,126.307,385.694z">
                                            </path>
                                            <path style="fill:#E09B2D;"
                                                d="M231.898,360.393c-3.406,0-6.676-1.91-8.252-5.185c-2.191-4.553-0.276-10.021,4.277-12.212 c18.369-8.838,37.786-8.838,56.151,0c4.553,2.191,6.469,7.659,4.277,12.213c-2.191,4.553-7.661,6.467-12.212,4.277 c-13.177-6.341-27.107-6.34-40.284,0C234.58,360.102,233.229,360.393,231.898,360.393z">
                                            </path>
                                        </g>
                                        <path style="fill:#2BA5F7;"
                                            d="M77.509,413.974L77.509,413.974c-22.743-22.743-22.743-59.616,0-82.358l41.179-41.179l41.179,41.179 c22.742,22.742,22.742,59.616,0,82.358l0,0C137.125,436.716,100.253,436.716,77.509,413.974z">
                                        </path>
                                        <path style="fill:#2197D8;"
                                            d="M106.787,413.974L106.787,413.974c-22.742-22.742-22.742-59.616,0-82.358l26.541-26.541 l-14.639-14.639l-41.179,41.179c-22.742,22.743-22.742,59.616,0,82.358l0,0c15.127,15.127,36.503,20.179,55.818,15.182 C123.605,426.641,114.402,421.589,106.787,413.974z">
                                        </path>
                                        <path
                                            d="M256,0C114.842,0,0.001,114.841,0.001,256.001S114.842,512.001,256,512.001c141.159,0,256.001-114.841,256.001-256.001 S397.16,0,256,0z M83.979,338.085l34.71-34.71l34.71,34.71c19.138,19.138,19.138,50.28,0,69.419 c-19.138,19.138-50.28,19.14-69.419,0C64.84,388.365,64.84,357.224,83.979,338.085z M256,493.701 c-57.82,0-110.877-20.761-152.129-55.207c4.875,1.092,9.845,1.655,14.817,1.655c17.256,0,34.512-6.568,47.65-19.706 c26.274-26.274,26.274-69.024,0-95.298l-41.179-41.179c-1.715-1.716-4.043-2.68-6.469-2.68c-2.426,0-4.754,0.964-6.469,2.68 l-41.179,41.179C56.836,339.351,50.328,358.371,51.482,377c-21.065-35.467-33.182-76.843-33.182-121 C18.3,124.932,124.932,18.299,256,18.299c131.069,0,237.702,106.632,237.702,237.702S387.069,493.701,256,493.701z">
                                        </path>
                                        <path
                                            d="M174.858,149.552c-31.233,1.474-58.686,18.813-73.44,46.381c-2.384,4.455-0.705,10,3.75,12.385 c1.375,0.736,2.852,1.085,4.31,1.085c3.266,0,6.427-1.754,8.075-4.833c11.686-21.836,33.43-35.569,58.168-36.736 c5.048-0.238,8.946-4.524,8.708-9.57C184.191,153.216,179.899,149.296,174.858,149.552z">
                                        </path>
                                        <path
                                            d="M337.142,149.552c-5.053-0.245-9.331,3.66-9.57,8.708c-0.239,5.048,3.66,9.333,8.708,9.57 c24.738,1.167,46.485,14.9,58.169,36.736c1.649,3.082,4.808,4.835,8.075,4.835c1.455,0,2.934-0.349,4.309-1.085 c4.454-2.385,6.134-7.93,3.749-12.384C395.831,168.365,368.376,151.026,337.142,149.552z">
                                        </path>
                                        <path
                                            d="M314.46,318.618c-32.779-19.764-84.136-19.764-116.918,0c-4.327,2.609-5.72,8.232-3.111,12.559 c2.609,4.327,8.233,5.72,12.559,3.111c26.565-16.017,71.453-16.017,98.022,0c1.477,0.891,3.106,1.315,4.715,1.315 c3.101,0,6.125-1.577,7.844-4.426C320.18,326.85,318.787,321.227,314.46,318.618z">
                                        </path>
                                        <path
                                            d="M146.724,203.59c-18.666,0-33.852,15.186-33.852,33.852s15.186,33.852,33.852,33.852s33.852-15.186,33.852-33.852 S165.39,203.59,146.724,203.59z">
                                        </path>
                                        <path
                                            d="M365.276,203.59c-18.666,0-33.852,15.186-33.852,33.852s15.186,33.852,33.852,33.852s33.852-15.186,33.852-33.852 S383.942,203.59,365.276,203.59z">
                                        </path>
                                        <g>
                                            <circle style="fill:#FFFFFF;" cx="158.812" cy="230.105" r="9.15"></circle>
                                            <circle style="fill:#FFFFFF;" cx="377.181" cy="230.105" r="9.15"></circle>
                                        </g>
                                    </g>
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
import { onMounted, ref, watch, onBeforeMount } from 'vue';
import axios from 'axios';
import { useRoute, useRouter } from 'vue-router';
import { editNickname, handleWithDrawal, changeProfileImage, writerConfirm } from '@/api/common';
import { useAuthStore } from '@/stores/auth';
const props = defineProps({
    user: {
        type: Object,
        required: true,
    },
});
const changedNickName = ref("");
let msgEmail = ref("");
const msgNickname = ref(" ");
let isChanged = ref(false);
let isChangedNickname = ref(false);
const modalOpen = ref(false);
const route = useRoute();
const router = useRouter();
const previewImage = ref(null);
const token = localStorage.getItem('Authorization');
const authStore = useAuthStore();
const { currentUser } = authStore;
const writerMsg = ref("작가 신청을 하지 않았습니다.");
const ifWriter = ref(false);
const usersId = ref();
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
async function approval(userId) {
  
      try {
        const response = await writerConfirm(userId);
        console.log("writerConfirm : " + response.data);
        if (response.data === 'Approving') {
          writerMsg.value = "작가 신청이 완료되었습니다. 빠른 시일 안에 안내 드리겠습니다.";
          console.log("작가 신청이 완료되었습니다. 빠른 시일 안에 안내 드리겠습니다.");
        }
        else if (response.data === 'Accepted') {
          writerMsg.value = "축하합니다. 작가가 되었습니다.";
          console.log("축하합니다. 작가가 되었습니다.");
        //   ifWriter.value = true;
        }
        else if (response.data === 'Rejected') {
          writerMsg.value = "결과가 이메일로 전송되었습니다.";
          console.log("결과가 이메일로 전송되었습니다.");
        }
      } catch (error) {
        console.log("에러 : " + error + error.response);
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
    console.log("currentUser : " + currentUser);
    approval(usersId.value);
});
onBeforeMount(()=>{
	usersId.value = route.params.id;
});
</script>