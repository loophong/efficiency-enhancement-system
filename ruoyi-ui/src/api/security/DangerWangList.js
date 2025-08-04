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

// 导出风险网格化清单
export function exportDangerWangList(query) {
  return request({
    url: '/security/DangerWangList/export',
    method: 'get',
    params: query
  })
}

// 获取风险网格化清单导入模板
export function importTemplate() {
  return request({
    url: '/security/DangerWangList/importTemplate',
    method: 'get'
  })
}

// 导入风险网格化清单数据
export function importData(data) {
  return request({
    url: '/security/DangerWangList/importData',
    method: 'post',
    data: data
  })
}

// 根据关联ID查询风险网格化清单数据
export function listDangerWangListByRelatedId(relatedId) {
  return request({
    url: '/security/DangerWangList/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
