import request from '@/utils/request'

// 查询纠正与预防措施审批实施列表
export function listCorrectiveMeasuresApproval(query) {
  return request({
    url: '/security/CorrectiveMeasuresApproval/list',
    method: 'get',
    params: query
  })
}

// 查询纠正与预防措施审批实施详细
export function getCorrectiveMeasuresApproval(id) {
  return request({
    url: '/security/CorrectiveMeasuresApproval/' + id,
    method: 'get'
  })
}

// 新增纠正与预防措施审批实施
export function addCorrectiveMeasuresApproval(data) {
  return request({
    url: '/security/CorrectiveMeasuresApproval',
    method: 'post',
    data: data
  })
}

// 修改纠正与预防措施审批实施
export function updateCorrectiveMeasuresApproval(data) {
  return request({
    url: '/security/CorrectiveMeasuresApproval',
    method: 'put',
    data: data
  })
}

// 删除纠正与预防措施审批实施
export function delCorrectiveMeasuresApproval(id) {
  return request({
    url: '/security/CorrectiveMeasuresApproval/' + id,
    method: 'delete'
  })
}
