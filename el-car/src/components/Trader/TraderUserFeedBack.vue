<template>
  <div class="user-feedback">
    <!-- 筛选部分 -->
    <div class="filters">
      <el-row :gutter="10">
        <!-- 星级评分筛选 -->
        <el-col :span="5">
          <div class="filter-rating-container">
            <div class="filter-label">评分：</div>
            <el-rate
              v-model="selectedRating"
              :max="5"
              :disabled="false"
              :show-text="true"
              :texts="['1星', '2星', '3星', '4星', '5星']"
              class="filter-rating"
              label="星级评分"
            />
          </div>
        </el-col>

        <!-- 充电站品牌筛选 -->
        <el-col :span="8">
          <el-select
            v-model="selectedBrand"
            placeholder="选择充电站品牌"
            label="充电站品牌"
            class="filter-brand"
          >
            <el-option
              v-for="brand in availableBrands"
              :key="brand"
              :label="brand"
              :value="brand"
            />
          </el-select>
        </el-col>

        <!-- 重置筛选按钮 -->
        <el-col :span="5">
          <el-button type="primary" @click="resetFilters" class="reset-button">重置筛选</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 用户评论列表 -->
    <el-row :gutter="20" v-if="filteredReviews.length">
      <el-col :span="24" v-for="(review, index) in filteredReviews" :key="index">
        <el-card class="feedback-card">
          <!-- 用户头像和评论信息 -->
          <div class="card-header">
            <el-avatar :src="review.userAvatar" size="large" />
            <div class="review-info">
              <div class="username">{{ review.username }}</div>
              <div class="review-date">{{ review.date }}</div>
              <div class="review-rating">
                <el-rate v-model="review.rating" disabled />
              </div>
            </div>
          </div>

          <!-- 评论内容 -->
          <div class="card-body">
            <p>{{ review.comment }}</p>
          </div>

          <!-- 查看详情按钮，放到右侧并调整大小 -->
          <div class="card-footer">
            <el-button @click="viewReviewDetail(review)" size="mini" type="primary" class="view-detail-button">查看详情</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 没有评论时显示 -->
    <div v-else class="no-reviews">
      <el-alert title="暂无用户反馈" type="info" />
    </div>

    <!-- 分页组件 -->
    <div class="pagination">
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredReviews.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
      />
    </div>

    <!-- 查看详情对话框 -->
    <el-dialog
      title="评论详情"
      v-model="dialogVisible"
      width="50%"
      @close="dialogVisible = false"
    >
      <el-card class="feedback-card">
        <!-- 头部：用户信息 -->
        <div class="card-header">
          <el-avatar :src="currentReview.userAvatar" size="large" class="avatar" />
          <div class="review-info">
            <div class="username">{{ currentReview.username }}</div>
            <div class="review-date">{{ currentReview.date }}</div>
            <div class="review-rating">
              <el-rate v-model="currentReview.rating" disabled />
            </div>
          </div>
        </div>

        <!-- 评论详情 -->
         <div class="card-body">
          <el-row>
            <!-- 充电桩编号 -->
            <el-col :span="24" class="detail-item">
              <el-text class="detail-label">充电桩编号：</el-text>
              <el-text>{{ currentReview.pileId }}</el-text>
            </el-col>

            <!-- 商户名称 -->
            <el-col :span="24" class="detail-item">
              <el-text class="detail-label">商户名称：</el-text>
              <el-text>{{ currentReview.traderName }}</el-text>
            </el-col>

            <!-- 评论内容 -->
            <el-col :span="24" class="detail-item">
              <el-text class="detail-label">评论内容：</el-text>
              <el-text>{{ currentReview.comment }}</el-text>
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  inject: ['user'],
  data() {
    return {
      reviews: [], // 评论数据
      currentPage: 1,
      pageSize: 5,
      selectedRating: 0, // 星级评分筛选
      selectedBrand: '', // 充电站品牌筛选
      availableBrands: [], // 动态充电站品牌列表
      dialogVisible: false, // 控制详情对话框显示
      currentReview: null, // 当前选中的评论
    };
  },
  computed: {
    // 过滤评论
    filteredReviews() {
      return this.reviews.filter((review) => {
        const matchesRating = this.selectedRating === 0 || review.rating === this.selectedRating;
        const matchesBrand = !this.selectedBrand || review.traderName === this.selectedBrand;
        return matchesRating && matchesBrand;
      });
    },
  },
  methods: {
    // 获取评论数据
    async fetchReviews() {
      try {
        const response = await axios.get('/home/Trader/getAllreviews');
        const data = response.data;

       // 获取当前用户的 tradername
      const userResponse = await axios.get(`/home/Trader/${this.user.id}`);
      const traderName = userResponse.data.tradername;

      // 根据当前用户的 tradername 筛选出相关评论
      const filteredReviews = data.filter((item) => item.tradername === traderName);

        
        // 随机生成评论数据
        this.reviews = filteredReviews.map((item) => {
          return {
            pileId: item.pileId,
            userId: item.userId,
            rating: item.rating,
            traderName: item.tradername,
            comment: item.comment,
            date: this.getRandomDate(), // 随机生成日期
            userAvatar: `https://randomuser.me/api/portraits/men/${Math.floor(Math.random() * 10)}.jpg`, // 随机生成头像
            username: this.getRandomUsername(), // 随机生成用户名
          };
        });

        // 动态获取充电站品牌并去重
        const brands = [...new Set(this.reviews.map((review) => review.traderName))];
        this.availableBrands = brands;
      } catch (error) {
        console.error('获取评论数据失败', error);
      }
    },

    // 获取随机日期
    getRandomDate() {
      const start = new Date(2022, 0, 1); // 起始日期
      const end = new Date(); // 当前日期
      const randomDate = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
      return randomDate.toLocaleDateString(); // 格式化日期
    },

    // 获取随机用户名
    getRandomUsername() {
      const names = ['John', 'Jane', 'Alex', 'Chris', 'Katie', 'Michael', 'Sarah', 'David', 'Laura', 'Daniel'];
      const randomName = names[Math.floor(Math.random() * names.length)];
      const randomNumber = Math.floor(Math.random() * 100);
      return `${randomName}${randomNumber}`;
    },

    // 查看评论详情
    viewReviewDetail(review) {
      this.currentReview = review;
      this.dialogVisible = true;
    },

    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page;
    },

    // 重置筛选
    resetFilters() {
      this.selectedRating = 0;
      this.selectedBrand = '';
    },
  },
  mounted() {
    this.fetchReviews(); // 获取评论数据
  },
};
</script>

