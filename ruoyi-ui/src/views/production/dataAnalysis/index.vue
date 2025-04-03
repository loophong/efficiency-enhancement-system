<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="网点名称" prop="branch">
        <el-input v-model="queryParams.branch" placeholder="请输入网点名称" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="合同号" prop="contractNumber">
        <el-input v-model="queryParams.contractNumber" placeholder="请输入合同号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="接单日期" prop="orderDate">
        <el-date-picker clearable v-model="queryParams.orderDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择接单日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleModel">
        <el-input v-model="queryParams.vehicleModel" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
<!--      <el-form-item label="数量" prop="quantity">-->
<!--        <el-input v-model="queryParams.quantity" placeholder="请输入数量" clearable @keyup.enter="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="数量" prop="quantity">
        <el-input-number
            v-model="queryParams.quantity"
            placeholder="请输入数量"
            clearable
            @keyup.enter="handleQuery"
            :min="0"/>
      </el-form-item>
<!--      <el-form-item label="阀片" prop="valvePlate">-->
<!--        <el-input v-model="queryParams.valvePlate" placeholder="请输入阀片" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="货叉" prop="forklift">-->
<!--        <el-input v-model="queryParams.forklift" placeholder="请输入货叉" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="门架" prop="mast">-->
<!--        <el-input v-model="queryParams.mast" placeholder="请输入门架" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="空滤" prop="airFilter">-->
<!--        <el-input v-model="queryParams.airFilter" placeholder="请输入空滤" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="属具" prop="attachments">-->
<!--        <el-input v-model="queryParams.attachments" placeholder="请输入属具" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="轮胎" prop="tires">-->
<!--        <el-input v-model="queryParams.tires" placeholder="请输入轮胎" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="配置" prop="configuration">-->
<!--        <el-input v-model="queryParams.configuration" placeholder="请输入配置" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="车号" prop="vehicleNumber">-->
<!--        <el-input v-model="queryParams.vehicleNumber" placeholder="请输入车号" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
      <el-form-item label="备注信息" prop="remarks">
        <el-input v-model="queryParams.remarks" placeholder="请输入备注信息" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
<!--      <el-form-item label="交货形式" prop="deliveryForm">-->
<!--        <el-input v-model="queryParams.deliveryForm" placeholder="请输入交货形式" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="交货地点" prop="deliveryLocation">-->
<!--        <el-input v-model="queryParams.deliveryLocation" placeholder="请输入交货地点" clearable-->
<!--                  @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="联系人姓名" prop="contactPerson">-->
<!--        <el-input v-model="queryParams.contactPerson" placeholder="请输入联系人姓名" clearable-->
<!--                  @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="联系电话" prop="phoneNumber">-->
<!--        <el-input v-model="queryParams.phoneNumber" placeholder="请输入联系电话" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
      <el-form-item label="订单系统交货期" prop="systemDeliveryDate" label-width="120px">
        <el-date-picker clearable v-model="queryParams.systemDeliveryDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择订单系统交货期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="生产回复完工日期" prop="productionCompletionDate" label-width="130px">
        <el-date-picker clearable v-model="queryParams.productionCompletionDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择生产回复完工日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="采购回复到货时间" prop="procurementArrivalDate" label-width="130px">
        <el-date-picker clearable v-model="queryParams.procurementArrivalDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择采购回复到货时间">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="生产周期" prop="productionCycle">-->
<!--        <el-input v-model="queryParams.productionCycle" placeholder="请输入生产周期" clearable-->
<!--                  @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
      <el-form-item label="生产周期" prop="productionCycle">
        <el-input-number
            v-model="queryParams.productionCycle"
            placeholder="请输入生产周期"
            clearable
            @keyup.enter="handleQuery"
            :min="0"/>
      </el-form-item>
      <el-form-item label="最晚上线日期" prop="latestOnlineDate" label-width="100px">
        <el-date-picker clearable v-model="queryParams.latestOnlineDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择最晚上线日期">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="是否超期" prop="isOverdue">-->
