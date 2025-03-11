import request from '@/utils/request'

// 查询生产文件列表
export function listFile(query) {
    return request({
        url: '/production/file/list',
        method: 'get',
        params: query
    })
}

// 查询生产文件详细
export function getFile(id) {
    return request({
        url: '/production/file/' + id,
        method: 'get'
    })
}

// 新增生产文件
export function addFile(data) {
    return request({
        url: '/production/file',
        method: 'post',
        data: data
    })
}

// 修改生产文件
export function updateFile(data) {
    return request({
        url: '/production/file',
        method: 'put',
        data: data
    })
}

// 删除生产文件
export function delFile(id) {
    return request({
        url: '/production/file/' + id,
        method: 'delete'
    })
}
