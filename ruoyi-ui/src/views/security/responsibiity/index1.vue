<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="制度名称" prop="zhiDu">
        <el-input
          v-model="queryParams.zhiDu"
          placeholder="请输入制度名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="办法名称" prop="banFa">
        <el-input
          v-model="queryParams.banFa"
          placeholder="请输入办法名称"
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
          v-hasPermi="['security:ProductionResponsibilityList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:ProductionResponsibilityList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:ProductionResponsibilityList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:ProductionResponsibilityList:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:ProductionResponsibilityList:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="ProductionResponsibilityListList" @selection-change="handleSelectionChange" :span-method="arraySpanMethod">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="制度名称" align="center" prop="zhiDu" />
      <el-table-column label="办法名称" align="center" prop="banFa" />
      <el-table-column label="文件" align="center" width="200">
        <template #default="scope">
          <file-upload v-model="scope.row.fileList" :limit="5" @change="handleFileChange($event, scope.row)" :show-button="false" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:ProductionResponsibilityList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:ProductionResponsibilityList:remove']">删除</el-button>
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

    <!-- 添加或修改安全生产责任制清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="ProductionResponsibilityListRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="制度名称" prop="zhiDu">
          <el-input v-model="form.zhiDu" placeholder="请输入制度名称" />
        </el-form-item>
        <el-form-item label="办法名称" prop="banFa">
          <el-input v-model="form.banFa" placeholder="请输入办法名称" />
        </el-form-item>
        <el-form-item label="文件" prop="fileList">
          <file-upload v-model="form.fileList" :limit="5" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 安全生产责任制清单导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据（基于制度名称、办法名称匹配）
            </div>
            <span>仅允许导入xls、xlsx格式文件。支持重复数据导入和单元格合并。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
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

<script setup name="ProductionResponsibilityList">
import { listProductionResponsibilityList, getProductionResponsibilityList, delProductionResponsibilityList, addProductionResponsibilityList, updateProductionResponsibilityList } from "@/api/security/ProductionResponsibilityList";
import { getToken } from "@/utils/auth";
import FileUpload from "@/components/FileUpload/index.vue";
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'
import VueOfficeExcel from '@vue-office/excel'
import '@vue-office/excel/lib/index.css'
const { proxy } = getCurrentInstance();

const ProductionResponsibilityListList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const route = useRoute();
const previewSrc = ref(''); // 确保 previewSrc 被正确声明
const comStyle = ref({ width: '100%', height: '600px' });
const previewDialogVisible = ref(false); // 确保 previewDialogVisible 被正确声明
const fileType = ref(''); // 添加 fileType 变量
/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/ProductionResponsibilityList/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    zhiDu: null,
    banFa: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全生产责任制清单列表 */
function getList() {
  loading.value = true;
  listProductionResponsibilityList(queryParams.value).then(response => {
    ProductionResponsibilityListList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    // 计算合并单元格的信息
    calculateSpanInfo();
  });
}

// 单元格合并相关的响应式数据
const spanInfo = ref({
  zhiDuSpanArr: [],
  banFaSpanArr: [],
  zhiDuPos: 0,
  banFaPos: 0
});

/** 计算单元格合并信息 */
function calculateSpanInfo() {
  const data = ProductionResponsibilityListList.value;
  if (!data || data.length === 0) {
    spanInfo.value.zhiDuSpanArr = [];
    spanInfo.value.banFaSpanArr = [];
    return;
  }

  // 初始化合并信息数组
  spanInfo.value.zhiDuSpanArr = new Array(data.length).fill(1);
  spanInfo.value.banFaSpanArr = new Array(data.length).fill(1);

  // 计算制度名称的合并信息
  calculateColumnSpan(data, 'zhiDu', spanInfo.value.zhiDuSpanArr);

  // 计算办法名称的合并信息
  calculateColumnSpan(data, 'banFa', spanInfo.value.banFaSpanArr);
}

/** 计算指定列的合并信息 */
function calculateColumnSpan(data, columnKey, spanArr) {
  if (data.length === 0) return;

  // 初始化第一行
  spanArr[0] = 1;
  let mergeStartIndex = 0;

  for (let i = 1; i < data.length; i++) {
    const currentValue = data[i][columnKey];
    const prevValue = data[i - 1][columnKey];

    // 判断是否应该与前一行合并
    // 条件：当前值与前一行值相同，且都不为空字符串和null/undefined
    const shouldMerge = (currentValue === prevValue) &&
                       (currentValue !== null && currentValue !== undefined && currentValue !== '');

    if (shouldMerge) {
      // 合并：增加合并起始行的跨度，当前行设为0
      spanArr[mergeStartIndex]++;
      spanArr[i] = 0;
    } else {
      // 不合并：当前行开始新的合并组
      spanArr[i] = 1;
      mergeStartIndex = i;
    }
  }
}

/** 表格单元格合并方法 */
function arraySpanMethod({ column, rowIndex }) {
  if (column.property === 'zhiDu') {
    const _row = spanInfo.value.zhiDuSpanArr[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  } else if (column.property === 'banFa') {
    const _row = spanInfo.value.banFaSpanArr[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  }
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
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

// 表单重置
function reset() {
  form.value = {
    id: null,
    zhiDu: null,
    banFa: null,
    fileList: null
  };
  proxy.resetForm("ProductionResponsibilityListRef");
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
  title.value = "添加安全生产责任制清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getProductionResponsibilityList(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全生产责任制清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["ProductionResponsibilityListRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateProductionResponsibilityList(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addProductionResponsibilityList(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 文件上传变更处理 */
function handleFileChange(file, row) {
  if (file) {
    console.log('文件上传成功:', file);
    // 确保row.fileList是字符串格式
    const updatedData = {
      id: row.id,
      fileList: typeof row.fileList === 'string' ? row.fileList : JSON.stringify(row.fileList)
    };
    console.log('准备更新的数据:', updatedData);
    updateProductionResponsibilityList(updatedData).then(() => {
      proxy.$modal.msgSuccess("文件更新成功");
      getList(); // 更新列表数据
    }).catch(err => {
      console.error('文件更新失败:', err);
      proxy.$modal.msgError("文件更新失败，请重试");
    });
  }
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除安全生产责任制清单编号为"' + _ids + '"的数据项？').then(function() {
    return delProductionResponsibilityList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/ProductionResponsibilityList/export', {
    ...queryParams.value
  }, `ProductionResponsibilityList_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "安全生产责任制清单导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  // 使用带认证的请求下载模板
  proxy.download('security/ProductionResponsibilityList/importTemplate', {}, `安全生产责任制清单导入模板_${new Date().getTime()}.xlsx`);
}

/** 文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};

/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
};

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

// 检查关联ID参数
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
