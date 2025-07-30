<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <!-- <el-form-item label="序号" prop="majorOrder">
        <el-input v-model="queryParams.majorOrder" placeholder="请输入序号" clearable @keyup.enter="handleQuery" />
      </el-form-item> -->
      <el-form-item label="设备名称" prop="majorName">
        <el-input v-model="queryParams.majorName" placeholder="请输入设备名称" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="部位" prop="majorPosition">
        <el-input v-model="queryParams.majorPosition" placeholder="请输入部位" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="保养维修项目" prop="majorProject">
        <el-input v-model="queryParams.majorProject" placeholder="请输入保养维修项目" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="周期" prop="majorCycleNum">
        <el-input v-model="queryParams.majorCycleNum" placeholder="请输入周期" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="人员" prop="majorPeople">
        <el-input v-model="queryParams.majorPeople" placeholder="请输入人员" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="筛选" prop="majorContent">
        <el-cascader v-model="cascaderValue" :options="cascaderOptions" :props="cascaderProps" clearable />
        <!-- <el-input v-model="queryParams.majorContent" placeholder="请输入内容" clearable @keyup.enter="handleQuery" /> -->
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd"
          v-hasPermi="['maintenanceTable:plan:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <!--Excel 参数导入 -->
        <el-button type="primary" icon="UploadFilled" @click="showDialog = true" plain>导入
        </el-button>
        <el-dialog title="导入专业计划Excel文件" v-model="showDialog" width="30%">
          <el-form :model="form" ref="formRef" label-width="90px">
          </el-form>
          <span>上传表：</span>
          <span style="color:darkgreen">专业保全计划</span>
          <br />
          <br />
          <div class="upload-area">
            <i class="el-icon-upload"></i>
            <input type="file" id="inputFile" ref="fileInput" @change="checkFile" />
          </div>
          <br />
          <span class="dialog-footer">
            <el-button @click="showDialog = false">取 消</el-button>
            <el-button type="primary" @click="fileSend" v-if="buttonLoading === false">确 定</el-button>
            <el-button type="primary" :loading="true" v-else>上传中</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="downloadTemplate()">下载导入模板</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['maintenanceTable:plan:export']">导出</el-button>
      </el-col> -->

      <el-col :span="1.5">
        <el-button type="primary" plain icon="View" v-if="showFullYear == false" @click="showFullYear = true"
          v-hasPermi="['maintenanceTable:plan:add']">显示全年数据</el-button>
        <el-button type="primary" plain icon="Hide" v-else @click="showFullYear = false"
          v-hasPermi="['maintenanceTable:plan:add']">显示当月及上月数据</el-button>
      </el-col>

      <el-col :span="1.5">

      </el-col>
      <el-badge :value="listForTip.length" class="item" color="green">
        <el-button @click="openMessage = true">消息详情</el-button>
      </el-badge>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="主键id" align="center" prop="majorId" /> -->
      <!-- <el-table-column label="序号" align="center" prop="majorOrder" /> -->
      <el-table-column label="设备名称" align="center" prop="majorName" width="160" />
      <el-table-column label="部位" align="center" prop="majorPosition" />
      <el-table-column label="保养维修项目" align="center" prop="majorProject" width="280" />
      <el-table-column label="周期" align="center" prop="majorCycleNum" />
      <el-table-column label="人员" align="center" prop="majorPeople" />
      <el-table-column label="创建年份" align="center" prop="createTime">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="月份" align="center" prop="majorMonth" /> -->
      <el-table-column label="一月1W" align="center" prop="weekJanOne" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJanOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJanOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="一月2W" align="center" prop="weekJanTwo" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJanTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJanTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="一月3W" align="center" prop="weekJanThree" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJanThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJanThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="一月4W" align="center" prop="weekJanFour" v-if="showFullYear || ifCurrentMonth(1)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJanFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJanFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="二月1W" align="center" prop="weekFebOne" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekFebOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekFebOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="二月2W" align="center" prop="weekFebTwo" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekFebTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekFebTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="二月3W" align="center" prop="weekFebThree" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekFebThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekFebThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="二月4W" align="center" prop="weekFebFour" v-if="showFullYear || ifCurrentMonth(2)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekFebFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekFebFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="三月1W" align="center" prop="weekMarOne" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMarOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMarOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="三月2W" align="center" prop="weekMarTwo" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMarTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMarTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="三月3W" align="center" prop="weekMarThree" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMarThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMarThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="三月4W" align="center" prop="weekMarFour" v-if="showFullYear || ifCurrentMonth(3)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMarFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMarFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="四月1W" align="center" prop="weekAprOne" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAprOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAprOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="四月2W" align="center" prop="weekAprTwo" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAprTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAprTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="四月3W" align="center" prop="weekAprThree" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAprThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAprThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="四月4W" align="center" prop="weekAprFour" v-if="showFullYear || ifCurrentMonth(4)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAprFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAprFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="五月1W" align="center" prop="weekMayOne" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMayOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMayOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="五月2W" align="center" prop="weekMayTwo" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMayTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMayTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="五月3W" align="center" prop="weekMayThree" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMayThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMayThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="五月4W" align="center" prop="weekMayFour" v-if="showFullYear || ifCurrentMonth(5)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekMayFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekMayFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="六月1W" align="center" prop="weekJunOne" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJunOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJunOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="六月2W" align="center" prop="weekJunTwo" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJunTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJunTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="六月3W" align="center" prop="weekJunThree" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJunThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJunThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="六月4W" align="center" prop="weekJunFour" v-if="showFullYear || ifCurrentMonth(6)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJunFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJunFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="七月1W" align="center" prop="weekJulOne" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJulOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJulOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="七月2W" align="center" prop="weekJulTwo" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJulTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJulTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="七月3W" align="center" prop="weekJulThree" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJulThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJulThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="七月4W" align="center" prop="weekJulFour" v-if="showFullYear || ifCurrentMonth(7)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekJulFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekJulFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="八月1W" align="center" prop="weekAugOne" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAugOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAugOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="八月2W" align="center" prop="weekAugTwo" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAugTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAugTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="八月3W" align="center" prop="weekAugThree" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAugThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAugThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="八月4W" align="center" prop="weekAugFour" v-if="showFullYear || ifCurrentMonth(8)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekAugFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekAugFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="九月1W" align="center" prop="weekSepOne" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekSepOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekSepOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="九月2W" align="center" prop="weekSepTwo" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekSepTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekSepTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="九月3W" align="center" prop="weekSepThree" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekSepThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekSepThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="九月4W" align="center" prop="weekSepFour" v-if="showFullYear || ifCurrentMonth(9)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekSepFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekSepFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十月1W" align="center" prop="weekOctOne" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekOctOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekOctOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十月2W" align="center" prop="weekOctTwo" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekOctTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekOctTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十月3W" align="center" prop="weekOctThree" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekOctThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekOctThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十月4W" align="center" prop="weekOctFour" v-if="showFullYear || ifCurrentMonth(10)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekOctFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekOctFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十一月1W" align="center" prop="weekNovOne" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekNovOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekNovOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十一月2W" align="center" prop="weekNovTwo" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekNovTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekNovTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十一月3W" align="center" prop="weekNovThree" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekNovThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekNovThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十一月4W" align="center" prop="weekNovFour" v-if="showFullYear || ifCurrentMonth(11)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekNovFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekNovFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十二月1W" align="center" prop="weekDecOne" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekDecOne')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecOne).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekDecOne).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十二月2W" align="center" prop="weekDecTwo" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekDecTwo')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecTwo).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekDecTwo).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十二月3W" align="center" prop="weekDecThree" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekDecThree')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecThree).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekDecThree).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="十二月4W" align="center" prop="weekDecFour" v-if="showFullYear || ifCurrentMonth(12)">
        <template #default="scope">
          <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
            @click="handleCellClick(scope.row, 'weekDecFour')">
            <span>
              <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecFour).symbol
              }}</span>
              <span>{{ parseStatus(scope.row.weekDecFour).description }}</span>
            </span>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['maintenanceTable:plan:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['maintenanceTable:plan:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改专业计划保养对话框 -->
    <el-dialog :title="title" v-model="open" width="600px" append-to-body>
      <el-form ref="planRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="序号" prop="majorOrder">
          <el-input v-model="form.majorOrder" placeholder="请输入序号" />
        </el-form-item>
        <el-form-item label="设备名称" prop="majorName">
          <el-input v-model="form.majorName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="部位" prop="majorPosition">
          <el-input v-model="form.majorPosition" placeholder="请输入部位" />
        </el-form-item>
        <el-form-item label="保养维修项目" prop="majorProject">
          <el-input v-model="form.majorProject" placeholder="请输入保养维修项目" />
        </el-form-item>
        <el-form-item label="周期" prop="majorCycleNum">
          <el-input v-model="form.majorCycleNum" placeholder="请输入周期" />
        </el-form-item>
        <el-form-item label="人员" prop="majorPeople">
          <el-input v-model="form.majorPeople" placeholder="请输入人员" />
        </el-form-item>


      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog title="消息提醒" v-model="openMessage" width="1600px" append-to-body>
      <el-button type="primary" @click="watchCurrentWeek" v-show="!showCurrent">仅查看本周</el-button>
      <el-button type="primary" @click="watchAllWeek" v-show="showCurrent">查看所有</el-button>
      <el-table v-loading="loading" :data="listForTip" @selection-change="handleSelectionChange" border>
        <el-table-column label="设备名称" align="center" prop="majorName" width="160" />
        <el-table-column label="部位" align="center" prop="majorPosition" />
        <el-table-column label="保养维修项目" align="center" prop="majorProject" width="280" />
        <el-table-column label="周期" align="center" prop="majorCycleNum" />
        <el-table-column label="人员" align="center" prop="majorPeople" />
        <el-table-column label="一月1W" align="center" prop="weekJanOne" v-if="ifCurrentMonth(1)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJanOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJanOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="一月2W" align="center" prop="weekJanTwo" v-if="ifCurrentMonth(1)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJanTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJanTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="一月3W" align="center" prop="weekJanThree" v-if="ifCurrentMonth(1)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJanThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJanThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="一月4W" align="center" prop="weekJanFour" v-if="ifCurrentMonth(1)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJanFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJanFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJanFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="二月1W" align="center" prop="weekFebOne" v-if="ifCurrentMonth(2)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekFebOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekFebOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="二月2W" align="center" prop="weekFebTwo" v-if="ifCurrentMonth(2)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekFebTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekFebTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="二月3W" align="center" prop="weekFebThree" v-if="ifCurrentMonth(2)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekFebThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekFebThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="二月4W" align="center" prop="weekFebFour" v-if="ifCurrentMonth(2)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekFebFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekFebFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekFebFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="三月1W" align="center" prop="weekMarOne" v-if="ifCurrentMonth(3)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMarOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMarOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="三月2W" align="center" prop="weekMarTwo" v-if="ifCurrentMonth(3)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMarTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMarTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="三月3W" align="center" prop="weekMarThree" v-if="ifCurrentMonth(3)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMarThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMarThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="三月4W" align="center" prop="weekMarFour" v-if="ifCurrentMonth(3)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMarFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMarFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMarFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="四月1W" align="center" prop="weekAprOne" v-if="ifCurrentMonth(4)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAprOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAprOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="四月2W" align="center" prop="weekAprTwo" v-if="ifCurrentMonth(4)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAprTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAprTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="四月3W" align="center" prop="weekAprThree" v-if="ifCurrentMonth(4)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAprThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAprThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="四月4W" align="center" prop="weekAprFour" v-if="ifCurrentMonth(4)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAprFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAprFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAprFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="五月1W" align="center" prop="weekMayOne" v-if="ifCurrentMonth(5)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMayOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMayOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="五月2W" align="center" prop="weekMayTwo" v-if="ifCurrentMonth(5)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMayTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMayTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="五月3W" align="center" prop="weekMayThree" v-if="ifCurrentMonth(5)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMayThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMayThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="五月4W" align="center" prop="weekMayFour" v-if="ifCurrentMonth(5)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekMayFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekMayFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekMayFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="六月1W" align="center" prop="weekJunOne" v-if="ifCurrentMonth(6)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJunOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJunOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="六月2W" align="center" prop="weekJunTwo" v-if="ifCurrentMonth(6)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJunTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJunTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="六月3W" align="center" prop="weekJunThree" v-if="ifCurrentMonth(6)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJunThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJunThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="六月4W" align="center" prop="weekJunFour" v-if="ifCurrentMonth(6)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJunFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJunFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJunFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="七月1W" align="center" prop="weekJulOne" v-if="ifCurrentMonth(7)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJulOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJulOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="七月2W" align="center" prop="weekJulTwo" v-if="ifCurrentMonth(7)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJulTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJulTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="七月3W" align="center" prop="weekJulThree" v-if="ifCurrentMonth(7)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJulThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJulThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="七月4W" align="center" prop="weekJulFour" v-if="ifCurrentMonth(7)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekJulFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekJulFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekJulFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="八月1W" align="center" prop="weekAugOne" v-if="ifCurrentMonth(8)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAugOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAugOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="八月2W" align="center" prop="weekAugTwo" v-if="ifCurrentMonth(8)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAugTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAugTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="八月3W" align="center" prop="weekAugThree" v-if="ifCurrentMonth(8)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAugThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAugThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="八月4W" align="center" prop="weekAugFour" v-if="ifCurrentMonth(8)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekAugFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekAugFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekAugFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="九月1W" align="center" prop="weekSepOne" v-if="ifCurrentMonth(9)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekSepOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekSepOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="九月2W" align="center" prop="weekSepTwo" v-if="ifCurrentMonth(9)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekSepTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekSepTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="九月3W" align="center" prop="weekSepThree" v-if="ifCurrentMonth(9)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekSepThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekSepThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="九月4W" align="center" prop="weekSepFour" v-if="ifCurrentMonth(9)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekSepFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekSepFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekSepFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十月1W" align="center" prop="weekOctOne" v-if="ifCurrentMonth(10)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekOctOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekOctOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十月2W" align="center" prop="weekOctTwo" v-if="ifCurrentMonth(10)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekOctTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekOctTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十月3W" align="center" prop="weekOctThree" v-if="ifCurrentMonth(10)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekOctThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekOctThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十月4W" align="center" prop="weekOctFour" v-if="ifCurrentMonth(10)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekOctFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekOctFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekOctFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十一月1W" align="center" prop="weekNovOne" v-if="ifCurrentMonth(11)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekNovOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekNovOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十一月2W" align="center" prop="weekNovTwo" v-if="ifCurrentMonth(11)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekNovTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekNovTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十一月3W" align="center" prop="weekNovThree" v-if="ifCurrentMonth(11)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekNovThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekNovThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十一月4W" align="center" prop="weekNovFour" v-if="ifCurrentMonth(11)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekNovFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekNovFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekNovFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十二月1W" align="center" prop="weekDecOne" v-if="ifCurrentMonth(12)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekDecOne')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecOne).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekDecOne).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十二月2W" align="center" prop="weekDecTwo" v-if="ifCurrentMonth(12)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekDecTwo')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecTwo).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekDecTwo).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十二月3W" align="center" prop="weekDecThree" v-if="ifCurrentMonth(12)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekDecThree')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecThree).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekDecThree).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="十二月4W" align="center" prop="weekDecFour" v-if="ifCurrentMonth(12)">
          <template #default="scope">
            <div style="display: flex; justify-content: center; align-items: center; height: 100%; cursor: pointer;"
              @click="handleCellClick(scope.row, 'weekDecFour')">
              <span>
                <span :style="{ fontSize: '36px', fontWeight: 'bold' }">{{ parseStatus(scope.row.weekDecFour).symbol
                }}</span>
                <span>{{ parseStatus(scope.row.weekDecFour).description }}</span>
              </span>
            </div>
          </template>
        </el-table-column>

      </el-table>
    </el-dialog>
    <record ref="recordRef" :rowFromProps="rowForProps" :showDialog="showDialogNull" :majorGroup="majorGroup"
      v-if="showRecord" @getMajor="getList"></record>
  </div>
