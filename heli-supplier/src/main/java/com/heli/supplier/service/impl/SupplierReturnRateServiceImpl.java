package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.listener.OnetimeSimpleListener;
import com.heli.supplier.listener.ReturnRateListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierReturnRateMapper;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.service.ISupplierReturnRateService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 售后返修率Service业务层处理
 * 
 * @author wll
 * @date 2025-02-27
 */
@Service
@Slf4j
public class SupplierReturnRateServiceImpl  extends ServiceImpl<SupplierReturnRateMapper, SupplierReturnRate> implements ISupplierReturnRateService
{
    @Autowired
    private SupplierReturnRateMapper supplierReturnRateMapper;

    /**
     * 查询售后返修率
     * 
     * @param id 售后返修率主键
     * @return 售后返修率
     */
    @Override
    public SupplierReturnRate selectSupplierReturnRateById(String id)
    {
        return supplierReturnRateMapper.selectSupplierReturnRateById(id);
    }

    /**
     * 查询售后返修率列表
     * 
     * @param supplierReturnRate 售后返修率
     * @return 售后返修率
     */
    @Override
    public List<SupplierReturnRate> selectSupplierReturnRateList(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.selectSupplierReturnRateList(supplierReturnRate);
    }

    /**
     * 新增售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    @Override
    public int insertSupplierReturnRate(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.insertSupplierReturnRate(supplierReturnRate);
    }

    /**
     * 修改售后返修率
     * 
     * @param supplierReturnRate 售后返修率
     * @return 结果
     */
    @Override
    public int updateSupplierReturnRate(SupplierReturnRate supplierReturnRate)
    {
        return supplierReturnRateMapper.updateSupplierReturnRate(supplierReturnRate);
    }

    /**
     * 批量删除售后返修率
     * 
     * @param ids 需要删除的售后返修率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReturnRateByIds(String[] ids)
    {
        return supplierReturnRateMapper.deleteSupplierReturnRateByIds(ids);
    }

    /**
     * 删除售后返修率信息
     * 
     * @param id 售后返修率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReturnRateById(String id)
    {
        return supplierReturnRateMapper.deleteSupplierReturnRateById(id);
    }
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(), SupplierReturnRate.class,
                                new ReturnRateListener(supplierReturnRateMapper,uploadMonth))
                                .sheet().doRead();
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
