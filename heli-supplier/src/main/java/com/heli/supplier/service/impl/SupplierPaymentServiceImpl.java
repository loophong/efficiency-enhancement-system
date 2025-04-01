package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.listener.PaymentListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPaymentMapper;
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.service.ISupplierPaymentService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 付款限制条件Service业务层处理
 * 
 * @author wll
 * @date 2025-03-25
 */
@Service
@Slf4j
public class SupplierPaymentServiceImpl extends ServiceImpl<SupplierPaymentMapper, SupplierPayment> implements ISupplierPaymentService
{
    @Autowired
    private SupplierPaymentMapper supplierPaymentMapper;

    /**
     * 查询付款限制条件
     * 
     * @param id 付款限制条件主键
     * @return 付款限制条件
     */
    @Override
    public SupplierPayment selectSupplierPaymentById(String id)
    {
        return supplierPaymentMapper.selectSupplierPaymentById(id);
    }

    /**
     * 查询付款限制条件列表
     * 
     * @param supplierPayment 付款限制条件
     * @return 付款限制条件
     */
    @Override
    public List<SupplierPayment> selectSupplierPaymentList(SupplierPayment supplierPayment)
    {
        return supplierPaymentMapper.selectSupplierPaymentList(supplierPayment);
    }

    /**
     * 新增付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    @Override
    public int insertSupplierPayment(SupplierPayment supplierPayment)
    {
        return supplierPaymentMapper.insertSupplierPayment(supplierPayment);
    }

    /**
     * 修改付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    @Override
    public int updateSupplierPayment(SupplierPayment supplierPayment)
    {
        return supplierPaymentMapper.updateSupplierPayment(supplierPayment);
    }

    /**
     * 批量删除付款限制条件
     * 
     * @param ids 需要删除的付款限制条件主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPaymentByIds(String[] ids)
    {
        return supplierPaymentMapper.deleteSupplierPaymentByIds(ids);
    }

    /**
     * 删除付款限制条件信息
     * 
     * @param id 付款限制条件主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPaymentById(String id)
    {
        return supplierPaymentMapper.deleteSupplierPaymentById(id);
    }


    /**
     *
     * @param fileName
     * @param excelFile
     * @param uploadMonth
     */
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierPayment.class,
                                new PaymentListener(supplierPaymentMapper,uploadMonth))
                        .sheet("付款限制条件")
                        .doRead();
                log.info("读取文件成功: {}", fileName);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());

        }
    }


}
