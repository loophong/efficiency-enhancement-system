package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.domain.SupplierReturnRate;
import com.heli.supplier.mapper.SupplierOnetimeSimpleMapper;
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
public class OnetimeSimpleListener implements ReadListener<SupplierOnetimeSimple> {

    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierOnetimeSimpleMapper supplierOnetimeSimpleMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierOnetimeSimple> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);



    private int rowIndex = 0; // 添加行号计数器
    private Long batchId = System.currentTimeMillis(); // 添加批次号
    public OnetimeSimpleListener(SupplierOnetimeSimpleMapper supplierOnetimeSimpleMapper, Date uploadMonth) {
        this.supplierOnetimeSimpleMapper = supplierOnetimeSimpleMapper;
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
    public void invoke(SupplierOnetimeSimple registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);

        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUpdateMonth(uploadMonth);
            // 设置行号和批次号
            registerInfoExcel.setRowIndex(rowIndex++);
            registerInfoExcel.setBatchId(batchId);



            // 将供应商编码的前缀0去掉
            registerInfoExcel.setSupplierCode(registerInfoExcel.getSupplierCode().replaceFirst("^0+", ""));
            // 计算分数
            double score = calculateScore(registerInfoExcel.getQuantityPassRate());
            registerInfoExcel.setScore(score);

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



        // 先删除该月份的所有数据
        deleteExistingData();


//        supplierOnetimeSimpleMapper.insert(cacheDataList);
        supplierOnetimeSimpleMapper.insert(cacheDataList);
    }


    /**
     * 计算分数
     * @param quantityPassRate 合格率（字符串格式，如 "98.5%" 或 "98"）
     * @return 计算后的得分
     */
    private double calculateScore(String quantityPassRate) {
        if (quantityPassRate == null || quantityPassRate.isEmpty()) {
            return 100; // 为空时默认 100 分
        }
        try {
            double qualifiedRate;
            double baseScore = 100; // 基础分 100 分
            // 处理百分比格式
            if (quantityPassRate.contains("%")) {
                String cleanedRate = quantityPassRate.replace("%", "").trim();
                qualifiedRate = Double.parseDouble(cleanedRate);
            } else {
                // 处理小数格式
                qualifiedRate = Double.parseDouble(quantityPassRate);
                if (qualifiedRate <= 1) {
                    qualifiedRate *= 100; // 转换为百分比
                }
                // 对于 100, 99 这种整数，不需要转换，直接当作百分比使用
            }
            // 计算不合格率
            double unqualifiedRate = 100 - qualifiedRate;

            // 按1%区间计算扣分
            int deductionIntervals = (int) Math.ceil(unqualifiedRate); // 向上取整到区间数
            double deduction = deductionIntervals * 20; // 每个区间扣20分

            double finalScore = Math.max(0, baseScore - deduction); // 最低为 0 分
            return finalScore;
        } catch (NumberFormatException e) {
            log.error("合格率格式错误: {}", quantityPassRate);
            return 0; // 格式错误时默认 0 分
        }
    }

    /**
     * 删除指定月份的数据
     */
    private void deleteExistingData() {
        try {
            // 使用MyBatis-Plus的删除方法
            QueryWrapper<SupplierOnetimeSimple> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("update_month", uploadMonth);
            int deletedCount = supplierOnetimeSimpleMapper.delete(deleteWrapper);
            log.info("删除了 {} 条该月份的旧数据", deletedCount);

            // 重置行号计数器
            this.rowIndex = 0;
        } catch (Exception e) {
            log.error("删除旧数据失败: {}", e.getMessage());
        }
    }
}
