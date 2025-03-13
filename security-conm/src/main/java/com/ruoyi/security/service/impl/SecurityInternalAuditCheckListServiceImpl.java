package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityInternalAuditCheckListMapper;
import com.ruoyi.security.domain.SecurityInternalAuditCheckList;
import com.ruoyi.security.service.ISecurityInternalAuditCheckListService;

/**
 * 内审检查Service业务层处理
 * 
 * @author wang
 * @date 2025-03-12
 */
@Service
public class SecurityInternalAuditCheckListServiceImpl implements ISecurityInternalAuditCheckListService 
{
    @Autowired
    private SecurityInternalAuditCheckListMapper securityInternalAuditCheckListMapper;

    /**
     * 查询内审检查
     * 
     * @param id 内审检查主键
     * @return 内审检查
     */
    @Override
    public SecurityInternalAuditCheckList selectSecurityInternalAuditCheckListById(String id)
    {
        return securityInternalAuditCheckListMapper.selectSecurityInternalAuditCheckListById(id);
    }

    /**
     * 查询内审检查列表
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 内审检查
     */
    @Override
    public List<SecurityInternalAuditCheckList> selectSecurityInternalAuditCheckListList(SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        return securityInternalAuditCheckListMapper.selectSecurityInternalAuditCheckListList(securityInternalAuditCheckList);
    }

    /**
     * 新增内审检查
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 结果
     */
    @Override
    public int insertSecurityInternalAuditCheckList(SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        return securityInternalAuditCheckListMapper.insertSecurityInternalAuditCheckList(securityInternalAuditCheckList);
    }

    /**
     * 修改内审检查
     * 
     * @param securityInternalAuditCheckList 内审检查
     * @return 结果
     */
    @Override
    public int updateSecurityInternalAuditCheckList(SecurityInternalAuditCheckList securityInternalAuditCheckList)
    {
        return securityInternalAuditCheckListMapper.updateSecurityInternalAuditCheckList(securityInternalAuditCheckList);
    }

    /**
     * 批量删除内审检查
     * 
     * @param ids 需要删除的内审检查主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditCheckListByIds(String[] ids)
    {
        return securityInternalAuditCheckListMapper.deleteSecurityInternalAuditCheckListByIds(ids);
    }

    /**
     * 删除内审检查信息
     * 
     * @param id 内审检查主键
     * @return 结果
     */
    @Override
    public int deleteSecurityInternalAuditCheckListById(String id)
    {
        return securityInternalAuditCheckListMapper.deleteSecurityInternalAuditCheckListById(id);
    }
}
