<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="班组" prop="teamName">
        <el-input
          v-model="queryParams.teamName"
          placeholder="请输入班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter="handleQuery"
        />
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
          v-hasPermi="['security:OccupationaPersonnelList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:OccupationaPersonnelList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:OccupationaPersonnelList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:OccupationaPersonnelList:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:OccupationaPersonnelList:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Download"
          @click="handleDownloadTemplate"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="OccupationaPersonnelListList"
      @selection-change="handleSelectionChange"
      :span-method="arraySpanMethod"
      border
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="班组" align="center" prop="teamName" width="200">
        <template #default="scope">
          <span>{{ scope.row.teamName || '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="name" width="100" />
      <el-table-column label="性别" align="center" prop="gender" width="80" />
      <el-table-column label="工种" align="center" prop="jobType" width="120" />
      <el-table-column label="接害因素（毒害、噪声、高温、粉尘、特殊工种）" align="center" prop="exposureFactors" width="300" />
      <el-table-column label="主要岗位在岗人员职业健康检查情况" align="center">
        <el-table-column label="上岗前" align="center" prop="preJobHealthCheckup" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.preJobHealthCheckup === '是'" type="success" size="small">
              <el-icon><Check /></el-icon>
            </el-tag>
            <el-tag v-else type="info" size="small">
              <el-icon><Close /></el-icon>
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="在岗中" align="center" prop="duringJobHealthCheckup" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.duringJobHealthCheckup === '是'" type="success" size="small">
              <el-icon><Check /></el-icon>
            </el-tag>
            <el-tag v-else type="info" size="small">
              <el-icon><Close /></el-icon>
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="离岗时" align="center" prop="postJobHealthCheckup" width="100">
          <template #default="scope">
            <el-tag v-if="scope.row.postJobHealthCheckup === '是'" type="success" size="small">
              <el-icon><Check /></el-icon>
            </el-tag>
            <el-tag v-else type="info" size="small">
              <el-icon><Close /></el-icon>
            </el-tag>
          </template>
        </el-table-column>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" width="150" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180" fixed="right">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:OccupationaPersonnelList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:OccupationaPersonnelList:remove']">删除</el-button>
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

    <!-- 添加或修改危险职业岗位人员清单及管理台帐对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="OccupationaPersonnelListRef" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="班组" prop="teamName">
              <el-input v-model="form.teamName" placeholder="请输入班组" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工种" prop="jobType">
              <el-input v-model="form.jobType" placeholder="请输入工种" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="接害因素" prop="exposureFactors">
          <el-input v-model="form.exposureFactors" type="textarea" :rows="3" placeholder="请输入接害因素（毒害、噪声、高温、粉尘、特殊工种）" />
        </el-form-item>
        <el-form-item label="职业健康检查情况">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="上岗前" prop="preJobHealthCheckup" label-width="80px">
                <el-select v-model="form.preJobHealthCheckup" placeholder="请选择" style="width: 100%">
                  <el-option label="是" value="是" />
                  <el-option label="否" value="否" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="在岗中" prop="duringJobHealthCheckup" label-width="80px">
                <el-select v-model="form.duringJobHealthCheckup" placeholder="请选择" style="width: 100%">
                  <el-option label="是" value="是" />
                  <el-option label="否" value="否" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="离岗时" prop="postJobHealthCheckup" label-width="80px">
                <el-select v-model="form.postJobHealthCheckup" placeholder="请选择" style="width: 100%">
                  <el-option label="是" value="是" />
                  <el-option label="否" value="否" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" :rows="3" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :on-error="handleFileError"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <span>仅允许导入xls、xlsx格式文件。请先下载模板按照格式填写数据。</span>
            <br>
            <span style="color: #909399; font-size: 12px;">注意：导入时会直接添加所有数据，包括重复数据。</span>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="OccupationaPersonnelList">
import { listOccupationaPersonnelList, getOccupationaPersonnelList, delOccupationaPersonnelList, addOccupationaPersonnelList, updateOccupationaPersonnelList, listByRelatedId } from "@/api/security/OccupationaPersonnelList";
import { getToken } from "@/utils/auth";
import { UploadFilled, Check, Close } from '@element-plus/icons-vue';
const route = useRoute();
const { proxy } = getCurrentInstance();
const uploadRef = ref();

const OccupationaPersonnelListList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入参数
const upload = reactive({
  // 是否显示弹出层（导入）
  open: false,
  // 弹出层标题（导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/OccupationaPersonnelList/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    teamName: null,
    name: null,
    gender: null,
    jobType: null,
    exposureFactors: null,
  },
  rules: {
    teamName: [
      { required: true, message: "班组不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "姓名不能为空", trigger: "blur" }
    ],
    gender: [
      { required: true, message: "性别不能为空", trigger: "change" }
    ],
    jobType: [
      { required: true, message: "工种不能为空", trigger: "blur" }
    ],
    exposureFactors: [
      { required: true, message: "接害因素不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询危险职业岗位人员清单及管理台帐列表 */
function getList() {
  loading.value = true;
  listOccupationaPersonnelList(queryParams.value).then(response => {
    OccupationaPersonnelListList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // 处理数据，为合并单元格做准备
    processDataForMerge();
  });
}

/** 处理数据，为合并单元格做准备 */
function processDataForMerge() {
  const data = OccupationaPersonnelListList.value;
  let currentTeam = '';

  for (let i = 0; i < data.length; i++) {
    // 如果当前行的班组为空或与上一行相同，则使用上一行的班组
    if (!data[i].teamName || data[i].teamName.trim() === '') {
      data[i].teamName = currentTeam;
    } else {
      currentTeam = data[i].teamName;
    }
  }
}

/** 表格合并行或列的计算方法 */
function arraySpanMethod({ rowIndex, columnIndex }) {
  // 只对班组列（第2列，索引为2）进行合并
  if (columnIndex === 2) {
    const data = OccupationaPersonnelListList.value;
    if (rowIndex === 0) {
      return getSpanArr(data, 'teamName', rowIndex);
    } else {
      // 如果当前行的班组与上一行相同，则不显示（rowspan为0）
      if (data[rowIndex].teamName === data[rowIndex - 1].teamName) {
        return {
          rowspan: 0,
          colspan: 1
        };
      } else {
        return getSpanArr(data, 'teamName', rowIndex);
      }
    }
  }
}

/** 获取合并的行数 */
function getSpanArr(data, key, index) {
  let spanCount = 1;
  const currentValue = data[index][key];

  // 向下查找相同的值
  for (let i = index + 1; i < data.length; i++) {
    if (data[i][key] === currentValue) {
      spanCount++;
    } else {
      break;
    }
  }

  return {
    rowspan: spanCount,
    colspan: 1
  };
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
    teamName: null,
    name: null,
    gender: null,
    jobType: null,
    exposureFactors: null,
    preJobHealthCheckup: "否",
    duringJobHealthCheckup: "否",
    postJobHealthCheckup: "否",
    remarks: null,
    relatedId: null
  };
  proxy.resetForm("OccupationaPersonnelListRef");
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
  title.value = "添加危险职业岗位人员清单及管理台帐";
  // 如果URL中有relatedId参数，设置到表单中
  if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOccupationaPersonnelList(_id).then(response => {
    form.value = response.data;
    // 健康检查字段已经是字符串格式，不需要split处理
    open.value = true;
    title.value = "修改危险职业岗位人员清单及管理台帐";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["OccupationaPersonnelListRef"].validate(valid => {
    if (valid) {
      // 健康检查字段已经是字符串格式，不需要join处理
      if (form.value.id != null) {
        updateOccupationaPersonnelList(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOccupationaPersonnelList(form.value).then(() => {
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
  proxy.$modal.confirm('是否确认删除危险职业岗位人员清单及管理台帐编号为"' + _ids + '"的数据项？').then(function() {
    return delOccupationaPersonnelList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/OccupationaPersonnelList/export', {
    ...queryParams.value
  }, `OccupationaPersonnelList_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "危险职业岗位人员清单及管理台帐导入";
  upload.open = true;
}

/** 独立的模板下载按钮操作 */
function handleDownloadTemplate() {
  // 直接使用简单模板下载，更稳定可靠
  proxy.download('security/OccupationaPersonnelList/importTemplateSimple', {}, '危险职业岗位人员清单及管理台帐导入模板.xlsx');
}

/** 文件上传中处理 */
function handleFileUploadProgress() {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.open = false;
  upload.isUploading = false;
  uploadRef.value.clearFiles();

  if (response.code === 200) {
    proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {
      dangerouslyUseHTMLString: true,
      type: 'success'
    });
    getList();
  } else {
    proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入失败", {
      dangerouslyUseHTMLString: true,
      type: 'error'
    });
  }
}

/** 文件上传失败处理 */
function handleFileError() {
  upload.open = false;
  upload.isUploading = false;
  uploadRef.value.clearFiles();
  proxy.$modal.msgError("文件上传失败，请重试");
}

/** 提交上传文件 */
function submitFileForm() {
  uploadRef.value.submit();
}

// 检查关联ID参数
function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    loading.value = true;
    // 使用关联ID进行筛选查询
    listByRelatedId(relatedId).then(response => {
      OccupationaPersonnelListList.value = response.rows;
      total.value = response.total;
      loading.value = false;
      // 处理数据，为合并单元格做准备
      processDataForMerge();
      proxy.$modal.msgSuccess(`已加载关联文件数据，共 ${response.total} 条记录`);
    }).catch(() => {
      loading.value = false;
      proxy.$modal.msgError("加载关联数据失败");
    });
  }
}

onMounted(() => {
  // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
    getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
});
</script>

<style scoped>
.el-table .el-table__cell {
  padding: 8px 0;
}

.el-table .merged-cell {
  background-color: #f5f7fa;
}

.el-upload__tip {
  color: #606266;
  font-size: 12px;
  margin-top: 7px;
}

.el-upload__tip .el-link {
  margin-left: 10px;
}

/* 表格边框样式 */
.el-table--border .el-table__cell {
  border-right: 1px solid #ebeef5;
}

.el-table--border .el-table__header th {
  border-right: 1px solid #ebeef5;
}

/* 健康检查状态样式 */
.health-check-yes {
  color: #67c23a;
  font-weight: bold;
}

.health-check-no {
  color: #909399;
}

/* 表单样式优化 */
.el-dialog .el-form {
  padding: 0 20px;
}

.el-form-item {
  margin-bottom: 18px;
}

.el-form-item__label {
  font-weight: 500;
  color: #606266;
}

/* 健康检查表单区域样式 */
.el-form-item .el-row .el-form-item {
  margin-bottom: 0;
}

/* 按钮样式 */
.el-button + .el-button {
  margin-left: 10px;
}

/* 表格中的标签样式 */
.el-tag {
  border: none;
  font-weight: 500;
}

.el-tag .el-icon {
  margin-right: 0;
}
</style>
