import request from '@/utils/request'

// 查询相关方安全教育培训及考试列表
export function listSafetyTrainingExam(query) {
  return request({
    url: '/security/SafetyTrainingExam/list',
    method: 'get',
    params: query
  })
}

// 查询相关方安全教育培训及考试详细
export function getSafetyTrainingExam(id) {
  return request({
    url: '/security/SafetyTrainingExam/' + id,
    method: 'get'
  })
}

// 新增相关方安全教育培训及考试
export function addSafetyTrainingExam(data) {
  return request({
    url: '/security/SafetyTrainingExam',
    method: 'post',
    data: data
  })
}

// 修改相关方安全教育培训及考试
export function updateSafetyTrainingExam(data) {
  return request({
    url: '/security/SafetyTrainingExam',
    method: 'put',
    data: data
  })
}

// 删除相关方安全教育培训及考试
export function delSafetyTrainingExam(id) {
  return request({
    url: '/security/SafetyTrainingExam/' + id,
    method: 'delete'
  })
}
