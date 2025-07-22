<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode" style="width: 320px;">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商代码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName" style="width: 320px;">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生时间" prop="happenTime">
        <el-date-picker clearable
          v-model="queryParams.happenTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择发生时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="完成时间" prop="completeTime">
        <el-date-picker clearable
          v-model="queryParams.completeTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择完成时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="截止时间" prop="deadline">
        <el-date-picker clearable
          v-model="queryParams.deadline"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择截止时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="填报人" prop="uploadName" style="width: 320px;">
        <el-input
          v-model="queryParams.uploadName"
          placeholder="请输入填报人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="备选1" prop="one">
        <el-input
          v-model="queryParams.one"
          placeholder="请输入备选1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备选2" prop="two">
        <el-input
          v-model="queryParams.two"
          placeholder="请输入备选2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备选3" prop="three">
        <el-input
          v-model="queryParams.three"
          placeholder="请输入备选3"
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
          v-hasPermi="['supplier:happen:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:happen:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:happen:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:happen:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="happenList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />

      <!-- <el-table-column label="文件路径" align="center" prop="fileUrl"  width="380"/> -->
      <!-- <el-table-column label="文件操作" align="center" prop="fileUrl" width="200">
          <template #default="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handlePreview(scope.row.fileUrl)"
              :disabled="!scope.row.fileUrl"
            >预览</el-button>
            <el-button
              size="mini"
              type="success"
              @click="handleDownload(scope.row.fileUrl)"
              :disabled="!scope.row.fileUrl"
            >下载</el-button>
          </template>
