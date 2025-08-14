package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.listener.ComplexHeaderReturnRateListener;
import com.heli.supplier.listener.OnetimeSimpleListener;
//import com.heli.supplier.listener.ReturnRateListener;  ///原来的监听器
import com.ruoyi.common.utils.DateUtils;
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
        double score = calculateScore(supplierReturnRate.getReturnRate());
        supplierReturnRate.setScore(score);
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
        double score = calculateScore(supplierReturnRate.getReturnRate());
        supplierReturnRate.setScore(score);
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
//            this.remove(new QueryWrapper<>());
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {

                EasyExcel.read(excelFile.getInputStream(),
                                new ComplexHeaderReturnRateListener(supplierReturnRateMapper, uploadMonth))
//                                SupplierReturnRate.class, new ReturnRateListener(supplierReturnRateMapper,uploadMonth))
                        .sheet().headRowNumber(1)
//                        .sheet("售后返修率").headRowNumber(1)
                        .doRead();
//                                .sheet(1).doRead();

               log.info("读取文件成功: {}", fileName);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
        }
    }

    /**
     * 根据返修率计算最终得分
     * @param returnRate 返修率（字符串格式，如 "85.5%"）
     * @return 最终得分（BigDecimal 格式）
     */
    private double calculateScore(String returnRate) {
//        if (returnRate == null || !returnRate.endsWith("%")) {
//            throw new IllegalArgumentException("返修率格式错误，必须是百分比字符串，如 '85.5%'");
//        }

        // 去掉百分号并转换为数值
        double rate = Double.parseDouble(returnRate.replace("%", "").trim());
        rate = 100 - rate;
        // 计算基础分
        double baseScore;
        if (rate < 80) {
            baseScore = 0;
        } else if (rate < 90) {
            baseScore = 30;
        } else if (rate < 100) {
            baseScore = 60;
        } else if(rate >= 100) {
            baseScore = 100;
        }else{
            baseScore = 0;
        }
        // 计算最终得分
        return baseScore * 0.08;
    }

}
