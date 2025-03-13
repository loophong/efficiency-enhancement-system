import request from '@/utils/request'

// 查询环境监测报告列表
export function listEnvironmentalMonitoringReport(query) {
  return request({
    url: '/security/EnvironmentalMonitoringReport/list',
    method: 'get',
    params: query
  })
}

// 查询环境监测报告详细
export function getEnvironmentalMonitoringReport(id) {
  return request({
    url: '/security/EnvironmentalMonitoringReport/' + id,
    method: 'get'
  })
}

// 新增环境监测报告
export function addEnvironmentalMonitoringReport(data) {
  return request({
    url: '/security/EnvironmentalMonitoringReport',
    method: 'post',
    data: data
  })
}

// 修改环境监测报告
export function updateEnvironmentalMonitoringReport(data) {
  return request({
    url: '/security/EnvironmentalMonitoringReport',
    method: 'put',
    data: data
  })
}

// 删除环境监测报告
export function delEnvironmentalMonitoringReport(id) {
  return request({
    url: '/security/EnvironmentalMonitoringReport/' + id,
    method: 'delete'
  })
}
