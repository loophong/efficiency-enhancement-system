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
      <el-form-item label="记录时间" prop="time">
        <el-date-picker clearable
          v-model="queryParams.time"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择记录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="存在专项返利政策" prop="specialRebatePolicy">
        <el-select v-model="queryParams.specialRebatePolicy" placeholder="请选择存在专项返利政策" clearable>
          <el-option
            v-for="dict in supplier_price_compete_policy"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="需自主提货" prop="selfPickup">
        <el-select v-model="queryParams.selfPickup" placeholder="请选择需自主提货" clearable>
          <el-option
            v-for="dict in supplier_price_compete_self"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="常规物料有价格优势" prop="regularPrice">
        <el-select v-model="queryParams.regularPrice" placeholder="请选择常规物料有价格优势" clearable>
          <el-option
            v-for="dict in supplier_price_compete_regular"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单一物料无价格优势" prop="singleNoPrice">
        <el-select v-model="queryParams.singleNoPrice" placeholder="请选择单一物料无价格优势" clearable>
          <el-option
            v-for="dict in supplier_price_compete_single_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="常规物料无价格优势" prop="regularNoPrice">
        <el-select v-model="queryParams.regularNoPrice" placeholder="请选择常规物料无价格优势" clearable>
          <el-option
            v-for="dict in supplier_price_compete_regular_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上传时间" prop="uploadTime">
        <el-date-picker clearable
          v-model="queryParams.uploadTime"
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
      <el-form-item label="模型得分" prop="modelScore">
        <el-input
          v-model="queryParams.modelScore"
          placeholder="请输入模型得分"
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
          v-hasPermi="['supplier:pricecompete:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:pricecompete:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:pricecompete:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:pricecompete:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="pricecompeteList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="记录时间" align="center" prop="time" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="存在专项返利政策" align="center" prop="specialRebatePolicy" >
        <template #default="scope">
          <dict-tag :options="supplier_price_compete_policy" :value="scope.row.specialRebatePolicy"/>
        </template>
      </el-table-column>
      <el-table-column label="需自主提货" align="center" prop="selfPickup">
        <template #default="scope">
          <dict-tag :options="supplier_price_compete_self" :value="scope.row.selfPickup"/>
        </template>
      </el-table-column>
      <el-table-column label="常规物料有价格优势" align="center" prop="regularPrice">
        <template #default="scope">
          <dict-tag :options="supplier_price_compete_regular" :value="scope.row.regularPrice"/>
        </template>
      </el-table-column>
      <el-table-column label="单一物料无价格优势" align="center" prop="singleNoPrice">
        <template #default="scope">
          <dict-tag :options="supplier_price_compete_single_no" :value="scope.row.singleNoPrice"/>
        </template>
      </el-table-column>
      <el-table-column label="常规物料无价格优势" align="center" prop="regularNoPrice">
        <template #default="scope">
          <dict-tag :options="supplier_price_compete_regular_no" :value="scope.row.regularNoPrice"/>
        </template>
      </el-table-column>
      <el-table-column label="上传时间" align="center" prop="uploadTime" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.uploadTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="得分" align="center" prop="score" />
      <el-table-column label="模型得分" align="center" prop="modelScore" />
      <el-table-column label="填报人" align="center" prop="uploadName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:pricecompete:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:pricecompete:remove']">删除</el-button>
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

    <!-- 添加或修改价格竞争力对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="pricecompeteRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商代码" prop="supplierCode">
          <el-input v-model="form.supplierCode" placeholder="请输入供应商代码" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="supplierName">
          <el-input v-model="form.supplierName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="记录时间" prop="time">
          <el-date-picker clearable
            v-model="form.time"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择记录时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="存在专项返利政策" prop="specialRebatePolicy">
          <el-radio-group v-model="form.specialRebatePolicy">
            <el-radio
              v-for="dict in supplier_price_compete_policy"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="需自主提货" prop="selfPickup">
          <el-radio-group v-model="form.selfPickup">
            <el-radio
              v-for="dict in supplier_price_compete_self"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="常规物料有价格优势" prop="regularPrice">
          <el-radio-group v-model="form.regularPrice">
            <el-radio
              v-for="dict in supplier_price_compete_regular"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="单一物料无价格优势" prop="singleNoPrice">
          <el-radio-group v-model="form.singleNoPrice">
            <el-radio
              v-for="dict in supplier_price_compete_single_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="常规物料无价格优势" prop="regularNoPrice">
          <el-radio-group v-model="form.regularNoPrice">
            <el-radio
              v-for="dict in supplier_price_compete_regular_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传时间" prop="uploadTime">
          <el-date-picker clearable
            v-model="form.uploadTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择上传时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item>
        <el-form-item label="模型得分" prop="modelScore">
          <el-input v-model="form.modelScore" placeholder="请输入模型得分" />
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

<script setup name="Pricecompete">
import { listPricecompete, getPricecompete, delPricecompete, addPricecompete, updatePricecompete } from "@/api/supplier/pricecompete";

const { proxy } = getCurrentInstance();
const { supplier_price_compete_regular_no, supplier_price_compete_regular, supplier_price_compete_self, supplier_price_compete_single_no, supplier_price_compete_policy } = proxy.useDict('supplier_price_compete_regular_no', 'supplier_price_compete_regular', 'supplier_price_compete_self', 'supplier_price_compete_single_no', 'supplier_price_compete_policy');

const pricecompeteList = ref([]);
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
    time: null,
    specialRebatePolicy: null,
    selfPickup: null,
    regularPrice: null,
    singleNoPrice: null,
    regularNoPrice: null,
    uploadTime: null,
    score: null,
    modelScore: null,
    uploadName: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询价格竞争力列表 */
function getList() {
  loading.value = true;
  listPricecompete(queryParams.value).then(response => {
    pricecompeteList.value = response.rows;
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
    time: null,
    specialRebatePolicy: null,
    selfPickup: null,
    regularPrice: null,
    singleNoPrice: null,
    regularNoPrice: null,
    uploadTime: null,
    score: null,
    modelScore: null,
    uploadName: null
  };
  proxy.resetForm("pricecompeteRef");
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
  title.value = "添加价格竞争力";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getPricecompete(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改价格竞争力";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["pricecompeteRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updatePricecompete(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPricecompete(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除价格竞争力编号为"' + _ids + '"的数据项？').then(function() {
    return delPricecompete(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/pricecompete/export', {
    ...queryParams.value
  }, `pricecompete_${new Date().getTime()}.xlsx`)
}

getList();
</script>
