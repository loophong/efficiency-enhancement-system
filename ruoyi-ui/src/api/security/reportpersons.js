import request from '@/utils/request'

// 查询职业危害接触人员体检报告列表
export function listReportpersons(query) {
  return request({
    url: '/security/reportpersons/list',
    method: 'get',
    params: query
  })
}

// 查询职业危害接触人员体检报告详细
export function getReportpersons(id) {
  return request({
    url: '/security/reportpersons/' + id,
    method: 'get'
  })
}

// 新增职业危害接触人员体检报告
export function addReportpersons(data) {
  return request({
    url: '/security/reportpersons',
    method: 'post',
    data: data
  })
}

// 修改职业危害接触人员体检报告
export function updateReportpersons(data) {
  return request({
    url: '/security/reportpersons',
    method: 'put',
    data: data
  })
}

// 删除职业危害接触人员体检报告
export function delReportpersons(id) {
  return request({
    url: '/security/reportpersons/' + id,
    method: 'delete'
  })
}
