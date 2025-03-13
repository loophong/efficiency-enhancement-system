import request from '@/utils/request'

// 查询内审方案列表
export function listInternaPlan(query) {
  return request({
    url: '/security/InternaPlan/list',
    method: 'get',
    params: query
  })
}

// 查询内审方案详细
export function getInternaPlan(id) {
  return request({
    url: '/security/InternaPlan/' + id,
    method: 'get'
  })
}

// 新增内审方案
export function addInternaPlan(data) {
  return request({
    url: '/security/InternaPlan',
    method: 'post',
    data: data
  })
}

// 修改内审方案
export function updateInternaPlan(data) {
  return request({
    url: '/security/InternaPlan',
    method: 'put',
    data: data
  })
}

// 删除内审方案
export function delInternaPlan(id) {
  return request({
    url: '/security/InternaPlan/' + id,
    method: 'delete'
  })
}
