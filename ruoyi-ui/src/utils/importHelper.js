import { ElMessage, ElMessageBox } from 'element-plus'
import { ElLoading } from 'element-plus'
import axios from 'axios';
import { getToken } from '@/utils/auth';
import { addFilemanagement } from '@/api/security/filemanagement';

/**
 * Excel导入辅助工具
 * 提供通用的Excel导入功能，并自动记录导入的文件到文件管理中心
 */
const importHelper = {
  /**
   * 执行导入操作
   * @param {Object} options 导入选项
   * @param {String} options.url 导入接口地址
   * @param {File} options.file 要导入的Excel文件
   * @param {String} options.moduleName 模块名称
   * @param {String} options.moduleCode 模块代码
   * @param {Function} options.success 成功回调
   * @param {Function} options.error 错误回调
   * @param {Boolean} options.showMsg 是否显示消息提示，默认true
   * @param {Boolean} options.recordToFileManagement 是否记录到文件管理中心，默认true
   */
  importExcel(options) {
    if (!options.url) {
      ElMessage.error('导入接口地址不能为空');
      return;
    }

    if (!options.file) {
      ElMessage.error('导入文件不能为空');
      return;
    }

    // 验证文件类型
    const fileName = options.file.name;
    const fileExt = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
    if (fileExt !== 'xlsx' && fileExt !== 'xls') {
      ElMessage.error('只支持.xlsx和.xls格式的Excel文件');
      return;
    }

    // 显示加载中
    const loading = ElLoading.service({
      text: '正在导入数据，请稍候...',
      background: 'rgba(0, 0, 0, 0.7)'
    });

    // 构建表单数据
    const formData = new FormData();
    formData.append('file', options.file);

    // 如果有其他参数，也添加到formData中
    if (options.params) {
      Object.keys(options.params).forEach(key => {
        formData.append(key, options.params[key]);
      });
    }

    // 调用接口导入数据
    axios({
      url: options.url,
      method: 'post',
      data: formData,
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': 'Bearer ' + getToken()
      }
    }).then(response => {
      loading.close();
      
      const data = response.data;
      if (data.code === 200) {
        if (options.showMsg !== false) {
          ElMessage.success(data.msg || '导入成功');
        }
        
        // 记录导入文件到文件管理中心
        if (options.recordToFileManagement !== false) {
          this.recordImportFile(options.file, options);
        }
        
        // 调用成功回调
        if (typeof options.success === 'function') {
          options.success(data);
        }
      } else {
        if (options.showMsg !== false) {
          ElMessage.error(data.msg || '导入失败');
        }
        
        // 调用错误回调
        if (typeof options.error === 'function') {
          options.error(data);
        }
      }
    }).catch(error => {
      loading.close();
      
      if (options.showMsg !== false) {
        ElMessage.error('导入失败：' + error.message);
      }
      
      // 调用错误回调
      if (typeof options.error === 'function') {
        options.error(error);
      }
    });
  },

  /**
   * 记录导入文件到文件管理中心
   * @param {File} file Excel文件
   * @param {Object} options 导入选项
   */
  recordImportFile(file, options) {
    // 获取当前页面信息
    const path = window.location.pathname;
    const title = document.title.replace(' - 若依管理系统', '');
    
    // 构造文件记录
    const fileRecord = {
      fileName: file.name,
      filePath: file.name, // 由于是导入文件，这里可能没有实际存储路径，暂用文件名
      fileSize: file.size,
      fileType: file.type || 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      fileCategory: 'IMPORT', // 设置为导入文件
      moduleName: options.moduleName || title || '未知模块',
      moduleCode: options.moduleCode || path || '',
      uploadTime: new Date(),
      status: '0',
      remark: `从${options.moduleName || title || '未知模块'}导入`
    };
    
    // 保存到文件管理中心
    addFilemanagement(fileRecord).then(response => {
      if (response.code === 200) {
        console.log('导入文件已记录到文件管理中心:', response.data);
      } else {
        console.error('记录导入文件到管理中心失败:', response.msg);
      }
    }).catch(error => {
      console.error('记录导入文件到管理中心出错:', error);
    });
  }
};

export default importHelper; 