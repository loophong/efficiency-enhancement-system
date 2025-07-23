package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialCheckMapper;
import com.ruoyi.security.domain.SecuritySpecialCheck;
import com.ruoyi.security.service.ISecuritySpecialCheckService;

/**
 * 专项检查记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecuritySpecialCheckServiceImpl implements ISecuritySpecialCheckService 
{
    @Autowired
    private SecuritySpecialCheckMapper securitySpecialCheckMapper;

    /**
     * 查询专项检查记录
     * 
     * @param id 专项检查记录主键
     * @return 专项检查记录
     */
    @Override
    public SecuritySpecialCheck selectSecuritySpecialCheckById(String id)
    {
        return securitySpecialCheckMapper.selectSecuritySpecialCheckById(id);
    }

    /**
     * 查询专项检查记录列表
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 专项检查记录
     */
    @Override
    public List<SecuritySpecialCheck> selectSecuritySpecialCheckList(SecuritySpecialCheck securitySpecialCheck)
    {
        return securitySpecialCheckMapper.selectSecuritySpecialCheckList(securitySpecialCheck);
    }

    /**
     * 新增专项检查记录
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialCheck(SecuritySpecialCheck securitySpecialCheck)
    {
        return securitySpecialCheckMapper.insertSecuritySpecialCheck(securitySpecialCheck);
    }

    /**
     * 修改专项检查记录
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialCheck(SecuritySpecialCheck securitySpecialCheck)
    {
        return securitySpecialCheckMapper.updateSecuritySpecialCheck(securitySpecialCheck);
    }

    /**
     * 批量删除专项检查记录
     * 
     * @param ids 需要删除的专项检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialCheckByIds(String[] ids)
    {
        return securitySpecialCheckMapper.deleteSecuritySpecialCheckByIds(ids);
    }

    /**
     * 删除专项检查记录信息
     * 
     * @param id 专项检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialCheckById(String id)
    {
        return securitySpecialCheckMapper.deleteSecuritySpecialCheckById(id);
    }
}
