<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNumber">
        <el-input
            v-model="queryParams.orderNumber"
            placeholder="请输入订单号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上线日期" prop="onlineDate">
        <el-date-picker clearable
                        v-model="queryParams.onlineDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择上线日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="故障原因" prop="faultReason">
        <el-select v-model="queryParams.faultReason" placeholder="请选择故障原因" clearable>
          <el-option
              v-for="dict in production_fault_reason"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="责任科室" prop="responsibleDepartment">
        <el-input
            v-model="queryParams.responsibleDepartment"
            placeholder="请输入责任科室"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否为重大故障" prop="isMajorFault">
        <el-select v-model="queryParams.isMajorFault" placeholder="请选择是否为重大故障" clearable>
          <el-option
              v-for="dict in production_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="approvalStatus">
        <el-input
            v-model="queryParams.approvalStatus"
            placeholder="请输入处理状态"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="填报时间" prop="uploadDate">
        <el-date-picker clearable
                        v-model="queryParams.uploadDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择填报时间">
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
            v-hasPermi="['production:cases:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['production:cases:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['production:cases:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['production:cases:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="casesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键" align="center" prop="id"/>
      <el-table-column label="订单号" align="center" prop="orderNumber"/>
      <el-table-column label="上线日期" align="center" prop="onlineDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.onlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="故障原因" align="center" prop="faultReason">
        <template #default="scope">
          <dict-tag :options="production_fault_reason" :value="scope.row.faultReason"/>
        </template>
      </el-table-column>
      <el-table-column label="故障说明" align="center" prop="faultDescription"/>
      <el-table-column label="责任科室" align="center" prop="responsibleDepartment"/>
      <el-table-column label="解决方案" align="center" prop="solution"/>
      <el-table-column label="是否为重大故障" align="center" prop="isMajorFault">
        <template #default="scope">
          <dict-tag :options="production_yes_no" :value="scope.row.isMajorFault"/>
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="approvalStatus"/>
      <el-table-column label="填报时间" align="center" prop="uploadDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:cases:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:cases:remove']">删除
          </el-button>
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

    <!-- 添加或修改特殊情况对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="casesRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable
                          v-model="form.onlineDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障原因" prop="faultReason">
          <el-select v-model="form.faultReason" placeholder="请选择故障原因">
            <el-option
                v-for="dict in production_fault_reason"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障说明" prop="faultDescription">
          <el-input v-model="form.faultDescription" placeholder="请输入故障说明"/>
        </el-form-item>
        <el-form-item label="责任科室" prop="responsibleDepartment">
          <el-input v-model="form.responsibleDepartment" placeholder="请输入责任科室"/>
        </el-form-item>
        <el-form-item label="是否为重大故障" prop="isMajorFault">
          <el-select v-model="form.isMajorFault" placeholder="请选择是否为重大故障">
            <el-option
                v-for="dict in production_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="处理状态" prop="approvalStatus">
          <el-input v-model="form.approvalStatus" placeholder="请输入处理状态"/>
        </el-form-item>
        <el-form-item label="填报时间" prop="uploadDate">
          <el-date-picker clearable
                          v-model="form.uploadDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择填报时间">
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

<script setup name="Cases">
import {listCases, getCases, delCases, addCases, updateCases} from "@/api/production/special";

const {proxy} = getCurrentInstance();
const {production_fault_reason, production_yes_no} = proxy.useDict('production_fault_reason', 'production_yes_no');

const casesList = ref([]);
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
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    responsibleDepartment: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询特殊情况列表 */
function getList() {
  loading.value = true;
  listCases(queryParams.value).then(response => {
    casesList.value = response.rows;
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
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    faultDescription: null,
    responsibleDepartment: null,
    solution: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null
  };
  proxy.resetForm("casesRef");
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
  title.value = "添加特殊情况";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCases(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改特殊情况";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["casesRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCases(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCases(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除特殊情况编号为"' + _ids + '"的数据项？').then(function () {
    return delCases(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/cases/export', {
    ...queryParams.value
  }, `cases_${new Date().getTime()}.xlsx`)
}

getList();
</script>
