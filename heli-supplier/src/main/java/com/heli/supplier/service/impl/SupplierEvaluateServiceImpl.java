package com.heli.supplier.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.domain.*;
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
        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>().
                eq(SupplierEvaluate::getHappenTime, happenTime).
                eq(SupplierEvaluate::getEndTime, endTime)).size();
//        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>()).size();
        if (size > 0) {
            this.remove(new LambdaQueryWrapper<SupplierEvaluate>().eq(SupplierEvaluate::getHappenTime, happenTime).eq(SupplierEvaluate::getEndTime, endTime));
//            this.remove(new LambdaQueryWrapper<SupplierEvaluate>()

        }
        log.info("开始计算"+list.size());
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
                supplierEvaluate.setZeroKilometerFailurerate(zeroKilometerFailurerate(happenTime, endTime, item.getSupplierName()));
                supplierEvaluate.setQualityNotificationOrderrate(qualityNotificationOrderrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setFeedbackOrderletterTimeliness(feedbackOrderletterTimeliness(happenTime, endTime, item.getSupplierCode()));


                //下面三个计算没写
//                supplierEvaluate.setZeroKilometerFailurerate(BigDecimal.valueOf(0));

//                supplierEvaluate.setQualityNotificationOrderrate(qualityNotificationOrderrate(happenTime, endTime, item.getSupplierCode()));
//                supplierEvaluate.setQualityNotificationOrderrate(BigDecimal.valueOf(0));


                supplierEvaluate.setWarrantyperiodRepairrate(warrantyperiodRepairrate(happenTime, endTime, item.getSupplierName()));
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
//            log.info("计算已完成");


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
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
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
//        BigDecimal bigDecimal = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierOnetimeSimple> list = supplierOnetimeSimpleService.list(
                new LambdaQueryWrapper<SupplierOnetimeSimple>()
                        .eq(SupplierOnetimeSimple::getSupplierCode, supplierCode)
                        .between(SupplierOnetimeSimple::getUpdateMonth, happenTime, endTime));
        BigDecimal avgScore;
        if (list.size() > 0) {
            // 计算所有分数的总和
            BigDecimal totalScore = BigDecimal.ZERO;
            for (SupplierOnetimeSimple item : list) {
                totalScore = totalScore.add(new BigDecimal(item.getScore()));
            }
            // 计算平均分
            avgScore = totalScore.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        } else {
            // 没有数据时默认满分
            avgScore = new BigDecimal(100);
        }

        // 模块得分为平均分的2%
