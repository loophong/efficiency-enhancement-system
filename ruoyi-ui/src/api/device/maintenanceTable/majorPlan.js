import request from '@/utils/request'

// 查询专业计划保养列表
export function listPlan(query) {
  return request({
    url: '/fault/majorPlan/list',
    method: 'get',
    params: query
  })
}

// 查询专业计划保养详细
export function getPlan(majorId) {
  return request({
    url: '/fault/majorPlan/' + majorId,
    method: 'get'
  })
}

// 新增专业计划保养
export function addPlan(data) {
  return request({
    url: '/fault/majorPlan',
    method: 'post',
    data: data
  })
}

// 修改专业计划保养
export function updatePlan(data) {
  return request({
    url: '/fault/majorPlan',
    method: 'put',
    data: data
  })
}

// 删除专业计划保养
export function delPlan(majorId) {
  return request({
    url: '/fault/majorPlan/' + majorId,
    method: 'delete'
  })
}

// 专业计划保养消息列表
export function tipList() {
  return request({
    url: '/fault/majorPlan/tipList',
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