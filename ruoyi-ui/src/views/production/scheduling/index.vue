<template>
  <div class="app-container">

    <!-- 第一步:定义出4个步骤  -->
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="第一步" description="设置生产日期和产能"/>
      <el-step title="第二步" description="Some description"/>
      <el-step title="第三步" description="Some description"/>
      <el-step title="第四步" description="Some description"/>
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
          <el-form-item :label="item.vehicleModel">
            <el-input-number v-model="item.productionQuantity" :min="0"></el-input-number>
          </el-form-item>
        </div>
      </el-form>

    </div>

    <div v-show="active === 2">
      <div style="display: flex; margin: 20px; width: 95%;">
        <div style="flex: 1; border: #00afff solid;">
          <!--   渲染产能总数和已使用产能数量   -->
          <el-form label-width="120px">
            <el-col style="margin: 20px">
              <div v-for="(item, index) in usedCapacity" :key="index">
                <el-form-item :label="item.vehicleModel">
                  <el-input v-model="item.productionQuantity" readonly style="width: 150px;"
                            input-style="font-size: large; font-weight: bold;" size="large" placeholder="Please input">
                    <template #append>
                      <span style="width: 30px; font-size: large; font-weight: bold;">
                       {{ getCapacity(item.vehicleModel) }}
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


// 初始化步骤条为1
let active = ref(1);
// 总产能列表
const capacity = ref([]);
// 已使用产能列表
const usedCapacity = ref([]);
// 排产日期

const productionDate = ref('')

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
        'vehicleModel': item.vehicleModel,
        'productionQuantity': item.productionQuantity
      })
      usedCapacity.value.push({
        'vehicleModel': item.vehicleModel,
        'productionQuantity': 0
      })
    })
    console.log("初始化产能" + JSON.stringify(capacity.value))
  })
}

// 使用 onMounted 钩子在组件挂载时调用 getCapacityList
onMounted(() => {
  getCapacityList();
});

function getCapacity(vehicleModel) {
  const used = capacity.value.find(item => item.vehicleModel === vehicleModel);
  return used ? used.productionQuantity : 0;
}

function getUrgentOrder() {
  // 获取加急订单

}

</script>

<style scoped>
.capacity-display {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>