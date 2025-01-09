import request from '@/utils/request'

// 查询主计划表列表
export function listMainPlanTable(query) {
    return request({
        url: '/production/mainPlanTable/list',
        method: 'get',
        params: query
    })
}

// 查询主计划表详细
export function getMainPlanTable(id) {
    return request({
        url: '/production/mainPlanTable/' + id,
        method: 'get'
    })
}

// 新增主计划表
export function addMainPlanTable(data) {
    return request({
        url: '/production/mainPlanTable',
        method: 'post',
        data: data
    })
}

// 修改主计划表
export function updateMainPlanTable(data) {
    return request({
        url: '/production/mainPlanTable',
        method: 'put',
        data: data
    })
}

// 删除主计划表
export function delMainPlanTable(id) {
    return request({
        url: '/production/mainPlanTable/' + id,
        method: 'delete'
    })
}


// 上传主计划表
export function importFile(formData) {
    return request({
        url: '/production/mainPlanTable/import',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
