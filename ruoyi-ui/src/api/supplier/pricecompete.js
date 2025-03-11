import request from '@/utils/request'

// 查询价格竞争力列表
export function listPricecompete(query) {
  return request({
    url: '/supplier/pricecompete/list',
    method: 'get',
    params: query
  })
}

// 查询价格竞争力详细
export function getPricecompete(id) {
  return request({
    url: '/supplier/pricecompete/' + id,
    method: 'get'
  })
}

// 新增价格竞争力
export function addPricecompete(data) {
  return request({
    url: '/supplier/pricecompete',
    method: 'post',
    data: data
  })
}

// 修改价格竞争力
export function updatePricecompete(data) {
  return request({
    url: '/supplier/pricecompete',
    method: 'put',
    data: data
  })
}

// 删除价格竞争力
export function delPricecompete(id) {
  return request({
    url: '/supplier/pricecompete/' + id,
    method: 'delete'
  })
}
