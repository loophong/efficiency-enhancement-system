import request from '@/utils/request'

// 查询内审检查列表
export function listAuditCheckList(query) {
  return request({
    url: '/security/AuditCheckList/list',
    method: 'get',
    params: query
  })
}

// 查询内审检查详细
export function getAuditCheckList(id) {
  return request({
    url: '/security/AuditCheckList/' + id,
    method: 'get'
  })
}

// 新增内审检查
export function addAuditCheckList(data) {
  return request({
    url: '/security/AuditCheckList',
    method: 'post',
    data: data
  })
}

// 修改内审检查
export function updateAuditCheckList(data) {
  return request({
    url: '/security/AuditCheckList',
    method: 'put',
    data: data
  })
}

// 删除内审检查
export function delAuditCheckList(id) {
  return request({
    url: '/security/AuditCheckList/' + id,
    method: 'delete'
  })
}
