import request from '@/utils/request'

// 查询应急演练计划列表
export function listEmergencydrillplan(query) {
  return request({
    url: '/security/emergencydrillplan/list',
    method: 'get',
    params: query
  })
}

// 查询应急演练计划详细
export function getEmergencydrillplan(id) {
  return request({
    url: '/security/emergencydrillplan/' + id,
    method: 'get'
  })
}

// 新增应急演练计划
export function addEmergencydrillplan(data) {
  return request({
    url: '/security/emergencydrillplan',
    method: 'post',
    data: data
  })
}

// 修改应急演练计划
export function updateEmergencydrillplan(data) {
  return request({
    url: '/security/emergencydrillplan',
    method: 'put',
    data: data
  })
}

// 删除应急演练计划
export function delEmergencydrillplan(id) {
  return request({
    url: '/security/emergencydrillplan/' + id,
    method: 'delete'
  })
}

// 导入应急演练计划
export function importEmergencydrillplan(data) {
  return request({
    url: '/security/emergencydrillplan/importData',
    method: 'post',
    data: data
  })
}

// 下载应急演练计划导入模板
export function downloadTemplate() {
  return request({
    url: '/security/emergencydrillplan/importTemplate',
    method: 'get'
  })
}

// 根据关联ID查询应急演练计划列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/emergencydrillplan/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
