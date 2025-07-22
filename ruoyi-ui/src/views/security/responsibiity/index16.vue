<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部门" prop="department">
        <el-input
          v-model="queryParams.department"
          placeholder="请输入部门"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发证机关" prop="issuingAuthority">
        <el-input
          v-model="queryParams.issuingAuthority"
          placeholder="请输入发证机关"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作业类别" prop="operationCategory">
        <el-input
          v-model="queryParams.operationCategory"
          placeholder="请输入作业类别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="idNumber">
        <el-input
          v-model="queryParams.idNumber"
          placeholder="请输入身份证号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="证件编号" prop="certificateNumber">
        <el-input
          v-model="queryParams.certificateNumber"
          placeholder="请输入证件编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="取证时间" prop="issuanceDate">
        <el-date-picker clearable
          v-model="queryParams.issuanceDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择取证时间">
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
          v-hasPermi="['security:specialoperationspersonnelInfo:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:specialoperationspersonnelInfo:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:specialoperationspersonnelInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:specialoperationspersonnelInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="specialoperationspersonnelInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" type="index" />
      <el-table-column label="部门" align="center" prop="department" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="性别" align="center" prop="gender" />
      <el-table-column label="发证机关" align="center" prop="issuingAuthority" />
      <el-table-column label="证件类型" align="center" prop="certificateType" />
      <el-table-column label="作业类别" align="center" prop="operationCategory" />
      <el-table-column label="身份证号" align="center" prop="idNumber" />
      <el-table-column label="证件编号" align="center" prop="certificateNumber" />
      <el-table-column label="取证时间" align="center" prop="issuanceDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.issuanceDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第一次到期" align="center" prop="firstExpirationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.firstExpirationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第一次复审时间" align="center" prop="firstRenewalDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.firstRenewalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第二次到期" align="center" prop="secondExpirationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.secondExpirationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第二次复审时间" align="center" prop="secondRenewalDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.secondRenewalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第三次到期" align="center" prop="thirdExpirationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.thirdExpirationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="第三次复审时间" align="center" prop="thirdRenewalDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.thirdRenewalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remarks" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:specialoperationspersonnelInfo:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:specialoperationspersonnelInfo:remove']">删除</el-button>
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

    <!-- 添加或修改特种作业人员信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="specialoperationspersonnelInfoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门" prop="department">
          <el-input v-model="form.department" placeholder="请输入部门" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="发证机关" prop="issuingAuthority">
          <el-input v-model="form.issuingAuthority" placeholder="请输入发证机关" />
        </el-form-item>
        <el-form-item label="作业类别" prop="operationCategory">
          <el-input v-model="form.operationCategory" placeholder="请输入作业类别" />
        </el-form-item>
        <el-form-item label="身份证号" prop="idNumber">
          <el-input v-model="form.idNumber" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="证件编号" prop="certificateNumber">
          <el-input v-model="form.certificateNumber" placeholder="请输入证件编号" />
        </el-form-item>
        <el-form-item label="取证时间" prop="issuanceDate">
          <el-date-picker clearable
            v-model="form.issuanceDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择取证时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第一次到期" prop="firstExpirationDate">
          <el-date-picker clearable
            v-model="form.firstExpirationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第一次到期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第一次复审时间" prop="firstRenewalDate">
          <el-date-picker clearable
            v-model="form.firstRenewalDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第一次复审时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第二次到期" prop="secondExpirationDate">
          <el-date-picker clearable
            v-model="form.secondExpirationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第二次到期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第二次复审时间" prop="secondRenewalDate">
          <el-date-picker clearable
            v-model="form.secondRenewalDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第二次复审时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第三次到期" prop="thirdExpirationDate">
          <el-date-picker clearable
            v-model="form.thirdExpirationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第三次到期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="第三次复审时间" prop="thirdRenewalDate">
          <el-date-picker clearable
            v-model="form.thirdRenewalDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择第三次复审时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remarks">
          <el-input v-model="form.remarks" type="textarea" placeholder="请输入内容" />
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

<script setup name="SpecialoperationspersonnelInfo">
import { listSpecialoperationspersonnelInfo, getSpecialoperationspersonnelInfo, delSpecialoperationspersonnelInfo, addSpecialoperationspersonnelInfo, updateSpecialoperationspersonnelInfo } from "@/api/security/specialoperationspersonnelInfo";

const { proxy } = getCurrentInstance();

const specialoperationspersonnelInfoList = ref([]);
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
    department: null,
    name: null,
    gender: null,
    issuingAuthority: null,
    certificateType: null,
    operationCategory: null,
    idNumber: null,
    certificateNumber: null,
    issuanceDate: null,
  },
  rules: {
    department: [
      { required: true, message: "部门不能为空", trigger: "blur" }
    ],
    name: [
      { required: true, message: "姓名不能为空", trigger: "blur" }
    ],
    gender: [
      { required: true, message: "性别不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询特种作业人员信息列表 */
function getList() {
  loading.value = true;
  listSpecialoperationspersonnelInfo(queryParams.value).then(response => {
    specialoperationspersonnelInfoList.value = response.rows;
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
    department: null,
    name: null,
    gender: null,
    issuingAuthority: null,
    certificateType: null,
    operationCategory: null,
    idNumber: null,
    certificateNumber: null,
    issuanceDate: null,
    firstExpirationDate: null,
    firstRenewalDate: null,
    secondExpirationDate: null,
    secondRenewalDate: null,
    thirdExpirationDate: null,
    thirdRenewalDate: null,
    remarks: null
  };
  proxy.resetForm("specialoperationspersonnelInfoRef");
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
  title.value = "添加特种作业人员信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getSpecialoperationspersonnelInfo(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改特种作业人员信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["specialoperationspersonnelInfoRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateSpecialoperationspersonnelInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addSpecialoperationspersonnelInfo(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除特种作业人员信息编号为"' + _ids + '"的数据项？').then(function() {
    return delSpecialoperationspersonnelInfo(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/specialoperationspersonnelInfo/export', {
    ...queryParams.value
  }, `specialoperationspersonnelInfo_${new Date().getTime()}.xlsx`)
}

getList();
</script>
