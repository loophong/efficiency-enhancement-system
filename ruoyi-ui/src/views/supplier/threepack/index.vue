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
      <el-form-item label="判定责任时间点" prop="responsibilityJudgmentTime">
          <el-date-picker clearable
            v-model="queryParams.responsibilityJudgmentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择判定责任时间点">
       
          </el-date-picker>
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
          v-hasPermi="['supplier:threepack:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:threepack:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:threepack:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:threepack:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="threepackList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="判定责任时间点" align="center" prop="responsibilityJudgmentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.responsibilityJudgmentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="计划发货时间点" align="center" prop="plannedDeliveryTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.plannedDeliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际发货时间点" align="center" prop="actualDeliveryTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.actualDeliveryTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="具体三包事项" align="center" prop="specificWarrantyItems" />
      <!-- <el-table-column label="分数" align="center" prop="score" /> -->
      <!-- <el-table-column label="备选1" align="center" prop="one" />
      <el-table-column label="备选2" align="center" prop="two" />
      <el-table-column label="填报时间" align="center" prop="happenTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.happenTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="填报人" align="center" prop="reporter" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:threepack:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:threepack:remove']">删除</el-button>
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

    <!-- 添加或修改三包发货及时率对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="threepackRef" :model="form" :rules="rules" label-width="115px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="判定责任时间点" prop="responsibilityJudgmentTime">
          <el-date-picker clearable
            v-model="form.responsibilityJudgmentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择判定责任时间点">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="计划发货时间点" prop="plannedDeliveryTime">
          <el-date-picker clearable
            v-model="form.plannedDeliveryTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择计划发货时间点">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际发货时间点" prop="actualDeliveryTime">
          <el-date-picker clearable
            v-model="form.actualDeliveryTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择实际发货时间点">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="具体三包事项" prop="specificWarrantyItems">
          <el-input v-model="form.specificWarrantyItems" type="textarea" placeholder="请输入内容" />
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

<script setup name="Threepack">
import { listThreepack, getThreepack, delThreepack, addThreepack, updateThreepack } from "@/api/supplier/threepack";

const { proxy } = getCurrentInstance();

const threepackList = ref([]);
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
    responsibilityJudgmentTime: null,
    plannedDeliveryTime: null,
    actualDeliveryTime: null,
    specificWarrantyItems: null,
    happenTime: null,
    reporter: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询三包发货及时率列表 */
function getList() {
  loading.value = true;
  listThreepack(queryParams.value).then(response => {
    threepackList.value = response.rows;
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
    responsibilityJudgmentTime: null,
    plannedDeliveryTime: null,
    actualDeliveryTime: null,
    specificWarrantyItems: null,
    happenTime: null,
    reporter: null
  };
  proxy.resetForm("threepackRef");
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
  title.value = "添加三包发货及时率";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getThreepack(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改三包发货及时率";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["threepackRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateThreepack(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addThreepack(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除？').then(function() {
    return delThreepack(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/threepack/export', {
    ...queryParams.value
  }, `threepack_${new Date().getTime()}.xlsx`)
}

getList();
</script>
