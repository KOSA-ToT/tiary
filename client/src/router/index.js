import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/Home.vue'
import ArticleTest from '@/pages/ArticleTest.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Home},
        {path: '/article-test', component: ArticleTest},
    ],
    })

export default router;