//        return avgScore*0.02;
        return avgScore.multiply(BigDecimal.valueOf(0.02)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 3.1.3零公里故障率
     */
    BigDecimal zeroKilometerFailurerate(Date happenTime, Date endTime, String supplierName) {
        BigDecimal baseScore = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierZeroKilometerFailureRate> list = supplierZeroKilometerFailureRateService.list(
                new LambdaQueryWrapper<SupplierZeroKilometerFailureRate>()
                        .eq(SupplierZeroKilometerFailureRate::getSupplierName, supplierName)
                        .between(SupplierZeroKilometerFailureRate::getUploadMonth, happenTime, endTime));
//        // 如果没有数据，返回基础分的 8%
//        if (list.isEmpty()) {
//            return baseScore.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP);
//        }
        // 遍历列表，计算最低得分
//        BigDecimal minScore = baseScore;
//        for (SupplierZeroKilometerFailureRate record : list) {
//            BigDecimal score = calculateScore(record.getPpmValue(), record.getZeroFailureRate());
//            minScore = minScore.min(score); // 取最低得分
//        }

//        return minScore.multiply(new BigDecimal("0.08")); // 取8%作为最终模块得分
        if (list.isEmpty()) {
            return baseScore.multiply(BigDecimal.valueOf(0.08)); // 返回默认值
        }
        // 计算统计期间内的平均交货及时率
        BigDecimal totalTimelyRate = BigDecimal.ZERO;
        for (SupplierZeroKilometerFailureRate zero : list) {
            if (zero.getScore() != null) {
                totalTimelyRate = totalTimelyRate.add(new BigDecimal(zero.getScore()));
            }
        }
        // 计算平均
        BigDecimal avgTimelyRate = totalTimelyRate.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        baseScore = avgTimelyRate;
        // 模块得分
        return baseScore.multiply(BigDecimal.valueOf(0.08)).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 计算评分
     */
    private BigDecimal calculateScore(String ppmValue, String zeroFailureRate) {
        BigDecimal baseScore = new BigDecimal(100); // 基础分100分

        // 优先处理 ppmValue（产品过程故障率）
        if (ppmValue != null && !ppmValue.equals("#DIV/0!") && !ppmValue.equals("#VALUE!") && !ppmValue.trim().isEmpty()) {
            try {
                double ppm = new BigDecimal(ppmValue).doubleValue();
                double rate = ppm / 1_000_000; // ppm 转换为小数（如1000ppm = 0.001 = 0.1%）

                if (rate <= 0) {
                    return baseScore; // 小于等于0，达标不扣分
                } else {
                    // 转换为百分比进行计算
                    double percentage = rate * 100;
                    int deduction;
                    if (percentage <= 1.0) {
                        deduction = 10; // 1%以内（含）扣10分
                    } else {
                        // 每超过1%加扣10分
                        int extraPercent = (int) Math.ceil(percentage - 1.0);
                        deduction = 10 + (extraPercent * 10);
                    }
                    return BigDecimal.valueOf(Math.max(0, 100 - deduction));
                }
            } catch (NumberFormatException ignored) {
                // ppm解析失败，尝试使用 zeroFailureRate
            }
        }

        // 处理 zeroFailureRate（零公里故障率）
        if (zeroFailureRate != null && !zeroFailureRate.trim().isEmpty()) {
            try {
                double rate = new BigDecimal(zeroFailureRate.replace("%", "")).doubleValue();

                if (rate <= 0) {
                    return baseScore; // 小于等于0，达标不扣分
                } else {
                    int deduction;
                    if (rate <= 1.0) {
                        deduction = 10; // 1%以内（含）扣10分
                    } else {
                        // 每超过1%加扣10分
                        int extraPercent = (int) Math.ceil(rate - 1.0);
                        deduction = 10 + (extraPercent * 10);
                    }
                    return BigDecimal.valueOf(Math.max(0, 100 - deduction));
                }
            } catch (NumberFormatException ignored) {
                return baseScore; // 解析失败返回满分
            }
        }
        return baseScore; // 都没有数据时返回满分
    }
    /**
     * 3.1.4质量通知单及时率
     */
    BigDecimal qualityNotificationOrderrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal baseScore = BigDecimal.valueOf(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierHappen> list = supplierHappenService.list(
                new LambdaQueryWrapper<SupplierHappen>()
                        .eq(SupplierHappen::getSupplierCode, supplierCode)
                        .between(SupplierHappen::getDeadline, happenTime, endTime));
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
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierHappen> list = supplierHappenService.list(
                new LambdaQueryWrapper<SupplierHappen>()
                        .eq(SupplierHappen::getSupplierCode, supplierCode)
                        .between(SupplierHappen::getDeadline, happenTime, endTime));
        // 记录回函不及时的次数
        int feedbackCount = 0;

        // 遍历每个发生记录，判断回函是否及时
        for (SupplierHappen happen : list) {
            Date completeTime = happen.getReplyTime();
            Date deadline = happen.getDeadline();

            // 判断完成时间是否晚于截止时间
            if ((completeTime != null && deadline != null && completeTime.after(deadline))|| completeTime == null) {
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
    BigDecimal warrantyperiodRepairrate(Date happenTime, Date endTime, String supplierName) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierReturnRate> list = supplierReturnRateService.list(
                new LambdaQueryWrapper<SupplierReturnRate>()
                        .eq(SupplierReturnRate::getSupplierName, supplierName)
                        .between(SupplierReturnRate::getMonth, happenTime, endTime));

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                bigDecimal = bigDecimal.add(new BigDecimal(list.get(i).getScore()));
            }
            bigDecimal = bigDecimal.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        } else {
            bigDecimal = new BigDecimal(0);
        }
        return bigDecimal;
    }

    /**
     * 3.1.7三包配件发货及时率
     */
    BigDecimal threePackageComponentRepairrate(Date happenTime, Date endTime, String supplierCode) {
//        BigDecimal bigDecimal = new BigDecimal(3);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierThreePack> list = supplierThreePackService.list(
                new LambdaQueryWrapper<SupplierThreePack>()
                        .eq(SupplierThreePack::getSupplierCode, supplierCode)
                        .between(SupplierThreePack::getResponsibilityJudgmentTime, happenTime, endTime));
        BigDecimal score = new BigDecimal(0);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
//                score = score.add(new BigDecimal(list.get(i).getScore()));
                if (list.get(i).getScore() == 0) {
                    score = BigDecimal.valueOf(0);
                    return score;
                }
            }
        }
