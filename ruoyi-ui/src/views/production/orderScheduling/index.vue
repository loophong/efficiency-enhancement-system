<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="网点名称" prop="branch">
        <el-input v-model="queryParams.branch" placeholder="请输入网点名称" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="合同号" prop="contractNumber">
        <el-input v-model="queryParams.contractNumber" placeholder="请输入合同号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="接单日期" prop="orderDate">
        <el-date-picker clearable v-model="queryParams.orderDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择接单日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="车型" prop="vehicleModel">
        <el-input v-model="queryParams.vehicleModel" placeholder="请输入车型" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <!--      <el-form-item label="数量" prop="quantity">-->
      <!--        <el-input v-model="queryParams.quantity" placeholder="请输入数量" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="阀片" prop="valvePlate">-->
      <!--        <el-input v-model="queryParams.valvePlate" placeholder="请输入阀片" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="货叉" prop="forklift">-->
      <!--        <el-input v-model="queryParams.forklift" placeholder="请输入货叉" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="门架" prop="mast">-->
      <!--        <el-input v-model="queryParams.mast" placeholder="请输入门架" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="空滤" prop="airFilter">-->
      <!--        <el-input v-model="queryParams.airFilter" placeholder="请输入空滤" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="属具" prop="attachments">-->
      <!--        <el-input v-model="queryParams.attachments" placeholder="请输入属具" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="轮胎" prop="tires">-->
      <!--        <el-input v-model="queryParams.tires" placeholder="请输入轮胎" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="配置" prop="configuration">-->
      <!--        <el-input v-model="queryParams.configuration" placeholder="请输入配置" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <el-form-item label="车号" prop="vehicleNumber">
        <el-input v-model="queryParams.vehicleNumber" placeholder="请输入车号" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <el-form-item label="备注信息" prop="remarks">
        <el-input v-model="queryParams.remarks" placeholder="请输入备注信息" clearable @keyup.enter="handleQuery"/>
      </el-form-item>
      <!--      <el-form-item label="交货形式" prop="deliveryForm">-->
      <!--        <el-input v-model="queryParams.deliveryForm" placeholder="请输入交货形式" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="交货地点" prop="deliveryLocation">-->
      <!--        <el-input v-model="queryParams.deliveryLocation" placeholder="请输入交货地点" clearable-->
      <!--                  @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="联系人姓名" prop="contactPerson">-->
      <!--        <el-input v-model="queryParams.contactPerson" placeholder="请输入联系人姓名" clearable-->
      <!--                  @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="联系电话" prop="phoneNumber">-->
      <!--        <el-input v-model="queryParams.phoneNumber" placeholder="请输入联系电话" clearable @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <el-form-item label="订单系统交货期" label-width="120px" prop="systemDeliveryDate">
        <el-date-picker clearable v-model="queryParams.systemDeliveryDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择订单系统交货期">
        </el-date-picker>
      </el-form-item>
      <!--      <el-form-item label="生产回复完工日期" prop="productionCompletionDate">-->
      <!--        <el-date-picker clearable v-model="queryParams.productionCompletionDate" type="date" value-format="YYYY-MM-DD"-->
      <!--                        placeholder="请选择生产回复完工日期">-->
      <!--        </el-date-picker>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="采购回复到货时间" prop="procurementArrivalDate">-->
      <!--        <el-date-picker clearable v-model="queryParams.procurementArrivalDate" type="date" value-format="YYYY-MM-DD"-->
      <!--                        placeholder="请选择采购回复到货时间">-->
      <!--        </el-date-picker>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="生产周期" prop="productionCycle">-->
      <!--        <el-input v-model="queryParams.productionCycle" placeholder="请输入生产周期" clearable-->
      <!--                  @keyup.enter="handleQuery"/>-->
      <!--      </el-form-item>-->
      <el-form-item label="最晚上线日期" label-width="100px" prop="latestOnlineDate">
        <el-date-picker clearable v-model="queryParams.latestOnlineDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择最晚上线日期">
        </el-date-picker>
      </el-form-item>