<!--        <el-input v-model="queryParams.isOverdue" placeholder="请输入是否超期" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否加急" prop="isUrgent">-->
<!--        <el-input v-model="queryParams.isUrgent" placeholder="请输入是否加急" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否排产" prop="isScheduling">-->
<!--        <el-input v-model="queryParams.isScheduling" placeholder="请输入是否排产" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
<!--        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:scheduling:add']">新增-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"-->
<!--                   v-hasPermi="['production:scheduling:edit']">修改-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"-->
<!--                   v-hasPermi="['production:scheduling:remove']">删除-->
<!--        </el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
        <el-button type="warning" plain icon="Download" @click="handleExport"
                   v-hasPermi="['production:scheduling:export']">导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schedulingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="主键" align="center" prop="id"/>-->
      <el-table-column label="网点名称" align="center" prop="branch"/>
      <el-table-column label="合同号" align="center" prop="contractNumber" width="110"/>
      <el-table-column label="订单号" align="center" prop="orderNumber" width="110"/>
      <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车型" align="center" prop="vehicleModel" width="160"/>
      <el-table-column label="数量" align="center" prop="quantity"/>
<!--      <el-table-column label="阀片" align="center" prop="valvePlate"/>-->
<!--      <el-table-column label="货叉" align="center" prop="forklift"/>-->
<!--      <el-table-column label="门架" align="center" prop="mast"/>-->
<!--      <el-table-column label="空滤" align="center" prop="airFilter"/>-->
<!--      <el-table-column label="属具" align="center" prop="attachments"/>-->
<!--      <el-table-column label="轮胎" align="center" prop="tires"/>-->
<!--      <el-table-column label="配置" align="center" prop="configuration"/>-->
<!--      <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
      <el-table-column label="备注信息" align="center" prop="remarks" width="200"/>
<!--      <el-table-column label="交货形式" align="center" prop="deliveryForm"/>-->
<!--      <el-table-column label="交货地点" align="center" prop="deliveryLocation"/>-->
<!--      <el-table-column label="联系人姓名" align="center" prop="contactPerson" width="100"/>-->
<!--      <el-table-column label="联系电话" align="center" prop="phoneNumber"/>-->
      <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产回复完工日期" align="center" prop="productionCompletionDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.productionCompletionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产周期" align="center" prop="productionCycle"/>
      <el-table-column label="产能型号" align="center" prop="capacityType"/>
      <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="是否超期" align="center" prop="isOverdue"/>-->
