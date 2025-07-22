<template>
  <div class="app-container">
    <!-- 关联ID筛选提示
    <el-alert
      v-if="route.query.relatedId"
      :title="`当前正在显示关联ID为 ${route.query.relatedId} 的数据`"
      type="info"
      :closable="false"
      show-icon
      style="margin-bottom: 10px;"
    >
      <template #default>
        <span>当前正在显示关联ID为 <strong>{{ route.query.relatedId }}</strong> 的数据</span>
        <el-button
          type="text"
          size="small"
          @click="clearFilter"
          style="margin-left: 10px;"
        >
          清除筛选
        </el-button>
      </template>
    </el-alert> -->

    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="documentNumber">
        <el-input
          v-model="queryParams.documentNumber"
          placeholder="请输入编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="接收单位" prop="receivingUnit">
        <el-input
          v-model="queryParams.receivingUnit"
          placeholder="请输入接收单位"
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
          v-hasPermi="['security:controlleddirectory:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:controlleddirectory:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:controlleddirectory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:controlleddirectory:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload">
          导入
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:controlleddirectory:export']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="controlleddirectoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="编号" align="center" prop="documentNumber" />
      <el-table-column label="接收单位" align="center" prop="receivingUnit" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:controlleddirectory:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:controlleddirectory:remove']">删除</el-button>
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

    <!-- 添加或修改受控文件发放目录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="controlleddirectoryRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="接收单位" prop="receivingUnit">
          <el-input v-model="form.receivingUnit" placeholder="请输入接收单位" />
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
    <el-dialog
      title="导入受控文件发放目录"
      v-model="uploadDialogVisible"
      width="500px"
      :before-close="cancelUpload"
    >
      <el-form :model="uploadForm" label-width="100px">
        <el-form-item label="模块名称：">
          <span style="color: #409eff; font-weight: 500;">受控文件发放目录</span>
        </el-form-item>
        <el-form-item label="选择文件：">
          <div class="upload-container">
            <el-upload
              ref="uploadRef"
              :limit="1"
              accept=".xlsx,.xls"
              :auto-upload="false"
              :on-change="handleFileChange"
              :on-remove="handleFileRemove"
              :file-list="fileList"
              drag
            >
              <el-icon class="el-icon--upload"><upload-filled /></el-icon>
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  只能上传 xlsx/xls 文件，且不超过 10MB
                </div>
              </template>
            </el-upload>
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="cancelUpload">取 消</el-button>
          <el-button
            type="primary"
            @click="uploadFile"
            :loading="isLoading"
            :disabled="fileList.length === 0"
          >
            {{ isLoading ? '导入中...' : '确 定' }}
          </el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup name="Controlleddirectory">
import { listControlleddirectory, getControlleddirectory, delControlleddirectory, addControlleddirectory, updateControlleddirectory ,importFile, downloadTemplate, listByRelatedId} from "@/api/security/controlleddirectory";
const route = useRoute();
const { proxy } = getCurrentInstance();
// 上传文件
const uploadDialogVisible = ref(false);
const uploadRef = ref(null);
const fileList = ref([]);
const isLoading = ref(false);
const uploadForm = ref({});
const controlleddirectoryList = ref([]);
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
    fileName: null,
    documentNumber: null,
    receivingUnit: null
  },
  rules: {
    fileName: [
      { required: true, message: "文件名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询受控文件发放目录列表 */
function getList() {
  loading.value = true;

  // 检查URL参数中是否有关联ID
  const relatedId = route.query.relatedId;

  if (relatedId) {
    console.log('检测到关联ID参数:', relatedId);
    // 使用关联ID进行筛选查询
    listByRelatedId(relatedId).then(response => {
      controlleddirectoryList.value = response.rows;
      total.value = response.total;
      loading.value = false;
      proxy.$modal.msgInfo(`已筛选显示关联ID为 ${relatedId} 的数据，共 ${response.total} 条记录`);
    }).catch(() => {
      loading.value = false;
    });
  } else {
    // 正常查询所有数据
    listControlleddirectory(queryParams.value).then(response => {
      controlleddirectoryList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    }).catch(() => {
      loading.value = false;
      proxy.$modal.msgError("查询数据失败");
    });
  }
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
    fileName: null,
    documentNumber: null,
    receivingUnit: null
  };
  proxy.resetForm("controlleddirectoryRef");
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
  title.value = "添加受控文件发放目录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getControlleddirectory(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改受控文件发放目录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["controlleddirectoryRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateControlleddirectory(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addControlleddirectory(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除受控文件发放目录编号为"' + _ids + '"的数据项？').then(function() {
    return delControlleddirectory(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/controlleddirectory/export', {
    ...queryParams.value
  }, `controlleddirectory_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
}

/** 表单重置 */
function resetUpload() {
  fileList.value = [];
  uploadForm.value = {};
  if (uploadRef.value) {
    uploadRef.value.clearFiles();
  }
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  resetUpload();
}

/** 文件选择变化 */
function handleFileChange(file, files) {
  fileList.value = files;
  // 检查文件类型
  const fileName = file.name;
  const fileExt = fileName.split(".").pop().toLowerCase();

  if (!["xlsx", "xls", "xlsm"].includes(fileExt)) {
    proxy.$modal.msgError("只能上传 Excel 文件！");
    handleFileRemove(file, files);
    return false;
  }

  // 检查文件大小（10MB）
  if (file.size > 10 * 1024 * 1024) {
    proxy.$modal.msgError("文件大小不能超过 10MB！");
    handleFileRemove(file, files);
    return false;
  }
}

/** 文件移除 */
function handleFileRemove(file, files) {
  fileList.value = files;
}

/** excel文件上传 */
function uploadFile() {
  if (fileList.value.length === 0) {
    proxy.$modal.msgError("请选择文件");
    return;
  }

  isLoading.value = true;
  const file = fileList.value[0].raw;
  const formData = new FormData();

  formData.append('excelFile', file);

  importFile(formData).then(() => {
    proxy.$modal.msgSuccess("导入成功");
    getList();
    uploadDialogVisible.value = false;
  }).catch((error) => {
    console.error("导入失败:", error);
    proxy.$modal.msgError("导入失败，请检查文件格式和内容");
  }).finally(() => {
    isLoading.value = false;
    resetUpload();
  });
}

/** 下载模板 */
function handleDownloadTemplate() {
  downloadTemplate().then(response => {
    const blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = '受控文件发放目录导入模板.xlsx';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
    proxy.$modal.msgSuccess("模板下载成功");
  }).catch(() => {
    proxy.$modal.msgError("模板下载失败");
  });
}

/** 清除关联ID筛选 */
function clearFilter() {
  // 移除URL中的relatedId参数
  const newQuery = { ...route.query };
  delete newQuery.relatedId;

  // 更新路由
  proxy.$router.replace({
    path: route.path,
    query: newQuery
  });

  // 重新加载数据
  getList();
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
