<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="培训项目1" prop="trainingProject1">
        <el-input
          v-model="queryParams.trainingProject1"
          placeholder="请输入培训项目1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训项目2" prop="trainingProject2">
        <el-input
          v-model="queryParams.trainingProject2"
          placeholder="请输入培训项目2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训对象" prop="trainees">
        <el-input
          v-model="queryParams.trainees"
          placeholder="请输入培训对象"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训方式" prop="trainingMethod">
        <el-input
          v-model="queryParams.trainingMethod"
          placeholder="请输入培训方式"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课时" prop="hours">
        <el-input
          v-model="queryParams.hours"
          placeholder="请输入课时"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训月份" prop="trainingMonth">
        <el-input
          v-model="queryParams.trainingMonth"
          placeholder="请输入培训月份"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核方式" prop="assessmentMethod">
        <el-input
          v-model="queryParams.assessmentMethod"
          placeholder="请输入考核方式"
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
          v-hasPermi="['security:trainingplan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:trainingplan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:trainingplan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:trainingplan:export']"
        >导出</el-button>
      </el-col>
      <!-- 添加导入按钮 -->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:trainingplan:import']"
        >导入</el-button>
      </el-col>
      <!-- 添加下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:trainingplan:export']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="trainingplanList" 
      @selection-change="handleSelectionChange" 
      :span-method="handleMergeCell"
      class="training-plan-table"
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
      <el-table-column label="培训项目1" align="center" prop="trainingProject1" min-width="120" />
      <el-table-column label="培训项目2" align="center" prop="trainingProject2" min-width="120" />
      <el-table-column label="培训对象" align="center" prop="trainees" min-width="120" />
      <el-table-column label="培训类别/内容" align="center" prop="trainingCategoryContent" min-width="200">
        <template #default="scope">
          <div class="training-content-cell">
            {{ scope.row.trainingCategoryContent }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="培训方式" align="center" prop="trainingMethod" min-width="120" />
      <el-table-column label="课时" align="center" prop="hours" width="80" />
      <el-table-column label="培训月份" align="center" prop="trainingMonth" width="100" />
      <el-table-column label="考核方式" align="center" prop="assessmentMethod" min-width="120" />
      <el-table-column label="备注" align="center" prop="remarks" min-width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="160" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:trainingplan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:trainingplan:remove']">删除</el-button>
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

    <!-- 添加或修改年度培训计划对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="trainingplanRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="培训项目1" prop="trainingProject1">
          <el-input v-model="form.trainingProject1" placeholder="请输入培训项目1" />
        </el-form-item>
        <el-form-item label="培训项目2" prop="trainingProject2">
          <el-input v-model="form.trainingProject2" placeholder="请输入培训项目2" />
        </el-form-item>
        <el-form-item label="培训对象" prop="trainees">
          <el-input v-model="form.trainees" placeholder="请输入培训对象" />
        </el-form-item>
        <el-form-item label="培训类别/内容">
          <editor v-model="form.trainingCategoryContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="培训方式" prop="trainingMethod">
          <el-input v-model="form.trainingMethod" placeholder="请输入培训方式" />
        </el-form-item>
        <el-form-item label="课时" prop="hours">
          <el-input v-model="form.hours" placeholder="请输入课时" />
        </el-form-item>
        <el-form-item label="培训月份" prop="trainingMonth">
          <el-input v-model="form.trainingMonth" placeholder="请输入培训月份" />
        </el-form-item>
        <el-form-item label="考核方式" prop="assessmentMethod">
          <el-input v-model="form.assessmentMethod" placeholder="请输入考核方式" />
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

    <!-- 添加导入对话框 -->
    <el-dialog
      :title="upload.title"
      v-model="upload.open"
      width="400px"
      append-to-body
      :before-close="cancelImport"
    >
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm" :loading="upload.isUploading">
            {{ upload.isUploading ? '导入中...' : '确 定' }}
          </el-button>
          <el-button @click="cancelImport">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Trainingplan">
import { listTrainingplan, getTrainingplan, delTrainingplan, addTrainingplan, updateTrainingplan } from "@/api/security/trainingplan";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance();
const route = useRoute();
const trainingplanList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 添加合并单元格相关数据
const mergeConfig = ref({});

// 添加上传相关的数据对象
const upload = reactive({
  open: false,
  title: '',
  isUploading: false,
  updateSupport: false,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/security/trainingplan/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    trainingProject1: null,
    trainingProject2: null,
    trainees: null,
    trainingCategoryContent: null,
    trainingMethod: null,
    hours: null,
    trainingMonth: null,
    assessmentMethod: null,
    remarks: null
  },
  rules: {
    trainees: [
      { required: true, message: "培训对象不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询年度培训计划列表 */
function getList() {
  loading.value = true;
  listTrainingplan(queryParams.value).then(response => {
    trainingplanList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    calculateMergeConfig();
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
    trainingProject1: null,
    trainingProject2: null,
    trainees: null,
    trainingCategoryContent: null,
    trainingMethod: null,
    hours: null,
    trainingMonth: null,
    assessmentMethod: null,
    remarks: null
  };
  proxy.resetForm("trainingplanRef");
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
  title.value = "添加年度培训计划";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getTrainingplan(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改年度培训计划";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["trainingplanRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTrainingplan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTrainingplan(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除年度培训计划编号为"' + _ids + '"的数据项？').then(function() {
    return delTrainingplan(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/trainingplan/export', {
    ...queryParams.value
  }, `trainingplan_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "导入年度培训计划";
  upload.open = true;
  upload.isUploading = false;
  upload.updateSupport = false;

  proxy.nextTick(() => {
    if (proxy.$refs.uploadRef) {
      proxy.$refs.uploadRef.clearFiles();
    }
  });
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/trainingplan/importTemplate', {}, `trainingplan_template_${new Date().getTime()}.xlsx`);
}

/** 文件上传中处理 */
function handleFileUploadProgress(event) {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.isUploading = false;

  try {
    if (response && response.code === 200) {
      // 关闭对话框
      upload.open = false;

      // 清空上传文件列表
      if (proxy.$refs.uploadRef) {
        proxy.$refs.uploadRef.clearFiles();
      }

      // 重置上传状态
      upload.updateSupport = false;

      // 显示成功消息
      proxy.$modal.msgSuccess(response.msg || "导入成功");

      // 刷新列表数据
      setTimeout(() => {
        getList();
      }, 100);
    } else {
      proxy.$modal.msgError(response?.msg || "导入失败");
    }
  } catch (error) {
    console.error("处理导入结果时出错:", error);
    proxy.$modal.msgError("导入处理失败");
  }
}

/** 文件上传失败处理 */
function handleFileError(error) {
  upload.isUploading = false;
  proxy.$modal.msgError("导入失败，请检查文件格式或网络连接");
  console.error("文件上传失败:", error);
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs.uploadRef.submit();
}

/** 取消导入 */
function cancelImport(done) {
  upload.open = false;
  upload.isUploading = false;
  upload.updateSupport = false;

  // 清空上传文件列表
  if (proxy.$refs.uploadRef) {
    proxy.$refs.uploadRef.clearFiles();
  }

  // 如果是通过 before-close 调用的，需要执行 done 回调
  if (typeof done === 'function') {
    done();
  }
}

/**
 * 计算表格合并配置
 */
function calculateMergeConfig() {
  const data = trainingplanList.value;
  if (!data || data.length === 0) return;
  
  console.log('计算表格合并配置...');
  
  // 创建合并配置对象，包含行合并和列合并
  const config = {
    rowMerge: {
      trainingProject1: {},
      trainingProject2: {},
      trainees: {},
      trainingCategoryContent: {},
      trainingMethod: {},
      hours: {},
      trainingMonth: {},
      assessmentMethod: {}
    },
    colMerge: {} // 存储每行的列合并信息
  };
  
  // 先处理行合并（垂直合并）
  processColumnMerge(data, config.rowMerge, 'trainingProject1');
  processColumnMergeWithDependency(data, config.rowMerge, 'trainingProject2', ['trainingProject1']);
  processColumnMergeWithDependency(data, config.rowMerge, 'trainees', ['trainingProject1', 'trainingProject2']);
  processColumnMergeWithDependency(data, config.rowMerge, 'trainingCategoryContent', ['trainingProject1', 'trainingProject2', 'trainees']);
  processColumnMergeWithDependency(data, config.rowMerge, 'trainingMethod', ['trainingProject1', 'trainingProject2', 'trainees', 'trainingCategoryContent']);
  processColumnMergeWithDependency(data, config.rowMerge, 'hours', ['trainingProject1', 'trainingProject2', 'trainees', 'trainingCategoryContent', 'trainingMethod']);
  processColumnMergeWithDependency(data, config.rowMerge, 'trainingMonth', ['trainingProject1', 'trainingProject2', 'trainees', 'trainingCategoryContent', 'trainingMethod', 'hours']);
  processColumnMergeWithDependency(data, config.rowMerge, 'assessmentMethod', ['trainingProject1', 'trainingProject2', 'trainees', 'trainingCategoryContent', 'trainingMethod', 'hours', 'trainingMonth']);
  
  // 再处理列合并（水平合并）
  processRowMerge(data, config.colMerge);
  
  mergeConfig.value = config;
  console.log('合并配置计算完成', config);
}

/**
 * 处理单列的合并
 */
function processColumnMerge(data, config, columnName) {
  let currentValue = null;
  let startIndex = 0;
  let span = 1;
  
  for (let i = 0; i < data.length; i++) {
    const value = data[i][columnName];
    
    if (i === 0) {
      currentValue = value;
    } else {
      if (value === currentValue) {
        span++;
      } else {
        if (span > 1) {
          config[columnName][startIndex] = span;
        }
        currentValue = value;
        startIndex = i;
        span = 1;
      }
    }
  }
  
  if (span > 1) {
    config[columnName][startIndex] = span;
  }
}

/**
 * 处理带依赖的列合并
 */
function processColumnMergeWithDependency(data, config, columnName, dependencies) {
  let groups = [];
  let currentGroup = null;
  
  for (let i = 0; i < data.length; i++) {
    const row = data[i];
    const dependencyKey = dependencies.map(dep => row[dep]).join('|');
    
    if (currentGroup === null || currentGroup.dependencyKey !== dependencyKey) {
      currentGroup = {
        dependencyKey,
        startIndex: i,
        values: [{ value: row[columnName], index: i }]
      };
      groups.push(currentGroup);
    } else {
      currentGroup.values.push({ value: row[columnName], index: i });
    }
  }
  
  for (const group of groups) {
    let currentValue = null;
    let startIndex = 0;
    let span = 1;
    
    for (let i = 0; i < group.values.length; i++) {
      const { value, index } = group.values[i];
      
      if (i === 0) {
        currentValue = value;
        startIndex = index;
      } else {
        if (value === currentValue) {
          span++;
        } else {
          if (span > 1) {
            config[columnName][startIndex] = span;
          }
          currentValue = value;
          startIndex = index;
          span = 1;
        }
      }
    }
    
    if (span > 1) {
      config[columnName][startIndex] = span;
    }
  }
}

/**
 * 处理行内相同值的列合并（水平合并）
 */
function processRowMerge(data, colMergeConfig) {
  const columns = ['trainingProject1', 'trainingProject2', 'trainees', 'trainingCategoryContent', 'trainingMethod', 'hours', 'trainingMonth', 'assessmentMethod'];
  
  for (let rowIndex = 0; rowIndex < data.length; rowIndex++) {
    const row = data[rowIndex];
    colMergeConfig[rowIndex] = {};
    
    // 检查每一行中相邻列是否有相同值
    for (let colIndex = 0; colIndex < columns.length - 1; colIndex++) {
      const currentCol = columns[colIndex];
      const currentValue = row[currentCol];
      
      // 如果当前列还没有被合并过
      if (!colMergeConfig[rowIndex][currentCol]) {
        let colspan = 1;
        
        // 检查后续相邻列是否有相同值
        for (let nextColIndex = colIndex + 1; nextColIndex < columns.length; nextColIndex++) {
          const nextCol = columns[nextColIndex];
          const nextValue = row[nextCol];
          
          if (currentValue && nextValue && currentValue === nextValue) {
            colspan++;
            // 标记后续列为被合并状态
            colMergeConfig[rowIndex][nextCol] = 0;
          } else {
            break; // 遇到不同值就停止
          }
        }
        
        // 如果有合并，记录合并信息
        if (colspan > 1) {
          colMergeConfig[rowIndex][currentCol] = colspan;
        }
      }
    }
  }
}

/**
 * 表格合并单元格方法
 */
function handleMergeCell({ row, column, rowIndex, columnIndex }) {
  if (!mergeConfig.value || Object.keys(mergeConfig.value).length === 0) {
    return { rowspan: 1, colspan: 1 };
  }
  
  const property = column.property;
  let rowspan = 1;
  let colspan = 1;
  
  // 处理行合并（垂直合并）
  if (property && mergeConfig.value.rowMerge[property]) {
    if (mergeConfig.value.rowMerge[property][rowIndex]) {
      rowspan = mergeConfig.value.rowMerge[property][rowIndex];
    } else {
      // 检查是否被前面的行合并
      for (let i = 0; i < rowIndex; i++) {
        const span = mergeConfig.value.rowMerge[property][i];
        if (span && i + span > rowIndex) {
          rowspan = 0;
          break;
        }
      }
    }
  }
  
  // 处理列合并（水平合并）
  if (mergeConfig.value.colMerge[rowIndex] && mergeConfig.value.colMerge[rowIndex][property] !== undefined) {
    colspan = mergeConfig.value.colMerge[rowIndex][property];
  }
  
  // 如果行被合并了，列合并也要相应处理
  if (rowspan === 0) {
    colspan = 0;
  }
  
  return { rowspan, colspan };
}
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
.training-content-cell {
  max-height: 60px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  line-clamp: 3;
  -webkit-box-orient: vertical;
  line-height: 1.4;
  word-break: break-word;
}

.training-content-cell:hover {
  overflow: visible;
  max-height: none;
  background-color: #f5f7fa;
  padding: 8px;
  border-radius: 4px;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  z-index: 999;
  position: relative;
}

/* 表格样式美化 */
.training-plan-table {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

/* 表格边框加粗 */
.training-plan-table :deep(.el-table__cell) {
  border: 2px solid #dcdfe6 !important;
  border-right: 2px solid #dcdfe6 !important;
  border-bottom: 2px solid #dcdfe6 !important;
}

/* 表头样式 */
.training-plan-table :deep(.el-table__header-wrapper) {
  background: linear-gradient(135deg, #ffffff 0%, #f9f9fa 100%);
}

.training-plan-table :deep(.el-table__header-wrapper th) {
  background: linear-gradient(135deg, #f8f8f8 0%, #ffffff 100%) !important;
  color: #000000 !important;
  font-weight: bold !important;
  font-size: 14px !important;
  text-align: center !important;
  border: 2px solid #fefeff !important;
  padding: 15px 8px !important;
}

/* 表格行样式 */
.training-plan-table :deep(.el-table__row) {
  transition: all 0.3s ease;
}

.training-plan-table :deep(.el-table__row:hover) {
  background-color: #f0f9ff !important;
  transform: scale(1.01);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 斑马纹样式 */
.training-plan-table :deep(.el-table__row--striped) {
  background-color: #fafbfc !important;
}

.training-plan-table :deep(.el-table__row--striped:hover) {
  background-color: #f0f9ff !important;
}

/* 合并单元格样式 */
.training-plan-table :deep(.el-table__cell) {
  position: relative;
  vertical-align: middle !important;
}

/* 合并单元格边框加强 */
.training-plan-table :deep(.el-table td) {
  border: 2px solid #dcdfe6 !important;
  padding: 12px 8px !important;
  font-size: 13px !important;
  line-height: 1.5 !important;
}

/* 选择框列样式 */
.training-plan-table :deep(.el-table-column--selection .cell) {
  padding: 0 !important;
  text-align: center !important;
}

/* 操作列样式 */
.training-plan-table :deep(.el-table__fixed-right) {
  box-shadow: -2px 0 8px rgba(0, 0, 0, 0.1);
}

/* 操作按钮样式 */
.training-plan-table :deep(.el-button--text) {
  padding: 8px 12px !important;
  margin: 0 2px !important;
  border-radius: 4px !important;
  transition: all 0.3s ease !important;
}

.training-plan-table :deep(.el-button--text:hover) {
  background-color: #409eff !important;
  color: #ffffff !important;
  transform: translateY(-1px) !important;
  box-shadow: 0 2px 4px rgba(64, 158, 255, 0.3) !important;
}

/* 表格容器样式 */
.app-container {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
}

/* 搜索表单样式 */
.el-form {
  background: #ffffff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

/* 按钮组样式 */
.mb8 {
  background: #ffffff;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 20px;
}

/* 分页器样式 */
.pagination-container {
  background: #ffffff;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-top: 20px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .training-plan-table {
    font-size: 12px;
  }
  
  .training-plan-table :deep(.el-table__cell) {
    padding: 8px 4px !important;
  }
  
  .training-plan-table :deep(.el-table__header-wrapper th) {
    padding: 12px 4px !important;
    font-size: 12px !important;
  }
}

/* 加载动画优化 */
.training-plan-table :deep(.el-loading-mask) {
  background-color: rgba(255, 255, 255, 0.9) !important;
  backdrop-filter: blur(2px) !important;
}

/* 空数据样式 */
.training-plan-table :deep(.el-table__empty-block) {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  border-radius: 8px;
  margin: 20px;
}
</style>
