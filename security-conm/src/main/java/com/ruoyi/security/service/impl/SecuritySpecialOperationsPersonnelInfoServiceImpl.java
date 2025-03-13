package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialOperationsPersonnelInfoMapper;
import com.ruoyi.security.domain.SecuritySpecialOperationsPersonnelInfo;
import com.ruoyi.security.service.ISecuritySpecialOperationsPersonnelInfoService;

/**
 * 特种作业人员信息Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecuritySpecialOperationsPersonnelInfoServiceImpl implements ISecuritySpecialOperationsPersonnelInfoService 
{
    @Autowired
    private SecuritySpecialOperationsPersonnelInfoMapper securitySpecialOperationsPersonnelInfoMapper;

    /**
     * 查询特种作业人员信息
     * 
     * @param id 特种作业人员信息主键
     * @return 特种作业人员信息
     */
    @Override
    public SecuritySpecialOperationsPersonnelInfo selectSecuritySpecialOperationsPersonnelInfoById(Long id)
    {
        return securitySpecialOperationsPersonnelInfoMapper.selectSecuritySpecialOperationsPersonnelInfoById(id);
    }

    /**
     * 查询特种作业人员信息列表
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 特种作业人员信息
     */
    @Override
    public List<SecuritySpecialOperationsPersonnelInfo> selectSecuritySpecialOperationsPersonnelInfoList(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.selectSecuritySpecialOperationsPersonnelInfoList(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 新增特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.insertSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 修改特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.updateSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 批量删除特种作业人员信息
     * 
     * @param ids 需要删除的特种作业人员信息主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsPersonnelInfoByIds(Long[] ids)
    {
        return securitySpecialOperationsPersonnelInfoMapper.deleteSecuritySpecialOperationsPersonnelInfoByIds(ids);
    }

    /**
     * 删除特种作业人员信息信息
     * 
     * @param id 特种作业人员信息主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsPersonnelInfoById(Long id)
    {
        return securitySpecialOperationsPersonnelInfoMapper.deleteSecuritySpecialOperationsPersonnelInfoById(id);
    }
}
