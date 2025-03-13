<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="工厂" prop="factory">
        <el-input
          v-model="queryParams.factory"
          placeholder="请输入工厂"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料编码" prop="materialCode">
        <el-input
          v-model="queryParams.materialCode"
          placeholder="请输入物料编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料描述" prop="materialDescription">
        <el-input
          v-model="queryParams.materialDescription"
          placeholder="请输入物料描述"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采购组" prop="procurementGroup">
        <el-input
          v-model="queryParams.procurementGroup"
          placeholder="请输入采购组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货总批数" prop="totalReceipts">
        <el-input
          v-model="queryParams.totalReceipts"
          placeholder="请输入收货总批数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="合格接收批数" prop="qualifiedReceipts">
        <el-input
          v-model="queryParams.qualifiedReceipts"
          placeholder="请输入合格接收批数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="让步接收批数" prop="concessionReceipts">
        <el-input
          v-model="queryParams.concessionReceipts"
          placeholder="请输入让步接收批数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部分退货批数" prop="partialReturns">
        <el-input
          v-model="queryParams.partialReturns"
          placeholder="请输入部分退货批数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="全部退货批数" prop="totalReturns">
        <el-input
          v-model="queryParams.totalReturns"
          placeholder="请输入全部退货批数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批的合格率" prop="passRate">
        <el-input
          v-model="queryParams.passRate"
          placeholder="请输入批的合格率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="company">
        <el-input
          v-model="queryParams.company"
          placeholder="请输入单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收货总数量" prop="totalQuantity">
        <el-input
          v-model="queryParams.totalQuantity"
          placeholder="请输入收货总数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一次合格数量" prop="onceQualified">
        <el-input
          v-model="queryParams.onceQualified"
          placeholder="请输入一次合格数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一次不合格数量" prop="onceNotQualified">
        <el-input
          v-model="queryParams.onceNotQualified"
          placeholder="请输入一次不合格数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常检验总数量" prop="exceptionInspections">
        <el-input
          v-model="queryParams.exceptionInspections"
          placeholder="请输入异常检验总数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常检验让步接收数量" prop="exceptionConcessions">
        <el-input
          v-model="queryParams.exceptionConcessions"
          placeholder="请输入异常检验让步接收数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="异常检验退货数量" prop="exceptionReturns">
        <el-input
          v-model="queryParams.exceptionReturns"
          placeholder="请输入异常检验退货数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数量合格率" prop="quantityPassRate">
        <el-input
          v-model="queryParams.quantityPassRate"
          placeholder="请输入数量合格率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不合格通知单数量" prop="nonconformanceNotices">
        <el-input
          v-model="queryParams.nonconformanceNotices"
          placeholder="请输入不合格通知单数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料不合格率" prop="materialUnqualifiedRate">
        <el-input
          v-model="queryParams.materialUnqualifiedRate"
          placeholder="请输入物料不合格率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传月份" prop="month">
        <el-date-picker clearable
          v-model="queryParams.month"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择上传月份">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['supplier:onetime:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:onetime:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:onetime:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:onetime:export']"
        >导出</el-button>
      </el-col>

      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:onetime:import']">导入
              </el-button>
            </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="onetimeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工厂" align="center" prop="factory" />
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="物料描述" align="center" prop="materialDescription" />
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="采购组" align="center" prop="procurementGroup" />
      <el-table-column label="收货总批数" align="center" prop="totalReceipts" />
      <el-table-column label="合格接收批数" align="center" prop="qualifiedReceipts" />
      <el-table-column label="让步接收批数" align="center" prop="concessionReceipts" />
      <el-table-column label="部分退货批数" align="center" prop="partialReturns" />
      <el-table-column label="全部退货批数" align="center" prop="totalReturns" />
      <el-table-column label="批的合格率" align="center" prop="passRate" />
      <el-table-column label="单位" align="center" prop="company" />
      <el-table-column label="收货总数量" align="center" prop="totalQuantity" />
      <el-table-column label="一次合格数量" align="center" prop="onceQualified" />
      <el-table-column label="一次不合格数量" align="center" prop="onceNotQualified" />
      <el-table-column label="异常检验总数量" align="center" prop="exceptionInspections" />
      <el-table-column label="异常检验让步接收数量" align="center" prop="exceptionConcessions" />
      <el-table-column label="异常检验退货数量" align="center" prop="exceptionReturns" />
      <el-table-column label="数量合格率" align="center" prop="quantityPassRate" />
      <el-table-column label="不合格通知单数量" align="center" prop="nonconformanceNotices" />
      <el-table-column label="物料不合格率" align="center" prop="materialUnqualifiedRate" />
      <el-table-column label="上传月份" align="center" prop="month" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.month, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:onetime:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:onetime:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改一次交检合格率对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="onetimeRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工厂" prop="factory">
          <el-input v-model="form.factory" placeholder="请输入工厂" />
        </el-form-item>
        <el-form-item label="物料编码" prop="materialCode">
          <el-input v-model="form.materialCode" placeholder="请输入物料编码" />
        </el-form-item>
        <el-form-item label="物料描述" prop="materialDescription">
          <el-input v-model="form.materialDescription" placeholder="请输入物料描述" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="采购组" prop="procurementGroup">
          <el-input v-model="form.procurementGroup" placeholder="请输入采购组" />
        </el-form-item>
        <el-form-item label="收货总批数" prop="totalReceipts">
          <el-input v-model="form.totalReceipts" placeholder="请输入收货总批数" />
        </el-form-item>
        <el-form-item label="合格接收批数" prop="qualifiedReceipts">
          <el-input v-model="form.qualifiedReceipts" placeholder="请输入合格接收批数" />
        </el-form-item>
        <el-form-item label="让步接收批数" prop="concessionReceipts">
          <el-input v-model="form.concessionReceipts" placeholder="请输入让步接收批数" />
        </el-form-item>
        <el-form-item label="部分退货批数" prop="partialReturns">
          <el-input v-model="form.partialReturns" placeholder="请输入部分退货批数" />
        </el-form-item>
        <el-form-item label="全部退货批数" prop="totalReturns">
          <el-input v-model="form.totalReturns" placeholder="请输入全部退货批数" />
        </el-form-item>
        <el-form-item label="批的合格率" prop="passRate">
          <el-input v-model="form.passRate" placeholder="请输入批的合格率" />
        </el-form-item>
        <el-form-item label="单位" prop="company">
          <el-input v-model="form.company" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="收货总数量" prop="totalQuantity">
          <el-input v-model="form.totalQuantity" placeholder="请输入收货总数量" />
        </el-form-item>
        <el-form-item label="一次合格数量" prop="onceQualified">
          <el-input v-model="form.onceQualified" placeholder="请输入一次合格数量" />
        </el-form-item>
        <el-form-item label="一次不合格数量" prop="onceNotQualified">
          <el-input v-model="form.onceNotQualified" placeholder="请输入一次不合格数量" />
        </el-form-item>
        <el-form-item label="异常检验总数量" prop="exceptionInspections">
          <el-input v-model="form.exceptionInspections" placeholder="请输入异常检验总数量" />
        </el-form-item>
        <el-form-item label="异常检验让步接收数量" prop="exceptionConcessions">
          <el-input v-model="form.exceptionConcessions" placeholder="请输入异常检验让步接收数量" />
        </el-form-item>
        <el-form-item label="异常检验退货数量" prop="exceptionReturns">
          <el-input v-model="form.exceptionReturns" placeholder="请输入异常检验退货数量" />
        </el-form-item>
        <el-form-item label="数量合格率" prop="quantityPassRate">
          <el-input v-model="form.quantityPassRate" placeholder="请输入数量合格率" />
        </el-form-item>
        <el-form-item label="不合格通知单数量" prop="nonconformanceNotices">
          <el-input v-model="form.nonconformanceNotices" placeholder="请输入不合格通知单数量" />
        </el-form-item>
        <el-form-item label="物料不合格率" prop="materialUnqualifiedRate">
          <el-input v-model="form.materialUnqualifiedRate" placeholder="请输入物料不合格率" />
        </el-form-item>
        <el-form-item label="上传月份" prop="month">
          <el-date-picker clearable
            v-model="form.month"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传月份">
          </el-date-picker>
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
    <el-dialog title="导入一次交检合格率表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">合格率表单</span>
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

