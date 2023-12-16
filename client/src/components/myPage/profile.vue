<template>
	<div class="flex items-center">
		<section class="w-full py-32 bg-center bg-cover"
			style="background-image: url('https://source.unsplash.com/random');">
			<div class="container mx-auto text-center text-white">
				<div class="flex items-center justify-center">
					<img class="w-24 h-24 mb-6 rounded-full shadow-lg"
						:src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + User.userPicture.value" alt="">
				</div>

				<h1 class="mb-6 text-5xl font-medium">{{ User.nickname.value }}</h1>
				<!-- <p class="mb-12 text-xl">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra euismod odio,
				gravida pellentesque urna varius vitae.</p> -->
				<!-- <a href="#" class="px-12 py-4 text-white bg-indigo-500 rounded-full hover:bg-indigo-600">구독</a> -->
			</div>
		</section>
	</div>
	<div>
		<router-view :user="User"/>
	</div>
</template>
<script setup>
import MyPageMenu from '@/components/myPage/myMenu.vue'
import MyProfile from '@/components/myPage/myProfile.vue'
import { storeToRefs } from 'pinia';
import api from '@/router/api';
import { onMounted, ref } from 'vue';
import axios from "axios";
import { getUser } from '@/api/common';
// const serverUrl = import.meta.env.VUE_APP_API_BASE_URL;

const User = {
	id: ref(),
	nickname: ref(),
	email: ref(),
	role: ref(),
	userPicture: ref(),
	userStatus: ref()
}

async function getUsers() {
	try {
		const response = await getUser(2);
		const data = response.data;
		User.id.value = data.id;
		User.nickname.value = data.nickname;
		User.email.value = data.email;
		User.role.value = data.role;
		User.userPicture.value = data.userPicture;
		User.userStatus.value = data.userStatus;
		console.log(User.nickname.value);
		console.log("User.id.value : " + User.id.value);
	} catch (error) {
		console.log(error);
	}
}
onMounted(() => {
	getUsers();
});

</script>