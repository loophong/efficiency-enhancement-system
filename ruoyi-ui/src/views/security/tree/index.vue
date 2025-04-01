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
              <el-radio label="F">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          
          <el-form-item label="菜单名称" prop="menuName">
            <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
          </el-form-item>
          
          <el-form-item label="显示排序" prop="orderNum">
            <el-input-number v-model="form.orderNum" :min="0" />
          </el-form-item>
          
          <el-form-item v-if="form.menuType !== 'F'" label="路由地址" prop="path">
            <el-input v-model="form.path" placeholder="请输入路由地址" />
          </el-form-item>
          
          <el-form-item v-if="form.menuType === 'C'" label="组件路径" prop="component">
            <el-input v-model="form.component" placeholder="请输入组件路径" />
          </el-form-item>
          
          <el-form-item v-if="form.menuType !== 'F'" label="是否外链" prop="isFrame">
            <el-radio-group v-model="form.isFrame">
              <el-radio :label="false">否</el-radio>
              <el-radio :label="true">是</el-radio>
            </el-radio-group>
          </el-form-item>
          
          <el-form-item label="菜单状态" prop="status">
            <el-radio-group v-model="form.status">
              <el-radio :label="true">显示</el-radio>
              <el-radio :label="false">隐藏</el-radio>
            </el-radio-group>
          </el-form-item>
          
          <el-form-item label="权限标识" prop="perms">
            <el-input v-model="form.perms" placeholder="请输入权限标识" />
          </el-form-item>
          
          <el-form-item label="菜单图标" prop="icon">
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
          </el-form-item>
          
          <el-form-item label="备注" prop="remark">
            <el-input
              v-model="form.remark"
              type="textarea"
              placeholder="请输入备注信息"
            />
          </el-form-item>
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
  
  export default {
    name: 'SecuritySysMenuTree',
    components: { IconSelect },
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
          orderNum: [
            { required: true, message: '菜单顺序不能为空', trigger: 'blur' }
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
            symbol: 'roundRect',
            symbolSize: [80, 30],
            orient: 'vertical',
            expandAndCollapse: true,
            initialTreeDepth: 3,
            label: {
              position: 'top',
              fontSize: 12,
              color: '#333'
            },
            leaves: {
              label: {
                position: 'right',
                verticalAlign: 'middle'
              }
            },
            emphasis: {
              focus: 'descendant',
              itemStyle: {
                shadowBlur: 10,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            animationDuration: 550
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
              fontSize: 12
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
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px; 
  }
  </style>