import request from '@/utils/request'

// 查询公司级重要环境因素清单列表
export function listImportantfactors(query) {
  return request({
    url: '/security/importantfactors/list',
    method: 'get',
    params: query
  })
}

// 查询公司级重要环境因素清单详细
export function getImportantfactors(id) {
  return request({
    url: '/security/importantfactors/' + id,
    method: 'get'
  })
}

// 新增公司级重要环境因素清单
export function addImportantfactors(data) {
  return request({
    url: '/security/importantfactors',
    method: 'post',
    data: data
  })
}

// 修改公司级重要环境因素清单
export function updateImportantfactors(data) {
  return request({
    url: '/security/importantfactors',
    method: 'put',
    data: data
  })
}

// 删除公司级重要环境因素清单
export function delImportantfactors(id) {
  return request({
    url: '/security/importantfactors/' + id,
    method: 'delete'
  })
}
