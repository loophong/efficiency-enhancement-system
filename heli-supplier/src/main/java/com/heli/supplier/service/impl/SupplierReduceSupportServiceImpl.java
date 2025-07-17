package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierPriceTrust;
import com.heli.supplier.listener.GuaranteeListener;
import com.heli.supplier.listener.OnetimeSimpleListener;
import com.heli.supplier.listener.PriceTrustListener;
import com.heli.supplier.listener.ReduceSupportListener;
import com.heli.supplier.mapper.SupplierOnetimeSimpleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierReduceSupportMapper;
import com.heli.supplier.domain.SupplierReduceSupport;
import com.heli.supplier.service.ISupplierReduceSupportService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 降本支持Service业务层处理
 * 
 * @author wll
 * @date 2025-03-06
 */
@Slf4j
@Service
public class SupplierReduceSupportServiceImpl extends ServiceImpl<SupplierReduceSupportMapper, SupplierReduceSupport> implements ISupplierReduceSupportService
{
    @Autowired
    private SupplierReduceSupportMapper supplierReduceSupportMapper;

    /**
     * 查询降本支持
     * 
     * @param id 降本支持主键
     * @return 降本支持
     */
    @Override
    public SupplierReduceSupport selectSupplierReduceSupportById(String id)
    {
        return supplierReduceSupportMapper.selectSupplierReduceSupportById(id);
    }

    /**
     * 查询降本支持列表
     * 
     * @param supplierReduceSupport 降本支持
     * @return 降本支持
     */
    @Override
    public List<SupplierReduceSupport> selectSupplierReduceSupportList(SupplierReduceSupport supplierReduceSupport)
    {
        return supplierReduceSupportMapper.selectSupplierReduceSupportList(supplierReduceSupport);
    }

    /**
     * 新增降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    @Override
    public int insertSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport)
    {
        if (supplierReduceSupport.getPercentage() == null || !supplierReduceSupport.getPercentage().endsWith("%")) {
            throw new IllegalArgumentException("格式错误，必须是百分比字符串，如 '85.5%'");
        }
        calculateAndSetScore(supplierReduceSupport);
        return supplierReduceSupportMapper.insertSupplierReduceSupport(supplierReduceSupport);
    }

    /**
     * 修改降本支持
     * 
     * @param supplierReduceSupport 降本支持
     * @return 结果
     */
    @Override
    public int updateSupplierReduceSupport(SupplierReduceSupport supplierReduceSupport)
    {
        if (supplierReduceSupport.getPercentage() == null || !supplierReduceSupport.getPercentage().endsWith("%")) {
            throw new IllegalArgumentException("格式错误，必须是百分比字符串，如 '85.5%'");
        }
        calculateAndSetScore(supplierReduceSupport);
        return supplierReduceSupportMapper.updateSupplierReduceSupport(supplierReduceSupport);
    }

    /**
     * 批量删除降本支持
     * 
     * @param ids 需要删除的降本支持主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReduceSupportByIds(String[] ids)
    {
        return supplierReduceSupportMapper.deleteSupplierReduceSupportByIds(ids);
    }

    /**
     * 删除降本支持信息
     * 
     * @param id 降本支持主键
     * @return 结果
     */
    @Override
    public int deleteSupplierReduceSupportById(String id)
    {
        return supplierReduceSupportMapper.deleteSupplierReduceSupportById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
//            EasyExcel.read(excelFile.getInputStream(), SupplierReduceSupport.class,
//                            new ReduceSupportListener(supplierReduceSupportMapper, uploadMonth))
//                    .sheet().doRead();
//            EasyExcel.read(excelFile.getInputStream(), SupplierReduceSupport.class,
//                            new ReduceSupportListener(supplierReduceSupportMapper, uploadMonth))
//                    .sheet(1).doRead();
//            EasyExcel.read(excelFile.getInputStream(), SupplierReduceSupport.class,
//                            new ReduceSupportListener(supplierReduceSupportMapper, uploadMonth))
//                    .sheet(2).doRead();
//            EasyExcel.read(excelFile.getInputStream(), SupplierReduceSupport.class,
//                            new ReduceSupportListener(supplierReduceSupportMapper, uploadMonth))
//                    .sheet(3).doRead();
//
//            log.info("读取文件成功: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierReduceSupport.class,
                                new ReduceSupportListener(supplierReduceSupportMapper,uploadMonth))
                        .sheet("降本支持")
                        .doRead();
                log.info("读取文件成功: {}", fileName);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            log.error("读取 {} 文件失败, 原因: {}", fileName, e.getMessage());
        }
    }
    /**
     * 根据 SupplierReduceSupport 的 percentage 计算 score 并设置
     *
     * @param supplierReduceSupport 降本支持对象
     */
    private void calculateAndSetScore(SupplierReduceSupport supplierReduceSupport) {
        String percentageStr = supplierReduceSupport.getPercentage();
//        if (percentageStr == null || !percentageStr.endsWith("%")) {
//            throw new IllegalArgumentException("无效的降本占比格式");
//        }
        double percentageValue;
//        try {
        if (percentageStr != null && percentageStr.endsWith("%")) {
            percentageValue = Double.parseDouble(percentageStr.replace("%", ""));
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("无法解析降本占比数值: " + percentageStr);
//        }
            int score = 10;
            if (percentageValue > 0.1 && percentageValue <= 0.5) {
                score = 20;
            } else if (percentageValue > 0.5 && percentageValue <= 1.5) {
                score = 50;
            } else if (percentageValue > 1.5 && percentageValue <= 3.0) {
                score = 80;
            } else if (percentageValue > 3.0) {
                score = 100;
            }
            supplierReduceSupport.setScore((double) score);
        }
        else {
            supplierReduceSupport.setScore(0.0);
        }

    }

}
