import request from '@/utils/request'

// 查询一次交检合格率-简化版列表
export function listOnetimesimple(query) {
  return request({
    url: '/supplier/onetimesimple/list',
    method: 'get',
    params: query
  })
}

// 查询一次交检合格率-简化版详细
export function getOnetimesimple(id) {
  return request({
    url: '/supplier/onetimesimple/' + id,
    method: 'get'
  })
}

// 新增一次交检合格率-简化版
export function addOnetimesimple(data) {
  return request({
    url: '/supplier/onetimesimple',
    method: 'post',
    data: data
  })
}

// 修改一次交检合格率-简化版
export function updateOnetimesimple(data) {
  return request({
    url: '/supplier/onetimesimple',
    method: 'put',
    data: data
  })
}

// 删除一次交检合格率-简化版
export function delOnetimesimple(id) {
  return request({
    url: '/supplier/onetimesimple/' + id,
    method: 'delete'
  })
}


// 上传主计划表
export function importFile(formData) {
  return request({
    url: '/supplier/onetimesimple/import',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
  }
  })
}