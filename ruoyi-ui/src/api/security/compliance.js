import request from '@/utils/request'

// 查询合规性评价记录列表
export function listCompliance(query) {
  return request({
    url: '/security/compliance/list',
    method: 'get',
    params: query
  })
}

// 查询合规性评价记录详细
export function getCompliance(id) {
  return request({
    url: '/security/compliance/' + id,
    method: 'get'
  })
}

// 新增合规性评价记录
export function addCompliance(data) {
  return request({
    url: '/security/compliance',
    method: 'post',
    data: data
  })
}

// 修改合规性评价记录
export function updateCompliance(data) {
  return request({
    url: '/security/compliance',
    method: 'put',
    data: data
  })
}

// 删除合规性评价记录
export function delCompliance(id) {
  return request({
    url: '/security/compliance/' + id,
    method: 'delete'
  })
}
