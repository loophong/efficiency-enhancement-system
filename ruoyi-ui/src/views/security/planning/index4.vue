<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="法律法规名称" prop="regulationName">
        <el-input
          v-model="queryParams.regulationName"
          placeholder="请输入法律法规名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文号" prop="documentNumber">
        <el-input
          v-model="queryParams.documentNumber"
          placeholder="请输入文号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布单位" prop="issuingUnit">
        <el-input
          v-model="queryParams.issuingUnit"
          placeholder="请输入发布单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布/修订日期" prop="issuanceRevisionDate">
        <el-date-picker clearable
          v-model="queryParams.issuanceRevisionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择发布/修订日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实施日期" prop="implementationDate">
        <el-date-picker clearable
          v-model="queryParams.implementationDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择实施日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="效力" prop="effectiveness">
        <el-input
          v-model="queryParams.effectiveness"
          placeholder="请输入效力"
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
          v-hasPermi="['security:legallist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:legallist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:legallist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:legallist:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- 自定义导入按钮 -->
        <el-button
          type="success"
          plain
          icon="Upload"
          @click="handleCustomImport"
          v-hasPermi="['security:legallist:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:legallist:import']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="legallistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="法律法规名称" align="center" prop="regulationName" />
      <el-table-column label="文号" align="center" prop="documentNumber" />
      <el-table-column label="发布单位" align="center" prop="issuingUnit" />
      <el-table-column label="发布/修订日期" align="center" prop="issuanceRevisionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.issuanceRevisionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实施日期" align="center" prop="implementationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.implementationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="效力" align="center" prop="effectiveness" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:legallist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:legallist:remove']">删除</el-button>
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

    <!-- 添加或修改安全/环境法律法规识别清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="legallistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="法律法规名称" prop="regulationName">
          <el-input v-model="form.regulationName" placeholder="请输入法律法规名称" />
        </el-form-item>
        <el-form-item label="文号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入文号" />
        </el-form-item>
        <el-form-item label="发布单位" prop="issuingUnit">
          <el-input v-model="form.issuingUnit" placeholder="请输入发布单位" />
        </el-form-item>
        <el-form-item label="发布/修订日期" prop="issuanceRevisionDate">
          <el-date-picker clearable
            v-model="form.issuanceRevisionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择发布/修订日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实施日期" prop="implementationDate">
          <el-date-picker clearable
            v-model="form.implementationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择实施日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="效力" prop="effectiveness">
          <el-input v-model="form.effectiveness" placeholder="请输入效力" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 自定义文件上传对话框 -->
    <el-dialog title="Excel导入" v-model="importDialogVisible" width="35%" @close="resetImportDialog">
      <el-form label-width="90px">
        <el-form-item label="导入模块：">
          <span style="color: rgb(68, 140, 39)">环境法律法规识别清单</span>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <div class="custom-upload-container">
            <input
              type="file"
              ref="fileInput"
              style="display: none"
              @change="handleFileChange"
              accept=".xlsx,.xls"
            />
            <el-button type="primary" @click="triggerFileSelect" size="small">选择文件</el-button>
            <span v-if="selectedFile" class="file-name">{{ selectedFile.name }}</span>
            <span v-else class="no-file">未选择文件</span>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="importDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitImportFile" :loading="importLoading">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Legallist">
import { listLegallist, getLegallist, delLegallist, addLegallist, updateLegallist, importFile, downloadTemplate } from "@/api/security/legallist";
import request from '@/utils/request';
import { getToken } from '@/utils/auth';
const route = useRoute();
const { proxy } = getCurrentInstance();

const legallistList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 自定义文件上传相关
const fileInput = ref(null);
const selectedFile = ref(null);
const importDialogVisible = ref(false);
const importLoading = ref(false);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    regulationName: null,
    documentNumber: null,
    issuingUnit: null,
    issuanceRevisionDate: null,
    implementationDate: null,
    effectiveness: null,
    relatedId: null
  },
  rules: {
    regulationName: [
      { required: true, message: "法律法规名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全/环境法律法规识别清单列表 */
function getList() {
  loading.value = true;
  listLegallist(queryParams.value).then(response => {
    legallistList.value = response.rows;
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
    regulationName: null,
    documentNumber: null,
    issuingUnit: null,
    issuanceRevisionDate: null,
    implementationDate: null,
    effectiveness: null
  };
  proxy.resetForm("legallistRef");
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
  title.value = "添加安全法律法规识别清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getLegallist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全法律法规识别清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["legallistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLegallist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLegallist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全法律法规识别清单编号为"' + _ids + '"的数据项？').then(function() {
    return delLegallist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/legallist/export', {
    ...queryParams.value
  }, `legallist_${new Date().getTime()}.xlsx`)
}

/** 下载模板按钮操作 */
function handleDownloadTemplate() {
  downloadTemplate().then(response => {
    const blob = new Blob([response]);
    const fileName = `环境法律法规识别清单模板_${new Date().getTime()}.xlsx`;
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

/** 触发自定义导入对话框 */
function handleCustomImport() {
  importDialogVisible.value = true;
  selectedFile.value = null;
}

/** 触发文件选择 */
function triggerFileSelect() {
  fileInput.value.click();
}

/** 处理文件选择变更 */
function handleFileChange(event) {
  const files = event.target.files;
  if (files && files.length > 0) {
    const file = files[0];
    const fileExt = file.name.split('.').pop().toLowerCase();
    
    if (fileExt !== 'xlsx' && fileExt !== 'xls') {
      proxy.$modal.msgError('只能上传Excel文件！');
      resetFileInput();
      return;
    }
    
    selectedFile.value = file;
  }
}

/** 重置文件输入框 */
function resetFileInput() {
  if (fileInput.value) {
    fileInput.value.value = '';
  }
  selectedFile.value = null;
}

/** 重置导入对话框 */
function resetImportDialog() {
  resetFileInput();
  importLoading.value = false;
}

/** 提交导入文件 */
function submitImportFile() {
  if (!selectedFile.value) {
    proxy.$modal.msgError('请选择文件');
    return;
  }
  
  importLoading.value = true;
  
  // 创建FormData对象
  const formData = new FormData();
  formData.append('file', selectedFile.value);
  
  // 发送请求
  request({
    url: '/security/legallist/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data',
      'Authorization': 'Bearer ' + getToken()
    }
  }).then(response => {
    proxy.$modal.msgSuccess(response.msg || '导入成功');
    importDialogVisible.value = false;
    importLoading.value = false;
    resetFileInput();
    getList(); // 刷新列表
  }).catch(error => {
    console.error('导入失败:', error);
    let errorMsg = '导入失败';
    if (error.response && error.response.data) {
      errorMsg = error.response.data.message || error.response.data.msg || errorMsg;
    } else if (error.message) {
      errorMsg = error.message;
    }
    proxy.$modal.msgError(errorMsg);
    importLoading.value = false;
  });
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

<style scoped>
.custom-upload-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.file-name {
  color: #67C23A;
  font-size: 14px;
  word-break: break-all;
  max-width: 250px;
  display: inline-block;
  vertical-align: middle;
}

.no-file {
  color: #909399;
  font-size: 14px;
}
</style>
