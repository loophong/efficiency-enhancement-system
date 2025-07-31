package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentTableInfo;

/**
 * 环境识别表格元数据Service接口
 * 
 */
public interface ISecurityEnvironmentTableInfoService 
{
    /**
     * 查询环境识别表格元数据
     * 
     * @param id 环境识别表格元数据主键
     * @return 环境识别表格元数据
     */
    public SecurityEnvironmentTableInfo selectSecurityEnvironmentTableInfoById(Long id);

    /**
     * 查询环境识别表格元数据列表
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 环境识别表格元数据集合
     */
    public List<SecurityEnvironmentTableInfo> selectSecurityEnvironmentTableInfoList(SecurityEnvironmentTableInfo securityEnvironmentTableInfo);

    /**
     * 新增环境识别表格元数据
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 结果
     */
    public int insertSecurityEnvironmentTableInfo(SecurityEnvironmentTableInfo securityEnvironmentTableInfo);

    /**
     * 修改环境识别表格元数据
     * 
     * @param securityEnvironmentTableInfo 环境识别表格元数据
     * @return 结果
     */
    public int updateSecurityEnvironmentTableInfo(SecurityEnvironmentTableInfo securityEnvironmentTableInfo);

    /**
     * 批量删除环境识别表格元数据
     * 
     * @param ids 需要删除的环境识别表格元数据主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentTableInfoByIds(Long[] ids);

    /**
     * 删除环境识别表格元数据信息
     * 
     * @param id 环境识别表格元数据主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentTableInfoById(Long id);
} 