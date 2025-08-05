<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" v-show="showSearch" class="search-form">
      <el-row :gutter="24">
        <el-col :span="6">
      <el-form-item label="所在班组" prop="changsuoBanzu">
        <el-input
          v-model="queryParams.changsuoBanzu"
          placeholder="请输入所在班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
      <el-form-item label="岗位/工序/设备" prop="gongxuShebei">
        <el-input
          v-model="queryParams.gongxuShebei"
          placeholder="请输入岗位/工序/设备"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="危险物质/能量" prop="jituBuwei">
        <el-input
          v-model="queryParams.jituBuwei"
          placeholder="请输入危险物质、作业、能量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="事故类型" prop="keNengFaSheng">
        <el-input
          v-model="queryParams.keNengFaSheng"
          placeholder="请输入可能发生的事故类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="24">
        <el-col :span="6">
      <el-form-item label="风险等级" prop="fenXianDengJi">
        <el-input
          v-model="queryParams.fenXianDengJi"
          placeholder="请输入风险等级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="责任人" prop="fuZeRen">
        <el-input
          v-model="queryParams.fuZeRen"
          placeholder="请输入本岗位责任人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="网格负责人" prop="wangLuoFuZeRen">
        <el-input
          v-model="queryParams.wangLuoFuZeRen"
          placeholder="请输入网格负责人及联系电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
        </el-col>
        <el-col :span="6">
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['security:DangerWangList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:DangerWangList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:DangerWangList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:DangerWangList:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:DangerWangList:import']"
        >导入</el-button>
      </el-col>
      <!-- 添加下载模板按钮 -->
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Document"
          @click="importTemplate"
          v-hasPermi="['security:DangerWangList:export']"
        >下载模板</el-button>
      </el-col>
            <!-- 添加返回上一级按钮 -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Back"
          @click="handleGoBack"
        >返回</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="DangerWangListList" 
      @selection-change="handleSelectionChange"
      class="danger-wang-list-table"
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
      <el-table-column label="所在班组" align="center" prop="changsuoBanzu" width="120" />
      <el-table-column label="岗位/工序/设备" align="center" prop="gongxuShebei" width="150" />
      <el-table-column label="危险物质、作业、能量" align="center" prop="jituBuwei" width="180" />
      <el-table-column label="风险因素" align="center" prop="zhuyaoWeixian" width="150" />
      <el-table-column label="可能发生的事故类型" align="center" prop="keNengFaSheng" width="180" />
      <el-table-column label="L" align="center" prop="scoreL" width="60" />
      <el-table-column label="S" align="center" prop="scoreS" width="60" />
      <el-table-column label="R(LS)" align="center" prop="scoreLs" width="80" />
      <el-table-column label="风险等级" align="center" prop="fenXianDengJi" width="100" />
      <el-table-column label="管控/预防措施" align="center" prop="guankongYufang" min-width="200" show-overflow-tooltip />
      <el-table-column label="本岗位责任人" align="center" prop="fuZeRen" width="120" />
      <el-table-column label="网格负责人及联系电话" align="center" prop="wangLuoFuZeRen" width="180" />
      <el-table-column label="备注" align="center" prop="zhuyaoGongzuo" width="120" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="150" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:DangerWangList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:DangerWangList:remove']">删除</el-button>
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

    <!-- 添加或修改风险网格化清单对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body destroy-on-close>
      <el-form ref="DangerWangListRef" :model="form" :rules="rules" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
        <el-form-item label="所在班组" prop="changsuoBanzu">
          <el-input v-model="form.changsuoBanzu" placeholder="请输入所在班组" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="岗位/工序/设备" prop="gongxuShebei">
          <el-input v-model="form.gongxuShebei" placeholder="请输入岗位/工序/设备" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="危险物质/能量" prop="jituBuwei">
          <el-input v-model="form.jituBuwei" placeholder="请输入危险物质、作业、能量" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="风险因素" prop="zhuyaoWeixian">
          <el-input v-model="form.zhuyaoWeixian" placeholder="请输入风险因素" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="可能事故类型" prop="keNengFaSheng">
          <el-input v-model="form.keNengFaSheng" placeholder="请输入可能发生的事故类型" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
        <el-form-item label="L" prop="scoreL">
          <el-input v-model="form.scoreL" placeholder="请输入L" />
        </el-form-item>
          </el-col>
          <el-col :span="8">
        <el-form-item label="S" prop="scoreS">
          <el-input v-model="form.scoreS" placeholder="请输入S" />
        </el-form-item>
          </el-col>
          <el-col :span="8">
        <el-form-item label="R(LS)" prop="scoreLs">
          <el-input v-model="form.scoreLs" placeholder="请输入R(LS)" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
        <el-form-item label="风险等级" prop="fenXianDengJi">
          <el-input v-model="form.fenXianDengJi" placeholder="请输入风险等级" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="本岗位责任人" prop="fuZeRen">
          <el-input v-model="form.fuZeRen" placeholder="请输入本岗位责任人" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="管控/预防措施" prop="guankongYufang">
              <el-input v-model="form.guankongYufang" type="textarea" :rows="3" placeholder="请输入管控/预防措施" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="网格负责人电话" prop="wangLuoFuZeRen">
          <el-input v-model="form.wangLuoFuZeRen" placeholder="请输入网格负责人及联系电话" />
        </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
        <el-form-item label="备注" prop="zhuyaoGongzuo">
          <el-input v-model="form.zhuyaoGongzuo" placeholder="请输入备注" />
        </el-form-item>
          </el-col>
          <el-col :span="12">
            <!-- <el-form-item label="关联ID" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联id" />
            </el-form-item> -->
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 风险网格化清单导入对话框 -->
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
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <!-- 移除更新选项 -->
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
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

