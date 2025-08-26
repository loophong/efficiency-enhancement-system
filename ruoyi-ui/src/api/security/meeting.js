import request from '@/utils/request'

// 查询安全会议记录列表
export function listMeeting(query) {
  return request({
    url: '/security/meeting/list',
    method: 'get',
    params: query
  })
}

// 查询安全会议记录详细
export function getMeeting(id) {
  return request({
    url: '/security/meeting/' + id,
    method: 'get'
  })
}

// 新增安全会议记录
export function addMeeting(data) {
  return request({
    url: '/security/meeting',
    method: 'post',
    data: data
  })
}

// 修改安全会议记录
export function updateMeeting(data) {
  return request({
    url: '/security/meeting',
    method: 'put',
    data: data
  })
}

// 删除安全会议记录
export function delMeeting(id) {
  return request({
    url: '/security/meeting/' + id,
    method: 'delete'
  })
}
