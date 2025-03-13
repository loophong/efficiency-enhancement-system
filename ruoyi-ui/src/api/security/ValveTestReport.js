import request from '@/utils/request'

// 查询安全阀检测报告列表
export function listValveTestReport(query) {
  return request({
    url: '/security/ValveTestReport/list',
    method: 'get',
    params: query
  })
}

// 查询安全阀检测报告详细
export function getValveTestReport(id) {
  return request({
    url: '/security/ValveTestReport/' + id,
    method: 'get'
  })
}

// 新增安全阀检测报告
export function addValveTestReport(data) {
  return request({
    url: '/security/ValveTestReport',
    method: 'post',
    data: data
  })
}

// 修改安全阀检测报告
export function updateValveTestReport(data) {
  return request({
    url: '/security/ValveTestReport',
    method: 'put',
    data: data
  })
}

// 删除安全阀检测报告
export function delValveTestReport(id) {
  return request({
    url: '/security/ValveTestReport/' + id,
    method: 'delete'
  })
}
