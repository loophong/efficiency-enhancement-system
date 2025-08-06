<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会议时间" prop="meetingTime">
        <el-date-picker clearable
          v-model="queryParams.meetingTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择会议时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="会议地点" prop="meetingLocation">
        <el-input
          v-model="queryParams.meetingLocation"
          placeholder="请输入会议地点"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="会议主持" prop="meetingChairman">
        <el-input
          v-model="queryParams.meetingChairman"
          placeholder="请输入会议主持"
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
          v-hasPermi="['security:MeetingRecord:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:MeetingRecord:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:MeetingRecord:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:MeetingRecord:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="MeetingRecordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100"/>
      <el-table-column label="会议时间" align="center" prop="meetingTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.meetingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="会议简要内容" align="center" prop="meetingContent" />
      <el-table-column label="会议地点" align="center" prop="meetingLocation" />
      <el-table-column label="参加会议人员" align="center" prop="attendees" />
      <el-table-column label="会议主持" align="center" prop="meetingChairman" />
      <el-table-column label="会议记录人" align="center" prop="meetingNotes" />
      <el-table-column label="会议详细记录" align="center" prop="meetingContent2" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:MeetingRecord:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:MeetingRecord:remove']">删除</el-button>
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

    <!-- 添加或修改安全会议记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="MeetingRecordRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会议时间" prop="meetingTime">
          <el-date-picker clearable
            v-model="form.meetingTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择会议时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="会议简要内容" prop="meetingContent">
          <el-input v-model="form.meetingContent" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="会议地点" prop="meetingLocation">
          <el-input v-model="form.meetingLocation" placeholder="请输入会议地点" />
        </el-form-item>
        <el-form-item label="参加会议人员" prop="attendees">
          <el-input v-model="form.attendees" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="会议主持" prop="meetingChairman">
          <el-input v-model="form.meetingChairman" placeholder="请输入会议主持" />
        </el-form-item>
        <el-form-item label="会议记录人" prop="meetingNotes">
          <el-input v-model="form.meetingNotes" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="会议详细记录">
          <editor v-model="form.meetingContent2" :min-height="192"/>
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

<script setup name="MeetingRecord">
import { listMeetingRecord, getMeetingRecord, delMeetingRecord, addMeetingRecord, updateMeetingRecord } from "@/api/security/MeetingRecord";

const { proxy } = getCurrentInstance();

const MeetingRecordList = ref([]);
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
    meetingTime: null,
    meetingContent: null,
    meetingLocation: null,
    attendees: null,
    meetingChairman: null,
    meetingNotes: null,
    meetingContent2: null
  },
  rules: {
    meetingTime: [
      { required: true, message: "会议时间不能为空", trigger: "blur" }
    ],
    meetingContent: [
      { required: true, message: "会议简要内容不能为空", trigger: "blur" }
    ],
    meetingLocation: [
      { required: true, message: "会议地点不能为空", trigger: "blur" }
    ],
    attendees: [
      { required: true, message: "参加会议人员不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全会议记录列表 */
function getList() {
  loading.value = true;
  listMeetingRecord(queryParams.value).then(response => {
    MeetingRecordList.value = response.rows;
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
    meetingTime: null,
    meetingContent: null,
    meetingLocation: null,
    attendees: null,
    meetingChairman: null,
    meetingNotes: null,
    meetingContent2: null
  };
  proxy.resetForm("MeetingRecordRef");
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
  title.value = "添加安全会议记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getMeetingRecord(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全会议记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["MeetingRecordRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateMeetingRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addMeetingRecord(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全会议记录编号为"' + _ids + '"的数据项？').then(function() {
    return delMeetingRecord(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/MeetingRecord/export', {
    ...queryParams.value
  }, `MeetingRecord_${new Date().getTime()}.xlsx`)
}

getList();
</script>
