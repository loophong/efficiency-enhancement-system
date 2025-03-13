import request from '@/utils/request'

// 查询相关方期望列表
export function listRequireExpectParty(query) {
  return request({
    url: '/security/RequireExpectParty/list',
    method: 'get',
    params: query
  })
}

// 查询相关方期望详细
export function getRequireExpectParty(id) {
  return request({
    url: '/security/RequireExpectParty/' + id,
    method: 'get'
  })
}

// 新增相关方期望
export function addRequireExpectParty(data) {
  return request({
    url: '/security/RequireExpectParty',
    method: 'post',
    data: data
  })
}

// 修改相关方期望
export function updateRequireExpectParty(data) {
  return request({
    url: '/security/RequireExpectParty',
    method: 'put',
    data: data
  })
}

// 删除相关方期望
export function delRequireExpectParty(id) {
  return request({
    url: '/security/RequireExpectParty/' + id,
    method: 'delete'
  })
}
