import request from '@/utils/request'

// 查询设备设施安全操作规程列表
export function listUploadfileJob(query) {
  return request({
    url: '/security/UploadfileJob/list',
    method: 'get',
    params: query
  })
}

// 查询设备设施安全操作规程详细
export function getUploadfileJob(id) {
  return request({
    url: '/security/UploadfileJob/' + id,
    method: 'get'
  })
}

// 新增设备设施安全操作规程
export function addUploadfileJob(data) {
  return request({
    url: '/security/UploadfileJob',
    method: 'post',
    data: data
  })
}

// 修改设备设施安全操作规程
export function updateUploadfileJob(data) {
  return request({
    url: '/security/UploadfileJob',
    method: 'put',
    data: data
  })
}

// 删除设备设施安全操作规程
export function delUploadfileJob(id) {
  return request({
    url: '/security/UploadfileJob/' + id,
    method: 'delete'
  })
}
