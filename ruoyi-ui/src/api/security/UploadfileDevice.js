import request from '@/utils/request'

// 查询岗位安全操作规程列表
export function listUploadfileDevice(query) {
  return request({
    url: '/security/UploadfileDevice/list',
    method: 'get',
    params: query
  })
}

// 查询岗位安全操作规程详细
export function getUploadfileDevice(id) {
  return request({
    url: '/security/UploadfileDevice/' + id,
    method: 'get'
  })
}

// 新增岗位安全操作规程
export function addUploadfileDevice(data) {
  return request({
    url: '/security/UploadfileDevice',
    method: 'post',
    data: data
  })
}

// 修改岗位安全操作规程
export function updateUploadfileDevice(data) {
  return request({
    url: '/security/UploadfileDevice',
    method: 'put',
    data: data
  })
}

// 删除岗位安全操作规程
export function delUploadfileDevice(id) {
  return request({
    url: '/security/UploadfileDevice/' + id,
    method: 'delete'
  })
}
