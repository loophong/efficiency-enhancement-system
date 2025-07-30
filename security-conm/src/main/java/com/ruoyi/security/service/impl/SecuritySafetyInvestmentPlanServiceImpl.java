package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySafetyInvestmentPlanMapper;
import com.ruoyi.security.domain.SecuritySafetyInvestmentPlan;
import com.ruoyi.security.service.ISecuritySafetyInvestmentPlanService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Validator;

/**
 * 安全投入计划Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecuritySafetyInvestmentPlanServiceImpl implements ISecuritySafetyInvestmentPlanService 
{
    private static final Logger log = LoggerFactory.getLogger(SecuritySafetyInvestmentPlanServiceImpl.class);

    @Autowired
    private SecuritySafetyInvestmentPlanMapper securitySafetyInvestmentPlanMapper;
    
    @Autowired
    protected Validator validator;

    /**
     * 查询安全投入计划
     * 
     * @param serialNumber 安全投入计划主键
     * @return 安全投入计划
     */
    @Override
    public SecuritySafetyInvestmentPlan selectSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber)
    {
        return securitySafetyInvestmentPlanMapper.selectSecuritySafetyInvestmentPlanBySerialNumber(serialNumber);
    }

    /**
     * 查询安全投入计划列表
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 安全投入计划
     */
    @Override
    public List<SecuritySafetyInvestmentPlan> selectSecuritySafetyInvestmentPlanList(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.selectSecuritySafetyInvestmentPlanList(securitySafetyInvestmentPlan);
    }

    /**
     * 新增安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    @Override
    public int insertSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.insertSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan);
    }

    /**
     * 修改安全投入计划
     * 
     * @param securitySafetyInvestmentPlan 安全投入计划
     * @return 结果
     */
    @Override
    public int updateSecuritySafetyInvestmentPlan(SecuritySafetyInvestmentPlan securitySafetyInvestmentPlan)
    {
        return securitySafetyInvestmentPlanMapper.updateSecuritySafetyInvestmentPlan(securitySafetyInvestmentPlan);
    }

    /**
     * 批量删除安全投入计划
     * 
     * @param serialNumbers 需要删除的安全投入计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyInvestmentPlanBySerialNumbers(Long[] serialNumbers)
    {
        return securitySafetyInvestmentPlanMapper.deleteSecuritySafetyInvestmentPlanBySerialNumbers(serialNumbers);
    }

    /**
     * 删除安全投入计划信息
     * 
     * @param serialNumber 安全投入计划主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyInvestmentPlanBySerialNumber(Long serialNumber)
    {
        return securitySafetyInvestmentPlanMapper.deleteSecuritySafetyInvestmentPlanBySerialNumber(serialNumber);
    }
    
    /**
     * 导入安全投入计划数据
     * 
     * @param investmentPlanList 安全投入计划数据列表
     * @param updateSupport 是否支持更新
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importInvestmentPlan(List<SecuritySafetyInvestmentPlan> investmentPlanList, boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(investmentPlanList) || investmentPlanList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecuritySafetyInvestmentPlan investmentPlan : investmentPlanList)
        {
            try
            {
                // 验证是否存在这个项目
                SecuritySafetyInvestmentPlan existPlan = new SecuritySafetyInvestmentPlan();
                existPlan.setProjectName(investmentPlan.getProjectName());
                List<SecuritySafetyInvestmentPlan> existList = securitySafetyInvestmentPlanMapper.selectSecuritySafetyInvestmentPlanList(existPlan);
                
                if (!existList.isEmpty() && updateSupport)
                {
                    // 更新已有数据
                    SecuritySafetyInvestmentPlan existingPlan = existList.get(0);
                    investmentPlan.setSerialNumber(existingPlan.getSerialNumber());
                    BeanValidators.validateWithException(validator, investmentPlan);
                    securitySafetyInvestmentPlanMapper.updateSecuritySafetyInvestmentPlan(investmentPlan);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、项目 ").append(investmentPlan.getProjectName()).append(" 更新成功");
                }
                else if (!existList.isEmpty() && !updateSupport)
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、项目 ").append(investmentPlan.getProjectName()).append(" 已存在");
                }
                else
                {
                    BeanValidators.validateWithException(validator, investmentPlan);
                    securitySafetyInvestmentPlanMapper.insertSecuritySafetyInvestmentPlan(investmentPlan);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、项目 ").append(investmentPlan.getProjectName()).append(" 导入成功");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、项目 " + investmentPlan.getProjectName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
