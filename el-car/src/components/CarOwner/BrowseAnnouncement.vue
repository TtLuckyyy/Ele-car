<template>
  <div class="policy-view">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>平台政策信息</h2>
      <p>查看平台发布的所有政策公告，及时了解政策动态。</p>
    </div>

    <!-- 政策列表展示 -->
    <el-row class="policy-row" gutter={20}>
      <el-col :span="8" v-for="(policy, index) in policies" :key="index">
        <el-card :body-style="{ padding: '20px' }" class="policy-card">
          <!-- 政策标题和发布日期 -->
          <div class="policy-header">
            <h3 class="policy-title">{{ policy.title }}</h3>
            <el-tag :type="getTagType(policy.status)" class="status-tag">{{ policy.statusText }}</el-tag>
            <p class="policy-date">发布日期：{{ policy.date }}</p>
          </div>

          <!-- 政策内容预览 -->
          <div class="policy-content">
            <p>{{ policy.preview }}</p>
          </div>

          <!-- 查看全文按钮 -->
          <el-button type="primary" @click="viewFullContent(policy)" class="view-button">查看全文</el-button>
        </el-card>
      </el-col>
    </el-row>

    <!-- 全文显示 -->
    <el-dialog v-model="isDialogVisible" title="政策详情" width="60%">
      <div v-if="selectedPolicy">
        <el-row>
          <el-col :span="24">
            <h3>{{ selectedPolicy.title }}</h3>
            <el-divider></el-divider>
            <el-row>
              <el-col :span="12">
                <p><strong>发布日期：</strong>{{ selectedPolicy.date }}</p>
              </el-col>
              <el-col :span="12">
                <p><strong>政策类别：</strong>{{ selectedPolicy.category }}</p>
              </el-col>
            </el-row>
            <el-divider></el-divider>
            <p><strong>政策内容：</strong></p>
            <div v-html="selectedPolicy.content"></div>
            <el-divider></el-divider>
            <!-- 附件部分 -->
            <el-link :href="'https://www.w3.org/WAI/WCAG21/quickref/pdf/QuickRef-WCAG21.pdf'" target="_blank">
              查看附件 (PDF)
            </el-link>
          </el-col>
        </el-row>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="isDialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 模拟政策数据
      policies: [
        {
          title: "新能源汽车车主信息登记方式更新通知",
          date: "2024-12-01",
          status: "active",
          statusText: "已发布",
          preview: "本政策对新能源车车主的信息登记方式进行了更新和维护，详细请看附件。",
          content: "<p>详细内容：本政策的更新涉及新能源车车主在本网站的信息登记方式，具体操作请见附件。</p>",
          category: "用户协议"
        },
        {
          title: "充电桩安装政策调整",
          date: "2024-11-15",
          status: "draft",
          statusText: "草案中",
          preview: "新的充电桩安装政策草案已经提出，具体内容请见附件。",
          content: "<p>详细内容：根据新的计划，充电桩的安装位置和资质标准进行了调整。</p>",
          category: "服务条款"
        },
        {
          title: "运营商服务协议变更",
          date: "2024-10-10",
          status: "active",
          statusText: "已发布",
          preview: "此政策对运营商服务协议进行了变更，具体请见附件。",
          content: "<>详细内容：本政策对运营商服务协议进行了调整，对能够在本网站进行信息计策的运营商的筛选标准进行了修改。</p>",
          category: "隐私政策"
        },
        {
          title: "智能充电设备补贴政策",
          date: "2024-09-25",
          status: "draft",
          statusText: "草案中",
          preview: "为了促进智能充电产品的普及，新的补贴政策正在讨论中。",
          content: "<p>详细内容：本政策将提供不同档次的补贴，适用于购买符合条件的智能充电桩设备的用户。</p>",
          category: "服务条款"
        },
        {
          title: "充电桩规划调整意见收集",
          date: "2024-08-15",
          status: "active",
          statusText: "已发布",
          preview: "为了进一步优化充电桩服务，现面对平台用户进行对充电桩位置规划的意见收集",
          content: "<p>详细内容：本计划包括对全国范围内应用于本网站的的充电桩站建设进行重新规划，提高充电桩覆盖率与充电便捷性。</p>",
          category: "隐私政策"
        },
        {
          title: "电子支付系统升级通知",
          date: "2024-07-01",
          status: "active",
          statusText: "已发布",
          preview: "电子支付系统将进行全面升级，改进支付流程和安全性。",
          content: "<p>详细内容：升级后的系统将提供更快速的支付体验，同时加强交易安全性，支持更多支付方式。</p>",
          category: "用户协议"
        }
      ],
      isDialogVisible: false, // 控制全文弹窗
      selectedPolicy: null // 当前选中的政策
    };
  },
  methods: {
    // 获取政策状态标签的类型
    getTagType(status) {
      return status === "active" ? "success" : status === "draft" ? "warning" : "info";
    },
    // 查看政策详情
    viewFullContent(policy) {
      this.selectedPolicy = policy;
      this.isDialogVisible = true;
    }
  }
};
</script>

<style scoped>
/* 让整个页面背景变成灰色 */
html, body {
  margin: 0;
  padding: 0;
  background-color: #f4f6f9; /* 设置灰色背景 */
  height: 100%; /* 使 body 占满全屏 */
}

/* 让内容区域背景色覆盖整个页面 */
.policy-view {
  padding: 30px;
  background-color: #f4f6f9; /* 保持与整体页面一致的背景颜色 */
  min-height: 100vh; /* 确保背景色延伸到整个视口 */
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-header h2 {
  font-size: 2rem;
  color: #4e5b6e;
}

.page-header p {
  color: #9b9b9b;
}

/* 让整个卡片列表居中 */
.policy-row {
  display: flex;
  justify-content: center;
}

.policy-card {
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  margin-bottom: 20px; /* 增加卡片之间的间距 */
  margin-left: 5px;
}

/* 卡片悬停效果 */
.policy-card:hover {
  transform: scale(1.05);  /* 增大卡片 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);  /* 增加阴影，模拟浮动效果 */
}

/* 中间卡片样式 */
.center-card {
  transition: all 0.3s ease;
  transform: scale(1.1);  /* 中间卡片放大 */
  z-index: 2;
}

/* 左侧卡片样式 */
.left-card {
  transition: all 0.3s ease;
  transform: scale(0.9);  /* 左侧卡片缩小 */
  z-index: 1;
}

/* 右侧卡片样式 */
.right-card {
  transition: all 0.3s ease;
  transform: scale(0.9);  /* 右侧卡片缩小 */
  z-index: 1;
}

.policy-header {
  display: flex;
  flex-direction: column;
  margin-bottom: 15px;
}

.policy-title {
  font-size: 1.25rem;
  font-weight: bold;
  color: #2c3e50;
}

.status-tag {
  margin-top: 10px;
  margin-bottom: 10px;
}

.policy-date {
  font-size: 0.875rem;
  color: #7f8c8d;
}

.policy-content {
  font-size: 1rem;
  color: #34495e;
  line-height: 1.6;
}

.view-button {
  margin-top: 15px;
}

.el-dialog__body {
  padding: 20px;
}

.el-button {
  font-size: 0.9rem;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

.el-card__header {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  padding-bottom: 12px;
  border-bottom: 2px solid #f1f1f1;
}

.el-form-item {
  margin-bottom: 20px;
}
</style>
