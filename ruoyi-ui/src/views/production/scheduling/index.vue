<template>
  <div class="app-container">

    <!-- 第一步:定义出4个步骤  -->
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="第一步" description="设置生产日期和产能"/>
      <el-step title="第二步" description="加急订单排产"/>
      <el-step title="第三步" description="系统排产"/>
      <el-step title="第四步" description="订单预测"/>
      <el-step title="第五步" description="生成日生产计划"/>
    </el-steps>


    <!--    第二步:定义form表单-->
    <!--    <el-form-->
    <!--        ref="dataForm"-->
    <!--        :model="temp"-->
    <!--        :rules="formRules"-->
    <!--        label-position="left"-->
    <!--        label-width="100px"-->
    <!--        style="width: 400px;-->
    <!--    margin-left: 50px">-->

    <!-- 第三步:定义4个盒子对象active => 1 到 4-->
    <div v-show="active === 1">
      <!-- 第四步:放置表单项-->
      <!--    根据后台传入的各个型号产能，动态生成计数器个数    -->
      <!---->
      <!--      <el-form-item label="排产日期">-->
      <!--        <el-date-picker v-model="productionDate" type="date" placeholder="选择日期" />-->
      <!--      </el-form-item>-->


      <el-form :model="capacity" label-width="120px">

        <el-form-item label="排产日期">
          <el-date-picker v-model="productionDate" type="date" placeholder="选择日期"/>
        </el-form-item>

        <div v-for="(item, index) in capacity" :key="index">
          <el-form-item :label="item.capacityType">
            <el-input-number v-model="item.productionQuantity" :min="0"></el-input-number>
          </el-form-item>
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

        <div style="flex: 4; border: #2d2d2d solid;">
          <!--   展示加急订单   -->
          <el-table ref="urgentTable" v-loading="loading" :data="urgentList" width="auto"
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
            <!--            <el-table-column label="生产回复完工日期" align="center" prop="productionCompletionDate" width="100">-->
            <!--              <template #default="scope">-->
            <!--                <span>{{ parseTime(scope.row.productionCompletionDate, '{y}-{m}-{d}') }}</span>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
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
            <!--            <el-table-column label="是否加急" align="center" prop="isUrgent">-->
            <!--              <template #default="scope">-->
            <!--                <el-tag v-if="scope.row.isUrgent === 0" type="info">否</el-tag>-->
            <!--                <el-tag v-else-if="scope.row.isUrgent === 1" type="danger">是</el-tag>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
            <!--            <el-table-column label="是否排产" align="center" prop="isScheduling">-->
            <!--              <template #default="scope">-->
            <!--                <el-tag v-if="scope.row.isScheduling === 0" type="info">未排产</el-tag>-->
            <!--                <el-tag v-else-if="scope.row.isScheduling === 1" type="success">已排产</el-tag>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
            <!--            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
            <!--              <template #default="scope">-->
            <!--                <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"-->
            <!--                           v-hasPermi="['production:scheduling:edit']">修改-->
            <!--                </el-button>-->
            <!--                <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"-->
            <!--                           v-hasPermi="['production:scheduling:remove']">删除-->
            <!--                </el-button>-->
            <!--              </template>-->
            <!--            </el-table-column>-->
          </el-table>
        </div>
      </div>


    </div>


    <div v-show="active === 3">333333333333</div>
    <div v-show="active === 4">444444444</div>

    <!--    </el-form>-->

    <!--    第五步:设置上一步和下一步的按钮-->
    <div style="  display: flex;  justify-content: center;">
      <el-button v-if="active > 1" type="primary" size="large" @click="pre">上一步</el-button>
      <el-button v-if="active < 4" type="primary" size="large" @click="next">下一步</el-button>
    </div>


  </div>
</template>

<script setup name="Scheduling">
import {ref, reactive} from "vue";
import {all} from "@/api/production/capacity.js";
import {getUrgentOrder} from "@/api/production/orderScheduling.js";
import {getCurrentInstance} from 'vue';

const {proxy} = getCurrentInstance();

// 初始化步骤条为1
let active = ref(1);
// 总产能列表
const capacity = ref([]);
// 已使用产能列表
const usedCapacity = ref([]);
// 排产日期
const productionDate = ref('')
// 加急排产列表
const urgentList = ref([]);

// 步骤条下一步的方法
function next() {
  if (active.value < 4) {
    active.value++
  }
  console.log("当前active：" + active.value)
}

// 步骤条上一步的方法
function pre() {
  if (active.value > 1) {
    active.value--
  }
  console.log("当前active：" + active.value)
}

/** 初始化时，获取产能列表 */
function getCapacityList() {
  all().then(response => {
    // 从response.data中解析出车型 和 产量
    response.data.forEach(item => {
      capacity.value.push({
        'capacityType': item.capacityType,
        'productionQuantity': item.productionQuantity
      })
      usedCapacity.value.push({
        'capacityType': item.capacityType,
        'productionQuantity': 0
      })
    })
    console.log("初始化产能" + JSON.stringify(capacity.value))
  })
}

// 使用 onMounted 钩子在组件挂载时调用 getCapacityList
onMounted(() => {
  getCapacityList();
  getUrgentOrderList();
});

function getCapacity(capacityType) {
  const used = capacity.value.find(item => item.capacityType === capacityType);
  return used ? used.productionQuantity : 0;
}

function getUrgentOrderList() {
  // 获取加急订单
  getUrgentOrder().then(response => {
    console.log("加急订单：" + JSON.stringify(response.data))
    urgentList.value = response.data;
  })
}

const schedulingUrgentList = ref([]);

// 多选框选中加急排产数据
function handleUrgentSelectionChange(selection) {
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
        schedulingUrgentList.value = selection;
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
  schedulingUrgentList.value = selection;
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
    // 从加急订单中统计已使用产能
    let urgentNum = getUsedCapacity(item.capacityType, schedulingUrgentList);
    // 从临期订单中统计已使用产能
    // let urgentNum = getUsedCapacity(item.capacityType, schedulingUrgentList);
    // 从正常订单中统计已使用产能
    // let urgentNum = getUsedCapacity(item.capacityType, schedulingUrgentList);
    item.productionQuantity = urgentNum;
  })
  console.log("已使用产能：" + JSON.stringify(usedCapacity.value))
}

// 根据车型获取已使用产能
function getUsedCapacity(capacityType, list) {
  console.log("根据车型获取已使用产能：" + capacityType)
  console.log("车型list：" + JSON.stringify(list))
  const used = list.value.find(item => item.capacityType === capacityType);
  return used ? used.quantity : 0;
}

</script>

<style scoped>
.capacity-display {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>