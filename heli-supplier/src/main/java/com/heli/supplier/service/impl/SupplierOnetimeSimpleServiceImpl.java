package com.heli.supplier.service.impl;

import java.util.Date;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.listener.OnetimeSimpleListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SupplierOnetimeSimpleMapper;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.service.ISupplierOnetimeSimpleService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 一次交检合格率-简化版Service业务层处理
 * 
 * @author weill
 * @date 2025-02-21
 */
@Service
@Slf4j
public class SupplierOnetimeSimpleServiceImpl extends ServiceImpl<SupplierOnetimeSimpleMapper, SupplierOnetimeSimple> implements ISupplierOnetimeSimpleService
{
    @Autowired
    private SupplierOnetimeSimpleMapper supplierOnetimeSimpleMapper;

    /**
     * 查询一次交检合格率-简化版
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 一次交检合格率-简化版
     */
    @Override
    public SupplierOnetimeSimple selectSupplierOnetimeSimpleById(Long id)
    {
        return supplierOnetimeSimpleMapper.selectSupplierOnetimeSimpleById(id);
    }

    /**
     * 查询一次交检合格率-简化版列表
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 一次交检合格率-简化版
     */
    @Override
    public List<SupplierOnetimeSimple> selectSupplierOnetimeSimpleList(SupplierOnetimeSimple supplierOnetimeSimple)
    {
        return supplierOnetimeSimpleMapper.selectSupplierOnetimeSimpleList(supplierOnetimeSimple);
    }

    /**
     * 新增一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
//    @Override
//    public int insertSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple)
//    {
//        return supplierOnetimeSimpleMapper.insertSupplierOnetimeSimple(supplierOnetimeSimple);
//    }

    @Override
    public int insertSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple) {
        // 计算分数
        double score = calculateScore(supplierOnetimeSimple.getQuantityPassRate());
        supplierOnetimeSimple.setScore(score);

        return supplierOnetimeSimpleMapper.insert(supplierOnetimeSimple);
    }

    /**
     * 修改一次交检合格率-简化版
     * 
     * @param supplierOnetimeSimple 一次交检合格率-简化版
     * @return 结果
     */
    @Override
    public int updateSupplierOnetimeSimple(SupplierOnetimeSimple supplierOnetimeSimple)
    {
        return supplierOnetimeSimpleMapper.updateSupplierOnetimeSimple(supplierOnetimeSimple);
    }

    /**
     * 批量删除一次交检合格率-简化版
     * 
     * @param ids 需要删除的一次交检合格率-简化版主键
     * @return 结果
     */
    @Override
    public int deleteSupplierOnetimeSimpleByIds(Long[] ids)
    {
        return supplierOnetimeSimpleMapper.deleteSupplierOnetimeSimpleByIds(ids);
    }

    /**
     * 删除一次交检合格率-简化版信息
     * 
     * @param id 一次交检合格率-简化版主键
     * @return 结果
     */
    @Override
    public int deleteSupplierOnetimeSimpleById(Long id)
    {
        return supplierOnetimeSimpleMapper.deleteSupplierOnetimeSimpleById(id);
    }


    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadMonth) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());

//            this.remove(new LambdaQueryWrapper<SuppliersQualified>()
//                    .eq(SuppliersQualified::getDate,date));

            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            EasyExcel.read(excelFile.getInputStream(), SupplierOnetimeSimple.class,
                    new OnetimeSimpleListener(supplierOnetimeSimpleMapper, uploadMonth))
                    .sheet().doRead();

            log.info("读取文件成功: {}", fileName);

        } catch (Exception e) {
            log.error("读取 {} 文件失败, 原因: {}", fileName, e.getMessage());
        }
    }


//            try {
//
//                EasyExcel.read(excelFile.getInputStream(), SupplierOnetimeSimple.class, new OnetimeSimpleListener(supplierOnetimeSimpleMapper,uploadMonth)).sheet().doRead();
////                EasyExcel.read(excelFile.getInputStream(), HistoryOrderEntity.class, new HistoryOrderListener(historyOrderMapper, date)).sheet().doRead();
//                log.info("读取文件成功: {}", fileName);
//
//            } catch (Exception e) {
//                log.info("读取文件失败: {}", e.getMessage());
//            }
//
////            return R.ok("读取" + fileName + "文件成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
////            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
//        }
//    }


    /**
     * 计算分数
     * @param quantityPassRate 合格率（字符串格式，如 "98.5%" 或 "98"）
     * @return 计算后的得分
     */
    private double calculateScore(String quantityPassRate) {
        if (quantityPassRate == null || quantityPassRate.isEmpty()) {
            return 0; // 为空时默认 0 分
        }

        try {
            // 去掉可能存在的 "%" 符号，并转换为 double
            double qualifiedRate = Double.parseDouble(quantityPassRate.replace("%", "").trim());

            double baseScore = 100; // 基础分 100 分
            double deduction = 20 * (100 - qualifiedRate); // 每下降 1% 扣 20 分
            double finalScore = Math.max(0, baseScore - deduction); // 最低为 0 分
            return finalScore * 0.02; // 乘以 2% 作为模块得分
        } catch (NumberFormatException e) {
            log.error("合格率格式错误: {}", quantityPassRate);
            return 0; // 格式错误时默认 0 分
        }
    }

}
