package com.heli.supplier.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierHappenMapper;
import com.heli.supplier.domain.SupplierHappen;
import com.heli.supplier.service.ISupplierHappenService;

/**
 * 质量通知单Service业务层处理
 * 
 * @author wll
 * @date 2025-03-07
 */
@Service
public class SupplierHappenServiceImpl implements ISupplierHappenService 
{
    @Autowired
    private SupplierHappenMapper supplierHappenMapper;

    /**
     * 查询质量通知单
     * 
     * @param id 质量通知单主键
     * @return 质量通知单
     */
    @Override
    public SupplierHappen selectSupplierHappenById(Long id)
    {
        return supplierHappenMapper.selectSupplierHappenById(id);
    }

    /**
     * 查询质量通知单列表
     * 
     * @param supplierHappen 质量通知单
     * @return 质量通知单
     */
    @Override
    public List<SupplierHappen> selectSupplierHappenList(SupplierHappen supplierHappen)
    {
        return supplierHappenMapper.selectSupplierHappenList(supplierHappen);
    }

    /**
     * 新增质量通知单
     * 
     * @param supplierHappen 质量通知单
     * @return 结果
     */
    @Override
    public int insertSupplierHappen(SupplierHappen supplierHappen)
    {
        return supplierHappenMapper.insertSupplierHappen(supplierHappen);
    }

    /**
     * 修改质量通知单
     * 
     * @param supplierHappen 质量通知单
     * @return 结果
     */
    @Override
    public int updateSupplierHappen(SupplierHappen supplierHappen)
    {
        return supplierHappenMapper.updateSupplierHappen(supplierHappen);
    }

    /**
     * 批量删除质量通知单
     * 
     * @param ids 需要删除的质量通知单主键
     * @return 结果
     */
    @Override
    public int deleteSupplierHappenByIds(Long[] ids)
    {
        return supplierHappenMapper.deleteSupplierHappenByIds(ids);
    }

    /**
     * 删除质量通知单信息
     * 
     * @param id 质量通知单主键
     * @return 结果
     */
    @Override
    public int deleteSupplierHappenById(Long id)
    {
        return supplierHappenMapper.deleteSupplierHappenById(id);
    }
}
