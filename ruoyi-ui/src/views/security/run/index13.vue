<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="级别" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入级别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室/班组" prop="departmentTeam">
        <el-input
          v-model="queryParams.departmentTeam"
          placeholder="请输入科室/班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预计演练时间" prop="plannedDrillTime">
        <el-date-picker clearable
          v-model="queryParams.plannedDrillTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择预计演练时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="演练主题" prop="drillTheme">
        <el-input
          v-model="queryParams.drillTheme"
          placeholder="请输入演练主题"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="drillCategory">
        <el-input
          v-model="queryParams.drillCategory"
          placeholder="请输入类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['security:emergencydrillplan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:emergencydrillplan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:emergencydrillplan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:emergencydrillplan:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:emergencydrillplan:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="emergencydrillplanList"
      @selection-change="handleSelectionChange"
      :span-method="arraySpanMethod"
      border>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="级别" align="center" prop="level" />
      <el-table-column label="科室/班组" align="center" prop="departmentTeam" />
      <el-table-column label="科室" align="center" prop="drillType" />
      <el-table-column label="预计演练时间" align="center" prop="plannedDrillTime" width="180" />
      <el-table-column label="演练主题" align="center" prop="drillTheme" />
      <el-table-column label="参考情景" align="center" prop="referenceScenario" />
      <el-table-column label="参演及观摩人员" align="center" prop="participantsObservers" />
      <el-table-column label="类型" align="center" prop="drillCategory" />
      <!-- <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:emergencydrillplan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:emergencydrillplan:remove']">删除</el-button>
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

    <!-- 添加或修改应急演练计划对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="emergencydrillplanRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="级别" prop="level">
          <el-input v-model="form.level" placeholder="请输入级别" />
        </el-form-item>
        <el-form-item label="科室/班组" prop="departmentTeam">
          <el-input v-model="form.departmentTeam" placeholder="请输入科室/班组" />
        </el-form-item>
        <el-form-item label="预计演练时间" prop="plannedDrillTime">
          <el-date-picker clearable
            v-model="form.plannedDrillTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择预计演练时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="演练主题" prop="drillTheme">
          <el-input v-model="form.drillTheme" placeholder="请输入演练主题" />
        </el-form-item>
        <el-form-item label="参考情景" prop="referenceScenario">
          <el-input v-model="form.referenceScenario" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="参演及观摩人员" prop="participantsObservers">
          <el-input v-model="form.participantsObservers" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="类型" prop="drillCategory">
          <el-input v-model="form.drillCategory" placeholder="请输入类型" />
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

    <!-- 应急演练计划导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据（基于级别、科室/班组、演练主题匹配）
            </div>
            <span>仅允许导入xls、xlsx格式文件。支持重复数据导入。</span>
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

<script setup name="Emergencydrillplan">
import { listEmergencydrillplan, getEmergencydrillplan, delEmergencydrillplan, addEmergencydrillplan, updateEmergencydrillplan } from "@/api/security/emergencydrillplan";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const emergencydrillplanList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const route = useRoute();
// 合并单元格相关数据
const levelSpanArr = ref([]);
const departmentSpanArr = ref([]);
const typeSpanArr = ref([]);



