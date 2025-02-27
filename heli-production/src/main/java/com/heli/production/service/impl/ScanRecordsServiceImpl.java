package com.heli.production.service.impl;


import com.heli.production.domain.entity.ScanRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.ScanRecordsMapper;
import com.heli.production.service.IScanRecordsService;

import java.util.List;

/**
 * 整机扫码记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-02-27
 */
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
    public ScanRecords selectScanRecordsById(String id) {
        return scanRecordsMapper.selectScanRecordsById(id);
    }

    /**
     * 查询整机扫码记录列表
     *
     * @param scanRecords 整机扫码记录
     * @return 整机扫码记录
     */
    @Override
    public List<ScanRecords> selectScanRecordsList(ScanRecords scanRecords) {
        return scanRecordsMapper.selectScanRecordsList(scanRecords);
    }

    /**
     * 新增整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    @Override
    public int insertScanRecords(ScanRecords scanRecords) {
        return scanRecordsMapper.insertScanRecords(scanRecords);
    }

    /**
     * 修改整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    @Override
    public int updateScanRecords(ScanRecords scanRecords) {
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
}
