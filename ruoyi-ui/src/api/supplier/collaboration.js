import request from '@/utils/request'

// 查询服务与协作列表
export function listCollaboration(query) {
  return request({
    url: '/supplier/collaboration/list',
    method: 'get',
    params: query
  })
}

// 查询服务与协作详细
export function getCollaboration(id) {
  return request({
    url: '/supplier/collaboration/' + id,
    method: 'get'
  })
}

// 新增服务与协作
export function addCollaboration(data) {
  return request({
    url: '/supplier/collaboration',
    method: 'post',
    data: data
  })
}

// 修改服务与协作
export function updateCollaboration(data) {
  return request({
    url: '/supplier/collaboration',
    method: 'put',
    data: data
  })
}

// 删除服务与协作
export function delCollaboration(id) {
  return request({
    url: '/supplier/collaboration/' + id,
    method: 'delete'
  })
}
// 上传
export function importFile(formData) {
  return request({
    url: '/supplier/collaboration/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
  }
  })
}