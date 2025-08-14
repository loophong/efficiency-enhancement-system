<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传月份" prop="uploadMonth">
        <el-date-picker clearable
          v-model="queryParams.uploadMonth"
          type="month"
          value-format="YYYY-MM-DD"
          placeholder="请选择上传月份">
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
          v-hasPermi="['supplier:zeroFailureRate:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:zeroFailureRate:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:zeroFailureRate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:zeroFailureRate:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:zeroFailureRate:import']">零公里故障指标完成率(PPM)导入
              </el-button>
            </el-col>

            <el-col :span="1.5">
              <el-button @click="handleImport1" type="success" plain icon="Upload"
                :disabled="!isFirstImportCompleted"
                         v-hasPermi="['production:zeroFailureRate:import']">产品过程故障率导入
              </el-button>
            </el-col>
      
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-download"
         @click="handleDownload1" size="mini" 
         plain v-if="true">下载PPM模版文件
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-download"
         @click="handleDownload2" size="mini" 
         plain v-if="true">下载产品过程故障率模版文件
        </el-button>
      </el-col>



      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zeroFailureRateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <!-- <el-table-column label="部件类别" align="center" prop="componentType" /> -->
      <!-- <el-table-column label="2024年指标值" align="center" prop="2024TargetPpm" /> -->
      <!-- <el-table-column label="故障描述" align="center" prop="failureDescription" /> -->
      <!-- <el-table-column label="故障数" align="center" prop="failureCount" />
      <el-table-column label="装车数" align="center" prop="installedVehicles" /> -->
      <el-table-column label="零公里故障指标完成率" align="center" prop="ppmValue" />
      <!-- <el-table-column label="季度累计PPM值" align="center" prop="cumulativePpm" /> -->
      <el-table-column label="产品过程故障率" align="center" prop="zeroFailureRate" />
      <el-table-column label="得分" align="center" prop="score" />
      <!-- <el-table-column label="月份" align="center" prop="uploadMonth" /> -->
      <el-table-column label="上传月份" align="center" prop="uploadMonth" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadMonth, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="月份" align="center" prop="time" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:zeroFailureRate:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:zeroFailureRate:remove']">删除</el-button>
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

    <!-- 添加或修改零公里故障指标完成率对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="zeroFailureRateRef" :model="form" :rules="rules" label-width="120px">
        <!-- <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item> -->


        <el-form-item label="供应商名称" prop="supplierName">
          <el-select v-model="form.supplierName" clearable filterable placeholder="请选择或输入供应商名称" style="width: 240px">
            <el-option v-for="item in qualifiedList" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="2024年指标值" prop="2024TargetPpm">
          <el-input v-model="form.TargetPpm" placeholder="请输入2024年指标值" />
        </el-form-item>
        <el-form-item label="故障描述" prop="failureDescription">
          <el-input v-model="form.failureDescription" placeholder="请输入故障描述" />
        </el-form-item>
        <el-form-item label="故障数" prop="failureCount">
          <el-input v-model="form.failureCount" placeholder="请输入故障数" />
        </el-form-item>
        <el-form-item label="装车数" prop="installedVehicles">
          <el-input v-model="form.installedVehicles" placeholder="请输入装车数" />
        </el-form-item> -->
        <el-form-item label="PPM值" prop="ppmValue">
          <el-input v-model="form.ppmValue" placeholder="请输入PPM值" />
        </el-form-item>
        <!-- <el-form-item label="季度累计PPM值" prop="cumulativePpm">
          <el-input v-model="form.cumulativePpm" placeholder="请输入季度累计PPM值" />
        </el-form-item> -->
        <el-form-item label="零公里故障率" prop="zeroFailureRate">
          <el-input v-model="form.zeroFailureRate" placeholder="请输入零公里故障率" />
        </el-form-item>
        <el-form-item label="上传月份" prop="uploadMonth">
          <el-date-picker clearable
            v-model="form.uploadMonth"
            type="month"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传月份">
          </el-date-picker>
          <!-- <el-date-picker
          v-model="form.updateMonth"
          type="month"
        
          placeholder="Pick a month"
        /> -->
        </el-form-item>
        <!-- <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item> -->
        
        <!-- <el-form-item label="上传月份" prop="uploadmonth">
          <el-input v-model="form.uploadmonth" placeholder="请输入上传月份" />
        </el-form-item> -->
         <!-- <el-form-item label="月份" prop="time">
          <el-input v-model="form.time" placeholder="请输入月份" />
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


      <!-- 文件上传弹窗 -->
      <el-dialog title="导入表单" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">表单</span>
    <br>
  </el-form-item>

  <el-form-item label="时间">
    <el-date-picker
        v-model="uploadDate"
        type="month"
        placeholder="Pick a day"
        date-format="yyyy-MM-dd"
        :size="size"
        
        :disabled="isFirstImportCompleted" 
        
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

