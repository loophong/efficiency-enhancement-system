import request from '@/utils/request'

// 查询特种设备监督检验证书列表
export function listInspectionCertificate(query) {
  return request({
    url: '/security/InspectionCertificate/list',
    method: 'get',
    params: query
  })
}

// 查询特种设备监督检验证书详细
export function getInspectionCertificate(id) {
  return request({
    url: '/security/InspectionCertificate/' + id,
    method: 'get'
  })
}

// 新增特种设备监督检验证书
export function addInspectionCertificate(data) {
  return request({
    url: '/security/InspectionCertificate',
    method: 'post',
    data: data
  })
}

// 修改特种设备监督检验证书
export function updateInspectionCertificate(data) {
  return request({
    url: '/security/InspectionCertificate',
    method: 'put',
    data: data
  })
}

// 删除特种设备监督检验证书
export function delInspectionCertificate(id) {
  return request({
    url: '/security/InspectionCertificate/' + id,
    method: 'delete'
  })
}
