package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.supplier.domain.SupplierZeroKilometerFailureRate;
import com.heli.supplier.mapper.SupplierZeroKilometerFailureRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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

    public ZeroKilometerFailureRateListener(SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper,
                                            Date uploadMonth) {
        this.supplierZeroKilometerFailureRateMapper = supplierZeroKilometerFailureRateMapper;
        this.uploadMonth = uploadMonth;
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
//        supplierOnetimeSimpleMapper.insert(cacheDataList);
//        supplierZeroKilometerFailureRateMapper.insert(cacheDataList);
        for (SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate : cacheDataList) {
            SupplierZeroKilometerFailureRate result = supplierZeroKilometerFailureRateMapper.selectOne(
                    new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                            .eq(SupplierZeroKilometerFailureRate::getSupplierName, supplierZeroKilometerFailureRate.getSupplierName())
                            .eq(SupplierZeroKilometerFailureRate::getUploadMonth, supplierZeroKilometerFailureRate.getUploadMonth()));
            if (result != null ){
                result.setPpmValue(supplierZeroKilometerFailureRate.getPpmValue());
                supplierZeroKilometerFailureRateMapper.updateById(result);
            }else {
                supplierZeroKilometerFailureRateMapper.insert(supplierZeroKilometerFailureRate);
            }
        }
    }

}
