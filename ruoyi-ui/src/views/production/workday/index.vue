<template>
  <div>
    <div>
      <el-calendar v-model="value" first-day-of-week="1">
        <template #date-cell="{ data }">
          <div @contextmenu="(event) => rightClick(data.day, event)">
            <div class="date-content" style="display: flex;">
              {{ data.day.split('-').slice(2).join('-') }}
              <span v-if="isRestDay(data.day)" class="rest">休</span>
            </div>
            <div style="display: flex; justify-content: center;">
              <el-tag type="success">已排产</el-tag>
            </div>
          </div>
        </template>
      </el-calendar>
    </div>

    <div id="contextmenu" v-show="menuVisible" class="menu">
      <div class="contextmenu__item" @click="orderScheduling">订单排产</div>
      <div class="contextmenu__item" @click="workdayConvert">设置工作日/休息日</div>
    </div>

  </div>

</template>

<script setup name="Workday">
import {listWorkday, setWorkday} from "@/api/production/workday";


const value = ref(new Date())
const dialogVisible = ref(false);
const menuVisible = ref(false);


const {proxy} = getCurrentInstance();

const workdayList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

//休息日列表
const restDays = ref([]);
// 记忆右击的日期
let selectDate = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    date: null,
    status: null
  },
  rules: {
    date: [
      {required: true, message: "日期不能为空", trigger: "blur"}
    ],
    status: [
      {required: true, message: "状态：1、工作日  2、休息日不能为空", trigger: "change"}
    ]
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询工作日列表 */
function getList() {
  loading.value = true;
  listWorkday().then(response => {
    restDays.value = response.rows.map(row => row.date.split('T')[0]);
    loading.value = false;
  });
}

/** 判断是否为休息日 */
function isRestDay(date) {
  return restDays.value.includes(date);
}

/**
 * 右击日期
 */
function rightClick(date, event) {

  menuVisible.value = false // 先把模态框关死，目的是 第二次或者第n次右键鼠标的时候 它默认的是true
  menuVisible.value = true // 显示模态窗口，跳出自定义菜单栏
  selectDate = date
  console.log("selectDate", selectDate)
  console.log("event.clientY ", event.clientY)
  console.log("event.clientX ", event.clientX)

  // Set the position of the menu based on the mouse event coordinates
  const menu = document.getElementById('contextmenu');

  menu.style.top = `${event.clientY - 90}px`;
  menu.style.left = `${event.clientX - 200}px`;

  event.preventDefault() //关闭浏览器右键默认事件
}

/**
 * 左击关闭操作菜单
 */
onMounted(() => {
  document.addEventListener('click', handleDocumentClick);
});

function handleDocumentClick(event) {
  const menu = document.getElementById('contextmenu');
  if (menu && !menu.contains(event.target)) {
    menuVisible.value = false;
  }
}

/**
 * 设置工作日
 */
function workdayConvert() {

  console.log("date" + selectDate)
  let status = 0;
  // 检查当前时间是否为工作日
  if (isRestDay(selectDate)) {
    console.log("date是工作日" + selectDate)
    status = 1;
  } else {
    console.log("date不是工作日" + selectDate)
  }
  console.log("请求参数date" + selectDate)
  console.log("请求参数status" + status)
  setWorkday(selectDate, status).then(response => {
    proxy.$modal.msgSuccess("设置成功");
    menuVisible.value = false;
    getList();
  });

}


getList();
</script>


<style>
.date-content {
  height: 40px;
  display: flex;
  align-items: center;
  font-size: 14px;
}

.date-content .rest {
  color: #fff;
  border-radius: 50%;
  background: rgb(250, 124, 77);
  width: 20px;
  height: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px;
  margin-left: 50%;
}

.date-content .text {
  font-size: 14px;
}


.contextmenu__item {
  display: block;
  line-height: 34px;
  text-align: center;
}

.contextmenu__item:not(:last-child) {
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
}

.menu {
  position: absolute;
  background-color: #fff;
  width: 130px;
  /*height: 106px;*/
  font-size: 12px;
  color: #444040;
  border-radius: 4px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border-radius: 3px;
  border: 1px solid rgba(0, 0, 0, 0.15);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
  white-space: nowrap;
  z-index: 1000;
}

.contextmenu__item:hover {
  cursor: pointer;
  background: #66b1ff;
  border-color: #66b1ff;
  color: #fff;
}

</style>
