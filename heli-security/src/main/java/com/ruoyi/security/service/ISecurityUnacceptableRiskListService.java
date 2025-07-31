package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityUnacceptableRiskList;
import org.springframework.web.multipart.MultipartFile;

/**
 * 不可接受风险清单Service接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface ISecurityUnacceptableRiskListService 
{
    /**
     * 查询不可接受风险清单
     * 
     * @param id 不可接受风险清单主键
     * @return 不可接受风险清单
     */
    public SecurityUnacceptableRiskList selectSecurityUnacceptableRiskListById(Long id);

    /**
     * 查询不可接受风险清单列表
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 不可接受风险清单集合
     */
    public List<SecurityUnacceptableRiskList> selectSecurityUnacceptableRiskListList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 新增不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    public int insertSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 修改不可接受风险清单
     * 
     * @param securityUnacceptableRiskList 不可接受风险清单
     * @return 结果
     */
    public int updateSecurityUnacceptableRiskList(SecurityUnacceptableRiskList securityUnacceptableRiskList);

    /**
     * 批量删除不可接受风险清单
     * 
     * @param ids 需要删除的不可接受风险清单主键集合
     * @return 结果
     */
    public int deleteSecurityUnacceptableRiskListByIds(Long[] ids);

    /**
     * 删除不可接受风险清单信息
     * 
     * @param id 不可接受风险清单主键
     * @return 结果
     */
    public int deleteSecurityUnacceptableRiskListById(Long id);
    
    /**
     * 导入不可接受风险清单数据
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
