<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="危险源点" prop="hazardSource">
        <el-input
          v-model="queryParams.hazardSource"
          placeholder="请输入危险源点"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重要危险源" prop="significantHazard">
        <el-input
          v-model="queryParams.significantHazard"
          placeholder="请输入重要危险源"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="导致事故健康危害" prop="accidentHealthHazards">
        <el-input
          v-model="queryParams.accidentHealthHazards"
          placeholder="请输入导致事故健康危害"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="工序/ 设备/人员" prop="processEquipmentPersonnel">
        <el-input
          v-model="queryParams.processEquipmentPersonnel"
          placeholder="请输入工序/ 设备/人员"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="控制方法" prop="controlMethods">
        <el-input
          v-model="queryParams.controlMethods"
          placeholder="请输入控制方法"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="部门/工序" prop="departmentProcess">
        <el-input
          v-model="queryParams.departmentProcess"
          placeholder="请输入部门/工序"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="审批状态" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审批状态" clearable>
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
          v-hasPermi="['security:unacceptablerisklist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:unacceptablerisklist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:unacceptablerisklist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:unacceptablerisklist:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:unacceptablerisklist:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:unacceptablerisklist:import']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unacceptablerisklistList" @selection-change="handleSelectionChange" :span-method="handleMergeCell">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="危险源点" align="center" prop="hazardSource" />
      <el-table-column label="重要危险源" align="center" prop="significantHazard" />
      <el-table-column label="导致事故健康危害" align="center" prop="accidentHealthHazards" />
      <el-table-column label="工序/ 设备/人员" align="center" prop="processEquipmentPersonnel" />
      <el-table-column label="控制方法" align="center" prop="controlMethods" />
      <el-table-column label="部门/工序" align="center" prop="departmentProcess" />
      <!-- <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:unacceptablerisklist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:unacceptablerisklist:remove']">删除</el-button>
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

    <!-- 添加或修改不可接受风险清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="unacceptablerisklistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="危险源点" prop="hazardSource">
          <el-input v-model="form.hazardSource" placeholder="请输入危险源点" />
        </el-form-item>
        <el-form-item label="重要危险源" prop="significantHazard">
          <el-input v-model="form.significantHazard" placeholder="请输入重要危险源" />
        </el-form-item>
        <el-form-item label="导致事故健康危害" prop="accidentHealthHazards">
          <el-input v-model="form.accidentHealthHazards" placeholder="请输入导致事故健康危害" />
        </el-form-item>
        <el-form-item label="工序/ 设备/人员" prop="processEquipmentPersonnel">
          <el-input v-model="form.processEquipmentPersonnel" placeholder="请输入工序/ 设备/人员" />
        </el-form-item>
        <el-form-item label="控制方法" prop="controlMethods">
          <el-input v-model="form.controlMethods" placeholder="请输入控制方法" />
        </el-form-item>
        <el-form-item label="部门/工序" prop="departmentProcess">
          <el-input v-model="form.departmentProcess" placeholder="请输入部门/工序" />
        </el-form-item>
        <!-- <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
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
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-button type="primary" :loading="upload.isUploading" style="margin-left:16px;" @click="submitFileForm">确 定</el-button>
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup name="Unacceptablerisklist">
import { listUnacceptablerisklist, getUnacceptablerisklist, delUnacceptablerisklist, addUnacceptablerisklist, updateUnacceptablerisklist, importUnacceptablerisklist, downloadTemplate } from "@/api/security/unacceptablerisklist";
import { getToken } from "@/utils/auth";
import { ref, reactive, toRefs, watch, onMounted, nextTick } from 'vue';
import { UploadFilled } from '@element-plus/icons-vue';
const route = useRoute();
const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const unacceptablerisklistList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const mergeConfig = ref({});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    hazardSource: null,
    significantHazard: null,
    accidentHealthHazards: null,
    processEquipmentPersonnel: null,
    controlMethods: null,
    departmentProcess: null,
    statu: null
  },
  rules: {
    hazardSource: [
      { required: true, message: "危险源点不能为空", trigger: "blur" }
    ],
    significantHazard: [
      { required: true, message: "重要危险源不能为空", trigger: "blur" }
    ],
    accidentHealthHazards: [
      { required: true, message: "导致事故健康危害不能为空", trigger: "blur" }
    ],
  },
  upload: {
    open: false,
    title: "导入不可接受风险清单数据",
    isUploading: false,
    updateSupport: 0,
    url: import.meta.env.VITE_APP_BASE_API + "/security/unacceptablerisklist/importData",
    headers: { Authorization: "Bearer " + getToken() }
  }
});

