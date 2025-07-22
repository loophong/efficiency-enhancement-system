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

// 导入班组人员清单
export function importPeopleList(data) {
  return request({
    url: '/security/PeopleList/importData',
    method: 'post',
    data: data
  })
}

// 下载班组人员清单导入模板
export function downloadTemplate() {
  return request({
    url: '/security/PeopleList/importTemplate',
    method: 'post'
  })
}

// 根据关联ID查询班组人员清单列表
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/PeopleList/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
