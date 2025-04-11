<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNumber">
        <el-input
            v-model="queryParams.orderNumber"
            placeholder="请输入订单号"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="上线日期" prop="onlineDate">
        <el-date-picker clearable v-model="queryParams.onlineDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择上线日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="故障原因" prop="faultReason">
        <el-select v-model="queryParams.faultReason" placeholder="请选择故障原因" clearable style="width: 150px">
          <el-option v-for="dict in production_fault_reason" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="责任科室" prop="responsibleDepartment">
        <el-select v-model="queryParams.responsibleDepartment" placeholder="请选择责任科室" clearable style="width: 150px">
          <el-option v-for="dict in system_dept" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否为重大故障" prop="isMajorFault" label-width="120px">
        <el-select v-model="queryParams.isMajorFault" placeholder="请选择是否为重大故障" clearable style="width: 150px">
          <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态" prop="approvalStatus">
        <el-select v-model="queryParams.approvalStatus" placeholder="请选择处理状态" clearable style="width: 150px">
          <el-option v-for="dict in production_special_statue" :key="dict.value" :label="dict.label"
                     :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="处理时间" prop="uploadDate">
        <el-date-picker clearable v-model="queryParams.uploadDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择填报时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:cases:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:cases:edit']">修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:cases:remove']">删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="warning" plain icon="Download" @click="handleExport" v-hasPermi="['production:cases:export']">-->
      <!--          导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="casesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="主键" align="center" prop="id"/>-->
      <el-table-column label="订单号" align="center" prop="orderNumber"/>
      <el-table-column label="上线日期" align="center" prop="onlineDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.onlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="故障原因" align="center" prop="faultReason">
        <template #default="scope">
          <dict-tag :options="production_fault_reason" :value="scope.row.faultReason"/>
        </template>
      </el-table-column>
      <el-table-column label="故障说明" align="center" prop="faultDescription"/>
      <el-table-column label="责任科室" align="center" prop="responsibleDepartment">
        <template #default="scope">
          <dict-tag :options="system_dept" :value="scope.row.responsibleDepartment"/>
        </template>
      </el-table-column>
      <el-table-column label="处理状态" align="center" prop="approvalStatus">
        <template #default="scope">
          <dict-tag :options="production_special_statue" :value="scope.row.approvalStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="是否为重大故障" align="center" prop="isMajorFault">
        <template #default="scope">
          <dict-tag :options="production_yes_no" :value="scope.row.isMajorFault"/>
        </template>
      </el-table-column>
      <el-table-column label="解决方案" align="center" prop="solution"/>
      <el-table-column label="处理时间" align="center" prop="uploadDate" width="180">
