<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode" style="width: 320px;">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商代码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName"  style="width: 320px;">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="数据合格率" prop="quantityPassRate"  style="width: 320px;">
        <el-input
          v-model="queryParams.quantityPassRate"
          placeholder="请输入数据合格率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传月份" prop="updateMonth" style="width: 320px;">
        <el-date-picker clearable
          v-model="queryParams.updateMonth"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择上次月份">
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="填报人" prop="addName">
        <el-input
          v-model="queryParams.addName"
          placeholder="请输入填报人"
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
          v-hasPermi="['supplier:onetimesimple:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:onetimesimple:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:onetimesimple:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:onetimesimple:export']"
        >导出</el-button>
      </el-col>

      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:onetimesimple:import']">导入
              </el-button>
            </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="onetimesimpleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="数据合格率" align="center" prop="quantityPassRate" />
      <el-table-column label="上传月份" align="center" prop="updateMonth" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateMonth, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分数" align="center" prop="score" />
      <!-- <el-table-column label="备选1" align="center" prop="one" />
      <el-table-column label="备选2" align="center" prop="two" /> -->
      <!-- <el-table-column label="填报人" align="center" prop="addName" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:onetimesimple:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:onetimesimple:remove']">删除</el-button>
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

    <!-- 添加或修改一次交检合格率对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="onetimesimpleRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="数据合格率" prop="quantityPassRate">
          <el-input v-model="form.quantityPassRate" placeholder="请输入数据合格率" />
        </el-form-item>
        <el-form-item label="上传月份" prop="updateMonth">
          <!-- <el-date-picker clearable
            v-model="form.updateMonth"
                      type="month"
            value-format="yyyy-MM"
            placeholder="请选择上次月份">
          </el-date-picker> -->
          <el-date-picker
          v-model="form.updateMonth"
          type="month"
        
          placeholder="Pick a month"
        />
        </el-form-item>
        
        <!-- <el-form-item label="填报人" prop="addName">
          <el-input v-model="form.addName" placeholder="请输入填报人" />
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
    <el-dialog title="导入一次交检合格率表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">合格率表单</span>
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

<script setup name="Onetimesimple">
import { listOnetimesimple, getOnetimesimple, delOnetimesimple, addOnetimesimple, updateOnetimesimple,importFile } from "@/api/supplier/onetimesimple";

const { proxy } = getCurrentInstance();
import dayjs from 'dayjs';

const onetimesimpleList = ref([]);
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
    // files: [] // 添加一个数组来存储文件  修改
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    quantityPassRate: null,
    updateMonth: null,
    addName: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

// // 定义 parseTime 函数
// function parseTime(time, format) {
//   if (time == null || time === '') {
//     return '';
//   }
//   return dayjs(time).format(format);
// }

/** 查询一次交检合格率-简化版列表 */
function getList() {
  loading.value = true;
  listOnetimesimple(queryParams.value).then(response => {
    onetimesimpleList.value = response.rows;
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
    quantityPassRate: null,
    updateMonth: null,
    addName: null
  };
  proxy.resetForm("onetimesimpleRef");
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
  title.value = "添加一次交检合格率";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOnetimesimple(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改一次交检合格率";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["onetimesimpleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOnetimesimple(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOnetimesimple(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除一次交检合格率编号为"' + _ids + '"的数据项？').then(function() {
    return delOnetimesimple(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/onetimesimple/export', {
    ...queryParams.value
  }, `onetimesimple_${new Date().getTime()}.xlsx`)
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
    // const formData = new FormData();
    // formData.append('excelFile', file);


    console.log("上传时间"+uploadDate.value);
    let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
    // formData.append('uploadMonth',date );

    // formData.append('date', date);
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
  // }else {//修改
  //   data.form.files.push(file); // 将文件添加到数组中
  // }
}
}
/** excel文件上传 */
// function uploadFile() {
//   console.log('开始上传文件');
//   if (inputFile.value && inputFile.value.files.length > 0) {
//     console.log('文件已选择');
//     isLoading.value = true;
//     const file = inputFile.value.files[0];
//     console.log('inputFile:', inputFile.value);
//     console.log('file:', file);
//     // let date = XXXdate;
//     const formData = new FormData();

//     formData.append('excelFile', file);
//     // formData.append('date', date);

//     console.log('开始上传文件到服务器');
//     importFile(formData).then(() => {
//       console.log('文件上传成功');
//       proxy.$modal.msgSuccess("导入成功");
//       console.log('开始获取列表数据');
//       getList().then(data => {
//         console.log('获取到的列表数据:', data);
//       }).catch(error => {
//         console.error('获取列表数据失败:', error);
//       });
//       console.log('关闭上传对话框');
//       uploadDialogVisible.value = false;
//       console.log('设置加载状态为 false');
//       isLoading.value = false;
//     }).catch(() => {
//       console.log('文件上传失败');
//       proxy.$modal.msgError("导入失败");
//       console.log('设置加载状态为 false');
//       isLoading.value = false;
//     }).finally(() => {
//       console.log('重置上传状态');
//       resetUpload();
//     });
//   } else {
//     console.log('没有选择文件');
//     proxy.$modal.msgError("请选择文件");
//   }
// }
</script>
