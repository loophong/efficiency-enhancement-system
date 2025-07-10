<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="制度名称" prop="systemName">
        <el-input
          v-model="queryParams.systemName"
          placeholder="请输入制度名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="编号" prop="documentNumber">
        <el-input
          v-model="queryParams.documentNumber"
          placeholder="请输入编号"
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
          v-hasPermi="['security:OhsDocuments:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:OhsDocuments:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:OhsDocuments:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:OhsDocuments:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- 使用通用Excel导入组件 -->
        <excel-import
          import-url="/security/OhsDocuments/import"
          module-name="环境职业健康安全管理体系文件清单"
          module-code="security/OhsDocuments"
          button-text="导入"
          button-type="success"
          button-plain
          button-icon="Upload"
          @success="getList"
        />
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OhsDocumentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="制度名称" align="center" prop="systemName" />
      <el-table-column label="编号" align="center" prop="documentNumber" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:OhsDocuments:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:OhsDocuments:remove']">删除</el-button>
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

    <!-- 添加或修改环境职业健康安全管理体系文件清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="OhsDocumentsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="制度名称" prop="systemName">
          <el-input v-model="form.systemName" placeholder="请输入制度名称" />
        </el-form-item>
        <el-form-item label="编号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入编号" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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

<script setup name="OhsDocuments">
import { listOhsDocuments, getOhsDocuments, delOhsDocuments, addOhsDocuments, updateOhsDocuments } from "@/api/security/OhsDocuments";
import ExcelImport from "@/components/ExcelImport/index.vue";

const { proxy } = getCurrentInstance();
const OhsDocumentsList = ref([]);
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
    systemName: null,
    documentNumber: null,
    remarks: null
  },
  rules: {
    systemName: [
      { required: true, message: "制度名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询环境职业健康安全管理体系文件清单列表 */
function getList() {
  loading.value = true;
  listOhsDocuments(queryParams.value).then(response => {
    OhsDocumentsList.value = response.rows;
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
    systemName: null,
    documentNumber: null,
    remarks: null
  };
  proxy.resetForm("OhsDocumentsRef");
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
  title.value = "添加环境职业健康安全管理体系文件清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOhsDocuments(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改环境职业健康安全管理体系文件清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["OhsDocumentsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOhsDocuments(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOhsDocuments(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除环境职业健康安全管理体系文件清单编号为"' + _ids + '"的数据项？').then(function() {
    return delOhsDocuments(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/OhsDocuments/export', {
    ...queryParams.value
  }, `OhsDocuments_${new Date().getTime()}.xlsx`)
}

getList();
</script>
