<template>
  <div class="admin-announcement">
    <el-card class="announcement-card" shadow="always">
      <div slot="header" style="font-size: 24px;">
        <span>平台政策公告发布</span>
      </div>

      <el-form :model="announcement" label-width="120px" ref="form">
        <!-- 公告标题 -->
        <el-form-item label="公告标题" :rules="[{ required: true, message: '请输入公告标题', trigger: 'blur' }]">
          <el-input
            v-model="announcement.title"
            placeholder="请输入公告标题"
            class="input-field"
          />
        </el-form-item>

        <!-- 公告内容 -->
        <el-form-item label="公告内容" :rules="[{ required: true, message: '请输入公告内容', trigger: 'blur' }]">
          <el-input
            type="textarea"
            v-model="announcement.content"
            placeholder="请输入公告内容"
            rows="6"
            class="input-field"
          />
        </el-form-item>

        <!-- 发布日期 -->
        <el-form-item label="发布日期">
          <el-date-picker v-model="announcement.date" type="date" placeholder="选择发布日期" />
        </el-form-item>

        <!-- 政策类别 -->
        <el-form-item label="政策类别">
          <el-select v-model="announcement.category" placeholder="请选择政策类别">
            <el-option label="用户协议" value="user_agreement" />
            <el-option label="隐私政策" value="privacy_policy" />
            <el-option label="服务条款" value="terms_of_service" />
            <el-option label="社区规范" value="community_guidelines" />
          </el-select>
        </el-form-item>

        <!-- 上传附件 -->
        <el-form-item label="附件上传">
          <el-upload
            class="upload-demo"
            action="#"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button size="small" type="default" icon="el-icon-upload2" class="upload-button">上传附件</el-button>
            <div v-if="attachment" class="attachment-info">
              <el-icon :name="attachmentIcon" class="upload-icon"></el-icon>
              {{ attachment }}
            </div>
          </el-upload>
        </el-form-item>

        <!-- 发布按钮 -->
        <el-form-item>
          <div class="button-container">
            <el-button type="primary" @click="submitAnnouncement" class="submit-btn">发布公告</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      announcement: {
        title: "",
        content: "",
        date: "",
        category: "",
      },
      attachment: null, // 存储上传的附件文件名
      attachmentIcon: "el-icon-document", // 附件图标
    };
  },
  methods: {
    // 附件上传前的钩子
    beforeUpload(file) {
      this.attachment = file.name;
      return false; // 阻止实际的上传
    },
    // 提交公告
    submitAnnouncement() {
      // 调用表单验证
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.$message.success("公告发布成功!");
        } else {
          this.$message.error("请填写完整的公告信息!");
          return false;
        }
      });
    },
  },
};
</script>

<style scoped>
.admin-announcement {
  padding: 30px;
  background: linear-gradient(135deg, #e4e4e5 0%, #dae0e0 100%); /* 渐变背景 */
}

.announcement-card {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 15px;
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.announcement-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.2);
}

.el-card__header {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  padding-bottom: 12px;
  border-bottom: 2px solid #f1f1f1;
}

.el-input,
.el-select,
.el-date-picker {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.el-input:focus,
.el-select:focus,
.el-date-picker:focus {
  border-color: #409eff;
  box-shadow: 0 0 8px rgba(64, 158, 255, 0.5);
}

.input-field {
  padding: 12px;
  font-size: 14px;
}

.el-button {
  font-size: 14px;
  border-radius: 5px;
  transition: all 0.3s ease;
}

.el-button:hover {
  background-color: #409eff;
  color: white;
}

/* 修改上传按钮为灰色，字体居中对齐 */
.upload-button {
  background-color: #d3d3d3;
  color: #333;
  width: 70px; /* 确保按钮的宽度足够 */
  padding: 10px 20px; /* 内边距让按钮内容看起来更舒适 */
  display: inline-flex; /* 使用 flexbox 布局 */
  justify-content: center !important; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  text-align: center !important; /* 确保文本居中 */
  border-radius: 5px;
}

.upload-button:hover {
  background-color: #c1c1c1;
}

.attachment-info {
  margin-top: 8px;
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #555;
}

.upload-icon {
  margin-right: 8px;
  color: #409eff;
}

/* Center the submit button but slightly align to the left */
.button-container {
  display: flex;
  justify-content: flex-start; /* 左对齐 */
  width: 100%;  /* 让按钮容器的宽度为卡片的宽度 */
  padding-left: 260px;  /* 添加左边距，使按钮稍微靠左 */
}

.submit-btn {
  font-size: 16px;
  padding: 10px 20px;
  background-color: #20a3f1;
  color: white;
  border-radius: 5px;
}

.submit-btn:hover {
  background-color: #1e91d3;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.el-card__header {
  border-bottom: 2px solid #f0f0f0;
}

.el-form-item {
  margin-bottom: 20px;
}
</style>