<script setup name="DangerWangList">
import { listDangerWangList, getDangerWangList, delDangerWangList, addDangerWangList, updateDangerWangList, listDangerWangListByRelatedId } from "@/api/security/DangerWangList";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';

const { proxy } = getCurrentInstance();
const route = useRoute();

const DangerWangListList = ref([]);
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
  // 是否更新已经存在的用户数据 - 修改为始终为true
  updateSupport: true,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/DangerWangList/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    changsuoBanzu: null,
    gongxuShebei: null,
    jituBuwei: null,
    keNengFaSheng: null,
    fenXianDengJi: null,
    fuZeRen: null,
    wangLuoFuZeRen: null,
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

// 查询风险网格化清单列表
function getList() {
  loading.value = true;
  listDangerWangList(queryParams.value).then(response => {
    DangerWangListList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
// 返回上一级页面
function handleGoBack() {
  proxy.$router.go(-1);
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
    changsuoBanzu: null,
    gongxuShebei: null,
    jituBuwei: null,
    zhuyaoWeixian: null,
    keNengFaSheng: null,
    scoreL: null,
    scoreS: null,
    scoreLs: null,
    fenXianDengJi: null,
    guankongYufang: null,
    fuZeRen: null,
    wangLuoFuZeRen: null,
    zhuyaoGongzuo: null,
    relatedId: null
  };
  proxy.resetForm("DangerWangListRef");
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
  // 如果当前有关联ID，则在新增记录时自动填充关联ID
  if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加风险网格化清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getDangerWangList(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改风险网格化清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["DangerWangListRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDangerWangList(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDangerWangList(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除风险网格化清单编号为"' + _ids + '"的数据项？').then(function() {
    return delDangerWangList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/DangerWangList/export', {
    ...queryParams.value
  }, `DangerWangList_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.open = true;
  upload.title = "导入风险网格化清单";
}

/** 文件上传中处理 */
function handleFileUploadProgress(event) {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.isUploading = false;
  proxy.$modal.msgSuccess("导入成功");
  upload.open = false;
  getList();
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/DangerWangList/importTemplate', {}, `风险网格化清单导入模板_${new Date().getTime()}.xlsx`, 'get');
}

/** 提交导入 */
function submitFileForm() {
  proxy.$refs.uploadRef.submit();
}

// 检查关联ID参数
function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    loadDataByRelatedId(relatedId);
  } else {
    getList(); // 如果没有关联ID参数，加载所有数据
  }
}

// 根据关联ID加载数据
function loadDataByRelatedId(relatedId) {
  loading.value = true;
  listDangerWangListByRelatedId(relatedId).then(response => {
    DangerWangListList.value = response.rows;
    total.value = response.total;
    loading.value = false;
    proxy.$modal.msgSuccess("已加载关联文件数据");
  }).catch(error => {
    console.error("加载关联数据失败:", error);
    loading.value = false;
    proxy.$modal.msgError("加载关联数据失败，请重试");
  });
}

onMounted(() => {
  checkRelatedId();
});
</script>

<style scoped>
.danger-wang-list-table {
  margin-top: 15px;
  margin-bottom: 15px;
}

.danger-wang-list-table :deep(.el-table__header) {
  background-color: #f5f7fa;
}

.danger-wang-list-table :deep(.el-table__row:hover) {
  background-color: #f0f9eb;
}

.dialog-footer {
  text-align: center;
  margin-top: 10px;
}

.el-form-item {
  margin-bottom: 18px;
}

.el-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e8e8e8;
  padding: 15px 20px;
  background-color: #f5f7fa;
}

.el-dialog :deep(.el-dialog__body) {
  padding: 20px 25px;
}

.el-dialog :deep(.el-dialog__footer) {
  border-top: 1px solid #e8e8e8;
  padding: 15px 20px;
}

/* 美化导入对话框 */
.el-upload {
  width: 100%;
}

.el-upload :deep(.el-upload-dragger) {
  width: 100%;
  height: 200px;
}

.el-upload__tip {
  margin-top: 10px;
  line-height: 1.5;
}

/* 搜索表单样式 */
.search-form {
  background-color: #f8f8f8;
  padding: 15px 15px 0;
  margin-bottom: 20px;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.search-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: #606266;
}

.search-form :deep(.el-input__wrapper) {
  border-radius: 4px;
}

.search-form :deep(.el-button) {
  padding: 10px 20px;
  margin-right: 10px;
}
</style>
