<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="资本化日期" style="width: 308px">
        <el-date-picker v-model="daterangeFinancialDate" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="存货号" prop="inventoryNum">
        <el-input v-model="queryParams.inventoryNum" placeholder="请输入存货号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="固定资产名称" prop="fixedAssetName">
        <el-input v-model="queryParams.fixedAssetName" placeholder="请输入固定资产名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="班组" prop="detailsGroup">
        <el-input v-model="queryParams.detailsGroup" placeholder="请输入班组" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="重点设备标注" prop="ifKey">
        <el-input v-model="queryParams.ifKey" placeholder="请输入重点设备标注" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="保管使用单位" prop="storageUnit">
        <el-input v-model="queryParams.storageUnit" placeholder="请输入保管使用单位" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="责任成本中心" prop="costCenter">
        <el-input v-model="queryParams.costCenter" placeholder="请输入责任成本中心" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="使用年限" prop="usedYear">
        <el-input v-model="queryParams.usedYear" placeholder="请输入使用年限" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="资产原值" prop="assetOrigin">
        <el-input v-model="queryParams.assetOrigin" placeholder="请输入资产原值" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input v-model="queryParams.brand" placeholder="请输入品牌" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="吨位" prop="tonnage">
        <el-input v-model="queryParams.tonnage" placeholder="请输入吨位" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['file:details:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['file:details:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['file:details:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!--Excel 参数导入 -->
        <el-button type="primary" icon="UploadFilled" @click="showDialog = true" plain>导入
        </el-button>
        <el-dialog title="导入Excel文件" v-model="showDialog" width="30%">
          <el-form :model="form" ref="formRef" label-width="90px">
            <!-- 如果有表单内容，这里添加 -->
          </el-form>

          <div class="upload-area">
            <i class="el-icon-upload"></i>
            <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
          </div>
          <span class="dialog-footer">
            <br />
            <el-button @click="showDialog = false">取 消</el-button>
            <el-button type="primary" @click="fileSend" v-if="buttonLoading === false">确 定</el-button>
            <el-button type="primary" :loading="true" v-else>上传中</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['file:details:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailsList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="detailsId" /> -->
      <el-table-column label="资本化日期" align="center" prop="financialDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.financialDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="存货号" align="center" prop="inventoryNum" />
      <el-table-column label="固定资产名称" align="center" prop="fixedAssetName" />
      <el-table-column label="班组" align="center" prop="detailsGroup" />
      <el-table-column label="设备状态" align="center" prop="deviceStatus" />
      <el-table-column label="设备类别" align="center" prop="deviceType" />
      <el-table-column label="重点设备标注" align="center" prop="ifKey" />
      <el-table-column label="保管使用单位" align="center" prop="storageUnit" />
      <el-table-column label="责任成本中心" align="center" prop="costCenter" />
      <el-table-column label="使用年限" align="center" prop="usedYear" />
      <el-table-column label="资产原值" align="center" prop="assetOrigin" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="吨位" align="center" prop="tonnage" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['file:details:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['file:details:remove']">删除</el-button>
          <el-button link type="primary" icon="Position" @click="handleToRoute(scope.row, 'fault', 'maintenance')"
            v-hasPermi="['file:details:remove']">故障记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改设备台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="detailsRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资本化日期" prop="financialDate">
          <el-date-picker clearable v-model="form.financialDate" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择资本化日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="存货号" prop="inventoryNum">
          <el-input v-model="form.inventoryNum" placeholder="请输入存货号" />
        </el-form-item>
        <el-form-item label="固定资产名称" prop="fixedAssetName">
          <el-input v-model="form.fixedAssetName" placeholder="请输入固定资产名称" />
        </el-form-item>
        <el-form-item label="班组" prop="detailsGroup">
          <el-input v-model="form.detailsGroup" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="设备状态" prop="deviceStatus">
          <el-input v-model="form.deviceStatus" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="设备类别" prop="deviceType">
          <el-input v-model="form.deviceType" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="重点设备标注" prop="ifKey">
          <el-input v-model="form.ifKey" placeholder="请输入重点设备标注" />
        </el-form-item>
        <el-form-item label="保管使用单位" prop="storageUnit">
          <el-input v-model="form.storageUnit" placeholder="请输入保管使用单位" />
        </el-form-item>
        <el-form-item label="责任成本中心" prop="costCenter">
          <el-input v-model="form.costCenter" placeholder="请输入责任成本中心" />
        </el-form-item>
        <el-form-item label="使用年限" prop="usedYear">
          <el-input v-model="form.usedYear" placeholder="请输入使用年限" />
        </el-form-item>
        <el-form-item label="资产原值" prop="assetOrigin">
          <el-input v-model="form.assetOrigin" placeholder="请输入资产原值" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="吨位" prop="tonnage">
          <el-input v-model="form.tonnage" placeholder="请输入吨位" />
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

<script setup name="Details">
import { listDetails, getDetails, delDetails, addDetails, updateDetails, uploadFile } from "@/api/device/fileTable/details";
import { ElMessage } from 'element-plus'
const { proxy } = getCurrentInstance();

const detailsList = ref([]);
const open = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const router = useRouter();
const route = useRoute();
const showDialog = ref(false);
const daterangeFinancialDate = ref([]);

const routerDeviceNum = route.query.deviceNum;

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    financialDate: null,
    inventoryNum: null,
    fixedAssetName: null,
    detailsGroup: null,
    deviceStatus: null,
    deviceType: null,
    ifKey: null,
    storageUnit: null,
    costCenter: null,
    usedYear: null,
    assetOrigin: null,
    brand: null,
    tonnage: null,
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

const handleRouteParams = () => {
  if (routerDeviceNum) {
    console.log('Received deviceNum:', routerDeviceNum);
    data.queryParams.inventoryNum = routerDeviceNum;
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

/** 查询设备台账列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeFinancialDate && '' != daterangeFinancialDate) {
    queryParams.value.params["beginFinancialDate"] = daterangeFinancialDate.value[0];
    queryParams.value.params["endFinancialDate"] = daterangeFinancialDate.value[1];
  }
  listDetails(queryParams.value).then(response => {
    detailsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

/** 跳转按钮操作 */
function handleToRoute(row, module, destination) {
  router.push({ path: `/deviceManagement/${module}Management/${destination}`, query: { deviceNum: row.inventoryNum } });
}

// 表单重置
function reset() {
  form.value = {
    detailsId: null,
    financialDate: null,
    inventoryNum: null,
    fixedAssetName: null,
    detailsGroup: null,
    deviceStatus: null,
    deviceType: null,
    ifKey: null,
    storageUnit: null,
    costCenter: null,
    usedYear: null,
    assetOrigin: null,
    brand: null,
    tonnage: null
  };
  proxy.resetForm("detailsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeFinancialDate.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.detailsId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _detailsId = row.detailsId || ids.value
  getDetails(_detailsId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["detailsRef"].validate(valid => {
    if (valid) {
      if (form.value.detailsId != null) {
        updateDetails(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDetails(form.value).then(response => {
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
  const _detailsIds = row.detailsId || ids.value;
  proxy.$modal.confirm('是否确认删除设备台账编号为"' + _detailsIds + '"的数据项？').then(function () {
    return delDetails(_detailsIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

// 导入excel，检查文件类型
function checkFile() {
  const file = proxy.$refs["fileInput"].files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    this.$message.error("只能上传 Excel 文件！");
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
  buttonLoading.value = true;
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
  uploadFile(formData, `/file/details/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      showDialog.value = false
      getList();
      buttonLoading.value = false;
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
  proxy.download('file/details/export', {
    ...queryParams.value
  }, `details_${new Date().getTime()}.xlsx`)
}

// getList();
</script>