</template>

<script setup name="majorPlan">
import { listPlan, getPlan, delPlan, addPlan, updatePlan, uploadFile, tipList } from "@/api/device/maintenanceTable/majorPlan";
import { listTemplate } from "@/api/device/fileTable/template";
import { ElMessage } from 'element-plus'
import record from "./record.vue"
import { format } from 'date-fns';
import { ElNotification } from 'element-plus'
import { getInfo } from "@/api/login";

const { proxy } = getCurrentInstance();

const planList = ref([]);
const listForTip = ref([]);
const open = ref(false);
const openMessage = ref(false);
const loading = ref(true);
const buttonLoading = ref(false);
const showSearch = ref(true);
const showFullYear = ref(false);
const showRecord = ref(false);
const showDialogNull = ref(false);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const showDialog = ref(false);
const showCurrent = ref(false);
const total = ref(0);
const notifyOffset = ref(0);
const title = ref("");
const tmpListForTip = ref([]);
const rowForProps = reactive({})
const majorGroup = ref('专业')
const currentUserName = ref("");
const currentUserId = ref(0);
const ifTip = ref(true);
const needShow = reactive({});
const baseUrl = import.meta.env.VITE_APP_BASE_API;
//级联选择器
const cascaderValue = ref([])
const cascaderProps = {
  expandTrigger: 'hover',
};
const monthLabels = [
  '一月', '二月', '三月', '四月', '五月', '六月',
  '七月', '八月', '九月', '十月', '十一月', '十二月'
];

