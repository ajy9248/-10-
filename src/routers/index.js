import { createRouter, createWebHistory } from 'vue-router'
import Main from '../views/Main/Main'
import StudentMain from '../views/Main/StudentMain'
import TeacherMain from '../views/Main/TeacherMain'
const routes = [
    {
        path: "/login",
        name: "login",
        component: () => import("../views/Pages/Login.vue")
    },
    {
        path: '/',
        name:"main",
        component: Main,
        children: [
            {
                path: "/studentinfo",
                name: "studentinfo",
                component: () => import("../views/Pages/StudentInfo.vue")
            },
            {
                path: "/teacherinfo",
                name: "teacherinfo",
                component: () => import("../views/Pages/TeacherInfo.vue")
            },
            {
                path: "/predictioninfo",
                name: "predictioninfo",
                component: () => import("../views/Pages/PredictionInfo.vue")
            },
            {
                path: "/scoreinfo",
                name: "scoreinfo",
                component: () => import("../views/Pages/ScoreInfo.vue")
            },
            {
                path: "/datacenter",
                name: "datacenter",
                component: () => import("../views/Pages/DataCenter.vue")
            }
            ]
    },
    {
        path: '/studentmain',
        name:"studentmain",
        component: StudentMain,
        children: [
            {
                path: "/studentpre",
                name: "studentpre",
                component: () => import("../views/Pages/StudentPre.vue")
            },
            {
                path: "/selfcenter",
                name: "selfcenter",
                component: () => import("../views/Pages/SelfCenter.vue")
            }
        ]
    },
    {
        path: '/teachermain',
        name: "teachermain",
        component: TeacherMain,
        children: [
            {
                path: "/inquirescores",
                name: "inquirescores",
                component: () => import("../views/Pages/InquireScores.vue")
            }
        ]
    }
]


const router = createRouter({
    history:createWebHistory(process.env.BASE_URL),
    routes
})

export default router
