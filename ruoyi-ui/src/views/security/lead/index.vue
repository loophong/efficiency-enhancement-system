<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="单位名称" prop="unitName">
        <el-input
            v-model="queryParams.unitName"
            placeholder="请输入单位名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重点工作名称" prop="keyWorkName">
        <el-input
            v-model="queryParams.keyWorkName"
            placeholder="请输入重点工作名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现状及要因 " prop="currentStatusAndCauses">
        <el-input
            v-model="queryParams.currentStatusAndCauses"
            placeholder="请输入现状及要因 "
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标" prop="target">
        <el-input
            v-model="queryParams.target"
            placeholder="请输入目标"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重1" prop="weight1">
        <el-input
            v-model="queryParams.weight1"
            placeholder="请输入权重1"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="权重2" prop="weight2">
        <el-input
            v-model="queryParams.weight2"
            placeholder="请输入权重2"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="一季度重点实施事项 	" prop="q1FocusImplementation">
        <el-input
            v-model="queryParams.q1FocusImplementation"
            placeholder="请输入一季度重点实施事项 	"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成结果" prop="q1CompletionResults">
        <el-input
            v-model="queryParams.q1CompletionResults"
            placeholder="请输入完成结果"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="得分" prop="q1Score">
        <el-input
            v-model="queryParams.q1Score"
            placeholder="请输入得分"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <!-- <el-form-item label="二季度重点实施事项 	" prop="q2FocusImplementation">
        <el-input
            v-model="queryParams.q2FocusImplementation"
            placeholder="请输入二季度重点实施事项 	"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成结果" prop="q2CompletionResults">
        <el-input
            v-model="queryParams.q2CompletionResults"
            placeholder="请输入完成结果"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="得分" prop="q2Score">
        <el-input
            v-model="queryParams.q2Score"
            placeholder="请输入得分"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="三季度重点实施事项" prop="q3FocusImplementation">
        <el-input
            v-model="queryParams.q3FocusImplementation"
            placeholder="请输入三季度重点实施事项"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="完成结果" prop="q3CompletionResults">
        <el-input
            v-model="queryParams.q3CompletionResults"
            placeholder="请输入完成结果"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="得分" prop="q3Score">
        <el-input
            v-model="queryParams.q3Score"
            placeholder="请输入得分"
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
            v-hasPermi="['security:works:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:works:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:works:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:works:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Upload"
            @click="handleImport"
            v-hasPermi="['security:works:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="info"
            plain
            icon="Download"
            @click="handleDownloadTemplate"
            v-hasPermi="['security:works:import']"
        >下载模板</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="worksList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="单位名称" align="center" prop="unitName" />
      <el-table-column label="重点工作名称" align="center" prop="keyWorkName" />
      <el-table-column label="现状及要因 " align="center" prop="currentStatusAndCauses" />
      <el-table-column label="目标" align="center" prop="target" />
      <el-table-column label="权重1" align="center" prop="weight1" />
      <el-table-column label="权重2" align="center" prop="weight2" />
      <el-table-column label="一季度重点实施事项" align="center" prop="q1FocusImplementation" />
      <el-table-column label="一季度完成结果" align="center" prop="q1CompletionResults" />
      <el-table-column label="一季度得分" align="center" prop="q1Score" />
      <el-table-column label="二季度重点实施事项" align="center" prop="q2FocusImplementation" />
      <el-table-column label="二季度完成结果" align="center" prop="q2CompletionResults" />
      <el-table-column label="二季度得分" align="center" prop="q2Score" />
      <el-table-column label="三季度重点实施事项" align="center" prop="q3FocusImplementation" />
      <el-table-column label="三季度完成结果" align="center" prop="q3CompletionResults" />
      <el-table-column label="三季度得分" align="center" prop="q3Score" />
      <el-table-column label="四季度重点实施事项" align="center" prop="q4FocusImplementation" />
      <el-table-column label="四季度完成结果" align="center" prop="q4CompletionResults" />
      <el-table-column label="四季度得分" align="center" prop="q4Score" />
      <el-table-column label="总分" align="center" prop="totalScore" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:works:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:works:remove']">删除</el-button>
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

    <!-- 添加或修改公司重点工作对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="worksRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="单位名称" prop="unitName">
          <el-input v-model="form.unitName" placeholder="请输入单位名称" />
        </el-form-item>
        <el-form-item label="重点工作名称" prop="keyWorkName">
          <el-input v-model="form.keyWorkName" placeholder="请输入重点工作名称" />
        </el-form-item>
        <el-form-item label="现状及要因" prop="currentStatusAndCauses">
          <el-input v-model="form.currentStatusAndCauses" type="textarea" placeholder="请输入现状及要因" />
        </el-form-item>
        <el-form-item label="目标" prop="target">
          <el-input v-model="form.target" placeholder="请输入目标" />
        </el-form-item>
        <el-form-item label="权重1" prop="weight1">
          <el-input v-model="form.weight1" placeholder="请输入权重1" />
        </el-form-item>
        <el-form-item label="权重2" prop="weight2">
          <el-input v-model="form.weight2" placeholder="请输入权重2" />
        </el-form-item>
        
        <el-divider content-position="center">一季度</el-divider>
        <el-form-item label="重点实施事项" prop="q1FocusImplementation">
          <el-input v-model="form.q1FocusImplementation" type="textarea" placeholder="请输入一季度重点实施事项" />
        </el-form-item>
        <el-form-item label="完成结果" prop="q1CompletionResults">
          <el-input v-model="form.q1CompletionResults" type="textarea" placeholder="请输入一季度完成结果" />
        </el-form-item>
        <el-form-item label="得分" prop="q1Score">
          <el-input v-model="form.q1Score" placeholder="请输入一季度得分" />
        </el-form-item>
        
        <el-divider content-position="center">二季度</el-divider>
        <el-form-item label="重点实施事项" prop="q2FocusImplementation">
          <el-input v-model="form.q2FocusImplementation" type="textarea" placeholder="请输入二季度重点实施事项" />
        </el-form-item>
        <el-form-item label="完成结果" prop="q2CompletionResults">
          <el-input v-model="form.q2CompletionResults" type="textarea" placeholder="请输入二季度完成结果" />
        </el-form-item>
        <el-form-item label="得分" prop="q2Score">
          <el-input v-model="form.q2Score" placeholder="请输入二季度得分" />
        </el-form-item>
        
        <el-divider content-position="center">三季度</el-divider>
        <el-form-item label="重点实施事项" prop="q3FocusImplementation">
          <el-input v-model="form.q3FocusImplementation" type="textarea" placeholder="请输入三季度重点实施事项" />
        </el-form-item>
        <el-form-item label="完成结果" prop="q3CompletionResults">
          <el-input v-model="form.q3CompletionResults" type="textarea" placeholder="请输入三季度完成结果" />
        </el-form-item>
        <el-form-item label="得分" prop="q3Score">
          <el-input v-model="form.q3Score" placeholder="请输入三季度得分" />
        </el-form-item>
        
        <el-divider content-position="center">四季度</el-divider>
        <el-form-item label="重点实施事项" prop="q4FocusImplementation">
          <el-input v-model="form.q4FocusImplementation" type="textarea" placeholder="请输入四季度重点实施事项" />
        </el-form-item>
        <el-form-item label="完成结果" prop="q4CompletionResults">
          <el-input v-model="form.q4CompletionResults" type="textarea" placeholder="请输入四季度完成结果" />
        </el-form-item>
        <el-form-item label="得分" prop="q4Score">
          <el-input v-model="form.q4Score" placeholder="请输入四季度得分" />
        </el-form-item>
        
        <el-divider content-position="center">总评</el-divider>
        <el-form-item label="总分" prop="totalScore">
          <el-input v-model="form.totalScore" placeholder="请输入总分" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 添加导入对话框 -->
    <el-dialog title="导入数据" v-model="uploadDialogVisible" width="400px" append-to-body>
      <el-upload
          ref="uploadRef"
          :limit="1"
          accept=".xlsx, .xls"
          :on-exceed="handleExceed"
          :auto-upload="false"
          :show-file-list="true"
          :on-change="handleFileChange"
          :before-remove="handleBeforeRemove"
          drag>
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">
          将文件拖到此处，或<em>点击上传</em>
        </div>
        <template #tip>
          <div class="el-upload__tip">
            仅允许导入Excel文件，且不超过5MB
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" :loading="isLoading" @click="uploadFile">确 定</el-button>
          <el-button @click="cancelUpload">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Works">
