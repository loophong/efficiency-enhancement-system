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
      <el-form-item label="汇编人" prop="compiler">
        <el-input
          v-model="queryParams.compiler"
          placeholder="请输入汇编人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核人" prop="auditor">
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
      </el-form-item>
      <el-form-item label="日期" prop="date">
        <el-date-picker clearable
          v-model="queryParams.date"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable>
          <el-option
            v-for="dict in security_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="HealthAndSafetyGoalsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="serialNumber" />
      <el-table-column label="部门" align="center" prop="departmentName" />
      <el-table-column label="目标项" align="center" prop="goalItem" />
      <el-table-column label="目标值" align="center" prop="goalValue" />
      <el-table-column label="考核结果" align="center" prop="evaluationResult" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="汇编人" align="center" prop="compiler" />
      <el-table-column label="审核人" align="center" prop="auditor" />
      <el-table-column label="批准人" align="center" prop="approver" />
      <el-table-column label="日期" align="center" prop="date" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.date, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:HealthAndSafetyGoals:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:HealthAndSafetyGoals:remove']">删除</el-button>
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
        <el-form-item label="汇编人" prop="compiler">
          <el-input v-model="form.compiler" placeholder="请输入汇编人" />
        </el-form-item>
        <el-form-item label="审核人" prop="auditor">
          <el-input v-model="form.auditor" placeholder="请输入审核人" />
        </el-form-item>
        <el-form-item label="批准人" prop="approver">
          <el-input v-model="form.approver" placeholder="请输入批准人" />
        </el-form-item>
        <el-form-item label="日期" prop="date">
          <el-date-picker clearable
            v-model="form.date"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择审核状态">
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
  </div>
</template>

<script setup name="HealthAndSafetyGoals">
import { listHealthAndSafetyGoals, getHealthAndSafetyGoals, delHealthAndSafetyGoals, addHealthAndSafetyGoals, updateHealthAndSafetyGoals } from "@/api/security/HealthAndSafetyGoals";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const HealthAndSafetyGoalsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

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
    status: null
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

/** 查询年度环境职业健康安全目标、指标及工作分解列表 */
function getList() {
  loading.value = true;
  listHealthAndSafetyGoals(queryParams.value).then(response => {
    HealthAndSafetyGoalsList.value = response.rows;
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
    status: null
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

getList();
</script>
