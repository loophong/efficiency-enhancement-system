<template>
    <div class="app-container">
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="操作视图" name="operation">
          <el-button type="primary" @click="handleAddRoot">添加根节点</el-button>
          <div style="margin-top: 15px">
            <el-tree
              ref="menuTree"
              :data="menuTreeData"
              :props="defaultProps"
              node-key="menuId"
              highlight-current
              @node-click="handleNodeClick"
            >
              <template #default="{ node, data }">
                <span class="custom-tree-node">
                  <span>
                    <i :class="data.icon" style="margin-right: 5px"></i>
                    {{ node.label }}
                  </span>
                  <span>
                    <el-button
                      type="text"
                      size="small"
                      @click.stop="() => handleAddChild(data)"
                    >添加子节点</el-button>
                    <el-button
                      type="text"
                      size="small"
                      @click.stop="() => handleEdit(data)"
                    >编辑</el-button>
                    <el-button
                      type="text"
                      size="small"
                      @click.stop="() => handleDelete(data)"
                    >删除</el-button>
                  </span>
                </span>
              </template>
            </el-tree>
          </div>
        </el-tab-pane>
        
        <el-tab-pane label="可视化视图" name="visualization">
          <div ref="echartContainer" style="width: 100%; height: 700px;"></div>
        </el-tab-pane>
      </el-tabs>
  
      <!-- 新增/编辑弹窗 -->
      <el-dialog :title="formTitle" v-model="dialogVisible" width="600px">
        <!-- 原有表单内容 -->
      </el-dialog>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  import { getSecuritySysMenuTree, addSecuritySysMenu, updateSecuritySysMenu, delSecuritySysMenu } from '@/api/security/sysmenu';
  
  export default {
    name: 'SecuritySysMenuTree',
    data() {
      return {
        activeTab: 'operation',
        menuTreeData: [],
        defaultProps: {
          children: 'children',
          label: 'menuName'
        },
        dialogVisible: false,
        formTitle: '',
        form: {
          // 原有表单数据
        },
        rules: {
          // 原有验证规则
        },
        echartInstance: null,
        currentEchartNode: null
      };
    },
    mounted() {
      this.initEChart();
      this.getTree();
    },
    methods: {
      // 初始化ECharts
      initEChart() {
        this.echartInstance = echarts.init(this.$refs.echartContainer);
        window.addEventListener('resize', this.resizeEChart);
        
        // 监听ECharts点击事件
        this.echartInstance.on('click', params => {
          if (params.data) {
            this.handleEChartNodeClick(params.data);
          }
        });
      },
      
      // 处理ECharts节点点击
      handleEChartNodeClick(nodeData) {
        this.currentEchartNode = nodeData;
        
        // 执行跳转（如果是菜单类型且有路径）
        if (nodeData.menuType === 'C' && nodeData.path) {
          if (nodeData.isFrame) {
            window.open(nodeData.path);
          } else {
            this.$router.push(nodeData.path);
          }
        }
        
        // 高亮显示当前节点
        this.echartInstance.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: nodeData.index
        });
      },
      
      // 构建ECharts数据
      convertToEChartData(data) {
        let index = 0;
        const processNode = (node, parentId = null) => {
          const echartNode = {
            id: node.menuId,
            name: node.menuName,
            value: node.menuId,
            menuType: node.menuType,
            path: node.path,
            isFrame: node.isFrame,
            index: index++,
            itemStyle: {
              color: this.getNodeColor(node.menuType)
            },
            symbolSize: node.menuType === 'M' ? 30 : 20,
            label: {
              show: true,
              position: node.children && node.children.length ? 'left' : 'right',
              fontSize: 12
            }
          };
          
          if (parentId !== null) {
            echartNode.parentId = parentId;
          }
          
          if (node.children && node.children.length) {
            echartNode.children = node.children.map(child => processNode(child, node.menuId));
            echartNode.collapsed = false; // 默认展开
          }
          
          return echartNode;
        };
        
        return data.map(root => processNode(root));
      },
      
      // 获取节点颜色
      getNodeColor(menuType) {
        switch(menuType) {
          case 'M': return '#5470c6'; // 目录 - 蓝色
          case 'C': return '#91cc75'; // 菜单 - 绿色
          case 'F': return '#ee6666'; // 按钮 - 红色
          default: return '#73c0de';  // 默认 - 浅蓝
        }
      },
      
      // 渲染ECharts图表
      renderEChart(data) {
        const option = {
          tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove',
            formatter: params => {
              return `${params.data.name}<br/>类型: ${this.getTypeName(params.data.menuType)}`;
            }
          },
          series: [
            {
              type: 'tree',
              data: data,
              top: '5%',
              left: '15%',
              bottom: '5%',
              right: '15%',
              symbol: 'roundRect',
              symbolSize: [60, 30],
              orient: 'vertical',
              expandAndCollapse: true,
              initialTreeDepth: 3,
              label: {
                position: 'top',
                verticalAlign: 'middle',
                align: 'center',
                fontSize: 12
              },
              leaves: {
                label: {
                  position: 'right',
                  verticalAlign: 'middle',
                  align: 'left'
                }
              },
              emphasis: {
                focus: 'descendant',
                itemStyle: {
                  shadowBlur: 10,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              animationDuration: 550,
              animationDurationUpdate: 750
            }
          ]
        };
        
        this.echartInstance.setOption(option);
      },
      
      // 获取类型名称
      getTypeName(menuType) {
        switch(menuType) {
          case 'M': return '目录';
          case 'C': return '菜单';
          case 'F': return '按钮';
          default: return '未知';
        }
      },
      
      // 获取菜单数据
      getTree() {
        getSecuritySysMenuTree().then(response => {
          const treeData = this.buildTree(response.data, 0);
          this.menuTreeData = treeData;
          
          // 转换为ECharts数据并渲染
          const echartData = this.convertToEChartData(treeData);
          this.renderEChart(echartData);
        });
      },
      
      // 构建树形结构
      buildTree(data, parentId) {
        const tree = [];
        data.forEach(item => {
          if (item.parentId === parentId) {
            const children = this.buildTree(data, item.menuId);
            if (children.length > 0) {
              item.children = children;
            }
            tree.push(item);
          }
        });
        return tree;
      },
      
      // 添加根节点
      handleAddRoot() {
        this.resetForm();
        this.formTitle = '添加根菜单';
        this.form.parentId = 0;
        this.form.parentName = '无';
        this.dialogVisible = true;
      },
      
      // 添加子节点
      handleAddChild(data) {
        this.resetForm();
        this.formTitle = '添加子菜单';
        this.form.parentId = data.menuId;
        this.form.parentName = data.menuName;
        this.dialogVisible = true;
      },
      
      // 编辑节点
      handleEdit(data) {
        // 原有编辑逻辑
      },
      
      // 删除节点
      handleDelete(data) {
        this.$confirm('确认删除该菜单吗?', '提示', {
          type: 'warning'
        }).then(() => {
          delSecuritySysMenu(data.menuId).then(() => {
            this.$message.success('删除成功');
            this.getTree(); // 刷新数据
          });
        });
      },
      
      // 提交表单
      submitForm() {
        this.$refs.menuForm.validate(valid => {
          if (valid) {
            const api = this.form.menuId ? updateSecuritySysMenu : addSecuritySysMenu;
            api(this.form).then(() => {
              this.$message.success('操作成功');
              this.dialogVisible = false;
              this.getTree(); // 刷新数据
            });
          }
        });
      },
      
      // 重置表单
      resetForm() {
        this.form = {
          menuId: undefined,
          parentId: 0,
          parentName: '无',
          menuType: 'M',
          menuName: '',
          orderNum: 0,
          path: '',
          component: '',
          isFrame: false,
          visible: true,
          status: true,
          perms: '',
          icon: '',
          remark: ''
        };
      },
      
      // 调整图表大小
      resizeEChart() {
        this.echartInstance && this.echartInstance.resize();
      }
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.resizeEChart);
      this.echartInstance && this.echartInstance.dispose();
    }
  };
  </script>
  
  <style scoped>
  .app-container {
    padding: 20px;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
  </style>