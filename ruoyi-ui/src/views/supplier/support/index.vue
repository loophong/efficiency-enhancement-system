<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="140px">
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
      <!-- <el-form-item label="上传时间" prop="uploadTime">
        <el-date-picker clearable
          v-model="queryParams.uploadTime"
          type="month"
          value-format="YYYY-MM"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item> -->
      
      <!-- <el-form-item label="新引进供应商代码" prop="supplierCodeNow">
        <el-input
          v-model="queryParams.supplierCodeNow"
          placeholder="请输入新引进供应商代码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="新引进供应商名称" prop="supplierNameNow">
        <el-input
          v-model="queryParams.supplierNameNow"
          placeholder="请输入新引进供应商名称"
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
          v-hasPermi="['supplier:support:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:support:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:support:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:support:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:support:import']">导入
              </el-button>
            </el-col>

      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-download" 
        @click="handleDownload" size="mini" plain v-if="true">下载模版文件
        </el-button>
      </el-col>


      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="降本金额" align="center" prop="reduceMoney" />
      <el-table-column label="降本占比" align="center" prop="percentage" />
      <el-table-column label="得分" align="center" prop="score" />
      <el-table-column label="上传时间" align="center" prop="uploadTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="原供应商代码" align="center" prop="supplierCodePast" />
      <el-table-column label="原供应商名称" align="center" prop="supplierNamePast" />
      <el-table-column label="新引进供应商代码" align="center" prop="supplierCodeNow" />
      <el-table-column label="新引进供应商名称" align="center" prop="supplierNameNow" />
      <el-table-column label="降幅" align="center" prop="decrease" />
      <el-table-column label="总采购量" align="center" prop="totalPurchases" />
      <el-table-column label="单价" align="center" prop="price" />
      <el-table-column label="供货金额=单价*总采购量" align="center" prop="supplyAmount" />
      <el-table-column label="价格统计日期" align="center" prop="priceStatisticDate" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:support:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:support:remove']">删除</el-button>
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

    <!-- 添加或修改降本支持对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="supportRef" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item> -->
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-select v-model="form.supplierCode" clearable filterable placeholder="请选择或输入供应商代码" style="width: 240px">
            <el-option v-for="item in qualifiedList" :key="item.value" :label="item.value" :value="item.value" />
            </el-select>
        </el-form-item>

        <el-form-item label="供应商名称" prop="supplierName">
          <el-select v-model="form.supplierName" clearable filterable placeholder="请选择或输入供应商名称" style="width: 240px">
            <el-option v-for="item in qualifiedList" :key="item.value" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>

        <el-form-item label="降本金额" prop="reduceMoney">
          <el-input v-model="form.reduceMoney" placeholder="请输入降本金额" />
        </el-form-item>
        <el-form-item label="降本占比" prop="percentage">
          <el-input v-model="form.percentage" placeholder="请输入降本占比" />
        </el-form-item>
        <!-- <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item> -->
        <el-form-item label="上传时间" prop="uploadTime">
          <el-date-picker clearable
            v-model="form.uploadTime"
            type="month"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="原供应商代码" prop="supplierCodePast">
          <el-input v-model="form.supplierCodePast" placeholder="请输入原供应商代码" />
        </el-form-item>
        <el-form-item label="原供应商名称" prop="supplierNamePast">
          <el-input v-model="form.supplierNamePast" placeholder="请输入原供应商名称" />
        </el-form-item>
        <el-form-item label="新引进供应商代码" prop="supplierCodeNow">
          <el-input v-model="form.supplierCodeNow" placeholder="请输入新引进供应商代码" />
        </el-form-item>
        <el-form-item label="新引进供应商名称" prop="supplierNameNow">
          <el-input v-model="form.supplierNameNow" placeholder="请输入新引进供应商名称" />
        </el-form-item>
        <el-form-item label="降幅" prop="decrease">
          <el-input v-model="form.decrease" placeholder="请输入降幅" />
        </el-form-item>
        <el-form-item label="总采购量" prop="totalPurchases">
          <el-input v-model="form.totalPurchases" placeholder="请输入总采购量" />
        </el-form-item>
        <el-form-item label="单价" prop="price">
          <el-input v-model="form.price" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="供货金额=单价*总采购量" prop="supplyAmount">
          <el-input v-model="form.supplyAmount" placeholder="请输入供货金额=单价*总采购量" />
        </el-form-item>
        <el-form-item label="价格统计日期" prop="priceStatisticDate">
          <el-input v-model="form.priceStatisticDate" placeholder="请输入价格统计日期" />
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
    <el-dialog title="导入降本支持表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">降本支持表单</span>
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

<script setup name="Support">
import { listSupport, getSupport, delSupport, addSupport, updateSupport,importFile } from "@/api/supplier/support";
import dayjs from 'dayjs';
import {handleTrueDownload} from "@/api/tool/gen"


import { all } from "@/api/supplier/qualified";
import { watch, ref, reactive } from 'vue';

const { proxy } = getCurrentInstance();

const supportList = ref([]);
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
    reduceMoney: null,
    percentage: null,
    score: null,
    uploadTime: null,
    supplierCodePast: null,
    supplierNamePast: null,
    supplierCodeNow: null,
    supplierNameNow: null,
    decrease: null,
    totalPurchases: null,
    price: null,
    supplyAmount: null,
    priceStatisticDate: null
  },
  rules: {
    supplierCode: [
      { required: true, message: "供应商编码不能为空", trigger: "blur" }
    ],
    supplierName: [
      { required: true, message: "供应商名称不能为空", trigger: "blur" }
    ],
    reduceMoney:[
      { required: true, message: "降本金额不能为空", trigger: "blur" }
    ],
    percentage:[
      { required: true, message: "降本占比不能为空", trigger: "blur" }
    ],
    uploadTime:[
      { required: true, message: "上传时间不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

function handleDownload() {
  const url = "/profile/excel_templates/supply/降本支持.xlsx";
  handleTrueDownload(url);
}

/** 查询降本支持列表 */
function getList() {
  loading.value = true;
  listSupport(queryParams.value).then(response => {
    supportList.value = response.rows;
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
    reduceMoney: null,
    percentage: null,
    score: null,
    uploadTime: null,
    supplierCodePast: null,
    supplierNamePast: null,
    supplierCodeNow: null,
    supplierNameNow: null,
    decrease: null,
    totalPurchases: null,
    price: null,
    supplyAmount: null,
    priceStatisticDate: null
  };
  proxy.resetForm("supportRef");
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
  title.value = "添加降本支持";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSupport(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改降本支持";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["supportRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSupport(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSupport(form.value).then(response => {
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
    return delSupport(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/support/export', {
    ...queryParams.value
  }, `support_${new Date().getTime()}.xlsx`)
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
    // // formData.append('date', date);
    console.log("上传时间"+uploadDate.value);
    let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
    // formData.append('uploadMonth',date );

    // formData.append('date', date);
    let uploadFileDTO = {
      'uploadMonth': date,
      'excelFile': file
    }

    importFile(uploadFileDTO).then(() => {
      proxy.$modal.msgSuccess("数据已更新");
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
