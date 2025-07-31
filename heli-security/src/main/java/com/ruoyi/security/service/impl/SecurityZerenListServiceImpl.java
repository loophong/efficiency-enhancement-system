package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityZerenListMapper;
import com.ruoyi.security.domain.SecurityZerenList;
import com.ruoyi.security.service.ISecurityZerenListService;

/**
 * 安全责任清单Service业务层处理
 * 
 * @author wang
 * @date 2025-07-18
 */
@Service
public class SecurityZerenListServiceImpl implements ISecurityZerenListService 
{
    @Autowired
    private SecurityZerenListMapper securityZerenListMapper;

    /**
     * 查询安全责任清单
     * 
     * @param id 安全责任清单主键
     * @return 安全责任清单
     */
    @Override
    public SecurityZerenList selectSecurityZerenListById(Long id)
    {
        return securityZerenListMapper.selectSecurityZerenListById(id);
    }

    /**
     * 查询安全责任清单列表
     * 
     * @param securityZerenList 安全责任清单
     * @return 安全责任清单
     */
    @Override
    public List<SecurityZerenList> selectSecurityZerenListList(SecurityZerenList securityZerenList)
    {
        return securityZerenListMapper.selectSecurityZerenListList(securityZerenList);
    }

    /**
     * 新增安全责任清单
     * 
     * @param securityZerenList 安全责任清单
     * @return 结果
     */
    @Override
    public int insertSecurityZerenList(SecurityZerenList securityZerenList)
    {
        return securityZerenListMapper.insertSecurityZerenList(securityZerenList);
    }

    /**
     * 修改安全责任清单
     * 
     * @param securityZerenList 安全责任清单
     * @return 结果
     */
    @Override
    public int updateSecurityZerenList(SecurityZerenList securityZerenList)
    {
        return securityZerenListMapper.updateSecurityZerenList(securityZerenList);
    }

    /**
     * 批量删除安全责任清单
     * 
     * @param ids 需要删除的安全责任清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityZerenListByIds(Long[] ids)
    {
        return securityZerenListMapper.deleteSecurityZerenListByIds(ids);
    }

    /**
     * 删除安全责任清单信息
     * 
     * @param id 安全责任清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityZerenListById(Long id)
    {
        return securityZerenListMapper.deleteSecurityZerenListById(id);
    }
}
