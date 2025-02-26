import request from '@/utils/request'

// 查询2.设备故障记录(导入)列表
export function listTable(query) {
  return request({
    url: '/fault/maintenance/list',
    method: 'get',
    params: query
  })
}

// 查询2.设备故障记录(导入)详细
export function getTable(maintenanceTableId) {
  return request({
    url: '/fault/maintenance/' + maintenanceTableId,
    method: 'get'
  })
}

// 新增2.设备故障记录(导入)
export function addTable(data) {
  return request({
    url: '/fault/maintenance',
    method: 'post',
    data: data
  })
}

// 修改2.设备故障记录(导入)
export function updateTable(data) {
  return request({
    url: '/fault/maintenance',
    method: 'put',
    data: data
  })
}

// 删除2.设备故障记录(导入)
export function delTable(maintenanceTableId) {
  return request({
    url: '/fault/maintenance/' + maintenanceTableId,
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