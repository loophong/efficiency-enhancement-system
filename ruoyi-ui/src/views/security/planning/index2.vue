<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="危险源点
" prop="hazardSource">
        <el-input
          v-model="queryParams.hazardSource"
          placeholder="请输入危险源点
"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="重要危险源" prop="significantHazard">
        <el-input
          v-model="queryParams.significantHazard"
          placeholder="请输入重要危险源"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="导致事故健康危害" prop="accidentHealthHazards">
        <el-input
          v-model="queryParams.accidentHealthHazards"
          placeholder="请输入导致事故健康危害"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序/ 设备/人员" prop="processEquipmentPersonnel">
        <el-input
          v-model="queryParams.processEquipmentPersonnel"
          placeholder="请输入工序/ 设备/人员"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="控制方法" prop="controlMethods">
        <el-input
          v-model="queryParams.controlMethods"
          placeholder="请输入控制方法"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门/工序" prop="departmentProcess">
        <el-input
          v-model="queryParams.departmentProcess"
          placeholder="请输入部门/工序"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in security_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['security:unacceptablerisklist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:unacceptablerisklist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:unacceptablerisklist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:unacceptablerisklist:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unacceptablerisklistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="危险源点
" align="center" prop="hazardSource" />
      <el-table-column label="重要危险源" align="center" prop="significantHazard" />
      <el-table-column label="导致事故健康危害" align="center" prop="accidentHealthHazards" />
      <el-table-column label="工序/ 设备/人员" align="center" prop="processEquipmentPersonnel" />
      <el-table-column label="控制方法" align="center" prop="controlMethods" />
      <el-table-column label="部门/工序" align="center" prop="departmentProcess" />
      <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:unacceptablerisklist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:unacceptablerisklist:remove']">删除</el-button>
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

    <!-- 添加或修改不可接受风险清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="unacceptablerisklistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="危险源点
" prop="hazardSource">
          <el-input v-model="form.hazardSource" placeholder="请输入危险源点
" />
        </el-form-item>
        <el-form-item label="重要危险源" prop="significantHazard">
          <el-input v-model="form.significantHazard" placeholder="请输入重要危险源" />
        </el-form-item>
        <el-form-item label="导致事故健康危害" prop="accidentHealthHazards">
          <el-input v-model="form.accidentHealthHazards" placeholder="请输入导致事故健康危害" />
        </el-form-item>
        <el-form-item label="工序/ 设备/人员" prop="processEquipmentPersonnel">
          <el-input v-model="form.processEquipmentPersonnel" placeholder="请输入工序/ 设备/人员" />
        </el-form-item>
        <el-form-item label="控制方法" prop="controlMethods">
          <el-input v-model="form.controlMethods" placeholder="请输入控制方法" />
        </el-form-item>
        <el-form-item label="部门/工序" prop="departmentProcess">
          <el-input v-model="form.departmentProcess" placeholder="请输入部门/工序" />
        </el-form-item>
        <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="Unacceptablerisklist">
import { listUnacceptablerisklist, getUnacceptablerisklist, delUnacceptablerisklist, addUnacceptablerisklist, updateUnacceptablerisklist } from "@/api/security/unacceptablerisklist";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const unacceptablerisklistList = ref([]);
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
    hazardSource: null,
    significantHazard: null,
    accidentHealthHazards: null,
    processEquipmentPersonnel: null,
    controlMethods: null,
    departmentProcess: null,
    statu: null
  },
  rules: {
    hazardSource: [
      { required: true, message: "危险源点不能为空", trigger: "blur" }
    ],
    significantHazard: [
      { required: true, message: "重要危险源不能为空", trigger: "blur" }
    ],
    accidentHealthHazards: [
      { required: true, message: "导致事故健康危害不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询不可接受风险清单列表 */
function getList() {
  loading.value = true;
  listUnacceptablerisklist(queryParams.value).then(response => {
    unacceptablerisklistList.value = response.rows;
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
    hazardSource: null,
    significantHazard: null,
    accidentHealthHazards: null,
    processEquipmentPersonnel: null,
    controlMethods: null,
    departmentProcess: null,
    statu: null
  };
  proxy.resetForm("unacceptablerisklistRef");
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
  title.value = "添加不可接受风险清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getUnacceptablerisklist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改不可接受风险清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["unacceptablerisklistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUnacceptablerisklist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUnacceptablerisklist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除不可接受风险清单编号为"' + _ids + '"的数据项？').then(function() {
    return delUnacceptablerisklist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/unacceptablerisklist/export', {
    ...queryParams.value
  }, `unacceptablerisklist_${new Date().getTime()}.xlsx`)
}

getList();
</script>
