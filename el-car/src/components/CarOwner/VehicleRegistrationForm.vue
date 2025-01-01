<template>
  <div class="vehicle-registration">
    <div class="form-container">
      <h1 class="form-title">车辆信息登记</h1>
      <el-form 
        :model="form" 
        ref="vehicleForm" 
        :rules="rules" 
        label-width="120px" 
        class="form-content">
        <el-form-item label="车主姓名" prop="ownerName">
          <el-input v-model="form.ownerName" placeholder="请输入车主姓名"></el-input>
        </el-form-item>
        <el-form-item label="车辆型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入车辆型号"></el-input>
        </el-form-item>
        <el-form-item label="车牌号" prop="licensePlate">
          <el-input v-model="form.licensePlate" placeholder="请输入车牌号"></el-input>
        </el-form-item>
        <el-form-item label="信息填写日期" prop="registrationDate">
          <el-date-picker v-model="form.registrationDate" type="date" placeholder="选择注册日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="上传车辆照片(仅限一张)" prop="fileList">
          <el-upload
            class="upload-demo"
            action="#"
            list-type="picture-card"
            :on-change="handleUploadChange"
            :on-preview="handlePreview"
            :file-list="form.fileList"
            :limit="1"
            :auto-upload="false"
          >
            <div v-if="form.fileList.length === 0">
              选择图片
            </div>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="车辆照片" />
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
  name: 'VehicleRegistrationForm',
  inject: ['user'],
  data() {
    return {
      form: {
        ownerName: '',
        model: '',
        licensePlate: '',
        registrationDate: null,
        fileList: [], // 图片上传列表
      },
      dialogImageUrl: '', // 图片预览的URL
      dialogVisible: false, // 图片预览对话框的显示状态
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '用户名长度需在3到20个字符之间', trigger: 'blur' },
        ],
        ownerName: [
          { required: true, message: '请输入车主姓名', trigger: 'blur' },
          { pattern: /^[\u4e00-\u9fa5a-zA-Z]{2,20}$/, message: '车主姓名必须是2-20个字，且不能包含特殊字符', trigger: 'blur' },
        ],
        model: [
          { required: true, message: '请输入车辆型号', trigger: 'blur' },
          { max: 30, message: '车辆型号不能超过30个字符', trigger: 'blur' },
        ],
        licensePlate: [
          { required: true, message: '请输入车牌号', trigger: 'blur' },

          { pattern: /^[A-Za-z]{1}[0-9]{5}$/, message: '车牌号格式不正确，例如：A12345', trigger: 'blur' },
        ],
        registrationDate: [
          { required: true, message: '请选择注册日期', trigger: 'change' },
        ],
         fileList: [
          { 
            required: true, 
            message: '请上传车辆照片', 
            trigger: 'change', 
            validator: (rule, value) => {
              if (value.length === 0) {
                return new Error('请上传车辆照片');
              }
              return true;
            }
          }
        ]
      },
    };
  },
  methods: {
   async submitForm() {
      this.$refs.vehicleForm.validate(async (valid) => {
        if (valid) {
          // 创建一个表单对象，将表单中的数据提交到后端
          const formData = {
            username: this.user.username, 
            ownername: this.form.ownerName,
            model: this.form.model,
            licensePlate: this.form.licensePlate,
            registrationDate: this.form.registrationDate,
          };
          
          try {
            const response = await this.$http.post('/home/CarOwner/vehicleRegistration', formData);

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
      this.$refs.vehicleForm.resetFields();
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
      this.$refs.vehicleForm.validateField('fileList');
    },
    handlePreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    },
};
</script>

<style scoped>
.vehicle-registration {
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
