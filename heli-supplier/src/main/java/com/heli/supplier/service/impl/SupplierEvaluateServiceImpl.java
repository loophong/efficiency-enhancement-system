package com.heli.supplier.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Transactional
    public int calculateScore(Date happenTime, Date endTime) {
        List<SuppliersQualified> list = suppliersQualifiedService.list();
        // 检查当前周期是否计算，如果已经计算则清空计算数据
//        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>().
//                eq(SupplierEvaluate::getHappenTime, happenTime).
//                eq(SupplierEvaluate::getEndTime, endTime)).size();
        int size = this.list(new LambdaQueryWrapper<SupplierEvaluate>()).size();
        if(size > 0){
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
                //supplierEvaluate.setZeroKilometerFailurerate(zeroKilometerFailurerate(//happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setZeroKilometerFailurerate(BigDecimal.valueOf(0));
                supplierEvaluate.setQualityNotificationOrderrate(BigDecimal.valueOf(0));
                supplierEvaluate.setFeedbackOrderletterTimeliness(BigDecimal.valueOf(0));
                supplierEvaluate.setWarrantyperiodRepairrate(warrantyperiodRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setThreepackageComponentRepairrate(threePackageComponentRepairrate(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSecondpartyAuditscore(secondpartyAuditscore(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setSelfinspectionReportaccuracy(selfInspectionAccuracy(happenTime, endTime, item.getSupplierCode()));
                supplierEvaluate.setProductmaterialParametersizeChange(productmaterialParametersizeChange(happenTime, endTime, item.getSupplierCode()));


            }

            // todo: 计算全部分数


            //保存计算结果
            this.save(supplierEvaluate);

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
//        log.info("一次交检合格率:{},供应商编号：{}", bigDecimal,supplierCode);
//        log.info("list:{}",list);
        return bigDecimal;
    }
    /**
     * 3.1.3零公里故障率   不会做
     */
    BigDecimal zeroKilometerFailurerate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
//        happenTime = DateUtils.getMonthFirstDay(happenTime);
//        endTime = DateUtils.getLastMonthEndDay(endTime);
//        List<SupplierOnetimeSimple> list = onetimeSimpleService.list(
//                new LambdaQueryWrapper<SupplierOnetimeSimple>()
//                        .eq(SupplierOnetimeSimple::getSupplierCode, supplierCode)
//                       );
        return bigDecimal;
    }

    /**
     * 3.1.4质量通知单及时率
     */
    BigDecimal qualityNotificationOrderrate(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.list(
                new LambdaQueryWrapper<SupplierTwoReviewScore>()
                        .eq(SupplierTwoReviewScore::getSupplierCode, supplierCode)
        );
        return bigDecimal;
    }

    /**
     * 3.1.5 反馈单回函及时率
     */
    BigDecimal feedbackOrderletterTimeliness(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(0);
        happenTime = DateUtils.getMonthFirstDay(happenTime);
        endTime = DateUtils.getLastMonthEndDay(endTime);
        List<SupplierTwoReviewScore> list = supplierTwoReviewScoreService.list(
                new LambdaQueryWrapper<SupplierTwoReviewScore>()
                        .eq(SupplierTwoReviewScore::getSupplierCode, supplierCode)
        );
        return bigDecimal;
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
        }else{
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
        }else{
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
        }else{
            bigDecimal = new BigDecimal(2);
        }
        return bigDecimal;
    }
    /**
     * 3.1.10 擅自变更产品材质参数尺寸
     */
    BigDecimal productmaterialParametersizeChange(Date happenTime, Date endTime, String supplierCode) {
        BigDecimal bigDecimal = new BigDecimal(100);
        // 查询该供应商的所有变更记录
        List<SupplierChange> list = supplierChangeService.list(
                new LambdaQueryWrapper<SupplierChange>()
                        .eq(SupplierChange::getSupplierCode, supplierCode)
                        .between(SupplierChange::getHappenTime, happenTime, endTime));
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

    // 其他方法保持不变

}



