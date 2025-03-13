<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="类别" prop="category">
        <el-input
          v-model="queryParams.category"
          placeholder="请输入类别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目" prop="project">
        <el-input
          v-model="queryParams.project"
          placeholder="请输入项目"
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
          v-hasPermi="['security:AccidentCauseAnalysis:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:AccidentCauseAnalysis:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:AccidentCauseAnalysis:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:AccidentCauseAnalysis:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AccidentCauseAnalysisList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="类别" align="center" prop="category" />
      <el-table-column label="项目" align="center" prop="project" />
      <el-table-column label="具体内容" align="center" prop="specificContent" />
      <el-table-column label="分析结果" align="center" prop="analysisResult" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:AccidentCauseAnalysis:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:AccidentCauseAnalysis:remove']">删除</el-button>
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

    <!-- 添加或修改事故原因分析记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="AccidentCauseAnalysisRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="类别" prop="category">
          <el-input v-model="form.category" placeholder="请输入类别" />
        </el-form-item>
        <el-form-item label="项目" prop="project">
          <el-input v-model="form.project" placeholder="请输入项目" />
        </el-form-item>
        <el-form-item label="具体内容">
          <editor v-model="form.specificContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="分析结果" prop="analysisResult">
          <el-input v-model="form.analysisResult" type="textarea" placeholder="请输入内容" />
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

<script setup name="AccidentCauseAnalysis">
import { listAccidentCauseAnalysis, getAccidentCauseAnalysis, delAccidentCauseAnalysis, addAccidentCauseAnalysis, updateAccidentCauseAnalysis } from "@/api/security/AccidentCauseAnalysis";

const { proxy } = getCurrentInstance();

const AccidentCauseAnalysisList = ref([]);
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
    category: null,
    project: null,
  },
  rules: {
    category: [
      { required: true, message: "类别不能为空", trigger: "blur" }
    ],
    project: [
      { required: true, message: "项目不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询事故原因分析记录列表 */
function getList() {
  loading.value = true;
  listAccidentCauseAnalysis(queryParams.value).then(response => {
    AccidentCauseAnalysisList.value = response.rows;
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
    category: null,
    project: null,
    specificContent: null,
    analysisResult: null
  };
  proxy.resetForm("AccidentCauseAnalysisRef");
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
  title.value = "添加事故原因分析记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAccidentCauseAnalysis(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改事故原因分析记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["AccidentCauseAnalysisRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAccidentCauseAnalysis(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAccidentCauseAnalysis(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除事故原因分析记录编号为"' + _ids + '"的数据项？').then(function() {
    return delAccidentCauseAnalysis(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/AccidentCauseAnalysis/export', {
    ...queryParams.value
  }, `AccidentCauseAnalysis_${new Date().getTime()}.xlsx`)
}

getList();
</script>
