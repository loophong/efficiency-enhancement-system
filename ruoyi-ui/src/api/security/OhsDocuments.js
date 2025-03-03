import request from '@/utils/request'

// 查询环境职业健康安全管理体系文件清单列表
export function listOhsDocuments(query) {
  return request({
    url: '/security/OhsDocuments/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理体系文件清单详细
export function getOhsDocuments(id) {
  return request({
    url: '/security/OhsDocuments/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理体系文件清单
export function addOhsDocuments(data) {
  return request({
    url: '/security/OhsDocuments',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理体系文件清单
export function updateOhsDocuments(data) {
  return request({
    url: '/security/OhsDocuments',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理体系文件清单
export function delOhsDocuments(id) {
  return request({
    url: '/security/OhsDocuments/' + id,
    method: 'delete'
  })
}
