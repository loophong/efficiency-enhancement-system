import request from '@/utils/request'

// 查询安全验收评价列表
export function listAcceptanceEvaluation(query) {
  return request({
    url: '/security/AcceptanceEvaluation/list',
    method: 'get',
    params: query
  })
}

// 查询安全验收评价详细
export function getAcceptanceEvaluation(id) {
  return request({
    url: '/security/AcceptanceEvaluation/' + id,
    method: 'get'
  })
}

// 新增安全验收评价
export function addAcceptanceEvaluation(data) {
  return request({
    url: '/security/AcceptanceEvaluation',
    method: 'post',
    data: data
  })
}

// 修改安全验收评价
export function updateAcceptanceEvaluation(data) {
  return request({
    url: '/security/AcceptanceEvaluation',
    method: 'put',
    data: data
  })
}

// 删除安全验收评价
export function delAcceptanceEvaluation(id) {
  return request({
    url: '/security/AcceptanceEvaluation/' + id,
    method: 'delete'
  })
}