const { queryParams, form, rules, upload } = toRefs(data);

/** 查询不可接受风险清单列表 */
function getList() {
  loading.value = true;
  listUnacceptablerisklist(queryParams.value).then(response => {
    // 预处理数据：确保所有字段有效
    const rows = response.rows || [];
    
    // 数据预处理
    const processedData = rows.map(item => {
      // 确保所有字段有值
      if (!item.hazardSource) item.hazardSource = '';
      if (!item.significantHazard) item.significantHazard = '';
      if (!item.accidentHealthHazards) item.accidentHealthHazards = '';
      if (!item.processEquipmentPersonnel) item.processEquipmentPersonnel = '';
      if (!item.controlMethods) item.controlMethods = '';
      if (!item.departmentProcess) item.departmentProcess = '';
      
      // 去除前后空格
      item.hazardSource = item.hazardSource.trim();
      item.significantHazard = item.significantHazard.trim();
      item.accidentHealthHazards = item.accidentHealthHazards.trim();
      item.processEquipmentPersonnel = item.processEquipmentPersonnel.trim();
      item.controlMethods = item.controlMethods.trim();
      item.departmentProcess = item.departmentProcess.trim();
      
      return item;
    });
    
    // 直接使用后端返回的数据顺序
    unacceptablerisklistList.value = processedData;
    total.value = response.total;
    loading.value = false;
    
    // 计算合并配置
    calculateMergeConfig();
  });
}

/**
 * 计算表格合并配置
 * 处理所有列的合并单元格
 */
function calculateMergeConfig() {
  const data = unacceptablerisklistList.value;
  if (!data || data.length === 0) return;
  
  console.log('计算表格合并配置...');
  
  // 创建合并配置对象
  const config = {
    hazardSource: {}, // 危险源点
    significantHazard: {}, // 重要危险源
    accidentHealthHazards: {}, // 导致事故健康危害
    processEquipmentPersonnel: {}, // 工序/设备/人员
    controlMethods: {}, // 控制方法
    departmentProcess: {} // 部门/工序
  };
  
  // 处理每一列的合并
  processColumnMerge(data, config, 'hazardSource');
  
  // 处理其他列的合并（考虑前面列的值）
  processColumnMergeWithDependency(data, config, 'significantHazard', ['hazardSource']);
  processColumnMergeWithDependency(data, config, 'accidentHealthHazards', ['hazardSource', 'significantHazard']);
  processColumnMergeWithDependency(data, config, 'processEquipmentPersonnel', ['hazardSource', 'significantHazard', 'accidentHealthHazards']);
  processColumnMergeWithDependency(data, config, 'controlMethods', ['hazardSource', 'significantHazard', 'accidentHealthHazards', 'processEquipmentPersonnel']);
  processColumnMergeWithDependency(data, config, 'departmentProcess', ['hazardSource', 'significantHazard', 'accidentHealthHazards', 'processEquipmentPersonnel', 'controlMethods']);
  
  mergeConfig.value = config;
  console.log('合并配置计算完成');
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
    
    // 第一行，初始化当前值
    if (i === 0) {
      currentValue = value;
    } 
    // 非第一行，检查是否与前一行相同
    else {
      // 值相同，增加合并行数
      if (value === currentValue) {
        span++;
      } 
      // 值不同，记录上一组合并配置，开始新一组
      else {
        // 记录上一组合并配置（只有跨行数>1时才需要合并）
        if (span > 1) {
          config[columnName][startIndex] = span;
        }
        
        // 开始新一组
        currentValue = value;
        startIndex = i;
        span = 1;
      }
    }
  }
  
  // 处理最后一组数据
  if (span > 1) {
    config[columnName][startIndex] = span;
  }
}

/**
 * 处理带依赖的列合并
 * 只有当所有依赖列的值都相同时，才考虑合并当前列
 */
