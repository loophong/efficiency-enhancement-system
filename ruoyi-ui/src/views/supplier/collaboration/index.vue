<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商代码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上传时间" prop="time" style="width: 300px;">
        <el-date-picker clearable
          v-model="queryParams.time"
          type="month"
          value-format="YYYY-MM-DD"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" prop="letter">
        <el-select v-model="queryParams.letter" placeholder="请选择供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_one"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商如受到相关方处罚" prop="punish">
        <el-select v-model="queryParams.punish" placeholder="请选择供应商如受到相关方处罚" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_two"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商如对采购员需求反馈不及时" prop="feedbackNotTimely">
        <el-select v-model="queryParams.feedbackNotTimely" placeholder="请选择供应商如对采购员需求反馈不及时" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_three"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item> -->
      <!-- <el-form-item label="得分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入得分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="具体内容" prop="specificContent">
        <el-input
          v-model="queryParams.specificContent"
          placeholder="请输入具体内容"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="填报人" prop="uploadName">
        <el-input
          v-model="queryParams.uploadName"
          placeholder="请输入填报人"
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
          v-hasPermi="['supplier:collaboration:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:collaboration:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:collaboration:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:collaboration:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
              <el-button @click="handleImport" type="success" plain icon="Upload"
                         v-hasPermi="['production:collaboration:import']">导入
              </el-button>
            </el-col>


      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-download" 
        @click="handleDownload" size="mini" plain v-if="true">下载模版文件
        </el-button>
      </el-col>     

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="collaborationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" align="center" prop="letter"/>
        
      <el-table-column label="供应商如受到相关方处罚" align="center" prop="punish"/>
        <!-- <template #default="scope">
          {{ scope.row.letter }}
        </template>
      </el-table-column> -->
      <el-table-column label="供应商如对采购员需求反馈不及时" align="center" prop="feedbackNotTimely"/>
        <!-- <template #default="scope">
          {{ scope.row.letter }}
        </template>
      </el-table-column> -->
      <el-table-column label="得分" align="center" prop="score" />
      <el-table-column label="上传时间" align="center" prop="time" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="具体内容" align="center" prop="specificContent" /> -->
      <!-- <el-table-column label="填报人" align="center" prop="uploadName" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:collaboration:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:collaboration:remove']">删除</el-button>
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

    <!-- 添加或修改服务与协作对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="collaborationRef" :model="form" :rules="rules" label-width="300px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" prop="letter">
          <el-input v-model="form.letter" placeholder="请选择供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" />
            <!-- <el-option
              v-for="dict in supplier_performance_services_collaboration_one"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.label)"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <el-form-item label="供应商如受到相关方处罚" prop="punish">
          <el-input v-model="form.punish" placeholder="请选择供应商如受到相关方处罚" />
            <!-- <el-option
              v-for="dict in supplier_performance_services_collaboration_two"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.label)"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <el-form-item label="供应商如对采购员需求反馈不及时" prop="feedbackNotTimely" >
          <el-input v-model="form.feedbackNotTimely" placeholder="请选择供应商如对采购员需求反馈不及时" />
            <!-- <el-option
              v-for="dict in supplier_performance_services_collaboration_three"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.label)"
            ></el-option>
          </el-select> -->
        </el-form-item>
        <!-- <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item> -->
        <el-form-item label="上传时间" prop="time">
          <el-date-picker clearable
            v-model="form.time"
            type="month"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传时间"> 
              </el-date-picker>
            <!-- <el-date-picker
          v-model="form.updateMonth"
          type="month"
        
          placeholder="Pick a month"
        /> -->
        </el-form-item>
        <!-- <el-form-item label="具体内容" prop="specificContent">
          <el-input v-model="form.specificContent" placeholder="请输入具体内容" />
        </el-form-item> -->
        <!-- <el-form-item label="填报人" prop="uploadName">
          <el-input v-model="form.uploadName" placeholder="请输入填报人" />
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  <!-- 文件上传弹窗 -->
  <el-dialog title="导入服务于协作表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

<el-form :model="form" ref="form" label-width="90px">
  <el-form-item label="上传表类：">
    <span style="color: rgb(68, 140, 39);">服务于协作表单</span>
    <br>
  </el-form-item>

  <el-form-item label="时间">
    <el-date-picker
        v-model="uploadDate"
        type="month"
        placeholder="Pick a day"
        date-format="yyyy-MM-dd"
        :size="size"
      />
    <br>
  </el-form-item>

    <el-form-item label="上传文件：">
    <input type="file" ref="inputFile" @change="checkFile"/>
    <br>
  </el-form-item>
      </el-form>
            <span slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
              <el-button @click="cancelUpload">取 消</el-button>
              <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
              <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
            </span>
      </el-dialog>





  </div>
</template>

<script setup name="Collaboration">
import { listCollaboration, getCollaboration, delCollaboration, addCollaboration, updateCollaboration,importFile } from "@/api/supplier/collaboration";
import dayjs from 'dayjs';
import {handleTrueDownload} from "@/api/tool/gen"

const { proxy } = getCurrentInstance();
const { supplier_performance_services_collaboration_two, supplier_performance_services_collaboration_three, supplier_performance_services_collaboration_one } = proxy.useDict('supplier_performance_services_collaboration_two', 'supplier_performance_services_collaboration_three', 'supplier_performance_services_collaboration_one');

const collaborationList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const uploadDate = ref("");
// 导入参数
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    time: null,
    letter: null,
    punish: null,
    feedbackNotTimely: null,
    score: null,
    specificContent: null,
    uploadName: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

function handleDownload() {
  const url = "/profile/excel_templates/supply/服务与协作.xlsx";
  handleTrueDownload(url);
}

/** 查询服务与协作列表 */
function getList() {
  loading.value = true;
  listCollaboration(queryParams.value).then(response => {
    collaborationList.value = response.rows;
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
    time: null,
    letter: null,
    punish: null,
    feedbackNotTimely: null,
    score: null,
    specificContent: null,
    uploadName: null
  };
  proxy.resetForm("collaborationRef");
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
  title.value = "添加服务与协作";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCollaboration(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改服务与协作";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["collaborationRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCollaboration(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCollaboration(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除？').then(function() {
    return delCollaboration(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/collaboration/export', {
    ...queryParams.value
  }, `collaboration_${new Date().getTime()}.xlsx`)
}

getList();



/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
}

/** 表单重置 */
function resetUpload() {
  if (inputFile.value) {
    inputFile.value.value = "";
  }
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  resetUpload();
}

/** excel文件上传 */
function uploadFile() {
  if (inputFile.value && inputFile.value.files.length > 0) {
    isLoading.value = true;
    const file = inputFile.value.files[0];
    console.log(inputFile.value);
    console.log(file);
    console.log("上传时间"+uploadDate.value);
    let date =dayjs(uploadDate.value).format('YYYY-MM-DD'); // 使用 dayjs 格式化日期
    let uploadFileDTO = {
      'uploadMonth': date,
      'excelFile': file
    }

    importFile(uploadFileDTO).then(() => {
      proxy.$modal.msgSuccess("导入成功");
      getList();
      uploadDialogVisible.value = false;
      isLoading.value = false;
    }).catch(() => {
      proxy.$modal.msgError("导入失败");
      isLoading.value = false;
    }).finally(() => {
      resetUpload();
    });
  }else {
    proxy.$modal.msgError("请选择文件");
  }
}

/** 检查文件是否为excel */
function checkFile() {
  const file = inputFile.value.files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    proxy.$modal.msgError("只能上传 Excel 文件！");
    resetUpload();
  }
}

</script>
