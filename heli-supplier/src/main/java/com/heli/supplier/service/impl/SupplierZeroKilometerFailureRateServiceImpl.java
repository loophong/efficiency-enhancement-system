package com.heli.supplier.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class SupplierZeroKilometerFailureRateServiceImpl extends ServiceImpl<SupplierZeroKilometerFailureRateMapper, SupplierZeroKilometerFailureRate> implements ISupplierZeroKilometerFailureRateService
{
    @Autowired
    private SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper;

    /**
     * 查询零公里故障指标完成率
     * 
     * @param id 零公里故障指标完成率主键
     * @return 零公里故障指标完成率
     */
    @Override
    public SupplierZeroKilometerFailureRate selectSupplierZeroKilometerFailureRateById(String id)
    {
        return supplierZeroKilometerFailureRateMapper.selectSupplierZeroKilometerFailureRateById(id);
    }

    /**
     * 查询零公里故障指标完成率列表
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 零公里故障指标完成率
     */
    @Override
    public List<SupplierZeroKilometerFailureRate> selectSupplierZeroKilometerFailureRateList(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        return supplierZeroKilometerFailureRateMapper.selectSupplierZeroKilometerFailureRateList(supplierZeroKilometerFailureRate);
    }

    /**
     * 新增零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    @Override
    public int insertSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        return supplierZeroKilometerFailureRateMapper.insertSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
    }

    /**
     * 修改零公里故障指标完成率
     * 
     * @param supplierZeroKilometerFailureRate 零公里故障指标完成率
     * @return 结果
     */
    @Override
    public int updateSupplierZeroKilometerFailureRate(SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate)
    {
        return supplierZeroKilometerFailureRateMapper.updateSupplierZeroKilometerFailureRate(supplierZeroKilometerFailureRate);
    }

    /**
     * 批量删除零公里故障指标完成率
     * 
     * @param ids 需要删除的零公里故障指标完成率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierZeroKilometerFailureRateByIds(String[] ids)
    {
        return supplierZeroKilometerFailureRateMapper.deleteSupplierZeroKilometerFailureRateByIds(ids);
    }

    /**
     * 删除零公里故障指标完成率信息
     * 
     * @param id 零公里故障指标完成率主键
     * @return 结果
     */
    @Override
    public int deleteSupplierZeroKilometerFailureRateById(String id)
    {
        return supplierZeroKilometerFailureRateMapper.deleteSupplierZeroKilometerFailureRateById(id);
    }



    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);

            try {
                int x = 2;
                // **读取第一行内容，提取时间**
                String extractedTime = extractTimeFromExcel(excelFile,x);
                if (extractedTime != null) {
                    log.info("提取的时间: {}", extractedTime);
                    uploadMonth = DateUtils.parseDate(extractedTime, "yyyy年M月");
                }
                log.info("上传月份: {}", uploadMonth);
                for( ;x <= 10; x++){
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierZeroKilometerFailureRate.class,
                                new ZeroKilometerFailureRateListener(supplierZeroKilometerFailureRateMapper,uploadMonth))
                        .sheet(x).headRowNumber(3)
                        .doRead();
                log.info("读取文件成功: {}", fileName);}
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());

        }
    }
    public String extractTimeFromExcel(MultipartFile excelFile,int x) {
        try {
            InputStream inputStream = excelFile.getInputStream();
            List<Map<Integer, String>> data = EasyExcel.read(inputStream)
                    .sheet(x) // 读取第x个 sheet
                    .headRowNumber(0) // 读取第一行
                    .doReadSync();

            if (!data.isEmpty()) {
                Map<Integer, String> firstRow = data.get(0); // 获取第一行
                StringBuilder firstRowText = new StringBuilder();
                for (String value : firstRow.values()) {
                    firstRowText.append(value).append(" ");
                }

                // **使用正则匹配 "2024年3月" 这样的格式**
                Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月");
                Matcher matcher = pattern.matcher(firstRowText.toString());

                if (matcher.find()) {
                    return matcher.group(); // **返回匹配到的 "2024年3月"**
                }
            }
        } catch (Exception e) {
            log.error("提取Excel时间失败: {}", e.getMessage());
        }
        return null;
    }

}
