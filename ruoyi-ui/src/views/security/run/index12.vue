<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘锦合力SAP物料号" prop="panjinHeliSapMaterialNumber">
        <el-input
          v-model="queryParams.panjinHeliSapMaterialNumber"
          placeholder="请输入盘锦合力SAP物料号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入物料名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格" prop="specifications">
        <el-input
          v-model="queryParams.specifications"
          placeholder="请输入规格"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入单位"
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
          v-hasPermi="['security:healthprotectiveequipment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:healthprotectiveequipment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:healthprotectiveequipment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:healthprotectiveequipment:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:healthprotectiveequipment:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="healthprotectiveequipmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="盘锦合力SAP物料号" align="center" prop="panjinHeliSapMaterialNumber" />
      <el-table-column label="物料名称" align="center" prop="materialName" />
      <el-table-column label="规格" align="center" prop="specifications" />
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:healthprotectiveequipment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:healthprotectiveequipment:remove']">删除</el-button>
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

    <!-- 添加或修改职业健康防护用品台帐对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="healthprotectiveequipmentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="盘锦合力SAP物料号" prop="panjinHeliSapMaterialNumber">
          <el-input v-model="form.panjinHeliSapMaterialNumber" placeholder="请输入盘锦合力SAP物料号" />
        </el-form-item>
        <el-form-item label="物料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入物料名称" />
        </el-form-item>
        <el-form-item label="规格" prop="specifications">
          <el-input v-model="form.specifications" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
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

    <!-- 职业健康防护用品台帐导入对话框 -->
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据（基于SAP物料号匹配）
            </div>
            <span>仅允许导入xls、xlsx格式文件。支持重复数据导入。</span>
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

<script setup name="Healthprotectiveequipment">
import { listHealthprotectiveequipment, getHealthprotectiveequipment, delHealthprotectiveequipment, addHealthprotectiveequipment, updateHealthprotectiveequipment } from "@/api/security/healthprotectiveequipment";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const route = useRoute();
const healthprotectiveequipmentList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

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
  url: import.meta.env.VITE_APP_BASE_API + "/security/healthprotectiveequipment/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    panjinHeliSapMaterialNumber: null,
    materialName: null,
    specifications: null,
    brand: null,
    unit: null,
    remarks: null
  },
  rules: {
    panjinHeliSapMaterialNumber: [
      { required: true, message: "盘锦合力SAP物料号不能为空", trigger: "blur" }
    ],
    materialName: [
      { required: true, message: "物料名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询职业健康防护用品台帐列表 */
function getList() {
  loading.value = true;
  listHealthprotectiveequipment(queryParams.value).then(response => {
    healthprotectiveequipmentList.value = response.rows;
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
    panjinHeliSapMaterialNumber: null,
    materialName: null,
    specifications: null,
    brand: null,
    unit: null,
    remarks: null
  };
  proxy.resetForm("healthprotectiveequipmentRef");
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
  title.value = "添加职业健康防护用品台帐";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHealthprotectiveequipment(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改职业健康防护用品台帐";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["healthprotectiveequipmentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHealthprotectiveequipment(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHealthprotectiveequipment(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除职业健康防护用品台帐编号为"' + _ids + '"的数据项？').then(function() {
    return delHealthprotectiveequipment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/healthprotectiveequipment/export', {
    ...queryParams.value
  }, `healthprotectiveequipment_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "职业健康防护用品台帐导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('/security/healthprotectiveequipment/importTemplate', {}, `职业健康防护用品台帐导入模板_${new Date().getTime()}.xlsx`);
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

function checkRelatedId() {
  // 从路由参数中获取关联ID
  const relatedId = route.query.relatedId;

  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    // 将关联ID设置到查询参数中
    queryParams.value.relatedId = relatedId;
    // 调用getList()执行实际的数据查询
    getList();
    // 显示提示信息
    proxy.$modal.msgSuccess("已加载关联的特种设备台账信息数据");
  } else {
    // 没有关联ID时，加载所有数据
    getList();
  }
}

onMounted(() => {
  // 检查关联ID参数并加载相应数据
  checkRelatedId();
});
</script>
