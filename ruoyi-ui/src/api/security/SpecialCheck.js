import request from '@/utils/request'

// 查询专项检查记录列表
export function listSpecialCheck(query) {
  return request({
    url: '/security/SpecialCheck/list',
    method: 'get',
    params: query
  })
}

// 查询专项检查记录详细
export function getSpecialCheck(id) {
  return request({
    url: '/security/SpecialCheck/' + id,
    method: 'get'
  })
}

// 新增专项检查记录
export function addSpecialCheck(data) {
  return request({
    url: '/security/SpecialCheck',
    method: 'post',
    data: data
  })
}

// 修改专项检查记录
export function updateSpecialCheck(data) {
  return request({
    url: '/security/SpecialCheck',
    method: 'put',
    data: data
  })
}

// 删除专项检查记录
export function delSpecialCheck(id) {
  return request({
    url: '/security/SpecialCheck/' + id,
    method: 'delete'
  })
}
