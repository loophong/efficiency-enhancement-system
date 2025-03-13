import request from '@/utils/request'

// 查询安全制度清单列表
export function listRegulationList(query) {
  return request({
    url: '/security/regulationList/list',
    method: 'get',
    params: query
  })
}

// 查询安全制度清单详细
export function getRegulationList(id) {
  return request({
    url: '/security/regulationList/' + id,
    method: 'get'
  })
}

// 新增安全制度清单
export function addRegulationList(data) {
  return request({
    url: '/security/regulationList',
    method: 'post',
    data: data
  })
}

// 修改安全制度清单
export function updateRegulationList(data) {
  return request({
    url: '/security/regulationList',
    method: 'put',
    data: data
  })
}

// 删除安全制度清单
export function delRegulationList(id) {
  return request({
    url: '/security/regulationList/' + id,
    method: 'delete'
  })
}
