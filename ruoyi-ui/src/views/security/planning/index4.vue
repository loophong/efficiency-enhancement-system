<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="法律法规名称" prop="regulationName">
        <el-input
          v-model="queryParams.regulationName"
          placeholder="请输入法律法规名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="文号" prop="documentNumber">
        <el-input
          v-model="queryParams.documentNumber"
          placeholder="请输入文号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布单位" prop="issuingUnit">
        <el-input
          v-model="queryParams.issuingUnit"
          placeholder="请输入发布单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发布/修订日期" prop="issuanceRevisionDate">
        <el-date-picker clearable
          v-model="queryParams.issuanceRevisionDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择发布/修订日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实施日期" prop="implementationDate">
        <el-date-picker clearable
          v-model="queryParams.implementationDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择实施日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="效力" prop="effectiveness">
        <el-input
          v-model="queryParams.effectiveness"
          placeholder="请输入效力"
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
          v-hasPermi="['security:legallist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:legallist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:legallist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:legallist:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="legallistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="法律法规名称" align="center" prop="regulationName" />
      <el-table-column label="文号" align="center" prop="documentNumber" />
      <el-table-column label="发布单位" align="center" prop="issuingUnit" />
      <el-table-column label="发布/修订日期" align="center" prop="issuanceRevisionDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.issuanceRevisionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实施日期" align="center" prop="implementationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.implementationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="效力" align="center" prop="effectiveness" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:legallist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:legallist:remove']">删除</el-button>
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

    <!-- 添加或修改安全/环境法律法规识别清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="legallistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="法律法规名称" prop="regulationName">
          <el-input v-model="form.regulationName" placeholder="请输入法律法规名称" />
        </el-form-item>
        <el-form-item label="文号" prop="documentNumber">
          <el-input v-model="form.documentNumber" placeholder="请输入文号" />
        </el-form-item>
        <el-form-item label="发布单位" prop="issuingUnit">
          <el-input v-model="form.issuingUnit" placeholder="请输入发布单位" />
        </el-form-item>
        <el-form-item label="发布/修订日期" prop="issuanceRevisionDate">
          <el-date-picker clearable
            v-model="form.issuanceRevisionDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择发布/修订日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实施日期" prop="implementationDate">
          <el-date-picker clearable
            v-model="form.implementationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择实施日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="效力" prop="effectiveness">
          <el-input v-model="form.effectiveness" placeholder="请输入效力" />
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

<script setup name="Legallist">
import { listLegallist, getLegallist, delLegallist, addLegallist, updateLegallist } from "@/api/security/legallist";

const { proxy } = getCurrentInstance();

const legallistList = ref([]);
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
    regulationName: null,
    documentNumber: null,
    issuingUnit: null,
    issuanceRevisionDate: null,
    implementationDate: null,
    effectiveness: null
  },
  rules: {
    regulationName: [
      { required: true, message: "法律法规名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全/环境法律法规识别清单列表 */
function getList() {
  loading.value = true;
  listLegallist(queryParams.value).then(response => {
    legallistList.value = response.rows;
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
    regulationName: null,
    documentNumber: null,
    issuingUnit: null,
    issuanceRevisionDate: null,
    implementationDate: null,
    effectiveness: null
  };
  proxy.resetForm("legallistRef");
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
  title.value = "添加安全法律法规识别清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getLegallist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全法律法规识别清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["legallistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateLegallist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addLegallist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全法律法规识别清单编号为"' + _ids + '"的数据项？').then(function() {
    return delLegallist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/legallist/export', {
    ...queryParams.value
  }, `legallist_${new Date().getTime()}.xlsx`)
}

getList();
</script>
