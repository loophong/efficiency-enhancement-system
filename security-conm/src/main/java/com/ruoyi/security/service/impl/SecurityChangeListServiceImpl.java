package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityChangeListMapper;
import com.ruoyi.security.domain.SecurityChangeList;
import com.ruoyi.security.service.ISecurityChangeListService;

/**
 * 变更管理Service业务层处理
 * 
 * @author wnag
 * @date 2025-07-21
 */
@Service
public class SecurityChangeListServiceImpl implements ISecurityChangeListService 
{
    @Autowired
    private SecurityChangeListMapper securityChangeListMapper;

    /**
     * 查询变更管理
     * 
     * @param id 变更管理主键
     * @return 变更管理
     */
    @Override
    public SecurityChangeList selectSecurityChangeListById(Long id)
    {
        return securityChangeListMapper.selectSecurityChangeListById(id);
    }

    /**
     * 查询变更管理列表
     * 
     * @param securityChangeList 变更管理
     * @return 变更管理
     */
    @Override
    public List<SecurityChangeList> selectSecurityChangeListList(SecurityChangeList securityChangeList)
    {
        return securityChangeListMapper.selectSecurityChangeListList(securityChangeList);
    }

    /**
     * 新增变更管理
     * 
     * @param securityChangeList 变更管理
     * @return 结果
     */
    @Override
    public int insertSecurityChangeList(SecurityChangeList securityChangeList)
    {
        return securityChangeListMapper.insertSecurityChangeList(securityChangeList);
    }

    /**
     * 修改变更管理
     * 
     * @param securityChangeList 变更管理
     * @return 结果
     */
    @Override
    public int updateSecurityChangeList(SecurityChangeList securityChangeList)
    {
        return securityChangeListMapper.updateSecurityChangeList(securityChangeList);
    }

    /**
     * 批量删除变更管理
     * 
     * @param ids 需要删除的变更管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityChangeListByIds(Long[] ids)
    {
        return securityChangeListMapper.deleteSecurityChangeListByIds(ids);
    }

    /**
     * 删除变更管理信息
     * 
     * @param id 变更管理主键
     * @return 结果
     */
    @Override
    public int deleteSecurityChangeListById(Long id)
    {
        return securityChangeListMapper.deleteSecurityChangeListById(id);
    }
}
