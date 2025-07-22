<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
      class="search-form"
    >
      <el-form-item label="设施类别" prop="facilityCategory">
        <el-input
          v-model="queryParams.facilityCategory"
          placeholder="请输入设施类别"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="安全设施名称" prop="safetyFacilityName">
        <el-input
          v-model="queryParams.safetyFacilityName"
          placeholder="请输入安全设施名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入数量"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校验周期" prop="inspectionCycle">
        <el-input
          v-model="queryParams.inspectionCycle"
          placeholder="请输入校验周期"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下次校验时间" prop="nextInspectionDate">
        <el-date-picker
          v-model="queryParams.nextInspectionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择下次校验时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="责任单位" prop="responsibleUnit">
        <el-input
          v-model="queryParams.responsibleUnit"
          placeholder="请输入责任单位"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入位置"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="personInCharge">
        <el-input
          v-model="queryParams.personInCharge"
          placeholder="请输入负责人"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现状" prop="currentStatus">
        <el-input
          v-model="queryParams.currentStatus"
          placeholder="请输入现状"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="Search"
          @click="handleQuery"
          style="margin-right: 10px"
        >搜索</el-button>
        <el-button
          icon="Refresh"
          @click="resetQuery"
        >重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['security:SafetyFacilityLedger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:SafetyFacilityLedger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:SafetyFacilityLedger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:SafetyFacilityLedger:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:SafetyFacilityLedger:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="SafetyFacilityLedgerList"
      @selection-change="handleSelectionChange"
      :span-method="arraySpanMethod"
      class="safety-facility-ledger-table"
      border
      stripe
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#303133',
        fontWeight: 'bold',
        textAlign: 'center',
        fontSize: '14px'
      }"
      :cell-style="{
        textAlign: 'center',
        padding: '12px 8px',
        fontSize: '13px'
      }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="设施类别" align="center" prop="facilityCategory" min-width="120" show-overflow-tooltip />
      <el-table-column label="安全设施名称" align="center" prop="safetyFacilityName" min-width="150" show-overflow-tooltip />
      <el-table-column label="数量" align="center" prop="quantity" min-width="80" show-overflow-tooltip />
      <el-table-column label="校验周期" align="center" prop="inspectionCycle" min-width="100" show-overflow-tooltip />
      <el-table-column label="下次校验时间" align="center" prop="nextInspectionDate" width="130" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.nextInspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="责任单位" align="center" prop="responsibleUnit" min-width="120" show-overflow-tooltip />
      <el-table-column label="位置" align="center" prop="location" min-width="100" show-overflow-tooltip />
      <el-table-column label="负责人" align="center" prop="personInCharge" min-width="100" show-overflow-tooltip />
      <el-table-column label="现状" align="center" prop="currentStatus" min-width="100" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:SafetyFacilityLedger:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:SafetyFacilityLedger:remove']"
            size="small"
          >删除</el-button>
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

    <!-- 添加或修改安全防护设备设施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="SafetyFacilityLedgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设施类别" prop="facilityCategory">
          <el-input v-model="form.facilityCategory" placeholder="请输入设施类别" />
        </el-form-item>
        <el-form-item label="安全设施名称" prop="safetyFacilityName">
          <el-input v-model="form.safetyFacilityName" placeholder="请输入安全设施名称" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="校验周期" prop="inspectionCycle">
          <el-input v-model="form.inspectionCycle" placeholder="请输入校验周期" />
        </el-form-item>
        <el-form-item label="下次校验时间" prop="nextInspectionDate">
          <el-date-picker clearable
            v-model="form.nextInspectionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择下次校验时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="责任单位" prop="responsibleUnit">
          <el-input v-model="form.responsibleUnit" placeholder="请输入责任单位" />
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入位置" />
        </el-form-item>
        <el-form-item label="负责人" prop="personInCharge">
          <el-input v-model="form.personInCharge" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="现状" prop="currentStatus">
          <el-input v-model="form.currentStatus" placeholder="请输入现状" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 安全防护设备设施导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=false'"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入xls、xlsx格式文件。支持合并单元格。</span>
            <br/>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="SafetyFacilityLedger">
