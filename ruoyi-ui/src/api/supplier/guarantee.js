import request from '@/utils/request'

// 查询供货保障列表
export function listGuarantee(query) {
  return request({
    url: '/supplier/guarantee/list',
    method: 'get',
    params: query
  })
}

// 查询供货保障详细
export function getGuarantee(id) {
  return request({
    url: '/supplier/guarantee/' + id,
    method: 'get'
  })
}

// 新增供货保障
export function addGuarantee(data) {
  return request({
    url: '/supplier/guarantee',
    method: 'post',
    data: data
  })
}

// 修改供货保障
export function updateGuarantee(data) {
  return request({
    url: '/supplier/guarantee',
    method: 'put',
    data: data
  })
}

// 删除供货保障
export function delGuarantee(id) {
  return request({
    url: '/supplier/guarantee/' + id,
    method: 'delete'
  })
}
// 上传
export function importFile(formData) {
  return request({
    url: '/supplier/guarantee/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}