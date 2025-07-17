<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入分类"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="指标名称" prop="indicatorName">
        <el-input
          v-model="queryParams.indicatorName"
          placeholder="请输入指标名称"
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
          v-hasPermi="['security:kpi:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:kpi:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:kpi:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:kpi:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:kpi:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:kpi:import']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kpiList" @selection-change="handleSelectionChange" :span-method="objectSpanMethod">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="分类" align="center" prop="category" />
      <el-table-column label="指标名称" align="center" prop="indicatorName" />
      <el-table-column label="单位" align="center" prop="unitOfMeasurement" width="60" />
      <el-table-column label="年度目标值" align="center">
        <template #default="scope">
          <span>
            {{ formatAnnualTargetValue(scope.row) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="考核频次" align="center" prop="evaluationFrequency" width="80" />
      <el-table-column label="权重" align="center" prop="weight" width="60" />
      <el-table-column label="一季度" align="center">
        <el-table-column label="目标值" align="center">
          <template #default="scope">
            <span>{{ formatQ1TargetValue(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结果值" align="center" prop="q1Result" />
        <el-table-column label="核定值" align="center" prop="q1VerifiedValue" width="60" />
      </el-table-column>
      <el-table-column label="二季度" align="center">
        <el-table-column label="目标值" align="center">
          <template #default="scope">
            <span>{{ formatQ2TargetValue(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结果值" align="center" prop="q2Result" />
        <el-table-column label="核定值" align="center" prop="q2VerifiedValue" width="60" />
      </el-table-column>
      <el-table-column label="三季度" align="center">
        <el-table-column label="目标值" align="center">
          <template #default="scope">
            <span>{{ formatQ3TargetValue(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结果值" align="center" prop="q3Result" />
        <el-table-column label="核定值" align="center" prop="q3VerifiedValue" width="60" />
      </el-table-column>
      <el-table-column label="四季度" align="center">
        <el-table-column label="目标值" align="center">
          <template #default="scope">
            <span>{{ formatQ4TargetValue(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结果值" align="center" prop="q4Result" />
        <el-table-column label="核定值" align="center" prop="q4VerifiedValue" width="60" />
      </el-table-column>
      <el-table-column label="备注" align="center">
        <template #default="scope">
          <span>{{ formatBeizhu(scope.row.beizhu) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:kpi:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:kpi:remove']">删除</el-button>
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

    <!-- 添加或修改公司KPI对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="kpiRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-input v-model="form.category" placeholder="请输入分类" />
        </el-form-item>
        <el-form-item label="指标名称" prop="indicatorName">
          <el-input v-model="form.indicatorName" placeholder="请输入指标名称" />
        </el-form-item>
        <el-form-item label="单位" prop="unitOfMeasurement">
          <el-input v-model="form.unitOfMeasurement" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-date-picker
            v-model="form.year"
            type="year"
            placeholder="选择年份"
            value-format="YYYY"
          />
        </el-form-item>
        <el-form-item label="目标值" prop="annualTargetValue">
          <el-input v-model="form.annualTargetValue" placeholder="请输入目标值">
            <template #prepend>
              <el-select v-model="targetFormats.annual" placeholder="格式" style="width: 80px">
                <el-option label="无" value="" />
                <el-option label=">" value=">" />
                <el-option label=">=" value=">=" />
                <el-option label="<" value="<" />
                <el-option label="<=" value="<=" />
                <el-option label="=" value="=" />
              </el-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="考核频次" prop="evaluationFrequency">
          <el-select v-model="form.evaluationFrequency" placeholder="请选择考核频次">
            <el-option label="年度" value="年度" />
            <el-option label="季度" value="季度" />
            <el-option label="半年度" value="半年度" />
          </el-select>
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入权重" />
        </el-form-item>
        
        <el-divider content-position="center">一季度</el-divider>
        <el-form-item label="目标值" prop="q1TargetValue">
          <el-input v-model="form.q1TargetValue" placeholder="请输入一季度目标值">
            <template #prepend>
              <el-select v-model="targetFormats.q1" placeholder="格式" style="width: 80px">
                <el-option label="无" value="" />
                <el-option label=">" value=">" />
                <el-option label=">=" value=">=" />
                <el-option label="<" value="<" />
                <el-option label="<=" value="<=" />
                <el-option label="=" value="=" />
              </el-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="结果值" prop="q1Result">
          <el-input v-model="form.q1Result" placeholder="请输入一季度结果值" />
        </el-form-item>
        <el-form-item label="核定值" prop="q1VerifiedValue">
          <el-input v-model="form.q1VerifiedValue" placeholder="请输入一季度核定值" />
        </el-form-item>
        
        <el-divider content-position="center">二季度</el-divider>
        <el-form-item label="目标值" prop="q2TargetValue">
          <el-input v-model="form.q2TargetValue" placeholder="请输入二季度目标值">
            <template #prepend>
              <el-select v-model="targetFormats.q2" placeholder="格式" style="width: 80px">
                <el-option label="无" value="" />
                <el-option label=">" value=">" />
                <el-option label=">=" value=">=" />
                <el-option label="<" value="<" />
                <el-option label="<=" value="<=" />
                <el-option label="=" value="=" />
              </el-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="结果值" prop="q2Result">
          <el-input v-model="form.q2Result" placeholder="请输入二季度结果值" />
        </el-form-item>
        <el-form-item label="核定值" prop="q2VerifiedValue">
          <el-input v-model="form.q2VerifiedValue" placeholder="请输入二季度核定值" />
        </el-form-item>
        
        <el-divider content-position="center">三季度</el-divider>
        <el-form-item label="目标值" prop="q3TargetValue">
          <el-input v-model="form.q3TargetValue" placeholder="请输入三季度目标值">
            <template #prepend>
              <el-select v-model="targetFormats.q3" placeholder="格式" style="width: 80px">
                <el-option label="无" value="" />
                <el-option label=">" value=">" />
                <el-option label=">=" value=">=" />
                <el-option label="<" value="<" />
                <el-option label="<=" value="<=" />
                <el-option label="=" value="=" />
              </el-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="结果值" prop="q3Result">
          <el-input v-model="form.q3Result" placeholder="请输入三季度结果值" />
        </el-form-item>
        <el-form-item label="核定值" prop="q3VerifiedValue">
          <el-input v-model="form.q3VerifiedValue" placeholder="请输入三季度核定值" />
        </el-form-item>
        
        <el-divider content-position="center">四季度</el-divider>
        <el-form-item label="目标值" prop="q4TargetValue">
          <el-input v-model="form.q4TargetValue" placeholder="请输入四季度目标值">
            <template #prepend>
              <el-select v-model="targetFormats.q4" placeholder="格式" style="width: 80px">
                <el-option label="无" value="" />
                <el-option label=">" value=">" />
                <el-option label=">=" value=">=" />
                <el-option label="<" value="<" />
                <el-option label="<=" value="<=" />
                <el-option label="=" value="=" />
              </el-select>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="结果值" prop="q4Result">
          <el-input v-model="form.q4Result" placeholder="请输入四季度结果值" />
        </el-form-item>
        <el-form-item label="核定值" prop="q4VerifiedValue">
          <el-input v-model="form.q4VerifiedValue" placeholder="请输入四季度核定值" />
        </el-form-item>
        
        <el-divider content-position="center">其他</el-divider>
        <el-form-item label="备注" prop="beizhu">
          <el-input v-model="form.beizhu" type="textarea" placeholder="请输入备注" />
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

<script setup name="Kpi">
import { listKpi, getKpi, delKpi, addKpi, updateKpi, importKpi} from "@/api/security/kpi";
import { UploadFilled } from '@element-plus/icons-vue';
import { ref, reactive, toRefs, getCurrentInstance, watch } from 'vue';
import axios from 'axios'; // 导入axios
import { getToken } from '@/utils/auth'; // 导入获取token的函数

const { proxy } = getCurrentInstance();
const route = useRoute();
const kpiList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const selectedFile = ref(null);
const spanArr = ref([]);
const pos = ref({});
const targetFormats = reactive({
  annual: "",
  q1: "",
  q2: "",
  q3: "",
  q4: ""
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    unitName: null,
    category: null,
    indicatorName: null,
    unitOfMeasurement: null,
    year: null,
    annualTargetValue: null,
    evaluationFrequency: null,
    weight: null,
    q1TargetValue: null,
    q1Result: null,
    q1VerifiedValue: null,
    q2TargetValue: null,
    q2Result: null,
    q2VerifiedValue: null,
    q3TargetValue: null,
    q3Result: null,
    q3VerifiedValue: null,
    q4TargetValue: null,
    q4Result: null,
    q4VerifiedValue: null,
    beizhu: null
  },
  rules: {
    unitName: [],
    category: [],
    indicatorName: [],
    unitOfMeasurement: [],
    year: [],
    annualTargetValue: [],
    evaluationFrequency: [],
    weight: [],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询公司KPI列表 */
function getList() {
  loading.value = true;
  listKpi(queryParams.value).then(response => {
    kpiList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // 初始化合并单元格数据
    getSpanArr(kpiList.value);
  });
}

// 计算合并单元格的方法
function getSpanArr(data) {
  spanArr.value = [];
  pos.value = {};
  
  // 初始化pos和spanArr
  for (let i = 0; i < data.length; i++) {
    // 处理分类列
    if (i === 0) {
      spanArr.value.push(1);
      pos.value[data[0].category] = 0;
    } else {
      // 判断当前行与上一行的分类是否相同
      if (data[i].category === data[i-1].category) {
        spanArr.value[pos.value[data[i].category]] += 1;
        spanArr.value.push(0);
      } else {
        spanArr.value.push(1);
        pos.value[data[i].category] = i;
      }
    }
  }
}

// 合并单元格方法
function objectSpanMethod({ row, column, rowIndex, columnIndex }) {
  if (columnIndex === 2) { // 分类列
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
    id: null,
    unitName: null,
    category: null,
    indicatorName: null,
    unitOfMeasurement: null,
    year: new Date().getFullYear().toString(),
    annualTargetValue: null,
    evaluationFrequency: null,
    weight: null,
    q1TargetValue: null,
    q1Result: null,
    q1VerifiedValue: null,
    q2TargetValue: null,
    q2Result: null,
    q2VerifiedValue: null,
    q3TargetValue: null,
    q3Result: null,
    q3VerifiedValue: null,
    q4TargetValue: null,
    q4Result: null,
    q4VerifiedValue: null,
    beizhu: null
  };
  proxy.resetForm("kpiRef");
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
  targetFormats.annual = "";
  targetFormats.q1 = "";
  targetFormats.q2 = "";
  targetFormats.q3 = "";
  targetFormats.q4 = "";
  open.value = true;
  title.value = "添加公司KPI";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getKpi(_id).then(response => {
    form.value = response.data;
    
    // 处理备注，提取用户实际输入的备注
    if (form.value.beizhu) {
      form.value.beizhu = formatBeizhu(form.value.beizhu);
    }
    
    // 解析目标值格式
    targetFormats.annual = getTargetFormat(response.data.beizhu, "annual") || "";
    targetFormats.q1 = getTargetFormat(response.data.beizhu, "q1") || "";
    targetFormats.q2 = getTargetFormat(response.data.beizhu, "q2") || "";
    targetFormats.q3 = getTargetFormat(response.data.beizhu, "q3") || "";
    targetFormats.q4 = getTargetFormat(response.data.beizhu, "q4") || "";
    open.value = true;
    title.value = "修改公司KPI";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["kpiRef"].validate(valid => {
    if (valid) {
      // 保存表单前处理特殊格式
      const formData = { ...form.value };
      
      // 构建格式信息
      let formatInfo = "";
      
      // 处理年度目标值格式
      if (targetFormats.annual) {
        formatInfo += `ANNUAL_TARGET:${targetFormats.annual};`;
      }
      
      // 处理一季度目标值格式
      if (targetFormats.q1) {
        formatInfo += `Q1_TARGET:${targetFormats.q1};`;
      }
      
      // 处理二季度目标值格式
      if (targetFormats.q2) {
        formatInfo += `Q2_TARGET:${targetFormats.q2};`;
      }
      
      // 处理三季度目标值格式
      if (targetFormats.q3) {
        formatInfo += `Q3_TARGET:${targetFormats.q3};`;
      }
      
      // 处理四季度目标值格式
      if (targetFormats.q4) {
        formatInfo += `Q4_TARGET:${targetFormats.q4};`;
      }
      
      // 添加格式信息到备注
      if (formatInfo) {
        // 保存用户输入的原始备注
        const userBeizhu = formData.beizhu || "";
        
        // 如果原来有格式标记，更新它
        if (userBeizhu.includes("FORMAT_INFO:")) {
          // 提取用户实际输入的备注部分
          let realBeizhu = "";
          if (userBeizhu.indexOf("FORMAT_INFO:") === 0) {
            // 格式信息在前面
            const parts = userBeizhu.split("；");
            if (parts.length > 1) {
              parts.shift();
              realBeizhu = parts.join("；");
            }
          } else {
            // 格式信息在后面
            realBeizhu = userBeizhu.substring(0, userBeizhu.indexOf("；FORMAT_INFO:"));
          }
          
          // 组合新的备注
          if (realBeizhu) {
            formData.beizhu = realBeizhu + "；FORMAT_INFO:" + formatInfo;
          } else {
            formData.beizhu = "FORMAT_INFO:" + formatInfo;
          }
        } 
        // 如果没有格式标记
        else {
          // 添加格式信息
          if (userBeizhu) {
            formData.beizhu = userBeizhu + "；FORMAT_INFO:" + formatInfo;
          } else {
            formData.beizhu = "FORMAT_INFO:" + formatInfo;
          }
        }
      }
      
      if (formData.id != null) {
        updateKpi(formData).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addKpi(formData).then(response => {
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
  proxy.$modal.confirm('是否确认删除公司KPI编号为"' + _ids + '"的数据项？').then(function() {
    return delKpi(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/kpi/export', {
    ...queryParams.value
  }, `KPI考核_${new Date().getTime()}.xlsx`, 'get')
}

/** 导入按钮操作 */
function handleImport() {
  uploadDialogVisible.value = true;
  selectedFile.value = null;
}

/** 下载模板按钮操作 */
function handleDownloadTemplate() {
  proxy.download('security/kpi/importTemplate', {}, `公司KPI考核导入模板_${new Date().getTime()}.xlsx`, 'post');
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
    
    // 使用axios直接发送请求，绕过request.js的处理
    axios.post(import.meta.env.VITE_APP_BASE_API + '/security/kpi/importData', formData, {
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

// 格式化目标值显示
function formatTargetValue(row) {
  // 处理特殊格式的年度目标值
  if (row.beizhu) {
    // 检查是否包含目标格式标记
    const targetFormatMatch = row.beizhu.match(/TARGET_FORMAT:([<>=]+)/);
    if (targetFormatMatch && targetFormatMatch[1]) {
      const operator = targetFormatMatch[1];
      return `${operator}${row.annualTargetValue}`;
    }
    
    // 兼容旧的格式标记
    if (row.beizhu.includes('>=')) {
      return `>=${row.annualTargetValue}`;
    } else if (row.beizhu.includes('>')) {
      return `>${row.annualTargetValue}`;
    } else if (row.beizhu.includes('<=')) {
      return `<=${row.annualTargetValue}`;
    } else if (row.beizhu.includes('<')) {
      return `<${row.annualTargetValue}`;
    } else if (row.beizhu.includes('=')) {
      return `=${row.annualTargetValue}`;
    }
  }
  return row.annualTargetValue;
}

// 获取目标值格式化前缀
function getTargetFormat(beizhu, quarter = "annual") {
  if (!beizhu) {
    return null;
  }
  
  // 尝试从新格式中获取
  const formatInfo = parseFormatInfo(beizhu);
  if (formatInfo) {
    if (quarter === "annual" && formatInfo.ANNUAL_TARGET) {
      return formatInfo.ANNUAL_TARGET;
    } else if (quarter === "q1" && formatInfo.Q1_TARGET) {
      return formatInfo.Q1_TARGET;
    } else if (quarter === "q2" && formatInfo.Q2_TARGET) {
      return formatInfo.Q2_TARGET;
    } else if (quarter === "q3" && formatInfo.Q3_TARGET) {
      return formatInfo.Q3_TARGET;
    } else if (quarter === "q4" && formatInfo.Q4_TARGET) {
      return formatInfo.Q4_TARGET;
    }
  }
  
  // 兼容旧格式
  if (quarter === "annual") {
    const match = beizhu.match(/TARGET_FORMAT:([<>=]+)/);
    if (match && match[1]) {
      return match[1];
    }
  }
  
  return null;
}

// 格式化年度目标值
function formatAnnualTargetValue(row) {
  if (row.beizhu) {
    // 解析格式信息
    const formatInfo = parseFormatInfo(row.beizhu);
    if (formatInfo && formatInfo.ANNUAL_TARGET) {
      return `${formatInfo.ANNUAL_TARGET}${row.annualTargetValue}`;
    }
    
    // 兼容旧格式
    if (row.beizhu.includes('>=')) {
      return `>=${row.annualTargetValue}`;
    } else if (row.beizhu.includes('>')) {
      return `>${row.annualTargetValue}`;
    } else if (row.beizhu.includes('<=')) {
      return `<=${row.annualTargetValue}`;
    } else if (row.beizhu.includes('<')) {
      return `<${row.annualTargetValue}`;
    } else if (row.beizhu.includes('=')) {
      return `=${row.annualTargetValue}`;
    }
  }
  return row.annualTargetValue;
}

// 格式化一季度目标值
function formatQ1TargetValue(row) {
  if (row.beizhu) {
    const formatInfo = parseFormatInfo(row.beizhu);
    if (formatInfo && formatInfo.Q1_TARGET) {
      return `${formatInfo.Q1_TARGET}${row.q1TargetValue}`;
    }
  }
  return row.q1TargetValue;
}

// 格式化二季度目标值
function formatQ2TargetValue(row) {
  if (row.beizhu) {
    const formatInfo = parseFormatInfo(row.beizhu);
    if (formatInfo && formatInfo.Q2_TARGET) {
      return `${formatInfo.Q2_TARGET}${row.q2TargetValue}`;
    }
  }
  return row.q2TargetValue;
}

// 格式化三季度目标值
function formatQ3TargetValue(row) {
  if (row.beizhu) {
    const formatInfo = parseFormatInfo(row.beizhu);
    if (formatInfo && formatInfo.Q3_TARGET) {
      return `${formatInfo.Q3_TARGET}${row.q3TargetValue}`;
    }
  }
  return row.q3TargetValue;
}

// 格式化四季度目标值
function formatQ4TargetValue(row) {
  if (row.beizhu) {
    const formatInfo = parseFormatInfo(row.beizhu);
    if (formatInfo && formatInfo.Q4_TARGET) {
      return `${formatInfo.Q4_TARGET}${row.q4TargetValue}`;
    }
  }
  return row.q4TargetValue;
}

// 解析格式信息
function parseFormatInfo(beizhu) {
  if (!beizhu || !beizhu.includes('FORMAT_INFO:')) {
    return null;
  }
  
  const formatInfoMatch = beizhu.match(/FORMAT_INFO:([^；]+)/);
  if (!formatInfoMatch || !formatInfoMatch[1]) {
    return null;
  }
  const route = useRoute();
  const formatInfoStr = formatInfoMatch[1];
  const formatParts = formatInfoStr.split(';');
  const formatInfo = {};
  
  formatParts.forEach(part => {
    if (!part) return;
    const [key, value] = part.split(':');
    if (key && value) {
      formatInfo[key] = value;
    }
  });
  
  return formatInfo;
}

// 格式化备注显示
function formatBeizhu(beizhu) {
  if (!beizhu) {
    return "";
  }
  
  // 如果备注中包含格式信息标记
  if (beizhu.includes('FORMAT_INFO:') || beizhu.includes('TARGET_FORMAT:')) {
    // 检查是否只有格式信息没有实际备注
    if (!beizhu.includes('；')) {
      return ""; // 如果只有格式信息，返回空字符串
    }
    
    // 提取实际备注内容
    let realBeizhu = "";
    
    // 处理新格式
    if (beizhu.includes('FORMAT_INFO:')) {
      // 格式信息在备注前面
      if (beizhu.indexOf('FORMAT_INFO:') === 0) {
        const parts = beizhu.split('；');
        if (parts.length > 1) {
          parts.shift(); // 移除第一部分（格式信息）
          realBeizhu = parts.join('；'); // 剩余部分为实际备注
        }
      } 
      // 格式信息在备注后面
      else {
        realBeizhu = beizhu.substring(0, beizhu.indexOf('；FORMAT_INFO:'));
      }
    }
    // 处理旧格式
    else if (beizhu.includes('TARGET_FORMAT:')) {
      // 格式信息在备注前面
      if (beizhu.indexOf('TARGET_FORMAT:') === 0) {
        const parts = beizhu.split('；');
        if (parts.length > 1) {
          parts.shift(); // 移除第一部分（格式信息）
          realBeizhu = parts.join('；'); // 剩余部分为实际备注
        }
      } 
      // 格式信息在备注后面
      else {
        realBeizhu = beizhu.substring(0, beizhu.indexOf('；TARGET_FORMAT:'));
      }
    }
    
    return realBeizhu;
  }
  
  // 如果没有格式信息，直接返回原备注
  return beizhu;
}

function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件KPI考核数据");
    // 添加这行确保数据加载
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

