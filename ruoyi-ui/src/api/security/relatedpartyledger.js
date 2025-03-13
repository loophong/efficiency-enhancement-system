import request from '@/utils/request'

// 查询相关方管理台账列表
export function listRelatedpartyledger(query) {
  return request({
    url: '/security/relatedpartyledger/list',
    method: 'get',
    params: query
  })
}

// 查询相关方管理台账详细
export function getRelatedpartyledger(id) {
  return request({
    url: '/security/relatedpartyledger/' + id,
    method: 'get'
  })
}

// 新增相关方管理台账
export function addRelatedpartyledger(data) {
  return request({
    url: '/security/relatedpartyledger',
    method: 'post',
    data: data
  })
}

// 修改相关方管理台账
export function updateRelatedpartyledger(data) {
  return request({
    url: '/security/relatedpartyledger',
    method: 'put',
    data: data
  })
}

// 删除相关方管理台账
export function delRelatedpartyledger(id) {
  return request({
    url: '/security/relatedpartyledger/' + id,
    method: 'delete'
  })
}
