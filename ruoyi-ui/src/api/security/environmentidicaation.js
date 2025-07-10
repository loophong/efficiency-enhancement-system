import request from '@/utils/request'

// 查询环境识别列表
export function listEnvironmentidicaation(query) {
  return request({
    url: '/security/environmentidicaation/list',
    method: 'get',
    params: query
  })
}

// 查询环境识别树形结构
export function listOrganization(query) {
  return request({
    url: '/security/environmentidicaation/treeList',
    method: 'get',
    params: query
  })
}

// 查询环境识别详细
export function getEnvironmentidicaation(id) {
  return request({
    url: '/security/environmentidicaation/' + id,
    method: 'get'
  })
}

// 新增环境识别
export function addEnvironmentidicaation(data) {
  return request({
    url: '/security/environmentidicaation',
    method: 'post',
    data: data
  })
}

// 修改环境识别
export function updateEnvironmentidicaation(data) {
  return request({
    url: '/security/environmentidicaation',
    method: 'put',
    data: data
  })
}

// 删除环境识别
export function delEnvironmentidicaation(id) {
  return request({
    url: '/security/environmentidicaation/' + id,
    method: 'delete'
  })
}

// 导入环境识别
export function importEnvironmentidicaation(formData) {
  return request({
    url: '/security/environmentidicaation/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 下载导入模板
export function importTemplate() {
  return request({
    url: '/security/environmentidicaation/import/template',
    method: 'get',
    responseType: 'blob'
  })
}
