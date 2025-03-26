import request from '@/utils/request'

// 查询文件预览列表
export function listUploadfile(query) {
  return request({
    url: '/security/uploadfile/list',
    method: 'get',
    params: query
  })
}

// 查询文件预览详细
export function getUploadfile(id) {
  return request({
    url: '/security/uploadfile/' + id,
    method: 'get'
  })
}

// 新增文件预览
export function addUploadfile(data) {
  return request({
    url: '/security/uploadfile',
    method: 'post',
    data: data
  })
}

// 修改文件预览
export function updateUploadfile(data) {
  return request({
    url: '/security/uploadfile',
    method: 'put',
    data: data
  })
}

// 删除文件预览
export function delUploadfile(id) {
  return request({
    url: '/security/uploadfile/' + id,
    method: 'delete'
  })
}
