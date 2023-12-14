import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/pages/Home.vue'
import Error from '@/pages/Error.vue'

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {path: '/', component: Home},
        {path: '/error', component: Error}
    ],
    })

export default router;