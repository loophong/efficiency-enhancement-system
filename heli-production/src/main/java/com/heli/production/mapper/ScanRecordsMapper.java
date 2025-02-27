package com.heli.production.mapper;

import com.heli.production.domain.entity.ScanRecordsEntity;

import java.util.List;


/**
 * 整机扫码记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-02-27
 */
public interface ScanRecordsMapper {
    /**
     * 查询整机扫码记录
     *
     * @param id 整机扫码记录主键
     * @return 整机扫码记录
     */
    public ScanRecordsEntity selectScanRecordsById(String id);

    /**
     * 查询整机扫码记录列表
     *
     * @param scanRecords 整机扫码记录
     * @return 整机扫码记录集合
     */
    public List<ScanRecordsEntity> selectScanRecordsList(ScanRecordsEntity scanRecords);

    /**
     * 新增整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    public int insertScanRecords(ScanRecordsEntity scanRecords);

    /**
     * 修改整机扫码记录
     *
     * @param scanRecords 整机扫码记录
     * @return 结果
     */
    public int updateScanRecords(ScanRecordsEntity scanRecords);

    /**
     * 删除整机扫码记录
     *
     * @param id 整机扫码记录主键
     * @return 结果
     */
    public int deleteScanRecordsById(String id);

    /**
     * 批量删除整机扫码记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteScanRecordsByIds(String[] ids);

    void batchInsert(List<ScanRecordsEntity> cacheDataList);
}