<!--        <template #default="scope">-->
<!--          <span>{{ parseTime(scope.row.uploadDate, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>-->
<!--        </template>-->
      </el-table-column>
      <el-table-column label="文件" align="center" prop="files"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <!--                    <el-button link type="primary" icon="View" @click="handleUpdate(scope.row)"-->
          <!--                               v-hasPermi="['production:cases:query']">修改-->
          <!--                    </el-button>-->
          <el-button v-if="scope.row.approvalStatus === 0" link type="primary" icon="Edit"
                     @click="handleSpecial(scope.row)"
                     v-hasPermi="['production:cases:handle']">处理
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:cases:remove']">删除
          </el-button>
          <el-button link type="primary" icon="View" @click="viewResult(scope.row)"
                     v-hasPermi="['production:cases:query']">查看
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改特殊情况对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="casesRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable v-model="form.onlineDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障原因" prop="faultReason">
          <el-select v-model="form.faultReason" placeholder="请选择故障原因">
            <el-option v-for="dict in production_fault_reason" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任科室" prop="responsibleDepartment">
          <el-select v-model="form.responsibleDepartment" placeholder="请选择责任科室">
            <el-option v-for="dict in system_dept" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障说明" prop="faultDescription">
          <el-input v-model="form.faultDescription" placeholder="请输入故障说明" type="textarea"/>
        </el-form-item>

        <!--        <el-form-item label="是否为重大故障" prop="isMajorFault">-->
        <!--          <el-select v-model="form.isMajorFault" placeholder="请选择是否为重大故障">-->
        <!--            <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label"-->
        <!--                       :value="parseInt(dict.value)"></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="处理状态" prop="approvalStatus">-->
        <!--          <el-select v-model="form.approvalStatus" placeholder="请选择处理状态">-->
        <!--            <el-option v-for="dict in production_special_statue" :key="dict.value" :label="dict.label"-->
        <!--                       :value="parseInt(dict.value)"></el-option>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="填报时间" prop="uploadDate">-->
        <!--          <el-date-picker clearable v-model="form.uploadDate" type="date" value-format="YYYY-MM-DD"-->
        <!--                          placeholder="请选择填报时间">-->
        <!--          </el-date-picker>-->
        <!--        </el-form-item>-->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 处理特殊情况对话框 -->
    <el-dialog title="特殊情况处理" v-model="handleVisible" width="500px" append-to-body>
      <el-form ref="handleRef" :model="handleForm" :rules="handleRules" label-width="120px">
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="handleForm.orderNumber" disabled placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable v-model="handleForm.onlineDate" disabled type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障原因" prop="faultReason">
          <el-select v-model="handleForm.faultReason" disabled placeholder="请选择故障原因">
            <el-option v-for="dict in production_fault_reason" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障说明" prop="faultDescription">
          <el-input v-model="handleForm.faultDescription" disabled placeholder="请输入故障说明"/>
        </el-form-item>
        <el-form-item label="责任科室" prop="responsibleDepartment">
          <el-select v-model="handleForm.responsibleDepartment" disabled placeholder="请选择责任科室">
            <el-option v-for="dict in system_dept" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否为重大故障" prop="isMajorFault">
          <el-select v-model="handleForm.isMajorFault" placeholder="请选择是否为重大故障"
                     @change="handleMajorFaultChange">
            <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="handleForm.isMajorFault" label="通知用户" prop="notifyUserList">
          <el-select v-model="handleForm.notifyUserList" multiple placeholder="请选择分配用户">
            <el-option v-for="user in usersList" :key="user.userId" :label="user.nickName + '（' + user.deptName + '）'"
                       :value="user.userId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="解决方案" prop="solution">
          <el-input v-model="handleForm.solution" placeholder="请输入解决方案" type="textarea"/>
        </el-form-item>
        <el-form-item label="文件" prop="files">
          <file-upload v-model="handleForm.files"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitHandleResult">确 定</el-button>
          <el-button @click="handleCancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 查看对话框 -->
    <el-dialog :title="title" v-model="viewResultVisible" width="500px" append-to-body>
      <el-form ref="resultRef" :model="resultForm" label-width="120px">
        <el-form-item label="订单号" prop="orderNumber">
          <el-input disabled v-model="resultForm.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker disabled clearable v-model="resultForm.onlineDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障原因" prop="faultReason">
          <el-select disabled v-model="resultForm.faultReason" placeholder="请选择故障原因">
            <el-option v-for="dict in production_fault_reason" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任科室" prop="responsibleDepartment">
          <el-select disabled v-model="resultForm.responsibleDepartment" placeholder="请选择责任科室">
            <el-option v-for="dict in system_dept" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障说明" prop="faultDescription">
          <el-input disabled v-model="resultForm.faultDescription" placeholder="请输入故障说明" type="textarea"/>
        </el-form-item>
        <el-form-item label="是否为重大故障" prop="isMajorFault">
          <el-select disabled v-model="resultForm.isMajorFault" placeholder="请选择是否为重大故障">
            <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="处理状态" prop="approvalStatus">
          <el-select disabled v-model="resultForm.approvalStatus" placeholder="请选择处理状态">
            <el-option v-for="dict in production_special_statue" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item  label="处理时间" prop="uploadDate">
          <el-date-picker disabled clearable v-model="resultForm.uploadDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择填报时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="解决方案" prop="solution">
          <el-input disabled v-model="resultForm.solution" placeholder="请输入解决方案" type="textarea"/>
        </el-form-item>
        <el-form-item label="文件" prop="files">
          <file-view v-model="resultForm.files"/>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script setup name="Cases">
import {listCases, getCases, delCases, addCases, updateCases} from "@/api/production/special";
import {listUserAndDept} from "@/api/system/user";
import fileView from "@/views/production/fileView";

const {proxy} = getCurrentInstance();
const {
  production_fault_reason,
  production_special_statue,
  production_yes_no,
  system_dept
} = proxy.useDict('production_fault_reason', 'production_special_statue', 'production_yes_no', 'system_dept');

const casesList = ref([]);
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
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    responsibleDepartment: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null
  },

  rules: {
    orderNumber: [
      {required: true, message: "订单号不能为空", trigger: "blur"}
    ],
    onlineDate: [
      {required: true, message: "上线日期不能为空", trigger: "blur"}
    ],
    faultReason: [
      {required: true, message: "故障原因不能为空", trigger: "change"}
    ],
    faultDescription: [
      {required: true, message: "故障说明不能为空", trigger: "blur"}
    ],
    responsibleDepartment: [
      {required: true, message: "责任科室不能为空", trigger: "change"}
    ],
  },

  handleRules: {
    orderNumber: [
      {required: true, message: "订单号不能为空", trigger: "blur"}
    ],
    onlineDate: [
      {required: true, message: "上线日期不能为空", trigger: "blur"}
    ],
    faultReason: [
      {required: true, message: "故障原因不能为空", trigger: "change"}
    ],
    faultDescription: [
      {required: true, message: "故障说明不能为空", trigger: "blur"}
    ],
    responsibleDepartment: [
      {required: true, message: "责任科室不能为空", trigger: "change"}
    ],
    solution: [
      {required: true, message: "解决方案不能为空", trigger: "change"}
    ],
    isMajorFault: [
      {required: true, message: "是否为重大故障不能为空", trigger: "change"}
    ]
  }
});

