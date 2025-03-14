import request from '@/utils/request'

// 查询产能列表
export function listCapacity(query) {
    return request({
        url: '/production/capacity/list',
        method: 'get',
        params: query
    })
}

// 查询产能详细
export function getCapacity(id) {
    return request({
        url: '/production/capacity/' + id,
        method: 'get'
    })
}

// 新增产能
export function addCapacity(data) {
    return request({
        url: '/production/capacity',
        method: 'post',
        data: data
    })
}

// 修改产能
export function updateCapacity(data) {
    return request({
        url: '/production/capacity',
        method: 'put',
        data: data
    })
}

// 删除产能
export function delCapacity(id) {
    return request({
        url: '/production/capacity/' + id,
        method: 'delete'
    })
}

// 获取全部产能
export function all(date) {
    return request({
        url: '/production/capacity/all',
        method: 'get',
        params: {
            'date': date
        }
    })
}