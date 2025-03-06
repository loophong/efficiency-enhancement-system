import request from '@/utils/request'

// 查询供应商列表
export function listQualified(query) {
  return request({
    url: '/supplier/qualified/list',
    method: 'get',
    params: query
  })
}

// 查询供应商详细
export function getQualified(id) {
  return request({
    url: '/supplier/qualified/' + id,
    method: 'get'
  })
}

// 新增供应商
export function addQualified(data) {
  return request({
    url: '/supplier/qualified',
    method: 'post',
    data: data
  })
}

// 修改供应商
export function updateQualified(data) {
  return request({
    url: '/supplier/qualified',
    method: 'put',
    data: data
  })
}

// 删除供应商
export function delQualified(id) {
  return request({
    url: '/supplier/qualified/' + id,
    method: 'delete'
  })
}

// 上传主计划表
export function importFile(formData) {
  return request({
    url: '/supplier/qualified/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

export function all() {
  return request({
    url: '/supplier/qualified/all',
    method: 'get'
  })
}
