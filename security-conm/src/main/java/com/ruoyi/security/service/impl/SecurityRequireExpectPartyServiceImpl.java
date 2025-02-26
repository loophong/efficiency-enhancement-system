package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRequireExpectPartyMapper;
import com.ruoyi.security.domain.SecurityRequireExpectParty;
import com.ruoyi.security.service.ISecurityRequireExpectPartyService;

/**
 * 相关方期望Service业务层处理
 * 
 * @author wang
 * @date 2025-02-20
 */
@Service
public class SecurityRequireExpectPartyServiceImpl implements ISecurityRequireExpectPartyService 
{
    @Autowired
    private SecurityRequireExpectPartyMapper securityRequireExpectPartyMapper;

    /**
     * 查询相关方期望
     * 
     * @param id 相关方期望主键
     * @return 相关方期望
     */
    @Override
    public SecurityRequireExpectParty selectSecurityRequireExpectPartyById(Long id)
    {
        return securityRequireExpectPartyMapper.selectSecurityRequireExpectPartyById(id);
    }

    /**
     * 查询相关方期望列表
     * 
     * @param securityRequireExpectParty 相关方期望
     * @return 相关方期望
     */
    @Override
    public List<SecurityRequireExpectParty> selectSecurityRequireExpectPartyList(SecurityRequireExpectParty securityRequireExpectParty)
    {
        return securityRequireExpectPartyMapper.selectSecurityRequireExpectPartyList(securityRequireExpectParty);
    }

    /**
     * 新增相关方期望
     * 
     * @param securityRequireExpectParty 相关方期望
     * @return 结果
     */
    @Override
    public int insertSecurityRequireExpectParty(SecurityRequireExpectParty securityRequireExpectParty)
    {
        return securityRequireExpectPartyMapper.insertSecurityRequireExpectParty(securityRequireExpectParty);
    }

    /**
     * 修改相关方期望
     * 
     * @param securityRequireExpectParty 相关方期望
     * @return 结果
     */
    @Override
    public int updateSecurityRequireExpectParty(SecurityRequireExpectParty securityRequireExpectParty)
    {
        return securityRequireExpectPartyMapper.updateSecurityRequireExpectParty(securityRequireExpectParty);
    }

    /**
     * 批量删除相关方期望
     * 
     * @param ids 需要删除的相关方期望主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRequireExpectPartyByIds(Long[] ids)
    {
        return securityRequireExpectPartyMapper.deleteSecurityRequireExpectPartyByIds(ids);
    }

    /**
     * 删除相关方期望信息
     * 
     * @param id 相关方期望主键
     * @return 结果
     */
    @Override
    public int deleteSecurityRequireExpectPartyById(Long id)
    {
        return securityRequireExpectPartyMapper.deleteSecurityRequireExpectPartyById(id);
    }
}
