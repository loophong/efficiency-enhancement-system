package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelatedPartyPreManagement;

/**
 * 相关方前置管理Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityRelatedPartyPreManagementMapper 
{
    /**
     * 查询相关方前置管理
     * 
     * @param id 相关方前置管理主键
     * @return 相关方前置管理
     */
    public SecurityRelatedPartyPreManagement selectSecurityRelatedPartyPreManagementById(String id);

    /**
     * 查询相关方前置管理列表
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 相关方前置管理集合
     */
    public List<SecurityRelatedPartyPreManagement> selectSecurityRelatedPartyPreManagementList(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement);

    /**
     * 新增相关方前置管理
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 结果
     */
    public int insertSecurityRelatedPartyPreManagement(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement);

    /**
     * 修改相关方前置管理
     * 
     * @param securityRelatedPartyPreManagement 相关方前置管理
     * @return 结果
     */
    public int updateSecurityRelatedPartyPreManagement(SecurityRelatedPartyPreManagement securityRelatedPartyPreManagement);

    /**
     * 删除相关方前置管理
     * 
     * @param id 相关方前置管理主键
     * @return 结果
     */
    public int deleteSecurityRelatedPartyPreManagementById(String id);

    /**
     * 批量删除相关方前置管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRelatedPartyPreManagementByIds(String[] ids);
}
