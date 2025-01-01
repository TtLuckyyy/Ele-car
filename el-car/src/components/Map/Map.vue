<template>
  <div class="map-container">
    <!-- 地图容器 -->
    <div id="map-container" class="map-container-style"></div>

    <!-- 充电站信息 -->
    <el-card :body-style="{ padding: '20px' }" class="station-info-card">
      <div slot="header" class="clearfix">
        <span class="card-title">{{ selectedStation ? selectedStation.name : '充电站信息统计' }}</span>
      </div>
      <el-row gutter={20}>
        <!-- 显示总的统计信息 -->
        <el-col :span="12" v-if="!selectedStation">
          <el-tag type="success" class="info-tag">充电站总数：{{ stationData.length }}</el-tag><br>
          <el-tag class="info-tag">电动车充电桩类型：交流桩、直流桩</el-tag><br>
          <el-tag class="info-tag">平均充电价格：1.7元/度</el-tag><br>
          <el-tag class="info-tag">正在使用的充电桩：5</el-tag><br>
        </el-col>

        <!-- 显示充电站的详细信息 -->
        <el-col :span="12" v-if="selectedStation">
          <el-tag type="primary" class="info-tag">类型：{{ selectedStation.type }}</el-tag><br>
          <el-tag type="info" class="info-tag">位置：{{ selectedStation.location.join(', ') }}</el-tag><br>
          <el-tag type="warning" class="info-tag">价格：{{ selectedStation.price }}</el-tag><br>
          <el-tag type="danger" class="info-tag">营业时间：{{ selectedStation.operatingTime }}</el-tag><br>
        </el-col>

        <!-- 继续显示总的统计信息 -->
        <el-col :span="12" v-if="!selectedStation">
          <el-tag class="info-tag">每日充电次数：120</el-tag><br>
          <el-tag class="info-tag">充电桩总数：20</el-tag><br>
          <el-tag class="info-tag">服务区域：上海全境</el-tag><br>
          <el-tag class="info-tag">预计用户增长：15%</el-tag><br>
        </el-col>

        <!-- 继续显示充电站的详细信息 -->
        <el-col :span="12" v-if="selectedStation">
          <el-tag type="success" class="info-tag">可用充电桩：{{ selectedStation.availableChargers }}</el-tag><br>
          <el-tag type="primary" class="info-tag">总充电次数：{{ selectedStation.totalCharges }}</el-tag><br>
          <el-tag type="info" class="info-tag">充电站ID：{{ selectedStation.id }}</el-tag><br>
          <el-tag type="warning" class="info-tag">充电站状态：{{ selectedStation.status }}</el-tag><br>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { ElCard, ElRow, ElCol, ElTag } from 'element-plus';

