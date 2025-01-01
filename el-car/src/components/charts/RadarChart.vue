<template>
  <div ref="radarChart" style="width: 600px; height: 450px;"></div> <!-- 设置宽度和高度 -->
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    data: {
      type: Object,
      required: true
    }
  },
  mounted() {
    this.initChart();
  },
  methods: {
    initChart() {
      const chart = echarts.init(this.$refs.radarChart);
      const option = {
        title: {
          text: '新能源车主评分情况',
          left: 'center',  // 标题居中
          top: 20,  
        },
        radar: {
          indicator: this.data.categories.map(category => ({ name: category, max: 5 })),
          radius: '60%',  // 雷达图的半径调整
          center: ['50%', '50%'],  // 雷达图居中显示
          axisName: {
            color: '#999',  // 坐标轴名称颜色
            fontSize: 14,
          },
          splitLine: {
            lineStyle: {
              color: '#ddd',  // 网格线颜色
            }
          }
        },
        series: [{
          type: 'radar',
          data: [{
            value: this.data.values,
            name: '评分'
          }],
          areaStyle: {
            color: 'rgba(70, 185, 74, 0.4)',  // 填充颜色
          },
          lineStyle: {
            color: '#4caf50',  // 雷达线条颜色
          }
        }]
      };
      chart.setOption(option);
    }
  }
};
</script>

<style scoped>
/* 可以在这里添加更多的样式来调整布局 */
</style>
