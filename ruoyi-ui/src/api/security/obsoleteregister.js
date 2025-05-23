import request from '@/utils/request'

// 查询作废受控文件收回销毁登记列表
export function listObsoleteregister(query) {
  return request({
    url: '/security/obsoleteregister/list',
    method: 'get',
    params: query
  })
}

// 查询作废受控文件收回销毁登记详细
export function getObsoleteregister(id) {
  return request({
    url: '/security/obsoleteregister/' + id,
    method: 'get'
  })
}

// 新增作废受控文件收回销毁登记
export function addObsoleteregister(data) {
  return request({
    url: '/security/obsoleteregister',
    method: 'post',
    data: data
  })
}

// 修改作废受控文件收回销毁登记
export function updateObsoleteregister(data) {
  return request({
    url: '/security/obsoleteregister',
    method: 'put',
    data: data
  })
}

// 删除作废受控文件收回销毁登记
export function delObsoleteregister(id) {
  return request({
    url: '/security/obsoleteregister/' + id,
    method: 'delete'
  })
}

// 导入作废受控文件收回销毁登记
export function importFile(formData) {
  return request({
    url: '/security/obsoleteregister/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type':'multipart/form-data'
    }
  })
}
