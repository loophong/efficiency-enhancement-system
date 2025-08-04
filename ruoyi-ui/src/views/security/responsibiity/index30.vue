<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="项目名称" prop="projectId">
        <el-input
          v-model="queryParams.projectId"
          placeholder="请输入项目名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
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
          v-hasPermi="['security:InvestmentPlan:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:InvestmentPlan:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:InvestmentPlan:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:InvestmentPlan:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:InvestmentPlan:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="InvestmentPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100" />
      <!-- <el-table-column label="项目ID" align="center" prop="projectId" /> -->
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="全年预算" align="center" prop="budgetTotal2024" />
      <el-table-column label="一季度预算" align="center" prop="budgetQ12024" />
      <el-table-column label="二季度预算" align="center" prop="budgetQ22024" />
      <el-table-column label="三季度预算" align="center" prop="budgetQ32024" />
      <el-table-column label="四季度预算" align="center" prop="budgetQ42024" />
      <el-table-column label="预算依据或简要说明" align="center" prop="budgetBasis" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:InvestmentPlan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:InvestmentPlan:remove']">删除</el-button>
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

    <!-- 添加或修改安全投入计划对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="InvestmentPlanRef" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID" />
        </el-form-item> -->
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="全年预算" prop="budgetTotal2024">
          <el-input v-model="form.budgetTotal2024" placeholder="请输入2024年预算合计" />
        </el-form-item>
        <el-form-item label="一季度预算" prop="budgetQ12024">
          <el-input v-model="form.budgetQ12024" placeholder="请输入2024年第一季度" />
        </el-form-item>
        <el-form-item label="二季度预算" prop="budgetQ22024">
          <el-input v-model="form.budgetQ22024" placeholder="请输入2024年第二季度" />
        </el-form-item>
        <el-form-item label="三季度预算" prop="budgetQ32024">
          <el-input v-model="form.budgetQ32024" placeholder="请输入2024年第三季度" />
        </el-form-item>
        <el-form-item label="四季度预算" prop="budgetQ42024">
          <el-input v-model="form.budgetQ42024" placeholder="请输入2024年第四季度" />
        </el-form-item>
        <el-form-item label="预算依据或简要说明" prop="budgetBasis">
          <el-input v-model="form.budgetBasis" type="textarea" placeholder="请输入预算依据或简要说明" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 安全投入计划导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据（基于项目名称匹配）
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
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

<script setup name="InvestmentPlan">
import { listInvestmentPlan, getInvestmentPlan, delInvestmentPlan, addInvestmentPlan, updateInvestmentPlan } from "@/api/security/InvestmentPlan";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance();

const InvestmentPlanList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

/*** 用户导入参数 */
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
  url: import.meta.env.VITE_APP_BASE_API + "/security/InvestmentPlan/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    projectId: null,
    projectName: null,
  },
  rules: {
    // projectId: [
    //   { required: true, message: "项目ID不能为空", trigger: "blur" }
    // ],
    projectName: [
      { required: true, message: "项目名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全投入计划列表 */
function getList() {
  loading.value = true;
  listInvestmentPlan(queryParams.value).then(response => {
    InvestmentPlanList.value = response.rows;
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
    serialNumber: null,
    projectId: null,
    projectName: null,
    budgetTotal2024: null,
    budgetQ12024: null,
    budgetQ22024: null,
    budgetQ32024: null,
    budgetQ42024: null,
    budgetBasis: null
  };
  proxy.resetForm("InvestmentPlanRef");
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
  ids.value = selection.map(item => item.serialNumber);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加安全投入计划";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _serialNumber = row.serialNumber || ids.value
  getInvestmentPlan(_serialNumber).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全投入计划";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["InvestmentPlanRef"].validate(valid => {
    if (valid) {
      if (form.value.serialNumber != null) {
        updateInvestmentPlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addInvestmentPlan(form.value).then(response => {
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
  const _serialNumbers = row.serialNumber || ids.value;
  proxy.$modal.confirm('是否确认删除安全投入计划编号为"' + _serialNumbers + '"的数据项？').then(function() {
    return delInvestmentPlan(_serialNumbers);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/InvestmentPlan/export', {
    ...queryParams.value
  }, `InvestmentPlan_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "安全投入计划导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/InvestmentPlan/importTemplate', {}, `安全投入计划导入模板_${new Date().getTime()}.xlsx`, 'get');
}

/** 文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

getList();
</script>
