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

// 导入环境识别数据
export function importEnvironmentidicaation(formData) {
  return request({
    url: '/security/environmentidicaation/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    // 阻止axios自动处理Content-Type和formData
    transformRequest: [function (data) {
      // 如果是FormData实例，直接返回
      if (data instanceof FormData) {
        return data;
      }
      // 否则使用默认转换
      return JSON.stringify(data);
    }]
  })
}

// 下载导入模板
export function importTemplate() {
  return request({
    url: '/security/environmentidicaation/import/template',
    method: 'get',
    responseType: 'blob'
  })
}

// 根据关联ID查询环境识别
export function listByRelatedId(relatedId) {
  return request({
    url: '/security/environmentidicaation/listByRelatedId/' + relatedId,
    method: 'get'
  })
}
