import request from '@/utils/request'

// 查询特种作业人员信息列表
export function listSpecialoperationspersonnelInfo(query) {
  return request({
    url: '/security/specialoperationspersonnelInfo/list',
    method: 'get',
    params: query
  })
}

// 查询特种作业人员信息详细
export function getSpecialoperationspersonnelInfo(id) {
  return request({
    url: '/security/specialoperationspersonnelInfo/' + id,
    method: 'get'
  })
}

// 新增特种作业人员信息
export function addSpecialoperationspersonnelInfo(data) {
  return request({
    url: '/security/specialoperationspersonnelInfo',
    method: 'post',
    data: data
  })
}

// 修改特种作业人员信息
export function updateSpecialoperationspersonnelInfo(data) {
  return request({
    url: '/security/specialoperationspersonnelInfo',
    method: 'put',
    data: data
  })
}

// 删除特种作业人员信息
export function delSpecialoperationspersonnelInfo(id) {
  return request({
    url: '/security/specialoperationspersonnelInfo/' + id,
    method: 'delete'
  })
}
