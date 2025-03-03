import request from '@/utils/request'

// 查询设备台账列表
export function listDetails(query) {
  return request({
    url: '/file/details/list',
    method: 'get',
    params: query
  })
}

// 查询设备台账详细
export function getDetails(detailsId) {
  return request({
    url: '/file/details/' + detailsId,
    method: 'get'
  })
}

// 新增设备台账
export function addDetails(data) {
  return request({
    url: '/file/details',
    method: 'post',
    data: data
  })
}

// 修改设备台账
export function updateDetails(data) {
  return request({
    url: '/file/details',
    method: 'put',
    data: data
  })
}

// 删除设备台账
export function delDetails(detailsId) {
  return request({
    url: '/file/details/' + detailsId,
    method: 'delete'
  })
}

export function uploadFile(data, aimUrl) {
  return request({
    url: aimUrl,
    method: 'post',
    data: data,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
    });
}