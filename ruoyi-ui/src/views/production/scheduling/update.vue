<template>
  <div class="app-container">

    <!-- 第一步:定义出4个步骤  -->
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="第一步" description="设置生产日期和产能"/>
      <el-step title="第二步" description="特殊订单排产"/>
      <el-step title="第三步" description="系统排产"/>
      <el-step title="第四步" description="未排产订单列表"/>
      <!--      <el-step title="第五步" description="预测订单生成"/>-->
<!--      <el-step title="第六步" description="生成日生产计划"/>-->
    </el-steps>


    <!-- 第三步:定义4个盒子对象active => 1 到 4-->
    <div v-show="active === 1">
      <!-- 第四步:放置表单项-->
      <!-- 根据后台传入的各个型号产能，动态生成计数器个数    -->
      <el-form :model="capacity" label-width="120px">
        <el-form-item label="排产日期">
          <el-date-picker v-model="productionDate" type="date" date-format="yyyy-MM-dd" placeholder="选择日期"/>
        </el-form-item>

        <div  style="margin-bottom: 10px"  >
          <el-row :gutter="20">
            <el-col :span="1"></el-col>
            <el-col :span="2" style="text-align: center; font-weight: bold;">产能车型</el-col>
            <el-col :span="2" style="text-align: center; font-weight: bold;">产能设置</el-col>
            <el-col :span="2" style="text-align: center; font-weight: bold;">产能需求</el-col>
          </el-row>
        </div>

        <div v-for="(item, index) in capacity" :key="index">

          <!--          <el-form-item :label="item.capacityType">-->
          <!--            <el-input-number v-model="item.productionQuantity" :min="0"></el-input-number>-->
          <!--            <el-input v-model="item.requiredQuantity" readonly syle="width: 150px;"></el-input>-->
          <!--          </el-form-item>-->

          <div style="display: flex; align-items: center;">
            <el-form-item :label="item.capacityType" label-width="200px">
              <el-input-number v-model="item.productionQuantity" :min="0"></el-input-number>
              <el-input v-model="item.requiredQuantity" readonly style="width: 150px; margin-left: 10px;"></el-input>
            </el-form-item>
          </div>
        </div>

      </el-form>
    </div>

    <div v-show="active === 2">
      <div style="display: flex; margin: 20px; width:95%;">
        <div style="flex: 1; border: #00afff solid;">
          <!--   渲染产能总数和已使用产能数量   -->
          <el-form label-width="120px">
            <el-col style="margin: 20px">
              <div v-for="(item, index) in usedCapacity" :key="index">
                <el-form-item :label="item.capacityType">
                  <el-input v-model="item.productionQuantity" readonly style="width: 150px;"
                            input-style="font-size: large; font-weight: bold;" size="large" placeholder="Please input">
                    <template #append>
                      <span style="width: 30px; font-size: large; font-weight: bold;">
                       {{ getCapacity(item.capacityType) }}
                      </span>
                    </template>
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-form>
        </div>


        <!--   加急订单页面   -->
        <div style="flex: 4; border: #2d2d2d solid;">

          <el-table ref="urgentTable" :data="urgentList" width="auto" height="500px"
                    @selection-change="handleUrgentSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="网点名称" align="center" prop="branch"/>
            <el-table-column label="合同号" align="center" prop="contractNumber"/>
            <el-table-column label="订单号" align="center" prop="orderNumber"/>
            <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="车型" align="center" prop="vehicleModel" width="100"/>
            <el-table-column label="数量" align="center" prop="quantity"/>
            <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
            <el-table-column label="备注信息" align="center" prop="remarks" width="150"/>
            <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="缺件信息" align="center" prop="missingPartRecords"/>
            <el-table-column label="生产周期(天)" align="center" prop="productionCycle"/>
            <el-table-column label="产能型号" align="center" prop="capacityType"/>
            <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否超期" align="center" prop="isOverdue"/>
          </el-table>
        </div>
      </div>


    </div>

    <!--   一般订单页面   -->
    <div v-show="active === 3">
      <div style="display: flex; margin: 20px; width:95%;">
        <div style="flex: 1; border: #00afff solid;">
          <!--   渲染产能总数和已使用产能数量   -->
          <el-form label-width="120px">
            <el-col style="margin: 20px">
              <div v-for="(item, index) in usedCapacity" :key="index">
                <el-form-item :label="item.capacityType">
                  <el-input v-model="item.productionQuantity" readonly style="width: 150px;"
                            input-style="font-size: large; font-weight: bold;" size="large" placeholder="Please input">
                    <template #append>
                      <span style="width: 30px; font-size: large; font-weight: bold;">
                       {{ getCapacity(item.capacityType) }}
                      </span>
                    </template>
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-form>
        </div>

        <div style="flex: 4; border: #2d2d2d solid;">
          <!--   展示一般订单   -->
          <el-table ref="standardTable" :data="standardList" height="500px" width="auto"
                    @selection-change="handleStandardSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="网点名称" align="center" prop="branch"/>
            <el-table-column label="合同号" align="center" prop="contractNumber"/>
            <el-table-column label="订单号" align="center" prop="orderNumber"/>
            <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="车型" align="center" prop="vehicleModel" width="100"/>
            <el-table-column label="数量" align="center" prop="quantity"/>
            <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
            <el-table-column label="备注信息" align="center" prop="remarks" width="150"/>
            <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="生产周期(天)" align="center" prop="productionCycle"/>
            <el-table-column label="产能型号" align="center" prop="capacityType"/>
            <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否超期" align="center" prop="isOverdue"/>
          </el-table>
        </div>
      </div>
    </div>

    <div v-show="active === 4">
      <div style="display: flex; margin: 20px; width:95%;">
        <div style="flex: 1; border: #00afff solid;">
          <!--   渲染产能总数和已使用产能数量   -->
          <el-form label-width="120px">
            <el-col style="margin: 20px">
              <div v-for="(item, index) in usedCapacity" :key="index">
                <el-form-item :label="item.capacityType">
                  <el-input v-model="item.productionQuantity" readonly style="width: 150px;"
                            input-style="font-size: large; font-weight: bold;" size="large" placeholder="Please input">
                    <template #append>
                      <span style="width: 30px; font-size: large; font-weight: bold;">
                       {{ getCapacity(item.capacityType) }}
                      </span>
                    </template>
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-form>
        </div>

        <div style="flex: 4; border: #2d2d2d solid;">
          <!--   展示一般订单   -->
          <el-table ref="unscheduledTable" :data="unscheduledList" height="500px" width="auto"
                    @selection-change="handleUnscheduledSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="网点名称" align="center" prop="branch"/>
            <el-table-column label="合同号" align="center" prop="contractNumber"/>
            <el-table-column label="订单号" align="center" prop="orderNumber"/>
            <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="车型" align="center" prop="vehicleModel" width="100"/>
            <el-table-column label="数量" align="center" prop="quantity"/>
            <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
            <el-table-column label="备注信息" align="center" prop="remarks" width="150"/>
            <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="生产周期(天)" align="center" prop="productionCycle"/>
            <el-table-column label="产能型号" align="center" prop="capacityType"/>
            <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否超期" align="center" prop="isOverdue"/>
          </el-table>
        </div>
      </div>
    </div>

    <div v-show="active === 5">

      <div style="display: flex; margin: 20px; width:95%;">
        <div style="flex: 1; border: #00afff solid;">
          <!--   渲染产能总数和已使用产能数量   -->
          <el-form label-width="120px">
            <el-col style="margin: 20px">
              <div v-for="(item, index) in usedCapacity" :key="index">
                <el-form-item :label="item.capacityType">
                  <el-input v-model="item.productionQuantity" readonly style="width: 150px;"
                            input-style="font-size: large; font-weight: bold;" size="large" placeholder="Please input">
                    <template #append>
                      <span style="width: 30px; font-size: large; font-weight: bold;">
                       {{ getCapacity(item.capacityType) }}
                      </span>
                    </template>
                  </el-input>
                </el-form-item>
              </div>
            </el-col>
          </el-form>
        </div>

        <div style="flex: 4; border: #2d2d2d solid;">
          <!--   展示预测订单   -->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" plain icon="Plus" v-hasPermi="['production:orderStatistics:add']">
                新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="success" plain icon="Edit" :disabled="single"
                         v-hasPermi="['production:orderStatistics:edit']">修改
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" plain icon="Delete" :disabled="multiple"
                         v-hasPermi="['production:orderStatistics:remove']">删除
              </el-button>
            </el-col>
          </el-row>

          <el-table ref="unscheduledTable" :data="unscheduledList" height="500px" width="auto"
                    @selection-change="handleUnscheduledSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="网点名称" align="center" prop="branch"/>
            <el-table-column label="合同号" align="center" prop="contractNumber"/>
            <el-table-column label="订单号" align="center" prop="orderNumber"/>
            <el-table-column label="接单日期" align="center" prop="orderDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.orderDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="车型" align="center" prop="vehicleModel" width="100"/>
            <el-table-column label="数量" align="center" prop="quantity"/>
            <!--            <el-table-column label="车号" align="center" prop="vehicleNumber"/>-->
            <el-table-column label="备注信息" align="center" prop="remarks" width="150"/>
            <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="采购回复到货时间" align="center" prop="procurementArrivalDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.procurementArrivalDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="生产周期(天)" align="center" prop="productionCycle"/>
            <el-table-column label="产能型号" align="center" prop="capacityType"/>
            <el-table-column label="最晚上线日期" align="center" prop="latestOnlineDate" width="100">
              <template #default="scope">
                <span>{{ parseTime(scope.row.latestOnlineDate, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否超期" align="center" prop="isOverdue"/>
          </el-table>
        </div>
      </div>


    </div>

    <div v-show="active === 6">666666666</div>


    <!--    第五步:设置上一步和下一步的按钮-->
    <div style="  display: flex;  justify-content: center;">
      <el-button v-if="active > 1" type="primary" size="large" @click="pre">上一步</el-button>
      <el-button v-if="active < 4" type="primary" size="large" @click="next">下一步</el-button>
      <el-button v-if="active === 4" type="success" size="large" @click="submitForm">完成</el-button>
    </div>


  </div>
</template>

<script setup name="Scheduling">
import {getCurrentInstance, ref} from "vue";
import {all} from "@/api/production/capacity.js";
import {getOrders, schedulingOrders} from "@/api/production/orderScheduling.js";
import dayjs from 'dayjs';


import useTagsViewStore from "@/store/modules/tagsView";

const route = useRoute(); // 获取路由实例
const router = useRouter();

const {proxy} = getCurrentInstance();

// 初始化步骤条为1
let active = ref(1);
// 总产能列表
const capacity = ref([]);
// 已使用产能列表
const usedCapacity = ref([]);
// 需要产能
const requiredCapacity = ref([]);

// 排产日期
const productionDate = ref('')

// 所有订单列表
const allOrders = ref([]);
// 加急订单列表
const urgentList = ref([]);
// 一般订单列表
const standardList = ref([]);
// 未排产订单列表
const unscheduledList = ref([]);


// 步骤条下一步的方法
function next() {
  if (active.value === 1) {
    // 校验日期是否选择
    if (productionDate.value === '') {
      proxy.$modal.msgError("请选择排产日期")
      return
    }
  }
  if (active.value < 6) {
    active.value++
  }

  if (active.value === 2) {
    updateSelectionStatus()
  }

  if (active.value === 3) {
    updateSelectionStatus()
  }

  if (active.value === 4) {
    getUnscheduledList()
  }
  if (active.value === 5) {
    active.value++
  }
  console.log("当前active：" + active.value)
}

// 步骤条上一步的方法
function pre() {
  if (active.value > 1) {
    active.value--
  }

  if (active.value === 3) {
    updateSelectionStatus()
  }
  if (active.value === 4) {
    getUnscheduledList()
  }
  console.log("当前active：" + active.value)
}

function getUnscheduledList() {
  unscheduledList.value = allOrders.value.filter(item => {
    return item.isScheduling === 0
  })
}

/** 初始化时，获取产能列表 和 当日使用的产能 */
function getCapacityList() {
  all(productionDate.value).then(response => {
    console.log("产能列表：" + response.data)
    if (response.data.dailyUsedCapacityEntities === null || response.data.dailyUsedCapacityEntities === '') {
      console.log("当日没有排产")
    } else {
      console.log("当日已排产")
      response.data.capacityEntities.forEach(item => {
        let a = response.data.dailyUsedCapacityEntities.find(used => used.capacityType === item.capacityType)
        console.log("当日已排产：" + JSON.stringify(a))
        if (a !== undefined) {
          capacity.value.push({
            'capacityType': item.capacityType,
            'productionQuantity': a.quantitySettings,
            'requiredQuantity': 0
          })
          usedCapacity.value.push({
            'capacityType': item.capacityType,
            'productionQuantity': a.productionQuantity
          })
          requiredCapacity.value.push({
            'capacityType': item.capacityType,
            'productionQuantity': 0
          })
        }
      })
    }
  })
}

// 使用 onMounted 钩子在组件挂载时调用 getCapacityList
onMounted(() => {
  const dateParam = route.query.date; // 假设 date 参数在 query 中
  if (dateParam) {
    productionDate.value = dateParam;
  }

  getCapacityList();

  getOrderList();


});

function getCapacity(capacityType) {
  const used = capacity.value.find(item => item.capacityType === capacityType);
  return used ? used.productionQuantity : 0;
}


// 获取总的订单列表
function getOrderList() {
  getOrders(productionDate.value).then(response => {
    // console.log("一般订单：" + JSON.stringify(response.data))

    // 从订单列表中划分出一般订单和加急订单
    allOrders.value = response.data

    // update :3-12 修改，将加急订单改为特殊订单，特殊订单包括 加急订单 和 缺件订单
    response.data.forEach(item => {
      if (item.isUrgent === 1 || item.procurementArrivalDate !== null) {
        urgentList.value.push(item)
      } else {
        standardList.value.push(item)
      }
    })

    standardList.value.sort((a, b) => new Date(a.latestOnlineDate) - new Date(b.latestOnlineDate));

    console.log("urgentList：" + JSON.stringify(urgentList.value))
    console.log("standardList：" + JSON.stringify(standardList.value))


    // 按车型分类统计未排产订单数
    capacity.value.forEach(item => {
      // 从allOrders订单中统计需要的产能
      item.requiredQuantity = getRequiredCapacity(item.capacityType, allOrders);
    })

    console.log("需要的产能为：" + JSON.stringify(requiredCapacity))
    usedCapacity.value.forEach(item => {
      // 从allOrders订单中统计已使用产能
      item.productionQuantity = getUsedCapacity(item.capacityType, allOrders);
    })
    setInitialSelection();
  })
}

let disableSelectionChange = ref(false);

function setInitialSelection() {
  disableSelectionChange.value = true;
  allOrders.value.forEach(item => {
    if (item.isScheduling === 1) {
      urgentList.value.forEach(order => {
        if (item.id === order.id) {
          proxy.$refs.urgentTable.toggleRowSelection(order, true);
        }
      });
      standardList.value.forEach(order => {
        if (item.id === order.id) {
          proxy.$refs.standardTable.toggleRowSelection(order, true);
        }
      });
    }
  });
  disableSelectionChange.value = false;
}

// 多选框选中加急排产数据
function handleUrgentSelectionChange(selection) {
  if (disableSelectionChange.value) return;
  console.log('selection', selection);
  // 检查当前型号是否进行排产
  selection.forEach((item, index) => {
    if (!checkCapacityType(item.capacityType)) {
      // 询问用户是否对当前车型进行排产
      proxy.$confirm('当前车型未进行排产，是否对当前车型进行排产？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 跳转到排产页面
        capacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        usedCapacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        countUsedCapacity()
      }).catch(() => {
        // 取消操作,将当前选中的数据取消选中
        console.log('selection-before', selection);
        selection.splice(index, 1);
        proxy.$refs.urgentTable.toggleRowSelection(item, false);
        console.log('selection-after', selection);

      });
    }
  })
  console.log("下一步操作：统计")
  // 清空
  urgentList.value.forEach(item => {
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 0;
      }
    })
  })

  // 修改allOrders，将选中的订单排产状态改为1
  selection.forEach(item => {
    // 从修改allOrders中修改排产状态
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 1;
      }
    })
  })
  countUsedCapacity()
}

