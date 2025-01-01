// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // 如果使用了 Vue Router
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'; // 引入 Element Plus 样式
import axios from 'axios';
import './assets/font/font.css'   //一般都是这个路径


axios.defaults.baseURL = "http://localhost:8083/ele-car";

const app = createApp(App);
app.config.globalProperties.$http = axios;
app.use(router);
app.use(ElementPlus); // 使用 Element Plus
app.mount('#app');


