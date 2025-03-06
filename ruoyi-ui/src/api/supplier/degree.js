import request from '@/utils/request'

// 查询新产品研发配合程度列表
export function listDegree(query) {
  return request({
    url: '/supplier/degree/list',
    method: 'get',
    params: query
  })
}

// 查询新产品研发配合程度详细
export function getDegree(id) {
  return request({
    url: '/supplier/degree/' + id,
    method: 'get'
  })
}

// 新增新产品研发配合程度
export function addDegree(data) {
  return request({
    url: '/supplier/degree',
    method: 'post',
    data: data
  })
}

// 修改新产品研发配合程度
export function updateDegree(data) {
  return request({
    url: '/supplier/degree',
    method: 'put',
    data: data
  })
}

// 删除新产品研发配合程度
export function delDegree(id) {
  return request({
    url: '/supplier/degree/' + id,
    method: 'delete'
  })
}
