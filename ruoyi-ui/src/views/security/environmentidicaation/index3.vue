<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评审文件名称" prop="reviewFileName">
        <el-input
            v-model="queryParams.reviewFileName"
            placeholder="请输入评审文件名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评审日期" prop="reviewDate">
        <el-date-picker clearable
                        v-model="queryParams.reviewDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择评审日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="评审人" prop="reviewer">
        <el-input
            v-model="queryParams.reviewer"
            placeholder="请输入评审人"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室1" prop="reviewDepartment1">
        <el-select v-model="queryParams.reviewDepartment1" placeholder="请选择科室1" clearable>
          <el-option
              v-for="dict in security_department_review"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="科室2" prop="reviewDepartment2">
        <el-select v-model="queryParams.reviewDepartment2" placeholder="请选择科室2" clearable>
          <el-option
              v-for="dict in security_department_review"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审批状态" clearable>
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
            v-hasPermi="['security:record:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:record:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:record:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="评审文件名称" align="center" prop="reviewFileName" />
      <el-table-column label="评审日期" align="center" prop="reviewDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评审主要内容" align="center" prop="reviewContent" />
      <el-table-column label="评审结论" align="center" prop="reviewConclusion" />
      <el-table-column label="评审人" align="center" prop="reviewer" />
      <el-table-column label="存在问题及解决措施" align="center" prop="issuesAndSolutions" />
      <el-table-column label="科室1" align="center" prop="reviewDepartment1">
        <template #default="scope">
          <dict-tag :options="security_department_review" :value="scope.row.reviewDepartment1"/>
        </template>
      </el-table-column>
      <el-table-column label="日期1" align="center" prop="reviewDepartmentTime1" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewDepartmentTime1, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="存在问题处理结果" align="center" prop="issueResolutionResults" />
      <el-table-column label="科室2" align="center" prop="reviewDepartment2">
        <template #default="scope">
          <dict-tag :options="security_department_review" :value="scope.row.reviewDepartment2"/>
        </template>
      </el-table-column>
      <el-table-column label="日期2" align="center" prop="reviewDepartmentTime2" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewDepartmentTime2, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="审批时间" align="center" prop="statusTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.statusTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:record:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:record:remove']">删除</el-button>
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

    <!-- 添加或修改记录表对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="recordRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评审文件名称" prop="reviewFileName">
          <el-input v-model="form.reviewFileName" placeholder="请输入评审文件名称" />
        </el-form-item>
        <el-form-item label="评审日期" prop="reviewDate">
          <el-date-picker clearable
                          v-model="form.reviewDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择评审日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评审主要内容" prop="reviewContent">
          <el-input v-model="form.reviewContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="评审结论" prop="reviewConclusion">
          <el-input v-model="form.reviewConclusion" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="评审人" prop="reviewer">
          <el-input v-model="form.reviewer" placeholder="请输入评审人" />
        </el-form-item>
        <el-form-item label="存在问题及解决措施">
          <editor v-model="form.issuesAndSolutions" :min-height="192"/>
        </el-form-item>
        <el-form-item label="科室1" prop="reviewDepartment1">
          <el-select v-model="form.reviewDepartment1" placeholder="请选择科室1">
            <el-option
                v-for="dict in security_department_review"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期1" prop="reviewDepartmentTime1">
          <el-date-picker clearable
                          v-model="form.reviewDepartmentTime1"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择日期1">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="存在问题处理结果">
          <editor v-model="form.issueResolutionResults" :min-height="192"/>
        </el-form-item>
        <el-form-item label="科室2" prop="reviewDepartment2">
          <el-select v-model="form.reviewDepartment2" placeholder="请选择科室2">
            <el-option
                v-for="dict in security_department_review"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日期2" prop="reviewDepartmentTime2">
          <el-date-picker clearable
                          v-model="form.reviewDepartmentTime2"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择日期2">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审批状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择审批状态">
            <el-option
                v-for="dict in security_status"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批时间" prop="statusTime">
          <el-date-picker clearable
                          v-model="form.statusTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择审批时间">
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

<script setup name="Record">
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/security/record";

const { proxy } = getCurrentInstance();
const { security_department_review, security_status } = proxy.useDict('security_department_review', 'security_status');

const recordList = ref([]);
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
    reviewFileName: null,
    reviewDate: null,
    reviewContent: null,
    reviewer: null,
    reviewDepartment1: null,
    reviewDepartment2: null,
    status: null,
  },
  rules: {
    reviewFileName: [
      { required: true, message: "评审文件名称不能为空", trigger: "blur" }
    ],
    reviewDate: [
      { required: true, message: "评审日期不能为空", trigger: "blur" }
    ],
    reviewContent: [
      { required: true, message: "评审主要内容不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询记录表列表 */
function getList() {
  loading.value = true;
  listRecord(queryParams.value).then(response => {
    recordList.value = response.rows;
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
    reviewFileName: null,
    reviewDate: null,
    reviewContent: null,
    reviewConclusion: null,
    reviewer: null,
    issuesAndSolutions: null,
    reviewDepartment1: null,
    reviewDepartmentTime1: null,
    issueResolutionResults: null,
    reviewDepartment2: null,
    reviewDepartmentTime2: null,
    status: null,
    statusTime: null
  };
  proxy.resetForm("recordRef");
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
  title.value = "添加记录表";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRecord(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改记录表";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["recordRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRecord(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除记录表编号为"' + _ids + '"的数据项？').then(function() {
    return delRecord(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/record/export', {
    ...queryParams.value
  }, `record_${new Date().getTime()}.xlsx`)
}

getList();
</script>
