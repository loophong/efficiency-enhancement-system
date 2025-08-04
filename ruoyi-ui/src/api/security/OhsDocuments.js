 import request from '@/utils/request'

// 查询环境职业健康安全管理体系文件清单列表
export function listOhsDocuments(query) {
  return request({
    url: '/security/OhsDocuments/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理体系文件清单详细
export function getOhsDocuments(id) {
  return request({
    url: '/security/OhsDocuments/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理体系文件清单
export function addOhsDocuments(data) {
  return request({
    url: '/security/OhsDocuments',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理体系文件清单
export function updateOhsDocuments(data) {
  return request({
    url: '/security/OhsDocuments',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理体系文件清单
export function delOhsDocuments(id) {
  return request({
    url: '/security/OhsDocuments/' + id,
    method: 'delete'
  })
}

export function importFile(formData) {
  return request({
    url: '/security/OhsDocuments/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 上传文件
export function uploadFile(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data
  })
}

// 获取文件列表
export function getFileList(businessId) {
  return request({
    url: '/system/file/list',
    method: 'get',
    params: {
      businessId: businessId
    }
  })
}

// 删除文件
export function deleteFile(fileId) {
  return request({
    url: '/system/file/' + fileId,
    method: 'delete'
  })
}
