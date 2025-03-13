import request from '@/utils/request'

// 查询管理评审纠正和改进指令列表
export function listReviewInstructionForm(query) {
  return request({
    url: '/security/ReviewInstructionForm/list',
    method: 'get',
    params: query
  })
}

// 查询管理评审纠正和改进指令详细
export function getReviewInstructionForm(id) {
  return request({
    url: '/security/ReviewInstructionForm/' + id,
    method: 'get'
  })
}

// 新增管理评审纠正和改进指令
export function addReviewInstructionForm(data) {
  return request({
    url: '/security/ReviewInstructionForm',
    method: 'post',
    data: data
  })
}

// 修改管理评审纠正和改进指令
export function updateReviewInstructionForm(data) {
  return request({
    url: '/security/ReviewInstructionForm',
    method: 'put',
    data: data
  })
}

// 删除管理评审纠正和改进指令
export function delReviewInstructionForm(id) {
  return request({
    url: '/security/ReviewInstructionForm/' + id,
    method: 'delete'
  })
}
