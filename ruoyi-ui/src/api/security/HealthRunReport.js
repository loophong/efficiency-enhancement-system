import request from '@/utils/request'

// 查询环境职业健康安全管理体系运行报告列表
export function listHealthRunReport(query) {
  return request({
    url: '/security/HealthRunReport/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理体系运行报告详细
export function getHealthRunReport(id) {
  return request({
    url: '/security/HealthRunReport/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理体系运行报告
export function addHealthRunReport(data) {
  return request({
    url: '/security/HealthRunReport',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理体系运行报告
export function updateHealthRunReport(data) {
  return request({
    url: '/security/HealthRunReport',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理体系运行报告
export function delHealthRunReport(id) {
  return request({
    url: '/security/HealthRunReport/' + id,
    method: 'delete'
  })
}
