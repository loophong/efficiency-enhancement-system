<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input
            v-model="queryParams.supplierCode"
            placeholder="请输入供应商代码"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
            v-model="queryParams.supplierName"
            placeholder="请输入供应商名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开始时间" prop="happenTime">
        <el-date-picker clearable
          v-model="queryParams.happenTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable
          v-model="queryParams.endTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择结束时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="重要度" prop="weight">
        <el-input
          v-model="queryParams.weight"
          placeholder="请输入重要度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
      
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
            v-hasPermi="['supplier:evaluate:add']"
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
            v-hasPermi="['supplier:evaluate:edit']"
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
            v-hasPermi="['supplier:evaluate:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['supplier:evaluate:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain @click="handleCalculate" v-hasPermi="['supplier:evaluate:calculate']">
          计算
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="evaluateList" @selection-change="handleSelectionChange" width="50">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" width="75" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" width="120"/>
      <el-table-column label="质量事故" align="center" prop="qualityIncident" width="50"/>
      <el-table-column label="一次交检合格率" align="center" prop="firstInspectionPassrate" width="70"/>
      <el-table-column label="零公里故障指标完成率" align="center" prop="zeroKilometerFailurerate" width="50"/>
      <el-table-column label="质量通知单发生" align="center" prop="qualityNotificationOrderrate" width="50"/>
      <el-table-column label="反馈单回函及时率" align="center" prop="feedbackOrderletterTimeliness" width="50"/>
      <el-table-column label="保修期内市场售后返修率" align="center" prop="warrantyperiodRepairrate" width="50"/>
      <el-table-column label="三包配件发货及时率" align="center" prop="threepackageComponentRepairrate" width="50"/>
      <el-table-column label="二方审核得分" align="center" prop="secondpartyAuditscore" width="50"/>
      <el-table-column label="自检报告准确率" align="center" prop="selfinspectionReportaccuracy" width="50"/>
      <el-table-column label="擅自变更产品材质参数尺寸" align="center" prop="productmaterialParametersizeChange" width="50"/>
      <el-table-column label="重要度" align="center" prop="weight" width="50"/>
      <el-table-column label="经营风险" align="center" prop="businessRisk" width="50"/>
      <el-table-column label="供货保障" align="center" prop="supplyGuarantee" width="50"/>
      <el-table-column label="价格竞争力" align="center" prop="priceCompetitiveness" width="50"/>
      <el-table-column label="降本支持" align="center" prop="costreductionSupport" width="50"/>
      <el-table-column label="服务与协作" align="center" prop="serviceAndCoordination" width="50"/>
      <el-table-column label="价格诚信" align="center" prop="priceIntegrity" width="50"/>
      <el-table-column label="付款限制条件" align="center" prop="paymentRestrictionconditions" width="50"/>
      <el-table-column label="新产品研发配合程度" align="center" prop="newproductDevelopmentCooperationdegree" width="50"/>
      <el-table-column label="产品技术问题整改及时性" align="center" prop="producttechnologyproblemImprovementtimeliness" width="50"/>
      <el-table-column label="总分" align="center" prop="totalScore" width="70"/>
      <el-table-column label="开始时间" align="center" prop="happenTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.happenTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="备2" align="center" prop="2" />
      <el-table-column label="备3" align="center" prop="3" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.3, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="备4" align="center" prop="4" /> -->
      <!-- <el-table-column label="入库标识符" align="center" prop="storageFlag" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['supplier:evaluate:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['supplier:evaluate:remove']">删除
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

    <!-- 添加或修改评分表对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="evaluateRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码"/>
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称"/>
        </el-form-item>
        <el-form-item label="质量事故" prop="qualityIncident">
          <el-input v-model="form.qualityIncident" placeholder="请输入质量事故"/>
        </el-form-item>
        <el-form-item label="一次交检合格率" prop="firstInspectionPassrate">
          <el-input v-model="form.firstInspectionPassrate" placeholder="请输入一次交检合格率"/>
        </el-form-item>
        <el-form-item label="零公里故障指标完成率" prop="zeroKilometerFailurerate">
          <el-input v-model="form.zeroKilometerFailurerate" placeholder="请输入零公里故障指标完成率"/>
        </el-form-item>
        <el-form-item label="质量通知单发生" prop="qualityNotificationOrderrate">
          <el-input v-model="form.qualityNotificationOrderrate" placeholder="请输入质量通知单发生"/>
        </el-form-item>
        <el-form-item label="反馈单回函及时率" prop="feedbackOrderletterTimeliness">
          <el-input v-model="form.feedbackOrderletterTimeliness" placeholder="请输入反馈单回函及时率"/>
        </el-form-item>
        <el-form-item label="保修期内市场售后返修率" prop="warrantyperiodRepairrate">
          <el-input v-model="form.warrantyperiodRepairrate" placeholder="请输入保修期内市场售后返修率"/>
        </el-form-item>
        <el-form-item label="三包配件发货及时率" prop="threepackageComponentRepairrate">
          <el-input v-model="form.threepackageComponentRepairrate" placeholder="请输入三包配件发货及时率"/>
        </el-form-item>
        <el-form-item label="二方审核得分" prop="secondpartyAuditscore">
          <el-input v-model="form.secondpartyAuditscore" placeholder="请输入二方审核得分"/>
        </el-form-item>
        <el-form-item label="自检报告准确率" prop="selfinspectionReportaccuracy">
          <el-input v-model="form.selfinspectionReportaccuracy" placeholder="请输入自检报告准确率"/>
        </el-form-item>
        <el-form-item label="擅自变更产品材质参数尺寸" prop="productmaterialParametersizeChange">
          <el-input v-model="form.productmaterialParametersizeChange" placeholder="请输入擅自变更产品材质参数尺寸"/>
        </el-form-item>
        <el-form-item label="重要度" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重要度"/>
        </el-form-item>
        <el-form-item label="经营风险" prop="businessRisk">
          <el-input v-model="form.businessRisk" placeholder="请输入经营风险"/>
        </el-form-item>
        <el-form-item label="供货保障" prop="supplyGuarantee">
          <el-input v-model="form.supplyGuarantee" placeholder="请输入供货保障"/>
        </el-form-item>
        <el-form-item label="价格竞争力" prop="priceCompetitiveness">
          <el-input v-model="form.priceCompetitiveness" placeholder="请输入价格竞争力"/>
        </el-form-item>
        <el-form-item label="降本支持" prop="costreductionSupport">
          <el-input v-model="form.costreductionSupport" placeholder="请输入降本支持"/>
        </el-form-item>
        <el-form-item label="服务与协作" prop="serviceAndCoordination">
          <el-input v-model="form.serviceAndCoordination" placeholder="请输入服务与协作"/>
        </el-form-item>
        <el-form-item label="价格诚信" prop="priceIntegrity">
          <el-input v-model="form.priceIntegrity" placeholder="请输入价格诚信"/>
        </el-form-item>
        <el-form-item label="付款限制条件" prop="paymentRestrictionconditions">
          <el-input v-model="form.paymentRestrictionconditions" placeholder="请输入付款限制条件"/>
        </el-form-item>
        <el-form-item label="新产品研发配合程度" prop="newproductDevelopmentCooperationdegree">
          <el-input v-model="form.newproductDevelopmentCooperationdegree" placeholder="请输入新产品研发配合程度"/>
        </el-form-item>
        <el-form-item label="产品技术问题整改及时性" prop="producttechnologyproblemImprovementtimeliness">
          <el-input v-model="form.producttechnologyproblemImprovementtimeliness" placeholder="请输入产品技术问题整改及时性"/>
        </el-form-item>
        <el-form-item label="总分" prop="totalScore">
          <el-input v-model="form.totalScore" placeholder="请输入总分"/>
        </el-form-item>
        <el-form-item label="开始时间" prop="happenTime">
          <el-date-picker clearable
                          v-model="form.happenTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="备2" prop="2">
          <el-input v-model="form.2" placeholder="请输入备2" />
        </el-form-item>
        <el-form-item label="备3" prop="3">
          <el-date-picker clearable
            v-model="form.3"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择备3">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备4" prop="4">
          <el-input v-model="form.4" placeholder="请输入备4" />
        </el-form-item> -->
        <!-- <el-form-item label="入库标识符" prop="storageFlag">
          <el-input v-model="form.storageFlag" placeholder="请输入入库标识符"/>
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 计算得分对话框 -->
    <el-dialog title="计算得分" v-model="calculateVisible" width="500px" append-to-body>
      <el-form ref="evaluateRef" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="开始时间" prop="happenTime">
          <el-date-picker clearable
                          v-model="form.happenTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
                          v-model="form.endTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="calculate">确 定</el-button>
          <el-button @click="cancelCalculate">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Evaluate">
import {listEvaluate, getEvaluate, delEvaluate, addEvaluate, updateEvaluate,calculateScore} from "@/api/supplier/evaluate";

const {proxy} = getCurrentInstance();

const evaluateList = ref([]);
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
    supplierCode: null,
    supplierName: null,
    qualityIncident: null,
    firstInspectionPassrate: null,
    zeroKilometerFailurerate: null,
    qualityNotificationOrderrate: null,
    feedbackOrderletterTimeliness: null,
    warrantyperiodRepairrate: null,
    threepackageComponentRepairrate: null,
    secondpartyAuditscore: null,
    selfinspectionReportaccuracy: null,
    productmaterialParametersizeChange: null,
    weight: null,
    businessRisk: null,
    supplyGuarantee: null,
    priceCompetitiveness: null,
    costreductionSupport: null,
    serviceAndCoordination: null,
    priceIntegrity: null,
    paymentRestrictionconditions: null,
    newproductDevelopmentCooperationdegree: null,
    producttechnologyproblemImprovementtimeliness: null,
    totalScore: null,
    happenTime: null,
    endTime: null,
    2: null,
    3: null,
    4: null,
    storageFlag: null,
    orderByColumn: 'end_time',  // 添加这行：按结束时间排序
    isAsc: 'desc'  // 添加这行：降序排列，结束时间晚的在前
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询评分表列表 */
function getList() {
  loading.value = true;
  listEvaluate(queryParams.value).then(response => {
    evaluateList.value = response.rows;
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
    supplierCode: null,
    supplierName: null,
    qualityIncident: null,
    firstInspectionPassrate: null,
    zeroKilometerFailurerate: null,
    qualityNotificationOrderrate: null,
    feedbackOrderletterTimeliness: null,
    warrantyperiodRepairrate: null,
    threepackageComponentRepairrate: null,
    secondpartyAuditscore: null,
    selfinspectionReportaccuracy: null,
    productmaterialParametersizeChange: null,
    weight: null,
    businessRisk: null,
    supplyGuarantee: null,
    priceCompetitiveness: null,
    costreductionSupport: null,
    serviceAndCoordination: null,
    priceIntegrity: null,
    paymentRestrictionconditions: null,
    newproductDevelopmentCooperationdegree: null,
    producttechnologyproblemImprovementtimeliness: null,
    totalScore: null,
    happenTime: null,
    endTime: null,
    2: null,
    3: null,
    4: null,
    storageFlag: null
  };
  proxy.resetForm("evaluateRef");
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
  title.value = "添加评分表";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getEvaluate(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改评分表";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["evaluateRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateEvaluate(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEvaluate(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除？').then(function () {
    return delEvaluate(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/evaluate/export', {
    ...queryParams.value
  }, `evaluate_${new Date().getTime()}.xlsx`)
}

getList();

const calculateVisible = ref(false);

function handleCalculate() {
  calculateVisible.value = true;
}

function calculate() {
  calculateScore(form.value).then(response => {
    console.log(response);
    proxy.$modal.msgSuccess("计算成功"); // 弹窗提醒
    calculateVisible.value = false; // 关闭对话框
  }).catch(error => {
    proxy.$modal.msgError("计算失败"); // 弹窗提醒
  });
}
</script>
