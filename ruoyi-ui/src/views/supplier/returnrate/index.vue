<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="月份" prop="month">
        <el-date-picker clearable
          v-model="queryParams.month"
          type="month"
          value-format="YYYY-MM-DD"
          placeholder="请选择月份">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="售后返修率" prop="returnRate">
        <el-input
          v-model="queryParams.returnRate"
          placeholder="请输入售后返修率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
   
      <!-- <el-form-item label="备选1" prop="one">
        <el-input
          v-model="queryParams.one"
          placeholder="请输入备选1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="2" prop="two">
        <el-input
          v-model="queryParams.two"
          placeholder="请输入2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="3" prop="three">
        <el-input
          v-model="queryParams.three"
          placeholder="请输入3"
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
          v-hasPermi="['supplier:returnrate:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:returnrate:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:returnrate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:returnrate:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['supplier:returnrate:import']">导入
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

    <el-table v-loading="loading" :data="returnrateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <!-- <el-table-column label="供应商编码" align="center" prop="supplierCode" /> -->
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="售后返修率" align="center" prop="returnRate" />
      <el-table-column label="分数" align="center" prop="score" />
      <el-table-column label="月份" align="center" prop="month" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.month, '{y}-{m}') }}</span>
        </template>
      </el-table-column>

      <!-- <el-table-column label="2" align="center" prop="two" /> -->
      <!-- <el-table-column label="3" align="center" prop="three" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:returnrate:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:returnrate:remove']">删除</el-button>
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

    <!-- 添加或修改售后返修率对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="returnrateRef" :model="form" :rules="rules" label-width="100px">
        <!-- <el-form-item label="供应商编码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商编码" />
        </el-form-item> -->


        <el-form-item label="供应商名称" prop="supplierName">
          <el-select v-model="form.supplierName" clearable filterable placeholder="请选择或输入供应商名称" style="width: 240px">
            <el-option v-for="item in qualifiedList" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item> -->
        <el-form-item label="售后返修率" prop="returnRate">
          <el-input v-model="form.returnRate" placeholder="请输入售后返修率" />
        </el-form-item>
        <el-form-item label="月份" prop="month">
          <!-- <el-date-picker
          v-model="form.month"
          type="month"
        
          placeholder="Pick a month"
        /> -->
        <el-date-picker clearable
            v-model="form.month"
            type="month"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传月份">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="备选1" prop="one">
          <el-input v-model="form.one" placeholder="请输入备选1" />
        </el-form-item>
        <el-form-item label="2" prop="two">
          <el-input v-model="form.two" placeholder="请输入2" />
        </el-form-item>
        <el-form-item label="3" prop="three">
          <el-input v-model="form.three" placeholder="请输入3" />
        </el-form-item>-->
      </el-form> 
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


       <!-- 文件上传弹窗 -->
       <el-dialog title="导入返修率表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">返修率表单</span>
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

<script setup name="Returnrate">
import { listReturnrate, getReturnrate, delReturnrate, addReturnrate, 
  updateReturnrate, importFile} from "@/api/supplier/returnrate";
import dayjs from 'dayjs';
import {handleTrueDownload} from "@/api/tool/gen"
import { all } from "@/api/supplier/qualified";
import { watch, ref, reactive } from 'vue';


const { proxy } = getCurrentInstance();

