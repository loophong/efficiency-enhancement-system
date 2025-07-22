import request from '@/utils/request'

// 查询岗位清单列表
export function listPosition(query) {
  return request({
    url: '/security/Position/list',
    method: 'get',
    params: query
  })
}

// 查询岗位清单详细
export function getPosition(id) {
  return request({
    url: '/security/Position/' + id,
    method: 'get'
  })
}

// 新增岗位清单
export function addPosition(data) {
  return request({
    url: '/security/Position',
    method: 'post',
    data: data
  })
}

// 修改岗位清单
export function updatePosition(data) {
  return request({
    url: '/security/Position',
    method: 'put',
    data: data
  })
}

// 删除岗位清单
export function delPosition(id) {
  return request({
    url: '/security/Position/' + id,
    method: 'delete'
  })
}

// 导入岗位清单
export function importPosition(data) {
  return request({
    url: '/security/Position/importData',
    method: 'post',
    data: data
  })
}

// 下载岗位清单导入模板
export function downloadTemplate() {
  return request({
    url: '/security/Position/importTemplate',
    method: 'get'
  })
}

// 根据关联ID查询岗位清单列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/Position/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
