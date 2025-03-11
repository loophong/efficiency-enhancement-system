package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditProgram;

/**
 * 内审方案Service接口
 * 
 * @author ruoyi
 * @date 2025-03-10
 */
public interface ISecurityInternalAuditProgramService 
{
    /**
     * 查询内审方案
     * 
     * @param id 内审方案主键
     * @return 内审方案
     */
    public SecurityInternalAuditProgram selectSecurityInternalAuditProgramById(String id);

    /**
     * 查询内审方案列表
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 内审方案集合
     */
    public List<SecurityInternalAuditProgram> selectSecurityInternalAuditProgramList(SecurityInternalAuditProgram securityInternalAuditProgram);

    /**
     * 新增内审方案
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 结果
     */
    public int insertSecurityInternalAuditProgram(SecurityInternalAuditProgram securityInternalAuditProgram);

    /**
     * 修改内审方案
     * 
     * @param securityInternalAuditProgram 内审方案
     * @return 结果
     */
    public int updateSecurityInternalAuditProgram(SecurityInternalAuditProgram securityInternalAuditProgram);

    /**
     * 批量删除内审方案
     * 
     * @param ids 需要删除的内审方案主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditProgramByIds(String[] ids);

    /**
     * 删除内审方案信息
     * 
     * @param id 内审方案主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditProgramById(String id);
}
