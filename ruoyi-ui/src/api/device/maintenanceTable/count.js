import request from '@/utils/request'

// 查询设备指标分析列表
export function listCount(query) {
  return request({
    url: '/fault/indicator/list',
    method: 'get',
    params: query
  })
}

// 查询设备指标分析详细
export function getCount(indicatorId) {
  return request({
    url: '/fault/indicator/' + indicatorId,
    method: 'get'
  })
}

// 新增设备指标分析
export function addCount(data) {
  return request({
    url: '/fault/indicator',
    method: 'post',
    data: data
  })
}

// 修改设备指标分析
export function updateCount(data) {
  return request({
    url: '/fault/indicator',
    method: 'put',
    data: data
  })
}

// 删除设备指标分析
export function delCount(indicatorId) {
  return request({
    url: '/fault/indicator/' + indicatorId,
    method: 'delete'
  })
}
