<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作业活动" prop="activity">
        <el-input
          v-model="queryParams.activity"
          placeholder="请输入作业活动"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="环境因数" prop="environmentalFactor">
        <el-input
          v-model="queryParams.environmentalFactor"
          placeholder="请输入环境因数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="环境影响" prop="impactOfEnvironmentalFactor">
        <el-input
          v-model="queryParams.impactOfEnvironmentalFactor"
          placeholder="请输入环境影响"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="A" prop="evaluationOfEnvironmentalFactorA">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorA"
          placeholder="请输入A"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="B" prop="evaluationOfEnvironmentalFactorB">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorB"
          placeholder="请输入B"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="C" prop="evaluationOfEnvironmentalFactorC">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorC"
          placeholder="请输入C"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="D" prop="evaluationOfEnvironmentalFactorD">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorD"
          placeholder="请输入D"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="E" prop="evaluationOfEnvironmentalFactorE">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorE"
          placeholder="请输入E"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="F" prop="evaluationOfEnvironmentalFactorF">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorF"
          placeholder="请输入F"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="G" prop="evaluationOfEnvironmentalFactorG">
        <el-input
          v-model="queryParams.evaluationOfEnvironmentalFactorG"
          placeholder="请输入G"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="环境影响程度" prop="environmentalImpact">
        <el-input
          v-model="queryParams.environmentalImpact"
          placeholder="请输入环境影响程度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时态/状态" prop="tense">
        <el-input
          v-model="queryParams.tense"
          placeholder="请输入时态/状态"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="控制措施" prop="controlMeasures">
        <el-input
          v-model="queryParams.controlMeasures"
          placeholder="请输入控制措施"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="审批状态" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in security_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="关联ID" prop="relatedId">
        <el-input
          v-model="queryParams.relatedId"
          placeholder="请输入关联ID"
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
          v-hasPermi="['security:impact:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:impact:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:impact:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:impact:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:impact:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:impact:import']"
        >下载模板</el-button>
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

    <el-table v-loading="loading" :data="impactList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="作业活动" align="center" prop="activity" />
      <el-table-column label="环境因数" align="center" prop="environmentalFactor" />
      <el-table-column label="环境影响" align="center" prop="impactOfEnvironmentalFactor" />
      <el-table-column label="环境因素评价" align="center">
      <el-table-column label="A" align="center" prop="evaluationOfEnvironmentalFactorA" />
      <el-table-column label="B" align="center" prop="evaluationOfEnvironmentalFactorB" />
      <el-table-column label="C" align="center" prop="evaluationOfEnvironmentalFactorC" />
      <el-table-column label="D" align="center" prop="evaluationOfEnvironmentalFactorD" />
      <el-table-column label="E" align="center" prop="evaluationOfEnvironmentalFactorE" />
      <el-table-column label="F" align="center" prop="evaluationOfEnvironmentalFactorF" />
      <el-table-column label="G" align="center" prop="evaluationOfEnvironmentalFactorG" />
      </el-table-column>
      <el-table-column label="环境影响程度" align="center" prop="environmentalImpact" />
      <el-table-column label="时态/状态" align="center" prop="tense" />
      <el-table-column label="控制措施" align="center" prop="controlMeasures" />
      <!-- <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="关联ID" align="center" prop="relatedId" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:impact:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:impact:remove']">删除</el-button>
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

    <!-- 添加或修改环境因素清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="impactRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="作业活动" prop="activity">
          <el-input v-model="form.activity" placeholder="请输入作业活动" />
        </el-form-item>
        <el-form-item label="环境因数" prop="environmentalFactor">
          <el-input v-model="form.environmentalFactor" placeholder="请输入环境因数" />
        </el-form-item>
        <el-form-item label="环境影响" prop="impactOfEnvironmentalFactor">
          <el-input v-model="form.impactOfEnvironmentalFactor" placeholder="请输入环境影响" />
        </el-form-item>
        <el-form-item label="A" prop="evaluationOfEnvironmentalFactorA">
          <el-input v-model="form.evaluationOfEnvironmentalFactorA" placeholder="请输入A" />
        </el-form-item>
        <el-form-item label="B" prop="evaluationOfEnvironmentalFactorB">
          <el-input v-model="form.evaluationOfEnvironmentalFactorB" placeholder="请输入B" />
        </el-form-item>
        <el-form-item label="C" prop="evaluationOfEnvironmentalFactorC">
          <el-input v-model="form.evaluationOfEnvironmentalFactorC" placeholder="请输入C" />
        </el-form-item>
        <el-form-item label="D" prop="evaluationOfEnvironmentalFactorD">
          <el-input v-model="form.evaluationOfEnvironmentalFactorD" placeholder="请输入D" />
        </el-form-item>
        <el-form-item label="E" prop="evaluationOfEnvironmentalFactorE">
          <el-input v-model="form.evaluationOfEnvironmentalFactorE" placeholder="请输入E" />
        </el-form-item>
        <el-form-item label="F" prop="evaluationOfEnvironmentalFactorF">
          <el-input v-model="form.evaluationOfEnvironmentalFactorF" placeholder="请输入F" />
        </el-form-item>
        <el-form-item label="G" prop="evaluationOfEnvironmentalFactorG">
          <el-input v-model="form.evaluationOfEnvironmentalFactorG" placeholder="请输入G" />
        </el-form-item>
        <el-form-item label="环境影响程度" prop="environmentalImpact">
          <el-input v-model="form.environmentalImpact" placeholder="请输入环境影响程度" />
        </el-form-item>
        <el-form-item label="时态/状态" prop="tense">
          <el-input v-model="form.tense" placeholder="请输入时态/状态" />
        </el-form-item>
        <el-form-item label="控制措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" placeholder="请输入控制措施" />
        </el-form-item>
        <!-- <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="关联ID" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联ID" />
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
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
            <el-button type="primary" :loading="upload.isUploading" style="margin-left:16px;" @click="submitFileForm">确 定</el-button>
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup name="Impact">
import { listImpact, getImpact, delImpact, addImpact, updateImpact, importImpact, downloadTemplate } from "@/api/security/impact";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');
const route = useRoute();
const impactList = ref([]);
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
    environmentalFactor: null,
    impactOfEnvironmentalFactor: null,
    evaluationOfEnvironmentalFactorA: null,
    evaluationOfEnvironmentalFactorB: null,
    evaluationOfEnvironmentalFactorC: null,
    evaluationOfEnvironmentalFactorD: null,
    evaluationOfEnvironmentalFactorE: null,
    evaluationOfEnvironmentalFactorF: null,
    evaluationOfEnvironmentalFactorG: null,
    environmentalImpact: null,
    tense: null,
    controlMeasures: null,
    statu: null,
    relatedId: null
  },
  rules: {
    activity: [
      { required: true, message: "作业活动不能为空", trigger: "blur" }
    ],
    environmentalFactor: [
      { required: true, message: "环境因数不能为空", trigger: "blur" }
    ],
  },
  upload: {
    open: false,
    title: "导入环境因素清单数据",
    isUploading: false,
    updateSupport: 0,
    url: import.meta.env.VITE_APP_BASE_API + "/security/impact/importData",
    headers: { Authorization: "Bearer " + getToken() }
  }
});

