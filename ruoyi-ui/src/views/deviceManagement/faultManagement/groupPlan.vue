<template>
  <el-tabs type="border-card" style="padding: 6px">
    <el-tab-pane label="班组自主">
      <div class="app-container">
        <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
          <el-form-item label="序号" prop="orderNum">
            <el-input v-model="queryParams.orderNum" placeholder="请输入序号" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="执行班组" prop="executeGroup">
            <el-input v-model="queryParams.executeGroup" placeholder="请输入执行班组" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="保养周期" prop="maintenanceCycle">
            <el-input v-model="queryParams.maintenanceCycle" placeholder="请输入保养周期" clearable
              @keyup.enter="handleQuery" />
          </el-form-item>
          <!-- <el-form-item label="月度(1W)" prop="monthOne">
            <el-input v-model="queryParams.monthOne" placeholder="请输入月度(1W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(2W)" prop="monthTwo">
            <el-input v-model="queryParams.monthTwo" placeholder="请输入月度(2W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(3W)" prop="monthThree">
            <el-input v-model="queryParams.monthThree" placeholder="请输入月度(3W)" clearable @keyup.enter="handleQuery" />
          </el-form-item>
          <el-form-item label="月度(4W)" prop="monthFour">
            <el-input v-model="queryParams.monthFour" placeholder="请输入月度(4W)" clearable @keyup.enter="handleQuery" />
          </el-form-item> -->
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
            <el-dialog title="导入班组计划Excel文件" v-model="showDialog" width="30%">
              <el-form :model="form" ref="formRef" label-width="90px">
              </el-form>
              <span>上传表：</span>
              <span style="color:darkgreen">自主保全计划</span>
              <br />
              <br />
              <div class="upload-area">
                <i class="el-icon-upload"></i>
                <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
              </div>
              <br />
              <span class="dialog-footer">
                <el-button @click="showDialog = false">取 消</el-button>
                <el-button type="primary" @click="fileSend" v-if="buttonLoading === false">确 定</el-button>
                <el-button type="primary" :loading="true" v-else>上传中</el-button>
              </span>
            </el-dialog>
          </el-col>
          <!-- <el-col :span="1.5">
            <el-button type="warning" plain icon="Download" @click="handleExport"
              v-hasPermi="['maintenanceTable:plan:export']">导出</el-button>
          </el-col> -->
          <el-badge :value="listForTip.length" class="item" color="green">
            <el-button @click="openMessage = true">消息详情</el-button>
          </el-badge>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange" border stripe>
          <!-- <el-table-column type="selection" width="55" align="center" /> -->
          <!-- <el-table-column label="主键id" align="center" prop="groupId" /> -->
          <el-table-column label="序号" align="center" prop="orderNum" width="80" />
          <el-table-column label="设备类别" align="center" prop="deviceType" width="180" />
          <el-table-column label="执行班组" align="center" prop="executeGroup" width="160" />
          <el-table-column label="保养内容" align="center" prop="maintenanceContent" width="800">
            <template #default="scope">
              <span v-html="scope.row.maintenanceContent"></span>
            </template>
          </el-table-column>
          <el-table-column label="保养周期" align="center" prop="maintenanceCycle" width="80" />
          <el-table-column :label="firstWeek" align="center" prop="monthOne" width="180">
            <template #default="scope">
              <div @click="handleCellClick(scope.row, 'monthOne')" class="custom-cell">
                <span><span :style="{ fontSize: '36px' }">
                    {{ parseStatus(scope.row.monthOne).symbol }}
                  </span>
                  <span :style="{ fontSize: '16px' }">{{ parseStatus(scope.row.monthOne).description }}</span></span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="secondWeek" align="center" prop="monthTwo" width="180">
            <template #default="scope">
              <div @click="handleCellClick(scope.row, 'monthTwo')" class="custom-cell">
                <span :style="{ fontSize: '36px' }">
                  {{ parseStatus(scope.row.monthTwo).symbol }}
                </span>
                <span>{{ parseStatus(scope.row.monthTwo).description }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="thirdWeek" align="center" prop="monthThree" width="180">
            <template #default="scope">
              <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
                @click="handleCellClick(scope.row, 'monthThree')">
                <span>
                  <span :style="{ fontSize: '36px' }">{{ parseStatus(scope.row.monthThree).symbol
                  }}</span>
                  <span>{{ parseStatus(scope.row.monthThree).description }}</span>
                </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="fourthWeek" align="center" prop="monthFour" width="180">
            <template #default="scope">
              <div v-if="scope.row.monthFour == '待提交'" style="color: blue;"
                @click="handleCellClick(scope.row, 'monthFour')">
                {{ scope.row.monthFour }}
              </div>
              <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
                @click="handleCellClick(scope.row, 'monthFour')" v-else>
                <span>
                  <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.monthFour).symbol
                  }}</span>
                  <span>{{ parseStatus(scope.row.monthFour).description }}</span>
                </span>
              </div>
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

        <!-- 添加或修改班组计划保养对话框 -->
        <el-dialog :title="title" v-model="open" width="800px" append-to-body>
          <el-form ref="planRef" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="序号" prop="orderNum">
              <el-input v-model="form.orderNum" placeholder="请输入序号" />
            </el-form-item>
            <el-form-item label="设备类别" prop="deviceType">
              <el-input v-model="form.deviceType" placeholder="请输入设备类别" />
            </el-form-item>
            <el-form-item label="执行班组" prop="executeGroup">
              <el-input v-model="form.executeGroup" placeholder="请输入执行班组" />
            </el-form-item>
            <el-form-item label="保养内容" prop="maintenanceContent">
              <el-input v-model="form.maintenanceContent" placeholder="请输入保养内容" />
            </el-form-item>
            <el-form-item label="保养周期" prop="maintenanceCycle">
              <el-input v-model="form.maintenanceCycle" placeholder="请输入保养周期" />
            </el-form-item>
            <!-- <el-form-item label="月度(1W)" prop="monthOne">
              <el-select v-model="form.monthOne" placeholder="请选择">
                <el-option label="◎(检查保养项目)" value="◎"></el-option>
                <el-option label="◆(精度调整计划)" value="◆"></el-option>
                <el-option label="☆(检修计划)" value="☆"></el-option>
                <el-option label="空" value=""></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="月度(2W)" prop="monthTwo">
              <el-select v-model="form.monthTwo" placeholder="请选择">
                <el-option label="◎(检查保养项目)" value="◎"></el-option>
                <el-option label="◆(精度调整计划)" value="◆"></el-option>
                <el-option label="☆(检修计划)" value="☆"></el-option>
                <el-option label="空" value=""></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="月度(3W)" prop="monthThree">
              <el-select v-model="form.monthThree" placeholder="请选择">
                <el-option label="◎(检查保养项目)" value="◎"></el-option>
                <el-option label="◆(精度调整计划)" value="◆"></el-option>
                <el-option label="☆(检修计划)" value="☆"></el-option>
                <el-option label="空" value=""></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="月度(4W)" prop="monthFour">
              <el-select v-model="form.monthFour" placeholder="请选择">
                <el-option label="◎(检查保养项目)" value="◎"></el-option>
                <el-option label="◆(精度调整计划)" value="◆"></el-option>
                <el-option label="☆(检修计划)" value="☆"></el-option>
                <el-option label="空" value=""></el-option>
              </el-select>
            </el-form-item> -->
          </el-form>
          <template #footer>
            <div class="dialog-footer">
              <el-button type="primary" @click="submitForm">确 定</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </template>
        </el-dialog>
      </div>
      <el-dialog title="消息提醒" v-model="openMessage" width="1600px" append-to-body>
        <el-button type="primary" @click="watchCurrentWeek" v-if="!showCurrent">仅查看本周</el-button>
        <el-button type="primary" @click="watchAllWeek" v-if="showCurrent">查看所有</el-button>
        <el-table v-loading="loading" :data="listForTip" @selection-change="handleSelectionChange" border stripe>
          <el-table-column label="序号" align="center" prop="orderNum" width="60" />
          <el-table-column label="设备类别" align="center" prop="deviceType" width="120" />
          <el-table-column label="执行班组" align="center" prop="executeGroup" width="160" />
          <el-table-column label="保养内容" align="center" prop="maintenanceContent">
            <template #default="scope">
              <span v-html="scope.row.maintenanceContent"></span>
            </template>
          </el-table-column>
          <el-table-column label="保养周期" align="center" prop="maintenanceCycle" width="80" />
          <el-table-column :label="firstWeek" align="center" prop="monthOne" width="120">
            <template #default="scope">
              <div @click="handleCellClick(scope.row, 'monthOne')" class="custom-cell">
                <span><span :style="{ fontSize: '36px' }">
                    {{ parseStatus(scope.row.monthOne).symbol }}
                  </span>
                  <span :style="{ fontSize: '16px' }">{{ parseStatus(scope.row.monthOne).description }}</span></span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="secondWeek" align="center" prop="monthTwo" width="120">
            <template #default="scope">
              <div @click="handleCellClick(scope.row, 'monthTwo')" class="custom-cell">
                <span :style="{ fontSize: '36px' }">
                  {{ parseStatus(scope.row.monthTwo).symbol }}
                </span>
                <span>{{ parseStatus(scope.row.monthTwo).description }}</span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="thirdWeek" align="center" prop="monthThree" width="120">
            <template #default="scope">
              <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
                @click="handleCellClick(scope.row, 'monthThree')">
                <span>
                  <span :style="{ fontSize: '36px' }">{{ parseStatus(scope.row.monthThree).symbol
                  }}</span>
                  <span>{{ parseStatus(scope.row.monthThree).description }}</span>
                </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column :label="fourthWeek" align="center" prop="monthFour" width="120">
            <template #default="scope">
              <div v-if="scope.row.monthFour == '待提交'" style="color: blue;"
                @click="handleCellClick(scope.row, 'monthFour')">
                {{ scope.row.monthFour }}
              </div>
              <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
                @click="handleCellClick(scope.row, 'monthFour')" v-else>
                <span>
                  <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.monthFour).symbol
                  }}</span>
                  <span>{{ parseStatus(scope.row.monthFour).description }}</span>
                </span>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </el-tab-pane>

    <el-tab-pane label="专业计划">
      <major-plan></major-plan>
    </el-tab-pane>
  </el-tabs>
  <record ref="recordRef" :rowFromProps="rowForProps" :showDialog="showDialogNull" :majorGroup="majorGroup"
    :timeOfClick="timeOfClick" v-if="showRecord" @getGroup="getList"></record>
