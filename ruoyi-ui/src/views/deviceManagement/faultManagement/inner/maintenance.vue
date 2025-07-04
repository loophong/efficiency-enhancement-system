<template>
  <div class="app-container">
    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="maintenanceTableId" /> -->
      <el-table-column label="设备编号" align="center" prop="deviceNum" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="工单状态" align="center" prop="workStatus" />
      <el-table-column label="问题类型" align="center" prop="issueType" />
      <el-table-column label="故障类型" align="center" prop="faultType" />
      <el-table-column label="申请人" align="center" prop="applyBy" />
      <el-table-column label="申请部门" align="center" prop="applyDepartment" />
      <el-table-column label="维修人员" align="center" prop="maintenancePeople" />
      <el-table-column label="故障现象" align="center" prop="faultPhenomenon" />
      <el-table-column label="故障现象文件" align="center" prop="faultFile">
        <template #default="scope">
          <el-button @click="handlePreview(scope.row)" v-if="scope.row.faultFile">预览</el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:table:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:table:remove']">删除</el-button>
          <el-button link type="primary" icon="Position" @click="handleToRoute(scope.row, 'file', 'details')"
            v-hasPermi="['system:table:edit']">台账</el-button>
          <el-button link type="primary" icon="Position" @click="handleToRoute(scope.row, 'file', 'sop')"
            v-hasPermi="['system:table:edit']">SOP文件</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <el-drawer :title="drawerTitle" v-model="openDrawer" size="40%" :direction="direction">
      <vue-office-docx :src="drawerUrl" style="height: 100vh;" @rendered="renderedHandler" @error="errorHandler" />
    </el-drawer>

  </div>
</template>


<script setup name="MaintenanceInner">
import { listTable, getTable, uploadFile } from "@/api/device/maintenanceTable/table";
import { ElMessage } from 'element-plus';
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";

import { ElMessageBox } from 'element-plus'

import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'

const { proxy } = getCurrentInstance();
const { device_fault_analysis } = proxy.useDict('device_fault_analysis');



const tableList = ref([]);
const exportList = ref([]);
const open = ref(false);
const openDrawer = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const drawerTitle = ref("");
const drawerUrl = ref("");
const router = useRouter();
const route = useRoute();
const showDialog = ref(false);
const daterangeReportedTime = ref([]);
const daterangeResolutionTime = ref([]);


function handlePreview(input) {
  const firstFaultFile = input.faultFile.split(',')[0].trim();
  const uploadDateMatch = firstFaultFile.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
  drawerTitle.value = `${input.deviceName}(${input.deviceNum})  上传日期：${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`
  drawerUrl.value = `${import.meta.env.VITE_APP_BASE_API}${firstFaultFile}`
  openDrawer.value = true
}



const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
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
    ifDown: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

const routerDeviceNum = route.query.deviceNum;
const handleRouteParams = () => {
  if (routerDeviceNum) {
    console.log('Received deviceNum:', routerDeviceNum);
    data.queryParams.deviceNum = routerDeviceNum;
    getList(); // 假设需要根据路由参数重新获取列表数据
  } else {
    getList();
    console.log('No specific route params received.');
    // 可以选择性地在这里添加其他逻辑
  }
};

// 使用 Vue 的生命周期钩子，在组件挂载完成后检查路由参数
onMounted(() => {
  handleRouteParams();
});

/** 查询设备故障记录列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeReportedTime && '' != daterangeReportedTime && Array.isArray(daterangeReportedTime.value)) {
    queryParams.value.params["beginReportedTime"] = daterangeReportedTime.value[0];
    queryParams.value.params["endReportedTime"] = daterangeReportedTime.value[1];
  }
  if (null != daterangeResolutionTime && '' != daterangeResolutionTime && Array.isArray(daterangeResolutionTime.value)) {
    queryParams.value.params["beginResolutionTime"] = daterangeResolutionTime.value[0];
    queryParams.value.params["endResolutionTime"] = daterangeResolutionTime.value[1];
  }
  listTable(queryParams.value).then(response => {
    tableList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
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
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeReportedTime.value = [];
  daterangeResolutionTime.value = [];
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
  title.value = "新增";
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





/** 跳转按钮操作 */
function handleToRoute(row, module, destination) {
  router.push({ path: `/deviceManagement/${module}Management/${destination}`, query: { deviceNum: row.deviceNum } });
}


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
  buttonLoading.value = true

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
      buttonLoading.value = false;
      showDialog.value = false;
      getList();
      // this.isLoading = false;
    })
    .catch(error => {
      // 处理上传失败的情况
      buttonLoading.value = false;
      showDialog.value = false;
      ElMessage({
        message: `上传失败:${error}`,
        type: 'error',
      });
      // this.$message.error("上传失败，请重试");
      // this.isLoading = false;
    });
}

//导出为excel
async function exportAll() {
  let exportParams = queryParams.value
  exportParams.pageSize = 100000
  exportParams.params = {};
  if (null != daterangeReportedTime && '' != daterangeReportedTime && Array.isArray(daterangeReportedTime.value)) {
    exportParams.params["beginReportedTime"] = daterangeReportedTime.value[0];
    exportParams.params["endReportedTime"] = daterangeReportedTime.value[1];
  }
  if (null != daterangeResolutionTime && '' != daterangeResolutionTime && Array.isArray(daterangeResolutionTime.value)) {
    exportParams.params["beginResolutionTime"] = daterangeResolutionTime.value[0];
    exportParams.params["endResolutionTime"] = daterangeResolutionTime.value[1];
  }
  await listTable(exportParams).then(response => {
    exportList.value = response.rows;
  });
  const promises = exportList.value.map((tableRow, index) => {
    return {
      序号: index + 1,
      设备编号: tableRow.deviceNum,
      设备名称: tableRow.deviceName,
      工单状态: tableRow.workStatus,
      问题类型: tableRow.issueType,
      故障类型: tableRow.faultType,
      申请人: tableRow.applyBy,
      申请部门: tableRow.applyDepartment,
      维修人员: tableRow.maintenancePeople,
      故障现象: tableRow.faultPhenomenon,
      维修分析: tableRow.maintenanceAnalysis,
      维修内容: tableRow.maintenanceContent,
      报修时间: tableRow.reportedTime,
      处理时间: tableRow.resolutionTime,
      故障时长: tableRow.faultDuration,
      维修费用: tableRow.maintenanceCast,
      是否停机: tableRow.ifDown,
    };
  });
  // console.log({ promises })
  Promise.all(promises)
    .then((data) => {
      const ws = XLSX.utils.json_to_sheet(data);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "项目列表");

      const wbout = XLSX.write(wb, { bookType: "xlsx", type: "array" });
      saveAs(
        new Blob([wbout], { type: "application/octet-stream" }),
        "故障记录表.xlsx"
      );
      // // 提交数据到Vuex Store
      // this.updateExportedData(data);
    })
    // .finally(() => {
    //   loadingInstance.close();
    // })
    .catch((error) => {
      console.error("导出失败:", error);
      // loadingInstance.close();
    });
}
/** 导出按钮操作 */
function handleExport() {
  proxy.download('system/table/export', {
    ...queryParams.value
  }, `table_${new Date().getTime()}.xlsx`)
}

// getList();
</script>

<style>
/* 基础样式 */
.preview-wrapper {
  min-height: 80vh;
}

/* 调整文档预览样式 */
.docx-wrapper {
  background: #fff !important;
  padding: 20px !important;
}

.docx {
  max-width: 100% !important;
}
</style>