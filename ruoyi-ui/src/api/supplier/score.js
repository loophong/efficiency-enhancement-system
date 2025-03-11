import request from '@/utils/request'

// 查询二方审核得分列表
export function listScore(query) {
  return request({
    url: '/supplier/score/list',
    method: 'get',
    params: query
  })
}

// 查询二方审核得分详细
export function getScore(id) {
  return request({
    url: '/supplier/score/' + id,
    method: 'get'
  })
}

// 新增二方审核得分
export function addScore(data) {
  return request({
    url: '/supplier/score',
    method: 'post',
    data: data
  })
}

// 修改二方审核得分
export function updateScore(data) {
  return request({
    url: '/supplier/score',
    method: 'put',
    data: data
  })
}

// 删除二方审核得分
export function delScore(id) {
  return request({
    url: '/supplier/score/' + id,
    method: 'delete'
  })
}
