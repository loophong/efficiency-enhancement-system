import request from '@/utils/request'

// 查询制度明细列表
export function listFileList(query) {
  return request({
    url: '/security/fileList/list',
    method: 'get',
    params: query
  })
}

// 查询制度明细详细
export function getFileList(id) {
  return request({
    url: '/security/fileList/' + id,
    method: 'get'
  })
}

// 新增制度明细
export function addFileList(data) {
  return request({
    url: '/security/fileList',
    method: 'post',
    data: data
  })
}

// 修改制度明细
export function updateFileList(data) {
  return request({
    url: '/security/fileList',
    method: 'put',
    data: data
  })
}

// 删除制度明细
export function delFileList(id) {
  return request({
    url: '/security/fileList/' + id,
    method: 'delete'
  })
}