const monthAbbreviations = [
  'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
  'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
];
const cascaderOptions = monthLabels.map((label, index) => ({
  value: monthAbbreviations[index],
  label: label,
  children: [
    {
      value: 'One', label: '1W', children: [
        { value: '◎', label: '◎(检查保养项目)' },
        { value: '☆', label: '☆(精度调整计划)' },
        { value: '◆', label: '◆(检修计划)' },
        { value: '◎(待审核)', label: '◎(待审核)' },
        { value: '◆(待审核)', label: '◆(待审核)' },
        { value: '☆(待审核)', label: '☆(待审核)' },
        { value: '待审核', label: '所有(待审核)' },
      ]
    },
    {
      value: 'Two', label: '2W', children: [
        { value: '◎', label: '◎(检查保养项目)' },
        { value: '☆', label: '☆(精度调整计划)' },
        { value: '◆', label: '◆(检修计划)' },
        { value: '◎(待审核)', label: '◎(待审核)' },
        { value: '◆(待审核)', label: '◆(待审核)' },
        { value: '☆(待审核)', label: '☆(待审核)' },
        { value: '待审核', label: '所有(待审核)' },
      ]
    },
    {
      value: 'Three', label: '3W', children: [
        { value: '◎', label: '◎(检查保养项目)' },
        { value: '☆', label: '☆(精度调整计划)' },
        { value: '◆', label: '◆(检修计划)' },
        { value: '◎(待审核)', label: '◎(待审核)' },
        { value: '◆(待审核)', label: '◆(待审核)' },
        { value: '☆(待审核)', label: '☆(待审核)' },
        { value: '待审核', label: '所有(待审核)' },

      ]
    },
    {
      value: 'Four', label: '4W', children: [
        { value: '◎', label: '◎(检查保养项目)' },
        { value: '☆', label: '☆(精度调整计划)' },
        { value: '◆', label: '◆(检修计划)' },
        { value: '◎(待审核)', label: '◎(待审核)' },
        { value: '◆(待审核)', label: '◆(待审核)' },
        { value: '☆(待审核)', label: '☆(待审核)' },
        { value: '待审核', label: '所有(待审核)' },
      ]
    }
  ]
}));



