import request from '@/utils/request'

// 查询事故原因分析记录列表
export function listAccidentCauseAnalysis(query) {
  return request({
    url: '/security/AccidentCauseAnalysis/list',
    method: 'get',
    params: query
  })
}

// 查询事故原因分析记录详细
export function getAccidentCauseAnalysis(id) {
  return request({
    url: '/security/AccidentCauseAnalysis/' + id,
    method: 'get'
  })
}

// 新增事故原因分析记录
export function addAccidentCauseAnalysis(data) {
  return request({
    url: '/security/AccidentCauseAnalysis',
    method: 'post',
    data: data
  })
}

// 修改事故原因分析记录
export function updateAccidentCauseAnalysis(data) {
  return request({
    url: '/security/AccidentCauseAnalysis',
    method: 'put',
    data: data
  })
}

// 删除事故原因分析记录
export function delAccidentCauseAnalysis(id) {
  return request({
    url: '/security/AccidentCauseAnalysis/' + id,
    method: 'delete'
  })
}
