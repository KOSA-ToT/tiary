import { createRouter, createWebHistory } from "vue-router";

import Home from "@/pages/Home.vue";
import Error from "@/pages/Error.vue";
import MypageProfile from "@/components/myPage/profile.vue";
import MyInfoView from "@/components/myPage/myInfo.vue";
import MyPostView from "@/components/myPage/myPost.vue";
import MyCommentView from "@/components/myPage/myComment.vue";
import MySubscriberView from "@/components/myPage/mySubscriber.vue";
import WriterProfile from "@/components/myPage/writerProfile.vue";
import MySubscribePost from "@/components/myPage/MySubscribePost.vue";
import Comment from "@/components/comment/Comment.vue";

import ArticleList from "@/pages/ArticleList.vue";
import ArticleCreate from "@/components/article/ArticleCreate.vue";
import ArticleEdit from "@/components/article/ArticleEdit.vue";
import Post from "@/pages/Post.vue";
import NickCheck from "@/pages/Nickcheck.vue";
import VerifyEmail from "@/pages/VerifyEmail.vue";
import OauthCallback from "@/pages/OauthCallback.vue";
import Admin from "@/pages/Admin.vue";
import WriterManagement from "@/components/admin/WriterManagement.vue";
import NoticeManagement from "@/components/admin/NoticeManagement.vue";
import PaymentCallBack from '@/pages/PaymentCallBack.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", name: "Home", component: Home },
    { path: "/error", component: Error },
    {
      path: "/mypage/",
      component: MypageProfile,
      children: [
        {
          path: ":id",
          component: MyInfoView,
        },
        {
          path: "post/:id",
          component: MyPostView,
        },
        {
          path: "comment/:id",
          component: MyCommentView,
        },
        {
          path: "subscriber/:id",
          component: MySubscriberView,
        },
        {
            path: "my-subscribe/:id",
            component: MySubscribePost,
          },
      ],
    },
    { path: "/writer-page/:id", component: WriterProfile },
    { path: "/article-test", component: Comment },
    { path: "/articles", component: ArticleList },
    { path: "/article/:articleId", name: "Post", component: Post, props: true },
    {
      path: "/article-create",
      name: "ArticleCreate",
      component: ArticleCreate,
      props: true,
    },
    {
      path: "/article-edit/:articleId",
      name: "ArticleEdit",
      component: ArticleEdit,
      props: true,
    },
    {
      path: "/category",
      name: "CategoryPage",
      component: ArticleList,
      props: (route) => ({ categoryName: route.query.categoryName }),
    },
    { path: "/nickname-check/:email", name: "Nickcheck", component: NickCheck },
    { path: "/auth/verify-email", component: VerifyEmail },
    { path: "/oauth/callback", component: OauthCallback },
    {
      path: "/admin/",
      component: Admin,
      children: [
        {
          path: "writer-management",
          component: WriterManagement,
        },
        {
          path: "notice-management/:id",
          component: NoticeManagement,
        },
      ],
    },
    {path: '/payment/success/:data', component: PaymentCallBack, props: true}
  ],
});

router.beforeEach((to, from, next) => {
  window.scrollTo(0, 0);
  next();
});

export default router;
