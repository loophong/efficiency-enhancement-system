<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物质名称" prop="substanceName">
        <el-input
            v-model="queryParams.substanceName"
            placeholder="请输入物质名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="危化品序号" prop="hazardousMaterialsNumber">
        <el-input
            v-model="queryParams.hazardousMaterialsNumber"
            placeholder="请输入危化品序号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="CAS号" prop="casNumber">
        <el-input
            v-model="queryParams.casNumber"
            placeholder="请输入CAS号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="火灾危险性类别" prop="fireDangerClass">
        <el-input
            v-model="queryParams.fireDangerClass"
            placeholder="请输入火灾危险性类别"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="最大设计存量" prop="maxDesignStock">
        <el-input
            v-model="queryParams.maxDesignStock"
            placeholder="请输入最大设计存量"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年用量" prop="annualUsage">
        <el-input
            v-model="queryParams.annualUsage"
            placeholder="请输入年用量"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="日常存量" prop="dailyStock">
        <el-input
            v-model="queryParams.dailyStock"
            placeholder="请输入日常存量"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="储存地点" prop="storageLocation">
        <el-input
            v-model="queryParams.storageLocation"
            placeholder="请输入储存地点"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="管控负责人" prop="managementResponsiblePerson">
        <el-input
            v-model="queryParams.managementResponsiblePerson"
            placeholder="请输入管控负责人"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="联系电话" prop="contactNumber">
        <el-input
            v-model="queryParams.contactNumber"
            placeholder="请输入联系电话"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="备注" prop="remarks">
        <el-input
            v-model="queryParams.remarks"
            placeholder="请输入备注"
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
            v-hasPermi="['security:inventory:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['security:inventory:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['security:inventory:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['security:inventory:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <!-- 使用通用Excel导入组件替换原有导入功能 -->
        <excel-import
          import-url="/security/inventory/import"
          module-name="危险化学品台账"
          module-code="security/inventory"
          button-text="导入"
          button-type="success"
          button-plain
          button-icon="Upload"
          @success="getList"
        />
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
<!--      <el-table-column label="id" align="center" prop="id"/>-->
      <el-table-column label="物质名称" align="center" prop="substanceName"/>
      <el-table-column label="危化品序号" align="center" prop="hazardousMaterialsNumber"/>
      <el-table-column label="CAS号" align="center" prop="casNumber"/>
      <el-table-column label="火灾危险性类别" align="center" prop="fireDangerClass"/>
      <el-table-column label="外观" align="center" prop="appearance"/>
      <el-table-column label="最大设计存量" align="center" prop="maxDesignStock"/>
      <el-table-column label="年用量" align="center" prop="annualUsage"/>
      <el-table-column label="日常存量" align="center" prop="dailyStock"/>
      <el-table-column label="储存地点" align="center" prop="storageLocation"/>
      <el-table-column label="管控负责人" align="center" prop="managementResponsiblePerson"/>
      <el-table-column label="联系电话" align="center" prop="contactNumber"/>
      <el-table-column label="备注" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['security:inventory:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['security:inventory:remove']">删除
          </el-button>
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

    <!-- 添加或修改危险化学品台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="inventoryRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="物质名称" prop="substanceName">
          <el-input v-model="form.substanceName" placeholder="请输入物质名称"/>
        </el-form-item>
        <el-form-item label="危化品序号" prop="hazardousMaterialsNumber">
          <el-input v-model="form.hazardousMaterialsNumber" placeholder="请输入危化品序号"/>
        </el-form-item>
        <el-form-item label="CAS号" prop="casNumber">
          <el-input v-model="form.casNumber" placeholder="请输入CAS号"/>
        </el-form-item>
        <el-form-item label="火灾危险性类别" prop="fireDangerClass">
          <el-input v-model="form.fireDangerClass" placeholder="请输入火灾危险性类别"/>
        </el-form-item>
        <el-form-item label="外观" prop="appearance">
          <el-input v-model="form.appearance" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="最大设计存量" prop="maxDesignStock">
          <el-input v-model="form.maxDesignStock" placeholder="请输入最大设计存量"/>
        </el-form-item>
        <el-form-item label="年用量" prop="annualUsage">
          <el-input v-model="form.annualUsage" placeholder="请输入年用量"/>
        </el-form-item>
        <el-form-item label="日常存量" prop="dailyStock">
          <el-input v-model="form.dailyStock" placeholder="请输入日常存量"/>
        </el-form-item>
        <el-form-item label="储存地点" prop="storageLocation">
          <el-input v-model="form.storageLocation" placeholder="请输入储存地点"/>
        </el-form-item>
        <el-form-item label="管控负责人" prop="managementResponsiblePerson">
          <el-input v-model="form.managementResponsiblePerson" placeholder="请输入管控负责人"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="contactNumber">
          <el-input v-model="form.contactNumber" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注"/>
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

<script setup name="Inventory">
import {listInventory, getInventory, delInventory, addInventory, updateInventory } from "@/api/security/inventory";
import ExcelImport from "@/components/ExcelImport/index.vue";

const { proxy } = getCurrentInstance();
const inventoryList = ref([]);
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
    substanceName: null,
    hazardousMaterialsNumber: null,
    casNumber: null,
    fireDangerClass: null,
    appearance: null,
    maxDesignStock: null,
    annualUsage: null,
    dailyStock: null,
    storageLocation: null,
    managementResponsiblePerson: null,
    contactNumber: null,
    remarks: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询危险化学品台账列表 */
function getList() {
  loading.value = true;
  listInventory(queryParams.value).then(response => {
    inventoryList.value = response.rows;
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
    substanceName: null,
    hazardousMaterialsNumber: null,
    casNumber: null,
    fireDangerClass: null,
    appearance: null,
    maxDesignStock: null,
    annualUsage: null,
    dailyStock: null,
    storageLocation: null,
    managementResponsiblePerson: null,
    contactNumber: null,
    remarks: null
  };
  proxy.resetForm("inventoryRef");
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
  title.value = "添加危险化学品台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getInventory(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改危险化学品台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["inventoryRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateInventory(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addInventory(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除危险化学品台账编号为"' + _ids + '"的数据项？').then(function () {
    return delInventory(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/inventory/export', {
    ...queryParams.value
  }, `inventory_${new Date().getTime()}.xlsx`)
}

getList();
</script>
  