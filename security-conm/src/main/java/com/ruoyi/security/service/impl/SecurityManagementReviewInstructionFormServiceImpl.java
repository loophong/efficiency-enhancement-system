package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityManagementReviewInstructionFormMapper;
import com.ruoyi.security.domain.SecurityManagementReviewInstructionForm;
import com.ruoyi.security.service.ISecurityManagementReviewInstructionFormService;

/**
 * 管理评审纠正和改进指令Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityManagementReviewInstructionFormServiceImpl implements ISecurityManagementReviewInstructionFormService 
{
    @Autowired
    private SecurityManagementReviewInstructionFormMapper securityManagementReviewInstructionFormMapper;

    /**
     * 查询管理评审纠正和改进指令
     * 
     * @param id 管理评审纠正和改进指令主键
     * @return 管理评审纠正和改进指令
     */
    @Override
    public SecurityManagementReviewInstructionForm selectSecurityManagementReviewInstructionFormById(String id)
    {
        return securityManagementReviewInstructionFormMapper.selectSecurityManagementReviewInstructionFormById(id);
    }

    /**
     * 查询管理评审纠正和改进指令列表
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 管理评审纠正和改进指令
     */
    @Override
    public List<SecurityManagementReviewInstructionForm> selectSecurityManagementReviewInstructionFormList(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        return securityManagementReviewInstructionFormMapper.selectSecurityManagementReviewInstructionFormList(securityManagementReviewInstructionForm);
    }

    /**
     * 新增管理评审纠正和改进指令
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 结果
     */
    @Override
    public int insertSecurityManagementReviewInstructionForm(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        return securityManagementReviewInstructionFormMapper.insertSecurityManagementReviewInstructionForm(securityManagementReviewInstructionForm);
    }

    /**
     * 修改管理评审纠正和改进指令
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 结果
     */
    @Override
    public int updateSecurityManagementReviewInstructionForm(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm)
    {
        return securityManagementReviewInstructionFormMapper.updateSecurityManagementReviewInstructionForm(securityManagementReviewInstructionForm);
    }

    /**
     * 批量删除管理评审纠正和改进指令
     * 
     * @param ids 需要删除的管理评审纠正和改进指令主键
     * @return 结果
     */
    @Override
    public int deleteSecurityManagementReviewInstructionFormByIds(String[] ids)
    {
        return securityManagementReviewInstructionFormMapper.deleteSecurityManagementReviewInstructionFormByIds(ids);
    }

    /**
     * 删除管理评审纠正和改进指令信息
     * 
     * @param id 管理评审纠正和改进指令主键
     * @return 结果
     */
    @Override
    public int deleteSecurityManagementReviewInstructionFormById(String id)
    {
        return securityManagementReviewInstructionFormMapper.deleteSecurityManagementReviewInstructionFormById(id);
    }
}
