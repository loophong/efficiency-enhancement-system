<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="班组" prop="banZu">
        <el-input
          v-model="queryParams.banZu"
          placeholder="请输入班组"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="xingMing">
        <el-input
          v-model="queryParams.xingMing"
          placeholder="请输入姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="xingBie">
        <el-input
          v-model="queryParams.xingBie"
          placeholder="请输入性别"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出生年月" prop="chuShen">
        <el-date-picker clearable
          v-model="queryParams.chuShen"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择出生年月">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="何时进厂" prop="jingCTime">
        <el-date-picker clearable
          v-model="queryParams.jingCTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择何时进厂">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="何时进班组" prop="jingBanTime">
        <el-date-picker clearable
          v-model="queryParams.jingBanTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择何时进班组">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="文化程度" prop="wenhuaChengdu">
        <el-input
          v-model="queryParams.wenhuaChengdu"
          placeholder="请输入文化程度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原工种" prop="yuanGongZhong">
        <el-input
          v-model="queryParams.yuanGongZhong"
          placeholder="请输入原工种"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="现工种" prop="xianGongZhong">
        <el-input
          v-model="queryParams.xianGongZhong"
          placeholder="请输入现工种"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="技术等级" prop="level">
        <el-input
          v-model="queryParams.level"
          placeholder="请输入技术等级"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="家庭住址及联系电话" prop="homePhone">
        <el-input
          v-model="queryParams.homePhone"
          placeholder="请输入家庭住址及联系电话"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="何时调出本班组" prop="diaoChuBanzu">
        <el-date-picker clearable
          v-model="queryParams.diaoChuBanzu"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择何时调出本班组">
        </el-date-picker>
      </el-form-item>
      <!-- <el-form-item label="关联id" prop="relatedId">
        <el-input
          v-model="queryParams.relatedId"
          placeholder="请输入关联id"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item> -->
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
          v-hasPermi="['security:PeopleList:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['security:PeopleList:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['security:PeopleList:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['security:PeopleList:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="Upload"
          @click="handleImport"
          v-hasPermi="['security:PeopleList:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="PeopleListList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="班组" align="center" prop="banZu" />
      <el-table-column label="姓名" align="center" prop="xingMing" />
      <el-table-column label="性别" align="center" prop="xingBie" />
      <el-table-column label="出生年月" align="center" prop="chuShen" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.chuShen, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="何时进厂" align="center" prop="jingCTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.jingCTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="何时进班组" align="center" prop="jingBanTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.jingBanTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文化程度" align="center" prop="wenhuaChengdu" />
      <el-table-column label="原工种" align="center" prop="yuanGongZhong" />
      <el-table-column label="现工种" align="center" prop="xianGongZhong" />
      <el-table-column label="技术等级" align="center" prop="level" />
      <el-table-column label="家庭住址及联系电话" align="center" prop="homePhone" />
      <el-table-column label="何时调出本班组" align="center" prop="diaoChuBanzu" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.diaoChuBanzu, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="关联id" align="center" prop="relatedId" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['security:PeopleList:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['security:PeopleList:remove']">删除</el-button>
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

    <!-- 添加或修改班组人员清单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="PeopleListRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="班组" prop="banZu">
          <el-input v-model="form.banZu" placeholder="请输入班组" />
        </el-form-item>
        <el-form-item label="姓名" prop="xingMing">
          <el-input v-model="form.xingMing" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="xingBie">
          <el-input v-model="form.xingBie" placeholder="请输入性别" />
        </el-form-item>
        <el-form-item label="出生年月" prop="chuShen">
          <el-date-picker clearable
            v-model="form.chuShen"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择出生年月">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="何时进厂" prop="jingCTime">
          <el-date-picker clearable
            v-model="form.jingCTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择何时进厂">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="何时进班组" prop="jingBanTime">
          <el-date-picker clearable
            v-model="form.jingBanTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择何时进班组">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="文化程度" prop="wenhuaChengdu">
          <el-input v-model="form.wenhuaChengdu" placeholder="请输入文化程度" />
        </el-form-item>
        <el-form-item label="原工种" prop="yuanGongZhong">
          <el-input v-model="form.yuanGongZhong" placeholder="请输入原工种" />
        </el-form-item>
        <el-form-item label="现工种" prop="xianGongZhong">
          <el-input v-model="form.xianGongZhong" placeholder="请输入现工种" />
        </el-form-item>
        <el-form-item label="技术等级" prop="level">
          <el-input v-model="form.level" placeholder="请输入技术等级" />
        </el-form-item>
        <el-form-item label="家庭住址及联系电话" prop="homePhone">
          <el-input v-model="form.homePhone" placeholder="请输入家庭住址及联系电话" />
        </el-form-item>
        <el-form-item label="何时调出本班组" prop="diaoChuBanzu">
          <el-date-picker clearable
            v-model="form.diaoChuBanzu"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择何时调出本班组">
          </el-date-picker>
        </el-form-item>
        <!-- <el-form-item label="关联id" prop="relatedId">
          <el-input v-model="form.relatedId" placeholder="请输入关联id" />
        </el-form-item> -->
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 班组人员清单导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="PeopleList">
import { listPeopleList, getPeopleList, delPeopleList, addPeopleList, updatePeopleList, importPeopleList, downloadTemplate } from "@/api/security/PeopleList";
import { getToken } from "@/utils/auth";

const { proxy } = getCurrentInstance();

const PeopleListList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

/*** 用户导入参数 */
const upload = reactive({
  // 是否显示弹出层（用户导入）
  open: false,
  // 弹出层标题（用户导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的用户数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: { Authorization: "Bearer " + getToken() },
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/security/PeopleList/importData"
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    banZu: null,
    xingMing: null,
    xingBie: null,
    chuShen: null,
    jingCTime: null,
    jingBanTime: null,
    wenhuaChengdu: null,
    yuanGongZhong: null,
    xianGongZhong: null,
    level: null,
    homePhone: null,
    diaoChuBanzu: null,
    relatedId: null
  },
  rules: {
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询班组人员清单列表 */
function getList() {
  loading.value = true;
  listPeopleList(queryParams.value).then(response => {
    PeopleListList.value = response.rows;
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
    banZu: null,
    xingMing: null,
    xingBie: null,
    chuShen: null,
    jingCTime: null,
    jingBanTime: null,
    wenhuaChengdu: null,
    yuanGongZhong: null,
    xianGongZhong: null,
    level: null,
    homePhone: null,
    diaoChuBanzu: null,
    relatedId: null
  };
  proxy.resetForm("PeopleListRef");
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
  title.value = "添加班组人员清单";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getPeopleList(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改班组人员清单";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["PeopleListRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updatePeopleList(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addPeopleList(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除班组人员清单编号为"' + _ids + '"的数据项？').then(function() {
    return delPeopleList(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('security/PeopleList/export', {
    ...queryParams.value
  }, `PeopleList_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "班组人员清单导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download('security/PeopleList/importTemplate', {}, `班组人员清单导入模板_${new Date().getTime()}.xlsx`, 'post');
}

/** 文件上传中处理 */
function handleFileUploadProgress() {
  upload.isUploading = true;
}

/** 文件上传成功处理 */
function handleFileSuccess(response) {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].clearFiles();
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
  getList();
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
}

getList();
</script>
