package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelatedPartyPreManagementMapper;
import com.ruoyi.security.domain.SecurityRelatedPartyPreManagement;
import com.ruoyi.security.service.ISecurityRelatedPartyPreManagementService;

/**
 * 相关方前置管理Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecurityRelatedPartyPreManagementServiceImpl implements ISecurityRelatedPartyPreManagementService 
{
    @Autowired
    private SecurityRelatedPartyPreManagementMapper securityRelatedPartyPreManagementMapper;

    /**
     * 查询相关方前置管理
     * 
     * @param id 相关方前置管理主键
     * @return 相关方前置管理
     */
    @Override
    public SecurityRelatedPartyPreManagement selectSecurityRelatedPartyPreManagementById(String id)
    {
        return securityRelatedPartyPreManagementMapper.selectSecurityRelatedPartyPreManagementById(id);
    }

    /**
     * 查询相关方前置管理列表
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 相关方前置管理
     */
    @Override
    public List<SecurityRelatedPartyPreManagement> selectSecurityRelatedPartyPreManagementList(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        return securityRelatedPartyPreManagementMapper.selectSecurityRelatedPartyPreManagementList(securityRelatedPartyPreManagement);
    }

    /**
     * 新增相关方前置管理
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 结果
     */
    @Override
    public int insertSecurityRelatedPartyPreManagement(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        return securityRelatedPartyPreManagementMapper.insertSecurityRelatedPartyPreManagement(securityRelatedPartyPreManagement);
    }

    /**
     * 修改相关方前置管理
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 结果
     */
    @Override
    public int updateSecurityRelatedPartyPreManagement(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement)
    {
        return securityRelatedPartyPreManagementMapper.updateSecurityRelatedPartyPreManagement(securityRelatedPartyPreManagement);
    }

    /**
     * 批量删除相关方前置管理
     * 
     * @param ids 需要删除的相关方前置管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyPreManagementByIds(String[] ids)
    {
        return securityRelatedPartyPreManagementMapper.deleteSecurityRelatedPartyPreManagementByIds(ids);
    }

    /**
     * 删除相关方前置管理信息
     * 
     * @param id 相关方前置管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelatedPartyPreManagementById(String id)
    {
        return securityRelatedPartyPreManagementMapper.deleteSecurityRelatedPartyPreManagementById(id);
    }
}
