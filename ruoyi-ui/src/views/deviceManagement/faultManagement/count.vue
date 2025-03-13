<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="项目名" prop="indicatorName">
        <el-input v-model="queryParams.indicatorName" placeholder="请输入项目名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="目标值" prop="indicatorTarget">
        <el-input v-model="queryParams.indicatorTarget" placeholder="请输入目标值" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="日期" style="width: 308px">
        <el-date-picker v-model="daterangeIndicatorTime" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="一月数据" prop="indicatorJan">
        <el-input v-model="queryParams.indicatorJan" placeholder="请输入一月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="二月数据" prop="indicatorFeb">
        <el-input v-model="queryParams.indicatorFeb" placeholder="请输入二月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="三月数据" prop="indicatorMar">
        <el-input v-model="queryParams.indicatorMar" placeholder="请输入三月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="四月数据" prop="indicatorApr">
        <el-input v-model="queryParams.indicatorApr" placeholder="请输入四月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="五月数据" prop="indicatorMay">
        <el-input v-model="queryParams.indicatorMay" placeholder="请输入五月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="六月数据" prop="indicatorJun">
        <el-input v-model="queryParams.indicatorJun" placeholder="请输入六月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="七月数据" prop="indicatorJul">
        <el-input v-model="queryParams.indicatorJul" placeholder="请输入七月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="八月数据" prop="indicatorAug">
        <el-input v-model="queryParams.indicatorAug" placeholder="请输入八月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="九月数据" prop="indicatorSep">
        <el-input v-model="queryParams.indicatorSep" placeholder="请输入九月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十月数据" prop="indicatorOct">
        <el-input v-model="queryParams.indicatorOct" placeholder="请输入十月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十一月数据" prop="indicatorNov">
        <el-input v-model="queryParams.indicatorNov" placeholder="请输入十一月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="十二月数据" prop="indicatorDec">
        <el-input v-model="queryParams.indicatorDec" placeholder="请输入十二月数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['device:count:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['device:count:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['device:count:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!--Excel 参数导入 -->
        <el-button type="primary" icon="UploadFilled" @click="showDialog = true" plain>导入
        </el-button>
        <el-dialog title="导入Excel文件" v-model="showDialog" width="30%">
          <el-form :model="formExport" ref="formExportRef" :rules="rulesExport" label-width="90px">
            <el-form-item label="选择年份" prop="year">
              <el-date-picker v-model="formExport.year" type="year" placeholder="请选择年份" value-format="YYYY"
                style="width: 100%;" />
            </el-form-item>
          </el-form>
          <div class="upload-area">
            <i class="el-icon-upload"></i>
            <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
          </div>
          <span class="dialog-footer">
            <br />
            <el-button @click="showDialog = false">取 消</el-button>
            <el-button type="primary" @click="fileSend" v-if="buttonLoading === false">确 定</el-button>
            <el-button type="primary" :loading="true" v-else>上传中</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['device:count:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="countList" @selection-change="handleSelectionChange" border stripe>
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <!-- <el-table-column label="主键id" align="center" prop="indicatorId" /> -->
      <el-table-column label="项目名" align="center" prop="indicatorName" />
      <el-table-column label="年份" align="center" prop="indicatorTime" width="100" />
      <el-table-column label="目标值" align="center" prop="indicatorTarget" />
      <el-table-column label="目标值上限" align="center" prop="indicatorUp" />
      <el-table-column label="目标值下限" align="center" prop="indicatorDown" />
      <el-table-column label="一月" align="center" prop="indicatorJan">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorJan, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorJan }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="二月" align="center" prop="indicatorFeb">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorFeb, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorFeb }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="三月" align="center" prop="indicatorMar">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorMar, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorMar }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="四月" align="center" prop="indicatorApr">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorApr, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorApr }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="五月" align="center" prop="indicatorMay">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorMay, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorMay }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="六月" align="center" prop="indicatorJun">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorJun, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorJun }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="七月" align="center" prop="indicatorJul">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorJul, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorJul }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="八月" align="center" prop="indicatorAug">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorAug, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorAug }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="九月" align="center" prop="indicatorSep">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorSep, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorSep }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="十月" align="center" prop="indicatorOct">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorOct, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorOct }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="十一月" align="center" prop="indicatorNov">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorNov, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorNov }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="十二月" align="center" prop="indicatorDec">
        <template #default="scope">
          <span :style="{ color: getColor(scope.row.indicatorDec, scope.row.indicatorUp, scope.row.indicatorDown) }">
            {{ scope.row.indicatorDec }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="分析文档" align="center" prop="indicatorFile">
        <template #default="scope">
          <span v-html="formatFileInfo(scope.row.indicatorFile)"></span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['device:count:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['device:count:remove']">删除</el-button>
          <el-button link type="primary" icon="Histogram" @click="handleShowChart(scope.row)"
            v-hasPermi="['device:count:remove']">展示图</el-button>
          <el-button link type="primary" icon="Document" @click="wordExport(scope.row)"
            v-hasPermi="['device:count:remove']">生成文档</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改设备指标分析对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="countRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="项目名" prop="indicatorName">
          <el-input v-model="form.indicatorName" placeholder="请输入项目名" />
        </el-form-item>
        <el-form-item label="目标值" prop="indicatorTarget">
          <el-input v-model="form.indicatorTarget" placeholder="请输入目标值" />
        </el-form-item>
        <el-form-item label="目标值上限" align="center" prop="indicatorUp">
          <el-input v-model="form.indicatorUp" placeholder="请输入目标值上限" />
        </el-form-item>
        <el-form-item label="目标值下限" align="center" prop="indicatorDown">
          <el-input v-model="form.indicatorDown" placeholder="请输入目标值下限" />
        </el-form-item>
        <el-form-item label="年份" prop="indicatorTime">
          <el-input v-model="form.indicatorTime" placeholder="请输入年份" />
        </el-form-item>
        <el-form-item label="一月数据" prop="indicatorJan">
          <el-input v-model="form.indicatorJan" placeholder="请输入一月数据" />
        </el-form-item>
        <el-form-item label="二月数据" prop="indicatorFeb">
          <el-input v-model="form.indicatorFeb" placeholder="请输入二月数据" />
        </el-form-item>
        <el-form-item label="三月数据" prop="indicatorMar">
          <el-input v-model="form.indicatorMar" placeholder="请输入三月数据" />
        </el-form-item>
        <el-form-item label="四月数据" prop="indicatorApr">
          <el-input v-model="form.indicatorApr" placeholder="请输入四月数据" />
        </el-form-item>
        <el-form-item label="五月数据" prop="indicatorMay">
          <el-input v-model="form.indicatorMay" placeholder="请输入五月数据" />
        </el-form-item>
        <el-form-item label="六月数据" prop="indicatorJun">
          <el-input v-model="form.indicatorJun" placeholder="请输入六月数据" />
        </el-form-item>
        <el-form-item label="七月数据" prop="indicatorJul">
          <el-input v-model="form.indicatorJul" placeholder="请输入七月数据" />
        </el-form-item>
        <el-form-item label="八月数据" prop="indicatorAug">
          <el-input v-model="form.indicatorAug" placeholder="请输入八月数据" />
        </el-form-item>
        <el-form-item label="九月数据" prop="indicatorSep">
          <el-input v-model="form.indicatorSep" placeholder="请输入九月数据" />
        </el-form-item>
        <el-form-item label="十月数据" prop="indicatorOct">
          <el-input v-model="form.indicatorOct" placeholder="请输入十月数据" />
        </el-form-item>
        <el-form-item label="十一月数据" prop="indicatorNov">
          <el-input v-model="form.indicatorNov" placeholder="请输入十一月数据" />
        </el-form-item>
        <el-form-item label="十二月数据" prop="indicatorDec">
          <el-input v-model="form.indicatorDec" placeholder="请输入十二月数据" />
        </el-form-item>
        <el-form-item label="分析文档" prop="indicatorFile">
          <file-upload v-model="form.indicatorFile" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog :title="chartTitle" v-model="openChart" width="1000px" append-to-body>
      <count-chart ref="chartRef" :chartData="chartData"></count-chart>
    </el-dialog>
  </div>
</template>

<script setup name="Count">
import { listCount, getCount, delCount, addCount, updateCount, uploadFile } from "@/api/device/maintenanceTable/count";
import countChart from "./countChart.vue"
//导出为word
import JSZip from 'pizzip'
import Docxtemplater from 'docxtemplater'
import JSZipUtils from 'jszip-utils'
import { saveAs } from 'file-saver'

import { ElMessage } from 'element-plus'
import { format } from 'date-fns';

import ImageModule from 'docxtemplater-image-module-free'
import expressions from 'angular-expressions'

const chartRef = ref(null);

const { proxy } = getCurrentInstance();
const countList = ref([]);
const open = ref(false);
const openChart = ref(false);
const showDialog = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const currentBase64 = ref("");
const chartTitle = ref("");
const daterangeIndicatorTime = ref([]);
const chartData = reactive({})

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    indicatorName: null,
    indicatorTarget: null,
    indicatorUp: null,
    indicatorDown: null,
    indicatorTime: null,
    indicatorJan: null,
    indicatorFeb: null,
    indicatorMar: null,
    indicatorApr: null,
    indicatorMay: null,
    indicatorJun: null,
    indicatorJul: null,
    indicatorAug: null,
    indicatorSep: null,
    indicatorOct: null,
    indicatorNov: null,
    indicatorDec: null
  },
  rules: {
  },
  formExport: {},
  rulesExport: {
    year: [
      { required: true, message: '请选择年份', trigger: 'blur' }
    ],
  },


});

