import request from '@/utils/request'

// 查询环境职业健康安全管理评审会议记录列表
export function listHealthManagementMeeting(query) {
  return request({
    url: '/security/HealthManagementMeeting/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全管理评审会议记录详细
export function getHealthManagementMeeting(id) {
  return request({
    url: '/security/HealthManagementMeeting/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全管理评审会议记录
export function addHealthManagementMeeting(data) {
  return request({
    url: '/security/HealthManagementMeeting',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全管理评审会议记录
export function updateHealthManagementMeeting(data) {
  return request({
    url: '/security/HealthManagementMeeting',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全管理评审会议记录
export function delHealthManagementMeeting(id) {
  return request({
    url: '/security/HealthManagementMeeting/' + id,
    method: 'delete'
  })
}
