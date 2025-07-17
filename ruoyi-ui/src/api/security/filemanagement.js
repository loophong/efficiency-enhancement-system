import request from '@/utils/request'

// 查询文件管理列表
export function listFilemanagement(query) {
  return request({
    url: '/filemanagement/filemanagement/list',
    method: 'get',
    params: query
  })
}

// 查询文件管理详细
export function getFilemanagement(id) {
  return request({
    url: '/filemanagement/filemanagement/' + id,
    method: 'get'
  })
}

// 新增文件管理
export function addFilemanagement(data) {
  return request({
    url: '/filemanagement/filemanagement',
    method: 'post',
    data: data
  })
}

// 修改文件管理
export function updateFilemanagement(data) {
  return request({
    url: '/filemanagement/filemanagement',
    method: 'put',
    data: data
  })
}

// 删除文件管理
export function delFilemanagement(id) {
  return request({
    url: '/filemanagement/filemanagement/' + id,
    method: 'delete'
  })
}

// 获取文件统计信息
export function getFileStatistics() {
  return request({
    url: '/filemanagement/filemanagement/statistics',
    method: 'get'
  })
}

// 获取文件监控数据
export function getFileMonitorData(limit) {
  return request({
    url: '/filemanagement/filemanagement/monitor',
    method: 'get',
    params: { limit }
  })
}

// 记录文件上传
export function recordFileUpload(data) {
  return request({
    url: '/filemanagement/filemanagement/record',
    method: 'post',
    data: data
  })
}

// 下載文件
export function downloadFilemanagement(id) {
  return request({
    url: '/filemanagement/filemanagement/download/' + id,
    method: 'post',
    responseType: 'blob'
  })
}
