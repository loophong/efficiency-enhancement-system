<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="日期选择" prop="yearAndMonth">
        <el-date-picker v-model="queryParams.yearAndMonth" value-format="YYYY-MM" type="month"
          @change="handleQuery"></el-date-picker>
      </el-form-item>
      <el-form-item label="故障类型名称" prop="faultType">
        <el-select v-model="queryParams.faultType" placeholder="请选择故障类型名称" clearable filterable style="width: 180px;"
          @change="handleQuery">
          <el-option v-for="dict in device_fault_analysis" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="维修时间" prop="analysisTime">
        <el-input v-model="queryParams.analysisTime" placeholder="请输入维修时间" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <!-- <el-form-item label="原因分析" prop="maintenanceAnalysis">
        <el-input v-model="queryParams.maintenanceAnalysis" placeholder="请输入原因分析" clearable
          @keyup.enter="handleQuery" />
      </el-form-item> -->
      <!-- <el-form-item label="记录日期" style="width: 308px">
        <el-date-picker v-model="daterangeAnalysisRecordTime" value-format="YYYY-MM-DD" type="daterange"
          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" row-key="maintenanceTableId"
      @selection-change="handleSelectionChange" border stripe :default-expand-all="openAllTree">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="故障类型名称" align="center" prop="faultTypeName" width="220">
        <template #default="scope">
          <span v-if="scope.row.faultTypeName" :style="{ fontWeight: 'bold' }">{{ scope.row.faultTypeName }} ({{
            scope.row.count }})</span>
          <span v-else>{{ scope.row.deviceName }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="次数" align="center" prop="count" width="140" /> -->
      <el-table-column label="维修时间" align="center" prop="faultDuration" width="140">
        <template #default="scope">
          <span v-if="scope.row.faultDuration">{{ scope.row.faultDuration }} (h)</span>
          <span v-else>{{ scope.row.faultDuration }}</span>
        </template>
      </el-table-column>
      <el-table-column label="故障现象" align="center" prop="faultPhenomenon" />
      <el-table-column label="原因分析" align="center" prop="maintenanceAnalysis">
        <template #default="scope">
          <el-button @click="handlePreview(scope.row)"
            v-if="scope.row.maintenanceAnalysis && scope.row.maintenanceAnalysis.includes('upload')">预览</el-button>
          <span v-else>{{ scope.row.maintenanceAnalysis }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录日期" align="center" prop="reportedTime" width="180">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:analysis:edit']" v-if="scope.row.faultTypeName">原因分析</el-button>
          <el-button link type="primary" icon="Document" @click="handleWord(scope.row)"
            v-hasPermi="['maintenanceTable:analysis:edit']" v-if="scope.row.faultTypeName">生成文档</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" /> -->

    <!-- 添加或修改设备故障分析对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="analysisRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="故障类型名称" prop="analysisName">
          <el-select v-model="form.analysisName" placeholder="请选择故障类型名称" disabled>
            <el-option v-for="dict in device_fault_analysis" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="记录日期" prop="analysisRecordTime">
          <el-date-picker clearable v-model="form.analysisRecordTime" type="month" value-format="YYYY-MM"
            placeholder="请选择记录日期" disabled>
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="维修时间" prop="analysisTime">
          <el-input v-model="form.analysisTime" placeholder="请输入维修时间" />
        </el-form-item> -->
        <el-form-item label="原因分析" prop="maintenanceAnalysis">
          <file-upload v-model="form.maintenanceAnalysis" />
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-drawer :title="drawerTitle" v-model="openDrawer" size="40%">
      <vue-office-docx :src="drawerUrl" style="height: 100vh;" @rendered="renderedHandler" @error="errorHandler" />
    </el-drawer>
  </div>
</template>

<script setup name="Analysis">
import { listAnalysis, getAnalysis, delAnalysis, addAnalysis, updateAnalysis, listAnalysisByNameAndDate } from "@/api/device/maintenanceTable/analysis";
import { ElMessage } from 'element-plus'
import { format } from 'date-fns';

//导出为word
import JSZip from 'pizzip'
import Docxtemplater from 'docxtemplater'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'
import { ElMessageBox } from 'element-plus'

import VueOfficeDocx from '@vue-office/docx'
import '@vue-office/docx/lib/index.css'

const { proxy } = getCurrentInstance();
const { device_fault_analysis } = proxy.useDict('device_fault_analysis');

const analysisList = ref([]);
const open = ref(false);
const openDrawer = ref(false);
const openAllTree = ref(true);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const router = useRouter();
const route = useRoute();
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const drawerTitle = ref("");
const drawerUrl = ref("");
const daterangeAnalysisRecordTime = ref([]);
const tableList = ref([]);
const resultsReason = reactive({})
const data = reactive({
  form: {},
  queryParamsAnalysis: {
    pageNum: 1,
    pageSize: 10,
    analysisName: null,
    maintenanceAnalysis: null,
    analysisRecordTime: null
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    deviceName: null,
    deviceNum: null,
    workStatus: null,
    faultType: '',
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
  }
});

const { queryParams, form, rules, queryParamsAnalysis } = toRefs(data);


var routerFaultType = route.query.faultType;
var routerYearAndMonth = route.query.yearAndMonth;

watch(() => route.query, (newVal) => {
  console.log('检测到新参数--->', newVal)
  routerFaultType = route.query.faultType
  routerYearAndMonth = route.query.yearAndMonth
  handleRouteParams()
})
const handleRouteParams = () => {
  if (routerFaultType && routerYearAndMonth) {
    console.log('Received faultType:', routerFaultType);
    console.log('Received yearAndMonth:', routerYearAndMonth);
    data.queryParams.faultType = routerFaultType;
    data.queryParams.yearAndMonth = convertToYearMonth(routerYearAndMonth);
    getList(); // 假设需要根据路由参数重新获取列表数据
  } else {
    getList();
    console.log('No specific route params received.');
    // 可以选择性地在这里添加其他逻辑
  }
};



handleRouteParams();

function handlePreview(input) {
  console.log({ input })
  const firstFaultFile = input.maintenanceAnalysis.split(',')[0].trim();
  const uploadDateMatch = firstFaultFile.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
  drawerTitle.value = `${input.faultTypeName}  上传日期：${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`
  drawerUrl.value = `${import.meta.env.VITE_APP_BASE_API}${firstFaultFile}`
  openDrawer.value = true
}

// 解析输入的时间字符串
function convertToYearMonth(inputDateTime) {

  const date = new Date('20' + inputDateTime); // 假设年份是20xx，根据需要调整
  if (isNaN(date.getTime())) {
    console.error("Invalid date format");
    return '';
  }

  // 获取年份（四位数）和月份
  const year = date.getFullYear();
  const month = ('0' + (date.getMonth() + 1)).slice(-2); // JavaScript的月份是从0开始的

  // 返回格式化的字符串
  return `${year}-${month}`;
}


/** 查询设备故障分析列表 */
async function getList() {

  const chineseFaultTypes = [
    '润滑不良', '自然磨损', '操作保养不良', '操作者精力不集中',
    '修理质量不良', '操作不熟练', '不合理超载使用', '原制造问题',
    '违章操作', '原设计不良', '原因不明'
  ];

  const results = new Map(); // 用于存储查询结果

  for (let faultType of chineseFaultTypes) {
    queryParamsAnalysis.value.analysisName = faultType;
    queryParamsAnalysis.value.analysisRecordTime = queryParams.value.yearAndMonth;

    try {
      const result = await listAnalysisByNameAndDate(queryParamsAnalysis.value);
      if (result.rows && result.rows.length > 0) {
        // 使用中文故障类型名称作为键，直接存储analysisReason内容
        results[faultType] = result.rows[0].maintenanceAnalysis;
      } else {
        // 如果没有找到对应的数据，则设置为null
        results[faultType] = null;
      }
    } catch (error) {
      console.error(`查询失败: ${faultType}`, error);
      // 查询出错时也设置为null
      results[faultType] = null;
    }
  }
  resultsReason.data = results
  // console.log('------------>', queryParams.value.test)
  loading.value = true;
  queryParams.value.params = {};

  if (queryParams.value.yearAndMonth == null) {
    queryParams.value.yearAndMonth = format(new Date(), 'yyyy-MM')
  }
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
        const reason = resultsReason.data[key];
        const children = [...faultTypeCategories[key].items];
        const count = children.length;
        resultArrayFormat.push({
          faultTypeName: labelOriginal,
          children: children,
          count: count,
          maintenanceAnalysis: reason,
          maintenanceTableId: `${labelEn}-${count}`,
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
          maintenanceTableId: `${key}-${count}`,
          faultDuration: timeSum.toFixed(2)
        });
      }
    }
    if (queryParams.value.faultType == '' || queryParams.value.faultType == null) {
      tableList.value = resultArrayFormat
    } else {
      tableList.value = resultArrayFormat.filter(item => {
        return item.faultTypeName === queryParams.value.faultType
      })
    }

    console.log({ resultArrayFormat });

    console.log({ faultTypeCategories });
    total.value = response.total;
    loading.value = false;
  });
}

