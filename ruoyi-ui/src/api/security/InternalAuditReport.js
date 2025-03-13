import request from '@/utils/request'

// 查询内审报告列表
export function listInternalAuditReport(query) {
  return request({
    url: '/security/InternalAuditReport/list',
    method: 'get',
    params: query
  })
}

// 查询内审报告详细
export function getInternalAuditReport(id) {
  return request({
    url: '/security/InternalAuditReport/' + id,
    method: 'get'
  })
}

// 新增内审报告
export function addInternalAuditReport(data) {
  return request({
    url: '/security/InternalAuditReport',
    method: 'post',
    data: data
  })
}

// 修改内审报告
export function updateInternalAuditReport(data) {
  return request({
    url: '/security/InternalAuditReport',
    method: 'put',
    data: data
  })
}

// 删除内审报告
export function delInternalAuditReport(id) {
  return request({
    url: '/security/InternalAuditReport/' + id,
    method: 'delete'
  })
}
