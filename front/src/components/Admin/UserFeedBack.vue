<template>
  <div class="user-feedback">
    <!-- 筛选部分 -->
        <div class="filters">
        <el-row :gutter="10"> <!-- 减少 gutter 值，减少筛选项之间的间距 -->
            <!-- 星级评分筛选 -->
        <el-col :span="5">
          <div class="filter-rating-container">
            <!-- 添加星级筛选的 label -->
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
        <el-col :span="8"> <!-- 调整宽度，缩小列的占比 -->
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
        <el-col :span="5"> <!-- 调整宽度，缩小列的占比 -->
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
                <!-- 星级评分显示 -->
                <el-rate v-model="review.rating" disabled />
              </div>
            </div>
          </div>

          <!-- 评论内容 -->
          <div class="card-body">
            <p>{{ review.comment }}</p>
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
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 模拟的评论数据
      reviews: [
        {
          username: '张三',
          userAvatar: 'https://randomuser.me/api/portraits/men/1.jpg',
          date: '2024-12-01',
          rating: 5,
          brand: 'Tesla',  // 充电站品牌
          comment: '充电桩非常好，充电速度很快，推荐大家使用！',
        },
        {
          username: '李四',
          userAvatar: 'https://randomuser.me/api/portraits/women/2.jpg',
          date: '2024-12-03',
          rating: 4,
          brand: 'BYD',
          comment: '整体还不错，但有时会出现连接问题。',
        },
        {
          username: '王五',
          userAvatar: 'https://randomuser.me/api/portraits/men/2.jpg',
          date: '2024-12-05',
          rating: 3,
          brand: 'Tesla',
          comment: '充电过程中有些问题，希望能够改进。',
        },
        {
          username: '赵六',
          userAvatar: 'https://randomuser.me/api/portraits/women/3.jpg',
          date: '2024-12-06',
          rating: 2,
          brand: 'Tesla',
          comment: '体验不太好，充电不稳定，客服响应慢。',
        },
        {
          username: '钱七',
          userAvatar: 'https://randomuser.me/api/portraits/men/3.jpg',
          date: '2024-12-07',
          rating: 4,
          brand: 'BYD',
          comment: '虽然有些小问题，但整体服务还不错，值得推荐。',
        },
        {
          username: '孙八',
          userAvatar: 'https://randomuser.me/api/portraits/men/4.jpg',
          date: '2024-12-08',
          rating: 5,
          brand: 'BYD',
          comment: '非常好用！没有问题。',
        },
      ],
      currentPage: 1,
      pageSize: 5,
      selectedRating: 0, // 选择的星级评分，0表示不筛选
      selectedBrand: '', // 选择的充电站品牌
      availableBrands: ['Tesla', 'BYD'], // 充电站品牌列表
    };
  },
  computed: {
    filteredReviews() {
      // 根据评分和品牌筛选评论
      return this.reviews.filter((review) => {
        const matchesRating = this.selectedRating === 0 || review.rating === this.selectedRating;
        const matchesBrand = !this.selectedBrand || review.brand === this.selectedBrand;
        return matchesRating && matchesBrand;
      });
    },
  },
  methods: {
    handlePageChange(page) {
      this.currentPage = page;
    },
    resetFilters() {
      this.selectedRating = 0; // 重置星级筛选
      this.selectedBrand = ''; // 重置品牌筛选
    },
  },
};
</script>

<style scoped>
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
  width: 100%;
  margin-top: 0px;
  width: 50%;
}

.feedback-card {
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
  margin-bottom: 15px;
}

.card-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
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
}

.username {
  font-weight: bold;
  font-size: 16px;
  color: #333;
}

.review-date {
  font-size: 14px;
  color: #999;
}

.review-rating {
  margin-top: 5px;
}

.card-body {
  margin-top: 10px;
  font-size: 14px;
  color: #555;
}

.no-reviews {
  margin-top: 20px;
  text-align: center;
}

.pagination {
  margin-top: 20px;
  text-align: center;
}
.filter-rating-container {
  display: flex;
  align-items: center;
}

.filter-label {
  font-size: 14px;
  color: #333;
  margin-right: 10px; /* 控制标签和星级组件之间的间距 */
}
</style>
