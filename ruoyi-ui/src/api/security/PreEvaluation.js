import request from '@/utils/request'

// 查询安全预评价列表
export function listPreEvaluation(query) {
  return request({
    url: '/security/PreEvaluation/list',
    method: 'get',
    params: query
  })
}

// 查询安全预评价详细
export function getPreEvaluation(id) {
  return request({
    url: '/security/PreEvaluation/' + id,
    method: 'get'
  })
}

// 新增安全预评价
export function addPreEvaluation(data) {
  return request({
    url: '/security/PreEvaluation',
    method: 'post',
    data: data
  })
}

// 修改安全预评价
export function updatePreEvaluation(data) {
  return request({
    url: '/security/PreEvaluation',
    method: 'put',
    data: data
  })
}

// 删除安全预评价
export function delPreEvaluation(id) {
  return request({
    url: '/security/PreEvaluation/' + id,
    method: 'delete'
  })
}
