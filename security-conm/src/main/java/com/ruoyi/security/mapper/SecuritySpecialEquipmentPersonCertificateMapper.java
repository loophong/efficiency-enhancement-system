package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialEquipmentPersonCertificate;

/**
 * 特种设备人员证件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-13
 */
public interface SecuritySpecialEquipmentPersonCertificateMapper 
{
    /**
     * 查询特种设备人员证件
     * 
     * @param id 特种设备人员证件主键
     * @return 特种设备人员证件
     */
    public SecuritySpecialEquipmentPersonCertificate selectSecuritySpecialEquipmentPersonCertificateById(String id);

    /**
     * 查询特种设备人员证件列表
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 特种设备人员证件集合
     */
    public List<SecuritySpecialEquipmentPersonCertificate> selectSecuritySpecialEquipmentPersonCertificateList(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate);

    /**
     * 新增特种设备人员证件
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 结果
     */
    public int insertSecuritySpecialEquipmentPersonCertificate(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate);

    /**
     * 修改特种设备人员证件
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 结果
     */
    public int updateSecuritySpecialEquipmentPersonCertificate(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate);

    /**
     * 删除特种设备人员证件
     * 
     * @param id 特种设备人员证件主键
     * @return 结果
     */
    public int deleteSecuritySpecialEquipmentPersonCertificateById(String id);

    /**
     * 批量删除特种设备人员证件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialEquipmentPersonCertificateByIds(String[] ids);
}
