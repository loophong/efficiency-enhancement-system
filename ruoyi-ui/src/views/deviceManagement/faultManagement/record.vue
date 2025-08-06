<template>
  <div class="app-container">
    <!-- <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="关联主计划id" prop="maintenancePlanId">
        <el-input v-model="queryParams.maintenancePlanId" placeholder="请输入关联主计划id" clearable
          @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="关联月份数据" prop="planMonthTime">
        <el-input v-model="queryParams.planMonthTime" placeholder="请输入关联月份数据" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="截止时间" prop="maintenanceDeadline">
        <el-date-picker clearable v-model="queryParams.maintenanceDeadline" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择截止时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form> -->

    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['maintenanceTable:record:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['maintenanceTable:record:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['maintenanceTable:record:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['maintenanceTable:record:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <!-- <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主ID" align="center" prop="maintenanceId" />
      <el-table-column label="关联主计划id" align="center" prop="maintenancePlanId" />
      <el-table-column label="关联月份数据" align="center" prop="planMonthTime" />
      <el-table-column label="完成情况描述" align="center" prop="completionDescription" />
      <el-table-column label="照片附件" align="center" prop="imageAttachment" />
      <el-table-column label="遗留问题描述" align="center" prop="remainingIssuesDescription" />
      <el-table-column label="遗留问题分析" align="center" prop="remainingIssuesAnalysis" />
      <el-table-column label="审核状态" align="center" prop="maintenanceExamineStatus" />
      <el-table-column label="审核意见" align="center" prop="maintenanceExamineComment" />
      <el-table-column label="截止时间" align="center" prop="maintenanceDeadline" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.maintenanceDeadline, '{y}-{m}-{d}') }}</span>
        </template>
</el-table-column>
<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
  <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:record:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['maintenanceTable:record:remove']">删除</el-button>
        </template>
</el-table-column>
</el-table> -->

    <!-- <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" /> -->

    <!-- 添加或修改记录对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="recordRef" :model="form" :rules="rules" label-width="110px">
        <!-- <el-form-item label="关联主计划id" prop="maintenancePlanId">
          <el-input v-model="form.maintenancePlanId" placeholder="请输入关联主计划id" />
        </el-form-item> -->
        <!-- <el-form-item label="关联月份数据" prop="planMonthTime">
          <el-input v-model="form.planMonthTime" placeholder="请输入关联月份数据" />
        </el-form-item> -->
        <el-form-item label="完成情况描述" prop="completionDescription">
          <el-input v-model="form.completionDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="照片附件" prop="imageAttachment">
          <image-upload v-model="form.imageAttachment" />
        </el-form-item>
        <el-form-item label="遗留问题描述" prop="remainingIssuesDescription">
          <el-input v-model="form.remainingIssuesDescription" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="遗留问题分析" prop="remainingIssuesAnalysis">
          <el-input v-model="form.remainingIssuesAnalysis" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="status" placeholder="请选择">
            <el-option label="◎(检查保养项目)" value="◎(待审核)"></el-option>
            <el-option label="◆(精度调整计划)" value="◆(待审核)"></el-option>
            <el-option label="☆(检修计划)" value="☆(待审核)"></el-option>
            <el-option label="空" value=""></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核结果" prop="maintenanceExamineStatus" v-if="ifShowExamine && existContent">
          <el-select v-model="form.maintenanceExamineStatus" placeholder="请选择"
            :disabled="props.rowFromProps.data.createBy != currentUserId">
            <el-option label="通过" value="通过"></el-option>
            <el-option label="驳回" value="驳回"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="maintenanceExamineComment"
          v-if="form.maintenanceExamineStatus == '驳回' || (status != null && status != undefined && status.includes('驳回'))">
          <el-input v-model="form.maintenanceExamineComment" type="textarea" autosize placeholder="请输入内容" />
        </el-form-item>
        <!-- <el-form-item label="截止时间" prop="maintenanceDeadline">
          <el-date-picker clearable v-model="form.maintenanceDeadline" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择截止时间">
          </el-date-picker>
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <!-- <el-button @click="cancel">取 消</el-button> -->
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Record">
import { listRecord, getRecord, addRecord, updateRecord } from "@/api/device/maintenanceTable/record";
import { updatePlan as groupUpdatePlan } from "@/api/device/maintenanceTable/groupPlan";
import { updatePlan as majorUpdatePlan } from "@/api/device/maintenanceTable/majorPlan";
import { getInfo } from "@/api/login";
import { onMounted, watch, ref, nextTick } from 'vue';
const { proxy } = getCurrentInstance();
import { format, parse } from 'date-fns';

