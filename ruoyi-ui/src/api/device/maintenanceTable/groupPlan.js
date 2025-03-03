import request from '@/utils/request'

// 查询班组计划保养列表
export function listPlan(query) {
  return request({
    url: '/fault/groupPlan/list',
    method: 'get',
    params: query
  })
}

// 查询班组计划保养详细
export function getPlan(groupId) {
  return request({
    url: '/fault/groupPlan/' + groupId,
    method: 'get'
  })
}

// 新增班组计划保养
export function addPlan(data) {
  return request({
    url: '/fault/groupPlan',
    method: 'post',
    data: data
  })
}

// 修改班组计划保养
export function updatePlan(data) {
  return request({
    url: '/fault/groupPlan',
    method: 'put',
    data: data
  })
}

// 删除班组计划保养
export function delPlan(groupId) {
  return request({
    url: '/fault/groupPlan/' + groupId,
    method: 'delete'
  })
}

export function uploadFile(data, aimUrl) {
  return request({
    url: aimUrl,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
    });
}