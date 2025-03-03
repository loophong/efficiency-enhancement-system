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
      <el-form-item label="单位" prop="unitOfMeasurement">
        <el-input
          v-model="queryParams.unitOfMeasurement"
          placeholder="请输入单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标值" prop="annualTargetValue">
        <el-input
          v-model="queryParams.annualTargetValue"
          placeholder="请输入目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核频次" prop="evaluationFrequency">
        <el-input
          v-model="queryParams.evaluationFrequency"
          placeholder="请输入考核频次"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入权重"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="一季度目标值" prop="q1TargetValue">
        <el-input
          v-model="queryParams.q1TargetValue"
          placeholder="请输入一季度目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一季度结果" prop="q1Result">
        <el-input
          v-model="queryParams.q1Result"
          placeholder="请输入一季度结果"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一季度核定值" prop="q1VerifiedValue">
        <el-input
          v-model="queryParams.q1VerifiedValue"
          placeholder="请输入一季度核定值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二季度目标值" prop="q2TargetValue">
        <el-input
          v-model="queryParams.q2TargetValue"
          placeholder="请输入二季度目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二季度结果" prop="q2Result">
        <el-input
          v-model="queryParams.q2Result"
          placeholder="请输入二季度结果"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="二季度核定值" prop="q2VerifiedValue">
        <el-input
          v-model="queryParams.q2VerifiedValue"
          placeholder="请输入二季度核定值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三季度目标值" prop="q3TargetValue">
        <el-input
          v-model="queryParams.q3TargetValue"
          placeholder="请输入三季度目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三季度结果" prop="q3Result">
        <el-input
          v-model="queryParams.q3Result"
          placeholder="请输入三季度结果"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三季度核定值" prop="q3VerifiedValue">
        <el-input
          v-model="queryParams.q3VerifiedValue"
          placeholder="请输入三季度核定值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="四季度目标值" prop="q4TargetValue">
        <el-input
          v-model="queryParams.q4TargetValue"
          placeholder="请输入四季度目标值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="四季度结果" prop="q4Result">
        <el-input
          v-model="queryParams.q4Result"
          placeholder="请输入四季度结果"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="四季度核定值" prop="q4VerifiedValue">
        <el-input
          v-model="queryParams.q4VerifiedValue"
          placeholder="请输入四季度核定值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="备注" prop="beizhu">
        <el-input
          v-model="queryParams.beizhu"
          placeholder="请输入备注"
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
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="kpiList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="分类" align="center" prop="category" />
      <el-table-column label="指标名称" align="center" prop="indicatorName" />
      <el-table-column label="单位" align="center" prop="unitOfMeasurement" />
      <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="目标值" align="center" prop="annualTargetValue" />
      <el-table-column label="考核频次" align="center" prop="evaluationFrequency" />
      <el-table-column label="权重" align="center" prop="weight" />
      <el-table-column label="一季度目标值" align="center" prop="q1TargetValue" />
      <el-table-column label="一季度结果" align="center" prop="q1Result" />
      <el-table-column label="一季度核定值" align="center" prop="q1VerifiedValue" />
      <el-table-column label="二季度目标值" align="center" prop="q2TargetValue" />
      <el-table-column label="二季度结果" align="center" prop="q2Result" />
      <el-table-column label="二季度核定值" align="center" prop="q2VerifiedValue" />
      <el-table-column label="三季度目标值" align="center" prop="q3TargetValue" />
      <el-table-column label="三季度结果" align="center" prop="q3Result" />
      <el-table-column label="三季度核定值" align="center" prop="q3VerifiedValue" />
      <el-table-column label="四季度目标值" align="center" prop="q4TargetValue" />
      <el-table-column label="四季度结果" align="center" prop="q4Result" />
      <el-table-column label="四季度核定值" align="center" prop="q4VerifiedValue" />
      <el-table-column label="备注" align="center" prop="beizhu" />
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
        <el-form-item label="目标值" prop="annualTargetValue">
          <el-input v-model="form.annualTargetValue" placeholder="请输入目标值" />
        </el-form-item>
        <el-form-item label="考核频次" prop="evaluationFrequency">
          <el-input v-model="form.evaluationFrequency" placeholder="请输入考核频次" />
        </el-form-item>
        <el-form-item label="权重" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入权重" />
        </el-form-item>
        <el-form-item label="一季度目标值" prop="q1TargetValue">
          <el-input v-model="form.q1TargetValue" placeholder="请输入一季度目标值" />
        </el-form-item>
        <el-form-item label="一季度结果" prop="q1Result">
          <el-input v-model="form.q1Result" placeholder="请输入一季度结果" />
        </el-form-item>
        <el-form-item label="一季度核定值" prop="q1VerifiedValue">
          <el-input v-model="form.q1VerifiedValue" placeholder="请输入一季度核定值" />
        </el-form-item>
        <el-form-item label="二季度目标值" prop="q2TargetValue">
          <el-input v-model="form.q2TargetValue" placeholder="请输入二季度目标值" />
        </el-form-item>
        <el-form-item label="二季度结果" prop="q2Result">
          <el-input v-model="form.q2Result" placeholder="请输入二季度结果" />
        </el-form-item>
        <el-form-item label="二季度核定值" prop="q2VerifiedValue">
          <el-input v-model="form.q2VerifiedValue" placeholder="请输入二季度核定值" />
        </el-form-item>
        <el-form-item label="三季度目标值" prop="q3TargetValue">
          <el-input v-model="form.q3TargetValue" placeholder="请输入三季度目标值" />
        </el-form-item>
        <el-form-item label="三季度结果" prop="q3Result">
          <el-input v-model="form.q3Result" placeholder="请输入三季度结果" />
        </el-form-item>
        <el-form-item label="三季度核定值" prop="q3VerifiedValue">
          <el-input v-model="form.q3VerifiedValue" placeholder="请输入三季度核定值" />
        </el-form-item>
        <el-form-item label="四季度目标值" prop="q4TargetValue">
          <el-input v-model="form.q4TargetValue" placeholder="请输入四季度目标值" />
        </el-form-item>
        <el-form-item label="四季度结果" prop="q4Result">
          <el-input v-model="form.q4Result" placeholder="请输入四季度结果" />
        </el-form-item>
        <el-form-item label="四季度核定值" prop="q4VerifiedValue">
          <el-input v-model="form.q4VerifiedValue" placeholder="请输入四季度核定值" />
        </el-form-item>
        <el-form-item label="备注" prop="beizhu">
          <el-input v-model="form.beizhu" placeholder="请输入备注" />
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

<script setup name="Kpi">
import { listKpi, getKpi, delKpi, addKpi, updateKpi } from "@/api/security/kpi";

const { proxy } = getCurrentInstance();

const kpiList = ref([]);
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
    unitName: [
      { required: true, message: "单位名称不能为空", trigger: "blur" }
    ],
    category: [
      { required: true, message: "分类不能为空", trigger: "blur" }
    ],
    indicatorName: [
      { required: true, message: "指标名称不能为空", trigger: "blur" }
    ],
    unitOfMeasurement: [
      { required: true, message: "单位不能为空", trigger: "blur" }
    ],
    year: [
      { required: true, message: "年份不能为空", trigger: "blur" }
    ],
    annualTargetValue: [
      { required: true, message: "目标值不能为空", trigger: "blur" }
    ],
    evaluationFrequency: [
      { required: true, message: "考核频次不能为空", trigger: "blur" }
    ],
    weight: [
      { required: true, message: "权重不能为空", trigger: "blur" }
    ],
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
  open.value = true;
  title.value = "添加公司KPI";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getKpi(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改公司KPI";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["kpiRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateKpi(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addKpi(form.value).then(response => {
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
  }, `kpi_${new Date().getTime()}.xlsx`)
}

getList();
</script>
