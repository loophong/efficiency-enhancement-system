<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="班组" prop="teamName">
        <el-input
          v-model="queryParams.teamName"
          placeholder="请输入班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
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
          v-hasPermi="['security:OccupationaPersonnelList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:OccupationaPersonnelList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:OccupationaPersonnelList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:OccupationaPersonnelList:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="OccupationaPersonnelListList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="班组" align="center" prop="teamName" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="工种" align="center" prop="jobType" />
      <el-table-column label="接害因素" align="center" prop="exposureFactors" />
      <el-table-column label="上岗前" align="center" prop="preJobHealthCheckup" />
      <el-table-column label="在岗中" align="center" prop="duringJobHealthCheckup" />
      <el-table-column label="离岗时" align="center" prop="postJobHealthCheckup" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:OccupationaPersonnelList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:OccupationaPersonnelList:remove']">删除</el-button>
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

    <!-- 添加或修改危险职业岗位人员清单及管理台帐对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="OccupationaPersonnelListRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班组" prop="teamName">
          <el-input v-model="form.teamName" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="接害因素" prop="exposureFactors">
          <el-input v-model="form.exposureFactors" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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

<script setup name="OccupationaPersonnelList">
import { listOccupationaPersonnelList, getOccupationaPersonnelList, delOccupationaPersonnelList, addOccupationaPersonnelList, updateOccupationaPersonnelList } from "@/api/security/OccupationaPersonnelList";

const { proxy } = getCurrentInstance();

const OccupationaPersonnelListList = ref([]);
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
    teamName: null,
    name: null,
    gender: null,
    jobType: null,
    exposureFactors: null,
  },
  rules: {
    teamName: [
      { required: true, message: "班组不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "姓名不能为空", trigger: "blur" }
    ],
    gender: [
      { required: true, message: "性别不能为空", trigger: "change" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询危险职业岗位人员清单及管理台帐列表 */
function getList() {
  loading.value = true;
  listOccupationaPersonnelList(queryParams.value).then(response => {
    OccupationaPersonnelListList.value = response.rows;
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
    teamName: null,
    name: null,
    gender: null,
    jobType: null,
    exposureFactors: null,
    preJobHealthCheckup: [],
    duringJobHealthCheckup: [],
    postJobHealthCheckup: [],
    remarks: null
  };
  proxy.resetForm("OccupationaPersonnelListRef");
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
  title.value = "添加危险职业岗位人员清单及管理台帐";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getOccupationaPersonnelList(_id).then(response => {
    form.value = response.data;
    form.value.preJobHealthCheckup = form.value.preJobHealthCheckup.split(",");
    form.value.duringJobHealthCheckup = form.value.duringJobHealthCheckup.split(",");
    form.value.postJobHealthCheckup = form.value.postJobHealthCheckup.split(",");
    open.value = true;
    title.value = "修改危险职业岗位人员清单及管理台帐";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["OccupationaPersonnelListRef"].validate(valid => {
    if (valid) {
      form.value.preJobHealthCheckup = form.value.preJobHealthCheckup.join(",");
      form.value.duringJobHealthCheckup = form.value.duringJobHealthCheckup.join(",");
      form.value.postJobHealthCheckup = form.value.postJobHealthCheckup.join(",");
      if (form.value.id != null) {
        updateOccupationaPersonnelList(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addOccupationaPersonnelList(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除危险职业岗位人员清单及管理台帐编号为"' + _ids + '"的数据项？').then(function() {
    return delOccupationaPersonnelList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/OccupationaPersonnelList/export', {
    ...queryParams.value
  }, `OccupationaPersonnelList_${new Date().getTime()}.xlsx`)
}

getList();
</script>
