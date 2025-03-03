import request from '@/utils/request'

// 查询相关方期望列表
export function listParty1(query) {
  return request({
    url: '/security/party1/list',
    method: 'get',
    params: query
  })
}

// 查询相关方期望详细
export function getParty1(id) {
  return request({
    url: '/security/party1/' + id,
    method: 'get'
  })
}

// 新增相关方期望
export function addParty1(data) {
  return request({
    url: '/security/party1',
    method: 'post',
    data: data
  })
}

// 修改相关方期望
export function updateParty1(data) {
  return request({
    url: '/security/party1',
    method: 'put',
    data: data
  })
}

// 删除相关方期望
export function delParty1(id) {
  return request({
    url: '/security/party1/' + id,
    method: 'delete'
  })
}
