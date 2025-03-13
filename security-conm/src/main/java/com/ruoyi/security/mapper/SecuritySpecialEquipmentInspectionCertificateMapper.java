package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialEquipmentInspectionCertificate;

/**
 * 特种设备监督检验证书Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecuritySpecialEquipmentInspectionCertificateMapper 
{
    /**
     * 查询特种设备监督检验证书
     * 
     * @param id 特种设备监督检验证书主键
     * @return 特种设备监督检验证书
     */
    public SecuritySpecialEquipmentInspectionCertificate selectSecuritySpecialEquipmentInspectionCertificateById(String id);

    /**
     * 查询特种设备监督检验证书列表
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 特种设备监督检验证书集合
     */
    public List<SecuritySpecialEquipmentInspectionCertificate> selectSecuritySpecialEquipmentInspectionCertificateList(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate);

    /**
     * 新增特种设备监督检验证书
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 结果
     */
    public int insertSecuritySpecialEquipmentInspectionCertificate(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate);

    /**
     * 修改特种设备监督检验证书
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 结果
     */
    public int updateSecuritySpecialEquipmentInspectionCertificate(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate);

    /**
     * 删除特种设备监督检验证书
     * 
     * @param id 特种设备监督检验证书主键
     * @return 结果
     */
    public int deleteSecuritySpecialEquipmentInspectionCertificateById(String id);

    /**
     * 批量删除特种设备监督检验证书
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialEquipmentInspectionCertificateByIds(String[] ids);
}
