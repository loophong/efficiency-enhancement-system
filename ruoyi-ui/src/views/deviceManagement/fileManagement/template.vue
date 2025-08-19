<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item> -->
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['file:template:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['file:template:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['file:template:remove']">删除</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="主键id" align="center" prop="templateId" /> -->
      <el-table-column label="自主保全计划模板" align="center" prop="templateGroup" />
      <el-table-column label="专业保全计划模板" align="center" prop="templateMajor" />
      <el-table-column label="维修记录模板" align="center" prop="templateMaintenance" />
      <el-table-column label="指标分析模板" align="center" prop="templateIndicator" />
      <el-table-column label="设备台账模板" align="center" prop="templateDetails" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['file:template:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['file:template:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改设备导入模板文件对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="templateRef" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="自主保全计划模板" prop="templateGroup">
          <file-upload v-model="form.templateGroup" />
        </el-form-item>
        <el-form-item label="专业保全计划模板" prop="templateMajor">
          <file-upload v-model="form.templateMajor" />
        </el-form-item>
        <el-form-item label="维修记录模板" prop="templateMaintenance">
          <file-upload v-model="form.templateMaintenance" />
        </el-form-item>
        <el-form-item label="指标分析模板" prop="templateIndicator">
          <file-upload v-model="form.templateIndicator" />
        </el-form-item>
        <el-form-item label="设备台账模板" prop="templateDetails">
          <file-upload v-model="form.templateDetails" />
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

<script setup name="Template">
import { listTemplate, getTemplate, delTemplate, addTemplate, updateTemplate } from "@/api/device/fileTable/template";

const { proxy } = getCurrentInstance();

const templateList = ref([]);
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
    templateGroup: null,
    templateMajor: null,
    templateMaintenance: null,
    templateIndicator: null,
    templateDetails: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询设备导入模板文件列表 */
function getList() {
  loading.value = true;
  listTemplate(queryParams.value).then(response => {
    templateList.value = response.rows;
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
    templateId: null,
    templateGroup: null,
    templateMajor: null,
    templateMaintenance: null,
    templateIndicator: null,
    templateDetails: null
  };
  proxy.resetForm("templateRef");
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
  ids.value = selection.map(item => item.templateId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备导入模板文件";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _templateId = row.templateId || ids.value
  getTemplate(_templateId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备导入模板文件";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["templateRef"].validate(valid => {
    if (valid) {
      if (form.value.templateId != null) {
        updateTemplate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTemplate(form.value).then(response => {
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
  const _templateIds = row.templateId || ids.value;
  proxy.$modal.confirm('是否确认删除设备导入模板文件编号为"' + _templateIds + '"的数据项？').then(function () {
    return delTemplate(_templateIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/template/export', {
    ...queryParams.value
  }, `template_${new Date().getTime()}.xlsx`)
}

getList();
</script>
