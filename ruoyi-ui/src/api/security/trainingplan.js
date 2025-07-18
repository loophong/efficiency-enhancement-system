import request from '@/utils/request'

// 查询年度培训计划列表
export function listTrainingplan(query) {
  return request({
    url: '/security/trainingplan/list',
    method: 'get',
    params: query
  })
}

// 查询年度培训计划详细
export function getTrainingplan(id) {
  return request({
    url: '/security/trainingplan/' + id,
    method: 'get'
  })
}

// 新增年度培训计划
export function addTrainingplan(data) {
  return request({
    url: '/security/trainingplan',
    method: 'post',
    data: data
  })
}

// 修改年度培训计划
export function updateTrainingplan(data) {
  return request({
    url: '/security/trainingplan',
    method: 'put',
    data: data
  })
}

// 删除年度培训计划
export function delTrainingplan(id) {
  return request({
    url: '/security/trainingplan/' + id,
    method: 'delete'
  })
}

// 导入年度培训计划数据
export function importTrainingplan(data) {
  return request({
    url: '/security/trainingplan/importData',
    method: 'post',
    data: data
  })
}

// 下载年度培训计划导入模板
export function downloadTemplate() {
  return request({
    url: '/security/trainingplan/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}