<!--      <el-table-column label="是否加急" align="center" prop="isUrgent"/>-->
<!--      <el-table-column label="是否排产" align="center" prop="isScheduling"/>-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template #default="scope">-->
<!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"-->
<!--                     v-hasPermi="['production:scheduling:edit']">修改-->
<!--          </el-button>-->
<!--          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"-->
<!--                     v-hasPermi="['production:scheduling:remove']">删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="schedulingRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="网点名称" prop="branch">
          <el-input v-model="form.branch" placeholder="请输入网点名称"/>
        </el-form-item>
        <el-form-item label="合同号" prop="contractNumber">
          <el-input v-model="form.contractNumber" placeholder="请输入合同号"/>
        </el-form-item>
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="接单日期" prop="orderDate">
          <el-date-picker clearable v-model="form.orderDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择接单日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleModel">
          <el-input v-model="form.vehicleModel" placeholder="请输入车型"/>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量"/>
        </el-form-item>
        <el-form-item label="阀片" prop="valvePlate">
          <el-input v-model="form.valvePlate" placeholder="请输入阀片"/>
        </el-form-item>
        <el-form-item label="货叉" prop="forklift">
          <el-input v-model="form.forklift" placeholder="请输入货叉"/>
        </el-form-item>
        <el-form-item label="门架" prop="mast">
          <el-input v-model="form.mast" placeholder="请输入门架"/>
        </el-form-item>
        <el-form-item label="空滤" prop="airFilter">
          <el-input v-model="form.airFilter" placeholder="请输入空滤"/>
        </el-form-item>
        <el-form-item label="属具" prop="attachments">
          <el-input v-model="form.attachments" placeholder="请输入属具"/>
        </el-form-item>
        <el-form-item label="轮胎" prop="tires">
          <el-input v-model="form.tires" placeholder="请输入轮胎"/>
        </el-form-item>
        <el-form-item label="配置" prop="configuration">
          <el-input v-model="form.configuration" placeholder="请输入配置"/>
        </el-form-item>
        <el-form-item label="车号" prop="vehicleNumber">
          <el-input v-model="form.vehicleNumber" placeholder="请输入车号"/>
        </el-form-item>
        <el-form-item label="备注信息" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注信息"/>
        </el-form-item>
        <el-form-item label="交货形式" prop="deliveryForm">
          <el-input v-model="form.deliveryForm" placeholder="请输入交货形式"/>
        </el-form-item>
        <el-form-item label="交货地点" prop="deliveryLocation">
          <el-input v-model="form.deliveryLocation" placeholder="请输入交货地点"/>
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人姓名"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="订单系统交货期" prop="systemDeliveryDate">
          <el-date-picker clearable v-model="form.systemDeliveryDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择订单系统交货期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产回复完工日期" prop="productionCompletionDate">
          <el-date-picker clearable v-model="form.productionCompletionDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择生产回复完工日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采购回复到货时间" prop="procurementArrivalDate">
          <el-date-picker clearable v-model="form.procurementArrivalDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择采购回复到货时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产周期" prop="productionCycle">
          <el-input v-model="form.productionCycle" placeholder="请输入生产周期"/>
        </el-form-item>
        <el-form-item label="最晚上线日期" prop="latestOnlineDate">
          <el-date-picker clearable v-model="form.latestOnlineDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择最晚上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="是否超期" prop="isOverdue">
          <el-input v-model="form.isOverdue" placeholder="请输入是否超期"/>
        </el-form-item>
        <el-form-item label="是否加急" prop="isUrgent">
          <el-input v-model="form.isUrgent" placeholder="请输入是否加急"/>
        </el-form-item>
        <el-form-item label="是否排产" prop="isScheduling">
          <el-input v-model="form.isScheduling" placeholder="请输入是否排产"/>
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

<script setup name="Scheduling">
import {
  dataAnalysis,
  listScheduling,
  getScheduling,
  delScheduling,
  addScheduling,
  updateScheduling
} from "@/api/production/orderScheduling";

const {proxy} = getCurrentInstance();

const schedulingList = ref([]);
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
    branch: null,
    contractNumber: null,
    orderNumber: null,
    orderDate: null,
    vehicleModel: null,
    quantity: null,
    valvePlate: null,
    forklift: null,
    mast: null,
    airFilter: null,
    attachments: null,
    tires: null,
    configuration: null,
    vehicleNumber: null,
    remarks: null,
    deliveryForm: null,
    deliveryLocation: null,
    contactPerson: null,
    phoneNumber: null,
    systemDeliveryDate: null,
    productionCompletionDate: null,
    procurementArrivalDate: null,
    productionCycle: null,
    capacityType: null,
    latestOnlineDate: null,
    isOverdue: null,
    isUrgent: null,
    isScheduling: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询订单信息列表 */
function getList() {
  loading.value = true;
  dataAnalysis(queryParams.value).then(response => {
    schedulingList.value = response.rows;
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
    branch: null,
    contractNumber: null,
    orderNumber: null,
    orderDate: null,
    vehicleModel: null,
    quantity: null,
    valvePlate: null,
    forklift: null,
    mast: null,
    airFilter: null,
    attachments: null,
    tires: null,
    configuration: null,
    vehicleNumber: null,
    remarks: null,
    deliveryForm: null,
    deliveryLocation: null,
    contactPerson: null,
    phoneNumber: null,
    systemDeliveryDate: null,
    productionCompletionDate: null,
    procurementArrivalDate: null,
    productionCycle: null,
    capacityType: null,
    latestOnlineDate: null,
    isOverdue: null,
    isUrgent: null,
    isScheduling: null
  };
  proxy.resetForm("schedulingRef");
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
  title.value = "添加订单信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getScheduling(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改订单信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["schedulingRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateScheduling(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addScheduling(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除订单信息编号为"' + _ids + '"的数据项？').then(function () {
    return delScheduling(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/scheduling/exportDataAnalysis', {
    ...queryParams.value
  }, `数据分析表_${new Date().getTime()}.xlsx`)
}

getList();
</script>
