import request from '@/utils/request'

// 查询危险职业岗位人员清单及管理台帐列表
export function listOccupationaPersonnelList(query) {
  return request({
    url: '/security/OccupationaPersonnelList/list',
    method: 'get',
    params: query
  })
}

// 查询危险职业岗位人员清单及管理台帐详细
export function getOccupationaPersonnelList(id) {
  return request({
    url: '/security/OccupationaPersonnelList/' + id,
    method: 'get'
  })
}

// 新增危险职业岗位人员清单及管理台帐
export function addOccupationaPersonnelList(data) {
  return request({
    url: '/security/OccupationaPersonnelList',
    method: 'post',
    data: data
  })
}

// 修改危险职业岗位人员清单及管理台帐
export function updateOccupationaPersonnelList(data) {
  return request({
    url: '/security/OccupationaPersonnelList',
    method: 'put',
    data: data
  })
}

// 删除危险职业岗位人员清单及管理台帐
export function delOccupationaPersonnelList(id) {
  return request({
    url: '/security/OccupationaPersonnelList/' + id,
    method: 'delete'
  })
}
