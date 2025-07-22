import { addFilemanagement } from '@/api/security/filemanagement'
import request from '@/utils/request'
import router from '@/router'
import store from '@/store'

// 文件监听服务
const fileMonitor = {
  // 记录文件上传信息
  recordFileUpload(fileInfo) {
    // 构建文件管理记录
    const fileRecord = {
      fileName: fileInfo.fileName || fileInfo.name,
      filePath: fileInfo.filePath || fileInfo.url,
      fileSize: fileInfo.size || 0,
      fileType: fileInfo.type || '',
      fileCategory: fileInfo.isImport ? 'IMPORT' : 'UPLOAD', // 文件分类：上传或导入
      moduleName: this.getModuleName(),
      moduleCode: this.getModuleCode(),
      uploadTime: new Date().toISOString().split('T')[0], // 格式化为YYYY-MM-DD
      uploadUser: store.getters.name || '系统用户',
      relatedUrl: this.getCurrentRoute(),
      status: '0', // 默认正常状态
      remark: fileInfo.remark || '系统自动记录'
    }

    // 首先尝试调用后端API记录文件上传
    return request({
      url: '/monitor/file/record',
      method: 'post',
      data: {
        filePath: fileRecord.filePath,
        fileName: fileRecord.fileName,
        fileSize: fileRecord.fileSize,
        fileType: fileRecord.fileType,
        moduleName: fileRecord.moduleName,
        isImport: fileInfo.isImport,
        relatedUrl: fileRecord.relatedUrl
      }
    }).catch(error => {
      console.error('调用后端文件监控API失败，尝试使用文件管理API', error)
      // 如果后端API调用失败，则使用文件管理API作为备选
      return addFilemanagement(fileRecord)
    })
  },

  // 获取当前模块名称（基于路由路径）
  getModuleName() {
    const path = router.currentRoute.value.path
    const pathSegments = path.split('/')
    // 安全管理模块通常路径结构为 /security/xxx/yyy
    if (pathSegments.length >= 3 && pathSegments[1] === 'security') {
      return pathSegments[2] || '未知模块'
    }
    return '其他模块'
  },

  // 获取模块代码
  getModuleCode() {
    const path = router.currentRoute.value.path
    const pathSegments = path.split('/')
    if (pathSegments.length >= 3) {
      return pathSegments.slice(1).join('_')
    }
    return 'other'
  },

  // 获取当前路由地址
  getCurrentRoute() {
    return router.currentRoute.value.fullPath
  },
  
  // 获取文件上传缓存
  getFileUploadCache() {
    return request({
      url: '/monitor/file/cache',
      method: 'get'
    })
  },
  
  // 清除文件上传缓存
  clearFileUploadCache() {
    return request({
      url: '/monitor/file/clear',
      method: 'post'
    })
  }
}

export default fileMonitor

export function handleTrueDownload(url) {
  return window.location.href = "http://124.222.36.192:8080" + url;
}