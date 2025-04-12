import request from '@/utils/request'

// 查询受控文件发放目录列表
export function listControlleddirectory(query) {
  return request({
    url: '/security/controlleddirectory/list',
    method: 'get',
    params: query
  })
}

// 查询受控文件发放目录详细
export function getControlleddirectory(id) {
  return request({
    url: '/security/controlleddirectory/' + id,
    method: 'get'
  })
}

// 新增受控文件发放目录
export function addControlleddirectory(data) {
  return request({
    url: '/security/controlleddirectory',
    method: 'post',
    data: data
  })
}

// 修改受控文件发放目录
export function updateControlleddirectory(data) {
  return request({
    url: '/security/controlleddirectory',
    method: 'put',
    data: data
  })
}

// 删除受控文件发放目录
export function delControlleddirectory(id) {
  return request({
    url: '/security/controlleddirectory/' + id,
    method: 'delete'
  })
}

// 导入受控文件发放目录
export function importFile(formData) {
  return request({
    url: '/security/controlleddirectory/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type':'multipart/form-data'
    }
  })
}
