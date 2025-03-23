<template>

  <div class="app-container">
    <el-row>
      <el-col :span="8">
        <el-form :model="capacity" label-width="120px">
          <el-form-item label="预测月份">
            <el-date-picker v-model="monthDate" type="month" date-format="yyyy-MM-dd" placeholder="选择日期"
                            style="width: 200px;"/>
            <el-button type="primary" @click="handleForecast" style="margin-left: 10px;">预测</el-button>
            <el-button type="success" @click="exportToExcel" style="margin-left: 10px;">导出 Excel</el-button>
          </el-form-item>
          <div v-for="(item, index) in capacity" :key="index">
            <div style="display: flex; align-items: center;">
              <el-form-item :label="item.capacityType" label-width="200px">
                <el-input-number v-model="item.productionQuantity" :min="0"></el-input-number>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </el-col>
      <el-col :span="16">
        <el-table ref="resultTable" :data="resultList" height="600px" width="auto"
                  @selection-change="handleUnscheduledSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <!--      <el-table-column label="网点名称" align="center" prop="branch"/>-->
          <!--      <el-table-column label="合同号" align="center" prop="contractNumber"/>-->
          <!--      <el-table-column label="订单号" align="center" prop="orderNumber"/>-->
          <!--      <el-table-column label="接单日期" align="center" prop="orderDate" width="100">-->
          <!--        <template #default="scope">-->
          <!--          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <el-table-column label="车型" align="center" prop="vehicleModel" />
          <el-table-column label="门架" align="center" prop="mast" />
          <el-table-column label="数量" align="center" prop="quantity"/>
          <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
          <el-table-column label="备注信息" align="center" prop="remarks" />
          <!--      <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">-->
          <!--        <template #default="scope">-->
          <!--          <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <!--      <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">-->
          <!--        <template #default="scope">-->
          <!--          <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <!--      <el-table-column label="生产周期(天)" align="center" prop="productionCycle"/>-->
          <el-table-column label="产能型号" align="center" prop="capacityType" />
          <!--      <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="100">-->
          <!--        <template #default="scope">-->
          <!--          <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <!--      <el-table-column label="是否超期" align="center" prop="isOverdue"/>-->
        </el-table>
      </el-col>
    </el-row>


  </div>
</template>

<script setup name="forecast">

import {all} from "@/api/production/capacity.js";
import * as XLSX from 'xlsx';
import {
  forecast
} from "@/api/production/orderStatistics";


// 预测产能列表
const capacity = ref([]);
// 排产日期
const monthDate = ref('')

onMounted(() => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  monthDate.value = `${year}-${month}`;
});
const {proxy} = getCurrentInstance();

/** 初始化时，获取产能列表 */
function getCapacityList() {
  all().then(response => {
    //  从response.data中解析出车型 并 给出默认产量
    response.data.capacityEntities.forEach(item => {
      if (item.productionStatus)
        capacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
    })
    console.log("初始化产能" + JSON.stringify(capacity.value))
  })
}

// 初始化
getCapacityList()

const resultList = ref([])

function handleForecast() {

  let date = monthDate.value

  let capacityList = capacity.value

  let forecastParams = {
    date: date,
    capacityList: capacityList
  }

  console.log("提交预测产能，时间：" + date + "列表：" + JSON.stringify(capacityList))

  forecast(forecastParams).then(response => {
    console.log("预测成功,结果如下:" + response.data)
    resultList.value = response.data
  })
}

// 选中的数据
const selectedData = ref([]);


/** 处理选中数据 */
function handleUnscheduledSelectionChange(selection) {
  selectedData.value = selection;
}

const exportData = ref([{
  '车型': '',
  '门架': '',
  '数量': '',
  '备注信息': '',
  '产能类型': ''
}]);

/** 转换数据结构 */
function transformDataForExport(data) {
  return data.map(item => ({
    '车型': item.vehicleModel,
    '门架': item.mast,
    '数量': item.quantity,
    '备注信息': item.remarks,
    '产能类型': item.capacityType
  }));
}

/** 导出 Excel */
function exportToExcel() {
  if (selectedData.value.length === 0) {
    console.log("没有数据");
    // 提示用户选择数据
    proxy.$modal.msgError("请选择需要导出的数据");
    return;
  }

  // 转换数据结构
  const transformedData = transformDataForExport(selectedData.value);

  // 将转换后的数据转换为 Excel 工作表
  const worksheet = XLSX.utils.json_to_sheet(transformedData);
  const workbook = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(workbook, worksheet, '预测数据');

  // 生成 Excel 文件并下载
  const fileName = `预测数据_${new Date().getTime()}.xlsx`;
  XLSX.writeFile(workbook, fileName);

  proxy.$modal.msgSuccess("导出成功");
}

</script>