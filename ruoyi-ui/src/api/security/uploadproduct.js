import request from '@/utils/request'

// 查询安全生产责任制列表
export function listUploadproduct(query) {
  return request({
    url: '/security/uploadproduct/list',
    method: 'get',
    params: query
  })
}

// 查询安全生产责任制详细
export function getUploadproduct(id) {
  return request({
    url: '/security/uploadproduct/' + id,
    method: 'get'
  })
}

// 新增安全生产责任制
export function addUploadproduct(data) {
  return request({
    url: '/security/uploadproduct',
    method: 'post',
    data: data
  })
}

// 修改安全生产责任制
export function updateUploadproduct(data) {
  return request({
    url: '/security/uploadproduct',
    method: 'put',
    data: data
  })
}

// 删除安全生产责任制
export function delUploadproduct(id) {
  return request({
    url: '/security/uploadproduct/' + id,
    method: 'delete'
  })
}
