<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="评价单元" prop="evaluationUnit">
        <el-input
          v-model="queryParams.evaluationUnit"
          placeholder="请输入评价单元"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位" prop="position">
        <el-input
          v-model="queryParams.position"
          placeholder="请输入岗位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="检测地点" prop="inspectionLocation">
        <el-input
          v-model="queryParams.inspectionLocation"
          placeholder="请输入检测地点"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="化学因素名称" prop="chemicalFactorName">
        <el-input
          v-model="queryParams.chemicalFactorName"
          placeholder="请输入化学因素名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物理因素名称" prop="physicalFactorName">
        <el-input
          v-model="queryParams.physicalFactorName"
          placeholder="请输入物理因素名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产性粉尘名称" prop="productionDustName">
        <el-input
          v-model="queryParams.productionDustName"
          placeholder="请输入生产性粉尘名称"
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
          v-hasPermi="['security:hazardpointledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:hazardpointledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:hazardpointledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:hazardpointledger:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hazardpointledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="评价单元" align="center" prop="evaluationUnit" />
      <el-table-column label="岗位" align="center" prop="position" />
      <el-table-column label="检测地点" align="center" prop="inspectionLocation" />
      <el-table-column label="化学因素名称" align="center" prop="chemicalFactorName" />
      <el-table-column label="物理因素名称" align="center" prop="physicalFactorName" />
      <el-table-column label="生产性粉尘名称" align="center" prop="productionDustName" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:hazardpointledger:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:hazardpointledger:remove']">删除</el-button>
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

    <!-- 添加或修改有点害台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="hazardpointledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评价单元" prop="evaluationUnit">
          <el-input v-model="form.evaluationUnit" placeholder="请输入评价单元" />
        </el-form-item>
        <el-form-item label="岗位" prop="position">
          <el-input v-model="form.position" placeholder="请输入岗位" />
        </el-form-item>
        <el-form-item label="检测地点" prop="inspectionLocation">
          <el-input v-model="form.inspectionLocation" placeholder="请输入检测地点" />
        </el-form-item>
        <el-form-item label="化学因素名称" prop="chemicalFactorName">
          <el-input v-model="form.chemicalFactorName" placeholder="请输入化学因素名称" />
        </el-form-item>
        <el-form-item label="物理因素名称" prop="physicalFactorName">
          <el-input v-model="form.physicalFactorName" placeholder="请输入物理因素名称" />
        </el-form-item>
        <el-form-item label="生产性粉尘名称" prop="productionDustName">
          <el-input v-model="form.productionDustName" placeholder="请输入生产性粉尘名称" />
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

<script setup name="Hazardpointledger">
import { listHazardpointledger, getHazardpointledger, delHazardpointledger, addHazardpointledger, updateHazardpointledger } from "@/api/security/hazardpointledger";

const { proxy } = getCurrentInstance();

const hazardpointledgerList = ref([]);
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
    evaluationUnit: null,
    position: null,
    inspectionLocation: null,
    chemicalFactorName: null,
    physicalFactorName: null,
    productionDustName: null,
  },
  rules: {
    evaluationUnit: [
      { required: true, message: "评价单元不能为空", trigger: "blur" }
    ],
    position: [
      { required: true, message: "岗位不能为空", trigger: "blur" }
    ],
    inspectionLocation: [
      { required: true, message: "检测地点不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询有点害台账列表 */
function getList() {
  loading.value = true;
  listHazardpointledger(queryParams.value).then(response => {
    hazardpointledgerList.value = response.rows;
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
    evaluationUnit: null,
    position: null,
    inspectionLocation: null,
    chemicalFactorName: null,
    physicalFactorName: null,
    productionDustName: null,
    remarks: null
  };
  proxy.resetForm("hazardpointledgerRef");
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
  title.value = "添加有点害台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHazardpointledger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改有点害台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["hazardpointledgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHazardpointledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHazardpointledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除有点害台账编号为"' + _ids + '"的数据项？').then(function() {
    return delHazardpointledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/hazardpointledger/export', {
    ...queryParams.value
  }, `hazardpointledger_${new Date().getTime()}.xlsx`)
}

getList();
</script>
