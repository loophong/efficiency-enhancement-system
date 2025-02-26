<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryRef" :inline="true" label-width="68px">
      <el-form-item label="统计年月" prop="yearAndMonth">
        <el-date-picker v-model="queryParams.yearAndMonth" type="monthrange" clearable
                        unlink-panels range-separator="-" start-placeholder="开始时间"
                        end-placeholder="结束时间" :shortcuts="shortcuts">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="车型" prop="vehicleModel">
        <el-select v-model="queryParams.vehicleModel" placeholder="请选择车型" clearable style="width: 240px">
          <el-option v-for="item in vehicleModelList"
                     :key="item" :label="item" :value="item"/>
        </el-select>
      </el-form-item>
      <el-form-item label="产能类型" prop="capacityType">
        <el-select v-model="queryParams.capacityType" placeholder="请选择产能类型" clearable style="width: 240px">
          <el-option v-for="item in capacityTypeList"
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

// function onDataChange() {   import * as echarts from 'echarts';

//   // 当 queryParams 中的任意一个属性发生变化时，会触发此函数
//   console.log('数据发生了变化:', queryParams);
//   // 在这里添加你需要执行的逻辑
// }
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
    charDataList.value = response.data;
    console.log("response.data", response.data)
    console.log("charDataList", charDataList.value)
  })
  initChart();
};

const chart = ref(null);

const initChart = () => {

    if (!chart.value) return;
  const myChart = echarts.init(chart.value);

  let xAxisData = [];
  let seriesData = [];
   charDataList.value.forEach(item => {
     xAxisData.push(item.yearAndMonth)
     seriesData.push(item.quantity)
  });
  // const seriesData = charDataList.value.map(item => item.quantity);


  console.log("charDataList", charDataList.value)
  console.log("xAxisData", xAxisData)
  console.log("seriesData", seriesData)

  const option = {
    title: {
      text: '产能统计'
    },
    tooltip: {},
    legend: {
      data: ['产能']
    },
    xAxis: {
      type: 'category',
      data: xAxisData
    },
    // xAxis: {
    //   type: 'category',
    //   boundaryGap: false,
    //   data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    // },
    yAxis: {
      type: 'value'
    },
    series: [{
      name: '产能',
      type: 'bar',
      data: seriesData
    }]
  };

  myChart.setOption(option);
};

</script>