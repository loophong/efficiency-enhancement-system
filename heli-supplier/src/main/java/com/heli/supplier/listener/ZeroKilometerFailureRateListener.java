package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
import com.heli.supplier.mapper.SupplierZeroKilometerFailureRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ClassName: OnetimeSimpleListener
 * Package: com.heli.supplier.listener
 * Description:
 *
 * @Author
 * @Create 2025/2/21 15:29
 * @Version 1.0
 */
@Slf4j
public class ZeroKilometerFailureRateListener implements ReadListener<SupplierZeroKilometerFailureRate> {

    private static final int BATCH_COUNT = 20000;

    private int currentRow = 0;

    @Autowired
    private SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierZeroKilometerFailureRate> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    private int rowIndex = 0; // 添加行号计数器
    private Long batchId = System.currentTimeMillis(); // 添加批次号
    public ZeroKilometerFailureRateListener(SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper,
                                            Date uploadMonth) {
        this.supplierZeroKilometerFailureRateMapper = supplierZeroKilometerFailureRateMapper;
        this.uploadMonth = uploadMonth;
        this.rowIndex = 0;// 重置行号
    }


    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierZeroKilometerFailureRate registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);

        // 数据处理
        if (registerInfoExcel.getSupplierName() != null) {
            registerInfoExcel.setUploadMonth(uploadMonth);
            // 设置行号和批次号
            registerInfoExcel.setRowIndex(rowIndex++);
            registerInfoExcel.setBatchId(batchId);

            if (registerInfoExcel.getPpmValue().equals("#DIV/0!") ||
                    registerInfoExcel.getPpmValue().equals("#VALUE!"))
            {
                registerInfoExcel.setPpmValue("0");
            }else
            {
                double ppm = new BigDecimal(registerInfoExcel.getPpmValue()).doubleValue();
                double target = new BigDecimal(registerInfoExcel.getTargetPpm()).doubleValue();
                double rate =((ppm - target)/target)*100;

                // 保留两位小数
                String formattedRate = String.format("%.2f", rate);

                registerInfoExcel.setPpmValue(formattedRate +"%");
            }
            currentRow++;
            // 加入缓存
            cacheDataList.add(registerInfoExcel);
        }
        // 批量处理缓存的数据
//        if (cacheDataList.size() >= BATCH_COUNT) {
//            saveToDB();
//            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
//        }
    }

    /**
     * 处理结尾, 不足100条
     *
     * @param analysisContext 全局监听内容
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveToDB();
        log.info("所有数据解析完成");
    }

    /**
     * 将读取到的内容写入DB
     */
    private void saveToDB() {
        log.info("开始写入数据库");

        deleteExistingData();

//        supplierOnetimeSimpleMapper.insert(cacheDataList);
//        supplierZeroKilometerFailureRateMapper.insert(cacheDataList);
        for (SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate : cacheDataList) {
            SupplierZeroKilometerFailureRate result = supplierZeroKilometerFailureRateMapper.selectOne(
                    new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                            .eq(SupplierZeroKilometerFailureRate::getSupplierName, supplierZeroKilometerFailureRate.getSupplierName())
                            .eq(SupplierZeroKilometerFailureRate::getUploadMonth, supplierZeroKilometerFailureRate.getUploadMonth()));
            if (result != null ){
//                result.setPpmValue(supplierZeroKilometerFailureRate.getPpmValue());
//                result.setZeroFailureRate("");
//                result.setScore(null);
//                supplierZeroKilometerFailureRateMapper.updateById(result);
                // 使用 UpdateWrapper 明确设置为 null

                UpdateWrapper<SupplierZeroKilometerFailureRate> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", result.getId())
                        .set("ppm_value", supplierZeroKilometerFailureRate.getPpmValue())
                        .set("zero_failure_rate", "")
                        .set("score", null);  // 明确设置为 null
                supplierZeroKilometerFailureRateMapper.update(null, updateWrapper);
            }else {
                supplierZeroKilometerFailureRateMapper.insert(supplierZeroKilometerFailureRate);
            }
        }
    }
    private void deleteExistingData() {
        try {
            // 使用MyBatis-Plus的删除方法
            QueryWrapper<SupplierZeroKilometerFailureRate> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("upload_month", uploadMonth);
            int deletedCount = supplierZeroKilometerFailureRateMapper.delete(deleteWrapper);
            log.info("删除了 {} 条该月份的旧数据", deletedCount);

            // 重置行号计数器
            this.rowIndex = 0;
        } catch (Exception e) {
            log.error("删除旧数据失败: {}", e.getMessage());
        }
    }
}
