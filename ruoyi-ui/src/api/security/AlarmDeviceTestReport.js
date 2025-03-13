import request from '@/utils/request'

// 查询监测报警装置检测报告列表
export function listAlarmDeviceTestReport(query) {
  return request({
    url: '/security/AlarmDeviceTestReport/list',
    method: 'get',
    params: query
  })
}

// 查询监测报警装置检测报告详细
export function getAlarmDeviceTestReport(id) {
  return request({
    url: '/security/AlarmDeviceTestReport/' + id,
    method: 'get'
  })
}

// 新增监测报警装置检测报告
export function addAlarmDeviceTestReport(data) {
  return request({
    url: '/security/AlarmDeviceTestReport',
    method: 'post',
    data: data
  })
}

// 修改监测报警装置检测报告
export function updateAlarmDeviceTestReport(data) {
  return request({
    url: '/security/AlarmDeviceTestReport',
    method: 'put',
    data: data
  })
}

// 删除监测报警装置检测报告
export function delAlarmDeviceTestReport(id) {
  return request({
    url: '/security/AlarmDeviceTestReport/' + id,
    method: 'delete'
  })
}
