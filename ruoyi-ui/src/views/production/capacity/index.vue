<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="车型" prop="capacityType">
        <el-input
            v-model="queryParams.capacityType"
            placeholder="请输入车型"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="产量" prop="productionQuantity">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.productionQuantity"-->
      <!--            placeholder="请输入产量"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="产量" prop="productionQuantity">
        <el-input-number
            v-model="queryParams.productionQuantity"
            placeholder="请输入产量"
            clearable
            @keyup.enter="handleQuery"
            :min="0"/>
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
            v-hasPermi="['production:capacity:add']"
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
            v-hasPermi="['production:capacity:edit']"
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
            v-hasPermi="['production:capacity:remove']"
        >删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--            type="warning"-->
      <!--            plain-->
      <!--            icon="Download"-->
      <!--            @click="handleExport"-->
      <!--            v-hasPermi="['production:capacity:export']"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="capacityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="主键" align="center" prop="id" />-->
      <el-table-column label="车型" align="center" prop="capacityType"/>
      <el-table-column label="产量" align="center" prop="productionQuantity"/>
      <el-table-column label="生产状态" align="center" prop="productionStatus">
        <template #default="scope">
          <el-switch v-model="scope.row.productionStatus"
                     @change="changeStatus(scope.row)" class="ml-2" size="large"
                     inline-prompt active-text="在产" inactive-text="停产"
                     style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:capacity:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:capacity:remove']">删除
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

    <!-- 添加或修改产能对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="capacityRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车型" prop="capacityType">
          <el-input v-model="form.capacityType" placeholder="请输入车型"/>
        </el-form-item>
        <el-form-item label="产量" prop="productionQuantity">
          <el-input-number v-model="form.productionQuantity" :min="0"/>
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

<script setup name="Capacity">
import {listCapacity, getCapacity, delCapacity, addCapacity, updateCapacity} from "@/api/production/capacity";

const {proxy} = getCurrentInstance();

const capacityList = ref([]);
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
    capacityType: null,
    productionQuantity: null,
    remarks: null
  },
  rules: {
    capacityType: [
      {required: true, message: "车型不能为空", trigger: "blur"}
    ],
    productionQuantity: [
      {required: true, message: "产量不能为空", trigger: "blur"}
    ],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询产能列表 */
function getList() {
  loading.value = true;
  listCapacity(queryParams.value).then(response => {
    capacityList.value = response.rows;
    // productionStatus 为 number 类型，需要转换为 boolean 类型
    capacityList.value.forEach(item => {
      item.productionStatus = item.productionStatus === 1;
    });

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
    capacityType: null,
    productionQuantity: null,
    remarks: null
  };
  proxy.resetForm("capacityRef");
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
  title.value = "添加产能";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCapacity(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改产能";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["capacityRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCapacity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCapacity(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除产能编号为"' + _ids + '"的数据项？').then(function () {
    return delCapacity(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/capacity/export', {
    ...queryParams.value
  }, `capacity_${new Date().getTime()}.xlsx`)
}

getList();


/** 修改生产状态 */
function changeStatus(row) {
  // // productionStatus 为 boolean 类型，需要转换为 number 类型
  let newRow = {
    'id': row.id,
    'productionStatus': row.productionStatus === true ? 1 : 0
  }
  updateCapacity(newRow).then(response => {
    proxy.$modal.msgSuccess("修改成功");
  });
}

</script>
