import request from '@/utils/request'

// 查询设备故障记录列表
export function listTable(query) {
  return request({
    url: '/fault/maintenance/list',
    method: 'get',
    params: query
  })
}

// 查询设备故障记录详细
export function getTable(maintenanceTableId) {
  return request({
    url: '/fault/maintenance/' + maintenanceTableId,
    method: 'get'
  })
}

// 新增设备故障记录
export function addTable(data) {
  return request({
    url: '/fault/maintenance',
    method: 'post',
    data: data
  })
}

// 修改设备故障记录
export function updateTable(data) {
  return request({
    url: '/fault/maintenance',
    method: 'put',
    data: data
  })
}

// 删除设备故障记录
export function delTable(maintenanceTableId) {
  return request({
    url: '/fault/maintenance/' + maintenanceTableId,
    method: 'delete'
  })
}



export function getTreeList(query) {
  return request({
    url: '/fault/maintenance/treeList',
    method: 'get',
    params: query,
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