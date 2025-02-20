import request from '@/utils/request'

// 查询历史订单统计列表
export function listOrderStatistics(query) {
    return request({
        url: '/production/orderStatistics/list',
        method: 'get',
        params: query
    })
}

// 查询历史订单统计详细
export function getOrderStatistics(id) {
    return request({
        url: '/production/orderStatistics/' + id,
        method: 'get'
    })
}

// 新增历史订单统计
export function addOrderStatistics(data) {
    return request({
        url: '/production/orderStatistics',
        method: 'post',
        data: data
    })
}

// 修改历史订单统计
export function updateOrderStatistics(data) {
    return request({
        url: '/production/orderStatistics',
        method: 'put',
        data: data
    })
}

// 删除历史订单统计
export function delOrderStatistics(id) {
    return request({
        url: '/production/orderStatistics/' + id,
        method: 'delete'
    })
}
