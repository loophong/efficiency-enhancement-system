package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.SupplierRisk;
import com.heli.supplier.listener.PaymentListener;
import com.heli.supplier.listener.RiskListener;
import com.heli.supplier.mapper.SupplierRiskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierPaymentMapper;
import com.heli.supplier.domain.SupplierPayment;
import com.heli.supplier.service.ISupplierPaymentService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 付款限制条件Service业务层处理
 * 
 * @author wll
 * @date 2025-03-25
 */
@Service
@Slf4j
public class SupplierPaymentServiceImpl extends ServiceImpl<SupplierPaymentMapper, SupplierPayment> implements ISupplierPaymentService
{
    @Autowired
    private SupplierPaymentMapper supplierPaymentMapper;

    /**
     * 查询付款限制条件
     * 
     * @param id 付款限制条件主键
     * @return 付款限制条件
     */
    @Override
    public SupplierPayment selectSupplierPaymentById(String id)
    {
        return supplierPaymentMapper.selectSupplierPaymentById(id);
    }

    /**
     * 查询付款限制条件列表
     * 
     * @param supplierPayment 付款限制条件
     * @return 付款限制条件
     */
    @Override
    public List<SupplierPayment> selectSupplierPaymentList(SupplierPayment supplierPayment)
    {
        return supplierPaymentMapper.selectSupplierPaymentList(supplierPayment);
    }

    /**
     * 新增付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    @Override
    public int insertSupplierPayment(SupplierPayment supplierPayment)
    {
        double score = calculatePaymentScore(supplierPayment);
        supplierPayment.setScore(score);
        return supplierPaymentMapper.insertSupplierPayment(supplierPayment);
    }

    /**
     * 修改付款限制条件
     * 
     * @param supplierPayment 付款限制条件
     * @return 结果
     */
    @Override
    public int updateSupplierPayment(SupplierPayment supplierPayment)
    {
        double score = calculatePaymentScore(supplierPayment);
        supplierPayment.setScore(score);
        return supplierPaymentMapper.updateSupplierPayment(supplierPayment);
    }

    /**
     * 批量删除付款限制条件
     * 
     * @param ids 需要删除的付款限制条件主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPaymentByIds(String[] ids)
    {
        return supplierPaymentMapper.deleteSupplierPaymentByIds(ids);
    }

    /**
     * 删除付款限制条件信息
     * 
     * @param id 付款限制条件主键
     * @return 结果
     */
    @Override
    public int deleteSupplierPaymentById(String id)
    {
        return supplierPaymentMapper.deleteSupplierPaymentById(id);
    }


    /**
     *
     * @param fileName
     * @param excelFile
     * @param uploadMonth
     */
    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());
            log.info("开始读取文件: {}", fileName);
            PaymentListener listener = new PaymentListener(supplierPaymentMapper, uploadMonth);//xinzeng
            try {
                EasyExcel.read(excelFile.getInputStream(),
                                SupplierPayment.class,
                                new PaymentListener(supplierPaymentMapper,uploadMonth))
                        .sheet("付款限制条件")
                        .doRead();
                log.info("读取文件成功: {}", fileName);

//                // 构建结果消息
//                StringBuilder result = new StringBuilder();
//                result.append(String.format("导入完成！\n成功导入：%d条\n总处理：%d条",
//                        listener.getSuccessCount(),
//                        listener.getTotalCount()));
//                List<String> errors = listener.getErrorList();
//                if (!errors.isEmpty()) {
//                    result.append("\n\n导入失败的数据：\n");
//                    for (int i = 0; i < Math.min(errors.size(), 10); i++) { // 最多显示10条错误
//                        result.append(errors.get(i)).append("\n");
//                    }
//                    if (errors.size() > 10) {
//                        result.append(String.format("... 还有%d条错误数据", errors.size() - 10));
//                    }
//                }
//                return result.toString();
//                // 检查是否有错误
//                List<String> errors = listener.getErrorList();
//                if (!errors.isEmpty()) {
//                    return "导入完成，但以下数据存在问题：\n" + String.join("\n", errors);
//                }
//                return "导入成功";
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
//                throw new RuntimeException("导入失败: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            throw new RuntimeException("导入失败: " + e.getMessage());
        }
    }


    private int calculatePaymentScore(SupplierPayment supplierPayment) {
        // 优先检查备注中是否包含特殊字样
        String remark = supplierPayment.getRemark();
        if (remark != null && (remark.contains("平衡重") || remark.contains("第三方"))) {
            return 80;
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
        String errorMsg = String.format("匹配不到，供应商: [%s], 付款条件: [%s], 付款说明: [%s],备注:[%s]",
                supplierPayment.getSupplierCode(),
                paymentCondition,
                paymentDescription,
                supplierPayment.getOne());
        log.error(errorMsg);
        throw new RuntimeException(errorMsg);

    }

}
