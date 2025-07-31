package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityOccupationalPhysicalExaminationReportpersons;

/**
 * 职业危害接触人员体检报告Service接口
 * 
 * @author wang
 * @date 2025-03-11
 */
public interface ISecurityOccupationalPhysicalExaminationReportpersonsService 
{
    /**
     * 查询职业危害接触人员体检报告
     * 
     * @param id 职业危害接触人员体检报告主键
     * @return 职业危害接触人员体检报告
     */
    public SecurityOccupationalPhysicalExaminationReportpersons selectSecurityOccupationalPhysicalExaminationReportpersonsById(String id);

    /**
     * 查询职业危害接触人员体检报告列表
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 职业危害接触人员体检报告集合
     */
    public List<SecurityOccupationalPhysicalExaminationReportpersons> selectSecurityOccupationalPhysicalExaminationReportpersonsList(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons);

    /**
     * 新增职业危害接触人员体检报告
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 结果
     */
    public int insertSecurityOccupationalPhysicalExaminationReportpersons(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons);

    /**
     * 修改职业危害接触人员体检报告
     * 
     * @param securityOccupationalPhysicalExaminationReportpersons 职业危害接触人员体检报告
     * @return 结果
     */
    public int updateSecurityOccupationalPhysicalExaminationReportpersons(SecurityOccupationalPhysicalExaminationReportpersons securityOccupationalPhysicalExaminationReportpersons);

    /**
     * 批量删除职业危害接触人员体检报告
     * 
     * @param ids 需要删除的职业危害接触人员体检报告主键集合
     * @return 结果
     */
    public int deleteSecurityOccupationalPhysicalExaminationReportpersonsByIds(String[] ids);

    /**
     * 删除职业危害接触人员体检报告信息
     * 
     * @param id 职业危害接触人员体检报告主键
     * @return 结果
     */
    public int deleteSecurityOccupationalPhysicalExaminationReportpersonsById(String id);
}
