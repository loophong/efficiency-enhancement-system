package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heli.supplier.domain.SupplierGuarantee;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class GuaranteeListener implements ReadListener<SupplierGuarantee> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierGuaranteeMapper supplierGuaranteeMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierGuarantee> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public GuaranteeListener(SupplierGuaranteeMapper supplierGuaranteeMapper, Date uploadMonth) {
        this.supplierGuaranteeMapper = supplierGuaranteeMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierGuarantee registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if (registerInfoExcel.getSupplierCode() != null) {
            registerInfoExcel.setUploadMonth(uploadMonth);
            double score = calculateScore(registerInfoExcel.getTimelyDeliveryRate());
            registerInfoExcel.setTimelyRateScore(score);
            currentRow++;
            // 加入缓存
            cacheDataList.add(registerInfoExcel);
        }

        // 批量处理缓存的数据
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveToDB();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
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
        supplierGuaranteeMapper.insert(cacheDataList);

//        cacheDataList.forEach(data -> {
//            SupplierGuarantee supplierGuarantee = supplierGuaranteeMapper.selectOne(new QueryWrapper<SupplierGuarantee>().eq(时间).eq(供应商编号));
//            if (supplierGuarantee != null && supplierGuarantee.size() > 0) {
////                data 是什么导入，ppm
//                supplierGuarantee.setppm(data.getppm);
//                supplierGuaranteeMapper.updateById(supplierGuarantee);
//            }else{
//                supplierGuaranteeMapper.save(data);
//            }
//        });

//        int ppm = supplierGuaranteeMapper.selectList(new QueryWrapper<SupplierGuarantee>().eq(时间).eq(供应商编号).isNotNull("ppm")).size();
//        if(ppm  ){
//
//        }
    }
    private double calculateScore(String guarantee) {
        if (guarantee == null || !guarantee.endsWith("%")) {
            throw new IllegalArgumentException("供货及时率格式错误，必须是百分比字符串，如 '85.5%'");
        }
        // 去掉百分号并转换为数值
        double rate = Double.parseDouble(guarantee.replace("%", "").trim());
        return rate;
    }
}
