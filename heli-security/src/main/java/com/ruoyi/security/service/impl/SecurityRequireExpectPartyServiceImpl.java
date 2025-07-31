package com.ruoyi.security.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.security.domain.SecurityEnvironmentalOrganizationDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityRequireExpectPartyMapper;
import com.ruoyi.security.domain.SecurityRequireExpectParty;
import com.ruoyi.security.service.ISecurityRequireExpectPartyService;

/**
 * 相关方期望Service业务层处理
 *
 * @author wang
 * @date 2025-03-11
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

    /**
     * 更新最近导入数据的关联ID
     *
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        // 查询最近导入的数据
        SecurityRequireExpectParty query = new SecurityRequireExpectParty();
        // 按创建时间倒序排序，获取最近的记录
        List<SecurityRequireExpectParty> recentRecords =
                securityRequireExpectPartyMapper.selectLatestImportedRecords();

        if (recentRecords == null || recentRecords.isEmpty()) {
            return 0;
        }

        int updatedCount = 0;

        // 更新这些记录的relatedId
        for (SecurityRequireExpectParty record : recentRecords) {
            record.setRelatedId(relatedId);
            record.setUpdateTime(DateUtils.getNowDate());
            updatedCount += securityRequireExpectPartyMapper.updateSecurityRequireExpectParty(record);
        }

        return updatedCount;
    }
}
