<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="检查项目" prop="inspectionItem">
        <el-input
          v-model="queryParams.inspectionItem"
          placeholder="请输入检查项目"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检查人" prop="inspecter">
        <el-input
          v-model="queryParams.inspecter"
          placeholder="请输入检查人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检查时间" prop="inspectionTime">
        <el-date-picker clearable
          v-model="queryParams.inspectionTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择检查时间">
        </el-date-picker>
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
          v-hasPermi="['security:hazardousinspection:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:hazardousinspection:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:hazardousinspection:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:hazardousinspection:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload">
          导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hazardousinspectionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="检查项目" align="center" prop="inspectionItem" />
      <el-table-column label="检查标准" align="center" prop="inspectionStandard" />
      <el-table-column label="检查记录" align="center" prop="inspectionRecord" />
      <el-table-column label="检查人" align="center" prop="inspecter" />
      <el-table-column label="检查时间" align="center" prop="inspectionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.inspectionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:hazardousinspection:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:hazardousinspection:remove']">删除</el-button>
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


       <!-- 文件上传弹窗 -->
       <el-dialog title="危险化学品检查记录" v-model="uploadDialogVisible" width="35%" @close="resetUpload">
      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">危险化学品检查记录</span>
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



    <!-- 添加或修改危化品检查记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="hazardousinspectionRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="检查项目" prop="inspectionItem">
          <el-input v-model="form.inspectionItem" placeholder="请输入检查项目" />
        </el-form-item>
        <el-form-item label="检查标准" prop="inspectionStandard">
          <el-input v-model="form.inspectionStandard" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="检查记录" prop="inspectionRecord">
          <el-input v-model="form.inspectionRecord" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="检查人" prop="inspecter">
          <el-input v-model="form.inspecter" placeholder="请输入检查人" />
        </el-form-item>
        <el-form-item label="检查时间" prop="inspectionTime">
          <el-date-picker clearable
            v-model="form.inspectionTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择检查时间">
          </el-date-picker>
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

<script setup name="Hazardousinspection">
import { listHazardousinspection, getHazardousinspection, delHazardousinspection, addHazardousinspection, updateHazardousinspection,importFile } from "@/api/security/hazardousinspection";

const { proxy } = getCurrentInstance();

const hazardousinspectionList = ref([]);
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
    inspectionItem: null,
    inspectionStandard: null,
    inspectionRecord: null,
    inspecter: null,
    inspectionTime: null
  },
  rules: {
    inspectionItem: [
      { required: true, message: "检查项目不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询危化品检查记录列表 */
function getList() {
  loading.value = true;
  listHazardousinspection(queryParams.value).then(response => {
    hazardousinspectionList.value = response.rows;
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
    inspectionItem: null,
    inspectionStandard: null,
    inspectionRecord: null,
    inspecter: null,
    inspectionTime: null
  };
  proxy.resetForm("hazardousinspectionRef");
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
  title.value = "添加危化品检查记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHazardousinspection(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改危化品检查记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["hazardousinspectionRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHazardousinspection(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHazardousinspection(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除危化品检查记录编号为"' + _ids + '"的数据项？').then(function() {
    return delHazardousinspection(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/hazardousinspection/export', {
    ...queryParams.value
  }, `hazardousinspection_${new Date().getTime()}.xlsx`)
}

//上传文件
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

getList();
</script>