function handleWord(wordData) {
  console.log('wordData--->', wordData)
  console.log(wordData)
  wordData.children.forEach(item => {
    item.faultPhenomenon = item.faultPhenomenon || ''
    item.faultDuration = item.faultDuration || ''
    item.maintenanceAnalysis = item.maintenanceAnalysis || ''
    item.maintenanceContent = item.maintenanceContent || ''
  })
  console.log('After----->', wordData)
  // 读取并获得模板文件的二进制内容
  JSZipUtils.getBinaryContent("/wordAnalysis.docx", function (error, content) {
    if (error) {
      throw error;
    }

    // 创建一个PizZip实例，内容为模板的内容
    let zip = new JSZip(content);
    // 创建并加载docxtemplater实例对象
    let doc = new Docxtemplater();
    let table = {
      ...wordData,
      yearAndMonth: queryParams.value.yearAndMonth
    }
    doc.loadZip(zip);
    doc.setData(table);

    try {
      // 用模板变量的值替换所有模板变量
      doc.render();
    } catch (error) {
      // 抛出异常
      let e = {
        message: error.message,
        name: error.name,
        stack: error.stack,
        properties: error.properties
      };
      console.log(JSON.stringify({
        error: e
      }));
      throw error;
    }

    // 生成一个代表docxtemplater对象的zip文件（不是一个真实的文件，而是在内存中的表示）
    let out = doc.getZip().generate({
      type: "blob",
      mimeType: "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    });
    // 将目标文件对象保存为目标类型的文件，并命名
    saveAs(out, `${wordData.faultTypeName}(${queryParams.value.yearAndMonth})`);
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
    maintenanceAnalysis: null,
    analysisRecordTime: null
  };
  proxy.resetForm("analysisRef");
}

/** 搜索按钮操作 */
async function handleQuery() {
  if (!queryParams.value.faultType) {
    openAllTree.value = false
  } else {
    openAllTree.value = true
  }
  queryParams.value.pageNum = 1;
  // console.log(results['润滑不良'])
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
  queryParamsAnalysis.value.analysisName = row.faultTypeName
  queryParamsAnalysis.value.analysisRecordTime = queryParams.value.yearAndMonth
  console.log(queryParamsAnalysis.value)
  listAnalysisByNameAndDate(queryParamsAnalysis.value).then(result => {
    console.log(result.rows)
    if (result.total) {
      form.value = result.rows[0];
      open.value = true;
      title.value = "修改原因分析";
    } else {
      console.log(row)
      open.value = true;
      form.value.analysisName = row.faultTypeName
      form.value.analysisRecordTime = queryParams.value.yearAndMonth
      title.value = "添加原因分析";
    }
  })
  // const _analysisId = row.analysisId || ids.value
  // getAnalysis(_analysisId).then(response => {
  //   form.value = response.data;
  //   open.value = true;
  //   title.value = "修改设备故障分析";
  // });
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
