<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="相关方类型" prop="partyInvolved">
        <el-input
            v-model="queryParams.partyInvolved"
            placeholder="请输入相关方类型"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="需求和期望" prop="demand">
        <el-input
            v-model="queryParams.demand"
            placeholder="请输入需求和期望"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测指标" prop="monitoringIndicators">
        <el-input
            v-model="queryParams.monitoringIndicators"
            placeholder="请输入监测指标"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测频率" prop="monitoringFrequency">
        <el-input
            v-model="queryParams.monitoringFrequency"
            placeholder="请输入监测频率"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监测部门" prop="monitoringDepartment">
        <el-input
            v-model="queryParams.monitoringDepartment"
            placeholder="请输入监测部门"
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
            v-hasPermi="['security:RequireExpectParty:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:RequireExpectParty:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:RequireExpectParty:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:RequireExpectParty:export']"
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
            v-hasPermi="['security:RequireExpectParty:import']"
          >导入</el-button>
        </el-upload>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Download"
          @click="handleImportTemplate"
          v-hasPermi="['security:RequireExpectParty:import']"
        >模板下载</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table 
      v-loading="loading" 
      :data="flatData" 
      @selection-change="handleSelectionChange"
      :span-method="spanMethod"
      border
      class="thick-border-table"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" width="55" align="center">
        <template #default="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="相关方类型" align="center" prop="partyInvolved" />
      <el-table-column label="需求和期望" align="center" prop="demand" />
      <el-table-column label="监测指标" align="center" prop="monitoringIndicators" />
      <el-table-column label="监测频率" align="center" prop="monitoringFrequency" />
      <el-table-column label="监测部门" align="center" prop="monitoringDepartment" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:RequireExpectParty:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:RequireExpectParty:remove']">删除</el-button>
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

    <!-- 添加或修改相关方期望对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="RequireExpectPartyRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="相关方类型" prop="partyInvolved">
          <el-input v-model="form.partyInvolved" placeholder="请输入相关方类型" />
        </el-form-item>
        <el-form-item label="需求和期望" prop="demand">
          <el-input v-model="form.demand" placeholder="请输入需求和期望" />
        </el-form-item>
        <el-form-item label="监测指标" prop="monitoringIndicators">
          <el-input v-model="form.monitoringIndicators" placeholder="请输入监测指标" />
        </el-form-item>
        <el-form-item label="监测频率" prop="monitoringFrequency">
          <el-input v-model="form.monitoringFrequency" placeholder="请输入监测频率" />
        </el-form-item>
        <el-form-item label="监测部门" prop="monitoringDepartment">
          <el-input v-model="form.monitoringDepartment" placeholder="请输入监测部门" />
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

<script setup name="RequireExpectParty">
import { listRequireExpectParty, getRequireExpectParty, delRequireExpectParty, addRequireExpectParty, updateRequireExpectParty, importRequireExpectParty, importTemplate } from "@/api/security/RequireExpectParty";
import { getCurrentInstance, ref, reactive, toRefs } from 'vue';

const { proxy } = getCurrentInstance();

// 数据定义
const flatData = ref([]); // 扁平化数据数组
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const route = useRoute();

