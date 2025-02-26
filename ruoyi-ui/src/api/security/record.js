import request from '@/utils/request'

// 查询记录表列表
export function listRecord(query) {
  return request({
    url: '/security/record/list',
    method: 'get',
    params: query
  })
}

// 查询记录表详细
export function getRecord(id) {
  return request({
    url: '/security/record/' + id,
    method: 'get'
  })
}

// 新增记录表
export function addRecord(data) {
  return request({
    url: '/security/record',
    method: 'post',
    data: data
  })
}

// 修改记录表
export function updateRecord(data) {
  return request({
    url: '/security/record',
    method: 'put',
    data: data
  })
}

// 删除记录表
export function delRecord(id) {
  return request({
    url: '/security/record/' + id,
    method: 'delete'
  })
}
