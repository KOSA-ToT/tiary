import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/components/HelloWorld.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/example', component: Home},
    ],
    })

export default router;