/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/emergencydrillplan/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    level: null,
    departmentTeam: null,
    drillType: null,
    plannedDrillTime: null,
    drillTheme: null,
    referenceScenario: null,
    participantsObservers: null,
    drillCategory: null,
    statu: null
  },
  rules: {
    level: [
      { required: true, message: "级别不能为空", trigger: "blur" }
    ],
    departmentTeam: [
      { required: true, message: "科室/班组不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询应急演练计划列表 */
function getList() {
  loading.value = true;
  listEmergencydrillplan(queryParams.value).then(response => {
    // 按照id主键顺序排序（升序）
    let sortedData = response.rows.sort((a, b) => {
      return (a.id || 0) - (b.id || 0);
    });

    emergencydrillplanList.value = sortedData;
    total.value = response.total;
    loading.value = false;
    // 计算合并单元格
    getSpanArr(emergencydrillplanList.value);
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
    level: null,
    departmentTeam: null,
    drillType: null,
    plannedDrillTime: null,
    drillTheme: null,
    referenceScenario: null,
    participantsObservers: null,
    drillCategory: null,
    statu: null
  };
  proxy.resetForm("emergencydrillplanRef");
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
  title.value = "添加应急演练计划";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getEmergencydrillplan(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改应急演练计划";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["emergencydrillplanRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateEmergencydrillplan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEmergencydrillplan(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除应急演练计划编号为"' + _ids + '"的数据项？').then(function() {
    return delEmergencydrillplan(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/emergencydrillplan/export', {
    ...queryParams.value
  }, `emergencydrillplan_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "应急演练计划导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/emergencydrillplan/importTemplate', {}, `应急演练计划导入模板_${new Date().getTime()}.xlsx`);
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

/** 计算合并单元格 */
function getSpanArr(data) {
  levelSpanArr.value = calculateSpanArray(data, 'level');
  departmentSpanArr.value = calculateSpanArray(data, 'departmentTeam', 'level');
  typeSpanArr.value = calculateSpanArray(data, 'drillType', 'level', 'departmentTeam');
}

/** 计算指定字段的合并数组 */
function calculateSpanArray(data, field, ...dependentFields) {
  const spanArray = [];
  let pos = 0;

  for (let i = 0; i < data.length; i++) {
    if (i === 0) {
      spanArray.push(1);
      pos = 0;
    } else {
      // 检查当前字段值是否与上一行相同
      const currentValue = data[i][field];
      const prevValue = data[i - 1][field];
      let isSame = currentValue === prevValue && currentValue != null && currentValue !== '';

      // 如果有依赖字段，需要依赖字段也相同
      if (isSame && dependentFields.length > 0) {
        for (let depField of dependentFields) {
          const currentDepValue = data[i][depField];
          const prevDepValue = data[i - 1][depField];
          if (currentDepValue !== prevDepValue) {
            isSame = false;
            break;
          }
        }
      }

      if (isSame) {
        spanArray[pos] += 1;
        spanArray.push(0);
      } else {
        spanArray.push(1);
        pos = i;
      }
    }
  }

  return spanArray;
}

/** 合并单元格方法 */
function arraySpanMethod({ row, column, rowIndex, columnIndex }) {
  let spanArray = null;

  // 级别列(2) - 第3列
  if (columnIndex === 2) {
    spanArray = levelSpanArr.value;
  }
  // 科室/班组列(3) - 第4列
  else if (columnIndex === 3) {
    spanArray = departmentSpanArr.value;
  }
  // 类型列(4) - 第5列
  else if (columnIndex === 4) {
    spanArray = typeSpanArr.value;
  }

  if (spanArray && spanArray.length > rowIndex) {
    const _row = spanArray[rowIndex];
    const _col = _row > 0 ? 1 : 0;

    return {
      rowspan: _row,
      colspan: _col
    };
  }
}

function checkRelatedId() {
  // 从路由参数中获取关联ID
  const relatedId = route.query.relatedId;

  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    // 将关联ID设置到查询参数中
    queryParams.value.relatedId = relatedId;
    // 调用getList()执行实际的数据查询
    getList();
    // 显示提示信息
    proxy.$modal.msgSuccess("已加载关联的应急演练计划信息数据");
  } else {
    // 没有关联ID时，加载所有数据
    getList();
  }
}

onMounted(() => {
  // 检查关联ID参数并加载相应数据
  checkRelatedId();
});
</script>

<style scoped>
/* 表格合并单元格样式优化 */
:deep(.el-table .el-table__cell) {
  padding: 8px 0;
}

:deep(.el-table .el-table__row) {
  border-bottom: 1px solid #ebeef5;
}

/* 合并单元格的边框处理 */
:deep(.el-table td.el-table__cell) {
  border-right: 1px solid #ebeef5;
}

/* 让合并的单元格内容居中显示 */
:deep(.el-table .cell) {
  text-align: center;
  word-break: break-all;
}
</style>
