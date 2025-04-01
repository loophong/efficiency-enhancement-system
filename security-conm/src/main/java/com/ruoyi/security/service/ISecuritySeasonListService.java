package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecuritySeasonList;

/**
 * 季节性检查记录Service接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface ISecuritySeasonListService 
{
    /**
     * 查询季节性检查记录
     * 
     * @param id 季节性检查记录主键
     * @return 季节性检查记录
     */
    public SecuritySeasonList selectSecuritySeasonListById(String id);

    /**
     * 查询季节性检查记录列表
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 季节性检查记录集合
     */
    public List<SecuritySeasonList> selectSecuritySeasonListList(SecuritySeasonList securitySeasonList);

    /**
     * 新增季节性检查记录
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 结果
     */
    public int insertSecuritySeasonList(SecuritySeasonList securitySeasonList);

    /**
     * 修改季节性检查记录
     * 
     * @param securitySeasonList 季节性检查记录
     * @return 结果
     */
    public int updateSecuritySeasonList(SecuritySeasonList securitySeasonList);

    /**
     * 批量删除季节性检查记录
     * 
     * @param ids 需要删除的季节性检查记录主键集合
     * @return 结果
     */
    public int deleteSecuritySeasonListByIds(String[] ids);

    /**
     * 删除季节性检查记录信息
     * 
     * @param id 季节性检查记录主键
     * @return 结果
     */
    public int deleteSecuritySeasonListById(String id);
}
