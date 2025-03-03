package com.heli.production.domain.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;


/**
 * 整机扫码记录对象 production_scan_records
 *
 * @author ruoyi
 * @date 2025-02-27
 */
@Data
public class ScanRecordsEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ExcelIgnore
    private String id;

    /**
     * 线号
     */
    @Excel(name = "线号")
    @ExcelProperty(value = "线号")
    private String lineNumber;

    /**
     * 整机ID
     */
    @Excel(name = "整机ID")
    @ExcelProperty(value = "整机ID")
    private String machineId;

    /**
     * 计划日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "计划日期")
    @DateTimeFormat(value = "yy-MM-dd")
    private Date plannedDate;

    /**
     * 型号
     */
    @Excel(name = "型号")
    @ExcelProperty(value = "型号")
    private String model;

    /**
     * 门架型号
     */
    @Excel(name = "门架型号")
    @ExcelProperty(value = "门架型号")
    private String gantryModel;

    /**
     * 吨位
     */
    @Excel(name = "吨位")
    @ExcelProperty(value = "吨位")
    private String tonnage;

    /**
     * 生产单号
     */
    @Excel(name = "生产单号")
    @ExcelProperty(value = "生产单号")
    private String productionOrderNumber;

    /**
     * OK数量
     */
    @Excel(name = "OK数量")
    @ExcelProperty(value = "OK数量")
    private Long okQuantity;

    /**
     * 发送标记
     */
    @Excel(name = "发送标记")
    @ExcelProperty(value = "发送标记")
    private String sendFlag;

    /**
     * 生成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生成时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "生成时间")
    @DateTimeFormat(value = "yy-MM-dd HH:mm")

    private Date creationTime;

    /**
     * 自动重新报工时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "自动重新报工时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "自动重新报工时间")
    @DateTimeFormat(value = "yy-MM-dd HH:mm")
    private Date autoReworkTime;

    /**
     * 报工人
     */
    @Excel(name = "报工人")
    @ExcelProperty(value = "报工人")
    private String operator;

    /**
     * SAP返回标记
     */
    @Excel(name = "SAP返回标记")
    @ExcelProperty(value = "SAP返回标记")
    private String sapReturnFlag;

    /**
     * SAP报工返回信息
     */
    @Excel(name = "SAP报工返回信息")
    @ExcelProperty(value = "SAP报工返回信息")
    private String sapWorkorderReturnInfo;

    /**
     * 重发收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "重发收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "重发收货时间")
    @DateTimeFormat(value = "yy-MM-dd HH:mm ss")
    private Date resendReceiptTime;

    /**
     * SAP收货返回消息
     */
    @Excel(name = "SAP收货返回消息")
    @ExcelProperty(value = "SAP收货返回消息")
    private String sapReceiptReturnMessage;

    /**
     * SAP收货状态
     */
    @Excel(name = "SAP收货状态")
    @ExcelProperty(value = "SAP收货状态")
    private String sapReceiptStatus;

    /**
     * SAP收货返回状态
     */
    @Excel(name = "SAP收货返回状态")
    @ExcelProperty(value = "SAP收货返回状态")
    private String sapReceiptReturnStatus;

}
