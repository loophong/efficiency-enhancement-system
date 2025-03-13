package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危化品检查记录Service接口
 * 
 * @author wang
 * @date 2025-03-03
 */
public interface ISecurityHazardousMaterialsSafetyInspectionService 
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
     * 批量删除危化品检查记录
     * 
     * @param ids 需要删除的危化品检查记录主键集合
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsSafetyInspectionByIds(Long[] ids);

    /**
     * 删除危化品检查记录信息
     * 
     * @param id 危化品检查记录主键
     * @return 结果
     */
    public int deleteSecurityHazardousMaterialsSafetyInspectionById(Long id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}

