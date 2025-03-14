import request from '@/utils/request'

// 查询质量通知单列表
export function listHappen(query) {
  return request({
    url: '/supplier/happen/list',
    method: 'get',
    params: query
  })
}

// 查询质量通知单详细
export function getHappen(id) {
  return request({
    url: '/supplier/happen/' + id,
    method: 'get'
  })
}

// 新增质量通知单
export function addHappen(data) {
  return request({
    url: '/supplier/happen',
    method: 'post',
    data: data
  })
}

// 修改质量通知单
export function updateHappen(data) {
  return request({
    url: '/supplier/happen',
    method: 'put',
    data: data
  })
}

// 删除质量通知单
export function delHappen(id) {
  return request({
    url: '/supplier/happen/' + id,
    method: 'delete'
  })
}
