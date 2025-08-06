<template>
  <div class="home-back">
    <div class="tree-container">
      <!-- <h3>设备维修树状图</h3> -->
      <div class="tree-svg">
        <!-- 节点 -->
        <div class="nodes">
          <!-- 根节点 -->
          <div class="node-item root-node">
            <span>
              {{ treeData.deviceNum }}
            </span>
          </div>
          <!-- 分组节点 -->
          <div class="node-item node-item-level-2">
            <div v-for="(group, index) in groupNodes" :key="'group-' + index"
              :style="{ background: group.expanded ? '#1976D2' : '#2196F3' }" class="group-node"
              :class="group.expanded ? 'expanded' : ''" @click="toggleGroup(index)">
              <slot name="level2" :node="group" :index="index"></slot>
            </div>
          </div>
          <!-- 维修记录节点 (只在展开时显示) -->
          <div v-if="visibleMaintenanceNodes.length" class="node-item node-item-last">
            <div v-for="(item, index) in visibleMaintenanceNodes" :key="'maintenance-' + index" class="maintenance-node"
              :style="{ background: 'white', color: 'black' }" @click="showItemDetail(item)">
              <slot name="level3" :node="item" :index="index"></slot>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch, computed, onMounted } from 'vue'

// 定义props
const props = defineProps({
  chartData: {
    type: Object,
    default: () => ({})
  }
})


console.log('chartData---->', props.chartData.data)
// 定义事件
const emit = defineEmits(['node-click'])

// 响应式数据
const treeData = ref({})
const groupNodes = ref([])
const maintenanceNodes = ref([])
const expandedGroupIndex = ref(null)

// 计算属性 - 可见的维修节点
const visibleMaintenanceNodes = computed(() => {
  if (expandedGroupIndex.value === null) {
    return []
  }
  return maintenanceNodes.value.filter(node => node.groupIndex === expandedGroupIndex.value)
})

// 监听props.data变化
watch(
  () => props.chartData.data,
  (newData) => {
    treeData.value = newData
    generateTreeData()
  },
  { deep: true }
)

// 初始化数据
onMounted(() => {
  treeData.value = props.chartData.data
  generateTreeData()
})

// 生成树状数据
function generateTreeData() {
  // 生成分组节点
  const groups = treeData.value.children || []
  const groupCount = Math.min(groups.length, 20)

  groupNodes.value = groups.slice(0, groupCount).map((group, index) => ({
    timeSum: group.timeSum,
    faultType: group.faultType,
    count: group.count,
    children: group.children || [],
    expanded: false // 初始状态为收缩
  }))

  // 生成所有维修记录节点（但不显示）
  generateMaintenanceNodes()
}

// 生成维修记录节点
function generateMaintenanceNodes() {
  maintenanceNodes.value = []
  groupNodes.value.forEach((group, groupIndex) => {
    const maintenanceItems = group.children

    maintenanceItems.forEach((item, itemIndex) => {
      maintenanceNodes.value.push({
        ...item,
        groupIndex
      })
    })
  })
}

// 切换分组展开/收起
function toggleGroup(index) {
  if (expandedGroupIndex.value === index) {
    expandedGroupIndex.value = null
    groupNodes.value[index].expanded = false
  } else {
    if (expandedGroupIndex.value !== null) {
      groupNodes.value[expandedGroupIndex.value].expanded = false
    }
    expandedGroupIndex.value = index
    groupNodes.value[index].expanded = true
  }
}

// 获取节点颜色
function getNodeColor(issueType) {
  switch (issueType) {
    case '电器':
      return '#FF5722'
    case '机械':
      return '#FF9800'
    default:
      return '#9C27B0'
  }
}

// 显示维修详情
function showItemDetail(item) {
  emit('node-click', item, '3')
}
</script>

<style lang="scss" scoped>
.nodes {
  display: flex;

  .node-item {
    padding: 10px;
    padding-left: 40px;
    padding-right: 40px;
    max-height: 700px;
    overflow: auto;
    color: white;
    cursor: pointer;

    &:not(.root-node) {
      border-left: 1px solid #aaaaaa;
    }

    /* 隐藏滚动条 */
    scrollbar-width: none;
    /* Firefox */
    -ms-overflow-style: none;
    /* IE and Edge */

    &::-webkit-scrollbar {
      display: none;
      /* Chrome, Safari, Opera */
    }
  }

  .root-node {
    display: inline-flex;
    justify-content: center;
    align-items: center;
  }

  .root-node span {
    background: #4caf50;
    color: white;
    font-weight: bold;
    text-align: center;
    padding: 4px;
    border-radius: 4px;
  }

  .group-node {
    background: #2196f3;
    color: white;
    font-weight: bold;
    text-align: center;
    font-size: 14px;
    cursor: pointer;
    position: relative;
    padding: 4px;
    border-radius: 4px;
  }

  .maintenance-node+.maintenance-node,
  .group-node+.group-node {
    margin-top: 10px;
  }

  .maintenance-node {
    padding: 6px;
    border: 1px solid #ddd;
    border-radius: 4px;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }

  .maintenance-node,
  .group-node {
    position: relative;

    &::before {
      content: '';
      width: 40px;
      height: 1px;
      background: #aaa;
      position: absolute;
      top: 50%;
      left: -40px;
    }
  }

  .root-node span,
  .group-node.expanded {
    position: relative;

    &::after {
      content: '';
      width: 40px;
      height: 1px;
      background: #aaa;
      position: absolute;
      top: 50%;
      right: -40px;
    }
  }
}

.home-back {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 80vh;
}

.detail-modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.detail-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 500px;
  max-height: 80%;
  overflow-y: auto;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.2);
}

.detail-content h4 {
  margin-top: 0;
  margin-bottom: 15px;
  color: #333;
  border-bottom: 2px solid #4caf50;
  padding-bottom: 10px;
}

.detail-content p {
  margin: 10px 0;
  line-height: 1.5;
}

.detail-content button {
  background: #4caf50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 15px;
}

.detail-content button:hover {
  background: #45a049;
}
</style>