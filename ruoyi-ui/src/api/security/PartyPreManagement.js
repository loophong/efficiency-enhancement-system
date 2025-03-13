import request from '@/utils/request'

// 查询相关方前置管理列表
export function listPartyPreManagement(query) {
  return request({
    url: '/security/PartyPreManagement/list',
    method: 'get',
    params: query
  })
}

// 查询相关方前置管理详细
export function getPartyPreManagement(id) {
  return request({
    url: '/security/PartyPreManagement/' + id,
    method: 'get'
  })
}

// 新增相关方前置管理
export function addPartyPreManagement(data) {
  return request({
    url: '/security/PartyPreManagement',
    method: 'post',
    data: data
  })
}

// 修改相关方前置管理
export function updatePartyPreManagement(data) {
  return request({
    url: '/security/PartyPreManagement',
    method: 'put',
    data: data
  })
}

// 删除相关方前置管理
export function delPartyPreManagement(id) {
  return request({
    url: '/security/PartyPreManagement/' + id,
    method: 'delete'
  })
}
