import request from '@/utils/request'

// 查询领导干部带队检查记录列表
export function listTroopsCheck(query) {
  return request({
    url: '/security/TroopsCheck/list',
    method: 'get',
    params: query
  })
}

// 查询领导干部带队检查记录详细
export function getTroopsCheck(id) {
  return request({
    url: '/security/TroopsCheck/' + id,
    method: 'get'
  })
}

// 新增领导干部带队检查记录
export function addTroopsCheck(data) {
  return request({
    url: '/security/TroopsCheck',
    method: 'post',
    data: data
  })
}

// 修改领导干部带队检查记录
export function updateTroopsCheck(data) {
  return request({
    url: '/security/TroopsCheck',
    method: 'put',
    data: data
  })
}

// 删除领导干部带队检查记录
export function delTroopsCheck(id) {
  return request({
    url: '/security/TroopsCheck/' + id,
    method: 'delete'
  })
}
