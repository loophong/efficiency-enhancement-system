package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityUploadfileProduct;

/**
 * 安全生产责任制Mapper接口
 * 
 * @author wang
 * @date 2025-03-26
 */
public interface SecurityUploadfileProductMapper 
{
    /**
     * 查询安全生产责任制
     * 
     * @param id 安全生产责任制主键
     * @return 安全生产责任制
     */
    public SecurityUploadfileProduct selectSecurityUploadfileProductById(Long id);

    /**
     * 查询安全生产责任制列表
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 安全生产责任制集合
     */
    public List<SecurityUploadfileProduct> selectSecurityUploadfileProductList(SecurityUploadfileProduct securityUploadfileProduct);

    /**
     * 新增安全生产责任制
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 结果
     */
    public int insertSecurityUploadfileProduct(SecurityUploadfileProduct securityUploadfileProduct);

    /**
     * 修改安全生产责任制
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 结果
     */
    public int updateSecurityUploadfileProduct(SecurityUploadfileProduct securityUploadfileProduct);

    /**
     * 删除安全生产责任制
     * 
     * @param id 安全生产责任制主键
     * @return 结果
     */
    public int deleteSecurityUploadfileProductById(Long id);

    /**
     * 批量删除安全生产责任制
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityUploadfileProductByIds(Long[] ids);
}
