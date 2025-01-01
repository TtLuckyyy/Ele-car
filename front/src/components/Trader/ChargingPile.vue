<template>
  <div class="operator-registration">
    <div class="form-container">
      <h1 class="form-title">充电桩信息登记</h1>
      <el-form 
        :model="form" 
        ref="operatorForm" 
        :rules="rules" 
        label-width="120px" 
        class="form-content">
        
       <el-form-item label="充电桩信息描述" prop="introduction">
        <el-input
            type="textarea"
            v-model="form.introduction"
            placeholder="请输入充电桩相关信息描述"
            rows="5"
            class="large-input"
        ></el-input>
        </el-form-item>

        <el-form-item label="充电桩所在地址" prop="address">
        <el-input
            type="textarea"
            v-model="form.address"
            placeholder="请输入充电桩所在地址"
            rows="3"
            class="large-input"
        ></el-input>
        </el-form-item>

        <el-form-item label="充电价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入充电价格"></el-input>
        </el-form-item>

        <el-form-item label="信息填写日期" prop="registrationDate">
          <el-date-picker v-model="form.registrationDate" type="date" placeholder="选择注册日期"></el-date-picker>
        </el-form-item>
        
        <el-form-item label="充电桩照片" prop="fileList">
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
            <img width="100%" :src="dialogImageUrl" alt="充电桩照片" />
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
  name: 'ChargingPileRegistrationForm',
  inject: ['user'],
  data() {
    return {
      form: {
        introduction: '',
        address: '',
        price: '',
        registrationDate: null,
        fileList: [], // 文件上传列表
      },
      dialogImageUrl: '', // 图片预览的URL
      dialogVisible: false, // 图片预览对话框的显示状态
      rules: {
        introduction: [
          { required: true, message: '请输入充电桩相关描述', trigger: 'blur' },
          { max: 100, message: '充电桩介绍不能超过100个字符', trigger: 'blur' },
        ],
        address: [
          { required: true, message: '请输入充电桩所在地址', trigger: 'blur' },
          { max: 100, message: '地址描述不能超过100个字符', trigger: 'blur' },
        ],
        price: [
          { required: true, message: '请输入充电价格', trigger: 'blur' },
          { 
            pattern: /^\d+(\.\d+)?$/, 
            message: '充电价格必须为浮点数', 
            trigger: 'blur' 
          },
        ],
        registrationDate: [
          { required: true, message: '请选择充电桩注册日期', trigger: 'change' },
        ],
        fileList: [
          { 
            required: true, 
            message: '请上传管理者证件照', 
            trigger: 'change', 
            validator: (rule, value, callback) => {
              if (!value || value.length === 0) {
                callback(new Error('请上传管理者证件照'));
              } else {
                callback(); 
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
          try {
            // 获取 tradername
            const traderResponse = await this.$http.get(`/home/Trader/${this.user.id}`);
            if(traderResponse.data.status === 'error')
            {
                this.$message.error(traderResponse.data.message);
                return;
            }
            const tradername = traderResponse.data.tradername;
            const trader_id = traderResponse.data.trader_id;

            // 创建一个表单对象，将表单中的数据提交到后端
            const formData = {
              tradername: tradername,
              trader_id: trader_id,
              introduction: this.form.introduction, 
              address: this.form.address,
              price: this.form.price,
              status: 'available',
              registrationDate: this.form.registrationDate,
            };

            console.log(formData);
            const response = await this.$http.post('/home/Trader/ChargingPileRegistration', formData);

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
.large-input .el-input__inner {
  height: 50px;  /* 增大输入框高度 */
  font-size: 16px;  /* 增大字体 */
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
