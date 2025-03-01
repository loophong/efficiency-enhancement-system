<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="线号" prop="lineNumber">
        <el-input v-model="queryParams.lineNumber" placeholder="请输入线号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="整机ID" prop="machineId">
        <el-input v-model="queryParams.machineId" placeholder="请输入整机ID" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="计划日期" prop="plannedDate">
        <el-date-picker clearable v-model="queryParams.plannedDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择计划日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="型号" prop="model">
        <el-input v-model="queryParams.model" placeholder="请输入型号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="门架型号" prop="gantryModel">
        <el-input v-model="queryParams.gantryModel" placeholder="请输入门架型号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="吨位" prop="tonnage">
        <el-input v-model="queryParams.tonnage" placeholder="请输入吨位" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="生产单号" prop="productionOrderNumber">
        <el-input v-model="queryParams.productionOrderNumber" placeholder="请输入生产单号" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="OK数量" prop="okQuantity">
        <el-input v-model="queryParams.okQuantity" placeholder="请输入OK数量" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="发送标记" prop="sendFlag">
        <el-input v-model="queryParams.sendFlag" placeholder="请输入发送标记" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="生成时间" prop="creationTime">
        <el-date-picker clearable v-model="queryParams.creationTime" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择生成时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="自动重新报工时间" prop="autoReworkTime">
        <el-date-picker clearable v-model="queryParams.autoReworkTime" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择自动重新报工时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="报工人" prop="operator">
        <el-input v-model="queryParams.operator" placeholder="请输入报工人" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="SAP返回标记" prop="sapReturnFlag">
        <el-input v-model="queryParams.sapReturnFlag" placeholder="请输入SAP返回标记" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="SAP报工返回信息" prop="sapWorkorderReturnInfo">
        <el-input v-model="queryParams.sapWorkorderReturnInfo" placeholder="请输入SAP报工返回信息" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="重发收货时间" prop="resendReceiptTime">
        <el-date-picker clearable v-model="queryParams.resendReceiptTime" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择重发收货时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="SAP收货返回消息" prop="sapReceiptReturnMessage">
        <el-input v-model="queryParams.sapReceiptReturnMessage" placeholder="请输入SAP收货返回消息" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:scanRecords:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:scanRecords:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:scanRecords:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
                   v-hasPermi="['production:scanRecords:export']">导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload"
                   v-hasPermi="['production:scanRecords:import']">导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scanRecordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id"/>
      <el-table-column label="线号" align="center" prop="lineNumber"/>
      <el-table-column label="整机ID" align="center" prop="machineId"/>
      <el-table-column label="计划日期" align="center" prop="plannedDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.plannedDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="型号" align="center" prop="model"/>
      <el-table-column label="门架型号" align="center" prop="gantryModel"/>
      <el-table-column label="吨位" align="center" prop="tonnage"/>
      <el-table-column label="生产单号" align="center" prop="productionOrderNumber"/>
      <el-table-column label="OK数量" align="center" prop="okQuantity"/>
      <el-table-column label="发送标记" align="center" prop="sendFlag"/>
      <el-table-column label="生成时间" align="center" prop="creationTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.creationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="自动重新报工时间" align="center" prop="autoReworkTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.autoReworkTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报工人" align="center" prop="operator"/>
      <el-table-column label="SAP返回标记" align="center" prop="sapReturnFlag"/>
      <el-table-column label="SAP报工返回信息" align="center" prop="sapWorkorderReturnInfo"/>
      <el-table-column label="重发收货时间" align="center" prop="resendReceiptTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.resendReceiptTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="SAP收货返回消息" align="center" prop="sapReceiptReturnMessage"/>
      <el-table-column label="SAP收货状态" align="center" prop="sapReceiptStatus"/>
      <el-table-column label="SAP收货返回状态" align="center" prop="sapReceiptReturnStatus"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:scanRecords:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:scanRecords:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改整机扫码记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="scanRecordsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="线号" prop="lineNumber">
          <el-input v-model="form.lineNumber" placeholder="请输入线号"/>
        </el-form-item>
        <el-form-item label="整机ID" prop="machineId">
          <el-input v-model="form.machineId" placeholder="请输入整机ID"/>
        </el-form-item>
        <el-form-item label="计划日期" prop="plannedDate">
          <el-date-picker clearable v-model="form.plannedDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择计划日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="型号" prop="model">
          <el-input v-model="form.model" placeholder="请输入型号"/>
        </el-form-item>
        <el-form-item label="门架型号" prop="gantryModel">
          <el-input v-model="form.gantryModel" placeholder="请输入门架型号"/>
        </el-form-item>
        <el-form-item label="吨位" prop="tonnage">
          <el-input v-model="form.tonnage" placeholder="请输入吨位"/>
        </el-form-item>
        <el-form-item label="生产单号" prop="productionOrderNumber">
          <el-input v-model="form.productionOrderNumber" placeholder="请输入生产单号"/>
        </el-form-item>
        <el-form-item label="OK数量" prop="okQuantity">
          <el-input v-model="form.okQuantity" placeholder="请输入OK数量"/>
        </el-form-item>
        <el-form-item label="发送标记" prop="sendFlag">
          <el-input v-model="form.sendFlag" placeholder="请输入发送标记"/>
        </el-form-item>
        <el-form-item label="生成时间" prop="creationTime">
          <el-date-picker clearable v-model="form.creationTime" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择生成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="自动重新报工时间" prop="autoReworkTime">
          <el-date-picker clearable v-model="form.autoReworkTime" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择自动重新报工时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报工人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入报工人"/>
        </el-form-item>
        <el-form-item label="SAP返回标记" prop="sapReturnFlag">
          <el-input v-model="form.sapReturnFlag" placeholder="请输入SAP返回标记"/>
        </el-form-item>
        <el-form-item label="SAP报工返回信息" prop="sapWorkorderReturnInfo">
          <el-input v-model="form.sapWorkorderReturnInfo" placeholder="请输入SAP报工返回信息"/>
        </el-form-item>
        <el-form-item label="重发收货时间" prop="resendReceiptTime">
          <el-date-picker clearable v-model="form.resendReceiptTime" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择重发收货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="SAP收货返回消息" prop="sapReceiptReturnMessage">
          <el-input v-model="form.sapReceiptReturnMessage" placeholder="请输入SAP收货返回消息"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 文件上传弹窗 -->
    <el-dialog title="整机扫码记录表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">整机扫码记录表</span>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <input type="file" ref="inputFile" @change="checkFile"/>
          <br>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="cancelUpload">取 消</el-button>
        <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
        <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script setup name="ScanRecords">
