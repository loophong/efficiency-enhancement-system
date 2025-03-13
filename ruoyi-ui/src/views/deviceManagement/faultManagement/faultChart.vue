<template>
  <div class="index">
    <!-- 确保给容器一个明确的高度和宽度 -->
    <div ref="chartContainer" id="main" style="width: 100%; height: 600px;"></div>
  </div>
</template>

<script setup>
import * as echarts from 'echarts';
import { onMounted } from 'vue';

const treeData = ref([]);
const myChart = ref(null)
const router = useRouter();
const props = defineProps({
  chartData: {
    type: Object,
    required: true,
  },
});

function renameField(node) {
  if (!node) return;

  // 处理当前节点
  if (node.deviceNum) node.name = node.deviceNum;
  if (node.faultType) {
    node.name = `${node.faultType}(${node.count})(${node.timeSum}h)`;
  }
  if (node.maintenanceAnalysis) {
    node.name = `分析:${node.maintenanceAnalysis}(${node.issueType})`;
    node.value = `现象:${node.maintenanceAnalysis}`;
  }

  // 递归处理子节点
  if (Array.isArray(node.children)) {
    node.children.forEach(child => renameField(child));
  }
}

function parseChartData(input) {
  const data = JSON.parse(JSON.stringify(input));

  if (!Array.isArray(data)) return;

  if (data.length === 1) {
    renameField(data[0]);
    treeData.value = data[0];
  } else {
    data.forEach(item => {
      item.name = item.deviceNum;
      renameField(item);
    });
    treeData.value = data;
  }
}
parseChartData(props.chartData.data[0]);
console.log('chartData---->', props.chartData.data[0])

console.log('treeData----->', treeData.value)

const chartContainer = ref(null);

onMounted(() => {
  // 确认DOM元素存在
  const container = chartContainer.value;
  if (container) {
    container.addEventListener('click', () => {
      console.log('DOM点击事件触发');
    });
  }
  initChart();
  myChart.value.on('click', function (params) {
    console.log('任意点击事件:', params);
    if (!params.data.children) {
      console.log('叶子节点数据:', params.data);
      handleToAnalysis(params.data, 'fault', 'analysis')
      console.log('完整路径:', getNodePath(params));
    }
  });
});

function initChart() {
  if (!chartContainer.value) {
    console.error('Chart container not found!');
    return;
  }
  let data = {}
  myChart.value = echarts.init(chartContainer.value);
  if (Array.isArray(props.chartData.data[0]) && props.chartData.data[0].length > 1) {
    console.log('多')
    data = {
      name: '故障树',
      children: treeData.value
    }
  } else if (Array.isArray(props.chartData.data[0]) && props.chartData.data[0].length == 1) {
    console.log('少')
    data = treeData.value
  }
  console.log(data)

  const option = {
    tooltip: {
      trigger: 'item',
      triggerOn: 'mousemove'
    },
    series: [
      {
        type: 'tree',
        id: 0,
        name: 'tree1',
        data: [data],
        top: '10%',
        left: '8%',
        bottom: '22%',
        right: '20%',
        symbolSize: 7,
        edgeShape: 'polyline',
        edgeForkPosition: '63%',
        initialTreeDepth: 20,
        lineStyle: {
          width: 2
        },
        label: {
          backgroundColor: '#fff',
          position: 'left',
          verticalAlign: 'middle',
          align: 'right'
        },
        leaves: {
          label: {
            position: 'right',
            verticalAlign: 'middle',
            align: 'left'
          }
        },
        emphasis: {
          focus: 'descendant'
        },
        expandAndCollapse: true,
        animationDuration: 550,
        animationDurationUpdate: 750,

      }
    ]

  };

  option && myChart.value.setOption(option);
  myChart.value.on('click', { seriesId: 0 }, (params) => {
    console.log('点击事件触发:', params); // 检查点击事件是否被触发以及params的内容
    if (params.dataType === 'leaf' || !params.data.children) {
      console.log('叶子节点数据:', params.data);
      console.log('完整路径:', getNodePath(params));
    }
  });
  // 窗口大小改变时重绘图表
  window.addEventListener('resize', () => {
    myChart.value.resize();
  });
}
// 节点路径获取方法
const getNodePath = (params) => {
  const path = [];
  let current = params.data;
  while (current) {
    path.unshift(current.name);
    current = current.parentNode;
  }
  return path.join(' -> ');
};

// 组件卸载时的清理
// onBeforeUnmount(() => {
//   if (myChart) {
//     myChart.off('click'); // 移除事件监听
//     myChart.dispose();
//   }
// });

function handleToAnalysis(row, module, destination) {
  router.push({ path: `/deviceManagement/${module}Management/${destination}`, query: { faultType: row.faultType, yearAndMonth: row.reportedTime } });
}
</script>

<style scoped>
.index #main {
  width: 100%;
  height: 100%;
}
</style>