<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="事故名称" prop="accidentName">
        <el-input
          v-model="queryParams.accidentName"
          placeholder="请输入事故名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事故时间" prop="accidentTime">
        <el-date-picker clearable
          v-model="queryParams.accidentTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择事故时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事故调查人" prop="investigatorSignature">
        <el-input
          v-model="queryParams.investigatorSignature"
          placeholder="请输入事故调查人"
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
          v-hasPermi="['security:AccidentReport:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:AccidentReport:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:AccidentReport:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:AccidentReport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AccidentReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="事故名称" align="center" prop="accidentName" />
      <el-table-column label="事故时间" align="center" prop="accidentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.accidentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="伤亡情况" align="center" prop="casualties" />
      <el-table-column label="事故损失" align="center" prop="accidentLoss" />
      <el-table-column label="事故经过" align="center" prop="accidentDescription" />
      <el-table-column label="事故现场环境" align="center" prop="accidentSceneEnvironment" />
      <el-table-column label="事故原因" align="center" prop="accidentCause" />
      <el-table-column label="事故责任" align="center" prop="accidentResponsibility" />
      <el-table-column label="事故处理" align="center" prop="accidentHandling" />
      <el-table-column label="事故防范措施" align="center" prop="accidentPreventiveMeasures" />
      <el-table-column label="事故调查人" align="center" prop="investigatorSignature" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:AccidentReport:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:AccidentReport:remove']">删除</el-button>
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

    <!-- 添加或修改事故处理报告对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="AccidentReportRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事故名称" prop="accidentName">
          <el-input v-model="form.accidentName" placeholder="请输入事故名称" />
        </el-form-item>
        <el-form-item label="事故时间" prop="accidentTime">
          <el-date-picker clearable
            v-model="form.accidentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择事故时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="伤亡情况" prop="casualties">
          <el-input v-model="form.casualties" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故损失" prop="accidentLoss">
          <el-input v-model="form.accidentLoss" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故经过">
          <editor v-model="form.accidentDescription" :min-height="192"/>
        </el-form-item>
        <el-form-item label="事故现场环境">
          <editor v-model="form.accidentSceneEnvironment" :min-height="192"/>
        </el-form-item>
        <el-form-item label="事故原因" prop="accidentCause">
          <el-input v-model="form.accidentCause" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故责任" prop="accidentResponsibility">
          <el-input v-model="form.accidentResponsibility" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故处理" prop="accidentHandling">
          <el-input v-model="form.accidentHandling" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故防范措施">
          <editor v-model="form.accidentPreventiveMeasures" :min-height="192"/>
        </el-form-item>
        <el-form-item label="事故调查人" prop="investigatorSignature">
          <el-input v-model="form.investigatorSignature" placeholder="请输入事故调查人" />
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

<script setup name="AccidentReport">
import { listAccidentReport, getAccidentReport, delAccidentReport, addAccidentReport, updateAccidentReport } from "@/api/security/AccidentReport";

const { proxy } = getCurrentInstance();

const AccidentReportList = ref([]);
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
    accidentName: null,
    accidentTime: null,
    investigatorSignature: null
  },
  rules: {
    accidentName: [
      { required: true, message: "事故名称不能为空", trigger: "blur" }
    ],
    accidentTime: [
      { required: true, message: "事故时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询事故处理报告列表 */
function getList() {
  loading.value = true;
  listAccidentReport(queryParams.value).then(response => {
    AccidentReportList.value = response.rows;
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
    accidentName: null,
    accidentTime: null,
    casualties: null,
    accidentLoss: null,
    accidentDescription: null,
    accidentSceneEnvironment: null,
    accidentCause: null,
    accidentResponsibility: null,
    accidentHandling: null,
    accidentPreventiveMeasures: null,
    investigatorSignature: null
  };
  proxy.resetForm("AccidentReportRef");
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
  title.value = "添加事故处理报告";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAccidentReport(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改事故处理报告";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["AccidentReportRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAccidentReport(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAccidentReport(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除事故处理报告编号为"' + _ids + '"的数据项？').then(function() {
    return delAccidentReport(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/AccidentReport/export', {
    ...queryParams.value
  }, `AccidentReport_${new Date().getTime()}.xlsx`)
}

getList();
</script>
