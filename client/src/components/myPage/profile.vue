<template>
	<div class="flex mx-64 bg-purple-100 sm:px-6 lg:px-8">
		<div class="pt-20 ml-auto">
			<h1 class="text-xl tracking-tight text-gray-900">
				{{ User.nickname.value }}
			</h1>
		</div>
		<div class="flex px-4 py-6 max-w-7xl sm:px-6 lg:px-8 lg:block md:block ">
			<img class="w-24 h-24 rounded-full shadow-lg" src="https://telegra.ph/file/2acfcad8d39e49d95addd.jpg" alt="">
		</div>
	</div>
	<div class="flex mx-64 min-h-screen h-[calc(100%-1rem)] w-screen pt-10">
		<div class="pr-10">
			<MyProfile :user=User></MyProfile>
			<MyPageMenu :user=User></MyPageMenu>
		</div>


		<div>
			<router-view :user="User"/>
		</div>
	</div>
</template>
<script setup>
import MyPageMenu from '@/components/myPage/myMenu.vue'
import MyProfile from '@/components/myPage/myProfile.vue'
import { storeToRefs } from 'pinia';
import api from '@/router/api';
import { onMounted, ref } from 'vue';
import axios from "axios";
// const serverUrl = import.meta.env.VUE_APP_API_BASE_URL;

const User = {
	id: ref(),
	nickname: ref(),
	email: ref(),
	role: ref(),
	userPicture: ref(),
	userStatus: ref()
}
// async function getUser() {
// await api
// .get("/users/1")
// .then((response) => {
//     const data = response.data;
// 	User.id.value = data.id;
//     User.nickname.value = data.nickname;
//     User.email.value = data.email;
//     User.role.value = data.role;
//     User.userPicture.value = data.userPicture;
//     User.userStatus.value = data.userStatus;
// });
const getUser = async () => {
	
		await axios.get('http://localhost:8088/users/1')
			.then(response => {
				// 성공 처리
				const data = response.data;
				User.id.value = data.id;
				User.nickname.value = data.nickname;
				User.email.value = data.email;
				User.role.value = data.role;
				User.userPicture.value = data.userPicture;
				User.userStatus.value = data.userStatus;
				console.log(User.nickname.value);
				// console.log(serverUrl);
			})
			.catch(error => {
				// 에러 처리
				console.log("에러"+serverUrl);
			});
		

	};
onMounted(() => {
	getUser();
});

</script>