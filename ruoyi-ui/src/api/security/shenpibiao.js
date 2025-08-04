import request from '@/utils/request'

// 查询不符合、纠正措施和预防措施程序列表
export function listShenpibiao(query) {
  return request({
    url: '/security/shenpibiao/list',
    method: 'get',
    params: query
  })
}

// 查询不符合、纠正措施和预防措施程序详细
export function getShenpibiao(id) {
  return request({
    url: '/security/shenpibiao/' + id,
    method: 'get'
  })
}

// 新增不符合、纠正措施和预防措施程序
export function addShenpibiao(data) {
  return request({
    url: '/security/shenpibiao',
    method: 'post',
    data: data
  })
}

// 修改不符合、纠正措施和预防措施程序
export function updateShenpibiao(data) {
  return request({
    url: '/security/shenpibiao',
    method: 'put',
    data: data
  })
}

// 删除不符合、纠正措施和预防措施程序
export function delShenpibiao(id) {
  return request({
    url: '/security/shenpibiao/' + id,
    method: 'delete'
  })
}
