import request from '@/utils/request'

// 查询每日使用的产能列表
export function listDailyCapacity(query) {
    return request({
        url: '/production/dailyCapacity/list',
        method: 'get',
        params: query
    })
}

// 查询每日使用的产能详细
export function getDailyCapacity(id) {
    return request({
        url: '/production/dailyCapacity/' + id,
        method: 'get'
    })
}

// 新增每日使用的产能
export function addDailyCapacity(data) {
    return request({
        url: '/production/dailyCapacity',
        method: 'post',
        data: data
    })
}

// 修改每日使用的产能
export function updateDailyCapacity(data) {
    return request({
        url: '/production/dailyCapacity',
        method: 'put',
        data: data
    })
}

// 删除每日使用的产能
export function delDailyCapacity(id) {
    return request({
        url: '/production/dailyCapacity/' + id,
        method: 'delete'
    })
}
