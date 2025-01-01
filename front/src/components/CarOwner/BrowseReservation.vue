<template>
  <div class="reservation-management">
    <h2>用户预约订单管理</h2>

    <!-- 搜索框和筛选条件 -->
    <div class="filter-container">
      <!-- 预约状态筛选 -->
      <el-select v-model="searchStatus" placeholder="选择预约状态" style="width: 150px; margin-right: 20px;">
        <el-option label="待处理" value="pending"></el-option>
        <el-option label="已完成" value="completed"></el-option>
        <el-option label="已取消" value="cancelled"></el-option>
        <el-option label="进行中" value="charging"></el-option>
        <el-option label="全部" value=""></el-option>
      </el-select>

      <!-- 预约时间筛选 -->
      <el-date-picker
        v-model="searchDate"
        type="daterange"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        style="width: 250px;"
      ></el-date-picker>
    </div>

    <!-- 预约订单列表 -->
    <el-table
      v-loading="loading"
      :data="paginatedReservations"
      border
      stripe
      class="custom-table"
      style="width: 100%;"
    >
      <!-- 预约状态列 -->
      <el-table-column prop="status" label="预约状态" align="center" min-width="15%">
        <template #default="scope">
          <el-tag :type="getStatusTagType(scope.row.status)" effect="dark">
            {{ getStatusLabel(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 预约时间列 -->
      <el-table-column prop="reservationDate" label="预约时间" align="center" min-width="20%">
        <template #default="scope">
          {{ formatDate(scope.row.reservationDate) }}
        </template>
      </el-table-column>

      <!-- 总费用列 -->
      <el-table-column prop="cost" label="总费用(元)" align="center" min-width="10%" />

      <!-- 操作列 -->
      <el-table-column label="操作" align="center" min-width="15%">
        <template #default="scope">
          <el-button size="mini" type="primary" @click="viewReservationDetails(scope.row)">
            查看详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页控件 -->
    <el-pagination
      :current-page="currentPage"       
      :page-size="pageSize"             
      :total="filteredReservationList.length"
      @current-change="handlePageChange" 
      layout="total, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog
      title="预约订单详情"
      v-model="dialogVisible"
      width="60%"
      @close="dialogVisible = false"
    >
    <!-- 使用 el-card 组件来美化布局 -->
    <el-card class="box-card" v-if="currentReservation">
        <div slot="header" class="clearfix">
        <span>预约详情</span>
        </div>

        <!-- 预约信息的展示 -->
        <div class="detail-content">
        <el-row>
            <!-- 用户ID -->
            <el-col :span="8">
            <el-form-item label="订单编号">
                <el-tag>{{ currentReservation.resId }}</el-tag>
            </el-form-item>
            </el-col>

            <!-- 充电桩ID -->
            <el-col :span="8">
            <el-form-item label="充电桩ID">
                <el-tag>{{ currentReservation.pileId }}</el-tag>
            </el-form-item>
            </el-col>

            <!-- 预约时间 -->
            <el-col :span="8">
            <el-form-item label="预约时间">
                <el-tag>{{ formatDate(currentReservation.reservationDate) }}</el-tag>
            </el-form-item>
            </el-col>
        </el-row>

        <el-row>
            <!-- 预约状态 -->
            <el-col :span="8">
            <el-form-item label="预约状态">
                <el-tag :type="getStatusTagType(currentReservation.status)" effect="dark">
                {{ getStatusLabel(currentReservation.status) }}
                </el-tag>
            </el-form-item>
            </el-col>

            <!-- 充电时长 -->
            <el-col :span="8">
            <el-form-item label="充电时长">
                <el-tag>{{ currentReservation.chargeTime }} 小时</el-tag>
            </el-form-item>
            </el-col>

            <!-- 单价 -->
            <el-col :span="8">
            <el-form-item label="单价">
                <el-tag>{{ currentReservation.price }} 元/小时</el-tag>
            </el-form-item>
            </el-col>
        </el-row>

        <el-row>
            <!-- 总费用 -->
            <el-col :span="8">
            <el-form-item label="总费用">
                <el-tag>{{ currentReservation.cost }} 元</el-tag>
            </el-form-item>
            </el-col>

            <!-- 充电类型 -->
            <el-col :span="8">
            <el-form-item label="充电类型">
                <el-tag>快充</el-tag>
            </el-form-item>
            </el-col>

            <!-- 备注 -->
            <el-col :span="8">
            <el-form-item label="备注">
                <el-tag>有事情请联系 15910000000</el-tag>
            </el-form-item>
            </el-col> 
        </el-row>
        </div>

        <!-- 只有在"待处理"状态下显示取消订单按钮 -->
        <div v-if="currentReservation.status === 'pending'" class="dialog-footer">
        <el-button type="danger" @click="cancelReservation">取消订单</el-button>
        <el-button @click="dialogVisible = false">关闭</el-button>
        </div>

        <!-- 只有在"其他"状态下显示关闭按钮 -->
        <div v-if="currentReservation.status === 'charging' || currentReservation.status === 'completed' || currentReservation.status === 'cancelled'" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
        </div>

    </el-card>
    </el-dialog>

  </div>
</template>



<script>
import axios from 'axios';

export default {
  data() {
    return {
      // 搜索框的筛选条件
      searchStatus: '',
      searchDate: [],
      currentPage: 1,  // 当前页码
      pageSize: 10,    // 每页数据量
      // 控制加载状态
      loading: false,

      // 预约列表数据
      reservationList: [],
      filteredReservationList: [],

      // 控制对话框是否可见
      dialogVisible: false,

      // 当前查看的预约信息
      currentReservation: null,
    };
  },
  inject: ['user'],
  computed: {
    // 计算属性：分页后的预约数据
    paginatedReservations() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredReservationList.slice(start, end);
    },
  },
  methods: {
    // 获取预约状态标签类型
    getStatusTagType(status) {
      if (status === 'pending') return 'warning';
      if (status === 'completed') return 'success';
      if (status === 'cancelled') return 'danger';
      if (status === 'charging') return 'info';
      return '';
    },

    // 获取预约状态标签文本
    getStatusLabel(status) {
      if (status === 'pending') return '待处理';
      if (status === 'completed') return '已完成';
      if (status === 'cancelled') return '已取消';
      if (status === 'charging') return '进行中';
      return '';
    },

    formatDate(date) {
    const newDate = new Date(date);
    // 手动调整时区，减去8小时（也可以根据需要调整）
    newDate.setHours(newDate.getHours() - 8);  // 将时间调整为中国时区

    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit'
    };
    return newDate.toLocaleString('zh-CN', options);
    },

    // 查询预约数据
    async fetchReservations() {
        this.loading = true;
        try {
            const response = await axios.get('/home/CarOwner/getAllReservations');
            const data = response.data;

            this.reservationList = data.filter(reservation => { return Number(reservation.userId) === Number(this.user.id);});
            this.filteredReservationList = this.reservationList;
        } catch (error) {
            console.error('获取用户数据失败:', error);
            this.$message.error('获取用户数据失败，请稍后重试');
        } finally {
            this.loading = false;
        }
    },
    // 取消预约订单
    async cancelReservation() {
      try {
        // 调用后端接口取消预约
        const response = await axios.put(`/home/CarOwner/cancelReservation/${this.currentReservation.resId}`);

        if (response.data.status==='success') {
          this.$message.success('订单已成功取消');
          // 更新当前订单的状态
          this.currentReservation.status = 'cancelled';
        } else {
          this.$message.error('取消订单失败，请稍后再试');
        }
      } catch (error) {
        console.error('取消订单失败:', error);
        this.$message.error('服务器错误，请联系开发人员！');
      } finally {
        // 关闭对话框
        this.dialogVisible = false;
      }
    },

    filterReservations() {
      this.filteredReservationList = this.reservationList.filter((reservation) => {
        const statusMatch = this.searchStatus ? reservation.status === this.searchStatus : true;
        const dateMatch =
          !this.searchDate || this.searchDate.length === 0 ||
          (new Date(reservation.reservationDate) >= new Date(this.searchDate[0]) &&
          new Date(reservation.reservationDate) <= new Date(this.searchDate[1]));
        
        return statusMatch && dateMatch;
      });
    },



    // 查看预约详情
    viewReservationDetails(reservation) {
      this.currentReservation = reservation;
      this.dialogVisible = true;
    },
    handlePageChange(page) {
    this.currentPage = page;  // 更新当前页码
  },
  },

  // 当组件加载时，获取预约数据
  mounted() {
    this.fetchReservations();
  },

  watch: {
    // 监听筛选条件变化，自动重新过滤数据
    searchStatus() {
      this.filterReservations();
    },
    searchDate() {
      this.filterReservations();
    },
  },
};
</script>
<style scoped>
.user-management {
  padding: 20px;
}

.filter-container {
  margin-bottom: 20px;
}

.custom-table {
  margin-top: 20px;
}

.el-dialog {
  width: 50%;
}
.detail-content {
  padding: 20px;
}

.el-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.el-form-item {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}


</style>
