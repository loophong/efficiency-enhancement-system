import request from '@/utils/request'

// 查询售后返修率列表
export function listReturnrate(query) {
  return request({
    url: '/supplier/returnrate/list',
    method: 'get',
    params: query
  })
}

// 查询售后返修率详细
export function getReturnrate(id) {
  return request({
    url: '/supplier/returnrate/' + id,
    method: 'get'
  })
}

// 新增售后返修率
export function addReturnrate(data) {
  return request({
    url: '/supplier/returnrate',
    method: 'post',
    data: data
  })
}

// 修改售后返修率
export function updateReturnrate(data) {
  return request({
    url: '/supplier/returnrate',
    method: 'put',
    data: data
  })
}

// 删除售后返修率
export function delReturnrate(id) {
  return request({
    url: '/supplier/returnrate/' + id,
    method: 'delete'
  })
}

// 上传
export function importFile(formData) {
  return request({
    url: '/supplier/returnrate/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
  }
  })
}

