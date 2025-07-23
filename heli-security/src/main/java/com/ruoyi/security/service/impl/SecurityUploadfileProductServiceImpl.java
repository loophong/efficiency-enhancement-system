package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityUploadfileProductMapper;
import com.ruoyi.security.domain.SecurityUploadfileProduct;
import com.ruoyi.security.service.ISecurityUploadfileProductService;

/**
 * 安全生产责任制Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecurityUploadfileProductServiceImpl implements ISecurityUploadfileProductService 
{
    @Autowired
    private SecurityUploadfileProductMapper securityUploadfileProductMapper;

    /**
     * 查询安全生产责任制
     * 
     * @param id 安全生产责任制主键
     * @return 安全生产责任制
     */
    @Override
    public SecurityUploadfileProduct selectSecurityUploadfileProductById(Long id)
    {
        return securityUploadfileProductMapper.selectSecurityUploadfileProductById(id);
    }

    /**
     * 查询安全生产责任制列表
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 安全生产责任制
     */
    @Override
    public List<SecurityUploadfileProduct> selectSecurityUploadfileProductList(SecurityUploadfileProduct securityUploadfileProduct)
    {
        return securityUploadfileProductMapper.selectSecurityUploadfileProductList(securityUploadfileProduct);
    }

    /**
     * 新增安全生产责任制
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 结果
     */
    @Override
    public int insertSecurityUploadfileProduct(SecurityUploadfileProduct securityUploadfileProduct)
    {
        return securityUploadfileProductMapper.insertSecurityUploadfileProduct(securityUploadfileProduct);
    }

    /**
     * 修改安全生产责任制
     * 
     * @param securityUploadfileProduct 安全生产责任制
     * @return 结果
     */
    @Override
    public int updateSecurityUploadfileProduct(SecurityUploadfileProduct securityUploadfileProduct)
    {
        return securityUploadfileProductMapper.updateSecurityUploadfileProduct(securityUploadfileProduct);
    }

    /**
     * 批量删除安全生产责任制
     * 
     * @param ids 需要删除的安全生产责任制主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileProductByIds(Long[] ids)
    {
        return securityUploadfileProductMapper.deleteSecurityUploadfileProductByIds(ids);
    }

    /**
     * 删除安全生产责任制信息
     * 
     * @param id 安全生产责任制主键
     * @return 结果
     */
    @Override
    public int deleteSecurityUploadfileProductById(Long id)
    {
        return securityUploadfileProductMapper.deleteSecurityUploadfileProductById(id);
    }
}
