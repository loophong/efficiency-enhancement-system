import request from '@/utils/request'

// 查询自检报告列表
export function listCheckreports(query) {
  return request({
    url: '/supplier/checkreports/list',
    method: 'get',
    params: query
  })
}

// 查询自检报告详细
export function getCheckreports(id) {
  return request({
    url: '/supplier/checkreports/' + id,
    method: 'get'
  })
}

// 新增自检报告
export function addCheckreports(data) {
  return request({
    url: '/supplier/checkreports',
    method: 'post',
    data: data
  })
}

// 修改自检报告
export function updateCheckreports(data) {
  return request({
    url: '/supplier/checkreports',
    method: 'put',
    data: data
  })
}

// 删除自检报告
export function delCheckreports(id) {
  return request({
    url: '/supplier/checkreports/' + id,
    method: 'delete'
  })
}
