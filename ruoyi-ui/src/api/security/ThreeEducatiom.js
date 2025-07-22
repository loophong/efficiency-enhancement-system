import request from '@/utils/request'

// 查询新职工三级安全教育台账列表
export function listThreeEducatiom(query) {
  return request({
    url: '/security/ThreeEducatiom/list',
    method: 'get',
    params: query
  })
}

// 根据关联ID查询新职工三级安全教育台账列表
export function listThreeEducatiomByRelatedId(relatedId, sourceModule) {
  return request({
    url: '/security/ThreeEducatiom/listByRelatedId',
    method: 'get',
    params: {
      relatedId: relatedId,
      sourceModule: sourceModule
    }
  })
}

// 查询新职工三级安全教育台账详细
export function getThreeEducatiom(id) {
  return request({
    url: '/security/ThreeEducatiom/' + id,
    method: 'get'
  })
}

// 新增新职工三级安全教育台账
export function addThreeEducatiom(data) {
  return request({
    url: '/security/ThreeEducatiom',
    method: 'post',
    data: data
  })
}

// 修改新职工三级安全教育台账
export function updateThreeEducatiom(data) {
  return request({
    url: '/security/ThreeEducatiom',
    method: 'put',
    data: data
  })
}

// 删除新职工三级安全教育台账
export function delThreeEducatiom(id) {
  return request({
    url: '/security/ThreeEducatiom/' + id,
    method: 'delete'
  })
}

// 导入新职工三级安全教育台账数据
export function importThreeEducatiom(data) {
  return request({
    url: '/security/ThreeEducatiom/importData',
    method: 'post',
    data: data
  })
}

// 下载新职工三级安全教育台账导入模板
export function importTemplateThreeEducatiom() {
  return request({
    url: '/security/ThreeEducatiom/importTemplate',
    method: 'post'
  })
}
