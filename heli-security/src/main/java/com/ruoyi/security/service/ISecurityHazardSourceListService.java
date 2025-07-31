package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityHazardSourceList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 危险源清单Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityHazardSourceListService 
{
    /**
     * 查询危险源清单
     * 
     * @param id 危险源清单主键
     * @return 危险源清单
     */
    public SecurityHazardSourceList selectSecurityHazardSourceListById(Long id);

    /**
     * 查询危险源清单列表
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 危险源清单集合
     */
    public List<SecurityHazardSourceList> selectSecurityHazardSourceListList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 新增危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    public int insertSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 修改危险源清单
     * 
     * @param securityHazardSourceList 危险源清单
     * @return 结果
     */
    public int updateSecurityHazardSourceList(SecurityHazardSourceList securityHazardSourceList);

    /**
     * 批量删除危险源清单
     * 
     * @param ids 需要删除的危险源清单主键集合
     * @return 结果
     */
    public int deleteSecurityHazardSourceListByIds(Long[] ids);

    /**
     * 删除危险源清单信息
     * 
     * @param id 危险源清单主键
     * @return 结果
     */
    public int deleteSecurityHazardSourceListById(Long id);
    
    /**
     * 导入危险源清单数据
     * 
     * @param file 导入文件
     * @param updateSupport 是否更新已存在数据
     * @return 结果
     */
    public String importData(MultipartFile file, boolean updateSupport);
    
    /**
     * 更新最近导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
