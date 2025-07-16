import request from '@/utils/request'

// 查询公司重点工作列表
export function listWorks(query) {
  return request({
    url: '/security/works/list',
    method: 'get',
    params: query
  })
}

// 查询公司重点工作详细
export function getWorks(id) {
  return request({
    url: '/security/works/' + id,
    method: 'get'
  })
}

// 新增公司重点工作
export function addWorks(data) {
  return request({
    url: '/security/works',
    method: 'post',
    data: data
  })
}

// 修改公司重点工作
export function updateWorks(data) {
  return request({
    url: '/security/works',
    method: 'put',
    data: data
  })
}

// 删除公司重点工作
export function delWorks(id) {
  return request({
    url: '/security/works/' + id,
    method: 'delete'
  })
}

// 导入公司重点工作数据
export function importFile(data) {
  return request({
    url: '/security/works/importData',
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}

// 下载公司重点工作导入模板
export function downloadTemplate() {
  return request({
    url: '/security/works/importTemplate',
    method: 'post',
    responseType: 'blob'
  })
}

// 根据关联ID查询公司重点工作列表
export function listWorksByRelatedId(relatedId) {
  return request({
    url: '/security/works/listByRelatedId/' + relatedId,
    method: 'get'
  })
}

// 更新公司重点工作的关联ID
export function updateWorksRelatedId(id, relatedId) {
  return request({
    url: '/security/works/updateRelatedId/' + id + '/' + relatedId,
    method: 'put'
  })
}

// 更新最近导入的公司重点工作的关联ID
export function updateLatestImportedRelatedId(relatedId) {
  return request({
    url: '/security/works/updateLatestImportedRelatedId/' + relatedId,
    method: 'put'
  })
}
