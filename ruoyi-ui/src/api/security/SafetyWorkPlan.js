import request from '@/utils/request'

// 查询中长短期安全工作计划列表
export function listSafetyWorkPlan(query) {
  return request({
    url: '/security/SafetyWorkPlan/list',
    method: 'get',
    params: query
  })
}

// 查询中长短期安全工作计划详细
export function getSafetyWorkPlan(id) {
  return request({
    url: '/security/SafetyWorkPlan/' + id,
    method: 'get'
  })
}

// 新增中长短期安全工作计划
export function addSafetyWorkPlan(data) {
  return request({
    url: '/security/SafetyWorkPlan',
    method: 'post',
    data: data
  })
}

// 修改中长短期安全工作计划
export function updateSafetyWorkPlan(data) {
  return request({
    url: '/security/SafetyWorkPlan',
    method: 'put',
    data: data
  })
}

// 删除中长短期安全工作计划
export function delSafetyWorkPlan(id) {
  return request({
    url: '/security/SafetyWorkPlan/' + id,
    method: 'delete'
  })
}
