import request from '@/utils/request'

// 查询产品技术问题整改及时性列表
export function listTimeliness(query) {
  return request({
    url: '/supplier/timeliness/list',
    method: 'get',
    params: query
  })
}

// 查询产品技术问题整改及时性详细
export function getTimeliness(id) {
  return request({
    url: '/supplier/timeliness/' + id,
    method: 'get'
  })
}

// 新增产品技术问题整改及时性
export function addTimeliness(data) {
  return request({
    url: '/supplier/timeliness',
    method: 'post',
    data: data
  })
}

// 修改产品技术问题整改及时性
export function updateTimeliness(data) {
  return request({
    url: '/supplier/timeliness',
    method: 'put',
    data: data
  })
}

// 删除产品技术问题整改及时性
export function delTimeliness(id) {
  return request({
    url: '/supplier/timeliness/' + id,
    method: 'delete'
  })
}
