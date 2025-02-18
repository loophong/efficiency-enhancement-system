import request from '@/utils/request'

// 查询设备基础档案列表
export function listFile(query) {
  return request({
    url: '/file/basic/list',
    method: 'get',
    params: query
  })
}

// 查询设备基础档案详细
export function getFile(basicFileId) {
  return request({
    url: '/file/basic/' + basicFileId,
    method: 'get'
  })
}

// 新增设备基础档案
export function addFile(data) {
  return request({
    url: '/file/basic',
    method: 'post',
    data: data
  })
}

// 修改设备基础档案
export function updateFile(data) {
  return request({
    url: '/file/basic',
    method: 'put',
    data: data
  })
}

// 删除设备基础档案
export function delFile(basicFileId) {
  return request({
    url: '/file/basic/' + basicFileId,
    method: 'delete'
  })
}
