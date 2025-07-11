<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="文件名称" prop="fileName">
        <el-input
          v-model="queryParams.fileName"
          placeholder="请输入文件名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="模块名称" prop="moduleName">
        <el-input
          v-model="queryParams.moduleName"
          placeholder="请输入所属模块名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
      <el-form-item label="更新者" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新者"
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
          v-hasPermi="['filemanagement:filemanagement:add']"
        >新增</el-button>
      </el-col>
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
          icon="Refresh"
          @click="handleRefresh"
        >刷新</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="filemanagementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键ID" align="center" prop="id" /> -->
      <el-table-column label="文件名称" align="center" prop="fileName" />
      <el-table-column label="文件路径" align="center" prop="filePath" />
      <!-- <el-table-column label="文件大小(字节)" align="center" prop="fileSize" />
      <el-table-column label="文件类型(MIME类型)" align="center" prop="fileType" /> -->
      <el-table-column label="文件分类" align="center" prop="fileCategory">
        <template #default="scope">
          <dict-tag :options="file_category" :value="scope.row.fileCategory"/>
        </template>
      </el-table-column>
      <el-table-column label="所属模块名称" align="center" prop="moduleName" />
      <el-table-column label="上传时间" align="center" prop="uploadTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传用户" align="center" prop="uploadUser" />
      <!-- <el-table-column label="状态(0正常 1禁用)" align="center" prop="status" /> -->
      <el-table-column label="备注" align="center" prop="remark" />
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
        <el-form-item label="文件路径" prop="filePath">
          <file-upload v-model="form.filePath" />
        </el-form-item>
        <!-- <el-form-item label="文件大小(字节)" prop="fileSize">
          <el-input v-model="form.fileSize" placeholder="请输入文件大小(字节)" />
        </el-form-item> -->
        <el-form-item label="文件分类" prop="fileCategory">
          <el-select v-model="form.fileCategory" placeholder="请选择文件分类">
            <el-option
              v-for="dict in file_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属模块名称" prop="moduleName">
          <el-input v-model="form.moduleName" placeholder="请输入所属模块名称" />
        </el-form-item>
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
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
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

<script setup name="Filemanagement">
import { listFilemanagement, getFilemanagement, delFilemanagement, addFilemanagement, updateFilemanagement } from "@/api/security/filemanagement";
import FileUpload from "@/components/FileUpload/index.vue";
import { onMounted } from 'vue';

const { proxy } = getCurrentInstance();
const { file_category } = proxy.useDict('file_category');

const filemanagementList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    fileName: null,
    filePath: null,
    fileCategory: null,
    moduleName: null,
    uploadTime: null,
    createBy: null,
    updateBy: null,
  },
  rules: {
    fileName: [
      { required: true, message: "文件名称不能为空", trigger: "blur" }
    ],
    filePath: [
      { required: true, message: "文件路径不能为空", trigger: "blur" }
    ],
    fileCategory: [
      { required: true, message: "文件分类(UPLOAD:上传文件/IMPORT:导入文件)不能为空", trigger: "change" }
    ],
    moduleName: [
      { required: true, message: "所属模块名称不能为空", trigger: "blur" }
    ],
    moduleCode: [
      { required: true, message: "所属模块代码不能为空", trigger: "blur" }
    ],
    uploadTime: [
      { required: true, message: "上传时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询文件管理列表 */
function getList() {
  loading.value = true;
  listFilemanagement(queryParams.value).then(response => {
    filemanagementList.value = response.rows;
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
    fileName: null,
    filePath: null,
    fileSize: null,
    fileType: null,
    fileCategory: null,
    moduleName: null,
    moduleCode: null,
    relatedId: null,
    relatedUrl: null,
    uploadTime: null,
    uploadUser: null,
    status: null,
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

/** 删除按钮操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
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
  // 重新获取文件列表
  getList();
  // 显示成功消息
  proxy.$modal.msgSuccess("刷新成功，已获取最新文件记录");
}

/** 查看文件 */
function handleView(row) {
  // 判断文件路径是否有效
  if (row.filePath && row.filePath.trim() !== '') {
    if (row.relatedUrl && row.relatedUrl.trim() !== '') {
      // 如果有关联URL，跳转到关联页面
      proxy.$router.push(row.relatedUrl);
    } else {
      // 否则尝试打开文件
      window.open(row.filePath, '_blank');
    }
  } else {
    proxy.$modal.msgError("文件路径无效，无法查看");
  }
}

// 页面加载时获取列表数据
onMounted(() => {
  getList();
});

</script>
