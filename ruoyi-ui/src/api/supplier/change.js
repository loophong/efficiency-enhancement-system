import request from '@/utils/request'

// 查询擅自变更产品材质参数尺寸列表
export function listChange(query) {
  return request({
    url: '/supplier/change/list',
    method: 'get',
    params: query
  })
}

// 查询擅自变更产品材质参数尺寸详细
export function getChange(id) {
  return request({
    url: '/supplier/change/' + id,
    method: 'get'
  })
}

// 新增擅自变更产品材质参数尺寸
export function addChange(data) {
  return request({
    url: '/supplier/change',
    method: 'post',
    data: data
  })
}

// 修改擅自变更产品材质参数尺寸
export function updateChange(data) {
  return request({
    url: '/supplier/change',
    method: 'put',
    data: data
  })
}

// 删除擅自变更产品材质参数尺寸
export function delChange(id) {
  return request({
    url: '/supplier/change/' + id,
    method: 'delete'
  })
}
