package com.heli.supplier.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
import org.springframework.web.multipart.MultipartFile;

/**
 * 零公里故障指标完成率Service接口
 * 
 * @author wll
 * @date 2025-03-24
 */
public interface ISupplierZeroKilometerFailureRateService extends IService<SupplierZeroKilometerFailureRate>
{
    /**
     * 查询零公里故障指标完成率
     * 
     * @param id 零公里故障指标完成率主键
     * @return 零公里故障指标完成率
     */
    public SupplierZeroKilometerFailureRate selectSupplierZeroKilometerFailureRateById(String id);

    /**
     * 查询零公里故障指标完成率列表
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 零公里故障指标完成率集合
     */
    public List<SupplierZeroKilometerFailureRate> selectSupplierZeroKilometerFailureRateList(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 新增零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    public int insertSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 修改零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    public int updateSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate);

    /**
     * 批量删除零公里故障指标完成率
     * 
     * @param ids 需要删除的零公里故障指标完成率主键集合
     * @return 结果
     */
    public int deleteSupplierZeroKilometerFailureRateByIds(String[] ids);

    /**
     * 删除零公里故障指标完成率信息
     * 
     * @param id 零公里故障指标完成率主键
     * @return 结果
     */
    public int deleteSupplierZeroKilometerFailureRateById(String id);


    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile, Date uploadMonth);
}
