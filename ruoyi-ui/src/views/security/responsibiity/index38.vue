<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="责任科室" prop="zerenKeshi">
        <el-input
          v-model="queryParams.zerenKeshi"
          placeholder="请输入责任科室"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场所/班组" prop="changsuoBanzu">
        <el-input
          v-model="queryParams.changsuoBanzu"
          placeholder="请输入场所/班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序/设备/区域" prop="gongxuShebei">
        <el-input
          v-model="queryParams.gongxuShebei"
          placeholder="请输入工序/设备/区域"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="具体部位" prop="jituBuwei">
        <el-input
          v-model="queryParams.jituBuwei"
          placeholder="请输入具体部位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作业工种" prop="zuoyeGongzong">
        <el-input
          v-model="queryParams.zuoyeGongzong"
          placeholder="请输入作业工种"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="主要危险源/危险物质" prop="zhuyaoWeixian">
        <el-input
          v-model="queryParams.zhuyaoWeixian"
          placeholder="请输入主要危险源/危险物质"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="可能发生的主要事故类型" prop="keNengFaSheng">
        <el-input
          v-model="queryParams.keNengFaSheng"
          placeholder="请输入可能发生的主要事故类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="网格负责人及联系电话" prop="wangLuoFuZeRen">
        <el-input
          v-model="queryParams.wangLuoFuZeRen"
          placeholder="请输入网格负责人及联系电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="主要工作内容" prop="zhuyaoGongzuo">
        <el-input
          v-model="queryParams.zhuyaoGongzuo"
          placeholder="请输入主要工作内容"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="关联id" prop="relatedId">
        <el-input
          v-model="queryParams.relatedId"
          placeholder="请输入关联id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
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
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="DangerWangListList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="责任科室" align="center" prop="zerenKeshi" />
      <el-table-column label="场所/班组" align="center" prop="changsuoBanzu" />
      <el-table-column label="工序/设备/区域" align="center" prop="gongxuShebei" />
      <el-table-column label="具体部位" align="center" prop="jituBuwei" />
      <el-table-column label="作业工种" align="center" prop="zuoyeGongzong" />
      <el-table-column label="主要危险源/危险物质" align="center" prop="zhuyaoWeixian" />
      <el-table-column label="可能发生的主要事故类型" align="center" prop="keNengFaSheng" />
      <el-table-column label="网格负责人及联系电话" align="center" prop="wangLuoFuZeRen" />
      <el-table-column label="主要工作内容" align="center" prop="zhuyaoGongzuo" />
      <!-- <el-table-column label="关联id" align="center" prop="relatedId" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="DangerWangListRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="责任科室" prop="zerenKeshi">
          <el-input v-model="form.zerenKeshi" placeholder="请输入责任科室" />
        </el-form-item>
        <el-form-item label="场所/班组" prop="changsuoBanzu">
          <el-input v-model="form.changsuoBanzu" placeholder="请输入场所/班组" />
        </el-form-item>
        <el-form-item label="工序/设备/区域" prop="gongxuShebei">
          <el-input v-model="form.gongxuShebei" placeholder="请输入工序/设备/区域" />
        </el-form-item>
        <el-form-item label="具体部位" prop="jituBuwei">
          <el-input v-model="form.jituBuwei" placeholder="请输入具体部位" />
        </el-form-item>
        <el-form-item label="作业工种" prop="zuoyeGongzong">
          <el-input v-model="form.zuoyeGongzong" placeholder="请输入作业工种" />
        </el-form-item>
        <el-form-item label="主要危险源/危险物质" prop="zhuyaoWeixian">
          <el-input v-model="form.zhuyaoWeixian" placeholder="请输入主要危险源/危险物质" />
        </el-form-item>
        <el-form-item label="可能发生的主要事故类型" prop="keNengFaSheng">
          <el-input v-model="form.keNengFaSheng" placeholder="请输入可能发生的主要事故类型" />
        </el-form-item>
        <el-form-item label="网格负责人及联系电话" prop="wangLuoFuZeRen">
          <el-input v-model="form.wangLuoFuZeRen" placeholder="请输入网格负责人及联系电话" />
        </el-form-item>
        <el-form-item label="主要工作内容" prop="zhuyaoGongzuo">
          <el-input v-model="form.zhuyaoGongzuo" placeholder="请输入主要工作内容" />
        </el-form-item>
        <!-- <el-form-item label="关联id" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联id" />
        </el-form-item> -->
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
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
import { listDangerWangList, getDangerWangList, delDangerWangList, addDangerWangList, updateDangerWangList, importDangerWangList, downloadTemplate } from "@/api/security/DangerWangList";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();

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
  // 是否更新已经存在的用户数据
  updateSupport: 0,
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
    zerenKeshi: null,
    changsuoBanzu: null,
    gongxuShebei: null,
    jituBuwei: null,
    zuoyeGongzong: null,
    zhuyaoWeixian: null,
    keNengFaSheng: null,
    wangLuoFuZeRen: null,
    zhuyaoGongzuo: null,
    relatedId: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询风险网格化清单列表 */
function getList() {
  loading.value = true;
  listDangerWangList(queryParams.value).then(response => {
    DangerWangListList.value = response.rows;
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
    zerenKeshi: null,
    changsuoBanzu: null,
    gongxuShebei: null,
    jituBuwei: null,
    zuoyeGongzong: null,
    zhuyaoWeixian: null,
    keNengFaSheng: null,
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
  upload.title = "风险网格化清单导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/DangerWangList/importTemplate', {}, `风险网格化清单导入模板_${new Date().getTime()}.xlsx`, 'post');
}

/** 文件上传中处理 */
function handleFileUploadProgress() {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

getList();
</script>
