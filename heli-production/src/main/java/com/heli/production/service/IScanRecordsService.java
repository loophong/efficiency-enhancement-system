package com.heli.production.service;

import com.heli.production.domain.entity.ScanRecordsEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



/**
 * 整机扫码记录Service接口
 *
 * @author ruoyi
 * @date 2025-02-27
 */
public interface IScanRecordsService {
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
     * 批量删除整机扫码记录
     *
     * @param ids 需要删除的整机扫码记录主键集合
     * @return 结果
     */
    public int deleteScanRecordsByIds(String[] ids);

    /**
     * 删除整机扫码记录信息
     *
     * @param id 整机扫码记录主键
     * @return 结果
     */
    public int deleteScanRecordsById(String id);

    void readSalaryExcelToDB(String originalFilename, MultipartFile excelFile);
}
