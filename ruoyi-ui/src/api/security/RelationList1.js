import request from '@/utils/request'

// 查询相关方检查记录列表
export function listRelationList1(query) {
  return request({
    url: '/security/RelationList1/list',
    method: 'get',
    params: query
  })
}

// 查询相关方检查记录详细
export function getRelationList1(id) {
  return request({
    url: '/security/RelationList1/' + id,
    method: 'get'
  })
}

// 新增相关方检查记录
export function addRelationList1(data) {
  return request({
    url: '/security/RelationList1',
    method: 'post',
    data: data
  })
}

// 修改相关方检查记录
export function updateRelationList1(data) {
  return request({
    url: '/security/RelationList1',
    method: 'put',
    data: data
  })
}

// 删除相关方检查记录
export function delRelationList1(id) {
  return request({
    url: '/security/RelationList1/' + id,
    method: 'delete'
  })
}
