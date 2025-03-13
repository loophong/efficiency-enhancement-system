import request from '@/utils/request'

// 查询职业危害作业点检测报告列表
export function listOperationPointTestReport(query) {
  return request({
    url: '/security/OperationPointTestReport/list',
    method: 'get',
    params: query
  })
}

// 查询职业危害作业点检测报告详细
export function getOperationPointTestReport(id) {
  return request({
    url: '/security/OperationPointTestReport/' + id,
    method: 'get'
  })
}

// 新增职业危害作业点检测报告
export function addOperationPointTestReport(data) {
  return request({
    url: '/security/OperationPointTestReport',
    method: 'post',
    data: data
  })
}

// 修改职业危害作业点检测报告
export function updateOperationPointTestReport(data) {
  return request({
    url: '/security/OperationPointTestReport',
    method: 'put',
    data: data
  })
}

// 删除职业危害作业点检测报告
export function delOperationPointTestReport(id) {
  return request({
    url: '/security/OperationPointTestReport/' + id,
    method: 'delete'
  })
}
