import request from '@/utils/request'

// 查询计划会签列表
export function listPlanCoSign(query) {
  return request({
    url: '/production/planCoSign/list',
    method: 'get',
    params: query
  })
}

// 查询计划会签详细
export function getPlanCoSign(id) {
  return request({
    url: '/production/planCoSign/' + id,
    method: 'get'
  })
}


// 新增计划会签
export function addPlanCoSign(data) {
  return request({
    url: '/production/planCoSign',
    method: 'post',
    data: data
  })
}

// 修改计划会签
export function updatePlanCoSign(data) {
  return request({
    url: '/production/planCoSign',
    method: 'put',
    data: data
  })
}

// 删除计划会签
export function delPlanCoSign(id) {
  return request({
    url: '/production/planCoSign/' + id,
    method: 'delete'
  })
}


// 查询计划会签详细
export function getCoSignAndDailyPlan(id) {
  return request({
    url: '/production/planCoSign/info/' + id,
    method: 'get'
  })
}
