import request from '@/utils/request'

// 查询班组人员清单列表
export function listPeopleList(query) {
  return request({
    url: '/security/PeopleList/list',
    method: 'get',
    params: query
  })
}

// 查询班组人员清单详细
export function getPeopleList(id) {
  return request({
    url: '/security/PeopleList/' + id,
    method: 'get'
  })
}

// 新增班组人员清单
export function addPeopleList(data) {
  return request({
    url: '/security/PeopleList',
    method: 'post',
    data: data
  })
}

// 修改班组人员清单
export function updatePeopleList(data) {
  return request({
    url: '/security/PeopleList',
    method: 'put',
    data: data
  })
}

// 删除班组人员清单
export function delPeopleList(id) {
  return request({
    url: '/security/PeopleList/' + id,
    method: 'delete'
  })
}
