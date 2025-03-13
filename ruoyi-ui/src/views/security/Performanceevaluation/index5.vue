<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['security:CorrectiveMeasuresApproval:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:CorrectiveMeasuresApproval:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:CorrectiveMeasuresApproval:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:CorrectiveMeasuresApproval:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="CorrectiveMeasuresApprovalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="责任科室" align="center" prop="responsibleUnit" />
      <el-table-column label="参加单位" align="center" prop="participatingUnits" />
      <el-table-column label="问题描述" align="center" prop="nonConformanceDescription" />
      <el-table-column label="原因分析" align="center" prop="causeAnalysis" />
      <el-table-column label="负责人" align="center" prop="responsiblePerson" />
      <el-table-column label="签字时间" align="center" prop="time" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="恢复计划" align="center" prop="correctivePreventivePlan" />
      <el-table-column label="责任单位主管" align="center" prop="responsibleUnitSupervisor" />
      <el-table-column label="管理者代表审批" align="center" prop="managerRepresentativeApproval">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.managerRepresentativeApproval"/>
        </template>
      </el-table-column>
      <el-table-column label="审批时间" align="center" prop="planTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.planTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成情况" align="center" prop="implementationStatus" />
      <el-table-column label="负责人签字" align="center" prop="responsibleDepartmentHead" />
      <el-table-column label="签字时间" align="center" prop="completionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.completionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:CorrectiveMeasuresApproval:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:CorrectiveMeasuresApproval:remove']">删除</el-button>
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

    <!-- 添加或修改纠正与预防措施审批实施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="CorrectiveMeasuresApprovalRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="责任科室" prop="responsibleUnit">
          <el-input v-model="form.responsibleUnit" placeholder="请输入责任科室" />
        </el-form-item>
        <el-form-item label="参加单位" prop="participatingUnits">
          <el-input v-model="form.participatingUnits" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="问题描述" prop="nonConformanceDescription">
          <el-input v-model="form.nonConformanceDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="原因分析" prop="causeAnalysis">
          <el-input v-model="form.causeAnalysis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="负责人" prop="responsiblePerson">
          <el-input v-model="form.responsiblePerson" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="签字时间" prop="time">
          <el-date-picker clearable
            v-model="form.time"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择签字时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="恢复计划" prop="correctivePreventivePlan">
          <el-input v-model="form.correctivePreventivePlan" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="责任单位主管" prop="responsibleUnitSupervisor">
          <el-input v-model="form.responsibleUnitSupervisor" placeholder="请输入责任单位主管" />
        </el-form-item>
        <el-form-item label="管理者代表审批" prop="managerRepresentativeApproval">
          <el-select v-model="form.managerRepresentativeApproval" placeholder="请选择管理者代表审批">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批时间" prop="planTime">
          <el-date-picker clearable
            v-model="form.planTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择审批时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="负责人签字" prop="responsibleDepartmentHead">
          <el-input v-model="form.responsibleDepartmentHead" placeholder="请输入负责人签字" />
        </el-form-item>
        <el-form-item label="签字时间" prop="completionTime">
          <el-date-picker clearable
            v-model="form.completionTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择签字时间">
          </el-date-picker>
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

<script setup name="CorrectiveMeasuresApproval">
import { listCorrectiveMeasuresApproval, getCorrectiveMeasuresApproval, delCorrectiveMeasuresApproval, addCorrectiveMeasuresApproval, updateCorrectiveMeasuresApproval } from "@/api/security/CorrectiveMeasuresApproval";

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');

const CorrectiveMeasuresApprovalList = ref([]);
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
  },
  rules: {
    responsibleUnit: [
      { required: true, message: "责任科室不能为空", trigger: "blur" }
    ],
    participatingUnits: [
      { required: true, message: "参加单位不能为空", trigger: "blur" }
    ],
    nonConformanceDescription: [
      { required: true, message: "问题描述不能为空", trigger: "blur" }
    ],
    causeAnalysis: [
      { required: true, message: "原因分析不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询纠正与预防措施审批实施列表 */
function getList() {
  loading.value = true;
  listCorrectiveMeasuresApproval(queryParams.value).then(response => {
    CorrectiveMeasuresApprovalList.value = response.rows;
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
    responsibleUnit: null,
    participatingUnits: null,
    nonConformanceDescription: null,
    causeAnalysis: null,
    responsiblePerson: null,
    time: null,
    correctivePreventivePlan: null,
    responsibleUnitSupervisor: null,
    managerRepresentativeApproval: null,
    planTime: null,
    implementationStatus: null,
    responsibleDepartmentHead: null,
    completionTime: null
  };
  proxy.resetForm("CorrectiveMeasuresApprovalRef");
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
  title.value = "添加纠正与预防措施审批实施";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCorrectiveMeasuresApproval(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改纠正与预防措施审批实施";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["CorrectiveMeasuresApprovalRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCorrectiveMeasuresApproval(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCorrectiveMeasuresApproval(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除纠正与预防措施审批实施编号为"' + _ids + '"的数据项？').then(function() {
    return delCorrectiveMeasuresApproval(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/CorrectiveMeasuresApproval/export', {
    ...queryParams.value
  }, `CorrectiveMeasuresApproval_${new Date().getTime()}.xlsx`)
}

getList();
</script>
