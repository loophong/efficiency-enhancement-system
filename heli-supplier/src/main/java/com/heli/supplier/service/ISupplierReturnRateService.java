package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.domain.SuppliersQualified;
import org.springframework.web.multipart.MultipartFile;

/**
 * 售后返修率Service接口
 * 
 * @author wll
 * @date 2025-02-27
 */
public interface ISupplierReturnRateService extends IService<SupplierReturnRate>
{
    /**
     * 查询售后返修率
     * 
     * @param id 售后返修率主键
     * @return 售后返修率
     */
    public SupplierReturnRate selectSupplierReturnRateById(String id);

    /**
     * 查询售后返修率列表
     * 
     * @param supplierReturnRate 售后返修率
     * @return 售后返修率集合
     */
    public List<SupplierReturnRate> selectSupplierReturnRateList(SupplierReturnRate supplierReturnRate);

    /**
     * 新增售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    public int insertSupplierReturnRate(SupplierReturnRate supplierReturnRate);

    /**
     * 修改售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    public int updateSupplierReturnRate(SupplierReturnRate supplierReturnRate);

    /**
     * 批量删除售后返修率
     * 
     * @param ids 需要删除的售后返修率主键集合
     * @return 结果
     */
    public int deleteSupplierReturnRateByIds(String[] ids);

    /**
     * 删除售后返修率信息
     * 
     * @param id 售后返修率主键
     * @return 结果
     */
    public int deleteSupplierReturnRateById(String id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);
}
