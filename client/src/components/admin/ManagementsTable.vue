<template>
  <div class="max-w-screen-md px-4 mx-auto mt-12 text-lg leading-relaxed text-gray-700 lg:px-0">
    <table class="min-w-full divide-y divide-gray-200 overflow-x-auto">
      <thead class="bg-gray-50">
        <tr>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            닉네임
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            상태
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            역할
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            이메일
          </th>
          <th
            scope="col"
            class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
          >
            변경
          </th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="approval in approvalList" :key="approval.userId">
          <td class="py-4 whitespace-nowrap pl-4">
            <div class="flex items-center">
              <div class="flex-shrink-0 h-10 w-10">
                <img
                  class="h-10 w-10 rounded-full"
                  :src="'https://tiary-images.s3.ap-northeast-2.amazonaws.com/' + approval.userPicture"
                  alt=""
                />
              </div>
            </div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <div class="text-sm text-gray-900">
              {{ approval.nickname }}
            </div>
            <div class="text-sm text-gray-500">블로그 바로가기</div>
          </td>
          <td class="px-6 py-4 whitespace-nowrap">
            <span
              class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800"
            >
            {{ approval.status }}
            </span>
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            {{ approval.userStatus }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
            {{ approval.email }}
          </td>
          <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
            <a @click="accept(approval.userId)" class="text-indigo-600 hover:text-indigo-900">수락</a>
            <a @click="reject(approval.userId)" href="#" class="ml-2 text-red-600 hover:text-red-900">반려</a>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted,ref } from 'vue';
import { listApprove, writerAccpet, writerReject } from '@/api/common';

const approvalList = ref([]);

async function listApproval() {
    try {
        const response = await listApprove();
        approvalList.value = response.data;
    } catch (error) {
        console.log(error);
    }
}
async function accept(userId) {
    try {
        const response = await writerAccpet(userId);
        window.location.reload();
    } catch (error) {
        console.log(error);
    }
}
async function reject(userId) {
    try {
        const response = await writerReject(userId);
        window.location.reload();
    } catch (error) {
        console.log(error);
    }
}
onMounted(() => {
  listApproval();
});
</script>

<style scoped></style>
