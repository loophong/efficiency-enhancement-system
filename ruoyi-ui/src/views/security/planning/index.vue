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
      <el-form-item label="环境因素评价" prop="evaluationOfEnvironmentalFactor">
        <el-select v-model="queryParams.evaluationOfEnvironmentalFactor" placeholder="请选择环境因素评价" clearable>
          <el-option
            v-for="dict in evaluation_of_environmental_factor"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="环境影响程度" prop="environmentalImpact">
        <el-input
          v-model="queryParams.environmentalImpact"
          placeholder="请输入环境影响程度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时态" prop="tense">
        <el-input
          v-model="queryParams.tense"
          placeholder="请输入时态"
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
      <el-form-item label="审批状态" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审批状态" clearable>
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
        <el-button @click="handleImport" type="success" plain icon="Upload">
          导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="impactList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="作业活动" align="center" prop="activity" />
      <el-table-column label="环境因数" align="center" prop="environmentalFactor" />
      <el-table-column label="环境影响" align="center" prop="impactOfEnvironmentalFactor" />
      <el-table-column label="环境因素评价" align="center" prop="evaluationOfEnvironmentalFactor">
        <template #default="scope">
          <dict-tag :options="evaluation_of_environmental_factor" :value="scope.row.evaluationOfEnvironmentalFactor"/>
        </template>
      </el-table-column>
      <el-table-column label="环境影响程度" align="center" prop="environmentalImpact" />
      <el-table-column label="时态" align="center" prop="tense" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="控制措施" align="center" prop="controlMeasures" />
      <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column>
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
        <el-form-item label="环境因素评价" prop="evaluationOfEnvironmentalFactor">
          <el-select v-model="form.evaluationOfEnvironmentalFactor" placeholder="请选择环境因素评价">
            <el-option
              v-for="dict in evaluation_of_environmental_factor"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="环境影响程度" prop="environmentalImpact">
          <el-input v-model="form.environmentalImpact" placeholder="请输入环境影响程度" />
        </el-form-item>
        <el-form-item label="时态" prop="tense">
          <el-input v-model="form.tense" placeholder="请输入时态" />
        </el-form-item>
        <el-form-item label="控制措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" placeholder="请输入控制措施" />
        </el-form-item>
        <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
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
     <!-- 文件上传弹窗 -->
     <el-dialog title="环境因素清单" v-model="uploadDialogVisible" width="35%" @close="resetUpload">
      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">环境因素清单</span>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <input type="file" ref="inputFile" @change="checkFile"/>
          <br>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="cancelUpload">取 消</el-button>
        <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
        <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup name="Impact">
import { listImpact, getImpact, delImpact, addImpact, updateImpact,importFile } from "@/api/security/impact";
const { proxy } = getCurrentInstance();
const { evaluation_of_environmental_factor, security_status } = proxy.useDict('evaluation_of_environmental_factor', 'security_status');

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
    evaluationOfEnvironmentalFactor: null,
    environmentalImpact: null,
    tense: null,
    status: null,
    controlMeasures: null,
    statu: null
  },
  rules: {
    activity: [
      { required: true, message: "作业活动不能为空", trigger: "blur" }
    ],
    environmentalFactor: [
      { required: true, message: "环境因数不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);
// 上传文件
const uploadDialogVisible = ref(false);
const inputFile = ref(null);
const isLoading = ref(false);

/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
}

/** 表单重置 */
function resetUpload() {
  if (inputFile.value) {
    inputFile.value.value = "";
  }
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  resetUpload();
}

/** excel文件上传 */
function uploadFile() {
  if (inputFile.value && inputFile.value.files.length > 0) {
    isLoading.value = true;
    const file = inputFile.value.files[0];
    console.log(inputFile.value);
    console.log(file);
    const formData = new FormData();

    formData.append('excelFile', file);
    importFile(formData).then(() => {
      proxy.$modal.msgSuccess("导入成功");
      getList();
      uploadDialogVisible.value = false;
      isLoading.value = false;
    }).catch(() => {
      proxy.$modal.msgError("导入失败");
      isLoading.value = false;
    }).finally(() => {
      resetUpload();
    });
  } else {
    proxy.$modal.msgError("请选择文件");
  }
}

/** 检查文件是否为excel */
function checkFile() {
  const file = inputFile.value.files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    proxy.$modal.msgError("只能上传 Excel 文件！");
    resetUpload();
  }
}
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
    evaluationOfEnvironmentalFactor: null,
    environmentalImpact: null,
    tense: null,
    status: null,
    controlMeasures: null,
    statu: null
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

/** 新增按钮操作 */
function handleAdd() {
  reset();
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

getList();
</script>
