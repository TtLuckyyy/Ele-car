<template>
  <div class="home">
    <!-- 轮播图 -->
    <div class="carousel">
      <el-carousel trigger="click" height="400px" autoplay>
        <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
          <img :src="item.imgSrc"  class="carousel-img" />
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 数据展示 -->
    <div class="data-summary">
      <el-row class="data-row" gutter={30}>
        <el-col :span="4" v-for="(data, index) in dataStats" :key="index">
          <el-card :body-style="{ padding: '20px' }" :class="['data-card', data.cardClass]">
            <div class="data-card-icon">
              <div class="traffic-icon">
                <div class="traffic-bar"></div>
              </div>
            </div>
            <div class="data-card-content">
              <h3 :style="{ color: data.color }">{{ data.value }}</h3>
              <p>{{ data.label }}</p>
              <!-- 新增的进度条 -->
              <el-progress :percentage="data.progress" :color="data.barColor" height="8px"></el-progress>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 政策展示 -->
    <div class="policy-section">
      <h2>最新政策</h2>
      <el-row gutter={20}>
        <el-col :span="8" v-for="(policy, index) in policies" :key="index">
          <el-card :body-style="{ padding: '20px' }" class="policy-card">
            <div class="policy-header">
              <h3 class="policy-title">{{ policy.title }}</h3>
              <el-tag :type="getTagType(policy.status)" class="status-tag">{{ policy.statusText }}</el-tag>
            </div>
            <div class="policy-content">
              <p>{{ policy.preview }}</p>
            </div>
            <el-button type="primary" @click="BrowseAnuceDetail(policy)" class="view-button">查看详情</el-button>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
        carouselItems: [
        { imgSrc: require('../../assets/Home/image1.png')},
        { imgSrc: require('../../assets/Home/image2.png')},
        { imgSrc: require('../../assets/Home/image3.png')}, // 新能源电动汽车
        { imgSrc: require('../../assets/Home/image4.png')},  // 充电站和电动汽车
        { imgSrc: require('../../assets/Home/image5.png')},  // 充电站和电动汽车
        ],
        dataStats: [
        { label: '用户数量', value: 1200, color: '#3498db', cardClass: 'card-lightblue', barColor: '#3498db', progress: 75 },  // 75% 的进度
        { label: '运营商数量', value: 35, color: '#1abc9c', cardClass: 'card-teal', barColor: '#1abc9c', progress: 50 },  // 50% 的进度
        { label: '交流桩数量', value: 540, color: '#2980b9', cardClass: 'card-darkblue', barColor: '#2980b9', progress: 80 },  // 80% 的进度
        { label: '直流桩数量', value: 380, color: '#8e44ad', cardClass: 'card-purple', barColor: '#8e44ad', progress: 60 },  // 60% 的进度
        { label: '充电站数量', value: 75, color: '#34495e', cardClass: 'card-gray', barColor: '#34495e', progress: 40 },  // 40% 的进度
        ],

      policies: [
        {
          title: "新能源车政策更新通知",
          status: "active",
          statusText: "已发布",
          preview: "本政策对新能源车的补贴政策进行了更新和维护，详细请看附件。",
        },
        {
          title: "充电桩安装政策调整",
          status: "draft",
          statusText: "草案中",
          preview: "新的充电桩安装政策草案已经提出，具体内容请见文档。",
        },
        {
          title: "电动车购置税减免政策",
          status: "active",
          statusText: "已发布",
          preview: "此政策对电动车购置税进行大幅度的减免，降低购车成本。",
        },
      ],
    };
  },
  methods: {
    getTagType(status) {
      return status === 'active' ? 'success' : status === 'draft' ? 'warning' : 'info';
    },
    BrowseAnuceDetail(policy) {
      this.$emit('BrowseAnuceDetail', 5);
    },
  },
};
</script>
<style scoped>
.home {
  padding-top: 0px;
}

.carousel {
  margin-bottom: 0px;
  margin-top: 0px !important;
}

.carousel-img {
  width: 100%;
  height: 100%;
  height: 400px;
  width: 1200px;
  object-fit: cover;
}

.data-summary {
  margin: 40px 0;
}

.data-row {
  justify-content: center; /* 使数据居中 */
}

.data-card {
  text-align: center;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.data-card:hover {
  transform: translateY(-10px);
}

.data-card-icon {
  margin-bottom: 10px;
}

.traffic-icon {
  width: 50px;
  height: 50px;
  background-color: #f4f6f9;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.traffic-bar {
  width: 10px;
  height: 20px;
  animation: flow 1.5s ease-in-out infinite;
}

@keyframes flow {
  0% {
    height: 10px;
  }
  50% {
    height: 30px;
  }
  100% {
    height: 10px;
  }
}

.data-card-content h3 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 10px;
  color: inherit; /* 使用继承的颜色 */
}

.data-card-content p {
  font-size: 1rem;
  color: #7f8c8d;
}

.policy-section h2 {
  font-size: 2rem;
  margin-bottom: 20px;
}

.policy-card {
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.policy-card:hover {
  transform: translateY(-10px);
}

.policy-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.policy-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.status-tag {
  margin-top: 10px;
  margin-bottom: 10px;
}

.policy-content p {
  font-size: 1rem;
  color: #34495e;
  line-height: 1.6;
}

.view-button {
  margin-top: 15px;
}

/* 卡片颜色 */
.card-lightblue {
  background-color: rgba(52, 152, 219, 0.1); /* 透明度高的蓝色 */
  color: #3498db;
}

.card-teal {
  background-color: rgba(26, 188, 156, 0.1); /* 透明度高的绿色 */
  color: #1abc9c;
}

.card-darkblue {
  background-color: rgba(41, 128, 185, 0.1); /* 透明度高的深蓝色 */
  color: #2980b9;
}

.card-purple {
  background-color: rgba(142, 68, 173, 0.1); /* 透明度高的紫色 */
  color: #8e44ad;
}

.card-gray {
  background-color: rgba(52, 152, 219, 0.1); /* 浅灰色调 */
  color: #34495e;
}

/* 动态流量条颜色 */
.card-lightblue .traffic-bar {
  background-color: #3498db; /* 蓝色 */
}

.card-teal .traffic-bar {
  background-color: #1abc9c; /* 绿色 */
}

.card-darkblue .traffic-bar {
  background-color: #2980b9; /* 深蓝色 */
}

.card-purple .traffic-bar {
  background-color: #8e44ad; /* 紫色 */
}

.card-gray .traffic-bar {
  background-color: #34495e; /* 灰色 */
}

/* 进度条样式 */
.el-progress {
  margin-top: 10px;
}
</style>
