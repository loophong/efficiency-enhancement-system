import request from '@/utils/request'

// 查询整机扫码记录列表
export function listScanRecords(query) {
  return request({
    url: '/production/scanRecords/list',
    method: 'get',
    params: query
  })
}

// 查询整机扫码记录详细
export function getScanRecords(id) {
  return request({
    url: '/production/scanRecords/' + id,
    method: 'get'
  })
}

// 新增整机扫码记录
export function addScanRecords(data) {
  return request({
    url: '/production/scanRecords',
    method: 'post',
    data: data
  })
}

// 修改整机扫码记录
export function updateScanRecords(data) {
  return request({
    url: '/production/scanRecords',
    method: 'put',
    data: data
  })
}

// 删除整机扫码记录
export function delScanRecords(id) {
  return request({
    url: '/production/scanRecords/' + id,
    method: 'delete'
  })
}


// 上传整机扫码记录表
export function importFile(formData) {
  return request({
    url: '/production/scanRecords/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