<style scoped>
/* 基本样式 */
.user-feedback {
  padding: 20px;
  background-color: #f9f9f9;
}

.filters {
  margin-bottom: 20px;
}

.filter-rating,
.filter-brand {
  width: 50%;
}

.reset-button {
  width: 50%;
  margin-top: 0px;
}

/* 卡片样式 */
.feedback-card {
  padding: 20px;
  border-radius: 10px;
  background-color: #fff;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
  transition: box-shadow 0.3s ease, transform 0.3s ease;
}

.feedback-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 15px rgba(0, 0, 0, 0.2);
}

/* 卡片头部 */
.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 2px solid #eee;
}

.card-header .el-avatar {
  margin-right: 15px;
  width: 70px;
  height: 70px;
}

.review-info {
  display: flex;
  flex-direction: column;
  font-size: 14px;
  color: #333;
}

.username {
  font-weight: bold;
  font-size: 18px;
  color: #333;
}

.review-date {
  font-size: 14px;
  color: #888;
}

.review-rating {
  margin-top: 5px;
}

/* 卡片正文 */
.card-body {
  margin-top: 15px;
  font-size: 15px;
  color: #555;
}

/* 评论详情部分 */
.el-divider {
  margin: 10px 0;
}

.detail-item {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  font-size: 16px;
  line-height: 1.6;
}

.detail-label {
  font-weight: bold;
  color: #333;
  margin-right: 10px;
}

.el-text {
  font-size: 16px;
  color: #555;
}

/* 无评论时提示 */
.no-reviews {
  margin-top: 20px;
  text-align: center;
}

/* 分页 */
.pagination {
  margin-top: 20px;
  text-align: center;
}

/* 筛选部分 */
.filter-rating-container {
  display: flex;
  align-items: center;
}

.filter-label {
  font-size: 14px;
  color: #333;
  margin-right: 10px;
}

/* 查看详情按钮 */
.view-detail-button {
  float: right;
}
</style>

