import { createRouter, createWebHistory } from 'vue-router'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/',
      name: 'layout',
      component: () => import('../components/Layout.vue'),
      redirect: "/home",
      children: [
        {
          path: 'home',
          name: 'Home',
          component: () => import('../views/HomeView.vue')
        },
        {
          path: 'user',
          name: 'User',
          component: () => import('../views/UserView.vue')
        }
      ]
    }
  ]
})

// 路由守卫
router.beforeEach((to,from,next) =>{
  if(to.path === "/login"){
    next();
  }
  const user = localStorage.getItem("user")
  if(!user && to.path !== "/login"){
    return next("/login")
  }
  next();
})

export default router
