package com.heli.supplier.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.ProductionErrorTable;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.listener.ProductProcessFailuresListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.listener.ZeroKilometerFailureRateListener;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierZeroKilometerFailureRateMapper;
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
import com.heli.supplier.service.ISupplierZeroKilometerFailureRateService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 零公里故障指标完成率Service业务层处理
 *
 * @author wll
 * @date 2025-03-24
 */
@Service
@Slf4j
public class SupplierZeroKilometerFailureRateServiceImpl extends ServiceImpl<SupplierZeroKilometerFailureRateMapper, SupplierZeroKilometerFailureRate> implements ISupplierZeroKilometerFailureRateService {
    @Autowired
    private SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper;

    /**
     * 查询零公里故障指标完成率
     *
     * @param id 零公里故障指标完成率主键
     * @return 零公里故障指标完成率
     */
    @Override
    public SupplierZeroKilometerFailureRate selectSupplierZeroKilometerFailureRateById(String id) {
        return supplierZeroKilometerFailureRateMapper.selectSupplierZeroKilometerFailureRateById(id);
    }

    /**
     * 查询零公里故障指标完成率列表
     *
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 零公里故障指标完成率
     */
    @Override
    public List<SupplierZeroKilometerFailureRate> selectSupplierZeroKilometerFailureRateList(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate) {
        return supplierZeroKilometerFailureRateMapper.selectSupplierZeroKilometerFailureRateList(supplierZeroKilometerFailureRate);
    }

    /**
     * 新增零公里故障指标完成率
     *
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    @Override
    public int insertSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate) {
        supplierZeroKilometerFailureRateMapper.insertSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
        // 数据导入完成后，计算分数
        calculateScoresAfterImport(supplierZeroKilometerFailureRate.getUploadMonth());
        return 1;
    }

    /**
     * 修改零公里故障指标完成率
     *
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    @Override
    public int updateSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate) {
        supplierZeroKilometerFailureRateMapper.updateSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
        calculateScoresAfterImport(supplierZeroKilometerFailureRate.getUploadMonth());
        return 1;
    }

    /**
     * 批量删除零公里故障指标完成率
     *
     * @param ids 需要删除的零公里故障指标完成率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierZeroKilometerFailureRateByIds(String[] ids) {
        return supplierZeroKilometerFailureRateMapper.deleteSupplierZeroKilometerFailureRateByIds(ids);
    }

    /**
     * 删除零公里故障指标完成率信息
     *
     * @param id 零公里故障指标完成率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierZeroKilometerFailureRateById(String id) {
        return supplierZeroKilometerFailureRateMapper.deleteSupplierZeroKilometerFailureRateById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
//            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            String sheetName = uploadMonth.getMonth() + 1 + "月";
            try {
//                int x = 2;
//                Date month = null;
                log.info("上传月份: {}", uploadMonth);
                    EasyExcel.read(excelFile.getInputStream(),
                                    SupplierZeroKilometerFailureRate.class,
                                    new ZeroKilometerFailureRateListener(supplierZeroKilometerFailureRateMapper, uploadMonth))
                            .sheet(sheetName).headRowNumber(3)
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

    @Override
    public void readProcessFailuresTableToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {

        try {

            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                ProductionErrorTable.class,
                                new ProductProcessFailuresListener(supplierZeroKilometerFailureRateMapper,uploadMonth))
                        .sheet().headRowNumber(2)
//                        .sheet("产品过程故障率").headRowNumber(2)
                        .doRead();
                log.info("读取文件成功: {}", fileName);



                // 数据导入完成后，计算分数
                calculateScoresAfterImport(uploadMonth);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
        }
    }
    /**
     * 数据导入完成后计算分数
     */
    @Transactional
    void calculateScoresAfterImport(Date uploadMonth) {
        log.info("开始计算零公里故障率分数，月份: {}", uploadMonth);

        try {
            // 查询当月所有需要计算分数的记录
            List<SupplierZeroKilometerFailureRate> records = this.list(
                    new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                            .eq(SupplierZeroKilometerFailureRate::getUploadMonth, uploadMonth)
            );

            int updatedCount = 0;
            for (SupplierZeroKilometerFailureRate record : records) {
                // 计算分数
                BigDecimal score = calculateScore(record.getPpmValue(), record.getZeroFailureRate(),record.getTargetPpm());

                // 更新分数
                record.setScore(new Double(String.valueOf(score)));
                this.updateById(record);
                updatedCount++;

                log.debug("供应商 {} 分数计算完成: PPM={}, 故障率={}, 得分={}",
                        record.getSupplierName(), record.getPpmValue(),
                        record.getZeroFailureRate(), score);
            }

            log.info("分数计算完成，共更新 {} 条记录", updatedCount);

        } catch (Exception e) {
            log.error("分数计算失败: {}", e.getMessage(), e);
            throw new RuntimeException("分数计算失败", e);
        }
    }

