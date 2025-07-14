import request from '@/utils/request'

// 查询环境因素清单列表
export function listImpact(query) {
  return request({
    url: '/security/impact/list',
    method: 'get',
    params: query
  })
}

// 查询环境因素清单详细
export function getImpact(id) {
  return request({
    url: '/security/impact/' + id,
    method: 'get'
  })
}

// 新增环境因素清单
export function addImpact(data) {
  return request({
    url: '/security/impact',
    method: 'post',
    data: data
  })
}

// 修改环境因素清单
export function updateImpact(data) {
  return request({
    url: '/security/impact',
    method: 'put',
    data: data
  })
}

// 删除环境因素清单
export function delImpact(id) {
  return request({
    url: '/security/impact/' + id,
    method: 'delete'
  })
}

// 导入环境因素清单数据
export function importImpact(data) {
  return request({
    url: '/security/impact/importData',
    method: 'post',
    data: data
  })
}

// 下载环境因素清单模板
export function downloadTemplate() {
  return request({
    url: '/security/impact/importTemplate',
    method: 'get',
    responseType: 'blob'
  })
}