export default {
  components: {
    ElCard,
    ElRow,
    ElCol,
    ElTag,
  },
  data() {
    return {
      map: null, // 存储地图实例
      stationData: [], // 存储充电站数据
      selectedStation: null, // 当前选中的充电站
    };
  },
  mounted() {
    this.initMap();
    this.loadChargingStations();
  },
  methods: {
    // 初始化地图
    initMap() {
      this.map = new AMap.Map('map-container', {
        center: [121.4737, 31.2304], // 上海市中心的经纬度
        zoom: 12, // 初始缩放级别
        resizeEnable: true, // 启用地图大小自适应
        mapStyle: 'amap://styles/blue-navy', // 设置蓝色主题地图样式
      });
    },

    // 模拟加载充电站数据
    loadChargingStations() {
      this.stationData = [
  { 
    id: 1,
    name: '充电站 1', 
    location: [121.4737, 31.2304], 
    price: '1.5元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 3, 
    totalCharges: 102,
    status: '正常',
  },
  { 
    id: 2,
    name: '充电站 2', 
    location: [121.4900, 31.2400], 
    price: '2.0元/度', 
    type: '直流桩', 
    operatingTime: '08:00-22:00',
    availableChargers: 2,
    totalCharges: 58,
    status: '正常',
  },
  { 
    id: 3,
    name: '充电站 3', 
    location: [121.5100, 31.2500], 
    price: '1.8元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 1,
    totalCharges: 76,
    status: '维护中',
  },
  { 
    id: 4,
    name: '充电站 4', 
    location: [121.4300, 31.2200], 
    price: '1.6元/度', 
    type: '直流桩', 
    operatingTime: '08:00-20:00',
    availableChargers: 4,
    totalCharges: 85,
    status: '正常',
  },
  { 
    id: 5,
    name: '充电站 5', 
    location: [121.4500, 31.2205], 
    price: '1.7元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 5,
    totalCharges: 132,
    status: '正常',
  },
  { 
    id: 6,
    name: '充电站 6', 
    location: [121.4600, 31.2350], 
    price: '1.9元/度', 
    type: '直流桩', 
    operatingTime: '06:00-22:00',
    availableChargers: 3,
    totalCharges: 90,
    status: '正常',
  },
  { 
    id: 7,
    name: '充电站 7', 
    location: [121.4800, 31.2550], 
    price: '1.4元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 6,
    totalCharges: 110,
    status: '正常',
  },
  { 
    id: 8,
    name: '充电站 8', 
    location: [121.4200, 31.2100], 
    price: '2.2元/度', 
    type: '直流桩', 
    operatingTime: '09:00-21:00',
    availableChargers: 2,
    totalCharges: 50,
    status: '正常',
  },
  { 
    id: 9,
    name: '充电站 9', 
    location: [121.4950, 31.2225], 
    price: '1.6元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 4,
    totalCharges: 95,
    status: '正常',
  },
  { 
    id: 10,
    name: '充电站 10', 
    location: [121.5050, 31.2300], 
    price: '2.1元/度', 
    type: '直流桩', 
    operatingTime: '10:00-20:00',
    availableChargers: 3,
    totalCharges: 65,
    status: '正常',
  },
  { 
    id: 11,
    name: '充电站 11', 
    location: [121.5150, 31.2150], 
    price: '1.8元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 5,
    totalCharges: 140,
    status: '正常',
  },
  { 
    id: 12,
    name: '充电站 12', 
    location: [121.4300, 31.2400], 
    price: '1.7元/度', 
    type: '直流桩', 
    operatingTime: '08:00-18:00',
    availableChargers: 3,
    totalCharges: 80,
    status: '故障',
  },
  { 
    id: 13,
    name: '充电站 13', 
    location: [121.4200, 31.2550], 
    price: '1.5元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 4,
    totalCharges: 120,
    status: '正常',
  },
  { 
    id: 14,
    name: '充电站 14', 
    location: [121.4950, 31.2400], 
    price: '2.0元/度', 
    type: '直流桩', 
    operatingTime: '10:00-22:00',
    availableChargers: 2,
    totalCharges: 60,
    status: '正常',
  },
  { 
    id: 15,
    name: '充电站 15', 
    location: [121.4600, 31.2200], 
    price: '1.9元/度', 
    type: '交流桩', 
    operatingTime: '08:00-22:00',
    availableChargers: 5,
    totalCharges: 105,
    status: '正常',
  },
  { 
    id: 16,
    name: '充电站 16', 
    location: [121.4750, 31.2450], 
    price: '2.3元/度', 
    type: '直流桩', 
    operatingTime: '09:00-20:00',
    availableChargers: 2,
    totalCharges: 45,
    status: '正常',
  },
  { 
    id: 17,
    name: '充电站 17', 
    location: [121.4300, 31.2650], 
    price: '1.6元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 4,
    totalCharges: 98,
    status: '正常',
  },
  { 
    id: 18,
    name: '充电站 18', 
    location: [121.4950, 31.2750], 
    price: '1.7元/度', 
    type: '直流桩', 
    operatingTime: '06:00-22:00',
    availableChargers: 3,
    totalCharges: 74,
    status: '正常',
  },
  { 
    id: 19,
    name: '充电站 19', 
    location: [121.4750, 31.2300], 
    price: '1.8元/度', 
    type: '交流桩', 
    operatingTime: '24小时',
    availableChargers: 5,
    totalCharges: 110,
    status: '维护中',
  },
  { 
    id: 20,
    name: '充电站 20', 
    location: [121.4550, 31.2150], 
    price: '1.9元/度', 
    type: '直流桩', 
    operatingTime: '08:00-22:00',
    availableChargers: 4,
    totalCharges: 80,
    status: '正常',
  }
];


      this.addChargingStationMarkers();
    },

    // 在地图上添加充电站的标记
    addChargingStationMarkers() {
      this.stationData.forEach((station) => {
        const marker = new AMap.Marker({
          position: station.location,
          title: station.name,
        });

        marker.on('click', () => {
          this.showStationInfo(station);
        });

        marker.setMap(this.map);
      });
    },

    // 显示充电站的信息
    showStationInfo(station) {
      this.selectedStation = station;
    },
  },
};
</script>

<style scoped>
.map-container {
  margin-bottom: 20px;
  position: relative;
}

.map-container-style {
  width: 100%;
  height: 500px;
  border-radius: 12px; /* 圆角效果 */
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1); /* 柔和阴影 */
  border: 2px solid #e0e0e0; /* 细边框 */
}

.station-info-card {
  margin-top: 20px;
  background-color: white; /* 背景改为白色 */
  border-radius: 10px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.station-info-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
}

.card-title {
  font-size: 22px;
  font-weight: bold;
  color: #34495e;
}

.el-tag {
  font-size: 18px; /* 字体稍大 */
  margin-bottom: 12px;
}

.info-bar {
  background-color: #f4f6f9;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}
</style>
