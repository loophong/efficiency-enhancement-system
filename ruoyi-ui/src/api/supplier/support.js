import request from '@/utils/request'

// 查询降本支持列表
export function listSupport(query) {
  return request({
    url: '/supplier/support/list',
    method: 'get',
    params: query
  })
}

// 查询降本支持详细
export function getSupport(id) {
  return request({
    url: '/supplier/support/' + id,
    method: 'get'
  })
}

// 新增降本支持
export function addSupport(data) {
  return request({
    url: '/supplier/support',
    method: 'post',
    data: data
  })
}

// 修改降本支持
export function updateSupport(data) {
  return request({
    url: '/supplier/support',
    method: 'put',
    data: data
  })
}

// 删除降本支持
export function delSupport(id) {
  return request({
    url: '/supplier/support/' + id,
    method: 'delete'
  })
}
