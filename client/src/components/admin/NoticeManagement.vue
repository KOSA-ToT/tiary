<template>
  <div>
    <a href="/article-create" class="btn btn-orange btn-outline">글쓰기</a>
  </div>
  <p>공지사항 신청 관리 컴포넌트~</p>
  <AdminPost></AdminPost>
</template>

<script setup>
import AdminPost from "@/components/admin/AdminPost.vue";
</script>

<style scoped>
.btn {
  margin-right: 5px;
  padding: 5px 16px;
  /* padding 수정 */
  min-width: 75px;
  /* 최소 너비 추가 */
  border: 1px solid orange;
  border-radius: 30px;
  transition: all 0.3s ease;
}

.btn:hover {
  border-color: #ff9800;
}

.btn-outline {
  background-color: transparent;
  color: #ff9800;
}

.btn-outline:hover {
  background-color: #ff9800;
  color: #fff;
}

.btn-orange {
  background-color: transparent;
  color: #ff9800;
}
</style>
