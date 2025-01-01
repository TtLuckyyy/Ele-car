<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>

import { reactive, provide } from 'vue';
import { watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';  // 导入 useRoute 和 useRouter

export default {
  setup() {
    // 使用 reactive 创建一个用户信息对象，可以在整个应用中共享
    const user = reactive({
      id: '',
      identity: '',
      gender: '',
      phone: '',
      username: '',
      password: '',
      email: '',
      status: '',
    });

    // 提供 user 对象到整个应用中
    provide('user', user);

    // 使用 useRoute 和 useRouter 获取路由信息
    const route = useRoute();   // 获取当前路由对象
    const router = useRouter(); // 获取路由实例

    // 使用 watch 监听路由路径和用户状态
    watch(
      () => [route.path, user],  // 监听路由路径和用户状态
      ([newPath, user]) => {
        if (!user.id && newPath !== '/register') {
          router.push('login');  // 跳转到登录页面
        }
      },
    );

    return {};
  }
}
</script>

<style>
#app {
  font-family: "Myfont" !important;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
