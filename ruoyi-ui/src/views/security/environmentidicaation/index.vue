<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="环境" prop="environment">
        <el-input
            v-model="queryParams.environment"
            placeholder="请输入环境"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="环境要素" prop="features">
        <el-input
            v-model="queryParams.features"
            placeholder="请输入环境要素"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审批状态" clearable>
          <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批科室" prop="statuDepartment">
        <el-select v-model="queryParams.statuDepartment" placeholder="请选择审批科室" clearable>
          <el-option
              v-for="dict in security_department_review"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批时间" prop="statuTime">
        <el-date-picker clearable
                        v-model="queryParams.statuTime"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择审批时间">
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
            v-hasPermi="['security:environmentidicaation:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:environmentidicaation:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:environmentidicaation:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:environmentidicaation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="environmentidicaationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="环境" align="center" prop="environment" />
      <el-table-column label="环境要素" align="center" prop="features" />
      <el-table-column label="环境要素描述" align="center" prop="description" />
      <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column>
      <el-table-column label="审批科室" align="center" prop="statuDepartment">
        <template #default="scope">
          <dict-tag :options="security_department_review" :value="scope.row.statuDepartment"/>
        </template>
      </el-table-column>
      <el-table-column label="审批时间" align="center" prop="statuTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.statuTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:environmentidicaation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:environmentidicaation:remove']">删除</el-button>
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

    <!-- 添加或修改环境识别对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="environmentidicaationRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="环境" prop="environment">
          <el-input v-model="form.environment" placeholder="请输入环境" />
        </el-form-item>
        <el-form-item label="环境要素" prop="features">
          <el-input v-model="form.features" placeholder="请输入环境要素" />
        </el-form-item>
        <el-form-item label="环境要素描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入环境要素描述" />
        </el-form-item>
        <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
                v-for="dict in security_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批科室" prop="statuDepartment">
          <el-select v-model="form.statuDepartment" placeholder="请选择审批科室">
            <el-option
                v-for="dict in security_department_review"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批时间" prop="statuTime">
          <el-date-picker clearable
                          v-model="form.statuTime"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择审批时间">
          </el-date-picker>
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

<script setup name="Environmentidicaation">
import { listEnvironmentidicaation, getEnvironmentidicaation, delEnvironmentidicaation, addEnvironmentidicaation, updateEnvironmentidicaation } from "@/api/security/environmentidicaation";

const { proxy } = getCurrentInstance();
const { security_department_review, security_status } = proxy.useDict('security_department_review', 'security_status');

const environmentidicaationList = ref([]);
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
    environment: null,
    features: null,
    statu: null,
    statuDepartment: null,
    statuTime: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询环境识别列表 */
function getList() {
  loading.value = true;
  listEnvironmentidicaation(queryParams.value).then(response => {
    environmentidicaationList.value = response.rows;
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
    environment: null,
    features: null,
    description: null,
    statu: null,
    statuDepartment: null,
    statuTime: null
  };
  proxy.resetForm("environmentidicaationRef");
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
  title.value = "添加环境识别";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getEnvironmentidicaation(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改环境识别";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["environmentidicaationRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateEnvironmentidicaation(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addEnvironmentidicaation(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除环境识别编号为"' + _ids + '"的数据项？').then(function() {
    return delEnvironmentidicaation(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/environmentidicaation/export', {
    ...queryParams.value
  }, `environmentidicaation_${new Date().getTime()}.xlsx`)
}

getList();
</script>
