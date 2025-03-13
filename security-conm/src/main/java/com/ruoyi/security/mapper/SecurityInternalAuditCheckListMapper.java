package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityInternalAuditCheckList;

/**
 * 内审检查Mapper接口
 * 
 * @author wang
 * @date 2025-03-12
 */
public interface SecurityInternalAuditCheckListMapper 
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
     * 删除内审检查
     * 
     * @param id 内审检查主键
     * @return 结果
     */
    public int deleteSecurityInternalAuditCheckListById(String id);

    /**
     * 批量删除内审检查
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityInternalAuditCheckListByIds(String[] ids);
}
