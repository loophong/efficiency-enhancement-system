import request from '@/utils/request'

// 查询季节性检查记录列表
export function listSeasonList(query) {
  return request({
    url: '/security/SeasonList/list',
    method: 'get',
    params: query
  })
}

// 查询季节性检查记录详细
export function getSeasonList(id) {
  return request({
    url: '/security/SeasonList/' + id,
    method: 'get'
  })
}

// 新增季节性检查记录
export function addSeasonList(data) {
  return request({
    url: '/security/SeasonList',
    method: 'post',
    data: data
  })
}

// 修改季节性检查记录
export function updateSeasonList(data) {
  return request({
    url: '/security/SeasonList',
    method: 'put',
    data: data
  })
}

// 删除季节性检查记录
export function delSeasonList(id) {
  return request({
    url: '/security/SeasonList/' + id,
    method: 'delete'
  })
}
