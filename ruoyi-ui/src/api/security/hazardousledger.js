import request from '@/utils/request'

// 查询危化品处理台账列表
export function listHazardousledger(query) {
  return request({
    url: '/security/hazardousledger/list',
    method: 'get',
    params: query
  })
}

// 查询危化品处理台账详细
export function getHazardousledger(id) {
  return request({
    url: '/security/hazardousledger/' + id,
    method: 'get'
  })
}

// 新增危化品处理台账
export function addHazardousledger(data) {
  return request({
    url: '/security/hazardousledger',
    method: 'post',
    data: data
  })
}

// 修改危化品处理台账
export function updateHazardousledger(data) {
  return request({
    url: '/security/hazardousledger',
    method: 'put',
    data: data
  })
}

// 删除危化品处理台账
export function delHazardousledger(id) {
  return request({
    url: '/security/hazardousledger/' + id,
    method: 'delete'
  })
}
