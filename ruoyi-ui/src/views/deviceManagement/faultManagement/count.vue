<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名" prop="indicatorName">
        <el-input v-model="queryParams.indicatorName" placeholder="请输入项目名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="目标值" prop="indicatorTarget">
        <el-input v-model="queryParams.indicatorTarget" placeholder="请输入目标值" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="日期" style="width: 308px">
        <el-date-picker v-model="daterangeIndicatorTime" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="一月数据" prop="indicatorJan">
        <el-input v-model="queryParams.indicatorJan" placeholder="请输入一月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="二月数据" prop="indicatorFeb">
        <el-input v-model="queryParams.indicatorFeb" placeholder="请输入二月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="三月数据" prop="indicatorMar">
        <el-input v-model="queryParams.indicatorMar" placeholder="请输入三月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="四月数据" prop="indicatorApr">
        <el-input v-model="queryParams.indicatorApr" placeholder="请输入四月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="五月数据" prop="indicatorMay">
        <el-input v-model="queryParams.indicatorMay" placeholder="请输入五月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="六月数据" prop="indicatorJun">
        <el-input v-model="queryParams.indicatorJun" placeholder="请输入六月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="七月数据" prop="indicatorJul">
        <el-input v-model="queryParams.indicatorJul" placeholder="请输入七月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="八月数据" prop="indicatorAug">
        <el-input v-model="queryParams.indicatorAug" placeholder="请输入八月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="九月数据" prop="indicatorSep">
        <el-input v-model="queryParams.indicatorSep" placeholder="请输入九月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十月数据" prop="indicatorOct">
        <el-input v-model="queryParams.indicatorOct" placeholder="请输入十月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十一月数据" prop="indicatorNov">
        <el-input v-model="queryParams.indicatorNov" placeholder="请输入十一月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十二月数据" prop="indicatorDec">
        <el-input v-model="queryParams.indicatorDec" placeholder="请输入十二月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['device:count:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['device:count:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['device:count:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!--Excel 参数导入 -->
        <el-button type="primary" icon="UploadFilled" @click="showDialog = true" size="mini" plain>导入
        </el-button>
        <el-dialog title="导入Excel文件" v-model="showDialog" width="30%" @close="resetFileInput">
          <el-form :model="form" ref="formRef" label-width="90px">
          </el-form>
          <div class="upload-area">
            <i class="el-icon-upload"></i>
            <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
          </div>
          <span class="dialog-footer">
            <el-button @click="showDialog = false">取 消</el-button>
            <el-button type="primary" @click="fileSend">确 定</el-button>
            <!-- <el-button type="primary" :loading="true">上传中</el-button> -->
          </span>
        </el-dialog>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['device:count:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="countList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="indicatorId" /> -->
      <el-table-column label="项目名" align="center" prop="indicatorName" />
      <el-table-column label="目标值" align="center" prop="indicatorTarget" />
      <el-table-column label="日期" align="center" prop="indicatorTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.indicatorTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="一月数据" align="center" prop="indicatorJan" />
      <el-table-column label="二月数据" align="center" prop="indicatorFeb" />
      <el-table-column label="三月数据" align="center" prop="indicatorMar" />
      <el-table-column label="四月数据" align="center" prop="indicatorApr" />
      <el-table-column label="五月数据" align="center" prop="indicatorMay" />
      <el-table-column label="六月数据" align="center" prop="indicatorJun" />
      <el-table-column label="七月数据" align="center" prop="indicatorJul" />
      <el-table-column label="八月数据" align="center" prop="indicatorAug" />
      <el-table-column label="九月数据" align="center" prop="indicatorSep" />
      <el-table-column label="十月数据" align="center" prop="indicatorOct" />
      <el-table-column label="十一月数据" align="center" prop="indicatorNov" />
      <el-table-column label="十二月数据" align="center" prop="indicatorDec" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['device:count:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['device:count:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改设备指标分析对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="countRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="项目名" prop="indicatorName">
          <el-input v-model="form.indicatorName" placeholder="请输入项目名" />
        </el-form-item>
        <el-form-item label="目标值" prop="indicatorTarget">
          <el-input v-model="form.indicatorTarget" placeholder="请输入目标值" />
        </el-form-item>
        <el-form-item label="日期" prop="indicatorTime">
          <el-date-picker clearable v-model="form.indicatorTime" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一月数据" prop="indicatorJan">
          <el-input v-model="form.indicatorJan" placeholder="请输入一月数据" />
        </el-form-item>
        <el-form-item label="二月数据" prop="indicatorFeb">
          <el-input v-model="form.indicatorFeb" placeholder="请输入二月数据" />
        </el-form-item>
        <el-form-item label="三月数据" prop="indicatorMar">
          <el-input v-model="form.indicatorMar" placeholder="请输入三月数据" />
        </el-form-item>
        <el-form-item label="四月数据" prop="indicatorApr">
          <el-input v-model="form.indicatorApr" placeholder="请输入四月数据" />
        </el-form-item>
        <el-form-item label="五月数据" prop="indicatorMay">
          <el-input v-model="form.indicatorMay" placeholder="请输入五月数据" />
        </el-form-item>
        <el-form-item label="六月数据" prop="indicatorJun">
          <el-input v-model="form.indicatorJun" placeholder="请输入六月数据" />
        </el-form-item>
        <el-form-item label="七月数据" prop="indicatorJul">
          <el-input v-model="form.indicatorJul" placeholder="请输入七月数据" />
        </el-form-item>
        <el-form-item label="八月数据" prop="indicatorAug">
          <el-input v-model="form.indicatorAug" placeholder="请输入八月数据" />
        </el-form-item>
        <el-form-item label="九月数据" prop="indicatorSep">
          <el-input v-model="form.indicatorSep" placeholder="请输入九月数据" />
        </el-form-item>
        <el-form-item label="十月数据" prop="indicatorOct">
          <el-input v-model="form.indicatorOct" placeholder="请输入十月数据" />
        </el-form-item>
        <el-form-item label="十一月数据" prop="indicatorNov">
          <el-input v-model="form.indicatorNov" placeholder="请输入十一月数据" />
        </el-form-item>
        <el-form-item label="十二月数据" prop="indicatorDec">
          <el-input v-model="form.indicatorDec" placeholder="请输入十二月数据" />
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

<script setup name="Count">
import { listCount, getCount, delCount, addCount, updateCount, uploadFile } from "@/api/device/maintenanceTable/count";

const { proxy } = getCurrentInstance();

const countList = ref([]);
const open = ref(false);
const showDialog = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeIndicatorTime = ref([]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    indicatorName: null,
    indicatorTarget: null,
    indicatorTime: null,
    indicatorJan: null,
    indicatorFeb: null,
    indicatorMar: null,
    indicatorApr: null,
    indicatorMay: null,
    indicatorJun: null,
    indicatorJul: null,
    indicatorAug: null,
    indicatorSep: null,
    indicatorOct: null,
    indicatorNov: null,
    indicatorDec: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询设备指标分析列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeIndicatorTime && '' != daterangeIndicatorTime) {
    queryParams.value.params["beginIndicatorTime"] = daterangeIndicatorTime.value[0];
    queryParams.value.params["endIndicatorTime"] = daterangeIndicatorTime.value[1];
  }
  listCount(queryParams.value).then(response => {
    countList.value = response.rows;
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
    indicatorId: null,
    indicatorName: null,
    indicatorTarget: null,
    indicatorTime: null,
    indicatorJan: null,
    indicatorFeb: null,
    indicatorMar: null,
    indicatorApr: null,
    indicatorMay: null,
    indicatorJun: null,
    indicatorJul: null,
    indicatorAug: null,
    indicatorSep: null,
    indicatorOct: null,
    indicatorNov: null,
    indicatorDec: null
  };
  proxy.resetForm("countRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeIndicatorTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.indicatorId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备指标分析";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _indicatorId = row.indicatorId || ids.value
  getCount(_indicatorId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备指标分析";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["countRef"].validate(valid => {
    if (valid) {
      if (form.value.indicatorId != null) {
        updateCount(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCount(form.value).then(response => {
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
  const _indicatorIds = row.indicatorId || ids.value;
  proxy.$modal.confirm('是否确认删除设备指标分析编号为"' + _indicatorIds + '"的数据项？').then(function () {
    return delCount(_indicatorIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}


// 导入excel，检查文件类型
function checkFile() {
  const file = proxy.$refs["fileInput"].files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    this.$message.error("只能上传 Excel 文件！");
    // this.$refs.fileInput.value = ""; // 清空文件选择框
  }
}
//导入excel，取消按钮绑定取消所选的xlsx
// function resetFileInput() {
//   this.$refs.fileInput.value = "";
// }
/** 导入按钮 */
function fileSend() {
  const fileInput = proxy.$refs["fileInput"];
  // console.log(proxy.$refs["fileInput"])
  // if (!fileInput || !fileInput.files.length) {
  //   ElMessage.warning('请选择要上传的文件');
  //   return;
  // }

  const file = fileInput.files[0];
  console.log('Selected file:', file);
  // console.log(file)
  const formData = new FormData();
  formData.append("excelFile", file);
  formData.append("yearAndMonth", '2024-12-12');
  console.log({ formData })
  // 使用如下方法打印出 formData 的内容
  for (let pair of formData.entries()) {
    console.log(pair[0] + ': ' + pair[1]);
  }

  // isLoading.value = true;
  uploadFile(formData, `/fault/indicator/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      // this.getList();
      // this.showDialog = false;
      // this.isLoading = false;
    })
    .catch(error => {
      // 处理上传失败的情况
      ElMessage({
        message: `上传失败:${error}`,
        type: 'error',
      });
      // this.$message.error("上传失败，请重试");
      // this.isLoading = false;
    });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('device/count/export', {
    ...queryParams.value
  }, `count_${new Date().getTime()}.xlsx`)
}

getList();
</script>
