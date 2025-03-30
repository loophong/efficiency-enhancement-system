import request from '@/utils/request'

// 查询重点部位检查记录列表
export function listImportantList(query) {
  return request({
    url: '/security/ImportantList/list',
    method: 'get',
    params: query
  })
}

// 查询重点部位检查记录详细
export function getImportantList(id) {
  return request({
    url: '/security/ImportantList/' + id,
    method: 'get'
  })
}

// 新增重点部位检查记录
export function addImportantList(data) {
  return request({
    url: '/security/ImportantList',
    method: 'post',
    data: data
  })
}

// 修改重点部位检查记录
export function updateImportantList(data) {
  return request({
    url: '/security/ImportantList',
    method: 'put',
    data: data
  })
}

// 删除重点部位检查记录
export function delImportantList(id) {
  return request({
    url: '/security/ImportantList/' + id,
    method: 'delete'
  })
}
