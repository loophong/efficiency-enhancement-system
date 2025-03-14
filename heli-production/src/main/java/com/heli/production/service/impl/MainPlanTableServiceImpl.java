package com.heli.production.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.Listener.MainPlanTableListener;
import com.heli.production.domain.entity.MainPlanTableEntity;
import com.heli.production.service.IMainPlanTableService;
import com.heli.production.mapper.MainPlanTableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author loophong
 * @description 针对表【production_main_plan_table】的数据库操作Service实现
 * @createDate 2025-01-07 17:57:23
 */
@Slf4j
@Service
public class MainPlanTableServiceImpl extends ServiceImpl<MainPlanTableMapper, MainPlanTableEntity> implements IMainPlanTableService {
    @Autowired
    private MainPlanTableMapper mainPlanTableMapper;

    /**
     * 查询主计划表
     *
     * @param id 主计划表主键
     * @return 主计划表
     */
    @Override
    public MainPlanTableEntity selectMainPlanTableById(String id) {
        return mainPlanTableMapper.selectMainPlanTableById(id);
    }

    /**
     * 查询主计划表列表
     *
     * @param mainPlanTable 主计划表
     * @return 主计划表
     */
    @Override
    public List<MainPlanTableEntity> selectMainPlanTableList(MainPlanTableEntity mainPlanTable) {
        return mainPlanTableMapper.selectMainPlanTableList(mainPlanTable);
    }

    /**
     * 新增主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    @Override
    public int insertMainPlanTable(MainPlanTableEntity mainPlanTable) {
        return mainPlanTableMapper.insertMainPlanTable(mainPlanTable);
    }

    /**
     * 修改主计划表
     *
     * @param mainPlanTable 主计划表
     * @return 结果
     */
    @Override
    public int updateMainPlanTable(MainPlanTableEntity mainPlanTable) {
        return mainPlanTableMapper.updateMainPlanTable(mainPlanTable);
    }

    /**
     * 批量删除主计划表
     *
     * @param ids 需要删除的主计划表主键
     * @return 结果
     */
    @Override
    public int deleteMainPlanTableByIds(String[] ids) {
        return mainPlanTableMapper.deleteMainPlanTableByIds(ids);
    }

    /**
     * 删除主计划表信息
     *
     * @param id 主计划表主键
     * @return 结果
     */
    @Override
    public int deleteMainPlanTableById(String id) {
        return mainPlanTableMapper.deleteMainPlanTableById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile, Date uploadDate) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            this.remove(new LambdaQueryWrapper<MainPlanTableEntity>().eq(MainPlanTableEntity::getUploadDate, uploadDate));

            try {

                EasyExcel.read(excelFile.getInputStream(), MainPlanTableEntity.class, new MainPlanTableListener(mainPlanTableMapper, uploadDate)).sheet().doRead();
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

    @Override
    public void checkTableData(String fileName, MultipartFile excelFile, Date uploadDate) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
//            this.remove(new LambdaQueryWrapper<MainPlanTableEntity>().eq(MainPlanTableEntity::getUploadDate, uploadDate));

            try {

                EasyExcel.read(excelFile.getInputStream(), MainPlanTableEntity.class, new MainPlanTableListener(mainPlanTableMapper, uploadDate)).sheet().doRead();
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




