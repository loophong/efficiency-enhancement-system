<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物资名称" prop="supplyName">
        <el-input
          v-model="queryParams.supplyName"
          placeholder="请输入物资名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物资数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入物资数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="计量单位" prop="unitOfMeasurement">
        <el-input
          v-model="queryParams.unitOfMeasurement"
          placeholder="请输入计量单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="存放位置" prop="storageLocation">
        <el-input
          v-model="queryParams.storageLocation"
          placeholder="请输入存放位置"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系人" prop="contactPerson">
        <el-input
          v-model="queryParams.contactPerson"
          placeholder="请输入联系人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="电话" prop="phoneNumber">
        <el-input
          v-model="queryParams.phoneNumber"
          placeholder="请输入电话"
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
          v-hasPermi="['security:emergencysuppliesledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:emergencysuppliesledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:emergencysuppliesledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:emergencysuppliesledger:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:emergencysuppliesledger:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="emergencysuppliesledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100" />
      <el-table-column label="物资名称" align="center" prop="supplyName" />
      <el-table-column label="应急物资类型" align="center" prop="emergencySupplyType" />
      <el-table-column label="物资数量" align="center" prop="quantity" />
      <el-table-column label="计量单位" align="center" prop="unitOfMeasurement" />
      <el-table-column label="存放位置" align="center" prop="storageLocation" />
      <el-table-column label="联系人" align="center" prop="contactPerson" />
      <el-table-column label="电话" align="center" prop="phoneNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:emergencysuppliesledger:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:emergencysuppliesledger:remove']">删除</el-button>
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

    <!-- 添加或修改应急物资管理台帐对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="emergencysuppliesledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物资名称" prop="supplyName">
          <el-input v-model="form.supplyName" placeholder="请输入物资名称" />
        </el-form-item>
        <el-form-item label="物资数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入物资数量" />
        </el-form-item>
        <el-form-item label="计量单位" prop="unitOfMeasurement">
          <el-input v-model="form.unitOfMeasurement" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="存放位置" prop="storageLocation">
          <el-input v-model="form.storageLocation" placeholder="请输入存放位置" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
        </el-form-item>
        <el-form-item label="电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入电话" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 应急物资管理台帐导入对话框 -->
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
              <span>仅允许导入xls、xlsx格式文件。支持重复数据导入。</span>
              <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
            </div>
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

<script setup name="Emergencysuppliesledger">
import { listEmergencysuppliesledger, getEmergencysuppliesledger, delEmergencysuppliesledger, addEmergencysuppliesledger, updateEmergencysuppliesledger } from "@/api/security/emergencysuppliesledger";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const route = useRoute();
const emergencysuppliesledgerList = ref([]);
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
  url: import.meta.env.VITE_APP_BASE_API + "/security/emergencysuppliesledger/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplyName: null,
    emergencySupplyType: null,
    quantity: null,
    unitOfMeasurement: null,
    storageLocation: null,
    contactPerson: null,
    phoneNumber: null
  },
  rules: {
    supplyName: [
      { required: true, message: "物资名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询应急物资管理台帐列表 */
function getList() {
  loading.value = true;
  listEmergencysuppliesledger(queryParams.value).then(response => {
    emergencysuppliesledgerList.value = response.rows;
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
    supplyName: null,
    emergencySupplyType: null,
    quantity: null,
    unitOfMeasurement: null,
    storageLocation: null,
    contactPerson: null,
    phoneNumber: null
  };
  proxy.resetForm("emergencysuppliesledgerRef");
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
  title.value = "添加应急物资管理台帐";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getEmergencysuppliesledger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改应急物资管理台帐";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["emergencysuppliesledgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateEmergencysuppliesledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEmergencysuppliesledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除应急物资管理台帐编号为"' + _ids + '"的数据项？').then(function() {
    return delEmergencysuppliesledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/emergencysuppliesledger/export', {
    ...queryParams.value
  }, `emergencysuppliesledger_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "应急物资管理台帐导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  // 使用带认证的请求下载模板
  proxy.download('security/emergencysuppliesledger/importTemplate', {}, `应急物资管理台帐导入模板_${new Date().getTime()}.xlsx`);
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
    proxy.$modal.msgSuccess("已加载关联的应急物资管理台帐信息数据");
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
