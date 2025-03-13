import request from '@/utils/request'

// 查询不符合报告列表
export function listNonComplianceReport(query) {
  return request({
    url: '/security/NonComplianceReport/list',
    method: 'get',
    params: query
  })
}

// 查询不符合报告详细
export function getNonComplianceReport(id) {
  return request({
    url: '/security/NonComplianceReport/' + id,
    method: 'get'
  })
}

// 新增不符合报告
export function addNonComplianceReport(data) {
  return request({
    url: '/security/NonComplianceReport',
    method: 'post',
    data: data
  })
}

// 修改不符合报告
export function updateNonComplianceReport(data) {
  return request({
    url: '/security/NonComplianceReport',
    method: 'put',
    data: data
  })
}

// 删除不符合报告
export function delNonComplianceReport(id) {
  return request({
    url: '/security/NonComplianceReport/' + id,
    method: 'delete'
  })
}