const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    majorOrder: null,
    majorName: null,
    majorPosition: null,
    majorProject: null,
    majorCycleNum: null,
    majorPeople: null,
    majorContent: null,
    majorGroup: null
  },
  rules: {
    majorName: [
      { required: true, message: "设备名称不能为空", trigger: "blur" }
    ],
    majorPosition: [
      { required: true, message: "部位不能为空", trigger: "blur" }
    ],
    majorProject: [
      { required: true, message: "保养维修项目不能为空", trigger: "blur" }
    ],
    majorCycleNum: [
      { required: true, message: "周期不能为空", trigger: "blur" }
    ],
    majorPeople: [
      { required: true, message: "人员不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);


function showNotification(keyword) {
  // 根据传递进来的参数计算新的偏移量
  const newOffset = notifyOffset.value + 100;
  // 显示通知
  ElNotification({
    title: '专业计划',
    dangerouslyUseHTMLString: true,
    message: `<span>您有</span><span style="color: #2d7ad6;">[${keyword}]</span><span>审核中，请及时处理</span>`,
    type: "warning",
    offset: newOffset,
    duration: 7000,
  })
  // 更新偏移量，避免堆叠
  notifyOffset.value = newOffset;
}
//显示当月及上月
function ifCurrentMonth(num) {
  const now = new Date();
  const currentMonth = now.getMonth() + 1; // 加1以转换为实际的月份
  return currentMonth === num || currentMonth - 1 === num;
}

/** 查询专业计划保养列表 */
function getList() {
  loading.value = true;
  if (Array.isArray(cascaderValue.value) && cascaderValue.value.length == 3) {
    // console.log(`week${cascaderValue.value[0]}${cascaderValue.value[1]}${cascaderValue.value[2]}`)
    queryParams.value[`week${cascaderValue.value[0]}${cascaderValue.value[1]}`] = cascaderValue.value[2]
  }

  listPlan(queryParams.value).then(response => {
    planList.value = response.rows;
    total.value = response.total;
    if (Array.isArray(cascaderValue.value) && cascaderValue.value.length == 3) {
      delete queryParams.value[`week${cascaderValue.value[0]}${cascaderValue.value[1]}`]
    }
    // console.log('needShow----->', needShow)
    // console.log('majorList------->', listForTip.value)
    loading.value = false;
  });
  if (ifTip.value) {
    tipList().then(result => {
      listForTip.value = result.rows

      const tmp = JSON.parse(JSON.stringify(result.rows))
      const fields = [];
      ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'].forEach(month => {
        ['One', 'Two', 'Three', 'Four'].forEach(week => {
          fields.push(`week${month}${week}`);
        });
      });
      tmp.forEach(element => {
        fields.forEach(field => {
          if (element.createBy == currentUserId.value && element[field] != '' && element[field] != null && element[field].includes('待审核')) {
            console.log(element)
            needShow[field] = true
            showNotification(field);
          }
        });
      });
    })
    ifTip.value = false
  }
}

//获取当前用户信息
getInfo().then(result => {
  currentUserId.value = result.user.userId
  currentUserName.value = result.user.userName
})
//判断为是否是本周
function isThisWeek(dateString) {
  const checkDate = new Date(dateString);
  checkDate.setHours(0, 0, 0, 0); // 标准化为目标日期的00:00:00

  const now = new Date();
  const currentStart = getStartOfWeek(now);
  const nextWeekStart = new Date(currentStart);
  nextWeekStart.setDate(currentStart.getDate() + 7); // 下周一 00:00:00

  return checkDate >= currentStart && checkDate < nextWeekStart;
}

function watchCurrentWeek() {
  showCurrent.value = true
  tmpListForTip.value = listForTip.value
  listForTip.value = listForTip.value.filter(item => {
    return isThisWeek(item.updateTime)
  })
}

function watchAllWeek() {
  showCurrent.value = false
  if (Array.isArray(tmpListForTip.value) && (tmpListForTip.value.length != 0)) {
    listForTip.value = tmpListForTip.value
  }
}
function getStartOfWeek(date) {
  const d = new Date(date);
  d.setHours(0, 0, 0, 0); // 清除时间部分
  const day = d.getDay(); // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
  const diff = (day === 0 ? -6 : 1 - day); // 调整为周一
  d.setDate(d.getDate() + diff);
  return d;
}
// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    majorId: null,
    majorOrder: null,
    majorName: null,
    majorPosition: null,
    majorProject: null,
    majorCycleNum: null,
    majorPeople: null,
    majorMonth: null,
    majorMonthWeek: null,
    majorContent: null
  };
  proxy.resetForm("planRef");
}


