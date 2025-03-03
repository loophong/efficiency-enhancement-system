<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="环境因素" prop="environmentalFactor">
        <el-input
          v-model="queryParams.environmentalFactor"
          placeholder="请输入环境因素"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="环境影响" prop="environmentalImpact">
        <el-input
          v-model="queryParams.environmentalImpact"
          placeholder="请输入环境影响"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动/生产/服务" prop="activityProductService">
        <el-input
          v-model="queryParams.activityProductService"
          placeholder="请输入活动/生产/服务"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门/工序/岗位" prop="departmentProcessPosition">
        <el-input
          v-model="queryParams.departmentProcessPosition"
          placeholder="请输入部门/工序/岗位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时态/状态" prop="temporalState">
        <el-input
          v-model="queryParams.temporalState"
          placeholder="请输入时态/状态"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管控措施" prop="controlMeasures">
        <el-input
          v-model="queryParams.controlMeasures"
          placeholder="请输入管控措施"
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
          v-hasPermi="['security:importantfactors:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:importantfactors:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:importantfactors:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:importantfactors:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="importantfactorsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="环境因素" align="center" prop="environmentalFactor" />
      <el-table-column label="环境影响" align="center" prop="environmentalImpact" />
      <el-table-column label="活动/生产/服务" align="center" prop="activityProductService" />
      <el-table-column label="部门/工序/岗位" align="center" prop="departmentProcessPosition" />
      <el-table-column label="时态/状态" align="center" prop="temporalState" />
      <el-table-column label="管控措施" align="center" prop="controlMeasures" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:importantfactors:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:importantfactors:remove']">删除</el-button>
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

    <!-- 添加或修改公司级重要环境因素清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="importantfactorsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="环境因素" prop="environmentalFactor">
          <el-input v-model="form.environmentalFactor" placeholder="请输入环境因素" />
        </el-form-item>
        <el-form-item label="环境影响" prop="environmentalImpact">
          <el-input v-model="form.environmentalImpact" placeholder="请输入环境影响" />
        </el-form-item>
        <el-form-item label="活动/生产/服务" prop="activityProductService">
          <el-input v-model="form.activityProductService" placeholder="请输入活动/生产/服务" />
        </el-form-item>
        <el-form-item label="部门/工序/岗位" prop="departmentProcessPosition">
          <el-input v-model="form.departmentProcessPosition" placeholder="请输入部门/工序/岗位" />
        </el-form-item>
        <el-form-item label="时态/状态" prop="temporalState">
          <el-input v-model="form.temporalState" placeholder="请输入时态/状态" />
        </el-form-item>
        <el-form-item label="管控措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" placeholder="请输入管控措施" />
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

<script setup name="Importantfactors">
import { listImportantfactors, getImportantfactors, delImportantfactors, addImportantfactors, updateImportantfactors } from "@/api/security/importantfactors";

const { proxy } = getCurrentInstance();

const importantfactorsList = ref([]);
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
    environmentalFactor: null,
    environmentalImpact: null,
    activityProductService: null,
    departmentProcessPosition: null,
    temporalState: null,
    controlMeasures: null
  },
  rules: {
    id: [
      { required: true, message: "序号不能为空", trigger: "blur" }
    ],
    environmentalFactor: [
      { required: true, message: "环境因素不能为空", trigger: "blur" }
    ],
    environmentalImpact: [
      { required: true, message: "环境影响不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询公司级重要环境因素清单列表 */
function getList() {
  loading.value = true;
  listImportantfactors(queryParams.value).then(response => {
    importantfactorsList.value = response.rows;
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
    environmentalFactor: null,
    environmentalImpact: null,
    activityProductService: null,
    departmentProcessPosition: null,
    temporalState: null,
    controlMeasures: null
  };
  proxy.resetForm("importantfactorsRef");
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
  title.value = "添加公司级重要环境因素清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getImportantfactors(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改公司级重要环境因素清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["importantfactorsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateImportantfactors(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addImportantfactors(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除公司级重要环境因素清单编号为"' + _ids + '"的数据项？').then(function() {
    return delImportantfactors(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/importantfactors/export', {
    ...queryParams.value
  }, `importantfactors_${new Date().getTime()}.xlsx`)
}

getList();
</script>
