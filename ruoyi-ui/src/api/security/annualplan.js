import request from '@/utils/request'

// 查询环境和职业健康安全方针列表
export function listAnnualplan(query) {
  return request({
    url: '/security/annualplan/list',
    method: 'get',
    params: query
  })
}

// 查询环境和职业健康安全方针详细
export function getAnnualplan(id) {
  return request({
    url: '/security/annualplan/' + id,
    method: 'get'
  })
}

// 新增环境和职业健康安全方针
export function addAnnualplan(data) {
  return request({
    url: '/security/annualplan',
    method: 'post',
    data: data
  })
}

// 修改环境和职业健康安全方针
export function updateAnnualplan(data) {
  return request({
    url: '/security/annualplan',
    method: 'put',
    data: data
  })
}

// 删除环境和职业健康安全方针
export function delAnnualplan(id) {
  return request({
    url: '/security/annualplan/' + id,
    method: 'delete'
  })
}
