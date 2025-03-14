<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <!--      <el-form-item label="接收消息的用户 ID" prop="receiverId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.receiverId"-->
      <!--            placeholder="请输入接收消息的用户 ID"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="发送消息的用户 ID" prop="senderId">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.senderId"-->
      <!--            placeholder="请输入发送消息的用户 ID"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="标题" prop="title">
        <el-input
            v-model="queryParams.title"
            placeholder="请输入标题"
            clearable
            @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态(0未读，1已读)" clearable style="width: 100px">
          <el-option
              v-for="dict in sys_notifications_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"/>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="消息创建的时间" prop="createdAt">-->
      <!--        <el-input-->
      <!--            v-model="queryParams.createdAt"-->
      <!--            placeholder="请输入消息创建的时间"-->
      <!--            clearable-->
      <!--            @keyup.enter="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:notifications:add']">新增
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"-->
      <!--                   v-hasPermi="['production:notifications:edit']">修改-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:notifications:remove']">删除
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button type="warning" plain icon="Download" @click="handleExport"-->
      <!--                   v-hasPermi="['production:notifications:export']">导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="notificationsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="主键" align="center" prop="id"/>-->
      <el-table-column label="标题" align="center" prop="title"/>
      <el-table-column label="内容" align="center" prop="content"/>
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_notifications_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="消息类型" align="center" prop="type"/>
      <!--      <el-table-column label="接收消息的用户 ID" align="center" prop="receiverId"/>-->
      <!--      <el-table-column label="发送消息的用户 ID" align="center" prop="senderId"/>-->
      <el-table-column label="发送时间" align="center" prop="createdAt"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <!--          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"-->
          <!--                     v-hasPermi="['production:notifications:edit']">修改-->
          <!--          </el-button>-->
          <el-button link type="primary" icon="view" @click="handleRead(scope.row)">阅读
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:notifications:remove']">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改消息通知对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="notificationsRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="接收消息的用户 ID" prop="receiverId">
          <el-input v-model="form.receiverId" placeholder="请输入接收消息的用户 ID"/>
        </el-form-item>
        <el-form-item label="发送消息的用户 ID" prop="senderId">
          <el-input v-model="form.senderId" placeholder="请输入发送消息的用户 ID"/>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="状态(0未读，1已读)" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in sys_notifications_status"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="消息创建的时间" prop="createdAt">
          <el-input v-model="form.createdAt" placeholder="请输入消息创建的时间"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 处理消息通知对话框 -->
    <el-dialog title="消息阅读" v-model="handleVisible" width="500px" append-to-body>
      <el-form ref="notificationsRef" :model="form"  label-width="80px">
        <el-form-item label="接收消息的用户 ID" prop="receiverId">
          <el-input v-model="form.receiverId" placeholder="请输入接收消息的用户 ID"/>
        </el-form-item>
        <el-form-item label="发送消息的用户 ID" prop="senderId">
          <el-input v-model="form.senderId" placeholder="请输入发送消息的用户 ID"/>
        </el-form-item>
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题"/>
        </el-form-item>
        <el-form-item label="内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
<!--        <el-form-item label="状态(0未读，1已读)" prop="status">-->
<!--          <el-radio-group v-model="form.status">-->
<!--            <el-radio-->
<!--                v-for="dict in sys_notifications_status"-->
<!--                :key="dict.value"-->
<!--                :label="parseInt(dict.value)"-->
<!--            >{{ dict.label }}-->
<!--            </el-radio>-->
<!--          </el-radio-group>-->
<!--        </el-form-item>-->
        <el-form-item label="消息创建的时间" prop="createdAt">
          <el-input v-model="form.createdAt" placeholder="请输入消息创建的时间"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="handleMessage">点击处理</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


  </div>
</template>

<script setup name="Notifications">
import {
  listNotifications,
  getNotifications,
  delNotifications,
  addNotifications,
  updateNotifications,
  mine
} from "@/api/production/notifications";

const {proxy} = getCurrentInstance();
const {sys_notifications_status} = proxy.useDict('sys_notifications_status');

const notificationsList = ref([]);
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
    type: null,
    receiverId: null,
    senderId: null,
    title: null,
    content: null,
    status: null,
    createdAt: null
  },
  rules: {}
});

const {queryParams, form, rules} = toRefs(data);

/** 查询消息通知列表 */
function getList() {
  loading.value = true;
  // 此处有修改，改为获取我的消息列表
  mine(queryParams.value).then(response => {
    notificationsList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  handleVisible.value = false;
  getList();
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    type: null,
    receiverId: null,
    senderId: null,
    title: null,
    content: null,
    status: null,
    createdAt: null
  };
  proxy.resetForm("notificationsRef");
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
  title.value = "添加消息通知";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getNotifications(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改消息通知";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["notificationsRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateNotifications(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addNotifications(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除消息通知编号为"' + _ids + '"的数据项？').then(function () {
    return delNotifications(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/notifications/export', {
    ...queryParams.value
  }, `notifications_${new Date().getTime()}.xlsx`)
}

getList();

const handleVisible = ref(false);

function handleRead(row) {
  const _id = row.id || ids.value
  updateNotifications({
    id: _id,
    status: 1
  }).then(response => {});

  reset();
  getNotifications(_id).then(response => {
    form.value = response.data;
    handleVisible.value = true;
  });
}

// 此处为路由，跳转到处理的页面
function handleMessage(){
  console.log("消息类型为："+  form.value.type)
  handleVisible.value = false
  if(form.value.type === "production_plan_co_sign"){
    proxy.$router.push({
      path: '/production/planCoSign',
    })
  }else if(form.value.type === "production_special"){
    proxy.$router.push({
      path: '/production/cases',
    })
  }else{
    // 提示用户跳转失败，并联系开发人员
    proxy.$modal.msgError("消息类型为："+  form.value.type + "，跳转失败，请联系开发人员");
  }
}
</script>
