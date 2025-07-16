package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentTableInfoMapper;
import com.ruoyi.security.domain.SecurityEnvironmentTableInfo;
import com.ruoyi.security.service.ISecurityEnvironmentTableInfoService;

/**
 * 环境识别表格元数据Service业务层处理
 * 
 */
@Service
public class SecurityEnvironmentTableInfoServiceImpl implements ISecurityEnvironmentTableInfoService 
{
    @Autowired
    private SecurityEnvironmentTableInfoMapper securityEnvironmentTableInfoMapper;

    /**
     * 查询环境识别表格元数据
     * 
     * @param id 环境识别表格元数据主键
     * @return 环境识别表格元数据
     */
    @Override
    public SecurityEnvironmentTableInfo selectSecurityEnvironmentTableInfoById(Long id)
    {
        return securityEnvironmentTableInfoMapper.selectSecurityEnvironmentTableInfoById(id);
    }

    /**
     * 查询环境识别表格元数据列表
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 环境识别表格元数据
     */
    @Override
    public List<SecurityEnvironmentTableInfo> selectSecurityEnvironmentTableInfoList(SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        return securityEnvironmentTableInfoMapper.selectSecurityEnvironmentTableInfoList(securityEnvironmentTableInfo);
    }

    /**
     * 新增环境识别表格元数据
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentTableInfo(SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        return securityEnvironmentTableInfoMapper.insertSecurityEnvironmentTableInfo(securityEnvironmentTableInfo);
    }

    /**
     * 修改环境识别表格元数据
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentTableInfo(SecurityEnvironmentTableInfo securityEnvironmentTableInfo)
    {
        return securityEnvironmentTableInfoMapper.updateSecurityEnvironmentTableInfo(securityEnvironmentTableInfo);
    }

    /**
     * 批量删除环境识别表格元数据
     * 
     * @param ids 需要删除的环境识别表格元数据主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentTableInfoByIds(Long[] ids)
    {
        return securityEnvironmentTableInfoMapper.deleteSecurityEnvironmentTableInfoByIds(ids);
    }

    /**
     * 删除环境识别表格元数据信息
     * 
     * @param id 环境识别表格元数据主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentTableInfoById(Long id)
    {
        return securityEnvironmentTableInfoMapper.deleteSecurityEnvironmentTableInfoById(id);
    }
} 