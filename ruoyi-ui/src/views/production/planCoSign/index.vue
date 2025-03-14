<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会签人编号" prop="reviewerId">
        <el-input v-model="queryParams.reviewerId" placeholder="请输入会签人编号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="会签人" prop="reviewerName">
        <el-input v-model="queryParams.reviewerName" placeholder="请输入会签人" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="会签状态" prop="reviewStatus">
        <el-select v-model="queryParams.reviewStatus" placeholder="请选择会签状态" clearable style="width: 100px">
          <el-option v-for="dict in production_plan_co_sign_status" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
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
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:planCoSign:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:planCoSign:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:planCoSign:remove']">删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['production:planCoSign:export']">导出</el-button>-->
      <!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planCoSignList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="会签编号" align="center" prop="id"/>
      <el-table-column label="会签人编号" align="center" prop="reviewerId"/>
      <el-table-column label="会签人" align="center" prop="reviewerName"/>
      <el-table-column label="会签状态" align="center" prop="reviewStatus">
        <template #default="scope">
          <dict-tag :options="production_plan_co_sign_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="上线日期" align="center" prop="onlineDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.onlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="评审意见" align="center" prop="comments"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:planCoSign:edit']">修改
          </el-button>
          <el-button link v-if="scope.row.reviewStatus ==='rejected' || scope.row.reviewStatus ==='created'"
                     type="primary" icon="Edit" @click="submitCoSign(scope.row)"
                     v-hasPermi="['production:planCoSign:handle']">发起会签
          </el-button>
          <el-button link v-if="scope.row.reviewStatus ==='pending'" type="primary" icon="Edit"
                     @click="handleCoSign(scope.row)"
                     v-hasPermi="['production:planCoSign:handle']">处理会签
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:planCoSign:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改计划会签对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="planCoSignRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会签人编号" prop="reviewerId">
          <el-input v-model="form.reviewerId" placeholder="请输入会签人编号"/>
        </el-form-item>
        <el-form-item label="会签人" prop="reviewerName">
          <el-input v-model="form.reviewerName" placeholder="请输入会签人"/>
        </el-form-item>
        <!--        <el-form-item label="会签状态" prop="reviewStatus">-->
        <!--          <el-radio-group v-model="form.reviewStatus">-->
        <!--            <el-radio v-for="dict in production_plan_co_sign_status" :key="dict.value" :label="dict.value">{{dict.label}}</el-radio>-->
        <!--          </el-radio-group>-->
        <!--          <el-radio-group v-model="form.reviewStatus">-->
        <!--            <el-radio value="created" size="large">已创建</el-radio>-->
        <!--            <el-radio value="pending" size="large">会签中</el-radio>-->
        <!--            <el-radio value="approved" size="large">通过</el-radio>-->
        <!--            <el-radio value="rejected" size="large">拒绝</el-radio>-->
        <!--          </el-radio-group>-->
        <!--        </el-form-item>-->
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable v-model="form.onlineDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评审意见" prop="comments">
          <el-input v-model="form.comments" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">创 建</el-button>
          <el-button type="primary" @click="createAndSubmitSign">创建并发起</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 处理会签对话框 -->
    <el-dialog title="计划会签" v-model="coSignVisible" width="1500px" append-to-body>
      <el-form ref="handlePlanCoSignRef" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="会签人编号" prop="reviewerId">-->
        <!--          <el-input v-model="form.reviewerId" placeholder="请输入会签人编号" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="会签人" prop="reviewerName">-->
        <!--          <el-input v-model="form.reviewerName" placeholder="请输入会签人" />-->
        <!--        </el-form-item>-->


        <el-table :data="dailyPlanList">
          <!--            <el-table-column label="序号" align="center" prop="serialNumber"/>-->
          <!--            <el-table-column label="生产订单号" align="center" prop="productionOrderNumber"/>-->
          <el-table-column label="合同号" align="center" prop="contractNumber"/>
          <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
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
        </el-table>


        <el-form-item label="会签状态" prop="reviewStatus">
          <el-radio-group v-model="form.reviewStatus">
            <el-radio value="approved" size="large">通过</el-radio>
            <el-radio value="rejected" size="large">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable v-model="form.onlineDate" type="date" value-format="YYYY-MM-DD" disabled
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="评审意见" prop="comments">
          <el-input v-model="form.comments" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitCoSignForm">确 定</el-button>
          <el-button @click="cancelSign">取 消</el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup name="PlanCoSign">