</el-table-column> -->
<el-table-column label="发生时间" align="center" prop="happenTime" width="120">
        <template #default="scope">
          <span>{{ parseTime(scope.row.happenTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="回函截止时间" align="center" prop="deadline" width="120">
        <template #default="scope">
          <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="反馈单文件名" align="center" prop="feedbackFileName" />
      <el-table-column label="反馈单附件" align="center" prop="feedbackFileUrl" width="190">
        <template #default="scope">
          <div v-if="scope.row.feedbackFileUrl">
            <div v-for="(fileUrl, index) in getFileList(scope.row.feedbackFileUrl)" :key="index" style="margin-bottom: 5px;">
              <el-button
                size="mini"
                type="primary"
                @click="handlePreview(fileUrl)"
              >预览{{ index + 1 }}</el-button>
              <el-button
                size="mini"
                type="success"
                @click="handleDownload(fileUrl)"
              >下载{{ index + 1 }}</el-button>
            </div>
          </div>
          <span v-else>暂无文件</span>
        </template>
      </el-table-column>
      <el-table-column label="回函文件名" align="center" prop="replyFileName" />
      <el-table-column label="回函附件" align="center" prop="replyFileUrl" width="190">
        <template #default="scope">
          <div v-if="scope.row.replyFileUrl">
            <div v-for="(fileUrl, index) in getFileList(scope.row.replyFileUrl)" :key="index" style="margin-bottom: 5px;">
              <el-button
                size="mini"
                type="primary"
                @click="handlePreview(fileUrl)"
              >预览{{ index + 1 }}</el-button>
              <el-button
                size="mini"
                type="success"
                @click="handleDownload(fileUrl)"
              >下载{{ index + 1 }}</el-button>
            </div>
          </div>
          <span v-else>暂无文件</span>
        </template>
      </el-table-column>

      <el-table-column label="回函上传时间" align="center" prop="replyTime" width="120">
        <template #default="scope">
          <span>{{ parseTime(scope.row.replyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="回函状态" align="center" prop="replyStatus" /> -->
      <el-table-column label="回函状态" align="center">
  <template #default="scope">
    <span>{{ getReplyStatus(scope.row) }}</span>
  </template>
</el-table-column>
      <!-- <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="填报人" align="center" prop="createBy" /> -->
      <!-- <el-table-column label="备选1" align="center" prop="one" />
      <el-table-column label="备选2" align="center" prop="two" />
      <el-table-column label="备选3" align="center" prop="three" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:happen:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:happen:remove']">删除</el-button>
          <el-button link type="warning" icon="Message" @click="handleReply(scope.row)" v-hasPermi="['supplier:happen:reply']">回函</el-button>
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
    <!-- 添加或修改质量通知单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="happenRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="发生时间" prop="happenTime">
          <el-date-picker clearable
            v-model="form.happenTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择发生时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="回函截止时间" prop="deadline">
          <el-date-picker clearable
            v-model="form.deadline"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择回函截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="反馈单文件名" prop="feedbackFileName">
          <el-input v-model="form.feedbackFileName" placeholder="请输入反馈单文件名" />
        </el-form-item>
        <el-form-item label="反馈单附件路径" prop="feedbackFileUrl">
          <file-upload v-model="form.feedbackFileUrl"/>
        </el-form-item>
        <!-- <el-form-item label="回函文件名" prop="replyFileName">
          <el-input v-model="form.replyFileName" placeholder="请输入回函文件名" />
        </el-form-item>
        <el-form-item label="回函附件路径" prop="replyFileUrl">
          <file-upload v-model="form.replyFileUrl"/>
        </el-form-item>
        <el-form-item label="回函上传时间" prop="replyTime">
          <el-date-picker clearable
            v-model="form.replyTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择回函上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备选1" prop="one">
          <el-input v-model="form.one" placeholder="请输入备选1" />
        </el-form-item>
        <el-form-item label="备选2" prop="two">
          <el-input v-model="form.two" placeholder="请输入备选2" />
        </el-form-item>
        <el-form-item label="备选3" prop="three">
          <el-input v-model="form.three" placeholder="请输入备选3" />
        </el-form-item> -->
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

<script setup name="Happen">
import { listHappen, getHappen, delHappen, addHappen, updateHappen } from "@/api/supplier/happen";
import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'

const { proxy } = getCurrentInstance();

const happenList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const previewDialogVisible = ref(false);
const previewSrc = ref('');
const comStyle = ref({
  height: '100vh'
});

function renderedHandler() {
  console.log('文档渲染完成');
}

function errorHandler(error) {
  console.error('文档预览失败:', error);
  proxy.$modal.msgError('文档预览失败');
}

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    happenTime: null,
    deadline: null,
    feedbackFileName: null,
    feedbackFileUrl: null,
    replyFileName: null,
    replyFileUrl: null,
    replyTime: null,
    replyStatus: null,
    createTime: null,
    createBy: null,
    one: null,
    two: null,
    three: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

const replyOpen = ref(false);
const replyForm = reactive({
  supplierCode: '',
  supplierName: '',
  replyTime: '',
  replyFileName: '',
  replyFileUrl: ''
});
function handleReply(row) {
  replyForm.id = row.id;
  replyForm.supplierCode = row.supplierCode;
  replyForm.supplierName = row.supplierName;
  replyForm.replyTime = row.replyTime || '';
  replyForm.replyFileName = row.replyFileName || '';
  replyForm.replyFileUrl = row.replyFileUrl || '';
  replyOpen.value = true;
}

// 提交回函
function submitReply() {
  // if (!replyForm.replyTime || !replyForm.replyFileName || !replyForm.replyFileUrl) {
  //   proxy.$modal.msgError("请填写完整回函信息！");
  //   return;
  // }
  updateHappen({
    id: replyForm.id,
    replyTime: replyForm.replyTime,
    replyFileName: replyForm.replyFileName,
    replyFileUrl: replyForm.replyFileUrl
  }).then(() => {
    proxy.$modal.msgSuccess("回函信息已提交！");
    replyOpen.value = false;
    getList();
  });
}
function handlePreview(fileUrl) {
  const baseUrl = import.meta.env.VITE_APP_BASE_API || "";
  const fullUrl = baseUrl + fileUrl;
  const fileExt = fileUrl.split('.').pop().toLowerCase();
  
  if (fileExt === 'docx' || fileExt === 'doc') {
    previewSrc.value = fullUrl;
    previewDialogVisible.value = true;
    console.log('预览文件路径:', previewSrc.value);
  } else {
    // 其他文件类型用原来的方式预览
    window.open(fullUrl, '_blank');
  }
}

function handleDownload(feedbackFileUrl) {
  const baseUrl = import.meta.env.VITE_APP_BASE_API || "";
  const link = document.createElement('a');
  link.href = baseUrl + feedbackFileUrl;
  link.download = '';
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}
function getReplyStatus(row) {
  if (!row.replyTime) {
    return "未回函";
  }
  if (row.deadline && row.replyTime > row.deadline) {
    return "回函不及时";
  }
  return "已回函";
}

// 解析文件URL字符串为数组
function getFileList(fileUrls) {
  if (!fileUrls) return [];
  return fileUrls.split(',').filter(url => url.trim());
}

// 获取文件名
function getFileName(fileUrl) {
  if (!fileUrl) return '';
  const fileName = fileUrl.split('/').pop();
  return fileName.split('.')[0]; // 去掉扩展名
}



/** 查询质量通知单列表 */
function getList() {
  loading.value = true;
  listHappen(queryParams.value).then(response => {
    happenList.value = response.rows;
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
    supplierCode: null,
    supplierName: null,
    happenTime: null,
    deadline: null,
    feedbackFileName: null,
    feedbackFileUrl: null,
    replyFileName: null,
    replyFileUrl: null,
    replyTime: null,
    replyStatus: null,
    createTime: null,
    createBy: null,
    one: null,
    two: null,
    three: null
  };
  proxy.resetForm("happenRef");
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
  title.value = "添加质量通知单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHappen(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改质量通知单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["happenRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHappen(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHappen(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除质量通知单编号为"' + _ids + '"的数据项？').then(function() {
    return delHappen(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/happen/export', {
    ...queryParams.value
  }, `happen_${new Date().getTime()}.xlsx`)
}

getList();
</script>
