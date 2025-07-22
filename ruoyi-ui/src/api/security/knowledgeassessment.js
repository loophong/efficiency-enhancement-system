import request from '@/utils/request'

// 查询安全知识考核列表
export function listKnowledgeassessment(query) {
  return request({
    url: '/security/knowledgeassessment/list',
    method: 'get',
    params: query
  })
}

// 查询安全知识考核详细
export function getKnowledgeassessment(id) {
  return request({
    url: '/security/knowledgeassessment/' + id,
    method: 'get'
  })
}

// 新增安全知识考核
export function addKnowledgeassessment(data) {
  return request({
    url: '/security/knowledgeassessment',
    method: 'post',
    data: data
  })
}

// 修改安全知识考核
export function updateKnowledgeassessment(data) {
  return request({
    url: '/security/knowledgeassessment',
    method: 'put',
    data: data
  })
}

// 删除安全知识考核
export function delKnowledgeassessment(id) {
  return request({
    url: '/security/knowledgeassessment/' + id,
    method: 'delete'
  })
}

// 导入安全知识考核数据
export function importKnowledgeassessment(data) {
  return request({
    url: '/security/knowledgeassessment/importData',
    method: 'post',
    data: data
  })
}

// 下载安全知识考核导入模板
export function importTemplateKnowledgeassessment() {
  return request({
    url: '/security/knowledgeassessment/importTemplate',
    method: 'post'
  })
}

// 根据关联ID查询安全知识考核列表
export function listKnowledgeassessmentByRelatedId(relatedId, sourceModule) {
  return request({
    url: '/security/knowledgeassessment/listByRelatedId',
    method: 'get',
    params: {
      relatedId: relatedId,
      sourceModule: sourceModule
    }
  })
}