    /**
     * 计算评分
     * 基础分100分，零公里故障指标完成率小于或等于零，即达标不扣分，
     * 1%以内（含）的扣10分，每超过1%加扣10分。
     */
    private BigDecimal calculateScore(String ppmValue, String zeroFailureRate, String targetPpm) {
        BigDecimal baseScore = new BigDecimal(100); // 基础分100分

//                double ppm = new BigDecimal(registerInfoExcel.getPpmValue()).doubleValue();
//                double target = new BigDecimal(registerInfoExcel.getTargetPpm()).doubleValue();
//                double rate =((ppm - target)/target)*100;
//
//                // 保留两位小数
//                String formattedRate = String.format("%.2f", rate);
//
//                registerInfoExcel.setPpmValue(formattedRate +"%");


        // 优先处理 ppmValue（产品过程故障率）

        if (ppmValue != null && !ppmValue.equals("#DIV/0!") && !ppmValue.equals("#VALUE!") && !ppmValue.trim().isEmpty()) {
            try {

                double  ppm = new BigDecimal(ppmValue).doubleValue();
                double target = new BigDecimal(targetPpm).doubleValue();
                double rate =((ppm - target)/target)*100;
//                double rate = new BigDecimal(ppmValue.replace("%", "")).doubleValue();
                if (rate <= 0) {
                    return baseScore; // 小于等于0，达标不扣分
                } else {
                    // 转换为百分比进行计算
                    double percentage = rate ;
                    int deduction;
                    if (percentage <= 1.0) {
                        deduction = 10; // 1%以内（含）扣10分
                    } else {
                        // 每超过1%加扣10分
                        int extraPercent = (int) Math.ceil(percentage - 1.0);
                        deduction = 10 + (extraPercent * 10);
                    }
                    return BigDecimal.valueOf(Math.max(0, 100 - deduction));
                }
            } catch (NumberFormatException ignored) {
                // ppm解析失败，尝试使用 zeroFailureRate
            }
        }

        // 处理 zeroFailureRate（零公里故障率）
        if (zeroFailureRate != null && !zeroFailureRate.trim().isEmpty()&&!zeroFailureRate.equals("#DIV/0!") && !zeroFailureRate.equals("#VALUE!") ) {
            try {
                double rate = new BigDecimal(zeroFailureRate.replace("%", "")).doubleValue();

                if (rate <= 0) {
                    return baseScore; // 小于等于0，达标不扣分
                } else {
                    int deduction;
                    if (rate <= 1.0) {
                        deduction = 10; // 1%以内（含）扣10分
                    } else {
                        // 每超过1%加扣10分
                        int extraPercent = (int) Math.ceil(rate - 1.0);
                        deduction = 10 + (extraPercent * 10);
                    }
                    return BigDecimal.valueOf(Math.max(0, 100 - deduction));
                }
            } catch (NumberFormatException ignored) {
                log.warn("故障率格式错误: {}", zeroFailureRate);
                return baseScore; // 解析失败返回满分
            }
        }

        return baseScore; // 都没有数据时返回满分
    }
}

