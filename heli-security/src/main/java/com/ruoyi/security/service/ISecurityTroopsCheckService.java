package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityTroopsCheck;

/**
 * 领导干部带队检查记录Service接口
 * 
 * @author ruoyi
 * @date 2025-03-28
 */
public interface ISecurityTroopsCheckService 
{
    /**
     * 查询领导干部带队检查记录
     * 
     * @param id 领导干部带队检查记录主键
     * @return 领导干部带队检查记录
     */
    public SecurityTroopsCheck selectSecurityTroopsCheckById(String id);

    /**
     * 查询领导干部带队检查记录列表
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 领导干部带队检查记录集合
     */
    public List<SecurityTroopsCheck> selectSecurityTroopsCheckList(SecurityTroopsCheck securityTroopsCheck);

    /**
     * 新增领导干部带队检查记录
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 结果
     */
    public int insertSecurityTroopsCheck(SecurityTroopsCheck securityTroopsCheck);

    /**
     * 修改领导干部带队检查记录
     * 
     * @param securityTroopsCheck 领导干部带队检查记录
     * @return 结果
     */
    public int updateSecurityTroopsCheck(SecurityTroopsCheck securityTroopsCheck);

    /**
     * 批量删除领导干部带队检查记录
     * 
     * @param ids 需要删除的领导干部带队检查记录主键集合
     * @return 结果
     */
    public int deleteSecurityTroopsCheckByIds(String[] ids);

    /**
     * 删除领导干部带队检查记录信息
     * 
     * @param id 领导干部带队检查记录主键
     * @return 结果
     */
    public int deleteSecurityTroopsCheckById(String id);
}
