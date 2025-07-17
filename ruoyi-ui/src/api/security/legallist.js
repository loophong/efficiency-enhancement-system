import request from '@/utils/request'

// 查询安全/环境法律法规识别清单列表
export function listLegallist(query) {
  return request({
    url: '/security/legallist/list',
    method: 'get',
    params: query
  })
}

// 查询安全/环境法律法规识别清单详细
export function getLegallist(id) {
  return request({
    url: '/security/legallist/' + id,
    method: 'get'
  })
}

// 新增安全/环境法律法规识别清单
export function addLegallist(data) {
  return request({
    url: '/security/legallist',
    method: 'post',
    data: data
  })
}

// 修改安全/环境法律法规识别清单
export function updateLegallist(data) {
  return request({
    url: '/security/legallist',
    method: 'put',
    data: data
  })
}

// 删除安全/环境法律法规识别清单
export function delLegallist(id) {
  return request({
    url: '/security/legallist/' + id,
    method: 'delete'
  })
}
// 导入安全/环境法律法规识别清单
export function importFile(formData) {
  return request({
    url: '/security/legallist/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 下载安全/环境法律法规识别清单模板
export function downloadTemplate() {
  return request({
    url: '/security/legallist/importTemplate',
    method: 'get',
    responseType: 'blob'
  })
}


