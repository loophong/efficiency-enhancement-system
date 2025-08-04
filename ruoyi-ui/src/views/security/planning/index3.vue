<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作业活动" prop="activity">
        <el-input
          v-model="queryParams.activity"
          placeholder="请输入作业活动"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部位" prop="part">
        <el-input
          v-model="queryParams.part"
          placeholder="请输入部位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="危险源" prop="hazardSource">
        <el-input
          v-model="queryParams.hazardSource"
          placeholder="请输入危险源"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="危害方式" prop="hazardMode">
        <el-input
          v-model="queryParams.hazardMode"
          placeholder="请输入危害方式"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="可能导致的事故" prop="possibleConsequences">
        <el-input
          v-model="queryParams.possibleConsequences"
          placeholder="请输入可能导致的事故"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="评价方式(LEC)" prop="evaluationMethod">
        <el-input
          v-model="queryParams.evaluationMethod"
          placeholder="请输入评价方式(LEC)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="L" prop="evaluationA">
        <el-input
          v-model="queryParams.evaluationA"
          placeholder="请输入L"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="E" prop="evaluationB">
        <el-input
          v-model="queryParams.evaluationB"
          placeholder="请输入E"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="C" prop="evaluationC">
        <el-input
          v-model="queryParams.evaluationC"
          placeholder="请输入C"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三角形" prop="evaluationSanjiaoxing">
        <el-input
          v-model="queryParams.evaluationSanjiaoxing"
          placeholder="请输入三角形"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="风险级别" prop="riskLevel">
        <el-input
          v-model="queryParams.riskLevel"
          placeholder="请输入风险级别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="控制措施" prop="controlMeasures">
        <el-input
          v-model="queryParams.controlMeasures"
          placeholder="请输入控制措施"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="审核人" prop="auditor">
        <el-input
          v-model="queryParams.auditor"
          placeholder="请输入审核人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="批准人" prop="approver">
        <el-input
          v-model="queryParams.approver"
          placeholder="请输入批准人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="时间" prop="time">
        <el-date-picker clearable
          v-model="queryParams.time"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审批状态" prop="statu">
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
          v-hasPermi="['security:risklist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:risklist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:risklist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:risklist:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:risklist:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:risklist:import']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="risklistList" 
      @selection-change="handleSelectionChange"
      :span-method="handleMergeCell">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号"  align="center" type="index" width="80" />
      <el-table-column label="作业活动" align="center" prop="activity" />
      <el-table-column label="部位" align="center" prop="part" />
      <el-table-column label="危险源" align="center" prop="hazardSource" />
      <el-table-column label="危害方式" align="center" prop="hazardMode" />
      <el-table-column label="可能导致的事故" align="center" prop="possibleConsequences" />
      <!-- <el-table-column label="评价方式(LEC)" align="center" prop="evaluationMethod" /> -->
      <el-table-column label="LEC评价方式" align="center">
        <el-table-column label="L" align="center" prop="evaluationA" />
        <el-table-column label="E" align="center" prop="evaluationB" />
        <el-table-column label="C" align="center" prop="evaluationC" />
        <el-table-column label="三角形" align="center" prop="evaluationSanjiaoxing">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.evaluationSanjiaoxing"/>
        </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="风险级别" align="center" prop="riskLevel" />
      <el-table-column label="控制措施" align="center" prop="controlMeasures" />
      <!-- <el-table-column label="审核人" align="center" prop="auditor" />
      <el-table-column label="批准人" align="center" prop="approver" />
      <el-table-column label="时间" align="center" prop="time" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:risklist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:risklist:remove']">删除</el-button>
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

    <!-- 添加或修改危险源清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="risklistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作业活动" prop="activity">
          <el-input v-model="form.activity" placeholder="请输入作业活动" />
        </el-form-item>
        <el-form-item label="部位" prop="part">
          <el-input v-model="form.part" placeholder="请输入部位" />
        </el-form-item>
        <el-form-item label="危险源" prop="hazardSource">
          <el-input v-model="form.hazardSource" placeholder="请输入危险源" />
        </el-form-item>
        <el-form-item label="危害方式" prop="hazardMode">
          <el-input v-model="form.hazardMode" placeholder="请输入危害方式" />
        </el-form-item>
        <el-form-item label="可能导致的事故" prop="possibleConsequences">
          <el-input v-model="form.possibleConsequences" placeholder="请输入可能导致的事故" />
        </el-form-item>
        <el-form-item label="评价方式(LEC)" prop="evaluationMethod">
          <el-input v-model="form.evaluationMethod" placeholder="请输入评价方式(LEC)" />
        </el-form-item>
        <el-form-item label="L" prop="evaluationA">
          <el-input v-model="form.evaluationA" placeholder="请输入L" />
        </el-form-item>
        <el-form-item label="E" prop="evaluationB">
          <el-input v-model="form.evaluationB" placeholder="请输入E" />
        </el-form-item>
        <el-form-item label="C" prop="evaluationC">
          <el-input v-model="form.evaluationC" placeholder="请输入C" />
        </el-form-item>
        <el-form-item label="三角形" prop="evaluationSanjiaoxing">
          <el-input v-model="form.evaluationSanjiaoxing" placeholder="请输入三角形" />
        </el-form-item>
        <el-form-item label="风险级别" prop="riskLevel">
          <el-input v-model="form.riskLevel" placeholder="请输入风险级别" />
        </el-form-item>
        <el-form-item label="控制措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" placeholder="请输入控制措施" />
        </el-form-item>
        <el-form-item label="审核人" prop="auditor">
          <el-input v-model="form.auditor" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="批准人" prop="approver">
          <el-input v-model="form.approver" placeholder="请输入批准人" />
        </el-form-item>
        <el-form-item label="时间" prop="time">
          <el-date-picker clearable
            v-model="form.time"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="Risklist">
