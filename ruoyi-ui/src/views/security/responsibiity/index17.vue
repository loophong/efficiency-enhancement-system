<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="区域" prop="area">
        <el-input
          v-model="queryParams.area"
          placeholder="请输入区域"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位名称" prop="unitName">
        <el-input
          v-model="queryParams.unitName"
          placeholder="请输入单位名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备名称" prop="equipmentName">
        <el-input
          v-model="queryParams.equipmentName"
          placeholder="请输入设备名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备类别" prop="equipmentCategory">
        <el-input
          v-model="queryParams.equipmentCategory"
          placeholder="请输入设备类别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备型号" prop="equipmentModel">
        <el-input
          v-model="queryParams.equipmentModel"
          placeholder="请输入设备型号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="内部编号" prop="internalNumber">
        <el-input
          v-model="queryParams.internalNumber"
          placeholder="请输入内部编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用证号" prop="usageCertificateNumber">
        <el-input
          v-model="queryParams.usageCertificateNumber"
          placeholder="请输入使用证号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否停用" prop="isDisabled">
        <el-select v-model="queryParams.isDisabled" placeholder="请选择是否停用" clearable>
          <el-option
            v-for="dict in security_special_device"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['security:specialoperationscertificate:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:specialoperationscertificate:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:specialoperationscertificate:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:specialoperationscertificate:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:specialoperationscertificate:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="specialoperationscertificateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="区域" align="center" prop="area" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="设备名称" align="center" prop="equipmentName" />
      <el-table-column label="设备类别" align="center" prop="equipmentCategory" />
      <el-table-column label="设备型号" align="center" prop="equipmentModel" />
      <el-table-column label="内部编号" align="center" prop="internalNumber" />
      <el-table-column label="使用证号" align="center" prop="usageCertificateNumber" />
      <el-table-column label="发证日期" align="center" prop="issuanceDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.issuanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="制造单位" align="center" prop="manufacturingUnit" />
      <el-table-column label="检验日期" align="center" prop="inspectionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="下次检验日期" align="center" prop="nextInspectionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.nextInspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否停用" align="center" prop="isDisabled">
        <template #default="scope">
          <dict-tag :options="security_special_device" :value="scope.row.isDisabled"/>
        </template>
      </el-table-column>
      <el-table-column label="特征设备类别" align="center" prop="specialEquipememtCategory" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:specialoperationscertificate:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:specialoperationscertificate:remove']">删除</el-button>
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

    <!-- 添加或修改特种设备台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="specialoperationscertificateRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="区域" prop="area">
          <el-input v-model="form.area" placeholder="请输入区域" />
        </el-form-item>
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="设备名称" prop="equipmentName">
          <el-input v-model="form.equipmentName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备类别" prop="equipmentCategory">
          <el-input v-model="form.equipmentCategory" placeholder="请输入设备类别" />
        </el-form-item>
        <el-form-item label="设备型号" prop="equipmentModel">
          <el-input v-model="form.equipmentModel" placeholder="请输入设备型号" />
        </el-form-item>
        <el-form-item label="内部编号" prop="internalNumber">
          <el-input v-model="form.internalNumber" placeholder="请输入内部编号" />
        </el-form-item>
        <el-form-item label="使用证号" prop="usageCertificateNumber">
          <el-input v-model="form.usageCertificateNumber" placeholder="请输入使用证号" />
        </el-form-item>
        <el-form-item label="发证日期" prop="issuanceDate">
          <el-date-picker clearable
            v-model="form.issuanceDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择发证日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="制造单位" prop="manufacturingUnit">
          <el-input v-model="form.manufacturingUnit" placeholder="请输入制造单位" />
        </el-form-item>
        <el-form-item label="检验日期" prop="inspectionDate">
          <el-date-picker clearable
            v-model="form.inspectionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择检验日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="下次检验日期" prop="nextInspectionDate">
          <el-date-picker clearable
            v-model="form.nextInspectionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择下次检验日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否停用" prop="isDisabled">
          <el-select v-model="form.isDisabled" placeholder="请选择是否停用">
            <el-option
              v-for="dict in security_special_device"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="特征设备类别" prop="specialEquipememtCategory">
          <el-input v-model="form.specialEquipememtCategory" placeholder="请输入特征设备类别" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 特种设备台账导入对话框 -->
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据（基于内部编号匹配）
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

<script setup name="Specialoperationscertificate">
import { listSpecialoperationscertificate, getSpecialoperationscertificate, delSpecialoperationscertificate, addSpecialoperationscertificate, updateSpecialoperationscertificate } from "@/api/security/specialoperationscertificate";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
const { security_special_device } = proxy.useDict('security_special_device');
const route = useRoute();
const specialoperationscertificateList = ref([]);
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
  url: import.meta.env.VITE_APP_BASE_API + "/security/specialoperationscertificate/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    area: null,
    unitName: null,
    equipmentName: null,
    equipmentCategory: null,
    equipmentModel: null,
    internalNumber: null,
    usageCertificateNumber: null,
    isDisabled: null,
  },
  rules: {
    unitName: [
      { required: true, message: "单位名称不能为空", trigger: "blur" }
    ],
    equipmentName: [
      { required: true, message: "设备名称不能为空", trigger: "blur" }
    ],
    specialEquipememtCategory: [
      { required: true, message: "特征设备类别不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询特种设备台账列表 */
function getList() {
  loading.value = true;
  listSpecialoperationscertificate(queryParams.value).then(response => {
    specialoperationscertificateList.value = response.rows;
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
    area: null,
    unitName: null,
    equipmentName: null,
    equipmentCategory: null,
    equipmentModel: null,
    internalNumber: null,
    usageCertificateNumber: null,
    issuanceDate: null,
    manufacturingUnit: null,
    inspectionDate: null,
    nextInspectionDate: null,
    isDisabled: null,
    specialEquipememtCategory: null
  };
  proxy.resetForm("specialoperationscertificateRef");
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
  title.value = "添加特种设备台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSpecialoperationscertificate(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改特种设备台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["specialoperationscertificateRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSpecialoperationscertificate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSpecialoperationscertificate(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除特种设备台账编号为"' + _ids + '"的数据项？').then(function() {
    return delSpecialoperationscertificate(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/specialoperationscertificate/export', {
    ...queryParams.value
  }, `specialoperationscertificate_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "特种设备台账导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('/security/specialoperationscertificate/importTemplate', {}, `特种设备台账导入模板_${new Date().getTime()}.xlsx`);
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
