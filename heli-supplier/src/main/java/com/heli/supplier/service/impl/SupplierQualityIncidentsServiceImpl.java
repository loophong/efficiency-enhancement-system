package com.heli.supplier.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import com.heli.supplier.service.ISuppliersQualifiedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierQualityIncidentsMapper;
import com.heli.supplier.domain.SupplierQualityIncidents;
import com.heli.supplier.service.ISupplierQualityIncidentsService;

/**
 * 质量事故Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-20
 */
@Service
@Slf4j
public class SupplierQualityIncidentsServiceImpl extends ServiceImpl<SupplierQualityIncidentsMapper, SupplierQualityIncidents> implements ISupplierQualityIncidentsService
{
    @Autowired
    private SupplierQualityIncidentsMapper supplierQualityIncidentsMapper;

    /**
     * 查询质量事故
     *
     * @param id 质量事故主键
     * @return 质量事故
     */
    @Override
    public SupplierQualityIncidents selectSupplierQualityIncidentsById(String id)
    {
        return supplierQualityIncidentsMapper.selectSupplierQualityIncidentsById(id);
    }

    /**
     * 查询质量事故列表
     *
     * @param supplierQualityIncidents 质量事故
     * @return 质量事故
     */
    @Override
    public List<SupplierQualityIncidents> selectSupplierQualityIncidentsList(SupplierQualityIncidents supplierQualityIncidents)
    {
        return supplierQualityIncidentsMapper.selectSupplierQualityIncidentsList(supplierQualityIncidents);
    }

    /**
     * 新增质量事故
     *
     * @param supplierQualityIncidents 质量事故
     * @return 结果
     */
    @Override
    public int insertSupplierQualityIncidents(SupplierQualityIncidents supplierQualityIncidents)
    {
        return supplierQualityIncidentsMapper.insertSupplierQualityIncidents(supplierQualityIncidents);
    }

    /**
     * 修改质量事故
     *
     * @param supplierQualityIncidents 质量事故
     * @return 结果
     */
    @Override
    public int updateSupplierQualityIncidents(SupplierQualityIncidents supplierQualityIncidents)
    {
        return supplierQualityIncidentsMapper.updateSupplierQualityIncidents(supplierQualityIncidents);
    }

    /**
     * 批量删除质量事故
     *
     * @param ids 需要删除的质量事故主键
     * @return 结果
     */
    @Override
    public int deleteSupplierQualityIncidentsByIds(String[] ids)
    {
        return supplierQualityIncidentsMapper.deleteSupplierQualityIncidentsByIds(ids);
    }

    /**
     * 删除质量事故信息
     *
     * @param id 质量事故主键
     * @return 结果
     */
    @Override
    public int deleteSupplierQualityIncidentsById(String id)
    {
        return supplierQualityIncidentsMapper.deleteSupplierQualityIncidentsById(id);
    }
}
