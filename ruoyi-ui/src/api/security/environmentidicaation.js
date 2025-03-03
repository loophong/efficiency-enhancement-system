import request from '@/utils/request'

// 查询环境识别列表
export function listEnvironmentidicaation(query) {
  return request({
    url: '/security/environmentidicaation/list',
    method: 'get',
    params: query
  })
}

// 查询环境识别详细
export function getEnvironmentidicaation(id) {
  return request({
    url: '/security/environmentidicaation/' + id,
    method: 'get'
  })
}

// 新增环境识别
export function addEnvironmentidicaation(data) {
  return request({
    url: '/security/environmentidicaation',
    method: 'post',
    data: data
  })
}

// 修改环境识别
export function updateEnvironmentidicaation(data) {
  return request({
    url: '/security/environmentidicaation',
    method: 'put',
    data: data
  })
}

// 删除环境识别
export function delEnvironmentidicaation(id) {
  return request({
    url: '/security/environmentidicaation/' + id,
    method: 'delete'
  })
}
