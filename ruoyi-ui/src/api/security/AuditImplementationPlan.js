import request from '@/utils/request'

// 查询内审实施计划列表
export function listAuditImplementationPlan(query) {
  return request({
    url: '/security/AuditImplementationPlan/list',
    method: 'get',
    params: query
  })
}

// 查询内审实施计划详细
export function getAuditImplementationPlan(id) {
  return request({
    url: '/security/AuditImplementationPlan/' + id,
    method: 'get'
  })
}

// 新增内审实施计划
export function addAuditImplementationPlan(data) {
  return request({
    url: '/security/AuditImplementationPlan',
    method: 'post',
    data: data
  })
}

// 修改内审实施计划
export function updateAuditImplementationPlan(data) {
  return request({
    url: '/security/AuditImplementationPlan',
    method: 'put',
    data: data
  })
}

// 删除内审实施计划
export function delAuditImplementationPlan(id) {
  return request({
    url: '/security/AuditImplementationPlan/' + id,
    method: 'delete'
  })
}
