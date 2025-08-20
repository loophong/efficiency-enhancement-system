<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="序号" prop="serialNumber">
        <el-input v-model="queryParams.serialNumber" placeholder="请输入序号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="生产订单号" prop="productionOrderNumber" label-width="90px">
        <el-input v-model="queryParams.productionOrderNumber" placeholder="请输入生产订单号" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="合同号" prop="contractNumber">
        <el-input v-model="queryParams.contractNumber" placeholder="请输入合同号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleModel">
        <el-input v-model="queryParams.vehicleModel" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="门架" prop="mast">
        <el-input v-model="queryParams.mast" placeholder="请输入门架" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="项目说明" prop="projectDescription">
        <el-input v-model="queryParams.projectDescription" placeholder="请输入项目说明" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="车号" prop="vehicleNumber">
        <el-input v-model="queryParams.vehicleNumber" placeholder="请输入车号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="数量" prop="quantity">
        <el-input v-model="queryParams.quantity" placeholder="请输入数量" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="属具" prop="attachments">
        <el-input v-model="queryParams.attachments" placeholder="请输入属具" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="阀片" prop="valvePlate">
        <el-input v-model="queryParams.valvePlate" placeholder="请输入阀片" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="描述性配置信息" prop="descriptiveConfigurationInfo" label-width="110px">
        <el-input v-model="queryParams.descriptiveConfigurationInfo" placeholder="请输入描述性配置信息" clearable
                  @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单系统交货期" prop="systemDeliveryDate"  label-width="110px">
        <el-date-picker clearable v-model="queryParams.systemDeliveryDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择订单系统交货期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="网点名称" prop="branch">
        <el-input v-model="queryParams.branch" placeholder="请输入网点名称" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="接单日期" prop="orderDate">
        <el-date-picker clearable v-model="queryParams.orderDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择接单日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="上线日期" prop="onlineDate">
        <el-date-picker clearable v-model="queryParams.onlineDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择上线日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:dailyPlan:add']">
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:dailyPlan:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:dailyPlan:remove']">删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
                   v-hasPermi="['production:dailyPlan:export']">导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dailyPlanList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="主键" align="center" prop="id"/>-->
      <el-table-column label="序号" align="center" prop="serialNumber"/>
      <el-table-column label="生产订单号" align="center" prop="productionOrderNumber"/>
      <el-table-column label="合同号" align="center" prop="contractNumber"/>
      <el-table-column label="车号" align="center" prop="vehicleNumber"/>
      <el-table-column label="项目号" align="center" prop="vehicleModel"/>
      <el-table-column label="项目说明" align="center" prop="projectDescription"/>
      <el-table-column label="配装门架" align="center" prop="mast"/>
      <el-table-column label="数量" align="center" prop="quantity"/>
      <el-table-column label="属具" align="center" prop="attachments"/>
      <el-table-column label="阀片" align="center" prop="valvePlate"/>
      <el-table-column label="描述性配置信息" align="center" prop="descriptiveConfigurationInfo" width="200"/>
      <el-table-column label="计划交货交货期" align="center" prop="systemDeliveryDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="客户" align="center" prop="branch"/>
      <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上线日期" align="center" prop="onlineDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.onlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:dailyPlan:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:dailyPlan:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改日生产计划对话框 -->
    <el-dialog :title="title" v-model="open" width="700px" append-to-body>
      <el-form ref="dailyPlanRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="序号" prop="serialNumber">
          <el-input v-model="form.serialNumber" placeholder="请输入序号"/>
        </el-form-item>
        <el-form-item label="生产订单号" prop="productionOrderNumber">
          <el-input v-model="form.productionOrderNumber" placeholder="请输入生产订单号"/>
        </el-form-item>
        <el-form-item label="合同号" prop="contractNumber">
          <el-input v-model="form.contractNumber" placeholder="请输入合同号"/>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleModel">
          <el-input v-model="form.vehicleModel" placeholder="请输入车型"/>
        </el-form-item>
        <el-form-item label="门架" prop="mast">
          <el-input v-model="form.mast" placeholder="请输入门架"/>
        </el-form-item>
        <el-form-item label="项目说明" prop="projectDescription">
          <el-input v-model="form.projectDescription" placeholder="请输入项目说明"/>
        </el-form-item>
        <el-form-item label="车号" prop="vehicleNumber">
          <el-input v-model="form.vehicleNumber" placeholder="请输入车号"/>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="属具" prop="attachments">
          <el-input v-model="form.attachments" placeholder="请输入属具"/>
        </el-form-item>
        <el-form-item label="阀片" prop="valvePlate">
          <el-input v-model="form.valvePlate" placeholder="请输入阀片"/>
        </el-form-item>
        <el-form-item label="描述性配置信息" prop="descriptiveConfigurationInfo">
          <el-input v-model="form.descriptiveConfigurationInfo" placeholder="请输入描述性配置信息"/>
        </el-form-item>
        <el-form-item label="订单系统交货期" prop="systemDeliveryDate">
          <el-date-picker clearable
                          v-model="form.systemDeliveryDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择订单系统交货期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="网点名称" prop="branch">
          <el-input v-model="form.branch" placeholder="请输入网点名称"/>
        </el-form-item>
        <el-form-item label="接单日期" prop="orderDate">
          <el-date-picker clearable
                          v-model="form.orderDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择接单日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable
                          v-model="form.onlineDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
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

