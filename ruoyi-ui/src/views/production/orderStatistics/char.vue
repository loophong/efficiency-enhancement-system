<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
      <el-form-item label="统计年月" prop="yearAndMonth">
        <el-date-picker v-model="queryParams.yearAndMonth" type="monthrange" clearable
                        unlink-panels range-separator="-" start-placeholder="开始时间" date-format="yyyy-MM"
                        value-format="YYYY-MM-DD"
                        end-placeholder="结束时间" :shortcuts="shortcuts">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="产能类型" prop="capacityType">
        <el-select v-model="queryParams.capacityType" placeholder="请选择产能类型" clearable filterable style="width: 240px">
          <el-option v-for="item in capacityTypeList"
                     :key="item" :label="item" :value="item"/>
        </el-select>
      </el-form-item>


      <el-form-item label="车型" prop="vehicleModel">
        <el-select v-model="queryParams.vehicleModel" placeholder="请选择车型" clearable filterable style="width: 240px">
          <el-option v-for="item in vehicleModelList"
                     :key="item" :label="item" :value="item"/>
        </el-select>
      </el-form-item>

      <el-form-item>
        <!--        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>-->
        <!--        <el-button icon="Refresh" @click="resetQuery">重置</el-button>-->
      </el-form-item>
    </el-form>

    <div ref="chart" style="width: 100%; height: 400px;"></div>
  </div>
</template>

<script setup name="char">
import {listOrderStatistics, charData} from "@/api/production/orderStatistics";
import {all} from "@/api/production/capacity";
import {listVehicleModel} from "@/api/production/cycle.js";
import * as echarts from 'echarts';

const {proxy} = getCurrentInstance();

const orderStatisticsList = ref([]);
const open = ref(false);

const loading = ref(true);

const single = ref(true);
const multiple = ref(true);

