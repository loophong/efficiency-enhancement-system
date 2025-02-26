<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动" prop="activity">
        <el-input v-model="queryParams.activity" placeholder="请输入活动" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="风险等级" prop="riskLevel">
        <el-input v-model="queryParams.riskLevel" placeholder="请输入风险等级" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="实施时间" prop="implementationTime">
        <el-date-picker clearable v-model="queryParams.implementationTime" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择实施时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="部门" prop="department">
        <el-input v-model="queryParams.department" placeholder="请输入部门" clearable @keyup.enter="handleQuery" />
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['security:assessment:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['security:assessment:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['security:assessment:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['security:assessment:export']">导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="info" plain icon="Upload" @click="handleImport" 
          v-hasPermi="['security:assessment:import']">导入</el-button>
        </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assessmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="活动" align="center" prop="activity" />
      <el-table-column label="风险和机遇" align="center" prop="riskOpportunity" />
      <el-table-column label="造成后果" align="center" prop="consequences" />
      <el-table-column label="评价" align="center" prop="evaluation" />
      <el-table-column label="风险等级" align="center" prop="riskLevel" />
      <el-table-column label="应对措施" align="center" prop="responseMeasures" />
      <el-table-column label="实施时间" align="center" prop="implementationTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.implementationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="部门" align="center" prop="department" />
      <el-table-column label="审批状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['security:assessment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['security:assessment:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
      @pagination="getList" />

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload ref="uploadRef" :limit="1" accept=".xlsx, .xls" :headers="upload.headers" :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading" :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的用户数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size: 12px; vertical-align: baseline" @click="importTemplate">下载模板</el-link>
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


    <!-- 添加或修改风险和机遇评估及控制措施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="assessmentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动" prop="activity">
          <el-input v-model="form.activity" placeholder="请输入活动" />
        </el-form-item>
        <el-form-item label="风险和机遇" prop="riskOpportunity">
          <el-input v-model="form.riskOpportunity" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="造成后果" prop="consequences">
          <el-input v-model="form.consequences" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="评价" prop="evaluation">
          <el-input v-model="form.evaluation" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="风险等级" prop="riskLevel">
          <el-input v-model="form.riskLevel" placeholder="请输入风险等级" />
        </el-form-item>
        <el-form-item label="应对措施" prop="responseMeasures">
          <el-input v-model="form.responseMeasures" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="实施时间" prop="implementationTime">
          <el-date-picker clearable v-model="form.implementationTime" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择实施时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入部门" />
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

<script setup name="Assessment">
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { listAssessment, getAssessment, delAssessment, addAssessment, updateAssessment } from "@/api/security/assessment";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const assessmentList = ref([]);
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
    riskOpportunity: null,
    consequences: null,
    evaluation: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null
  },
  rules: {
    activity: [
      { required: true, message: "活动不能为空", trigger: "blur" }
    ],
    riskOpportunity: [
      { required: true, message: "风险和机遇不能为空", trigger: "blur" }
    ],
  }
});

// 用户导入参数
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/assessment/importData"
});


const { queryParams, form, rules } = toRefs(data);

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
    evaluation: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null
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
  proxy.$modal.confirm('是否确认删除风险和机遇评估及控制措施编号为"' + _ids + '"的数据项？').then(function() {
    return delAssessment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/assessment/export', {
    ...queryParams.value
  }, `assessment_${new Date().getTime()}.xlsx`)
}


/** 导入按钮操作 */
function handleImport() {
  upload.title = "用户导入";
  upload.open = true;
};

/** 下载模板操作 */
function importTemplate() {
  // proxy.download("security/assessment/importTemplate", {
  // }, `user_template_${new Date().getTime()}.xlsx`);


  // "localhost:8080/security/assessment/importTemplate/xxx.xlsx"
};

/**文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].handleRemove(file);
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
};

getList();
</script>
