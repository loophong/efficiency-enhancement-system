import request from '@/utils/request'

// 查询记录列表
export function listRecord(query) {
  return request({
    url: '/fault/record/list',
    method: 'get',
    params: query
  })
}

// 查询记录详细
export function getRecord(maintenanceId) {
  return request({
    url: '/fault/record/' + maintenanceId,
    method: 'get'
  })
}

// 新增记录
export function addRecord(data) {
  return request({
    url: '/fault/record',
    method: 'post',
    data: data
  })
}

// 修改记录
export function updateRecord(data) {
  return request({
    url: '/fault/record',
    method: 'put',
    data: data
  })
}

// 删除记录
export function delRecord(maintenanceId) {
  return request({
    url: '/fault/record/' + maintenanceId,
    method: 'delete'
  })
}
