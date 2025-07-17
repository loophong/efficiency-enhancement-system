<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="环境因素" prop="environmentalFactor">
        <el-input
          v-model="queryParams.environmentalFactor"
          placeholder="请输入环境因素"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="环境影响" prop="environmentalImpact">
        <el-input
          v-model="queryParams.environmentalImpact"
          placeholder="请输入环境影响"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="活动/生产/服务" prop="activityProductService">
        <el-input
          v-model="queryParams.activityProductService"
          placeholder="请输入活动/生产/服务"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="部门/工序/岗位" prop="departmentProcessPosition">
        <el-input
          v-model="queryParams.departmentProcessPosition"
          placeholder="请输入部门/工序/岗位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="时态/状态" prop="temporalState">
        <el-input
          v-model="queryParams.temporalState"
          placeholder="请输入时态/状态"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管控措施" prop="controlMeasures">
        <el-input
          v-model="queryParams.controlMeasures"
          placeholder="请输入管控措施"
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
          v-hasPermi="['security:importantfactors:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:importantfactors:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:importantfactors:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:importantfactors:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:importantfactors:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="DocumentCopy"
          @click="handleTemplate"
          v-hasPermi="['security:importantfactors:export']"
        >模板下载</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="importantfactorsList" @selection-change="handleSelectionChange" :span-method="objectSpanMethod">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="序号" align="center" prop="id" /> -->
      <el-table-column label="环境因素" align="center" prop="environmentalFactor" />
      <el-table-column label="环境影响" align="center" prop="environmentalImpact" />
      <el-table-column label="活动/生产/服务" align="center" prop="activityProductService" />
      <el-table-column label="部门/工序/岗位" align="center" prop="departmentProcessPosition" />
      <el-table-column label="时态/状态" align="center" prop="temporalState" />
      <el-table-column label="管控措施" align="center" prop="controlMeasures" />
      <!-- <el-table-column label="审批状态" align="center" prop="statu">
        <template #default="scope">
          <dict-tag :options="security_status" :value="scope.row.statu"/>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:importantfactors:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:importantfactors:remove']">删除</el-button>
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

    <!-- 添加或修改公司级重要环境因素清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="importantfactorsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="环境因素" prop="environmentalFactor">
          <el-input v-model="form.environmentalFactor" placeholder="请输入环境因素" />
        </el-form-item>
        <el-form-item label="环境影响" prop="environmentalImpact">
          <el-input v-model="form.environmentalImpact" placeholder="请输入环境影响" />
        </el-form-item>
        <el-form-item label="活动/生产/服务" prop="activityProductService">
          <el-input v-model="form.activityProductService" placeholder="请输入活动/生产/服务" />
        </el-form-item>
        <el-form-item label="部门/工序/岗位" prop="departmentProcessPosition">
          <el-input v-model="form.departmentProcessPosition" placeholder="请输入部门/工序/岗位" />
        </el-form-item>
        <el-form-item label="时态/状态" prop="temporalState">
          <el-input v-model="form.temporalState" placeholder="请输入时态/状态" />
        </el-form-item>
        <el-form-item label="管控措施" prop="controlMeasures">
          <el-input v-model="form.controlMeasures" placeholder="请输入管控措施" />
        </el-form-item>
        <!-- <el-form-item label="审批状态" prop="statu">
          <el-select v-model="form.statu" placeholder="请选择审批状态">
            <el-option
              v-for="dict in security_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <el-icon class="el-icon--upload"><upload-filled /></el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport" />是否更新已经存在的数据
            </div>
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
            <el-button @click="upload.open = false">取 消</el-button>
          </div>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup name="Importantfactors">
