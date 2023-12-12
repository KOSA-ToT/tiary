<template>
  <div>
    <!-- <div class="flex flex-col gap-5 m-3"> -->
    <div class="gap-100 m-3">
      <!-- Comment Container -->
      <div>
        <div class="w-full border rounded-md">
          <div class="p-3">
            <div class="flex gap-3 items-center">
              <img
                src="https://avatars.githubusercontent.com/u/22263436?v=4"
                class="object-cover w-10 h-10 rounded-full border-2 border-emerald-400 shadow-emerald-400"
              />
              <h3 class="font-bold">
                id: {{ commentData.id }}/
                {{ commentData.createdBy }}
                <br />
                <span class="text-sm text-gray-400 font-normal"
                  >{{ formatCreatedAt(commentData.createdAt) }}
                </span>
                <span class="text-sm text-gray-400 font-normal"> 수정 </span>
                <span class="text-sm text-gray-400 font-normal"> 삭제</span>
              </h3>
            </div>
            <p class="text-gray-600 mt-2">{{ commentData.content }}</p>
            <div
              class="text-sm text-gray-400 font-normal"
              v-if="commentData.children && commentData.children.length > 0"
              @click="showReplyComment"
            >
              대댓글 개수: {{ commentData.children.length }}
              <ReplyCommentCard
                v-for="(replyComment, index) in commentData.children"
                :replyCommentData="commentData.children[index]"
              ></ReplyCommentCard>
              <!-- {{ commentData.children }} -->
            </div>

            <button class="text-left text-blue-500">Reply</button>
            <!-- 대댓글 표시 -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { defineProps } from "vue";
import ReplyCommentCard from "../comment/ReplyCommentCard.vue";
const { commentData } = defineProps(["commentData"]);

function showReplyComment() {}
function formatCreatedAt(createdAt) {
  const date = new Date(createdAt);
  return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(
    date.getDate()
  )} ${padZero(date.getHours())}:${padZero(date.getMinutes())}:${padZero(
    date.getSeconds()
  )}`;
}

function padZero(num) {
  return num.toString().padStart(2, "0");
}
</script>

<style scoped></style>
