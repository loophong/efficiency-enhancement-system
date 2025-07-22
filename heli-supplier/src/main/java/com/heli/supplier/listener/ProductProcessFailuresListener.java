package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.heli.supplier.domain.ProductionErrorTable;
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
public class ProductProcessFailuresListener implements ReadListener<ProductionErrorTable> {

    private static final int BATCH_COUNT = 20000;
    @Autowired
    private SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper;
    private Date uploadMonth;

    private List<ProductionErrorTable> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public ProductProcessFailuresListener(SupplierZeroKilometerFailureRateMapper supplierZeroKilometerFailureRateMapper, Date uploadMonth) {
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
    public void invoke(ProductionErrorTable registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        if (registerInfoExcel.getSupplierName() != null) {
            cacheDataList.add(registerInfoExcel);
        }
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
        int curMonth = uploadMonth.getMonth() + 1;

        for (ProductionErrorTable item : cacheDataList) {

            String result = "";
            // 数据处理
            if (curMonth == 1) {
                result = item.getOne();
            } else if (curMonth == 2) {
                result = item.getTwo();
            }
            else if (curMonth == 3) {
                result = item.getThree();
            }
            else if (curMonth == 4) {
                result = item.getFour();
            }
            else if (curMonth == 5) {
                result = item.getFive();
            }
            else if (curMonth == 6) {
                result = item.getSix();
            }
            else if (curMonth == 7) {
                result = item.getSeven();
            }

            else if (curMonth == 8) {
                result = item.getEight();
            }
            else if (curMonth == 9) {
                result = item.getNine();
            }
            else if (curMonth == 10) {
                result = item.getTen();
            }
            else if (curMonth == 11) {
                result = item.getEleven();
            }
            else if (curMonth == 12) {
                result = item.getTwelve();
            }

//            result = result.replace("\\%", "");
//            if(result.startsWith("=")){
//                result = "";
//            }

            SupplierZeroKilometerFailureRate selectOne = supplierZeroKilometerFailureRateMapper.selectOne(
                    new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                            .eq(SupplierZeroKilometerFailureRate::getSupplierName, item.getSupplierName())
                            .eq(SupplierZeroKilometerFailureRate::getUploadMonth, uploadMonth));
            if (selectOne != null) {
                selectOne.setZeroFailureRate(result);
                supplierZeroKilometerFailureRateMapper.updateById(selectOne);
            } else {
                SupplierZeroKilometerFailureRate supplierZeroKilometerFailureRate = new SupplierZeroKilometerFailureRate();
                supplierZeroKilometerFailureRate.setSupplierName(item.getSupplierName());
                supplierZeroKilometerFailureRate.setUploadMonth(uploadMonth);
                supplierZeroKilometerFailureRate.setZeroFailureRate(result);
                supplierZeroKilometerFailureRateMapper.insert(supplierZeroKilometerFailureRate);
            }
        }
    }

}