import { listImportantfactors, getImportantfactors, delImportantfactors, addImportantfactors, updateImportantfactors } from "@/api/security/importantfactors";
import { getToken } from "@/utils/auth";
import { UploadFilled } from '@element-plus/icons-vue';
import { ref, reactive, toRefs, getCurrentInstance, watch, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const { proxy } = getCurrentInstance();
const { security_status } = proxy.useDict('security_status');
const route = useRoute();
const importantfactorsList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const spanArr = ref([]); // 存储单元格合并信息

// 上传参数
const upload = reactive({
  open: false,
  title: '',
  url: '',
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() }
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    environmentalFactor: null,
    environmentalImpact: null,
    activityProductService: null,
    departmentProcessPosition: null,
    temporalState: null,
    controlMeasures: null,
    statu: null,
    relatedId: null
  },
  rules: {
    environmentalFactor: [
      { required: true, message: "环境因素不能为空", trigger: "blur" }
    ],
    environmentalImpact: [
      { required: true, message: "环境影响不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询公司级重要环境因素清单列表 */
function getList() {
  loading.value = true;
  listImportantfactors(queryParams.value).then(response => {
    // 对数据按环境因素和环境影响排序
    const sortedList = response.rows.sort((a, b) => {
      // 首先按环境因素排序
      const factorA = a.environmentalFactor || '';
      const factorB = b.environmentalFactor || '';
      if (factorA !== factorB) {
        return factorA.localeCompare(factorB);
      }
      // 其次按环境影响排序
      const impactA = a.environmentalImpact || '';
      const impactB = b.environmentalImpact || '';
      if (impactA !== impactB) {
        return impactA.localeCompare(impactB);
      }
      // 最后按管控措施排序
      const measuresA = a.controlMeasures || '';
      const measuresB = b.controlMeasures || '';
      return measuresA.localeCompare(measuresB);
    });
    
    importantfactorsList.value = sortedList;
    total.value = response.total;
    loading.value = false;
    // 调用获取合并单元格信息的方法
    getSpanArr();
  });
}

// 获取合并单元格信息
function getSpanArr() {
  const dataList = importantfactorsList.value;
  if (dataList.length === 0) return;

  console.log('开始计算合并单元格信息，数据条数:', dataList.length);

  // 创建用于存储每列合并信息的数组对象
  let factorSpanArr = []; // 环境因素列
  let impactSpanArr = []; // 环境影响列
  let measuresSpanArr = []; // 管控措施列

  // 初始化所有行为1（不合并）
  for (let i = 0; i < dataList.length; i++) {
    factorSpanArr.push(1);
    impactSpanArr.push(1);
    measuresSpanArr.push(1);
  }

  // 使用递增遍历方式计算需要合并的行数
  let factorIndex = 0; // 当前环境因素的开始行
  let impactIndex = 0; // 当前环境影响的开始行
  let measuresIndex = 0; // 当前管控措施的开始行

  for (let i = 1; i < dataList.length; i++) {
    // 环境因素合并计算
    if (dataList[i].environmentalFactor === dataList[factorIndex].environmentalFactor) {
      // 当前行与环境因素开始行相同，合并
      factorSpanArr[factorIndex]++;
      factorSpanArr[i] = 0; // 当前行不显示
    } else {
      // 当前行与开始行不同，开始新的一组
      factorIndex = i;
    }

    // 环境影响合并计算（只有环境因素相同时才考虑合并环境影响）
    if (dataList[i].environmentalFactor === dataList[i-1].environmentalFactor) {
      if (dataList[i].environmentalImpact === dataList[impactIndex].environmentalImpact) {
        // 当前行与环境影响开始行相同，合并
        impactSpanArr[impactIndex]++;
        impactSpanArr[i] = 0; // 当前行不显示
      } else {
        // 当前行与开始行不同，开始新的一组
        impactIndex = i;
      }
    } else {
      // 环境因素变化，重置环境影响的开始行
      impactIndex = i;
    }

    // 管控措施合并计算（只有环境因素和环境影响都相同时才考虑合并管控措施）
    if (dataList[i].environmentalFactor === dataList[i-1].environmentalFactor && 
        dataList[i].environmentalImpact === dataList[i-1].environmentalImpact) {
      if (dataList[i].controlMeasures === dataList[measuresIndex].controlMeasures) {
        // 当前行与管控措施开始行相同，合并
        measuresSpanArr[measuresIndex]++;
        measuresSpanArr[i] = 0; // 当前行不显示
      } else {
        // 当前行与开始行不同，开始新的一组
        measuresIndex = i;
      }
    } else {
      // 环境因素或环境影响变化，重置管控措施的开始行
      measuresIndex = i;
    }
  }

  // 存储合并信息
  spanArr.value = {
    factorSpanArr,
    impactSpanArr,
    measuresSpanArr
  };
  
  console.log('合并单元格计算完成，结果:', JSON.stringify(spanArr.value));
}

// 合并单元格方法
function objectSpanMethod({ row, column, rowIndex, columnIndex }) {
  if (!spanArr.value || Object.keys(spanArr.value).length === 0) {
    return;
  }

  // 使用表格的列属性名称来确定需要合并的列
  if (column.property === 'environmentalFactor') { // 环境因素列
    const _row = spanArr.value.factorSpanArr[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  } else if (column.property === 'environmentalImpact') { // 环境影响列
    const _row = spanArr.value.impactSpanArr[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  } else if (column.property === 'controlMeasures') { // 管控措施列
    const _row = spanArr.value.measuresSpanArr[rowIndex];
    const _col = _row > 0 ? 1 : 0;
    return {
      rowspan: _row,
      colspan: _col
    };
  }
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "公司级重要环境因素清单数据导入";
  upload.open = true;
  upload.url = import.meta.env.VITE_APP_BASE_API + "/security/importantfactors/importData";
}

/** 下载模板操作 */
function handleTemplate() {
  proxy.download("security/importantfactors/importTemplate", {}, `importantfactors_template_${new Date().getTime()}.xlsx`, 'post');
}

// 文件上传中处理
function handleFileUploadProgress() {
  upload.isUploading = true;
}

// 文件上传成功处理
function handleFileSuccess(response) {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$modal.msgSuccess(response.msg);
  getList();
}

// 提交上传文件
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
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
    environmentalFactor: null,
    environmentalImpact: null,
    activityProductService: null,
    departmentProcessPosition: null,
    temporalState: null,
    controlMeasures: null,
    statu: null,
    relatedId: queryParams.value.relatedId // 保留关联ID
  };
  proxy.resetForm("importantfactorsRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  // 保存当前的关联ID
  const relatedId = queryParams.value.relatedId;
  
  // 重置表单
  proxy.resetForm("queryRef");
  
  // 恢复关联ID
  queryParams.value.relatedId = relatedId;
  
  // 重新查询
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
  // 如果有关联ID，设置到表单中
  if (queryParams.value.relatedId) {
    form.value.relatedId = queryParams.value.relatedId;
  }
  open.value = true;
  title.value = "添加公司级重要环境因素清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getImportantfactors(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改公司级重要环境因素清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["importantfactorsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateImportantfactors(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addImportantfactors(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除公司级重要环境因素清单编号为"' + _ids + '"的数据项？').then(function() {
    return delImportantfactors(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/importantfactors/export', {
    ...queryParams.value
  }, `importantfactors_${new Date().getTime()}.xlsx`)
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

// 监听路由参数变化
watch(() => route.query.relatedId, (newVal, oldVal) => {
  if (newVal !== oldVal) {
    console.log("关联ID参数变化:", newVal);
    queryParams.value.relatedId = newVal;
    getList();
  }
}, { immediate: true });

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
