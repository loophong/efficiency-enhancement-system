package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;

/**
 * 安全生产责任制清单Service接口
 * 
 * @author ruoyi
 * @date 2025-03-12
 */
public interface ISecuritySafetyProductionResponsibilityListService 
{
    /**
     * 查询安全生产责任制清单
     * 
     * @param id 安全生产责任制清单主键
     * @return 安全生产责任制清单
     */
    public SecuritySafetyProductionResponsibilityList selectSecuritySafetyProductionResponsibilityListById(String id);

    /**
     * 查询安全生产责任制清单列表
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 安全生产责任制清单集合
     */
    public List<SecuritySafetyProductionResponsibilityList> selectSecuritySafetyProductionResponsibilityListList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList);

    /**
     * 新增安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    public int insertSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList);

    /**
     * 修改安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    public int updateSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList);

    /**
     * 批量删除安全生产责任制清单
     * 
     * @param ids 需要删除的安全生产责任制清单主键集合
     * @return 结果
     */
    public int deleteSecuritySafetyProductionResponsibilityListByIds(String[] ids);

    /**
     * 删除安全生产责任制清单信息
     * 
     * @param id 安全生产责任制清单主键
     * @return 结果
     */
    public int deleteSecuritySafetyProductionResponsibilityListById(String id);
}