import { listWorks, getWorks, delWorks, addWorks, updateWorks, importFile, downloadTemplate } from "@/api/security/works";
import { UploadFilled } from '@element-plus/icons-vue';
import { ref, reactive, toRefs, getCurrentInstance } from 'vue';

const { proxy } = getCurrentInstance();

const worksList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const selectedFile = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    unitName: null,
    keyWorkName: null,
    currentStatusAndCauses: null,
    target: null,
    weight1: null,
    weight2: null,
    q1FocusImplementation: null,
    q1CompletionResults: null,
    q1Score: null,
    q2FocusImplementation: null,
    q2CompletionResults: null,
    q2Score: null,
    q3FocusImplementation: null,
    q3CompletionResults: null,
    q3Score: null,
    q4FocusImplementation: null,
    q4CompletionResults: null,
    q4Score: null,
    totalScore: null
  },
  rules: {
    unitName: [
      { required: true, message: "单位名称不能为空", trigger: "blur" }
    ],
    keyWorkName: [
      { required: true, message: "重点工作名称不能为空", trigger: "blur" }
    ],
    currentStatusAndCauses: [
      { required: true, message: "现状及要因不能为空", trigger: "blur" }
    ],
    target: [
      { required: true, message: "目标不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询公司重点工作列表 */
function getList() {
  loading.value = true;
  listWorks(queryParams.value).then(response => {
    worksList.value = response.rows;
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
    unitName: null,
    keyWorkName: null,
    currentStatusAndCauses: null,
    target: null,
    weight1: null,
    weight2: null,
    q1FocusImplementation: null,
    q1CompletionResults: null,
    q1Score: null,
    q2FocusImplementation: null,
    q2CompletionResults: null,
    q2Score: null,
    q3FocusImplementation: null,
    q3CompletionResults: null,
    q3Score: null,
    q4FocusImplementation: null,
    q4CompletionResults: null,
    q4Score: null,
    totalScore: null
  };
  proxy.resetForm("worksRef");
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
  title.value = "添加公司重点工作";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getWorks(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改公司重点工作";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["worksRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateWorks(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addWorks(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除公司重点工作编号为"' + _ids + '"的数据项？').then(function() {
    return delWorks(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/works/export', {
    ...queryParams.value
  }, `works_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  uploadDialogVisible.value = true;
  selectedFile.value = null;
}

/** 上传文件超出数量限制 */
function handleExceed() {
  proxy.$modal.msgError("最多只能上传一个文件！");
}

/** 文件选择变更 */
function handleFileChange(file) {
  console.log('文件选择变更:', file);
  if (file) {
    const fileName = file.name;
    const fileExt = fileName.split(".").pop().toLowerCase();
    if (fileExt !== "xlsx" && fileExt !== "xlsm" && fileExt !== "xls") {
      proxy.$modal.msgError("只能上传 Excel 文件！");
      if (proxy.$refs.uploadRef) {
        proxy.$refs.uploadRef.clearFiles();
      }
      selectedFile.value = null;
      return false;
    }
    selectedFile.value = file;
    return true;
  }
  return false;
}

/** 文件移除前的处理 */
function handleBeforeRemove() {
  selectedFile.value = null;
  return true;
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  selectedFile.value = null;
  if (proxy.$refs.uploadRef) {
    proxy.$refs.uploadRef.clearFiles();
  }
}

/** excel文件上传 */
function uploadFile() {
  try {
    console.log('开始上传文件，selectedFile:', selectedFile.value);
    
    if (!selectedFile.value) {
      const uploadRef = proxy.$refs.uploadRef;
      console.log('uploadRef:', uploadRef);
      
      if (!uploadRef || !uploadRef.uploadFiles || uploadRef.uploadFiles.length === 0) {
        proxy.$modal.msgError("请选择文件");
        return;
      }
      
      selectedFile.value = uploadRef.uploadFiles[0];
    }
    
    if (!selectedFile.value || !selectedFile.value.raw) {
      proxy.$modal.msgError("文件无效");
      return;
    }
    
    isLoading.value = true;
    const file = selectedFile.value.raw;
    const formData = new FormData();
    formData.append('excelFile', file);
    
    // 添加日志，帮助调试
    console.log('准备上传文件:', file.name, file.size);
    
    importFile(formData)
      .then(response => {
        console.log('上传成功:', response);
        proxy.$modal.msgSuccess(response.msg || "导入成功");
        getList();
        uploadDialogVisible.value = false;
        selectedFile.value = null;
      })
      .catch(error => {
        console.error('上传失败:', error);
        proxy.$modal.msgError(error.message || "导入失败");
      })
      .finally(() => {
        isLoading.value = false;
        if (proxy.$refs.uploadRef) {
          proxy.$refs.uploadRef.clearFiles();
        }
      });
  } catch (error) {
    console.error('上传文件出错:', error);
    proxy.$modal.msgError("上传过程中发生错误");
    isLoading.value = false;
  }
}

/** 下载导入模板 */
function handleDownloadTemplate() {
  proxy.download('security/works/importTemplate', {}, `公司重点工作导入模板_${new Date().getTime()}.xlsx`, 'post');
}

getList();
</script>

<style scoped>
.el-divider {
  margin: 16px 0;
}
</style>
