package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditReport1;

/**
 * 内审报告Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityInternalAuditReport1Mapper 
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
     * 删除内审报告
     * 
     * @param id 内审报告主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditReport1ById(String id);

    /**
     * 批量删除内审报告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditReport1ByIds(String[] ids);
}
