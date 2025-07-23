package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfileList;

/**
 * 制度明细Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityUploadfileListService 
{
    /**
     * 查询制度明细
     * 
     * @param id 制度明细主键
     * @return 制度明细
     */
    public SecurityUploadfileList selectSecurityUploadfileListById(Long id);

    /**
     * 查询制度明细列表
     * 
     * @param securityUploadfileList 制度明细
     * @return 制度明细集合
     */
    public List<SecurityUploadfileList> selectSecurityUploadfileListList(SecurityUploadfileList securityUploadfileList);

    /**
     * 新增制度明细
     * 
     * @param securityUploadfileList 制度明细
     * @return 结果
     */
    public int insertSecurityUploadfileList(SecurityUploadfileList securityUploadfileList);

    /**
     * 修改制度明细
     * 
     * @param securityUploadfileList 制度明细
     * @return 结果
     */
    public int updateSecurityUploadfileList(SecurityUploadfileList securityUploadfileList);

    /**
     * 批量删除制度明细
     * 
     * @param ids 需要删除的制度明细主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileListByIds(Long[] ids);

    /**
     * 删除制度明细信息
     * 
     * @param id 制度明细主键
     * @return 结果
     */
    public int deleteSecurityUploadfileListById(Long id);
}
