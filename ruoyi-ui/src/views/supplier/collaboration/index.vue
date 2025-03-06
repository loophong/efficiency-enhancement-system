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
      <el-form-item label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" prop="letter">
        <el-select v-model="queryParams.letter" placeholder="请选择供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_one"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商如受到相关方处罚" prop="punish">
        <el-select v-model="queryParams.punish" placeholder="请选择供应商如受到相关方处罚" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_two"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商如对采购员需求反馈不及时" prop="feedbackNotTimely">
        <el-select v-model="queryParams.feedbackNotTimely" placeholder="请选择供应商如对采购员需求反馈不及时" clearable>
          <el-option
            v-for="dict in supplier_performance_services_collaboration_three"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="得分" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入得分"
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
          v-hasPermi="['supplier:collaboration:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['supplier:collaboration:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['supplier:collaboration:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['supplier:collaboration:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="collaborationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="供应商名称" align="center" prop="supplierName" />
      <el-table-column label="记录时间" align="center" prop="time" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.time, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" align="center" prop="letter">
        <template #default="scope">
          <!-- <dict-tag :options="supplier_performance_services_collaboration_one" :value="scope.row.letter"/> -->
          {{ scope.row.letter }}
        </template>
      </el-table-column>
      <el-table-column label="供应商如受到相关方处罚" align="center" prop="punish">
        <template #default="scope">
          <!-- <dict-tag :options="supplier_performance_services_collaboration_two" :value="scope.row.punish"/> -->
          {{ scope.row.letter }}
        </template>
      </el-table-column>
      <el-table-column label="供应商如对采购员需求反馈不及时" align="center" prop="feedbackNotTimely">
        <template #default="scope">
          <!-- <dict-tag :options="supplier_performance_services_collaboration_three" :value="scope.row.feedbackNotTimely"/> -->
          {{ scope.row.letter }}
        </template>
      </el-table-column>
      <el-table-column label="得分" align="center" prop="score" />
      <el-table-column label="具体内容" align="center" prop="specificContent" />
      <el-table-column label="填报人" align="center" prop="uploadName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['supplier:collaboration:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['supplier:collaboration:remove']">删除</el-button>
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

    <!-- 添加或修改服务与协作对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="collaborationRef" :model="form" :rules="rules" label-width="80px">
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
        <el-form-item label="供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作" prop="letter">
          <el-select v-model="form.letter" placeholder="请选择供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作">
            <el-option
              v-for="dict in supplier_performance_services_collaboration_one"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商如受到相关方处罚" prop="punish">
          <el-select v-model="form.punish" placeholder="请选择供应商如受到相关方处罚">
            <el-option
              v-for="dict in supplier_performance_services_collaboration_two"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="供应商如对采购员需求反馈不及时" prop="feedbackNotTimely">
          <el-select v-model="form.feedbackNotTimely" placeholder="请选择供应商如对采购员需求反馈不及时">
            <el-option
              v-for="dict in supplier_performance_services_collaboration_three"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="得分" prop="score">
          <el-input v-model="form.score" placeholder="请输入得分" />
        </el-form-item>
        <el-form-item label="具体内容" prop="specificContent">
          <el-input v-model="form.specificContent" placeholder="请输入具体内容" />
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

<script setup name="Collaboration">
import { listCollaboration, getCollaboration, delCollaboration, addCollaboration, updateCollaboration } from "@/api/supplier/collaboration";

const { proxy } = getCurrentInstance();
const { supplier_performance_services_collaboration_two, supplier_performance_services_collaboration_three, supplier_performance_services_collaboration_one } = proxy.useDict('supplier_performance_services_collaboration_two', 'supplier_performance_services_collaboration_three', 'supplier_performance_services_collaboration_one');

const collaborationList = ref([]);
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
    letter: null,
    punish: null,
    feedbackNotTimely: null,
    score: null,
    specificContent: null,
    uploadName: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询服务与协作列表 */
function getList() {
  loading.value = true;
  listCollaboration(queryParams.value).then(response => {
    collaborationList.value = response.rows;
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
    letter: null,
    punish: null,
    feedbackNotTimely: null,
    score: null,
    specificContent: null,
    uploadName: null
  };
  proxy.resetForm("collaborationRef");
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
  title.value = "添加服务与协作";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCollaboration(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改服务与协作";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["collaborationRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCollaboration(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCollaboration(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除服务与协作编号为"' + _ids + '"的数据项？').then(function() {
    return delCollaboration(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('supplier/collaboration/export', {
    ...queryParams.value
  }, `collaboration_${new Date().getTime()}.xlsx`)
}

getList();
</script>
