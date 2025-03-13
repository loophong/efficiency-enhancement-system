import request from '@/utils/request'

// 查询事故处理报告列表
export function listAccidentReport(query) {
  return request({
    url: '/security/AccidentReport/list',
    method: 'get',
    params: query
  })
}

// 查询事故处理报告详细
export function getAccidentReport(id) {
  return request({
    url: '/security/AccidentReport/' + id,
    method: 'get'
  })
}

// 新增事故处理报告
export function addAccidentReport(data) {
  return request({
    url: '/security/AccidentReport',
    method: 'post',
    data: data
  })
}

// 修改事故处理报告
export function updateAccidentReport(data) {
  return request({
    url: '/security/AccidentReport',
    method: 'put',
    data: data
  })
}

// 删除事故处理报告
export function delAccidentReport(id) {
  return request({
    url: '/security/AccidentReport/' + id,
    method: 'delete'
  })
}
