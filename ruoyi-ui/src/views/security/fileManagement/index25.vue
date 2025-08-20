<template>
  <div class="app-container">
    <!-- 注释掉统计信息卡片
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div slot="header" class="clearfix">
            <span>文件总数</span>
          </div>
          <div class="card-panel-num">{{ statistics.totalFiles || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div slot="header" class="clearfix">
            <span>上传文件</span>
          </div>
          <div class="card-panel-num">{{ statistics.uploadFiles || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div slot="header" class="clearfix">
            <span>导入文件</span>
          </div>
          <div class="card-panel-num">{{ statistics.importFiles || 0 }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div slot="header" class="clearfix">
            <span>模块数量</span>
          </div>
          <div class="card-panel-num">{{ statistics.moduleCount || 0 }}</div>
        </el-card>
      </el-col>
    </el-row>
    -->

    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- 注释掉文件分类查询
      <el-form-item label="文件分类" prop="fileCategory">
        <el-select v-model="queryParams.fileCategory" placeholder="请选择文件分类" clearable>
          <el-option
            v-for="dict in file_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      -->
      <!-- <el-form-item label="模块名称" prop="moduleName">
        <el-input
          v-model="queryParams.moduleName"
          placeholder="请输入所属模块名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="上传时间" prop="uploadTime">
        <el-date-picker clearable
          v-model="queryParams.uploadTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建者" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建者"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年份" prop="year">
        <el-input
          v-model="queryParams.year"
          placeholder="请输入年份"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="其他信息" prop="text">
        <el-input
          v-model="queryParams.text"
          placeholder="请输入其他信息"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="更新者" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新者"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['filemanagement:filemanagement:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['filemanagement:filemanagement:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['filemanagement:filemanagement:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['filemanagement:filemanagement:export']"
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
          @click="handleDownloadTemplate"
        >下载模板</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Refresh"
          @click="handleRefresh"
        >刷新</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button link 
        type="primary" 
        icon="View"
        @click="handleView(scope.row)"
         >查看</el-button>
      </el-col> -->
      <!-- 注释掉监控按钮
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Monitor"
          @click="handleMonitor"
        >监控</el-button>
      </el-col>
      -->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="filemanagementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="50" />
      <el-table-column label="年份" align="center" prop="year" />
      <el-table-column label="文件名称" align="center" prop="fileName" />


      <!-- <el-table-column label="文件路径" align="center" prop="filePath" /> -->
      <!-- <el-table-column label="文件大小(字节)" align="center" prop="fileSize" />
      <el-table-column label="文件类型(MIME类型)" align="center" prop="fileType" /> -->
      <!-- 注释掉文件分类列
      <el-table-column label="文件分类" align="center" prop="fileCategory">
        <template #default="scope">
          <dict-tag :options="file_category" :value="scope.row.fileCategory"/>
        </template>
      </el-table-column>
      -->
      <el-table-column label="所属模块名称" align="center" prop="moduleName" />
      <el-table-column label="上传时间" align="center" prop="uploadTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传用户" align="center" prop="uploadUser" />
      <el-table-column label="其他信息" align="center" prop="text" />
      <!-- <el-table-column label="状态(0正常 1禁用)" align="center" prop="status" /> -->
      <!-- <el-table-column label="备注" align="center" prop="remark" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleView(scope.row)">查看</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['filemanagement:filemanagement:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['filemanagement:filemanagement:remove']">删除</el-button>
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

    <!-- 添加或修改文件管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="filemanagementRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="文件名称" prop="fileName">
          <el-input v-model="form.fileName" placeholder="请输入文件名称" />
        </el-form-item>
        <!-- <el-form-item label="文件路径" prop="filePath">
          <file-upload v-model="form.filePath" />
        </el-form-item> -->
        <!-- <el-form-item label="文件大小(字节)" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="请输入文件大小(字节)" />
        </el-form-item> -->
        <!-- <el-form-item label="文件分类" prop="fileCategory">
          <el-select v-model="form.fileCategory" placeholder="请选择文件分类">
            <el-option
              v-for="dict in file_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
       
        <!-- <el-form-item label="所属模块名称" prop="moduleName">
          <el-input v-model="form.moduleName" placeholder="请输入所属模块名称" />
        </el-form-item> -->
        <!-- <el-form-item label="所属模块代码" prop="moduleCode">
          <el-input v-model="form.moduleCode" placeholder="请输入所属模块代码" /> -->
        <!-- </el-form-item> -->
        <!-- <el-form-item label="关联ID(用于导入文件跳转)" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联ID(用于导入文件跳转)" />
        </el-form-item>
        <el-form-item label="关联URL(用于跳转)" prop="relatedUrl">
          <el-input v-model="form.relatedUrl" placeholder="请输入关联URL(用于跳转)" />
        </el-form-item> -->
        <el-form-item label="上传时间" prop="uploadTime">
          <el-date-picker clearable
            v-model="form.uploadTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上传用户" prop="uploadUser">
          <el-input v-model="form.uploadUser" placeholder="请输入上传用户" />
        </el-form-item>
        <el-form-item label="年份" prop="year">
          <el-input v-model="form.year" placeholder="请输入年份" />
        </el-form-item>
        <el-form-item label="其他信息" prop="text">
          <el-input v-model="form.text" type="textarea" placeholder="请输入其他信息" />
        </el-form-item>
        <!-- <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item> -->
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
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><UploadFilled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip">
            <span>仅允许导入xls、xlsx格式文件。请先下载模板按照格式填写数据。</span>
            <br>
            <span style="color: #909399; font-size: 12px;">注意：导入时会直接添加所有数据，包括重复数据。</span>
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

    <!-- 注释掉文件监控对话框
    <el-dialog title="文件监控" v-model="monitorOpen" width="800px" append-to-body>
      <el-tabs v-model="activeTab">
        <el-tab-pane label="最近上传" name="recent">
          <el-table :data="recentFiles" stripe style="width: 100%">
            <el-table-column prop="fileName" label="文件名称" width="180"></el-table-column>
            <el-table-column prop="fileCategory" label="文件分类">
              <template #default="scope">
                <el-tag :type="scope.row.fileCategory === 'UPLOAD' ? 'primary' : 'success'">
                  {{ scope.row.fileCategory === 'UPLOAD' ? '上传' : '导入' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="moduleName" label="所属模块"></el-table-column>
            <el-table-column prop="uploadTime" label="上传时间" width="180">
              <template #default="scope">
                <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template #default="scope">
                <el-button link type="primary" icon="View" @click="handleViewMonitor(scope.row)">查看</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="模块统计" name="stats">
          <div id="moduleChart" style="width: 100%; height: 400px;"></div>
        </el-tab-pane>
      </el-tabs>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleRefreshMonitor">刷新</el-button>
          <el-button @click="monitorOpen = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
    -->
  </div>
</template>

<script setup name="Filemanagement">
import { listFilemanagement, getFilemanagement, delFilemanagement, addFilemanagement, updateFilemanagement, getFileStatistics, getFileMonitorData } from "@/api/security/filemanagement";
import FileUpload from "@/components/FileUpload/index.vue";
import { getToken } from "@/utils/auth";
import request from "@/utils/request";
import { UploadFilled } from '@element-plus/icons-vue';
import { onMounted, ref, reactive, toRefs, onUnmounted } from 'vue';
// 注释掉echarts导入
// import * as echarts from 'echarts';

const { proxy } = getCurrentInstance();
// 注释掉文件分类字典
// const { file_category } = proxy.useDict('file_category');

const filemanagementList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入参数
const upload = reactive({
  // 是否显示弹出层（导入）
  open: false,
  // 弹出层标题（导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址 - 使用相关方块的接口地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/relatedpartyledger/importData?sourceUrl=" + encodeURIComponent("securityConm/security1/run/environment-health-security-control/relatedpartyledger")
});

// 注释掉文件监控相关变量
/*
const monitorOpen = ref(false);
const activeTab = ref('recent');
const recentFiles = ref([]);
const moduleStats = ref({});
const statistics = ref({
  totalFiles: 0,
  uploadFiles: 0,
  importFiles: 0,
  moduleCount: 0
});
let moduleChart = null;
*/

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: null,
    filePath: null,
    // 注释掉fileCategory查询参数
    // fileCategory: null,
    moduleName: null,
    uploadTime: null,
    createBy: null,
    updateBy: null,
    year: null,
    text: null,
  },
  rules: {
    fileName: [
      { required: true, message: "文件名称不能为空", trigger: "blur" }
    ],
    filePath: [
      { required: true, message: "文件路径不能为空", trigger: "blur" }
    ],
    // 注释掉fileCategory验证规则
    /*
    fileCategory: [
      { required: true, message: "文件分类(UPLOAD:上传文件/IMPORT:导入文件)不能为空", trigger: "change" }
    ],
    */
    moduleName: [
      { required: true, message: "所属模块名称不能为空", trigger: "blur" }
    ],
    // moduleCode: [
    //   { required: true, message: "所属模块代码不能为空", trigger: "blur" }
    // ],
    uploadTime: [
      { required: true, message: "上传时间不能为空", trigger: "blur" }
    ],
    year: [
      { required: true, message: "年份不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件管理列表 */
function getList(name) {
  loading.value = true;
  // queryParams.value.moduleName = name;
  // console.log(name);
  // console.log(queryParams.value.moduleName);

  listFilemanagement(queryParams.value).then(response => {
    filemanagementList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
  
  // 注释掉获取统计数据
  /*
  getFileStatistics().then(res => {
    if (res.code === 200 && res.data) {
      statistics.value = res.data;
    }
  }).catch(err => {
    console.error("获取统计数据失败", err);
  });
  */
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
    fileName: null,
    filePath: null,
    fileSize: null,
    fileType: null,
    // 注释掉fileCategory
    // fileCategory: null,
    moduleName: null,
    moduleCode: null,
    relatedId: null,
    relatedUrl: null,
    uploadTime: null,
    uploadUser: null,
    status: null,
    year: null,
    text: null,
    remark: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  };
  proxy.resetForm("filemanagementRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  queryParams.value.moduleName = "有害点台帐";
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
  title.value = "添加文件管理";
  // 默认填充当前日期
  form.value.uploadTime = new Date().toISOString().split('T')[0];
  // 默认填充当前用户
  form.value.uploadUser = proxy.$store.getters.name;
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getFilemanagement(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改文件管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["filemanagementRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateFilemanagement(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addFilemanagement(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

function handleDelete(row) {
  const _ids = row.id || ids.value;
  // 检查是否是最后一条数据
  if (filemanagementList.value.length === 1 && (Array.isArray(_ids) ? _ids.length === 1 : true)) {
    proxy.$modal.msgWarning("不能删除最后一条数据");
    return;
  }
  proxy.$modal.confirm('是否确认删除文件管理编号为"' + _ids + '"的数据项？').then(function() {
    return delFilemanagement(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('filemanagement/filemanagement/export', {
    ...queryParams.value
  }, `filemanagement_${new Date().getTime()}.xlsx`)
}

/** 刷新按钮操作 */
function handleRefresh() {
  proxy.$modal.msgSuccess("刷新成功");
  getList();
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "相关方管理台账导入";
  upload.open = true;
}

/** 下载模板操作 */
function handleDownloadTemplate() {
  proxy.download('security/hazardpointledger/importTemplate', {}, `相关方管理台账模板_${new Date().getTime()}.xlsx`, 'post');
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
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

/** 查看文件 */
function handleView(row) {
  // 判断文件路径是否有效
  if (row.filePath && row.filePath.trim() !== '') {
    if (row.relatedUrl && row.relatedUrl.trim() !== '') {
      // 如果有关联URL，跳转到关联页面
      // 为所有页面添加关联ID参数
      if (row.id) {
        // 处理relatedUrl，确保只有一个securityConm前缀并保留原始参数
        let processedUrl = row.relatedUrl;
        
        // 先完全移除所有securityConm前缀
        while (processedUrl.indexOf('securityConm/') === 0) {
          processedUrl = processedUrl.substring('securityConm/'.length);
        }
        
        // 确保URL以/开头
        if (!processedUrl.startsWith('/')) {
          processedUrl = '/' + processedUrl;
        }
        
        // 添加一个securityConm前缀
        processedUrl = '/securityConm' + processedUrl;
        
        // 构建新的参数对象
        const params = new Map();
        
        // 如果URL中已有参数，先解析它们
        if (processedUrl.includes('?')) {
          const queryString = processedUrl.split('?')[1];
          if (queryString) {
            queryString.split('&').forEach(param => {
              if (param.includes('=')) {
                const [key, value] = param.split('=');
                // 不添加可能会重复的参数
                if (key !== 'relatedId' && key !== 'sourceModule') {
                  params.set(key, value);
                }
              }
            });
          }
          // 移除原URL中的查询参数
          processedUrl = processedUrl.split('?')[0];
        }
        
        // 添加必要的参数
        params.set('relatedId', row.id);
        if (row.moduleName) {
          params.set('sourceModule', encodeURIComponent(row.moduleName));
        }
        
        // 构建最终URL
        let finalUrl = processedUrl;
        if (params.size > 0) {
          const queryString = Array.from(params.entries())
            .map(([key, value]) => `${key}=${value}`)
            .join('&');
          finalUrl = `${processedUrl}?${queryString}`;
        }
          
        console.log("跳转到关联页面，带上唯一参数:", finalUrl);
        proxy.$router.push(finalUrl);
      } else {
        // 如果没有ID，也需要处理重复参数
        let url = row.relatedUrl;
        if (url && url.includes('?')) {
          const baseUrl = url.split('?')[0];
          const queryString = url.split('?')[1];
          
          // 处理重复参数
          const params = new Map();
          if (queryString) {
            queryString.split('&').forEach(param => {
              if (param.includes('=')) {
                const [key, value] = param.split('=');
                params.set(key, value);
              }
            });
          }
          
          // 重建URL
          const newQueryString = Array.from(params.entries())
            .map(([key, value]) => `${key}=${value}`)
            .join('&');
          
          url = newQueryString ? `${baseUrl}?${newQueryString}` : baseUrl;
        }
        
        console.log("直接跳转到关联页面(处理后):", url);
        proxy.$router.push(url);
      }
    } else {
      // 否则尝试打开文件
      window.open(row.filePath, '_blank');
    }
  } else {
    proxy.$modal.msgError("文件路径无效，无法查看");
  }
}

// 注释掉监控相关函数
/*
function handleMonitor() {
  monitorOpen.value = true;
  getMonitorData();
}

function getMonitorData() {
  getFileMonitorData(10).then(response => {
    if (response.code === 200) {
      recentFiles.value = response.data.recentFiles || [];
      moduleStats.value = response.data.moduleStats || {};
      
      // 在下一个DOM更新周期渲染图表
      proxy.$nextTick(() => {
        renderModuleChart();
      });
    }
  }).catch(error => {
    proxy.$modal.msgError("获取监控数据失败: " + error);
  });
}

function handleRefreshMonitor() {
  getMonitorData();
  proxy.$modal.msgSuccess("监控数据已刷新");
}

function handleViewMonitor(row) {
  if (row.filePath && row.filePath.trim() !== '') {
    if (row.relatedUrl && row.relatedUrl.trim() !== '') {
      // 如果有关联URL，处理重复参数后跳转
      let url = row.relatedUrl;
      if (url && url.includes('?')) {
        const baseUrl = url.split('?')[0];
        const queryString = url.split('?')[1];
        
        // 处理重复参数
        const params = new Map();
        if (queryString) {
          queryString.split('&').forEach(param => {
            if (param.includes('=')) {
              const [key, value] = param.split('=');
              params.set(key, value);
            }
          });
        }
        
        // 重建URL
        const newQueryString = Array.from(params.entries())
          .map(([key, value]) => `${key}=${value}`)
          .join('&');
        
        url = newQueryString ? `${baseUrl}?${newQueryString}` : baseUrl;
      }
      
      console.log("从监控页面跳转(处理后):", url);
      proxy.$router.push(url);
      monitorOpen.value = false;
    } else {
      // 否则尝试打开文件
      window.open(row.filePath, '_blank');
    }
  } else {
    proxy.$modal.msgError("文件路径无效，无法查看");
  }
}

function renderModuleChart() {
  const chartDom = document.getElementById('moduleChart');
  if (!chartDom) return;
  
  if (moduleChart) {
    moduleChart.dispose();
  }
  
  moduleChart = echarts.init(chartDom);
  
  const data = [];
  for (const [key, value] of Object.entries(moduleStats.value)) {
    data.push({
      name: key,
      value: value
    });
  }
  
  const option = {
    title: {
      text: '模块文件分布',
      left: 'center'
    },
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: Object.keys(moduleStats.value)
    },
    series: [
      {
        name: '文件数量',
        type: 'pie',
        radius: '50%',
        data: data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };
  
  moduleChart.setOption(option);
}
*/

// 页面加载时获取列表数据
onMounted(() => {
  // 读出跳转进入的路由参数
  const query = proxy.$route;
  console.log("query", query.name);
  queryParams.value.moduleName = query.name;
  getList(query.name);
});

// 注释掉页面销毁时释放图表资源
/*
onUnmounted(() => {
  if (moduleChart) {
    moduleChart.dispose();
    moduleChart = null;
  }
});
*/
</script>

<style scoped>
/* 注释掉统计卡片样式
.mb20 {
  margin-bottom: 20px;
}
.stat-card {
  height: 108px;
}
.card-panel-num {
  font-size: 30px;
  font-weight: bold;
  text-align: center;
  color: #409EFF;
}
*/
</style>
