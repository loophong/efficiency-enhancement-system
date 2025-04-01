import request from '@/utils/request'

// 查询菜单树
export function getSecuritySysMenuTree() {
  return request({
    url: '/security/sysmenu/tree',
    method: 'get'
  })
}

// 新增菜单
export function addSecuritySysMenu(data) {
  return request({
    url: '/security/sysmenu',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function updateSecuritySysMenu(data) {
  return request({
    url: '/security/sysmenu',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delSecuritySysMenu(menuId) {
  return request({
    url: '/security/sysmenu/' + menuId,
    method: 'delete'
  })
}

// 查询菜单详细
export function getSecuritySysMenu(menuId) {
  return request({
    url: '/security/sysmenu/' + menuId,
    method: 'get'
  })
}