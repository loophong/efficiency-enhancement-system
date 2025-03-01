import request from '@/utils/request'

// 查询特殊情况列表
export function listCases(query) {
    return request({
        url: '/production/special/list',
        method: 'get',
        params: query
    })
}

// 查询特殊情况详细
export function getCases(id) {
    return request({
        url: '/production/special/' + id,
        method: 'get'
    })
}

// 新增特殊情况
export function addCases(data) {
    return request({
        url: '/production/special',
        method: 'post',
        data: data
    })
}

// 修改特殊情况
export function updateCases(data) {
    return request({
        url: '/production/special',
        method: 'put',
        data: data
    })
}

// 删除特殊情况
export function delCases(id) {
    return request({
        url: '/production/special/' + id,
        method: 'delete'
    })
}
