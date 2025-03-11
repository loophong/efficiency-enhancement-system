import request from '@/utils/request'

// 查询价格诚信列表
export function listPricetrust(query) {
  return request({
    url: '/supplier/pricetrust/list',
    method: 'get',
    params: query
  })
}

// 查询价格诚信详细
export function getPricetrust(id) {
  return request({
    url: '/supplier/pricetrust/' + id,
    method: 'get'
  })
}

// 新增价格诚信
export function addPricetrust(data) {
  return request({
    url: '/supplier/pricetrust',
    method: 'post',
    data: data
  })
}

// 修改价格诚信
export function updatePricetrust(data) {
  return request({
    url: '/supplier/pricetrust',
    method: 'put',
    data: data
  })
}

// 删除价格诚信
export function delPricetrust(id) {
  return request({
    url: '/supplier/pricetrust/' + id,
    method: 'delete'
  })
}
