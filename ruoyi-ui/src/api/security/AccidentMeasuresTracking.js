import request from '@/utils/request'

// 查询事故对策及跟踪记录列表
export function listAccidentMeasuresTracking(query) {
  return request({
    url: '/security/AccidentMeasuresTracking/list',
    method: 'get',
    params: query
  })
}

// 查询事故对策及跟踪记录详细
export function getAccidentMeasuresTracking(id) {
  return request({
    url: '/security/AccidentMeasuresTracking/' + id,
    method: 'get'
  })
}

// 新增事故对策及跟踪记录
export function addAccidentMeasuresTracking(data) {
  return request({
    url: '/security/AccidentMeasuresTracking',
    method: 'post',
    data: data
  })
}

// 修改事故对策及跟踪记录
export function updateAccidentMeasuresTracking(data) {
  return request({
    url: '/security/AccidentMeasuresTracking',
    method: 'put',
    data: data
  })
}

// 删除事故对策及跟踪记录
export function delAccidentMeasuresTracking(id) {
  return request({
    url: '/security/AccidentMeasuresTracking/' + id,
    method: 'delete'
  })
}