<script setup name="Onetime">
import { listOnetime, getOnetime, delOnetime, addOnetime, updateOnetime,importFile  } from "@/api/supplier/onetime";

const { proxy } = getCurrentInstance();

const onetimeList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入参数
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    factory: null,
    materialCode: null,
    materialDescription: null,
    supplierName: null,
    procurementGroup: null,
    totalReceipts: null,
    qualifiedReceipts: null,
    concessionReceipts: null,
    partialReturns: null,
    totalReturns: null,
    passRate: null,
    company: null,
    totalQuantity: null,
    onceQualified: null,
    onceNotQualified: null,
    exceptionInspections: null,
    exceptionConcessions: null,
    exceptionReturns: null,
    quantityPassRate: null,
    nonconformanceNotices: null,
    materialUnqualifiedRate: null,
    month: null
  },
  rules: {
    month: [
      { required: true, message: "上传月份不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询一次交检合格率列表 */
function getList() {
  loading.value = true;
  listOnetime(queryParams.value).then(response => {
    onetimeList.value = response.rows;
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
    factory: null,
    materialCode: null,
    materialDescription: null,
    supplierCode: null,
    supplierName: null,
    procurementGroup: null,
    totalReceipts: null,
    qualifiedReceipts: null,
    concessionReceipts: null,
    partialReturns: null,
    totalReturns: null,
    passRate: null,
    company: null,
    totalQuantity: null,
    onceQualified: null,
    onceNotQualified: null,
    exceptionInspections: null,
    exceptionConcessions: null,
    exceptionReturns: null,
    quantityPassRate: null,
    nonconformanceNotices: null,
    materialUnqualifiedRate: null,
    month: null
  };
  proxy.resetForm("onetimeRef");
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
  ids.value = selection.map(item => item.supplierCode);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加一次交检合格率";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _supplierCode = row.supplierCode || ids.value
  getOnetime(_supplierCode).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改一次交检合格率";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["onetimeRef"].validate(valid => {
    if (valid) {
      if (form.value.supplierCode != null) {
        updateOnetime(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOnetime(form.value).then(response => {
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
  const _supplierCodes = row.supplierCode || ids.value;
  proxy.$modal.confirm('是否确认删除一次交检合格率编号为"' + _supplierCodes + '"的数据项？').then(function() {
    return delOnetime(_supplierCodes);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/onetime/export', {
    ...queryParams.value
  }, `onetime_${new Date().getTime()}.xlsx`)
}

getList();


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
    // let date = XXXdate;
    const formData = new FormData();

    formData.append('excelFile', file);
    // formData.append('date', date);

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
  }else {
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
