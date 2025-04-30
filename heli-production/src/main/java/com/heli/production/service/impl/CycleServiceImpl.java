package com.heli.production.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.listener.CycleTableListener;
import com.heli.production.domain.entity.CycleEntity;
import com.heli.production.service.ICycleService;
import com.heli.production.mapper.CycleMapper;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_cycle(产能表)】的数据库操作Service实现
 * @createDate 2025-01-17 09:33:12
 */
@Slf4j
@Service
public class CycleServiceImpl extends ServiceImpl<CycleMapper, CycleEntity> implements ICycleService {
    @Autowired
    private CycleMapper cycleMapper;

    /**
     * 查询产能
     *
     * @param id 产能主键
     * @return 产能
     */
    @Override
    public CycleEntity selectProductionCycleById(Long id) {
        return cycleMapper.selectProductionCycleById(id);
    }

    /**
     * 查询产能列表
     *
     * @param productionCycle 产能
     * @return 产能
     */
    @Override
    public List<CycleEntity> selectProductionCycleList(CycleEntity productionCycle) {
        return cycleMapper.selectProductionCycleList(productionCycle);
    }

    /**
     * 新增产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    @Override
    public int insertProductionCycle(CycleEntity productionCycle) {
        return cycleMapper.insertProductionCycle(productionCycle);
    }

    /**
     * 修改产能
     *
     * @param productionCycle 产能
     * @return 结果
     */
    @Override
    public int updateProductionCycle(CycleEntity productionCycle) {
        return cycleMapper.updateProductionCycle(productionCycle);
    }

    /**
     * 批量删除产能
     *
     * @param ids 需要删除的产能主键
     * @return 结果
     */
    @Override
    public int deleteProductionCycleByIds(Long[] ids) {
        return cycleMapper.deleteProductionCycleByIds(ids);
    }

    /**
     * 删除产能信息
     *
     * @param id 产能主键
     * @return 结果
     */
    @Override
    public int deleteProductionCycleById(Long id) {
        return cycleMapper.deleteProductionCycleById(id);
    }

    @Override
    public R readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 清空表
            log.info("清空数据库");
            this.remove(new QueryWrapper<>());
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {
                EasyExcel.read(excelFile.getInputStream(), CycleEntity.class, new CycleTableListener(cycleMapper)).sheet("在产车型").doRead();
                log.info("读取文件成功: {}", fileName);
            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

}




