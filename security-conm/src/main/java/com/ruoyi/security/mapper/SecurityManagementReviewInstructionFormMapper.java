package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityManagementReviewInstructionForm;

/**
 * 管理评审纠正和改进指令Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityManagementReviewInstructionFormMapper 
{
    /**
     * 查询管理评审纠正和改进指令
     * 
     * @param id 管理评审纠正和改进指令主键
     * @return 管理评审纠正和改进指令
     */
    public SecurityManagementReviewInstructionForm selectSecurityManagementReviewInstructionFormById(String id);

    /**
     * 查询管理评审纠正和改进指令列表
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 管理评审纠正和改进指令集合
     */
    public List<SecurityManagementReviewInstructionForm> selectSecurityManagementReviewInstructionFormList(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm);

    /**
     * 新增管理评审纠正和改进指令
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 结果
     */
    public int insertSecurityManagementReviewInstructionForm(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm);

    /**
     * 修改管理评审纠正和改进指令
     * 
     * @param securityManagementReviewInstructionForm 管理评审纠正和改进指令
     * @return 结果
     */
    public int updateSecurityManagementReviewInstructionForm(SecurityManagementReviewInstructionForm securityManagementReviewInstructionForm);

    /**
     * 删除管理评审纠正和改进指令
     * 
     * @param id 管理评审纠正和改进指令主键
     * @return 结果
     */
    public int deleteSecurityManagementReviewInstructionFormById(String id);

    /**
     * 批量删除管理评审纠正和改进指令
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityManagementReviewInstructionFormByIds(String[] ids);
}
