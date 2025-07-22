package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;

/**
 * 安全生产责任制清单Service接口
 * 
 * @author wang
 * @date 2025-07-18
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

    /**
     * 导入安全生产责任制清单数据
     *
     * @param responsibilityList 安全生产责任制清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSecuritySafetyProductionResponsibilityList(List<SecuritySafetyProductionResponsibilityList> responsibilityList, Boolean isUpdateSupport, String operName);

    /**
     * 根据关联ID查询安全生产责任制清单列表
     *
     * @param relatedId 关联ID
     * @return 安全生产责任制清单集合
     */
    public List<SecuritySafetyProductionResponsibilityList> selectSecuritySafetyProductionResponsibilityListByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
