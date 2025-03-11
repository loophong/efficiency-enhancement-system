import request from '@/utils/request'

// 查询职业健康防护用品台帐列表
export function listHealthprotectiveequipment(query) {
  return request({
    url: '/security/healthprotectiveequipment/list',
    method: 'get',
    params: query
  })
}

// 查询职业健康防护用品台帐详细
export function getHealthprotectiveequipment(id) {
  return request({
    url: '/security/healthprotectiveequipment/' + id,
    method: 'get'
  })
}

// 新增职业健康防护用品台帐
export function addHealthprotectiveequipment(data) {
  return request({
    url: '/security/healthprotectiveequipment',
    method: 'post',
    data: data
  })
}

// 修改职业健康防护用品台帐
export function updateHealthprotectiveequipment(data) {
  return request({
    url: '/security/healthprotectiveequipment',
    method: 'put',
    data: data
  })
}

// 删除职业健康防护用品台帐
export function delHealthprotectiveequipment(id) {
  return request({
    url: '/security/healthprotectiveequipment/' + id,
    method: 'delete'
  })
}
