package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;

/**
 * 环境识别Mapper接口
 * 
 * @author 王
 * @date 2025-01-14
 */
public interface SecurityEnvironmentalOrganizationDescriptionMapper 
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
     * 删除环境识别
     * 
     * @param id 环境识别主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOrganizationDescriptionById(Long id);

    /**
     * 批量删除环境识别
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOrganizationDescriptionByIds(Long[] ids);
}
