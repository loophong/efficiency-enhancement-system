import request from '@/utils/request'

// 查询变更管理列表
export function listChangelist(query) {
  return request({
    url: '/security/changelist/list',
    method: 'get',
    params: query
  })
}

// 查询变更管理详细
export function getChangelist(id) {
  return request({
    url: '/security/changelist/' + id,
    method: 'get'
  })
}

// 新增变更管理
export function addChangelist(data) {
  return request({
    url: '/security/changelist',
    method: 'post',
    data: data
  })
}

// 修改变更管理
export function updateChangelist(data) {
  return request({
    url: '/security/changelist',
    method: 'put',
    data: data
  })
}

// 删除变更管理
export function delChangelist(id) {
  return request({
    url: '/security/changelist/' + id,
    method: 'delete'
  })
}
