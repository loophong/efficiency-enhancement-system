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
      <el-form-item label="发生时间" prop="occurrenceTime">
        <el-date-picker clearable
          v-model="queryParams.occurrenceTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择发生时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="验收人" prop="inspector">
        <el-input
          v-model="queryParams.inspector"
          placeholder="请输入验收人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="验收时间" prop="inspectionTime">
        <el-date-picker clearable
          v-model="queryParams.inspectionTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择验收时间">
        </el-date-picker>
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
          v-hasPermi="['security:AccidentMeasuresTracking:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:AccidentMeasuresTracking:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:AccidentMeasuresTracking:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:AccidentMeasuresTracking:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="AccidentMeasuresTrackingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100"/>
      <el-table-column label="事故名称" align="center" prop="accidentName" />
      <el-table-column label="发生时间" align="center" prop="occurrenceTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.occurrenceTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="整改措施" align="center" prop="correctiveMeasures" />
      <el-table-column label="整改责任单位/责任人" align="center" prop="responsibleUnitPerson" />
      <el-table-column label="整改完成情况" align="center" prop="completionStatus" />
      <el-table-column label="整改完成时间" align="center" prop="completionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.completionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="验收人" align="center" prop="inspector" />
      <el-table-column label="验收时间" align="center" prop="inspectionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.inspectionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:AccidentMeasuresTracking:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:AccidentMeasuresTracking:remove']">删除</el-button>
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

    <!-- 添加或修改事故对策及跟踪记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="AccidentMeasuresTrackingRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="事故名称" prop="accidentName">
          <el-input v-model="form.accidentName" placeholder="请输入事故名称" />
        </el-form-item>
        <el-form-item label="发生时间" prop="occurrenceTime">
          <el-date-picker clearable
            v-model="form.occurrenceTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择发生时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="整改措施" prop="correctiveMeasures">
          <el-input v-model="form.correctiveMeasures" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="整改责任单位/责任人" prop="responsibleUnitPerson">
          <el-input v-model="form.responsibleUnitPerson" placeholder="请输入整改责任单位/责任人" />
        </el-form-item>
        <el-form-item label="整改完成情况" prop="completionStatus">
          <el-input v-model="form.completionStatus" placeholder="请输入整改完成情况" />
        </el-form-item>
        <el-form-item label="整改完成时间" prop="completionTime">
          <el-date-picker clearable
            v-model="form.completionTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择整改完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="验收人" prop="inspector">
          <el-input v-model="form.inspector" placeholder="请输入验收人" />
        </el-form-item>
        <el-form-item label="验收时间" prop="inspectionTime">
          <el-date-picker clearable
            v-model="form.inspectionTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择验收时间">
          </el-date-picker>
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

<script setup name="AccidentMeasuresTracking">
import { listAccidentMeasuresTracking, getAccidentMeasuresTracking, delAccidentMeasuresTracking, addAccidentMeasuresTracking, updateAccidentMeasuresTracking } from "@/api/security/AccidentMeasuresTracking";

const { proxy } = getCurrentInstance();

const AccidentMeasuresTrackingList = ref([]);
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
    occurrenceTime: null,
    correctiveMeasures: null,
    inspector: null,
    inspectionTime: null,
  },
  rules: {
    accidentName: [
      { required: true, message: "事故名称不能为空", trigger: "blur" }
    ],
    occurrenceTime: [
      { required: true, message: "发生时间不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询事故对策及跟踪记录列表 */
function getList() {
  loading.value = true;
  listAccidentMeasuresTracking(queryParams.value).then(response => {
    AccidentMeasuresTrackingList.value = response.rows;
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
    occurrenceTime: null,
    correctiveMeasures: null,
    responsibleUnitPerson: null,
    completionStatus: null,
    completionTime: null,
    inspector: null,
    inspectionTime: null,
    remarks: null
  };
  proxy.resetForm("AccidentMeasuresTrackingRef");
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
  title.value = "添加事故对策及跟踪记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAccidentMeasuresTracking(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改事故对策及跟踪记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["AccidentMeasuresTrackingRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAccidentMeasuresTracking(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAccidentMeasuresTracking(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除事故对策及跟踪记录编号为"' + _ids + '"的数据项？').then(function() {
    return delAccidentMeasuresTracking(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/AccidentMeasuresTracking/export', {
    ...queryParams.value
  }, `AccidentMeasuresTracking_${new Date().getTime()}.xlsx`)
}

getList();
</script>
