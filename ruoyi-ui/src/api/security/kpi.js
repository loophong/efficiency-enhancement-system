import request from '@/utils/request'

// 查询公司KPI列表
export function listKpi(query) {
  return request({
    url: '/security/kpi/list',
    method: 'get',
    params: query
  })
}

// 根据关联ID查询公司KPI列表
export function listKpiByRelatedId(relatedId) {
  return request({
    url: '/security/kpi/listByRelatedId/' + relatedId,
    method: 'get'
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

// 根据关联ID删除公司KPI
export function delKpiByRelatedId(relatedId) {
  return request({
    url: '/security/kpi/deleteByRelatedId/' + relatedId,
    method: 'delete'
  })
}

// 导出公司KPI
export function exportKpi(query) {
  return request({
    url: '/security/kpi/export',
    method: 'get',
    params: query
  })
}

// 下载公司KPI导入模板
export function importTemplate() {
  return request({
    url: '/security/kpi/importTemplate',
    method: 'post'
  })
}

// 导入公司KPI数据
export function importKpi(file) {
  const formData = new FormData();
  formData.append('excelFile', file);
  return request({
    url: '/security/kpi/importData',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
