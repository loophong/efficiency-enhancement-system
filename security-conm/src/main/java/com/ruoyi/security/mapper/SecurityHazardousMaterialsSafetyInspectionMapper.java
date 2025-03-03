package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;

/**
 * 危化品检查记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-03
 */
public interface SecurityHazardousMaterialsSafetyInspectionMapper 
{
    /**
     * 查询危化品检查记录
     * 
     * @param id 危化品检查记录主键
     * @return 危化品检查记录
     */
    public SecurityHazardousMaterialsSafetyInspection selectSecurityHazardousMaterialsSafetyInspectionById(Long id);

    /**
     * 查询危化品检查记录列表
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 危化品检查记录集合
     */
    public List<SecurityHazardousMaterialsSafetyInspection> selectSecurityHazardousMaterialsSafetyInspectionList(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection);

    /**
     * 新增危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    public int insertSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection);

    /**
     * 修改危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    public int updateSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection);

    /**
     * 删除危化品检查记录
     * 
     * @param id 危化品检查记录主键
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsSafetyInspectionById(Long id);

    /**
     * 批量删除危化品检查记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsSafetyInspectionByIds(Long[] ids);
}
