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
      <el-form-item label="处理时间" style="width: 308px" prop="daterangeResolutionTime">
        <el-date-picker v-model="queryParams.daterangeResolutionTime" format="YY/MM/DD" value-format="YY-MM-DD HH:mm"
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
        <el-button type="primary" plain icon="Hide" @click="showTable = false" v-if="showTable == true">隐藏表格</el-button>
        <el-button type="primary" plain icon="View" @click="showTable = true" v-if="showTable == false">显示表格</el-button>
        <el-button type="primary" plain icon="View" @click="showTree = true" v-if="showTree == false">显示故障树</el-button>
        <el-button type="primary" plain icon="Hide" @click="showTree = false" v-if="showTree == true">隐藏故障树</el-button>
      </el-col>


      <right-toolbar v-model:showSearch="showSearch" @queryTable="handleTreeList"></right-toolbar>
    </el-row>

    <el-table v-if="showTable" v-loading="loading" :data="tableList" row-key="uniqueId"
      @selection-change="handleSelectionChange" border stripe
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="主键id" align="center" prop="maintenanceTableId" /> -->
      <el-table-column label="设备编号" align="center" prop="deviceNum" width="180" />
      <el-table-column label="故障类型" align="center" prop="faultType">
        <template #default="scope">
          <span v-if="scope.row.count">{{ scope.row.faultType }}({{ scope.row.count }})</span>
          <span v-else>--</span>
        </template>
      </el-table-column>
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="工单状态" align="center" prop="workStatus" />
      <el-table-column label="问题类型" align="center" prop="issueType" />
      <el-table-column label="申请人" align="center" prop="applyBy" />
      <el-table-column label="申请部门" align="center" prop="applyDepartment" />
      <el-table-column label="维修人员" align="center" prop="maintenancePeople" />
      <el-table-column label="故障现象" align="center" prop="faultPhenomenon" />
      <el-table-column label="维修分析" align="center" prop="maintenanceAnalysis" />
      <el-table-column label="维修内容" align="center" prop="maintenanceContent" />
      <el-table-column label="报修时间" align="center" prop="reportedTime" width="180">
      </el-table-column>
      <el-table-column label="处理时间" align="center" prop="resolutionTime" width="180">
      </el-table-column>
      <el-table-column label="故障时长" align="center" prop="faultDuration" />
      <el-table-column label="维修费用" align="center" prop="maintenanceCast" />
      <el-table-column label="是否停机" align="center" prop="ifDown" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160">
        <template #default="scope">
          <!-- <el-button link type="primary" icon="Edit" v-if="scope.row.applyBy" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:table:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" v-if="scope.row.applyBy" @click="handleDelete(scope.row)"
            v-hasPermi="['system:table:remove']">删除</el-button> -->
          <el-button link type="primary" icon="Position" v-if="scope.row.reportedTime"
            @click="handleToAnalysis(scope.row, 'fault', 'analysis')"
            v-hasPermi="['system:table:edit']">跳转故障分析</el-button>
        </template>
      </el-table-column>
    </el-table>

    <fault-chart ref="chartRef" v-if="showTree" :chartData="chartData"></fault-chart>
    <!-- <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="handleChangePage(queryParams.pageNum)" /> -->

    <!-- 添加或修改设备故障记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
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
    </el-dialog>

  </div>
</template>


<script setup name="FaultTree">
import { listTable, getTable, delTable, addTable, updateTable, uploadFile, getTreeList, numberList } from "@/api/device/maintenanceTable/table";
import { ElMessage } from 'element-plus';
import faultChart from "./faultChart.vue"

const { proxy } = getCurrentInstance();

const total = ref(0);

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
// const daterangeReportedTime = ref([]);
// const daterangeResolutionTime = ref([]);

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
    daterangeReportedTime: [
      { required: true, message: '请选择报修时间范围', trigger: 'change' }
    ],
    daterangeResolutionTime: [
      { required: true, message: '请选择处理时间范围', trigger: 'change' }
    ]
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
  loading.value = true;
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
    chartData.data = pagesList.value
    loading.value = false;
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
  tableList.value = pages[0]

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

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.maintenanceTableId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _maintenanceTableId = row.maintenanceTableId || ids.value
  getTable(_maintenanceTableId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["tableRef"].validate(valid => {
    if (valid) {
      if (form.value.maintenanceTableId != null) {
        updateTable(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTable(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
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

/** 台账跳转按钮操作 */
// function handleToDetails(row) {
//   router.push({ path: "/file/details", query: { deviceNum: row.deviceNum, name: "若依" } });
// }
/** SOP跳转按钮操作 */
// function handleToSop(row) {
//   router.push({ path: "/file/sop", query: { deviceNum: row.deviceNum, name: "若依" } });
// }


// 导入excel，检查文件类型
function checkFile() {
  const file = proxy.$refs["fileInput"].files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    $.modal.msg("只能上传 Excel 文件！", error);
    // this.$refs.fileInput.value = ""; // 清空文件选择框
  }
}
//导入excel，取消按钮绑定取消所选的xlsx
// function resetFileInput() {
//   this.$refs.fileInput.value = "";
// }
/** 导入按钮 */
function fileSend() {
  const fileInput = proxy.$refs["fileInput"];
  // console.log(proxy.$refs["fileInput"])
  // if (!fileInput || !fileInput.files.length) {
  //   ElMessage.warning('请选择要上传的文件');
  //   return;
  // }

  const file = fileInput.files[0];
  console.log('Selected file:', file);
  // console.log(file)
  const formData = new FormData();
  formData.append("excelFile", file);
  formData.append("yearAndMonth", '2024-12-12');
  console.log({ formData })
  // 使用如下方法打印出 formData 的内容
  for (let pair of formData.entries()) {
    console.log(pair[0] + ': ' + pair[1]);
  }

  // isLoading.value = true;
  uploadFile(formData, `/fault/maintenance/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      // this.getList();
      // this.showDialog = false;
      // this.isLoading = false;
    })
    .catch(error => {
      // 处理上传失败的情况
      ElMessage({
        message: `上传失败:${error}`,
        type: 'error',
      });
      // this.$message.error("上传失败，请重试");
      // this.isLoading = false;
    });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/table/export', {
    ...queryParams.value
  }, `table_${new Date().getTime()}.xlsx`)
}

</script>