//            score = score.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
//        } else {
            score = new BigDecimal(3);
//        }
        return score;
    }

    /**
     * 3.1.8二方审核得分
     */
    BigDecimal secondpartyAuditscore(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(1);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.list(
                new LambdaQueryWrapper<SupplierTwoReviewScore>()
                        .eq(SupplierTwoReviewScore::getSupplierCode, supplierCode)
                        .between(SupplierTwoReviewScore::getUpdateMonth, happenTime, endTime));
        // 计算不符合项的数量
        if (list.isEmpty()) {
            // 如果没有数据，默认得1分，乘以权重2%
            return BigDecimal.ONE;
        }

        // 计算该供应商的不符合项总数
        int totalNonComplianceCount = 0;
        for (SupplierTwoReviewScore score : list) {
            if (score.getNotTrue() != null && !score.getNotTrue().isEmpty()) {
                    // 将notTrue字段转换为数字并累加
                    totalNonComplianceCount ++;
            }
        }
        return totalNonComplianceCount > 3 ? BigDecimal.ZERO : BigDecimal.ONE;
    }

    /**
     * 3.1.9 自检报告准确率
     */
    BigDecimal selfInspectionAccuracy(Date happenTime, Date endTime, String supplierCode) {
//        BigDecimal bigDecimal = new BigDecimal(2);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierSelfTestReports> list = supplierSelfTestReportsService.list(
                new LambdaQueryWrapper<SupplierSelfTestReports>()
                        .eq(SupplierSelfTestReports::getSupplierCode, supplierCode)
                        .between(SupplierSelfTestReports::getResponsibilityJudgmentTime, happenTime, endTime));
        BigDecimal score = new BigDecimal(0);
        if (list.size() > 0) {
//            for (int i = 0; i < list.size(); i++) {
//                score = score.add(new BigDecimal(list.get(i).getScore()));
//            }
            score = BigDecimal.valueOf(0.0);
        } else {
            score = new BigDecimal(2);
        }
        return score;
    }

    /**
     * 3.1.10 擅自变更产品材质参数尺寸
     */
    BigDecimal productmaterialParametersizeChange(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
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
        endTime = DateUtils.getMonthEndDay(endTime);
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

        // 计算平均分
        BigDecimal totalScore = BigDecimal.ZERO;
        for (SupplierImportance item : list) {
            totalScore = totalScore.add(new BigDecimal(item.getScore()));
        }
        // 计算平均值
        importanceScore = totalScore.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);

        return importanceScore.multiply(BigDecimal.valueOf(0.02));
