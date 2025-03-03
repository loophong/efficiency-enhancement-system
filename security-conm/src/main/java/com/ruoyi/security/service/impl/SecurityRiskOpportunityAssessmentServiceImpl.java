package com.ruoyi.security.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRiskOpportunityAssessmentMapper;
import com.ruoyi.security.domain.SecurityRiskOpportunityAssessment;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 风险和机遇评估及控制措施Service业务层处理
 *
 * @author wang
 * @date 2025-02-23
 */
@Service
public class SecurityRiskOpportunityAssessmentServiceImpl implements ISecurityRiskOpportunityAssessmentService
{
    @Autowired
    private SecurityRiskOpportunityAssessmentMapper securityRiskOpportunityAssessmentMapper;
    private Validator validator;
    private static final Logger log = LoggerFactory.getLogger(SecurityRiskOpportunityAssessmentServiceImpl.class);
    /**
     * 查询风险和机遇评估及控制措施
     *
     * @param id 风险和机遇评估及控制措施主键
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public SecurityRiskOpportunityAssessment selectSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentById(id);
    }

    /**
     * 查询风险和机遇评估及控制措施列表
     *
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 风险和机遇评估及控制措施
     */
    @Override
    public List<SecurityRiskOpportunityAssessment> selectSecurityRiskOpportunityAssessmentList(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.selectSecurityRiskOpportunityAssessmentList(securityRiskOpportunityAssessment);
    }

    /**
     * 新增风险和机遇评估及控制措施
     *
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int insertSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.insertSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 修改风险和机遇评估及控制措施
     *
     * @param securityRiskOpportunityAssessment 风险和机遇评估及控制措施
     * @return 结果
     */
    @Override
    public int updateSecurityRiskOpportunityAssessment(SecurityRiskOpportunityAssessment securityRiskOpportunityAssessment)
    {
        return securityRiskOpportunityAssessmentMapper.updateSecurityRiskOpportunityAssessment(securityRiskOpportunityAssessment);
    }

    /**
     * 批量删除风险和机遇评估及控制措施
     *
     * @param ids 需要删除的风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentByIds(Long[] ids)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentByIds(ids);
    }

    /**
     * 删除风险和机遇评估及控制措施信息
     *
     * @param id 风险和机遇评估及控制措施主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRiskOpportunityAssessmentById(Long id)
    {
        return securityRiskOpportunityAssessmentMapper.deleteSecurityRiskOpportunityAssessmentById(id);
    }

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */


    @Override
    public String importsalary(List<SecurityRiskOpportunityAssessment> userList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(userList) || userList.size() == 0)
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityRiskOpportunityAssessment user : userList)
        {
//            try
//            {
//                BeanValidators.validateWithException(validator, user);
//                user.setCreateBy(operName);
//                this.insertSecurityRiskOpportunityAssessment(user);
//                successNum++;
//                successMsg.append("<br/>" + successNum + "、账号 " + user.getUserName() + " 导入成功");
//
//            }
//            catch (Exception e)
//            {
//                failureNum++;
//                String msg = "<br/>" + failureNum + "、账号 " + user.getUserName() + " 导入失败：";
//                failureMsg.append(msg + e.getMessage());
//                log.error(msg, e);
//            }
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

