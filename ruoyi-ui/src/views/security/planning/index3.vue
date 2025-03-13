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
      <el-form-item label="评价方式(LEC)" prop="evaluationMethod">
        <el-input
          v-model="queryParams.evaluationMethod"
          placeholder="请输入评价方式(LEC)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="时间" prop="time">
        <el-date-picker clearable
          v-model="queryParams.time"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择时间">
        </el-date-picker>
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
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="risklistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="作业活动" align="center" prop="activity" />
      <el-table-column label="危险源" align="center" prop="hazardSource" />
      <el-table-column label="危害方式" align="center" prop="hazardMode" />
      <el-table-column label="可能导致的事故" align="center" prop="possibleConsequences" />
      <el-table-column label="评价方式(LEC)" align="center" prop="evaluationMethod" />
      <el-table-column label="L" align="center" prop="evaluationA" />
      <el-table-column label="E" align="center" prop="evaluationB" />
      <el-table-column label="C" align="center" prop="evaluationC" />
      <el-table-column label="三角形" align="center" prop="evaluationSanjiaoxing" />
      <el-table-column label="风险级别" align="center" prop="riskLevel" />
      <el-table-column label="控制措施" align="center" prop="controlMeasures" />
      <el-table-column label="审核人" align="center" prop="auditor" />
      <el-table-column label="批准人" align="center" prop="approver" />
      <el-table-column label="时间" align="center" prop="time" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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

<script setup name="Risklist">
import { listRisklist, getRisklist, delRisklist, addRisklist, updateRisklist } from "@/api/security/risklist";

const { proxy } = getCurrentInstance();

const risklistList = ref([]);
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
    activity: null,
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
    time: null
  },
  rules: {
    activity: [
      { required: true, message: "作业活动不能为空", trigger: "blur" }
    ],
    hazardSource: [
      { required: true, message: "危险源不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询危险源清单列表 */
function getList() {
  loading.value = true;
  listRisklist(queryParams.value).then(response => {
    risklistList.value = response.rows;
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
    activity: null,
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
    time: null
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

getList();
</script>