function processColumnMergeWithDependency(data, config, columnName, dependencies) {
  let groups = [];
  let currentGroup = null;
  
  // 根据依赖列的值分组
  for (let i = 0; i < data.length; i++) {
    const row = data[i];
    
    // 构建依赖键
    const dependencyKey = dependencies.map(dep => row[dep]).join('|');
    
    if (currentGroup === null || currentGroup.dependencyKey !== dependencyKey) {
      // 创建新分组
      currentGroup = {
        dependencyKey,
        startIndex: i,
        values: [{ value: row[columnName], index: i }]
      };
      groups.push(currentGroup);
    } else {
      // 添加到当前分组
      currentGroup.values.push({ value: row[columnName], index: i });
    }
  }
  
  // 处理每个分组内的合并
  for (const group of groups) {
    let currentValue = null;
    let startIndex = 0;
    let span = 1;
    
    for (let i = 0; i < group.values.length; i++) {
      const { value, index } = group.values[i];
      
      // 第一个值，初始化
      if (i === 0) {
        currentValue = value;
        startIndex = index;
      } 
      // 非第一个值，检查是否与前一个相同
      else {
        // 值相同，增加合并行数
        if (value === currentValue) {
          span++;
        } 
        // 值不同，记录上一组合并配置，开始新一组
        else {
          // 记录上一组合并配置（只有跨行数>1时才需要合并）
          if (span > 1) {
            config[columnName][startIndex] = span;
          }
          
          // 开始新一组
          currentValue = value;
          startIndex = index;
          span = 1;
        }
      }
    }
    
    // 处理最后一组数据
    if (span > 1) {
      config[columnName][startIndex] = span;
    }
  }
}

/**
 * 表格合并单元格方法
 */
function handleMergeCell({ row, column, rowIndex, columnIndex }) {
  // 检查是否有合并配置
  if (!mergeConfig.value || Object.keys(mergeConfig.value).length === 0) {
    return { rowspan: 1, colspan: 1 };
  }
  
  // 根据列属性名称处理合并
  const property = column.property;
  if (property && mergeConfig.value[property]) {
    // 如果当前行是合并的起始行
    if (mergeConfig.value[property][rowIndex]) {
      return { 
        rowspan: mergeConfig.value[property][rowIndex], 
        colspan: 1 
      };
    } 
    // 如果当前行被合并到前面的行中
    else {
      // 查找前面的行，检查当前行是否被合并
      for (let i = 0; i < rowIndex; i++) {
        const span = mergeConfig.value[property][i];
        if (span && i + span > rowIndex) {
          // 当前行被合并，隐藏
          return { rowspan: 0, colspan: 0 };
        }
      }
    }
  }
  
  // 默认不合并
  return { rowspan: 1, colspan: 1 };
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    hazardSource: null,
    significantHazard: null,
    accidentHealthHazards: null,
    processEquipmentPersonnel: null,
    controlMethods: null,
    departmentProcess: null,
    statu: null
  };
  proxy.resetForm("unacceptablerisklistRef");
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
      if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加不可接受风险清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getUnacceptablerisklist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改不可接受风险清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["unacceptablerisklistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUnacceptablerisklist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUnacceptablerisklist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除不可接受风险清单编号为"' + _ids + '"的数据项？').then(function() {
    return delUnacceptablerisklist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/unacceptablerisklist/export', {
    ...queryParams.value
  }, `unacceptablerisklist_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.value.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  downloadTemplate().then(response => {
    const blob = new Blob([response]);
    const fileName = `不可接受风险清单模板_${new Date().getTime()}.xlsx`;
    if ('download' in document.createElement('a')) {
      const link = document.createElement('a');
      link.download = fileName;
      link.style.display = 'none';
      link.href = URL.createObjectURL(blob);
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(link.href);
      document.body.removeChild(link);
    } else {
      navigator.msSaveBlob(blob, fileName);
    }
  }).catch(err => {
    console.error('下载模板失败', err);
    proxy.$modal.msgError('下载模板失败，请联系管理员！');
  });
}

/** 文件上传中处理 */
function handleFileUploadProgress(event, file) {
  upload.value.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response, file) {
  upload.value.open = false;
  upload.value.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

// 在数据变化时重新计算合并配置
watch(unacceptablerisklistList, () => {
  nextTick(() => {
    calculateMergeConfig();
  });
}, { deep: true });

function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
  }
}

// 在组件挂载时初始化数据
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