<script setup name="ZeroFailureRate">
import { listZeroFailureRate, getZeroFailureRate, delZeroFailureRate, addZeroFailureRate, updateZeroFailureRate,importFile,importFile1 } from "@/api/supplier/zeroFailureRate";
import dayjs from 'dayjs';
import {handleTrueDownload} from "@/api/tool/gen"
import { all } from "@/api/supplier/qualified";
import { watch, ref, reactive } from 'vue';



const { proxy } = getCurrentInstance();

const zeroFailureRateList = ref([]);
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
    supplierName: null,
    componentType: null,
    TargetPpm: null,
    failureDescription: null,
    failureCount: null,
    installedVehicles: null,
    ppmValue: null,
    cumulativePpm: null,
    zeroFailureRate: null,
    score: null,
    uploadMonth: null,
    time: null,
    orderByColumn: 'batch_id ',
    isAsc: 'desc'
  },
  rules: {

    supplierName: [
      { required: true, message: "供应商名称不能为空", trigger: "blur" }
    ],
    // ppmValue: [
    //   { required: true, message: "ppm值不能为空", trigger: "blur" }
    // ],
    // zeroFailureRate: [
    //   { required: true, message: "零公里故障率不能为空", trigger: "blur" }
    // ],
    uploadMonth: [
      { required: true, message: "上传月份不能为空", trigger: "blur" }
    ],

  }
});

const { queryParams, form, rules } = toRefs(data);
function handleDownload1() {
  const url = "/profile/excel_templates/supply/零公里故障指标完成率(PPM).xlsx";
  handleTrueDownload(url);
}

function handleDownload2() {
  const url = "/profile/excel_templates/supply/产品过程故障率.xlsx";
  handleTrueDownload(url);
}

/** 查询零公里故障指标完成率列表 */
function getList() {
  loading.value = true;
  listZeroFailureRate(queryParams.value).then(response => {
    zeroFailureRateList.value = response.rows;
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
    supplierName: null,
    componentType: null,
    TargetPpm: null,
    failureDescription: null,
    failureCount: null,
    installedVehicles: null,
    ppmValue: null,
    cumulativePpm: null,
    zeroFailureRate: null,
    score: null,
    uploadMonth: null,
    time: null
  };
  proxy.resetForm("zeroFailureRateRef");
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
  title.value = "添加零公里故障指标完成率";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getZeroFailureRate(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改零公里故障指标完成率";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["zeroFailureRateRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateZeroFailureRate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addZeroFailureRate(form.value).then(response => {
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
    return delZeroFailureRate(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/zeroFailureRate/export', {
    ...queryParams.value
  }, `zeroFailureRate_${new Date().getTime()}.xlsx`)
}

getList();

// 新增一个响应式变量来存储当前的导入函数
const currentImportFunction = ref(null);
/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
  currentImportFunction.value = importFile;
}


/** 导入按钮操作1 */
function handleImport1() {
  resetUpload();
  uploadDialogVisible.value = true;
  currentImportFunction.value = importFile1;
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



const isFirstImportCompleted = ref(false); // 第一个导入是否完成

// /** excel文件上传 */
// function uploadFile() {
//   if (inputFile.value && inputFile.value.files.length > 0) {
//     isLoading.value = true;
//     const file = inputFile.value.files[0];
//     console.log(inputFile.value);
//     console.log(file);
//     // let date = XXXdate;
//     // const formData = new FormData();
//     // formData.append('excelFile', file);


//     console.log("上传时间"+uploadDate.value);
//     let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
//     // formData.append('uploadMonth',date );

//     // formData.append('date', date);
//     let uploadFileDTO = {
//       'uploadMonth': date,
//       'excelFile': file
//     }
//     currentImportFunction.value(uploadFileDTO).then(() => {
//       proxy.$modal.msgSuccess("导入成功");

//   if (currentImportFunction.value === importFile) {
//     isFirstImportCompleted.value = true;
//   } else if (currentImportFunction.value === importFile1) {
//     // 第二个导入完成后自动重置
//     setTimeout(() => {
//       isFirstImportCompleted.value = false;
//       proxy.$modal.msgInfo("导入流程已完成并重置");
//     }, 100); // 1.5秒后自动重置
//   }

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
    
    // 只有零公里故障指标完成率导入时才检查月份冲突
    if (currentImportFunction.value === importFile) {
      // 先查询该月份是否有数据
      listZeroFailureRate({ uploadMonth: date, pageNum: 1, pageSize: 1 }).then(response => {
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
            uploadDialogVisible.value = false;
            resetUpload();
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
      // 产品过程故障率导入，直接导入不检查
      performImport(file, date);
    }
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
  
  currentImportFunction.value(uploadFileDTO).then(() => {
    proxy.$modal.msgSuccess("导入成功");

    if (currentImportFunction.value === importFile) {
      isFirstImportCompleted.value = true;
    } else if (currentImportFunction.value === importFile1) {
      // 第二个导入完成后自动重置
      setTimeout(() => {
        isFirstImportCompleted.value = false;
        proxy.$modal.msgInfo("导入流程已完成并重置");
      }, 100);
    }

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
  // }else {//修改
  //   data.form.files.push(file); // 将文件添加到数组中
  // }
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
