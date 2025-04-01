package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRelateNowcheckMapper;
import com.ruoyi.security.domain.SecurityRelateNowcheck;
import com.ruoyi.security.service.ISecurityRelateNowcheckService;

/**
 * 相关方现场检查Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecurityRelateNowcheckServiceImpl implements ISecurityRelateNowcheckService 
{
    @Autowired
    private SecurityRelateNowcheckMapper securityRelateNowcheckMapper;

    /**
     * 查询相关方现场检查
     * 
     * @param id 相关方现场检查主键
     * @return 相关方现场检查
     */
    @Override
    public SecurityRelateNowcheck selectSecurityRelateNowcheckById(String id)
    {
        return securityRelateNowcheckMapper.selectSecurityRelateNowcheckById(id);
    }

    /**
     * 查询相关方现场检查列表
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 相关方现场检查
     */
    @Override
    public List<SecurityRelateNowcheck> selectSecurityRelateNowcheckList(SecurityRelateNowcheck securityRelateNowcheck)
    {
        return securityRelateNowcheckMapper.selectSecurityRelateNowcheckList(securityRelateNowcheck);
    }

    /**
     * 新增相关方现场检查
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 结果
     */
    @Override
    public int insertSecurityRelateNowcheck(SecurityRelateNowcheck securityRelateNowcheck)
    {
        return securityRelateNowcheckMapper.insertSecurityRelateNowcheck(securityRelateNowcheck);
    }

    /**
     * 修改相关方现场检查
     * 
     * @param securityRelateNowcheck 相关方现场检查
     * @return 结果
     */
    @Override
    public int updateSecurityRelateNowcheck(SecurityRelateNowcheck securityRelateNowcheck)
    {
        return securityRelateNowcheckMapper.updateSecurityRelateNowcheck(securityRelateNowcheck);
    }

    /**
     * 批量删除相关方现场检查
     * 
     * @param ids 需要删除的相关方现场检查主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelateNowcheckByIds(String[] ids)
    {
        return securityRelateNowcheckMapper.deleteSecurityRelateNowcheckByIds(ids);
    }

    /**
     * 删除相关方现场检查信息
     * 
     * @param id 相关方现场检查主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRelateNowcheckById(String id)
    {
        return securityRelateNowcheckMapper.deleteSecurityRelateNowcheckById(id);
    }
}
