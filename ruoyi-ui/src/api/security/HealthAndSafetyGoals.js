import request from '@/utils/request'

// 查询年度环境职业健康安全目标、指标及工作分解列表
export function listHealthAndSafetyGoals(query) {
  return request({
    url: '/security/HealthAndSafetyGoals/list',
    method: 'get',
    params: query
  })
}

// 查询年度环境职业健康安全目标、指标及工作分解详细
export function getHealthAndSafetyGoals(serialNumber) {
  return request({
    url: '/security/HealthAndSafetyGoals/' + serialNumber,
    method: 'get'
  })
}

// 新增年度环境职业健康安全目标、指标及工作分解
export function addHealthAndSafetyGoals(data) {
  return request({
    url: '/security/HealthAndSafetyGoals',
    method: 'post',
    data: data
  })
}

// 修改年度环境职业健康安全目标、指标及工作分解
export function updateHealthAndSafetyGoals(data) {
  return request({
    url: '/security/HealthAndSafetyGoals',
    method: 'put',
    data: data
  })
}

// 删除年度环境职业健康安全目标、指标及工作分解
export function delHealthAndSafetyGoals(serialNumber) {
  return request({
    url: '/security/HealthAndSafetyGoals/' + serialNumber,
    method: 'delete'
  })
}
