import request from '@/utils/request'

// 查询一次交检合格率列表
export function listOnetime(query) {
  return request({
    url: '/supplier/onetime/list',
    method: 'get',
    params: query
  })
}

// 查询一次交检合格率详细
export function getOnetime(supplierCode) {
  return request({
    url: '/supplier/onetime/' + supplierCode,
    method: 'get'
  })
}

// 新增一次交检合格率
export function addOnetime(data) {
  return request({
    url: '/supplier/onetime',
    method: 'post',
    data: data
  })
}

// 修改一次交检合格率
export function updateOnetime(data) {
  return request({
    url: '/supplier/onetime',
    method: 'put',
    data: data
  })
}

// 删除一次交检合格率
export function delOnetime(supplierCode) {
  return request({
    url: '/supplier/onetime/' + supplierCode,
    method: 'delete'
  })
}


// 上传主计划表
export function importFile(formData) {
  return request({
    url: '/supplier/onetime/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
