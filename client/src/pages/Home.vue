<template>
    <button @click="openModal('로그인')">로그인 버튼</button>
    <button @click="openModal('회원가입')">회원가입 버튼</button>
    <Thumbnails></Thumbnails>
    <Band></Band>
    <Card></Card>
    <Footer></Footer>
    <UserModal
        v-if="userVars.isShowModal"
        v-model:task="userVars.task"
        v-model:isShowModal="userVars.isShowModal"
        >
    </UserModal>
</template>

<script setup>
import {ref } from 'vue'

import Footer from '@/components/Footer.vue'
import Thumbnails from '@/components/Thumbnails.vue'
import Card from '@/components/Card.vue'
import Band from '@/components/Band.vue'
import UserModal from '@/components/UserModal.vue'

// 유저
const userVars = ref({
    task: '',
    isShowModal: false
})

function openModal(tasks) {
    userVars.value.task = tasks
    userVars.value.isShowModal = true;
}

</script>