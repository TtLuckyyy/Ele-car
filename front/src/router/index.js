import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/login.vue'; // 引入登录页面
import Register from '../views/register.vue'; // 引入登录页面
import Home from '../views/home.vue'; // 引入登录页面

const routes = [
    {
        path: '/',         // 将根路径设置为 /ele-car
        redirect: '/login' // 当访问 /ele-car 时重定向到 /ele-car/login
    },
    {
        path: '/login',   // 确保 path 以 '/' 开头
        name: 'login',
        component: Login // 显示 Login 组件
    },
    {
        path: '/register',   // 确保 path 以 '/' 开头
        name: 'register',
        component: Register // 显示 Login 组件
    },
    {
        path: '/home',   // 确保 path 以 '/' 开头
        name: 'home',
        component: Home // 显示 Login 组件
    },
];

const router = createRouter({
    history: createWebHistory('/ele-car'), // 设置根路径为 /ele-car
    routes
});


export default router;
