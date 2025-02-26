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
      <el-form-item label="环境要素描述" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="请输入环境要素描述"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备用字段1" prop="beiyongziduan1">
        <el-input
          v-model="queryParams.beiyongziduan1"
          placeholder="请输入备用字段1"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备用字段2" prop="beiyongziduan2">
        <el-input
          v-model="queryParams.beiyongziduan2"
          placeholder="请输入备用字段2"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备用字段3" prop="beiyongdiduan3">
        <el-date-picker clearable
          v-model="queryParams.beiyongdiduan3"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择备用字段3">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="备用字段4" prop="beiyongziduan4">
        <el-date-picker clearable
          v-model="queryParams.beiyongziduan4"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择备用字段4">
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
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:environmentidicaation:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="environmentidicaationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" />
      <el-table-column label="环境" align="center" prop="environment" />
      <el-table-column label="环境要素" align="center" prop="features" />
      <el-table-column label="环境要素描述" align="center" prop="description" />
      <el-table-column label="备用字段1" align="center" prop="beiyongziduan1" />
      <el-table-column label="备用字段2" align="center" prop="beiyongziduan2" />
      <el-table-column label="备用字段3" align="center" prop="beiyongdiduan3" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.beiyongdiduan3, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备用字段4" align="center" prop="beiyongziduan4" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.beiyongziduan4, '{y}-{m}-{d}') }}</span>
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
    <!-- 添加文件导入对话框 -->
    <el-dialog title="导入文件" v-model="importDialog" width="500px" append-to-body>
      <form enctype="multipart/form-data" @submit.prevent="submitFileUpload">
        <div class="col-xs-offset-1">
          <input type="file" id="file" name="file" accept=".xls,.xlsx" ref="fileInput"/>
          <div class="mt10 pt5">
            <input type="checkbox" id="updateSupport" name="updateSupport" title="如果登录账户已经存在，更新这条数据。"> 是否更新已经存在的用户数据
            &nbsp;<el-button type="default" @click="downloadTemplate" icon="Download">下载模板</el-button>
          </div>
          <font color="red" class="pull-left mt10">
            提示：仅允许导入“xls”或“xlsx”格式文件！
          </font>
        </div>
        <div class="dialog-footer">
          <el-button type="primary" native-type="submit">上传</el-button>
          <el-button @click="importDialog = false">取消</el-button>
        </div>
      </form>
    </el-dialog>

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
        <el-form-item label="备用字段1" prop="beiyongziduan1">
          <el-input v-model="form.beiyongziduan1" placeholder="请输入备用字段1" />
        </el-form-item>
        <el-form-item label="备用字段2" prop="beiyongziduan2">
          <el-input v-model="form.beiyongziduan2" placeholder="请输入备用字段2" />
        </el-form-item>
        <el-form-item label="备用字段3" prop="beiyongdiduan3">
          <el-date-picker clearable
            v-model="form.beiyongdiduan3"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择备用字段3">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备用字段4" prop="beiyongziduan4">
          <el-date-picker clearable
            v-model="form.beiyongziduan4"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择备用字段4">
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

const environmentidicaationList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const importDialog = ref(false);
const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    environment: null,
    features: null,
    description: null,
    beiyongziduan1: null,
    beiyongziduan2: null,
    beiyongdiduan3: null,
    beiyongziduan4: null
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
    beiyongziduan1: null,
    beiyongziduan2: null,
    beiyongdiduan3: null,
    beiyongziduan4: null
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

/** 导入按钮操作 */
function handleImport() {
  importDialog.value = true;
}

/** 下载模板按钮操作 */
function downloadTemplate() {
  proxy.download('security/environmentidicaation/importTemplate', {}, `environmentidicaation_template_${new Date().getTime()}.xlsx`);
}

/** 提交文件上传表单 */
function submitFileUpload() {
  const fileInput = proxy.$refs.fileInput;
  const file = fileInput.files[0];
  if (!file) {
    proxy.$modal.msgError('请选择一个文件');
    return;
  }
  const fileType = file.type;
  if (fileType !== 'application/vnd.ms-excel' && fileType !== 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
    proxy.$modal.msgError('仅允许导入“xls”或“xlsx”格式文件！');
    return;
  }
  const formData = new FormData();
  formData.append('file', file);
  formData.append('updateSupport', document.getElementById('updateSupport').checked);
  // 这里可以添加文件上传的逻辑，例如通过AJAX将文件发送到服务器
  // 例如：
  // 假设后端路径为 /import
  proxy.$http.post('security/environmentidicaation/import', formData).then(response => {
    proxy.$modal.msgSuccess('导入成功');
  }).catch(error => {
    proxy.$modal.msgError('导入失败: ' + error.message);
  });
  proxy.$modal.msgSuccess('上传成功'); // 模拟上传成功
  importDialog.value = false;
  getList();
}

</script>
