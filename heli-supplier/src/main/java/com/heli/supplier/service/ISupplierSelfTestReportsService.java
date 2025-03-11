package com.heli.supplier.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierSelfTestReports;
import com.heli.supplier.domain.SuppliersQualified;

/**
 * 自检报告Service接口
 * 
 * @author wll
 * @date 2025-02-26
 */
public interface ISupplierSelfTestReportsService extends IService<SupplierSelfTestReports>
{
    /**
     * 查询自检报告
     * 
     * @param id 自检报告主键
     * @return 自检报告
     */
    public SupplierSelfTestReports selectSupplierSelfTestReportsById(Long id);

    /**
     * 查询自检报告列表
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 自检报告集合
     */
    public List<SupplierSelfTestReports> selectSupplierSelfTestReportsList(SupplierSelfTestReports supplierSelfTestReports);

    /**
     * 新增自检报告
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 结果
     */
    public int insertSupplierSelfTestReports(SupplierSelfTestReports supplierSelfTestReports);

    /**
     * 修改自检报告
     * 
     * @param supplierSelfTestReports 自检报告
     * @return 结果
     */
    public int updateSupplierSelfTestReports(SupplierSelfTestReports supplierSelfTestReports);

    /**
     * 批量删除自检报告
     * 
     * @param ids 需要删除的自检报告主键集合
     * @return 结果
     */
    public int deleteSupplierSelfTestReportsByIds(Long[] ids);

    /**
     * 删除自检报告信息
     * 
     * @param id 自检报告主键
     * @return 结果
     */
    public int deleteSupplierSelfTestReportsById(Long id);
}
