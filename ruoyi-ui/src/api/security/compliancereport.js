import request from '@/utils/request'

// 查询合规性评价报告列表
export function listCompliancereport(query) {
  return request({
    url: '/security/compliancereport/list',
    method: 'get',
    params: query
  })
}

// 查询合规性评价报告详细
export function getCompliancereport(id) {
  return request({
    url: '/security/compliancereport/' + id,
    method: 'get'
  })
}

// 新增合规性评价报告
export function addCompliancereport(data) {
  return request({
    url: '/security/compliancereport',
    method: 'post',
    data: data
  })
}

// 修改合规性评价报告
export function updateCompliancereport(data) {
  return request({
    url: '/security/compliancereport',
    method: 'put',
    data: data
  })
}

// 删除合规性评价报告
export function delCompliancereport(id) {
  return request({
    url: '/security/compliancereport/' + id,
    method: 'delete'
  })
}
