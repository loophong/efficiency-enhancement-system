import request from '@/utils/request'

// 查询危险源清单列表
export function listRisklist(query) {
  return request({
    url: '/security/risklist/list',
    method: 'get',
    params: query
  })
}

// 查询危险源清单详细
export function getRisklist(id) {
  return request({
    url: '/security/risklist/' + id,
    method: 'get'
  })
}

// 新增危险源清单
export function addRisklist(data) {
  return request({
    url: '/security/risklist',
    method: 'post',
    data: data
  })
}

// 修改危险源清单
export function updateRisklist(data) {
  return request({
    url: '/security/risklist',
    method: 'put',
    data: data
  })
}

// 删除危险源清单
export function delRisklist(id) {
  return request({
    url: '/security/risklist/' + id,
    method: 'delete'
  })
}
