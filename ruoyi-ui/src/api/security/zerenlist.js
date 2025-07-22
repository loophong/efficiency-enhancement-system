import request from '@/utils/request'

// 查询安全责任清单列表
export function listZerenlist(query) {
  return request({
    url: '/security/zerenlist/list',
    method: 'get',
    params: query
  })
}

// 查询安全责任清单详细
export function getZerenlist(id) {
  return request({
    url: '/security/zerenlist/' + id,
    method: 'get'
  })
}

// 新增安全责任清单
export function addZerenlist(data) {
  return request({
    url: '/security/zerenlist',
    method: 'post',
    data: data
  })
}

// 修改安全责任清单
export function updateZerenlist(data) {
  return request({
    url: '/security/zerenlist',
    method: 'put',
    data: data
  })
}

// 删除安全责任清单
export function delZerenlist(id) {
  return request({
    url: '/security/zerenlist/' + id,
    method: 'delete'
  })
}
