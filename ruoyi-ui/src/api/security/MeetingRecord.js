import request from '@/utils/request'

// 查询安全会议记录列表
export function listMeetingRecord(query) {
  return request({
    url: '/security/MeetingRecord/list',
    method: 'get',
    params: query
  })
}

// 查询安全会议记录详细
export function getMeetingRecord(id) {
  return request({
    url: '/security/MeetingRecord/' + id,
    method: 'get'
  })
}

// 新增安全会议记录
export function addMeetingRecord(data) {
  return request({
    url: '/security/MeetingRecord',
    method: 'post',
    data: data
  })
}

// 修改安全会议记录
export function updateMeetingRecord(data) {
  return request({
    url: '/security/MeetingRecord',
    method: 'put',
    data: data
  })
}

// 删除安全会议记录
export function delMeetingRecord(id) {
  return request({
    url: '/security/MeetingRecord/' + id,
    method: 'delete'
  })
}
