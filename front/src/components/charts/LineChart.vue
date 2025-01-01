<template>
  <div ref="lineChart" style="width: 600px; height: 350px;"></div> <!-- 固定宽度 -->
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    data: {
      type: Array,
      required: true
    }
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const chart = echarts.init(this.$refs.lineChart);
      const option = {
        title: {
          text: '24小时使用流量',
          left: 'center', // Title centered
        },
        tooltip: {
          trigger: 'axis', // 鼠标悬停时显示 tooltip
          axisPointer: {
            type: 'line' // 鼠标悬停时显示指示线
          },
          formatter: '{b}: {c} 人次' // 显示格式
        },
        xAxis: {
          type: 'category',
          data: this.data.map(item => item.hour),
          axisLabel: {
            rotate: 45, // 让横坐标的标签倾斜，以防止重叠
            margin: 10, // 标签距离坐标轴的距离
            interval: 0 // 每个刻度都显示
          },
          axisLine: {
            show: true, // 显示坐标轴
            lineStyle: {
              color: '#ccc'
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            formatter: '{value} 人次' // Y轴标签格式
          },
          axisLine: {
            show: true, // 显示坐标轴
            lineStyle: {
              color: '#ccc'
            }
          }
        },
        grid: {
          left: '5%', // 适当减小左边距，保证横坐标显示完全
          right: '5%', // 适当减小右边距
          top: '20%',  // 增加顶部空白，避免标题和坐标轴重叠
          bottom: '5%'  // 减小底部空白，避免盖住雷达图
        },
        series: [{
          data: this.data.map(item => item.usage),
          type: 'line',
          smooth: true, // 平滑折线
          lineStyle: {
            color: '#4caf50' // 线条颜色
          },
          symbolSize: 8, // 调整数据点大小
        }]
      };
      chart.setOption(option);
    }
  }
};
</script>

<style scoped>
</style>
