<template>
  <el-dialog v-model="visible" title="用户信息" @close="resetForm">
    <el-form :model="userInfo" label-width="100px"> <!-- 设置标签的宽度，确保对齐 -->
      <el-form-item label="用户ID">
        <el-input v-model="currentUser.id" readonly /> <!-- 设置为只读 -->
      </el-form-item>
      <el-form-item label="用户身份">
        <el-input v-model="currentUser.identity" />
      </el-form-item>
      <el-form-item label="用户性别">
        <el-input v-model="currentUser.gender" />
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="currentUser.phone" />
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="currentUser.username" />
      </el-form-item>
      <el-form-item label="登陆密码">
        <el-input v-model="currentUser.password" />
      </el-form-item>
      <el-form-item label="电子邮箱">
        <el-input v-model="currentUser.email" />
      </el-form-item>
      <el-form-item label="用户状态">
        <el-input v-model="currentUser.status" readonly /> <!-- 设置为只读 -->
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="closeDialog">关闭</el-button>
      <el-button type="primary" @click="saveUserInfo">保存</el-button>
    </span>
  </el-dialog>
</template>



<script>
export default {
  props: {
    dialogVisible: {
      type: Boolean,
      required: true,
    },
    currentUser: {
      type: Object,
      required: true,
    },
  },
  computed: {
    visible: {
      get() {
        return this.dialogVisible;
      },
      set(value) {
        this.$emit('closedialogvisible', value);
      },
    },
  },
  methods: {
    closeDialog() {
      this.visible = false; // 关闭对话框
    },
    async saveUserInfo() {
      if(this.currentUser.id ==='null')
      {
        try {
          const response = await this.$http.post('/home/user/addUser', this.currentUser);
          if (response.data.status === 'success') {
            alert('用户成功添加！');
            // 通知父组件编辑完成
            this.$emit('userEditComplete');
            this.closeDialog(); // 关闭对话框
          } else {
            this.$message.error(response.data.message);
          }
        } catch (error) {
          this.$message.error('服务器错误，请联系开发人员');
        }
      }
      else
      {
        try {
          const response = await this.$http.post('/home/user/updateUser', this.currentUser);
          if (response.data.status === 'success') {
            alert('用户信息已保存！');
            // 通知父组件编辑完成
            this.$emit('userEditComplete');
            this.closeDialog(); // 关闭对话框
          } else {
            this.$message.error(response.data.message);
          }
        } catch (error) {
          this.$message.error('服务器错误，请联系开发人员');
        }
      }
    },
  },
};
</script>

<style scoped>
.dialog-footer {
  text-align: right; /* 右对齐按钮 */
}
.el-input {
  width: 100%; /* 输入框自适应对话框宽度 */
}
</style>
