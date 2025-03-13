import request from '@/utils/request'

// 查询压力检测报告列表
export function listGaugeTestReport(query) {
  return request({
    url: '/security/GaugeTestReport/list',
    method: 'get',
    params: query
  })
}

// 查询压力检测报告详细
export function getGaugeTestReport(id) {
  return request({
    url: '/security/GaugeTestReport/' + id,
    method: 'get'
  })
}

// 新增压力检测报告
export function addGaugeTestReport(data) {
  return request({
    url: '/security/GaugeTestReport',
    method: 'post',
    data: data
  })
}

// 修改压力检测报告
export function updateGaugeTestReport(data) {
  return request({
    url: '/security/GaugeTestReport',
    method: 'put',
    data: data
  })
}

// 删除压力检测报告
export function delGaugeTestReport(id) {
  return request({
    url: '/security/GaugeTestReport/' + id,
    method: 'delete'
  })
}
