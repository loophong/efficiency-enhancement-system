import request from '@/utils/request'

// 查询危险化学品台账列表
export function listInventory(query) {
  return request({
    url: '/security/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询危险化学品台账详细
export function getInventory(id) {
  return request({
    url: '/security/inventory/' + id,
    method: 'get'
  })
}

// 新增危险化学品台账
export function addInventory(data) {
  return request({
    url: '/security/inventory',
    method: 'post',
    data: data
  })
}

// 修改危险化学品台账
export function updateInventory(data) {
  return request({
    url: '/security/inventory',
    method: 'put',
    data: data
  })
}

// 删除危险化学品台账
export function delInventory(id) {
  return request({
    url: '/security/inventory/' + id,
    method: 'delete'
  })
}