</template>

<script setup name="Plan">
import { listPlan, getPlan, delPlan, addPlan, updatePlan, uploadFile, tipList, resetPlan } from "@/api/device/maintenanceTable/groupPlan";
import majorPlan from "./majorPlan.vue"
import record from "./record.vue"
import { ElMessage } from 'element-plus'
import { getInfo } from "@/api/login";
import { ElNotification } from 'element-plus'
import { subDays, format } from 'date-fns';


const { proxy } = getCurrentInstance();
const recordRef = ref(null);
const planList = ref([]);
const listForTip = ref([]);
const tmpListForTip = ref([]);
const open = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const openMessage = ref(false);
const showSearch = ref(true);
const showRecord = ref(false);
const showDialogNull = ref(false);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const notifyOffset = ref(0);
const showDialog = ref(false);
const showCurrent = ref(false);
const title = ref("");
const firstWeek = ref(format(subDays(getMonday(new Date()), 21), 'yy-MM-dd'));;
const secondWeek = ref(format(subDays(getMonday(new Date()), 14), 'yy-MM-dd'));;
const thirdWeek = ref(format(subDays(getMonday(new Date()), 7), 'yy-MM-dd'));;
const fourthWeek = ref(format(getMonday(new Date()), 'yy-MM-dd'));;
const timeOfClick = ref("");
const currentUserName = ref("");
const currentUserId = ref(0);

