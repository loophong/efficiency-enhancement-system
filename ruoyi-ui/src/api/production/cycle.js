import request from '@/utils/request'

// 查询产能列表
export function listCycle(query) {
    return request({
        url: '/production/cycle/list',
        method: 'get',
        params: query
    })
}

// 查询产能详细
export function getCycle(id) {
    return request({
        url: '/production/cycle/' + id,
        method: 'get'
    })
}

// 新增产能
export function addCycle(data) {
    return request({
        url: '/production/cycle',
        method: 'post',
        data: data
    })
}

// 修改产能
export function updateCycle(data) {
    return request({
        url: '/production/cycle',
        method: 'put',
        data: data
    })
}

// 删除产能
export function delCycle(id) {
    return request({
        url: '/production/cycle/' + id,
        method: 'delete'
    })
}
