import request from '@/utils/request'

// 查询安全法律法规识别清单列表
export function listRegulationsIdentificationList1(query) {
  return request({
    url: '/security/RegulationsIdentificationList1/list',
    method: 'get',
    params: query
  })
}

// 查询安全法律法规识别清单详细
export function getRegulationsIdentificationList1(id) {
  return request({
    url: '/security/RegulationsIdentificationList1/' + id,
    method: 'get'
  })
}

// 新增安全法律法规识别清单
export function addRegulationsIdentificationList1(data) {
  return request({
    url: '/security/RegulationsIdentificationList1',
    method: 'post',
    data: data
  })
}

// 修改安全法律法规识别清单
export function updateRegulationsIdentificationList1(data) {
  return request({
    url: '/security/RegulationsIdentificationList1',
    method: 'put',
    data: data
  })
}

// 删除安全法律法规识别清单
export function delRegulationsIdentificationList1(id) {
  return request({
    url: '/security/RegulationsIdentificationList1/' + id,
    method: 'delete'
  })
}

export function importFile(formData) {
  return request({
    url: '/security/RegulationsIdentificationList1/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
