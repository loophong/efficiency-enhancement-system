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
