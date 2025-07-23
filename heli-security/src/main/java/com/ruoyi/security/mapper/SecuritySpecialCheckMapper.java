package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecuritySpecialCheck;

/**
 * 专项检查记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface SecuritySpecialCheckMapper 
{
    /**
     * 查询专项检查记录
     * 
     * @param id 专项检查记录主键
     * @return 专项检查记录
     */
    public SecuritySpecialCheck selectSecuritySpecialCheckById(String id);

    /**
     * 查询专项检查记录列表
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 专项检查记录集合
     */
    public List<SecuritySpecialCheck> selectSecuritySpecialCheckList(SecuritySpecialCheck securitySpecialCheck);

    /**
     * 新增专项检查记录
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 结果
     */
    public int insertSecuritySpecialCheck(SecuritySpecialCheck securitySpecialCheck);

    /**
     * 修改专项检查记录
     * 
     * @param securitySpecialCheck 专项检查记录
     * @return 结果
     */
    public int updateSecuritySpecialCheck(SecuritySpecialCheck securitySpecialCheck);

    /**
     * 删除专项检查记录
     * 
     * @param id 专项检查记录主键
     * @return 结果
     */
    public int deleteSecuritySpecialCheckById(String id);

    /**
     * 批量删除专项检查记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecuritySpecialCheckByIds(String[] ids);
}