import { listRisklist, getRisklist, delRisklist, addRisklist, updateRisklist, importRisklist, downloadTemplate } from "@/api/security/risklist";
import { getToken } from "@/utils/auth";
import { ref, reactive, toRefs, watch, onMounted, nextTick } from 'vue';

const { proxy } = getCurrentInstance();
const { security_status, sys_yes_no } = proxy.useDict('security_status', 'sys_yes_no');
const route = useRoute();
const risklistList = ref([]);
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
    activity: null,
    part: null,
    hazardSource: null,
    hazardMode: null,
    possibleConsequences: null,
    evaluationMethod: null,
    evaluationA: null,
    evaluationB: null,
    evaluationC: null,
    evaluationSanjiaoxing: null,
    riskLevel: null,
    controlMeasures: null,
    auditor: null,
    approver: null,
    time: null,
    statu: null
  },
  rules: {
    activity: [
      { required: true, message: "作业活动不能为空", trigger: "blur" }
    ],
    hazardSource: [
      { required: true, message: "危险源不能为空", trigger: "blur" }
    ],
    riskLevel: [
      { required: true, message: "风险级别不能为空", trigger: "blur" }
    ],

  },
  upload: {
    open: false,
    title: "导入危险源清单数据",
    isUploading: false,
    updateSupport: 0,
    url: import.meta.env.VITE_APP_BASE_API + "/security/risklist/importData",
    headers: { Authorization: "Bearer " + getToken() }
  }
});

const { queryParams, form, rules, upload } = toRefs(data);

/** 查询危险源清单列表 */
function getList() {
  loading.value = true;
  console.log('开始加载数据...');
  listRisklist(queryParams.value).then(response => {
    // 预处理数据：确保所有字段有效，按序号排序
    const rows = response.rows || [];
    
    // 数据预处理
    const processedData = rows.map(item => {
      // 确保必要字段有值
      if (!item.activity) {
        item.activity = '';
      }
      // 去除前后空格
      item.activity = item.activity.trim();
      return item;
    });
    
    // 按照序号排序（即按照数据在数据库中的原始顺序）
    const sortedList = [...processedData];
    
    risklistList.value = sortedList;
    total.value = response.total;
    loading.value = false;
    console.log('数据加载完成，共', response.total, '条记录');
    
    // 计算合并配置
    calculateMergeConfig();
  });
}

