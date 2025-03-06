<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input
          v-model="queryParams.supplierCode"
          placeholder="请输入供应商代码"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商名称" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          placeholder="请输入供应商名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="具体内容" prop="specificContent">
        <el-input
          v-model="queryParams.specificContent"
          placeholder="请输入具体内容"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配合程度" prop="cooperationDegree">
        <el-select v-model="queryParams.cooperationDegree" placeholder="请选择配合程度" clearable>
          <el-option
            v-for="dict in supplier_cooperation_degree"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传时间" prop="month">
        <el-date-picker clearable
          v-model="queryParams.month"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择上传时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="得分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入得分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="填报人" prop="uploadName">
        <el-input
          v-model="queryParams.uploadName"
          placeholder="请输入填报人"
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
          v-hasPermi="['supplier:degree:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:degree:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:degree:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:degree:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="degreeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="具体内容" align="center" prop="specificContent" />
      <el-table-column label="配合程度" align="center" prop="cooperationDegree" width="280">
        <template #default="scope">
          <dict-tag :options="supplier_cooperation_degree" :value="scope.row.cooperationDegree"/>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" align="center" prop="month" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.month, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="得分" align="center" prop="score" />
      <el-table-column label="填报人" align="center" prop="uploadName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:degree:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:degree:remove']">删除</el-button>
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

    <!-- 添加或修改新产品研发配合程度对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="degreeRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="具体内容" prop="specificContent">
          <el-input v-model="form.specificContent" placeholder="请输入具体内容" />
        </el-form-item>
        <el-form-item label="配合程度" prop="cooperationDegree">
          <el-select v-model="form.cooperationDegree" placeholder="请选择配合程度">
            <el-option
              v-for="dict in supplier_cooperation_degree"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上传时间" prop="month">
          <el-date-picker clearable
            v-model="form.month"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item>
        <el-form-item label="填报人" prop="uploadName">
          <el-input v-model="form.uploadName" placeholder="请输入填报人" />
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

<script setup name="Degree">
import { listDegree, getDegree, delDegree, addDegree, updateDegree } from "@/api/supplier/degree";

const { proxy } = getCurrentInstance();
const { supplier_cooperation_degree } = proxy.useDict('supplier_cooperation_degree');

const degreeList = ref([]);
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
    supplierCode: null,
    supplierName: null,
    specificContent: null,
    cooperationDegree: null,
    month: null,
    score: null,
    uploadName: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询新产品研发配合程度列表 */
function getList() {
  loading.value = true;
  listDegree(queryParams.value).then(response => {
    degreeList.value = response.rows;
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
    specificContent: null,
    cooperationDegree: null,
    month: null,
    score: null,
    uploadName: null
  };
  proxy.resetForm("degreeRef");
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
  title.value = "添加新产品研发配合程度";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getDegree(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改新产品研发配合程度";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["degreeRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateDegree(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addDegree(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除新产品研发配合程度编号为"' + _ids + '"的数据项？').then(function() {
    return delDegree(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/degree/export', {
    ...queryParams.value
  }, `degree_${new Date().getTime()}.xlsx`)
}

getList();
</script>
