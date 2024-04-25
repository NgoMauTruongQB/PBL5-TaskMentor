import { createRouter, createWebHistory } from 'vue-router'
import ErrorView from '../views/Error.vue'
import HomeView from '../views/Home.vue'
import LoginView from '../views/Login.vue'
import RegisterView from '../views/Register.vue'
import Appointment from '../views/user/Appointment.vue'
import CreateClass from '@/views/user/teacher/CreateClass.vue'
import Kanban from '@/views/user/student/Kanban.vue'
import Requirement from '@/views/user/student/Requirement.vue'
import QRAndCode from '@/views/user/QRAndCode.vue'
import ListClass from '../views/user/teacher/ListClass.vue'
import JoinClass from '../views/user/student/JoinClass.vue'
import ListClassStudent from '../views/user/student/ListClass.vue'
import Class from '../views/user/teacher/Class.vue'
import Notification from '../views/user/student/Notification.vue'


import DashboardView from '../views/admin/DashboardView.vue'

const routes =  [
    { path: '/', component: HomeView },
    { path: '/manager', name: 'manager', redirect: { name: 'dashboard'},
        children: [
            { path: 'dashboard', name: 'dashboard', component: DashboardView, meta: { layout: 'ManagerLayout' } },
        ]
    },
    { path: '/login', name: 'login', component: LoginView, meta: { layout: 'AuthLayout' } },
    { path: '/register', name: 'register', component: RegisterView, meta: { layout: 'AuthLayout' } },
    { path: '/404', name: 'notfound', component: ErrorView, meta: { layout: 'NotFoundLayout'} },
    { path: '/appointment', name: 'appointment', component: Appointment },
    { path: '/createClass', name: 'createClass', component: CreateClass },
    { path: '/requirement', name: 'requirement', component: Requirement },
    { path: '/kanban', name: 'Kanban', component: Kanban },
    { path: '/QRAndCode', name: 'QRAndCode', component: QRAndCode },
    { path: '/joinClass', name: 'JoinClass', component: JoinClass },
    { path: '/listClass', name: 'ListClass', component: ListClass},
    { path: '/joinedClass', name: 'ListClassStudent', component: ListClassStudent },
    { path: '/notification', name: 'Notification', component: Notification },
    { path: '/:pathMatch(.*)*', redirect: { name: 'notfound'} },
    { path: '/class', name: 'Class', component: Class }

]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})


export default router