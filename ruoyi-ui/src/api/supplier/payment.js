import request from '@/utils/request'

// 查询付款限制条件列表
export function listPayment(query) {
  return request({
    url: '/supplier/payment/list',
    method: 'get',
    params: query
  })
}

// 查询付款限制条件详细
export function getPayment(id) {
  return request({
    url: '/supplier/payment/' + id,
    method: 'get'
  })
}

// 新增付款限制条件
export function addPayment(data) {
  return request({
    url: '/supplier/payment',
    method: 'post',
    data: data
  })
}

// 修改付款限制条件
export function updatePayment(data) {
  return request({
    url: '/supplier/payment',
    method: 'put',
    data: data
  })
}

// 删除付款限制条件
export function delPayment(id) {
  return request({
    url: '/supplier/payment/' + id,
    method: 'delete'
  })
}

// 上传
export function importFile(formData) {
  return request({
    url: '/supplier/payment/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
  }
  })
}