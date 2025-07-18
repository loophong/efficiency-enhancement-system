<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主管科室" prop="responsibleDepartment">
        <el-input
          v-model="queryParams.responsibleDepartment"
          placeholder="请输入主管科室"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="相关方名称" prop="relatedPartyName">
        <el-input
          v-model="queryParams.relatedPartyName"
          placeholder="请输入相关方名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="主要联系人" prop="mainContactPerson">
        <el-input
          v-model="queryParams.mainContactPerson"
          placeholder="请输入主要联系人"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="我公司对接人员" prop="ourCompanyContactPerson">
        <el-input
          v-model="queryParams.ourCompanyContactPerson"
          placeholder="请输入我公司对接人员"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否签订安全管理协议" prop="isSafetyManagementAgreementSigned">
        <el-select
          v-model="queryParams.isSafetyManagementAgreementSigned"
          placeholder="请选择是否签订安全管理协议"
          clearable
        >
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="签订时间" prop="agreementSigningDate">
        <el-date-picker clearable
          v-model="queryParams.agreementSigningDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择签订时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="进厂时间" prop="factoryEntryStartDate">
        <el-date-picker clearable
          v-model="queryParams.factoryEntryStartDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择进厂时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="外来参观" prop="waiLai">
        <el-select
          v-model="queryParams.waiLai"
          placeholder="请选择外来参观"
          clearable
        >
          <el-option label="是" value="1"></el-option>
          <el-option label="否" value="0"></el-option>
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
          v-hasPermi="['security:relatedpartyledger:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:relatedpartyledger:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:relatedpartyledger:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:relatedpartyledger:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="relatedpartyledgerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="主管科室" align="center" prop="responsibleDepartment" />
      <el-table-column label="相关方名称" align="center" prop="relatedPartyName" />
      <el-table-column label="主要联系人" align="center" prop="mainContactPerson" />
      <el-table-column label="我公司对接人员" align="center" prop="ourCompanyContactPerson" />
      <el-table-column label="是否签订安全管理协议" align="center" prop="isSafetyManagementAgreementSigned">
        <template #default="scope">
          <span>{{ scope.row.isSafetyManagementAgreementSigned == '1' ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="签订时间" align="center" prop="agreementSigningDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.agreementSigningDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="进厂时间" align="center" prop="factoryEntryStartDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.factoryEntryStartDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="外来参观" align="center" prop="waiLai">
        <template #default="scope">
          <span>{{ scope.row.waiLai == '1' ? '是' : '否' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实习生" align="center" prop="externalVisitors" />
      <el-table-column label="劳务派遣，外包" align="center" prop="interns" />
      <el-table-column label="施工作业类" align="center" prop="laborDispatchOutsourcing" />
      <el-table-column label="清运、监测、服务" align="center" prop="constructionWork" />
      <el-table-column label="物流配送" align="center" prop="transportationInspectionServices" />
      <el-table-column label="驻厂相关方" align="center" prop="logisticsDelivery" />
      <el-table-column label="其他" align="center" prop="onSiteParties" />
      <el-table-column label="${comment}" align="center" prop="others" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:relatedpartyledger:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:relatedpartyledger:remove']">删除</el-button>
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

    <!-- 添加或修改相关方管理台账对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="relatedpartyledgerRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="主管科室" prop="responsibleDepartment">
          <el-input v-model="form.responsibleDepartment" placeholder="请输入主管科室" />
        </el-form-item>
        <el-form-item label="相关方名称" prop="relatedPartyName">
          <el-input v-model="form.relatedPartyName" placeholder="请输入相关方名称" />
        </el-form-item>
        <el-form-item label="主要联系人" prop="mainContactPerson">
          <el-input v-model="form.mainContactPerson" placeholder="请输入主要联系人" />
        </el-form-item>
        <el-form-item label="我公司对接人员" prop="ourCompanyContactPerson">
          <el-input v-model="form.ourCompanyContactPerson" placeholder="请输入我公司对接人员" />
        </el-form-item>
        <el-form-item label="是否签订安全管理协议" prop="isSafetyManagementAgreementSigned">
          <el-select v-model="form.isSafetyManagementAgreementSigned" placeholder="请选择是否签订安全管理协议">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="签订时间" prop="agreementSigningDate">
          <el-date-picker clearable
            v-model="form.agreementSigningDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择签订时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="进厂时间" prop="factoryEntryStartDate">
          <el-date-picker clearable
            v-model="form.factoryEntryStartDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择进厂时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="外来参观" prop="waiLai">
          <el-select v-model="form.waiLai" placeholder="请选择外来参观">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="${comment}" prop="others">
          <el-input v-model="form.others" type="textarea" placeholder="请输入内容" />
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

<script setup name="Relatedpartyledger">
import { listRelatedpartyledger, getRelatedpartyledger, delRelatedpartyledger, addRelatedpartyledger, updateRelatedpartyledger } from "@/api/security/relatedpartyledger";

const { proxy } = getCurrentInstance();

const relatedpartyledgerList = ref([]);
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
    responsibleDepartment: null,
    relatedPartyName: null,
    mainContactPerson: null,
    ourCompanyContactPerson: null,
    isSafetyManagementAgreementSigned: null,
    agreementSigningDate: null,
    factoryEntryStartDate: null,
    waiLai: null,
    externalVisitors: null,
    interns: null,
    laborDispatchOutsourcing: null,
    constructionWork: null,
    transportationInspectionServices: null,
    logisticsDelivery: null,
    onSiteParties: null,
    others: null
  },
  rules: {
    responsibleDepartment: [
      { required: true, message: "主管科室不能为空", trigger: "blur" }
    ],
    relatedPartyName: [
      { required: true, message: "相关方名称不能为空", trigger: "blur" }
    ],
    mainContactPerson: [
      { required: true, message: "主要联系人不能为空", trigger: "blur" }
    ],
    ourCompanyContactPerson: [
      { required: true, message: "我公司对接人员不能为空", trigger: "blur" }
    ],
    isSafetyManagementAgreementSigned: [
      { required: true, message: "是否签订安全管理协议不能为空", trigger: "blur" }
    ],
    agreementSigningDate: [
      { required: true, message: "签订时间不能为空", trigger: "blur" }
    ],
    factoryEntryStartDate: [
      { required: true, message: "进厂时间不能为空", trigger: "blur" }
    ],
    waiLai: [
      { required: true, message: "外来参观不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询相关方管理台账列表 */
function getList() {
  loading.value = true;
  listRelatedpartyledger(queryParams.value).then(response => {
    relatedpartyledgerList.value = response.rows;
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
    responsibleDepartment: null,
    relatedPartyName: null,
    mainContactPerson: null,
    ourCompanyContactPerson: null,
    isSafetyManagementAgreementSigned: "0",
    agreementSigningDate: null,
    factoryEntryStartDate: null,
    waiLai: "0",
    externalVisitors: [],
    interns: null,
    laborDispatchOutsourcing: null,
    constructionWork: null,
    transportationInspectionServices: null,
    logisticsDelivery: null,
    onSiteParties: null,
    others: null
  };
  proxy.resetForm("relatedpartyledgerRef");
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
  title.value = "添加相关方管理台账";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getRelatedpartyledger(_id).then(response => {
    form.value = response.data;
    form.value.externalVisitors = form.value.externalVisitors.split(",");
    open.value = true;
    title.value = "修改相关方管理台账";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["relatedpartyledgerRef"].validate(valid => {
    if (valid) {
      form.value.externalVisitors = form.value.externalVisitors.join(",");
      if (form.value.id != null) {
        updateRelatedpartyledger(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addRelatedpartyledger(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除相关方管理台账编号为"' + _ids + '"的数据项？').then(function() {
    return delRelatedpartyledger(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/relatedpartyledger/export', {
    ...queryParams.value
  }, `relatedpartyledger_${new Date().getTime()}.xlsx`)
}

getList();
</script>
