import request from '@/utils/request'

// 查询公司重点工作列表
export function listWorks(query) {
  return request({
    url: '/security/works/list',
    method: 'get',
    params: query
  })
}

// 查询公司重点工作详细
export function getWorks(id) {
  return request({
    url: '/security/works/' + id,
    method: 'get'
  })
}

// 新增公司重点工作
export function addWorks(data) {
  return request({
    url: '/security/works',
    method: 'post',
    data: data
  })
}

// 修改公司重点工作
export function updateWorks(data) {
  return request({
    url: '/security/works',
    method: 'put',
    data: data
  })
}

// 删除公司重点工作
export function delWorks(id) {
  return request({
    url: '/security/works/' + id,
    method: 'delete'
  })
}
