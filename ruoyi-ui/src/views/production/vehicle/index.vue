<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="系列名称" prop="series">
        <el-input v-model="queryParams.series" placeholder="请输入系列名称" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="吨位" prop="tonnage">
        <el-input v-model="queryParams.tonnage" placeholder="请输入吨位" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleType">
        <el-input v-model="queryParams.vehicleType" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:vehicle:add']">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:vehicle:edit']">
          修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:vehicle:remove']">
          删除
        </el-button>
      </el-col>

      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['production:vehicle:export']">-->
      <!--          导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->

      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload" v-hasPermi="['production:vehicle:import']">
          导入
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="primary" icon="Download" @click="handleDownload"  plain v-if="true">下载模版文件
        </el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="vehicleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="${comment}" align="center" prop="id"/>-->
      <el-table-column label="系列名称" align="center" prop="series" width="150"/>
      <el-table-column label="吨位" align="center" prop="tonnage" width="120"/>
      <el-table-column label="车型" align="center" prop="vehicleType" width="200"/>
      <el-table-column label="发动机/主要配置" align="center" prop="mainConfiguration"/>
      <el-table-column label="标配：阀片、电瓶、轮胎" align="center" prop="standardEquipment"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:vehicle:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:vehicle:remove']">删除
          </el-button>
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

    <!-- 添加或修改车型对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="vehicleRef" :model="form" :rules="rules" label-width="180px">
        <el-form-item label="系列名称" prop="series">
          <el-input v-model="form.series" placeholder="请输入系列名称"/>
        </el-form-item>
        <el-form-item label="吨位" prop="tonnage">
          <el-input v-model="form.tonnage" placeholder="请输入吨位"/>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleType">
          <el-input v-model="form.vehicleType" placeholder="请输入车型"/>
        </el-form-item>
        <el-form-item label="发动机/主要配置" prop="mainConfiguration">
          <el-input v-model="form.mainConfiguration" type="textarea" :rows="4" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="标配：阀片、电瓶、轮胎" prop="standardEquipment">
          <el-input v-model="form.standardEquipment" type="textarea" :rows="4" placeholder="请输入内容"/>
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
    <el-dialog title="导入车辆分类信息" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">车辆分类表</span>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <input type="file" ref="inputFile" @change="checkFile"/>
          <br>
        </el-form-item>
      </el-form>

      <!--      <i class="el-icon-upload"></i>-->

      <!--      <div class="upload-demo" ref="uploadRef" style="margin-bottom: 20px;">-->
      <!--        <input type="file" ref="inputFile" @change="checkFile"/>-->
      <!--      </div>-->

      <span slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="cancelUpload">取 消</el-button>
        <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
        <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script setup name="Vehicle">
import {listVehicle, getVehicle, delVehicle, addVehicle, updateVehicle, importFile} from "@/api/production/vehicle";
import {reactive} from "vue";
import {handleTrueDownload} from "@/utils/fileMonitor.js";

const {proxy} = getCurrentInstance();

const vehicleList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 上传文件
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);


// export default {
//   setup() {
//     const uploadDialogVisible = ref(false);
//
//     const updateUser = () => {
//       user.name = 'Jane Doe';
//       user.age = 25;
//     };
//
//     return {
//       uploadDialogVisible,
//       updateUser
//     }
//   }
// }

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    series: null,
    tonnage: null,
    vehicleType: null,
    mainConfiguration: null,
    standardEquipment: null
  },
  rules: {
    series: [
      {required: true, message: "系列名称不能为空", trigger: "blur"}
    ],
    tonnage: [
      {required: true, message: "吨位不能为空", trigger: "blur"}
    ],
    vehicleType: [
      {required: true, message: "车型不能为空", trigger: "blur"}
    ],
    mainConfiguration: [
      {required: true, message: "发动机/主要配置", trigger: "blur"}
    ],
    standardEquipment: [
      {required: true, message: "标配：阀片、电瓶、轮胎", trigger: "blur"}
    ]
  }

});

const {queryParams, form, rules} = toRefs(data);

/** 查询车型列表 */
function getList() {
  loading.value = true;
  listVehicle(queryParams.value).then(response => {
    vehicleList.value = response.rows;
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
    series: null,
    tonnage: null,
    vehicleType: null,
    mainConfiguration: null,
    standardEquipment: null
  };
  proxy.resetForm("vehicleRef");
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
  title.value = "添加车型";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getVehicle(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改车型";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["vehicleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateVehicle(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addVehicle(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除车型编号为"' + _ids + '"的数据项？').then(function () {
    return delVehicle(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/vehicle/export', {
    ...queryParams.value
  }, `vehicle_${new Date().getTime()}.xlsx`)
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

function handleDownload() {
  const url = "/profile/excel_templates/production/在产车型信息导入模板.xlsx";
  handleTrueDownload(url);
}

</script>
