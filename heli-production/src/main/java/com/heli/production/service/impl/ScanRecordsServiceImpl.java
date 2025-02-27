package com.heli.production.service.impl;


import com.alibaba.excel.EasyExcel;
import com.heli.production.Listener.HistoryOrderListener;
import com.heli.production.Listener.ScanRecordsListener;
import com.heli.production.domain.entity.HistoryOrderEntity;
import com.heli.production.domain.entity.ScanRecordsEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.ScanRecordsMapper;
import com.heli.production.service.IScanRecordsService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 整机扫码记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-27
 */
@Slf4j
@Service
public class ScanRecordsServiceImpl implements IScanRecordsService {
    @Autowired
    private ScanRecordsMapper scanRecordsMapper;

    /**
     * 查询整机扫码记录
     *
     * @param id 整机扫码记录主键
     * @return 整机扫码记录
     */
    @Override
    public ScanRecordsEntity selectScanRecordsById(String id) {
        return scanRecordsMapper.selectScanRecordsById(id);
    }

    /**
     * 查询整机扫码记录列表
     *
     * @param scanRecords 整机扫码记录
     * @return 整机扫码记录
     */
    @Override
    public List<ScanRecordsEntity> selectScanRecordsList(ScanRecordsEntity scanRecords) {
        return scanRecordsMapper.selectScanRecordsList(scanRecords);
    }

    /**
     * 新增整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    @Override
    public int insertScanRecords(ScanRecordsEntity scanRecords) {
        return scanRecordsMapper.insertScanRecords(scanRecords);
    }

    /**
     * 修改整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    @Override
    public int updateScanRecords(ScanRecordsEntity scanRecords) {
        return scanRecordsMapper.updateScanRecords(scanRecords);
    }

    /**
     * 批量删除整机扫码记录
     *
     * @param ids 需要删除的整机扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteScanRecordsByIds(String[] ids) {
        return scanRecordsMapper.deleteScanRecordsByIds(ids);
    }

    /**
     * 删除整机扫码记录信息
     *
     * @param id 整机扫码记录主键
     * @return 结果
     */
    @Override
    public int deleteScanRecordsById(String id) {
        return scanRecordsMapper.deleteScanRecordsById(id);
    }

    @Override
    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        // 读取文件内容
        log.info("开始读取文件: {}", fileName);

        try {
            EasyExcel.read(excelFile.getInputStream(), ScanRecordsEntity.class, new ScanRecordsListener(scanRecordsMapper)).sheet().doRead();
            log.info("读取文件成功: {}", fileName);

        } catch (Exception e) {
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
        }
    }
}
