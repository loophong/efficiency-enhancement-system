<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="关联保养id" prop="basicCombineMaintenance">
        <el-input v-model="queryParams.basicCombineMaintenance" placeholder="请输入关联保养id" clearable
          @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item label="设备编号" prop="deviceNum">
        <el-input v-model="queryParams.deviceNum" placeholder="请输入设备编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="设备名称" prop="deviceName">
        <el-input v-model="queryParams.deviceName" placeholder="请输入设备名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="上传日期" prop="upTime">
        <el-date-picker clearable v-model="queryParams.upTime" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择上传日期">
        </el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="版本号" prop="versionId">
        <el-input v-model="queryParams.versionId" placeholder="请输入版本号" clearable @keyup.enter="handleQuery" />
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['file:basic:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['file:basic:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['file:basic:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['file:basic:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange" border stripe>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="basicFileId" /> -->
      <!-- <el-table-column label="关联保养id" align="center" prop="basicCombineMaintenance" /> -->
      <!-- <el-table-column label="关联维修id" align="center" prop="basicCombineRepair" /> -->
      <el-table-column label="设备编号" align="center" prop="deviceNum" width="180" />
      <el-table-column label="设备名称" align="center" prop="deviceName" />
      <el-table-column label="档案类型" align="center" prop="fileType" />
      <el-table-column label="文件信息" align="center" prop="fileInfoRepair" width="500">
        <template #default="scope">
          <span v-html="formatFileInfo(scope.row.fileInfoRepair)"></span>
        </template>
      </el-table-column>
      <el-table-column label="版本号" align="center" prop="versionId" width="120" />
      <!-- <el-table-column label="是否是历史版本" align="center" prop="ifHistory" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['file:basic:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['file:basic:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改设备基础档案对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="fileRef" :model="form" :rules="rules" label-width="120px">
        <!-- <el-form-item label="关联保养id" prop="basicCombineMaintenance">
          <el-input v-model="form.basicCombineMaintenance" placeholder="请输入关联保养id" />
        </el-form-item> -->
        <el-form-item label="设备编号" prop="deviceNum">
          <el-input v-model="form.deviceNum" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="档案类型" prop="fileType">
          <el-input v-model="form.fileType" placeholder="请输入档案类型" />
        </el-form-item>
        <!-- <el-form-item label="上传日期" prop="upTime">
          <el-date-picker clearable v-model="form.upTime" type="date" value-format="YYYY-MM-DD" placeholder="请选择上传日期">
          </el-date-picker>
        </el-form-item> -->
        <!-- <el-form-item label="版本号" prop="versionId">
          <el-input v-model="form.versionId" placeholder="请输入版本号" />
        </el-form-item> -->
        <!-- <el-form-item label="保养文件信息" prop="fileInfo">
          <file-upload :limit="1" v-model="form.fileInfo" />
        </el-form-item> -->
        <el-form-item label="文件信息" prop="fileInfoRepair">
          <file-upload v-model="form.fileInfoRepair" />
        </el-form-item>
        <!-- <el-form-item label="是否是历史版本" prop="ifHistory">
          <el-input v-model="form.ifHistory" placeholder="请输入是否是历史版本" />
        </el-form-item> -->
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

<script setup name="Basic">
import { listFile, getFile, delFile, addFile, updateFile } from "@/api/device/fileTable/basic";
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

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    basicCombineMaintenance: null,
    basicCombineRepair: null,
    deviceNum: null,
    deviceName: null,
    fileType: null,
    upTime: null,
    versionId: null,
    fileInfo: null,
    fileInfoRepair: null,
    ifHistory: null
  },
  rules: {
    deviceNum: [
      { required: true, message: "设备编号不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

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


/** 查询设备基础档案列表 */
function getList() {
  loading.value = true;
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
    basicFileId: null,
    basicCombineMaintenance: null,
    basicCombineRepair: [],
    deviceNum: null,
    deviceName: null,
    fileType: null,
    upTime: null,
    versionId: null,
    fileInfo: null,
    fileInfoRepair: null,
    ifHistory: null
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
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.basicFileId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备基础档案";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _basicFileId = row.basicFileId || ids.value
  getFile(_basicFileId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备基础档案";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["fileRef"].validate(valid => {
    if (valid) {
      form.value.basicCombineRepair = form.value.basicCombineRepair.join(",");
      if (form.value.basicFileId != null) {
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
  const _basicFileIds = row.basicFileId || ids.value;
  proxy.$modal.confirm('是否确认删除设备基础档案编号为"' + _basicFileIds + '"的数据项？').then(function () {
    return delFile(_basicFileIds);
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
