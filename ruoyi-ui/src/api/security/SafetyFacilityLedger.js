import request from '@/utils/request'

// 查询安全防护设备设施列表
export function listSafetyFacilityLedger(query) {
  return request({
    url: '/security/SafetyFacilityLedger/list',
    method: 'get',
    params: query
  })
}

// 查询安全防护设备设施详细
export function getSafetyFacilityLedger(id) {
  return request({
    url: '/security/SafetyFacilityLedger/' + id,
    method: 'get'
  })
}

// 新增安全防护设备设施
export function addSafetyFacilityLedger(data) {
  return request({
    url: '/security/SafetyFacilityLedger',
    method: 'post',
    data: data
  })
}

// 修改安全防护设备设施
export function updateSafetyFacilityLedger(data) {
  return request({
    url: '/security/SafetyFacilityLedger',
    method: 'put',
    data: data
  })
}

// 删除安全防护设备设施
export function delSafetyFacilityLedger(id) {
  return request({
    url: '/security/SafetyFacilityLedger/' + id,
    method: 'delete'
  })
}
