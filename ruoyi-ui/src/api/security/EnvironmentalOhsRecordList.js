import request from '@/utils/request'

// 查询环境职业健康安全记录清单列表
export function listEnvironmentalOhsRecordList(query) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/list',
    method: 'get',
    params: query
  })
}

// 查询环境职业健康安全记录清单详细
export function getEnvironmentalOhsRecordList(id) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/' + id,
    method: 'get'
  })
}

// 新增环境职业健康安全记录清单
export function addEnvironmentalOhsRecordList(data) {
  return request({
    url: '/security/EnvironmentalOhsRecordList',
    method: 'post',
    data: data
  })
}

// 修改环境职业健康安全记录清单
export function updateEnvironmentalOhsRecordList(data) {
  return request({
    url: '/security/EnvironmentalOhsRecordList',
    method: 'put',
    data: data
  })
}

// 删除环境职业健康安全记录清单
export function delEnvironmentalOhsRecordList(id) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/' + id,
    method: 'delete'
  })
}

export function importFile(formData) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 导入环境职业健康安全记录清单
export function importEnvironmentalOhsRecordList(data) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/importData',
    method: 'post',
    data: data
  })
}

// 下载环境职业健康安全记录清单导入模板
export function downloadTemplate() {
  return request({
    url: '/security/EnvironmentalOhsRecordList/importTemplate',
    method: 'get'
  })
}

// 根据关联ID查询环境职业健康安全记录清单列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/EnvironmentalOhsRecordList/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
