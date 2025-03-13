import request from '@/utils/request'

// 查询不可接受风险清单列表
export function listUnacceptablerisklist(query) {
  return request({
    url: '/security/unacceptablerisklist/list',
    method: 'get',
    params: query
  })
}

// 查询不可接受风险清单详细
export function getUnacceptablerisklist(id) {
  return request({
    url: '/security/unacceptablerisklist/' + id,
    method: 'get'
  })
}

// 新增不可接受风险清单
export function addUnacceptablerisklist(data) {
  return request({
    url: '/security/unacceptablerisklist',
    method: 'post',
    data: data
  })
}

// 修改不可接受风险清单
export function updateUnacceptablerisklist(data) {
  return request({
    url: '/security/unacceptablerisklist',
    method: 'put',
    data: data
  })
}

// 删除不可接受风险清单
export function delUnacceptablerisklist(id) {
  return request({
    url: '/security/unacceptablerisklist/' + id,
    method: 'delete'
  })
}
