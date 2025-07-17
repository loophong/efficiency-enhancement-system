<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="培训项目1" prop="trainingProject1">
        <el-input
          v-model="queryParams.trainingProject1"
          placeholder="请输入培训项目1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训项目2" prop="trainingProject2">
        <el-input
          v-model="queryParams.trainingProject2"
          placeholder="请输入培训项目2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训对象" prop="trainees">
        <el-input
          v-model="queryParams.trainees"
          placeholder="请输入培训对象"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训方式" prop="trainingMethod">
        <el-input
          v-model="queryParams.trainingMethod"
          placeholder="请输入培训方式"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="课时" prop="hours">
        <el-input
          v-model="queryParams.hours"
          placeholder="请输入课时"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="培训月份" prop="trainingMonth">
        <el-input
          v-model="queryParams.trainingMonth"
          placeholder="请输入培训月份"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考核方式" prop="assessmentMethod">
        <el-input
          v-model="queryParams.assessmentMethod"
          placeholder="请输入考核方式"
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
          v-hasPermi="['security:trainingplan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:trainingplan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:trainingplan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:trainingplan:export']"
        >导出</el-button>
      </el-col>
      <!-- 添加导入按钮 -->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:trainingplan:import']"
        >导入</el-button>
      </el-col>
      <!-- 添加下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:trainingplan:export']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="trainingplanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="培训项目1" align="center" prop="trainingProject1" />
      <el-table-column label="培训项目2" align="center" prop="trainingProject2" />
      <el-table-column label="培训对象" align="center" prop="trainees" />
      <el-table-column label="培训类别/内容" align="center" prop="trainingCategoryContent" />
      <el-table-column label="培训方式" align="center" prop="trainingMethod" />
      <el-table-column label="课时" align="center" prop="hours" />
      <el-table-column label="培训月份" align="center" prop="trainingMonth" />
      <el-table-column label="考核方式" align="center" prop="assessmentMethod" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:trainingplan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:trainingplan:remove']">删除</el-button>
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

    <!-- 添加或修改年度培训计划对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="trainingplanRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="培训项目1" prop="trainingProject1">
          <el-input v-model="form.trainingProject1" placeholder="请输入培训项目1" />
        </el-form-item>
        <el-form-item label="培训项目2" prop="trainingProject2">
          <el-input v-model="form.trainingProject2" placeholder="请输入培训项目2" />
        </el-form-item>
        <el-form-item label="培训对象" prop="trainees">
          <el-input v-model="form.trainees" placeholder="请输入培训对象" />
        </el-form-item>
        <el-form-item label="培训类别/内容">
          <editor v-model="form.trainingCategoryContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="培训方式" prop="trainingMethod">
          <el-input v-model="form.trainingMethod" placeholder="请输入培训方式" />
        </el-form-item>
        <el-form-item label="课时" prop="hours">
          <el-input v-model="form.hours" placeholder="请输入课时" />
        </el-form-item>
        <el-form-item label="培训月份" prop="trainingMonth">
          <el-input v-model="form.trainingMonth" placeholder="请输入培训月份" />
        </el-form-item>
        <el-form-item label="考核方式" prop="assessmentMethod">
          <el-input v-model="form.assessmentMethod" placeholder="请输入考核方式" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup name="Trainingplan">
import { listTrainingplan, getTrainingplan, delTrainingplan, addTrainingplan, updateTrainingplan } from "@/api/security/trainingplan";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance();

const trainingplanList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 添加上传相关的数据对象
const upload = reactive({
  open: false,
  title: '',
  isUploading: false,
  updateSupport: false,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/security/trainingplan/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    trainingProject1: null,
    trainingProject2: null,
    trainees: null,
    trainingCategoryContent: null,
    trainingMethod: null,
    hours: null,
    trainingMonth: null,
    assessmentMethod: null,
    remarks: null
  },
  rules: {
    trainees: [
      { required: true, message: "培训对象不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询年度培训计划列表 */
function getList() {
  loading.value = true;
  listTrainingplan(queryParams.value).then(response => {
    trainingplanList.value = response.rows;
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
    trainingProject1: null,
    trainingProject2: null,
    trainees: null,
    trainingCategoryContent: null,
    trainingMethod: null,
    hours: null,
    trainingMonth: null,
    assessmentMethod: null,
    remarks: null
  };
  proxy.resetForm("trainingplanRef");
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
  title.value = "添加年度培训计划";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getTrainingplan(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改年度培训计划";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["trainingplanRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTrainingplan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTrainingplan(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除年度培训计划编号为"' + _ids + '"的数据项？').then(function() {
    return delTrainingplan(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/trainingplan/export', {
    ...queryParams.value
  }, `trainingplan_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "导入年度培训计划";
  upload.open = true;
  proxy.nextTick(() => {
    proxy.$refs.uploadRef.clearFiles();
  });
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/trainingplan/importTemplate', {}, `trainingplan_template_${new Date().getTime()}.xlsx`);
}

/** 文件上传中处理 */
function handleFileUploadProgress(event) {
  proxy.upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  proxy.upload.open = false;
  proxy.upload.isUploading = false;
  proxy.$modal.msgSuccess("导入成功");
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs.uploadRef.submit();
}

getList();
</script>