<!--      <el-form-item label="是否超期" prop="isOverdue">-->
<!--        <el-input v-model="queryParams.isOverdue" placeholder="请输入是否超期" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否加急" prop="isUrgent">-->
<!--        <el-input v-model="queryParams.isUrgent" placeholder="请输入是否加急" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否排产" prop="isScheduling">-->
<!--        <el-input v-model="queryParams.isScheduling" placeholder="请输入是否排产" clearable @keyup.enter="handleQuery"/>-->
<!--      </el-form-item>-->

      <el-form-item label="是否超期" prop="isOverdue">
        <el-select v-model="queryParams.isOverdue" placeholder="请选择是否超期" clearable style="width: 150px">
          <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否加急" prop="isUrgent">
        <el-select v-model="queryParams.isUrgent" placeholder="请选择是否加急" clearable style="width: 150px">
          <el-option v-for="dict in production_yes_no" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否排产" prop="isScheduling">
        <el-select v-model="queryParams.isScheduling" placeholder="请选择是否排产" clearable style="width: 150px">
          <el-option v-for="dict in production_is_scheduling" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="上线时间" prop="onlineDate">
        <el-date-picker clearable v-model="queryParams.onlineDate" type="date" value-format="YYYY-MM-DD"
                        placeholder="请选择排产时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['production:scheduling:add']">新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
                   v-hasPermi="['production:scheduling:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
                   v-hasPermi="['production:scheduling:remove']">删除
        </el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button @click="handleImport" type="success" plain icon="Upload"
                   v-hasPermi="['production:mainPlanTable:import']">导入
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="schedulingList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <!--      <el-table-column label="主键" align="center" prop="id"/>-->
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
      <el-table-column label="车号" align="center" prop="vehicleNumber"/>
      <el-table-column label="备注信息" align="center" prop="remarks" width="150"/>
      <el-table-column label="订单系统交货期" align="center" prop="systemDeliveryDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.systemDeliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="生产回复完工日期" align="center" prop="productionCompletionDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.productionCompletionDate, '{y}-{m}-{d}') }}</span>
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
<!--      <el-table-column label="是否超期" align="center" prop="isOverdue"/>-->
<!--      <el-table-column label="是否加急" align="center" prop="isUrgent">-->
<!--        <template #default="scope">-->
<!--          <el-tag v-if="scope.row.isUrgent === 0" type="info">否</el-tag>-->
<!--          <el-tag v-else-if="scope.row.isUrgent === 1" type="danger">是</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="是否超期" align="center" prop="isOverdue">
        <template #default="scope">
          <dict-tag :options="production_yes_no" :value="scope.row.isOverdue"/>
        </template>
      </el-table-column>
      <el-table-column label="是否加急" align="center" prop="isUrgent">
        <template #default="scope">
          <dict-tag :options="production_yes_no" :value="scope.row.isUrgent"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="是否排产" align="center" prop="isScheduling">-->
