import request from '@/utils/request'

// 查询安全生产责任制清单列表
export function listProductionResponsibilityList(query) {
  return request({
    url: '/security/ProductionResponsibilityList/list',
    method: 'get',
    params: query
  })
}

// 查询安全生产责任制清单详细
export function getProductionResponsibilityList(id) {
  return request({
    url: '/security/ProductionResponsibilityList/' + id,
    method: 'get'
  })
}

// 新增安全生产责任制清单
export function addProductionResponsibilityList(data) {
  return request({
    url: '/security/ProductionResponsibilityList',
    method: 'post',
    data: data
  })
}

// 修改安全生产责任制清单
export function updateProductionResponsibilityList(data) {
  return request({
    url: '/security/ProductionResponsibilityList',
    method: 'put',
    data: data
  })
}

// 删除安全生产责任制清单
export function delProductionResponsibilityList(id) {
  return request({
    url: '/security/ProductionResponsibilityList/' + id,
    method: 'delete'
  })
}
