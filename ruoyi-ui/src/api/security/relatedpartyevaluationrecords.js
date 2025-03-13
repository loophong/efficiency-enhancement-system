import request from '@/utils/request'

// 查询相关方评价记录列表
export function listRelatedpartyevaluationrecords(query) {
  return request({
    url: '/security/relatedpartyevaluationrecords/list',
    method: 'get',
    params: query
  })
}

// 查询相关方评价记录详细
export function getRelatedpartyevaluationrecords(id) {
  return request({
    url: '/security/relatedpartyevaluationrecords/' + id,
    method: 'get'
  })
}

// 新增相关方评价记录
export function addRelatedpartyevaluationrecords(data) {
  return request({
    url: '/security/relatedpartyevaluationrecords',
    method: 'post',
    data: data
  })
}

// 修改相关方评价记录
export function updateRelatedpartyevaluationrecords(data) {
  return request({
    url: '/security/relatedpartyevaluationrecords',
    method: 'put',
    data: data
  })
}

// 删除相关方评价记录
export function delRelatedpartyevaluationrecords(id) {
  return request({
    url: '/security/relatedpartyevaluationrecords/' + id,
    method: 'delete'
  })
}
