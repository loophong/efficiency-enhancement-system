<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入岗位名称"
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

    <el-table v-loading="loading" :data="UploadfileDeviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100"/>
      <el-table-column label="岗位名称" align="center" prop="fileName" />
      <el-table-column label="操作规程附件" align="center" prop="file" />
      <el-table-column label="备注" align="center" prop="text" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:UploadfileDevice:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:UploadfileDevice:remove']">删除</el-button>
          <el-button link type="primary" icon="View" @click="handlePreview(scope.row)">预览</el-button>
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
          <!-- 文件预览对话框 -->
       <el-dialog title="文件预览" v-model="previewDialogVisible" width="80%" append-to-body>
       <vue-office-docx
      :src="previewSrc"
      :style="comStyle"
      @rendered="renderedHandler"
      @error="errorHandler"
    />
  </el-dialog>
    <!-- 添加或修改岗位安全操作规程对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="UploadfileDeviceRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="岗位名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="操作规程附件" prop="file">
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
  </div>
</template>

<script setup name="UploadfileDevice">
import { listUploadfileDevice, getUploadfileDevice, delUploadfileDevice, addUploadfileDevice, updateUploadfileDevice } from "@/api/security/UploadfileDevice";
import VueOfficeDocx from '@vue-office/docx'
const { proxy } = getCurrentInstance();

const UploadfileDeviceList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const previewSrc = ref(''); // 确保 previewSrc 被正确声明
const comStyle = ref({ width: '100%', height: '600px' });
const previewDialogVisible = ref(false); // 确保 previewDialogVisible 被正确声明
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: null,
  },
  rules: {
    fileName: [
      { required: true, message: "岗位名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询岗位安全操作规程列表 */
function getList() {
  loading.value = true;
  listUploadfileDevice(queryParams.value).then(response => {
    UploadfileDeviceList.value = response.rows;
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
    fileName: null,
    file: null,
    text: null
  };
  proxy.resetForm("UploadfileDeviceRef");
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

/** 提交按钮 */
function submitForm() {
  proxy.$refs["UploadfileDeviceRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUploadfileDevice(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUploadfileDevice(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除岗位安全操作规程编号为"' + _ids + '"的数据项？').then(function() {
    return delUploadfileDevice(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/UploadfileDevice/export', {
    ...queryParams.value
  }, `UploadfileDevice_${new Date().getTime()}.xlsx`)
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

/** 渲染完成处理 */
function renderedHandler() {
  console.log('文档渲染完成');
}

/** 错误处理 */
function errorHandler(error) {
  console.error('文档渲染错误', error);
}

getList();
</script>
