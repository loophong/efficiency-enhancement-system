<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商代码"
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
      <el-form-item label="上传时间" prop="uploadMonth">
        <el-date-picker clearable
          v-model="queryParams.uploadMonth"
          type="month"
          value-format="YYYY-MM"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item> 
      <!-- <el-form-item label="总条目数" prop="totalNumber">
        <el-input
          v-model="queryParams.totalNumber"
          placeholder="请输入总条目数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="及时条目数" prop="timelyNumber">
        <el-input
          v-model="queryParams.timelyNumber"
          placeholder="请输入及时条目数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="不及时条目数" prop="notTimely">
        <el-input
          v-model="queryParams.notTimely"
          placeholder="请输入不及时条目数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供货及时率" prop="timelyDeliveryRate">
        <el-input
          v-model="queryParams.timelyDeliveryRate"
          placeholder="请输入供货及时率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="及时率得分" prop="timelyRateScore">
        <el-input
          v-model="queryParams.timelyRateScore"
          placeholder="请输入及时率得分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="模型得分" prop="modelScore">
        <el-input
          v-model="queryParams.modelScore"
          placeholder="请输入模型得分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>-->

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
          v-hasPermi="['supplier:guarantee:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:guarantee:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:guarantee:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:guarantee:export']"
        >导出</el-button>
      </el-col>

      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:guarantee:import']">导入
              </el-button>
            </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="guaranteeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="总条目数" align="center" prop="totalNumber" />
      <el-table-column label="及时条目数" align="center" prop="timelyNumber" />
      <el-table-column label="不及时条目数" align="center" prop="notTimely" />
      <el-table-column label="供货及时率" align="center" prop="timelyDeliveryRate" />
      <el-table-column label="得分" align="center" prop="timelyRateScore" />
      <!-- <el-table-column label="模型得分" align="center" prop="modelScore" /> -->
      <el-table-column label="上传时间" align="center" prop="uploadMonth" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadMonth, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:guarantee:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:guarantee:remove']">删除</el-button>
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

    <!-- 添加或修改供货保障对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="guaranteeRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="总条目数" prop="totalNumber">
          <el-input v-model="form.totalNumber" placeholder="请输入总条目数" />
        </el-form-item>
        <el-form-item label="及时条目数" prop="timelyNumber">
          <el-input v-model="form.timelyNumber" placeholder="请输入及时条目数" />
        </el-form-item>
        <el-form-item label="不及时条目数" prop="notTimely">
          <el-input v-model="form.notTimely" placeholder="请输入不及时条目数" />
        </el-form-item>
        <el-form-item label="供货及时率" prop="timelyDeliveryRate">
          <el-input v-model="form.timelyDeliveryRate" placeholder="请输入供货及时率" />
        </el-form-item>
        <!-- <el-form-item label="及时率得分" prop="timelyRateScore">
          <el-input v-model="form.timelyRateScore" placeholder="请输入及时率得分" />
        </el-form-item>
        <el-form-item label="模型得分" prop="modelScore">
          <el-input v-model="form.modelScore" placeholder="请输入模型得分" />
        </el-form-item> -->
        <el-form-item label="上传时间" prop="uploadMonth">
          <el-date-picker clearable
            v-model="form.uploadMonth"
            type="month"
            value-format="YYYY-MM"
            placeholder="请选择上传月份">
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


<!-- 文件上传弹窗 -->
<el-dialog title="导入供货保障信息" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">供货保障</span>
    <br>
  </el-form-item>

  <el-form-item label="时间">
<el-date-picker
    v-model="uploadDate"
    type="month"
    placeholder="Pick a day"
    date-format="yyyy-MM-dd"
    :size="size"
  />
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

<script setup name="Guarantee">
import { listGuarantee, getGuarantee, delGuarantee, addGuarantee, updateGuarantee,importFile } from "@/api/supplier/guarantee";
import dayjs from 'dayjs';
const { proxy } = getCurrentInstance();

const guaranteeList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const uploadDate = ref("");

// 导入参数
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);


const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    totalNumber: null,
    timelyNumber: null,
    notTimely: null,
    timelyDeliveryRate: null,
    timelyRateScore: null,
    modelScore: null,
    uploadMonth: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询供货保障列表 */
function getList() {
  loading.value = true;
  listGuarantee(queryParams.value).then(response => {
    guaranteeList.value = response.rows;
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
    supplierCode: null,
    supplierName: null,
    totalNumber: null,
    timelyNumber: null,
    notTimely: null,
    timelyDeliveryRate: null,
    timelyRateScore: null,
    modelScore: null,
    uploadMonth: null
  };
  proxy.resetForm("guaranteeRef");
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
  title.value = "添加供货保障";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getGuarantee(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改供货保障";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["guaranteeRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateGuarantee(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addGuarantee(form.value).then(response => {
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
    return delGuarantee(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/guarantee/export', {
    ...queryParams.value
  }, `guarantee_${new Date().getTime()}.xlsx`)
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
    console.log("上传时间"+uploadDate.value);
    let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
    let uploadFileDTO = {
      'uploadMonth': date,
      'excelFile': file
    }

    importFile(uploadFileDTO).then(() => {
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
