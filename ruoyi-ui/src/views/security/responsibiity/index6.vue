<template>
  <div class="app-container">
    <!-- 标签页切换 -->
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="岗位安全操作规程" name="procedure">
        <!-- 岗位安全操作规程搜索表单 -->
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="文件名称" prop="fileName">
            <el-input
              v-model="queryParams.fileName"
              placeholder="请输入文件名称"
              clearable
              @keyup.enter="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="岗位清单" name="position">
        <!-- 岗位清单搜索表单 -->
        <el-form :model="positionQueryParams" ref="positionQueryRef" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="岗位名称" prop="positionName">
            <el-input
              v-model="positionQueryParams.positionName"
              placeholder="请输入岗位名称"
              clearable
              @keyup.enter="handlePositionQuery"
            />
          </el-form-item>
          <el-form-item label="部门" prop="department">
            <el-input
              v-model="positionQueryParams.department"
              placeholder="请输入部门"
              clearable
              @keyup.enter="handlePositionQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handlePositionQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetPositionQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
    </el-tabs>

    <!-- 岗位安全操作规程操作按钮 -->
    <el-row :gutter="10" class="mb8" v-if="activeTab === 'procedure'">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['security:UploadfileDevice:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:UploadfileDevice:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:UploadfileDevice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:UploadfileDevice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 岗位清单操作按钮 -->
    <el-row :gutter="10" class="mb8" v-if="activeTab === 'position'">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handlePositionAdd"
          v-hasPermi="['security:Position:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="positionSingle"
          @click="handlePositionUpdate"
          v-hasPermi="['security:Position:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="positionMultiple"
          @click="handlePositionDelete"
          v-hasPermi="['security:Position:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handlePositionExport"
          v-hasPermi="['security:Position:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handlePositionImport"
          v-hasPermi="['security:Position:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getPositionList"></right-toolbar>
    </el-row>

    <!-- 岗位安全操作规程表格 -->
    <el-table v-loading="loading" :data="UploadfileDeviceList" @selection-change="handleSelectionChange" v-if="activeTab === 'procedure'">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="文件" align="center" prop="file" />
      <el-table-column label="备注" align="center" prop="text" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:UploadfileDevice:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:UploadfileDevice:remove']">删除</el-button>
          <el-button link type="primary" icon="View" @click="handlePreview(scope.row)">预览</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 岗位清单表格 -->
    <el-table v-loading="positionLoading" :data="positionList" @selection-change="handlePositionSelectionChange" v-if="activeTab === 'position'">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="岗位名称" align="center" prop="positionName" />
      <el-table-column label="部门" align="center" prop="department" />
      <el-table-column label="职责描述" align="center" prop="description" show-overflow-tooltip />
      <el-table-column label="附件" align="center" prop="attachment">
        <template #default="scope">
          <el-button link type="primary" v-if="scope.row.attachment" @click="handleAttachmentPreview(scope.row)">查看附件</el-button>
          <span v-else>无附件</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handlePositionUpdate(scope.row)" v-hasPermi="['security:Position:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handlePositionDelete(scope.row)" v-hasPermi="['security:Position:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="activeTab === 'procedure' && total > 0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <pagination
      v-show="activeTab === 'position' && positionTotal > 0"
      :total="positionTotal"
      v-model:page="positionQueryParams.pageNum"
      v-model:limit="positionQueryParams.pageSize"
      @pagination="getPositionList"
    />

    <!-- 添加或修改岗位安全操作规程对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="UploadfileDeviceRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <el-form-item label="文件" prop="file">
          <file-upload v-model="form.file"/>
        </el-form-item>
        <el-form-item label="备注" prop="text">
          <el-input v-model="form.text" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加或修改岗位清单对话框 -->
    <el-dialog :title="positionTitle" v-model="positionOpen" width="600px" append-to-body>
      <el-form ref="positionFormRef" :model="positionForm" :rules="positionRules" label-width="80px">
        <el-form-item label="岗位名称" prop="positionName">
          <el-input v-model="positionForm.positionName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-input v-model="positionForm.department" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="职责描述" prop="description">
          <el-input v-model="positionForm.description" type="textarea" :rows="4" placeholder="请输入职责描述" />
        </el-form-item>
        <el-form-item label="附件" prop="attachment">
          <file-upload v-model="positionForm.attachment"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitPositionForm">确 定</el-button>
          <el-button @click="cancelPosition">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 岗位清单导入对话框 -->
    <el-dialog :title="positionUpload.title" v-model="positionUpload.open" width="400px" append-to-body>
      <el-upload
        ref="positionUploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="positionUpload.headers"
        :action="positionUpload.url + '?updateSupport=' + positionUpload.updateSupport"
        :disabled="positionUpload.isUploading"
        :on-progress="handlePositionFileUploadProgress"
        :on-success="handlePositionFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="positionUpload.updateSupport" />是否更新已存在数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importPositionTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitPositionFileForm">确 定</el-button>
          <el-button @click="positionUpload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
       <!-- 文件预览对话框 -->
       <el-dialog title="文件预览" v-model="previewDialogVisible" width="80%" append-to-body>
       <vue-office-docx
      :src="previewSrc"
      :style="comStyle"
      @rendered="renderedHandler"
      @error="errorHandler"
    />
  </el-dialog>
  </div>
</template>

<script setup name="UploadfileDevice">
import { listUploadfileDevice, getUploadfileDevice, delUploadfileDevice, addUploadfileDevice, updateUploadfileDevice } from "@/api/security/UploadfileDevice";
import { listPosition, getPosition, delPosition, addPosition, updatePosition } from "@/api/security/Position";
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
import { getToken } from "@/utils/auth";
const { proxy } = getCurrentInstance();

// 标签页相关
const activeTab = ref('procedure');

// 岗位安全操作规程相关
const UploadfileDeviceList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const previewSrc = ref('');
const comStyle = ref({ width: '100%', height: '600px' });
const previewDialogVisible = ref(false);

// 岗位清单相关
const positionList = ref([]);
const positionOpen = ref(false);
const positionLoading = ref(true);
const positionIds = ref([]);
const positionSingle = ref(true);
const positionMultiple = ref(true);
const positionTotal = ref(0);
const positionTitle = ref("");

// 岗位清单导入相关
const positionUpload = reactive({
  open: false,
  title: "",
  isUploading: false,
  updateSupport: 0,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/security/Position/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: null,
  },
  rules: {
    fileName: [
      { required: true, message: "文件名称不能为空", trigger: "blur" }
    ],
  },
  // 岗位清单相关数据
  positionForm: {},
  positionQueryParams: {
    pageNum: 1,
    pageSize: 10,
    positionName: null,
    department: null,
  },
  positionRules: {
    positionName: [
      { required: true, message: "岗位名称不能为空", trigger: "blur" }
    ],
    department: [
      { required: true, message: "部门不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules, positionForm, positionQueryParams, positionRules } = toRefs(data);

/** 标签页切换 */
function handleTabClick(tab) {
  activeTab.value = tab.name;
  if (tab.name === 'procedure') {
    getList();
  } else if (tab.name === 'position') {
    getPositionList();
  }
}

/** 查询岗位安全操作规程列表 */
function getList() {
  loading.value = true;
  listUploadfileDevice(queryParams.value).then(response => {
    UploadfileDeviceList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 查询岗位清单列表 */
function getPositionList() {
  positionLoading.value = true;
  listPosition(positionQueryParams.value).then(response => {
    positionList.value = response.rows;
    positionTotal.value = response.total;
    positionLoading.value = false;
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
    fileName: null,
    file: null,
    text: null
  };
  proxy.resetForm("UploadfileDeviceRef");
}

// 岗位清单取消按钮
function cancelPosition() {
  positionOpen.value = false;
  resetPosition();
}

// 岗位清单表单重置
function resetPosition() {
  positionForm.value = {
    id: null,
    positionName: null,
    department: null,
    description: null,
    attachment: null
  };
  proxy.resetForm("positionFormRef");
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

/** 岗位清单搜索按钮操作 */
function handlePositionQuery() {
  positionQueryParams.value.pageNum = 1;
  getPositionList();
}

/** 岗位清单重置按钮操作 */
function resetPositionQuery() {
  proxy.resetForm("positionQueryRef");
  handlePositionQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

// 岗位清单多选框选中数据
function handlePositionSelectionChange(selection) {
  positionIds.value = selection.map(item => item.id);
  positionSingle.value = selection.length != 1;
  positionMultiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加岗位安全操作规程";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getUploadfileDevice(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改岗位安全操作规程";
  });
}

/** 岗位清单新增按钮操作 */
function handlePositionAdd() {
  resetPosition();
  positionOpen.value = true;
  positionTitle.value = "添加岗位清单";
}

/** 岗位清单修改按钮操作 */
function handlePositionUpdate(row) {
  resetPosition();
  const _id = row.id || positionIds.value;
  getPosition(_id).then(response => {
    positionForm.value = response.data;
    positionOpen.value = true;
    positionTitle.value = "修改岗位清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["UploadfileDeviceRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUploadfileDevice(form.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUploadfileDevice(form.value).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 岗位清单提交按钮 */
function submitPositionForm() {
  proxy.$refs["positionFormRef"].validate(valid => {
    if (valid) {
      if (positionForm.value.id != null) {
        updatePosition(positionForm.value).then(() => {
          proxy.$modal.msgSuccess("修改成功");
          positionOpen.value = false;
          getPositionList();
        });
      } else {
        addPosition(positionForm.value).then(() => {
          proxy.$modal.msgSuccess("新增成功");
          positionOpen.value = false;
          getPositionList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除岗位安全操作规程编号为"' + _ids + '"的数据项？').then(function() {
    return delUploadfileDevice(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 岗位清单删除按钮操作 */
function handlePositionDelete(row) {
  const _ids = row.id || positionIds.value;
  proxy.$modal.confirm('是否确认删除岗位清单编号为"' + _ids + '"的数据项？').then(function() {
    return delPosition(_ids);
  }).then(() => {
    getPositionList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/UploadfileDevice/export', {
    ...queryParams.value
  }, `UploadfileDevice_${new Date().getTime()}.xlsx`)
}

/** 岗位清单导出按钮操作 */
function handlePositionExport() {
  proxy.download('security/Position/export', {
    ...positionQueryParams.value
  }, `Position_${new Date().getTime()}.xlsx`)
}

/** 岗位清单导入按钮操作 */
function handlePositionImport() {
  positionUpload.title = "岗位清单导入";
  positionUpload.open = true;
}

/** 下载岗位清单模板操作 */
function importPositionTemplate() {
  proxy.download('security/Position/importTemplate', {}, `岗位清单导入模板_${new Date().getTime()}.xlsx`);
}

/** 岗位清单文件上传中处理 */
function handlePositionFileUploadProgress() {
  positionUpload.isUploading = true;
}

/** 岗位清单文件上传成功处理 */
function handlePositionFileSuccess(response) {
  positionUpload.open = false;
  positionUpload.isUploading = false;
  proxy.$refs["positionUploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getPositionList();
}

/** 提交岗位清单上传文件 */
function submitPositionFileForm() {
  proxy.$refs["positionUploadRef"].submit();
}

function handlePreview(row) {
  const staticPath = 'http://localhost/dev-api'; // 静态地址
  const dynamicPath = row.file; // 动态地址
  const fileExt = dynamicPath.split('.').pop().toLowerCase();
  if (fileExt === 'docx') {
    previewSrc.value = staticPath + dynamicPath; // 静态地址和动态地址相加
    console.log("文件地址: " + previewSrc.value);
    previewDialogVisible.value = true;
    console.log('预览文件路径:', previewSrc.value); // 添加日志以确认文件路径
    console.log('预览对话框可见性:', previewDialogVisible.value); // 添加日志以确认对话框可见性
  } else {
    console.error('不支持的文件类型,只能查看docx文件:', fileExt);
    proxy.$modal.msgError('不支持的文件类型,只能查看docx文件');
  }
}

/** 岗位清单附件预览 */
function handleAttachmentPreview(row) {
  const staticPath = 'http://localhost/dev-api';
  const dynamicPath = row.attachment;
  const fileExt = dynamicPath.split('.').pop().toLowerCase();
  if (fileExt === 'docx') {
    previewSrc.value = staticPath + dynamicPath;
    previewDialogVisible.value = true;
  } else {
    proxy.$modal.msgError('不支持的文件类型,只能查看docx文件');
  }
}

/** 渲染完成处理 */
function renderedHandler() {
  console.log('文档渲染完成');
}

/** 错误处理 */
function errorHandler(error) {
  console.error('文档渲染错误', error);
}

// 初始化加载数据
getList();
</script>
