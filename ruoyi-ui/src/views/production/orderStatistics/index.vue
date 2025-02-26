<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统计年月" prop="yearAndMonth">
        <el-date-picker clearable v-model="queryParams.yearAndMonth" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择统计年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleModel">
        <el-input v-model="queryParams.vehicleModel" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input v-model="queryParams.quantity" placeholder="请输入数量" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="产能类型" prop="capacityType">
        <el-input v-model="queryParams.capacityType" placeholder="请输入产能类型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:orderStatistics:add']">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:orderStatistics:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:orderStatistics:remove']">删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button type="warning" plain icon="Download" @click="handleExport"-->
<!--                   v-hasPermi="['production:orderStatistics:export']">导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderStatisticsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="id" align="center" prop="id"/>-->
      <el-table-column label="统计年月" align="center" prop="yearAndMonth" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.yearAndMonth, '{y}-{m}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车型" align="center" prop="vehicleModel"/>
      <el-table-column label="数量" align="center" prop="quantity"/>
      <el-table-column label="产能类型" align="center" prop="capacityType"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:orderStatistics:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:orderStatistics:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改历史订单统计对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="orderStatisticsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="统计年月" prop="yearAndMonth">
          <el-date-picker clearable v-model="form.yearAndMonth" type="date" value-format="YYYY-MM-DD" placeholder="请选择统计年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleModel">
          <el-input v-model="form.vehicleModel" placeholder="请输入车型"/>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="产能类型" prop="capacityType">
          <el-input v-model="form.capacityType" placeholder="请输入产能类型"/>
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

<script setup name="OrderStatistics">
import {
  listOrderStatistics,
  getOrderStatistics,
  delOrderStatistics,
  addOrderStatistics,
  updateOrderStatistics
} from "@/api/production/orderStatistics";

const {proxy} = getCurrentInstance();

const orderStatisticsList = ref([]);
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
    yearAndMonth: null,
    vehicleModel: null,
    quantity: null,
    capacityType: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询历史订单统计列表 */
function getList() {
  loading.value = true;
  listOrderStatistics(queryParams.value).then(response => {
    orderStatisticsList.value = response.rows;
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
    yearAndMonth: null,
    vehicleModel: null,
    quantity: null,
    capacityType: null
  };
  proxy.resetForm("orderStatisticsRef");
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
  title.value = "添加历史订单统计";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOrderStatistics(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改历史订单统计";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["orderStatisticsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateOrderStatistics(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOrderStatistics(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除历史订单统计编号为"' + _ids + '"的数据项？').then(function () {
    return delOrderStatistics(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
// function handleExport() {
//   proxy.download('production/orderStatistics/export', {
//     ...queryParams.value
//   }, `orderStatistics_${new Date().getTime()}.xlsx`)
// }

getList();
</script>
