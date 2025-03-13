<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="事故名称" prop="accidentName">
        <el-input
          v-model="queryParams.accidentName"
          placeholder="请输入事故名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生地点" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入发生地点"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事故类别" prop="accidentCategory">
        <el-input
          v-model="queryParams.accidentCategory"
          placeholder="请输入事故类别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="事故伤害人员姓名" prop="injuredPersonName">
        <el-input
          v-model="queryParams.injuredPersonName"
          placeholder="请输入事故伤害人员姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="injuredPersonGender">
        <el-input
          v-model="queryParams.injuredPersonGender"
          placeholder="请输入性别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="injuredPersonAge">
        <el-input
          v-model="queryParams.injuredPersonAge"
          placeholder="请输入年龄"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工种" prop="injuredPersonJobTitle">
        <el-input
          v-model="queryParams.injuredPersonJobTitle"
          placeholder="请输入工种"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="受伤部位" prop="injuredPart">
        <el-input
          v-model="queryParams.injuredPart"
          placeholder="请输入受伤部位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="受过何种安全教育" prop="safetyEducationReceivedInjured">
        <el-input
          v-model="queryParams.safetyEducationReceivedInjured"
          placeholder="请输入受过何种安全教育"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="职工编码" prop="employeeCode">
        <el-input
          v-model="queryParams.employeeCode"
          placeholder="请输入职工编码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactNumber">
        <el-input
          v-model="queryParams.contactNumber"
          placeholder="请输入联系电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号码" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入身份证号码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工作时间" prop="workTime">
        <el-date-picker clearable
          v-model="queryParams.workTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择工作时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事故责任人员姓名" prop="responsiblePersonName">
        <el-input
          v-model="queryParams.responsiblePersonName"
          placeholder="请输入事故责任人员姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="responsiblePersonGender">
        <el-input
          v-model="queryParams.responsiblePersonGender"
          placeholder="请输入性别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年龄" prop="responsiblePersonAge">
        <el-input
          v-model="queryParams.responsiblePersonAge"
          placeholder="请输入年龄"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工种" prop="responsiblePersonJobTitle">
        <el-input
          v-model="queryParams.responsiblePersonJobTitle"
          placeholder="请输入工种"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="伤害程度" prop="injurySeverity">
        <el-input
          v-model="queryParams.injurySeverity"
          placeholder="请输入伤害程度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="受过何种安全教育" prop="safetyEducationReceivedResponsible">
        <el-input
          v-model="queryParams.safetyEducationReceivedResponsible"
          placeholder="请输入受过何种安全教育"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参加工作时间" prop="startWorkingDate">
        <el-date-picker clearable
          v-model="queryParams.startWorkingDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择参加工作时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="事故判定" prop="accidentClassification">
        <el-input
          v-model="queryParams.accidentClassification"
          placeholder="请输入事故判定"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="记录时间" prop="recordDate">
        <el-date-picker clearable
          v-model="queryParams.recordDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="记录人" prop="recorder">
        <el-input
          v-model="queryParams.recorder"
          placeholder="请输入记录人"
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
          v-hasPermi="['security:AccidentProcessRecords:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:AccidentProcessRecords:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:AccidentProcessRecords:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:AccidentProcessRecords:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AccidentProcessRecordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="事故名称" align="center" prop="accidentName" />
      <el-table-column label="发生地点" align="center" prop="location" />
      <el-table-column label="事故类别" align="center" prop="accidentCategory" />
      <el-table-column label="事故伤害人员姓名" align="center" prop="injuredPersonName" />
      <el-table-column label="性别" align="center" prop="injuredPersonGender" />
      <el-table-column label="年龄" align="center" prop="injuredPersonAge" />
      <el-table-column label="工种" align="center" prop="injuredPersonJobTitle" />
      <el-table-column label="工作描述" align="center" prop="injuredPersonWorkDescription" />
      <el-table-column label="受伤部位" align="center" prop="injuredPart" />
      <el-table-column label="受过何种安全教育" align="center" prop="safetyEducationReceivedInjured" />
      <el-table-column label="职工编码" align="center" prop="employeeCode" />
      <el-table-column label="家庭详细地址" align="center" prop="homeAddress" />
      <el-table-column label="联系电话" align="center" prop="contactNumber" />
      <el-table-column label="身份证号码" align="center" prop="idNumber" />
      <el-table-column label="工作时间" align="center" prop="workTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.workTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事故责任人员姓名" align="center" prop="responsiblePersonName" />
      <el-table-column label="性别" align="center" prop="responsiblePersonGender" />
      <el-table-column label="年龄" align="center" prop="responsiblePersonAge" />
      <el-table-column label="工种" align="center" prop="responsiblePersonJobTitle" />
      <el-table-column label="伤害程度" align="center" prop="injurySeverity" />
      <el-table-column label="受过何种安全教育" align="center" prop="safetyEducationReceivedResponsible" />
      <el-table-column label="参加工作时间" align="center" prop="startWorkingDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startWorkingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事故发生过程" align="center" prop="accidentProcess" />
      <el-table-column label="事故发生后抢救措施" align="center" prop="postAccidentActions" />
      <el-table-column label="事故判定" align="center" prop="accidentClassification" />
      <el-table-column label="记录时间" align="center" prop="recordDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.recordDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录人" align="center" prop="recorder" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:AccidentProcessRecords:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:AccidentProcessRecords:remove']">删除</el-button>
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

    <!-- 添加或修改事故过程记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="AccidentProcessRecordsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事故名称" prop="accidentName">
          <el-input v-model="form.accidentName" placeholder="请输入事故名称" />
        </el-form-item>
        <el-form-item label="发生地点" prop="location">
          <el-input v-model="form.location" placeholder="请输入发生地点" />
        </el-form-item>
        <el-form-item label="事故类别" prop="accidentCategory">
          <el-input v-model="form.accidentCategory" placeholder="请输入事故类别" />
        </el-form-item>
        <el-form-item label="事故伤害人员姓名" prop="injuredPersonName">
          <el-input v-model="form.injuredPersonName" placeholder="请输入事故伤害人员姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="injuredPersonGender">
          <el-input v-model="form.injuredPersonGender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="年龄" prop="injuredPersonAge">
          <el-input v-model="form.injuredPersonAge" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="工种" prop="injuredPersonJobTitle">
          <el-input v-model="form.injuredPersonJobTitle" placeholder="请输入工种" />
        </el-form-item>
        <el-form-item label="工作描述" prop="injuredPersonWorkDescription">
          <el-input v-model="form.injuredPersonWorkDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="受伤部位" prop="injuredPart">
          <el-input v-model="form.injuredPart" placeholder="请输入受伤部位" />
        </el-form-item>
        <el-form-item label="受过何种安全教育" prop="safetyEducationReceivedInjured">
          <el-input v-model="form.safetyEducationReceivedInjured" placeholder="请输入受过何种安全教育" />
        </el-form-item>
        <el-form-item label="职工编码" prop="employeeCode">
          <el-input v-model="form.employeeCode" placeholder="请输入职工编码" />
        </el-form-item>
        <el-form-item label="家庭详细地址" prop="homeAddress">
          <el-input v-model="form.homeAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="身份证号码" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="工作时间" prop="workTime">
          <el-date-picker clearable
            v-model="form.workTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择工作时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="事故责任人员姓名" prop="responsiblePersonName">
          <el-input v-model="form.responsiblePersonName" placeholder="请输入事故责任人员姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="responsiblePersonGender">
          <el-input v-model="form.responsiblePersonGender" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="年龄" prop="responsiblePersonAge">
          <el-input v-model="form.responsiblePersonAge" placeholder="请输入年龄" />
        </el-form-item>
        <el-form-item label="工种" prop="responsiblePersonJobTitle">
          <el-input v-model="form.responsiblePersonJobTitle" placeholder="请输入工种" />
        </el-form-item>
        <el-form-item label="伤害程度" prop="injurySeverity">
          <el-input v-model="form.injurySeverity" placeholder="请输入伤害程度" />
        </el-form-item>
        <el-form-item label="受过何种安全教育" prop="safetyEducationReceivedResponsible">
          <el-input v-model="form.safetyEducationReceivedResponsible" placeholder="请输入受过何种安全教育" />
        </el-form-item>
        <el-form-item label="参加工作时间" prop="startWorkingDate">
          <el-date-picker clearable
            v-model="form.startWorkingDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择参加工作时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="事故发生过程" prop="accidentProcess">
          <el-input v-model="form.accidentProcess" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故发生后抢救措施" prop="postAccidentActions">
          <el-input v-model="form.postAccidentActions" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="事故判定" prop="accidentClassification">
          <el-input v-model="form.accidentClassification" placeholder="请输入事故判定" />
        </el-form-item>
        <el-form-item label="记录时间" prop="recordDate">
          <el-date-picker clearable
            v-model="form.recordDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择记录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="记录人" prop="recorder">
          <el-input v-model="form.recorder" placeholder="请输入记录人" />
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