// 用于单元格合并的数据结构
const mergeData = ref({
  partyInvolved: {}, // 相关方类型合并信息
  demand: {} // 需求和期望合并信息
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    partyInvolved: null,
    demand: null,
    monitoringIndicators: null,
    monitoringFrequency: null,
    relatedId: null,
    monitoringDepartment: null
  },
  rules: {
    partyInvolved: [
      { required: true, message: "相关方类型不能为空", trigger: "blur" }
    ],
    demand: [
      { required: true, message: "需求和期望不能为空", trigger: "blur" }
    ]
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询相关方期望列表 */
function getList() {
  loading.value = true;
  listRequireExpectParty(queryParams.value).then(response => {
    // 处理数据，保持原始顺序
    const list = response.rows;
    
    // 初始化合并单元格数据
    const partyInvolvedSpans = {};
    const demandSpans = {};
    
    // 按相关方类型分组
    const typeGroups = {};
    list.forEach((item, index) => {
      const type = item.partyInvolved || '';
      if (!typeGroups[type]) {
        typeGroups[type] = [];
        partyInvolvedSpans[index] = 1; // 记录每个类型的起始位置
      } else {
        partyInvolvedSpans[index] = 0; // 非起始行不显示
      }
      typeGroups[type].push({...item, index});
    });
    
    // 在每个相关方类型内，按需求和期望分组
    Object.keys(typeGroups).forEach(type => {
      const typeItems = typeGroups[type];
      const demandGroups = {};
      
      typeItems.forEach(item => {
        const demand = item.demand || '';
        if (!demandGroups[demand]) {
          demandGroups[demand] = [];
          demandSpans[item.index] = 1; // 记录每个需求的起始位置
        } else {
          demandSpans[item.index] = 0; // 非起始行不显示
        }
        demandGroups[demand].push(item);
      });
      
      // 更新相关方类型的合并行数
      const firstIndex = typeItems[0].index;
      partyInvolvedSpans[firstIndex] = typeItems.length;
      
      // 更新每个需求和期望的合并行数
      Object.keys(demandGroups).forEach(demand => {
        const demandItems = demandGroups[demand];
        const firstDemandIndex = demandItems[0].index;
        demandSpans[firstDemandIndex] = demandItems.length;
      });
    });
    
    // 保存合并信息
    mergeData.value = {
      partyInvolved: partyInvolvedSpans,
      demand: demandSpans
    };
    
    // 保存扁平化数据
    flatData.value = list;
    total.value = response.total;
    loading.value = false;
  });
}

// 合并单元格方法
function spanMethod({ row, column, rowIndex, columnIndex }) {
  // 序号列不合并
  if (columnIndex === 1) {
    return {
      rowspan: 1,
      colspan: 1
    };
  }
  
  // 相关方类型列合并
  if (columnIndex === 2) {
    const rowspan = mergeData.value.partyInvolved[rowIndex];
    if (rowspan !== undefined) {
      return {
        rowspan: rowspan,
        colspan: rowspan ? 1 : 0
      };
    }
    return {
      rowspan: 1,
      colspan: 1
    };
  }
  
  // 需求和期望列合并
  if (columnIndex === 3) {
    const rowspan = mergeData.value.demand[rowIndex];
    if (rowspan !== undefined) {
      return {
        rowspan: rowspan,
        colspan: rowspan ? 1 : 0
      };
    }
    return {
      rowspan: 1,
      colspan: 1
    };
  }
  
  // 其他列不合并
  return {
    rowspan: 1,
    colspan: 1
  };
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
    relatedId: null
  };
  // 如果有关联ID查询参数，保留该值
  if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
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
    if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加相关方期望";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRequireExpectParty(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改相关方期望";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["RequireExpectPartyRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateRequireExpectParty(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRequireExpectParty(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除相关方期望编号为"' + _ids + '"的数据项？').then(function() {
    return delRequireExpectParty(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/RequireExpectParty/export', {
    ...queryParams.value
  }, `RequireExpectParty_${new Date().getTime()}.xlsx`)
}

// 导入前校验
function beforeImport(file) {
  const formData = new FormData();
  formData.append("file", file);
  importRequireExpectParty(formData).then(res => {
    proxy.$modal.msgSuccess(res.msg || "导入成功");
    getList();
  }).catch((error) => {
    console.error("导入失败:", error);
    proxy.$modal.msgError("导入失败: " + (error.message || "未知错误"));
  });
  return false; // 阻止自动上传
}

// 下载模板
function handleImportTemplate() {
  importTemplate().then(response => {
    const blob = new Blob([response], { type: "application/vnd.ms-excel" });
    const link = document.createElement("a");
    link.href = window.URL.createObjectURL(blob);
    link.download = "相关方期望导入模板.xlsx";
    link.click();
    window.URL.revokeObjectURL(link.href);
  });
}

function checkRelatedId() {
  // 从路由参数中获取关联ID
  const relatedId = route.query.relatedId;
  
  if (relatedId) {
    console.log("检测到关联ID参数:", relatedId);
    // 将关联ID设置到查询参数中
    queryParams.value.relatedId = relatedId;
    // 显示提示信息
    proxy.$modal.msgSuccess("已加载关联文件的相关方识别表");
  }
}

// 初始化函数
onMounted(() => {
  // 检查URL参数中是否有关联ID
  checkRelatedId();
  // 加载数据
  getList();
});

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
