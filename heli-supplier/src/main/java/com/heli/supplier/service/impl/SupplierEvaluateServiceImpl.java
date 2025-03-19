package com.heli.supplier.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.*;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.service.*;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierEvaluateMapper;

/**
 * 评分表Service业务层处理
 *
 * @author wll
 * @date 2025-03-13
 */
@Slf4j
@Service
public class SupplierEvaluateServiceImpl  extends ServiceImpl<SupplierEvaluateMapper, SupplierEvaluate>  implements ISupplierEvaluateService {
    @Autowired
    private SupplierEvaluateMapper supplierEvaluateMapper;

    @Autowired
    private ISuppliersQualifiedService suppliersQualifiedService;

    @Autowired
    private ISupplierQualityIncidentsService supplierQualityIncidentsService;//3.1.1
    @Autowired
    private ISupplierOnetimeSimpleService onetimeSimpleService;//3.1.2


    @Autowired
    private ISupplierReturnRateService supplierReturnRateService;//3.1.6

    @Autowired
    private ISupplierThreePackService supplierThreePackService;//3.1.7

    @Autowired
    private ISupplierTwoReviewScoreService supplierTwoReviewScoreService;//3.1.8

    @Autowired
    private ISupplierSelfTestReportsService supplierSelfTestReportsService;//3.1.9

    @Autowired
    private ISupplierChangeService supplierChangeService;//3.1.10

    /**
     * 查询评分表
     *
     * @param id 评分表主键
     * @return 评分表
     */
    @Override
    public SupplierEvaluate selectSupplierEvaluateById(String id) {
        return supplierEvaluateMapper.selectSupplierEvaluateById(id);
    }

    /**
     * 查询评分表列表
     *
     * @param supplierEvaluate 评分表
     * @return 评分表
     */
    @Override
    public List<SupplierEvaluate> selectSupplierEvaluateList(SupplierEvaluate supplierEvaluate) {
        return supplierEvaluateMapper.selectSupplierEvaluateList(supplierEvaluate);
    }

    /**
     * 新增评分表
     *
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    @Override
    public int insertSupplierEvaluate(SupplierEvaluate supplierEvaluate) {
        return supplierEvaluateMapper.insertSupplierEvaluate(supplierEvaluate);
    }

    /**
     * 修改评分表
     *
     * @param supplierEvaluate 评分表
     * @return 结果
     */
    @Override
    public int updateSupplierEvaluate(SupplierEvaluate supplierEvaluate) {
        return supplierEvaluateMapper.updateSupplierEvaluate(supplierEvaluate);
    }

    /**
     * 批量删除评分表
     *
     * @param ids 需要删除的评分表主键
     * @return 结果
     */
    @Override
    public int deleteSupplierEvaluateByIds(String[] ids) {
        return supplierEvaluateMapper.deleteSupplierEvaluateByIds(ids);
    }

    /**
     * 删除评分表信息
     *
     * @param id 评分表主键
     * @return 结果
     */
    @Override
    public int deleteSupplierEvaluateById(String id) {
        return supplierEvaluateMapper.deleteSupplierEvaluateById(id);
    }


