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
