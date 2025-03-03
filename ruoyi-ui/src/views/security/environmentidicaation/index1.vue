<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="相关方类型" prop="partyInvolved">
        <el-input
          v-model="queryParams.partyInvolved"
          placeholder="请输入相关方类型"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求和期望" prop="demand">
        <el-input
          v-model="queryParams.demand"
          placeholder="请输入需求和期望"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测指标" prop="monitoringIndicators">
        <el-input
          v-model="queryParams.monitoringIndicators"
          placeholder="请输入监测指标"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测频率" prop="monitoringFrequency">
        <el-input
          v-model="queryParams.monitoringFrequency"
          placeholder="请输入监测频率"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测部门" prop="monitoringDepartment">
        <el-input
          v-model="queryParams.monitoringDepartment"
          placeholder="请输入监测部门"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备用字段1" prop="beiyongziduan1">
        <el-date-picker clearable
          v-model="queryParams.beiyongziduan1"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择备用字段1">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备用字段2" prop="beiyongziduan2">
        <el-input
          v-model="queryParams.beiyongziduan2"
          placeholder="请输入备用字段2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备用字段3" prop="beiyongziduan3">
        <el-date-picker clearable
          v-model="queryParams.beiyongziduan3"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择备用字段3">
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
          v-hasPermi="['security:party1:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:party1:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:party1:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:party1:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="party1List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="相关方类型" align="center" prop="partyInvolved" />
      <el-table-column label="需求和期望" align="center" prop="demand" />
      <el-table-column label="监测指标" align="center" prop="monitoringIndicators" />
      <el-table-column label="监测频率" align="center" prop="monitoringFrequency" />
      <el-table-column label="监测部门" align="center" prop="monitoringDepartment" />
      <el-table-column label="备用字段1" align="center" prop="beiyongziduan1" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.beiyongziduan1, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备用字段2" align="center" prop="beiyongziduan2" />
      <el-table-column label="备用字段3" align="center" prop="beiyongziduan3" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.beiyongziduan3, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:party1:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:party1:remove']">删除</el-button>
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

    <!-- 添加或修改相关方期望对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="party1Ref" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="相关方类型" prop="partyInvolved">
          <el-input v-model="form.partyInvolved" placeholder="请输入相关方类型" />
        </el-form-item>
        <el-form-item label="需求和期望" prop="demand">
          <el-input v-model="form.demand" placeholder="请输入需求和期望" />
        </el-form-item>
        <el-form-item label="监测指标" prop="monitoringIndicators">
          <el-input v-model="form.monitoringIndicators" placeholder="请输入监测指标" />
        </el-form-item>
        <el-form-item label="监测频率" prop="monitoringFrequency">
          <el-input v-model="form.monitoringFrequency" placeholder="请输入监测频率" />
        </el-form-item>
        <el-form-item label="监测部门" prop="monitoringDepartment">
          <el-input v-model="form.monitoringDepartment" placeholder="请输入监测部门" />
        </el-form-item>
        <el-form-item label="备用字段1" prop="beiyongziduan1">
          <el-date-picker clearable
            v-model="form.beiyongziduan1"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择备用字段1">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备用字段2" prop="beiyongziduan2">
          <el-input v-model="form.beiyongziduan2" placeholder="请输入备用字段2" />
        </el-form-item>
        <el-form-item label="备用字段3" prop="beiyongziduan3">
          <el-date-picker clearable
            v-model="form.beiyongziduan3"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择备用字段3">
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

<script setup name="Party1">
import { listParty1, getParty1, delParty1, addParty1, updateParty1 } from "@/api/security/party1";

const { proxy } = getCurrentInstance();

const party1List = ref([]);
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
    partyInvolved: null,
    demand: null,
    monitoringIndicators: null,
    monitoringFrequency: null,
    monitoringDepartment: null,
    beiyongziduan1: null,
    beiyongziduan2: null,
    beiyongziduan3: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询相关方期望列表 */
function getList() {
  loading.value = true;
  listParty1(queryParams.value).then(response => {
    party1List.value = response.rows;
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
    partyInvolved: null,
    demand: null,
    monitoringIndicators: null,
    monitoringFrequency: null,
    monitoringDepartment: null,
    beiyongziduan1: null,
    beiyongziduan2: null,
    beiyongziduan3: null
  };
  proxy.resetForm("party1Ref");
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
  title.value = "添加相关方期望";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getParty1(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改相关方期望";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["party1Ref"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateParty1(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addParty1(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除相关方期望编号为"' + _ids + '"的数据项？').then(function() {
    return delParty1(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/party1/export', {
    ...queryParams.value
  }, `party1_${new Date().getTime()}.xlsx`)
}

getList();
</script>
