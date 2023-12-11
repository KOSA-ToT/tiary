<template>
  <div>
      <div class="flex flex-col gap-5 m-3">
        <!-- Comment Container -->
        <div>
          <div class="flex w-full justify-between border rounded-md">
            <div class="p-3">
              <div class="flex gap-3 items-center">
                <img
                  src="https://avatars.githubusercontent.com/u/22263436?v=4"
                  class="object-cover w-10 h-10 rounded-full border-2 border-emerald-400 shadow-emerald-400"
                />
                <h3 class="font-bold">
                  {{commentData.createdBy}}
                  <br />
                  <span class="text-sm text-gray-400 font-normal">{{commentData.createdAt}}</span>
                </h3>
              </div>
              <p class="text-gray-600 mt-2">{{ commentData.content }}</p>
              <button class="text-right text-blue-500">Reply</button>
            </div>
          </div>
        </div>
      </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
const { commentData } = defineProps(["commentData"]);
</script>

<style scoped></style>