const {queryParams, form, rules, handleRules} = toRefs(data);

const usersList = ref([]);

/** 查询特殊情况列表 */
function getList() {
  loading.value = true;
  listCases(queryParams.value).then(response => {
    casesList.value = response.rows;
    total.value = response.total;
    loading.value = false;

    // 对文件名进行修改
    // 当前文件名字为files:"/profile/upload/2025/04/04/新建 DOCX 文档_20250404154611A001.docx,/profile/upload/2025/04/04/新建文本文档 (2)_20250404154616A002.txt,/profile/upload/2025/04/04/神经网络验证_20250404154622A003.docx"
    casesList.value.forEach(item => {
      if (item.files) {
        item.files = item.files.split(",").map(file => file.split("/").pop());
        console.log("处理后的文件名为"+JSON.stringify(item.files))
      }
    });
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
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    faultDescription: null,
    responsibleDepartment: null,
    solution: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null
  };
  proxy.resetForm("casesRef");
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
  title.value = "添加特殊情况";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getCases(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改特殊情况";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["casesRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateCases(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.approvalStatus = 0;
        addCases(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除特殊情况编号为"' + _ids + '"的数据项？').then(function () {
    return delCases(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/cases/export', {
    ...queryParams.value
  }, `cases_${new Date().getTime()}.xlsx`)
}

getList();

const handleVisible = ref(false);
const handleForm = ref({
  id: null,
  orderNumber: null,
  onlineDate: null,
  faultReason: null,
  faultDescription: null,
  responsibleDepartment: null,
  solution: null,
  isMajorFault: null,
  approvalStatus: null,
  uploadDate: null,
  notifyUserList: [] // 添加字段用于存储分配的用户ID
});

// 取消按钮
function handleCancel() {
  handleVisible.value = false;
  handleReset();
}

function handleReset() {
  handleForm.value = {
    id: null,
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    faultDescription: null,
    responsibleDepartment: null,
    solution: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null,
    files: null
  };
  proxy.resetForm("handleRef");
}

/**
 * 特殊情况处理
 */
function handleSpecial(row) {
  reset();
  const _id = row.id || ids.value
  getCases(_id).then(response => {
    handleForm.value = response.data;
    // if (handleForm.value.isMajorFault) {
    //   listUserAndDept().then(response => {
    //     usersList.value = response.data;
    //   });
    // }
    handleVisible.value = true;
    title.value = "特殊情况处理";
  });
}

/** 提交按钮 */
function submitHandleResult() {
  handleForm.value.approvalStatus = 1;
  console.log("处理结果为：" + handleForm.value)
  proxy.$refs["handleRef"].validate(valid => {
    if (valid) {
      updateCases(handleForm.value).then(response => {
        proxy.$modal.msgSuccess("处理成功");
        handleVisible.value = false;
        getList();
      });
    }
  })
}

function handleMajorFaultChange(value) {
  if (value) {
    listUserAndDept().then(response => {
      usersList.value = response.data;
    });
  }
}


const viewResultVisible = ref(false);
const resultForm = ref({});

function viewResult(row) {
  resetResult();
  const _id = row.id
  getCases(_id).then(response => {
    resultForm.value = response.data;
    viewResultVisible.value = true;
    title.value = "查看";
  });
}

function resetResult() {
  resultForm.value = {
    id: null,
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    faultDescription: null,
    responsibleDepartment: null,
    solution: null,
    isMajorFault: null,
    approvalStatus: null,
    uploadDate: null,
    files: null
  };
  proxy.resetForm("resultRef");
}
</script>
