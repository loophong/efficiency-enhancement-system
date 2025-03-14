import request from '@/utils/request'

// 查询重要度列表
export function listImportance(query) {
  return request({
    url: '/supplier/importance/list',
    method: 'get',
    params: query
  })
}

// 查询重要度详细
export function getImportance(id) {
  return request({
    url: '/supplier/importance/' + id,
    method: 'get'
  })
}

// 新增重要度
export function addImportance(data) {
  return request({
    url: '/supplier/importance',
    method: 'post',
    data: data
  })
}

// 修改重要度
export function updateImportance(data) {
  return request({
    url: '/supplier/importance',
    method: 'put',
    data: data
  })
}

// 删除重要度
export function delImportance(id) {
  return request({
    url: '/supplier/importance/' + id,
    method: 'delete'
  })
}
