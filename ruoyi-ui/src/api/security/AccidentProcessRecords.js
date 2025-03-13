import request from '@/utils/request'

// 查询事故过程记录列表
export function listAccidentProcessRecords(query) {
  return request({
    url: '/security/AccidentProcessRecords/list',
    method: 'get',
    params: query
  })
}

// 查询事故过程记录详细
export function getAccidentProcessRecords(id) {
  return request({
    url: '/security/AccidentProcessRecords/' + id,
    method: 'get'
  })
}

// 新增事故过程记录
export function addAccidentProcessRecords(data) {
  return request({
    url: '/security/AccidentProcessRecords',
    method: 'post',
    data: data
  })
}

// 修改事故过程记录
export function updateAccidentProcessRecords(data) {
  return request({
    url: '/security/AccidentProcessRecords',
    method: 'put',
    data: data
  })
}

// 删除事故过程记录
export function delAccidentProcessRecords(id) {
  return request({
    url: '/security/AccidentProcessRecords/' + id,
    method: 'delete'
  })
}
