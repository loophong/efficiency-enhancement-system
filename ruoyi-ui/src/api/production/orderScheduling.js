import request from '@/utils/request'

// 查询订单信息列表
export function listScheduling(query) {
    return request({
        url: '/production/scheduling/list',
        method: 'get',
        params: query
    })
}

// 查询订单信息详细
export function getScheduling(id) {
    return request({
        url: '/production/scheduling/' + id,
        method: 'get'
    })
}

// 新增订单信息
export function addScheduling(data) {
    return request({
        url: '/production/scheduling',
        method: 'post',
        data: data
    })
}

// 修改订单信息
export function updateScheduling(data) {
    return request({
        url: '/production/scheduling',
        method: 'put',
        data: data
    })
}

// 删除订单信息
export function delScheduling(id) {
    return request({
        url: '/production/scheduling/' + id,
        method: 'delete'
    })
}
