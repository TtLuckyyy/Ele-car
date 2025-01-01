<template>
  <div class="charging-pile-management">
    <!-- 小标题 -->
    <h2 class="title">充电桩管理</h2>

    <!-- 筛选条件 -->
    <el-row :gutter="20" class="filter-container" align="middle">
      <el-col :span="4">
        <el-select v-model="filter.status" placeholder="选择状态" clearable size="small">
          <el-option label="正常运转" value="available"></el-option>
          <el-option label="暂停使用" value="unavailable"></el-option>
        </el-select>
      </el-col>
      <el-col :span="4">
        <el-select v-model="filter.tradername" placeholder="选择品牌" clearable size="small">
          <el-option v-for="brand in uniqueBrands" :key="brand" :label="brand" :value="brand" />
        </el-select>
      </el-col>
    </el-row>

    <!-- 充电桩卡片展示 -->
    <el-row :gutter="30" justify="start" class="charging-pile-row">
      <el-col v-for="(pile, index) in paginatedChargingPiles" :key="index" :span="4">
        <div class="charging-pile-card">
          <img :src="pile.imageUrl" alt="充电桩图片" class="charging-pile-image" />
          <div class="button-group">
            <el-button size="mini" @click="viewDetails(pile)">详情</el-button>
            <el-button size="mini" type="warning" @click="openEditDialog(pile)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              :loading="loading"
              @click="deleteChargingPile(pile)">
              删除
            </el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-pagination
      :current-page="currentPage"
      :page-size="pageSize"
      :total="filteredChargingPiles.length"
      layout="total, prev, pager, next, jumper"
      @current-change="handlePageChange"
      style="text-align: center; margin-top: 20px;"
    ></el-pagination>

    <!-- 详情对话框 -->
    <el-dialog title="充电桩详情" v-model="detailsDialogVisible" width="800px">
      <el-form :model="detailForm" label-width="100px">
        <el-form-item label="充电桩ID">
          <el-input v-model="detailForm.pile_id" disabled></el-input>
        </el-form-item>
        <el-form-item label="充电桩品牌">
          <el-input v-model="detailForm.tradername" disabled></el-input>
        </el-form-item>
        <el-form-item label="使用介绍">
          <el-input v-model="detailForm.introduction" disabled type="textarea" rows="3"></el-input>
        </el-form-item>
        <el-form-item label="充电桩地址">
          <el-input v-model="detailForm.address" disabled></el-input>
        </el-form-item>
        <el-form-item label="充电价格">
          <el-input v-model="detailForm.price" disabled></el-input>
        </el-form-item>
        <el-form-item label="当前状态">
          <el-input v-model="detailForm.statusText" :value="statusText" disabled></el-input>
        </el-form-item>

        <!-- 折线图显示 -->
        <el-col :span="24"> 
          <el-form-item label=" ">
            <div class="chart-container" > 
              <line-chart :data="lineChartData" />
            </div>
          </el-form-item>
        </el-col>

        <!-- 雷达图显示 -->
        <el-col :span="24"> 
          <el-form-item label=" ">
            <div class="chart-container" > 
              <radar-chart :data="radarChartData" />
            </div>
          </el-form-item>
        </el-col>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailsDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 编辑对话框 -->
    <el-dialog title="编辑充电桩信息" v-model="editDialogVisible" width="500px">
      <el-form :model="editForm" label-width="100px">
        <el-form-item label="充电桩介绍">
          <el-input v-model="editForm.introduction" type="textarea" rows="3" placeholder="请输入充电桩介绍"></el-input>
        </el-form-item>
        <el-form-item label="充电桩地址">
          <el-input v-model="editForm.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item label="当前状态">
          <el-select v-model="editForm.status" placeholder="请选择状态">
            <el-option label="正常运转" value="available"></el-option>
            <el-option label="暂停使用" value="unavailable"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="充电价格">
          <el-slider v-model="editForm.price" :min="0" :max="2" :step="0.00001" show-tooltip></el-slider>
        </el-form-item>
        <el-form-item label="更改图片">
          <el-button 
            size="small" 
            type="default" 
            @click="changeImage" 
            class="upload-button">
            上传
          </el-button>
        </el-form-item>
        <el-form-item label="当前图片">
          <div class="image-preview">
            <img :src="editForm.imageUrl" alt="充电桩图片" class="charging-pile-image-preview" />
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveChanges">保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// 引入你需要的图表组件
import LineChart from '../charts/LineChart.vue';  
import RadarChart from '../charts/RadarChart.vue';  
import axios from 'axios'; // 引入 axios

