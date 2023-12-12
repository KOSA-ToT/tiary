import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/components/HelloWorld.vue'
import mypage from '@/components/myPage/myPage.vue'
import MypageProfile from '@/components/myPage/profile.vue'
import MyInfoView from '@/components/myPage/myInfo.vue'
import MyPostView from '@/components/myPage/myPost.vue'
import MyCommentView from '@/components/myPage/myComment.vue'
import MySubscriberView from '@/components/myPage/mySubscriber.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
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
    ],
    })

export default router;