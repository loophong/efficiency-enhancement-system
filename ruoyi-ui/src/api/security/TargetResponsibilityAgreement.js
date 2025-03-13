import request from '@/utils/request'

// 查询安全生产目标责任书列表
export function listTargetResponsibilityAgreement(query) {
  return request({
    url: '/security/TargetResponsibilityAgreement/list',
    method: 'get',
    params: query
  })
}

// 查询安全生产目标责任书详细
export function getTargetResponsibilityAgreement(id) {
  return request({
    url: '/security/TargetResponsibilityAgreement/' + id,
    method: 'get'
  })
}

// 新增安全生产目标责任书
export function addTargetResponsibilityAgreement(data) {
  return request({
    url: '/security/TargetResponsibilityAgreement',
    method: 'post',
    data: data
  })
}

// 修改安全生产目标责任书
export function updateTargetResponsibilityAgreement(data) {
  return request({
    url: '/security/TargetResponsibilityAgreement',
    method: 'put',
    data: data
  })
}

// 删除安全生产目标责任书
export function delTargetResponsibilityAgreement(id) {
  return request({
    url: '/security/TargetResponsibilityAgreement/' + id,
    method: 'delete'
  })
}
