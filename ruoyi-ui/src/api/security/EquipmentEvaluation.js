import request from '@/utils/request'

// 查询安全设备设施现状评价列表
export function listEquipmentEvaluation(query) {
  return request({
    url: '/security/EquipmentEvaluation/list',
    method: 'get',
    params: query
  })
}

// 查询安全设备设施现状评价详细
export function getEquipmentEvaluation(id) {
  return request({
    url: '/security/EquipmentEvaluation/' + id,
    method: 'get'
  })
}

// 新增安全设备设施现状评价
export function addEquipmentEvaluation(data) {
  return request({
    url: '/security/EquipmentEvaluation',
    method: 'post',
    data: data
  })
}

// 修改安全设备设施现状评价
export function updateEquipmentEvaluation(data) {
  return request({
    url: '/security/EquipmentEvaluation',
    method: 'put',
    data: data
  })
}

// 删除安全设备设施现状评价
export function delEquipmentEvaluation(id) {
  return request({
    url: '/security/EquipmentEvaluation/' + id,
    method: 'delete'
  })
}
