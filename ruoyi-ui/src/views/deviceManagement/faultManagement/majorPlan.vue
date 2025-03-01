<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="序号" prop="majorOrder">
        <el-input v-model="queryParams.majorOrder" placeholder="请输入序号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="设备名称" prop="majorName">
        <el-input v-model="queryParams.majorName" placeholder="请输入设备名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="部位" prop="majorPosition">
        <el-input v-model="queryParams.majorPosition" placeholder="请输入部位" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="保养维修项目" prop="majorProject">
        <el-input v-model="queryParams.majorProject" placeholder="请输入保养维修项目" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="周期" prop="majorCycleNum">
        <el-input v-model="queryParams.majorCycleNum" placeholder="请输入周期" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="人员" prop="majorPeople">
        <el-input v-model="queryParams.majorPeople" placeholder="请输入人员" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="月份" prop="majorMonth">
        <el-input v-model="queryParams.majorMonth" placeholder="请输入月份" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="第几周" prop="majorMonthWeek">
        <el-input v-model="queryParams.majorMonthWeek" placeholder="请输入第几周" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="内容" prop="majorContent">
        <el-input v-model="queryParams.majorContent" placeholder="请输入内容" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['maintenanceTable:plan:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!--Excel 参数导入 -->
        <el-button type="primary" icon="UploadFilled" @click="showDialog = true" plain>导入
        </el-button>
        <el-dialog title="导入Excel文件" v-model="showDialog" width="30%" @close="resetFileInput">
          <el-form :model="form" ref="formRef" label-width="90px">
          </el-form>
          <div class="upload-area">
            <i class="el-icon-upload"></i>
            <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
          </div>
          <span class="dialog-footer">
            <el-button @click="showDialog = false">取 消</el-button>
            <el-button type="primary" @click="fileSend" v-if="buttonLoading === false">确 定</el-button>
            <el-button type="primary" :loading="true" v-else>上传中</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['maintenanceTable:plan:export']">导出</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="primary" plain icon="View" @click="showFullYear = true"
          v-hasPermi="['maintenanceTable:plan:add']">全年数据</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button type="primary" plain icon="Hide" @click="showFullYear = false"
          v-hasPermi="['maintenanceTable:plan:add']">当月及上月数据</el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="majorId" /> -->
      <!-- <el-table-column label="序号" align="center" prop="majorOrder" /> -->
      <el-table-column label="设备名称" align="center" prop="majorName" width="160" />
      <el-table-column label="部位" align="center" prop="majorPosition" />
      <el-table-column label="保养维修项目" align="center" prop="majorProject" width="280" />
      <el-table-column label="周期" align="center" prop="majorCycleNum" />
      <el-table-column label="人员" align="center" prop="majorPeople" />
      <!-- <el-table-column label="月份" align="center" prop="majorMonth" /> -->
      <el-table-column label="一月1W" align="center" prop="weekJanOne" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJanOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="一月2W" align="center" prop="weekJanTwo" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJanTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="一月3W" align="center" prop="weekJanThree" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJanThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="一月4W" align="center" prop="weekJanFour" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJanFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二月1W" align="center" prop="weekFebOne" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekFebOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二月2W" align="center" prop="weekFebTwo" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekFebTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二月3W" align="center" prop="weekFebThree" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekFebThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二月4W" align="center" prop="weekFebFour" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekFebFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="三月1W" align="center" prop="weekMarOne" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMarOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="三月2W" align="center" prop="weekMarTwo" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMarTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="三月3W" align="center" prop="weekMarThree" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMarThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="三月4W" align="center" prop="weekMarFour" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMarFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="四月1W" align="center" prop="weekAprOne" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAprOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="四月2W" align="center" prop="weekAprTwo" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAprTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="四月3W" align="center" prop="weekAprThree" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAprThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="四月4W" align="center" prop="weekAprFour" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAprFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="五月1W" align="center" prop="weekMayOne" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMayOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="五月2W" align="center" prop="weekMayTwo" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMayTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="五月3W" align="center" prop="weekMayThree" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMayThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="五月4W" align="center" prop="weekMayFour" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekMayFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="六月1W" align="center" prop="weekJunOne" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJunOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="六月2W" align="center" prop="weekJunTwo" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJunTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="六月3W" align="center" prop="weekJunThree" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJunThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="六月4W" align="center" prop="weekJunFour" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJunFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="七月1W" align="center" prop="weekJulOne" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJulOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="七月2W" align="center" prop="weekJulTwo" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJulTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="七月3W" align="center" prop="weekJulThree" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJulThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="七月4W" align="center" prop="weekJulFour" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekJulFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="八月1W" align="center" prop="weekAugOne" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAugOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="八月2W" align="center" prop="weekAugTwo" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAugTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="八月3W" align="center" prop="weekAugThree" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAugThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="八月4W" align="center" prop="weekAugFour" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekAugFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="九月1W" align="center" prop="weekSepOne" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekSepOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="九月2W" align="center" prop="weekSepTwo" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekSepTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="九月3W" align="center" prop="weekSepThree" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekSepThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="九月4W" align="center" prop="weekSepFour" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekSepFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十月1W" align="center" prop="weekOctOne" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekOctOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十月2W" align="center" prop="weekOctTwo" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekOctTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十月3W" align="center" prop="weekOctThree" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekOctThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十月4W" align="center" prop="weekOctFour" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekOctFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十一月1W" align="center" prop="weekNovOne" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekNovOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十一月2W" align="center" prop="weekNovTwo" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekNovTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十一月3W" align="center" prop="weekNovThree" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekNovThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十一月4W" align="center" prop="weekNovFour" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekNovFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十二月1W" align="center" prop="weekDecOne" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekDecOne }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十二月2W" align="center" prop="weekDecTwo" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekDecTwo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十二月3W" align="center" prop="weekDecThree" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekDecThree }}</span>
        </template>
      </el-table-column>
      <el-table-column label="十二月4W" align="center" prop="weekDecFour" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <span :style="{ fontSize: '30px', fontWeight: 'bold' }">{{ scope.row.weekDecFour }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改专业计划保养对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="planRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="序号" prop="majorOrder">
          <el-input v-model="form.majorOrder" placeholder="请输入序号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="部位" prop="majorPosition">
          <el-input v-model="form.majorPosition" placeholder="请输入部位" />
        </el-form-item>
        <el-form-item label="保养维修项目" prop="majorProject">
          <el-input v-model="form.majorProject" placeholder="请输入保养维修项目" />
        </el-form-item>
        <el-form-item label="周期" prop="majorCycleNum">
          <el-input v-model="form.majorCycleNum" placeholder="请输入周期" />
        </el-form-item>
        <el-form-item label="人员" prop="majorPeople">
          <el-input v-model="form.majorPeople" placeholder="请输入人员" />
        </el-form-item>
        <el-form-item label="月份" prop="majorMonth">
          <el-input v-model="form.majorMonth" placeholder="请输入月份" />
        </el-form-item>
        <el-form-item label="第几周" prop="majorMonthWeek">
          <el-input v-model="form.majorMonthWeek" placeholder="请输入第几周" />
        </el-form-item>
        <el-form-item label="内容" prop="majorContent">
          <el-input v-model="form.majorContent" placeholder="请输入内容" />
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

<script setup name="majorPlan">
import { listPlan, getPlan, delPlan, addPlan, updatePlan, uploadFile } from "@/api/device/maintenanceTable/majorPlan";
import { ElMessage } from 'element-plus'
import { format } from 'date-fns';

const { proxy } = getCurrentInstance();

const planList = ref([]);
const open = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const showSearch = ref(true);
const showFullYear = ref(false);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const showDialog = ref(false);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    majorOrder: null,
    majorName: null,
    majorPosition: null,
    majorProject: null,
    majorCycleNum: null,
    majorPeople: null,
    majorMonth: null,
    majorMonthWeek: null,
    majorContent: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);


function ifCurrentMonth(num) {
  const now = new Date();
  const currentMonth = now.getMonth() + 1; // 加1以转换为实际的月份
  return currentMonth === num || currentMonth - 1 === num;
}

/** 查询专业计划保养列表 */
function getList() {
  loading.value = true;
  listPlan(queryParams.value).then(response => {
    planList.value = response.rows;
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
    majorId: null,
    majorOrder: null,
    majorName: null,
    majorPosition: null,
    majorProject: null,
    majorCycleNum: null,
    majorPeople: null,
    majorMonth: null,
    majorMonthWeek: null,
    majorContent: null
  };
  proxy.resetForm("planRef");
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
  ids.value = selection.map(item => item.majorId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加专业计划保养";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _majorId = row.majorId || ids.value
  getPlan(_majorId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改专业计划保养";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["planRef"].validate(valid => {
    if (valid) {
      if (form.value.majorId != null) {
        updatePlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPlan(form.value).then(response => {
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
  const _majorIds = row.majorId || ids.value;
  proxy.$modal.confirm('是否确认删除专业计划保养编号为"' + _majorIds + '"的数据项？').then(function () {
    return delPlan(_majorIds);
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
  formData.append("yearAndMonth", '2024-12-12');
  console.log({ formData })
  // 使用如下方法打印出 formData 的内容
  for (let pair of formData.entries()) {
    console.log(pair[0] + ': ' + pair[1]);
  }

  // isLoading.value = true;
  uploadFile(formData, `/fault/majorPlan/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      getList();
      buttonLoading.value = false
      showDialog.value = false;
      // this.isLoading = false;
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


/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/plan/export', {
    ...queryParams.value
  }, `plan_${new Date().getTime()}.xlsx`)
}

getList();
</script>