// 多选框选中一般排产数据
function handleStandardSelectionChange(selection) {
  if (disableSelectionChange.value) return;
  console.log('selection', selection);
  // 检查当前型号是否进行排产
  selection.forEach((item, index) => {
    if (!checkCapacityType(item.capacityType)) {
      // 询问用户是否对当前车型进行排产
      proxy.$confirm('当前车型未进行排产，是否对当前车型进行排产？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 跳转到排产页面
        capacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        usedCapacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        countUsedCapacity()
      }).catch(() => {
        // 取消操作,将当前选中的数据取消选中
        console.log('selection-before', selection);
        selection.splice(index, 1);
        proxy.$refs.standardTable.toggleRowSelection(item, false);
        console.log('selection-after', selection);

      });
    }
  })
  console.log("下一步操作：统计")
  // 清空
  standardList.value.forEach(item => {
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 0;
      }
    })
  })

  // 修改allOrders，将选中的订单排产状态改为1
  selection.forEach(item => {
    // 从修改allOrders中修改排产状态
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 1;
      }
    })
  })
  countUsedCapacity()

}

function handleUnscheduledSelectionChange(selection) {
  if (disableSelectionChange.value) return;
  console.log('selection', selection);
  // 检查当前型号是否进行排产
  selection.forEach((item, index) => {
    if (!checkCapacityType(item.capacityType)) {
      // 询问用户是否对当前车型进行排产
      proxy.$confirm('当前车型未进行排产，是否对当前车型进行排产？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 跳转到排产页面
        capacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        usedCapacity.value.push({
          'capacityType': item.capacityType,
          'productionQuantity': 0
        })
        countUsedCapacity()
      }).catch(() => {
        // 取消操作,将当前选中的数据取消选中
        console.log('selection-before', selection);
        selection.splice(index, 1);
        proxy.$refs.unscheduledTable.toggleRowSelection(item, false);
        console.log('selection-after', selection);

      });
    }
  })
  console.log("下一步操作：统计")
  // 清空
  unscheduledList.value.forEach(item => {
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 0;
      }
    })
  })

  // 修改allOrders，将选中的订单排产状态改为1
  selection.forEach(item => {
    // 从修改allOrders中修改排产状态
    allOrders.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 1;
      }
    })
  })
  countUsedCapacity()
}

