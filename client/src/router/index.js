import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/Home.vue'
import ArticleList from '@/pages/ArticleList.vue'
import ArticlePost from '@/components/ArticlePost.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Home},
        {path: '/article', component : ArticleList},
        {path: '/article-post', component : ArticlePost},
        {path: '/category' ,name: 'CategoryPage', component :       ArticleList , props : (route) =>
        ({categoryCode : route.query.categoryCode})}
    ],
    })

export default router;