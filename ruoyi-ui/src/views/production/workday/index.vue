<template>

  <div>

    <div>
      <el-calendar v-model="calendarDate" first-day-of-week="1">
        <template #date-cell="{ data }">
          <div @contextmenu="(event) => rightClick(data.day, event)">
            <div class="date-content" style="display: flex;">
              {{ data.day.split('-').slice(2).join('-') }}
              <span v-if="isRestDay(data.day)" class="rest">休</span>
            </div>
            <div style="display: flex; justify-content: center;">
              <el-tag v-if="isProduction(data.day)" type="success">已排产</el-tag>
            </div>
          </div>
        </template>
      </el-calendar>
    </div>

    <div id="contextmenu" v-show="menuVisible" class="menu">
      <div class="contextmenu__item" @click="funcOrderScheduling">订单排产</div>
      <div class="contextmenu__item" @click="funcCancelScheduling">取消排产</div>
      <div class="contextmenu__item" @click="workdayConvert">设置工作日/休息日</div>
    </div>


  </div>

</template>

<script setup name="Workday">
import {listWorkday, getWorkday, setWorkday, getLimitWorkdayList} from "@/api/production/workday";
import {cancelScheduling} from "@/api/production/orderScheduling";

// 日历的日期
const calendarDate = ref(new Date())
// 右键菜单是否显示
const menuVisible = ref(false);


const {proxy} = getCurrentInstance();


const loading = ref(true);

// 休息日列表
const restDays = ref([]);

// 已排产日期
const productionDays = ref([]);

// 右键选择的日期
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
// function getList() {
//   loading.value = true;
//   listWorkday(queryParams.value).then(response => {
//     restDays.value = response.rows
//         .filter(row => row.status === 0)
//         .map(row => row.date.split('T')[0]);
//
//     productionDays.value = response.rows
//         .filter(row => row.productStatus === 1)
//         .map(row => row.date.split('T')[0]);
//     loading.value = false;
//   });
// }


/** 判断是否为休息日 */
function isRestDay(date) {
  return restDays.value.includes(date);
}

/** 判断是否为休息日 */
function isProduction(date) {
  return productionDays.value.includes(date);
}


// function rightClick(date, event) {
//
//   menuVisible.value = false // 先把模态框关死，目的是 第二次或者第n次右键鼠标的时候 它默认的是true
//   menuVisible.value = true // 显示模态窗口，跳出自定义菜单栏
//   selectDate = date
//   console.log("selectDate", selectDate)
//   console.log("event.clientY ", event.clientY)
//   console.log("event.clientX ", event.clientX)
//
//   // Set the position of the menu based on the mouse event coordinates
//   const menu = document.getElementById('contextmenu');
//   // menu.style.top = `${event.clientY }px`;
//   // menu.style.left = `${event.clientX }px`;
//   menu.style.top = `${event.clientY - 90}px`;
//   menu.style.left = `${event.clientX - 200}px`;
//
//   event.preventDefault() //关闭浏览器右键默认事件
// }
function rightClick(date, event) {
  console.log("date", date)

  menuVisible.value = false // 先把模态框关死，目的是 第二次或者第n次右键鼠标的时候 它默认的是true
  menuVisible.value = true // 显示模态窗口，跳出自定义菜单栏
  selectDate = date
  console.log("event.clientY ", event.clientY)
  console.log("event.clientX ", event.clientX)

  // Set the position of the menu based on the mouse event coordinates
  const menu = document.getElementById('contextmenu');
  // menu.style.top = `${event.clientY }px`;
  // menu.style.left = `${event.clientX }px`;
  menu.style.top = `${event.clientY - 90}px`;
  menu.style.left = `${event.clientX - 200}px`;

  event.preventDefault() //关闭浏览器右键默认事件
}

/**
 * 左键隐藏操作列表
 */
onMounted(() => {
  document.addEventListener('click', handleDocumentClick);

  handleCalendarDateChange(calendarDate.value);
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
    // getList();
    // getLimitWorkdayList()
    handleCalendarDateChange(calendarDate.value);
  });

}

watch(calendarDate, handleCalendarDateChange);

function handleCalendarDateChange(val) {
  if (val !== undefined && val !== null) {
    const formattedDate = new Intl.DateTimeFormat('en-CA').format(new Date(val));
    console.log(`当前值为：${formattedDate}`);
    getLimitWorkdayList(formattedDate).then(response => {
      console.log("response", response)
      restDays.value = response.rows
          .filter(row => row.status === 0)
          .map(row => row.date.split('T')[0]);

      productionDays.value = response.rows
          .filter(row => row.productStatus === 1)
          .map(row => row.date.split('T')[0]);
    });
  }
}


/**
 * 取消排产
 */
function funcCancelScheduling() {
  console.log("date" + selectDate)
  cancelScheduling(selectDate).then(response => {
    proxy.$modal.msgSuccess("取消排产成功");
    handleCalendarDateChange(calendarDate.value);
  })
}


/**
 * 订单排产
 */
function funcOrderScheduling() {
  // 检查当前日期是否排产，如果排产则提示用户已排产
  getWorkday(selectDate).then(response => {
    console.log("response", response)
    if (response.data.productStatus === 1) {
      proxy.$modal.msgError("当前日期已排产，请勿重复排产");

    }else {
      // 跳转进入排产页面
      proxy.$router.push({path: '/production/scheduling', query: {date: selectDate}})
    }
  });
}
// getList();

</script>


<style>

.is-selected {
  color: #1989fa;
}


.date-content {
  height: 70px;
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
