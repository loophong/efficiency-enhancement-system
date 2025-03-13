import request from '@/utils/request'

// 查询环境职业健康安全绩效评价报告列表
export function listHealthEvaluationReport(query) {
  return request({
    url: '/security/HealthEvaluationReport/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全绩效评价报告详细
export function getHealthEvaluationReport(id) {
  return request({
    url: '/security/HealthEvaluationReport/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全绩效评价报告
export function addHealthEvaluationReport(data) {
  return request({
    url: '/security/HealthEvaluationReport',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全绩效评价报告
export function updateHealthEvaluationReport(data) {
  return request({
    url: '/security/HealthEvaluationReport',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全绩效评价报告
export function delHealthEvaluationReport(id) {
  return request({
    url: '/security/HealthEvaluationReport/' + id,
    method: 'delete'
  })
}
