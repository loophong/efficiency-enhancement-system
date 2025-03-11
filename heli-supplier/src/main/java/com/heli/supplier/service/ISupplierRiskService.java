package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 经营风险Service接口
 * 
 * @author wll
 * @date 2025-02-27
 */
public interface ISupplierRiskService extends IService<SupplierRisk>
{
    /**
     * 查询经营风险
     * 
     * @param id 经营风险主键
     * @return 经营风险
     */
    public SupplierRisk selectSupplierRiskById(String id);

    /**
     * 查询经营风险列表
     * 
     * @param supplierRisk 经营风险
     * @return 经营风险集合
     */
    public List<SupplierRisk> selectSupplierRiskList(SupplierRisk supplierRisk);

    /**
     * 新增经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    public int insertSupplierRisk(SupplierRisk supplierRisk);

    /**
     * 修改经营风险
     * 
     * @param supplierRisk 经营风险
     * @return 结果
     */
    public int updateSupplierRisk(SupplierRisk supplierRisk);

    /**
     * 批量删除经营风险
     * 
     * @param ids 需要删除的经营风险主键集合
     * @return 结果
     */
    public int deleteSupplierRiskByIds(String[] ids);

    /**
     * 删除经营风险信息
     * 
     * @param id 经营风险主键
     * @return 结果
     */
    public int deleteSupplierRiskById(String id);
}
