package com.heli.supplier.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.*;
import com.heli.supplier.mapper.SupplierCooperationDegreeMapper;
import com.heli.supplier.mapper.SupplierGuaranteeMapper;
import com.heli.supplier.service.*;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierEvaluateMapper;
import org.springframework.transaction.annotation.Transactional;

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


    //质量绩效
    @Autowired
    private ISuppliersQualifiedService suppliersQualifiedService;

    @Autowired
    private ISupplierQualityIncidentsService supplierQualityIncidentsService;//3.1.1
    @Autowired
    private ISupplierOnetimeSimpleService supplierOnetimeSimpleService;//3.1.2

    @Autowired
    private ISupplierZeroKilometerFailureRateService supplierZeroKilometerFailureRateService;//3.1.3

    @Autowired
    private ISupplierHappenService supplierHappenService;//3.1.4   3.1.5
//
//    @Autowired
//    private ISupplierOnetimeSimpleService onetimeSimpleService;//3.1.5

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


    //供货绩效
    @Autowired
    private ISupplierImportanceService supplierImportanceService;//3.2.1

    @Autowired
    private ISupplierRiskService supplierRiskService;//3.2.2

    @Autowired
    private ISupplierGuaranteeService supplierGuaranteeService;//3.2.3

    @Autowired
    private ISupplierPriceCompeteService supplierPriceCompeteService;//3.2.4

    @Autowired
    private ISupplierReduceSupportService supplierReduceSupportService;//3.2.5

    @Autowired
    private ISupplierPerformanceServicesCollaborationService supplierPerformanceServicesCollaborationService;//3.2.6

    @Autowired
    private ISupplierPriceTrustService supplierPriceTrustService;//3.2.7

    @Autowired
    private ISupplierPaymentService supplierPaymentService;//3.2.8

    //研发绩效
    @Autowired
    private ISupplierCooperationDegreeService supplierCooperationDegreeService;//3.3.1

    @Autowired
    private ISupplierRectificationTimelinessService supplierRectificationTimelinessService;//3.3.2

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
    @Transactional
    public int calculateScore(Date happenTime, Date endTime) {
        List<SuppliersQualified> list = suppliersQualifiedService.list();
        // 检查当前周期是否计算，如果已经计算则清空计算数据
//        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>().
//                eq(SupplierEvaluate::getHappenTime, happenTime).
//                eq(SupplierEvaluate::getEndTime, endTime)).size();
        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>()).size();
        if (size > 0) {
            //this.remove(new LambdaQueryWrapper<SupplierEvaluate>().eq(SupplierEvaluate::getHappenTime, happenTime).eq(SupplierEvaluate::getEndTime, endTime));
            this.remove(new LambdaQueryWrapper<SupplierEvaluate>()
            );
        }
        list.forEach(item -> {
            SupplierEvaluate supplierEvaluate = new SupplierEvaluate();

            supplierEvaluate.setSupplierCode(item.getSupplierCode());
            supplierEvaluate.setSupplierName(item.getSupplierName());
            supplierEvaluate.setHappenTime(happenTime);
            supplierEvaluate.setEndTime(endTime);

            // 质量绩效
            boolean b = qualityIncident(happenTime, endTime, item.getSupplierCode());
            supplierEvaluate.setQualityIncident(BigDecimal.valueOf(0));
            if (b) {
                supplierEvaluate.setQualityIncident(BigDecimal.valueOf(1));
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
                supplierEvaluate.setZeroKilometerFailurerate(zeroKilometerFailurerate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setQualityNotificationOrderrate(qualityNotificationOrderrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setFeedbackOrderletterTimeliness(feedbackOrderletterTimeliness(happenTime, endTime, item.getSupplierCode()));


                //下面三个计算没写
//                supplierEvaluate.setZeroKilometerFailurerate(BigDecimal.valueOf(0));

//                supplierEvaluate.setQualityNotificationOrderrate(qualityNotificationOrderrate(happenTime, endTime, item.getSupplierCode()));
//                supplierEvaluate.setQualityNotificationOrderrate(BigDecimal.valueOf(0));


                supplierEvaluate.setWarrantyperiodRepairrate(warrantyperiodRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setThreepackageComponentRepairrate(threePackageComponentRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSecondpartyAuditscore(secondpartyAuditscore(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSelfinspectionReportaccuracy(selfInspectionAccuracy(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setProductmaterialParametersizeChange(productmaterialParametersizeChange(happenTime, endTime, item.getSupplierCode()));
            }
            //供货绩效
            supplierEvaluate.setWeight(weight(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setBusinessRisk(businessRisk(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setSupplyGuarantee(supplyGuarantee(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setPriceCompetitiveness(priceCompetitiveness(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setCostreductionSupport(costReductionSupport(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setServiceAndCoordination(serviceAndCoordination(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setPriceIntegrity(priceIntegrity(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setPaymentRestrictionconditions(paymentRestrictionconditions(happenTime, endTime, item.getSupplierCode()));

            //研发绩效
            supplierEvaluate.setNewproductDevelopmentCooperationdegree(newProductDevelopmentCooperationDegree(happenTime, endTime, item.getSupplierCode()));
            supplierEvaluate.setProducttechnologyproblemImprovementtimeliness(productTechnologyProblemImprovementTimeliness(happenTime, endTime, item.getSupplierCode()));

            // 计算全部分数
            BigDecimal totalScore = calculateTotalScore(supplierEvaluate);
            supplierEvaluate.setTotalScore(totalScore);

            //保存计算结果
            this.save(supplierEvaluate);

        });
        return 1;
    }

    /**
     * 计算全部分数
     */
    BigDecimal calculateTotalScore(SupplierEvaluate supplierEvaluate) {
        BigDecimal totalScore = BigDecimal.ZERO;
        // 质量绩效
        totalScore = totalScore.add(supplierEvaluate.getFirstInspectionPassrate());
        totalScore = totalScore.add(supplierEvaluate.getZeroKilometerFailurerate());
        totalScore = totalScore.add(supplierEvaluate.getQualityNotificationOrderrate());
        totalScore = totalScore.add(supplierEvaluate.getFeedbackOrderletterTimeliness());
        totalScore = totalScore.add(supplierEvaluate.getWarrantyperiodRepairrate());
        totalScore = totalScore.add(supplierEvaluate.getThreepackageComponentRepairrate());
        totalScore = totalScore.add(supplierEvaluate.getSecondpartyAuditscore());
        totalScore = totalScore.add(supplierEvaluate.getSelfinspectionReportaccuracy());
        totalScore = totalScore.add(supplierEvaluate.getProductmaterialParametersizeChange());

        // 供货绩效
        totalScore = totalScore.add(supplierEvaluate.getWeight());
        totalScore = totalScore.add(supplierEvaluate.getBusinessRisk());
        totalScore = totalScore.add(supplierEvaluate.getSupplyGuarantee());
        totalScore = totalScore.add(supplierEvaluate.getPriceCompetitiveness());
        totalScore = totalScore.add(supplierEvaluate.getServiceAndCoordination());
        totalScore = totalScore.add(supplierEvaluate.getPriceIntegrity());
        totalScore = totalScore.add(supplierEvaluate.getPaymentRestrictionconditions());

        // 研发绩效
        totalScore = totalScore.add(supplierEvaluate.getNewproductDevelopmentCooperationdegree());
        totalScore = totalScore.add(supplierEvaluate.getProducttechnologyproblemImprovementtimeliness());

        // 确保总分不超过100
        totalScore = totalScore.min(BigDecimal.valueOf(100));

        return totalScore.setScale(2, RoundingMode.HALF_UP);
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
        List<SupplierOnetimeSimple> list = supplierOnetimeSimpleService.list(
                new LambdaQueryWrapper<SupplierOnetimeSimple>()
                        .eq(SupplierOnetimeSimple::getSupplierCode, supplierCode)
                        .between(SupplierOnetimeSimple::getUpdateMonth, happenTime, endTime));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal.divide(new BigDecimal(list.size()));
        } else {
            bigDecimal = new BigDecimal(2);
        }
//        log.info("一次交检合格率:{},供应商编号：{}", bigDecimal,supplierCode);
//        log.info("list:{}",list);
        return bigDecimal;
    }

    /**
     * 3.1.3零公里故障率   不会做
     */
    BigDecimal zeroKilometerFailurerate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal baseScore = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierZeroKilometerFailureRate> list = supplierZeroKilometerFailureRateService.list(
                new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                        .eq(SupplierZeroKilometerFailureRate::getSupplierName, supplierCode)
                        .between(SupplierZeroKilometerFailureRate::getUploadMonth, happenTime, endTime));
//        // 如果没有数据，返回基础分的 8%
//        if (list.isEmpty()) {
//            return baseScore.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP);
//        }
        // 遍历列表，计算最低得分
        BigDecimal minScore = baseScore;
        for (SupplierZeroKilometerFailureRate record : list) {
            BigDecimal score = calculateScore(record.getPpmValue(), record.getZeroFailureRate());
            minScore = minScore.min(score); // 取最低得分
        }

        return minScore.multiply(new BigDecimal("0.08")); // 取8%作为最终模块得分
    }

    /**
     * 计算评分
     */
    private BigDecimal calculateScore(String ppmValue, String zeroFailureRate) {
        BigDecimal baseScore = new BigDecimal(100); // 基础分
        // 处理 ppmValue
        if (ppmValue != null && !ppmValue.equals("#DIV/0!") && !ppmValue.equals("#VALUE!")) {
            try {
                double ppm = new BigDecimal(ppmValue).doubleValue();
                double rate = ppm / 1_000_000; // ppm 转换为百分比

                if (rate > 0) {
                    int deduction = (int) Math.ceil(rate * 100) * 10;
                    return BigDecimal.valueOf(Math.max(0, 100 - deduction));
                }
            } catch (NumberFormatException ignored) {
                // 解析失败，使用 zeroFailureRate
            }
        }

        // 处理 zeroFailureRate
        if (zeroFailureRate != null ) {
            try {
                double rate = new BigDecimal(zeroFailureRate.replace("%", "")).divide(new BigDecimal("100")).doubleValue();
                int deduction = (int) Math.ceil(rate * 100) * 10;
                return BigDecimal.valueOf(Math.max(0, 100 - deduction));
            } catch (NumberFormatException ignored) {
                return baseScore;
            }
        }
        return baseScore; // 默认 0 分
    }

//    /**
//     * 判断 PPM 值是否有效（数字且不是 #DIV/0! 或 #VALUE!）
//     */
//    private boolean isValidPpmValue(String ppmValue) {
//        if (ppmValue == null || ppmValue.trim().isEmpty()) {
//            return false;
//        }
//        // 判断是否是有效的数字，并且不等于 #DIV/0! 或 #VALUE!
//        return ppmValue.matches("^-?\\d+(\\.\\d+)?$") && !ppmValue.equals("#DIV/0!") && !ppmValue.equals("#VALUE!");
//    }
//
//    /**
//     * 判断 ZeroFailureRate 是否有效（百分率，例如 5%）
//     */
//    private boolean isValidZeroFailureRate(String zeroFailureRate) {
//        if (zeroFailureRate == null || zeroFailureRate.trim().isEmpty()) {
//            return false;
//        }
//        // 判断是否是有效的百分率（包含 % 符号）
//        if (!zeroFailureRate.matches("^-?\\d+(\\.\\d+)?%$") || zeroFailureRate.equals("#DIV/0!") || zeroFailureRate.equals("#VALUE!")) {
//            return false;
//        }
//        // 去掉百分号并转换为小数
//        BigDecimal value = new BigDecimal(zeroFailureRate.replace("%", ""));
//        return value.compareTo(BigDecimal.ZERO) >= 0 && value.compareTo(BigDecimal.valueOf(100)) <= 0; // 百分率范围是0到100
//    }

    /**
     * 3.1.4质量通知单及时率
     */
    BigDecimal qualityNotificationOrderrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal baseScore = BigDecimal.valueOf(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierHappen> list = supplierHappenService.list(
                new LambdaQueryWrapper<SupplierHappen>()
                        .eq(SupplierHappen::getSupplierCode, supplierCode)
                        .between(SupplierHappen::getHappenTime, happenTime, endTime));
        int feedbackCount = list.size();
        // 每次发生质量信息反馈单扣 20 分
        BigDecimal totalDeduction = BigDecimal.valueOf(feedbackCount * 20);

        // 扣除总分
        baseScore = baseScore.subtract(totalDeduction);

        // 计算最终得分为基础分的 8%
        BigDecimal finalScore = baseScore.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP);

        // 确保得分不低于 0
        return finalScore.max(BigDecimal.ZERO);

    }

    /**
     * 3.1.5 反馈单回函及时率
     */
    BigDecimal feedbackOrderletterTimeliness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal baseScore = BigDecimal.valueOf(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierHappen> list = supplierHappenService.list(
                new LambdaQueryWrapper<SupplierHappen>()
                        .eq(SupplierHappen::getSupplierCode, supplierCode)
                        .between(SupplierHappen::getHappenTime, happenTime, endTime));
        // 记录回函不及时的次数
        int feedbackCount = 0;

        // 遍历每个发生记录，判断回函是否及时
        for (SupplierHappen happen : list) {
            Date completeTime = happen.getCompleteTime();
            Date deadline = happen.getDeadline();

            // 判断完成时间是否晚于截止时间
            if (completeTime != null && deadline != null && completeTime.after(deadline)) {
                // 如果回函不及时，扣除 40 分
                feedbackCount++;
            }
        }

        // 每次回函不及时扣 40 分
        BigDecimal scoreDeduction = BigDecimal.valueOf(feedbackCount * 40);

        // 计算最终得分，模块得分为基础分的 5%
        BigDecimal finalScore = baseScore.subtract(scoreDeduction).multiply(BigDecimal.valueOf(0.05));

        // 确保得分不低于 0
        return finalScore.max(BigDecimal.ZERO);
    }


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
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal.divide(new BigDecimal(list.size()));
        } else {
            bigDecimal = new BigDecimal(8);
        }
//        log.info("一次交检合格率:{},供应商编号：{}", bigDecimal,supplierCode);
//        log.info("list:{}",list);
        return bigDecimal;
    }

    /**
     * 3.1.7三包配件发货及时率
     */
    BigDecimal threePackageComponentRepairrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierThreePack> list = supplierThreePackService.list(
                new LambdaQueryWrapper<SupplierThreePack>()
                        .eq(SupplierThreePack::getSupplierCode, supplierCode)
                        .between(SupplierThreePack::getResponsibilityJudgmentTime, happenTime, endTime));
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal.divide(new BigDecimal(list.size()));
        } else {
            bigDecimal = new BigDecimal(3);
        }
//        log.info("一次交检合格率:{},供应商编号：{}", bigDecimal,supplierCode);
//        log.info("list:{}",list);
        return bigDecimal;
//        for (SupplierThreePack supplierThreePack : list) {
//            if (supplierThreePack.getActualDeliveryTime().after(supplierThreePack.getPlannedDeliveryTime())) {
//                bigDecimal = BigDecimal.ZERO;
//                break;
//            }
//        }
//        return bigDecimal;
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
//        if (notTrueCount > 3) {
//            return BigDecimal.ZERO; // 不符合项超过3条，得分为0
//        } else {
//            return BigDecimal.ONE; // 不符合项不超过3条，得分为1
//        }
        return notTrueCount > 3 ? BigDecimal.ZERO : BigDecimal.ONE;
    }

    /**
     * 3.1.9 自检报告准确率
     */
    BigDecimal selfInspectionAccuracy(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierSelfTestReports> list = supplierSelfTestReportsService.list(
                new LambdaQueryWrapper<SupplierSelfTestReports>()
                        .eq(SupplierSelfTestReports::getSupplierCode, supplierCode)
                        .between(SupplierSelfTestReports::getResponsibilityJudgmentTime, happenTime, endTime));
//        boolean isFraudDetected = false; // 标志是否检测到造假行为
//        for (SupplierSelfTestReports supplierSelfTestReports : list) {
//            // 检查 specificContent 是否为空
//            if (supplierSelfTestReports.getSpecificContent() == null || supplierSelfTestReports.getSpecificContent().isEmpty()) {
//                isFraudDetected = true;
//                break; // 发现造假行为后直接退出循环
//            }
//        }
//        if (isFraudDetected) {
//            bigDecimal = BigDecimal.ZERO; // 如果存在造假行为，得分为0
//        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal.divide(new BigDecimal(list.size()));
        } else {
            bigDecimal = new BigDecimal(2);
        }
        return bigDecimal;
    }

    /**
     * 3.1.10 擅自变更产品材质参数尺寸
     */
    BigDecimal productmaterialParametersizeChange(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        // 查询该供应商的所有变更记录
        List<SupplierChange> list = supplierChangeService.list(
                new LambdaQueryWrapper<SupplierChange>()
                        .eq(SupplierChange::getSupplierCode, supplierCode)
                        .between(SupplierChange::getResponsibilityJudgmentTime, happenTime, endTime));
        int time = list.size();
        if (time > 0) {
            // 假设 formLeibie 是 SupplierChange 对象的一个属性
            long formLeibie = list.get(0).getFormLeibie(); // 获取第一个记录的供应商类别
            if (formLeibie == 20) { // 股份共有供应商
//                    bigDecimal = BigDecimal.valueOf(100-time * 20);
                bigDecimal = 100 - time * 20 > 0 ? BigDecimal.valueOf(100 - time * 20) : BigDecimal.ZERO;
            } else if (formLeibie == 40) { // 自主供应商
//                    bigDecimal = BigDecimal.valueOf(100-time * 40);
                bigDecimal = 100 - time * 40 > 0 ? BigDecimal.valueOf(100 - time * 40) : BigDecimal.ZERO;
            } else {
                bigDecimal = new BigDecimal(100);
            }
        }
        return bigDecimal.multiply(new BigDecimal(0.03));
    }


    /**
     * 3.2.1 重要度
     */
    BigDecimal weight(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal importanceScore = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierImportance> list = supplierImportanceService.list(
                new LambdaQueryWrapper<SupplierImportance>()
                        .eq(SupplierImportance::getSupplierCode, supplierCode)
                        .between(SupplierImportance::getUploadTime, happenTime, endTime));
        // 检查列表是否为空
        if (list.isEmpty()) {
            // 如果列表为空，返回默认值或抛出异常
            log.warn("No data found for supplierCode: {}, happenTime: {}, endTime: {}", supplierCode, happenTime, endTime);
            return importanceScore; // 返回默认值 0
        }

        importanceScore =  new BigDecimal(list.get(0).getScore());

        return importanceScore.multiply(BigDecimal.valueOf(0.02));
    }

    /**
     * 3.2.2 经营风险
     */
    BigDecimal businessRisk(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal importanceScore = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierRisk> list = supplierRiskService.list(
                new LambdaQueryWrapper<SupplierRisk>()
                        .eq(SupplierRisk::getSupplierCode, supplierCode)
                        .between(SupplierRisk::getUploadTime, happenTime, endTime));
        if (list.isEmpty()) {
              return importanceScore.multiply(BigDecimal.valueOf(0.04)); // 返回默认值
        }
        importanceScore = new BigDecimal(list.get(0).getScore());
        return importanceScore.multiply(BigDecimal.valueOf(0.04));

    }

    /**
     * 3.2.3 供货保障
     */
    BigDecimal supplyGuarantee(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierGuarantee> list = supplierGuaranteeService.list(
                new LambdaQueryWrapper<SupplierGuarantee>()
                        .eq(SupplierGuarantee::getSupplierCode, supplierCode)
                        .between(SupplierGuarantee::getUploadMonth, happenTime, endTime));
        if (list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.07)); // 返回默认值
        }
        score = new BigDecimal(list.get(0).getModelScore());
        return score.setScale(1, RoundingMode.HALF_UP);
    }


    /**
     * 3.2.4 价格竞争力
     */
    BigDecimal priceCompetitiveness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(60);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierPriceCompete> list = supplierPriceCompeteService.list(
                new LambdaQueryWrapper<SupplierPriceCompete>()
                        .eq(SupplierPriceCompete::getSupplierCode, supplierCode)
                        .between(SupplierPriceCompete::getUploadTime, happenTime, endTime));
        if (list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.07)); // 返回默认值
        }
        score = new BigDecimal(list.get(0).getModelScore());
        return score.setScale(1, RoundingMode.HALF_UP);
    }

    /**
     * 3.2.5 降本支持
     */
    BigDecimal costReductionSupport(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(10);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierReduceSupport> list = supplierReduceSupportService.list(
                new LambdaQueryWrapper<SupplierReduceSupport>()
                        .eq(SupplierReduceSupport::getSupplierCodePast, supplierCode)
                        .between(SupplierReduceSupport::getUploadTime, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.06));  // 如果没有数据，返回 0 分
        }
        // 计算降本金额总和
        BigDecimal reduceMoneySum = list.stream()
                .map(s -> s.getReduceMoney() == null ? BigDecimal.ZERO : BigDecimal.valueOf(s.getReduceMoney()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算供货金额总和
        BigDecimal supplyAmountSum = list.stream()
                .map(s -> s.getSupplyAmount() == null ? BigDecimal.ZERO : BigDecimal.valueOf(s.getSupplyAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算降本占比
        BigDecimal percentage = (supplyAmountSum.compareTo(BigDecimal.ZERO) == 0)
                ? BigDecimal.ZERO
                : reduceMoneySum.divide(supplyAmountSum, 4, RoundingMode.DOWN);

        if (percentage.compareTo(new BigDecimal("0.001")) <= 0) {
            score = BigDecimal.TEN; // 10 分
        } else if (percentage.compareTo(new BigDecimal("0.005")) <= 0) {
            score = new BigDecimal("20"); // 20 分
        } else if (percentage.compareTo(new BigDecimal("0.015")) <= 0) {
            score = new BigDecimal("50"); // 50 分
        } else if (percentage.compareTo(new BigDecimal("0.03")) <= 0) {
            score = new BigDecimal("80"); // 80 分
        } else {
            score = new BigDecimal("100"); // 100 分
        }
        // 计算最终得分（乘以 6%）
        return score.multiply(new BigDecimal("0.06")).setScale(2, RoundingMode.DOWN);
    }





    /**
     * 3.2.6 服务与协作
     */
    BigDecimal serviceAndCoordination(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierPerformanceServicesCollaboration> list = supplierPerformanceServicesCollaborationService.list(
                new LambdaQueryWrapper<SupplierPerformanceServicesCollaboration>()
                        .eq(SupplierPerformanceServicesCollaboration::getSupplierCode, supplierCode)
                        .between(SupplierPerformanceServicesCollaboration::getTime, happenTime, endTime));
        // 校验列表是否为空
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.06)).setScale(2, RoundingMode.HALF_UP);
        }
        long letter = 0;
        long punish = 0;
        long feedbackNotTimely = 0;
        // 获取数据并处理可能的 null 值
        letter = (list.get(0).getLetter() != null) ? list.get(0).getLetter() : 0;
        punish = (list.get(0).getPunish() != null) ? list.get(0).getPunish() : 0;
        feedbackNotTimely = (list.get(0).getFeedbackNotTimely() != null) ? list.get(0).getFeedbackNotTimely() : 0;
        // 计算扣分
        BigDecimal deduction = BigDecimal.valueOf((letter * 20) + (punish * 40) + (feedbackNotTimely * 10));
        // 计算最终得分
        BigDecimal finalScore = score.subtract(deduction);
        // 确保得分不能小于 0
        finalScore = finalScore.max(BigDecimal.ZERO);
        // 计算得分，确保最终分数也不能小于 0
        return finalScore.multiply(BigDecimal.valueOf(0.06)).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 3.2.7 价格诚信
     */
    BigDecimal priceIntegrity(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierPriceTrust> list = supplierPriceTrustService.list(
                new LambdaQueryWrapper<SupplierPriceTrust>()
                        .eq(SupplierPriceTrust::getSupplierCode, supplierCode)
                        .between(SupplierPriceTrust::getTime, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        }
        long happenNumber = (list.get(0).getHappenNumber() != null) ? list.get(0).getHappenNumber() : 0;
        BigDecimal finalScore = score.subtract(BigDecimal.valueOf(happenNumber*20));
        finalScore = finalScore.max(BigDecimal.ZERO);
        return finalScore.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
    }



    /**
     * 3.2.8 付款限制条件
     */
    BigDecimal paymentRestrictionconditions(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierPayment> list = supplierPaymentService.list(
                new LambdaQueryWrapper<SupplierPayment>()
                        .eq(SupplierPayment::getSupplierCode, supplierCode)
                        .between(SupplierPayment::getUploadTime, happenTime, endTime));
        if (list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.03)); // 返回默认值
        }
        score = new BigDecimal(list.get(0).getModelScore());
        return score.setScale(1, RoundingMode.HALF_UP);
    }

    // 其他方法保持不变


    /**
     * 3.3.1新产品研发配合程度
     */
    BigDecimal newProductDevelopmentCooperationDegree(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal cooperationScore = new BigDecimal(70);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierCooperationDegree> list = supplierCooperationDegreeService.list(
                new LambdaQueryWrapper<SupplierCooperationDegree>()
                        .eq(SupplierCooperationDegree::getSupplierCode, supplierCode)
                        .between(SupplierCooperationDegree::getMonth, happenTime, endTime));
        if (list != null && !list.isEmpty()) {
            BigDecimal cooperationDegree = new BigDecimal(list.get(0).getCooperationDegree()); // 假设 getCooperationDegree() 是获取配合度的方法

            if (cooperationDegree == null) {
                return BigDecimal.valueOf(7); // 如果为空，则返回默认值 70
            }

            switch (cooperationDegree.intValue()) {
                case 100: // 配合度高，满足设计时效
                    cooperationScore = BigDecimal.valueOf(100);
                    break;
                case 40: // 配合意愿较低，影响生产进度
                    cooperationScore = BigDecimal.valueOf(40);
                    break;
                case 0: // 配合差，影响生产
                    cooperationScore = BigDecimal.ZERO;
                    break;
                default:
                    cooperationScore = BigDecimal.valueOf(70); // 其他情况，保持默认值
            }
        }

        // 计算最终得分：模块得分 = 评定得分 10%
        return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 3.3.2 产品技术问题整改及时性
     */
    BigDecimal productTechnologyProblemImprovementTimeliness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal cooperationScore = new BigDecimal(70);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierRectificationTimeliness> list = supplierRectificationTimelinessService.list(
                new LambdaQueryWrapper<SupplierRectificationTimeliness>()
                        .eq(SupplierRectificationTimeliness::getSupplierCode, supplierCode)
                        .between(SupplierRectificationTimeliness::getMonth, happenTime, endTime));
        if (list != null && !list.isEmpty()) {
            BigDecimal cooperationDegree = new BigDecimal(list.get(0).getCooperationDegree()); // 假设 getCooperationDegree() 是获取配合度的方法

            if (cooperationDegree == null) {
                return BigDecimal.valueOf(7); // 如果为空，则返回默认值 70
            }

            switch (cooperationDegree.intValue()) {
                case 100: // 配合度高，满足设计时效
                    cooperationScore = BigDecimal.valueOf(100);
                    break;
                case 40: // 配合意愿较低，影响生产进度
                    cooperationScore = BigDecimal.valueOf(40);
                    break;
                case 0: // 配合差，影响生产
                    cooperationScore = BigDecimal.ZERO;
                    break;
                default:
                    cooperationScore = BigDecimal.valueOf(70); // 其他情况，保持默认值
            }
        }

        // 计算最终得分：模块得分 = 评定得分 10%
        return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
    }

}