import { listSafetyFacilityLedger, listSafetyFacilityLedgerByRelatedId, getSafetyFacilityLedger, delSafetyFacilityLedger, addSafetyFacilityLedger, updateSafetyFacilityLedger } from "@/api/security/SafetyFacilityLedger";
import { ref, reactive, toRefs, getCurrentInstance, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const route = useRoute();

const SafetyFacilityLedgerList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 合并单元格相关
const spanArr = ref([]);
const pos = ref(0);

// 导入相关
const upload = reactive({
  open: false,
  title: "",
  isUploading: false,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/security/SafetyFacilityLedger/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    facilityCategory: null,
    safetyFacilityName: null,
    quantity: null,
    inspectionCycle: null,
    nextInspectionDate: null,
    responsibleUnit: null,
    location: null,
    personInCharge: null,
    currentStatus: null,
    relatedId: null
  },
  rules: {
    facilityCategory: [
      { required: true, message: "设施类别不能为空", trigger: "blur" }
    ],
    safetyFacilityName: [
      { required: true, message: "安全设施名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全防护设备设施列表 */
function getList() {
  loading.value = true;

  // 如果有关联ID，使用关联ID查询
  if (queryParams.value.relatedId) {
    listSafetyFacilityLedgerByRelatedId(queryParams.value.relatedId, route.query.sourceModule).then(response => {
      SafetyFacilityLedgerList.value = response.rows;
      total.value = response.total;
      loading.value = false;
      // 处理合并单元格
      getSpanArr(SafetyFacilityLedgerList.value);
    });
  } else {
    // 否则使用普通查询
    listSafetyFacilityLedger(queryParams.value).then(response => {
      SafetyFacilityLedgerList.value = response.rows;
      total.value = response.total;
      loading.value = false;
      // 处理合并单元格
      getSpanArr(SafetyFacilityLedgerList.value);
    });
  }
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
    facilityCategory: null,
    safetyFacilityName: null,
    quantity: null,
    inspectionCycle: null,
    nextInspectionDate: null,
    responsibleUnit: null,
    location: null,
    personInCharge: null,
    currentStatus: null
  };
  proxy.resetForm("SafetyFacilityLedgerRef");
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
  title.value = "添加安全防护设备设施";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSafetyFacilityLedger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全防护设备设施";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["SafetyFacilityLedgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSafetyFacilityLedger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSafetyFacilityLedger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全防护设备设施编号为"' + _ids + '"的数据项？').then(function() {
    return delSafetyFacilityLedger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/SafetyFacilityLedger/export', {
    ...queryParams.value
  }, `SafetyFacilityLedger_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "安全防护设备设施导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/SafetyFacilityLedger/importTemplate', {}, `安全防护设备设施导入模板_${new Date().getTime()}.xlsx`);
}

/** 文件上传中处理 */
function handleFileUploadProgress() {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

/** 处理合并单元格数据 */
function getSpanArr(data) {
  spanArr.value = [];
  pos.value = 0;

  if (!data || data.length === 0) {
    return;
  }

  console.log("开始处理合并单元格数据，数据长度:", data.length);

  for (let i = 0; i < data.length; i++) {
    if (i === 0) {
      spanArr.value.push(1);
      pos.value = 0;
      console.log(`第${i}行: 初始行，spanArr[${i}] = 1`);
    } else {
      // 判断当前元素与上一个元素的设施类别是否相同
      const currentCategory = data[i].facilityCategory || '';
      const prevCategory = data[i - 1].facilityCategory || '';

      if (currentCategory === prevCategory && currentCategory !== '') {
        spanArr.value[pos.value] += 1;
        spanArr.value.push(0);
        console.log(`第${i}行: 与上一行相同类别(${currentCategory})，spanArr[${pos.value}] += 1, spanArr[${i}] = 0`);
      } else {
        spanArr.value.push(1);
        pos.value = i;
        console.log(`第${i}行: 新类别(${currentCategory})，spanArr[${i}] = 1, pos = ${i}`);
      }
    }
  }

  console.log("合并单元格数组:", spanArr.value);
}

/** 合并单元格方法 */
function arraySpanMethod({ row, column, rowIndex, columnIndex }) {
  // 只对设施类别列进行合并 (设施类别是第3列，索引为2)
  if (columnIndex === 2) { // 设施类别列的索引：选择框(0) + 序号(1) + 设施类别(2)
    const _row = spanArr.value[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  }
  // 其他列不进行合并，返回默认值
}

/** 检查关联ID参数 */
function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
  }
}

onMounted(() => {
  // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
    getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
});

getList();
</script>

<style scoped>
.search-form {
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.search-form .el-form-item {
  margin-bottom: 16px;
  margin-right: 20px;
}

.search-form .el-form-item__label {
  color: #606266;
  font-weight: 500;
  font-size: 14px;
}

.search-form .el-input__inner,
.search-form .el-date-editor .el-input__inner {
  border-radius: 6px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

.search-form .el-input__inner:focus,
.search-form .el-date-editor .el-input__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.search-form .el-button {
  border-radius: 6px;
  padding: 10px 20px;
  font-weight: 500;
  transition: all 0.3s;
}

.search-form .el-button--primary {
  background: linear-gradient(135deg, #409eff 0%, #66b3ff 100%);
  border: none;
}

.search-form .el-button--primary:hover {
  background: linear-gradient(135deg, #337ecc 0%, #5aa3e6 100%);
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
}

.search-form .el-button:not(.el-button--primary) {
  background: #ffffff;
  border: 1px solid #dcdfe6;
  color: #606266;
}

.search-form .el-button:not(.el-button--primary):hover {
  background: #f5f7fa;
  border-color: #c0c4cc;
  color: #409eff;
}

.safety-facility-ledger-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
