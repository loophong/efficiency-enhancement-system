package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialEquipmentInspectionCertificateMapper;
import com.ruoyi.security.domain.SecuritySpecialEquipmentInspectionCertificate;
import com.ruoyi.security.service.ISecuritySpecialEquipmentInspectionCertificateService;

/**
 * 特种设备监督检验证书Service业务层处理
 * 
 * @author wang
 * @date 2025-03-13
 */
@Service
public class SecuritySpecialEquipmentInspectionCertificateServiceImpl implements ISecuritySpecialEquipmentInspectionCertificateService 
{
    @Autowired
    private SecuritySpecialEquipmentInspectionCertificateMapper securitySpecialEquipmentInspectionCertificateMapper;

    /**
     * 查询特种设备监督检验证书
     * 
     * @param id 特种设备监督检验证书主键
     * @return 特种设备监督检验证书
     */
    @Override
    public SecuritySpecialEquipmentInspectionCertificate selectSecuritySpecialEquipmentInspectionCertificateById(String id)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.selectSecuritySpecialEquipmentInspectionCertificateById(id);
    }

    /**
     * 查询特种设备监督检验证书列表
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 特种设备监督检验证书
     */
    @Override
    public List<SecuritySpecialEquipmentInspectionCertificate> selectSecuritySpecialEquipmentInspectionCertificateList(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.selectSecuritySpecialEquipmentInspectionCertificateList(securitySpecialEquipmentInspectionCertificate);
    }

    /**
     * 新增特种设备监督检验证书
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialEquipmentInspectionCertificate(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.insertSecuritySpecialEquipmentInspectionCertificate(securitySpecialEquipmentInspectionCertificate);
    }

    /**
     * 修改特种设备监督检验证书
     * 
     * @param securitySpecialEquipmentInspectionCertificate 特种设备监督检验证书
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialEquipmentInspectionCertificate(SecuritySpecialEquipmentInspectionCertificate securitySpecialEquipmentInspectionCertificate)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.updateSecuritySpecialEquipmentInspectionCertificate(securitySpecialEquipmentInspectionCertificate);
    }

    /**
     * 批量删除特种设备监督检验证书
     * 
     * @param ids 需要删除的特种设备监督检验证书主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialEquipmentInspectionCertificateByIds(String[] ids)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.deleteSecuritySpecialEquipmentInspectionCertificateByIds(ids);
    }

    /**
     * 删除特种设备监督检验证书信息
     * 
     * @param id 特种设备监督检验证书主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialEquipmentInspectionCertificateById(String id)
    {
        return securitySpecialEquipmentInspectionCertificateMapper.deleteSecuritySpecialEquipmentInspectionCertificateById(id);
    }
}
