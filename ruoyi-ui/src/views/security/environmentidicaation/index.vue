<template>
  <div class="app-container">
    <!-- 查询表单 -->
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
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
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
        <el-upload
          :show-file-list="false"
          :before-upload="beforeImport"
          accept=".xlsx,.xls"
        >
          <el-button
            type="info"
            plain
            icon="Upload"
            v-hasPermi="['security:environmentidicaation:import']"
          >导入</el-button>
        </el-upload>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Download"
          @click="handleImportTemplate"
          v-hasPermi="['security:environmentidicaation:import']"
        >模板下载</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格展示 -->
    <el-table 
      v-loading="loading" 
      :data="flatData" 
      @selection-change="handleSelectionChange"
      :span-method="spanEnvironmentMethod"
      border
      class="thick-border-table"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="环境" align="center" prop="environment" />
      <el-table-column label="环境要素" align="center" prop="features" />
      <el-table-column label="环境要素描述" align="center" prop="description" />
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:environmentidicaation:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:environmentidicaation:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
        v-show="total>0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="environmentidicaationRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="环境" prop="environment">
          <el-input v-model="form.environment" placeholder="请输入环境" />
        </el-form-item>
        <el-table :data="form.children" v-if="form.id && form.children && form.children.length > 0">
          <el-table-column label="环境要素" prop="features" />
          <el-table-column label="环境要素描述" prop="description" />
        </el-table>
        <el-form-item label="环境要素" prop="features">
          <el-input v-model="form.features" placeholder="请输入环境要素" />
        </el-form-item>
        <el-form-item label="环境要素描述" prop="description">
          <el-input v-model="form.description" placeholder="请输入环境要素描述" />
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
import { listEnvironmentidicaation, getEnvironmentidicaation, delEnvironmentidicaation, addEnvironmentidicaation, updateEnvironmentidicaation, importEnvironmentidicaation, importTemplate } from "@/api/security/environmentidicaation";
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';

const { proxy } = getCurrentInstance();

// 資料定義
const flatData = ref([]); // 新增：攤平成一維陣列
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const spanArr = ref([]); // 用於記錄每個環境的起始 index

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    environment: null,
    features: null
  },
  rules: {}
});

const { queryParams, form, rules } = toRefs(data);
let title = ref("");

/** 查詢環境識別列表 */
function getList() {
  loading.value = true;
  listEnvironmentidicaation(queryParams.value).then(response => {
    // 攤平成一維陣列
    const tempArr = [];
    const spanArrTemp = [];
    let pos = 0;

    // 以環境分組
    const envMap = {};
    response.rows.forEach(item => {
      if (!envMap[item.environment]) {
        envMap[item.environment] = [];
      }
      envMap[item.environment].push(item);
    });

    Object.keys(envMap).forEach(env => {
      const group = envMap[env];
      group.forEach((item, idx) => {
        tempArr.push({
          ...item,
          environment: env
        });
        // 記錄每個環境的第一行 index
        if (idx === 0) {
          spanArrTemp.push(pos);
        }
        pos++;
      });
    });

    flatData.value = tempArr;
    spanArr.value = spanArrTemp;
    total.value = response.total;
    loading.value = false;
  });
}

// 合併單元格方法
function spanEnvironmentMethod({ row, column, rowIndex, columnIndex }) {
  if (column.property === 'environment') {
    const index = spanArr.value.indexOf(rowIndex);
    if (index > -1) {
      // 找到該環境的第一行
      // 計算該環境有幾行
      const env = row.environment;
      const count = flatData.value.filter(item => item.environment === env).length;
      return {
        rowspan: count,
        colspan: 1
      };
    }
    return {
      rowspan: 0,
      colspan: 0
    };
  }
  return {
    rowspan: 1,
    colspan: 1
    };
}

// 取消按鈕
function cancel() {
  open.value = false;
  reset();
}

// 表單重置
function reset() {
  form.value = {
    id: null,
    environment: null,
    features: null,
    description: null,
    children: []
  };
  proxy.resetForm("environmentidicaationRef");
}

/** 搜索按鈕操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按鈕操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多選框選中資料
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按鈕操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加環境識別";
}

/** 修改按鈕操作 */
function handleUpdate(row) {
  reset();

  // 如果是子項，獲取父項
  if (row.parentId) {
    getEnvironmentidicaation(row.parentId).then(response => {
      form.value = {
        ...response.data,
        children: [row]
      };
      open.value = true;
      title.value = "修改環境識別";
    });
  } else {
    getEnvironmentidicaation(row.id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改環境識別";
    });
  }
}

/** 提交按鈕 */
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

/** 刪除按鈕操作 */
function handleDelete(row) {
  const _ids = row.id || ids.value;
  proxy.$modal.confirm('是否確認刪除環境識別編號為"' + _ids + '"的數據項？').then(function() {
    return delEnvironmentidicaation(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("刪除成功");
  }).catch(() => {});
}

/** 導出按鈕操作 */
function handleExport() {
  proxy.download('security/environmentidicaation/export', {
    ...queryParams.value
  }, `environmentidicaation_${new Date().getTime()}.xlsx`)
}

// 导入前校验
function beforeImport(file) {
  const formData = new FormData();
  formData.append("file", file);
  importEnvironmentidicaation(formData).then(res => {
    proxy.$modal.msgSuccess(res.msg || "导入成功");
    getList();
  }).catch(() => {
    proxy.$modal.msgError("导入失败");
  });
  return false; // 阻止自动上传
}

// 下载模板
function handleImportTemplate() {
  importTemplate().then(response => {
    const blob = new Blob([response], { type: "application/vnd.ms-excel" });
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = "环境识别导入模板.xlsx";
    link.click();
    window.URL.revokeObjectURL(link.href);
  });
}

// 初始化加載數據
getList();
</script>

<style scoped>
.thick-border-table :deep(.el-table__inner-wrapper)::after {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  content: '';
  z-index: 1;
  pointer-events: none;
  box-shadow: 0 0 0 2px #000000 inset; /* 外框加粗 */
}
.thick-border-table :deep(.el-table__cell),
.thick-border-table :deep(.el-table th),
.thick-border-table :deep(.el-table td) {
  border-width: 2px !important;
  border-color: #000000 !important;
}
</style>