import {
  listScanRecords,
  getScanRecords,
  delScanRecords,
  addScanRecords,
  updateScanRecords,
  importFile
} from "@/api/production/scanRecords";

const {proxy} = getCurrentInstance();

const scanRecordsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    lineNumber: null,
    machineId: null,
    plannedDate: null,
    model: null,
    gantryModel: null,
    tonnage: null,
    productionOrderNumber: null,
    okQuantity: null,
    sendFlag: null,
    creationTime: null,
    autoReworkTime: null,
    operator: null,
    sapReturnFlag: null,
    sapWorkorderReturnInfo: null,
    resendReceiptTime: null,
    sapReceiptReturnMessage: null,
    sapReceiptStatus: null,
    sapReceiptReturnStatus: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询整机扫码记录列表 */
function getList() {
  loading.value = true;
  listScanRecords(queryParams.value).then(response => {
    scanRecordsList.value = response.rows;
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
    id: null,
    lineNumber: null,
    machineId: null,
    plannedDate: null,
    model: null,
    gantryModel: null,
    tonnage: null,
    productionOrderNumber: null,
    okQuantity: null,
    sendFlag: null,
    creationTime: null,
    autoReworkTime: null,
    operator: null,
    sapReturnFlag: null,
    sapWorkorderReturnInfo: null,
    resendReceiptTime: null,
    sapReceiptReturnMessage: null,
    sapReceiptStatus: null,
    sapReceiptReturnStatus: null
  };
  proxy.resetForm("scanRecordsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加整机扫码记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getScanRecords(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改整机扫码记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["scanRecordsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateScanRecords(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addScanRecords(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除整机扫码记录编号为"' + _ids + '"的数据项？').then(function () {
    return delScanRecords(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/scanRecords/export', {
    ...queryParams.value
  }, `scanRecords_${new Date().getTime()}.xlsx`)
}

getList();


// 上传文件
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);

/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
}

/** 表单重置 */
function resetUpload() {
  if (inputFile.value) {
    inputFile.value.value = "";
  }
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  resetUpload();
}

/** excel文件上传 */
function uploadFile() {
  if (inputFile.value && inputFile.value.files.length > 0) {
    isLoading.value = true;
    const file = inputFile.value.files[0];
    console.log(inputFile.value);
    console.log(file);
    const formData = new FormData();
    formData.append('excelFile', file);
    importFile(formData).then(() => {
      proxy.$modal.msgSuccess("导入成功");
      getList();
      uploadDialogVisible.value = false;
      isLoading.value = false;
    }).catch(() => {
      proxy.$modal.msgError("导入失败");
      isLoading.value = false;
    }).finally(() => {
      resetUpload();
    });
  } else {
    proxy.$modal.msgError("请选择文件");
  }
}

/** 检查文件是否为excel */
function checkFile() {
  const file = inputFile.value.files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    proxy.$modal.msgError("只能上传 Excel 文件！");
    resetUpload();
  }
}


</script>
