import request from '@/utils/request'

// 查询危化品检查记录列表
export function listHazardousinspection(query) {
  return request({
    url: '/security/hazardousinspection/list',
    method: 'get',
    params: query
  })
}

// 查询危化品检查记录详细
export function getHazardousinspection(id) {
  return request({
    url: '/security/hazardousinspection/' + id,
    method: 'get'
  })
}

// 新增危化品检查记录
export function addHazardousinspection(data) {
  return request({
    url: '/security/hazardousinspection',
    method: 'post',
    data: data
  })
}

// 修改危化品检查记录
export function updateHazardousinspection(data) {
  return request({
    url: '/security/hazardousinspection',
    method: 'put',
    data: data
  })
}

// 删除危化品检查记录
export function delHazardousinspection(id) {
  return request({
    url: '/security/hazardousinspection/' + id,
    method: 'delete'
  })
}
