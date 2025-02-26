import request from '@/utils/request'

// 查询设备故障分析列表
export function listAnalysis(query) {
  return request({
    url: '/fault/analysis/list',
    method: 'get',
    params: query
  })
}

// 查询设备故障分析详细
export function getAnalysis(analysisId) {
  return request({
    url: '/fault/analysis/' + analysisId,
    method: 'get'
  })
}

// 新增设备故障分析
export function addAnalysis(data) {
  return request({
    url: '/fault/analysis',
    method: 'post',
    data: data
  })
}

// 修改设备故障分析
export function updateAnalysis(data) {
  return request({
    url: '/fault/analysis',
    method: 'put',
    data: data
  })
}

// 删除设备故障分析
export function delAnalysis(analysisId) {
  return request({
    url: '/fault/analysis/' + analysisId,
    method: 'delete'
  })
}
