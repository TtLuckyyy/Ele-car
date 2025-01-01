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
      <el-col :span="4">
        <el-input v-model="filter.pile_id" placeholder="搜索充电桩ID" size="small" clearable />
      </el-col>
    </el-row>

    <!-- 充电桩卡片展示 -->
    <el-row :gutter="30" justify="start" class="charging-pile-row">
      <el-col v-for="(pile, index) in paginatedChargingPiles" :key="index" :span="4">
        <div class="charging-pile-card">
          <img :src="pile.imageUrl" alt="充电桩图片" class="charging-pile-image" />
          <div class="button-group">
            <el-button size="mini" @click="viewDetails(pile)">详情</el-button>
            <el-button size="mini" type="success" @click="reserveChargingPile(pile)">预约</el-button>
            <el-button size="mini" type="info" @click="leaveReview(pile)">评价</el-button>
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
        <el-form-item label="充电单价（元/小时）">
          <el-input v-model="detailForm.price" disabled></el-input>
        </el-form-item>
        <el-form-item label="当前状态">
          <el-input :value="statusText" disabled></el-input>
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

    <el-dialog title="预约充电桩" v-model="reserveDialogVisible" width="500px">
    <el-form :model="reserveForm" label-width="100px">
        <!-- 预约时间 -->
        <el-form-item label="预约时间">
        <el-date-picker v-model="reserveForm.reserveDate" type="datetime" placeholder="选择预约时间"></el-date-picker>
        </el-form-item>

        <!-- 预约时长 -->
        <el-form-item label="预约时长">
        <el-slider 
            v-model="reserveForm.chargetime" 
            :min="0" 
            :max="24" 
            step="1" 
            show-input>
        </el-slider>
        <span>{{ reserveForm.chargetime }} 小时</span>
        </el-form-item>

        <!-- 充电类型 -->
        <el-form-item label="充电类型">
            <el-radio-group v-model="reserveForm.chargetype" :disabled="false">
                <el-radio label="fast">快充</el-radio>
                <el-radio label="slow">慢充</el-radio>
            </el-radio-group>
        </el-form-item>

        <!-- 备注 -->
        <el-form-item label="备注">
        <el-input v-model="reserveForm.remarks" type="textarea" placeholder="请输入备注" rows="3"></el-input>
        </el-form-item>

    </el-form>

    <!-- 对话框底部按钮 -->
    <div slot="footer" class="dialog-footer">
        <el-button @click="reserveDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveReservation">预约</el-button>
    </div>
    </el-dialog>


    <!-- 评价对话框 -->
    <el-dialog title="充电桩评价" v-model="reviewDialogVisible" width="500px">
      <el-form :model="reviewForm" label-width="100px">
        <el-form-item label="评分">
          <el-rate v-model="reviewForm.rating" />
        </el-form-item>
        <el-form-item label="问题反馈">
          <el-input v-model="reviewForm.comment" type="textarea" placeholder="请输入问题反馈信息" rows="3"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reviewDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitReview">提交</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
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
        pile_id: '',     // 新增字段：用于筛选充电桩ID
      },
      reserveDialogVisible: false,
      reviewDialogVisible: false,
      currentPage: 1,  // 当前页码
      pageSize: 12,  // 每页显示的数量
      reserveForm: {
        reserveDate: '',
        chargetime: '',
        chargetype: '',
        remarks: ''
      },
      reviewForm: {
        rating: 0,
        comment: ''
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
    uniqueBrands() {
      return [...new Set(this.chargingPiles.map(pile => pile.tradername))];
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
    uniqueBrands() {
      const brands = this.chargingPiles.map(pile => pile.tradername);
      return [...new Set(brands)]; // 使用 Set 来获取唯一值
    },
      filteredChargingPiles() {
      return this.chargingPiles.filter(pile => {
        const matchTradername = this.filter.tradername ? pile.tradername.toLowerCase().includes(this.filter.tradername.toLowerCase()) : true;
        const matchStatus = this.filter.status ? pile.status === this.filter.status : true;
        const matchPileId = this.filter.pile_id ? pile.pile_id.toString().includes(this.filter.pile_id.toString()) : true; // 筛选pile_id
        return matchTradername && matchStatus && matchPileId;
      });
    },
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

    reserveChargingPile(pile) {
      this.reserveForm = { ...this.reserveForm,...pile };
      this.reserveDialogVisible = true;
    },

    async saveReservation() {
        // 计算费用，假设充电时长由用户选择
        const chargeTime = this.reserveForm.chargetime;  // 从表单中获取充电时长
        const price = this.reserveForm.price;  // 充电桩价格
        const cost = chargeTime*10 * price;  // 根据时长计算费用

        const reservationData = {
            userId: this.user.id,  // 当前用户的ID
            pileId: this.reserveForm.pile_id,  // 充电桩ID
            reservationDate: this.reserveForm.reserveDate,  // 预约时间
            status: 'pending',  // 默认状态为待处理
            price: price,  // 充电价格
            chargeTime: chargeTime,  // 充电时长
            cost: cost,  // 计算后的费用
        };

        try {
            const response = await axios.post('/home/CarOwner/reserveChargingPile', reservationData);
            if (response.data.status === 'success') {
            this.$message.success('预约成功');
            this.reserveDialogVisible = false;
            this.reserveForm = {    
            reserveDate: '',
            chargetime:0,
            chargetype: '',
            remarks: ''
              };
            } else {
            this.$message.error(response.data.message);
            }
        } catch (error) {
            console.error('预约失败', error);
            this.$message.error('服务器错误，请稍后再试！');
        }
    },

    leaveReview(pile) {
      this.reviewForm = { ...pile };
      this.reviewDialogVisible = true;
    },
    handlePageChange(page) {
    this.currentPage = page;
    },
    async submitReview() {
      try {
        const Form={
          pileId:this.reviewForm.pile_id,
          userId:this.user.id,
          rating:this.reviewForm.rating,  
          comment:this.reviewForm.comment,
          tradername:this.reviewForm.tradername,
        }
        const response = await axios.post('/home/CarOwner/submitReview', Form);
        if (response.data.status === 'success') {
          this.$message.success('评价提交成功');
          this.reviewDialogVisible = false;
        } else {
          this.$message.error(response.data.message);
        }
      } catch (error) {
        console.error('提交评价失败', error);
        this.$message.error('服务器错误，请稍后再试！');
      }
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

.chart-container {
  margin-top: 10px;
}

.el-row {
  margin-top: 20px;
}

.el-col {
  padding: 0 10px;
}

.chart-container el-card {
  width: 100%;
  height: 300px;
  max-width: 100%;
}

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
  height: 400px;
}

line-chart {
  width: 100%; 
  height: 100%;
}

radar-chart {
  width: 100%; 
  height: 100%;
}
</style>
