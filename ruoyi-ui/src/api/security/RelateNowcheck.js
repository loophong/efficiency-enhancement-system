import request from '@/utils/request'

// 查询相关方现场检查列表
export function listRelateNowcheck(query) {
  return request({
    url: '/security/RelateNowcheck/list',
    method: 'get',
    params: query
  })
}

// 查询相关方现场检查详细
export function getRelateNowcheck(id) {
  return request({
    url: '/security/RelateNowcheck/' + id,
    method: 'get'
  })
}

// 新增相关方现场检查
export function addRelateNowcheck(data) {
  return request({
    url: '/security/RelateNowcheck',
    method: 'post',
    data: data
  })
}

// 修改相关方现场检查
export function updateRelateNowcheck(data) {
  return request({
    url: '/security/RelateNowcheck',
    method: 'put',
    data: data
  })
}

// 删除相关方现场检查
export function delRelateNowcheck(id) {
  return request({
    url: '/security/RelateNowcheck/' + id,
    method: 'delete'
  })
}
