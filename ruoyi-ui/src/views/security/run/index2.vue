<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="处理时间" prop="handlingTime">
        <el-date-picker clearable
          v-model="queryParams.handlingTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择处理时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="化学品名称" prop="chemicalName">
        <el-input
          v-model="queryParams.chemicalName"
          placeholder="请输入化学品名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="使用负责人" prop="userInCharge">
        <el-input
          v-model="queryParams.userInCharge"
          placeholder="请输入使用负责人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理量" prop="handlingQuantity">
        <el-input
          v-model="queryParams.handlingQuantity"
          placeholder="请输入处理量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理方法" prop="handlingMethod">
        <el-input
          v-model="queryParams.handlingMethod"
          placeholder="请输入处理方法"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理人" prop="handler">
        <el-input
          v-model="queryParams.handler"
          placeholder="请输入处理人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否合规" prop="isCompliant">
        <el-input
          v-model="queryParams.isCompliant"
          placeholder="请输入是否合规"
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
          v-hasPermi="['security:hazardousledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:hazardousledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:hazardousledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:hazardousledger:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- 使用通用Excel导入组件 -->
        <excel-import
          import-url="/security/hazardousledger/import"
          module-name="危化品处理台账"
          module-code="security/hazardousledger"
          button-text="导入"
          button-type="success"
          button-plain
          button-icon="Upload"
          @success="getList"
        />
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="info"
            plain
            icon="Download"
            @click="handleDownloadTemplate"
            v-hasPermi="['security:hazardousledger:import']"
        >模板下载
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="hazardousledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="处理时间" align="center" prop="handlingTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.handlingTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="化学品名称" align="center" prop="chemicalName" />
      <el-table-column label="使用负责人" align="center" prop="userInCharge" />
      <el-table-column label="处理量" align="center" prop="handlingQuantity" />
      <el-table-column label="处理方法" align="center" prop="handlingMethod" />
      <el-table-column label="是否合规" align="center" prop="isCompliant" />
      <el-table-column label="处理人" align="center" prop="handler" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:hazardousledger:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:hazardousledger:remove']">删除</el-button>
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

    <!-- 添加或修改危化品处理台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="hazardousledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="处理时间" prop="handlingTime">
          <el-date-picker clearable
            v-model="form.handlingTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择处理时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="化学品名称" prop="chemicalName">
          <el-input v-model="form.chemicalName" placeholder="请输入化学品名称" />
        </el-form-item>
        <el-form-item label="使用负责人" prop="userInCharge">
          <el-input v-model="form.userInCharge" placeholder="请输入使用负责人" />
        </el-form-item>
        <el-form-item label="处理量" prop="handlingQuantity">
          <el-input v-model="form.handlingQuantity" placeholder="请输入处理量" />
        </el-form-item>
        <el-form-item label="处理方法" prop="handlingMethod">
          <el-input v-model="form.handlingMethod" placeholder="请输入处理方法" />
        </el-form-item>
        <el-form-item label="是否合规" prop="isCompliant">
          <el-input v-model="form.isCompliant" placeholder="请输入是否合规（如：是、否、合规、不合规等）" />
        </el-form-item>
        <el-form-item label="处理人" prop="handler">
          <el-input v-model="form.handler" placeholder="请输入处理人" />
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

<script setup name="Hazardousledger">
import { listHazardousledger, getHazardousledger, delHazardousledger, addHazardousledger, updateHazardousledger } from "@/api/security/hazardousledger";
import ExcelImport from "@/components/ExcelImport/index.vue";

const { proxy } = getCurrentInstance();
const route = useRoute();

const hazardousledgerList = ref([]);
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
    handlingTime: null,
    chemicalName: null,
    userInCharge: null,
    handlingQuantity: null,
    handlingMethod: null,
    isCompliant: null,
    handler: null,
    relatedId: null
  },
  rules: {
    handlingTime: [
      { required: true, message: "处理时间不能为空", trigger: "blur" }
    ],
    chemicalName: [
      { required: true, message: "化学品名称不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询危化品处理台账列表 */
function getList() {
  loading.value = true;
  console.log('前端查询参数:', queryParams.value);
  listHazardousledger(queryParams.value).then(response => {
    console.log('后端返回数据:', response);
    hazardousledgerList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  }).catch(error => {
    console.error('查询失败:', error);
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
    handlingTime: null,
    chemicalName: null,
    userInCharge: null,
    handlingQuantity: null,
    handlingMethod: null,
    isCompliant: null,
    handler: null
  };
  proxy.resetForm("hazardousledgerRef");
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
    if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加危化品处理台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getHazardousledger(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改危化品处理台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["hazardousledgerRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateHazardousledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addHazardousledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除危化品处理台账编号为"' + _ids + '"的数据项？').then(function() {
    return delHazardousledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/hazardousledger/export', {
    ...queryParams.value
  }, `hazardousledger_${new Date().getTime()}.xlsx`)
}

/** 模板下载按钮操作 */
function handleDownloadTemplate() {
  // 使用 proxy.download 方法，指定 GET 请求
  proxy.download('security/hazardousledger/template', {}, `危化品处理台账导入模板_${new Date().getTime()}.xlsx`, 'get');
}



// 检查关联ID参数
function checkRelatedId() {
  const relatedId = route.query.relatedId;
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    queryParams.value.relatedId = relatedId;
    proxy.$modal.msgSuccess("已加载关联文件数据");
    getList();
  }
}

onMounted(() => {
  // 如果没有关联ID参数，直接加载所有数据
  if (!route.query.relatedId) {
    getList();
  }
  // 有关联ID参数时，checkRelatedId会处理数据加载
  else {
    checkRelatedId();
  }
});
</script>
