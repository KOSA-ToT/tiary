<template>
    <div class="w-48 p-8 mb-5 bg-white rounded-lg shadow-md">
        <!-- Album Cover -->
        <img src="https://telegra.ph/file/2acfcad8d39e49d95addd.jpg" alt="idk - Highvyn, Taylor Shin"
            class="w-32 h-32 mx-auto mb-4 rounded-full shadow-lg shadow-teal-50">
        <!-- Song Title -->
        <h2 class="text-xl font-semibold text-center">{{ props.user.nickname.value }}</h2>
        <!-- Artist Name -->
        <p class="text-sm text-center text-gray-600"></p>
        <!-- Music Controls -->
        <div class="flex items-center justify-center mt-6">
            <button class="p-2 bg-gray-200 rounded-full hover:bg-gray-300 focus:outline-none">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5">
                    <path
                        d="M7.712 4.819A1.5 1.5 0 0110 6.095v2.973c.104-.131.234-.248.389-.344l6.323-3.905A1.5 1.5 0 0119 6.095v7.81a1.5 1.5 0 01-2.288 1.277l-6.323-3.905a1.505 1.505 0 01-.389-.344v2.973a1.5 1.5 0 01-2.288 1.276l-6.323-3.905a1.5 1.5 0 010-2.553L7.712 4.82z" />
                </svg>

            </button>
            <button class="p-2 mx-4 bg-gray-200 rounded-full hover:bg-gray-300 focus:outline-none">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5">
                    <path
                        d="M5.75 3a.75.75 0 00-.75.75v12.5c0 .414.336.75.75.75h1.5a.75.75 0 00.75-.75V3.75A.75.75 0 007.25 3h-1.5zM12.75 3a.75.75 0 00-.75.75v12.5c0 .414.336.75.75.75h1.5a.75.75 0 00.75-.75V3.75a.75.75 0 00-.75-.75h-1.5z" />
                </svg>

            </button>
            <button class="p-2 bg-gray-200 rounded-full hover:bg-gray-300 focus:outline-none">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor" class="w-5 h-5">
                    <path
                        d="M3.288 4.819A1.5 1.5 0 001 6.095v7.81a1.5 1.5 0 002.288 1.277l6.323-3.905c.155-.096.285-.213.389-.344v2.973a1.5 1.5 0 002.288 1.276l6.323-3.905a1.5 1.5 0 000-2.553L12.288 4.82A1.5 1.5 0 0010 6.095v2.973a1.506 1.506 0 00-.389-.344L3.288 4.82z" />
                </svg>

            </button>
        </div>
        <!-- Progress Bar -->
        <div class="h-2 mt-6 rounded-full bg-purple-50">
            <div class="w-1/2 h-2 bg-pink-200 rounded-full"></div>
        </div>
</div></template>
<script setup>
    const props = defineProps({
        user: {
            type: Object,
            required: true,
        },
    });
</script>