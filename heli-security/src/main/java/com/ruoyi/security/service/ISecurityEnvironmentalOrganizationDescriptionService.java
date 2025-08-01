package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;

/**
 * 环境识别Service接口
 * 
 */
public interface ISecurityEnvironmentalOrganizationDescriptionService 
{
    /**
     * 查询环境识别
     * 
     * @param id 环境识别主键
     * @return 环境识别
     */
    public SecurityEnvironmentalOrganizationDescription selectSecurityEnvironmentalOrganizationDescriptionById(Long id);

    /**
     * 查询环境识别列表
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 环境识别集合
     */
    public List<SecurityEnvironmentalOrganizationDescription> selectSecurityEnvironmentalOrganizationDescriptionList(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription);

    /**
     * 构建树形结构列表
     *
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 树形结构列表
     */
    public List<SecurityEnvironmentalOrganizationDescription> buildTreeList(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription);

    /**
     * 新增环境识别
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    public int insertSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription);

    /**
     * 修改环境识别
     * 
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    public int updateSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription);

    /**
     * 批量删除环境识别
     * 
     * @param ids 需要删除的环境识别主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOrganizationDescriptionByIds(Long[] ids);

    /**
     * 删除环境识别信息
     * 
     * @param id 环境识别主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOrganizationDescriptionById(Long id);
    
    /**
     * 更新最近导入的环境识别数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
    
    /**
     * 批量新增环境识别
     * 
     * @param list 环境识别列表
     * @return 结果
     */
    public int batchInsertSecurityEnvironmentalOrganizationDescription(List<SecurityEnvironmentalOrganizationDescription> list);
}
