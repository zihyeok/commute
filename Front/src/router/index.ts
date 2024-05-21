import { setupLayouts } from 'virtual:generated-layouts'
import { createRouter, createWebHistory } from 'vue-router'
import routes from '~pages'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...setupLayouts(routes),

    // {
    //     path: '/login',
    //     name: 'login',
    //     component: () => import("@/pages/login.vue"),
    //     meta: {

    //     }
    //   },
    // {
    //   path: '/register',
    //   name: 'register',
    //   component: () => import("@/pages/register.vue"),
    //   meta: {

    //   }
    // },
  ],
})

// Docs: https://router.vuejs.org/guide/advanced/navigation-guards.html#global-before-guards

export default router
