<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="故障类型名称" prop="analysisName">
        <el-select v-model="queryParams.analysisName" placeholder="请选择故障类型名称" clearable style="width: 180px;">
          <el-option v-for="dict in device_fault_analysis" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="维修时间" prop="analysisTime">
        <el-input v-model="queryParams.analysisTime" placeholder="请输入维修时间" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="原因分析" prop="analysisReason">
        <el-input v-model="queryParams.analysisReason" placeholder="请输入原因分析" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="日期选择" prop="yearAndMonth">
        <el-date-picker v-model="queryParams.yearAndMonth" value-format="YYYY-MM" type="month"></el-date-picker>
      </el-form-item>
      <el-form-item label="记录日期" style="width: 308px">
        <el-date-picker v-model="daterangeAnalysisRecordTime" value-format="YYYY-MM-DD" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['maintenanceTable:analysis:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['maintenanceTable:analysis:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['maintenanceTable:analysis:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['maintenanceTable:analysis:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" row-key="maintenanceId" @selection-change="handleSelectionChange"
      border stripe>
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="故障类型名称" align="center" prop="faultTypeName" width="180" />
      <el-table-column label="次数" align="center" prop="count" width="140" />
      <el-table-column label="维修时间" align="center" prop="faultDuration" width="140" />
      <el-table-column label="故障现象" align="center" prop="faultPhenomenon" />
      <el-table-column label="原因分析" align="center" prop="maintenanceAnalysis" />
      <el-table-column label="记录日期" align="center" prop="analysisRecordTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.analysisRecordTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:analysis:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['maintenanceTable:analysis:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" /> -->

    <!-- 添加或修改设备故障分析对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="analysisRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="故障类型名称" prop="analysisName">
          <el-select v-model="form.analysisName" placeholder="请选择故障类型名称">
            <el-option v-for="dict in device_fault_analysis" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="维修时间" prop="analysisTime">
          <el-input v-model="form.analysisTime" placeholder="请输入维修时间" />
        </el-form-item> -->
        <el-form-item label="原因分析" prop="analysisReason">
          <el-input v-model="form.analysisReason" placeholder="请输入原因分析" />
        </el-form-item>
        <el-form-item label="记录日期" prop="analysisRecordTime">
          <el-date-picker clearable v-model="form.analysisRecordTime" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择记录日期">
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

<script setup name="Analysis">
import { listAnalysis, getAnalysis, delAnalysis, addAnalysis, updateAnalysis } from "@/api/device/maintenanceTable/analysis";
import { ElMessage } from 'element-plus'
import { format } from 'date-fns';

const { proxy } = getCurrentInstance();
const { device_fault_analysis } = proxy.useDict('device_fault_analysis');

const analysisList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeAnalysisRecordTime = ref([]);
const tableList = ref([]);

const data = reactive({
  form: {},
  // queryParams: {
  //   pageNum: 1,
  //   pageSize: 10,
  //   analysisName: null,
  //   analysisTime: null,
  //   analysisReason: null,
  //   analysisRecordTime: null
  // },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceName: null,
    deviceNum: null,
    workStatus: null,
    faultType: null,
    applyBy: null,
    applyDepartment: null,
    maintenancePeople: null,
    faultPhenomenon: null,
    maintenanceAnalysis: null,
    maintenanceContent: null,
    reportedTime: null,
    resolutionTime: null,
    faultDuration: null,
    maintenanceCast: null,
    yearAndMonth: format(new Date(), 'yyyy-MM'),
    ifDown: null
  },
  rules: {
    analysisName: [
      { required: true, message: "故障类型名称不能为空", trigger: "change" }
    ],
    // analysisTime: [
    //   { required: true, message: "维修时间不能为空", trigger: "blur" }
    // ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询设备故障分析列表 */
function getList() {
  console.log('------------>', queryParams.value.test)
  loading.value = true;
  queryParams.value.params = {};
  // if (null != daterangeAnalysisRecordTime && '' != daterangeAnalysisRecordTime) {
  //   queryParams.value.params["beginAnalysisRecordTime"] = daterangeAnalysisRecordTime.value[0];
  //   queryParams.value.params["endAnalysisRecordTime"] = daterangeAnalysisRecordTime.value[1];
  // }
  listAnalysis(queryParams.value).then(response => {
    analysisList.value = response.rows;

    const chineseToEnglishMap = {
      '润滑不良': 'poorLubrication',
      '自然磨损': 'naturalWear',
      '操作保养不良': 'poorOperationMaintenance',
      '操作者精力不集中': 'operatorDistraction',
      '修理质量不良': 'poorRepairQuality',
      '操作不熟练': 'unskilledOperation',
      '不合理超载使用': 'unreasonableOverload',
      '原制造问题': 'originalManufacturingIssues',
      '违章操作': 'violationOperation',
      '原设计不良': 'poorDesign',
      '原因不明': 'unknownReason'
    };
    // 初始化一个对象用于保存每种 faultType 的数据，使用 label 作为键
    const faultTypeCategories = device_fault_analysis.value.reduce((acc, analysis) => {
      acc[analysis.value] = { label: analysis.label, items: [] };
      return acc;
    }, {});

    response.rows.forEach(item => {
      // 检查 item.faultType 是否不是 null，并且存在于 deviceFaultAnalysis 的 value 中
      if (item.faultType !== null) {
        const faultTypeInfo = device_fault_analysis.value.find(fa => fa.value === item.faultType);
        if (faultTypeInfo) {
          // 根据 faultType 将 item 添加到相应的类别中
          faultTypeCategories[faultTypeInfo.value].items.push(item);
        }
      }
      // 如果 faultType 是 null，则跳过
    });

    let resultArrayFormat = [];

    for (let key in faultTypeCategories) {
      let timeSum = 0;

      for (const item of faultTypeCategories[key].items) {
        const faultDurationStr = item.faultDuration;
        if (faultDurationStr) {
          try {
            timeSum += parseFloat(faultDurationStr.trim());
          } catch (e) {
            console.error(`Failed to parse faultDuration: ${faultDurationStr}`, e);
          }
        }
      }

      if (chineseToEnglishMap[key]) {
        const labelEn = chineseToEnglishMap[key];
        const labelOriginal = faultTypeCategories[key].label;
        const children = [...faultTypeCategories[key].items];
        const count = children.length;
        resultArrayFormat.push({
          faultTypeName: labelOriginal,
          children: children,
          count: count,
          maintenanceId: `${labelEn}-${count}`,
          faultDuration: timeSum.toFixed(2)
        });
      } else {
        // 如果存在未在映射中定义的键，则保留原样或另行处理
        const children = [...faultTypeCategories[key].items];
        const count = children.length;
        resultArrayFormat.push({
          faultTypeName: key,
          children: children,
          count: count,
          maintenanceId: `${key}-${count}`,
          faultDuration: timeSum.toFixed(2)
        });
      }
    }
    tableList.value = resultArrayFormat
    console.log({ resultArrayFormat });

    console.log({ faultTypeCategories });
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
    analysisId: null,
    analysisName: null,
    analysisTime: null,
    analysisReason: null,
    analysisRecordTime: null
  };
  proxy.resetForm("analysisRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeAnalysisRecordTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.analysisId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备故障分析";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _analysisId = row.analysisId || ids.value
  getAnalysis(_analysisId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备故障分析";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["analysisRef"].validate(valid => {
    if (valid) {
      if (form.value.analysisId != null) {
        updateAnalysis(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAnalysis(form.value).then(response => {
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
  const _analysisIds = row.analysisId || ids.value;
  proxy.$modal.confirm('是否确认删除设备故障分析编号为"' + _analysisIds + '"的数据项？').then(function () {
    return delAnalysis(_analysisIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/analysis/export', {
    ...queryParams.value
  }, `analysis_${new Date().getTime()}.xlsx`)
}

getList();
</script>
