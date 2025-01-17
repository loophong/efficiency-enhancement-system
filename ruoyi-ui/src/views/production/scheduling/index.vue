<template>
  <div class="app-container">

    <!-- 第一步:定义出4个步骤  -->
    <el-steps :active="active" finish-status="success" align-center>
      <el-step title="第一步" description="Some description"/>
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
        111111111
        <!--  ...-->
        <!--        <el-form-item label="设备名称" prop="name">-->
        <!--          <el-input v-model="temp.name" placeholder="请输入"/>-->
        <!--        </el-form-item>-->

      </div>
      <div v-show="active === 2">22222222222</div>
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

// 引入组件库的少量全局样式变量


let active = ref(1);

let temp = reactive({
  name: '',
  code: '',
  type: '',
  status: '',
  remark: '',
  createBy: '',
  createTime: '',
  updateBy: '',
  updateTime: '',
  delFlag: '',
  id: ''
})


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


</script>