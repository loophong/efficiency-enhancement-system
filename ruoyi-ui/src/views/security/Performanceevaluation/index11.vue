<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="上传时间" prop="uploadDate">
        <el-date-picker clearable
                        v-model="queryParams.uploadDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上传人" prop="uploadBy">
        <el-input
            v-model="queryParams.uploadBy"
            placeholder="请输入上传人"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传部门" prop="uploadDept">
        <el-input
            v-model="queryParams.uploadDept"
            placeholder="请输入上传部门"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="描述" prop="comment">
        <el-input
            v-model="queryParams.comment"
            placeholder="请输入描述"
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
            v-hasPermi="['security:InternalAuditReport:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:InternalAuditReport:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:InternalAuditReport:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:InternalAuditReport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="InternalAuditReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100"/>
      <el-table-column label="上传时间" align="center" prop="uploadDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传人" align="center" prop="uploadBy" />
      <el-table-column label="上传部门" align="center" prop="uploadDept" />
      <el-table-column label="文件" align="center" prop="files" />
      <el-table-column label="描述" align="center" prop="comment" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:InternalAuditReport:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:InternalAuditReport:remove']">删除</el-button>
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

    <!-- 添加或修改内审报告对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="InternalAuditReportRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上传时间" prop="uploadDate">
          <el-date-picker clearable
                          v-model="form.uploadDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上传人" prop="uploadBy">
          <el-input v-model="form.uploadBy" placeholder="请输入上传人" />
        </el-form-item>
        <el-form-item label="上传部门" prop="uploadDept">
          <el-input v-model="form.uploadDept" placeholder="请输入上传部门" />
        </el-form-item>
        <el-form-item label="文件" prop="files">
          <file-upload v-model="form.files"/>
        </el-form-item>
        <el-form-item label="描述" prop="comment">
          <el-input v-model="form.comment" placeholder="请输入描述" />
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

<script setup name="InternalAuditReport">
import { listInternalAuditReport, getInternalAuditReport, delInternalAuditReport, addInternalAuditReport, updateInternalAuditReport } from "@/api/security/InternalAuditReport";

const { proxy } = getCurrentInstance();

const InternalAuditReportList = ref([]);
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
    uploadDate: null,
    uploadBy: null,
    uploadDept: null,
    files: null,
    comment: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询内审报告列表 */
function getList() {
  loading.value = true;
  listInternalAuditReport(queryParams.value).then(response => {
    InternalAuditReportList.value = response.rows;
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
    uploadDate: null,
    uploadBy: null,
    uploadDept: null,
    files: null,
    comment: null
  };
  proxy.resetForm("InternalAuditReportRef");
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
  title.value = "添加内审报告";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getInternalAuditReport(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改内审报告";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["InternalAuditReportRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInternalAuditReport(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addInternalAuditReport(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除内审报告编号为"' + _ids + '"的数据项？').then(function() {
    return delInternalAuditReport(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/InternalAuditReport/export', {
    ...queryParams.value
  }, `InternalAuditReport_${new Date().getTime()}.xlsx`)
}

getList();
</script>
