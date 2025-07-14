import request from '@/utils/request'

// 查询设备导入模板文件列表
export function listTemplate(query) {
  return request({
    url: '/file/template/list',
    method: 'get',
    params: query
  })
}

// 查询设备导入模板文件详细
export function getTemplate(templateId) {
  return request({
    url: '/file/template/' + templateId,
    method: 'get'
  })
}

// 新增设备导入模板文件
export function addTemplate(data) {
  return request({
    url: '/file/template',
    method: 'post',
    data: data
  })
}

// 修改设备导入模板文件
export function updateTemplate(data) {
  return request({
    url: '/file/template',
    method: 'put',
    data: data
  })
}

// 删除设备导入模板文件
export function delTemplate(templateId) {
  return request({
    url: '/file/template/' + templateId,
    method: 'delete'
  })
}
