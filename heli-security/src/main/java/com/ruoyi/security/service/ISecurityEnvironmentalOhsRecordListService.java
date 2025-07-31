package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsRecordList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 环境职业健康安全记录清单Service接口
 * 
 * @author wang
 * @date 2025-03-07
 */
public interface ISecurityEnvironmentalOhsRecordListService 
{
    /**
     * 查询环境职业健康安全记录清单
     * 
     * @param id 环境职业健康安全记录清单主键
     * @return 环境职业健康安全记录清单
     */
    public SecurityEnvironmentalOhsRecordList selectSecurityEnvironmentalOhsRecordListById(Long id);

    /**
     * 查询环境职业健康安全记录清单列表
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 环境职业健康安全记录清单集合
     */
    public List<SecurityEnvironmentalOhsRecordList> selectSecurityEnvironmentalOhsRecordListList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 新增环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    public int insertSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 修改环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    public int updateSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList);

    /**
     * 批量删除环境职业健康安全记录清单
     * 
     * @param ids 需要删除的环境职业健康安全记录清单主键集合
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsRecordListByIds(Long[] ids);

    /**
     * 删除环境职业健康安全记录清单信息
     *
     * @param id 环境职业健康安全记录清单主键
     * @return 结果
     */
    public int deleteSecurityEnvironmentalOhsRecordListById(Long id);
    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);

    /**
     * 导入环境职业健康安全记录清单数据
     *
     * @param recordList 环境职业健康安全记录清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSecurityEnvironmentalOhsRecordList(List<SecurityEnvironmentalOhsRecordList> recordList, Boolean isUpdateSupport, String operName);

    /**
     * 根据关联ID查询环境职业健康安全记录清单列表
     *
     * @param relatedId 关联ID
     * @return 环境职业健康安全记录清单集合
     */
    public List<SecurityEnvironmentalOhsRecordList> selectSecurityEnvironmentalOhsRecordListByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
