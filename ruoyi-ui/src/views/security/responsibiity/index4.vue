<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="qiYe">
        <el-input
          v-model="queryParams.qiYe"
          placeholder="请输入企业名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室" prop="keShi">
        <el-input
          v-model="queryParams.keShi"
          placeholder="请输入科室"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位" prop="gangWei">
        <el-input
          v-model="queryParams.gangWei"
          placeholder="请输入岗位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位负责人" prop="gangweiFuze">
        <el-input
          v-model="queryParams.gangweiFuze"
          placeholder="请输入岗位负责人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="安全生产职责" prop="gangweiFuzeZizhe">
        <el-input
          v-model="queryParams.gangweiFuzeZizhe"
          placeholder="安全生产职责"
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
          v-hasPermi="['security:zerenlist:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:zerenlist:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:zerenlist:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:zerenlist:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="zerenlistList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" width="100"/>
      <el-table-column label="企业名称" align="center" prop="qiYe" />
      <el-table-column label="科室" align="center" prop="keShi" />
      <el-table-column label="岗位" align="center" prop="gangWei" />
      <el-table-column label="岗位负责人" align="center" prop="gangweiFuze" />
      <el-table-column label="安全生产职责" align="center" prop="gangweiFuzeZizhe" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:zerenlist:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:zerenlist:remove']">删除</el-button>
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

    <!-- 添加或修改安全责任清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="zerenlistRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="企业名称" prop="qiYe">
          <el-input v-model="form.qiYe" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="科室" prop="keShi">
          <el-input v-model="form.keShi" placeholder="请输入科室" />
        </el-form-item>
        <el-form-item label="岗位" prop="gangWei">
          <el-input v-model="form.gangWei" placeholder="请输入岗位" />
        </el-form-item>
        <el-form-item label="岗位负责人" prop="gangweiFuze">
          <el-input v-model="form.gangweiFuze" placeholder="请输入岗位负责人" />
        </el-form-item>
        <el-form-item label="安全生产职责" prop="gangweiFuzeZizhe">
          <el-input v-model="form.gangweiFuzeZizhe" placeholder="安全生产职责" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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

<script setup name="Zerenlist">
import { listZerenlist, getZerenlist, delZerenlist, addZerenlist, updateZerenlist } from "@/api/security/zerenlist";

const { proxy } = getCurrentInstance();

const zerenlistList = ref([]);
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
    qiYe: null,
    keShi: null,
    gangWei: null,
    gangweiFuze: null,
    gangweiFuzeZizhe: null,
  },
  rules: {
    qiYe: [
      { required: true, message: "企业名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询安全责任清单列表 */
function getList() {
  loading.value = true;
  listZerenlist(queryParams.value).then(response => {
    zerenlistList.value = response.rows;
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
    qiYe: null,
    keShi: null,
    gangWei: null,
    gangweiFuze: null,
    gangweiFuzeZizhe: null,
    remark: null
  };
  proxy.resetForm("zerenlistRef");
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
  title.value = "添加安全责任清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getZerenlist(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改安全责任清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["zerenlistRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateZerenlist(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addZerenlist(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除安全责任清单编号为"' + _ids + '"的数据项？').then(function() {
    return delZerenlist(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/zerenlist/export', {
    ...queryParams.value
  }, `zerenlist_${new Date().getTime()}.xlsx`)
}

getList();
</script>
