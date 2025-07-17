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

// 轮换班组计划保养
export function resetPlan() {
  return request({
    url: '/fault/groupPlan/reset',
    method: 'post',
  })
}

// 更新提醒设置
export function updateMessage(data) {
  return request({
    url: '/fault/groupPlan/updateMessage',
    method: 'post',
    data: { messageSet: data }  
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

// 班组计划保养消息列表
export function tipList() {
  return request({
    url: '/fault/groupPlan/tipList',
    method: 'get'
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