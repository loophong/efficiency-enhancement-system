package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfileList;

/**
 * 制度明细Mapper接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface SecurityUploadfileListMapper 
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
     * 删除制度明细
     * 
     * @param id 制度明细主键
     * @return 结果
     */
    public int deleteSecurityUploadfileListById(Long id);

    /**
     * 批量删除制度明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileListByIds(Long[] ids);
}
