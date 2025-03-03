<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="关联id" prop="sopCombineId">
        <el-input v-model="queryParams.sopCombineId" placeholder="请输入关联id" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item label="设备编号" prop="sopNum">
        <el-input v-model="queryParams.sopNum" placeholder="请输入设备编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="设备名称" prop="sopName">
        <el-input v-model="queryParams.sopName" placeholder="请输入设备名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="SOP类型" prop="sopMaintenance">
        <el-input v-model="queryParams.sopMaintenance" placeholder="请输入SOP类型" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="上传时间" style="width: 308px">
        <el-date-picker v-model="daterangeUpTime" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="是否是历史版本" prop="ifHistory">
        <el-input v-model="queryParams.ifHistory" placeholder="请输入是否是历史版本" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['maintenanceTable:file:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['maintenanceTable:file:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['maintenanceTable:file:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['maintenanceTable:file:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备编号" align="center" prop="sopNum" />
      <el-table-column label="设备名称" align="center" prop="sopName" />
      <el-table-column label="保养文件" align="center" prop="sopMaintenance">
        <template #default="scope">
          <span v-html="formatFileInfo(scope.row.sopMaintenance)"></span>
        </template>
      </el-table-column>
      <el-table-column label="维修文件" align="center" prop="sopRepair">
        <template #default="scope">
          <span v-html="formatFileInfo(scope.row.sopRepair)"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:file:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['maintenanceTable:file:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改SOP文件管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="fileRef" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="关联id" prop="sopCombineId">
          <el-input v-model="form.sopCombineId" placeholder="请输入关联id" />
        </el-form-item> -->
        <el-form-item label="设备编号" prop="sopNum">
          <el-input v-model="form.sopNum" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="sopName">
          <el-input v-model="form.sopName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="保养文件" prop="sopMaintenance">
          <file-upload :limit="1" v-model="form.sopMaintenance" />
        </el-form-item>
        <el-form-item label="维修文件" prop="sopRepair">
          <file-upload v-model="form.sopRepair" />
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

<script setup name="Sop">
import { listFile, getFile, delFile, addFile, updateFile } from "@/api/device/fileTable/sop";
import { ElMessage } from 'element-plus'
const { proxy } = getCurrentInstance();

const fileList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeUpTime = ref([]);
const route = useRoute();
const router = useRouter();

const routerDeviceNum = route.query.deviceNum;

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    sopCombineId: null,
    sopNum: null,
    sopName: null,
    sopMaintenance: null,
    upTime: null,
    ifHistory: null,
    sopRepair: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

const handleRouteParams = () => {
  if (routerDeviceNum) {
    console.log('跳转进入---→Received deviceNum:', routerDeviceNum);
    data.queryParams.sopNum = routerDeviceNum;
    getList(); // 假设需要根据路由参数重新获取列表数据
  } else {
    console.log('非跳转进入');
    // 可以选择性地在这里添加其他逻辑
  }
};


onMounted(() => {
  handleRouteParams();
});


function formatFileInfo(fileInfo) {
  if (fileInfo == '' || fileInfo == null) {
    return fileInfo;
  }

  // 判断是否包含逗号（多个文件）
  const filePaths = fileInfo.includes(',') ? fileInfo.split(',') : [fileInfo];
  const formattedInfo = filePaths.map(path => {
    // 提取文件名（带扩展名）
    const fileNameWithExt = path.split('/').pop();

    // 去掉文件名中的时间戳部分
    const fileName = fileNameWithExt.split('_')[0]; // 取第一部分作为文件名
    const fileExt = fileNameWithExt.split('.').pop(); // 获取文件扩展名

    // 提取上传日期
    const uploadDateMatch = path.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
    let formattedDate = '';
    if (uploadDateMatch) {
      // 组合上传日期
      formattedDate = `${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`;
    }

    // 返回格式化后的字符串，用 [] 括起来
    return `{文件名：${fileName}.${fileExt} 上传日期：${formattedDate}}`;
  }).join('<br>'); // 如果有多个文件

  return formattedInfo;
}

/** 查询SOP文件管理列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeUpTime && '' != daterangeUpTime) {
    queryParams.value.params["beginUpTime"] = daterangeUpTime.value[0];
    queryParams.value.params["endUpTime"] = daterangeUpTime.value[1];
  }
  listFile(queryParams.value).then(response => {
    fileList.value = response.rows;
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
    sopFileId: null,
    sopCombineId: null,
    sopNum: null,
    sopName: null,
    sopMaintenance: null,
    upTime: null,
    ifHistory: null,
    sopRepair: null
  };
  proxy.resetForm("fileRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeUpTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.sopFileId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加SOP文件管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _sopFileId = row.sopFileId || ids.value
  getFile(_sopFileId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改SOP文件管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["fileRef"].validate(valid => {
    if (valid) {
      if (form.value.sopFileId != null) {
        updateFile(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addFile(form.value).then(response => {
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
  const _sopFileIds = row.sopFileId || ids.value;
  proxy.$modal.confirm('是否确认删除SOP文件管理编号为"' + _sopFileIds + '"的数据项？').then(function () {
    return delFile(_sopFileIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/file/export', {
    ...queryParams.value
  }, `file_${new Date().getTime()}.xlsx`)
}

getList();
</script>
