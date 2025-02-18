import request from '@/utils/request'

// 查询SOP文件管理列表
export function listFile(query) {
  return request({
    url: '/file/sop/list',
    method: 'get',
    params: query
  })
}

// 查询SOP文件管理详细
export function getFile(sopFileId) {
  return request({
    url: '/file/sop/' + sopFileId,
    method: 'get'
  })
}

// 新增SOP文件管理
export function addFile(data) {
  return request({
    url: '/file/sop',
    method: 'post',
    data: data
  })
}

// 修改SOP文件管理
export function updateFile(data) {
  return request({
    url: '/file/sop',
    method: 'put',
    data: data
  })
}

// 删除SOP文件管理
export function delFile(sopFileId) {
  return request({
    url: '/file/sop/' + sopFileId,
    method: 'delete'
  })
}
