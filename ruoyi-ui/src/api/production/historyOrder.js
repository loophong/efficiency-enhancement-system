import request from '@/utils/request'

// 查询历史订单列表
export function listHistoryOrder(query) {
    return request({
        url: '/production/historyOrder/list',
        method: 'get',
        params: query
    })
}

// 查询历史订单详细
export function getHistoryOrder(id) {
    return request({
        url: '/production/historyOrder/' + id,
        method: 'get'
    })
}

// 新增历史订单
export function addHistoryOrder(data) {
    return request({
        url: '/production/historyOrder',
        method: 'post',
        data: data
    })
}

// 修改历史订单
export function updateHistoryOrder(data) {
    return request({
        url: '/production/historyOrder',
        method: 'put',
        data: data
    })
}

// 删除历史订单
export function delHistoryOrder(id) {
    return request({
        url: '/production/historyOrder/' + id,
        method: 'delete'
    })
}
