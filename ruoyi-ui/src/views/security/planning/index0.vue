<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部门" prop="departmentName">
        <el-input
          v-model="queryParams.departmentName"
          placeholder="请输入部门"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标项" prop="goalItem">
        <el-input
          v-model="queryParams.goalItem"
          placeholder="请输入目标项"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标值" prop="goalValue">
        <el-input
          v-model="queryParams.goalValue"
          placeholder="请输入目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核结果" prop="evaluationResult">
        <el-input
          v-model="queryParams.evaluationResult"
          placeholder="请输入考核结果"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="汇编人" prop="compiler">
        <el-input
          v-model="queryParams.compiler"
          placeholder="请输入汇编人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="审核人" prop="auditor">
        <el-input
          v-model="queryParams.auditor"
          placeholder="请输入审核人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="批准人" prop="approver">
        <el-input
          v-model="queryParams.approver"
          placeholder="请输入批准人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="日期" prop="date">
        <el-date-picker clearable
          v-model="queryParams.date"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择日期">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in security_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
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
          v-hasPermi="['security:HealthAndSafetyGoals:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:HealthAndSafetyGoals:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:HealthAndSafetyGoals:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:HealthAndSafetyGoals:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:HealthAndSafetyGoals:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:HealthAndSafetyGoals:import']"
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
      :data="HealthAndSafetyGoalsList"
      @selection-change="handleSelectionChange"
      :span-method="objectSpanMethod"
      class="health-safety-goals-table"
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
      <el-table-column label="部门" align="center" prop="departmentName" min-width="120" show-overflow-tooltip />
      <el-table-column label="目标项" align="center" prop="goalItem" min-width="150" show-overflow-tooltip />
      <el-table-column label="目标值" align="center" prop="goalValue" min-width="120" show-overflow-tooltip />
      <el-table-column label="考核结果" align="center" prop="evaluationResult" min-width="120" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="remarks" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:HealthAndSafetyGoals:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:HealthAndSafetyGoals:remove']"
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

    <!-- 添加或修改年度环境职业健康安全目标、指标及工作分解对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="HealthAndSafetyGoalsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门" prop="departmentName">
          <el-input v-model="form.departmentName" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="目标项" prop="goalItem">
          <el-input v-model="form.goalItem" placeholder="请输入目标项" />
        </el-form-item>
        <el-form-item label="目标值" prop="goalValue">
          <el-input v-model="form.goalValue" placeholder="请输入目标值" />
        </el-form-item>
        <el-form-item label="考核结果" prop="evaluationResult">
          <el-input v-model="form.evaluationResult" placeholder="请输入考核结果" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="汇编人" prop="compiler">
          <el-input v-model="form.compiler" placeholder="请输入汇编人" />
        </el-form-item>
        <el-form-item label="审核人" prop="auditor">
          <el-input v-model="form.auditor" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="批准人" prop="approver">
          <el-input v-model="form.approver" placeholder="请输入批准人" />
        </el-form-item> -->
        <!-- <el-form-item label="日期" prop="date">
          <el-date-picker clearable
            v-model="form.date"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item> -->
        <!-- <el-form-item label="审核状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择审核状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog title="导入数据" v-model="uploadDialogVisible" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :on-exceed="handleExceed"
        :auto-upload="false"
        :show-file-list="true"
        :on-change="handleFileChange"
        :before-remove="handleBeforeRemove"
        drag>
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            仅允许导入Excel文件，且不超过5MB
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" :loading="isLoading" @click="uploadFile">确 定</el-button>
          <el-button @click="cancelUpload">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="HealthAndSafetyGoals">
import { listHealthAndSafetyGoals, getHealthAndSafetyGoals, delHealthAndSafetyGoals, addHealthAndSafetyGoals, updateHealthAndSafetyGoals, importHealthAndSafetyGoals } from "@/api/security/HealthAndSafetyGoals";
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { UploadFilled } from '@element-plus/icons-vue';
import { getToken } from '@/utils/auth';
import axios from 'axios';
import { useRoute } from 'vue-router';
import { watch, onMounted } from 'vue';

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');
const route = useRoute();

const HealthAndSafetyGoalsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const spanArr = ref([]);
const pos = ref({});
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const selectedFile = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    departmentName: null,
    goalItem: null,
    goalValue: null,
    evaluationResult: null,
    remarks: null,
    compiler: null,
    auditor: null,
    approver: null,
    date: null,
    status: null,
    relatedId: null
  },
  rules: {
    departmentName: [
      { required: true, message: "部门不能为空", trigger: "blur" }
    ],
    goalItem: [
      { required: true, message: "目标项不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);
// 返回上一级页面
function handleGoBack() {
  proxy.$router.go(-1);
}
/** 查询年度环境职业健康安全目标、指标及工作分解列表 */
function getList() {
  loading.value = true;
  listHealthAndSafetyGoals(queryParams.value).then(response => {
    HealthAndSafetyGoalsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // 初始化合并单元格数据
    getSpanArr(HealthAndSafetyGoalsList.value);
  });
}

// 计算合并单元格的方法
function getSpanArr(data) {
  spanArr.value = [];
  pos.value = {};
  
  // 初始化pos和spanArr
  for (let i = 0; i < data.length; i++) {
    // 处理部门列
    if (i === 0) {
      spanArr.value.push(1);
      pos.value[data[0].departmentName] = 0;
    } else {
      // 判断当前行与上一行的部门是否相同
      if (data[i].departmentName === data[i-1].departmentName) {
        spanArr.value[pos.value[data[i].departmentName]] += 1;
        spanArr.value.push(0);
      } else {
        spanArr.value.push(1);
        pos.value[data[i].departmentName] = i;
      }
    }
  }
}

// 合并单元格方法
function objectSpanMethod({ row, column, rowIndex, columnIndex }) {
  if (columnIndex === 2) { // 部门列
    const _row = spanArr.value[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  } else if (columnIndex === 5) { // 考核结果列
    const _row = spanArr.value[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
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
    serialNumber: null,
    departmentName: null,
    goalItem: null,
    goalValue: null,
    evaluationResult: null,
    remarks: null,
    compiler: null,
    auditor: null,
    approver: null,
    date: null,
    status: null,
    relatedId: null
  };
  proxy.resetForm("HealthAndSafetyGoalsRef");
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
  ids.value = selection.map(item => item.serialNumber);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加年度环境职业健康安全目标、指标及工作分解";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _serialNumber = row.serialNumber || ids.value
  getHealthAndSafetyGoals(_serialNumber).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改年度环境职业健康安全目标、指标及工作分解";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["HealthAndSafetyGoalsRef"].validate(valid => {
    if (valid) {
      if (form.value.serialNumber != null) {
        updateHealthAndSafetyGoals(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHealthAndSafetyGoals(form.value).then(response => {
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
  const _serialNumbers = row.serialNumber || ids.value;
  proxy.$modal.confirm('是否确认删除年度环境职业健康安全目标、指标及工作分解编号为"' + _serialNumbers + '"的数据项？').then(function() {
    return delHealthAndSafetyGoals(_serialNumbers);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/HealthAndSafetyGoals/export', {
    ...queryParams.value
  }, `HealthAndSafetyGoals_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  uploadDialogVisible.value = true;
  selectedFile.value = null;
}

/** 下载模板按钮操作 */
function handleDownloadTemplate() {
  console.log('开始下载模板...');
  try {
    // 使用相对路径，避免混合内容问题
    const downloadUrl = '/security/HealthAndSafetyGoals/importTemplate';
    console.log('下载模板URL:', downloadUrl);
    
    // 使用原生的下载方法
    proxy.download('security/HealthAndSafetyGoals/importTemplate', {}, `年度环境职业健康安全目标导入模板_${new Date().getTime()}.xlsx`, 'get');
    console.log('下载请求已发送');
  } catch (error) {
    console.error('下载模板出错:', error);
    proxy.$modal.msgError('下载模板出错：' + (error.message || '未知错误'));
  }
}

/** 上传文件超出数量限制 */
function handleExceed() {
  proxy.$modal.msgError("最多只能上传一个文件！");
}

/** 文件选择变更 */
function handleFileChange(file) {
  console.log('文件选择变更:', file);
  if (file) {
    const fileName = file.name;
    const fileExt = fileName.split(".").pop().toLowerCase();
    if (fileExt !== "xlsx" && fileExt !== "xlsm" && fileExt !== "xls") {
      proxy.$modal.msgError("只能上传 Excel 文件！");
      if (proxy.$refs.uploadRef) {
        proxy.$refs.uploadRef.clearFiles();
      }
      selectedFile.value = null;
      return false;
    }
    selectedFile.value = file;
    return true;
  }
  return false;
}

/** 文件移除前的处理 */
function handleBeforeRemove() {
  selectedFile.value = null;
  return true;
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  selectedFile.value = null;
  if (proxy.$refs.uploadRef) {
    proxy.$refs.uploadRef.clearFiles();
  }
}

/** excel文件上传 */
function uploadFile() {
  try {
    console.log('开始上传文件，selectedFile:', selectedFile.value);
    
    if (!selectedFile.value) {
      const uploadRef = proxy.$refs.uploadRef;
      console.log('uploadRef:', uploadRef);
      
      if (!uploadRef || !uploadRef.uploadFiles || uploadRef.uploadFiles.length === 0) {
        proxy.$modal.msgError("请选择文件");
        return;
      }
      
      selectedFile.value = uploadRef.uploadFiles[0];
    }
    
    if (!selectedFile.value || !selectedFile.value.raw) {
      proxy.$modal.msgError("文件无效");
      return;
    }
    
    isLoading.value = true;
    const file = selectedFile.value.raw;
    
    // 调试信息
    console.log('文件信息:', file.name, file.type, file.size);
    
    // 创建FormData并确保参数名正确
    const formData = new FormData();
    formData.append('excelFile', file);
    
    // 检查FormData内容
    for (let [key, value] of formData.entries()) {
      console.log('FormData内容:', key, value instanceof File ? value.name : value);
    }
    
    // 使用axios直接发送请求
    axios.post(import.meta.env.VITE_APP_BASE_API + '/security/HealthAndSafetyGoals/importData', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': 'Bearer ' + getToken()
      }
    }).then(response => {
      console.log('上传成功:', response);
      if (response.data.code === 200) {
        proxy.$modal.msgSuccess(response.data.msg || "导入成功");
        getList();
      } else {
        proxy.$modal.msgError(response.data.msg || "导入失败");
      }
      uploadDialogVisible.value = false;
      selectedFile.value = null;
    }).catch(error => {
      console.error('上传失败:', error);
      if (error.response && error.response.data && error.response.data.msg) {
        proxy.$modal.msgError(error.response.data.msg);
      } else {
        proxy.$modal.msgError(error.message || "导入失败");
      }
    }).finally(() => {
      isLoading.value = false;
      if (proxy.$refs.uploadRef) {
        proxy.$refs.uploadRef.clearFiles();
      }
    });
  } catch (error) {
    console.error('上传文件出错:', error);
    proxy.$modal.msgError("上传过程中发生错误");
    isLoading.value = false;
  }
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

// 监听路由参数变化
watch(() => route.query.relatedId, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    queryParams.value.relatedId = newVal;
    getList();
  }
}, { immediate: true });

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
.el-divider {
  margin: 16px 0;
}
</style>
