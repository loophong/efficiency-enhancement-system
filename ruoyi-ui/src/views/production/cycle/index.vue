<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="系列名称" prop="series">
        <el-input v-model="queryParams.series" placeholder="请输入系列名称" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="吨位" prop="tonnage">
        <el-input v-model="queryParams.tonnage" placeholder="请输入吨位" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleModel">
        <el-input v-model="queryParams.vehicleModel" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="产能车型" prop="capacityVehicleModel">
        <el-input v-model="queryParams.capacityVehicleModel" placeholder="请输入产能车型" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
<!--      <el-form-item label="生产周期" prop="productionCycle">-->
<!--        <el-input v-model="queryParams.productionCycle" placeholder="请输入生产周期" clearable-->
<!--                  @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
      <el-form-item label="生产周期" prop="productionCycle">
        <el-input-number
            v-model="queryParams.productionCycle"
            placeholder="请输入生产周期"
            clearable
            @keyup.enter="handleQuery"
            :min="0"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:cycle:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:cycle:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:cycle:remove']">删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['production:cycle:export']">-->
<!--          导出-->
<!--        </el-button>-->
<!--      </el-col>-->

      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload" v-hasPermi="['production:cycle:import']">导入
        </el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="cycleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="id" align="center" prop="id"/>-->
      <el-table-column label="系列名称" align="center" prop="series"/>
      <el-table-column label="吨位" align="center" prop="tonnage"/>
      <el-table-column label="车型" align="center" prop="vehicleModel"/>
      <el-table-column label="产能车型" align="center" prop="capacityVehicleModel"/>
      <el-table-column label="生产周期" align="center" prop="productionCycle"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:cycle:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:cycle:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改产能对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="cycleRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="系列名称" prop="series">
          <el-input v-model="form.series" placeholder="请输入系列名称"/>
        </el-form-item>
        <el-form-item label="吨位" prop="tonnage">
          <el-input v-model="form.tonnage" placeholder="请输入吨位"/>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleModel">
          <el-input v-model="form.vehicleModel" placeholder="请输入车型"/>
        </el-form-item>
        <!--        <el-form-item label="产能车型" prop="capacityVehicleModel">-->
        <!--          <el-input v-model="form.capacityVehicleModel" placeholder="请输入产能车型"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="产能车型" prop="capacityVehicleModel">
          <el-select v-model="form.capacityVehicleModel" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in capacityList" :key="item.value" :label="item.label"
                       :value="item.value"/>
          </el-select>
        </el-form-item>

<!--        <el-form-item label="生产周期" prop="productionCycle">-->
<!--          <el-input v-model="form.productionCycle" placeholder="请输入生产周期"/>-->
<!--        </el-form-item>-->
        <el-form-item label="生产周期" prop="productionCycle">
          <el-input-number
              v-model="form.productionCycle"
              placeholder="请输入生产周期"
              clearable
              :min="0"/>
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
    <el-dialog title="导入生产周期表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">生产周期表</span>
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

<script setup name="Cycle">
import {listCycle, getCycle, delCycle, addCycle, updateCycle, importFile} from "@/api/production/cycle";
import {all} from "@/api/production/capacity"

const {proxy} = getCurrentInstance();

const cycleList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 上传文件相关
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    series: null,
    tonnage: null,
    vehicleModel: null,
    capacityVehicleModel: null,
    productionCycle: null
  },
  rules: {
    series: [
      {required: true, message: "系列名称不能为空", trigger: "blur"}
    ],
    tonnage: [
      {required: true, message: "吨位不能为空", trigger: "blur"}
    ],
    vehicleModel: [
      {required: true, message: "车型不能为空", trigger: "blur"}
    ],
    capacityVehicleModel: [
      {required: true, message: "产能车型不能为空", trigger: "blur"}
    ],
    // productionCycle: [
    //   {required: true, message: "生产周期不能为空", trigger: "blur"}
    // ]
    // 生产周期必须为大于0的整数类型
    productionCycle: [
      { required: true, message: "生产周期不能为空", trigger: "blur" },
      { validator: (rule, value, callback) => {
          if (!Number.isInteger(Number(value)) || Number(value) <= 0) {
            callback(new Error("生产周期必须为大于0的整数"));
          } else {
            callback();
          }
        }, trigger: "blur"
      }
    ]

  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询生产周期列表 */
function getList() {
  loading.value = true;
  listCycle(queryParams.value).then(response => {
    cycleList.value = response.rows;
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
    vehicleModel: null,
    capacityVehicleModel: null,
    productionCycle: null
  };
  proxy.resetForm("cycleRef");
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
  title.value = "添加产能";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCycle(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改产能";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["cycleRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCycle(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCycle(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除产能编号为"' + _ids + '"的数据项？').then(function () {
    return delCycle(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/cycle/export', {
    ...queryParams.value
  }, `cycle_${new Date().getTime()}.xlsx`)
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

const capacityList = ref([]);
// 创建页面时调用
onMounted(() => {
  all().then(response => {
    response.data.capacityEntities.forEach((item) => {
      capacityList.value.push({
        'label': item.capacityType,
        'value': item.capacityType,
      })
    });
  });
});


</script>
