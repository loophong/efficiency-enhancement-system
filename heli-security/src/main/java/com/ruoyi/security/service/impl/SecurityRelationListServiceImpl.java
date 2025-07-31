package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelationListMapper;
import com.ruoyi.security.domain.SecurityRelationList;
import com.ruoyi.security.service.ISecurityRelationListService;

/**
 * 相关方检查记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityRelationListServiceImpl implements ISecurityRelationListService 
{
    @Autowired
    private SecurityRelationListMapper securityRelationListMapper;

    /**
     * 查询相关方检查记录
     * 
     * @param id 相关方检查记录主键
     * @return 相关方检查记录
     */
    @Override
    public SecurityRelationList selectSecurityRelationListById(String id)
    {
        return securityRelationListMapper.selectSecurityRelationListById(id);
    }

    /**
     * 查询相关方检查记录列表
     * 
     * @param securityRelationList 相关方检查记录
     * @return 相关方检查记录
     */
    @Override
    public List<SecurityRelationList> selectSecurityRelationListList(SecurityRelationList securityRelationList)
    {
        return securityRelationListMapper.selectSecurityRelationListList(securityRelationList);
    }

    /**
     * 新增相关方检查记录
     * 
     * @param securityRelationList 相关方检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityRelationList(SecurityRelationList securityRelationList)
    {
        return securityRelationListMapper.insertSecurityRelationList(securityRelationList);
    }

    /**
     * 修改相关方检查记录
     * 
     * @param securityRelationList 相关方检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityRelationList(SecurityRelationList securityRelationList)
    {
        return securityRelationListMapper.updateSecurityRelationList(securityRelationList);
    }

    /**
     * 批量删除相关方检查记录
     * 
     * @param ids 需要删除的相关方检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelationListByIds(String[] ids)
    {
        return securityRelationListMapper.deleteSecurityRelationListByIds(ids);
    }

    /**
     * 删除相关方检查记录信息
     * 
     * @param id 相关方检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelationListById(String id)
    {
        return securityRelationListMapper.deleteSecurityRelationListById(id);
    }
}
