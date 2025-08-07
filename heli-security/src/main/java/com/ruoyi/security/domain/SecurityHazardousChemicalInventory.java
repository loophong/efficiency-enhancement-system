package com.ruoyi.security.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危险化学品台账对象 security_hazardous_chemical_inventory
 *
 * @author ruoyi
 * @date 2025-02-26
 */
@Data
public class SecurityHazardousChemicalInventory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "序号", type = Excel.Type.IMPORT)
    @ExcelProperty("序号")
    private String id;

    /** 物质名称 */
    @Excel(name = "物质名称")
    @ExcelProperty("物质名称")
    private String substanceName;

    /** 危化品序号 */
    @Excel(name = "危化品序号")
    @ExcelProperty("危化品序号")
    private String hazardousMaterialsNumber;

    /** CAS号 */
    @Excel(name = "CAS号")
    @ExcelProperty("CAS号")
    private String casNumber;

    /** 火灾危险性类别 */
    @Excel(name = "火灾危险性类别")
    @ExcelProperty("火灾危险性类别")
    private String fireDangerClass;

    /** 外观 */
    @Excel(name = "外观")
    @ExcelProperty("外观")
    private String appearance;

    /** 最大设计存量 */
    @Excel(name = "最大设计存量")
    @ExcelProperty("最大设计存量")
    private String maxDesignStock;

    /** 年用量 */
    @Excel(name = "年用量")
    @ExcelProperty("年用量")
    private String annualUsage;

    /** 日常存量 */
    @Excel(name = "日常存量")
    @ExcelProperty("日常存量")
    private String dailyStock;

    /** 储存地点 */
    @Excel(name = "储存地点")
    @ExcelProperty("储存地点")
    private String storageLocation;

    /** 管控负责人 */
    @Excel(name = "管控负责人")
    @ExcelProperty("管控负责人")
    private String managementResponsiblePerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    @ExcelProperty("联系电话")
    private String contactNumber;

    /** 备注 */
    @Excel(name = "备注")
    @ExcelProperty("备注")
    private String remarks;

    /** 关联ID */
    private Long relatedId;

}
