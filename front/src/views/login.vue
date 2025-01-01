<template>
    <div class="login-container">
        <el-card class="login-card" shadow="hover">
            <!-- 插入标题和图片 -->
            <div class="login-title">
                <h2>充电桩服务平台登录</h2>
            </div>
            <el-form :model="loginForm" ref="loginForm" :rules="rules" label-width="0px">
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" show-password>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <div class="login-button-container" justify-content="center">
                        <el-button type="primary" @click="handleLogin" class="login-button">登录</el-button>
                    </div>
                </el-form-item>
            </el-form>
            <p class="login-footer">
               <router-link to="/register">立即注册</router-link>
                <span> | </span>
                <a href="https://cn.bing.com/search?q=%E5%BF%98%E8%AE%B0%E5%
                AF%86%E7%A0%81%E6%80%8E%E4%B9%88%E5%8A%9E&form=ANNTH1&refig=6
                72595cca658430f9ef9c52cd53314c1&pc=LCTS&adppc=EdgeStart">忘记密码</a>
            </p>
        </el-card>
    </div>
</template>

<script>
import { User, Lock } from '@element-plus/icons-vue'

export default {
    data() {
        return {
            loginForm: {
                username: '',
                password: ''
            },
            rules: {
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
            }
        };
    },
    inject: ['user'],
    methods: {
        // validate为ele-ui自带的库函数，可以验证当前表单是否通过验证
        async handleLogin() {
            this.$refs.loginForm.validate(async (valid) => {   
                if (valid) {
                    try {
                        const response = await this.$http.post('/login', {
                            username: this.loginForm.username,
                            password: this.loginForm.password
                        });
                        if (response.data.status === 'success') {
                            this.$message.success('登录成功！');
                            Object.assign(this.user, response.data.user); // 将返回的用户数据赋值给全局 user 对象
                            this.$router.push('/home');  // 登录成功后跳转到主页
                        } else {
                            this.$message.error(response.data.message);
                        }
                    } catch (error) {
                        this.$message.error('无法连接服务器，请稍后重试');
                    }
                } else {
                    this.$message.error('请填写完整信息');
                }
            });
        }
    },
    components: {
        User,
        Lock
    }
};
</script>

<style scoped>
/* 设置整个页面容器的背景图片 */
.login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('@/assets/background/charge.png');
    background-size: cover;
    background-position: center;
    position: relative;
}

.login-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    z-index: 1;
}

.login-card {
    width: 400px;
    max-width: 90vw; /* 响应式设计，适配小屏幕 */
    padding: 40px 20px;
    border-radius: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    text-align: center;
    background-color: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    position: relative;
    z-index: 2;
}

.login-title {
    text-align: center;
    margin-bottom: 20px;
    color: #fff; /* 使标题在深色背景上清晰 */
}

.el-input {
    margin-bottom: 15px;
    background: rgba(255, 255, 255, 0.2); /* 输入框半透明效果 */
    border-radius: 5px;
}

.el-input .el-input__inner {
    color: #fff;
}

.el-form-item__error {
    color: #ff4d4f; /* 错误提示颜色 */
}

.login-button-container {
    display: flex;
    justify-content: center; /* 水平居中对齐 */
    width: 100%; /* 确保父容器占满宽度 */
}

.login-button {
    width: 100%;
    max-width: 200px; /* 按钮最大宽度，居中效果更好 */
    background-color: #409eff;
    color: #fff;
    border-radius: 5px;
}

.login-footer {
    text-align: center;
    color: #ddd;
    margin-top: 15px;
}

.login-footer a {
    color: #409eff;
    text-decoration: none;
}

.login-footer a:hover {
    text-decoration: underline;
}
</style>