// 检查当前型号是否在capacity中
function checkCapacityType(capacityType) {
  return capacity.value.find(item => item.capacityType === capacityType);
}

// 统计已使用产能
function countUsedCapacity() {
  console.log("开始统计已使用产能")

  usedCapacity.value.forEach(item => {
    item.productionQuantity = 0;
  })
  usedCapacity.value.forEach(item => {
    // 从allOrders订单中统计已使用产能
    item.productionQuantity = getUsedCapacity(item.capacityType, allOrders);
  })
  console.log("已使用产能列表：" + JSON.stringify(usedCapacity.value))
}

// 根据车型获取已使用产能
function getUsedCapacity(capacityType, list) {
  console.log("当前统计的车型为：" + capacityType)
  const used = list.value.filter(item =>
      item.capacityType === capacityType && item.isScheduling === 1
  ).reduce((sum, item) => sum + item.quantity, 0);
  console.log("车型已使用产能：" + used)
  return used ? used : 0;
}

// 根据车型和allOrders获取需要的产能
function getRequiredCapacity(capacityType, list) {
  console.log("当前统计的车型为：" + capacityType)
  const used = list.value.filter(item =>
      item.capacityType === capacityType
  ).reduce((sum, item) => sum + item.quantity, 0);
  console.log("车型已使用产能：" + used)
  return used ? used : 0;
}

