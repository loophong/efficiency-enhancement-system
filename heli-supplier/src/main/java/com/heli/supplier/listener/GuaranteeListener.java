package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
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

    public GuaranteeListener(SupplierGuaranteeMapper supplierGuaranteeMapper,Date uploadMonth) {
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
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadMonth(uploadMonth);
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
    }

}
