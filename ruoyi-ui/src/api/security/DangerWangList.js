import request from '@/utils/request'

// 查询风险网格化清单列表
export function listDangerWangList(query) {
  return request({
    url: '/security/DangerWangList/list',
    method: 'get',
    params: query
  })
}

// 查询风险网格化清单详细
export function getDangerWangList(id) {
  return request({
    url: '/security/DangerWangList/' + id,
    method: 'get'
  })
}

// 新增风险网格化清单
export function addDangerWangList(data) {
  return request({
    url: '/security/DangerWangList',
    method: 'post',
    data: data
  })
}

// 修改风险网格化清单
export function updateDangerWangList(data) {
  return request({
    url: '/security/DangerWangList',
    method: 'put',
    data: data
  })
}

// 删除风险网格化清单
export function delDangerWangList(id) {
  return request({
    url: '/security/DangerWangList/' + id,
    method: 'delete'
  })
}
