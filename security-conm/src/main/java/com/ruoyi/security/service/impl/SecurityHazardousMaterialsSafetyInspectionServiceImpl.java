package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsSafetyInspectionMapper;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.service.ISecurityHazardousMaterialsSafetyInspectionService;

/**
 * 危化品检查记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-03
 */
@Service
public class SecurityHazardousMaterialsSafetyInspectionServiceImpl implements ISecurityHazardousMaterialsSafetyInspectionService 
{
    @Autowired
    private SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapper;

    /**
     * 查询危化品检查记录
     * 
     * @param id 危化品检查记录主键
     * @return 危化品检查记录
     */
    @Override
    public SecurityHazardousMaterialsSafetyInspection selectSecurityHazardousMaterialsSafetyInspectionById(Long id)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.selectSecurityHazardousMaterialsSafetyInspectionById(id);
    }

    /**
     * 查询危化品检查记录列表
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 危化品检查记录
     */
    @Override
    public List<SecurityHazardousMaterialsSafetyInspection> selectSecurityHazardousMaterialsSafetyInspectionList(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.selectSecurityHazardousMaterialsSafetyInspectionList(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 新增危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.insertSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 修改危化品检查记录
     * 
     * @param securityHazardousMaterialsSafetyInspection 危化品检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityHazardousMaterialsSafetyInspection(SecurityHazardousMaterialsSafetyInspection securityHazardousMaterialsSafetyInspection)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.updateSecurityHazardousMaterialsSafetyInspection(securityHazardousMaterialsSafetyInspection);
    }

    /**
     * 批量删除危化品检查记录
     * 
     * @param ids 需要删除的危化品检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsSafetyInspectionByIds(Long[] ids)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.deleteSecurityHazardousMaterialsSafetyInspectionByIds(ids);
    }

    /**
     * 删除危化品检查记录信息
     * 
     * @param id 危化品检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityHazardousMaterialsSafetyInspectionById(Long id)
    {
        return securityHazardousMaterialsSafetyInspectionMapper.deleteSecurityHazardousMaterialsSafetyInspectionById(id);
    }
}
