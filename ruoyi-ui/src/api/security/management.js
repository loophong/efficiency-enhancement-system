import request from '@/utils/request'

// 查询管理手册列表
export function listManagement(query) {
  return request({
    url: '/security/management/list',
    method: 'get',
    params: query
  })
}

// 查询管理手册详细
export function getManagement(id) {
  return request({
    url: '/security/management/' + id,
    method: 'get'
  })
}

// 新增管理手册
export function addManagement(data) {
  return request({
    url: '/security/management',
    method: 'post',
    data: data
  })
}

// 修改管理手册
export function updateManagement(data) {
  return request({
    url: '/security/management',
    method: 'put',
    data: data
  })
}

// 删除管理手册
export function delManagement(id) {
  return request({
    url: '/security/management/' + id,
    method: 'delete'
  })
}
