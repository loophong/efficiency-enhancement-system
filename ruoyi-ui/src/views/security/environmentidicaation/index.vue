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
      :cell-style="{ 'white-space': 'pre-wrap', 'word-break': 'break-all' }"
      :row-style="{ height: 'auto' }"
      :header-row-style="{ height: '55px' }"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="环境" align="center" prop="environment" min-width="120" show-overflow-tooltip />
      <el-table-column label="环境要素" align="center" prop="features" min-width="120" show-overflow-tooltip />
      <el-table-column label="环境要素描述" align="center" min-width="200" :show-overflow-tooltip="false">
        <template #default="scope">
          <div class="description-cell">{{ formatText(scope.row.description) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120">
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
        <el-form-item label="环境要素" prop="features">
          <el-input v-model="form.features" placeholder="请输入环境要素" />
        </el-form-item>
        <el-form-item label="环境要素描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入环境要素描述" />
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
import { getCurrentInstance, ref, reactive, toRefs, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import request from "@/utils/request";

const { proxy } = getCurrentInstance();
const router = useRouter();
const route = useRoute();

// 数据定义
const data = reactive({
  // 遮罩层
  loading: true,
  // 选中数组
  ids: [],
  // 非单个禁用
  single: true,
  // 非多个禁用
  multiple: true,
  // 显示搜索条件
  showSearch: true,
  // 总条数
  total: 0,
  // 环境识别表格数据
  environmentidicaationList: [],
  // 弹出层标题
  title: "",
  // 是否显示弹出层
  open: false,
  // 表单参数
  form: {
    id: null,
    environment: null,
    features: null,
    description: null
  },
  // 查询参数
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    environment: null,
    features: null
  },
  rules: {},
});

// 解构响应式对象
const { queryParams, form, rules, loading, showSearch, ids, single, multiple, total, open, title, environmentidicaationList } = toRefs(data);

// 数据定义
const flatData = ref([]); // 扁平化一维数组
const spanArr = ref([]); // 用于记录每个环境的起始 index

// 初始化函数
onMounted(() => {
  // 加载数据
  getList();
});

/** 查询环境识别列表 */
function getList() {
  loading.value = true;
  listEnvironmentidicaation(queryParams.value).then(response => {
    if (!response || !response.rows) {
      loading.value = false;
      return;
    }
    
    // 处理数据
    processData(response.rows);
    environmentidicaationList.value = response.rows;
    total.value = response.total;
    
    loading.value = false;
  });
}

// 环境数据处理
function processData(data) {
  if (!data || !Array.isArray(data)) {
    console.warn("processData接收到非数组数据:", data);
    flatData.value = [];
    spanArr.value = [];
    return;
  }

  console.log("处理数据:", data.length, "条记录");
  
  // 初始化spanArr
  spanArr.value = [];
  
  // 对数据进行排序，确保相同环境的记录连续
  const sortedData = [...data].sort((a, b) => {
    if (!a.environment) return 1;
    if (!b.environment) return -1;
    return a.environment.localeCompare(b.environment);
  });
  
  // 计算span，为每组相同环境的记录设置合并单元格
  if (sortedData.length > 0) {
    let currentEnv = sortedData[0].environment;
    let count = 1;
    let startIndex = 0;
    
    // 初始化为0
    for (let i = 0; i < sortedData.length; i++) {
      spanArr.value.push(0);
    }
    
    // 计算合并单元格
    for (let i = 1; i < sortedData.length; i++) {
      if (sortedData[i].environment === currentEnv) {
        count++;
      } else {
        // 更新前一组环境的span值
        spanArr.value[startIndex] = count;
        // 开始新的一组
        currentEnv = sortedData[i].environment;
        startIndex = i;
        count = 1;
      }
    }
    
    // 处理最后一组
    spanArr.value[startIndex] = count;
  }
  
  // 输出调试信息
  console.log("spanArr:", spanArr.value);
  console.log("处理后数据:", sortedData.length, "条记录");
  
  // 将数据扁平化为一维数组
  flatData.value = sortedData;
}

// 合并单元格方法
function spanEnvironmentMethod({ row, column, rowIndex, columnIndex }) {
  // 第一列是选择框，不合并
  if (columnIndex === 0) {
    return {
      rowspan: 1,
      colspan: 1
    };
  }
  
  // 第二列是环境字段，需要合并
  if (columnIndex === 1) {
    if (spanArr.value[rowIndex] > 0) {
      return {
        rowspan: spanArr.value[rowIndex],
        colspan: 1
      };
    } else {
      return {
        rowspan: 0,
        colspan: 0
      };
    }
  }
  
  // 其他列不合并
  return {
    rowspan: 1,
    colspan: 1
  };
}

/** 格式化文本，保留换行符 */
function formatText(text) {
  if (!text) return '';
  return text.replace(/\\n/g, '\n');
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
    description: null
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
  if (!row || !row.id) {
    proxy.$modal.msgError("无效的数据行");
    return;
  }
  
  const _id = row.id || ids.value[0];
  console.log("编辑数据，ID:", _id);
  
  getEnvironmentidicaation(_id).then(response => {
    if (!response || !response.data) {
      proxy.$modal.msgError("获取数据失败");
      return;
    }
    
    form.value = response.data;
    open.value = true;
    title.value = "修改环境识别信息";
  }).catch(error => {
    console.error("获取详情失败:", error);
    proxy.$modal.msgError("获取详情失败");
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

// 导入前校验
function beforeImport(file) {
  loading.value = true;
  const formData = new FormData();
  formData.append("file", file);
  
  importEnvironmentidicaation(formData).then(res => {
    if (res.code === 200) {
      proxy.$modal.msgSuccess(res.msg);
      getList();
    } else {
      proxy.$modal.msgError(res.msg);
    }
  }).finally(() => {
    loading.value = false;
  });
  return false;
}

/** 下载导入模板 */
function handleImportTemplate() {
  importTemplate().then(response => {
    if (!response) {
    return;
  }
    const blob = new Blob([response]);
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "环境识别导入模板.xlsx";
    link.click();
    URL.revokeObjectURL(link.href);
  });
}
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
  padding: 8px !important; /* 增加单元格内边距 */
}

/* 自动换行样式 */
.thick-border-table :deep(.cell) {
  white-space: pre-wrap !important;
  line-height: 1.5;
  word-break: break-all;
  min-height: 24px;
}

/* 表格行高度自适应 */
.thick-border-table :deep(.el-table__row) {
  height: auto !important;
}

/* 描述单元格样式 */
.description-cell {
  text-align: left;
  white-space: pre-wrap;
  word-break: break-all;
  line-height: 1.6;
  padding: 4px 0;
  min-height: 24px;
}
</style>
