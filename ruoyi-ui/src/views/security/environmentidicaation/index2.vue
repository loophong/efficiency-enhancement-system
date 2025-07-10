<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动/过程" prop="activity">
        <el-input
            v-model="queryParams.activity"
            placeholder="请输入活动/过程"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="风险等级" prop="riskLevel">
        <el-input
            v-model="queryParams.riskLevel"
            placeholder="请输入风险等级"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实施时间" prop="implementationTime">
        <el-date-picker clearable
                        v-model="queryParams.implementationTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择实施时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实施部门" prop="department">
        <el-input
            v-model="queryParams.department"
            placeholder="请输入实施部门"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="严重程度" prop="severity">
        <el-input
            v-model="queryParams.severity"
            placeholder="请输入严重程度"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生频次" prop="frequency">
        <el-input
            v-model="queryParams.frequency"
            placeholder="请输入发生频次"
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
            v-hasPermi="['security:assessment:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:assessment:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:assessment:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:assessment:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-upload
          :show-file-list="false"
          :before-upload="beforeImport"
          accept=".xlsx,.xls"
        >
          <el-button
            type="info"
            plain
            icon="Upload"
          >导入</el-button>
        </el-upload>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Download"
          @click="handleImportTemplate"
        >模板下载</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="assessmentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动/过程" align="center" prop="activity" />
      <el-table-column label="风险和机遇" align="center" prop="riskOpportunity" />
      <el-table-column label="造成后果" align="center" prop="consequences" />
      <el-table-column label="风险等级" align="center" prop="riskLevel">
        <template #default="scope">
          <div v-if="scope.row.riskLevel === '高'">
            <el-tag type="danger">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else-if="scope.row.riskLevel === '一般'">
            <el-tag type="warning">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else-if="scope.row.riskLevel === '低'">
            <el-tag type="success">{{ scope.row.riskLevel }}</el-tag>
          </div>
          <div v-else>
            {{ scope.row.riskLevel }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="应对措施" align="center" prop="responseMeasures" />
      <el-table-column label="实施时间" align="center" prop="implementationTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.implementationTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实施部门" align="center" prop="department" />
      <el-table-column label="严重程度" align="center" prop="severity" />
      <el-table-column label="发生频次" align="center" prop="frequency" />
      <el-table-column label="风险系数" align="center" prop="risk" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:assessment:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:assessment:remove']">删除</el-button>
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

    <!-- 添加或修改风险和机遇评估及控制措施对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="assessmentRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="活动/过程" prop="activity">
          <el-input v-model="form.activity" placeholder="请输入活动/过程" />
        </el-form-item>
        <el-form-item label="风险和机遇" prop="riskOpportunity">
          <el-input v-model="form.riskOpportunity" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="造成后果" prop="consequences">
          <el-input v-model="form.consequences" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="风险等级" prop="riskLevel">
          <el-select v-model="form.riskLevel" placeholder="请选择风险等级">
            <el-option label="高" value="高"></el-option>
            <el-option label="一般" value="一般"></el-option>
            <el-option label="低" value="低"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="应对措施" prop="responseMeasures">
          <el-input v-model="form.responseMeasures" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="实施时间" prop="implementationTime">
          <el-date-picker clearable
                          v-model="form.implementationTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择实施时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实施部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入实施部门" />
        </el-form-item>
        <el-form-item label="严重程度" prop="severity">
          <el-input v-model="form.severity" placeholder="请输入严重程度" />
        </el-form-item>
        <el-form-item label="发生频次" prop="frequency">
          <el-input v-model="form.frequency" placeholder="请输入发生频次" />
        </el-form-item>
        <el-form-item label="风险系数" prop="risk">
          <el-input v-model="form.risk" placeholder="请输入风险系数" />
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

<script setup name="Assessment">
import {listAssessment, getAssessment, delAssessment, addAssessment, updateAssessment} from "@/api/security/assessment";
import * as XLSX from 'xlsx';
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';
import { Check } from '@element-plus/icons-vue';

const {proxy} = getCurrentInstance();
const {security_status} = proxy.useDict('security_status');

const assessmentList = ref([]);
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
    activity: null,
    riskOpportunity: null,
    consequences: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null,
    severity: null,
    frequency: null
  },
  rules: {
    activity: [
      {required: true, message: "活动/过程不能为空", trigger: "blur"}
    ],
    riskOpportunity: [
      {required: true, message: "风险和机遇不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询风险和机遇评估及控制措施列表 */
function getList() {
  loading.value = true;
  listAssessment(queryParams.value).then(response => {
    assessmentList.value = response.rows;
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
    activity: null,
    riskOpportunity: null,
    consequences: null,
    riskLevel: null,
    responseMeasures: null,
    implementationTime: null,
    department: null,
    severity: null,
    frequency: null
  };
  proxy.resetForm("assessmentRef");
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
  title.value = "添加风险和机遇评估及控制措施";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getAssessment(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改风险和机遇评估及控制措施";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["assessmentRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateAssessment(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addAssessment(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除风险和机遇评估及控制措施编号为"' + _ids + '"的数据项？').then(function () {
    return delAssessment(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/assessment/export', {
    ...queryParams.value
  }, `assessment_${new Date().getTime()}.xlsx`)
}

/** 下载模板 */
function handleImportTemplate() {
  // 创建示例数据，与提供的模板一致
  const exampleData = [
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['', '', '', '', '', '', '', '', '', '', '', ''],
    ['活动/过程', '风险和机遇', '造成后果', '严重程度', '发生频次', '风险系数', '高', '一般', '低', '应对措施', '时间', '部门'],
    ['法律法规', '对产品标准、法律法规的更新了解不及时、准确', '产品不符合最新产品标准、法律法规的规定要求', '3', '3', '9', '', '√', '', '随时关注最新产品标准/法律法规更新信息、适时对技术文件、检验规程等文件及时进行更新，并组织培训教育', '2023.7.1', '安环设备科/质量科/技术科'],
    ['设备管理', '设备精度不够、', '加工精度不达标', '2', '3', '6', '', '√', '', '更新设备', '2023.7.1', '安环设备科'],
    ['材料管理', '材质不对', '产品不合格', '1', '1', '1', '', '', '√', '加强采购控制，索要材质单', '2023.7.1', '技术科/供应科'],
    ['加工制造', '图纸设计不合理', '加工尺寸不合格', '3', '2', '6', '', '√', '', '图纸符合校对', '2023.7.1', '技术科'],
    ['检验试验设备', '未检定/失效', '产品不合格', '3', '2', '6', '', '√', '', '定期检定，发现失效后及时处理', '2023.7.1', '安环设备科'],
    ['检验', '质检不严', '不合格品出厂', '4', '4', '16', '√', '', '', '严格按照规定进行检验，人员培训提高业务水平', '2023.7.1', '质量科'],
    ['生产', '任务安排不及时', '交货延期', '4', '2', '8', '', '√', '', '根据合同要求及时下达生产任务，跟踪生产进度', '2023.7.1', '生产管理科'],
    ['市场开发', '市场了解不深入', '产品推广能力不足', '5', '1', '5', '', '√', '', '加强陌地市场分析，细分不同市场，提高市场占有率', '2023.7.1', '生产管理科（市场）'],
    ['售后服务', '回访长', '顾客满意度低', '2', '1', '2', '', '', '√', '定期回访客户', '2023.7.1', '生产管理科（市场）'],
    ['设备操作', '违章操作', '设备损坏', '2', '1', '2', '', '', '√', '培训指导设备正确操作', '2023.7.1', '安环设备科'],
    ['设备维保', '不及时', '设备性能降低、影响生产效率', '1', '2', '2', '', '', '√', '设备定期维保、改进维修机制', '2023.7.1', '安环设备科'],
    ['员工管理', '安全意识', '人身伤害', '4', '1', '4', '', '√', '', '安全培训，提高员工的工作责任', '2023.7.1', '企业管理科'],
    ['安全环保管理', '制度不完善，管理不善', '安全环保事故', '5', '1', '5', '', '√', '', '完善制度，加强管理，提高管理水平', '2023.7.1', '安环设备科'],
    ['产品交付', '产品不合格', '顾客已验收的产品不符合要求', '5', '3', '15', '√', '', '', '交货验收，严格检查，确保符合要求', '2023.7.1', '生产管理科、质量科'],
    ['企业形象', '花费少，宣传不足，知名度低', '市场竞争力不足', '4', '4', '16', '√', '', '', '严控产品质量，定期体检', '2023.7.1', '全公司'],
    ['国家环保法律日常完善且严格，本公司适时建立环境及职业健康安全管理体系，以增加顾客、社会及员工信赖。', '', '', '5', '3', '15', '-', '-', '-', '提高机遇，提高企业知名度及品牌，扩大市场，提高市场占有率。', '2023.7.1', '管理层及各科室'],
    ['时间：2023.7.1', '', '', '', '', '', '', '', '', '', '', '']
  ];
  
  // 创建工作簿和工作表
  const workbook = XLSX.utils.book_new();
  const worksheet = XLSX.utils.aoa_to_sheet(exampleData);
  
  // 设置列宽
  const wscols = [
    {wch: 12}, // A - 活动/过程
    {wch: 25}, // B - 风险和机遇
    {wch: 25}, // C - 造成后果
    {wch: 10}, // D - 严重程度
    {wch: 10}, // E - 发生频次
    {wch: 10}, // F - 风险系数
    {wch: 6},  // G - 高
    {wch: 6},  // H - 一般
    {wch: 6},  // I - 低
    {wch: 30}, // J - 应对措施
    {wch: 12}, // K - 实施时间
    {wch: 18}  // L - 部门
  ];
  worksheet['!cols'] = wscols;
  
  // 合并单元格设置
  worksheet['!merges'] = [
    {s: {r: 2, c: 2}, e: {r: 2, c: 5}}, // C3:F3 - 评价
    {s: {r: 2, c: 6}, e: {r: 2, c: 8}}, // G3:I3 - 风险等级
    {s: {r: 2, c: 10}, e: {r: 2, c: 10}}, // K3 - 实施
    {s: {r: 20, c: 0}, e: {r: 20, c: 2}}, // A21:C21 - 国家环保法律...
  ];
  
  // 将工作表添加到工作簿
  XLSX.utils.book_append_sheet(workbook, worksheet, '风险和机遇评估');
  
  // 生成Excel文件并下载
  XLSX.writeFile(workbook, '风险和机遇评估导入模板.xlsx');
}

/** 导入前校验 */
function beforeImport(file) {
  loading.value = true;
  const fileReader = new FileReader();
  fileReader.onload = (e) => {
    try {
      const data = e.target.result;
      const workbook = XLSX.read(data, { type: 'array' });
      const firstSheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[firstSheetName];
      const jsonData = XLSX.utils.sheet_to_json(worksheet, { header: 1 });
      
      // 解析表头，跳过前四行
      const importedData = [];
      
      // 从第五行开始解析数据 (索引4)
      for (let i = 4; i < jsonData.length; i++) {
        const row = jsonData[i];
        if (!row || row.length === 0) continue; // 跳过空行
        
        // 跳过最后一行时间标记
        if (row[0] && row[0].toString().includes('时间：')) continue;
        
        const item = {};
        
        // 根据模板格式解析数据
        item.activity = row[0] || ''; // A - 活动/过程
        item.riskOpportunity = row[1] || ''; // B - 风险和机遇
        item.consequences = row[2] || ''; // C - 造成后果
        item.severity = row[3] || ''; // D - 严重程度
        item.frequency = row[4] || ''; // E - 发生频次
        item.risk = row[5] || ''; // F - 风险系数
        
        // 风险等级判断
        if (row[6] === '√') {
          item.riskLevel = '高';
        } else if (row[7] === '√') {
          item.riskLevel = '一般';
        } else if (row[8] === '√') {
          item.riskLevel = '低';
        } else {
          // 处理特殊情况，如国家环保法律那一行使用了'-'
          if (row[6] === '-' && row[7] === '-' && row[8] === '-') {
            item.riskLevel = '-';
          } else {
            item.riskLevel = '一般'; // 默认为一般
          }
        }
        
        item.responseMeasures = row[9] || ''; // J - 应对措施
        
        // 处理日期格式
        const dateStr = row[10] || '';
        if (dateStr) {
          // 将2023.7.1格式转换为2023-07-01
          const dateParts = dateStr.split('.');
          if (dateParts.length === 3) {
            const year = dateParts[0];
            const month = dateParts[1].padStart(2, '0');
            const day = dateParts[2].padStart(2, '0');
            item.implementationTime = `${year}-${month}-${day}`;
          } else {
            item.implementationTime = dateStr;
          }
        }
        
        // 处理部门名称，直接使用导入的文本值，不进行字典替换
        const departmentText = row[11] || '';
        if (departmentText) {
          item.department = departmentText;
        }
        
        importedData.push(item);
      }
      
      // 保存数据到数据库
      saveImportedData(importedData);
    } catch (error) {
      console.error("导入失败:", error);
      proxy.$modal.msgError("导入失败: " + error.message);
      loading.value = false;
    }
  };
  
  fileReader.readAsArrayBuffer(file);
  return false; // 阻止自动上传
}

/** 保存导入的数据到数据库 */
function saveImportedData(importedData) {
  if (importedData.length === 0) {
    proxy.$modal.msgError("没有有效的数据可导入");
    loading.value = false;
    return;
  }
  
  // 过滤掉没有必填字段的数据
  const validData = importedData.filter(item => {
    // 检查必填字段是否存在
    if (!item.activity || !item.riskOpportunity) {
      return false;
    }
    return true;
  });
  
  if (validData.length === 0) {
    proxy.$modal.msgError("导入的数据缺少必填字段，请检查模板格式");
    loading.value = false;
    return;
  }
  
  // 使用Promise.all批量保存数据
  const savePromises = validData.map(item => {
    // 确保所有必填字段都有值
    const data = {
      ...item,
      activity: item.activity || '',
      riskOpportunity: item.riskOpportunity || '',
      consequences: item.consequences || '',
      riskLevel: item.riskLevel || '一般',
      responseMeasures: item.responseMeasures || '',
      department: item.department || '',
      severity: item.severity || '',
      frequency: item.frequency || '',
      risk: item.risk || ''
    };
    return addAssessment(data);
  });
  
  Promise.all(savePromises)
    .then(() => {
      proxy.$modal.msgSuccess("导入成功，共" + validData.length + "条数据已保存到数据库");
      getList(); // 刷新列表
    })
    .catch(error => {
      console.error("保存失败:", error);
      proxy.$modal.msgError("保存失败: " + (error.message || "未知错误"));
    })
    .finally(() => {
      loading.value = false;
    });
}

getList();
</script>
