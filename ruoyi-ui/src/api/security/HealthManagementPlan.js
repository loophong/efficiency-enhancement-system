import request from '@/utils/request'

// 查询环境职业健康安全管理评审计划列表
export function listHealthManagementPlan(query) {
  return request({
    url: '/security/HealthManagementPlan/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理评审计划详细
export function getHealthManagementPlan(id) {
  return request({
    url: '/security/HealthManagementPlan/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理评审计划
export function addHealthManagementPlan(data) {
  return request({
    url: '/security/HealthManagementPlan',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理评审计划
export function updateHealthManagementPlan(data) {
  return request({
    url: '/security/HealthManagementPlan',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理评审计划
export function delHealthManagementPlan(id) {
  return request({
    url: '/security/HealthManagementPlan/' + id,
    method: 'delete'
  })
}
