import request from '@/utils/request'

// 查询日生产计划列表
export function listDailyPlan(query) {
  return request({
    url: '/production/dailyPlan/list',
    method: 'get',
    params: query
  })
}

// 查询日生产计划详细
export function getDailyPlan(id) {
  return request({
    url: '/production/dailyPlan/' + id,
    method: 'get'
  })
}

// 新增日生产计划
export function addDailyPlan(data) {
  return request({
    url: '/production/dailyPlan',
    method: 'post',
    data: data
  })
}

// 修改日生产计划
export function updateDailyPlan(data) {
  return request({
    url: '/production/dailyPlan',
    method: 'put',
    data: data
  })
}

// 删除日生产计划
export function delDailyPlan(id) {
  return request({
    url: '/production/dailyPlan/' + id,
    method: 'delete'
  })
}
