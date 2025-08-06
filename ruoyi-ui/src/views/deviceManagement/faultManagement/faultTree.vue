<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px"
      :rules="ruleSearch">
      <el-form-item label="设备编号" prop="deviceNum">
        <el-select v-model="queryParams.deviceNum" placeholder="请输入设备编号" clearable @keyup.enter="handleQuery"
          style="width: 200px;" filterable>
          <el-option v-for="item in numberOptionList" :key="item" :label="item" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="报修时间" style="width: 308px" prop="daterangeReportedTime">
        <el-date-picker v-model="queryParams.daterangeReportedTime" format="YY/MM/DD" value-format="YY-MM-DD HH:mm"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
          style="width: 200px;"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <!-- <el-button type="primary" plain icon="Hide" @click="showTable = false" v-if="showTable == true">隐藏表格</el-button>
        <el-button type="primary" plain icon="View" @click="showTable = true" v-if="showTable == false">显示表格</el-button> -->
        <!-- <el-button type="primary" plain icon="View" @click="showTree = true" v-if="showTree == false">显示故障树</el-button>
        <el-button type="primary" plain icon="Hide" @click="showTree = false" v-if="showTree == true">隐藏故障树</el-button> -->
      </el-col>


      <right-toolbar v-model:showSearch="showSearch" @queryTable="handleTreeList"></right-toolbar>
    </el-row>

    <!-- <fault-chart ref="chartRef" v-if="showTree" :chartData="chartData"></fault-chart> -->
    <div class="tree-container">
      <tree-components :chartData="chartData" v-if="showTree" @node-click="showItemDetail">
        <template #level2="{ node }">
          <span>
            {{ node.faultType }}({{ node.count }})
          </span>
          <div>
            ({{ node.timeSum }}h){{ node.expanded ? '-' : '+' }}
          </div>
        </template>
        <template #level3="{ node }">
          <span>
            现象:{{ node.faultPhenomenon }}
          </span>
          <span>
            分析:{{ node.maintenanceAnalysis }}
          </span>
        </template>
      </tree-components>

      <!-- 详情弹窗 -->
      <el-dialog title="维修记录详情" v-model="dialogVisible" width="30%" @close="handleClose" :close-on-click-modal="false">
        <div class="detail-content">
          <p><strong>设备名称：</strong>{{ selectedItem?.deviceName }}</p>
          <p><strong>申请人员：</strong>{{ selectedItem?.applyBy }}</p>
          <p><strong>申请部门：</strong>{{ selectedItem?.applyDepartment }}</p>
          <p><strong>故障现象：</strong>{{ selectedItem?.faultPhenomenon }}</p>
          <p><strong>故障类型：</strong>{{ selectedItem?.issueType }}</p>
          <p><strong>故障时长：</strong>{{ selectedItem?.faultDuration }}</p>
          <p><strong>维修分析：</strong>{{ selectedItem?.maintenanceAnalysis }}</p>
          <p><strong>维修内容：</strong>{{ selectedItem?.maintenanceContent }}</p>
          <p><strong>维修人员：</strong>{{ selectedItem?.maintenancePeople }}</p>
          <p><strong>维修费用：</strong>{{ selectedItem?.maintenanceCast }}</p>
          <p><strong>报修时间：</strong>{{ selectedItem?.reportedTime }}</p>
          <p><strong>处理时间：</strong>{{ selectedItem?.resolutionTime }}</p>
          <p><strong>是否停机：</strong>{{ selectedItem?.ifDown }}</p>
          <p><strong>工作状态：</strong>{{ selectedItem?.workStatus }}</p>
        </div>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
            <el-button @click="handleToAnalysis(selectedItem, 'fault', 'analysis')">跳转至故障分析</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
    <!-- <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="handleChangePage(queryParams.pageNum)" /> -->

    <!-- 添加或修改设备故障记录对话框 -->
    <!-- <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="tableRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设备编号" prop="deviceNum">
          <el-input v-model="form.deviceNum" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="工单状态" prop="workStatus">
          <el-input v-model="form.workStatus" placeholder="请输入工单状态" />
        </el-form-item>
        <el-form-item label="问题类型" prop="issueType">
          <el-input v-model="form.issueType" placeholder="请输入问题类型" />
        </el-form-item>
        <el-form-item label="故障类型" prop="faultType">
          <el-input v-model="form.faultType" placeholder="请输入故障类型" />
        </el-form-item>
        <el-form-item label="申请人" prop="applyBy">
          <el-input v-model="form.applyBy" placeholder="请输入申请人" />
        </el-form-item>
        <el-form-item label="申请部门" prop="applyDepartment">
          <el-input v-model="form.applyDepartment" placeholder="请输入申请部门" />
        </el-form-item>
        <el-form-item label="维修人员" prop="maintenancePeople">
          <el-input v-model="form.maintenancePeople" placeholder="请输入维修人员" />
        </el-form-item>
        <el-form-item label="故障现象" prop="faultPhenomenon">
          <el-input v-model="form.faultPhenomenon" placeholder="请输入故障现象" />
        </el-form-item>
        <el-form-item label="维修分析" prop="maintenanceAnalysis">
          <el-input v-model="form.maintenanceAnalysis" placeholder="请输入维修分析" />
        </el-form-item>
        <el-form-item label="维修内容" prop="maintenanceContent">
          <el-input v-model="form.maintenanceContent" placeholder="请输入维修内容" />
        </el-form-item>
        <el-form-item label="报修时间" prop="reportedTime">
          <el-input v-model="form.reportedTime" placeholder="请输入报修时间" />
        </el-form-item>
        <el-form-item label="处理时间" prop="resolutionTime">
          <el-input v-model="form.resolutionTime" placeholder="请输入处理时间" />
        </el-form-item>
        <el-form-item label="故障时长" prop="faultDuration">
          <el-input v-model="form.faultDuration" placeholder="请输入故障时长" />
        </el-form-item>
        <el-form-item label="维修费用" prop="maintenanceCast">
          <el-input v-model="form.maintenanceCast" placeholder="请输入维修费用" />
        </el-form-item>
        <el-form-item label="是否停机" prop="ifDown">
          <el-input v-model="form.ifDown" placeholder="请输入是否停机" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog> -->

  </div>
