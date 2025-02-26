import request from '@/utils/request'

// 查询车型列表
export function listVehicle(query) {
    return request({
        url: '/production/vehicle/list',
        method: 'get',
        params: query
    })
}

// 查询车型详细
export function getVehicle(id) {
    return request({
        url: '/production/vehicle/' + id,
        method: 'get'
    })
}

// 新增车型
export function addVehicle(data) {
    return request({
        url: '/production/vehicle',
        method: 'post',
        data: data
    })
}

// 修改车型
export function updateVehicle(data) {
    return request({
        url: '/production/vehicle',
        method: 'put',
        data: data
    })
}

// 删除车型
export function delVehicle(id) {
    return request({
        url: '/production/vehicle/' + id,
        method: 'delete'
    })
}

// 上传车型表
export function importFile(formData) {
    return request({
        url: '/production/vehicle/import',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}


