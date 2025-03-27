import request from '@/utils/request'

// 查询零公里故障指标完成率列表
export function listZeroFailureRate(query) {
  return request({
    url: '/supplier/zeroFailureRate/list',
    method: 'get',
    params: query
  })
}

// 查询零公里故障指标完成率详细
export function getZeroFailureRate(id) {
  return request({
    url: '/supplier/zeroFailureRate/' + id,
    method: 'get'
  })
}

// 新增零公里故障指标完成率
export function addZeroFailureRate(data) {
  return request({
    url: '/supplier/zeroFailureRate',
    method: 'post',
    data: data
  })
}

// 修改零公里故障指标完成率
export function updateZeroFailureRate(data) {
  return request({
    url: '/supplier/zeroFailureRate',
    method: 'put',
    data: data
  })
}

// 删除零公里故障指标完成率
export function delZeroFailureRate(id) {
  return request({
    url: '/supplier/zeroFailureRate/' + id,
    method: 'delete'
  })
}

// 上传主计划表
export function importFile(formData) {
  return request({
    url: '/supplier/zeroFailureRate/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
  }
  })
}