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

export function getUrgentOrder() {
    return request({
        url: '/production/scheduling/getUrgentOrder',
        method: 'get'
    })
}

export function getOrders() {
    return request({
        url: '/production/scheduling/getOrderList',
        method: 'get'
    })
}

export function schedulingOrders(orderSchedulingList, dailyUsedCapacityList) {

    // 构建 schedulingDTO
    const schedulingDTO = {
        orderSchedulingList,
        dailyUsedCapacityList
    };

    return request({
        url: '/production/scheduling/orders',
        method: 'post',
        data: schedulingDTO
    })
}

// 取消排产
export function cancelScheduling(date) {
    return request({
        url: '/production/scheduling/cancel',
        method: 'get',
        params: {
            'date': date
        }
    })
}

// 查询缺件信息列表
export function listMissingParts(query) {
    return request({
        url: '/production/scheduling/listMissingParts',
        method: 'get',
        params: query
    })
}
