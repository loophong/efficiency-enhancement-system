import request from '@/utils/request'

// 查询我的消息
export function mine(query) {
  return request({
    url: '/production/notifications/mine',
    method: 'get',
    params: query
  })
}

// 查询消息通知列表
export function listNotifications(query) {
  return request({
    url: '/production/notifications/list',
    method: 'get',
    params: query
  })
}

// 查询消息通知详细
export function getNotifications(id) {
  return request({
    url: '/production/notifications/' + id,
    method: 'get'
  })
}

// 新增消息通知
export function addNotifications(data) {
  return request({
    url: '/production/notifications',
    method: 'post',
    data: data
  })
}

// 修改消息通知
export function updateNotifications(data) {
  return request({
    url: '/production/notifications',
    method: 'put',
    data: data
  })
}

// 删除消息通知
export function delNotifications(id) {
  return request({
    url: '/production/notifications/' + id,
    method: 'delete'
  })
}
