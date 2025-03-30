package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityWeekendListMapper;
import com.ruoyi.security.domain.SecurityWeekendList;
import com.ruoyi.security.service.ISecurityWeekendListService;

/**
 * 节假日检查记录Service业务层处理
 * 
 * @author wang
 * @date 2025-03-28
 */
@Service
public class SecurityWeekendListServiceImpl implements ISecurityWeekendListService 
{
    @Autowired
    private SecurityWeekendListMapper securityWeekendListMapper;

    /**
     * 查询节假日检查记录
     * 
     * @param id 节假日检查记录主键
     * @return 节假日检查记录
     */
    @Override
    public SecurityWeekendList selectSecurityWeekendListById(String id)
    {
        return securityWeekendListMapper.selectSecurityWeekendListById(id);
    }

    /**
     * 查询节假日检查记录列表
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 节假日检查记录
     */
    @Override
    public List<SecurityWeekendList> selectSecurityWeekendListList(SecurityWeekendList securityWeekendList)
    {
        return securityWeekendListMapper.selectSecurityWeekendListList(securityWeekendList);
    }

    /**
     * 新增节假日检查记录
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 结果
     */
    @Override
    public int insertSecurityWeekendList(SecurityWeekendList securityWeekendList)
    {
        return securityWeekendListMapper.insertSecurityWeekendList(securityWeekendList);
    }

    /**
     * 修改节假日检查记录
     * 
     * @param securityWeekendList 节假日检查记录
     * @return 结果
     */
    @Override
    public int updateSecurityWeekendList(SecurityWeekendList securityWeekendList)
    {
        return securityWeekendListMapper.updateSecurityWeekendList(securityWeekendList);
    }

    /**
     * 批量删除节假日检查记录
     * 
     * @param ids 需要删除的节假日检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityWeekendListByIds(String[] ids)
    {
        return securityWeekendListMapper.deleteSecurityWeekendListByIds(ids);
    }

    /**
     * 删除节假日检查记录信息
     * 
     * @param id 节假日检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecurityWeekendListById(String id)
    {
        return securityWeekendListMapper.deleteSecurityWeekendListById(id);
    }
}