    /**
     * 计算评分
     *
     * @param happenTime
     * @param endTime
     * @return
     */
    @Override
    public int calculateScore(Date happenTime, Date endTime) {
        List<SuppliersQualified> list = suppliersQualifiedService.list();
        list.forEach(item -> {
            SupplierEvaluate supplierEvaluate = this.getOne(new LambdaQueryWrapper<SupplierEvaluate>()
                    .eq(SupplierEvaluate::getSupplierCode, item.getSupplierCode())
                    .eq(SupplierEvaluate::getHappenTime, happenTime)
                    .eq(SupplierEvaluate::getEndTime, endTime));
//            SupplierEvaluate supplierEvaluate = new SupplierEvaluate();
            // 如果 supplierEvaluate 为 null，则初始化一个新的 SupplierEvaluate 对象
            if (supplierEvaluate == null) {
                supplierEvaluate = new SupplierEvaluate();
            }

            supplierEvaluate.setSupplierCode(item.getSupplierCode());
            supplierEvaluate.setSupplierName(item.getSupplierName());
            supplierEvaluate.setHappenTime(happenTime);
            supplierEvaluate.setEndTime(endTime);
            // 质量绩效
            boolean b = qualityIncident(happenTime, endTime, item.getSupplierCode());
            supplierEvaluate.setQualityIncident(BigDecimal.valueOf(0));
            if (b) {
                supplierEvaluate.setFirstInspectionPassrate(BigDecimal.valueOf(0));
                supplierEvaluate.setZeroKilometerFailurerate(BigDecimal.valueOf(0));
                supplierEvaluate.setQualityNotificationOrderrate(BigDecimal.valueOf(0));
                supplierEvaluate.setFeedbackOrderletterTimeliness(BigDecimal.valueOf(0));
                supplierEvaluate.setWarrantyperiodRepairrate(BigDecimal.valueOf(0));
                supplierEvaluate.setThreepackageComponentRepairrate(BigDecimal.valueOf(0));
                supplierEvaluate.setSecondpartyAuditscore(BigDecimal.valueOf(0));
                supplierEvaluate.setSelfinspectionReportaccuracy(BigDecimal.valueOf(0));
                supplierEvaluate.setProductmaterialParametersizeChange(BigDecimal.valueOf(0));
            } else {
                supplierEvaluate.setFirstInspectionPassrate(firstInspectionPassrate(happenTime, endTime, item.getSupplierCode()));

//                supplierEvaluate.setZeroKilometerFailurerate(zeroKilometerFailurerate(happenTime, endTime, item.getSupplierCode()));
//                supplierEvaluate.setQualityNotificationOrderrate(qualityNotificationOrderrate(happenTime, endTime, item.getSupplierCode()));
//                supplierEvaluate.setFeedbackOrderletterTimeliness(feedbackOrderletterTimeliness(happenTime, endTime, item.getSupplierCode()));
//                supplierEvaluate.setWarrantyperiodRepairrate(warrantyperiodRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setThreepackageComponentRepairrate(threePackageComponentRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSecondpartyAuditscore(secondpartyAuditscore(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSelfinspectionReportaccuracy(selfInspectionAccuracy(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setProductmaterialParametersizeChange(productmaterialParametersizeChange(happenTime, endTime, item.getSupplierCode()));


            }

            // todo: 计算全部分数


            //保存计算结果
            this.saveOrUpdate(supplierEvaluate);

        });
        return 1;
    }

    /**
     * 3.1.1质量事故得分计算
     */
    boolean qualityIncident(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        List<SupplierQualityIncidents> list = supplierQualityIncidentsService.list(
                new LambdaQueryWrapper<SupplierQualityIncidents>()
                        .eq(SupplierQualityIncidents::getSupplierCode, supplierCode)
                        .between(SupplierQualityIncidents::getUploadTime, happenTime, endTime)
        );
        return list.size() > 0;
    }

    /**
     * 3.1.2一次交检合格率
     */
    BigDecimal firstInspectionPassrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
//        log.info("happenTime:{}", happenTime);
//        log.info("endTime:{}", endTime);
        List<SupplierOnetimeSimple> list = onetimeSimpleService.list(
                new LambdaQueryWrapper<SupplierOnetimeSimple>()
                        .eq(SupplierOnetimeSimple::getSupplierCode, supplierCode)
                        .between(SupplierOnetimeSimple::getUpdateMonth, happenTime, endTime));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal.divide(new BigDecimal(list.size()));
        }else{
            bigDecimal = new BigDecimal(2);
        }
        log.info("一次交检合格率:{},供应商编号：{}", bigDecimal,supplierCode);
        log.info("list:{}",list);
        return bigDecimal;
    }
    /**
     * 3.1.3零公里故障率
     */
//    BigDecimal zeroKilometerFailurerate(Date happenTime, Date endTime, String supplierCode) {
//        BigDecimal bigDecimal = new BigDecimal(0);
//        happenTime = DateUtils.getMonthFirstDay(happenTime);
//        endTime = DateUtils.getLastMonthEndDay(endTime);
//        List<SupplierOnetimeSimple> list = onetimeSimpleService.list(
//                new LambdaQueryWrapper<SupplierOnetimeSimple>()
//                        .eq(SupplierOnetimeSimple::getSupplierCode, supplierCode)
//        );
//        return bigDecimal;
//    }

    /**
     * 3.1.4质量通知单及时率
     */