// 根据allOrders中排产状态，更新selection状态
function updateSelectionStatus() {
  console.log("更新selection状态")
  console.log("allOrders", allOrders.value)
  allOrders.value.forEach(item => {
    console.log("item", item)
    if (item.isScheduling === 1) {
      console.log("item.isScheduling", item)

      // 紧急订单
      urgentList.value.forEach(order => {
        if (item.id === order.id) {
          proxy.$refs.urgentTable.toggleRowSelection(order, true);

        }
      })

      // 一般订单
      standardList.value.forEach(order => {
        if (item.id === order.id) {
          proxy.$refs.standardTable.toggleRowSelection(order, true);
        }
      })
    }
  })
}


/**
 *  自动排产，遍历standardList，判断当前车型产能是否用尽，如果还有产能则进行排产，并更新selection状态
 * */
// function autoScheduling() {
//   console.log("自动排产")
//   standardList.value.forEach(item => {
//     // 判断当前车型是否用尽
//     const capacityType = item.capacityType;
//     // 如果capacityType 为null，则跳过剩余操作
//     if (capacityType === null) {
//       return;
//     }
//     let used = usedCapacity.value.find(item => item.capacityType === capacityType)
//     let required = capacity.value.find(item => item.capacityType === capacityType)
//     // console.log("capacityType排产车型" + capacityType + "已使用产能" + used + "需要产能" + required)
//     console.log("capacityType排产车型" + capacityType)
//     console.log("已使用产能" + JSON.stringify(used))
//     console.log("需要产能" + JSON.stringify(required))
//
//     if (used.productionQuantity < required.productionQuantity) {
//       // 进行排产
//       item.isScheduling = 1;
//     }
//     countUsedCapacity()
//     updateSelectionStatus()
//   })
//   console.log("自动排产完成" + JSON.stringify(usedCapacity.value))
// }