const { queryParams, form, rules, formExport, rulesExport } = toRefs(data);
//文件显示
function formatFileInfo(fileInfo) {
  if (fileInfo == '' || fileInfo == null) {
    return fileInfo;
  }

  // 判断是否包含逗号（多个文件）
  const filePaths = fileInfo.includes(',') ? fileInfo.split(',') : [fileInfo];
  const formattedInfo = filePaths.map(path => {
    // 提取文件名（带扩展名）
    const fileNameWithExt = path.split('/').pop();

    // 去掉文件名中的时间戳部分
    const fileName = fileNameWithExt.split('_')[0]; // 取第一部分作为文件名
    const fileExt = fileNameWithExt.split('.').pop(); // 获取文件扩展名

    // 提取上传日期
    const uploadDateMatch = path.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
    let formattedDate = '';
    if (uploadDateMatch) {
      // 组合上传日期
      formattedDate = `${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`;
    }

    // 返回格式化后的字符串，用 [] 括起来
    return `{文件名：${fileName}.${fileExt} 上传日期：${formattedDate}}`;
  }).join('<br>'); // 如果有多个文件

  return formattedInfo;
}

// function handleExportWord(row) {
//   chartTitle.value = row.indicatorName;
//   chartData.data = row;
//   // 假设这里可能需要一些额外的逻辑来确保 chartRef 已经准备好了
//   nextTick(() => {
//     if (chartRef.value && typeof chartRef.value.getChartBase64 === 'function') {
//       const base64 = chartRef.value.getChartBase64();
//       console.log(base64);
//     }
//   });
// }

