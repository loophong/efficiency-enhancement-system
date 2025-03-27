package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.domain.SupplierRisk;
import org.springframework.web.multipart.MultipartFile;

/**
 * 付款限制条件Service接口
 * 
 * @author wll
 * @date 2025-03-25
 */
public interface ISupplierPaymentService extends IService<SupplierPayment>
{
    /**
     * 查询付款限制条件
     * 
     * @param id 付款限制条件主键
     * @return 付款限制条件
     */
    public SupplierPayment selectSupplierPaymentById(String id);

    /**
     * 查询付款限制条件列表
     * 
     * @param supplierPayment 付款限制条件
     * @return 付款限制条件集合
     */
    public List<SupplierPayment> selectSupplierPaymentList(SupplierPayment supplierPayment);

    /**
     * 新增付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    public int insertSupplierPayment(SupplierPayment supplierPayment);

    /**
     * 修改付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    public int updateSupplierPayment(SupplierPayment supplierPayment);

    /**
     * 批量删除付款限制条件
     * 
     * @param ids 需要删除的付款限制条件主键集合
     * @return 结果
     */
    public int deleteSupplierPaymentByIds(String[] ids);

    /**
     * 删除付款限制条件信息
     * 
     * @param id 付款限制条件主键
     * @return 结果
     */
    public int deleteSupplierPaymentById(String id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);
}