const returnrateList = ref([]);
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
  form: {
    supplierCode: '',
    supplierName: ''
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    returnRate: null,
    month: null,
    score: null,
    two: null,
    three: null
  },
  rules: {
    supplierName: [
      { required: true, message: "供应商名称不能为空", trigger: "blur" }
    ],
    returnRate: [
      { required: true, message: "售后返修率不能为空", trigger: "blur" }
    ],
    month: [
      { required: true, message: "上传月份不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);
function handleDownload() {
  const url = "/profile/excel_templates/supply/售后返修率.xlsx";
  handleTrueDownload(url);
}/** 查询售后返修率列表 */
function getList() {
  loading.value = true;
  listReturnrate(queryParams.value).then(response => {
    returnrateList.value = response.rows;
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
    returnRate: null,
    month: null,
    score: null,
    two: null,
    three: null
  };
  proxy.resetForm("returnrateRef");
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
  title.value = "添加售后返修率";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getReturnrate(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改售后返修率";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["returnrateRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateReturnrate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addReturnrate(form.value).then(response => {
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
    return delReturnrate(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/returnrate/export', {
    ...queryParams.value
  }, `returnrate_${new Date().getTime()}.xlsx`)
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

// /** excel文件上传 */
// function uploadFile() {
//   if (inputFile.value && inputFile.value.files.length > 0) {
//     isLoading.value = true;
//     const file = inputFile.value.files[0];
//     console.log(inputFile.value);
//     console.log(file);
//     console.log("上传时间"+uploadDate.value);
//     let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
//     let uploadFileDTO = {
//       'uploadMonth': date,
//       'excelFile': file
//     }

//     importFile(uploadFileDTO).then(() => {
//       proxy.$modal.msgSuccess("导入成功");
//       getList();
//       uploadDialogVisible.value = false;
//       isLoading.value = false;
//     }).catch(() => {
//       proxy.$modal.msgError("导入失败");
//       isLoading.value = false;
//     }).finally(() => {
//       resetUpload();
//     });
//   }else {
//     proxy.$modal.msgError("请选择文件");
//   }
// }
/** excel文件上传 */
function uploadFile() {
  // 检查时间是否为空
if (!uploadDate.value) {
  proxy.$modal.msgError("请选择时间");
  return;
}

  if (inputFile.value && inputFile.value.files.length > 0) {
    isLoading.value = true;
    const file = inputFile.value.files[0];
    let date = dayjs(uploadDate.value).format('YYYY-MM-DD');
    
    // 先检查月份是否存在
    listReturnrate({ month: date, pageNum: 1, pageSize: 1 }).then(response => {
      if (response.rows && response.rows.length > 0) {
        // 有数据，弹出确认对话框
        proxy.$modal.confirm('该月份的数据已存在，是否覆盖？', '提示', {
          confirmButtonText: '覆盖',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 用户选择覆盖，直接导入
          performImport(file, date);
        }).catch(() => {
          // 用户取消
          isLoading.value = false;
          uploadDialogVisible.value = false;  // 添加这行关闭窗口
          resetUpload();  // 添加这行重置表单
          // proxy.$message.info("导入已取消");
          proxy.$message({
            message: '导入已取消',
            type: 'warning',
            duration: 3000,
            showClose: true
          });
        });
      } else {
        // 没有数据，直接导入
        performImport(file, date);
      }
    }).catch(() => {
      // 查询失败，直接导入
      performImport(file, date);
    });
  } else {
    proxy.$modal.msgError("请选择文件");
  }
}

// 执行导入操作
function performImport(file, date) {
  let uploadFileDTO = {
    'uploadMonth': date,
    'excelFile': file
  };
  
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
//供应商代码/名称 增加时 模糊查询
const qualifiedList = ref([]);

function getCodeAndName(row) {
  all().then(response => {
    console.log("请求的供应商数据" + JSON.stringify(response.data))
    response.data.forEach(element => {
      qualifiedList.value.push({
        label: element.supplierName,
        value: element.supplierCode
      })
    });
    console.log("请求的供应商数据" + JSON.stringify(qualifiedList))

  })
}

// 初始化时调用上面的方法
onMounted(() => {
  getCodeAndName()
})

//如果form.supplierName发生改变
watch(() => form.value.supplierName, (newValue, oldValue) => {
  console.log("form.supplierName发生改变", newValue, oldValue);
  const selectedItem = qualifiedList.value.find(item => item.label === newValue);
  if (selectedItem) {
    form.value.supplierCode = selectedItem.value;
  }
  console.log("form.supplierCode", form.value.supplierCode);
});

//如果form.supplierCode发生改变
watch(() => form.value.supplierCode, (newValue, oldValue) => {
  console.log("form.supplierCode发生改变", newValue, oldValue);
  const selectedItem = qualifiedList.value.find(item => item.value === newValue);
  if (selectedItem) {
    form.value.supplierName = selectedItem.label;
  }
  console.log("form.supplierName", form.value.supplierName);
});
</script>
