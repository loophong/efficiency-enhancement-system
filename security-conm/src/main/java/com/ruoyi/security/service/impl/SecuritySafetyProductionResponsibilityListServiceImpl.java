package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySafetyProductionResponsibilityListMapper;
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;
import com.ruoyi.security.service.ISecuritySafetyProductionResponsibilityListService;

/**
 * 安全生产责任制清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-12
 */
@Service
public class SecuritySafetyProductionResponsibilityListServiceImpl implements ISecuritySafetyProductionResponsibilityListService 
{
    @Autowired
    private SecuritySafetyProductionResponsibilityListMapper securitySafetyProductionResponsibilityListMapper;

    /**
     * 查询安全生产责任制清单
     * 
     * @param id 安全生产责任制清单主键
     * @return 安全生产责任制清单
     */
    @Override
    public SecuritySafetyProductionResponsibilityList selectSecuritySafetyProductionResponsibilityListById(String id)
    {
        return securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListById(id);
    }

    /**
     * 查询安全生产责任制清单列表
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 安全生产责任制清单
     */
    @Override
    public List<SecuritySafetyProductionResponsibilityList> selectSecuritySafetyProductionResponsibilityListList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 新增安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    @Override
    public int insertSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.insertSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 修改安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    @Override
    public int updateSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.updateSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 批量删除安全生产责任制清单
     * 
     * @param ids 需要删除的安全生产责任制清单主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyProductionResponsibilityListByIds(String[] ids)
    {
        return securitySafetyProductionResponsibilityListMapper.deleteSecuritySafetyProductionResponsibilityListByIds(ids);
    }

    /**
     * 删除安全生产责任制清单信息
     * 
     * @param id 安全生产责任制清单主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyProductionResponsibilityListById(String id)
    {
        return securitySafetyProductionResponsibilityListMapper.deleteSecuritySafetyProductionResponsibilityListById(id);
    }
}
