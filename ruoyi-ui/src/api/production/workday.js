import request from '@/utils/request'

// 查询工作日列表
export function listWorkday() {
    return request({
        url: '/production/workday/list',
        method: 'get'
    })
}

// 查询工作日详细
export function getWorkday(date) {
    return request({
        url: '/production/workday/' + date,
        method: 'get'
    })
}

// 新增工作日
export function addWorkday(data) {
    return request({
        url: '/production/workday',
        method: 'post',
        data: data
    })
}


export function setWorkday(selectDate,status) {
    return request({
        url: '/production/workday',
        method: 'post',
        data: {
            'date': selectDate,
            'status': status
        }
    })
}