export default {
  inject: ['user'],
  components: {
    LineChart,
    RadarChart
  },
  data() {
    return {
      chargingPiles: [], // 初始化为空数组
      filter: {
        tradername: '', // 用于筛选充电桩品牌
        status: '',      // 用于筛选充电桩状态
      },
      editDialogVisible: false,
      currentPage: 1,  // 当前页码
      pageSize: 12,  // 每页显示的数量
      editForm: {
        statusText: '',
      },
      detailsDialogVisible: false,
      detailForm: {},
      lineChartData: this.generateLineChartData(),
      radarChartData: this.generateRadarChartData(),
    };
  },
  mounted() {
    this.fetchChargingPiles(); // 获取充电桩数据
  },
  computed: {
    // 计算属性，筛选充电桩
    filteredChargingPiles() {
      return this.chargingPiles.filter(pile => {
        const matchTradername = this.filter.tradername ? pile.tradername.toLowerCase().includes(this.filter.tradername.toLowerCase()) : true;
        const matchStatus = this.filter.status ? pile.status === this.filter.status : true;
        return matchTradername && matchStatus;
      });
    },
    paginatedChargingPiles() {
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    return this.filteredChargingPiles.slice(start, end);
    },
    statusText() {
        if (this.detailForm.status === 'available') {
            return '充电桩正常运转';
        } else if (this.detailForm.status === 'unavailable') {
            return '充电桩暂停使用';
        } else {
            return '无状态信息';  // 如果状态不明确，返回默认值
        }
    },
    watch: {
        // 监听 status 改变时，更新 statusText
        'detailForm.status': function() {
        this.detailForm.statusText = this.statusText;
        }
    },
    // 获取充电桩品牌的唯一列表
    uniqueBrands() {
      const brands = this.chargingPiles.map(pile => pile.tradername);
      return [...new Set(brands)]; // 使用 Set 来获取唯一值
    }
  },
  methods: {
    async fetchChargingPiles() {
      try {
        const response = await axios.get('/home/Trader/ChargingPilesList');
        const data = response.data;
        const imageUrls = [
          require('../../assets/chargepile/pile1.png'),
          require('../../assets/chargepile/pile2.png'),
          require('../../assets/chargepile/pile3.png'),
          require('../../assets/chargepile/pile4.png'),
          require('../../assets/chargepile/pile5.png'),
          require('../../assets/chargepile/pile6.png'),
          require('../../assets/chargepile/pile7.png'),
          require('../../assets/chargepile/pile8.png'),
          require('../../assets/chargepile/pile9.png'),
          require('../../assets/chargepile/pile10.png'),
          require('../../assets/chargepile/pile11.png'),
          require('../../assets/chargepile/pile12.png'),
          require('../../assets/chargepile/pile13.png'),
          require('../../assets/chargepile/pile14.png'),
          require('../../assets/chargepile/pile15.png'),
        ];
        this.chargingPiles = data.map(pile => ({
          ...pile,
          imageUrl: imageUrls[Math.floor(Math.random() * imageUrls.length)],
        }));
      } catch (error) {
        console.error('服务器错误，请联系开发人员！');
      }
    },

    viewDetails(pile) {
      this.detailForm = { ...pile };
      this.detailsDialogVisible = true;
    },

    openEditDialog(pile) {
      this.editForm = { ...pile };
      this.editDialogVisible = true;
    },
    handlePageChange(page) {
    this.currentPage = page;
    },
    async saveChanges() {
      try {
        const response = await axios.post('/home/Trader/updateChargingPile', this.editForm);
        if (response.data.status === 'success') {
          const index = this.chargingPiles.findIndex(pile => pile.pile_id === this.editForm.pile_id);
          if (index !== -1) {
            this.chargingPiles.splice(index, 1, { ...this.editForm });
          }
          this.editDialogVisible = false;
          this.$message.success('保存成功');
        } else {
          this.$message.error(response.data.message);
        }
      } catch (error) {
        console.error('保存充电桩信息失败', error);
        this.$message.error('服务器错误，请联系开发人员！');
      }
    },

    async deleteChargingPile(pile) {
      try {
        this.loading = true; // 开启加载状态

        // 显示确认框并返回 Promise
        const confirmation = await this.$confirm(
          `确定要删除充电桩 ${pile.pile_id} 吗？`,
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
        );

        // 只有点击了确认按钮，才执行删除逻辑
        if (confirmation === 'confirm') {
          const response = await axios.delete(`/home/Trader/deleteChargingPile/${pile.pile_id}`);
          if (response.data.status === 'success') {
            this.$message.success(response.data.message);
            this.chargingPiles = this.chargingPiles.filter(p => p.pile_id !== pile.pile_id);
          } else {
            this.$message.error(response.data.message);
          }
        }
      } catch (error) {
        // 处理取消或关闭时的情况，不会进入 catch 块
        if (error === 'cancel') {
          this.$message.info('已取消删除操作');
        } else {
          console.error('删除充电桩失败', error);
          this.$message.error('服务器错误，请稍后再试！');
        }
      } finally {
        // 无论成功还是失败都关闭加载状态
        this.loading = false;
      }
    },

    changeImage() {
      const newImageUrl = 'https://via.placeholder.com/300';
      this.editForm.imageUrl = newImageUrl;
    },

    generateLineChartData() {
      return Array.from({ length: 24 }, (_, i) => ({
        hour: `${i}:00`,
        usage: Math.floor(Math.random() * 10) + 1,
      }));
    },

    generateRadarChartData() {
      return {
        categories: ['1星', '2星', '3星', '4星', '5星'],
        values: Array.from({ length: 5 }, () => Math.floor(Math.random() * 5) + 1)
      };
    }
  },
};
</script>

