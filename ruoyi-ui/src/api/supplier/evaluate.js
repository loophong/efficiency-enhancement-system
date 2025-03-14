import request from '@/utils/request'

// 查询评分表列表
export function listEvaluate(query) {
  return request({
    url: '/supplier/evaluate/list',
    method: 'get',
    params: query
  })
}

// 查询评分表详细
export function getEvaluate(id) {
  return request({
    url: '/supplier/evaluate/' + id,
    method: 'get'
  })
}

// 新增评分表
export function addEvaluate(data) {
  return request({
    url: '/supplier/evaluate',
    method: 'post',
    data: data
  })
}

// 修改评分表
export function updateEvaluate(data) {
  return request({
    url: '/supplier/evaluate',
    method: 'put',
    data: data
  })
}

// 删除评分表
export function delEvaluate(id) {
  return request({
    url: '/supplier/evaluate/' + id,
    method: 'delete'
  })
}

// 删除评分表
export function calculateScore(data) {
  return request({
    url: '/supplier/evaluate/calculateScore',
    method: 'post',
    data: data
  })
}
