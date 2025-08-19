<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="系统" prop="controlSystem">
        <el-input
          v-model="queryParams.controlSystem"
          placeholder="请输入系统"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商编码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="主要供货范围" prop="mainSupplyrange">
        <el-input
          v-model="queryParams.mainSupplyrange"
          placeholder="请输入主要供货范围"
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
          v-hasPermi="['supplier:qualified:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:qualified:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:qualified:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:qualified:export']"
        >导出</el-button>
      </el-col>

      <el-col :span="1.5">
          <el-button @click="handleImport" type="success" plain icon="Upload"
                v-hasPermi="['supplier:qualified:import']">导入
          </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-download"
         @click="handleDownload" size="mini" 
         plain v-if="true">下载模版文件
        </el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="qualifiedList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键  " align="center" prop="id" /> -->
      <el-table-column label="系统" align="center" prop="controlSystem" />
      <el-table-column label="供应商编码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="主要供货范围" align="center" prop="mainSupplyrange" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:qualified:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:qualified:remove']">删除</el-button>
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

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="qualifiedRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="系统" prop="controlSystem">
          <el-input v-model="form.controlSystem" placeholder="请输入系统" />
        </el-form-item>
        <el-form-item label="供应商编码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商编码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="主要供货范围" prop="mainSupplyrange">
          <el-input v-model="form.mainSupplyrange" placeholder="请输入主要供货范围" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注" />
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
        <el-dialog title="导入合格供应商信息" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

          <el-form :model="form" ref="form" label-width="90px">
            <el-form-item label="上传表类：">
              <span style="color: rgb(68, 140, 39);">合格供应商表</span>
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

<script setup name="Qualified">
import { listQualified, getQualified, delQualified, addQualified, updateQualified,importFile } from "@/api/supplier/qualified";
import {handleTrueDownload} from "@/api/tool/gen"
const { proxy } = getCurrentInstance();

const qualifiedList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入参数
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    controlSystem: null,
    supplierCode: null,
    supplierName: null,
    mainSupplyrange: null,
    remarks: null
  },
  rules: {
    supplierCode: [
      { required: true, message: "供应商编码不能为空", trigger: "blur" }
    ],
    supplierName: [
      { required: true, message: "供应商名称不能为空", trigger: "blur" }
    ],
    controlSystem: [
      { required: true, message: "系统不能为空", trigger: "blur" }
    ],
    mainSupplyrange: [
      { required: true, message: "主要供货范围不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);
function handleDownload() {
  const url = "/profile/excel_templates/supply/合格供应商名录.xlsx";
  handleTrueDownload(url);
}
/** 查询供应商列表 */
function getList() {
  loading.value = true;
  listQualified(queryParams.value).then(response => {
    qualifiedList.value = response.rows;
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
    controlSystem: null,
    supplierCode: null,
    supplierName: null,
    mainSupplyrange: null,
    remarks: null
  };
  proxy.resetForm("qualifiedRef");
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
  title.value = "添加供应商";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getQualified(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改供应商";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["qualifiedRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateQualified(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addQualified(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除？').then(function() {
    return delQualified(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/qualified/export', {
    ...queryParams.value
  }, `qualified_${new Date().getTime()}.xlsx`)
}

getList();



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
    // let date = XXXdate;
    const formData = new FormData();

    formData.append('excelFile', file);
    // formData.append('date', date);

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
  }else {
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

</script>
