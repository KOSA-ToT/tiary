<template>
    <div class="w-48 p-8 mb-5 bg-white rounded-lg shadow-md">
        <!-- Album Cover -->
        <img :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/'+user.userPicture.value" alt=""
            class="w-32 h-32 mx-auto mb-4 rounded-full shadow-lg shadow-teal-50">
        <!-- Song Title -->
        <h2 class="text-xl font-semibold text-center">{{ props.user.nickname.value }}</h2>
        <!-- Artist Name -->
        <p class="text-sm text-center text-gray-600"></p>
</div></template>
<script setup>
    const props = defineProps({
        user: {
            type: Object,
            required: true,
        },
    });
</script>