<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="序号" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入序号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分值" prop="score">
        <el-input
          v-model="queryParams.score"
          placeholder="请输入分值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评分" prop="rating">
        <el-input
          v-model="queryParams.rating"
          placeholder="请输入评分"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核标记" prop="statu">
        <el-select v-model="queryParams.statu" placeholder="请选择审核标记" clearable>
          <el-option
            v-for="dict in security_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['security:relatedpartyevaluationrecords:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:relatedpartyevaluationrecords:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:relatedpartyevaluationrecords:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:relatedpartyevaluationrecords:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="relatedpartyevaluationrecordsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="评价内容" align="center" prop="evaluationContent" />
      <el-table-column label="分值" align="center" prop="score" />
      <el-table-column label="评分" align="center" prop="rating" />
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="参与评价人员" align="center" prop="evaluators" />
      <el-table-column label="评价标准" align="center" prop="evaluationCriteria">
        <template #default="scope">
          <dict-tag :options="security_relation_suggestion" :value="scope.row.evaluationCriteria"/>
        </template>
      </el-table-column>
      <el-table-column label="评价建议" align="center" prop="evaluationSuggestions" />
      <el-table-column label="审核标记" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:relatedpartyevaluationrecords:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:relatedpartyevaluationrecords:remove']">删除</el-button>
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

    <!-- 添加或修改相关方评价记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="relatedpartyevaluationrecordsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="评价内容">
          <editor v-model="form.evaluationContent"type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="分值" prop="score">
          <el-input v-model="form.score" placeholder="请输入分值" />
        </el-form-item>
        <el-form-item label="评分" prop="rating">
          <el-input v-model="form.rating" placeholder="请输入评分" />
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="参与评价人员" prop="evaluators">
          <el-input v-model="form.evaluators" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="评价标准" prop="evaluationCriteria">
          <el-select v-model="form.evaluationCriteria" placeholder="请选择评价标准">
            <el-option
              v-for="dict in security_relation_suggestion"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="评价建议" prop="evaluationSuggestions">
          <el-input v-model="form.evaluationSuggestions" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="审核标记" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审核标记">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
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

<script setup name="Relatedpartyevaluationrecords">
import { listRelatedpartyevaluationrecords, getRelatedpartyevaluationrecords, delRelatedpartyevaluationrecords, addRelatedpartyevaluationrecords, updateRelatedpartyevaluationrecords } from "@/api/security/relatedpartyevaluationrecords";

const { proxy } = getCurrentInstance();
const { security_relation_suggestion, security_status } = proxy.useDict('security_relation_suggestion', 'security_status');
const route = useRoute();
const relatedpartyevaluationrecordsList = ref([]);
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
    id: null,
    score: null,
    rating: null,
    evaluators: null,
    statu: null
  },
  rules: {
    evaluationContent: [
      { required: true, message: "评价内容不能为空", trigger: "blur" }
    ],
    score: [
      { required: true, message: "分值不能为空", trigger: "blur" }
    ],
    rating: [
      { required: true, message: "评分不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询相关方评价记录列表 */
function getList() {
  loading.value = true;
  listRelatedpartyevaluationrecords(queryParams.value).then(response => {
    relatedpartyevaluationrecordsList.value = response.rows;
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
    evaluationContent: null,
    score: null,
    rating: null,
    remarks: null,
    evaluators: null,
    evaluationCriteria: null,
    evaluationSuggestions: null,
    statu: null
  };
  proxy.resetForm("relatedpartyevaluationrecordsRef");
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
  title.value = "添加相关方评价记录";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRelatedpartyevaluationrecords(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改相关方评价记录";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["relatedpartyevaluationrecordsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRelatedpartyevaluationrecords(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRelatedpartyevaluationrecords(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除相关方评价记录编号为"' + _ids + '"的数据项？').then(function() {
    return delRelatedpartyevaluationrecords(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/relatedpartyevaluationrecords/export', {
    ...queryParams.value
  }, `relatedpartyevaluationrecords_${new Date().getTime()}.xlsx`)
}

getList();
</script>
