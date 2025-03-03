import request from '@/utils/request'

// 查询公司KPI列表
export function listKpi(query) {
  return request({
    url: '/security/kpi/list',
    method: 'get',
    params: query
  })
}

// 查询公司KPI详细
export function getKpi(id) {
  return request({
    url: '/security/kpi/' + id,
    method: 'get'
  })
}

// 新增公司KPI
export function addKpi(data) {
  return request({
    url: '/security/kpi',
    method: 'post',
    data: data
  })
}

// 修改公司KPI
export function updateKpi(data) {
  return request({
    url: '/security/kpi',
    method: 'put',
    data: data
  })
}

// 删除公司KPI
export function delKpi(id) {
  return request({
    url: '/security/kpi/' + id,
    method: 'delete'
  })
}
