<template>
    <div class="app-container">
      <el-tabs v-model="activeTab" type="card">
        <el-tab-pane label="操作视图" name="operation">
          <el-button type="primary" @click="handleAddRoot" v-hasPermi="['security:menu:add']">添加根节点   </el-button>
          <div style="margin-top: 20px">
            <el-tree
              ref="menuTree"
              :data="menuTreeData"
              :props="defaultProps"
              node-key="menuId"
              highlight-current
              :indent="30"
              :expand-on-click-node="false"
              @node-click="handleNodeClick"
          
            >
              <template #default="{ node, data }">
                <div class="custom-tree-node">
                  <div class="node-content">
                    <i :class="data.icon" class="node-icon"></i>
                    <span class="node-label">{{ node.label }}</span>
                  </div>
                  <div class="node-actions">
                    <el-button
                      type="primary"
                      link
                      size="small"
                      @click.stop="() => handleAddChild(data)"
                      v-hasPermi="['security:menu:add']"
                    >
                      <el-icon><Plus /></el-icon>
                      添加子节点
                    </el-button>
                    <el-button
                      type="warning"
                      link
                      size="small"
                      @click.stop="() => handleEdit(data)"
                      v-hasPermi="['security:menu:edit']"
                    >
                      <el-icon><Edit /></el-icon>
                      编辑
                    </el-button>
                    <el-button
                      type="danger"
                      link
                      size="small"
                      @click.stop="() => handleDelete(data)"
                      v-hasPermi="['security:menu:remove']"
                    >
                      <el-icon><Delete /></el-icon>
                      删除
                    </el-button>
                  </div>
                </div>
              </template>
            </el-tree>
          </div>
        </el-tab-pane>
      
        <el-tab-pane label="可视化视图" name="visualization">
          <div ref="echartContainer" style="width: 1250px; height: 4000px;"></div>
        </el-tab-pane>
      </el-tabs>
  
      <!-- 新增/编辑弹窗 -->
      <el-dialog 
        :title="formTitle" 
        v-model="dialogVisible" 
        width="600px"
        @closed="resetForm"
      >
        <el-form
          ref="menuForm"
          :model="form"
          :rules="rules"
          label-width="100px"
        >
          <el-form-item label="父级菜单" prop="parentName">
            <el-input v-model="form.parentName" disabled />
          </el-form-item>
          
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="form.menuType">
              <el-radio label="M">目录</el-radio>
              <el-radio label="C">菜单</el-radio>
              <!-- <el-radio label="F">按钮</el-radio> -->
            </el-radio-group>
          </el-form-item>
          
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
          
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" :min="0" />
          </el-form-item>
          
          <el-form-item v-if="form.menuType == 'C'" label="路由地址" prop="path">
            <el-input v-model="form.path" placeholder="请输入路由地址" />
          </el-form-item>
          
          <!-- <el-form-item v-if="form.menuType === 'C'" label="组件路径" prop="component">
            <el-input v-model="form.component" placeholder="请输入组件路径" />
          </el-form-item> -->
          
          <!-- <el-form-item v-if="form.menuType !== 'F'" label="是否外链" prop="isFrame">
            <el-radio-group v-model="form.isFrame">
              <el-radio :label="false">否</el-radio>
              <el-radio :label="true">是</el-radio>
            </el-radio-group>
          </el-form-item> -->
          
          <!-- <el-form-item label="菜单状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio :label="true">显示</el-radio>
              <el-radio :label="false">隐藏</el-radio>
            </el-radio-group>
          </el-form-item> -->
          
          <!-- <el-form-item label="权限标识" prop="perms">
            <el-input v-model="form.perms" placeholder="请输入权限标识" />
          </el-form-item> -->
          
          <!-- <el-form-item label="菜单图标" prop="icon">
            <el-popover
              placement="bottom-start"
              width="450"
              trigger="click"
            >
              <template #reference>
                <el-input
                  v-model="form.icon"
                  placeholder="点击选择图标"
                  readonly
                >
                  <template #prefix>
                    <svg-icon
                      v-if="form.icon"
                      :icon-class="form.icon"
                      class="el-input__icon"
                      style="height: 32px;width: 16px;"
                    />
                    <i v-else class="el-icon-search el-input__icon" />
                  </template>
                </el-input>
              </template>
              <icon-select @selected="selectedIcon" />
            </el-popover>
          </el-form-item> -->
          
          <!-- <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请输入备注信息"
            />
          </el-form-item> -->
        </el-form>
        
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import * as echarts from 'echarts';
  import {
    getSecuritySysMenuTree,
    addSecuritySysMenu,
    updateSecuritySysMenu,
    delSecuritySysMenu,
    getSecuritySysMenu
  } from '@/api/security/sysmenu';
  import IconSelect from '@/components/IconSelect';
  import { Plus, Edit, Delete } from '@element-plus/icons-vue';
  
  export default {
    name: 'SecuritySysMenuTree',
    components: {
      IconSelect,
      Plus,
      Edit,
      Delete
    },
    data() {
      return {
        activeTab: 'operation', // 当前激活的标签页
        menuTreeData: [], // 树形结构数据
        defaultProps: {
          children: 'children', // 子节点字段名
          label: 'menuName' // 节点显示的标签字段名
        },
        dialogVisible: false, // 控制弹窗显示与隐藏
        formTitle: '', // 弹窗标题
        form: {
          menuId: undefined, // 菜单ID
          parentId: 0, // 父级菜单ID
          parentName: '无', // 父级菜单名称
          menuType: 'M', // 菜单类型：M-目录, C-菜单, F-按钮
          menuName: '', // 菜单名称
          orderNum: 0, // 显示排序
          path: '', // 路由地址
          component: '', // 组件路径
          isFrame: false, // 是否外链
          visible: true, // 是否显示
          status: true, // 菜单状态
          perms: '', // 权限标识
          icon: '', // 菜单图标
          remark: '' // 备注
        },
        rules: {
          menuName: [
            { required: true, message: '菜单名称不能为空', trigger: 'blur' }
          ],
          path: [
            { required: true, message: '路由地址不能为空', trigger: 'blur' }
          ]
        },
        echartInstance: null // ECharts实例
      };
    },
    mounted() {
      this.initEChart(); // 初始化ECharts
      this.getTree(); // 获取菜单树数据
    },
    methods: {
      initEChart() {
        // 初始化ECharts实例
        this.echartInstance = echarts.init(this.$refs.echartContainer);
        window.addEventListener('resize', this.resizeEChart); // 监听窗口大小变化
        
        // 监听ECharts节点点击事件
        this.echartInstance.on('click', params => {
          if (params.data && params.data.path) {
            this.handleEChartNodeClick(params.data);
          }
        });
      },
      
      // 处理ECharts节点点击事件
      handleEChartNodeClick(nodeData) {
        if (nodeData.menuType === 'C' && nodeData.path) {
          if (nodeData.isFrame) {
            window.open(nodeData.path); // 外链打开
          } else {
            this.$router.push(nodeData.path); // 内部路由跳转
          }
        }
        
        // 高亮显示点击的节点
        this.echartInstance.dispatchAction({
          type: 'highlight',
          seriesIndex: 0,
          dataIndex: nodeData.dataIndex
        });
      },
  
      // 获取菜单树数据
      getTree() {
        getSecuritySysMenuTree().then(response => {
          this.menuTreeData = this.buildTree(response.data, 0); // 构建树形结构
          this.renderEChart(this.menuTreeData); // 渲染ECharts
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
      
      // 渲染ECharts图表
      renderEChart(data) {
        const option = {
          tooltip: {
            trigger: 'item',
            triggerOn: 'mousemove',
            formatter: params => {
              return `${params.data.menuName}<br/>类型: ${this.getTypeName(params.data.menuType)}`;
            }
          },
          series: [{
            type: 'tree',
            data: this.convertToEChartData(data), // 转换为ECharts数据格式
            symbol: 'roundRect', // 节点形状：圆角矩形
            symbolSize: [20, 20], // 节点大小调整为更宽一些
            orient: 'horizontal', // 修改为水平方向
            expandAndCollapse: true, // 允许展开和折叠节点
            initialTreeDepth: 2, // 初始展开的层级减少，避免过于拥挤
            label: {
              position: 'left', // 标签位置调整到左侧
              fontSize: 14, // 字体大小适当增大
              color: '#333', // 字体颜色保持深色
              align: 'right', // 文本右对齐，与水平方向匹配
              verticalAlign: 'middle' // 垂直居中对齐
            },
            leaves: {
              label: {
                position: 'bottom', // 叶子节点标签放在底部
                verticalAlign: 'middle'
              }
            },
            emphasis: {
              focus: 'descendant', // 高亮时聚焦子孙节点
              itemStyle: {
                shadowBlur: 15, // 增加阴影模糊度
                shadowColor: 'rgba(0, 0, 0, 0.6)', // 阴影颜色更深一些
                borderColor: '#fff', // 边框颜色设置为白色
                borderWidth: 2 // 边框宽度增加
              }
            },
            animationDuration: 700, // 动画持续时间稍微延长
            animationDurationUpdate: 500, // 更新动画的时间
            lineStyle: {
              color: '#ccc', // 连接线颜色变浅
              width: 2, // 线条宽度增加
              curveness: 0.5 // 曲线弯曲程度增加，使线条更柔和
            }
          }]
        };
        this.echartInstance.setOption(option); // 设置ECharts配置项
      },
      
      // 转换为ECharts数据格式
      convertToEChartData(data) {
        let dataIndex = 0;
        const processNode = (node) => {
          const echartNode = {
            name: node.menuName,
            menuName: node.menuName,
            menuType: node.menuType,
            path: node.path,
            isFrame: node.isFrame,
            dataIndex: dataIndex++,
            itemStyle: {
              color: this.getNodeColor(node.menuType) // 设置节点颜色
            },
            symbolSize: node.menuType === 'M' ? 30 : 20,
            label: {
              show: true,
              position: node.children && node.children.length ? 'left' : 'right',
              fontSize: 12,
              offset: [0, 0],
            }
          };
  
          if (node.children && node.children.length) {
            echartNode.children = node.children.map(processNode);
            echartNode.collapsed = false;
          }
  
          return echartNode;
        };
        return data.map(processNode);
      },
      
      // 获取节点颜色
      getNodeColor(menuType) {
        switch(menuType) {
          case 'M': return '#5470c6'; // 目录颜色
          case 'C': return '#91cc75'; // 菜单颜色
          case 'F': return '#ee6666'; // 按钮颜色
          default: return '#73c0de'; // 默认颜色
        }
      },
      
      // 获取菜单类型名称
      getTypeName(menuType) {
        switch(menuType) {
          case 'M': return '目录';
          case 'C': return '菜单';
          case 'F': return '按钮';
          default: return '未知';
        }
      },
      
      // 处理添加根节点
      handleAddRoot() {
        this.resetForm(); // 重置表单
        this.formTitle = '添加根菜单'; // 设置弹窗标题
        this.form.parentId = 0; // 设置父级菜单ID为0
        this.form.parentName = '无'; // 设置父级菜单名称为“无”
        this.dialogVisible = true; // 显示弹窗
      },
      
      // 处理添加子节点
      handleAddChild(data) {
        this.resetForm(); // 重置表单
        this.formTitle = '添加子菜单'; // 设置弹窗标题
        this.form.parentId = data.menuId; // 设置父级菜单ID
        this.form.parentName = data.menuName; // 设置父级菜单名称
        this.dialogVisible = true; // 显示弹窗
      },
      
      // 处理编辑节点
      handleEdit(data) {
        this.resetForm(); // 重置表单
        this.formTitle = '修改菜单'; // 设置弹窗标题
        getSecuritySysMenu(data.menuId).then(response => {
          this.form = response.data; // 设置表单数据
          if (this.$refs.menuTree) {
            const node = this.$refs.menuTree.getNode(data.menuId);
            if (node.parent && node.parent.data) {
              this.form.parentName = node.parent.data.menuName; // 设置父级菜单名称
            } else {
              this.form.parentName = '无'; // 设置父级菜单名称为“无”
            }
          }
          this.dialogVisible = true; // 显示弹窗
        });
      },
      
      // 处理删除节点
      handleDelete(data) {
        this.$confirm('确认删除该菜单吗?', '提示', {
          type: 'warning'
        }).then(() => {
          delSecuritySysMenu(data.menuId).then(() => {
            this.$message.success('删除成功'); // 显示删除成功提示
            this.getTree(); // 重新获取菜单树数据
          });
        });
      },
      
      // 提交表单
      submitForm() {
        this.$refs.menuForm.validate(valid => {
          if (valid) {
            const api = this.form.menuId ? updateSecuritySysMenu : addSecuritySysMenu; // 根据是否有menuId选择更新或添加API
            api(this.form).then(() => {
              this.$message.success('操作成功'); // 显示操作成功提示
              this.dialogVisible = false; // 隐藏弹窗
              this.getTree(); // 重新获取菜单树数据
            });
          }
        });
      },
      
      // 重置表单
      resetForm() {
        this.form = {
          menuId: undefined,
          parentId: this.form.parentId || 0,
          parentName: this.form.parentName || '无',
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
        this.$nextTick(() => {
          if (this.$refs.menuForm) {
            this.$refs.menuForm.resetFields(); // 重置表单字段
          }
        });
      },
      
      // 选择图标
      selectedIcon(iconName) {
        this.form.icon = iconName; // 设置选中的图标
      },
      
      // 调整ECharts大小
      resizeEChart() {
        this.echartInstance && this.echartInstance.resize(); // 调整ECharts大小
      },
      
      // 处理树形节点点击事件
      handleNodeClick(data) {
        if (data.menuType === 'C' && data.path) {
          if (data.isFrame) {
            window.open(data.path); // 外链打开
          } else {
            this.$router.push(data.path); // 内部路由跳转
          }
        }
      }
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.resizeEChart); // 移除窗口大小变化监听
      this.echartInstance && this.echartInstance.dispose(); // 销毁ECharts实例
    }
  };
  </script>
  
  <style scoped>
  .app-container {
    padding: 20px;
  }

  /* 树节点样式优化 */
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding: 8px 12px;
    margin: 2px 0;
    border-radius: 6px;
    transition: all 0.3s ease;
    min-height: 40px;
  }

  .custom-tree-node:hover {
    background-color: #f5f7fa;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }

  .node-content {
    display: flex;
    align-items: center;
    flex: 1;
  }

  .node-icon {
    margin-right: 8px;
    font-size: 16px;
    color: #409eff;
    min-width: 16px;
  }

  .node-label {
    font-weight: 500;
    color: #303133;
    line-height: 1.5;
  }

  .node-actions {
    display: flex;
    align-items: center;
    gap: 8px;
    opacity: 0;
    transition: opacity 0.3s ease;
  }

  .custom-tree-node:hover .node-actions {
    opacity: 1;
  }

  .node-actions .el-button {
    padding: 4px 8px;
    font-size: 12px;
    border-radius: 4px;
  }

  .node-actions .el-button .el-icon {
    margin-right: 4px;
  }

  /* 树组件整体样式 */
  :deep(.el-tree) {
    background: transparent;
  }

  :deep(.el-tree-node) {
    margin-bottom: 4px;
  }

  :deep(.el-tree-node__content) {
    padding: 0 !important;
    height: auto !important;
    min-height: 44px;
    border-radius: 6px;
    transition: all 0.3s ease;
  }

  :deep(.el-tree-node__content:hover) {
    background-color: transparent !important;
  }

  :deep(.el-tree-node__expand-icon) {
    padding: 8px;
    font-size: 14px;
    color: #606266;
  }

  :deep(.el-tree-node__expand-icon.expanded) {
    transform: rotate(90deg);
  }

  /* 选中状态样式 */
  :deep(.el-tree-node.is-current > .el-tree-node__content) {
    background-color: #e6f7ff !important;
    border: 1px solid #91d5ff;
  }

  :deep(.el-tree-node.is-current > .el-tree-node__content) .node-label {
    color: #1890ff;
    font-weight: 600;
  }

  /* 根节点特殊样式 */
  :deep(.el-tree > .el-tree-node > .el-tree-node__content) {
    font-weight: 600;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
    border-radius: 8px;
    margin-bottom: 8px;
  }

  :deep(.el-tree > .el-tree-node > .el-tree-node__content) .node-icon {
    color: white;
  }

  :deep(.el-tree > .el-tree-node > .el-tree-node__content) .node-label {
    color: white;
  }

  /* 子节点缩进优化 */
  :deep(.el-tree-node__children) {
    padding-left: 20px;
    border-left: 2px solid #e4e7ed;
    margin-left: 15px;
  }
  </style>