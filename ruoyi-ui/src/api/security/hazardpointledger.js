import request from '@/utils/request'

// 查询有点害台账列表
export function listHazardpointledger(query) {
  return request({
    url: '/security/hazardpointledger/list',
    method: 'get',
    params: query
  })
}

// 查询有点害台账详细
export function getHazardpointledger(id) {
  return request({
    url: '/security/hazardpointledger/' + id,
    method: 'get'
  })
}

// 新增有点害台账
export function addHazardpointledger(data) {
  return request({
    url: '/security/hazardpointledger',
    method: 'post',
    data: data
  })
}

// 修改有点害台账
export function updateHazardpointledger(data) {
  return request({
    url: '/security/hazardpointledger',
    method: 'put',
    data: data
  })
}

// 删除有点害台账
export function delHazardpointledger(id) {
  return request({
    url: '/security/hazardpointledger/' + id,
    method: 'delete'
  })
}
