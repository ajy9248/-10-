import { createRouter, createWebHistory } from 'vue-router'
import Main from '../views/Main/Main'
const routes = [
    {
        path: '/',
        name:"main",
        component: Main,
        children: [
            {
                path: "/studentinfo",
                name: "studentinfo",
                component: () => import("../views/Pages/StudentInfo.vue")
            }
            ]
    }
]


const router = createRouter({
    history:createWebHistory(process.env.BASE_URL),
    routes
})

export default router