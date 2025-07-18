import request from '@/utils/request'

// 查询特种设备台账列表
export function listSpecialoperationscertificate(query) {
  return request({
    url: '/security/specialoperationscertificate/list',
    method: 'get',
    params: query
  })
}

// 查询特种设备台账详细
export function getSpecialoperationscertificate(id) {
  return request({
    url: '/security/specialoperationscertificate/' + id,
    method: 'get'
  })
}

// 新增特种设备台账
export function addSpecialoperationscertificate(data) {
  return request({
    url: '/security/specialoperationscertificate',
    method: 'post',
    data: data
  })
}

// 修改特种设备台账
export function updateSpecialoperationscertificate(data) {
  return request({
    url: '/security/specialoperationscertificate',
    method: 'put',
    data: data
  })
}

// 删除特种设备台账
export function delSpecialoperationscertificate(id) {
  return request({
    url: '/security/specialoperationscertificate/' + id,
    method: 'delete'
  })
}

// 导入特种设备台账
export function importSpecialoperationscertificate(data) {
  return request({
    url: '/security/specialoperationscertificate/importData',
    method: 'post',
    data: data
  })
}

// 下载特种设备台账模板
export function downloadTemplate() {
  return request({
    url: '/security/specialoperationscertificate/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
