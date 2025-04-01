package com.heli.supplier.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.excel.EasyExcel;
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
        return supplierZeroKilometerFailureRateMapper.insertSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
    }

    /**
     * 修改零公里故障指标完成率
     *
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    @Override
    public int updateSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate) {
        return supplierZeroKilometerFailureRateMapper.updateSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
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
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
//            uploadMonth
//                    DateUtils.getNextMonth()
            String sheetName = String.valueOf(uploadMonth.getMonth() + 1) + "月";
            try {
//                int x = 2;
//                Date month = null;
                log.info("上传月份: {}", uploadMonth);
//                for (; x < 12; x++) {
//                    // **读取第一行内容，提取时间**
//                    String extractedTime = extractTimeFromExcel(excelFile, x);
//                    if (extractedTime != null) {
//                        log.info("提取的时间: {}", extractedTime);
//                        month = DateUtils.parseDate(extractedTime, "yyyy年M月");
//
//                    }
//                EasyExcel.read(excelFile.getInputStream(),
//                                SupplierZeroKilometerFailureRate.class,
//                                new ZeroKilometerFailureRateListener(supplierZeroKilometerFailureRateMapper,uploadMonth,month))
//                        .sheet(x).headRowNumber(3)
//                        .doRead();
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
                        .sheet("Sheet2").headRowNumber(2)
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

