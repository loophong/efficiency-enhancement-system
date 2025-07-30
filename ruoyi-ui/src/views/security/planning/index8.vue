<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="考生姓名" prop="examineeName">
        <el-input
          v-model="queryParams.examineeName"
          placeholder="请输入考生姓名"
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
      <!-- <el-form-item label="考试时间" prop="examinationDate">
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
      <!-- 导入按钮 -->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:knowledgeassessment:import']"
        >导入</el-button>
      </el-col>
      <!-- 下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:knowledgeassessment:export']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="knowledgeassessmentList"
      @selection-change="handleSelectionChange"
      class="knowledge-assessment-table"
      border
      stripe
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#303133',
        fontWeight: 'bold',
        textAlign: 'center',
        fontSize: '14px'
      }"
      :cell-style="{
        textAlign: 'center',
        padding: '12px 8px',
        fontSize: '13px'
      }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="考生姓名" align="center" prop="examineeName" min-width="120" show-overflow-tooltip />
      <el-table-column label="笔试成绩" align="center" prop="writtenScore" min-width="100" show-overflow-tooltip />
      <el-table-column label="实操成绩" align="center" prop="practicalScore" min-width="100" show-overflow-tooltip />
      <el-table-column label="其他" align="center" prop="other" min-width="120" show-overflow-tooltip />
      <el-table-column label="是否合格" align="center" prop="isQualified" min-width="100" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="remarks" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:knowledgeassessment:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:knowledgeassessment:remove']"
            size="small"
          >删除</el-button>
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
        <el-form-item label="考生姓名" prop="examineeName">
          <el-input v-model="form.examineeName" placeholder="请输入考生姓名" />
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
        <!-- <el-form-item label="考核内容">
          <el-input v-model="form.assessmentContent"  type="textarea" placeholder="请输入内容"/>
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
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 安全知识考核导入对话框 -->
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
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="handleDownloadTemplate">下载模板</el-link>
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

<script setup name="Knowledgeassessment">
import { listKnowledgeassessment, getKnowledgeassessment, delKnowledgeassessment, addKnowledgeassessment, updateKnowledgeassessment } from "@/api/security/knowledgeassessment";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance();
const route = useRoute();
const knowledgeassessmentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/knowledgeassessment/importData"
});
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
        if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
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

/** 导入按钮操作 */
function handleImport() {
  upload.title = "安全知识考核导入";
  upload.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/knowledgeassessment/importTemplate', {}, `安全知识考核模板_${new Date().getTime()}.xlsx`);
}

/** 文件上传中处理 */
function handleFileUploadProgress(event, file, fileList) {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response, file, fileList) {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}
function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
  }
}

onMounted(() => {
  // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
});
</script>
