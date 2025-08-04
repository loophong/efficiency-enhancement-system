import request from '@/utils/request'

// 查询风险和机遇评应对措施评审记录2列表
export function listCopy222(query) {
  return request({
    url: '/security/copy222/list',
    method: 'get',
    params: query
  })
}

// 查询风险和机遇评应对措施评审记录2详细
export function getCopy222(id) {
  return request({
    url: '/security/copy222/' + id,
    method: 'get'
  })
}

// 新增风险和机遇评应对措施评审记录2
export function addCopy222(data) {
  return request({
    url: '/security/copy222',
    method: 'post',
    data: data
  })
}

// 修改风险和机遇评应对措施评审记录2
export function updateCopy222(data) {
  return request({
    url: '/security/copy222',
    method: 'put',
    data: data
  })
}

// 删除风险和机遇评应对措施评审记录2
export function delCopy222(id) {
  return request({
    url: '/security/copy222/' + id,
    method: 'delete'
  })
}
