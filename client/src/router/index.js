import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/Home.vue'
import ArticleList from '@/pages/ArticleList.vue'
import Post from '@/pages/Post.vue'
import ArticleCreate from '@/components/article/ArticleCreate.vue'
import ArticleEdit from '@/components/article/ArticleEdit.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', name : 'Home', component: Home},
        {path: '/articles', component : ArticleList},
        {path: '/article/:articleId' , name: 'Post', component : Post, props : true },
        {path: '/article-create', name:'ArticleCreate', component : ArticleCreate, props : true},
        {path: '/article-edit/:articleId', name:'ArticleEdit', component : ArticleEdit, props : true},
        {path: '/category' ,name: 'CategoryPage', component :       ArticleList , props : (route) =>
        ({categoryCode : route.query.categoryCode})},
    ],
    })

export default router;