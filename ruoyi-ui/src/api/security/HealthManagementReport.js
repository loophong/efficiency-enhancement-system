import request from '@/utils/request'

// 查询环境职业健康安全管理评审报告列表
export function listHealthManagementReport(query) {
  return request({
    url: '/security/HealthManagementReport/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理评审报告详细
export function getHealthManagementReport(id) {
  return request({
    url: '/security/HealthManagementReport/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理评审报告
export function addHealthManagementReport(data) {
  return request({
    url: '/security/HealthManagementReport',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理评审报告
export function updateHealthManagementReport(data) {
  return request({
    url: '/security/HealthManagementReport',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理评审报告
export function delHealthManagementReport(id) {
  return request({
    url: '/security/HealthManagementReport/' + id,
    method: 'delete'
  })
}
