import request from '@/utils/request'

// 查询节假日检查记录列表
export function listWeekendList(query) {
  return request({
    url: '/security/WeekendList/list',
    method: 'get',
    params: query
  })
}

// 查询节假日检查记录详细
export function getWeekendList(id) {
  return request({
    url: '/security/WeekendList/' + id,
    method: 'get'
  })
}

// 新增节假日检查记录
export function addWeekendList(data) {
  return request({
    url: '/security/WeekendList',
    method: 'post',
    data: data
  })
}

// 修改节假日检查记录
export function updateWeekendList(data) {
  return request({
    url: '/security/WeekendList',
    method: 'put',
    data: data
  })
}

// 删除节假日检查记录
export function delWeekendList(id) {
  return request({
    url: '/security/WeekendList/' + id,
    method: 'delete'
  })
}