<!--        <template #default="scope">-->
<!--          <el-tag v-if="scope.row.isScheduling === 0" type="info">未排产</el-tag>-->
<!--          <el-tag v-else-if="scope.row.isScheduling === 1" type="success">已排产</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="是否排产" align="center" prop="isScheduling">
        <template #default="scope">
          <dict-tag :options="production_is_scheduling" :value="scope.row.isScheduling"/>
        </template>
      </el-table-column>
      <el-table-column label="上线日期" align="center" prop="latestOnlineDate" width="100">
        <template #default="scope">
          <span>{{ parseTime(scope.row.onlineDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['production:scheduling:edit']">修改
          </el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['production:scheduling:remove']">删除
          </el-button>
          <!--     如果订单未排产，则显示插单按钮     -->
          <el-button link type="primary" icon="Edit" @click="handleInsert(scope.row)"
                     v-if="scope.row.isScheduling === 0"
                     v-hasPermi="['production:scheduling:insert']">插单
          </el-button>
          <!--    如果已经排产，则显示特殊情况上报按钮    -->
          <el-button link type="primary" icon="Upload" @click="handleSpecialReport(scope.row)"
                     v-if="scope.row.isScheduling === 1"
                     v-hasPermi="['production:scheduling:report']">特殊情况上报
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="schedulingRef" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="网点名称" prop="branch">
          <el-input v-model="form.branch" placeholder="请输入网点名称"/>
        </el-form-item>
        <el-form-item label="合同号" prop="contractNumber">
          <el-input v-model="form.contractNumber" placeholder="请输入合同号"/>
        </el-form-item>
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="form.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="接单日期" prop="orderDate">
          <el-date-picker clearable v-model="form.orderDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择接单日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="车型" prop="vehicleModel">
          <el-input v-model="form.vehicleModel" placeholder="请输入车型"/>
        </el-form-item>
<!--        <el-form-item label="数量" prop="quantity">-->
<!--          <el-input v-model="form.quantity" placeholder="请输入数量"/>-->
<!--        </el-form-item>-->
        <el-form-item label="数量" prop="quantity">
          <el-input-number v-model="form.quantity" placeholder="请输入数量" :min="0" :step="1" />
        </el-form-item>

        <el-form-item label="阀片" prop="valvePlate">
          <el-input v-model="form.valvePlate" placeholder="请输入阀片"/>
        </el-form-item>
        <el-form-item label="货叉" prop="forklift">
          <el-input v-model="form.forklift" placeholder="请输入货叉"/>
        </el-form-item>
        <el-form-item label="门架" prop="mast">
          <el-input v-model="form.mast" placeholder="请输入门架"/>
        </el-form-item>
        <el-form-item label="空滤" prop="airFilter">
          <el-input v-model="form.airFilter" placeholder="请输入空滤"/>
        </el-form-item>
        <el-form-item label="属具" prop="attachments">
          <el-input v-model="form.attachments" placeholder="请输入属具"/>
        </el-form-item>
        <el-form-item label="轮胎" prop="tires">
          <el-input v-model="form.tires" placeholder="请输入轮胎"/>
        </el-form-item>
        <el-form-item label="配置" prop="configuration">
          <el-input v-model="form.configuration" placeholder="请输入配置"/>
        </el-form-item>
        <el-form-item label="车号" prop="vehicleNumber">
          <el-input v-model="form.vehicleNumber" placeholder="请输入车号"/>
        </el-form-item>
        <el-form-item label="备注信息" prop="remarks">
          <el-input v-model="form.remarks" placeholder="请输入备注信息"/>
        </el-form-item>
        <el-form-item label="交货形式" prop="deliveryForm">
          <el-input v-model="form.deliveryForm" placeholder="请输入交货形式"/>
        </el-form-item>
        <el-form-item label="交货地点" prop="deliveryLocation">
          <el-input v-model="form.deliveryLocation" placeholder="请输入交货地点"/>
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactPerson">
          <el-input v-model="form.contactPerson" placeholder="请输入联系人姓名"/>
        </el-form-item>
        <el-form-item label="联系电话" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入联系电话"/>
        </el-form-item>
        <el-form-item label="订单系统交货期" prop="systemDeliveryDate">
          <el-date-picker clearable v-model="form.systemDeliveryDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择订单系统交货期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="生产回复完工日期" prop="productionCompletionDate">
          <el-date-picker clearable v-model="form.productionCompletionDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择生产回复完工日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="采购回复到货时间" prop="procurementArrivalDate">
          <el-date-picker clearable v-model="form.procurementArrivalDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择采购回复到货时间">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="生产周期" prop="productionCycle">-->
<!--          <el-input v-model="form.productionCycle" placeholder="请输入生产周期"/>-->
<!--        </el-form-item>-->
        <el-form-item label="生产周期" prop="productionCycle">
          <el-input-number v-model="form.productionCycle" placeholder="请输入生产周期" :min="0" :step="1" />
        </el-form-item>
        <el-form-item label="最晚上线日期" prop="latestOnlineDate">
          <el-date-picker clearable v-model="form.latestOnlineDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择最晚上线日期">
          </el-date-picker>
        </el-form-item>
<!--        <el-form-item label="是否超期" prop="isOverdue">-->
<!--          <el-input v-model="form.isOverdue" placeholder="请输入是否超期"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否加急" prop="isUrgent">-->
<!--          <el-input v-model="form.isUrgent" placeholder="请输入是否加急"/>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否排产" prop="isScheduling">-->
<!--          <el-input v-model="form.isScheduling" placeholder="请输入是否排产"/>-->
<!--        </el-form-item>-->
        <el-form-item label="是否超期" prop="isOverdue">
          <el-select v-model="form.isOverdue" placeholder="请选择是否超期">
            <el-option
                v-for="dict in production_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否加急" prop="isUrgent">
          <el-select v-model="form.isUrgent" placeholder="请选择是否加急">
            <el-option
                v-for="dict in production_yes_no"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否排产" prop="isScheduling">
          <el-select v-model="form.isScheduling" placeholder="请选择是否排产">
            <el-option
                v-for="dict in production_is_scheduling"
                :key="dict.value"
                :label="dict.label"
                :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 特殊情况上报对话框 -->
    <el-dialog title="特殊情况填报" v-model="specialVisible" width="500px" append-to-body>
      <el-form ref="specialRef" :model="specialForm" :rules="specialRules" label-width="80px">
        <el-form-item label="订单号" prop="orderNumber">
          <el-input v-model="specialForm.orderNumber" placeholder="请输入订单号"/>
        </el-form-item>
        <el-form-item label="上线日期" prop="onlineDate">
          <el-date-picker clearable v-model="specialForm.onlineDate" type="date"
                          value-format="YYYY-MM-DD" placeholder="请选择上线日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="故障原因" prop="faultReason">
          <el-select v-model="specialForm.faultReason" placeholder="请选择故障原因">
            <el-option v-for="dict in production_fault_reason" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="责任科室" prop="responsibleDepartment">
          <el-select v-model="specialForm.responsibleDepartment" placeholder="请选择责任科室">
            <el-option v-for="dict in system_dept" :key="dict.value" :label="dict.label"
                       :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="故障说明" prop="faultDescription">
          <el-input v-model="specialForm.faultDescription" placeholder="请输入故障说明" type="textarea"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitSpecial">确 定</el-button>
          <el-button @click="cancelSpecial">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 插单处理对话框 -->
    <el-dialog title="插单处理窗口" v-model="insertDialogVisible" width="95%" append-to-body>

      <el-date-picker v-model="insertOrderDate" type="date" value-format="YYYY-MM-DD"
                      placeholder="请选择插单日期"></el-date-picker>
      <el-button type="primary" @click="getOrdersAndCapacityByDate">查 询</el-button>

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
                       {{ item.quantitySettings }}
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
          <el-table ref="scheduledTable" :data="scheduledList" height="500px" width="auto"
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

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitInsetOrder">确 定</el-button>
          <el-button @click="cancelInsertOrder">取 消</el-button>
        </div>
      </template>
    </el-dialog>


    <!-- 文件上传弹窗 -->
    <el-dialog title="导入主计划表" v-model="uploadDialogVisible" width="35%" @close="resetUpload">

      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item label="上传表类：">
          <span style="color: rgb(68, 140, 39);">主计划表</span>
          <br>
        </el-form-item>
        <el-form-item label="上传日期：">
          <!--    时间选择器      -->
          <el-date-picker clearable v-model="uploadDate" type="date" value-format="YYYY-MM-DD"
                          placeholder="请选择上传日期">
          </el-date-picker>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <input type="file" ref="inputFile" @change="checkFile"/>
          <br>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="cancelUpload">取 消</el-button>
        <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
        <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
      </span>
    </el-dialog>


  </div>
</template>

<script setup name="Scheduling">
import {
  listScheduling,
  getScheduling,
  delScheduling,
  addScheduling,
  updateScheduling,
  getOrdersAndCapacityInfoByDate, schedulingOrders
} from "@/api/production/orderScheduling.js";


import {addCases} from "@/api/production/special";
import dayjs from "dayjs";
import useTagsViewStore from "@/store/modules/tagsView.js";
import {importFile} from "@/api/production/mainPlanTable.js";
// import {getCapacity} from "@/api/production/capacity";
// import {handleStandardSelectionChange} from "../scheduling/index.vue";
const {proxy} = getCurrentInstance();
const { production_is_scheduling, production_yes_no } = proxy.useDict('production_is_scheduling', 'production_yes_no');

const {
  production_fault_reason,
  system_dept
} = proxy.useDict('production_fault_reason', 'system_dept');

const schedulingList = ref([]);
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
    branch: null,
    contractNumber: null,
    orderNumber: null,
    orderDate: null,
    vehicleModel: null,
    quantity: null,
    valvePlate: null,
    forklift: null,
    mast: null,
    airFilter: null,
    attachments: null,
    tires: null,
    configuration: null,
    vehicleNumber: null,
    remarks: null,
    deliveryForm: null,
    deliveryLocation: null,
    contactPerson: null,
    phoneNumber: null,
    systemDeliveryDate: null,
    productionCompletionDate: null,
    procurementArrivalDate: null,
    productionCycle: null,
    capacityType: null,
    latestOnlineDate: null,
    isOverdue: null,
    isUrgent: null,
    isScheduling: null,
    onlineDate: null
  },
  rules: {
    branch: [
      { required: true, message: "网点名称不能为空", trigger: "blur" }
    ],
    contractNumber: [
      { required: true, message: "合同号不能为空", trigger: "blur" }
    ],
    orderNumber: [
      { required: true, message: "订单号不能为空", trigger: "blur" }
    ],
    orderDate: [
      { required: true, message: "接单日期不能为空", trigger: "blur" }
    ],
    vehicleModel: [
      { required: true, message: "车型不能为空", trigger: "blur" }
    ],
    quantity: [
      { required: true, message: "数量不能为空", trigger: "blur" }
    ],
    valvePlate: [
      { required: true, message: "阀片不能为空", trigger: "blur" }
    ],
    forklift: [
      { required: true, message: "货叉不能为空", trigger: "blur" }
    ],
    mast: [
      { required: true, message: "门架不能为空", trigger: "blur" }
    ],
    airFilter: [
      { required: true, message: "空滤不能为空", trigger: "blur" }
    ],
    attachments: [
      { required: true, message: "属具不能为空", trigger: "blur" }
    ],
    tires: [
      { required: true, message: "轮胎不能为空", trigger: "blur" }
    ],
    configuration: [
      { required: true, message: "配置不能为空", trigger: "blur" }
    ],
    vehicleNumber: [
      { required: true, message: "车号不能为空", trigger: "blur" }
    ],
    remarks: [
      { required: true, message: "备注信息不能为空", trigger: "blur" }
    ],
    deliveryForm: [
      { required: true, message: "交货形式不能为空", trigger: "blur" }
    ],
    deliveryLocation: [
      { required: true, message: "交货地点不能为空", trigger: "blur" }
    ],
    contactPerson: [
      { required: true, message: "联系人姓名不能为空", trigger: "blur" }
    ],
    phoneNumber: [
      { required: true, message: "联系电话不能为空", trigger: "blur" }
    ],
    systemDeliveryDate: [
      { required: true, message: "订单系统交货期不能为空", trigger: "blur" }
    ],
    productionCompletionDate: [
      { required: true, message: "生产回复完工日期不能为空", trigger: "blur" }
    ],
    procurementArrivalDate: [
      { required: true, message: "采购回复到货时间不能为空", trigger: "blur" }
    ],
    productionCycle: [
      { required: true, message: "生产周期不能为空", trigger: "blur" }
    ],
    capacityType: [
      { required: true, message: "产能型号不能为空", trigger: "change" }
    ],
    latestOnlineDate: [
      { required: true, message: "最晚上线日期不能为空", trigger: "blur" }
    ],
    isOverdue: [
      { required: true, message: "是否超期不能为空", trigger: "change" }
    ],
    isUrgent: [
      { required: true, message: "是否加急不能为空", trigger: "change" }
    ],
    isScheduling: [
      { required: true, message: "是否排产不能为空", trigger: "change" }
    ]
  },

  specialRules: {
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
  }
});