//        importanceScore =  new BigDecimal(list.get(0).getScore());
//        return importanceScore.multiply(BigDecimal.valueOf(0.02));
    }

    /**
     * 3.2.2 经营风险
     */
    BigDecimal businessRisk(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal riskScore = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierRisk> list = supplierRiskService.list(
                new LambdaQueryWrapper<SupplierRisk>()
                        .eq(SupplierRisk::getSupplierCode, supplierCode)
                        .between(SupplierRisk::getUploadTime, happenTime, endTime));
        if (list.isEmpty()) {
              return riskScore.multiply(BigDecimal.valueOf(0.04)); // 返回默认值
        }
        // 计算总的风险条目数量
        int totalRiskCount = 0;
        for (SupplierRisk risk : list) {
            Long riskNumber = risk.getRiskNumber();
            if (riskNumber != null) {
                totalRiskCount += riskNumber.intValue();
            }
        }
        // 每条风险扣10分
        BigDecimal deduction = BigDecimal.valueOf(totalRiskCount * 10);
        // 计算最终得分，确保不低于0
        BigDecimal finalScore = riskScore.subtract(deduction);
        finalScore = finalScore.max(BigDecimal.ZERO);
        // 返回模块得分（基础分的4%）
        return finalScore.multiply(BigDecimal.valueOf(0.04));
//        riskScore = new BigDecimal(list.get(0).getScore());
//        return riskScore.multiply(BigDecimal.valueOf(0.04));

    }

    /**
     * 3.2.3 供货保障
     */
    BigDecimal supplyGuarantee(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierGuarantee> list = supplierGuaranteeService.list(
                new LambdaQueryWrapper<SupplierGuarantee>()
                        .eq(SupplierGuarantee::getSupplierCode, supplierCode)
                        .between(SupplierGuarantee::getUploadMonth, happenTime, endTime));
        if (list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.07)); // 返回默认值
        }
        // 计算统计期间内的平均交货及时率
        BigDecimal totalTimelyRate = BigDecimal.ZERO;
        for (SupplierGuarantee guarantee : list) {
            if (guarantee.getTimelyRateScore() != null) {
                totalTimelyRate = totalTimelyRate.add(new BigDecimal(guarantee.getTimelyRateScore()));
            }
        }
        // 计算平均及时率
        BigDecimal avgTimelyRate = totalTimelyRate.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        // 基础分 = 平均及时率（已经是百分比数值，如85.5表示85.5%）
        score = avgTimelyRate;
        // 模块得分 = 基础分的7%
        return score.multiply(BigDecimal.valueOf(0.07)).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 3.2.4 价格竞争力
     */
    BigDecimal priceCompetitiveness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(60);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierPriceCompete> list = supplierPriceCompeteService.list(
                new LambdaQueryWrapper<SupplierPriceCompete>()
                        .eq(SupplierPriceCompete::getSupplierCode, supplierCode)
                        .between(SupplierPriceCompete::getUploadTime, happenTime, endTime));
        if (list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.07)); // 返回默认值
        }
        // 计算统计期间内的平均交货及时率
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierPriceCompete priceCompete : list) {
            if (priceCompete.getScore() != null) {
                total = total.add(new BigDecimal(priceCompete.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        score = avg;
        return score.multiply(BigDecimal.valueOf(0.07)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 3.2.5 降本支持
     */
    BigDecimal costReductionSupport(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierReduceSupport> list = supplierReduceSupportService.list(
                new LambdaQueryWrapper<SupplierReduceSupport>()
                        .eq(SupplierReduceSupport::getSupplierCode, supplierCode)
                        .between(SupplierReduceSupport::getUploadTime, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.06));  // 如果没有数据，返回 0 分
        }
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierReduceSupport reduceSupport : list) {
            if (reduceSupport.getScore() != null) {
                total = total.add(new BigDecimal(reduceSupport.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        score = avg;
        return score.multiply(BigDecimal.valueOf(0.06)).setScale(2, RoundingMode.HALF_UP);

        // 计算降本金额总和
//        BigDecimal reduceMoneySum = list.stream()
//                .map(s -> s.getReduceMoney() == null ? BigDecimal.ZERO : BigDecimal.valueOf(s.getReduceMoney()))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 计算最终得分（乘以 6%）
//        return score.multiply(new BigDecimal("0.06")).setScale(2, RoundingMode.DOWN);
    }





    /**
     * 3.2.6 服务与协作
     */
    BigDecimal serviceAndCoordination(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierPerformanceServicesCollaboration> list = supplierPerformanceServicesCollaborationService.list(
                new LambdaQueryWrapper<SupplierPerformanceServicesCollaboration>()
                        .eq(SupplierPerformanceServicesCollaboration::getSupplierCode, supplierCode)
                        .between(SupplierPerformanceServicesCollaboration::getTime, happenTime, endTime));
        // 校验列表是否为空
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.06)).setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierPerformanceServicesCollaboration performanceServicesCollaboration : list) {
            if (performanceServicesCollaboration.getScore() != null) {
                total = total.add(new BigDecimal(performanceServicesCollaboration.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        score = avg;
        return score.multiply(BigDecimal.valueOf(0.06)).setScale(2, RoundingMode.HALF_UP);
    }


    /**
     * 3.2.7 价格诚信
     */
    BigDecimal priceIntegrity(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(100);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierPriceTrust> list = supplierPriceTrustService.list(
                new LambdaQueryWrapper<SupplierPriceTrust>()
                        .eq(SupplierPriceTrust::getSupplierCode, supplierCode)
                        .between(SupplierPriceTrust::getTime, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierPriceTrust priceTrust : list) {
            if (priceTrust.getScore() != null) {
                total = total.add(new BigDecimal(priceTrust.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        score = avg;
        return score.multiply(BigDecimal.valueOf(0.05)).setScale(2, RoundingMode.HALF_UP);

    }



    /**
     * 3.2.8 付款限制条件
     */
    BigDecimal paymentRestrictionconditions(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal score = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierPayment> list = supplierPaymentService.list(
                new LambdaQueryWrapper<SupplierPayment>()
                        .eq(SupplierPayment::getSupplierCode, supplierCode)
                        .between(SupplierPayment::getUploadTime, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return score.multiply(BigDecimal.valueOf(0.03)).setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierPayment payment : list) {
            if (payment.getScore() != null) {
                total = total.add(new BigDecimal(payment.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        score = avg;
        return score.multiply(BigDecimal.valueOf(0.03)).setScale(2, RoundingMode.HALF_UP);
//        if (list.isEmpty()) {
//            return score.multiply(BigDecimal.valueOf(0.03)); // 返回默认值
//        }
//        score = new BigDecimal(list.get(0).getModelScore());
//        return score.setScale(1, RoundingMode.HALF_UP);
    }

    // 其他方法保持不变


    /**
     * 3.3.1新产品研发配合程度
     */
    BigDecimal newProductDevelopmentCooperationDegree(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal cooperationScore = new BigDecimal(70);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierCooperationDegree> list = supplierCooperationDegreeService.list(
                new LambdaQueryWrapper<SupplierCooperationDegree>()
                        .eq(SupplierCooperationDegree::getSupplierCode, supplierCode)
                        .between(SupplierCooperationDegree::getMonth, happenTime, endTime));
            if (list == null || list.isEmpty()) {
                return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
            }
            BigDecimal total = BigDecimal.ZERO;
            for (SupplierCooperationDegree degree : list) {
                if (degree.getScore() != null) {
                    total = total.add(new BigDecimal(degree.getScore()));
                }
            }
            BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
            cooperationScore = avg;
            return cooperationScore.setScale(2, RoundingMode.HALF_UP);
//        return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 3.3.2 产品技术问题整改及时性
     */
    BigDecimal productTechnologyProblemImprovementTimeliness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal cooperationScore = new BigDecimal(70);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getMonthEndDay(endTime);
        List<SupplierRectificationTimeliness> list = supplierRectificationTimelinessService.list(
                new LambdaQueryWrapper<SupplierRectificationTimeliness>()
                        .eq(SupplierRectificationTimeliness::getSupplierCode, supplierCode)
                        .between(SupplierRectificationTimeliness::getMonth, happenTime, endTime));
        if (list == null || list.isEmpty()) {
            return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal total = BigDecimal.ZERO;
        for (SupplierRectificationTimeliness degree : list) {
            if (degree.getScore() != null) {
                total = total.add(new BigDecimal(degree.getScore()));
            }
        }
        BigDecimal avg = total.divide(new BigDecimal(list.size()), 2, RoundingMode.HALF_UP);
        cooperationScore = avg;
        return cooperationScore.setScale(2, RoundingMode.HALF_UP);
//        return cooperationScore.multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP);
    }


//    @Override
//    public boolean deleteSupplierEvaluateByTimeRange(Date happenTime, Date endTime) {
//       log.info("删除时间段内评分表数据：{} - {}", happenTime, endTime);
//        return this.remove(new LambdaQueryWrapper<SupplierEvaluate>()
//                .eq(SupplierEvaluate::getHappenTime, happenTime)
//                .eq(SupplierEvaluate::getEndTime, endTime));
//    }

}
