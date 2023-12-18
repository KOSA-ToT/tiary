<template>
	<div>
    <Header :threshold="100"></Header>
	<div class="flex items-center">
		<section class="w-full pt-5 bg-center bg-cover py-15"
			style="background-image: url('https://source.unsplash.com/random');">
			<div class="container mx-auto text-center text-white">
				<div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
					<div class="flex items-center justify-end px-12">
						<h1 class="mt-6 mr-6 text-2xl font-medium text-white">{{ User.nickname.value }}</h1>
						<img class="w-24 h-24 mb-6 rounded-full shadow-lg"
							:src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + User.userPicture.value" alt="">
					</div>
				</div>
				
				<!-- <p class="mb-12 text-xl">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam viverra euismod odio,
				gravida pellentesque urna varius vitae.</p> -->
				<!-- <a href="#" class="px-12 py-4 text-white bg-indigo-500 rounded-full hover:bg-indigo-600">구독</a> -->
			</div>
		</section>
	</div>
	<div>
		<router-view :user="User" />
	</div>
	<Footer></Footer>
</div>
</template>
<script setup>
import Header from '@/components/Header.vue';
import Footer from '@/components/Footer.vue';
import MyPageMenu from '@/components/myPage/myMenu.vue'
import MyProfile from '@/components/myPage/myProfile.vue'
import { storeToRefs } from 'pinia';
import api from '@/router/api';
import { onBeforeMount, onMounted, ref } from 'vue';
import axios from "axios";
import { getUser } from '@/api/common';
import { useRoute } from 'vue-router';
const id = ref(null);
  const route = useRoute();
  
// const serverUrl = import.meta.env.VUE_APP_API_BASE_URL;

const User = {
	id: ref(),
	nickname: ref(),
	email: ref(),
	role: ref(),
	userPicture: ref(),
	userStatus: ref()
}

async function getUsers(props) {
	try {
		console.log("id.value : "+id.value);
		const response = await getUser(id.value);
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
onBeforeMount(()=>{
	id.value = route.params.id;
});
</script>