function handleShowChart(row) {
  console.log('countList---->', countList.value[0])
  console.log('row---->', row)
  chartTitle.value = row.indicatorName
  chartData.data = row
  openChart.value = true
}


function getColor(value, targetUp, targetDown) {
  if (targetUp == '' && targetDown == '') {
    return '#606266'
  }
  var up = parseFloat(targetUp)
  var down = parseFloat(targetDown)
  var result = parseFloat(value)
  if (result < down || result > up) {
    return '#de6673';
  }
  return '#606266'; // 默认颜色
}

function wordExport(wordData) {
  console.log('wordData--->', wordData)
  wordData.image = currentBase64.value
  console.log(wordData)
  openChart.value = false
  // 读取并获得模板文件的二进制内容
  JSZipUtils.getBinaryContent("/wordCount.docx", function (error, content) {
    if (error) {
      throw error;
    }
    expressions.filters.size = function (input, width, height) {
      return {
        data: input,
        size: [width, height],
      };
    };

    // 图片处理
    let opts = {}

    opts = {
      //图像是否居中
      centered: false
    };

    opts.getImage = (chartId) => {
      //console.log(chartId);//base64数据
      //将base64的数据转为ArrayBuffer
      return base64DataURLToArrayBuffer(chartId);
    }

    opts.getSize = function (img, tagValue, tagName) {
      //console.log(img);//ArrayBuffer数据
      //console.log(tagValue);//base64数据
      //console.log(tagName);//wordData对象的图像属性名
      //自定义指定图像大小
      // if (imgSize.hasOwnProperty(tagName)) {
      //   return imgSize[tagName];
      // } else {
      return [600, 350];
      // }
    }

    // 创建一个PizZip实例，内容为模板的内容
    let zip = new JSZip(content);
    // 创建并加载docxtemplater实例对象
    let doc = new Docxtemplater();
    doc.attachModule(new ImageModule(opts));
    doc.loadZip(zip);

    doc.setData(wordData);

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
    saveAs(out, `${wordData.indicatorName}(${wordData.indicatorTime})`);
  });
}

