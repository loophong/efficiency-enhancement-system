import request from '@/utils/request'

// 查询应急预案演练列表
export function listEmergencyPlanDrill(query) {
  return request({
    url: '/security/EmergencyPlanDrill/list',
    method: 'get',
    params: query
  })
}

// 查询应急预案演练详细
export function getEmergencyPlanDrill(id) {
  return request({
    url: '/security/EmergencyPlanDrill/' + id,
    method: 'get'
  })
}

// 新增应急预案演练
export function addEmergencyPlanDrill(data) {
  return request({
    url: '/security/EmergencyPlanDrill',
    method: 'post',
    data: data
  })
}

// 修改应急预案演练
export function updateEmergencyPlanDrill(data) {
  return request({
    url: '/security/EmergencyPlanDrill',
    method: 'put',
    data: data
  })
}

// 删除应急预案演练
export function delEmergencyPlanDrill(id) {
  return request({
    url: '/security/EmergencyPlanDrill/' + id,
    method: 'delete'
  })
}
