import request from '@/utils/request'

// 查询应急物资管理台帐列表
export function listEmergencysuppliesledger(query) {
  return request({
    url: '/security/emergencysuppliesledger/list',
    method: 'get',
    params: query
  })
}

// 查询应急物资管理台帐详细
export function getEmergencysuppliesledger(id) {
  return request({
    url: '/security/emergencysuppliesledger/' + id,
    method: 'get'
  })
}

// 新增应急物资管理台帐
export function addEmergencysuppliesledger(data) {
  return request({
    url: '/security/emergencysuppliesledger',
    method: 'post',
    data: data
  })
}

// 修改应急物资管理台帐
export function updateEmergencysuppliesledger(data) {
  return request({
    url: '/security/emergencysuppliesledger',
    method: 'put',
    data: data
  })
}

// 删除应急物资管理台帐
export function delEmergencysuppliesledger(id) {
  return request({
    url: '/security/emergencysuppliesledger/' + id,
    method: 'delete'
  })
}

// 导入应急物资管理台帐
export function importEmergencysuppliesledger(data) {
  return request({
    url: '/security/emergencysuppliesledger/importData',
    method: 'post',
    data: data
  })
}

// 下载应急物资管理台帐导入模板
export function downloadTemplate() {
  return request({
    url: '/security/emergencysuppliesledger/importTemplate',
    method: 'get'
  })
}

// 根据关联ID查询应急物资管理台帐列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/emergencysuppliesledger/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