const rowForProps = reactive({})
const majorGroup = ref('班组')

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    orderNum: null,
    deviceType: null,
    executeGroup: null,
    maintenanceContent: null,
    maintenanceCycle: null,
    monthOne: null,
    monthTwo: null,
    monthThree: null,
    monthFour: null
  },
  rules: {
    orderNum: [
      { required: true, message: "序号不能为空", trigger: "blur" }
    ],
    deviceType: [
      { required: true, message: "设备类别不能为空", trigger: "blur" }
    ],
    executeGroup: [
      { required: true, message: "执行班组不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

function showNotification(keyword, keyword2) {
  // 根据传递进来的参数计算新的偏移量
  const newOffset = notifyOffset.value + 65;
  // 显示通知
  ElNotification({
    title: '班组自主',
    dangerouslyUseHTMLString: true,
    message: `<span>您有</span><span style="color: #2d7ad6;">[${keyword}]</span><span>${keyword2 || '审核中'}，请及时处理</span>`,
    type: "warning",
    offset: newOffset,
    duration: 7000,
  })
  // 更新偏移量，避免堆叠
  notifyOffset.value = newOffset;
}

console.log(format(subDays(new Date(), 7), 'yyyy-MM-dd HH:mm:ss'));
//获取当前登录用户信息
getInfo().then(result => {
  currentUserId.value = result.user.userId
  currentUserName.value = result.user.userName
})
resetPlan().then(result => {
  console.log(result)
  if (result != '当前时间为当前周，无需处理。') {
    window.location.reload();
  }
});
//点击单元格
function handleCellClick(row, cell) {
  console.log('id:', row.groupId);
  // console.log('row:', row);
  const content = row[cell];
  console.log('内容：', content);
  if (cell == 'monthOne') {
    timeOfClick.value = firstWeek.value
  } else if (cell == 'monthTwo') {
    timeOfClick.value = secondWeek.value
  } else if (cell == 'monthThree') {
    timeOfClick.value = thirdWeek.value
  } else if (cell == 'monthFour') {
    timeOfClick.value = fourthWeek.value
  }
  console.log('timeOfClick:', timeOfClick.value)
  row.monthTime = cell
  rowForProps.data = row
  showDialogNull.value = !showDialogNull.value
  showRecord.value = true
}


//判断为是否是本周
function isThisWeek(dateString) {
  const checkDate = new Date(dateString);
  checkDate.setHours(0, 0, 0, 0); // 标准化为目标日期的00:00:00

  const now = new Date();
  const currentStart = getStartOfWeek(now);
  const nextWeekStart = new Date(currentStart);
  nextWeekStart.setDate(currentStart.getDate() + 7); // 下周一 00:00:00

  return checkDate >= currentStart && checkDate < nextWeekStart;
}

function watchCurrentWeek() {
  showCurrent.value = true
  tmpListForTip.value = listForTip.value
  listForTip.value = listForTip.value.filter(item => {
    return isThisWeek(item.updateTime)
  })
}
function watchAllWeek() {
  showCurrent.value = false
  if (Array.isArray(tmpListForTip.value) && (tmpListForTip.value.length != 0)) {
    listForTip.value = tmpListForTip.value
  }
}

function getStartOfWeek(date) {
  const d = new Date(date);
  d.setHours(0, 0, 0, 0); // 清除时间部分
  const day = d.getDay(); // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
  const diff = (day === 0 ? -6 : 1 - day); // 调整为周一
  d.setDate(d.getDate() + diff);
  return d;
}

function getMonday(date) {
  const day = date.getDay();
  const daysToSubtract = (day + 6) % 7;
  return subDays(date, daysToSubtract);
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


/** 查询班组计划保养列表 */
function getList() {
  loading.value = true;
  listPlan(queryParams.value).then(response => {
    planList.value = response.rows;
    total.value = response.total;

    //消息通知
    tipList().then(result => {
      listForTip.value = result.rows
      const tmp = JSON.parse(JSON.stringify(result.rows))
      tmp.forEach(element => {
        if ((element.createBy == currentUserId.value) && element.monthOne != '' && element.monthOne != null && element.monthOne.includes('待审核')) {
          showNotification('自主保全计划')
        }
        if ((element.createBy == currentUserId.value) && element.monthTwo != '' && element.monthTwo != null && element.monthTwo.includes('待审核')) {
          showNotification('自主保全计划')
        }
        if ((element.createBy == currentUserId.value) && element.monthThree != '' && element.monthThree != null && element.monthThree.includes('待审核')) {
          showNotification('自主保全计划')
        }
        if ((element.createBy == currentUserId.value) && element.monthFour != '' && element.monthFour != null && element.monthFour.includes('待审核')) {
          showNotification('自主保全计划')
        }
      });
      console.log('ListForTipGroup------>', listForTip.value)
      listForTip.value.forEach(i => {
        if (i.monthFour == null || i.monthFour === '') {
          // const maintenanceCycle = i.maintenanceCycle;
          // const lastCompleteTime = i.lastCompleteTime;
          if (i.maintenanceCycle == '1') {
            i.monthFour = '待提交'
            showNotification('自主保全计划', '待提交')
          } else if (i.maintenanceCycle == '2' && !i.monthThree) {
            i.monthFour = '待提交'
            showNotification('自主保全计划', '待提交')
          } else if (i.maintenanceCycle == '3' && !i.monthTwo && !i.monthThree) {
            i.monthFour = '待提交'
            showNotification('自主保全计划', '待提交')
          } else if (i.maintenanceCycle == '4' && !i.monthOne && !i.monthTwo && !i.monthThree) {
            i.monthFour = '待提交'
            showNotification('自主保全计划', '待提交')
          }
          // try {
          //   let date = new Date(lastCompleteTime);
          //   let weeks = parseInt(maintenanceCycle, 10);
          //   if (isNaN(weeks)) {
          //     throw new Error("maintenanceCycle 必须是有效的数字");
          //   }
          //   date.setDate(date.getDate() + weeks * 7); 1
          //   const formattedDate = format(date, 'yyyy-MM-dd HH:mm:ss');
          //   if (isThisWeek(formattedDate)) {
          //     i.monthFour = '待提交'
          //     showNotification('自主保全计划', '待提交')
          //   }
          // } catch (error) {
          //   console.error(`处理设备 ${i.deviceId} 时出错：`, error.message);
          // }
        }
      });
    })
    const tmpHandle = JSON.parse(JSON.stringify(response.rows))
    console.log({ tmpHandle })

    planList.value.forEach(i => {
      if (i.monthFour == null || i.monthFour === '') {
        if (i.maintenanceCycle == '1') {
          i.monthFour = '待提交'
          showNotification('自主保全计划', '待提交')
        } else if (i.maintenanceCycle == '2' && !i.monthThree) {
          i.monthFour = '待提交'
          showNotification('自主保全计划', '待提交')
        } else if (i.maintenanceCycle == '3' && !i.monthTwo && !i.monthThree) {
          i.monthFour = '待提交'
          showNotification('自主保全计划', '待提交')
        } else if (i.maintenanceCycle == '4' && !i.monthOne && !i.monthTwo && !i.monthThree) {
          i.monthFour = '待提交'
          showNotification('自主保全计划', '待提交')
        }
        // const maintenanceCycle = i.maintenanceCycle;
        // const lastCompleteTime = i.lastCompleteTime;
        // try {
        //   let date = new Date(lastCompleteTime);
        //   let weeks = parseInt(maintenanceCycle, 10);
        //   if (isNaN(weeks)) {
        //     throw new Error("maintenanceCycle 必须是有效的数字");
        //   }
        //   date.setDate(date.getDate() + weeks * 7); 1
        //   const formattedDate = format(date, 'yyyy-MM-dd HH:mm:ss');
        //   if (isThisWeek(formattedDate)) {
        //     i.monthFour = '待提交'
        //     showNotification('自主保全计划', '待提交')
        //   }
        // } catch (error) {
        //   console.error(`处理设备 ${i.deviceId} 时出错：`, error.message);
        // }
      }
    });
    listForTip.value.forEach(element => {
      if ((element.createBy == currentUserId.value) && element.monthOne != '' && element.monthOne != null && element.monthOne.includes('待审核')) {
        showNotification('自主保全计划')
      }
      if ((element.createBy == currentUserId.value) && element.monthTwo != '' && element.monthTwo != null && element.monthTwo.includes('待审核')) {
        showNotification('自主保全计划')
      }
      if ((element.createBy == currentUserId.value) && element.monthThree != '' && element.monthThree != null && element.monthThree.includes('待审核')) {
        showNotification('自主保全计划')
      }
      if ((element.createBy == currentUserId.value) && element.monthFour != '' && element.monthFour != null && element.monthFour.includes('待审核')) {
        showNotification('自主保全计划')
      }
    });
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
    groupId: null,
    orderNum: null,
    deviceType: null,
    executeGroup: null,
    maintenanceContent: null,
    maintenanceCycle: null,
    monthOne: null,
    monthTwo: null,
    monthThree: null,
    monthFour: null
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
  ids.value = selection.map(item => item.groupId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加班组计划保养";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _groupId = row.groupId || ids.value
  getPlan(_groupId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改班组计划保养";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["planRef"].validate(valid => {
    if (valid) {
      if (form.value.groupId != null) {
        updatePlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.createBy = currentUserId.value
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
  const _groupIds = row.groupId || ids.value;
  proxy.$modal.confirm('是否确认删除班组计划保养编号为"' + _groupIds + '"的数据项？').then(function () {
    return delPlan(_groupIds);
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
  uploadFile(formData, `/fault/groupPlan/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      showDialog.value = false
      getList();
      buttonLoading.value = false
      // this.isLoading = false;
    })
    .catch(error => {
      // 处理上传失败的情况
      ElMessage({
        message: `上传失败:${error}`,
        type: 'error',
      });
      // this.$message.error("上传失败，请重试");
      showDialog.value = false
      buttonLoading.value = false
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