function handleCellClick(row, cell) {
  console.log('id:', row.groupId);
  const content = row[cell];
  console.log('内容：', content);
  row.monthTime = cell
  rowForProps.data = row
  showDialogNull.value = !showDialogNull.value
  showRecord.value = true
}

/** 搜索按钮操作 */
function handleQuery() {
  // console.log(cascaderValue.value)
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
  ids.value = selection.map(item => item.majorId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加专业计划保养";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const _majorId = row.majorId || ids.value
  getPlan(_majorId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改专业计划保养";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["planRef"].validate(valid => {
    if (valid) {
      if (form.value.majorId != null) {
        updatePlan(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.createBy = currentUserId.value
        addPlan(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

function parseStatus(input) {
  const trimmedInput = input ? input.trim() : '';

  const match = trimmedInput.match(/^([^\(\)])(?:[^(]*\((.*?)\))?/);
  if (match) {
    const symbol = match[1]; // 提取符号
    const description = match[2] ? `(${match[2]})` : '';
    return { symbol: symbol, description: description };
  } else {
    if (!trimmedInput || trimmedInput.length === 0) {
      return { symbol: '\u200B', description: '' };
    } else {
      return { symbol: trimmedInput, description: '' };
    }
  }
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _majorIds = row.majorId || ids.value;
  proxy.$modal.confirm('是否确认删除专业计划保养编号为"' + _majorIds + '"的数据项？').then(function () {
    return delPlan(_majorIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => { });
}

// 导入excel，检查文件类型
function checkFile() {
  const file = proxy.$refs["fileInput"].files[0];
  const fileName = file.name;
  const fileExt = fileName.split(".").pop(); // 获取文件的扩展名

  if (fileExt.toLowerCase() !== "xlsx" && fileExt.toLowerCase() !== "xlsm" && fileExt.toLowerCase() !== "xls") {
    this.$message.error("只能上传 Excel 文件！");
    // this.$refs.fileInput.value = ""; // 清空文件选择框
  }
}
//导入excel，取消按钮绑定取消所选的xlsx
// function resetFileInput() {
//   this.$refs.fileInput.value = "";
// }
/** 导入按钮 */
function fileSend() {
  const fileInput = proxy.$refs["fileInput"];
  // console.log(proxy.$refs["fileInput"])
  // if (!fileInput || !fileInput.files.length) {
  //   ElMessage.warning('请选择要上传的文件');
  //   return;
  // }
  buttonLoading.value = true
  const file = fileInput.files[0];
  console.log('Selected file:', file);
  // console.log(file)
  const formData = new FormData();
  formData.append("excelFile", file);
  formData.append("yearAndMonth", '2024-12-12');
  console.log({ formData })
  // 使用如下方法打印出 formData 的内容
  for (let pair of formData.entries()) {
    console.log(pair[0] + ': ' + pair[1]);
  }

  // isLoading.value = true;
  uploadFile(formData, `/fault/majorPlan/import`)
    .then(data => {
      // 处理上传成功的情况
      ElMessage({
        message: '上传成功',
        type: 'success',
      });
      getList();
      buttonLoading.value = false
      showDialog.value = false;
      // this.isLoading = false;
    })
    .catch(error => {
      // 处理上传失败的情况
      ElMessage({
        message: `上传失败:${error}`,
        type: 'error',
      });
      buttonLoading.value = false
      showDialog.value = false;
    });
}


/** 导出按钮操作 */
function handleExport() {
  proxy.download('maintenanceTable/plan/export', {
    ...queryParams.value
  }, `plan_${new Date().getTime()}.xlsx`)
}
function downloadTemplate() {
  listTemplate().then(r => {
    console.log(`${baseUrl}${r.rows[0].templateMajor}`)
    window.open(`${baseUrl}${r.rows[0].templateMajor}`)
  })
}
getList();
</script>
