<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="制度名称" prop="systemName">
        <el-input
          v-model="queryParams.systemName"
          placeholder="请输入制度名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="documentNumber">
        <el-input
          v-model="queryParams.documentNumber"
          placeholder="请输入编号"
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
          v-hasPermi="['security:OhsDocuments:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:OhsDocuments:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:OhsDocuments:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:OhsDocuments:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- 使用通用Excel导入组件 -->
        <excel-import
          import-url="/security/OhsDocuments/import"
          module-name="环境职业健康安全管理体系文件清单"
          module-code="security/OhsDocuments"
          button-text="导入"
          button-type="success"
          button-plain
          button-icon="Upload"
          @success="getList"
        />
      </el-col>
      <!-- 添加下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleDownloadTemplate"
          v-hasPermi="['security:OhsDocuments:export']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OhsDocumentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index"  width="100" />
      <el-table-column label="制度名称" align="center" prop="systemName" />
      <el-table-column label="编号" align="center" prop="documentNumber" />
      <el-table-column label="文件列表" align="center"    width="200">
        <template #default="scope">
          <file-upload v-model="scope.row.fileList" :limit="5" @change="handleFileChange($event, scope.row)" :show-button="false" />
        </template>
      </el-table-column> 
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:OhsDocuments:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:OhsDocuments:remove']">删除</el-button>
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

   <!-- 文件预览对话框 预览excel文件-和docx文件 选择功能-->
    <el-dialog title="文件预览" v-model="previewDialogVisible" width="80%" append-to-body>
      <vue-office-excel
        v-if="fileType === 'excel'"
        :src="previewSrc"
        :style="comStyle"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
      <vue-office-docx
        v-else-if="fileType === 'docx'"
        :src="previewSrc"
        :style="comStyle"
        @rendered="renderedHandler"
        @error="errorHandler"
      />
    </el-dialog>

    <!-- 添加或修改环境职业健康安全管理体系文件清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="OhsDocumentsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="制度名称" prop="systemName">
          <el-input v-model="form.systemName" placeholder="请输入制度名称" />
        </el-form-item>
        <el-form-item label="编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="文件列表" prop="fileList">
          <file-upload v-model="form.fileList" :limit="5" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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

<script setup name="OhsDocuments">
import { listOhsDocuments, getOhsDocuments, delOhsDocuments, addOhsDocuments, updateOhsDocuments } from "@/api/security/OhsDocuments";
import ExcelImport from "@/components/ExcelImport/index.vue";
import FileUpload from "@/components/FileUpload/index.vue";
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'
import VueOfficeExcel from '@vue-office/excel'
import '@vue-office/excel/lib/index.css'

const route = useRoute();
const { proxy } = getCurrentInstance();
const OhsDocumentsList = ref([]);
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
const fileType = ref(''); // 添加 fileType 变量
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    systemName: null,
    documentNumber: null,
    remarks: null
  },
  rules: {
    systemName: [
      { required: true, message: "制度名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询环境职业健康安全管理体系文件清单列表 */
function getList() {
  loading.value = true;
  listOhsDocuments(queryParams.value).then(response => {
    OhsDocumentsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 预览文件 */
function handlePreview(row) {
  const staticPath = import.meta.env.VITE_APP_BASE_API; // 静态地址
  const dynamicPath = row.fileList; // 动态地址
  const fileExt = dynamicPath.split('.').pop().toLowerCase();
  
  // 设置文件类型
  if (fileExt === 'xlsx') {
    fileType.value = 'excel';
  } else if (fileExt === 'docx') {
    fileType.value = 'docx';
  }
  
  if (fileExt === 'xlsx' || fileExt === 'docx') {
    previewSrc.value = staticPath + dynamicPath; // 静态地址和动态地址相加
    console.log("文件地址: " + previewSrc.value);
    previewDialogVisible.value = true;
    console.log('预览文件路径:', previewSrc.value); // 添加日志以确认文件路径
    console.log('预览对话框可见性:', previewDialogVisible.value); // 添加日志以确认对话框可见性
  } else {
    console.error('不支持的文件类型,只能查看xlsx和docx文件:', fileExt);
    proxy.$modal.msgError('不支持的文件类型,只能查看xlsx和docx文件');
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
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    systemName: null,
    documentNumber: null,
    fileList: null,
    remarks: null
  };
  proxy.resetForm("OhsDocumentsRef");
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
        if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加环境职业健康安全管理体系文件清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOhsDocuments(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改环境职业健康安全管理体系文件清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["OhsDocumentsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOhsDocuments(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOhsDocuments(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除环境职业健康安全管理体系文件清单编号为"' + _ids + '"的数据项？').then(function() {
    return delOhsDocuments(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/OhsDocuments/export', {
    ...queryParams.value
  }, `OhsDocuments_${new Date().getTime()}.xlsx`)
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/OhsDocuments/importTemplate', {}, `OhsDocuments_template_${new Date().getTime()}.xlsx`);
}

// 文件上传变更处理
function handleFileChange(file, row) {
  if (file) {
    console.log('文件上传成功:', file);
    // 确保row.fileList是字符串格式
    const updatedData = {
      id: row.id,
      fileList: typeof row.fileList === 'string' ? row.fileList : JSON.stringify(row.fileList)
    };
    console.log('准备更新的数据:', updatedData);
    updateOhsDocuments(updatedData).then(() => {
      proxy.$modal.msgSuccess("文件更新成功");
      getList(); // 更新列表数据
    }).catch(err => {
      console.error('文件更新失败:', err);
      proxy.$modal.msgError("文件更新失败，请重试");
    });
  }
}

function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
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
