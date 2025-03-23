<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item label="供应商类别" prop="formLeibie"  style="width: 280px;">
        <el-select v-model="queryParams.formLeibie" placeholder="请选择供应商类别" clearable>
          <el-option
            v-for="dict in supplier_change"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="判定责任时间点" prop="responsibilityJudgmentTime">
        <el-date-picker clearable
          v-model="queryParams.responsibilityJudgmentTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择判定责任时间点">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="具体内容" prop="specificContent">
        <el-input
          v-model="queryParams.specificContent"
          placeholder="请输入具体内容"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发生时间" prop="happenTime">
        <el-date-picker clearable
          v-model="queryParams.happenTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择发生时间">
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
      <el-form-item label="填报人" prop="reporter">
        <el-input
          v-model="queryParams.reporter"
          placeholder="请输入填报人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备选1" prop="one">
        <el-input
          v-model="queryParams.one"
          placeholder="请输入备选1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备选2" prop="two">
        <el-input
          v-model="queryParams.two"
          placeholder="请输入备选2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备选3" prop="three">
        <el-input
          v-model="queryParams.three"
          placeholder="请输入备选3"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
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
          v-hasPermi="['supplier:change:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:change:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:change:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:change:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="changeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="供应商类别" align="center" prop="formLeibie">
        <template #default="scope">
          <dict-tag :options="supplier_change" :value="scope.row.formLeibie"/>
        </template>
      </el-table-column>
      <el-table-column label="判定责任时间点" align="center" prop="responsibilityJudgmentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.responsibilityJudgmentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="具体内容" align="center" prop="specificContent" />
      <!-- <el-table-column label="发生时间" align="center" prop="happenTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.happenTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="得分" align="center" prop="score" /> -->
      <!-- <el-table-column label="填报人" align="center" prop="reporter" /> -->
      <!-- <el-table-column label="发生次数" align="center" prop="happenNumber" /> -->
      <!-- <el-table-column label="备选2" align="center" prop="two" /> -->
      <!-- <el-table-column label="备选3" align="center" prop="three" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:change:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:change:remove']">删除</el-button>
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

    <!-- 添加或修改擅自变更产品材质参数尺寸对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="changeRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商类别" prop="formLeibie">
          <el-radio-group v-model="form.formLeibie">
            <el-radio
              v-for="dict in supplier_change"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="判定责任时间点" prop="responsibilityJudgmentTime">
          <el-date-picker clearable
            v-model="form.responsibilityJudgmentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择判定责任时间点">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="具体内容" prop="specificContent">
          <el-input v-model="form.specificContent" placeholder="请输入具体内容" />
        </el-form-item>

        <!-- <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item>
        <el-form-item label="填报人" prop="reporter">
          <el-input v-model="form.reporter" placeholder="请输入填报人" />
        </el-form-item> -->
        <!-- <el-form-item label="发生次数" prop="happenNumber">
          <el-input v-model="form.one" placeholder="请输入发生次数" />
        </el-form-item> -->
        <!-- <el-form-item label="备选2" prop="two">
          <el-input v-model="form.two" placeholder="请输入备选2" />
        </el-form-item>
        <el-form-item label="备选3" prop="three">
          <el-input v-model="form.three" placeholder="请输入备选3" />
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
</template>

<script setup name="Change">
import { listChange, getChange, delChange, addChange, updateChange } from "@/api/supplier/change";

const { proxy } = getCurrentInstance();
const { supplier_change } = proxy.useDict('supplier_change');

const changeList = ref([]);
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
    formLeibie: null,
    responsibilityJudgmentTime: null,
    specificContent: null,
    happenTime: null,
    score: null,
    reporter: null,
    one: null,
    two: null,
    three: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询擅自变更产品材质参数尺寸列表 */
function getList() {
  loading.value = true;
  listChange(queryParams.value).then(response => {
    changeList.value = response.rows;
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
    formLeibie: null,
    responsibilityJudgmentTime: null,
    specificContent: null,
    happenTime: null,
    score: null,
    reporter: null,
    one: null,
    two: null,
    three: null
  };
  proxy.resetForm("changeRef");
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
  title.value = "添加擅自变更产品材质参数尺寸";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getChange(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改擅自变更产品材质参数尺寸";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["changeRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateChange(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addChange(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除擅自变更产品材质参数尺寸编号为"' + _ids + '"的数据项？').then(function() {
    return delChange(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/change/export', {
    ...queryParams.value
  }, `change_${new Date().getTime()}.xlsx`)
}

getList();
</script>
