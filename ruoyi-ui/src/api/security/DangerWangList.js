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

// 导入风险网格化清单
export function importDangerWangList(data) {
  return request({
    url: '/security/DangerWangList/importData',
    method: 'post',
    data: data
  })
}

// 下载风险网格化清单导入模板
export function downloadTemplate() {
  return request({
    url: '/security/DangerWangList/importTemplate',
    method: 'post'
  })
}

// 根据关联ID查询风险网格化清单列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/DangerWangList/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
