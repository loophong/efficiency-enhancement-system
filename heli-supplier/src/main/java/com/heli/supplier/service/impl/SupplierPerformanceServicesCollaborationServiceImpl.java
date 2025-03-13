package com.heli.supplier.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPerformanceServicesCollaborationMapper;
import com.heli.supplier.domain.SupplierPerformanceServicesCollaboration;
import com.heli.supplier.service.ISupplierPerformanceServicesCollaborationService;

/**
 * 服务与协作Service业务层处理
 * 
 * @author wll
 * @date 2025-03-05
 */
@Service
public class SupplierPerformanceServicesCollaborationServiceImpl implements ISupplierPerformanceServicesCollaborationService 
{
    @Autowired
    private SupplierPerformanceServicesCollaborationMapper supplierPerformanceServicesCollaborationMapper;

    /**
     * 查询服务与协作
     * 
     * @param id 服务与协作主键
     * @return 服务与协作
     */
    @Override
    public SupplierPerformanceServicesCollaboration selectSupplierPerformanceServicesCollaborationById(String id)
    {
        return supplierPerformanceServicesCollaborationMapper.selectSupplierPerformanceServicesCollaborationById(id);
    }

    /**
     * 查询服务与协作列表
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 服务与协作
     */
    @Override
    public List<SupplierPerformanceServicesCollaboration> selectSupplierPerformanceServicesCollaborationList(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.selectSupplierPerformanceServicesCollaborationList(supplierPerformanceServicesCollaboration);
    }

    /**
     * 新增服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    @Override
    public int insertSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.insertSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration);
    }

    /**
     * 修改服务与协作
     * 
     * @param supplierPerformanceServicesCollaboration 服务与协作
     * @return 结果
     */
    @Override
    public int updateSupplierPerformanceServicesCollaboration(SupplierPerformanceServicesCollaboration supplierPerformanceServicesCollaboration)
    {
        return supplierPerformanceServicesCollaborationMapper.updateSupplierPerformanceServicesCollaboration(supplierPerformanceServicesCollaboration);
    }

    /**
     * 批量删除服务与协作
     * 
     * @param ids 需要删除的服务与协作主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPerformanceServicesCollaborationByIds(String[] ids)
    {
        return supplierPerformanceServicesCollaborationMapper.deleteSupplierPerformanceServicesCollaborationByIds(ids);
    }

    /**
     * 删除服务与协作信息
     * 
     * @param id 服务与协作主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPerformanceServicesCollaborationById(String id)
    {
        return supplierPerformanceServicesCollaborationMapper.deleteSupplierPerformanceServicesCollaborationById(id);
    }
}
