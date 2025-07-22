<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input v-model="queryParams.supplierCode" placeholder="请输入供应商代码" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input v-model="queryParams.supplierName" placeholder="请输入供应商名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>

      <el-form-item label="发生时间" prop="uploadTime">
        <el-date-picker clearable v-model="queryParams.uploadTime" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择质量事故发生时间">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="填报人" prop="uploadName">
        <el-input v-model="queryParams.uploadName" placeholder="请输入填报人" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <!-- <el-form-item label="事故描述" prop="incidentDescription">
        <el-input v-model="queryParams.incidentDescription" placeholder="请输入事故描述" clearable
          @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['supplier:incidents:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['supplier:incidents:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['supplier:incidents:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['supplier:incidents:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="incidentsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="${comment}" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="质量事故发生时间" align="center" prop="uploadTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事故描述" align="center" prop="incidentDescription" />
      <!-- <el-table-column label="分数" align="center" prop="score" /> -->
      <!-- <el-table-column label="填报人" align="center" prop="uploadName" />
      <el-table-column label="备选1" align="center" prop="one" />
      <el-table-column label="备选2" align="center" prop="two" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['supplier:incidents:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['supplier:incidents:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改质量事故对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="incidentsRef" :model="form" :rules="rules" label-width="100px">

        <el-form-item label="供应商代码" prop="supplierCode">
            <el-input v-model="form.supplierCode" disabled placeholder="请输入供应商代码" />
        </el-form-item>

        <el-form-item label="供应商名称" prop="supplierName">
          <el-select v-model="form.supplierName" clearable filterable placeholder="请选择或输入供应商名称" style="width: 240px">
            <el-option v-for="item in qualifiedList" :key="item.label" :label="item.label" :value="item.label" />
          </el-select>
        </el-form-item>

        <el-form-item label="发生时间" prop="uploadTime">
          <el-date-picker clearable v-model="form.uploadTime" type="date" value-format="YYYY-MM-DD"
            placeholder="请选择质量事故发生时间">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="填报人" prop="uploadName">
          <el-input v-model="form.uploadName" placeholder="请输入填报人" />
        </el-form-item> -->
        <el-form-item label="事故描述" prop="incidentDescription">
          <el-input v-model="form.incidentDescription" placeholder="请输入事故描述" />
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

<script setup name="Incidents">
import { listIncidents, getIncidents, delIncidents, addIncidents, updateIncidents } from "@/api/supplier/incidents";
import { all } from "@/api/supplier/qualified";
import { watch, ref, reactive } from 'vue';

const { proxy } = getCurrentInstance();

const incidentsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {
    supplierCode: '',
    supplierName: ''
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    supplierCode: null,
    supplierName: null,
    uploadTime: null,
    uploadName: null,
    incidentDescription: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询质量事故列表 */
function getList() {
  loading.value = true;
  listIncidents(queryParams.value).then(response => {
    incidentsList.value = response.rows;
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
    supplierCode: null,
    supplierName: null,
    uploadTime: null,
    uploadName: null,
    incidentDescription: null
  };
  proxy.resetForm("incidentsRef");
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
  title.value = "添加质量事故";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getIncidents(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改质量事故";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["incidentsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateIncidents(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addIncidents(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除？').then(function () {
    return delIncidents(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/incidents/export', {//
    ...queryParams.value
  }, `incidents_${new Date().getTime()}.xlsx`)
}


const qualifiedList = ref([]);

function getCodeAndName(row) {
  all().then(response => {
    console.log("请求的供应商数据" + JSON.stringify(response.data))
    response.data.forEach(element => {
      qualifiedList.value.push({
        label: element.supplierName,
        value: element.supplierCode
      })
    });
    console.log("请求的供应商数据" + JSON.stringify(qualifiedList))

  })
}

// 初始化时调用上面的方法
onMounted(() => {
  getCodeAndName()
})

//如果form.supplierName发生改变
watch(() => form.value.supplierName, (newValue, oldValue) => {
  console.log("form.supplierName发生改变", newValue, oldValue);
   qualifiedList.value.find(item => {
    if(item.label === newValue){
      form.value.supplierCode = item.value
    }
     
  });
  // let a = tmp.value
  console.log("form.tmp", form.value.supplierCode);
  // console.log("form.tmp", a);

});

getList();
</script>