const {queryParams, form, rules, specialRules} = toRefs(data);

/** 查询订单信息列表 */
function getList() {
  loading.value = true;
  listScheduling(queryParams.value).then(response => {
    schedulingList.value = response.rows;
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
    branch: null,
    contractNumber: null,
    orderNumber: null,
    orderDate: null,
    vehicleModel: null,
    quantity: null,
    valvePlate: null,
    forklift: null,
    mast: null,
    airFilter: null,
    attachments: null,
    tires: null,
    configuration: null,
    vehicleNumber: null,
    remarks: null,
    deliveryForm: null,
    deliveryLocation: null,
    contactPerson: null,
    phoneNumber: null,
    systemDeliveryDate: null,
    productionCompletionDate: null,
    procurementArrivalDate: null,
    productionCycle: null,
    capacityType: null,
    latestOnlineDate: null,
    isOverdue: null,
    isUrgent: null,
    isScheduling: null,
    onlineDate: null
  };
  proxy.resetForm("schedulingRef");
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
  title.value = "添加订单信息";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _id = row.id || ids.value
  getScheduling(_id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改订单信息";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["schedulingRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateScheduling(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addScheduling(form.value).then(response => {
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
  proxy.$modal.confirm('是否确认删除订单信息编号为"' + _ids + '"的数据项？').then(function () {
    return delScheduling(_ids);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('production/scheduling/export', {
    ...queryParams.value
  }, `scheduling_${new Date().getTime()}.xlsx`)
}

getList();


const specialVisible = ref(false);
const specialForm = ref({});

function handleSpecialReport(row) {
  console.log(row);
  resetSpecialForm();
  specialVisible.value = true;
  specialForm.value.orderNumber = row.orderNumber;
  specialForm.value.onlineDate = row.onlineDate;
}

// 取消按钮
function cancelSpecial() {
  specialVisible.value = false;
  resetSpecialForm();
}

function resetSpecialForm() {
  specialForm.value = {
    orderNumber: null,
    onlineDate: null,
    faultReason: null,
    faultDescription: null,
    responsibleDepartment: null,
  }
}

/** 提交按钮 */
function submitSpecial() {
  proxy.$refs["specialRef"].validate(valid => {
    if (valid) {
      specialForm.value.approvalStatus = 0;
      addCases(specialForm.value).then(response => {
        proxy.$modal.msgSuccess("特殊情况上报成功");
        specialVisible.value = false;
        getList();
      });
    }
  })
}


const insertDialogVisible = ref(false);
const schedulingOrderVisible = ref(false);
const insertOrderDate = ref("");

/**
 *  插单处理
 *  1、打开对话框
 *  2、用户选择插单时间
 *  3、从后台获取当日产能信息 和 当日排产信息
 */
const insertDialogRow = ref({});

function handleInsert(row) {
  insertDialogVisible.value = true;
  insertDialogRow.value = row;
}

// 已使用产能列表
const usedCapacity = ref([]);
// 未排产订单列表
const scheduledList = ref([]);

function getOrdersAndCapacityByDate() {
  if (insertOrderDate.value === null || insertOrderDate.value === "") {
    proxy.$modal.msgError("请选择插单日期");
  } else {
    console.log("插单日期：" + insertOrderDate.value);
    getOrdersAndCapacityInfoByDate(insertOrderDate.value).then(response => {
      console.log("查询结果为" + response);
      usedCapacity.value = response.data.dailyUsedCapacityEntities;
      scheduledList.value = response.data.orderSchedulingEntities;
      submitDate.value = insertOrderDate.value;

      scheduledList.value.unshift(insertDialogRow.value);
      console.log("待排产订单：" + JSON.stringify(insertDialogRow.value));

      // 将 scheduledList 的selection全部勾选上
      nextTick(() => {
        scheduledList.value.forEach(item => {
          proxy.$refs.scheduledTable.toggleRowSelection(item, true);

        });
        handleStandardSelectionChange(selection)
      });
    })
  }
}

const submitDate = ref(dayjs().format("YYYY-MM-DD"));

function submitInsetOrder() {

  // insertOrderDate.value();
  // usedCapacity.value
  // scheduledList.value
  let date = dayjs(insertOrderDate.value).format("YYYY-MM-DD");
  console.log("格式化之后的时间" + date)
  let orderSchedulingList = []
  scheduledList.value.forEach(item => {
    orderSchedulingList.push({
      'id': item.id,
      'isScheduling': item.isScheduling,
      'onlineDate': item.isScheduling === 1 ? date : ''
    })
  });

  schedulingOrders(submitDate.value, orderSchedulingList, usedCapacity.value).then((response) => {
    console.log("排产结果" + response)
    // 提示用户排产成功
    // proxy.$message({
    //   message: "排产成功"
    // })
    cancelInsertOrder()
    getList()
  })
}


function cancelInsertOrder() {
  insertDialogVisible.value = false;
  // 清空
  usedCapacity.value = [];
  scheduledList.value = [];
  insertOrderDate.value = "";
  getList()
}

/**
 * 下面三个方法是插单统计，不要修改
 *
 */
// 多选框选中一般排产数据
function handleStandardSelectionChange(selection) {
  console.log('selection', selection);
  console.log("统计")
  // 清空
  scheduledList.value.forEach(order => {
    order.isScheduling = 0;
  })

  // 修改allOrders，将选中的订单排产状态改为1
  selection.forEach(item => {
    // 从修改allOrders中修改排产状态
    scheduledList.value.forEach(order => {
      if (item.id === order.id) {
        order.isScheduling = 1;
      }
    })
  })
  countUsedCapacity()
}

// 统计已使用产能
function countUsedCapacity() {
  console.log("开始统计已使用产能")

  usedCapacity.value.forEach(item => {
    item.productionQuantity = 0;
  })
  usedCapacity.value.forEach(item => {
    // 从allOrders订单中统计已使用产能
    item.productionQuantity = getUsedCapacity(item.capacityType, scheduledList);
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


// 上传文件
const uploadDialogVisible = ref(false);
const isLoading = ref(false);
const inputFile = ref(null);
const uploadDate = ref('');


/** 导入按钮操作 */
function handleImport() {
  resetUpload();
  uploadDialogVisible.value = true;
}

/** 表单重置 */
function resetUpload() {
  if (inputFile.value) {
    inputFile.value.value = "";
  }
}

/** 取消上传 */
function cancelUpload() {
  uploadDialogVisible.value = false;
  resetUpload();
}

/** excel文件上传 */
function uploadFile() {
  if (inputFile.value && inputFile.value.files.length > 0) {
    isLoading.value = true;
    const file = inputFile.value.files[0];
    console.log(inputFile.value);
    console.log("时间：" + uploadDate.value);
    console.log(file);
    const formData = new FormData();
    formData.append('date', uploadDate.value);
    formData.append('excelFile', file);
    importFile(formData).then((response) => {
      console.log("导入结果：" + response);

      if (response.length === 0) {
        proxy.$modal.msgSuccess("导入成功");
        getList();
        uploadDialogVisible.value = false;
        isLoading.value = false;
      } else {
// 使用对话框提示用户，并且点击确认才可关闭
        proxy.$modal.confirm('导入失败，车型的生产周期不存在，请填写后重新导入：' + JSON.stringify(response.data), '导入失败', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'error',
          callback: action => {
            // if (action === 'confirm') {
            //   // 跳转进入生产周期index页面
            //   proxy.$router.push({ path: '/production/cycle' });
            // }
            // uploadDialogVisible.value = false;
            // isLoading.value = false;
          }
        }).then(() => {
          // 跳转进入生产周期index页面
          uploadDialogVisible.value = false;
          isLoading.value = false;
          proxy.$router.push({path: '/production/cycle'});
        }).catch(() => {
          uploadDialogVisible.value = false;
          isLoading.value = false;
        });
      }

    }).catch(() => {
      proxy.$modal.msgError("导入失败");
      isLoading.value = false;
    }).finally(() => {
      resetUpload();
    });
  } else {
    proxy.$modal.msgError("请选择文件");
  }
}

/** 检查文件是否为excel */
function checkFile() {
  const file = inputFile.value.files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    proxy.$modal.msgError("只能上传 Excel 文件！");
    resetUpload();
  }
}


</script>
