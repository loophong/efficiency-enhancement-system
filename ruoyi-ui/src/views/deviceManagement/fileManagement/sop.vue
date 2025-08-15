<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="80px">
      <!-- <el-form-item label="关联id" prop="sopCombineId">
        <el-input v-model="queryParams.sopCombineId" placeholder="请输入关联id" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item label="设备编号" prop="sopNum">
        <el-input v-model="queryParams.sopNum" placeholder="请输入设备编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="设备名称" prop="sopName">
        <el-input v-model="queryParams.sopName" placeholder="请输入设备名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <!-- <el-form-item label="SOP类型" prop="sopMaintenance">
        <el-input v-model="queryParams.sopMaintenance" placeholder="请输入SOP类型" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <!-- <el-form-item label="上传时间" style="width: 308px">
        <el-date-picker v-model="daterangeUpTime" value-format="YYYY-MM-DD" type="daterange" range-separator="-"
          start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item> -->
      <!-- <el-form-item label="是否是历史版本" prop="ifHistory">
        <el-input v-model="queryParams.ifHistory" placeholder="请输入是否是历史版本" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['file:sop:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['file:sop:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['file:sop:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['file:sop:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="Refresh" @click="resetGetList" v-hasPermi="['file:sop:list']">重置</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="fileList" @selection-change="handleSelectionChange" border stripe>
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="设备编号" align="center" prop="sopNum" width="160" />
      <el-table-column label="设备名称" align="center" prop="sopName" width="160" />
      <el-table-column label="保养文件" align="center" prop="sopMaintenance">
        <template #default="scope">
          <el-button @click="handlePreview(scope.row)" v-if="scope.row.sopMaintenance">
            {{ formatFileInfo(scope.row.sopMaintenance) }}
          </el-button>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="维修文件" align="center" prop="sopRepair">
        <template #default="scope">
          <!-- 调用 formatFileInfo 处理文件信息 -->
          <div v-if="scope.row.sopRepair && scope.row.sopRepair !== ''">
            <!-- 遍历格式化后的文件信息 -->
            <div v-for="(file, index) in parseFileInfo(scope.row.sopRepair)" :key="index">
              <el-button @click="handlePreview2(scope.row.sopRepair, index, scope.row.sopNum, scope.row.sopName)"
                style="margin-bottom: 5px; display: block;">
                {{ file }}
              </el-button>
            </div>
          </div>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="修改人" align="center" prop="updateBy" v-if="currentStatus == '历史'" width="120" />
      <el-table-column label="修改时间" align="center" prop="updateTime" v-if="currentStatus == '历史'" width="120">
        <!-- <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template> -->
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['file:sop:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['file:sop:remove']">删除</el-button>
          <el-button link type="primary" icon="InfoFilled" @click="getHistoryList(scope.row)"
            v-hasPermi="['file:sop:list']" v-if="scope.row.ifHistory == '0'">查看历史</el-button>
          <el-button link type="primary" icon="Back" @click="resetGetList(scope.row)"
            v-if="scope.row.ifHistory == '1'">返回</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getWhich" />

    <!-- 添加或修改SOP文件管理对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="fileRef" :model="form" :rules="rules" label-width="80px">
        <!-- <el-form-item label="关联id" prop="sopCombineId">
          <el-input v-model="form.sopCombineId" placeholder="请输入关联id" />
        </el-form-item> -->
        <el-form-item label="设备编号" prop="sopNum">
          <el-input v-model="form.sopNum" placeholder="请输入设备编号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="sopName">
          <el-input v-model="form.sopName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="保养文件" prop="sopMaintenance">
          <file-upload :limit="1" v-model="form.sopMaintenance" />
        </el-form-item>
        <el-form-item label="维修文件" prop="sopRepair">
          <file-upload v-model="form.sopRepair" />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-drawer :title="drawerTitle" v-model="openDrawer" size="40%" :before-close="handleClose">
      <vue-office-docx v-if="showDocx" :src="drawerUrl" style="height: 100vh;" @rendered="renderedHandler"
        @error="errorHandler" />
      <vue-office-excel v-if="showExcel" :src="drawerUrl" style="height: 100vh;" />
      <vue-office-pdf v-if="showPdf" :src="drawerUrl" style="height: 100vh;" @rendered="renderedHandler"
        @error="errorHandler" />
    </el-drawer>
  </div>
</template>

<script setup name="Sop">
import { listFile, getFile, delFile, addFile, updateFile } from "@/api/device/fileTable/sop";
import { getInfo } from "@/api/login";
import { ElMessage } from 'element-plus'
import { format } from 'date-fns';
import { ElMessageBox } from 'element-plus'

import VueOfficeDocx from '@vue-office/docx'
import VueOfficeExcel from '@vue-office/excel'
import VueOfficePdf from '@vue-office/pdf'
import '@vue-office/docx/lib/index.css'
import '@vue-office/excel/lib/index.css'

const { proxy } = getCurrentInstance();

const fileList = ref([]);
const open = ref(false);
const openDrawer = ref(false);
const showDocx = ref(false);
const showExcel = ref(false);
const showPdf = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const drawerTitle = ref("");
const drawerUrl = ref("");
const currentStatus = ref("默认");
const daterangeUpTime = ref([]);
const multiButton = ref([]);
const route = useRoute();
const router = useRouter();
const currentUserName = ref("");
const currentUserId = ref(0);

var routerDeviceNum = route.query.deviceNum;

const data = reactive({
  form: {},
  formForHistory: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    sopCombineId: null,
    sopNum: null,
    sopName: null,
    sopMaintenance: null,
    upTime: null,
    ifHistory: null,
    sopRepair: null
  },
  rules: {
  }
});

const { queryParams, form, rules, formForHistory } = toRefs(data);

const handleRouteParams = () => {
  if (routerDeviceNum) {
    console.log('跳转进入---→Received deviceNum:', routerDeviceNum);
    data.queryParams.sopNum = routerDeviceNum;
    getList(); // 假设需要根据路由参数重新获取列表数据
  } else {
    console.log('非跳转进入');
    // 可以选择性地在这里添加其他逻辑
  }
};


watch(() => ({ deviceNum: route.query.deviceNum, from: route.query.from }),
  (newParams) => {
    console.log('检测到新参数 deviceNum -->', newParams.deviceNum);
    console.log('检测到新参数 from -->', newParams.from);
    if (newParams.from && newParams.from != 'sop') {
      routerDeviceNum = newParams.deviceNum;
      handleRouteParams(newParams);
    }
  }
);

// 使用 Vue 的生命周期钩子，在组件挂载完成后检查路由参数
onMounted(() => {
  handleRouteParams();
});

function handlePreview(input) {
  showDocx.value = false
  showExcel.value = false
  showPdf.value = false
  const firstFaultFile = input.sopMaintenance.split(',')[0].trim();
  if (firstFaultFile && firstFaultFile.includes('doc')) {
    showDocx.value = true
  } else if (firstFaultFile && firstFaultFile.includes('xl')) {
    showExcel.value = true
  } else if (firstFaultFile && firstFaultFile.includes('pdf')) {
    showPdf.value = true
  }


  const uploadDateMatch = firstFaultFile.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
  drawerTitle.value = `${input.sopName}(${input.sopNum})  上传日期：${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`
  drawerUrl.value = `${import.meta.env.VITE_APP_BASE_API}${firstFaultFile}`
  openDrawer.value = true
}

function handlePreview2(input, index, num, name) {
  // console.log({ input })
  // console.log({ index })
  showDocx.value = false
  showExcel.value = false
  showPdf.value = false
  const firstFaultFile = input.split(',')[index].trim();
  if (firstFaultFile && firstFaultFile.includes('doc')) {
    showDocx.value = true
  } else if (firstFaultFile && firstFaultFile.includes('xl')) {
    showExcel.value = true
  } else if (firstFaultFile && firstFaultFile.includes('pdf')) {
    showPdf.value = true
  }
  const uploadDateMatch = firstFaultFile.match(/\/(\d{4})\/(\d{2})\/(\d{2})\//);
  drawerTitle.value = `${name}(${num})  上传日期：${uploadDateMatch[1]}/${uploadDateMatch[2]}/${uploadDateMatch[3]}`
  drawerUrl.value = `${import.meta.env.VITE_APP_BASE_API}${firstFaultFile}`
  openDrawer.value = true
}

// docx作为参数通过父组件传参
const renderedHandler = () => {
  console.log("渲染完成")
}
const errorHandler = () => {
  console.log("渲染失败")
}

function handleClose(done) {
  ElMessageBox.confirm(`确认关闭吗?`)
    .then(() => {
      openDrawer.value = false
    })
    .catch(() => {
      // catch error
    })
}


function resetGetList() {
  currentStatus.value = '默认';
  queryParams.value = {
    pageNum: 1,
    pageSize: 10,
    sopCombineId: null,
    sopNum: null,
    sopName: null,
    sopMaintenance: null,
    upTime: null,
    ifHistory: null,
    sopRepair: null
  }
  getList();
}

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

function parseFileInfo(fileInfo) {
  // 调用 formatFileInfo 格式化文件信息
  const formattedInfo = formatFileInfo(fileInfo);
  // 将格式化后的字符串按 <br> 拆分为数组
  return formattedInfo.split('<br>').filter(item => item.trim() !== '');
}

/** 查询SOP文件管理列表 */
function getList() {
  loading.value = true;
  queryParams.value.ifHistory = currentStatus.value == '默认' ? '0' : '1';
  queryParams.value.params = {};
  if (null != daterangeUpTime && '' != daterangeUpTime && Array.isArray(daterangeUpTime.value)) {
    queryParams.value.params["beginUpTime"] = daterangeUpTime.value[0];
    queryParams.value.params["endUpTime"] = daterangeUpTime.value[1];
  }
  listFile(queryParams.value).then(response => {
    fileList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 查询SOP文件历史列表 */
function getHistoryList(row) {
  loading.value = true;
  currentStatus.value = '历史'
  queryParams.value.sopNum = row.sopNum
  queryParams.value.ifHistory = '1';
  queryParams.value.params = {};
  if (null != daterangeUpTime && '' != daterangeUpTime) {
    queryParams.value.params["beginUpTime"] = daterangeUpTime.value[0];
    queryParams.value.params["endUpTime"] = daterangeUpTime.value[1];
  }
  listFile(queryParams.value).then(response => {
    fileList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

getInfo().then(result => {
  currentUserId.value = result.user.userId
  currentUserName.value = result.user.userName
})

function getWhich() {
  if (currentStatus.value == '默认') {
    getList();
  } else {
    getHistoryList();
  }
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    sopFileId: null,
    sopCombineId: null,
    sopNum: null,
    sopName: null,
    sopMaintenance: null,
    upTime: null,
    ifHistory: null,
    sopRepair: null
  };
  proxy.resetForm("fileRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeUpTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.sopFileId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加SOP文件管理";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  formForHistory.value = {};
  const _sopFileId = row.sopFileId || ids.value
  getFile(_sopFileId).then(response => {
    form.value = { ...response.data };
    formForHistory.value = { ...response.data };
    formForHistory.value.sopFileId = null;
    formForHistory.value.ifHistory = '1';
    formForHistory.value.updateBy = currentUserName.value;
    formForHistory.value.updateTime = format(new Date(), 'yyyy-MM-dd HH:mm:ss');
    open.value = true;
    title.value = "修改SOP文件管理";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["fileRef"].validate(valid => {
    if (valid) {
      if (form.value.sopFileId != null) {
        updateFile(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          addFile(formForHistory.value)
          open.value = false;
          getList();
        });
      } else {
        form.value.ifHistory = '0';
        form.value.createBy = currentUserName.value;
        addFile(form.value).then(response => {
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
  const _sopFileIds = row.sopFileId || ids.value;
  proxy.$modal.confirm('是否确认删除SOP文件管理编号为"' + _sopFileIds + '"的数据项？').then(function () {
    return delFile(_sopFileIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('file/sop/export', {
    ...queryParams.value
  }, `Sop_${new Date().getTime()}.xlsx`)
}

getList();
</script>
