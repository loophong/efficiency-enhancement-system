package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditReport1;

/**
 * 内审报告Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityInternalAuditReport1Service 
{
    /**
     * 查询内审报告
     * 
     * @param id 内审报告主键
     * @return 内审报告
     */
    public SecurityInternalAuditReport1 selectSecurityInternalAuditReport1ById(String id);

    /**
     * 查询内审报告列表
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 内审报告集合
     */
    public List<SecurityInternalAuditReport1> selectSecurityInternalAuditReport1List(SecurityInternalAuditReport1 securityInternalAuditReport1);

    /**
     * 新增内审报告
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 结果
     */
    public int insertSecurityInternalAuditReport1(SecurityInternalAuditReport1 securityInternalAuditReport1);

    /**
     * 修改内审报告
     * 
     * @param securityInternalAuditReport1 内审报告
     * @return 结果
     */
    public int updateSecurityInternalAuditReport1(SecurityInternalAuditReport1 securityInternalAuditReport1);

    /**
     * 批量删除内审报告
     * 
     * @param ids 需要删除的内审报告主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditReport1ByIds(String[] ids);

    /**
     * 删除内审报告信息
     * 
     * @param id 内审报告主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditReport1ById(String id);
}
