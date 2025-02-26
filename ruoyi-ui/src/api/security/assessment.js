import request from '@/utils/request'

// 查询风险和机遇评估及控制措施列表
export function listAssessment(query) {
  return request({
    url: '/security/assessment/list',
    method: 'get',
    params: query
  })
}

// 查询风险和机遇评估及控制措施详细
export function getAssessment(id) {
  return request({
    url: '/security/assessment/' + id,
    method: 'get'
  })
}

// 新增风险和机遇评估及控制措施
export function addAssessment(data) {
  return request({
    url: '/security/assessment',
    method: 'post',
    data: data
  })
}

// 修改风险和机遇评估及控制措施
export function updateAssessment(data) {
  return request({
    url: '/security/assessment',
    method: 'put',
    data: data
  })
}

// 删除风险和机遇评估及控制措施
export function delAssessment(id) {
  return request({
    url: '/security/assessment/' + id,
    method: 'delete'
  })
}
