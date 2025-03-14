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
public class SupplierEvaluateServiceImpl extends ServiceImpl<SupplierEvaluateMapper, SupplierEvaluate> implements ISupplierEvaluateService {
    @Autowired
    private SupplierEvaluateMapper supplierEvaluateMapper;

    @Autowired
    private ISuppliersQualifiedService suppliersQualifiedService;

    @Autowired
    private ISupplierQualityIncidentsService supplierQualityIncidentsService;
    @Autowired
    private ISupplierOnetimeSimpleService onetimeSimpleService;

    @Autowired
    private ISupplierThreePackService supplierThreePackService;
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
//            if (supplierEvaluate == null) {
//                supplierEvaluate = new SupplierEvaluate();
//            }

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
//                supplierEvaluate.setSecondpartyAuditscore(secondpartyAuditscore(happenTime, endTime, item.getSupplierCode()));


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
}