<script setup name="DailyPlan">
import {listDailyPlan, getDailyPlan, delDailyPlan, addDailyPlan, updateDailyPlan} from "@/api/production/dailyPlan";

const {proxy} = getCurrentInstance();

const dailyPlanList = ref([]);
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
    serialNumber: null,
    productionOrderNumber: null,
    contractNumber: null,
    vehicleModel: null,
    mast: null,
    projectDescription: null,
    vehicleNumber: null,
    quantity: null,
    attachments: null,
    valvePlate: null,
    descriptiveConfigurationInfo: null,
    systemDeliveryDate: null,
    branch: null,
    orderDate: null,
    onlineDate: null
  },
  rules: {
    serialNumber: [
      { required: true, message: "序号不能为空", trigger: "blur" }
    ],
    productionOrderNumber: [
      { required: true, message: "生产订单号不能为空", trigger: "blur" }
    ],
    contractNumber: [
      { required: true, message: "合同号不能为空", trigger: "blur" }
    ],
    vehicleModel: [
      { required: true, message: "车型不能为空", trigger: "blur" }
    ],
    mast: [
      { required: true, message: "门架不能为空", trigger: "blur" }
    ],
    projectDescription: [
      { required: true, message: "项目说明不能为空", trigger: "blur" }
    ],
    vehicleNumber: [
      { required: true, message: "车号不能为空", trigger: "blur" }
    ],
    quantity: [
      { required: true, message: "数量不能为空", trigger: "blur" }
    ],
    attachments: [
      { required: true, message: "属具不能为空", trigger: "blur" }
    ],
    valvePlate: [
      { required: true, message: "阀片不能为空", trigger: "blur" }
    ],
    descriptiveConfigurationInfo: [
      { required: true, message: "描述性配置信息不能为空", trigger: "blur" }
    ],
    systemDeliveryDate: [
      { required: true, message: "订单系统交货期不能为空", trigger: "blur" }
    ],
    branch: [
      { required: true, message: "网点名称不能为空", trigger: "blur" }
    ],
    orderDate: [
      { required: true, message: "接单日期不能为空", trigger: "blur" }
    ],
    onlineDate: [
      { required: true, message: "上线日期不能为空", trigger: "blur" }
    ]
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询日生产计划列表 */
function getList() {
  loading.value = true;
  listDailyPlan(queryParams.value).then(response => {
    dailyPlanList.value = response.rows;
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
    serialNumber: null,
    productionOrderNumber: null,
    contractNumber: null,
    vehicleModel: null,
    mast: null,
    projectDescription: null,
    vehicleNumber: null,
    quantity: null,
    attachments: null,
    valvePlate: null,
    descriptiveConfigurationInfo: null,
    systemDeliveryDate: null,
    branch: null,
    orderDate: null,
    onlineDate: null
  };
  proxy.resetForm("dailyPlanRef");
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
  title.value = "添加日生产计划";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getDailyPlan(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改日生产计划";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["dailyPlanRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDailyPlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDailyPlan(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除日生产计划编号为"' + _ids + '"的数据项？').then(function () {
    return delDailyPlan(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/dailyPlan/export', {
    ...queryParams.value
  }, `dailyPlan_${new Date().getTime()}.xlsx`)
}

getList();
</script>