/**
 * 计算表格合并配置
 * 使用更高效的算法计算合并单元格
 */
function calculateMergeConfig() {
  const data = risklistList.value;
  if (!data || data.length === 0) return;
  
  console.log('计算表格合并配置...');
  
  // 创建合并配置对象
  const config = {
    activity: {}, // 作业活动列的合并配置
    part: {} // 部位列的合并配置
  };
  
  // 特殊处理：先检查数据中是否有相同的作业活动值
  const activityValues = new Set();
  let hasDuplicates = false;
  
  // 检查是否有重复的作业活动值
  for (let i = 0; i < data.length; i++) {
    const activity = data[i].activity || '';
    if (activityValues.has(activity)) {
      hasDuplicates = true;
      break;
    }
    activityValues.add(activity);
  }
  
  // 如果没有重复的作业活动值，则不需要合并
  if (!hasDuplicates) {
    console.log('没有重复的作业活动值，不需要合并');
    mergeConfig.value = { activity: {}, part: {} };
    return;
  }
  
  // 重置并继续处理合并逻辑
  let currentActivity = null;
  let activitySpan = 1;
  let activityStartIndex = 0;
  
  // 遍历数据，计算合并配置
  for (let i = 0; i < data.length; i++) {
    const row = data[i];
    const activity = row.activity || '';
    
    // 第一行，初始化当前值
    if (i === 0) {
      currentActivity = activity;
      activityStartIndex = 0;
      activitySpan = 1;
      
      // 特殊处理：检查第一行的值是否在后续行中重复
      let firstRowDuplicate = false;
      for (let j = 1; j < data.length; j++) {
        if (data[j].activity === activity) {
          firstRowDuplicate = true;
          break;
        }
      }
      
      // 如果第一行的值不重复，则不合并第一行
      if (!firstRowDuplicate) {
        console.log('第一行作业活动值不重复，不合并第一行');
      }
    } 
    // 非第一行，检查是否与前一行相同
    else {
      // 作业活动相同，增加合并行数
      if (activity === currentActivity) {
        activitySpan++;
      } 
      // 作业活动不同，记录上一组合并配置，开始新一组
      else {
        // 记录上一组合并配置（只有跨行数>1时才需要合并）
        if (activitySpan > 1) {
          config.activity[activityStartIndex] = activitySpan;
          console.log(`合并行 ${activityStartIndex} 到 ${activityStartIndex + activitySpan - 1}，值: "${currentActivity}"`);
        }
        
        // 开始新一组
        currentActivity = activity;
        activityStartIndex = i;
        activitySpan = 1;
      }
    }
  }
  
  // 处理最后一组数据
  if (activitySpan > 1) {
    config.activity[activityStartIndex] = activitySpan;
    console.log(`合并行 ${activityStartIndex} 到 ${activityStartIndex + activitySpan - 1}，值: "${currentActivity}"`);
  }
  
  // 部位列的合并配置
  const partValues = new Set();
  let partSpan = 1;
  let partStartIndex = 0;
  let currentPart = null;

  for (let i = 0; i < data.length; i++) {
    const row = data[i];
    const part = row.part || '';

    if (i === 0) {
      currentPart = part;
      partStartIndex = 0;
      partSpan = 1;
    } else {
      if (part === currentPart) {
        partSpan++;
      } else {
        if (partSpan > 1) {
          config.part[partStartIndex] = partSpan;
          console.log(`合并行 ${partStartIndex} 到 ${partStartIndex + partSpan - 1}，值: "${currentPart}"`);
        }
        currentPart = part;
        partStartIndex = i;
        partSpan = 1;
      }
    }
  }

  if (partSpan > 1) {
    config.part[partStartIndex] = partSpan;
    console.log(`合并行 ${partStartIndex} 到 ${partStartIndex + partSpan - 1}，值: "${currentPart}"`);
  }

  mergeConfig.value = config;
  console.log('合并配置计算完成:', JSON.stringify(mergeConfig.value));
}