function angularParser(tag) {
  tag = tag
    .replace(/^\.$/, 'this')
    .replace(/(’|‘)/g, "'")
    .replace(/(“|”)/g, '"')
  const expr = expressions.compile(tag)
  return {
    get: function (scope, context) {
      let obj = {}
      const index = last(context.scopePathItem)
      const scopeList = context.scopeList
      const num = context.num
      for (let i = 0, len = num + 1; i < len; i++) {
        obj = assign(obj, scopeList[i])
      }
      //word模板中使用 $index+1 创建递增序号
      obj = assign(obj, { $index: index })
      return expr(scope, obj)
    }
  }
}
function base64DataURLToArrayBuffer(dataURL) {
  const base64Regex = /^data:image\/(png|jpg|svg|svg\+xml);base64,/;
  if (!base64Regex.test(dataURL)) {
    return false;
  }
  const stringBase64 = dataURL.replace(base64Regex, "");
  let binaryString;
  if (typeof window !== "undefined") {
    binaryString = window.atob(stringBase64);
  } else {
    binaryString = new Buffer(stringBase64, "base64").toString("binary");
  }
  const len = binaryString.length;
  const bytes = new Uint8Array(len);
  for (let i = 0; i < len; i++) {
    const ascii = binaryString.charCodeAt(i);
    bytes[i] = ascii;
  }
  console.log(bytes.buffer)
  return bytes.buffer;
}

/** 查询设备指标分析列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeIndicatorTime && '' != daterangeIndicatorTime) {
    queryParams.value.params["beginIndicatorTime"] = daterangeIndicatorTime.value[0];
    queryParams.value.params["endIndicatorTime"] = daterangeIndicatorTime.value[1];
  }
  listCount(queryParams.value).then(response => {
    countList.value = response.rows;
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
    indicatorId: null,
    indicatorName: null,
    indicatorTarget: null,
    indicatorTime: null,
    indicatorJan: null,
    indicatorFeb: null,
    indicatorMar: null,
    indicatorApr: null,
    indicatorMay: null,
    indicatorJun: null,
    indicatorJul: null,
    indicatorAug: null,
    indicatorSep: null,
    indicatorOct: null,
    indicatorNov: null,
    indicatorDec: null
  };
  proxy.resetForm("countRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeIndicatorTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.indicatorId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加设备指标分析";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _indicatorId = row.indicatorId || ids.value
  getCount(_indicatorId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改设备指标分析";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["countRef"].validate(valid => {
    if (valid) {
      if (form.value.indicatorId != null) {
        updateCount(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCount(form.value).then(response => {
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
  const _indicatorIds = row.indicatorId || ids.value;
  proxy.$modal.confirm('是否确认删除设备指标分析编号为"' + _indicatorIds + '"的数据项？').then(function () {
    return delCount(_indicatorIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}


// 导入excel，检查文件类型
function checkFile() {
  const file = proxy.$refs["fileInput"].files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    this.$message.error("只能上传 Excel 文件！");
    // this.$refs.fileInput.value = ""; // 清空文件选择框
  }
}
//导入excel，取消按钮绑定取消所选的xlsx
// function resetFileInput() {
//   this.$refs.fileInput.value = "";
// }
/** 导入按钮 */
function fileSend() {
  proxy.$refs["formExportRef"].validate(valid => {
    if (valid) {
      const fileInput = proxy.$refs["fileInput"];
      // console.log(proxy.$refs["fileInput"])
      // if (!fileInput || !fileInput.files.length) {
      //   ElMessage.warning('请选择要上传的文件');
      //   return;
      // }
      buttonLoading.value = true
      const file = fileInput.files[0];
      console.log('Selected file:', file);
      // console.log(file)
      const formData = new FormData();
      formData.append("excelFile", file);
      formData.append("year", formExport.value.year);
      console.log({ formData })
      // 使用如下方法打印出 formData 的内容
      for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
      }

      // isLoading.value = true;
      uploadFile(formData, `/fault/indicator/import`)
        .then(data => {
          // 处理上传成功的情况
          ElMessage({
            message: '上传成功',
            type: 'success',
          });
          getList();
          buttonLoading.value = false
          showDialog.value = false;
        })
        .catch(error => {
          // 处理上传失败的情况
          ElMessage({
            message: `上传失败:${error}`,
            type: 'error',
          });
          buttonLoading.value = false
          showDialog.value = false;
        });
    }
  })
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('device/count/export', {
    ...queryParams.value
  }, `count_${new Date().getTime()}.xlsx`)
}

watch(openChart, async (newVal) => {
  if (newVal) {
    // 等待图表渲染完成
    await nextTick();
    setTimeout(() => {  // 需要短暂延迟确保图表完成渲染
      if (chartRef.value) {
        currentBase64.value = chartRef.value.getChartBase64();
        console.log('当前图表 Base64:', currentBase64.value);
      }
    }, 300);  // 200-500ms 根据实际性能调整
  }
});

getList();
</script>
