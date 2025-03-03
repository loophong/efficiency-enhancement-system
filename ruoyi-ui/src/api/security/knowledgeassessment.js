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