/**
 * 表格合并单元格方法
 * 基于Element Plus的span-method实现
 */
function handleMergeCell({ row, column, rowIndex, columnIndex }) {
  // 检查是否有合并配置
  if (!mergeConfig.value || Object.keys(mergeConfig.value).length === 0) {
    return { rowspan: 1, colspan: 1 };
  }
  
  // 作业活动列合并（第1列，索引为1，第0列是选择框）
  if (column.property === 'activity') {
    // 记录调试信息
    console.log(`处理行 ${rowIndex}, 列 ${column.property}, 值: "${row.activity}"`);
    
    // 如果当前行是合并的起始行
    if (mergeConfig.value.activity[rowIndex]) {
      const span = mergeConfig.value.activity[rowIndex];
      console.log(`行 ${rowIndex} 是合并起始行，合并 ${span} 行`);
      return { 
        rowspan: span, 
        colspan: 1 
      };
    } 
    // 如果当前行被合并到前面的行中
    else if (rowIndex > 0) {
      // 查找前面的行，检查当前行是否被合并
      for (let i = 0; i < rowIndex; i++) {
        const span = mergeConfig.value.activity[i];
        if (span && i + span > rowIndex) {
          console.log(`行 ${rowIndex} 被合并到行 ${i} 中`);
          // 当前行被合并，隐藏
          return { rowspan: 0, colspan: 0 };
        }
      }
    }
    
    // 默认情况：单独显示该行
    console.log(`行 ${rowIndex} 不需要合并`);
    return { rowspan: 1, colspan: 1 };
  }

  // 部位列合并（第2列，索引为2，第0列是选择框）
  if (column.property === 'part') {
    console.log(`处理行 ${rowIndex}, 列 ${column.property}, 值: "${row.part}"`);
    if (mergeConfig.value.part[rowIndex]) {
      const span = mergeConfig.value.part[rowIndex];
      console.log(`行 ${rowIndex} 是合并起始行，合并 ${span} 行`);
      return {
        rowspan: span,
        colspan: 1
      };
    } else if (rowIndex > 0) {
      for (let i = 0; i < rowIndex; i++) {
        const span = mergeConfig.value.part[i];
        if (span && i + span > rowIndex) {
          console.log(`行 ${rowIndex} 被合并到行 ${i} 中`);
          return { rowspan: 0, colspan: 0 };
        }
      }
    }
    console.log(`行 ${rowIndex} 不需要合并`);
    return { rowspan: 1, colspan: 1 };
  }
  
  // 默认不合并
  return { rowspan: 1, colspan: 1 };
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
    activity: null,
    part: null,
    hazardSource: null,
    hazardMode: null,
    possibleConsequences: null,
    evaluationMethod: null,
    evaluationA: null,
    evaluationB: null,
    evaluationC: null,
    evaluationSanjiaoxing: null,
    riskLevel: null,
    controlMeasures: null,
    auditor: null,
    approver: null,
    time: null,
    statu: null
  };
  proxy.resetForm("risklistRef");
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
  title.value = "添加危险源清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRisklist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改危险源清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["risklistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRisklist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRisklist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除危险源清单编号为"' + _ids + '"的数据项？').then(function() {
    return delRisklist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/risklist/export', {
    ...queryParams.value
  }, `risklist_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.value.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  downloadTemplate().then(response => {
    const blob = new Blob([response]);
    const fileName = `危险源清单模板_${new Date().getTime()}.xlsx`;
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
watch(risklistList, () => {
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
// 在组件挂载后，添加一个额外的处理来确保表格正确渲染
onMounted(() => {
  console.log('组件挂载完成，初始化数据');
   // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
  
  // 添加额外的处理，确保表格在数据加载后正确渲染
  setTimeout(() => {
    // 强制触发表格重新渲染
    if (risklistList.value.length > 0) {
      const temp = [...risklistList.value];
      risklistList.value = [];
      nextTick(() => {
        risklistList.value = temp;
      });
    }
  }, 500);
});

</script>
