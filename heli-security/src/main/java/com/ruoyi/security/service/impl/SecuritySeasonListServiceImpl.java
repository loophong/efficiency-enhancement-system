package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySeasonListMapper;
import com.ruoyi.security.domain.SecuritySeasonList;
import com.ruoyi.security.service.ISecuritySeasonListService;

/**
 * 季节性检查记录Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
@Service
public class SecuritySeasonListServiceImpl implements ISecuritySeasonListService 
{
    @Autowired
    private SecuritySeasonListMapper securitySeasonListMapper;

    /**
     * 查询季节性检查记录
     * 
     * @param id 季节性检查记录主键
     * @return 季节性检查记录
     */
    @Override
    public SecuritySeasonList selectSecuritySeasonListById(String id)
    {
        return securitySeasonListMapper.selectSecuritySeasonListById(id);
    }

    /**
     * 查询季节性检查记录列表
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 季节性检查记录
     */
    @Override
    public List<SecuritySeasonList> selectSecuritySeasonListList(SecuritySeasonList securitySeasonList)
    {
        return securitySeasonListMapper.selectSecuritySeasonListList(securitySeasonList);
    }

    /**
     * 新增季节性检查记录
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 结果
     */
    @Override
    public int insertSecuritySeasonList(SecuritySeasonList securitySeasonList)
    {
        return securitySeasonListMapper.insertSecuritySeasonList(securitySeasonList);
    }

    /**
     * 修改季节性检查记录
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 结果
     */
    @Override
    public int updateSecuritySeasonList(SecuritySeasonList securitySeasonList)
    {
        return securitySeasonListMapper.updateSecuritySeasonList(securitySeasonList);
    }

    /**
     * 批量删除季节性检查记录
     * 
     * @param ids 需要删除的季节性检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySeasonListByIds(String[] ids)
    {
        return securitySeasonListMapper.deleteSecuritySeasonListByIds(ids);
    }

    /**
     * 删除季节性检查记录信息
     * 
     * @param id 季节性检查记录主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySeasonListById(String id)
    {
        return securitySeasonListMapper.deleteSecuritySeasonListById(id);
    }
}
