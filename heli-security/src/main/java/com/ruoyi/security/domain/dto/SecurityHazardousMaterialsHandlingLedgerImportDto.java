package com.ruoyi.security.domain.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 危化品处理台账Excel导入DTO
 * 
 * @author wang
 * @date 2025-07-16
 */
@Data
public class SecurityHazardousMaterialsHandlingLedgerImportDto {

    /** 序号 */
    @ExcelProperty(index = 0)
    @Excel(name = "序号")
    private String id;

    /** 处理时间 */
    @ExcelProperty(index = 1)
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String handlingTime;

    /** 化学品名称 */
    @ExcelProperty(index = 2)
    @Excel(name = "化学品名称")
    private String chemicalName;

    /** 使用负责人 */
    @ExcelProperty(index = 3)
    @Excel(name = "使用负责人")
    private String userInCharge;

    /** 处理量 */
    @ExcelProperty(index = 4)
    @Excel(name = "处理量")
    private String handlingQuantity;

    /** 处理方法 */
    @ExcelProperty(index = 5)
    @Excel(name = "处理方法")
    private String handlingMethod;

    /** 是否合规 */
    @ExcelProperty(index = 6)
    @Excel(name = "是否合规")
    private String isCompliant;

    /** 处理人 */
    @ExcelProperty(index = 7)
    @Excel(name = "处理人")
    private String handler;
}