<script setup name="AccidentProcessRecords">
import { listAccidentProcessRecords, getAccidentProcessRecords, delAccidentProcessRecords, addAccidentProcessRecords, updateAccidentProcessRecords } from "@/api/security/AccidentProcessRecords";

const { proxy } = getCurrentInstance();

const AccidentProcessRecordsList = ref([]);
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
    accidentName: null,
    location: null,
    accidentCategory: null,
    injuredPersonName: null,
    injuredPersonGender: null,
    injuredPersonAge: null,
    injuredPersonJobTitle: null,
    injuredPersonWorkDescription: null,
    injuredPart: null,
    safetyEducationReceivedInjured: null,
    employeeCode: null,
    homeAddress: null,
    contactNumber: null,
    idNumber: null,
    workTime: null,
    responsiblePersonName: null,
    responsiblePersonGender: null,
    responsiblePersonAge: null,
    responsiblePersonJobTitle: null,
    injurySeverity: null,
    safetyEducationReceivedResponsible: null,
    startWorkingDate: null,
    accidentProcess: null,
    postAccidentActions: null,
    accidentClassification: null,
    recordDate: null,
    recorder: null
  },
  rules: {
    accidentName: [
      { required: true, message: "事故名称不能为空", trigger: "blur" }
    ],
    location: [
      { required: true, message: "发生地点不能为空", trigger: "blur" }
    ],
    accidentCategory: [
      { required: true, message: "事故类别不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询事故过程记录列表 */
function getList() {
  loading.value = true;
  listAccidentProcessRecords(queryParams.value).then(response => {
    AccidentProcessRecordsList.value = response.rows;
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
    accidentName: null,
    location: null,
    accidentCategory: null,
    injuredPersonName: null,
    injuredPersonGender: null,
    injuredPersonAge: null,
    injuredPersonJobTitle: null,
    injuredPersonWorkDescription: null,
    injuredPart: null,
    safetyEducationReceivedInjured: null,
    employeeCode: null,
    homeAddress: null,
    contactNumber: null,
    idNumber: null,
    workTime: null,
    responsiblePersonName: null,
    responsiblePersonGender: null,
    responsiblePersonAge: null,
    responsiblePersonJobTitle: null,
    injurySeverity: null,
    safetyEducationReceivedResponsible: null,
    startWorkingDate: null,
    accidentProcess: null,
    postAccidentActions: null,
    accidentClassification: null,
    recordDate: null,
    recorder: null
  };
  proxy.resetForm("AccidentProcessRecordsRef");
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
  title.value = "添加事故过程记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAccidentProcessRecords(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改事故过程记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["AccidentProcessRecordsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAccidentProcessRecords(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAccidentProcessRecords(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除事故过程记录编号为"' + _ids + '"的数据项？').then(function() {
    return delAccidentProcessRecords(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/AccidentProcessRecords/export', {
    ...queryParams.value
  }, `AccidentProcessRecords_${new Date().getTime()}.xlsx`)
}

getList();
</script>
