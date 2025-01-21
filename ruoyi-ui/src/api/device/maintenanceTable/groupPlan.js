import request from '@/utils/request'

// 查询班组计划保养列表
export function listPlan(query) {
  return request({
    url: '/device/group/list',
    method: 'get',
    params: query
  })
}

// 查询班组计划保养详细
export function getPlan(groupId) {
  return request({
    url: '/device/group/' + groupId,
    method: 'get'
  })
}

// 新增班组计划保养
export function addPlan(data) {
  return request({
    url: '/device/group',
    method: 'post',
    data: data
  })
}

// 修改班组计划保养
export function updatePlan(data) {
  return request({
    url: '/device/group',
    method: 'put',
    data: data
  })
}

// 删除班组计划保养
export function delPlan(groupId) {
  return request({
    url: '/device/group/' + groupId,
    method: 'delete'
  })
}