//    BigDecimal qualityNotificationOrderrate(Date happenTime, Date endTime, String supplierCode) {
//        BigDecimal bigDecimal = new BigDecimal(0);
//        happenTime = DateUtils.getMonthFirstDay(happenTime);
//        endTime = DateUtils.getLastMonthEndDay(endTime);
//        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.list(
//                new LambdaQueryWrapper<SupplierTwoReviewScore>()
//                        .eq(SupplierTwoReviewScore::getSupplierCode, supplierCode)
//        );
//        return bigDecimal;
//    }

    /**
     * 3.1.6保修期内市场售后返修率
     */
    BigDecimal warrantyperiodRepairrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierReturnRate> list = supplierReturnRateService.list(
                new LambdaQueryWrapper<SupplierReturnRate>()
                        .eq(SupplierReturnRate::getSupplierCode, supplierCode)
                        .between(SupplierReturnRate::getMonth, happenTime, endTime));

        return bigDecimal;
    }

    /**
     * 3.1.7三包配件发货及时率
     */
    BigDecimal threePackageComponentRepairrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(3);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierThreePack> list = supplierThreePackService.list(
                new LambdaQueryWrapper<SupplierThreePack>()
                        .eq(SupplierThreePack::getSupplierCode, supplierCode)
                        .between(SupplierThreePack::getHappenTime, happenTime, endTime));

        for (SupplierThreePack supplierThreePack : list) {
            if (supplierThreePack.getActualDeliveryTime().after(supplierThreePack.getPlannedDeliveryTime())) {
                bigDecimal = BigDecimal.ZERO;
                break;
            }
        }
        return bigDecimal;
    }
    /**
     * 3.1.8二方审核得分
     */
    BigDecimal secondpartyAuditscore(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(1);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.list(
                new LambdaQueryWrapper<SupplierTwoReviewScore>()
                        .eq(SupplierTwoReviewScore::getSupplierCode, supplierCode)
                        .between(SupplierTwoReviewScore::getTime, happenTime, endTime));
        // 计算不符合项的数量
        int notTrueCount = 0;
        for (SupplierTwoReviewScore supplierTwoReviewScore : list) {
            if (supplierTwoReviewScore.getNotTrue1() != null && !supplierTwoReviewScore.getNotTrue1().isEmpty()) {
                notTrueCount++;
            }
            if (supplierTwoReviewScore.getNotTrue2() != null && !supplierTwoReviewScore.getNotTrue2().isEmpty()) {
                notTrueCount++;
            }
            if (supplierTwoReviewScore.getNotTrue3() != null && !supplierTwoReviewScore.getNotTrue3().isEmpty()) {
                notTrueCount++;
            }
            if (supplierTwoReviewScore.getNotTrue4() != null && !supplierTwoReviewScore.getNotTrue4().isEmpty()) {
                notTrueCount++;
            }
            if (supplierTwoReviewScore.getNotTrue5() != null && !supplierTwoReviewScore.getNotTrue5().isEmpty()) {
                notTrueCount++;
            }
        }
        // 根据不符合项数量来计算得分
        if (notTrueCount > 3) {
            return BigDecimal.ZERO; // 不符合项超过3条，得分为0
        } else {
            return BigDecimal.ONE; // 不符合项不超过3条，得分为1
        }
    }
    /**
     * 3.1.9 自检报告准确率
     */
    BigDecimal selfInspectionAccuracy(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(2);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierSelfTestReports> list = supplierSelfTestReportsService.list(
                new LambdaQueryWrapper<SupplierSelfTestReports>()
                        .eq(SupplierSelfTestReports::getSupplierCode, supplierCode)
                        .between(SupplierSelfTestReports::getHappenTime, happenTime, endTime));
        boolean isFraudDetected = false; // 标志是否检测到造假行为
        for (SupplierSelfTestReports supplierSelfTestReports : list) {
            // 检查 specificContent 是否为空
            if (supplierSelfTestReports.getSpecificContent() == null || supplierSelfTestReports.getSpecificContent().isEmpty()) {
                isFraudDetected = true;
                break; // 发现造假行为后直接退出循环
            }
        }
        if (isFraudDetected) {
            bigDecimal = BigDecimal.ZERO; // 如果存在造假行为，得分为0
        }
        return bigDecimal;
    }
    /**
     * 3.1.10 擅自变更产品材质参数尺寸
     */
    BigDecimal productmaterialParametersizeChange(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal baseScore = new BigDecimal(100);
        BigDecimal score = baseScore;
        // 查询该供应商的所有变更记录
        List<SupplierChange> list = supplierChangeService.list(
                new LambdaQueryWrapper<SupplierChange>()
                        .eq(SupplierChange::getSupplierCode, supplierCode)
                        .between(SupplierChange::getHappenTime, happenTime, endTime));
        if (list.isEmpty()) {
            return baseScore.multiply(new BigDecimal(0.03)); // 没有变更记录，得分=100×3%
        }
        // 取该供应商的供应商类型（假设所有记录的 formLeibie 值都相同）
        long supplierType = list.get(0).getFormLeibie();
        // 统计变更次数
        int changeCount = list.size();
        // 根据供应商类型扣分
        if (supplierType == 20) { // 股份供应商
            score = score.subtract(new BigDecimal(20 * changeCount));
        } else if (supplierType == 40) { // 自主供应商
            score = score.subtract(new BigDecimal(40 * changeCount));
        }
        // 计算最终得分（基础分 100 的 3%），确保不低于 0
        return score.multiply(new BigDecimal(0.03)).max(BigDecimal.ZERO);
    }

    // 其他方法保持不变

}