const open = ref(false);
const existContent = ref(false);
const ifShowExamine = ref(false);
const ids = ref([]);
const title = ref("");
const status = ref("◎(待审核)");
const currentUserName = ref("");
const currentUserId = ref(0);
const emit = defineEmits(['getGroup', 'getMajor']);

const props = defineProps({
  rowFromProps: {
    type: Object,
  },
  showDialog: {
    type: Boolean
  },
  majorGroup: {
    type: String
  },
  timeOfClick: {
    type: String
  },
});


const data = reactive({
  form: {},
  groupForm: {
    groupId: '',
  },
  majorForm: {
    majorId: '',
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    maintenancePlanId: null,
    planMonthTime: null,
    completionDescription: null,
    imageAttachment: null,
    remainingIssuesDescription: null,
    remainingIssuesAnalysis: null,
    maintenanceExamineStatus: null,
    maintenanceExamineComment: null,
    maintenanceDeadline: null,
    majorGroup: null,//1—>专业，2->班组
  },
  rules: {
    completionDescription: [
      { required: true, message: "完成情况描述不能为空", trigger: "blur" }
    ],
    imageAttachment: [
      { required: true, message: "照片附件不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules, groupForm, majorForm } = toRefs(data);

function handleProps() {
  console.log("接收到的参数1————>", JSON.stringify(props.rowFromProps.data));
  console.log("接收到的参数majorGroup————>", JSON.stringify(props.majorGroup));
  console.log("接收到的参数createBy————>", props.rowFromProps.data.createBy);
  console.log("接收到的参数timeOfClick————>", JSON.stringify(props.timeOfClick));

  ifShowExamine.value = false;
  if (props.rowFromProps.data[props.rowFromProps.data.monthTime] != '' && props.rowFromProps.data[props.rowFromProps.data.monthTime] != null) {
    ifShowExamine.value = ((props.rowFromProps.data.createBy == currentUserId.value) && props.rowFromProps.data[props.rowFromProps.data.monthTime].includes('待审核'));
  }
  console.log(ifShowExamine.value)
  if (props.majorGroup == '班组') {
    groupForm.value.groupId = Number(props.rowFromProps.data.groupId);
  } else {
    majorForm.value.majorId = Number(props.rowFromProps.data.majorId);
  }

  getList();
}

onMounted(() => {
  handleProps()
});

getInfo().then(result => {
  currentUserId.value = result.user.userId
  currentUserName.value = result.user.userName
})

/** 查询记录列表 */
function getList() {
  if (props.timeOfClick) {
    const [yearStr, monthStr, weekPart] = props.timeOfClick.split('-');
    const weekNum = parseInt(weekPart.replace('W', ''), 10);
    const year = 2000 + parseInt(yearStr, 10);
    const month = parseInt(monthStr, 10) - 1;
    const day = weekNum * 7;
    const date = new Date(year, month, day);
    var formattedDate = format(date, 'yyyy-MM-dd');
    console.log(formattedDate);
  }
  existContent.value = false
  queryParams.value.maintenancePlanId = props.majorGroup == '班组' ? props.rowFromProps.data.groupId : props.rowFromProps.data.majorId
  queryParams.value.planMonthTime = props.rowFromProps.data.monthTime
  queryParams.value.majorGroup = props.majorGroup
  queryParams.value.createTime = formattedDate
  listRecord(queryParams.value).then(response => {
    if (response.total) {
      form.value = response.rows[0];
      form.value.maintenanceExamineStatus = ''
      status.value = props.rowFromProps.data[props.rowFromProps.data.monthTime]
      existContent.value = true
    } else {
      reset();
      existContent.value = false
      status.value = props.majorGroup == '班组' ? '◎(待审核)' : '';
      form.value.maintenancePlanId = props.majorGroup == '班组' ? props.rowFromProps.data.groupId : props.rowFromProps.data.majorId;
      form.value.planMonthTime = props.rowFromProps.data.monthTime;
      form.value.majorGroup = props.majorGroup;
    }
  });
}

// 取消按钮
// function cancel() {
//   // open.value = false;
//   emit('closeChild');
//   reset();
// }

// 表单重置
function reset() {
  form.value = {
    maintenanceId: null,
    maintenancePlanId: null,
    planMonthTime: null,
    completionDescription: null,
    imageAttachment: null,
    remainingIssuesDescription: null,
    remainingIssuesAnalysis: null,
    maintenanceExamineStatus: null,
    maintenanceExamineComment: null,
    createTime: null,
    createBy: null,
    maintenanceDeadline: null
  };
  proxy.resetForm("recordRef");
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _maintenanceId = row.maintenanceId || ids.value
  getRecord(_maintenanceId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改记录";
  });
}


function parseStatus(input) {
  const trimmedInput = input ? input.trim() : '';

  const match = trimmedInput.match(/^([^\(\)])(?:[^(]*\((.*?)\))?/);
  if (match) {
    const symbol = match[1]; // 提取符号
    const description = match[2] ? `(${match[2]})` : '';
    return { symbol: symbol, description: description };
  } else {
    // 如果没有匹配到，则检查是否为纯符号或空字符串
    if (!trimmedInput || trimmedInput.length === 0) {
      return { symbol: '\u200B', description: '' }; // 返回零宽度空格表示无符号，无描述
    } else {
      // 输入仅包含符号
      return { symbol: trimmedInput, description: '' };
    }
  }
}


/** 提交按钮 */
function submitForm() {
  proxy.$refs["recordRef"].validate(valid => {
    if (valid) {
      if (form.value.maintenanceId != null) {
        form.value.updateTime = format(new Date(), 'yyyy-MM-dd HH:mm:ss')
        updateRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          if (props.majorGroup == '班组') {
            if (props.rowFromProps.data[props.rowFromProps.data.monthTime] != null && props.rowFromProps.data[props.rowFromProps.data.monthTime] != undefined && (props.rowFromProps.data[props.rowFromProps.data.monthTime].includes('待审核'))) {
              if (form.value.maintenanceExamineStatus == '通过') {
                groupForm.value[props.rowFromProps.data.monthTime] = parseStatus(status.value).symbol
                groupForm.value['lastCompleteTime'] = format(new Date(), 'yyyy-MM-dd HH:mm:ss')
              } else if (form.value.maintenanceExamineStatus == '驳回') {
                groupForm.value[props.rowFromProps.data.monthTime] = `${parseStatus(status.value).symbol}(驳回)`
              } else {
                groupForm.value[props.rowFromProps.data.monthTime] = `${parseStatus(status.value).symbol}(待审核)`
              }
            } else {
              // console.log('进入非待审核')
              groupForm.value[props.rowFromProps.data.monthTime] = status.value
            }
            groupUpdatePlan(groupForm.value).then(result => {
              emit('getGroup')
            })
          } else {
            if (props.rowFromProps.data[props.rowFromProps.data.monthTime] != null && props.rowFromProps.data[props.rowFromProps.data.monthTime] != undefined && (props.rowFromProps.data[props.rowFromProps.data.monthTime].includes('待审核'))) {
              if (form.value.maintenanceExamineStatus == '通过') {
                majorForm.value[props.rowFromProps.data.monthTime] = parseStatus(status.value).symbol
                majorForm.value['lastCompleteTime'] = format(new Date(), 'yyyy-MM-dd HH:mm:ss')
              } else if (form.value.maintenanceExamineStatus == '驳回') {
                majorForm.value[props.rowFromProps.data.monthTime] = `${parseStatus(status.value).symbol}(驳回)`
              } else {
                majorForm.value[props.rowFromProps.data.monthTime] = `${parseStatus(status.value).symbol}(待审核)`
              }
            } else {
              // console.log('进入非待审核')
              majorForm.value[props.rowFromProps.data.monthTime] = status.value
            }
            majorUpdatePlan(majorForm.value).then(result => {
              emit('getMajor')
            })
          }
        });
      } else {
        form.value.majorGroup = props.majorGroup;
        addRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          if (props.majorGroup == '班组') {
            groupForm.value[props.rowFromProps.data.monthTime] = status.value
            console.log('groupForm---->', groupForm.value)
            groupUpdatePlan(groupForm.value).then(result => {
              emit('getGroup');
            })
          } else {
            majorForm.value[props.rowFromProps.data.monthTime] = status.value
            console.log('majorForm---->', majorForm.value)
            majorUpdatePlan(majorForm.value).then(result => {
              emit('getMajor');
            })
          }
        });
      }
    }
  });
}

watch(() => props.showDialog, () => {
  open.value = true
  handleProps()
}, { deep: true });

</script>
