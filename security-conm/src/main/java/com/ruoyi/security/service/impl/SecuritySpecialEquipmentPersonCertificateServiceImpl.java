package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialEquipmentPersonCertificateMapper;
import com.ruoyi.security.domain.SecuritySpecialEquipmentPersonCertificate;
import com.ruoyi.security.service.ISecuritySpecialEquipmentPersonCertificateService;

/**
 * 特种设备人员证件Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-13
 */
@Service
public class SecuritySpecialEquipmentPersonCertificateServiceImpl implements ISecuritySpecialEquipmentPersonCertificateService 
{
    @Autowired
    private SecuritySpecialEquipmentPersonCertificateMapper securitySpecialEquipmentPersonCertificateMapper;

    /**
     * 查询特种设备人员证件
     * 
     * @param id 特种设备人员证件主键
     * @return 特种设备人员证件
     */
    @Override
    public SecuritySpecialEquipmentPersonCertificate selectSecuritySpecialEquipmentPersonCertificateById(String id)
    {
        return securitySpecialEquipmentPersonCertificateMapper.selectSecuritySpecialEquipmentPersonCertificateById(id);
    }

    /**
     * 查询特种设备人员证件列表
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 特种设备人员证件
     */
    @Override
    public List<SecuritySpecialEquipmentPersonCertificate> selectSecuritySpecialEquipmentPersonCertificateList(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        return securitySpecialEquipmentPersonCertificateMapper.selectSecuritySpecialEquipmentPersonCertificateList(securitySpecialEquipmentPersonCertificate);
    }

    /**
     * 新增特种设备人员证件
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialEquipmentPersonCertificate(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        return securitySpecialEquipmentPersonCertificateMapper.insertSecuritySpecialEquipmentPersonCertificate(securitySpecialEquipmentPersonCertificate);
    }

    /**
     * 修改特种设备人员证件
     * 
     * @param securitySpecialEquipmentPersonCertificate 特种设备人员证件
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialEquipmentPersonCertificate(SecuritySpecialEquipmentPersonCertificate securitySpecialEquipmentPersonCertificate)
    {
        return securitySpecialEquipmentPersonCertificateMapper.updateSecuritySpecialEquipmentPersonCertificate(securitySpecialEquipmentPersonCertificate);
    }

    /**
     * 批量删除特种设备人员证件
     * 
     * @param ids 需要删除的特种设备人员证件主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialEquipmentPersonCertificateByIds(String[] ids)
    {
        return securitySpecialEquipmentPersonCertificateMapper.deleteSecuritySpecialEquipmentPersonCertificateByIds(ids);
    }

    /**
     * 删除特种设备人员证件信息
     * 
     * @param id 特种设备人员证件主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialEquipmentPersonCertificateById(String id)
    {
        return securitySpecialEquipmentPersonCertificateMapper.deleteSecuritySpecialEquipmentPersonCertificateById(id);
    }
}
