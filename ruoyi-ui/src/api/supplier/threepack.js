import request from '@/utils/request'

// 查询三包发货及时率列表
export function listThreepack(query) {
  return request({
    url: '/supplier/threepack/list',
    method: 'get',
    params: query
  })
}

// 查询三包发货及时率详细
export function getThreepack(id) {
  return request({
    url: '/supplier/threepack/' + id,
    method: 'get'
  })
}

// 新增三包发货及时率
export function addThreepack(data) {
  return request({
    url: '/supplier/threepack',
    method: 'post',
    data: data
  })
}

// 修改三包发货及时率
export function updateThreepack(data) {
  return request({
    url: '/supplier/threepack',
    method: 'put',
    data: data
  })
}

// 删除三包发货及时率
export function delThreepack(id) {
  return request({
    url: '/supplier/threepack/' + id,
    method: 'delete'
  })
}