// const useTagsStore = useTagsViewStore()
// const currentTag = router.currentRoute.value;
function submitForm() {
  // productionDate
  // 格式化date
  // date转入后端少了一天
  let date = dayjs(productionDate.value).format("YYYY-MM-DD");
  console.log("格式化之后的时间" + date)
  let orderSchedulingList = []
  allOrders.value.forEach(item => {
    // if (item.isScheduling === 1) {
      orderSchedulingList.push({
        'id': item.id,
        'isScheduling': item.isScheduling,
        'quantity': item.quantity,
        'onlineDate': date
      })
    // }
  });
  console.log("排产订单列表" + JSON.stringify(orderSchedulingList))
  // console.log("提交表单" + JSON.stringify(ids))
  // console.log("排产日期" + productionDate.value)
  // console.log("使用的产能为" + JSON.stringify(usedCapacity.value))
  // console.log("使用的产能为" + JSON.stringify(capacity.value))

  let dailyUsedCapacityList = [];
  usedCapacity.value.forEach(used => {
    capacity.value.find(item => {
      if (item.capacityType === used.capacityType) {
        dailyUsedCapacityList.push({
          'capacityType': used.capacityType,
          'productionDate': date,
          'productionQuantity': used.productionQuantity,
          'quantitySettings': item.productionQuantity
        })
      }
    })
  })
  console.log("使用的产能为" + JSON.stringify(dailyUsedCapacityList))

  schedulingOrders(date, orderSchedulingList, dailyUsedCapacityList).then((response) => {
    console.log("排产结果" + response)
    // 提示用户排产成功
    // proxy.$message({
    //   message: "排产成功"
    // })

    // 关闭当前标签页，并返回上一层路由
    useTagsViewStore().delView(router.currentRoute.value);
    proxy.$router.go(-1);
  })


}
</script>

<style scoped>
.capacity-display {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>