<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评价单元" prop="evaluationUnit">
        <el-input
          v-model="queryParams.evaluationUnit"
          placeholder="请输入评价单元"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位" prop="position">
        <el-input
          v-model="queryParams.position"
          placeholder="请输入岗位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测地点" prop="inspectionLocation">
        <el-input
          v-model="queryParams.inspectionLocation"
          placeholder="请输入检测地点"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="化学因素名称" prop="chemicalFactorName">
        <el-input
          v-model="queryParams.chemicalFactorName"
          placeholder="请输入化学因素名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物理因素名称" prop="physicalFactorName">
        <el-input
          v-model="queryParams.physicalFactorName"
          placeholder="请输入物理因素名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产性粉尘名称" prop="productionDustName">
        <el-input
          v-model="queryParams.productionDustName"
          placeholder="请输入生产性粉尘名称"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['security:hazardpointledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:hazardpointledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:hazardpointledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:hazardpointledger:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:hazardpointledger:import']"
        >导入</el-button>
      </el-col>
      <!-- 下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:hazardpointledger:list']"
        >下载模板</el-button>
      </el-col>
            <!-- 添加返回上一级按钮 -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Back"
          @click="handleGoBack"
        >返回</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="hazardpointledgerList"
      @selection-change="handleSelectionChange"

      class="hazard-point-ledger-table"
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
      <el-table-column label="评价单元" align="center" prop="evaluationUnit" min-width="120" show-overflow-tooltip />
      <el-table-column label="岗位" align="center" prop="position" min-width="100" show-overflow-tooltip />
      <el-table-column label="检测地点" align="center" prop="inspectionLocation" min-width="120" show-overflow-tooltip />
      <el-table-column label="化学因素名称" align="center" prop="chemicalFactorName" min-width="130" show-overflow-tooltip />
      <el-table-column label="物理因素名称" align="center" prop="physicalFactorName" min-width="130" show-overflow-tooltip />
      <el-table-column label="生产性粉尘名称" align="center" prop="productionDustName" min-width="140" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="remarks" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:hazardpointledger:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:hazardpointledger:remove']"
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

    <!-- 添加或修改有点害台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="hazardpointledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评价单元" prop="evaluationUnit">
          <el-input v-model="form.evaluationUnit" placeholder="请输入评价单元" />
        </el-form-item>
        <el-form-item label="岗位" prop="position">
          <el-input v-model="form.position" placeholder="请输入岗位" />
        </el-form-item>
        <el-form-item label="检测地点" prop="inspectionLocation">
          <el-input v-model="form.inspectionLocation" placeholder="请输入检测地点" />
        </el-form-item>
        <el-form-item label="化学因素名称" prop="chemicalFactorName">
          <el-input v-model="form.chemicalFactorName" placeholder="请输入化学因素名称" />
        </el-form-item>
        <el-form-item label="物理因素名称" prop="physicalFactorName">
          <el-input v-model="form.physicalFactorName" placeholder="请输入物理因素名称" />
        </el-form-item>
        <el-form-item label="生产性粉尘名称" prop="productionDustName">
          <el-input v-model="form.productionDustName" placeholder="请输入生产性粉尘名称" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入xls、xlsx格式文件。请先下载模板按照格式填写数据。</span>
            <br>
            <span style="color: #909399; font-size: 12px;">注意：导入时会直接添加所有数据，包括重复数据。</span>
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

<script setup name="Hazardpointledger">
import { listHazardpointledger, getHazardpointledger, delHazardpointledger, addHazardpointledger, updateHazardpointledger, downloadTemplate } from "@/api/security/hazardpointledger";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';
import { useRoute } from 'vue-router';

const { proxy } = getCurrentInstance();
const route = useRoute();

const hazardpointledgerList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入参数
const upload = reactive({
  // 是否显示弹出层（导入）
  open: false,
  // 弹出层标题（导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/hazardpointledger/importData"
});



