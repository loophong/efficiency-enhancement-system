import request from '@/utils/request'

// 查询安全投入计划列表
export function listInvestmentPlan(query) {
  return request({
    url: '/security/InvestmentPlan/list',
    method: 'get',
    params: query
  })
}

// 查询安全投入计划详细
export function getInvestmentPlan(serialNumber) {
  return request({
    url: '/security/InvestmentPlan/' + serialNumber,
    method: 'get'
  })
}

// 新增安全投入计划
export function addInvestmentPlan(data) {
  return request({
    url: '/security/InvestmentPlan',
    method: 'post',
    data: data
  })
}

// 修改安全投入计划
export function updateInvestmentPlan(data) {
  return request({
    url: '/security/InvestmentPlan',
    method: 'put',
    data: data
  })
}

// 删除安全投入计划
export function delInvestmentPlan(serialNumber) {
  return request({
    url: '/security/InvestmentPlan/' + serialNumber,
    method: 'delete'
  })
}

// 导出安全投入计划
export function exportInvestmentPlan(query) {
  return request({
    url: '/security/InvestmentPlan/export',
    method: 'get',
    params: query
  })
}

// 获取安全投入计划导入模板
export function importTemplate() {
  return request({
    url: '/security/InvestmentPlan/importTemplate',
    method: 'get'
  })
}

// 导入安全投入计划数据
export function importData(data) {
  return request({
    url: '/security/InvestmentPlan/importData',
    method: 'post',
    data: data
  })
}
