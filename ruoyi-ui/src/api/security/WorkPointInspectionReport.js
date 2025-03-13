import request from '@/utils/request'

// 查询职业危害作业点检测报告列表
export function listWorkPointInspectionReport(query) {
  return request({
    url: '/security/WorkPointInspectionReport/list',
    method: 'get',
    params: query
  })
}

// 查询职业危害作业点检测报告详细
export function getWorkPointInspectionReport(id) {
  return request({
    url: '/security/WorkPointInspectionReport/' + id,
    method: 'get'
  })
}

// 新增职业危害作业点检测报告
export function addWorkPointInspectionReport(data) {
  return request({
    url: '/security/WorkPointInspectionReport',
    method: 'post',
    data: data
  })
}

// 修改职业危害作业点检测报告
export function updateWorkPointInspectionReport(data) {
  return request({
    url: '/security/WorkPointInspectionReport',
    method: 'put',
    data: data
  })
}

// 删除职业危害作业点检测报告
export function delWorkPointInspectionReport(id) {
  return request({
    url: '/security/WorkPointInspectionReport/' + id,
    method: 'delete'
  })
}