<style scoped>
.filter-container {
  margin-bottom: 20px;
}
</style>


<style scoped>
.charging-pile-management {
  padding: 20px;
}

.title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 20px;
}

.charging-pile-row {
  margin-bottom: 20px;
}

.charging-pile-card {
  background: #fff;
  width: 100%;
  border-radius: 8px;
  padding: 10px;
  text-align: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px
}

.charging-pile-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 10px;
  max-width: 500px;
  max-height: 170px;
  margin-left: auto;
  margin-right: auto;
}

.image-preview {
  margin-bottom: 20px;
}

.charging-pile-image-preview {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  max-width: 300px;
  max-height: 170px;
}

/* 使折线图和雷达图并排显示，并设置合适的大小 */
.chart-container {
  margin-top: 10px;
}

.el-row {
  margin-top: 20px;
}

.el-col {
  padding: 0 10px;
}

/* 限制图表最大宽度 */
.chart-container el-card {
  width: 100%;
  height: 300px;
  max-width: 100%;
}

/* 按钮组样式保持原样 */
.button-group {
  margin-top: 10px;
  display: flex;
  justify-content: space-between;
  width: 100%;
  gap: 10px;
}

.el-button {
  flex: 1;
  min-width: 20px;
}

.upload-button {
  background-color: rgba(0, 0, 0, 0.1); 
  color: #909399;
  border: 1px solid rgba(0, 0, 0, 0.1);
  width: 10px; 
  max-width: 100px;
  display: inline-block;
}
.chart-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 400px; /* Adjust this height for both charts */
}

line-chart {
  width: 100%; /* Ensure the line chart takes full width */
  height: 100%; /* Ensure the height is responsive */
}

radar-chart {
  width: 100%; /* Ensure the radar chart takes full width */
  height: 100%; /* Ensure the height is responsive */
}
</style>

