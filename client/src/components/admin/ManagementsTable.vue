<template>
  <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
    <table class="min-w-full divide-y divide-gray-200 overflow-x-auto mb-20">
      <thead class="bg-gray-50">
        <tr>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">

          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            닉네임
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            상태
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            글 수
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            이메일
          </th>
          <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
            변경
          </th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="approval in approvalList" :key="approval.userId">
          <td class="py-4 whitespace-nowrap pl-4">
            <div class="flex items-center">
              <div class="flex-shrink-0 h-10 w-10">
                <img class="h-10 w-10 rounded-full"
                  :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + approval.userPicture" alt="" />
              </div>
            </div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="flex grid-2">
            <img v-if="approval.status === 'Accepted'" class="mr-1" src="/images/writer.svg" />
            <div class="text-base text-gray-900 font-bold">
              {{ approval.nickname }}
            </div></div>
            <router-link :to="'/writer-page/' + approval.userId">
              <div class="text-xs text-gray-500">블로그 바로가기</div>
            </router-link>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <span v-if="approval.status === 'Approving'"
              class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
              {{ approval.status }}
            </span>
            <span v-else-if="approval.status === 'Accepted'"
              class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
              {{ approval.status }}
            </span>
            <span v-else class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">
              {{ approval.status }}
            </span>
          </td>
          <router-link :to="'/writer-page/' + approval.userId">
            <td class="px-6 pt-7 whitespace-nowrap text-sm text-red-500 flex grid-2" v-if="approval.articleCount < 5">
              {{ approval.articleCount }}
              <p class="text-xs pl-2 pt-0.5">미충족</p>
            </td>
            <td class="px-6 pt-7 whitespace-nowrap text-sm text-blue-500 flex grid-2" v-else>
              {{ approval.articleCount }}
              <p class="text-xs pl-2 pt-0.5">충족</p>
            </td>
          </router-link>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            {{ approval.email }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
            <a @click="accept(approval.userId, approval.email)" class="btn btn-blue btn-outline">수락</a>
            <a @click="reject(approval.userId, approval.email)" href="#"
              class="btn-reject btn-reject-red btn-reject-outline">반려</a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { listApprove, writerAccpet, writerReject, rejectEmailAuthReq, acceptEmailAuthReq } from '@/api/common';

const approvalList = ref([]);
const number = ref();
async function listApproval(userId) {
  try {
    const response = await listApprove();
    // const numberResponse = await numberPosts(userId);
    approvalList.value = response.data;
    // number.value = numberResponse.data;
  } catch (error) {
    console.log(error);
  }
}
async function accept(userId, userEmail) {
  try {
    const emailDto = {
      email: userEmail,
      task: "수락"
    };
    const response = await writerAccpet(userId);
    const request = await acceptEmailAuthReq(emailDto);
    window.location.reload();
    alert(request.data);
  } catch (error) {
    console.log(error);
  }
}
async function reject(userId, userEmail) {
  try {
    const emailDto = {
      email: userEmail,
      task: "거절"
    };
    const response = await writerReject(userId);
    const request = await rejectEmailAuthReq(emailDto);
    window.location.reload();
    alert(request.data);
  } catch (error) {
    console.log(error);
  }
}
onMounted(() => {
  listApproval();
});
</script>

<style scoped>
/* 추가된 스타일 */
.btn {
  margin-right: 10px;
  padding: 5px 12px;
  /* padding 수정 */
  min-width: 75px;
  /* 최소 너비 추가 */
  border: 1px solid #4872ff;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn:hover {
  border-color: #4872ff;
}

.btn-outline {
  background-color: transparent;
  color: #4872ff;
}

.btn-outline:hover {
  background-color: #4872ff;
  color: #fff;
}

.btn-blue {
  background-color: transparent;
  color: #4872ff;
}

.btn-reject {
  margin-right: 5px;
  padding: 5px 12px;
  /* padding 수정 */
  min-width: 75px;
  /* 최소 너비 추가 */
  border: 1px solid #ff6b6b;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn-reject:hover {
  border-color: #ff6b6b;
}

.btn-reject-outline {
  background-color: transparent;
  color: #ff6b6b;
}

.btn-reject-outline:hover {
  background-color: #ff6b6b;
  color: #fff;
}

.btn-reject-red {
  background-color: transparent;
  color: #ff6b6b;
}
</style>
