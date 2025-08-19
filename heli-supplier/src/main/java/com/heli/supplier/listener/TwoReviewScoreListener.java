package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierTwoReviewScore;
import com.heli.supplier.mapper.SupplierTwoReviewScoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class TwoReviewScoreListener implements ReadListener<SupplierTwoReviewScore> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierTwoReviewScoreMapper supplierTwoReviewScoreMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierTwoReviewScore> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);



    private int rowIndex = 0; // 添加行号计数器
    private Long batchId = System.currentTimeMillis(); // 添加批次号
    public TwoReviewScoreListener(SupplierTwoReviewScoreMapper supplierTwoReviewScoreMapper, Date uploadMonth) {
        this.supplierTwoReviewScoreMapper = supplierTwoReviewScoreMapper;
        this.uploadMonth = uploadMonth;
        this.rowIndex = 0;// 重置行号
        // 先删除该月份的所有数据
        deleteExistingData();

    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierTwoReviewScore registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUpdateMonth(uploadMonth);

            // 设置行号和批次号
            registerInfoExcel.setRowIndex(rowIndex++);
            registerInfoExcel.setBatchId(batchId);

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



        supplierTwoReviewScoreMapper.insert(cacheDataList);
    }
    /**
     * 删除指定月份的数据
     */
    private void deleteExistingData() {
        try {
            // 使用MyBatis-Plus的删除方法
            QueryWrapper<SupplierTwoReviewScore> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("updateMonth", uploadMonth);
            int deletedCount = supplierTwoReviewScoreMapper.delete(deleteWrapper);
            log.info("删除了 {} 条该月份的旧数据", deletedCount);

            // 重置行号计数器
            this.rowIndex = 0;
        } catch (Exception e) {
            log.error("删除旧数据失败: {}", e.getMessage());
        }
    }
}
