package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityChangeList;

/**
 * 变更管理Service接口
 * 
 * @author wnag
 * @date 2025-07-21
 */
public interface ISecurityChangeListService 
{
    /**
     * 查询变更管理
     * 
     * @param id 变更管理主键
     * @return 变更管理
     */
    public SecurityChangeList selectSecurityChangeListById(Long id);

    /**
     * 查询变更管理列表
     * 
     * @param securityChangeList 变更管理
     * @return 变更管理集合
     */
    public List<SecurityChangeList> selectSecurityChangeListList(SecurityChangeList securityChangeList);

    /**
     * 新增变更管理
     * 
     * @param securityChangeList 变更管理
     * @return 结果
     */
    public int insertSecurityChangeList(SecurityChangeList securityChangeList);

    /**
     * 修改变更管理
     * 
     * @param securityChangeList 变更管理
     * @return 结果
     */
    public int updateSecurityChangeList(SecurityChangeList securityChangeList);

    /**
     * 批量删除变更管理
     * 
     * @param ids 需要删除的变更管理主键集合
     * @return 结果
     */
    public int deleteSecurityChangeListByIds(Long[] ids);

    /**
     * 删除变更管理信息
     * 
     * @param id 变更管理主键
     * @return 结果
     */
    public int deleteSecurityChangeListById(Long id);
}
