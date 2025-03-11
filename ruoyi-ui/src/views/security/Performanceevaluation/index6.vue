<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="记录编号" prop="recordNumber">
        <el-input
          v-model="queryParams.recordNumber"
          placeholder="请输入记录编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录名称" prop="recordName">
        <el-input
          v-model="queryParams.recordName"
          placeholder="请输入记录名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="归口部门" prop="responsibleDepartment">
        <el-input
          v-model="queryParams.responsibleDepartment"
          placeholder="请输入归口部门"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保存期限" prop="retentionPeriod">
        <el-input
          v-model="queryParams.retentionPeriod"
          placeholder="请输入保存期限"
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
          v-hasPermi="['security:EnvironmentalOhsRecordList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:EnvironmentalOhsRecordList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:EnvironmentalOhsRecordList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:EnvironmentalOhsRecordList:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload">
          导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="EnvironmentalOhsRecordListList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="记录编号" align="center" prop="recordNumber" />
      <el-table-column label="记录名称" align="center" prop="recordName" />
      <el-table-column label="归口部门" align="center" prop="responsibleDepartment" />
      <el-table-column label="保存期限" align="center" prop="retentionPeriod" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:EnvironmentalOhsRecordList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:EnvironmentalOhsRecordList:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"></pagination>


    <!-- 文件上传弹窗 -->
    <el-dialog title="危险化学品台账" v-model="uploadDialogVisible" width="35%" @close="resetUpload">
      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">危险化学品台账</span>
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

    <!-- 添加或修改环境职业健康安全记录清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="EnvironmentalOhsRecordListRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="记录编号" prop="recordNumber">
          <el-input v-model="form.recordNumber" placeholder="请输入记录编号" />
        </el-form-item>
        <el-form-item label="记录名称" prop="recordName">
          <el-input v-model="form.recordName" placeholder="请输入记录名称" />
        </el-form-item>
        <el-form-item label="归口部门" prop="responsibleDepartment">
          <el-input v-model="form.responsibleDepartment" placeholder="请输入归口部门" />
        </el-form-item>
        <el-form-item label="保存期限" prop="retentionPeriod">
          <el-input v-model="form.retentionPeriod" placeholder="请输入保存期限" />
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

<script setup name="EnvironmentalOhsRecordList">
import { listEnvironmentalOhsRecordList, getEnvironmentalOhsRecordList, delEnvironmentalOhsRecordList, addEnvironmentalOhsRecordList, updateEnvironmentalOhsRecordList,importFile} from "@/api/security/EnvironmentalOhsRecordList";
const { proxy } = getCurrentInstance();

const EnvironmentalOhsRecordListList = ref([]);
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
    recordNumber: null,
    recordName: null,
    responsibleDepartment: null,
    retentionPeriod: null
  },
  rules: {
    recordNumber: [
      { required: true, message: "记录编号不能为空", trigger: "blur" }
    ],
    recordName: [
      { required: true, message: "记录名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询环境职业健康安全记录清单列表 */
function getList() {
  loading.value = true;
  listEnvironmentalOhsRecordList(queryParams.value).then(response => {
    EnvironmentalOhsRecordListList.value = response.rows;
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
    recordNumber: null,
    recordName: null,
    responsibleDepartment: null,
    retentionPeriod: null
  };
  proxy.resetForm("EnvironmentalOhsRecordListRef");
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
  title.value = "添加环境职业健康安全记录清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getEnvironmentalOhsRecordList(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改环境职业健康安全记录清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["EnvironmentalOhsRecordListRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateEnvironmentalOhsRecordList(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEnvironmentalOhsRecordList(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除环境职业健康安全记录清单编号为"' + _ids + '"的数据项？').then(function() {
    return delEnvironmentalOhsRecordList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/EnvironmentalOhsRecordList/export', {
    ...queryParams.value
  }, `EnvironmentalOhsRecordList_${new Date().getTime()}.xlsx`)
}

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

getList();
</script>
