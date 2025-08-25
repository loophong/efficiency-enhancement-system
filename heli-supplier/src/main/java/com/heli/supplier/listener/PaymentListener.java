package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.mapper.SupplierPaymentMapper;
import com.heli.supplier.mapper.SupplierRiskMapper;
import com.ruoyi.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Slf4j
public class PaymentListener implements ReadListener<SupplierPayment> {
    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierPaymentMapper supplierPaymentMapper;

    private Date date;
    private Date uploadMonth;

    private List<SupplierPayment> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public PaymentListener(SupplierPaymentMapper supplierPaymentMapper,Date uploadMonth) {
        this.supplierPaymentMapper = supplierPaymentMapper;
        this.uploadMonth = uploadMonth;
    }

    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */

//
//    private List<String> errorList = new ArrayList<>(); // 添加这一行收集错误
//    private int successCount = 0; // 成功导入数量
//    private int totalCount = 0;   // 总处理数量
    @Override
    public void invoke(SupplierPayment registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);
        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadTime(uploadMonth);
            currentRow++;
            double score = calculatePaymentScore(registerInfoExcel);
            registerInfoExcel.setScore(score);
            // 加入缓存
            cacheDataList.add(registerInfoExcel);
        }
        // 批量处理缓存的数据
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveToDB();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }
//        totalCount++;
        // 检查供应商代码，没有就跳过
//        if(registerInfoExcel.getSupplierCode() == null || registerInfoExcel.getSupplierCode().trim().isEmpty()) {
//            currentRow++;
//            return;
//        }
//        registerInfoExcel.setUploadTime(uploadMonth);
//        currentRow++;
//        try {
//            double score = calculatePaymentScore(registerInfoExcel);
//            registerInfoExcel.setScore(score);
//            successCount++; // 成功计数
//        } catch (RuntimeException e) {
//            // 收集错误信息
//            String error = String.format("第%d行: %s 付款条件无法匹配", currentRow, registerInfoExcel.getSupplierCode());
//            errorList.add(error);
//            registerInfoExcel.setScore(0.0);
//}
//        cacheDataList.add(registerInfoExcel);

//    public int getSuccessCount() { return successCount; }
//    public int getTotalCount() { return totalCount; }
//    public List<String> getErrorList() { // 添加获取错误的方法
//        return errorList;
//        }
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
        supplierPaymentMapper.insert(cacheDataList);
    }
    /**
     * 根据付款条件和付款说明计算得分
     * @param supplierPayment 供应商付款信息
     * @return 计算后的得分
     * @throws RuntimeException 当匹配不到任何条件时抛出异常
     */
    private int calculatePaymentScore(SupplierPayment supplierPayment) {
        // 优先检查备注中是否包含特殊字样
        String remark = supplierPayment.getRemark();
        String paymentTerms = supplierPayment.getPaymentTerms();
        if (remark != null && (remark.contains("平衡重") || remark.contains("第三方"))) {
            return 80;
        }
        if (remark == null && paymentTerms == null ) {
            return 0;
        }
        // 定义所有可能的付款条件和对应分数
        Map<String, Integer> paymentRules = new HashMap<>();

        // 添加所有付款条件规则
        paymentRules.put("Z003", 80);
        paymentRules.put("基准日25号，60天付款", 80);
        paymentRules.put("Z005", 60);
        paymentRules.put("基准日25号，30天付款", 60);
        paymentRules.put("J001", 60);
        paymentRules.put("基准日期为当月结束，到期日为下月底", 60);
        paymentRules.put("1", 20);
        paymentRules.put("立即应付的 到期净值", 20);
        paymentRules.put("Y006", 80);
        paymentRules.put("30天首付50%，余款60天结清", 80);
        paymentRules.put("Y008", 100);
        paymentRules.put("60天首付50%，余款80天结清", 100);
        paymentRules.put("Y003", 100);
        paymentRules.put("50天首付50%，余款100天结清", 100);
        paymentRules.put("Z001", 80);
        paymentRules.put("基准日25号，40天付款", 80);
        paymentRules.put("Y007", 100);
        paymentRules.put("40天首付50%，余款80天结清", 100);
        paymentRules.put("Z002", 80);
        paymentRules.put("基准日25号，50天付款", 80);
        paymentRules.put("Z011", 100);
        paymentRules.put("基准日25号，80天付款", 100);
        paymentRules.put("Y013", 80);
        paymentRules.put("30天首付90%，余款60天结清", 80);
        paymentRules.put("Z025", 60);
        paymentRules.put("挂账后次月付清", 60);
        paymentRules.put("Y005", 100);
        paymentRules.put("40天首付60%，余款80天结清", 100);
        paymentRules.put("Z023", 60);
        paymentRules.put("基准日25号，20天付款", 60);
        paymentRules.put("Y010", 80);
        paymentRules.put("20天首付70%，余款40天结清", 80);
        paymentRules.put("Z007", 0);
        paymentRules.put("款到发货", 0);

        // 先检查付款条件
        String paymentCondition = supplierPayment.getPaymentTerms();
        if (paymentCondition != null && !paymentCondition.trim().isEmpty()) {
            String condition = paymentCondition.trim();
            if (paymentRules.containsKey(condition)) {
                return paymentRules.get(condition);
            }
        }

        // 再检查付款说明
        String paymentDescription = supplierPayment.getOne();
        if (paymentDescription != null && !paymentDescription.trim().isEmpty()) {
            String description = paymentDescription.trim();
            if (paymentRules.containsKey(description)) {
                return paymentRules.get(description);
            }
        }

        // 如果都匹配不到，抛出异常
        String errorMsg = String.format("匹配不到付款条件，供应商: [%s], 付款条件: [%s], 付款说明: [%s]",
                supplierPayment.getSupplierCode(),
                paymentCondition,
                paymentDescription);
        log.error(errorMsg);
        throw new RuntimeException(errorMsg);

    }
}
