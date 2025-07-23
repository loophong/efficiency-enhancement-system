package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityAccidentProcessRecords;

/**
 * 事故过程记录Mapper接口
 * 
 * @author wang
 * @date 2025-03-13
 */
public interface SecurityAccidentProcessRecordsMapper 
{
    /**
     * 查询事故过程记录
     * 
     * @param id 事故过程记录主键
     * @return 事故过程记录
     */
    public SecurityAccidentProcessRecords selectSecurityAccidentProcessRecordsById(Long id);

    /**
     * 查询事故过程记录列表
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 事故过程记录集合
     */
    public List<SecurityAccidentProcessRecords> selectSecurityAccidentProcessRecordsList(SecurityAccidentProcessRecords securityAccidentProcessRecords);

    /**
     * 新增事故过程记录
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 结果
     */
    public int insertSecurityAccidentProcessRecords(SecurityAccidentProcessRecords securityAccidentProcessRecords);

    /**
     * 修改事故过程记录
     * 
     * @param securityAccidentProcessRecords 事故过程记录
     * @return 结果
     */
    public int updateSecurityAccidentProcessRecords(SecurityAccidentProcessRecords securityAccidentProcessRecords);

    /**
     * 删除事故过程记录
     * 
     * @param id 事故过程记录主键
     * @return 结果
     */
    public int deleteSecurityAccidentProcessRecordsById(Long id);

    /**
     * 批量删除事故过程记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityAccidentProcessRecordsByIds(Long[] ids);
}
