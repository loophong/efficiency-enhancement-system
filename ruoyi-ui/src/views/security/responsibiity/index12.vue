<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考试名称" prop="examineeName">
        <el-input
          v-model="queryParams.examineeName"
          placeholder="请输入考试名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="笔试成绩" prop="writtenScore">
        <el-input
          v-model="queryParams.writtenScore"
          placeholder="请输入笔试成绩"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实操成绩" prop="practicalScore">
        <el-input
          v-model="queryParams.practicalScore"
          placeholder="请输入实操成绩"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试时间" prop="examinationDate">
        <el-date-picker clearable
          v-model="queryParams.examinationDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择考试时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="考核评价人" prop="assessor">
        <el-input
          v-model="queryParams.assessor"
          placeholder="请输入考核评价人"
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
          v-hasPermi="['security:knowledgeassessment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:knowledgeassessment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:knowledgeassessment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:knowledgeassessment:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="knowledgeassessmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="考试名称" align="center" prop="examineeName" />
      <el-table-column label="笔试成绩" align="center" prop="writtenScore" />
      <el-table-column label="实操成绩" align="center" prop="practicalScore" />
      <el-table-column label="其他" align="center" prop="other" />
      <el-table-column label="是否合格" align="center" prop="isQualified" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="考核内容" align="center" prop="assessmentContent" />
      <el-table-column label="考试时间" align="center" prop="examinationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.examinationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="考察形式" align="center" prop="assessmentForm" />
      <el-table-column label="考核评价人" align="center" prop="assessor" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:knowledgeassessment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:knowledgeassessment:remove']">删除</el-button>
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

    <!-- 添加或修改安全知识考核对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="knowledgeassessmentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="考试名称" prop="examineeName">
          <el-input v-model="form.examineeName" placeholder="请输入考试名称" />
        </el-form-item>
        <el-form-item label="笔试成绩" prop="writtenScore">
          <el-input v-model="form.writtenScore" placeholder="请输入笔试成绩" />
        </el-form-item>
        <el-form-item label="实操成绩" prop="practicalScore">
          <el-input v-model="form.practicalScore" placeholder="请输入实操成绩" />
        </el-form-item>
        <el-form-item label="其他" prop="other">
          <el-input v-model="form.other" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="考核内容">
          <editor v-model="form.assessmentContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="考试时间" prop="examinationDate">
          <el-date-picker clearable
            v-model="form.examinationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择考试时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考核评价人" prop="assessor">
          <el-input v-model="form.assessor" placeholder="请输入考核评价人" />
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

<script setup name="Knowledgeassessment">
import { listKnowledgeassessment, getKnowledgeassessment, delKnowledgeassessment, addKnowledgeassessment, updateKnowledgeassessment } from "@/api/security/knowledgeassessment";

const { proxy } = getCurrentInstance();

const knowledgeassessmentList = ref([]);
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
    examineeName: null,
    writtenScore: null,
    practicalScore: null,
    other: null,
    isQualified: null,
    remarks: null,
    assessmentContent: null,
    examinationDate: null,
    assessmentForm: null,
    assessor: null
  },
  rules: {
    examineeName: [
      { required: true, message: "考试名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全知识考核列表 */
function getList() {
  loading.value = true;
  listKnowledgeassessment(queryParams.value).then(response => {
    knowledgeassessmentList.value = response.rows;
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
    examineeName: null,
    writtenScore: null,
    practicalScore: null,
    other: null,
    isQualified: null,
    remarks: null,
    assessmentContent: null,
    examinationDate: null,
    assessmentForm: null,
    assessor: null
  };
  proxy.resetForm("knowledgeassessmentRef");
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
  title.value = "添加安全知识考核";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getKnowledgeassessment(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全知识考核";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["knowledgeassessmentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateKnowledgeassessment(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addKnowledgeassessment(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全知识考核编号为"' + _ids + '"的数据项？').then(function() {
    return delKnowledgeassessment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/knowledgeassessment/export', {
    ...queryParams.value
  }, `knowledgeassessment_${new Date().getTime()}.xlsx`)
}

getList();
</script>
