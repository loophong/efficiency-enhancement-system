<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="设施类别" prop="facilityCategory">
        <el-input
          v-model="queryParams.facilityCategory"
          placeholder="请输入设施类别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="安全设施名称" prop="safetyFacilityName">
        <el-input
          v-model="queryParams.safetyFacilityName"
          placeholder="请输入安全设施名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="校验周期" prop="inspectionCycle">
        <el-input
          v-model="queryParams.inspectionCycle"
          placeholder="请输入校验周期"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="下次校验时间" prop="nextInspectionDate">
        <el-date-picker clearable
          v-model="queryParams.nextInspectionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择下次校验时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="责任单位" prop="responsibleUnit">
        <el-input
          v-model="queryParams.responsibleUnit"
          placeholder="请输入责任单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入位置"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人" prop="personInCharge">
        <el-input
          v-model="queryParams.personInCharge"
          placeholder="请输入负责人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现状" prop="currentStatus">
        <el-input
          v-model="queryParams.currentStatus"
          placeholder="请输入现状"
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
          v-hasPermi="['security:SafetyFacilityLedger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:SafetyFacilityLedger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:SafetyFacilityLedger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:SafetyFacilityLedger:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="SafetyFacilityLedgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="设施类别" align="center" prop="facilityCategory" />
      <el-table-column label="安全设施名称" align="center" prop="safetyFacilityName" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="校验周期" align="center" prop="inspectionCycle" />
      <el-table-column label="下次校验时间" align="center" prop="nextInspectionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.nextInspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="责任单位" align="center" prop="responsibleUnit" />
      <el-table-column label="位置" align="center" prop="location" />
      <el-table-column label="负责人" align="center" prop="personInCharge" />
      <el-table-column label="现状" align="center" prop="currentStatus" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:SafetyFacilityLedger:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:SafetyFacilityLedger:remove']">删除</el-button>
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

    <!-- 添加或修改安全防护设备设施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="SafetyFacilityLedgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="设施类别" prop="facilityCategory">
          <el-input v-model="form.facilityCategory" placeholder="请输入设施类别" />
        </el-form-item>
        <el-form-item label="安全设施名称" prop="safetyFacilityName">
          <el-input v-model="form.safetyFacilityName" placeholder="请输入安全设施名称" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="校验周期" prop="inspectionCycle">
          <el-input v-model="form.inspectionCycle" placeholder="请输入校验周期" />
        </el-form-item>
        <el-form-item label="下次校验时间" prop="nextInspectionDate">
          <el-date-picker clearable
            v-model="form.nextInspectionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择下次校验时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="责任单位" prop="responsibleUnit">
          <el-input v-model="form.responsibleUnit" placeholder="请输入责任单位" />
        </el-form-item>
        <el-form-item label="位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入位置" />
        </el-form-item>
        <el-form-item label="负责人" prop="personInCharge">
          <el-input v-model="form.personInCharge" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="现状" prop="currentStatus">
          <el-input v-model="form.currentStatus" placeholder="请输入现状" />
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

<script setup name="SafetyFacilityLedger">
import { listSafetyFacilityLedger, getSafetyFacilityLedger, delSafetyFacilityLedger, addSafetyFacilityLedger, updateSafetyFacilityLedger } from "@/api/security/SafetyFacilityLedger";

const { proxy } = getCurrentInstance();

const SafetyFacilityLedgerList = ref([]);
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
    facilityCategory: null,
    safetyFacilityName: null,
    quantity: null,
    inspectionCycle: null,
    nextInspectionDate: null,
    responsibleUnit: null,
    location: null,
    personInCharge: null,
    currentStatus: null
  },
  rules: {
    facilityCategory: [
      { required: true, message: "设施类别不能为空", trigger: "blur" }
    ],
    safetyFacilityName: [
      { required: true, message: "安全设施名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全防护设备设施列表 */
function getList() {
  loading.value = true;
  listSafetyFacilityLedger(queryParams.value).then(response => {
    SafetyFacilityLedgerList.value = response.rows;
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
    facilityCategory: null,
    safetyFacilityName: null,
    quantity: null,
    inspectionCycle: null,
    nextInspectionDate: null,
    responsibleUnit: null,
    location: null,
    personInCharge: null,
    currentStatus: null
  };
  proxy.resetForm("SafetyFacilityLedgerRef");
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
  title.value = "添加安全防护设备设施";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSafetyFacilityLedger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全防护设备设施";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["SafetyFacilityLedgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSafetyFacilityLedger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSafetyFacilityLedger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全防护设备设施编号为"' + _ids + '"的数据项？').then(function() {
    return delSafetyFacilityLedger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/SafetyFacilityLedger/export', {
    ...queryParams.value
  }, `SafetyFacilityLedger_${new Date().getTime()}.xlsx`)
}

getList();
</script>
