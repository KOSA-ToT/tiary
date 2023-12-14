<template>
<!-- source:https://codepen.io/owaiswiz/pen/jOPvEPB  tabindex="-1"--> 
<div class="min-h-screen bg-black/50 overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 text-gray-900 flex justify-center">
    <div class="max-w-screen-xl m-0 sm:m-10 bg-white shadow sm:rounded-lg flex justify-center flex-1">
        <div class="lg:w-1/2 xl:w-5/12 sm:p-12">
            <!-- <slot name="closeModalButton"></slot> -->
            <button 
                @click="$emit('update:isShowModal', false)"
                type="button"
                class="absolute top-15 right-15 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm ml-auto inline-flex items-center popup-close"><svg
                    aria-hidden="true" class="w-5 h-5" fill="#c6c7c7" viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                        d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                        cliprule="evenodd"></path>
                </svg>
                <span class="sr-only">Close Modal</span>
            </button>
            <!-- 로고 -->
            <img src="/images/logo.png"
                class="w-32 mx-auto" />
            <div class="mt-2 flex flex-col items-center">
                <h1 class="text-2xl xl:text-3xl font-extrabold">
                    {{ task }}
                </h1>
                <LoginOrReg v-if="!nickCheck" :task='task'></LoginOrReg>
                <NickNameCheckComp v-else :task='task'></NickNameCheckComp>
            </div>
        </div>
        <div class="flex-1 bg-indigo-100 text-center rounded-r-lg lg:flex sm:flex">
            <img src="/images/dog.jpg" class="rounded-r-lg">
        </div>
    </div>
</div>
</template>

<script setup>
import {ref } from 'vue'
import LoginOrReg from './LoginOrReg.vue';
import NickNameCheckComp from './NickNameCheckComp.vue';

const props = defineProps(['task', 'nickCheck'])
defineEmits(['update:isShowModal'])

const email = ref('')
const nickCheck = ref(props.nickCheck)

function submitEmail(task){
    console.log(task);
    console.log(email.value);
}

</script>