import {
  listPlanCoSign,
  getPlanCoSign,
  delPlanCoSign,
  addPlanCoSign,
  updatePlanCoSign,
  getCoSignAndDailyPlan
} from "@/api/production/planCoSign";

const {proxy} = getCurrentInstance();
const {production_plan_co_sign_status} = proxy.useDict('production_plan_co_sign_status');

const planCoSignList = ref([]);
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
    reviewerId: null,
    reviewerName: null,
    reviewStatus: null,
    onlineDate: null,
    comments: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询计划会签列表 */
function getList() {
  loading.value = true;
  listPlanCoSign(queryParams.value).then(response => {
    planCoSignList.value = response.rows;
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
    reviewerId: null,
    reviewerName: null,
    reviewStatus: null,
    onlineDate: null,
    comments: null
  };
  proxy.resetForm("planCoSignRef");
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
  title.value = "添加计划会签";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getPlanCoSign(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改计划会签";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["planCoSignRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        form.value.reviewStatus = "created";
        updatePlanCoSign(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.reviewStatus = "created";
        addPlanCoSign(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 创建 、修改会签，并发送会签 */
function createAndSubmitSign() {
  proxy.$refs["planCoSignRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        form.value.reviewStatus = "pending";
        updatePlanCoSign(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.reviewStatus = "pending";
        addPlanCoSign(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除计划会签编号为"' + _ids + '"的数据项？').then(function () {
    return delPlanCoSign(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
// function handleExport() {
//   proxy.download('production/planCoSign/export', {
//     ...queryParams.value
//   }, `planCoSign_${new Date().getTime()}.xlsx`)
// }

getList();


const coSignVisible = ref(false);
const dailyPlanList = ref([]);

/** 修改按钮操作 */
function handleCoSign(row) {
  reset();
  const _id = row.id || ids.value
  // getPlanCoSign(_id).then(response => {
  //   form.value = response.data;
  //   coSignVisible.value = true;
  // });
  getCoSignAndDailyPlan(_id).then(response => {
    console.log(response)
    form.value = response.data.planCoSign;
    dailyPlanList.value = response.data.dailyPlanList;
    form.value.reviewStatus = "approved"; // 设置默认值为 "approved"
    coSignVisible.value = true;
  });
}

/** 提交按钮 */
function submitCoSignForm() {
  proxy.$refs["handlePlanCoSignRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        // form.value.reviewStatus = "created";
        updatePlanCoSign(form.value).then(response => {
          proxy.$modal.msgSuccess("会签处理成功");
          coSignVisible.value = false;
          getList();
        });
      }
    }
  });
}

/** 会签审批确认按钮 */
// function submitCoSignForm() {
//   proxy.$refs["handlePlanCoSignRef"].validate(valid => {
//     if (valid) {
//       if (form.value.id != null) {
//         // form.value.reviewStatus = "created";
//         updatePlanCoSign(form.value).then(response => {
//           proxy.$modal.msgSuccess("会签处理成功");
//           coSignVisible.value = false;
//           getList();
//         });
//       }
//     }
//   });
// }

// 取消按钮
function cancelSign() {
  coSignVisible.value = false;
  reset();
}

function submitCoSign(row) {
  // 提示用户是否会签
  proxy.$modal.confirm('是否确认发起会签？').then(function () {
    // 用户点击确定，执行会签操作
    let result = {
      'id': row.id,
      'reviewStatus': "pending"
    }
    updatePlanCoSign(result).then(response => {
      proxy.$modal.msgSuccess("发起会签成功");
      getList();
    });
  }).catch(function () {
    // 用户点击取消，取消会签操作

  });

}
</script>

