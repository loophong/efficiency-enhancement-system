package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityRelationList;

/**
 * 相关方检查记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface SecurityRelationListMapper 
{
    /**
     * 查询相关方检查记录
     * 
     * @param id 相关方检查记录主键
     * @return 相关方检查记录
     */
    public SecurityRelationList selectSecurityRelationListById(String id);

    /**
     * 查询相关方检查记录列表
     * 
     * @param securityRelationList 相关方检查记录
     * @return 相关方检查记录集合
     */
    public List<SecurityRelationList> selectSecurityRelationListList(SecurityRelationList securityRelationList);

    /**
     * 新增相关方检查记录
     * 
     * @param securityRelationList 相关方检查记录
     * @return 结果
     */
    public int insertSecurityRelationList(SecurityRelationList securityRelationList);

    /**
     * 修改相关方检查记录
     * 
     * @param securityRelationList 相关方检查记录
     * @return 结果
     */
    public int updateSecurityRelationList(SecurityRelationList securityRelationList);

    /**
     * 删除相关方检查记录
     * 
     * @param id 相关方检查记录主键
     * @return 结果
     */
    public int deleteSecurityRelationListById(String id);

    /**
     * 批量删除相关方检查记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityRelationListByIds(String[] ids);
}
