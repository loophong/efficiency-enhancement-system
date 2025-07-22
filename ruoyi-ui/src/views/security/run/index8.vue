<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryRef"
      :inline="true"
      v-show="showSearch"
      label-width="120px"
      class="search-form"
    >
      <el-form-item label="主管科室" prop="responsibleDepartment">
        <el-input
          v-model="queryParams.responsibleDepartment"
          placeholder="请输入主管科室"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="相关方名称" prop="relatedPartyName">
        <el-input
          v-model="queryParams.relatedPartyName"
          placeholder="请输入相关方名称"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主要联系人" prop="mainContactPerson">
        <el-input
          v-model="queryParams.mainContactPerson"
          placeholder="请输入主要联系人"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="我公司对接人员" prop="ourCompanyContactPerson">
        <el-input
          v-model="queryParams.ourCompanyContactPerson"
          placeholder="请输入我公司对接人员"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否签订安全管理协议" prop="isSafetyManagementAgreementSigned">
        <el-select
          v-model="queryParams.isSafetyManagementAgreementSigned"
          placeholder="请选择"
          clearable
          style="width: 200px"
        >
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="签订时间" prop="agreementSigningDate">
        <el-date-picker
          v-model="queryParams.agreementSigningDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择签订时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="进厂时间" prop="factoryEntryStartDate">
        <el-date-picker
          v-model="queryParams.factoryEntryStartDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择进厂时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>

      <!-- <el-form-item label="外来参观" prop="waiLai">
        <el-select
          v-model="queryParams.waiLai"
          placeholder="请选择"
          clearable
          style="width: 200px"
        >
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="实习生" prop="shiXi">
        <el-select
          v-model="queryParams.shiXi"
          placeholder="请选择"
          clearable
          style="width: 200px"
        >
          <el-option label="是" value="1" />
          <el-option label="否" value="0" />
        </el-select>
      </el-form-item> -->
      <el-form-item>
        <el-button
          type="primary"
          icon="Search"
          @click="handleQuery"
          style="margin-right: 10px"
        >搜索</el-button>
        <el-button
          icon="Refresh"
          @click="resetQuery"
        >重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['security:relatedpartyledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:relatedpartyledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:relatedpartyledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:relatedpartyledger:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:relatedpartyledger:import']"
        >导入</el-button>
      </el-col>
      <!-- 下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="handleImportTemplate"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="relatedpartyledgerList"
      @selection-change="handleSelectionChange"
      class="related-party-ledger-table"
      border
      stripe
      :header-cell-style="{
        background: '#f5f7fa',
        color: '#303133',
        fontWeight: 'bold',
        textAlign: 'center',
        fontSize: '14px'
      }"
      :cell-style="{
        textAlign: 'center',
        padding: '12px 8px',
        fontSize: '13px'
      }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="80" />
      <el-table-column label="主管科室" align="center" prop="responsibleDepartment" min-width="120" show-overflow-tooltip />
      <el-table-column label="相关方名称" align="center" prop="relatedPartyName" min-width="150" show-overflow-tooltip />
      <el-table-column label="主要联系人" align="center" prop="mainContactPerson" min-width="120" show-overflow-tooltip />
      <el-table-column label="我公司对接人员" align="center" prop="ourCompanyContactPerson" min-width="130" show-overflow-tooltip />
      <el-table-column label="是否签订安全管理协议" align="center" prop="isSafetyManagementAgreementSigned" min-width="160" show-overflow-tooltip>
        <template #default="scope">
          <el-tag :type="scope.row.isSafetyManagementAgreementSigned == '1' ? 'success' : 'info'">
            {{ scope.row.isSafetyManagementAgreementSigned == '1' ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="签订时间" align="center" prop="agreementSigningDate" width="120" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.agreementSigningDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="进厂时间" align="center" prop="factoryEntryStartDate" width="120" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.factoryEntryStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="外来参观" align="center" prop="waiLai" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.waiLai === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="实习生" align="center" prop="shiXi" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.shiXi === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="劳务派遣、外包等" align="center" prop="laoWu" width="150">
        <template #default="scope">
          <el-tag v-if="scope.row.laoWu === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="施工作业类" align="center" prop="shiGong" width="120">
        <template #default="scope">
          <el-tag v-if="scope.row.shiGong === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="清洁检测服务" align="center" prop="qinjieJianceFuwu" width="140">
        <template #default="scope">
          <el-tag v-if="scope.row.qinjieJianceFuwu === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="物流配送" align="center" prop="wuLiu" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.wuLiu === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="驻厂相关方" align="center" prop="zhuCangXiangguanfang" width="120">
        <template #default="scope">
          <el-tag v-if="scope.row.zhuCangXiangguanfang === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="其他" align="center" prop="other" width="80">
        <template #default="scope">
          <el-tag v-if="scope.row.other === '1'" type="success">是</el-tag>
          <el-tag v-else type="info">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="相关方活动区域" align="center" prop="otherActivity" min-width="150" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="remark" min-width="120" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:relatedpartyledger:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:relatedpartyledger:remove']"
            size="small"
          >删除</el-button>
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

    <!-- 添加或修改相关方管理台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="relatedpartyledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主管科室" prop="responsibleDepartment">
          <el-input v-model="form.responsibleDepartment" placeholder="请输入主管科室" />
        </el-form-item>
        <el-form-item label="相关方名称" prop="relatedPartyName">
          <el-input v-model="form.relatedPartyName" placeholder="请输入相关方名称" />
        </el-form-item>
        <el-form-item label="主要联系人" prop="mainContactPerson">
          <el-input v-model="form.mainContactPerson" placeholder="请输入主要联系人" />
        </el-form-item>
        <el-form-item label="我公司对接人员" prop="ourCompanyContactPerson">
          <el-input v-model="form.ourCompanyContactPerson" placeholder="请输入我公司对接人员" />
        </el-form-item>
        <el-form-item label="是否签订安全管理协议" prop="isSafetyManagementAgreementSigned">
          <el-select v-model="form.isSafetyManagementAgreementSigned" placeholder="请选择是否签订安全管理协议">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="签订时间" prop="agreementSigningDate">
          <el-date-picker clearable
            v-model="form.agreementSigningDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择签订时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="进厂时间" prop="factoryEntryStartDate">
          <el-date-picker clearable
            v-model="form.factoryEntryStartDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择进厂时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="服务类型">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-checkbox v-model="form.waiLai" true-label="1" false-label="0">外来参观</el-checkbox>
            </el-col>
            <el-col :span="8">
              <el-checkbox v-model="form.shiXi" true-label="1" false-label="0">实习生</el-checkbox>
            </el-col>
            <el-col :span="8">
              <el-checkbox v-model="form.laoWu" true-label="1" false-label="0">劳务派遣、外包等</el-checkbox>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 10px;">
            <el-col :span="8">
              <el-checkbox v-model="form.shiGong" true-label="1" false-label="0">施工作业类</el-checkbox>
            </el-col>
            <el-col :span="8">
              <el-checkbox v-model="form.qinjieJianceFuwu" true-label="1" false-label="0">清洁检测服务</el-checkbox>
            </el-col>
            <el-col :span="8">
              <el-checkbox v-model="form.wuLiu" true-label="1" false-label="0">物流配送</el-checkbox>
            </el-col>
          </el-row>
          <el-row :gutter="20" style="margin-top: 10px;">
            <el-col :span="8">
              <el-checkbox v-model="form.zhuCangXiangguanfang" true-label="1" false-label="0">驻厂相关方</el-checkbox>
            </el-col>
            <el-col :span="8">
              <el-checkbox v-model="form.other" true-label="1" false-label="0">其他</el-checkbox>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="相关方活动区域" prop="otherActivity">
          <el-input v-model="form.otherActivity" placeholder="请输入相关方活动区域" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据（不勾选则允许重复数据导入）
            </div>
            <span>仅允许导入xls、xlsx格式文件。支持空值、重复名称和完全重复数据导入。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="handleImportTemplate">下载模板</el-link>
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

<script setup name="Relatedpartyledger">
import { listRelatedpartyledger, getRelatedpartyledger, delRelatedpartyledger, addRelatedpartyledger, updateRelatedpartyledger, importRelatedpartyledger, downloadTemplate, listByRelatedId } from "@/api/security/relatedpartyledger";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();
// 移除字典引用，因为不再使用服务性质字典
const route = useRoute();
const relatedpartyledgerList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 上传参数
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
  url: import.meta.env.VITE_APP_BASE_API + "/security/relatedpartyledger/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    responsibleDepartment: null,
    relatedPartyName: null,
    mainContactPerson: null,
    ourCompanyContactPerson: null,
    isSafetyManagementAgreementSigned: null,
    agreementSigningDate: null,
    factoryEntryStartDate: null,
    waiLai: null,
    shiXi: null,
    laoWu: null,
    shiGong: null,
    qinjieJianceFuwu: null,
    wuLiu: null,
    zhuCangXiangguanfang: null,
    other: null,
  },
  rules: {
    responsibleDepartment: [
      { required: true, message: "主管科室不能为空", trigger: "blur" }
    ],
    relatedPartyName: [
      { required: true, message: "相关方名称不能为空", trigger: "blur" }
    ],
    mainContactPerson: [
      { required: true, message: "主要联系人不能为空", trigger: "blur" }
    ],
    ourCompanyContactPerson: [
      { required: true, message: "我公司对接人员不能为空", trigger: "blur" }
    ],
    isSafetyManagementAgreementSigned: [
      { required: true, message: "是否签订安全管理协议不能为空", trigger: "blur" }
    ],
    agreementSigningDate: [
      { required: true, message: "签订时间不能为空", trigger: "blur" }
    ],
    factoryEntryStartDate: [
      { required: true, message: "进厂时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询相关方管理台账列表 */
function getList() {
  loading.value = true;
  listRelatedpartyledger(queryParams.value).then(response => {
    relatedpartyledgerList.value = response.rows;
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
    responsibleDepartment: null,
    relatedPartyName: null,
    mainContactPerson: null,
    ourCompanyContactPerson: null,
    isSafetyManagementAgreementSigned: "0",
    agreementSigningDate: null,
    factoryEntryStartDate: null,
    waiLai: "0",
    shiXi: "0",
    laoWu: "0",
    shiGong: "0",
    qinjieJianceFuwu: "0",
    wuLiu: "0",
    zhuCangXiangguanfang: "0",
    other: "0",
    otherActivity: null,
    relatedId: null,
    remark: null
  };
  proxy.resetForm("relatedpartyledgerRef");
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
  title.value = "添加相关方管理台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRelatedpartyledger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改相关方管理台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["relatedpartyledgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRelatedpartyledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRelatedpartyledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除相关方管理台账编号为"' + _ids + '"的数据项？').then(function() {
    return delRelatedpartyledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/relatedpartyledger/export', {
    ...queryParams.value
  }, `relatedpartyledger_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "相关方管理台账导入";
  upload.open = true;
}

/** 下载模板操作 */
function handleImportTemplate() {
  downloadTemplate().then(response => {
    proxy.download('security/relatedpartyledger/importTemplate', {}, `相关方管理台账导入模板_${new Date().getTime()}.xlsx`, 'post');
  }); 
}

// 文件上传中处理
function handleFileUploadProgress() {
  upload.isUploading = true;
}

// 文件上传成功处理
function handleFileSuccess(response) {
  upload.isUploading = false;
  upload.open = false;
  proxy.$refs.uploadRef.clearFiles();
  proxy.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

// 文件上传失败处理
function handleFileError() {
  upload.isUploading = false;
  proxy.$modal.msgError("导入失败，请检查数据格式是否正确");
}

// 提交上传文件
function submitFileForm() {
  proxy.$refs.uploadRef.submit();
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
    proxy.$modal.msgSuccess("已加载关联的相关方管理台账数据");
  } else {
    // 没有关联ID时，加载所有数据
    getList();
  }
}

// 初始化时检查关联ID
onMounted(() => {
  // 检查关联ID参数并加载相应数据
  checkRelatedId();
});


</script>

<style scoped>
.search-form {
  background: #f8f9fa;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.search-form .el-form-item {
  margin-bottom: 16px;
  margin-right: 20px;
}

.search-form .el-form-item__label {
  color: #606266;
  font-weight: 500;
  font-size: 14px;
}

.search-form .el-input__inner,
.search-form .el-select .el-input__inner {
  border-radius: 6px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

.search-form .el-input__inner:focus,
.search-form .el-select .el-input__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.search-form .el-button {
  border-radius: 6px;
  padding: 10px 20px;
  font-weight: 500;
  transition: all 0.3s;
}

.search-form .el-button--primary {
  background: linear-gradient(135deg, #409eff 0%, #66b3ff 100%);
  border: none;
}

.search-form .el-button--primary:hover {
  background: linear-gradient(135deg, #337ecc 0%, #5aa3e6 100%);
  transform: translateY(-1px);
  box-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
}

.search-form .el-button:not(.el-button--primary) {
  background: #ffffff;
  border: 1px solid #dcdfe6;
  color: #606266;
}

.search-form .el-button:not(.el-button--primary):hover {
  background: #f5f7fa;
  border-color: #c0c4cc;
  color: #409eff;
}

.related-party-ledger-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
