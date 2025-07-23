package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditCheckList;

/**
 * 内审检查Service接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface ISecurityInternalAuditCheckListService 
{
    /**
     * 查询内审检查
     * 
     * @param id 内审检查主键
     * @return 内审检查
     */
    public SecurityInternalAuditCheckList selectSecurityInternalAuditCheckListById(String id);

    /**
     * 查询内审检查列表
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 内审检查集合
     */
    public List<SecurityInternalAuditCheckList> selectSecurityInternalAuditCheckListList(SecurityInternalAuditCheckList securityInternalAuditCheckList);

    /**
     * 新增内审检查
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 结果
     */
    public int insertSecurityInternalAuditCheckList(SecurityInternalAuditCheckList securityInternalAuditCheckList);

    /**
     * 修改内审检查
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 结果
     */
    public int updateSecurityInternalAuditCheckList(SecurityInternalAuditCheckList securityInternalAuditCheckList);

    /**
     * 批量删除内审检查
     * 
     * @param ids 需要删除的内审检查主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditCheckListByIds(String[] ids);

    /**
     * 删除内审检查信息
     * 
     * @param id 内审检查主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditCheckListById(String id);
}
