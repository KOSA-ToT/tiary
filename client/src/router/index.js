import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/Home.vue'

import mypage from '@/components/myPage/myPage.vue'
import MypageProfile from '@/components/myPage/profile.vue'
import MyInfoView from '@/components/myPage/myInfo.vue'
import MyPostView from '@/components/myPage/myPost.vue'
import MyCommentView from '@/components/myPage/myComment.vue'
import MySubscriberView from '@/components/myPage/mySubscriber.vue'
import ArticleTest from '@/pages/ArticleTest.vue'
import ArticleList from '@/pages/ArticleList.vue'
import ArticlePost from '@/components/ArticlePost.vue'
import NickCheck from '@/pages/NickCheck.vue'
import VerifyEmail from '@/pages/VerifyEmail.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Home},
        { path: '/mypage/',
                    component: MypageProfile,
                    children : [
                        {
                            path : ":id",
                            component : MyInfoView,
                        },
                        {
                            path : "post/:id",
                            component : MyPostView,
                        },
                        {
                            path : "comment/:id",
                            component : MyCommentView,
                        },
                        {
                            path: 'subscriber/:id',
                            component:  MySubscriberView
                        }
                    ]},

        {path: '/', name : 'Home', component: Home},
         {path: '/article-test', component: ArticleTest},
        {path: '/articles', component : ArticleList},
        {path: '/article/:articleId' , name: 'Post', component : Post, props : true },
        {path: '/article-create', name:'ArticleCreate', component : ArticleCreate, props : true},
        {path: '/article-edit/:articleId', name:'ArticleEdit', component : ArticleEdit, props : true},
        {path: '/category' ,name: 'CategoryPage', component :       ArticleList , props : (route) =>
        ({categoryCode : route.query.categoryCode})}
    ],
    })

export default router;