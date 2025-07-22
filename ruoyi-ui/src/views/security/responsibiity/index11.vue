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
      <el-form-item label="姓名" prop="xingMing">
        <el-input
          v-model="queryParams.xingMing"
          placeholder="请输入姓名"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所在班组" prop="banZu">
        <el-input
          v-model="queryParams.banZu"
          placeholder="请输入所在班组"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工种" prop="gongZhong">
        <el-input
          v-model="queryParams.gongZhong"
          placeholder="请输入工种"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进厂时间" prop="jingCTime">
        <el-date-picker
          v-model="queryParams.jingCTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择进厂时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="一级教育时间" prop="educationOne">
        <el-date-picker
          v-model="queryParams.educationOne"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择一级教育时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="二级教育时间" prop="educationTwo">
        <el-date-picker
          v-model="queryParams.educationTwo"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择二级教育时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="三级教育时间" prop="educationThree">
        <el-date-picker
          v-model="queryParams.educationThree"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择三级教育时间"
          style="width: 200px"
          clearable
        />
      </el-form-item>
      <el-form-item label="是否合规" prop="heGui">
        <el-input
          v-model="queryParams.heGui"
          placeholder="请输入是否合规"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="来源" prop="source">
        <el-input
          v-model="queryParams.source"
          placeholder="请输入来源"
          clearable
          style="width: 200px"
          @keyup.enter="handleQuery"
        />
      </el-form-item>
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
          v-hasPermi="['security:ThreeEducatiom:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:ThreeEducatiom:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:ThreeEducatiom:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:ThreeEducatiom:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:ThreeEducatiom:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="ThreeEducatiomList"
      @selection-change="handleSelectionChange"
      class="three-education-table"
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
      <el-table-column label="姓名" align="center" prop="xingMing" min-width="100" show-overflow-tooltip />
      <el-table-column label="所在班组" align="center" prop="banZu" min-width="120" show-overflow-tooltip />
      <el-table-column label="工种" align="center" prop="gongZhong" min-width="120" show-overflow-tooltip />
      <el-table-column label="进厂时间" align="center" prop="jingCTime" width="120" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.jingCTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="一级教育时间" align="center" prop="educationOne" width="130" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.educationOne, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二级教育时间" align="center" prop="educationTwo" width="130" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.educationTwo, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="三级教育时间" align="center" prop="educationThree" width="130" show-overflow-tooltip>
        <template #default="scope">
          <span>{{ parseTime(scope.row.educationThree, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否合规" align="center" prop="heGui" min-width="100" show-overflow-tooltip />
      <el-table-column label="来源" align="center" prop="source" min-width="100" show-overflow-tooltip />
      <el-table-column label="备注" align="center" prop="remark" min-width="150" show-overflow-tooltip />
      <el-table-column label="操作" align="center" width="180" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['security:ThreeEducatiom:edit']"
            size="small"
          >修改</el-button>
          <el-button
            link
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['security:ThreeEducatiom:remove']"
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

    <!-- 添加或修改新职工三级安全教育台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="ThreeEducatiomRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="xingMing">
          <el-input v-model="form.xingMing" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="班组" prop="banZu">
          <el-input v-model="form.banZu" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="工作" prop="gongZhong">
          <el-input v-model="form.gongZhong" placeholder="请输入工作" />
        </el-form-item>
        <el-form-item label="进厂时间" prop="jingCTime">
          <el-date-picker clearable
            v-model="form.jingCTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择进厂时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="一级教育时间" prop="educationOne">
          <el-date-picker clearable
            v-model="form.educationOne"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择一级教育时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="二级教育时间" prop="educationTwo">
          <el-date-picker clearable
            v-model="form.educationTwo"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择二级教育时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="三期教育时间" prop="educationThree">
          <el-date-picker clearable
            v-model="form.educationThree"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择三期教育时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否合规" prop="heGui">
          <el-input v-model="form.heGui" placeholder="请输入是否合规" />
        </el-form-item>
        <el-form-item label="来源" prop="source">
          <el-input v-model="form.source" placeholder="请输入来源" />
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

    <!-- 新职工三级安全教育台账导入对话框 -->
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
              <el-checkbox v-model="upload.updateSupport" />是否更新已存在数据
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

<script setup name="ThreeEducatiom">
import { listThreeEducatiom, listThreeEducatiomByRelatedId, getThreeEducatiom, delThreeEducatiom, addThreeEducatiom, updateThreeEducatiom } from "@/api/security/ThreeEducatiom";
import { ref, reactive, toRefs, getCurrentInstance, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getToken } from "@/utils/auth";
const route = useRoute();
const { proxy } = getCurrentInstance();

const ThreeEducatiomList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

// 导入相关
const upload = reactive({
  open: false,
  title: "",
  isUploading: false,
  updateSupport: 0,
  headers: { Authorization: "Bearer " + getToken() },
  url: import.meta.env.VITE_APP_BASE_API + "/security/ThreeEducatiom/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    xingMing: null,
    banZu: null,
    gongZhong: null,
    jingCTime: null,
    educationOne: null,
    educationTwo: null,
    educationThree: null,
    heGui: null,
    source: null,
    relatedId: null,
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询新职工三级安全教育台账列表 */
function getList() {
  loading.value = true;

  // 如果有关联ID，使用关联ID查询
  if (queryParams.value.relatedId) {
    listThreeEducatiomByRelatedId(queryParams.value.relatedId, route.query.sourceModule).then(response => {
      ThreeEducatiomList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    });
  } else {
    // 否则使用普通查询
    listThreeEducatiom(queryParams.value).then(response => {
      ThreeEducatiomList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    });
  }
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
    xingMing: null,
    banZu: null,
    gongZhong: null,
    jingCTime: null,
    educationOne: null,
    educationTwo: null,
    educationThree: null,
    heGui: null,
    source: null,
    remark: null,
    relatedId: null
  };
  proxy.resetForm("ThreeEducatiomRef");
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
  title.value = "添加新职工三级安全教育台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getThreeEducatiom(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改新职工三级安全教育台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["ThreeEducatiomRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateThreeEducatiom(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addThreeEducatiom(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除新职工三级安全教育台账编号为"' + _ids + '"的数据项？').then(function() {
    return delThreeEducatiom(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/ThreeEducatiom/export', {
    ...queryParams.value
  }, `ThreeEducatiom_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "新职工三级安全教育台账导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/ThreeEducatiom/importTemplate', {}, `新职工三级安全教育台账导入模板_${new Date().getTime()}.xlsx`);
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

function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
  }
}
onMounted(() => {
  // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
});

getList();
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
.search-form .el-date-editor .el-input__inner {
  border-radius: 6px;
  border: 1px solid #dcdfe6;
  transition: all 0.3s;
}

.search-form .el-input__inner:focus,
.search-form .el-date-editor .el-input__inner:focus {
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

.three-education-table {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
</style>
