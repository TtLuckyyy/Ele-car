<template>
  <div class="operator-registration">
    <div class="form-container">
      <h1 class="form-title">运营商信息登记</h1>
      <el-form 
        :model="form" 
        ref="operatorForm" 
        :rules="rules" 
        label-width="120px" 
        class="form-content">
        
        <el-form-item label="运营商名称" prop="operatorName">
          <el-input v-model="form.operatorName" placeholder="请输入运营商名称"></el-input>
        </el-form-item>
        
        <el-form-item label="公司地址" prop="companyAddress">
          <el-input v-model="form.companyAddress" placeholder="请输入公司地址"></el-input>
        </el-form-item>
        
        <el-form-item label="信息填写日期" prop="registrationDate">
          <el-date-picker v-model="form.registrationDate" type="date" placeholder="选择注册日期"></el-date-picker>
        </el-form-item>
        
        <el-form-item label="营业执照" prop="fileList">
          <el-upload
            class="upload-demo"
            action="#"
            list-type="picture-card"
            :file-list="form.fileList"
            :on-change="handleUploadChange"
            :on-preview="handlePreview"
            :limit="1"
            :auto-upload="false"
          >
            <div v-if="form.fileList.length === 0">
              选择图片
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="营业执照" />
          </el-dialog>
        </el-form-item>

        <el-form-item>
          <div class="button-group">
            <el-button type="primary" @click="submitForm">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TraderRegistrationForm',
  inject: ['user'],
  data() {
    return {
      form: {
        operatorName: '',
        companyAddress: '',
        registrationDate: null,
        fileList: [], // 文件上传列表
      },
      dialogImageUrl: '', // 图片预览的URL
      dialogVisible: false, // 图片预览对话框的显示状态
      rules: {
        operatorName: [
          { required: true, message: '请输入运营商名称', trigger: 'blur' },
          { pattern: /^[a-zA-Z0-9\u4e00-\u9fa5]{2,50}$/, message: '运营商名称必须是2-50个字符，且不能包含特殊字符', trigger: 'blur' },
        ],
        companyAddress: [
          { required: true, message: '请输入公司地址', trigger: 'blur' },
          { max: 100, message: '公司地址不能超过100个字符', trigger: 'blur' },
        ],
        registrationDate: [
          { required: true, message: '请选择注册日期', trigger: 'change' },
        ],
        fileList: [
        { 
            required: true, 
            message: '请上传营业执照', 
            trigger: 'change', 
            validator: (rule, value, callback) => {
            // Check if value is undefined or an empty array
            if (!value || value.length === 0) {
                callback(new Error('请上传营业执照'));
            } else {
                callback(); // Validation passes if fileList is not empty
            }
            }
        }
        ]
      },
    };
  },
  methods: {
    async submitForm() {
      this.$refs.operatorForm.validate(async (valid) => {
        if (valid) {
          // 创建一个表单对象，将表单中的数据提交到后端
          const formData = {
            id: this.user.id,
            username: this.user.username, 
            tradername: this.form.operatorName,
            companyAddress: this.form.companyAddress,
            registrationDate: this.form.registrationDate,
          };

          try {
            const response = await this.$http.post('/home/Trader/TraderRegistration', formData);

            if (response.data.status === 'success') {
              this.$message.success(response.data.message);
              this.resetForm();  // 清空表单数据
            } else {
              this.$message.error(response.data.message);
            }
          } catch (error) {
            this.$message.error('服务器错误，请联系开发人员！');
          }
        } else {
          // 如果表单验证失败
          this.$message.error('请完整填写表单信息');
        }
      });
    },
    resetForm() {
      this.$refs.operatorForm.resetFields();
      this.form.fileList = []; // 清空图片列表
    },
    handleUploadChange(file, fileList) {
      // 更新图片列表
      const previewUrl = URL.createObjectURL(file.raw);
      this.form.fileList = fileList.map((item) => ({
        name: item.name,
        url: item.url || previewUrl,
      }));

      // 手动触发验证
      this.$refs.operatorForm.validateField('fileList');
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
  },
};
</script>

<style scoped>
.operator-registration {
  display: flex;
  justify-content: center;
  align-items: flex-start;
  background: rgba(200, 200, 200, 0.5);
  height: 100%;
  padding: 20px;
}

.form-container {
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  padding: 30px 40px;
  max-width: 500px;
  width: 100%;
  margin-top: 50px;
  animation: fadeIn 0.8s ease-in-out;
}

.form-title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

.form-content {
  font-family: 'Arial', sans-serif;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-input,
.el-date-picker {
  border-radius: 6px;
}

.button-group {
  display: flex;
  justify-content: space-between;
  margin: 0 60px;
}

.el-button {
  width: 48%;
  font-size: 16px;
  border-radius: 6px;
}

.el-button[type="primary"] {
  background-color: #409eff;
  color: #fff;
}

.el-button:hover {
  transform: translateY(-2px);
  transition: transform 0.2s;
}
.upload-demo {
  text-align: center;
}

.upload-demo .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 100%;
  text-align: center;
  cursor: pointer;
}

.upload-demo i {
  font-size: 32px;
  color: #8c939d;
}

/* 动画 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
