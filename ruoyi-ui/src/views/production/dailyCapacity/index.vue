<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="排产时间" prop="productionDate">
        <el-date-picker clearable
                        v-model="queryParams.productionDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择排产时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际的产量" prop="productionQuantity">
        <el-input
            v-model="queryParams.productionQuantity"
            placeholder="请输入实际的产量"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设置的产量" prop="quantitySettings">
        <el-input
            v-model="queryParams.quantitySettings"
            placeholder="请输入设置的产量"
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
            v-hasPermi="['production:dailyCapacity:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['production:dailyCapacity:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['production:dailyCapacity:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['production:dailyCapacity:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dailyCapacityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="排产时间" align="center" prop="productionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.productionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="产能车型" align="center" prop="capacityType" />
      <el-table-column label="实际的产量" align="center" prop="productionQuantity" />
      <el-table-column label="设置的产量" align="center" prop="quantitySettings" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['production:dailyCapacity:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['production:dailyCapacity:remove']">删除</el-button>
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

    <!-- 添加或修改每日使用的产能对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="dailyCapacityRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="排产时间" prop="productionDate">
          <el-date-picker clearable
                          v-model="form.productionDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择排产时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际的产量" prop="productionQuantity">
          <el-input v-model="form.productionQuantity" placeholder="请输入实际的产量" />
        </el-form-item>
        <el-form-item label="设置的产量" prop="quantitySettings">
          <el-input v-model="form.quantitySettings" placeholder="请输入设置的产量" />
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

<script setup name="DailyCapacity">
import { listDailyCapacity, getDailyCapacity, delDailyCapacity, addDailyCapacity, updateDailyCapacity } from "@/api/production/dailyCapacity";

const { proxy } = getCurrentInstance();

const dailyCapacityList = ref([]);
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
    productionDate: null,
    capacityType: null,
    productionQuantity: null,
    quantitySettings: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询每日使用的产能列表 */
function getList() {
  loading.value = true;
  listDailyCapacity(queryParams.value).then(response => {
    dailyCapacityList.value = response.rows;
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
    productionDate: null,
    capacityType: null,
    productionQuantity: null,
    quantitySettings: null
  };
  proxy.resetForm("dailyCapacityRef");
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
  title.value = "添加每日使用的产能";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getDailyCapacity(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改每日使用的产能";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["dailyCapacityRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDailyCapacity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDailyCapacity(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除每日使用的产能编号为"' + _ids + '"的数据项？').then(function() {
    return delDailyCapacity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/dailyCapacity/export', {
    ...queryParams.value
  }, `dailyCapacity_${new Date().getTime()}.xlsx`)
}

getList();
</script>
