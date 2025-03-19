import request from '@/utils/request'

// 查询经营风险列表
export function listRisk(query) {
  return request({
    url: '/supplier/risk/list',
    method: 'get',
    params: query
  })
}

// 查询经营风险详细
export function getRisk(id) {
  return request({
    url: '/supplier/risk/' + id,
    method: 'get'
  })
}

// 新增经营风险
export function addRisk(data) {
  return request({
    url: '/supplier/risk',
    method: 'post',
    data: data
  })
}

// 修改经营风险
export function updateRisk(data) {
  return request({
    url: '/supplier/risk',
    method: 'put',
    data: data
  })
}

// 删除经营风险
export function delRisk(id) {
  return request({
    url: '/supplier/risk/' + id,
    method: 'delete'
  })
}

// 上传主计划表
export function importFile(formData) {
  return request({
    url: '/supplier/risk/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}