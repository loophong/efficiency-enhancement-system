<template>
  <el-tabs type="border-card" style="padding: 6px">
    <el-tab-pane label="班组自主">
      <div class="app-container">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
          <el-form-item label="序号" prop="orderNum">
            <el-input v-model="queryParams.orderNum" placeholder="请输入序号" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="执行班组" prop="executeGroup">
            <el-input v-model="queryParams.executeGroup" placeholder="请输入执行班组" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="保养周期" prop="maintenanceCycle">
            <el-input v-model="queryParams.maintenanceCycle" placeholder="请输入保养周期" clearable
              @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(1W)" prop="monthOne">
            <el-input v-model="queryParams.monthOne" placeholder="请输入月度(1W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(2W)" prop="monthTwo">
            <el-input v-model="queryParams.monthTwo" placeholder="请输入月度(2W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(3W)" prop="monthThree">
            <el-input v-model="queryParams.monthThree" placeholder="请输入月度(3W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(4W)" prop="monthFour">
            <el-input v-model="queryParams.monthFour" placeholder="请输入月度(4W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
            <el-button icon="Refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd"
              v-hasPermi="['maintenanceTable:plan:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
              v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['maintenanceTable:plan:export']">导出</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange" border stripe>
          <el-table-column type="selection" width="55" align="center" />
          <!-- <el-table-column label="主键id" align="center" prop="groupId" /> -->
          <el-table-column label="序号" align="center" prop="orderNum" />
          <el-table-column label="设备类别" align="center" prop="deviceType" />
          <el-table-column label="执行班组" align="center" prop="executeGroup" />
          <el-table-column label="保养内容" align="center" prop="maintenanceContent">
            <template #default="scope">
              <span v-html="scope.row.maintenanceContent"></span>
            </template>
          </el-table-column>
          <el-table-column label="保养周期" align="center" prop="maintenanceCycle" />
          <el-table-column label="月度(1W)" align="center" prop="monthOne" />
          <el-table-column label="月度(2W)" align="center" prop="monthTwo" />
          <el-table-column label="月度(3W)" align="center" prop="monthThree" />
          <el-table-column label="月度(4W)" align="center" prop="monthFour" />
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template #default="scope">
              <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
          v-model:limit="queryParams.pageSize" @pagination="getList" />

        <!-- 添加或修改班组计划保养对话框 -->
        <el-dialog :title="title" v-model="open" width="800px" append-to-body>
          <el-form ref="planRef" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="序号" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="请输入序号" />
            </el-form-item>
            <el-form-item label="执行班组" prop="executeGroup">
              <el-input v-model="form.executeGroup" placeholder="请输入执行班组" />
            </el-form-item>
            <el-form-item label="设备类别" prop="deviceType">
              <el-input v-model="form.deviceType" placeholder="请输入设备类别" />
            </el-form-item>
            <el-form-item label="保养内容">
              <editor v-model="form.maintenanceContent" :min-height="192" />
            </el-form-item>
            <el-form-item label="保养周期" prop="maintenanceCycle">
              <el-input v-model="form.maintenanceCycle" placeholder="请输入保养周期" />
            </el-form-item>
            <el-form-item label="月度(1W)" prop="monthOne">
              <el-input v-model="form.monthOne" placeholder="请输入月度(1W)" />
            </el-form-item>
            <el-form-item label="月度(2W)" prop="monthTwo">
              <el-input v-model="form.monthTwo" placeholder="请输入月度(2W)" />
            </el-form-item>
            <el-form-item label="月度(3W)" prop="monthThree">
              <el-input v-model="form.monthThree" placeholder="请输入月度(3W)" />
            </el-form-item>
            <el-form-item label="月度(4W)" prop="monthFour">
              <el-input v-model="form.monthFour" placeholder="请输入月度(4W)" />
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
    </el-tab-pane>
    <el-tab-pane label="专业计划">

    </el-tab-pane>
  </el-tabs>
</template>

<script setup name="Plan">
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/device/maintenanceTable/groupPlan";

const { proxy } = getCurrentInstance();

const planList = ref([]);
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
    orderNum: null,
    deviceType: null,
    executeGroup: null,
    maintenanceContent: null,
    maintenanceCycle: null,
    monthOne: null,
    monthTwo: null,
    monthThree: null,
    monthFour: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询班组计划保养列表 */
function getList() {
  loading.value = true;
  listPlan(queryParams.value).then(response => {
    planList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}
//  HTML 
function renderMaintenanceCycle(htmlString) {

  return `http://localhost:8080/${htmlString}`;
}
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    groupId: null,
    orderNum: null,
    deviceType: null,
    executeGroup: null,
    maintenanceContent: null,
    maintenanceCycle: null,
    monthOne: null,
    monthTwo: null,
    monthThree: null,
    monthFour: null
  };
  proxy.resetForm("planRef");
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
  ids.value = selection.map(item => item.groupId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加班组计划保养";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _groupId = row.groupId || ids.value
  getPlan(_groupId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改班组计划保养";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["planRef"].validate(valid => {
    if (valid) {
      if (form.value.groupId != null) {
        updatePlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPlan(form.value).then(response => {
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
  const _groupIds = row.groupId || ids.value;
  proxy.$modal.confirm('是否确认删除班组计划保养编号为"' + _groupIds + '"的数据项？').then(function () {
    return delPlan(_groupIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/plan/export', {
    ...queryParams.value
  }, `plan_${new Date().getTime()}.xlsx`)
}

getList();
</script>