</template>


<script setup name="FaultTree">
import { listTable, getTable, delTable, addTable, updateTable, uploadFile, getTreeList, numberList } from "@/api/device/maintenanceTable/table";
import { ElMessage } from 'element-plus';
import faultChart from "./faultChart.vue"
import TreeComponents from './TreeComponents.vue'

const { proxy } = getCurrentInstance();
const dialogVisible = ref(false)
// 定义事件处理
const showItemDetail = (item) => {
  selectedItem.value = item
  dialogVisible.value = true
}

const closeDetail = () => {
  selectedItem.value = null
}
const total = ref(0);
const selectedItem = ref(null)
const tableList = ref([]);
const open = ref(false);
const loading = ref(false);
const showTree = ref(false);
const showTable = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const chartData = reactive({})
const route = useRoute();
const title = ref("");
const pagesList = ref([]);
const numberOptionList = ref([]);
const router = useRouter();
const showDialog = ref(false);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 1000,
    deviceName: null,
    deviceNum: null,
    workStatus: null,
    faultType: null,
    applyBy: null,
    applyDepartment: null,
    maintenancePeople: null,
    faultPhenomenon: null,
    maintenanceAnalysis: null,
    maintenanceContent: null,
    reportedTime: null,
    resolutionTime: null,
    faultDuration: null,
    maintenanceCast: null,
    ifDown: null,
    daterangeReportedTime: null,
    daterangeResolutionTime: null
  },

  rules: {
  },
  ruleSearch: {
    deviceNum: [
      { required: true, message: '设备编号不能为空', trigger: 'change' }
    ],
  }
});

const { queryParams, form, rules, ruleSearch } = toRefs(data);

onMounted(() => {
  numberList().then(r => {
    console.log(r.rows)
    numberOptionList.value = r.rows
  })
});


function handleTreeList() {
  // loading.value = true;
  queryParams.value.params = {};
  if (null != queryParams.value.daterangeReportedTime && '' != queryParams.value.daterangeReportedTime && Array.isArray(queryParams.value.daterangeReportedTime)) {
    queryParams.value.params["beginReportedTime"] = queryParams.value.daterangeReportedTime[0];
    queryParams.value.params["endReportedTime"] = queryParams.value.daterangeReportedTime[1];
  }
  if (null != queryParams.value.daterangeResolutionTime && '' != queryParams.value.daterangeResolutionTime && Array.isArray(queryParams.value.daterangeResolutionTime)) {
    queryParams.value.params["beginResolutionTime"] = queryParams.value.daterangeResolutionTime[0];
    queryParams.value.params["endResolutionTime"] = queryParams.value.daterangeResolutionTime[1];
  }
  getTreeList(queryParams.value).then(response => {
    total.value = response.total
    pagesList.value = paginateData(response).pages;
    // console.log({ pagesList })
    showTree.value = true
    chartData.data = pagesList.value[0][0]

    // loading.value = false;
  })
}
function paginateData(data, pageSize = 1000) {
  // console.log({ data })
  const rows = data.rows;
  const totalRows = rows.length;
  const totalPages = Math.ceil(totalRows / pageSize);
  const pages = [];

  for (let i = 0; i < totalRows; i += pageSize) {
    pages.push(rows.slice(i, i + pageSize));
  }
  // tableList.value = pages[0]

  // console.log({ pages })
  return {
    totalPages: totalPages,
    pages: pages
  };

}
//跳转
function handleToAnalysis(row, module, destination) {
  router.push({ path: `/deviceManagement/${module}Management/${destination}`, query: { faultType: row.faultType, yearAndMonth: row.reportedTime } });
}



// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    maintenanceTableId: null,
    deviceName: null,
    workStatus: null,
    faultType: null,
    applyBy: null,
    applyDepartment: null,
    maintenancePeople: null,
    faultPhenomenon: null,
    maintenanceAnalysis: null,
    maintenanceContent: null,
    reportedTime: null,
    resolutionTime: null,
    faultDuration: null,
    maintenanceCast: null,
    ifDown: null
  };
  proxy.resetForm("tableRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  proxy.$refs["queryRef"].validate(valid => {
    if (valid) {
      queryParams.value.pageNum = 1;
      handleTreeList();
    }
  })

}

/** 重置按钮操作 */
function resetQuery() {
  queryParams.daterangeReportedTime.value = [];
  queryParams.daterangeResolutionTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}


// /** 删除按钮操作 */
// function handleDelete(row) {
//   const _maintenanceTableIds = row.maintenanceTableId || ids.value;
//   proxy.$modal.confirm('是否确认删除2.设备故障记录(导入)编号为"' + _maintenanceTableIds + '"的数据项？').then(function () {
//     return delTable(_maintenanceTableIds);
//   }).then(() => {
//     getList();
//     proxy.$modal.msgSuccess("删除成功");
//   }).catch(() => { });
// }



</script>
