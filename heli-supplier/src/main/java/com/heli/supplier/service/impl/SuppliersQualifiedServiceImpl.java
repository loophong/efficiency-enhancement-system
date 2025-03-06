package com.heli.supplier.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.supplier.listener.QualifiedListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.supplier.mapper.SuppliersQualifiedMapper;
import com.heli.supplier.domain.SuppliersQualified;
import com.heli.supplier.service.ISuppliersQualifiedService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 供应商Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-17
 */
@Service
@Slf4j
public class SuppliersQualifiedServiceImpl extends ServiceImpl<SuppliersQualifiedMapper, SuppliersQualified> implements ISuppliersQualifiedService {
    @Autowired
    private SuppliersQualifiedMapper suppliersQualifiedMapper;

    /**
     * 查询供应商
     *
     * @param id 供应商主键
     * @return 供应商
     */
    @Override
    public SuppliersQualified selectSuppliersQualifiedById(Long id) {
        return suppliersQualifiedMapper.selectSuppliersQualifiedById(id);
    }

    /**
     * 查询供应商列表
     *
     * @param suppliersQualified 供应商
     * @return 供应商
     */
    @Override
    public List<SuppliersQualified> selectSuppliersQualifiedList(SuppliersQualified suppliersQualified) {
        return suppliersQualifiedMapper.selectSuppliersQualifiedList(suppliersQualified);
    }

    /**
     * 新增供应商
     *
     * @param suppliersQualified 供应商
     * @return 结果
     */
    @Override
    public int insertSuppliersQualified(SuppliersQualified suppliersQualified) {
        return suppliersQualifiedMapper.insertSuppliersQualified(suppliersQualified);
    }

    /**
     * 修改供应商
     *
     * @param suppliersQualified 供应商
     * @return 结果
     */
    @Override
    public int updateSuppliersQualified(SuppliersQualified suppliersQualified) {
        return suppliersQualifiedMapper.updateSuppliersQualified(suppliersQualified);
    }

    /**
     * 批量删除供应商
     *
     * @param ids 需要删除的供应商主键
     * @return 结果
     */
    @Override
    public int deleteSuppliersQualifiedByIds(Long[] ids) {
        return suppliersQualifiedMapper.deleteSuppliersQualifiedByIds(ids);
    }

    /**
     * 删除供应商信息
     *
     * @param id 供应商主键
     * @return 结果
     */
    @Override
    public int deleteSuppliersQualifiedById(Long id) {
        return suppliersQualifiedMapper.deleteSuppliersQualifiedById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 清空表单
            this.remove(new QueryWrapper<>());

//            this.remove(new LambdaQueryWrapper<SuppliersQualified>()
//                    .eq(SuppliersQualified::getDate,date));

            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {

                EasyExcel.read(excelFile.getInputStream(), SuppliersQualified.class, new QualifiedListener(suppliersQualifiedMapper))
                        .sheet("合格供应商名录")
//                        .sheet(1)
                        .headRowNumber(4)
                        .doRead();
//                EasyExcel.read(excelFile.getInputStream(), SuppliersQualified.class, new QualifiedListener(suppliersQualifiedMapper,date))
//                        .sheet("合格供应商名录")
////                        .sheet(1)
//                        .headRowNumber(4)
//                        .doRead();
                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

//            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }
}