const data = reactive({
  form: {},
  queryParams: {
    yearAndMonth: null,
    vehicleModel: null,
    capacityType: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

const shortcuts = [
  {
    text: '本月',
    value: [new Date(), new Date()],
  },
  {
    text: '本年',
    value: () => {
      const end = new Date()
      const start = new Date(new Date().getFullYear(), 0)
      return [start, end]
    },
  },
  {
    text: '过去6个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setMonth(start.getMonth() - 6)
      return [start, end]
    },
  },
  {
    text: '过去12个月',
    value: () => {
      const end = new Date()
      const start = new Date()
      start.setMonth(start.getMonth() - 12)
      return [start, end]
    },
  },
]

/** 查询历史订单统计列表 */
function getList() {
  loading.value = true;
  listOrderStatistics(queryParams.value).then(response => {
    orderStatisticsList.value = response.rows;
    loading.value = false;
  });
}

const vehicleModelList = ref([]);
const capacityTypeList = ref([]);

/** 查询全部车型 */
function getVehicleModel() {
  listVehicleModel().then(response => {
    console.log("查询全部车型", response)
    response.data.forEach(item => {
      vehicleModelList.value.push(item.vehicleModel)
    })
    console.log("获取产能类型列表", vehicleModelList)
  });
}

/** 获取产能类型列表 */
function getCapacityType() {
  all().then(response => {
    console.log("获取产能类型列表", response)
    response.data.forEach(item => {
      capacityTypeList.value.push(item.capacityType)
    })
    console.log("获取产能类型列表", capacityTypeList)
  });
}

/**
 * 初始化时调用上面两个方法
 */
onMounted(() => {
  getVehicleModel();
  getCapacityType();
  // initChart();
})

watch(
    () => queryParams.value.yearAndMonth,
    (newVal, oldVal) => {
      onDataChange();
    }
);

watch(
    () => queryParams.value.vehicleModel,
    (newVal, oldVal) => {
      onDataChange();
    }
);

watch(
    () => queryParams.value.capacityType,
    (newVal, oldVal) => {
      onDataChange();
    }
);

const charDataList = ref([]);
const onDataChange = () => {
  // 当 queryParams 中的任意一个属性发生变化时，会触发此函数
  console.log('数据发生了变化:', queryParams.value);

  const charParams = {
    'startTime': queryParams.value.yearAndMonth && queryParams.value.yearAndMonth[0] !== undefined ? queryParams.value.yearAndMonth[0] : null,
    'endTime': queryParams.value.yearAndMonth && queryParams.value.yearAndMonth[1] !== undefined ? queryParams.value.yearAndMonth[1] : null,
    'vehicleModel': queryParams.value.vehicleModel,
    'capacityType': queryParams.value.capacityType
  }
  console.log("charParams", charParams)
  charData(charParams).then(response => {
    console.log("char数据为", response)
    if (response.data && response.data.length > 0) {
      charDataList.value = response.data;
      console.log("response.data", response.data)
      console.log("charDataList", charDataList.value)
    } else {
      charDataList.value = [];
      console.log("response.data 为空，charDataList 清空")
    }
    // 清空char数据
    allVehicleModels = [];
    months = [];
    seriesData = [];
    vehicleModelMap = {};
    // 重新渲染图表
    initChart();
  });
};

const chart = ref(null);

let allVehicleModels = [];
let months = [];
let seriesData = [];
let vehicleModelMap = {};
const initChart = () => {

  if (!chart.value) return;

  // Dispose of the existing chart instance if it exists
  if (echarts.getInstanceByDom(chart.value)) {
    echarts.dispose(chart.value);
  }

  const myChart = echarts.init(chart.value);

  console.log("重新渲染echars")

  if (charDataList.value.length === 0) {
    // 设置空数据时的默认图表选项
    const option = {
      title: {
        text: '订单预测 - 无数据',
        left: 'center'
      },
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: []
    };
    myChart.setOption(option);
    return;
  }

  // 按月份分组，并生成适合 ECharts 的数据格式
  const months = [...new Set(charDataList.value.map(item => item.yearAndMonth.slice(0, 7)))].sort(); // 排序月份

  // 构建 series 数据
  seriesData = [];
  vehicleModelMap = {}; // 用于存储每个车型的产量数据

  // 遍历原始数据，构建车型与月份的映射关系
  charDataList.value.forEach(item => {
    const {vehicleModel, yearAndMonth, quantity} = item;
    const month = yearAndMonth.slice(0, 7);
    if (!vehicleModelMap[vehicleModel]) {
      vehicleModelMap[vehicleModel] = {};
    }
    // 确保只记录实际存在的月份和对应的产量
    vehicleModelMap[vehicleModel][month] = quantity;
  });

  // 将车型数据转换为 ECharts 的 series 格式
  for (const vehicleModel in vehicleModelMap) {
    const monthlyQuantities = months.map(month => {
      return vehicleModelMap[vehicleModel][month] || 0; // 如果某月没有数据，则填充 0
    });

    // 只保留有产量的车型
    if (monthlyQuantities.some(quantity => quantity > 0)) {
      seriesData.push({
        name: vehicleModel, // 车型名称作为图例
        type: 'bar',
        data: monthlyQuantities,
        label: {
          show: true,
          position: 'top' // 标签显示在柱子顶部
        }
      });
    }
  }

  // 配置 ECharts 选项
  const option = {
    title: {
      text: '订单预测 - 按日期分组',
      left: 'center'
    },
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: seriesData.map(series => series.name), // 图例只显示有产量的车型
      type: 'scroll', // 可滚动图例（可选）
      orient: 'vertical', // 图例垂直排列
      right: '10px', // 图例靠右对齐
      top: '20px' // 图例距离顶部 20px
    },
    xAxis: {
      type: 'category', // X 轴为分类轴（日期）
      data: months, // X 轴数据为月份
      name: '日期',
      axisLabel: {
        rotate: 45, // 旋转标签，避免重叠
        interval: 0 // 强制显示所有标签
      }
    },
    yAxis: {
      type: 'value', // Y 轴为数值轴（产量）
      name: '产量'
    },
    series: seriesData
  };

  // 设置图表选项
  myChart.setOption(option);
};

</script>