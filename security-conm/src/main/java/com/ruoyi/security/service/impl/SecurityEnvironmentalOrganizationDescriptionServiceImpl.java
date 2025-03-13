package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOrganizationDescriptionMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import com.ruoyi.security.service.ISecurityEnvironmentalOrganizationDescriptionService;

/**
 * 环境识别Service业务层处理
 *
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityEnvironmentalOrganizationDescriptionServiceImpl implements ISecurityEnvironmentalOrganizationDescriptionService
{
    @Autowired
    private SecurityEnvironmentalOrganizationDescriptionMapper securityEnvironmentalOrganizationDescriptionMapper;

    /**
     * 查询环境识别
     *
     * @param id 环境识别主键
     * @return 环境识别
     */
    @Override
    public SecurityEnvironmentalOrganizationDescription selectSecurityEnvironmentalOrganizationDescriptionById(Long id)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.selectSecurityEnvironmentalOrganizationDescriptionById(id);
    }

    /**
     * 查询环境识别列表
     *
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 环境识别
     */
    @Override
    public List<SecurityEnvironmentalOrganizationDescription> selectSecurityEnvironmentalOrganizationDescriptionList(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.selectSecurityEnvironmentalOrganizationDescriptionList(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 新增环境识别
     *
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.insertSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 修改环境识别
     *
     * @param securityEnvironmentalOrganizationDescription 环境识别
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOrganizationDescription(SecurityEnvironmentalOrganizationDescription securityEnvironmentalOrganizationDescription)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.updateSecurityEnvironmentalOrganizationDescription(securityEnvironmentalOrganizationDescription);
    }

    /**
     * 批量删除环境识别
     *
     * @param ids 需要删除的环境识别主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOrganizationDescriptionByIds(Long[] ids)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.deleteSecurityEnvironmentalOrganizationDescriptionByIds(ids);
    }

    /**
     * 删除环境识别信息
     *
     * @param id 环境识别主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOrganizationDescriptionById(Long id)
    {
        return securityEnvironmentalOrganizationDescriptionMapper.deleteSecurityEnvironmentalOrganizationDescriptionById(id);
    }
}
