package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardSourceListMapper;
import com.ruoyi.security.domain.SecurityHazardSourceList;
import com.ruoyi.security.service.ISecurityHazardSourceListService;

/**
 * 危险源清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-01
 */
@Service
public class SecurityHazardSourceListServiceImpl implements ISecurityHazardSourceListService 
{
    @Autowired
    private SecurityHazardSourceListMapper securityHazardSourceListMapper;

    /**
     * 查询危险源清单
     * 
     * @param id 危险源清单主键
     * @return 危险源清单
     */
    @Override
    public SecurityHazardSourceList selectSecurityHazardSourceListById(Long id)
    {
        return securityHazardSourceListMapper.selectSecurityHazardSourceListById(id);
    }

    /**
     * 查询危险源清单列表
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 危险源清单
     */
    @Override
    public List<SecurityHazardSourceList> selectSecurityHazardSourceListList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.selectSecurityHazardSourceListList(securityHazardSourceList);
    }

    /**
     * 新增危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    @Override
    public int insertSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.insertSecurityHazardSourceList(securityHazardSourceList);
    }

    /**
     * 修改危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    @Override
    public int updateSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList)
    {
        return securityHazardSourceListMapper.updateSecurityHazardSourceList(securityHazardSourceList);
    }

    /**
     * 批量删除危险源清单
     * 
     * @param ids 需要删除的危险源清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardSourceListByIds(Long[] ids)
    {
        return securityHazardSourceListMapper.deleteSecurityHazardSourceListByIds(ids);
    }

    /**
     * 删除危险源清单信息
     * 
     * @param id 危险源清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardSourceListById(Long id)
    {
        return securityHazardSourceListMapper.deleteSecurityHazardSourceListById(id);
    }
}
