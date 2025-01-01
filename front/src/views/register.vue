<template>
    <div class="register-container">
        <el-card class="register-card" shadow="hover">
            <!-- 插入标题和图片 -->
            <div class="register-title">
                <h2>充电桩服务平台注册</h2>
            </div>
            <el-form :model="registerForm" ref="registerForm" :rules="rules" label-width="0px">
                <el-form-item prop="identity">
                    <el-input v-model="registerForm.identity" placeholder="请输入所注册身份">
                        <template #prefix>
                            <el-icon><Avatar /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="gender">
                    <el-input v-model="registerForm.gender" placeholder="请输入性别">
                        <template #prefix>
                            <el-icon><Sunny /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="telephone">
                    <el-input v-model="registerForm.telephone" placeholder="请输入电话号码">
                        <template #prefix>
                            <el-icon><Phone /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input v-model="registerForm.username" placeholder="请输入用户名">
                        <template #prefix>
                            <el-icon><User /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" show-password>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="confirmPassword">
                    <el-input v-model="registerForm.confirmPassword" type="password" placeholder="确认密码" show-password>
                        <template #prefix>
                            <el-icon><Lock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input v-model="registerForm.email" placeholder="请输入邮箱">
                        <template #prefix>
                            <el-icon><Message /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item>
                    <div class="register-button-container">
                        <el-button type="primary" @click="handleRegister" class="register-button">注册</el-button>
                    </div>
                </el-form-item>
            </el-form>
            <p class="register-footer">
                已有账号？<router-link to="/login">立即登录</router-link>
            </p>
        </el-card>
    </div>
</template>

<script>
import { User, Lock, Message,Phone,Sunny,Avatar} from '@element-plus/icons-vue'

export default {
    data() {
        return {
            registerForm: {
                identity: '',
                gender: '',
                telephone: '',
                username: '',
                email: '',
                password: '',
                confirmPassword: ''
            },
            rules: {
                identity: [{ pattern: /^[1-3]{1,1}$/,required: true, message: '请输入有效的注册身份(1：用户 2：运营商 3：管理者)', trigger: 'blur' }],
                gender: [
                        { required: true, message: '请输入性别', trigger: 'blur' },
                        {
                            validator: (rule, value, callback) => {
                                if (value === 'male' || value === 'female') {
                                    callback(); // 输入有效，验证通过
                                } else {
                                    callback(new Error('请输入有效的性别（male 或 female）')); // 输入无效，返回错误
                                }
                            },
                            trigger: 'blur'
                        }
                    ],
                telephone: [
                    { required: true, message: '请输入电话号码', trigger: 'blur' },
                    { pattern: /^[0-9]{11,11}$/, message: '请输入有效的电话号码', trigger: 'blur' }
                ],
                username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
                email: [
                    { required: true, message: '请输入邮箱', trigger: 'blur' },
                    { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
                ],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                confirmPassword: [
                    { required: true, message: '请确认密码', trigger: 'blur' },
                    { validator: (rule, value, callback) => {
                        if (value !== this.registerForm.password) {
                            callback(new Error('两次输入的密码不一致'));
                        } else {
                            callback();
                        }
                    }, trigger: 'blur' }
                ]
            }
        };
    },
    
    methods: {
        async handleRegister() {
            this.$refs.registerForm.validate(async (valid) => {
                if (valid) {
                    try {
                        const response = await this.$http.post('/register', {
                            identity: this.registerForm.identity,
                            gender: this.registerForm.gender,
                            phone: this.registerForm.telephone,
                            username: this.registerForm.username,
                            email: this.registerForm.email,
                            password: this.registerForm.password
                        });
                        if (response.data.status === 'success') {
                            this.$message.success('注册成功！');
                            this.$router.push('/login'); // 跳转到登录页面
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
        Lock,
        Message,
        Phone,
        Sunny,
        Avatar
    }
};

</script>

<style scoped>
.register-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-image: url('@/assets/background/charge.png');
    background-size: cover;
    background-position: center;
    position: relative;
}

.register-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    z-index: 1;
}

.register-card {
    width: 400px;
    max-width: 90vw;
    padding: 40px 20px;
    border-radius: 10px;
    box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
    text-align: center;
    background-color: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(10px);
    position: relative;
    z-index: 2;
}

.register-title {
    text-align: center;
    margin-bottom: 20px;
    color: #fff;
}

.el-input {
    margin-bottom: 15px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 5px;
}

.el-input .el-input__inner {
    color: #fff;
}

.el-form-item__error {
    color: #ff4d4f;
}

.register-button-container {
    display: flex;
    justify-content: center;
    width: 100%;
}

.register-button {
    width: 100%;
    max-width: 200px;
    background-color: #409eff;
    color: #fff;
    border-radius: 5px;
}

.register-footer {
    text-align: center;
    color: #ddd;
    margin-top: 15px;
}

.register-footer a {
    color: #409eff;
    text-decoration: none;
}

.register-footer a:hover {
    text-decoration: underline;
}
</style>
