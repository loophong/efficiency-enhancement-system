package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalPhysicalExaminationReportpersonsMapper;
import com.ruoyi.security.domain.SecurityOccupationalPhysicalExaminationReportpersons;
import com.ruoyi.security.service.ISecurityOccupationalPhysicalExaminationReportpersonsService;

/**
 * 职业危害接触人员体检报告Service业务层处理
 * 
 * @author wang
 * @date 2025-03-11
 */
@Service
public class SecurityOccupationalPhysicalExaminationReportpersonsServiceImpl implements ISecurityOccupationalPhysicalExaminationReportpersonsService 
{
    @Autowired
    private SecurityOccupationalPhysicalExaminationReportpersonsMapper securityOccupationalPhysicalExaminationReportpersonsMapper;

    /**
     * 查询职业危害接触人员体检报告
     * 
     * @param id 职业危害接触人员体检报告主键
     * @return 职业危害接触人员体检报告
     */
    @Override
    public SecurityOccupationalPhysicalExaminationReportpersons selectSecurityOccupationalPhysicalExaminationReportpersonsById(String id)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.selectSecurityOccupationalPhysicalExaminationReportpersonsById(id);
    }

    /**
     * 查询职业危害接触人员体检报告列表
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 职业危害接触人员体检报告
     */
    @Override
    public List<SecurityOccupationalPhysicalExaminationReportpersons> selectSecurityOccupationalPhysicalExaminationReportpersonsList(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.selectSecurityOccupationalPhysicalExaminationReportpersonsList(securityOccupationalPhysicalExaminationReportpersons);
    }

    /**
     * 新增职业危害接触人员体检报告
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalPhysicalExaminationReportpersons(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.insertSecurityOccupationalPhysicalExaminationReportpersons(securityOccupationalPhysicalExaminationReportpersons);
    }

    /**
     * 修改职业危害接触人员体检报告
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalPhysicalExaminationReportpersons(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.updateSecurityOccupationalPhysicalExaminationReportpersons(securityOccupationalPhysicalExaminationReportpersons);
    }

    /**
     * 批量删除职业危害接触人员体检报告
     * 
     * @param ids 需要删除的职业危害接触人员体检报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalPhysicalExaminationReportpersonsByIds(String[] ids)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.deleteSecurityOccupationalPhysicalExaminationReportpersonsByIds(ids);
    }

    /**
     * 删除职业危害接触人员体检报告信息
     * 
     * @param id 职业危害接触人员体检报告主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalPhysicalExaminationReportpersonsById(String id)
    {
        return securityOccupationalPhysicalExaminationReportpersonsMapper.deleteSecurityOccupationalPhysicalExaminationReportpersonsById(id);
    }
}