const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    evaluationUnit: null,
    position: null,
    inspectionLocation: null,
    chemicalFactorName: null,
    physicalFactorName: null,
    productionDustName: null,
  },
  rules: {
    evaluationUnit: [
      { required: true, message: "评价单元不能为空", trigger: "blur" }
    ],
    position: [
      { required: true, message: "岗位不能为空", trigger: "blur" }
    ],
    inspectionLocation: [
      { required: true, message: "检测地点不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询有点害台账列表 */
function getList() {
  loading.value = true;
  listHazardpointledger(queryParams.value).then(response => {
    hazardpointledgerList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
// 返回上一级页面
function handleGoBack() {
  proxy.$router.go(-1);
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
    evaluationUnit: null,
    position: null,
    inspectionLocation: null,
    chemicalFactorName: null,
    physicalFactorName: null,
    productionDustName: null,
    remarks: null
  };
  proxy.resetForm("hazardpointledgerRef");
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
  title.value = "添加有点害台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHazardpointledger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改有点害台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["hazardpointledgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHazardpointledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHazardpointledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除有点害台账编号为"' + _ids + '"的数据项？').then(function() {
    return delHazardpointledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/hazardpointledger/export', {
    ...queryParams.value
  }, `hazardpointledger_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "有点害台账导入";
  upload.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/hazardpointledger/importTemplate', {}, `有点害台账导入模板_${new Date().getTime()}.xlsx`, 'post');
}

/** 文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

// 检查关联ID参数
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
</script>

<style scoped>
/* 搜索区域样式 */
.search-container {
  margin-bottom: 20px;
}

.search-card {
  border-radius: 8px;
  overflow: hidden;
}

.search-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.search-icon {
  color: #409eff;
  margin-right: 8px;
  font-size: 16px;
}

.search-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.search-tips {
  display: flex;
  gap: 8px;
}

.search-form {
  margin-top: 10px;
}

.search-buttons {
  text-align: center;
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ebeef5;
}

.search-btn {
  margin-right: 12px;
  padding: 10px 24px;
  font-weight: 500;
}

.reset-btn {
  margin-right: 12px;
  padding: 10px 24px;
}

.tips-btn {
  padding: 10px 20px;
}

/* 表格区域样式 */
.table-container {
  margin-top: 20px;
}

.table-card {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.table-title {
  display: flex;
  align-items: center;
  gap: 8px;
}

.title-icon {
  color: #409eff;
  font-size: 18px;
}

.table-title span {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.data-count {
  margin-left: 12px;
}

/* 美化表格样式 */
.beautiful-table {
  width: 100%;
}

.beautiful-table :deep(.el-table__header-wrapper) {
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
}

.beautiful-table :deep(.el-table__header th) {
  background: transparent !important;
  border-bottom: 2px solid #dee2e6;
}

.beautiful-table :deep(.el-table__row) {
  transition: all 0.3s ease;
}

.beautiful-table :deep(.el-table__row:hover) {
  background-color: #f8f9fa !important;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.beautiful-table :deep(.el-table__row--striped) {
  background-color: #fafbfc;
}

.beautiful-table :deep(.el-table__row--striped:hover) {
  background-color: #f8f9fa !important;
}

/* 单元格内容样式 */
.cell-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
}

.cell-icon {
  color: #6c757d;
  font-size: 14px;
}

.factor-content {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  flex-wrap: wrap;
}

.factor-icon {
  color: #6c757d;
  font-size: 14px;
}

.factor-tag {
  border-radius: 12px;
  font-weight: 500;
}

.empty-text {
  color: #adb5bd;
  font-style: italic;
}

.remarks-content {
  max-width: 150px;
  word-break: break-word;
}

.remarks-text {
  color: #495057;
  line-height: 1.4;
}

/* 操作按钮样式 */
.operation-column {
  background-color: #f8f9fa !important;
}

.operation-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
  align-items: center;
}

.op-btn {
  padding: 6px 12px;
  border-radius: 6px;
  font-size: 12px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.op-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .search-form :deep(.el-row) {
    margin-bottom: 10px;
  }

  .search-form :deep(.el-col) {
    margin-bottom: 10px;
  }
}

@media (max-width: 768px) {
  .search-form :deep(.el-col) {
    width: 100% !important;
    flex: 0 0 100% !important;
    max-width: 100% !important;
  }

  .operation-buttons {
    flex-direction: column;
    gap: 4px;
  }

  .op-btn {
    width: 100%;
    margin: 0;
  }
}

/* 加载状态优化 */
.beautiful-table :deep(.el-loading-mask) {
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(2px);
}

/* 合并单元格样式优化 */
.beautiful-table :deep(.el-table__cell) {
  border-right: 1px solid #ebeef5;
}

.beautiful-table :deep(.el-table__cell:last-child) {
  border-right: none;
}

/* 选择框样式 */
.beautiful-table :deep(.el-checkbox) {
  transform: scale(1.1);
}

.beautiful-table :deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
  background-color: #409eff;
  border-color: #409eff;
}

/* 标签样式优化 */
.beautiful-table :deep(.el-tag) {
  border: none;
  font-weight: 500;
  letter-spacing: 0.5px;
}

.beautiful-table :deep(.el-tag--info) {
  background-color: #e3f2fd;
  color: #1976d2;
}

.beautiful-table :deep(.el-tag--warning) {
  background-color: #fff3e0;
  color: #f57c00;
}

.beautiful-table :deep(.el-tag--success) {
  background-color: #e8f5e8;
  color: #2e7d32;
}
</style>
