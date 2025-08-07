<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动/过程" prop="activity">
        <el-input
            v-model="queryParams.activity"
            placeholder="请输入活动/过程"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="风险等级" prop="riskLevel">
        <el-input
            v-model="queryParams.riskLevel"
            placeholder="请输入风险等级"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实施时间" prop="implementationTime">
        <el-date-picker clearable
                        v-model="queryParams.implementationTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择实施时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实施部门" prop="department">
        <el-input
            v-model="queryParams.department"
            placeholder="请输入实施部门"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="严重程度" prop="severity">
        <el-input
            v-model="queryParams.severity"
            placeholder="请输入严重程度"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生频次" prop="frequency">
        <el-input
            v-model="queryParams.frequency"
            placeholder="请输入发生频次"
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
            v-hasPermi="['security:assessment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:assessment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:assessment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:assessment:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Download"
          @click="handleImportTemplate"
          v-hasPermi="['security:assessment:list']"
        >模板下载</el-button>
      </el-col>
            <!-- 添加返回上一级按钮 -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Back"
          @click="handleGoBack"
        >返回</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assessmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100" />
      <el-table-column label="活动/过程" align="center" prop="activity" />
      <el-table-column label="风险和机遇" align="center" prop="riskOpportunity" />
      <el-table-column label="造成后果" align="center" prop="consequences" />
      <el-table-column label="风险等级" align="center" prop="riskLevel">
        <template #default="scope">
          <div v-if="scope.row.riskLevel === '高'">
            <el-tag type="danger">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else-if="scope.row.riskLevel === '一般'">
            <el-tag type="warning">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else-if="scope.row.riskLevel === '低'">
            <el-tag type="success">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else>
            {{ scope.row.riskLevel }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="应对措施" align="center" prop="responseMeasures" />
      <el-table-column label="实施时间" align="center" prop="implementationTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.implementationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实施部门" align="center" prop="department" />
      <el-table-column label="评价" align="center">
        <el-table-column label="严重程度" align="center" prop="severity" />
        <el-table-column label="发生频次" align="center" prop="frequency" />
        <el-table-column label="风险系数" align="center" prop="risk" />
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:assessment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:assessment:remove']">删除</el-button>
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

    <!-- 添加或修改风险和机遇评估及控制措施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="assessmentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动/过程" prop="activity">
          <el-input v-model="form.activity" placeholder="请输入活动/过程" />
        </el-form-item>
        <el-form-item label="风险和机遇" prop="riskOpportunity">
          <el-input v-model="form.riskOpportunity" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="造成后果" prop="consequences">
          <el-input v-model="form.consequences" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="风险等级" prop="riskLevel">
          <el-select v-model="form.riskLevel" placeholder="请选择风险等级">
            <el-option label="高" value="高"></el-option>
            <el-option label="一般" value="一般"></el-option>
            <el-option label="低" value="低"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="应对措施" prop="responseMeasures">
          <el-input v-model="form.responseMeasures" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="实施时间" prop="implementationTime">
          <el-date-picker clearable
                          v-model="form.implementationTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择实施时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实施部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入实施部门" />
        </el-form-item>
        <el-form-item label="严重程度" prop="severity">
          <el-input v-model="form.severity" placeholder="请输入严重程度" />
        </el-form-item>
        <el-form-item label="发生频次" prop="frequency">
          <el-input v-model="form.frequency" placeholder="请输入发生频次" />
        </el-form-item>
        <el-form-item label="风险系数" prop="risk">
          <el-input v-model="form.risk" placeholder="请输入风险系数" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    
    <!-- 导入对话框 -->
    <el-dialog title="导入风险和机遇评估数据" v-model="uploadDialogVisible" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        name="excelFile"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip">请上传 .xlsx 或 .xls 格式文件</div>
        </template>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm" :loading="upload.isUploading">确 定</el-button>
        <el-button @click="uploadDialogVisible = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script setup name="Assessment">
import {listAssessment, getAssessment, delAssessment, addAssessment, updateAssessment, importFile, getImportTemplate} from "@/api/security/assessment";
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';
import { Check, UploadFilled } from '@element-plus/icons-vue';
import { getToken } from "@/utils/auth";

const {proxy} = getCurrentInstance();
const {security_status} = proxy.useDict('security_status');

const assessmentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const uploadDialogVisible = ref(false);

// 上传参数
const upload = reactive({
  // 是否禁用上传
  isUploading: false,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/assessment/import"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    activity: null,
    riskOpportunity: null,
    consequences: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null,
    severity: null,
    frequency: null
  },
  rules: {
    activity: [
      {required: true, message: "活动/过程不能为空", trigger: "blur"}
    ],
    riskOpportunity: [
      {required: true, message: "风险和机遇不能为空", trigger: "blur"}
    ],
  }
});
const route = useRoute();
const {queryParams, form, rules} = toRefs(data);
// 返回上一级页面
function handleGoBack() {
  proxy.$router.go(-1);
}
/** 查询风险和机遇评估及控制措施列表 */
function getList() {
  loading.value = true;
  listAssessment(queryParams.value).then(response => {
    assessmentList.value = response.rows;
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
    riskOpportunity: null,
    consequences: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null,
    severity: null,
    frequency: null
  };
  proxy.resetForm("assessmentRef");
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
  title.value = "添加风险和机遇评估及控制措施";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAssessment(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改风险和机遇评估及控制措施";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["assessmentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAssessment(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAssessment(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除风险和机遇评估及控制措施编号为"' + _ids + '"的数据项？').then(function () {
    return delAssessment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/assessment/export', {
    ...queryParams.value
  }, `assessment_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  uploadDialogVisible.value = true;
}

/** 下载模板操作 */
async function handleImportTemplate() {
  try {
    const response = await getImportTemplate();
    const blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `风险和机遇评估模板_${new Date().getTime()}.xlsx`;
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
    proxy.$modal.msgSuccess("模板下载成功");
  } catch (error) {
    console.error('模板下载失败:', error);
    proxy.$modal.msgError("模板下载失败，请稍后重试");
  }
}

// 文件上传中处理
function handleFileUploadProgress() {
  upload.isUploading = true;
}

// 文件上传成功处理
function handleFileSuccess(response) {
  upload.isUploading = false;
  uploadDialogVisible.value = false;
  proxy.$refs.uploadRef.clearFiles();
  proxy.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

// 文件上传失败处理
function handleFileError(error) {
  upload.isUploading = false;
  proxy.$modal.msgError("导入失败，请检查数据格式是否正确");
}

// 提交上传文件
function submitFileForm() {
  proxy.$refs.uploadRef.submit();
}

function checkRelatedId() {
  // 从路由参数中获取关联ID
  const relatedId = route.query.relatedId;

  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    // 将关联ID设置到查询参数中
    queryParams.value.relatedId = relatedId;
    // 显示提示信息
    proxy.$modal.msgSuccess("已加风险和机遇评估及控制措施表");
  }
}

// 初始化函数
onMounted(() => {
  // 检查URL参数中是否有关联ID
  checkRelatedId();
  // 加载数据
  getList();
});

</script>
