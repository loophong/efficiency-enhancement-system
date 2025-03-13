import request from '@/utils/request'

// 查询特种设备人员证件列表
export function listPersonCertificate(query) {
  return request({
    url: '/security/PersonCertificate/list',
    method: 'get',
    params: query
  })
}

// 查询特种设备人员证件详细
export function getPersonCertificate(id) {
  return request({
    url: '/security/PersonCertificate/' + id,
    method: 'get'
  })
}

// 新增特种设备人员证件
export function addPersonCertificate(data) {
  return request({
    url: '/security/PersonCertificate',
    method: 'post',
    data: data
  })
}

// 修改特种设备人员证件
export function updatePersonCertificate(data) {
  return request({
    url: '/security/PersonCertificate',
    method: 'put',
    data: data
  })
}

// 删除特种设备人员证件
export function delPersonCertificate(id) {
  return request({
    url: '/security/PersonCertificate/' + id,
    method: 'delete'
  })
}