const { queryParams, form, rules, upload } = toRefs(data);

/** 查询环境因素清单列表 */
function getList() {
  loading.value = true;
  listImpact(queryParams.value).then(response => {
    impactList.value = response.rows;
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
    environmentalFactor: null,
    impactOfEnvironmentalFactor: null,
    evaluationOfEnvironmentalFactorA: null,
    evaluationOfEnvironmentalFactorB: null,
    evaluationOfEnvironmentalFactorC: null,
    evaluationOfEnvironmentalFactorD: null,
    evaluationOfEnvironmentalFactorE: null,
    evaluationOfEnvironmentalFactorF: null,
    evaluationOfEnvironmentalFactorG: null,
    environmentalImpact: null,
    tense: null,
    controlMeasures: null,
    statu: null,
    relatedId: null
  };
  proxy.resetForm("impactRef");
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
// 返回上一级页面
function handleGoBack() {
  proxy.$router.go(-1);
}
/** 新增按钮操作 */
function handleAdd() {
  reset();
        if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加环境因素清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getImpact(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改环境因素清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["impactRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateImpact(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addImpact(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除环境因素清单编号为"' + _ids + '"的数据项？').then(function() {
    return delImpact(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/impact/export', {
    ...queryParams.value
  }, `impact_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.value.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  downloadTemplate().then(response => {
    const blob = new Blob([response]);
    const fileName = `环境因素清单模板_${new Date().getTime()}.xlsx`;
    if ('download' in document.createElement('a')) {
      const link = document.createElement('a');
      link.download = fileName;
      link.style.display = 'none';
      link.href = URL.createObjectURL(blob);
      document.body.appendChild(link);
      link.click();
      URL.revokeObjectURL(link.href);
      document.body.removeChild(link);
    } else {
      navigator.msSaveBlob(blob, fileName);
    }
  }).catch(err => {
    console.error('下载模板失败', err);
    proxy.$modal.msgError('下载模板失败，请联系管理员！');
  });
}

/** 文件上传中处理 */
function handleFileUploadProgress(event, file) {
  upload.value.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response, file) {
  upload.value.open = false;
  upload.value.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

// 检查关联ID参数
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
