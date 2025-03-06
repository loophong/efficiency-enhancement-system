import request from '@/utils/request'

// 查询质量事故列表
export function listIncidents(query) {
  return request({
    url: '/supplier/incidents/list',
    method: 'get',
    params: query
  })
}

// 查询质量事故详细
export function getIncidents(id) {
  return request({
    url: '/supplier/incidents/' + id,
    method: 'get'
  })
}

// 新增质量事故
export function addIncidents(data) {
  return request({
    url: '/supplier/incidents',
    method: 'post',
    data: data
  })
}

// 修改质量事故
export function updateIncidents(data) {
  return request({
    url: '/supplier/incidents',
    method: 'put',
    data: data
  })
}

// 删除质量事故
export function delIncidents(id) {
  return request({
    url: '/supplier/incidents/' + id,
    method: 'delete'
  })
}
